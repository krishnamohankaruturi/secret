package edu.ku.cete.service.impl.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.RawToScaleScoresService;
 

@SuppressWarnings("unused")
@Service
public class RawToScaleScoresCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	@Autowired
	private TestService testService;
	
	@Value("${report.subject.socialstudies.name}")
	private String CONTENT_AREA_SS;
	
	final static Log logger = LogFactory.getLog(RawToScaleScoresCustomValidationServiceImpl.class);

	@Value("${testingProgramName.Interim}")
	private String interimTestingProgram;
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for Excluded Items Batch Upload");
		RawToScaleScores rawToScaleScores = (RawToScaleScores) rowData;
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
		String lineNumber = rawToScaleScores.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		boolean performanceTestExists = false;
		ContentArea performanceSubject = null;
		
		if(!rawToScaleScores.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		}else{
			//validate testingprogram
			if(!rawToScaleScores.getTestingProgramName().equalsIgnoreCase(testingProgramName)){//testingprogram
				String errMsg = "Testing Program is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("testingProgramName", "", new String[]{lineNumber, mappedFieldNames.get("testingProgramName")}, errMsg);
				validationPassed = false;
			}else{
				//validate reportcycle only if the selected testingprogram is Interim
				if(interimTestingProgram.equalsIgnoreCase(testingProgramName) && !rawToScaleScores.getReportCycle().equalsIgnoreCase(reportCycle)){
					String errMsg = "Report Cycle is invalid. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("reportCycle", "", new String[]{lineNumber, mappedFieldNames.get("reportCycle")}, errMsg);
					validationPassed = false;
				}
				if(validationPassed){//validation passed

					//KAP - No Science this year. No validation needed.
					//validate subject
					if(!rawToScaleScores.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
						String errMsg = "Subject is invalid.";
						logger.debug(errMsg);
						validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
						validationPassed = false;
					}else{
						//validate grade
						gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, rawToScaleScores.getGrade());
						if(gradeCourse == null){
							String errMsg = "Grade is invalid for subject specified.";
							logger.debug(errMsg);
							validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
							validationPassed = false;
						}
						else{
							
							if(CONTENT_AREA_SS.equalsIgnoreCase(subjectCodeOnUI)){
								if(rawToScaleScores.getTestId1() != null){
									//validate testid1: Must be a valid testID in assessment program, grade and subject if provided for social studies
									List<Test> validTestOnRecord = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId1(), testingProgramName);
									if(CollectionUtils.isEmpty(validTestOnRecord)){
										String errMsg = "TestId1 is invalid for assessment program, testing program, grade and subject specified.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
										validationPassed = false;
									}
									//validate testid1: Cannot be a test in test collection associated with stage = Performance
									if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId1()))
									{
										String errMsg = "TestId1 belongs to Test collection associated with Performance stage tests.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
										validationPassed = false;
									}
								}
							}
							else
							{
								if(rawToScaleScores.getTestId1() != null){
									List<Test> validTestOnRecord = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId1(), testingProgramName);
									if(CollectionUtils.isEmpty(validTestOnRecord)){
										String errMsg = "TestId1 is invalid for assessment program, testing program, grade and subject specified.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
										validationPassed = false;
									}
									//validate testid1: Cannot be a test in test collection associated with stage = Performance
									if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId1()))
									{
										String errMsg = "TestId1 belongs to Test collection associated with Performance stage tests.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
										validationPassed = false;
									}
								}
								else{
									String errMsg = "TestId1 not specified.";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId1", "", new String[]{lineNumber, mappedFieldNames.get("testId1")}, errMsg);
									validationPassed = false;
								}
									
							}
							
							//TestID2 not required
							if(rawToScaleScores.getTestId2() != null){
								if(rawToScaleScores.getTestId2().equals(rawToScaleScores.getTestId1()))
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
									List<Test> validTestOnRecordForTest2 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId2(), testingProgramName);
									if(CollectionUtils.isEmpty(validTestOnRecordForTest2)){
										String errMsg = "TestId2 is invalid for assessment program, testing program, grade and subject specified.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
										validationPassed = false;
									}else{
										//validate testid2: Cannot be a test in teest collection associated with stage = Performance
										if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId2()))
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
							if(rawToScaleScores.getTestId3() != null){
								if(rawToScaleScores.getTestId3().equals(rawToScaleScores.getTestId1()) || rawToScaleScores.getTestId3().equals(rawToScaleScores.getTestId2()))
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
									List<Test> validTestOnRecordForTest3 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId3(), testingProgramName);
									if(CollectionUtils.isEmpty(validTestOnRecordForTest3)){
										String errMsg = "TestId3 is invalid for assessment program, grade and subject specified.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
										validationPassed = false;
									}else{
										//validate testid3: Cannot be a test in teest collection associated with stage = Performance
										if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId3()))
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
							if(rawToScaleScores.getTestId4() != null){
								if(rawToScaleScores.getTestId4().equals(rawToScaleScores.getTestId1()) || rawToScaleScores.getTestId4().equals(rawToScaleScores.getTestId2())
										|| rawToScaleScores.getTestId4().equals(rawToScaleScores.getTestId3()))
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
									List<Test> validTestOnRecordForTest4 = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(subjectId, gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getTestId4(), testingProgramName);
									if(CollectionUtils.isEmpty(validTestOnRecordForTest4)){
										String errMsg = "TestId4 is invalid for assessment program, testing program, grade and subject specified.";
										logger.debug(errMsg);
										validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
										validationPassed = false;
									}else{
										//validate testid4: Cannot be a test in teest collection associated with stage = Performance
										if(testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getTestId1()))
										{
											String errMsg = "TestId4 belongs to Test collection associated with Performance stage tests.";
											logger.debug(errMsg);
											validationErrors.rejectValue("testId4", "", new String[]{lineNumber, mappedFieldNames.get("testId4")}, errMsg);
											validationPassed = false;
										}
									}
								}
							}
							
							//performanceRawscoreInclude is no longer a required field
							//if(rawToScaleScores.getPerformanceRawscoreInclude()!=null && !rawToScaleScores.getPerformanceRawscoreInclude().isEmpty()){	
							//Irrespective of the testids/performance testids, validate if this flag has yes/no or empty. 
							//Validate that all rows in the "Upload raw score to scale score" have the same value for Include_Performance_Items_In_Raw_Score
							//	with respect to schoolyear/assessmentprogram/subject/grade
							RawToScaleScores firstRawToScaleObjectForFlag = rawToScaleScoresService.getFirstIncludePerformanceFlagForAssessmentProgramSubjectGrade(assessmentProgramId, subjectId, 
									gradeCourse.getId(), rawToScaleScores.getSchoolYear(), testingProgramName, reportCycle);
							if(firstRawToScaleObjectForFlag!=null){
								boolean invalidPerformanceRawscoreFlag = false;
								if(rawToScaleScores.getPerformanceRawscoreInclude() == null || rawToScaleScores.getPerformanceRawscoreInclude().isEmpty()){
									if(firstRawToScaleObjectForFlag.getPerformanceRawscoreIncludeFlag() != null)
										invalidPerformanceRawscoreFlag = true;
								}
								else if(rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase().equals("yes")) {
									if(firstRawToScaleObjectForFlag.getPerformanceRawscoreIncludeFlag() != Boolean.TRUE)
										invalidPerformanceRawscoreFlag = true;
								}
								else if (rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase().equals("no")) {
									if(firstRawToScaleObjectForFlag.getPerformanceRawscoreIncludeFlag() != Boolean.FALSE)
										invalidPerformanceRawscoreFlag = true;
								}
								
								if(invalidPerformanceRawscoreFlag)
								{
									String errMsg = "Value of Include_Performance_Items_In_Raw_Score must be the same for all the rows for the assessment program/subject/grade.";
									logger.debug(errMsg);
									validationErrors.rejectValue("performanceRawscoreInclude", "", new String[]{lineNumber, mappedFieldNames.get("performanceRawscoreInclude")}, errMsg);
									validationPassed = false;
								}
							}
								
								
							//If performanceTestId is provided, then the following must be checked
							if(rawToScaleScores.getPerformanceTestId() != null){
								
									//1. performanceSubject becomes a required field
									if (rawToScaleScores.getPerformanceSubject()==null || rawToScaleScores.getPerformanceSubject().isEmpty())
									{
										String errMsg = "Performance Subject is required as Performance TestID is provided.";
										logger.debug(errMsg);
										validationErrors.rejectValue("performanceSubject", "", new String[]{lineNumber, mappedFieldNames.get("performanceSubject")}, errMsg);
										validationPassed = false;
									}//End 1.
									else{
										performanceSubject = contentAreaService.findByAbbreviatedName(rawToScaleScores.getPerformanceSubject());
										
										//2. Check if performance Subject is valid 
										List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgram(assessmentProgramId);
										List<String> validSubjectAbbreviations = new ArrayList<String>(); 
										for(ContentArea subject : contentAreas)
											validSubjectAbbreviations.add(subject.getAbbreviatedName());
										
										if(performanceSubject==null || !validSubjectAbbreviations.contains(performanceSubject.getAbbreviatedName()))
										{
											String errMsg = "Performance Subject provided is not a valid subject for the given assessment program.";
											logger.debug(errMsg);
											validationErrors.rejectValue("performanceSubject", "", new String[]{lineNumber, mappedFieldNames.get("performanceSubject")}, errMsg);
											validationPassed = false;
										}
										else{
											//3. check if performance TestID is valid
											List<Test> validTestOnRecordForPerformanceTest = testService.getActiveTestByExternalIdGradeIdContentIdAssessmentId(performanceSubject.getId(), 
														gradeCourse.getAbbreviatedName(), assessmentProgramId, rawToScaleScores.getPerformanceTestId(), testingProgramName);
											if(CollectionUtils.isEmpty(validTestOnRecordForPerformanceTest)){
												String errMsg = "Performance TestID is invalid for assessment program, grade and performance subject specified.";
												logger.debug(errMsg);
												validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
												validationPassed = false;
												
											}
											else
											{
												//validate PerformanceTestId: Must be a test in test collection associated with stage = Performance
												if(!testService.isTestIdFromTestCollectionsWithPerformanceTests(rawToScaleScores.getPerformanceTestId()))
												{
													String errMsg = "PerformanceTestId must belong to Test collection associated with Performance stage tests.";
													logger.debug(errMsg);
													validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
													validationPassed = false;
												}
												else{
													performanceTestExists = true;
													//5. performanceItemWeight becomes a required field
													if(rawToScaleScores.getPerformanceItemWeight()==null || rawToScaleScores.getPerformanceRawscoreInclude().isEmpty())
													{
														String errMsg = "Performance_Item_Weight field is required for the given Performance TestID.";
														logger.debug(errMsg);
														validationErrors.rejectValue("performanceItemWeight", "", new String[]{lineNumber, mappedFieldNames.get("performanceItemWeight")}, errMsg);
														validationPassed = false;
													}
													else if(rawToScaleScores.getPerformanceItemWeight().compareTo(new BigDecimal("0")) != 0 )
													{
														int tempLength = rawToScaleScores.getPerformanceItemWeight().stripTrailingZeros().toPlainString().length();
														int index = rawToScaleScores.getPerformanceItemWeight().stripTrailingZeros().toPlainString().indexOf(".");
														int numOfDecimals = 0;
														if(index>0)
															numOfDecimals = tempLength - index - 1;
													    
													    if(numOfDecimals > 2)
													    {
													    	String errMsg = "Performance_Item_Weight field can have upto 2 decimals places only.";
															logger.debug(errMsg);
															validationErrors.rejectValue("performanceItemWeight", "", new String[]{lineNumber, mappedFieldNames.get("performanceItemWeight")}, errMsg);
															validationPassed = false;
													    }
													}
													//End 5.
												}
											}//End 3.
										}//End 2.
									//}//End 1.
									
								}//End AMP Test
							}//End Performance Test validations
						}
					}
				
				}
			}
		}
		
		//check duplicate raw score for different scale scores
		boolean rawToScaleScoresDuplicatesExist = rawToScaleScoresService.checkDuplicateTestIdsWithRawScore(rawToScaleScores.getTestId1(), rawToScaleScores.getTestId2(),
				rawToScaleScores.getTestId3(), rawToScaleScores.getTestId4(), rawToScaleScores.getPerformanceTestId(),
				rawToScaleScores.getRawScore(), rawToScaleScores.getScaleScore(), assessmentProgramId, subjectId, gradeCourse.getId(), rawToScaleScores.getDomain(), rawToScaleScores.getSchoolYear(), testingProgramName, reportCycle);
		if(rawToScaleScoresDuplicatesExist){
			String errMsg = "Duplicate rows exist for the same raw score "+rawToScaleScores.getRawScore()+" where scale score or SE is different.";
			logger.debug(errMsg);
			validationErrors.rejectValue("scaleScore", "", new String[]{lineNumber, mappedFieldNames.get("scaleScore")}, errMsg);
			validationPassed = false;
		}
		
		if (rawToScaleScores.getAssessmentProgram().equalsIgnoreCase("KELPA2") && StringUtils.isEmpty(rawToScaleScores.getDomain())) {
			logger.debug("RawScoreToScaleScore domain not given");
			validationErrors.rejectValue("domain", "", new String[]{lineNumber, mappedFieldNames.get("domain")}, "Domain not specified");
			validationPassed = false;
		}
		
		if(validationPassed){
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			rawToScaleScores.setAssessmentProgramId(assessmentProgramId); 
			rawToScaleScores.setSubjectId(subjectId);
			rawToScaleScores.setGradeId(gradeCourse.getId());
			rawToScaleScores.setTestingProgramId(testingProgramIdOnUI);
			
			if(performanceTestExists)
				rawToScaleScores.setPerformanceSubjectId(performanceSubject.getId());
			
			if ("yes".equals(rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase())) {
				rawToScaleScores.setPerformanceRawscoreIncludeFlag(Boolean.TRUE);
			}
			else if ("no".equals(rawToScaleScores.getPerformanceRawscoreInclude().trim().toLowerCase())) {
				rawToScaleScores.setPerformanceRawscoreIncludeFlag(Boolean.FALSE);
			}	
		}		
		
		rawToScaleScores.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", rawToScaleScores);
		logger.debug("Completed validation completed.");
		return customValidationResults;
 		
	}
}
