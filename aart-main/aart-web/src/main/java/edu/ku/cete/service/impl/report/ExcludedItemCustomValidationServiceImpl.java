package edu.ku.cete.service.impl.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
 

@SuppressWarnings("unused")
@Service
public class ExcludedItemCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestService testService;
	
	final static Log logger = LogFactory.getLog(ExcludedItemCustomValidationServiceImpl.class);

	@Value("${testingProgramName.Summative}")
	private String summativeTestingProgram;
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for Excluded Items Batch Upload");
		ExcludedItems excludedItems = (ExcludedItems) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long testingProgramId = (Long)params.get("testingProgramIdOnUI");
		String testingProgramName = (String)params.get("testingProgramNameOnUI");
		String reportCycle = (String)params.get("reportCycleOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber = excludedItems.getLineNumber();
		GradeCourse gradeCourse = null;
		if(!excludedItems.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
		}else{
			//validate subject
			if(!excludedItems.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
				String errMsg = "Subject is invalid.";
				logger.debug(errMsg);
				validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
			}else{
				//validate grade
				gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, excludedItems.getGrade());
				if(gradeCourse == null){
					String errMsg = "grade is invalid for subject specified.";
					logger.debug(errMsg);
					validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
				}
				else{
					//validate task variant
					List<TaskVariant> validTaskVariantOnRecord = testService.getTaskVariantByExternalIdGradeIdContentId(
							subjectId,
							gradeCourse.getAbbreviatedName(),
							assessmentProgramId,
							excludedItems.getTaskVariantId(), testingProgramName);
					if(CollectionUtils.isEmpty(validTaskVariantOnRecord)){
						String errorMsg = "Task Variant Id is invalid for assessment program, testing program, grade and subject specified.";
						logger.debug(errorMsg);
						validationErrors.rejectValue("taskVariantId", "", new String[]{lineNumber, mappedFieldNames.get("taskVariantId")}, errorMsg);
					} else {
						
					
						//validate testingprogram
						if(!excludedItems.getTestingProgramName().equalsIgnoreCase(testingProgramName)){//testingprogram
							String errMsg = "Testing Program is invalid. ";
							logger.debug(errMsg);
							validationErrors.rejectValue("testingProgramName", "", new String[]{lineNumber, mappedFieldNames.get("testingProgramName")}, errMsg);
							
						} else {								
							//validate reportcycle only if the selected testingprogram is Interim
							if(reportCycle != null) {
								if(!excludedItems.getReportCycle().equalsIgnoreCase(reportCycle)){
									String errMsg = "Report Cycle is invalid. ";
									logger.debug(errMsg);
									validationErrors.rejectValue("reportCycle", "", new String[]{lineNumber, mappedFieldNames.get("reportCycle")}, errMsg);									
								} else {
									excludedItems.setReportCycle(reportCycle);
								}
							} else{
								logger.debug("Custom Validation passed. Setting Params to domain object.");
								excludedItems.setAssessmentProgramId(assessmentProgramId); 
								excludedItems.setSubjectId(subjectId);
								excludedItems.setGradeId(gradeCourse.getId());
							}
							excludedItems.setTestingProgramId(testingProgramId);
							excludedItems.setAssessmentProgramId(assessmentProgramId); 
							excludedItems.setSubjectId(subjectId);
							excludedItems.setGradeId(gradeCourse.getId());
							excludedItems.setBatchUploadId(batchUploadId);
							excludedItems.setCreatedUser((Long)params.get("createdUser"));							
						}
					}							
							
				}
			}
		}
		
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", excludedItems);
		logger.debug("Completed validation completed.");
		return customValidationResults;
 		
	}
	

}
