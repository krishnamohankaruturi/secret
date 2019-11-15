/**
 * 
 */
package edu.ku.cete.service.impl;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.bytecode.opencsv.CSVReader;
import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.OrganizationTree;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.student.PersonalNeedsProfileRecord;
import edu.ku.cete.domain.student.StudentSchoolRelationInformation;
import edu.ku.cete.domain.upload.UploadedOrganization;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.model.student.ProfileAttributeDao;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UploadService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.OrgUploadSpecification;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.UploadHelper;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UploadServiceImpl implements UploadService {
 
	/**
     * logger.
     */
    private final Logger
    logger = LoggerFactory.getLogger(UploadServiceImpl.class);
    
    @Autowired
    private OrganizationService orgService;

    @Autowired
    private OrganizationTypeService orgTypeService;
    
    @Autowired
    private OrgUploadSpecification orgUploadSpecification;

	/**
	 * studentService.
	 */
    @Autowired
	private StudentService studentService;

	/**
	 * profileAttributeDao.
	 */
    @Autowired
	private ProfileAttributeDao profileAttributeDao;

    /**
     * studentProfileService.
     */
    @Autowired
	private StudentProfileService studentProfileService;

    /**
     * enrollmentService.
     */
    @Autowired
	private EnrollmentService enrollmentService;
    

	/* (non-Javadoc)
	 * @see edu.ku.cete.service.UploadService#bulkUploadOrganization(edu.ku.cete.domain.common.UploadFile,
	 *  java.util.List, java.util.Map, java.util.Map, edu.ku.cete.domain.common.Organization, java.util.List, java.util.List)
	 *  
	 *  1. TODO Good performance but too hard to follow this as a pattern.
	 *  2. TODO Service methods has to be state less i.e. don't rely on call by reference mechanism.
	 */
	@Override 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> bulkUploadOrganization(UploadFile uploadFile,
			Map<String, FieldSpecification> fieldSpecificationMap,
			Organization currentUserOrg, ContractingOrganizationTree contractingOrganizationTree,
			List<UploadedOrganization> invalidOrgs, String fileCharset, Boolean defaultFileCharset) throws IOException, AartParseException,
			IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException{
		
		Map<String, Object> returnResult = new HashMap<String, Object>();
		List<Organization> createdOrgsList = new ArrayList<Organization>();
		
    	InputStream inputStream = new FileInputStream(uploadFile.getFile());
    	CSVReader reader = new CSVReader(new InputStreamReader(inputStream,fileCharset)); 
    	
		Map<String, String> orgColMap = orgUploadSpecification.getOrgColumnMap();
        Class<UploadedOrganization> typeParameterClass = UploadedOrganization.class;
        List<UploadedOrganization> uploadedOrgs = new ArrayList<UploadedOrganization>();
        try {
        	uploadedOrgs = UploadHelper.aartParseUploadedContents(reader, orgColMap, fieldSpecificationMap, typeParameterClass);
        } catch(Exception ex) {
        	logger.error("Failed to upload Organization file beacuse "+ex.getMessage());        	
        } finally {
        	inputStream.close();
        }
        Collection<Organization> currentUserChildOrgs = contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationTree();
        		
        logger.debug("Received {} organizations to upload to the system :" + uploadedOrgs.size());
        
        int createdOrgs = 0;

        if (uploadedOrgs.size() > 0) {
                           
            logger.debug("Entering the uploadOrganizationsFromFile with - "
            + currentUserOrg.getDisplayIdentifier() + "," + currentUserOrg.getOrganizationType().getTypeCode());
            
            Long currentUserOrgId = currentUserOrg.getId();
            String currentUserOrgDispIdentifier = currentUserOrg.getDisplayIdentifier();
            int currentUserOrgTypeLevel = currentUserOrg.getOrganizationType().getTypeLevel();

            //Get the list of all organization type and order it by level 
            Map<String, List<UploadedOrganization>> validUploadedOrgsGrpByOrgType = new HashMap<String, List<UploadedOrganization>>();
            //List<OrganizationType> orderedOrgTypesByLevel = orgTypeService.getAll();
            List<OrganizationType> orderedOrgTypesByLevel = orgTypeService.getOrgHierarchyByOrg(uploadFile.getStateId());

            Collections.sort(orderedOrgTypesByLevel, OrganizationType.OrgTypeLevelComparator);

            Map<String, OrganizationType> orgTypesMap = new HashMap<String, OrganizationType>(); 
            Map<Long, Integer> orgTypeIdNLevelMap = new HashMap<Long, Integer>();
            for (OrganizationType orgType : orderedOrgTypesByLevel) {
                orgTypesMap.put(orgType.getTypeCode().toUpperCase(), orgType); 
                orgTypeIdNLevelMap.put(orgType.getOrganizationTypeId(), orgType.getTypeLevel());
            }
            
            //Maintain the current list of children for the context user 
            Map<String, Organization> validVerifiedParentOrgIdentifier = new HashMap< String, Organization>();
            validVerifiedParentOrgIdentifier.put(currentUserOrgDispIdentifier, currentUserOrg);
            for (Organization currentUserChildOrg : currentUserChildOrgs) {
                validVerifiedParentOrgIdentifier.put(currentUserChildOrg.getDisplayIdentifier(), currentUserChildOrg);
            }

            String upLoadOrgTypeCode = null;
            int upLoadOrgTypeLevel = 0;
            List<UploadedOrganization> uploadedOrganizations = new ArrayList<UploadedOrganization>();
            Set<String> uploadParentOrgIdentifier = new HashSet< String>();

            //Here the organization's type level and user's organization type level is compared.
            //Here the organization's type code and user's organization type code is compared.
            // Pass the list of objects and get back the list.
            for (UploadedOrganization uploadedOrg : uploadedOrgs) {
                if (!uploadedOrg.isDoReject()) {

                    if (!validVerifiedParentOrgIdentifier.containsKey(uploadedOrg.getParentDisplayIdentifier()))
                        uploadParentOrgIdentifier.add(uploadedOrg.getParentDisplayIdentifier());

                    upLoadOrgTypeCode = uploadedOrg.getOrganizationTypeCode().toUpperCase();
                    upLoadOrgTypeLevel = orgTypesMap.get(upLoadOrgTypeCode).getTypeLevel();
                    if (currentUserOrgTypeLevel >= upLoadOrgTypeLevel){
                        uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_TYPE_CODE + ParsingConstants.BLANK,
                                uploadedOrg.getOrganizationTypeCode() + ParsingConstants.BLANK, true, InvalidTypes.NOT_ALLOWED);
                        invalidOrgs.add(uploadedOrg);
                    } else if(orgTypesMap.containsKey(upLoadOrgTypeCode)) {
                        if (validUploadedOrgsGrpByOrgType.containsKey(upLoadOrgTypeCode)) {
                            uploadedOrganizations = validUploadedOrgsGrpByOrgType.get(upLoadOrgTypeCode);
                        } else {
                            uploadedOrganizations = new ArrayList<UploadedOrganization>();
                        }
                        uploadedOrg.setOrganizationType(orgTypesMap.get(upLoadOrgTypeCode));
                        uploadedOrganizations.add(uploadedOrg);
                        validUploadedOrgsGrpByOrgType.put(upLoadOrgTypeCode, uploadedOrganizations);
                    } else {
                        uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_TYPE_CODE + ParsingConstants.BLANK,
                                uploadedOrg.getOrganizationTypeCode() + ParsingConstants.BLANK, true, InvalidTypes.NOT_FOUND);
                        invalidOrgs.add(uploadedOrg);
                    }
                } else {
                    invalidOrgs.add(uploadedOrg);
                }
            }

            Organization parent = null;
            Long oldParentOrgId = null;
            Set<String> inValidVerifiedParentOrgIdentifier = new HashSet< String>();
            
            Set<String> organizationDisplayIdentifiers = contractingOrganizationTree.getOrganizationDisplayIdentifiers();
            
            //building uniqueness is set by the state
            Long stateId = uploadFile.getStateId();
            Organization state = orgService.get(stateId);
            OrganizationType buildingUniquenessType = null;
            if (state.getBuildingUniqueness() != null){
            	buildingUniquenessType = orgTypeService.get(state.getBuildingUniqueness());
            }
            //Checking for parents.
            //Here we are uploading the state and then adding the districts below it.
            for (OrganizationType orgType : orderedOrgTypesByLevel) {
                //logger.debug("orgType.getTypeCode().toUpperCase() - " + orgType.getTypeCode().toUpperCase());

                if (validUploadedOrgsGrpByOrgType.get(orgType.getTypeCode().toUpperCase()) != null) {
                    for (UploadedOrganization uploadedOrg : validUploadedOrgsGrpByOrgType.get(orgType.getTypeCode().toUpperCase())) {
                        parent = null;
                        oldParentOrgId = null;
                        int uploadOrgTypeLevelIndex = orderedOrgTypesByLevel.indexOf(orgTypesMap.get(uploadedOrg.getOrganizationTypeCode().toUpperCase()));
                        OrganizationType validParentOrgType = orderedOrgTypesByLevel.get(uploadOrgTypeLevelIndex-1);
                        
                        if (inValidVerifiedParentOrgIdentifier.contains(uploadedOrg.getParentDisplayIdentifier())) {
                            logger.debug("The parent is not allowed/found and already verified :" + uploadedOrg.getParentDisplayIdentifier());
                            uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_PARENT_IDENTIFIER + ParsingConstants.BLANK,
                                    uploadedOrg.getParentDisplayIdentifier() + ParsingConstants.BLANK, true, InvalidTypes.NOT_ALLOWED_NOT_FOUND);
                            invalidOrgs.add(uploadedOrg); 
                            continue;
                        }
                        else if (validVerifiedParentOrgIdentifier.containsKey(uploadedOrg.getParentDisplayIdentifier())) {
                            logger.debug("The parent is already found/verifed :" + uploadedOrg.getParentDisplayIdentifier() + "," + validVerifiedParentOrgIdentifier.get(uploadedOrg.getParentDisplayIdentifier()).getId());
                            parent = validVerifiedParentOrgIdentifier.get(uploadedOrg.getParentDisplayIdentifier());
                                                        
                            //if(orgTypeIdNLevelMap.get(parent.getOrganizationTypeId()) >= orgTypesMap.get(uploadedOrg.getOrganizationTypeCode().toUpperCase()).getTypeLevel()
                            if(validParentOrgType == null || (parent.getOrganizationTypeId() != validParentOrgType.getOrganizationTypeId())){
                        		uploadedOrg.addInvalidField(FieldName.DISPLAY_IDENTIFIER + ParsingConstants.BLANK,
                                        uploadedOrg.getDisplayIdentifier() + ParsingConstants.BLANK, true, InvalidTypes.NOT_ALLOWED_NOT_FOUND);
                                invalidOrgs.add(uploadedOrg);
                                continue;
                        	}
                            uploadedOrg.setParentOrganizationId(validVerifiedParentOrgIdentifier.get(uploadedOrg.getParentDisplayIdentifier()).getId());
                        }
                        else if (uploadParentOrgIdentifier.contains(uploadedOrg.getParentDisplayIdentifier()) 
                                && !validVerifiedParentOrgIdentifier.containsKey(uploadedOrg.getParentDisplayIdentifier())){
                            logger.debug("check uploadedOrg.getParentDisplayIdentifier() - " + uploadedOrg.getParentDisplayIdentifier());
                            parent = orgService.getByDisplayIdWithContext(uploadedOrg.getParentDisplayIdentifier(),currentUserOrgId);
                            if (parent != null) {
                            	//if(orgTypeIdNLevelMap.get(parent.getOrganizationTypeId()) >= orgTypesMap.get(uploadedOrg.getOrganizationTypeCode().toUpperCase()).getTypeLevel()){
                            	if(validParentOrgType == null || (parent.getOrganizationTypeId() != validParentOrgType.getOrganizationTypeId())){
                            		uploadedOrg.addInvalidField(FieldName.DISPLAY_IDENTIFIER + ParsingConstants.BLANK,
                                            uploadedOrg.getDisplayIdentifier() + ParsingConstants.BLANK, true, InvalidTypes.NOT_ALLOWED_NOT_FOUND);
                                    invalidOrgs.add(uploadedOrg);
                                    continue;
                            	}
                                //set the parent on the organization.
                                uploadedOrg.setParentOrganizationId(parent.getId());  
                                validVerifiedParentOrgIdentifier.put(uploadedOrg.getParentDisplayIdentifier(),parent);

                            } else {
                                uploadedOrg.addInvalidField(FieldName.DISPLAY_IDENTIFIER + ParsingConstants.BLANK,
                                        uploadedOrg.getParentDisplayIdentifier() + ParsingConstants.BLANK, true, InvalidTypes.NOT_ALLOWED_NOT_FOUND);
                                invalidOrgs.add(uploadedOrg);
                                inValidVerifiedParentOrgIdentifier.add(uploadedOrg.getParentDisplayIdentifier());
                                continue;
                            }
                        }
                                                                       
                       
                        try {
                        	Boolean matched = false;
                        	if (buildingUniquenessType != null && (uploadedOrg.getOrganizationType().getTypeLevel() > buildingUniquenessType.getTypeLevel())){
                        		//dupes are not found within this org but may be found out side this org
                        		if (null != orgService.getByDisplayIdWithContext(uploadedOrg.getDisplayIdentifier(), parent.getId())){
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
                            
                           	if(matched) {
                           	
                            	// There is no update organization requirement
	                        	uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_IDENTIFIER+ ParsingConstants.BLANK,
	                                     uploadedOrg.getDisplayIdentifier()+ ParsingConstants.BLANK, true, InvalidTypes.NOT_UNIQUE);
	                            invalidOrgs.add(uploadedOrg);
	                            continue;
                            }
                            else{
                            	Organization createdOrg = orgService.addorUpdateOrganization(uploadedOrg.getOrganization(), oldParentOrgId);
                            	createdOrg.setParentOrgDisplayName(parent.getDisplayIdentifier());
                                organizationDisplayIdentifiers.add(uploadedOrg.getDisplayIdentifier());
                                createdOrgs++;
                                uploadedOrg.setCreated(true);
                                createdOrgsList.add(createdOrg);
                            }

                        } catch (Exception ex) {
                            logger.error(ex.getMessage());
                            uploadedOrg.addInvalidField(FieldName.UPLOAD_ORG_IDENTIFIER + ParsingConstants.BLANK,
                                    uploadedOrg.getIdentifier() + ParsingConstants.BLANK, true, InvalidTypes.ERROR);
                            invalidOrgs.add(uploadedOrg);
                            continue;
                        }
                        
                    }
                }
            }

            logger.debug("uploadOrganizationsFromFile created orgs - " + createdOrgs);
        }

        //TODO try integrating this in to the Validation framework that is where this belongs.
        UploadHelper.convertActualFieldNameToCSVFieldName(invalidOrgs, orgColMap);
        
        returnResult.put("createdOrgs", createdOrgsList);
        returnResult.put("recordsCreatedCount", createdOrgs);
        returnResult.put("recordsRejectedCount", invalidOrgs.size());
        returnResult.put("recordsUpdatedCount", uploadedOrgs.size() - (createdOrgs + invalidOrgs.size())); 
        returnResult.put("totalRecordCount", uploadedOrgs.size());
        
        return returnResult;
	}
	/**
	 * @param personalNeedsProfileRecords
	 * @param userOrganizationTree
	 * @return
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<PersonalNeedsProfileRecord> cascadeAddOrUpdate(
			List<PersonalNeedsProfileRecord> personalNeedsProfileRecords,
			OrganizationTree userOrganizationTree) {
		boolean validInput = true;
		List<? extends StudentSchoolRelationInformation>
		studentSchoolRelations = null;
		if(personalNeedsProfileRecords == null
				|| userOrganizationTree == null
				|| CollectionUtils.isEmpty(personalNeedsProfileRecords)
				|| CollectionUtils.isEmpty(userOrganizationTree.getUserOrganizationTree())) {
			validInput = false;
		}
		if(validInput) {
			logger.debug("Verifying enrollments .");
			studentSchoolRelations = enrollmentService.verifyEnrollments(personalNeedsProfileRecords,
					userOrganizationTree.getUserOrganizationIds());
			logger.debug("Verifying enrollments succeeded.");
		}
		
		if(validInput && studentSchoolRelations != null 
				&& CollectionUtils.isNotEmpty(studentSchoolRelations)) {
			personalNeedsProfileRecords = studentProfileService.addProfileToStudent(
					studentSchoolRelations);
		}
		
		return personalNeedsProfileRecords;
    }
}
