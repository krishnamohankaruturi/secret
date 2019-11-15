package edu.ku.cete.controller.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.ku.cete.batch.BatchRegistrationProcessStarter;
import edu.ku.cete.domain.StudentResponseAudit;
import edu.ku.cete.domain.StudentResponseScore;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.TaskVariantDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.QuestarService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.xml.XmlUtil;
import edu.ku.cete.web.QuestarDTO;

@Controller
@RequestMapping("questar")
public class QuestarController {

	@Autowired
	private StudentsResponsesService studentsResponsesService;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private QuestarService questarService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired	
	private TaskVariantDao taskVariantDao; 
	
	@Autowired
	private AssessmentProgramService apService;
	
	@Autowired
	private OperationalTestWindowService operationalTestWindowService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Value("${questar.tasktypecodes}")
	private String taskTypeCodesProperty;
	
	@Value("${questar.ftp.directory}")
	private String questarFtpDir;
	
	@Value("${questar.ftp.directory.success}")
	private String processedQuestarDir;
	
	@Value("${questar.ftp.directory.fail}")
	private String failedQuestarDir;
	
	@Value("${questar.number.studentstests:#{null}}")
	private Long studentsTestsLimit;
	
	@Resource
	private Job questarJob;
	
	@Autowired
   	private BatchRegistrationProcessStarter batchRegStarter;
	
	@Autowired
	private QuestarPaperPencilFileProcessor questarPaperPencilFileProcessor;
	
	private Logger LOGGER = LoggerFactory.getLogger(QuestarController.class);
	
	private final String BLANK_RESPONSE = "Blank Response";
	
