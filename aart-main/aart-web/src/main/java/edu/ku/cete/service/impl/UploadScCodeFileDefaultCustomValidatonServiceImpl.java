package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.util.CommonConstants;

@Service
public class UploadScCodeFileDefaultCustomValidatonServiceImpl implements
		BatchUploadCustomValidationService {
	final static Log logger = LogFactory
			.getLog(UploadScCodeFileDefaultCustomValidatonServiceImpl.class);
	
	@Autowired
	private OrganizationService organizationService; 
	
	@Autowired
	private StudentService studentService; 

	@Autowired
	private AppConfigurationService appConfigurationService;
	
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		// TODO Auto-generated method stub
		UploadScCodeFile uploadScCodeFile = (UploadScCodeFile) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long uploadedUserId=(Long) params.get("uploadedUserId");
		Long sateId=(Long) params.get("stateId");
		Organization organization= organizationService.get(sateId);
		boolean validationPassed = true;
		String lineNumber = uploadScCodeFile.getLineNumber();
		
		if( !StringUtils.equalsIgnoreCase(organization.getOrganizationName(),uploadScCodeFile.getState())) {
			String errMsg = "State is not matched.";
			logger.debug(errMsg);
			validationErrors.rejectValue("state", "", new String[]{lineNumber, mappedFieldNames.get("state")}, errMsg);
			validationPassed = false;
		} 
		/*Student student=studentService.findById(uploadScCodeFile.getStudentId());
		if(student==null){
			String errMsg = "Given Studentid is not available.";
			logger.debug(errMsg);
			validationErrors.rejectValue("studentId", "", new String[]{lineNumber, mappedFieldNames.get("studentId")}, errMsg);
			validationPassed = false;
		}*/
		
		
		if(!uploadScCodeFile.getStateStudentIdentifier().trim().isEmpty() ){
    		String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
    		int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
    		if(uploadScCodeFile.getStateStudentIdentifier().trim().length()>allowedLength){
    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
    			String errMsg = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
    			validationErrors.rejectValue("stateStudentIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("stateStudentIdentifier")}, errMsg);
	    		validationPassed = false;
    		}
		}		
		
		if(validationPassed){
			uploadScCodeFile.setStateId(sateId);
			uploadScCodeFile.setActiveFlag(false);//If all the row pass custom validation successful then we make this to true
			uploadScCodeFile.setReportYear((Long) params.get("reportYear"));
		}
		
		uploadScCodeFile.setBatchUploadId(batchUploadId);
		uploadScCodeFile.setUploadedUserId(uploadedUserId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", uploadScCodeFile);
		logger.debug("Completed validation completed.");		
		return customValidationResults;
	}

}
