package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.report.domain.ExternalStudentReportResults;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.LevelDescriptionService;
import edu.ku.cete.util.CommonConstants;

@Service
public class UploadExternalStudentReportResultsCustomValidationServiceImpl
		implements BatchUploadCustomValidationService {
	final static Log logger = LogFactory
			.getLog(UploadExternalStudentReportResultsCustomValidationServiceImpl.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private TestTypeService testTypeService;
	
	@Autowired
	private BatchUploadService batchUploadService;
	
	@Autowired
    private AppConfigurationService appConfigurationService;
	
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
    @Value("${alternate.student.individual.report.type.code}")
   	private String dbDLMStudentReportsImportReportType;

    @Value("${cpass.student.individual.report.type.code}")
   	private String dbCPASSStudentReportsImportReportType;	
	
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long uploadedUserId = (Long) params.get("uploadedUserId");
	
		Long selectedAssessmentProgramId = (Long) params.get("assessmentProgramIdOnUI");
		Long stateIdOnUI = (Long) params.get("stateId");
		
		ExternalStudentReportResults externalStudentReportResults = (ExternalStudentReportResults) rowData;
	    
		String lineNumber = externalStudentReportResults.getLineNumber();
		externalStudentReportResults.setUploadLevel("student");
		//Validate Assessment program
		AssessmentProgram selectedAP = assessmentProgramService.findByAssessmentProgramId(selectedAssessmentProgramId);
		
		if(!externalStudentReportResults.getStateStudentIdentifier().trim().isEmpty() ){
    		String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
    		int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
    		if(externalStudentReportResults.getStateStudentIdentifier().trim().length()>allowedLength){
    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
    			String errMsg = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
    			validationErrors.rejectValue("stateStudentIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("stateStudentIdentifier")}, errMsg);
    		}
		}
		
		if(!externalStudentReportResults.getAssessmentProgram().equalsIgnoreCase(selectedAP.getAbbreviatedname())){
			String errMsg = "Must be assessment program specified on upload page. "+selectedAP.getAbbreviatedname()+" was selected and this line has "+externalStudentReportResults.getAssessmentProgram()+".";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
		}else{
			if("CPASS".equalsIgnoreCase(selectedAP.getAbbreviatedname())){
				externalStudentReportResults.setReportType(dbCPASSStudentReportsImportReportType);
			}else{
				externalStudentReportResults.setReportType(dbDLMStudentReportsImportReportType);
			}
			
			externalStudentReportResults.setAssessmentProgramId(selectedAP.getId());
			
			//Validate common CPASS columns
			batchUploadService.externalResultsUploadCommonValidation(externalStudentReportResults, validationErrors, params, mappedFieldNames);
			
			if(validationErrors.getAllErrors().size() == 0){			  
				//Validate student exist or not
				List<Student> students = studentService.getBySsidAndState(externalStudentReportResults.getStateStudentIdentifier(), stateIdOnUI);
				if (students == null || students.size() == 0){
					String errMsg = "State_Student_Identidier does not exist in Educator Portal. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("stateStudentIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("stateStudentIdentifier")}, errMsg);
				}else{
					//Validate achievement level
					List<LevelDescription> levelDescriptions = levelDescriptionService.getLevelDescriptionByLevelIdAndTestType(externalStudentReportResults.getSchoolYear(), selectedAP.getId(), externalStudentReportResults.getTestType(), externalStudentReportResults.getAchievementLevel());
					if(levelDescriptions.size() == 0){
						String errMsg = "Level_Number is invalid for "+externalStudentReportResults.getAssessmentName()+".";
						logger.debug(errMsg);
						validationErrors.rejectValue("achievementLevel", "", new String[]{lineNumber, mappedFieldNames.get("achievementLevel")}, errMsg);
					}else{					
						externalStudentReportResults.setStudentId(students.get(0).getId());
						externalStudentReportResults.setCreatedUser(uploadedUserId);
						externalStudentReportResults.setBatchUploadedId(batchUploadId);
						externalStudentReportResults.setAchievementLevel(levelDescriptions.get(0).getId());
						externalStudentReportResults.setActiveFlag(true);
					}
				}
			}
	
		}
		
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", externalStudentReportResults);
		logger.debug("Custom validation completed.");
		return customValidationResults;
	}	
}
