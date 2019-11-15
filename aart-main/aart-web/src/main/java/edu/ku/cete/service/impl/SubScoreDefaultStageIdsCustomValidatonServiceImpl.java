package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.SubScoresMissingStages;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestService;


@Service
public class SubScoreDefaultStageIdsCustomValidatonServiceImpl implements BatchUploadCustomValidationService {

	final static Log logger = LogFactory
			.getLog(SubScoreDefaultStageIdsCustomValidatonServiceImpl.class);

	@Autowired
	private GradeCourseService gradeCourseService;

	@Autowired
	private TestService testService;

	@Autowired
	private OrganizationService orgService;

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		
		SubScoresMissingStages subScoreMissingStages = (SubScoresMissingStages) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber = subScoreMissingStages.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;	
		
		if(!subScoreMissingStages.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		} else {
			
			List<Organization> contractingOrg = orgService.getContractingOrgsByAssessmentProgramId(assessmentProgramId);
			Long schoolYear = (long) contractingOrg.get(0).getReportYear();
			
			if(subScoreMissingStages.getSchoolYear() != null && !subScoreMissingStages.getSchoolYear().equals(schoolYear)) {
				String errMsg = "Reporting School year is not matched.";
				logger.debug(errMsg);
				validationErrors.rejectValue("schoolYear", "", new String[]{lineNumber, mappedFieldNames.get("schoolYear")}, errMsg);
				validationPassed = false;
			} else {
				if(!subScoreMissingStages.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
					String errMsg = "Subject is invalid.";
					logger.debug(errMsg);
					validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
					validationPassed = false;
				} else {
					gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, subScoreMissingStages.getGrade());
					if(gradeCourse == null){
						String errMsg = "Grade is invalid for subject specified.";
						logger.debug(errMsg);
						validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
						validationPassed = false;
					} else {
						if(subScoreMissingStages.getTestId2() != null){
							if(!testService.isTestIdFromTestCollectionsWithCorrectStageTests(subjectId, gradeCourse.getAbbreviatedName(), 
									assessmentProgramId, subScoreMissingStages.getTestId2(), "Stg2"))
							{
								String errMsg = "TestID_2 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
								validationPassed = false;
							} else {
								Long testId2 = testService.getTestIdBySchoolYearAndExternalId(schoolYear, subScoreMissingStages.getTestId2());
								if(testId2 == null) {
									String errMsg = "Test is not available with specified externalid";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId2", "", new String[]{lineNumber, mappedFieldNames.get("testId2")}, errMsg);
									validationPassed = false;
								} else {
									subScoreMissingStages.setDefaultStage2EpTestId(testId2);
								}
							}
						}
						
						if(subScoreMissingStages.getTestId3() != null){					
							if(!testService.isTestIdFromTestCollectionsWithCorrectStageTests(subjectId, gradeCourse.getAbbreviatedName(), 
									assessmentProgramId, subScoreMissingStages.getTestId3(), "Stg3")){
								String errMsg = "TestID_3 is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
								validationPassed = false;
							} else {
								Long testId3 = testService.getTestIdBySchoolYearAndExternalId(schoolYear, subScoreMissingStages.getTestId3());
								if(testId3 == null) {
									String errMsg = "Test is not available with specified externalid";
									logger.debug(errMsg);
									validationErrors.rejectValue("testId3", "", new String[]{lineNumber, mappedFieldNames.get("testId3")}, errMsg);
									validationPassed = false;
								} else {
									subScoreMissingStages.setDefaultStage3EpTestId(testId3);
								}
							}
						}
						
						if(subScoreMissingStages.getPerformanceTestId() != null){					
							if(!testService.isTestIdFromTestCollectionsWithCorrectStageTests(subjectId, gradeCourse.getAbbreviatedName(), 
									assessmentProgramId, subScoreMissingStages.getPerformanceTestId(), "Prfrm")){
								String errMsg = "Performance_TestID is invalid for assessment program, grade and subject specified.";
								logger.debug(errMsg);
								validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
								validationPassed = false;
							} else {
								Long  prfrmTestId= testService.getTestIdBySchoolYearAndExternalId(schoolYear, subScoreMissingStages.getPerformanceTestId());
								if(prfrmTestId == null) {
									String errMsg = "Test is not available with specified externalid";
									logger.debug(errMsg);
									validationErrors.rejectValue("performanceTestId", "", new String[]{lineNumber, mappedFieldNames.get("performanceTestId")}, errMsg);
									validationPassed = false;
								} else {
									subScoreMissingStages.setDefaultPerformanceEpTestId(prfrmTestId);									
								}
							}
						}
					}
				}
			}
		}
		
		if(validationPassed){
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			subScoreMissingStages.setAssessmentProgramId(assessmentProgramId);
			subScoreMissingStages.setGradeId(gradeCourse.getId());
			subScoreMissingStages.setSubjectId(subjectId);
		}
		
		subScoreMissingStages.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", subScoreMissingStages);
		logger.debug("Completed validation completed.");		
		return customValidationResults;
	}
}