	@RequestMapping(value = "getResponses.htm", method = {RequestMethod.GET, RequestMethod.POST},
			produces = {MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_XML_VALUE})
	public final void getResponses(HttpServletRequest request, HttpServletResponse response)
					throws ParserConfigurationException, TransformerConfigurationException, TransformerException, IOException {
		LOGGER.trace("--> questar/getResponses");
		
		PrintWriter responseWriter = response.getWriter();
		String contentType = MediaType.APPLICATION_XML_VALUE;
		
		String ret = "";
		boolean bContinue = true;
		
		String strBatchId = request.getParameter("batchId");
		Long batchId = 0L;
		if (strBatchId != null) {
			try {
				batchId = Long.parseLong(strBatchId);
			} catch (NumberFormatException nfe) {
				LOGGER.debug("NumberFormatException occurred trying to parse \"" + strBatchId + "\" as batchId:", nfe);
				contentType = MediaType.TEXT_PLAIN_VALUE;
				ret = "Could not parse \"" + strBatchId + "\" as batchId.";
				bContinue = false;
			}
		}
		
		if (!bContinue) {
			response.setContentType(contentType);
			responseWriter.write(ret);
			return;
		}
		
		if (batchId > 0) {
			response.setHeader("batchId", strBatchId);
			bContinue = false;
			StudentResponseAudit audit = questarService.selectAuditByPimaryKey(batchId);
			if (audit != null) {
				if (audit.getXml() == null) {
					contentType = MediaType.TEXT_PLAIN_VALUE;
					ret = "No XML found in batch #" + batchId + ".";
				} else {
					// contentType is still XML
					ret = audit.getXml();
				}
			} else {
				contentType = MediaType.TEXT_PLAIN_VALUE;
				ret = "No batch #" + batchId + " found.";
			}
		}
		
		if (!bContinue) {
			response.setContentType(contentType);
			responseWriter.write(ret);
			return;
		}
		
		String strContentArea = request.getParameter("contentArea");
		String strGrade = request.getParameter("grade");
		
		if ((strContentArea == null || strContentArea.trim().length() == 0) ||
				(strGrade == null || strGrade.trim().length() == 0)) {
			contentType = MediaType.TEXT_PLAIN_VALUE;
			ret = "Content Area and Grade parameters not set correctly.";
			response.setContentType(contentType);
			responseWriter.write(ret);
			return;
		}
		
		Map<String,Object> criteria = new HashMap<String, Object>();
		criteria.put("contentAreaAbbr", strContentArea.trim());
		criteria.put("gradeCourseAbbr", strGrade.trim());
		
		// will really be a CB testId
		long testExternalId = questarService.getFirstVariantGroupWithCriteria(criteria);
		
		if (testExternalId == 0) {
			contentType = MediaType.TEXT_PLAIN_VALUE;
			ret = "No unprocessed tests found matching given criteria.";
			LOGGER.info("No unprocessed tests found for " + strContentArea + ", grade " + strGrade);
			response.setContentType(contentType);
			responseWriter.write(ret);
			return;
		}
		
		List<String> taskTypeCodes = Arrays.asList(taskTypeCodesProperty.split(","));
		
		// doesn't retrieve all questions in the test, just the ones in the group
		List<TaskVariant> variants = questarService.getTaskVariantsInGroup(testExternalId, taskTypeCodes);
		List<Long> variantIds = new ArrayList<Long>(variants.size());
		for (int x = 0; x < variants.size(); x++) {
			variantIds.add(variants.get(x).getId());
		}
		
		List<Long> studentsTestsIds = studentsTestsService.findCompletedIdsByTestExternalIdWithTaskVariants(
				testExternalId, variantIds, studentsTestsLimit);
		
		while (studentsTestsIds.isEmpty()) {
			criteria.put("greaterThanGroup", testExternalId);
			testExternalId = questarService.getFirstVariantGroupWithCriteria(criteria);
			
			if (testExternalId == 0) {
				break;
			}
			
			variants = questarService.getTaskVariantsInGroup(testExternalId, taskTypeCodes);
			variantIds.clear();
			for (int x = 0; x < variants.size(); x++) {
				variantIds.add(variants.get(x).getId());
			}
			studentsTestsIds = studentsTestsService.findCompletedIdsByTestExternalIdWithTaskVariants(
					testExternalId, variantIds, studentsTestsLimit);
		}
		
		// this would mean we checked every group and didn't find any completed tests
		if (testExternalId == 0 && studentsTestsIds.isEmpty()) {
			contentType = MediaType.TEXT_PLAIN_VALUE;
			ret = "No completed or in-progress tests found matching given criteria.";
			LOGGER.info("Found no completed or in-progress studentstests for " + strContentArea + ", grade " + strGrade);
			response.setContentType(contentType);
			responseWriter.write(ret);
			return;
		}
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		StudentResponseAudit audit = new StudentResponseAudit();
		audit.setCreatedUser(user.getId().intValue());
		audit.setStartDate(new Date());
		questarService.insertAuditSelective(audit);
		
		response.setHeader("batchId", String.valueOf(audit.getId()));
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		
		Element records = doc.createElement("SIF_Message");
		
		int recordsCount = 0;
		Map<Long, String> taskLayoutFormatCache = new HashMap<Long, String>(); // make only a handful of DB calls at most
		List<StudentsResponses> responsesToUpdate = new ArrayList<StudentsResponses>();
		
		// break up the data needed into chunks so that we don't use silly amounts of memory by bringing them all back at once
		// so, if there are 5300 studentstests entries we need to retrieve, we'll need 6 pages
		final int SIZE = 1000;
		final int LASTPAGE = (studentsTestsIds.size() / SIZE) + (studentsTestsIds.size() % SIZE == 0 ? 0 : 1);
		
		for (int page = 0; page < LASTPAGE; page++) {
			int endIndex = (page + 1) * SIZE;
			if (endIndex > studentsTestsIds.size()) {
				endIndex = studentsTestsIds.size(); // prevent IndexOutOfBoundsException by using the correct number for ending index
			}
			criteria.clear();
			criteria.put("studentsTestsIds", studentsTestsIds.subList(page * SIZE, endIndex));
			criteria.put("taskVariantIds", variantIds);
			List<QuestarDTO> dtos = studentsResponsesService.getResponsesForQuestar(criteria);
			
			// There is an assumption that all items in a group will be in one section.
			// If this ever changes, the AssessmentAdministrationRefId will HAVE to be moved somewhere.
			// The reason is because the studentsTestSectionsId will NOT be accurate at the top-level, like it's used now,
			// since the responses would NOT all come from the same section. This would result in XML coming back (to sendScores.htm)
			// that would NOT map to any response in our system, resulting in incorrect scoring.
			
			for (QuestarDTO dto : dtos) {
				Long studentsTestSectionsId = dto.getStudentsTestSectionsId();
				String studentPersonalRefId = dto.getStudentId() == null ? "" : String.valueOf(dto.getStudentId());
				
				List<StudentsResponses> responses = dto.getResponses();
				
				Element studentResponseSet = doc.createElement("StudentResponseSet");
				studentResponseSet.setAttribute("RefId", String.valueOf(dto.getStudentsTestsId()));
				studentResponseSet.setAttribute("AssessmentAdministrationRefId", String.valueOf(studentsTestSectionsId));
				studentResponseSet.setAttribute("StudentPersonalRefId", studentPersonalRefId);
				
				String responseValue = "";
				List<Long> externalIdsForVariantsInResponses = new ArrayList<Long>();
				
				for (int x = 0; x < responses.size(); x++) {
					StudentsResponses studentsResponses = responses.get(x);
					TaskVariant taskVariant = findVariantInGroup(variants, studentsResponses.getTaskVariantId());
					String taskTypeCode = taskVariant.getTaskType();
					
					externalIdsForVariantsInResponses.add(taskVariant.getExternalId());
					
					if (x > 0) {
						responseValue += "<br/><br/>&lt;-- KITE PAGE BREAK --&gt;<br/><br/>";
					}
					String resp = "";
					if ("ER".equals(taskTypeCode)) {
						resp = studentsResponses.getResponse();
					} else if ("CR".equals(taskTypeCode)) {
						resp = processCRResponse(taskVariant, studentsResponses);
					} else if ("MC-MS".equals(taskTypeCode)) {
						resp = processMCMSResponses(taskVariant, studentsResponses, taskLayoutFormatCache);
					} else { // MC-K, T-F
						resp = processMCResponses(taskVariant, studentsResponses, taskLayoutFormatCache);
					}
					
					if (resp == null || "".equals(resp)) {
						resp = BLANK_RESPONSE;
					}
					
					responseValue += resp;
					
					responsesToUpdate.add(studentsResponses);
					recordsCount++;
					/*try {
						studentsResponsesService.updateQuestarRequestId(audit.getId(), studentsTestSectionsId, studentsResponses.getTaskVariantId());
						recordsCount++;
					} catch (Exception e) {
						LOGGER.error("Exception occurred when updating studentstestsectionsid="+studentsTestSectionsId+
								", taskvariant="+studentsResponses.getTaskVariantId()+":", e);
					}*/
				}
				
				Element responseElement = doc.createElement("Response");
				Element responseLocation = doc.createElement("ResponseLocation");
				Element responseTime = doc.createElement("ResponseTime");
				Element itemNumber = doc.createElement("ItemNumber");
				Element itemName = doc.createElement("ItemName");
				Element diagnosticStatement = doc.createElement("DiagnosticStatement");
				Element numberOfAttempts = doc.createElement("NumberOfAttempts");
				
				responseElement.setTextContent(XmlUtil.sanitizeXmlChars(responseValue));
				responseTime.setTextContent(""); // empty now, may come later
				itemNumber.setTextContent(String.valueOf(testExternalId));
				itemName.setTextContent("");
				numberOfAttempts.setTextContent(""); // empty now, may come later
				
				// the following nodes are meant to be empty
				responseLocation.setTextContent("");
				diagnosticStatement.setTextContent("");
				
				Element item = doc.createElement("Item");
				item.appendChild(responseElement);
				item.appendChild(responseLocation);
				item.appendChild(responseTime);
				item.appendChild(itemNumber);
				item.appendChild(itemName);
				item.appendChild(diagnosticStatement);
				item.appendChild(numberOfAttempts);
				
				Element items = doc.createElement("Items");
				items.appendChild(item);
				studentResponseSet.appendChild(items);
				
				Element extendedElements = doc.createElement("SIF_ExtendedElements");
				for (int x = 0; x < externalIdsForVariantsInResponses.size(); x++) {
					Element extendedElement = doc.createElement("SIF_ExtendedElement");
					extendedElement.setAttribute("Name", "ItemID"+(x+1));
					extendedElement.setTextContent(String.valueOf(externalIdsForVariantsInResponses.get(x)));
					extendedElements.appendChild(extendedElement);
				}
				studentResponseSet.appendChild(extendedElements);
				
				records.appendChild(studentResponseSet);
			}
		}
		
		doc.appendChild(records);
		
		ret = XmlUtil.convertToXMLString(doc.getDocumentElement(), "Response");
		
		audit.setNumberOfResponses(recordsCount);
		audit.setEndDate(new Date());
		audit.setXml(ret);
		questarService.updateAuditSelective(audit);
		
		//questarService.updateGroupToProcessed(testExternalId);
		
		studentsResponsesService.updateQuestarRequestId(audit.getId(), responsesToUpdate);
		
		response.setContentType(contentType);
		responseWriter.write(ret);
		LOGGER.trace("<-- questar/getResponses");
	}
	
