package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestTypeService;

@Service
public class UploadAssessmentTopicCustomValidationServiceImpl implements
		BatchUploadCustomValidationService {
	final static Log logger = LogFactory
			.getLog(UploadAssessmentTopicCustomValidationServiceImpl.class);

	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long testingProgramIdOnUI = (Long)params.get("testingProgramIdOnUI");
		
		AssessmentTopic assessmentTopic = (AssessmentTopic) rowData;
		
		Long subjectId = (Long)params.get("subjectIdOnUI");
		Long createdUserId = (Long)params.get("createdUser");
		String lineNumber = assessmentTopic.getLineNumber();
		
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		
		//Validate Subject
		ContentArea subject = contentAreaService.selectByPrimaryKey(subjectId);
		if(subject == null || !subject.getAbbreviatedName().equalsIgnoreCase(assessmentTopic.getSubject())){
			String errMsg = "Subject is not matching with upload page Subject selection. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
		}else{
			assessmentTopic.setContentareaId(subject.getId());
			//Validate Grade
			GradeCourse gc = gradeCourseService.selectGradeByAbbreviatedNameAndContentAreaId(assessmentTopic.getGrade(), subject.getId());
			if (gc == null){
				String errMsg = "Grade is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
			}else{
				assessmentTopic.setGradeId(gc.getId());
				//Validate TestType
				List<TestType> testType = testTypeService.getCPASSTestTypesForReportsByTestTypeCode(testingProgramIdOnUI, assessmentTopic.getTestType());
				if(testType == null || testType.size() == 0){
					String errMsg = "TestType is invalid. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("testType", "", new String[]{lineNumber, mappedFieldNames.get("testType")}, errMsg);
				}else{
					if(!assessmentTopic.getSubject().equalsIgnoreCase(testType.get(0).getSubjectCode())
							|| !assessmentTopic.getGrade().equalsIgnoreCase(testType.get(0).getGradeCode())){
						String errMsg = "Combination of Test_Type, Subject and grade is Invalid.";
						logger.debug(errMsg);
						validationErrors.rejectValue("testType", "", new String[]{lineNumber, mappedFieldNames.get("testType")}, errMsg);
			        }else{
			        	assessmentTopic.setAssessmentName(testType.get(0).getTestTypeName());
			        	assessmentTopic.setBatchUploadId(batchUploadId);
			        	assessmentTopic.setCreatedUser(createdUserId);
			        	assessmentTopic.setModifiedUser(createdUserId);
			        	assessmentTopic.setActiveFlag(true);
			        }								
			   }
		   }
	   }		
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", assessmentTopic);
		logger.debug("Completed validation completed.");
		return customValidationResults;
	}

}
