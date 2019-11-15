/**
 * 
 */
package edu.ku.cete.service.impl.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 1, 2017 10:21:52 AM
 */
@Service
public class QuestionInformationCustomValidationServiceImpl implements BatchUploadCustomValidationService {

	final static Log logger = LogFactory.getLog(QuestionInformationCustomValidationServiceImpl.class);
		
	@Autowired
	private GradeCourseService gradeCourseService;	
	
	@Autowired
	private TestService testService;	
	
	@Value("${testingProgramName.Interim}")
	private String interimTestingProgram;
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for QuestionInformation Batch Upload");
		QuestionInformation questionInformation = (QuestionInformation) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long testingProgramIdOnUI = (Long)params.get("testingProgramIdOnUI");
		String testingProgramName = (String)params.get("testingProgramNameOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		//reportCycle value is coming from batchupload table which was inserted from upload file, not a UI filter at this time
		String reportCycle = (String)params.get("reportCycleOnUI");
		Long createdUserId = (Long)params.get("createdUser");
		String lineNumber = questionInformation.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		
		if(!questionInformation.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		}else{
			//validate testingprogram
			if(!questionInformation.getTestingProgramName().equalsIgnoreCase(testingProgramName)){
				String errMsg = "Testing Program is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("testingProgramName", "", new String[]{lineNumber, mappedFieldNames.get("testingProgramName")}, errMsg);
				validationPassed = false;
			}else{
				//validate reportcycle only if the selected testingprogram is Interim
				if(interimTestingProgram.equalsIgnoreCase(testingProgramName) && !questionInformation.getReportCycle().equalsIgnoreCase(reportCycle)){
					String errMsg = "Report Cycle is invalid. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("reportCycle", "", new String[]{lineNumber, mappedFieldNames.get("reportCycle")}, errMsg);
					validationPassed = false;
				}
				if(validationPassed){

					if(!questionInformation.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
						String errMsg = "Subject is invalid.";
						logger.debug(errMsg);
						validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, "Subject is invalid.");
						validationPassed = false;
					}else{
						//validate grade
						gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, questionInformation.getGrade());
						if(gradeCourse == null){
							String errMsg = "Grade is invalid for subject specified.";
							logger.debug(errMsg);
							validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
							validationPassed = false;
						}else{
							//validate testid
							if(questionInformation.getExternalTestId() != null){
								List<Test> validTestOnRecord = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, questionInformation.getExternalTestId(), questionInformation.getTestingProgramName());
								if(CollectionUtils.isEmpty(validTestOnRecord)){
									String errMsg = "TestId is invalid for assessment program, testing program, grade and subject specified.";
									logger.debug(errMsg);
									validationErrors.rejectValue("externalTestId", "", new String[]{lineNumber, mappedFieldNames.get("externalTestId")}, errMsg);
									validationPassed = false;
								}
								
							}
							else{
								String errMsg = "TestId is not specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("externalTestId", "", new String[]{lineNumber, mappedFieldNames.get("externalTestId")}, errMsg);
								validationPassed = false;
							}
							//validate taskvariantid
							if(validationPassed){
								if(questionInformation.getTaskVariantExternalId() != null){
									List<TaskVariant> validTaskVariantOnRecord = testService.getTaskVariantByExternalIdGradeIdContentId(
											subjectId,
											gradeCourse.getAbbreviatedName(),
											assessmentProgramId,
											questionInformation.getTaskVariantExternalId(), interimTestingProgram);
									if(CollectionUtils.isEmpty(validTaskVariantOnRecord)){
										String errorMsg = "Task Variant Id is invalid for assessment program, testing program, grade and subject specified.";
										logger.debug(errorMsg);
										validationErrors.rejectValue("taskVariantExternalId", "", new String[]{lineNumber, mappedFieldNames.get("taskVariantExternalId")}, errorMsg);
										validationPassed = false;
									}
								}else{
									String errorMsg = "Task Variant Id is not specified.";
									logger.debug(errorMsg);
									validationErrors.rejectValue("taskVariantExternalId", "", new String[]{lineNumber, mappedFieldNames.get("taskVariantExternalId")}, errorMsg);
									validationPassed = false;
								}
							}
							//validate question description
							if(validationPassed){
								if(StringUtils.isBlank(questionInformation.getQuestionDescription())){
									String errorMsg = "Question description is not specified.";
									logger.debug(errorMsg);
									validationErrors.rejectValue("questionDescription", "", new String[]{lineNumber, mappedFieldNames.get("questionDescription")}, errorMsg);
									validationPassed = false;
								}
							}
							
						}
					}				
				}
			}	
		
		}
		
		if(validationPassed){
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			questionInformation.setAssessmentProgramId(assessmentProgramId); 
			questionInformation.setContentAreaId(subjectId);
			questionInformation.setGradeId(gradeCourse.getId());
			questionInformation.setTestingProgramId(testingProgramIdOnUI);
			questionInformation.setActiveFlag(Boolean.TRUE);
			questionInformation.setCreatedUser(createdUserId);
			questionInformation.setModifiedUser(createdUserId);
		}
		
		questionInformation.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", questionInformation);
		logger.debug("Completed validation completed.");
		
		return customValidationResults;
		
	}
}
	