	private TaskVariant findVariantInGroup(List<TaskVariant> variants, Long id) {
		for (int x  = 0; x < variants.size(); x++) {
			if (variants.get(x).getId().longValue() == id.longValue()) {
				return variants.get(x);
			}
		}
		return null; // shouldn't ever happen
	}
	
	private String processCRResponse(TaskVariant taskVariant, StudentsResponses studentsResponses) {
		String stem = taskVariant.getTaskStem();
		String response = studentsResponses.getResponse();
		final String CR_STRING = "[&nbsp;]";
		
		// constructed response spans take the following form (assuming the users haven't edited the span itself, which would be BAD):
		// <span class="constructedResponse" contenteditable="false" id="cb-766409-6-18">[&nbsp;]</span>
		
		// color the response
		stem = stem.replace("<span class=\"constructedResponse\"", "<span class=\"constructedResponse\" style=\"background-color: #F7D85B;\"");
		
		if (response != null) {
			String[] responseParts = response.split("~~~");
			try {
				for (int x = 0; x < responseParts.length; x++) {
					String repl = responseParts[x];
					if ("".equals(repl)) {
						repl = BLANK_RESPONSE;
					}
					stem = StringUtils.replaceOnce(stem, CR_STRING, repl);
				}
			} catch (IndexOutOfBoundsException e) {
				LOGGER.error("IndexOutOfBoundsException: stem = " + stem);
				LOGGER.error("IndexOutOfBoundsException: response = " + response);
				throw e;
			}
		}
		
		stem = stem.replace(CR_STRING, BLANK_RESPONSE); // for any remaining responses
		return stem;
	}
	
