package edu.ku.cete.controller.test;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.QuestarRegistrationReason;
import edu.ku.cete.report.domain.QuestarStagingFile;
import edu.ku.cete.report.domain.QuestarStagingRecord;
import edu.ku.cete.report.domain.QuestarStagingResponse;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.QuestarService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.xml.XmlUtil;

@Component("questarPaperPencilFileProcessor")
public class QuestarPaperPencilFileProcessor {
	private Logger LOGGER = LoggerFactory.getLogger(QuestarPaperPencilFileProcessor.class);
	
	@Autowired
	private QuestarService questarService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private BatchRegistrationService brService;
	
	@Value("${questar.ftp.directory}")
	private String questarFtpDir;
	
	@Value("${questar.ftp.directory.success}")
	private String processedQuestarDir;
	
	@Value("${questar.ftp.directory.fail}")
	private String failedQuestarDir;
	
	@Async
	public Future<List<String>> processFiles() throws IOException, ParserConfigurationException, SAXException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		BatchRegistration brRecord = new BatchRegistration();
		brRecord.setSubmissionDate(new Date());
		brRecord.setStatus("IN-PROGRESS");
		brRecord.setSuccessCount(0);
		brRecord.setFailedCount(0);
		brRecord.setBatchTypeCode(SourceTypeEnum.QUESTAR.name());
		brRecord.setCreatedDate(new Date());
		brRecord.setModifiedDate(new Date());
		brRecord.setCreatedUser(user.getId());
		brService.insertSelectiveBatchRegistration(brRecord);
		
		File ftpDir = new File(questarFtpDir);
		List<String> responses = new ArrayList<String>();
		
		if (!ftpDir.exists()) {
			brRecord.setStatus(BatchStatus.COMPLETED.name());
			brRecord.setModifiedDate(new Date());
			brService.updateBatchRegistrationSelective(brRecord);
			LOGGER.info("Import directory does not exist. (possibly not set correctly?)");
			responses.add("Import directory does not exist. (possibly not set correctly?)");
			return new AsyncResult<List<String>>(responses);
		}
		
		String processedDirStr = processedQuestarDir;
		if (!processedDirStr.endsWith(File.separator)) {
			processedDirStr += File.separator;
		}
		
		String failedDirStr = failedQuestarDir;
		if (!failedDirStr.endsWith(File.separator)) {
			failedDirStr += File.separator;
		}
		
		File processedDir = new File(processedDirStr);
		if (!processedDir.exists()) {
			processedDir.mkdirs();
		}
		
		File failedDir = new File(failedDirStr);
		if (!failedDir.exists()) {
			failedDir.mkdirs();
		}
		
		Iterator<File> files = FileUtils.iterateFiles(ftpDir, new String[]{"xml"}, false);
		if (!files.hasNext()) {
			brRecord.setStatus(BatchStatus.COMPLETED.name());
			brRecord.setModifiedDate(new Date());
			brService.updateBatchRegistrationSelective(brRecord);
			LOGGER.info("No files to import.");
			responses.add("No files to import.");
			return new AsyncResult<List<String>>(responses);
		}
		
		// this will be used by the processing methods to avoid making lots of redundant queries 
		Map<Long, String> taskVariantTypeCache = new HashMap<Long, String>();
		
