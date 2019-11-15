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
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.SubscoreFramework;
import edu.ku.cete.domain.test.ContentFrameworkDetailDTO;
import edu.ku.cete.model.test.ContentFrameworkDetailDao;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.SubscoreFrameworkService;
 

@SuppressWarnings("unused")
@Service
public class SubscoreFrameworkCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
	private ContentFrameworkDetailDao contentFrameworkDetailDao;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private SubscoreFrameworkService subscoreFrameworkService;
	
	
	final static Log logger = LogFactory.getLog(SubscoreFrameworkCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for Excluded Items Batch Upload");
		SubscoreFramework subscoreFramework = (SubscoreFramework) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber = subscoreFramework.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		if(!subscoreFramework.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationPassed = false;
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
		}else{
			//validate subject
			if(!subscoreFramework.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
				String errMsg = "Subject is invalid.";
				logger.debug(errMsg);
				validationPassed = false;
				validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
			}else{ 
				//validate grade
				gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, subscoreFramework.getGrade());
				if(gradeCourse == null){
					String errMsg="grade is invalid for subject specified.";
					logger.debug(errMsg);
					validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
					validationPassed = false;
				}else{
					List<Long> frameworkTypeIds = batchUploadService.findFrameWorkTypeForAssessmentProgram(assessmentProgramId, subscoreFramework.getFramework());
					if(frameworkTypeIds == null){
						String errMsg="Content Framework is not valid.";
						logger.debug(errMsg);
						validationErrors.rejectValue("framework", "", new String[]{lineNumber, mappedFieldNames.get("framework")}, errMsg);
						validationPassed = false;
					}else{
						if(StringUtils.isEmpty(subscoreFramework.getFrameworkLevel2())){
							subscoreFramework.setFrameworkLevel2(null);
						}
						if(StringUtils.isEmpty(subscoreFramework.getFrameworkLevel3())){
							subscoreFramework.setFrameworkLevel3(null);
						}
						//Framework Level 1 Code Validation - Must be Claim Level
						Long levelOneCode = batchUploadService.findContentFrameworkCodeWithLevelAssessmentGradeContentarea(assessmentProgramId,
								gradeCourse.getId(), subjectId, frameworkTypeIds, "Claim", subscoreFramework.getFrameworkLevel1());
						if(levelOneCode == null){
							String errMsg="Content Framework Level 1 is not valid for level Claim.";
							logger.debug(errMsg);
							validationErrors.rejectValue("frameworkLevel1", "", new String[]{lineNumber, mappedFieldNames.get("frameworkLevel1")}, errMsg);
							validationPassed = false;
						}
						//Framework Level 2 Code Validation - Must be Target Level
						if(subscoreFramework.getFrameworkLevel2() != null && !subscoreFramework.getFrameworkLevel2().equalsIgnoreCase("All")){
							Long levelTwoCode = batchUploadService.findContentFrameworkCodeWithLevelAssessmentGradeContentarea(assessmentProgramId,
									gradeCourse.getId(), subjectId, frameworkTypeIds, "Target", subscoreFramework.getFrameworkLevel2());
							if(levelTwoCode == null){
								String errMsg="Content Framework Level 2 is not valid for level Target.";
								logger.debug(errMsg);
								validationErrors.rejectValue("frameworkLevel2", "", new String[]{lineNumber, mappedFieldNames.get("frameworkLevel2")}, errMsg);
								validationPassed = false;
							}
						}
						
						//Framework Level 3 Code Validation - Must be Target Level
						if(subscoreFramework.getFrameworkLevel3() != null && !subscoreFramework.getFrameworkLevel3().equalsIgnoreCase("All")){
							Long levelTwoCode = batchUploadService.findContentFrameworkCodeWithLevelAssessmentGradeContentarea(assessmentProgramId,
									gradeCourse.getId(), subjectId, frameworkTypeIds, "Target", subscoreFramework.getFrameworkLevel3());
							if(levelTwoCode == null){
								String errMsg="Content Framework Level 3 is not valid for level Target.";
								logger.debug(errMsg);
								validationErrors.rejectValue("frameworkLevel3", "", new String[]{lineNumber, mappedFieldNames.get("frameworkLevel3")}, errMsg);
								validationPassed = false;
							}
						} 						
						
						//check duplicate
						SubscoreFramework existingSubscoreFramework = subscoreFrameworkService.selectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2(
								subscoreFramework.getSchoolYear(), assessmentProgramId, subjectId, gradeCourse.getId(), subscoreFramework.getSubScoreDefinitionName(), 
								subscoreFramework.getFramework(), subscoreFramework.getFrameworkLevel1(), subscoreFramework.getFrameworkLevel2(), subscoreFramework.getFrameworkLevel3());		
						if(existingSubscoreFramework != null){
							String errMsg="Subscore Framework already exisits with content framework, level 1 and level 2 and level 3.";
							logger.debug(errMsg);
							validationErrors.rejectValue("framework", "", new String[]{lineNumber, mappedFieldNames.get("framework")}, errMsg);
							validationPassed = false;
						}
					}
				}
			}
		}
		
		if(validationPassed){
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			subscoreFramework.setAssessmentProgramId(assessmentProgramId); 
			subscoreFramework.setSubjectId(subjectId);
			subscoreFramework.setGradeId(gradeCourse.getId());
		}
		
		subscoreFramework.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", subscoreFramework);
		logger.debug("Completed validation completed.");
		return customValidationResults;
	}
	

}
