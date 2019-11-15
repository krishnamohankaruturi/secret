package edu.ku.cete.service.impl.report;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.upload.UploadedOrganization;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.service.BatchUploadCustomValidationForAlertService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.util.ParsingConstants;
 

@SuppressWarnings("unused")
@Service
public class OrganizationDeleteUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService, BatchUploadCustomValidationForAlertService{
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OrganizationTypeService orgTypeService;
	
	final static Log logger = LogFactory.getLog(OrganizationDeleteUploadCustomValidationServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started orginization custom validation ..1 ");
		UploadedOrganization uploadedOrg = (UploadedOrganization) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long stateId = (Long) params.get("stateId");
		Long districtId = (Long) params.get("districtId");
		Long schoolId = (Long)params.get("schoolId");
		String lineNumber = uploadedOrg.getLineNumber();
		
		uploadedOrg.setStateId(stateId);
		
		logger.trace(String.format("stateId {%s},districtId {%s}, schoolId {%s} ", stateId, districtId, schoolId));
		
		boolean validationPassed = true;
		String organizationTypeCode = "";
		
		Long parentOrgId = (Long)params.get("selectedOrgId");
		
		UserDetailImpl currentUser =  (UserDetailImpl)params.get("currentUser");
		
		Map<String, Organization> validVerifiedParentOrgIdentifier = (Map<String, Organization>)params.get("validVerifiedParentOrgIdentifier");
		
		Map<String, OrganizationType> orgTypesMap = (Map<String, OrganizationType>) params.get("orgTypesMap");
		
		Organization currentContext = (Organization) params.get("currentContext");
		
		List<OrganizationType> orderedOrgTypesByLevel = (List<OrganizationType>) params.get("orderedOrgTypesByLevel");
		
		String uploadTypeCode = (String) params.get("uploadTypeCode");
		
		int currentUserOrgTypeLevel = currentContext.getOrganizationType().getTypeLevel();
		logger.trace(String.format("currentUserOrgTypeLevel {%s}", currentUserOrgTypeLevel));
		
        String upLoadOrgTypeCode = uploadedOrg.getOrganizationTypeCode().toUpperCase();
        
        logger.trace(String.format("upLoadOrgTypeCode {%s}", upLoadOrgTypeCode));
        if("DELETE_SCHOOL_XML_RECORD_TYPE".equals(uploadTypeCode) || "DELETE_LEA_XML_RECORD_TYPE".equals(uploadTypeCode)){
        	orgTypesMap = new HashMap<String, OrganizationType>(); 
        	orderedOrgTypesByLevel = orgTypeService.getAll();
        	Collections.sort(orderedOrgTypesByLevel, OrganizationType.OrgTypeLevelComparator);
        	for (OrganizationType orgType : orderedOrgTypesByLevel) {
        		orgTypesMap.put(orgType.getTypeCode().toUpperCase(), orgType); 
        	}
        }
        OrganizationType orgType = orgTypesMap.get(upLoadOrgTypeCode);
        if( orgType == null ){
        	uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_TYPE_CODE + ParsingConstants.BLANK,
                    uploadedOrg.getOrganizationTypeCode() + ParsingConstants.BLANK, true, " is in valid or not allowed ");
            validationPassed = false;
        }
        else{
	        int upLoadOrgTypeLevel = orgType.getTypeLevel();
        	logger.trace(String.format("upLoadOrgTypeLevel {%s}", upLoadOrgTypeLevel));
        	logger.trace(String.format("currentUserOrgTypeLevel >= upLoadOrgTypeLevel {%s}", (currentUserOrgTypeLevel >= upLoadOrgTypeLevel)));
        	logger.trace(String.format("!orgTypesMap.containsKey(upLoadOrgTypeCode) {%s}", (!orgTypesMap.containsKey(upLoadOrgTypeCode))));
	        if (currentUserOrgTypeLevel >= upLoadOrgTypeLevel){
	            uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_TYPE_CODE + ParsingConstants.BLANK,
	                    uploadedOrg.getOrganizationTypeCode() + ParsingConstants.BLANK, true, " is not allowed ");
	            validationPassed = false;
	        }
	        else if( !orgTypesMap.containsKey(upLoadOrgTypeCode)) {
	        	uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_TYPE_CODE + ParsingConstants.BLANK,
	                    uploadedOrg.getOrganizationTypeCode() + ParsingConstants.BLANK, true, " is not found ");
	        	validationPassed = false;
	        }
	        else { 
	        	
	        	uploadedOrg.setOrganizationType(orgTypesMap.get(upLoadOrgTypeCode));
	        	
	    		Organization parent = null;
	    	 	int uploadOrgTypeLevelIndex = orderedOrgTypesByLevel.indexOf(orgTypesMap.get(uploadedOrg.getOrganizationTypeCode().toUpperCase()));
	    	 	OrganizationType validParentOrgType = orderedOrgTypesByLevel.get(uploadOrgTypeLevelIndex-1);
	         
	    	 	logger.trace(String.format("validVerifiedParentOrgIdentifier.keySet() {%s}", validVerifiedParentOrgIdentifier.keySet()));
	    	 	logger.trace(String.format("uploadedOrg.getParentDisplayIdentifier() {%s}", uploadedOrg.getParentDisplayIdentifier()));
	    	 	if (validVerifiedParentOrgIdentifier.containsKey(uploadedOrg.getParentDisplayIdentifier())) {
	                logger.trace("The parent is already found/verifed :" + uploadedOrg.getParentDisplayIdentifier() + "," + validVerifiedParentOrgIdentifier.get(uploadedOrg.getParentDisplayIdentifier()).getId());
	                parent = validVerifiedParentOrgIdentifier.get(uploadedOrg.getParentDisplayIdentifier());
	                logger.trace(String.format("parent.getOrganizationTypeId() {%s}, validParentOrgType.getOrganizationTypeId() {%s}", parent.getOrganizationTypeId(),
	                		validParentOrgType.getOrganizationTypeId()));
	                if(validParentOrgType == null || (parent.getOrganizationTypeId() >= validParentOrgType.getOrganizationTypeId())){
	            		uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_IDENTIFIER + ParsingConstants.BLANK,
	                            uploadedOrg.getDisplayIdentifier() + ParsingConstants.BLANK, true, " is not allowed or not found ");
	            		validationPassed = false;
	            	}
	                else{
	                	uploadedOrg.setParentOrganizationId(validVerifiedParentOrgIdentifier.get(uploadedOrg.getParentDisplayIdentifier()).getId());
	                }
	            }
	            else {
	            	logger.trace(String.format("uploadedOrg.getParentDisplayIdentifier() {%s}", uploadedOrg.getParentDisplayIdentifier()));
	            	logger.trace(String.format("currentOrganization.getId() {%s}", currentContext.getId()));
	            	
	            	if("DELETE_SCHOOL_XML_RECORD_TYPE".equals(uploadTypeCode) || "DELETE_LEA_XML_RECORD_TYPE".equals(uploadTypeCode)){
	            		String contractOrgDisplayId = uploadedOrg.getContractOrgDisplayId();
	            		if(StringUtils.isNotBlank(contractOrgDisplayId)){
	            			parent = organizationService.getByDisplayIdAndType(uploadedOrg.getParentDisplayIdentifier(),
	            					orgTypeService.getByTypeCode(contractOrgDisplayId).getOrganizationTypeId(), stateId);
	            		}
	            	} else {
	            		parent = organizationService.getByDisplayIdWithContext(uploadedOrg.getParentDisplayIdentifier(),currentContext.getId());
	            	}
	            	
	            	logger.trace(String.format("parent != null {%s}", (parent != null)));
	            	
		            if (parent != null) {
		            	logger.trace(String.format("validParentOrgType == null  {%s}", (validParentOrgType == null)));
		            	logger.trace(String.format("parent.getOrganizationTypeId()  {%s}", (parent.getOrganizationTypeId())));
		            	logger.trace(String.format("validParentOrgType.getOrganizationTypeId()  {%s}", (validParentOrgType.getOrganizationTypeId())));
		            	if(validParentOrgType == null || (parent.getOrganizationTypeId() > validParentOrgType.getOrganizationTypeId())){
		            		uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_IDENTIFIER + ParsingConstants.BLANK,
		                            uploadedOrg.getDisplayIdentifier() + ParsingConstants.BLANK, true, " is not allowed or not found ");
		            		validationPassed = false;
		            	}
		            	else{
		            		//set the parent on the organization.
			                uploadedOrg.setParentOrganizationId(parent.getId());  
			                validVerifiedParentOrgIdentifier.put(uploadedOrg.getParentDisplayIdentifier(),parent);
		            	}
		            } else {
		                uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_IDENTIFIER + ParsingConstants.BLANK,
		                        uploadedOrg.getParentDisplayIdentifier() + ParsingConstants.BLANK, true, " is not allowed or not found ");
		                
		                validationPassed = false;
		            }
	            }
	    	 	if(StringUtils.isBlank(uploadedOrg.getDisplayIdentifier()) || "NULL".equals(uploadedOrg.getDisplayIdentifier().toUpperCase())){
	    	 		uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_IDENTIFIER + ParsingConstants.BLANK,
	                        ParsingConstants.BLANK, true, " is blank/NULL and not allowed");
	                validationPassed = false;
	    	 	}
	    	 	logger.trace(String.format("validationPassed  {%s}", (validationPassed)));
	    	 	if( validationPassed ){
					Set<String> organizationDisplayIdentifiers = (Set<String>)params.get("organizationDisplayIdentifiers");
		    	 	Boolean matched = false;
		    	 	OrganizationType buildingUniquenessType = (OrganizationType)params.get("buildingUniquenessType");
		    	 	logger.trace(String.format("buildingUniquenessType  {%s}", (buildingUniquenessType)));
		    	 	logger.trace(String.format("uploadedOrg.getOrganizationType().getTypeLevel()  {%s}", (uploadedOrg.getOrganizationType().getTypeLevel())));
		    	 	logger.trace(String.format("buildingUniquenessType.getTypeLevel()  {%s}", (buildingUniquenessType.getTypeLevel())));
		        	if (buildingUniquenessType != null && (uploadedOrg.getOrganizationType().getTypeLevel() > buildingUniquenessType.getTypeLevel())){
		        		//dupes are not found within this org but may be found out side this org
		        		logger.trace(String.format("uploadedOrg.getDisplayIdentifier()  {%s} parent.getId() {%s}", uploadedOrg.getDisplayIdentifier(), parent.getId()));
		        		if (null != organizationService.getByDisplayIdWithContext(uploadedOrg.getDisplayIdentifier(), parent.getId())){
		        			matched = true;
		        		}
		        	}else{
		        		//no dupes at all
		        		for (String displayIdentifier : organizationDisplayIdentifiers){
		        			if (displayIdentifier.equals(uploadedOrg.getDisplayIdentifier())){
		        				matched = true;
		        				break;
		        			}
		        		}
		            }
		            
		        	logger.trace(String.format("matched  {%s}", (matched)));
		           	if(matched) {
		           		// Found organization and update the name alone. 
		           		uploadedOrg.getOrganization().setOrganizationName(uploadedOrg.getOrganizationName());
		            }
		           	else{
		           		organizationDisplayIdentifiers.add(uploadedOrg.getDisplayIdentifier());
		           	}
	    	 	} 	
	        }
        }
        Organization org = organizationService.getByDisplayIdAndType(uploadedOrg.getOrganization().getDisplayIdentifier(), 
        		uploadedOrg.getOrganization().getOrganizationType().getOrganizationTypeId(), stateId);
        if(org == null){
        	uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_IDENTIFIER + ParsingConstants.BLANK,
                    ParsingConstants.BLANK + uploadedOrg.getOrganization().getDisplayIdentifier(), true, " does not exists in the system");
            validationPassed = false;
        }
        
        logger.trace(String.format("validationPassed  {%s}", (validationPassed)));
        if( !validationPassed ){
        	for( InValidDetail inValidDetail  : uploadedOrg.getInValidDetails() ){
        		String errMsg = new StringBuilder( inValidDetail.getInValidMessage()).toString() ;
				String fieldName = inValidDetail.getActualFieldName();
				validationErrors.rejectValue(fieldName, "", new String[]{lineNumber, mappedFieldNames.get(fieldName)}, errMsg);
        	}
        }
        else
        {
        	uploadedOrg.getOrganization().setCreatedUser(currentUser.getUserId());
        	uploadedOrg.getOrganization().setModifiedUser(currentUser.getUserId());
        }
		
 		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", uploadedOrg);
		logger.info("Completed orginization custom validation");
		return customValidationResults;
	}

	@Override
	public Map<String, Object> customValidationForAlert(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug(" organization upload custom validation For Alert Message");
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		UploadedOrganization organization = (UploadedOrganization) rowData;
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", organization);
		return customValidationResults;
	}
}
