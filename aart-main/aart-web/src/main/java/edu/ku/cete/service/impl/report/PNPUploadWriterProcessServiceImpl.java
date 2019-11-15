package edu.ku.cete.service.impl.report;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.PNPUploadRecord;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentJson;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.student.StudentProfileItemAttributeValueDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;

@Service
public class PNPUploadWriterProcessServiceImpl implements BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(PNPUploadWriterProcessServiceImpl.class);
	
	@Autowired
	private StudentProfileService studentProfileService;
	
	@Autowired
	private StudentProfileItemAttributeValueDao studentProfileItemAttributeValueDao;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AssessmentProgramService apService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		for (Object object : objects) {
			PNPUploadRecord pnpRecord = (PNPUploadRecord) object;
			write(pnpRecord);
		}
	}
	
	private void write(PNPUploadRecord pnpRecord) {
		Long studentId = pnpRecord.getStudent().getId();
		Long modifiedUser = pnpRecord.getModifiedUser();
		
		StudentJson studentJson = studentDao.getStudentjsonData(studentId);
		String studentBeforeJson = studentJson.buildjsonString();
		
		// these will be used for checking to unenroll from tests, String -> String, being the pianacId -> selectedValue
		Map<String, String> beforeSelectedValues = studentProfileService.getSelectedValuesForStudent(studentId);
		
		String beforeValueJson = studentProfileItemAttributeValueDao.getStudentValueJson(studentId);
		logger.debug("beforeValueJson = " + beforeValueJson);
		
		Map<String, FieldSpecification> fieldSpecs = pnpRecord.getFieldSpecs();
		ObjectMapper objectMapper = new ObjectMapper();
		logger.debug("writing PNP upload data for student id " + pnpRecord.getStudent().getId());
		FieldSpecification fieldSpec = null;
		for (Entry<String, FieldSpecification> entry : fieldSpecs.entrySet()) {
			fieldSpec = entry.getValue();
			String fieldName = fieldSpec.getFieldName();
			String fieldValueFromPOJO = null;
			boolean exceptionOccurred = false;
			try {
				fieldValueFromPOJO = (String) PropertyUtils.getProperty(pnpRecord, fieldName);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
				logger.error("Exception occurred while attempting to get property of pnpRecord: ", e);
				exceptionOccurred = true;
			}
			
			if (!exceptionOccurred && StringUtils.isNotEmpty(fieldSpec.getJsonData())) {
				try {
					JsonNode root = objectMapper.readTree(fieldSpec.getJsonData());
					JsonNode actions = root.get("actions");
					
					boolean isFileValuePopulated = StringUtils.isNotEmpty(fieldValueFromPOJO);
					JsonNode actionByValue = null;
					if (isFileValuePopulated) {
						// default to if we can find the specified value
						actionByValue = actions.get(fieldValueFromPOJO.toLowerCase());
						if (actionByValue == null) {
							// special case for things like numeric fields where possible values are pretty much endless,
							// so we couldn't list them all
							actionByValue = actions.get("VALIDVALUE");
						}
					} else {
						actionByValue = actions.get("EMPTY");
					}
					
					if (actionByValue != null) {
						JsonNode set = actionByValue.get("set");
						if (set.isArray()) {
							for (final JsonNode elementToSet : set) {
								String containerName = elementToSet.get("containerName").asText();
								String attributeName = elementToSet.get("attributeName").asText();
								String attributeValue = elementToSet.get("value").asText();
								if ("VALUE".equals(attributeValue)) {
									attributeValue = fieldValueFromPOJO;
								}
								
								studentProfileService.updateStudentPNPOption(studentId, containerName, attributeName, attributeValue, modifiedUser);
							}
						}
					} else {
						logger.error("no action value specified in " + fieldName + " for supplied upload value \"" + fieldValueFromPOJO + "\"");
					}
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					logger.error("JsonProcessingException occurred while attempting to save PNP option: ", e);
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("IOException occurred while attempting to save PNP option (this could be from objectMapper.readTree): ", e);
				}
			}
		}
		
		List<AssessmentProgram> aps = studentService.getStudentAssessmentProgram(studentId);
		List<Long> apIds = new ArrayList<Long>(aps.size());
		for (AssessmentProgram ap : aps) {
			apIds.add(ap.getId());
		}
		try {
			studentProfileService.removeNonAssociatedPNPSettings(studentId, apIds, pnpRecord.getModifiedUser(), pnpRecord.getStudent().getStateId());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			logger.error("JsonProcessingException occurred while attempting to remove options based on assessment program: ", e);
		}
		
		studentProfileService.removeActivateByDefaultsFromContainersWithoutSupport(studentId, modifiedUser);
		
		// update student's status
		String pnpStatus = studentProfileService.determinePNPStatus(studentId);
		studentDao.updateProfileStatusByStudentId(studentId, pnpStatus, new Date(), modifiedUser);
		
		try {
			// insert into the studentpnpjson table
			studentProfileService.insertOrUpdateStudentAttributes(studentId);
			
			// insert audit data
			String afterValueJson = studentProfileItemAttributeValueDao.getStudentValueJson(studentId);
			studentProfileService.insertPNPAuditHistory(studentId, beforeValueJson, afterValueJson);
		} catch (JsonProcessingException e) {
			logger.error("JsonProcessingException occurred while attempting to get property of pnpRecord:", e);
		}
		
		// F855-856, race/ethnicity modification is only for PLTW
		AssessmentProgram ap = apService.findByAssessmentProgramId(pnpRecord.getAssessmentProgramId());
		if (CommonConstants.ASSESSMENT_PROGRAM_PLTW.equals(ap.getAbbreviatedname())) {
			Student student = studentService.findById(studentId);
			student.setComprehensiveRace(StringUtils.isEmpty(pnpRecord.getComprehensiveRace()) ? null : pnpRecord.getComprehensiveRace());
			
			String uploadedHispanicEthnicity = pnpRecord.getHispanicEthnicity();
			//code added for f933 feature 
			if(uploadedHispanicEthnicity.equalsIgnoreCase("Yes")) {	
				student.setHispanicEthnicity("true");
			}
			else if(uploadedHispanicEthnicity.equalsIgnoreCase("No")) {
				student.setHispanicEthnicity("false");
			}
			else  {
				student.setHispanicEthnicity(uploadedHispanicEthnicity);
			}
			studentDao.updateIgnoreFinalBands(student);
		}
		
		if (studentProfileService.checkToUnenrollTestSessionsBasedOnPNP(ap.getAbbreviatedname(), studentId, beforeSelectedValues)) {
			User user = userService.get(modifiedUser);
			studentProfileService.pnpUnenrollTests(studentId, ap.getAbbreviatedname(), user);
		}
		
		studentJson = studentDao.getStudentjsonData(studentId);
		String studentAfterJson = studentJson.buildjsonString();
		
		studentService.insertIntoDomainAuditHistory(studentId, modifiedUser, EventTypeEnum.UPDATE.getCode(), SourceTypeEnum.UPLOAD.getCode(), studentBeforeJson, studentAfterJson);
	}
}
