package edu.ku.cete.service.impl.report;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;
 

@SuppressWarnings("unused")
@Service
public class SubscoreRawToScaleScoresCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private SubscoreRawToScaleScoresService subscoreRawToScaleScoresService;
	
	@Autowired
	private TestService testService;
	
	final static Log logger = LogFactory.getLog(SubscoreRawToScaleScoresCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params,Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for SubscoreRawToScaleScores Batch Upload");
		SubscoreRawToScaleScores subscoreRawToScaleScores = (SubscoreRawToScaleScores) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		String testingProgramName = (String)params.get("testingProgramNameOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber =subscoreRawToScaleScores.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		if(!subscoreRawToScaleScores.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg="Assessment Program is invalid.";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		}else{
			//validate subject
			if(!subscoreRawToScaleScores.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
				String errMsg="Subject is invalid.";
				logger.debug(errMsg);
				validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
				validationPassed = false;
			}else{
				//validate grade
				gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, subscoreRawToScaleScores.getGrade());
				if(gradeCourse == null){
					String errMsg="grade is invalid for subject specified.";
					logger.debug(errMsg);
					validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
					validationPassed = false;
				}
				else{
					//validate testid1: Must be a valid testID in assessment program, grade and subject
					List<Test> validTestOnRecord = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId1(), testingProgramName);
					if(CollectionUtils.isEmpty(validTestOnRecord)){
						String errMsg = "TestId1 is invalid for assessment program, grade and subject specified.";
						logger.debug(errMsg);
						validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
						validationPassed = false;
					}
					//validate testid1: Cannot be a test in test collection associated with stage = Performance
					if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId1()))
					{
						String errMsg = "TestId1 belongs to Test collection associated with Performance stage tests.";
						logger.debug(errMsg);
						validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
						validationPassed = false;
					}
					//TestID2 not required
					if(subscoreRawToScaleScores.getTestId2() != null){
						if(subscoreRawToScaleScores.getTestId2().equals(subscoreRawToScaleScores.getTestId1()))
						{
							//Cannot be the same testID as other testIDs.
							String errMsg = "Duplicate TestId2.";
							logger.debug(errMsg);
							validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
							validationPassed = false;
						}
						else
						{
							//validate testid2: Must be a valid testID in assessment program, grade and subject
							List<Test> validTestOnRecordForTest2 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId2(), testingProgramName);
							if(CollectionUtils.isEmpty(validTestOnRecordForTest2)){
								String errMsg = "TestId2 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
								validationPassed = false;
							}else{
								//validate testid2: Cannot be a test in teest collection associated with stage = Performance
								if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId2()))
								{
									String errMsg = "TestId2 belongs to Test collection associated with Performance stage tests.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
									validationPassed = false;
								}
							}
						}
					}
					//TestID3 not required
					if(subscoreRawToScaleScores.getTestId3() != null){
						if(subscoreRawToScaleScores.getTestId3().equals(subscoreRawToScaleScores.getTestId1()) || subscoreRawToScaleScores.getTestId3().equals(subscoreRawToScaleScores.getTestId2()))
						{
							//Cannot be the same testID as other testIDs.
							String errMsg = "Duplicate TestId3.";
							logger.debug(errMsg);
							validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
							validationPassed = false;
						}
						else
						{
							//validate testid3: Must be a valid testID in assessment program, grade and subject
							List<Test> validTestOnRecordForTest3 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId3(), testingProgramName);
							if(CollectionUtils.isEmpty(validTestOnRecordForTest3)){
								String errMsg = "TestId3 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
								validationPassed = false;
							}else{
								//validate testid3: Cannot be a test in teest collection associated with stage = Performance
								if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId3()))
								{
									String errMsg = "TestId3 belongs to Test collection associated with Performance stage tests.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
									validationPassed = false;
								}
							}
						}
					}
					//TestID4 not required
					if(subscoreRawToScaleScores.getTestId4() != null){
						if(subscoreRawToScaleScores.getTestId4().equals(subscoreRawToScaleScores.getTestId1()) || subscoreRawToScaleScores.getTestId4().equals(subscoreRawToScaleScores.getTestId2())
								|| subscoreRawToScaleScores.getTestId4().equals(subscoreRawToScaleScores.getTestId3()))
						{
							//Cannot be the same testID as other testIDs.
							String errMsg = "Duplicate TestId4.";
							logger.debug(errMsg);
							validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
							validationPassed = false;
						}
						else
						{
							//validate testid3: Must be a valid testID in assessment program, grade and subject
							List<Test> validTestOnRecordForTest4 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getTestId4(), testingProgramName);
							if(CollectionUtils.isEmpty(validTestOnRecordForTest4)){
								String errMsg = "TestId4 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
								validationPassed = false;
							}else{
								//validate testid4: Cannot be a test in test collection associated with stage = Performance
								if(testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getTestId1()))
								{
									String errMsg = "TestId4 belongs to Test collection associated with Performance stage tests.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
									validationPassed = false;
								}
							}
						}
					}
					
					//If performanceTestId is provided, then the following must be checked
					if(subscoreRawToScaleScores.getPerformanceTestId() != null){
						
						//check if performance TestID is valid
						List<Test> validTestOnRecordForPerformanceTest = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(null, 
									gradeCourse.getAbbreviatedName(), assessmentProgramId, subscoreRawToScaleScores.getPerformanceTestId(), testingProgramName);
						if(CollectionUtils.isEmpty(validTestOnRecordForPerformanceTest)){
							String errMsg = "Performance TestID is invalid for assessment program, grade and performance subject specified.";
							logger.debug(errMsg);
							validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
							validationPassed = false;
							
						}
						else
						{
							//validate PerformanceTestId: Must be a test in test collection associated with stage = Performance
							if(!testService.isTestIdFromTestCollectionsWithPerformanceTests(subscoreRawToScaleScores.getPerformanceTestId()))
							{
								String errMsg = "Performance TestID must belong to Test collection associated with Performance stage tests.";
								logger.debug(errMsg);
								validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
								validationPassed = false;
							}
						}
					}
					
					//check if subscore definition name already exists -- Created in the Upload Score Framework definitions
					boolean subscoreDefinitionNameExists = subscoreRawToScaleScoresService.checkIfSubscoreDefinitionNameExists(subscoreRawToScaleScores.getSubScoreDefinitionName(), gradeCourse.getId(), subjectId, assessmentProgramId);
					if(!subscoreDefinitionNameExists){
						String errMsg="Subscore definition name ["+subscoreRawToScaleScores.getSubScoreDefinitionName()+"] does not exist for Assessment Program, Subject, Grade";
						logger.debug(errMsg);
						validationErrors.rejectValue("subScoreDefinitionName", "", new String[]{lineNumber, mappedFieldNames.get("subScoreDefinitionName")}, errMsg);
						validationPassed = false;
					}
					
					if(subscoreRawToScaleScores.getMinimumPercentResponses()!=null && subscoreRawToScaleScores.getMinimumPercentResponses().compareTo(new BigDecimal("0")) != 0 )
					{
						int tempLength = subscoreRawToScaleScores.getMinimumPercentResponses().stripTrailingZeros().toPlainString().length();
						int index = subscoreRawToScaleScores.getMinimumPercentResponses().stripTrailingZeros().toPlainString().indexOf(".");
						int numOfDecimals = 0;
						if(index>0)
							numOfDecimals = tempLength - index - 1;
					    
					    if(numOfDecimals > 2)
					    {
					    	String errMsg = "Minimum percent responses field can have upto 2 decimals places only.";
							logger.debug(errMsg);
							validationErrors.rejectValue("minimumPercentResponses", "", new String[]{lineNumber, mappedFieldNames.get("minimumPercentResponses")}, errMsg);
							validationPassed = false;
					    }
					}
					
					
				}
			}
		}
		
		if(validationPassed){
				logger.debug("Custom Validation passed. Setting Params to domain object.");
				subscoreRawToScaleScores.setAssessmentProgramId(assessmentProgramId); 
				subscoreRawToScaleScores.setSubjectId(subjectId);
				subscoreRawToScaleScores.setGradeId(gradeCourse.getId());
		}
		subscoreRawToScaleScores.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", subscoreRawToScaleScores);
		logger.debug("Completed validation completed.");
		return customValidationResults;
 		
	}
	

}
