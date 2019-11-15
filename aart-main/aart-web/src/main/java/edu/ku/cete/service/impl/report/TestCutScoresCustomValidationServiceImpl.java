package edu.ku.cete.service.impl.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.report.TestCutScoresService;
 

@SuppressWarnings("unused")
@Service
public class TestCutScoresCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private TestCutScoresService testCutScoresService;
	
	@Autowired
	private TestTypeService testTypeService;
	
	final static Log logger = LogFactory.getLog(TestCutScoresCustomValidationServiceImpl.class);

	@Value("${testingProgramName.Interim}")
	private String interimTestingProgram;
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for Test Cut Scores Batch Upload");
		TestCutScores testCutScore = (TestCutScores) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long testingProgramIdOnUI = (Long)params.get("testingProgramIdOnUI");
		String testingProgramName = (String)params.get("testingProgramNameOnUI");
		//reportCycle value is coming from batchupload table which was inserted from upload file, not a UI filter at this time
		String reportCycle = (String)params.get("reportCycleOnUI");  
		Long subjectId = (Long)params.get("subjectIdOnUI");
		Long createdUserId = (Long)params.get("createdUser");
		String lineNumber = testCutScore.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;

		if(!testCutScore.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errorMsg = "Assessment Program is invalid.";
			logger.debug(errorMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errorMsg);
			validationPassed = false;
		}else{
			//validate testingprogram
			if(!testCutScore.getTestingProgramName().equalsIgnoreCase(testingProgramName)){
				String errMsg = "Testing Program is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("testingProgramName", "", new String[]{lineNumber, mappedFieldNames.get("testingProgramName")}, errMsg);
				validationPassed = false;
			}else{
				//vaidate reportcycle only if the selected testingprogram is Interim
				if((interimTestingProgram.equalsIgnoreCase(testingProgramName) || "CPASS".equalsIgnoreCase(assessmentProgramCodeOnUI)) 
						&& !testCutScore.getReportCycle().equalsIgnoreCase(reportCycle)){
					String errMsg = "Report Cycle is invalid. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("reportCycle", "", new String[]{lineNumber, mappedFieldNames.get("reportCycle")}, errMsg);
					validationPassed = false;
				}
				if(validationPassed){
					//validate subject
					if(!testCutScore.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
						logger.debug("Subject is invalid.");
						validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, "Subject is invalid.");
						validationPassed = false;
					}else{
						//validate grade
						gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, testCutScore.getGrade());
						if(gradeCourse == null){
							logger.debug("grade is invalid for subject specified.");
							validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, "grade is invalid for subject specified.");
							validationPassed = false;
						}else{
							if("CPASS".equalsIgnoreCase(assessmentProgramCodeOnUI)){//Added for CPASS Results upload
			            	    //Validate Test_Type  
								List<TestType> testType = testTypeService.getCPASSTestTypesForReportsByTestTypeCode(testingProgramIdOnUI, testCutScore.getTestType());
								if(testType.size() > 0){
									if(!testCutScore.getSubject().equalsIgnoreCase(testType.get(0).getSubjectCode())
											|| !testCutScore.getGrade().equalsIgnoreCase(testType.get(0).getGradeCode())){
										String errMsg = "Comination of Test_Type, Subject and grade is Invalid.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testType", "", new String[]{lineNumber, mappedFieldNames.get("testType")}, errMsg);
										validationPassed = false;
									}
								}else{
									String errMsg = "Test_Type is Invalid.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testType", "", new String[]{lineNumber, mappedFieldNames.get("testType")}, errMsg);
									validationPassed = false;
								}
							}

						}
						
					}
				}
			}
		}
		
		// Domain is specific to KELPA2, at least for 2017
		if (testCutScore.getAssessmentProgram().equalsIgnoreCase("KELPA2") && StringUtils.isEmpty(testCutScore.getDomain())) {
			logger.debug("TestCutScore domain not given");
			validationErrors.rejectValue("domain", "", new String[]{lineNumber, mappedFieldNames.get("domain")}, "Domain not specified");
			validationPassed = false;
		}
		
		//high score must be greater than low score
		if(testCutScore.getLevelHighCutScore().longValue() < testCutScore.getLevelLowCutScore().longValue()){
			logger.debug("High Cut Score must be greater than Low Cut Score.");
			validationErrors.rejectValue("levelHighCutScore", "", new String[]{lineNumber, mappedFieldNames.get("levelHighCutScore")}, "High Cut Score(" + testCutScore.getLevelHighCutScore() +") must be greater than Low Cut Score(" + testCutScore.getLevelLowCutScore() + ").");
			validationPassed = false;
		}
		//check duplicate level number
		//**** This condition was used to check the level number against the testid1 and testid2. But we removed testids as per US17646
		TestCutScores testCutScoresRecord = testCutScoresService.checkDuplicateLevels(testCutScore.getLevel(),  assessmentProgramId, subjectId, testCutScore.getSchoolYear(), testCutScore.getDomain(), testingProgramIdOnUI, reportCycle);
		if(testCutScoresRecord != null){
			logger.debug("Duplicate level number for same assessment program ("+assessmentProgramCodeOnUI+") and subject ("+subjectCodeOnUI+")");
			validationErrors.rejectValue("level", "", new String[]{lineNumber, mappedFieldNames.get("level")}, "Duplicate level number ("+testCutScore.getLevel()+") for assessment program ("+assessmentProgramCodeOnUI+") and subject ("+subjectCodeOnUI+").");
			validationPassed = false;
		}
		
		if(validationPassed){
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			testCutScore.setAssessmentProgramId(assessmentProgramId); 
			testCutScore.setSubjectId(subjectId);
			testCutScore.setGradeId(gradeCourse.getId());
			testCutScore.setTestingProgramId(testingProgramIdOnUI);
		}
		
		testCutScore.setBatchUploadId(batchUploadId);
		testCutScore.setCreateduser(createdUserId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", testCutScore);
		logger.debug("Completed validation completed.");
		return customValidationResults;
 		
	}
	

}