		int successCount = 0;
		int failedCount = 0;
		while (files.hasNext()) {
			File file = files.next();
			LOGGER.debug("Processing file " + file.getName());
			Document doc = null;
			try {
				doc = XmlUtil.readFromFile(file);
			} catch (Exception e) {
				LOGGER.error("Error reading XML file: " + file.getAbsolutePath(), e);
				continue;
			}
			
			// parse top-level stuff that isn't specific to one set of responses
			Element assessmentElement = (Element) doc.getDocumentElement().getElementsByTagName("Assessment").item(0);
			NodeList studentResponseSets = doc.getDocumentElement().getElementsByTagName("StudentResponseSet");
			
			QuestarStagingFile qsf = new QuestarStagingFile();
			qsf.setFileName(file.getName());
			qsf.setAssessmentRefId(assessmentElement.getAttribute("RefId"));
			qsf.setAssessmentName(XmlUtil.getChildProperty(assessmentElement, "Name"));
			qsf.setResult("INPROGRESS");
			questarService.insertStagingFileRecord(qsf);
			
			int importCount = 0;
			try {
				importCount = processQuestarXMLDocument(doc, qsf.getId(), taskVariantTypeCache, brRecord.getId());
			} catch (Exception e) {
				LOGGER.error("Caught Exception: ", e);
			}
			
			boolean success = importCount > 0;
			
			String status = "SUCCESS";
			File newFile;
			if (success) {
				successCount++;
				newFile = new File(processedDirStr + file.getName());
			} else {
				failedCount++;
				status = "FAILED";
				newFile = new File(failedDirStr + file.getName());
				LOGGER.debug("Did not import responses from " + file.getName());
			}
			
			if (!file.renameTo(newFile)) {
				responses.add("Failed to move " + file.getName() + " to " + (success ? processedDirStr : failedDirStr));
				LOGGER.error("Failed to move " + file.getName() + " to " + (success ? processedDirStr : failedDirStr));
			} else {
				responses.add(file.getName() + " (" + status + " -- " + importCount + "/" + studentResponseSets.getLength() + ")");
			}
			
			qsf.setSuccessCount(importCount);
			qsf.setSkipCount(studentResponseSets.getLength() - importCount);
			qsf.setResult(status);
			qsf.setProcessedDate(new Date());
			questarService.updateStagingFileRecord(qsf);
		}
		
		brRecord.setSuccessCount(successCount);
		brRecord.setFailedCount(failedCount);
		brRecord.setStatus(BatchStatus.COMPLETED.name());
		brRecord.setModifiedDate(new Date());
		brService.updateBatchRegistrationSelective(brRecord);
		