	private String processMCMSResponses(TaskVariant taskVariant, StudentsResponses studentsResponses,
			Map<Long, String> taskLayoutFormatCache) {
		String responseValue = "";
		String strFoilIds = studentsResponses.getResponse();
		if (strFoilIds == null) {
			responseValue += "";
		} else {
			strFoilIds = strFoilIds.trim();
			// foils are in the form: [foilid,foilid,foilid]
			strFoilIds = strFoilIds.substring(1, strFoilIds.length() - 1); // get rid of []
			String[] parts = strFoilIds.split(",");
			for (int y = 0; y < parts.length; y++) {
				if (y > 0) {
					responseValue += ",";
				}
				
				// handles the empty case, since [] will still split to an array of size 1,
				// with the empty string as the element
				// no other case will ever have an empty string for MCMS, unless it's a bug in TDE
				if ("".equals(parts[y])) {
					continue;
				}
				Long foilId = Long.parseLong(parts[y]);
				Long layoutFormatId = taskVariant.getReportTaskLayoutFormatId();
				String layoutFormat = null;
				if (taskLayoutFormatCache.containsKey(layoutFormatId)) {
					layoutFormat = taskLayoutFormatCache.get(layoutFormatId);
				} else {
					layoutFormat = testService.getTaskLayoutFormatLettersById(layoutFormatId);
					taskLayoutFormatCache.put(layoutFormatId, layoutFormat);
				}
				Integer responseOrder = testService.getTaskVariantFoilOrder(taskVariant.getId(), foilId);
				String responseLabel = "";
				if (layoutFormat != null && responseOrder != null) {
					responseLabel = layoutFormat.split(",")[responseOrder];
					responseLabel = responseLabel.substring(0, responseLabel.length() - 1); // strip off the ending ")"
				}
				responseValue += responseLabel;
			}
		}
		return responseValue;
	}
	
