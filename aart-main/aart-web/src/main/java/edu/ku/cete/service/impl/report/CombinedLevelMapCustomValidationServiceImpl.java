/**
 * 
 */
package edu.ku.cete.service.impl.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.UserService;

/**
 * @author ktaduru_sta
 *
 */
@Service
public class CombinedLevelMapCustomValidationServiceImpl implements BatchUploadCustomValidationService {

	final static Log logger = LogFactory.getLog(CombinedLevelMapCustomValidationServiceImpl.class);
	
	@Autowired
	private GradeCourseService gradeCourseService;	
	
	@Autowired
	private UserService userService;
	
	@Value("${wsAdminUserName}")
    private String wsAdminUserName;
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for Combined Level Map Batch Upload");
		CombinedLevelMap combinedLevelMap = (CombinedLevelMap) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber = combinedLevelMap.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		
		if(!combinedLevelMap.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Must be assessment program specified on upload page. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		}else{
			//validate subject
			if(!combinedLevelMap.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
				String errMsg = "Must be subject selected on upload page.";
				logger.debug(errMsg);
				validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
				validationPassed = false;
			}else{
				//validate grade
				gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, combinedLevelMap.getGrade());
				if(gradeCourse == null){
					String errMsg = "Must be a valid grade for subject specified.";
					logger.debug(errMsg);
					validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
					validationPassed = false;
				}else{
					//validate stages_high_scale_score
					if(combinedLevelMap.getStagesHighScaleScore().compareTo(combinedLevelMap.getStagesLowScaleScore()) < 0){
						String errMsg = "Must be higher value than low scale score.";
						logger.debug(errMsg);
						validationErrors.rejectValue("stagesHighScaleScore", "", new String[]{lineNumber, mappedFieldNames.get("stagesHighScaleScore")}, errMsg);
						validationPassed = false;
					}
				}
			}
		}		
		
		if(validationPassed){
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			combinedLevelMap.setAssessmentProgramId(assessmentProgramId); 
			combinedLevelMap.setSubjectId(subjectId);
			combinedLevelMap.setGradeId(gradeCourse.getId());
			UserDetailImpl user = new UserDetailImpl(userService.getByUserName(wsAdminUserName));
			if(user != null){
				combinedLevelMap.setCreatedUser(user.getUser().getId());
				combinedLevelMap.setModifiedUser(user.getUser().getId());
			}
			
		}
		combinedLevelMap.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", combinedLevelMap);
		logger.debug("Completed validation.");
		return customValidationResults;
	}

}