		return new AsyncResult<List<String>>(responses);
	}
	
	private int processQuestarXMLDocument(Document doc, Long questarStagingFileId, Map<Long, String> tvTypeCache, Long batchRegistrationId)
			throws TransformerConfigurationException, TransformerException {
		int numberImported = 0;
		NodeList studentResponseSets = doc.getDocumentElement().getElementsByTagName("StudentResponseSet");
		for (int x = 0; x < studentResponseSets.getLength(); x++) {
			Element studentResponseSet = (Element) studentResponseSets.item(x);
			Element extendedElementsElement = (Element) studentResponseSet.getElementsByTagName("SIF_ExtendedElements").item(0);
			
			QuestarStagingRecord record = parseQuestarStagingRecord(studentResponseSet, extendedElementsElement, batchRegistrationId,
					questarStagingFileId);
			
			if (record == null) {
				LOGGER.debug("Could not build QuestarStagingRecord for:\n" + XmlUtil.convertToXMLString(studentResponseSets.item(x)));
				continue;
			}
			
			// file id is not directly related to the student, but the current file as a whole
			record.setQuestarStagingFileId(questarStagingFileId);
			
			Long testExternalId = questarService.mapTestExternalId(record.getFormNumber());
			if (testExternalId == null) {
				String msg = "Could not find mapping for form number " + record.getFormNumber() + " for student state ID " + record.getStudentStateId();
				LOGGER.debug(msg + " -- skipping...");
				writeReason(batchRegistrationId, msg, record.getStudentKiteNumber(), questarStagingFileId);
				continue;
			}
			int duplicateCount = questarService.findDuplicateRecords(record);
			if (duplicateCount > 0) {
				String msg = "Student state ID " + record.getStudentStateId() + " was already imported with form number " + record.getFormNumber();
				LOGGER.debug(msg);
				writeReason(batchRegistrationId, msg, record.getStudentKiteNumber(), questarStagingFileId);
				continue;
			}
			
			Element itemsElement = (Element) studentResponseSet.getElementsByTagName("Items").item(0);
			NodeList itemElements = itemsElement.getElementsByTagName("Item");
			
			Map<String, String> intensityAttrs = new HashMap<String, String>();
			intensityAttrs.put("Name", "MarkIntensity");
			Element markIntensity = XmlUtil.getChildWithAttributes(extendedElementsElement, "SIF_ExtendedElement", intensityAttrs);
			Element intensityItems = (Element) markIntensity.getElementsByTagName("IntensityItems").item(0);
			
			boolean error = false;
			List<QuestarStagingResponse> responses = new ArrayList<QuestarStagingResponse>();
			for (int i = 0; i < itemElements.getLength(); i++) {
				Element item = (Element) itemElements.item(i);
				
				List<QuestarStagingResponse> currentItemResponses = parseQuestarStagingResponses(item, intensityItems, tvTypeCache, testExternalId, batchRegistrationId,
						record);
				
				if (currentItemResponses == null) {
					LOGGER.debug("Error building response info -- skipping student state ID " + record.getStudentStateId() +
							", form number " + record.getFormNumber());
					error = true;
					break;
				} else {
					int size = currentItemResponses.size();
					if (size == 0) {
						String itemNumberStr = XmlUtil.getChildProperty(item, "ItemNumber");
						String itemNameStr = XmlUtil.getChildProperty(item, "ItemName");
						String msg = "Could not build any responses for student state ID " + record.getStudentStateId() +
								", form number " + record.getFormNumber() + ", item number " + itemNumberStr + ", item name " + itemNameStr;
						LOGGER.debug(msg);
						msg += " -- Staging record will still appear";
						writeReason(batchRegistrationId, msg, record.getStudentKiteNumber(), questarStagingFileId);
					}
					for (int j = 0; j < size; j++) {
						QuestarStagingResponse response = currentItemResponses.get(j);
						if (response.getId() == null) { // flag the id as not null for the student NOT selecting, so we don't need it
							responses.add(response);
						}
					}
				}
			}
			if (!error) {
				questarService.insertStagingInfo(record, responses);
				numberImported++;
			}
		}
		return numberImported;
	}
	
	private QuestarStagingRecord parseQuestarStagingRecord(Element studentResponseSet, Element extendedElements, Long batchRegistrationId,
			Long questarStagingFileId) {
		Map<String, String> studentAttrs = new HashMap<String, String>();
		studentAttrs.put("Name", "Student");
		Element studentElement = XmlUtil.getChildWithAttributes(extendedElements, "SIF_ExtendedElement", studentAttrs);
		
		String[] aStudentProperties = {"WalkIn", "FormNumber", "DistrictCode", "SchoolCode", "Subject",
				"LastName", "FirstName", "MiddleName", "Grade", "StudentKiteNumber", "StudentId", "DOB", "SpecialCircumstances", "Accommodation"};
		Map<String, String> studentProperties = XmlUtil.getChildProperties(studentElement, aStudentProperties);
		
		for (String prop : aStudentProperties) {
			if (studentProperties.get(prop) == null) {
				LOGGER.debug("Could not find property \"" + prop + "\" in StudentResponseSet");
			}
		}
		
		boolean walkIn = "true".equalsIgnoreCase(studentProperties.get("WalkIn"));
		Long formNumber = null, studentId = null;
		
		try {
			formNumber = Long.parseLong(studentProperties.get("FormNumber"));
			if (!walkIn) { // they won't have a StudentKiteNumber if they're a walk-in
				studentId = Long.parseLong(studentProperties.get("StudentKiteNumber"));
			}
		} catch (Exception e) {
			String msg = "Could not parse FormNumber \""+studentProperties.get("FormNumber")+"\""+
					(walkIn ? "" : (", StudentKiteNumber \""+studentProperties.get("StudentKiteNumber")+"\""));
			LOGGER.debug(msg + " -- skipping...");
			writeReason(batchRegistrationId, msg, null, questarStagingFileId);
			return null;
		}
		
		Date dateOfBirth = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			dateOfBirth = df.parse(studentProperties.get("DOB"));
		} catch (Exception e) {
			LOGGER.debug("Could not parse DOB \"" + studentProperties.get("DOB") + "\" -- skipping...");
		}
		
		String districtCode, schoolCode, subject, firstName, lastName, middleName, grade, stateStudentId, scCode, accommodation;
		districtCode = studentProperties.get("DistrictCode");
		schoolCode = studentProperties.get("SchoolCode");
		subject = studentProperties.get("Subject");
		firstName = studentProperties.get("FirstName");
		lastName = studentProperties.get("LastName");
		middleName = studentProperties.get("MiddleName");
		grade = studentProperties.get("Grade");
		stateStudentId = studentProperties.get("StudentId");
		scCode = studentProperties.get("SpecialCircumstances");
		accommodation = studentProperties.get("Accommodation");
		
		QuestarStagingRecord record = new QuestarStagingRecord();
		record.setCreateDate(new Date());
		record.setRefId(studentResponseSet.getAttribute("RefId"));
		record.setAssessmentAdministrationRefId(studentResponseSet.getAttribute("AssessmentAdministrationRefId"));
		record.setStudentPersonalRefId(studentResponseSet.getAttribute("StudentPersonalRefId"));
		record.setWalkIn(walkIn);
		record.setFormNumber(formNumber);
		record.setDistrictCode(districtCode);
		record.setSchoolCode(schoolCode);
		record.setSubject(subject);
		record.setGrade(grade);
		record.setStudentFirstName(firstName);
		record.setStudentMiddleName(middleName);
		record.setStudentLastName(lastName);
		record.setStudentKiteNumber(studentId);
		record.setStudentStateId(stateStudentId);
		record.setStudentDateOfBirth(dateOfBirth);
		record.setScCode(scCode);
		record.setAccommodation(accommodation);
		record.setStatus("READY");
		
		return record;
	}
	
	private List<QuestarStagingResponse> parseQuestarStagingResponses(Element item, Element intensityItemsElement, Map<Long, String> tvTypeCache, Long testExternalId,
			Long batchRegistrationId, QuestarStagingRecord record) {
		final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
		
		QuestarStagingResponse response = new QuestarStagingResponse();
		List<QuestarStagingResponse> finalResponses = new ArrayList<QuestarStagingResponse>();
		
		String itemNumberStr = XmlUtil.getChildProperty(item, "ItemNumber"); // sequential order on the test
		String itemNameStr = XmlUtil.getChildProperty(item, "ItemName"); // taskvariantid_bubble
		String diagnosticStatement = XmlUtil.getChildProperty(item, "DiagnosticStatement");
		String numberOfAttemptsStr = XmlUtil.getChildProperty(item, "NumberOfAttempts");
		String responseValue = XmlUtil.getChildProperty(item, "Response");
		if (StringUtils.isEmpty(responseValue)) {
			String msg = "Student State ID " + record.getStudentStateId() + " had a null/empty response for form number " + record.getFormNumber() +
					", item number " + itemNumberStr + ", item name " + itemNameStr;
			LOGGER.debug(msg);
			writeReason(batchRegistrationId, msg, record.getStudentKiteNumber(), record.getQuestarStagingFileId());
			return null;
		}
		String[] itemNameParts = itemNameStr.split("_");
		
		Long itemNumber = null;
		try {
			itemNumber = Long.parseLong(itemNumberStr);
		} catch (NumberFormatException nfe) {
			String msg = "Student State ID " + record.getStudentStateId() + ", form number " + record.getFormNumber() + 
					" had an unexpected itemNumber -- " + itemNumberStr;
			LOGGER.debug(msg);
			writeReason(batchRegistrationId, msg, record.getStudentKiteNumber(), record.getQuestarStagingFileId());
			return null;
		}
		
		Long itemName = null;
		try {
			itemName = Long.parseLong(itemNameParts[0]); // don't need the result
		} catch (NumberFormatException nfe) {
			String msg = "Student State ID " + record.getStudentStateId() + ", form number " + record.getFormNumber() + 
					" had an unexpected itemName -- " + itemNameStr;
			LOGGER.debug(msg);
			writeReason(batchRegistrationId, msg, record.getStudentKiteNumber(), record.getQuestarStagingFileId());
			return null;
		}
		
		Integer numberOfAttempts = null;
		try {
			numberOfAttempts = Integer.parseInt(numberOfAttemptsStr);
		} catch (NumberFormatException nfe) {
			LOGGER.debug("Could not parse \"" + numberOfAttemptsStr + "\" as number of attempts, assuming null");
		}
		
		Map<String, String> intensityItemAttrs = new HashMap<String, String>();
		intensityItemAttrs.put("ItemNumber", itemNumberStr);
		intensityItemAttrs.put("ItemName", itemNameStr);
		Element intensityItem = XmlUtil.getChildWithAttributes(intensityItemsElement, "IntensityItem", intensityItemAttrs);
		
		String taskType = null;
		if (tvTypeCache.containsKey(itemName)) {
			taskType = tvTypeCache.get(itemName);
		} else {
			taskType = testService.getTaskTypeCodeByTaskVariantExternalIdAndTestExternalId(itemName, testExternalId);
			tvTypeCache.put(itemName, taskType);
		}
		
		if (taskType == null) {
			String msg = "Student State ID " + record.getStudentStateId() + ", form number " + record.getFormNumber() + 
					" -- could not find task type for task variant external id " + itemName;
			LOGGER.debug(msg);
			writeReason(batchRegistrationId, msg, record.getStudentKiteNumber(), record.getQuestarStagingFileId());
			return null;
		}
		
		// silly hacky variables -- yay urgency!
		boolean customMethod = false;
		boolean findIntensity = true;
		if (responseValue.equals("-") || responseValue.equals("U")) {
			response.setId(-1L); // flag for the student NOT selecting any bubbles for this question, so we don't need it, we check this for not null above
			finalResponses.add(response);
			return finalResponses;
		} else if (responseValue.equals("+")) {
			customMethod = true;
			findIntensity = false; // we'll find intensity within here, because there are multiple
			List<QuestarStagingResponse> d = buildMultipleResponses(item, intensityItem);
			finalResponses.addAll(d);
		}
		
		int choice = -1;
		if (taskType.equals("MC-MS") || (taskType.equals("ITP") && !responseValue.equals("+"))) { // MC-MS and ITP follow the same rules (for now, at least)
			if (responseValue.equals("S")) {
				// itemName is XXXXX_AX, XXXXX_BX, XXXXX_CX, etc.
				responseValue = itemNameParts[1];
				choice = 1; // always want intensity of choice 1 because of the formatting
			}
		}
		
		if (findIntensity) {
			if (choice == -1) {
				choice = ALPHA.indexOf(responseValue.toLowerCase().charAt(0)) + 1;
			}
			Map<String, String> intensityChoiceMap = new HashMap<String, String>();
			intensityChoiceMap.put("Choice", String.valueOf(choice));
					
			if(intensityItem != null) {
				String intensity = XmlUtil.getChildPropertyWithAttributes(intensityItem, "Intensity", intensityChoiceMap);
				
				if(intensity != null) {
					response.setIntensityHex(String.valueOf(intensity.charAt(0))); // only pull the first character
				} else {
					//::TODO what to do in case of null intensity item
					LOGGER.debug(" Null intensity found for an intensity item");				
				}
			} else {
				//::TODO what to do in case of null intensity item
				LOGGER.debug(" Null intensityitem found for a response");
			}
		}
		
		if (!customMethod) {
			finalResponses.add(response);
			
			for (int x = 0; x < finalResponses.size(); x++) {
				finalResponses.get(x).setResponse(responseValue);
			}
		}
		
		for (int x = 0; x < finalResponses.size(); x++) {
			finalResponses.get(x).setItemNumber(itemNumber);
			finalResponses.get(x).setItemName(itemNameStr);
			finalResponses.get(x).setDiagnosticStatement(diagnosticStatement);
			finalResponses.get(x).setNumberOfAttempts(numberOfAttempts);
			finalResponses.get(x).setTaskTypeCode(taskType);
		}
		
		return finalResponses;
	}
	
	private List<QuestarStagingResponse> buildMultipleResponses(Element item, Element intensityItemElement) {
		final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		List<QuestarStagingResponse> responses = new ArrayList<QuestarStagingResponse>();
		if (intensityItemElement != null) {
			List<QuestarStagingResponse> tmp = new ArrayList<QuestarStagingResponse>();
			
			NodeList intensityElements = intensityItemElement.getElementsByTagName("Intensity");
			for (int x = 0; x < intensityElements.getLength(); x++) {
				Element intensityElement = (Element) intensityElements.item(x);
				QuestarStagingResponse response = new QuestarStagingResponse();
				int intensity = 0;
				try {
					intensity = Integer.parseInt(intensityElement.getTextContent(), 16);
				} catch (NumberFormatException nfe) {
					continue;
				}
				if (intensity >= 5) { // less than 5 is an erase mark
					response.setIntensityHex(intensityElement.getTextContent());
					int alphaIndex = Integer.parseInt(intensityElement.getAttribute("Choice")) - 1;
					response.setResponse(String.valueOf(ALPHA.charAt(alphaIndex)));
					tmp.add(response);
				}
			}
			
			// check for the responses with over 10 intensity
			List<QuestarStagingResponse> over10 = new ArrayList<QuestarStagingResponse>();
			for (int x = 0; x < tmp.size(); x++) {
				QuestarStagingResponse response = tmp.get(x);
				int intensity = Integer.parseInt(response.getIntensityHex(), 16);
				if (intensity >= 10) {
					over10.add(response);
				}
			}
			
			Set<String> alreadyAddedResponses = new HashSet<String>(); // prevent duplicates from being added
			
			if (over10.size() > 1) { // if there's only 1, then it wouldn't trigger the mult, so it has to be the difference < 3
				responses.addAll(over10);
				for (int x = 0; x < over10.size(); x++) {
					QuestarStagingResponse over10Response = over10.get(x);
					alreadyAddedResponses.add(over10Response.getResponse());
				}
			}
			
			// check for responses with intensity difference less than 3
			List<QuestarStagingResponse> differenceUnder3 = new ArrayList<QuestarStagingResponse>();
			for (int x = 0; x < tmp.size(); x++) {
				QuestarStagingResponse response = tmp.get(x);
				int intensity = Integer.parseInt(response.getIntensityHex(), 16);
				for (int y = 0; y < tmp.size(); y++) {
					if (x == y) {
						continue;
					}
					QuestarStagingResponse response2 = tmp.get(y);
					int intensity2 = Integer.parseInt(response2.getIntensityHex(), 16);
					if (Math.abs(intensity - intensity2) < 3) {
						if (!alreadyAddedResponses.contains(response.getResponse())) {
							differenceUnder3.add(response);
							alreadyAddedResponses.add(response.getResponse());
						}
					}
				}
			}
			responses.addAll(differenceUnder3);
		}

		if (responses.isEmpty()) {
			LOGGER.debug("Did not populate any responses from a \"+\".");
		}
		return responses;
	}
	
	private void writeReason(Long batchRegistrationId, String msg, Long studentId, Long questarStagingFileId) {
		QuestarRegistrationReason reason = new QuestarRegistrationReason();
		reason.setBatchRegistrationId(batchRegistrationId);
		reason.setReason(msg);
		reason.setStudentId(studentId);
		reason.setQuestarStagingFileId(questarStagingFileId);
		List<QuestarRegistrationReason> reasons = new ArrayList<QuestarRegistrationReason>(1);
		reasons.add(reason);
		brService.insertQuestarReasons(reasons);
	}
}