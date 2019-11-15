package edu.ku.cete.service.impl.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.UserDao;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.SubscoresDescriptionService;
 

/**
 * Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(User)
 */
@SuppressWarnings("unused")
@Service
public class UserUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupsDao groupsDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AssessmentProgramDao assessmentProgramDao;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Value("${userupload.excludedUserRoles}")
	private String excludedUserRoles;
	
	final static Log logger = LogFactory.getLog(UserUploadCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		UploadedUser uploadedUser = (UploadedUser) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long stateId = (Long) params.get("stateId");
		Long districtId = (Long) params.get("districtId");
		Long schoolId = (Long)params.get("schoolId");
		
		String lineNumber = uploadedUser.getLineNumber();
		
		boolean validationPassed = true;
		String organizationTypeCode = "";
		
		Long parentOrgId = (Long)params.get("selectedOrgId");
		Long organizationId  = parentOrgId;
		User existingUser = null;
		Organization org = null;
		UserDetailImpl currentUser = null;
		ContractingOrganizationTree contractingOrganizationTree  =null;
		boolean isInsert = true;
		/**
		 * US16239 : Global System Administrator and PD Admin need to exclude from user upload  
		 * */
		if( ! isAllowedRoles(uploadedUser) ){
			addInvalidMsg(uploadedUser,mappedFieldNames,validationErrors,lineNumber);
			validationPassed = false;
		}
		else{
			contractingOrganizationTree = (ContractingOrganizationTree)params.get("contractingOrganizationTree");
			currentUser =  (UserDetailImpl)params.get("currentUser");
			
			org = contractingOrganizationTree
					.getUserOrganizationTree().getOrganization(
							uploadedUser.getDisplayIdentifier());
			
			uploadedUser = userService.isValidUserIdentifierAndOrg(uploadedUser, org,
					contractingOrganizationTree, parentOrgId,false);
			
			if (uploadedUser.isInValid() || uploadedUser.isDoReject()) {
				addInvalidMsg(uploadedUser,mappedFieldNames,validationErrors,lineNumber);
				validationPassed = false;
			}
			else{
				//existingUser = userService.getByEmail(uploadedUser.getEmail());
				/**
				 * DE10344: 
				 */
				existingUser = userService.getByEmailorUserName(uploadedUser.getEmail());
				String educatorIdentifier = uploadedUser.getEducatorIdentifier();
				if( educatorIdentifier != null 
						&& educatorIdentifier.trim().length() > 0 && uploadedUser.getExistingUser() != null ){
					
					/**
					 * Checks whether the educator identifier/unique common identifier exist for other user in the organization
					 */
					if( existingUser != null && ! existingUser.getId().equals(uploadedUser.getExistingUser().getId()) )
					{
						String errMsg = new StringBuilder("User with same ").append(mappedFieldNames.get("educatorIdentifier")).append(" ").append(uploadedUser.getEmail()).append(" was found in the system. ").toString();
						validationErrors.rejectValue("educatorIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("educatorIdentifier")}, errMsg);
						validationPassed = false;
					}
					else if( existingUser == null )
						existingUser  = uploadedUser.getExistingUser();
				}
				if (existingUser != null && existingUser.getId() > 0 ) { //&& existingUser.getActiveFlag() == true
					
					isInsert = false;
					
					if( ! StringUtils.isNotBlank(uploadedUser.getFirstName())  )
					{
						uploadedUser.setFirstName(null);
					}
					if( ! StringUtils.isNotBlank(uploadedUser.getLastName())  )
					{
						uploadedUser.setLastName(null);
					}
					if( StringUtils.isNotBlank(uploadedUser.getEmail())  )
					{
						uploadedUser.setUserName(uploadedUser.getEmail());
					}
					if( ! StringUtils.isNotBlank(educatorIdentifier)  )
					{
						uploadedUser.setEducatorIdentifier(null);
					}
					
					//changed to not accepting empty educator identifier if already educator identifier is present in db..
					/*if( ! StringUtils.isNotBlank(educatorIdentifier)  && existingUser.getUniqueCommonIdentifier() != null &&  StringUtils.isNotBlank(existingUser.getUniqueCommonIdentifier()))
					{
						String errMsg = new StringBuilder("Given User with").append(" ").append(uploadedUser.getEmail()).append(" already having educator identifier so  ").append(mappedFieldNames.get("educatorIdentifier")).append(" can not be empty").toString();
						validationErrors.rejectValue("educatorIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("educatorIdentifier")}, errMsg);
						validationPassed = false;
					}else if( ! StringUtils.isNotBlank(educatorIdentifier)  && existingUser.getUniqueCommonIdentifier() == null &&  !StringUtils.isNotBlank(existingUser.getUniqueCommonIdentifier())){
						uploadedUser.setEducatorIdentifier(null);
					}*/
					
					
					/*String errMsg = new StringBuilder("User with email / user name ").append(uploadedUser.getEmail()).append(" was found in the system. ").toString();
					validationErrors.rejectValue("email", "", new String[]{lineNumber, mappedFieldNames.get("email")}, errMsg);
					validationPassed = false;*/
				}else{
					/*uploadedUser = userService.isValidUserIdentifierAndOrg(uploadedUser, org,
											contractingOrganizationTree, parentOrgId,true);
					
					if (uploadedUser.isInValid() || uploadedUser.isDoReject()) {
						addInvalidMsg(uploadedUser,mappedFieldNames,validationErrors,lineNumber);
						validationPassed = false;
					}
					else{*/
					if( ! StringUtils.isNotBlank(uploadedUser.getFirstName())  ){
						validationErrors.rejectValue("firstName", "", 
								new String[]{lineNumber, mappedFieldNames.get("firstName")},
								"Value in field (" + mappedFieldNames.get("firstName")+ ") must be valid.");
						validationPassed = false;
					}
					if( ! StringUtils.isNotBlank(uploadedUser.getLastName())  ){
						validationErrors.rejectValue("lastName", "", 
								new String[]{lineNumber, mappedFieldNames.get("lastName")},
								"Value in field (" + mappedFieldNames.get("lastName")+ ") must be valid.");
						validationPassed = false;
					}
					if( ! StringUtils.isNotBlank(uploadedUser.getPrimaryRole())  ){
						validationErrors.rejectValue("primaryRole", "", 
								new String[]{lineNumber, mappedFieldNames.get("primaryRole")},
								"Value in field (" + mappedFieldNames.get("primaryRole")+ ") must be valid.");
						validationPassed = false;
					}
					if( ! StringUtils.isNotBlank(uploadedUser.getPrimaryAssessmentProgram())  ){
						validationErrors.rejectValue("primaryAssessmentProgram", "", 
								new String[]{lineNumber, mappedFieldNames.get("primaryAssessmentProgram")},
								"Value in field (" + mappedFieldNames.get("primaryAssessmentProgram")+ ") must be valid.");
						validationPassed = false;
					}
				}
			}
			if(StringUtils.isNotBlank(uploadedUser.getPrimaryRole()) 
					&& StringUtils.equalsIgnoreCase(uploadedUser.getPrimaryRole(),"TEA")
					&& !StringUtils.isNotBlank(uploadedUser.getEducatorIdentifier())){
				      validationErrors.rejectValue("primaryRole", "", 
				        new String[]{lineNumber, mappedFieldNames.get("educatorIdentifier")},
				        "Educator Identifier is not specified and is required for a Teacher role.");
				      validationPassed = false;
			}
			if(StringUtils.isNotBlank(uploadedUser.getSecondaryRole()) 
					&& StringUtils.equalsIgnoreCase(uploadedUser.getSecondaryRole(),"TEA") 
					&& !StringUtils.isNotBlank(uploadedUser.getEducatorIdentifier())){
				      validationErrors.rejectValue("secondaryRole", "", 
				        new String[]{lineNumber, mappedFieldNames.get("educatorIdentifier")},
				        "Educator Identifier is not specified and is required for a Teacher role");
				      validationPassed = false;
			}
		}
		if(validationPassed){
			List<Groups> groupsList = (List<Groups>)params.get("groupsList");
			userService.validatePrimarySecondaryRole(groupsList,uploadedUser,isInsert);
			if (uploadedUser.isInValid() || uploadedUser.isDoReject()) {
				addInvalidMsg(uploadedUser, mappedFieldNames, validationErrors, lineNumber);
				validationPassed = false;
			}
			else{
				List<Groups> allowedRoles = (List<Groups>)params.get("allowedRoles");
				userService.validatePrimarySecondaryRole(allowedRoles,uploadedUser,isInsert);
				if (uploadedUser.isInValid() || uploadedUser.isDoReject()) {
					String fieldName = uploadedUser.getInValidDetails().get(0).getActualFieldName();
					validationErrors.rejectValue(fieldName, "", 
							new String[]{lineNumber, mappedFieldNames.get(fieldName)},
							"The uploading User must have a higher role in the organizational hierarchy than those on the upload file");
					validationPassed = false;
				}
				else{	
						if( uploadedUser.getPrimaryGroups() != null ){
							if( uploadedUser.getPrimaryGroups().getOrganizationTypeId() != org.getOrganizationTypeId() ){
								validationErrors.rejectValue("primaryRole", "", 
										new String[]{lineNumber, mappedFieldNames.get("primaryRole")},
										uploadedUser.getPrimaryRole() + " is not applicable for selected organization. ");
								validationPassed = false;
							}
						}
						if( uploadedUser.getSecondaryGroups() != null ){
							if( uploadedUser.getSecondaryGroups().getOrganizationTypeId() != org.getOrganizationTypeId() ){
								validationErrors.rejectValue("secondaryRole", "", 
										new String[]{lineNumber, mappedFieldNames.get("secondaryRole")},
										uploadedUser.getSecondaryRole() + " is not applicable for selected organization. ");
								validationPassed = false;
							}
						}
						AssessmentProgram assessmentProgram = null;
						if(StringUtils.isNotBlank(uploadedUser.getPrimaryAssessmentProgram())  ){
							assessmentProgram = assessmentProgramDao.findByAbbreviatedName(uploadedUser.getPrimaryAssessmentProgram());
						}
						if(assessmentProgram == null){
							validationErrors.rejectValue("primaryAssessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("primaryAssessmentProgram")},
									" not valid");
							validationPassed = false;
						}
						if(validationPassed ){
							// Per US18825 & US18927
							
							List<Organization> parents = organizationService.getAllParents(organizationId);
					    	Long userstateId = null;
							//in the case the user is a state level user add the state
							parents.add(organizationService.get(organizationId));
							for (Organization organization : parents){
								if (organization.getOrganizationType().getTypeCode().equals("ST")){
									userstateId = organization.getId();
									break;
								}
							}
							
							if(("DTC".equalsIgnoreCase(uploadedUser.getPrimaryRole())) || ("DTC".equalsIgnoreCase(uploadedUser.getSecondaryRole()))){
								String groupCode = "DTC";
								Boolean isRestrictedToSingleUser = userDao.getIsRestrictedToSingleUser(groupCode,userstateId, assessmentProgram.getId());
								if(isRestrictedToSingleUser == null){
									// If there is no entry in restrictions then it defaults to multiple users exists per role.
									isRestrictedToSingleUser = new Boolean(false);
								}
								if( isRestrictedToSingleUser
									&& userDao.getIsSingleUserExists(groupCode, org.getId(), assessmentProgram.getId(), "DT", existingUser.getId())){
									validationErrors.rejectValue("primaryRole", "", new String[]{lineNumber, mappedFieldNames.get("primaryRole")},
											"DTC limited to one user");
									validationPassed = false;
								}
							}
							
							if(("BTC".equalsIgnoreCase(uploadedUser.getPrimaryRole())) || ("BTC".equalsIgnoreCase(uploadedUser.getSecondaryRole()))){
								String groupCode = "BTC";
								Boolean isRestrictedToSingleUser = userDao.getIsRestrictedToSingleUser(groupCode,userstateId, assessmentProgram.getId());
								if(isRestrictedToSingleUser == null){
									// If there is no entry in restrictions then it defaults to multiple users exists per role.
									isRestrictedToSingleUser = new Boolean(false);
								}
								if( isRestrictedToSingleUser
										&& userDao.getIsSingleUserExists(groupCode, org.getId(), assessmentProgram.getId(), "SCH", existingUser.getId())){
									validationErrors.rejectValue("primaryRole", "", new String[]{lineNumber, mappedFieldNames.get("primaryRole")},
											"BTC limited to one user");
									validationPassed = false;
								}
							}
							
							if(("TEA".equalsIgnoreCase(uploadedUser.getPrimaryRole()) || "TEAR".equalsIgnoreCase(uploadedUser.getPrimaryRole())) && ("TEA".equalsIgnoreCase(uploadedUser.getSecondaryRole()) || "TEAR".equalsIgnoreCase(uploadedUser.getSecondaryRole()))){
								validationErrors.rejectValue("primaryRole", "", 
										new String[]{lineNumber, mappedFieldNames.get("primaryRole")},
										"Both Teacher and Teacher:PNP Read Only role can't be assigned to a user");
								validationPassed = false;
								
							}
							if( !isInsert && validationPassed ){							
								if("TEA".equalsIgnoreCase(uploadedUser.getPrimaryRole()) || "TEAR".equalsIgnoreCase(uploadedUser.getPrimaryRole()) || "TEAR".equalsIgnoreCase(uploadedUser.getSecondaryRole()) || "TEA".equalsIgnoreCase(uploadedUser.getSecondaryRole())){
								  List<Groups> uploadedUserGroups = groupsDao.getUserRolesByUserId(existingUser.getId());
									if(uploadedUserGroups != null && uploadedUserGroups.size()>0){
									    for (Groups group : uploadedUserGroups) {
									    	if((long)org.getId() == (long)group.getOrganizationTypeId()){
									    		
									    		if(StringUtils.isNotEmpty(group.getGroupCode())){
									    		if((!group.getGroupCode().equalsIgnoreCase(uploadedUser.getPrimaryRole())) && (!group.getGroupCode().equalsIgnoreCase(uploadedUser.getSecondaryRole()))){
										    		if("TEA".equalsIgnoreCase(group.getGroupCode()) || "TEAR".equalsIgnoreCase(group.getGroupCode())){
										    			validationErrors.rejectValue("primaryRole", "", 
																new String[]{lineNumber, mappedFieldNames.get("primaryRole")},
																"Both Teacher and Teacher:PNP Read Only role can't be assigned to a user");
														validationPassed = false;
										    		}
										    	
										    	}   	
										    	
									    		} else {
									    			validationErrors.rejectValue("primaryRole", "", 
															new String[]{lineNumber, mappedFieldNames.get("primaryRole")},
															"Both Teacher and Teacher:PNP Read Only role can't be assigned to a user");
									    			validationPassed = false;
									    			break;
									    		}
									    	}
									    }
									}
								}
							}
						uploadedUser.setOrganizationId(org.getId()); 
						if( isInsert || StringUtils.isNotBlank(uploadedUser.getPrimaryAssessmentProgram())  ){
							userService.validateUserAssessmentProgram(uploadedUser);
							if (uploadedUser.isInValid() || uploadedUser.isDoReject()) {
								addInvalidMsg(uploadedUser,mappedFieldNames,validationErrors,lineNumber);
								validationPassed = false;
							}
						}
					}	
				}
			}
		}
		if(validationPassed){ 
			/*uploadedUser.setOrganizationId(organizationId); 
			uploadedUser.setOrganizationTypeCode(organizationTypeCode);*/
			uploadedUser.setExistingUser(existingUser);
			uploadedUser.setCurrentContext(org);
			uploadedUser.setContractingOrganizationTree(contractingOrganizationTree);
			uploadedUser.setLoggedinUser(currentUser);
		}
 		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", uploadedUser);
		return customValidationResults;
	}

	private void addInvalidMsg(UploadedUser uploadedUser,Map<String, String> mappedFieldNames,BeanPropertyBindingResult validationErrors,String lineNumber){
		for( InValidDetail inValidDetail  : uploadedUser.getInValidDetails() ){
			String errMsg = new StringBuilder(inValidDetail.getFormattedFieldValue()).append(" is ").append(inValidDetail.getInvalidType().name().toLowerCase().replaceAll("_", " ")).toString();
			String fieldName = inValidDetail.getActualFieldName();
			validationErrors.rejectValue(fieldName, "", new String[]{lineNumber, mappedFieldNames.get(fieldName)}, errMsg);
		}
	}
	
	private boolean isAllowedRoles(UploadedUser uploadedUser){
		String [] excludeRoles = excludedUserRoles.split(",");
		
		boolean valid = true;
		for(String excludeRole : excludeRoles)
		{
			if( excludeRole.equalsIgnoreCase(uploadedUser.getPrimaryRole()) )
			{
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_PRIMARY_ROLE_IDENTIFIER.toString(),
						excludeRole, true,
						InvalidTypes.NOT_ALLOWED);
				valid = false;
			}
			if( excludeRole.equalsIgnoreCase(uploadedUser.getSecondaryRole()) ){
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_SECONDARY_ROLE_IDENTIFIER.toString(),
						excludeRole, true,
						InvalidTypes.NOT_ALLOWED);
				valid = false;
			}
		}
		return valid;
	}

}
