package edu.ku.cete.service.impl.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestTypeService;
 


@Service
public class LevelDescriptionCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	final static Log logger = LogFactory.getLog(LevelDescriptionCustomValidationServiceImpl.class);
	
	@Autowired
	private GradeCourseService gradeCourseService;		
	
	@Value("${levelDescriptionType.mdpt}")
	private String levelDescriptionTypeMdpt;
	
	@Value("${levelDescriptionType.main}")
	private String levelDescriptionTypeMain;
	
	@Value("${levelDescriptionType.combined}")
	private String levelDescriptionTypeCombined;
	
	@Value("${levelDescription.mdpt.max.bullets}")
	private int levelDescriptionMdptMaxBullets;

	@Value("${levelDescription.main.max.bullets}")
	private int levelDescriptionMainMaxBullets;
	
	@Value("${levelDescription.mdpt.max.length}")
	private int levelDescriptionMdptMaxLength;
	
	@Value("${levelDescription.main.max.length}")
	private int levelDescriptionMainMaxLength;
	
	@Value("${levelDescriptionParagraphPageBottom.max.length}")
	private int levelDescriptionParagraphPageBottomMaxLength;
	
	@Value("${levelDescription.delimitter}")
	private String levelDescriptionDelimitter ;
	
	@Value("${testingProgramName.Interim}")
	private String interimTestingProgram;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for Excluded Items Batch Upload");
		LevelDescription levelDescription = (LevelDescription) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		Long testingProgramIdOnUI = (Long)params.get("testingProgramIdOnUI");
		String testingProgramName = (String)params.get("testingProgramNameOnUI");
		//reportCycle value is coming from batchupload table which was inserted from upload file, not a UI filter at this time
		String reportCycle = (String)params.get("reportCycleOnUI");  
		Long createdUserId = (Long)params.get("createdUser");
		String lineNumber = levelDescription.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		
		if(!levelDescription.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		}else{
			//validate testingprogram
			if(!levelDescription.getTestingProgramName().equalsIgnoreCase(testingProgramName)){
				String errMsg = "Testing Program is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("testingProgramName", "", new String[]{lineNumber, mappedFieldNames.get("testingProgramName")}, errMsg);
				validationPassed = false;
			}else{
				//validate reportcycle only if the selected testingprogram is Interim
				if((interimTestingProgram.equalsIgnoreCase(testingProgramName) ||  "CPASS".equalsIgnoreCase(assessmentProgramCodeOnUI)) 
						&& !levelDescription.getReportCycle().equalsIgnoreCase(reportCycle)){
					String errMsg = "Report Cycle is invalid. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("reportCycle", "", new String[]{lineNumber, mappedFieldNames.get("reportCycle")}, errMsg);
					validationPassed = false;
				}
				if(validationPassed){
					//validate subject
					if(!levelDescription.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
						String errMsg = "Subject is invalid.";
						logger.debug(errMsg);
						validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
						validationPassed = false;
					}else{
						//validate grade
						gradeCourse =  gradeCourseService.getByContentAreaAndAbbreviatedName(subjectId, levelDescription.getGrade());
						if(gradeCourse == null){
							String errMsg = "Grade is invalid for subject specified.";
							logger.debug(errMsg);
							validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
							validationPassed = false;
						}else{
			               if("CPASS".equalsIgnoreCase(assessmentProgramCodeOnUI)){//Added for CPASS Results upload
			            	    //Validate Test_Type  
								List<TestType> testType = testTypeService.getCPASSTestTypesForReportsByTestTypeCode(testingProgramIdOnUI, levelDescription.getTestType());
								if(testType.size() > 0){
									if(!levelDescription.getSubject().equalsIgnoreCase(testType.get(0).getSubjectCode())
											|| !levelDescription.getGrade().equalsIgnoreCase(testType.get(0).getGradeCode())){
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
			               if(validationPassed){
								if(StringUtils.isNotEmpty(levelDescription.getDescriptionParagraphPageBottom())
										&& levelDescription.getDescriptionParagraphPageBottom().trim().length() > levelDescriptionParagraphPageBottomMaxLength){
									String errMsg = "Value has exceeded maximum number of characters("+ levelDescriptionParagraphPageBottomMaxLength +") specified for this field";
									logger.debug(errMsg);
									validationErrors.rejectValue("descriptionParagraphPageBottom", "", new String[]{lineNumber, mappedFieldNames.get("descriptionParagraphPageBottom")}, errMsg);
									validationPassed = false;
									
								}else{
									if(StringUtils.isNotEmpty(levelDescription.getLevelDescription())){
										boolean invalidDescription = false;	
										String errMsg = StringUtils.EMPTY;
										
										if(levelDescription.getLevelDescription().indexOf(levelDescriptionDelimitter) > -1){
											List<String> levelDescBulletedList = new ArrayList<String>(Arrays.asList(levelDescription.getLevelDescription().split(levelDescriptionDelimitter)));
											
											if(levelDescription.getDescriptionType().equalsIgnoreCase(levelDescriptionTypeMdpt) && levelDescBulletedList.size() > levelDescriptionMdptMaxBullets){									
													invalidDescription = true;
													errMsg = "Value has exceeded maximum number of bullet points("+ levelDescriptionMdptMaxBullets +") specified for this field";
											}else if(levelDescription.getDescriptionType().equalsIgnoreCase(levelDescriptionTypeMain) && levelDescBulletedList.size() > levelDescriptionMainMaxBullets){
												invalidDescription = true;
												errMsg = "Value has exceeded maximum number of bullet points("+ levelDescriptionMainMaxBullets +") specified for this field";
											}
											
										}else{
											if(levelDescription.getDescriptionType().equalsIgnoreCase(levelDescriptionTypeMdpt) && levelDescription.getLevelDescription().trim().length() > levelDescriptionMdptMaxLength){
												invalidDescription = true;
												errMsg = "Value has exceeded maximum number of characters("+ levelDescriptionMdptMaxLength +") specified for this field";
											}else if(levelDescription.getDescriptionType().equalsIgnoreCase(levelDescriptionTypeMain) && levelDescription.getLevelDescription().trim().length() > levelDescriptionMainMaxLength){
												invalidDescription = true;
												errMsg = "Value has exceeded maximum number of characters("+ levelDescriptionMainMaxLength +") specified for this field";
											}
										}
										
										if(invalidDescription){
											
											logger.debug(errMsg);
											validationErrors.rejectValue("levelDescription", "", new String[]{lineNumber, mappedFieldNames.get("levelDescription")}, errMsg);
											validationPassed = false;
										}
										
									}
								}
						    }
						}
					}
				
				}
			}
		}		
		
		if(validationPassed){
				logger.debug("Custom Validation passed. Setting Params to domain object.");
				levelDescription.setAssessmentProgramId(assessmentProgramId); 
				levelDescription.setSubjectId(subjectId);
				levelDescription.setGradeId(gradeCourse.getId());
				levelDescription.setTestingProgramId(testingProgramIdOnUI);
		}
		
		levelDescription.setBatchUploadId(batchUploadId);
		levelDescription.setCreatedUser(createdUserId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", levelDescription);
		logger.debug("Completed validation completed.");
		return customValidationResults;
 		
	}
	

}