	private String processMCResponses(TaskVariant taskVariant, StudentsResponses studentsResponses,
			Map<Long, String> taskLayoutFormatCache) {
		String responseValue = "";
		Long foilId = studentsResponses.getFoilId();
		if (foilId != null) {
			String layoutFormat = null;
			if (taskLayoutFormatCache.containsKey(taskVariant.getTaskLayoutFormatId())) {
				layoutFormat = taskLayoutFormatCache.get(taskVariant.getTaskLayoutFormatId());
			} else {
				layoutFormat = testService.getTaskLayoutFormatLettersById(taskVariant.getReportTaskLayoutFormatId());
				taskLayoutFormatCache.put(taskVariant.getTaskLayoutFormatId(), layoutFormat);
			}
			Integer responseOrder = testService.getTaskVariantFoilOrder(taskVariant.getId(), foilId);
			String responseLabel = "";
			if (layoutFormat != null && responseOrder != null) {
				responseLabel = layoutFormat.split(",")[responseOrder];
				responseLabel = responseLabel.substring(0, responseLabel.length() - 1); // strip off the ending ")"
			}
			responseValue += responseLabel;
		}
		return responseValue;
	}
	
	@RequestMapping(value = "confirmBatch.htm", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	private String confirmBatch(@RequestParam("batchId") Long questarRequestId) {
		String ret = "";
		StudentResponseAudit audit = questarService.selectAuditByPimaryKey(questarRequestId);
		if (audit != null) {
			if (!audit.getConfirmed()) {
				audit.setConfirmed(true);
				audit.setConfirmedDate(new Date());
				questarService.updateAuditSelective(audit);
				ret = "Thank you for confirming batch #" + questarRequestId + ".";
			} else {
				ret = "Batch #" + questarRequestId + " has already been confirmed.";
			}
		} else {
			ret = "No batch #" + questarRequestId + " found.";
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "sendScores.htm", method = {RequestMethod.POST})
	@ResponseBody
	public final String sendScores(@RequestParam(value = "data", required = false) String xml) throws IOException {
		LOGGER.trace("--> questar/sendScores");
		
		xml = StringUtils.trim(xml);
		
		Date now = new Date();
		
		String ret = "";
		int importedCount = 0;
		List<String> errored = new ArrayList<String>();
		
		List<Category> nonScorableCodes = categoryService.selectByCategoryType("NON_SCORABLE_CODE");
		Map<String, Long> nonScorableCodesMap = new HashMap<String, Long>();
		for (Category nsc : nonScorableCodes) {
			nonScorableCodesMap.put(nsc.getCategoryCode(), nsc.getId());
		}
		
		if (StringUtils.isBlank(xml)) {
			ret = "No data detected.";
		} else try {
			Document doc = XmlUtil.readFromString(xml);
			
			NodeList nodeList = doc.getElementsByTagName("StudentScoreSet");
			
			HashMap<Long, List<Long>> externalTaskIdMap = questarService.getQuestarTaskvariantMapByExternalId();
			
			for (int x = 0; x < nodeList.getLength(); x++) {
				Element studentScoreSet = (Element) nodeList.item(x);
				String sssRefId = StringUtils.trim(studentScoreSet.getAttribute("RefId"));
				String sssScoreMetric = StringUtils.trim(studentScoreSet.getAttribute("ScoreMetric"));
				String sssAssessmentAdministrationRefId = StringUtils.trim(studentScoreSet.getAttribute("AssessmentAdministrationRefId"));
				String sssStudentPersonalRefId = StringUtils.trim(studentScoreSet.getAttribute("StudentPersonalRefId"));
				
				//String studentScoreSetXML = XmlUtil.convertToXMLString(studentScoreSet, false);
				String scoreElementXML = "StudentId:" + sssStudentPersonalRefId + ", StudentTestId:"+ sssRefId 
						+ ", StudentTestSectionId:"+ sssAssessmentAdministrationRefId;
				
				Long studentId = null;
				Long studentsTestsId = null;
				Long studentsTestSectionsId = null;
				try {
					studentId = Long.parseLong(sssStudentPersonalRefId);
					studentsTestsId = Long.parseLong(sssRefId);
					studentsTestSectionsId = Long.parseLong(sssAssessmentAdministrationRefId);
				} catch (NumberFormatException nfe) {
					LOGGER.warn("Error parsing top-level node values (\"" + sssRefId + "\" as studentsTestsId, \"" +
							sssAssessmentAdministrationRefId + "\" as studentsTestSectionsId, \"" +
							sssStudentPersonalRefId + "\" as studentId" +
							")...moving to next <StudentScoreSet> element", nfe);
					errored.add("Could not parse top-level values...skipped entire node:\n " + scoreElementXML);
					continue;
				}
				
				List<StudentsResponses> stuResponsesOfTestSection = studentsResponsesService.findQuestarResponseByStudentTestSectionId(studentsTestSectionsId);
				
				NodeList scores = studentScoreSet.getElementsByTagName("Scores");
				for (int y = 0; y < scores.getLength(); y++) {
					Element scoresElement = (Element) scores.item(y);
					
					NodeList scoreElements = scoresElement.getElementsByTagName("Score");
					
					Map<String, Object> extendedElements = parseExtendedElementsMap((Element) scoresElement.getElementsByTagName("SIF_ExtendedElements").item(0));
					
					String strRaterId = (String) extendedElements.get("RaterID");
					Long raterId = null;
					try {
						raterId = Long.parseLong(strRaterId);
					} catch (NumberFormatException nfe) {
						LOGGER.warn("Error parsing raterId " + strRaterId + " for (" +
								sssRefId + " as studentsTestsId, " +
								sssAssessmentAdministrationRefId + " as studentsTestSectionsId, " +
								sssStudentPersonalRefId + " as studentId" +
								")...moving to next <Scores> element", nfe);
						errored.add("Skipped <Scores> element (and the " + scoreElements.getLength() +
								" <Score> elements it contains)--Could not parse RaterID \"" + strRaterId + "\" for " + XmlUtil.convertToXMLString(scoresElement, false));
						continue;
					}
					
					Integer dimensionCount = null;
					try {
						String strDimensionCount = (String) extendedElements.get("DimensionCount");
						dimensionCount = Integer.parseInt(strDimensionCount);
					} catch (NumberFormatException nfe) {
						dimensionCount = scoreElements.getLength(); // fall back to how many they gave us
						LOGGER.debug("Failed to parse dimension count...assuming it's equal to the # of score Elements (" + dimensionCount + ")");
					}
					
					String raterName = (String) extendedElements.get("RaterName");
					if ("".equals(raterName)) {
						raterName = null;
					}
					
					String strRaterOrder = (String) extendedElements.get("RaterSequence");
					Short raterOrder = null;
					try {
						raterOrder = Short.valueOf(strRaterOrder);
					} catch (NumberFormatException nfe) {
						LOGGER.debug("Could not parse \"" + strRaterOrder + "\" as rater order, assuming null");
					}
					
					String strRaterExposure = (String) extendedElements.get("RaterExposure");
					Integer raterExposure = null;
					try {
						raterExposure = Integer.valueOf(strRaterExposure);
					} catch (NumberFormatException nfe) {
						LOGGER.debug("Could not parse \"" + strRaterExposure + "\" as rater exposure, assuming null");
					}
										
					
					Long processedTVId = null;
					List<Long> taskVariantIds = null;
					
					for (int z = 0; z < scoreElements.getLength() && z < dimensionCount; z++) {
						Element scoreElement = (Element) scoreElements.item(z);
						//String scoreElementXML = XmlUtil.convertToXMLString(scoreElement, false);
						
						String sAssessmentSubTestRefId = StringUtils.trim(scoreElement.getAttribute("AssessmentSubTestRefId"));
						String scoreValue = StringUtils.trim(XmlUtil.getChildProperty(scoreElement, "ScoreValue"));
						String diagnosticStatement = StringUtils.trim(XmlUtil.getChildProperty(scoreElement, "DiagnosticStatement"));
						
						// try to parse taskVariantId
						Long taskVariantExternalId = null;
						String scoreElementXMLInner = scoreElementXML;
						try {
							taskVariantExternalId = Long.parseLong(sAssessmentSubTestRefId);
							scoreElementXMLInner +=  ", taskVariantId:" + taskVariantExternalId + ", raterInfo:" + raterId + "-"+ raterName;
							if(processedTVId == null) {
								processedTVId = taskVariantExternalId;
							}
						} catch (NumberFormatException nfe) {
							LOGGER.warn("Error parsing task variant " + sAssessmentSubTestRefId + " for (" +
									sssRefId + " as studentsTestsId, " +
									sssAssessmentAdministrationRefId + " as studentsTestSectionsId, " +
									sssStudentPersonalRefId + " as studentId" +
									")...moving to next <Score> element", nfe);
							errored.add("Skipped score element--Could not parse \"" + sAssessmentSubTestRefId + "\" to a task variant ID in " + scoreElementXMLInner);
							continue;
						}
						
						LOGGER.debug("Processing Score Point Scores: StudentId:" + studentId 
								+ ", StudentTestId:"+ studentsTestsId + ", StudentTestSectionId:"+ studentsTestSectionsId 
								+ ", taskVariantId:" + taskVariantExternalId + ", raterInfo:" + raterId +"-"+raterName);
						
						Long nonScorableCodeId = null;
						// try to parse score
						BigDecimal score = null;
						if (StringUtils.isNotBlank(scoreValue)) {
							try {
								score = new BigDecimal(scoreValue);
							} catch (NumberFormatException nfe) {
								nonScorableCodeId = nonScorableCodesMap.get(scoreValue);
								if (nonScorableCodeId == null) {
									LOGGER.warn("Error interpreting score \"" + scoreValue + "\" for (" +
											sssRefId + " as studentsTestsId, " +
											sssAssessmentAdministrationRefId + " as studentsTestSectionsId, " +
											sssStudentPersonalRefId + " as studentId" +
											")...moving to next <Score> element", nfe);
									errored.add("Skipped score element--Could not interpret score \"" + scoreValue + "\" for " + scoreElementXMLInner);
									continue;
								}
							}
						} else {
							LOGGER.warn("Skipped score element--ScoreValue was empty for " + scoreElementXMLInner);
							errored.add("Skipped score element--ScoreValue was empty for " + scoreElementXMLInner);
							continue;
						}
						
						if (taskVariantIds == null) {							
							if(externalTaskIdMap.containsKey(taskVariantExternalId)) {
								taskVariantIds =(List<Long>) ((HashMap)externalTaskIdMap.get(taskVariantExternalId)).get("value");
							}
						}
						
						if (processedTVId.longValue() != taskVariantExternalId.longValue()) {
							taskVariantIds = null;
							
							if(externalTaskIdMap.containsKey(taskVariantExternalId)) {
								taskVariantIds = (List<Long>) ((HashMap) externalTaskIdMap.get(taskVariantExternalId)).get("value");
								processedTVId = taskVariantExternalId;
							}
						}
						
						// check if the response is valid for them to score
						//StudentsResponses sr = studentsResponsesService.selectQuestarResponse(studentId, studentsTestsId, studentsTestSectionsId, taskVariantId);
							
						if (isStudentResponseFound(taskVariantExternalId, studentId, studentsTestsId, studentsTestSectionsId, 
								stuResponsesOfTestSection, taskVariantIds)) {
							
							String dimension = (String) extendedElements.get("Dimension" + (z + 1));
							if (dimension == null) {
								LOGGER.warn("Dimension" + (z + 1) + " is null...moving to next <Score> element");
								continue;
							}
							
							StudentResponseScore record = new StudentResponseScore();
							record.setStudentsTestSectionsId(studentsTestSectionsId);
							record.setTaskVariantExternalId(taskVariantExternalId);
							record.setScore(score);
							record.setDimension(dimension);
							record.setDiagnosticStatement(diagnosticStatement);
							record.setRaterId(raterId);
							record.setRaterName(raterName);
							record.setRaterOrder(raterOrder);
							record.setRaterExposure(raterExposure);
							record.setCreateDate(now);
							record.setModifiedDate(now);
							record.setActiveFlag(true);
							record.setScorable(score != null);
							record.setNonScorableCodeId(nonScorableCodeId);
							
							try {
								studentsResponsesService.addOrUpdateStudentResponseScore(record);
								importedCount++;
							} catch (Exception e) {
								LOGGER.error("Error updating database:", e);
								errored.add("Error updating database for node " + scoreElementXML);
							}
						} else {
							LOGGER.info("No response found for (" +
									sssRefId + " as studentsTestsId, " +
									sssAssessmentAdministrationRefId + " as studentsTestSectionsId, " +
									sAssessmentSubTestRefId + " as taskVariantId, " +
									sssStudentPersonalRefId + " as studentId)" +
									")...moving to next <Score> element");
							errored.add("Skipped score element--Could not find response for " + scoreElementXMLInner);
						}
					}
				}
			}
		} catch (SAXException saxe) {
			LOGGER.error("Caught SAXException in questar/sendScores when trying to parse xml: " + xml, saxe);
			ret = "There was a problem parsing the XML.";
		} catch (IOException ioe) {
			LOGGER.error("Caught IOException in questar/sendScores", ioe);
			ret = "I/O Error";
		} catch (Exception e) {
			LOGGER.error("Caught Exception in questar/sendScores", e);
			ret = "Unknown Error";
		}
		
		// format output
		if (ret.equals("")) {
			ret = "Imported: " + importedCount + "\n\n";
			
			int errors = errored.size();
			if (errors > 0) {
				ret += "Encountered " + errors + " error";
				if (errors > 1) {
					ret += "s";
				}
				ret += ":\n\n";
				for (int x = 0; x < errors; x++) {
					ret += errored.get(x);
					if (x < (errors - 1)) { // more to come
						ret += "\n\n";
					}
				}
			} else {
				ret += "Encountered no errors.";
			}
		}
		
		LOGGER.trace("<-- questar/sendScores");
		return ret;
	}
	
	private boolean isStudentResponseFound(Long externalTaskVariantId, Long studentId, Long studentTestId, 
			Long studentTestSectionId, List<StudentsResponses> studentResponses, List<Long> taskVariantIds) {
		
		for (StudentsResponses stuResponse : studentResponses) {

			if(CollectionUtils.isNotEmpty(taskVariantIds) 
					&& taskVariantIds.contains(stuResponse.getTaskVariantId())
						&& stuResponse.getStudentId().longValue() == studentId.longValue() 
							&& stuResponse.getStudentsTestsId().longValue() == studentTestId.longValue()) {
					return true;
			}
		}
		
		return false;
	}
	
	private Map<String, Object> parseExtendedElementsMap(Element extendedElementsNode) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		NodeList extendedElements = extendedElementsNode.getElementsByTagName("SIF_ExtendedElement");
		for (int x = 0; x < extendedElements.getLength(); x++) {
			Element e = (Element) extendedElements.item(x);
			String content = e.getTextContent() == null ? null : e.getTextContent().trim();
			map.put(e.getAttribute("Name"), content);
		}
		return map;
	}
	
	@RequestMapping(value = "ampImport.htm")
	public @ResponseBody String ampImport() throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
		questarPaperPencilFileProcessor.processFiles();
		return "Process started. Check the database for info.";
	}
	
	@RequestMapping(value = "getAMPOperationalTestWindows.htm")
	public @ResponseBody List<OperationalTestWindow> getAMPOperationalTestWindows() {
		AssessmentProgram amp = apService.findByAbbreviatedName("AMP");
		List<OperationalTestWindow> windows = operationalTestWindowService.selectByAssessmentProgramAndHighStakes(amp.getId(), true);
		return windows;
	}
	
	@RequestMapping(value = "ampProcess.htm")
	public @ResponseBody String ampProcess(Long operationalTestWindowId) throws Exception {
		batchRegStarter.startQuestarJob(operationalTestWindowId);
		return "Job started.";
	}
}