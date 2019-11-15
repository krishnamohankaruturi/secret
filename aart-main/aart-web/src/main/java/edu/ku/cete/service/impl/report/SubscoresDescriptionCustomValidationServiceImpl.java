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
import edu.ku.cete.domain.report.SubscoresDescription;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.SubscoresDescriptionService;
 

@SuppressWarnings("unused")
@Service
public class SubscoresDescriptionCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private SubscoresDescriptionService subscoresDescriptionService;
	
	
	final static Log logger = LogFactory.getLog(SubscoresDescriptionCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for Subscores Description and Usage Batch Upload");
		SubscoresDescription subscoresDescription = (SubscoresDescription) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		String assessmentProgramCodeOnUI = (String) params.get("assessmentProgramCodeOnUI");
		String subjectCodeOnUI = (String) params.get("subjectCodeOnUI");
		Long assessmentProgramId = (Long)params.get("assessmentProgramIdOnUI");
		Long subjectId = (Long)params.get("subjectIdOnUI");
		String lineNumber = subscoresDescription.getLineNumber();
		GradeCourse gradeCourse = null;
		boolean validationPassed = true;
		
		if(!subscoresDescription.getAssessmentProgram().equalsIgnoreCase(assessmentProgramCodeOnUI)){
			String errMsg = "Assessment Program is invalid. ";
			logger.debug(errMsg);
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			validationPassed = false;
		}else{
			//validate subject
			if(!subscoresDescription.getSubject().equalsIgnoreCase(subjectCodeOnUI)){
				String errMsg = "Subject is invalid.";
				logger.debug(errMsg);
				validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
				validationPassed = false;
			}
			else
			{
				boolean subscoreDefinitionNameExists = subscoresDescriptionService.checkIfSubscoreDefinitionNameExists(subscoresDescription.getSchoolYear(), assessmentProgramId, subjectId, subscoresDescription.getSubscoreDefinitionName());
				if(!subscoreDefinitionNameExists){
					String errMsg="Subscore definition name ["+subscoresDescription.getSubscoreDefinitionName()+"] does not exist for Assessment Program, Subject";
					logger.debug(errMsg);
					validationErrors.rejectValue("subscoreDefinitionName", "", new String[]{lineNumber, mappedFieldNames.get("subscoreDefinitionName")}, errMsg);
					validationPassed = false;
				}
				else
				{
					boolean duplicateDefinitionName = subscoresDescriptionService.checkForDuplicateDefinitionForReport(subscoresDescription.getSchoolYear(), assessmentProgramId, subjectId, subscoresDescription.getReport(), subscoresDescription.getSubscoreDefinitionName());
					if(duplicateDefinitionName){
						String errMsg="More than 1 Subscore definition names for the report ["+subscoresDescription.getReport()+"]";
						logger.debug(errMsg);
						validationErrors.rejectValue("subscoreDefinitionName", "", new String[]{lineNumber, mappedFieldNames.get("subscoreDefinitionName")}, errMsg);
						validationPassed = false;
					}
					
					boolean duplicateSequenceForReport = subscoresDescriptionService.checkForDuplicateSequenceForReport(subscoresDescription.getSchoolYear(), assessmentProgramId, subjectId, subscoresDescription.getReport(), subscoresDescription.getSubscoreDefinitionName(), subscoresDescription.getSubscoreDisplaySequence());
					if(duplicateSequenceForReport){
						String errMsg="More than 1 Subscore definition names have the same value for display sequence for report ["+subscoresDescription.getReport()+"]";
						logger.debug(errMsg);
						validationErrors.rejectValue("subscoreDisplaySequence", "", new String[]{lineNumber, mappedFieldNames.get("subscoreDisplaySequence")}, errMsg);
						validationPassed = false;
					}
					//This is actually covered in common validations. Adding it here also for extra validation
					if(subscoresDescription.getSectionLineBelow()==null || subscoresDescription.getSectionLineBelow().isEmpty())
					{
						String errMsg = "Section_Line_Below is empty.";
						logger.debug(errMsg);
						validationErrors.rejectValue("sectionLineBelow", "", new String[]{lineNumber, mappedFieldNames.get("sectionLineBelow")}, errMsg);
						validationPassed = false;
					}
				}
			}
		}
		
		if(validationPassed){ 
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			subscoresDescription.setAssessmentProgramId(assessmentProgramId); 
			subscoresDescription.setSubjectId(subjectId);
			
			//Although the CSV file has Yes/No values, the database must store boolean true/false values
			if ("yes".contains(subscoresDescription.getSectionLineBelow().trim().toLowerCase())) {
				subscoresDescription.setSectionLineBelowFlag(Boolean.TRUE);
			}
			else if ("no".contains(subscoresDescription.getSectionLineBelow().trim().toLowerCase())) {
				subscoresDescription.setSectionLineBelowFlag(Boolean.FALSE);
			}
			
			//Although the CSV file has Yes/No values, the database must store boolean true/false values
			if(subscoresDescription.getIndentNeeded()==null || subscoresDescription.getIndentNeeded().isEmpty())
			{
				subscoresDescription.setIndentNeededFlag(Boolean.FALSE);
			}
			else if ("yes".contains(subscoresDescription.getIndentNeeded().trim().toLowerCase())) {
				subscoresDescription.setIndentNeededFlag(Boolean.TRUE);
			}
			else if ("no".contains(subscoresDescription.getIndentNeeded().trim().toLowerCase())) {
				subscoresDescription.setIndentNeededFlag(Boolean.FALSE);
			}
		}
		
		subscoresDescription.setBatchUploadId(batchUploadId);
 		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", subscoresDescription);
		logger.debug("Completed validation completed.");
		return customValidationResults;
	}
	

}
