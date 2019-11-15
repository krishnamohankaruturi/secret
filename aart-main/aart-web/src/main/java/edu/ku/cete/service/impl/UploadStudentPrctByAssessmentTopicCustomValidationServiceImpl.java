package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.student.Student;
import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.report.domain.StudentPrctByAssessmentTopic;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.report.AssessmentTopicService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.util.CommonConstants;

@Service
public class UploadStudentPrctByAssessmentTopicCustomValidationServiceImpl
		implements BatchUploadCustomValidationService {
	final static Log logger = LogFactory
			.getLog(UploadStudentPrctByAssessmentTopicCustomValidationServiceImpl.class);
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private AssessmentTopicService assessmentTopicService;

	@Autowired
	private AppConfigurationService appConfigurationService;
	
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		
		Long batchUploadId = (Long) params.get("batchUploadId");	
		Long stateIdOnUI = (Long) params.get("stateId");	
		
		StudentPrctByAssessmentTopic studentPrctByAssessmentTopic = (StudentPrctByAssessmentTopic) rowData;
		
		String lineNumber = studentPrctByAssessmentTopic.getLineNumber();
		studentPrctByAssessmentTopic.setUploadLevel("student");
		
		//Validate common CPASS columns
		batchUploadService.externalResultsUploadCommonValidation(studentPrctByAssessmentTopic, validationErrors, params, mappedFieldNames);
		
		if(validationErrors.getAllErrors().size() == 0){			  
			//Validate student exist or not
			List<Student> students = studentService.getBySsidAndState(studentPrctByAssessmentTopic.getStateStudentIdentifier(), stateIdOnUI);
			
			
			if(!studentPrctByAssessmentTopic.getStateStudentIdentifier().trim().isEmpty() ){
	    		String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
	    		int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
	    		if(studentPrctByAssessmentTopic.getStateStudentIdentifier().trim().length()>allowedLength){
	    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
	    			String errMsg = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");	    			
	    			validationErrors.rejectValue("stateStudentIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("stateStudentIdentifier")}, errMsg);
	    		}
			}
			
			if (students == null || students.size() == 0){
				String errMsg = "State_Student_Identidier does not exist in "+studentPrctByAssessmentTopic.getState()+".";
				logger.debug(errMsg);
				validationErrors.rejectValue("stateStudentIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("stateStudentIdentifier")}, errMsg);
			}else{
				//Validate Topic code
				List<AssessmentTopic> assessmentTopics = assessmentTopicService.getAssessmentTopic(studentPrctByAssessmentTopic.getSchoolYear(), studentPrctByAssessmentTopic.getTestType(),
						                                                                            studentPrctByAssessmentTopic.getTopicCode());
				if(assessmentTopics.size() == 0){
					String errMsg = "Topic_Code is invalid.";
					logger.debug(errMsg);
					validationErrors.rejectValue("topicCode", "", new String[]{lineNumber, mappedFieldNames.get("topicCode")}, errMsg);
				}else{
					studentPrctByAssessmentTopic.setStudentId(students.get(0).getId());
					studentPrctByAssessmentTopic.setAuditColumnProperties();
					studentPrctByAssessmentTopic.setBatchUploadedId(batchUploadId);
					studentPrctByAssessmentTopic.setTopicId((long) assessmentTopics.get(0).getId());
					studentPrctByAssessmentTopic.setActiveFlag(true);
				}
			}
		}
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", studentPrctByAssessmentTopic);
		logger.debug("Completed validation completed.");
		return customValidationResults;
	}

}
