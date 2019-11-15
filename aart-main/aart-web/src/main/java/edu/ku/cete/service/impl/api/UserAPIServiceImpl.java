package edu.ku.cete.service.impl.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.api.UserAPIObject;
import edu.ku.cete.domain.api.UserRolesAPIObject;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.user.OrganizationRoleRequest;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.domain.user.UserRequest;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.UserDao;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserContentAreasService;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.service.api.UserAPIService;
import edu.ku.cete.service.api.exception.APIRuntimeException;
import edu.ku.cete.util.UserStatus;


@Service
public class UserAPIServiceImpl implements UserAPIService {

	@Autowired
	protected UserService userService;
	
	@Autowired
	protected UserContentAreasService userContentAreasService;
	
	@Autowired
	protected OrganizationDao organizationDao;
	
	@Autowired
	protected GroupsService groupsService;
	
	@Autowired
	protected APIDashboardErrorService apiDashboardErrorService;
	
	@Autowired
	protected ContentAreaService contentAreaService;
	
	@Autowired
	protected AppConfigurationService appConfigService;
	
	@Autowired
	protected UserOrganizationsGroupsService userOrganizationsGroupsService;
	
	@Autowired
	protected UserDao userDao;
	
	private Logger logger = LoggerFactory.getLogger(UserAPIServiceImpl.class);
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> postUser(UserAPIObject uao, Long assessmentProgramId, Long createdUserId) throws APIRuntimeException{
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Boolean success = true;
		List<String> messages = new ArrayList<String>();
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		
		Map<String, Object> validateResult =  validateUserAPIObject(uao, assessmentProgramId);
		if(validateResult.containsKey("success") && (Boolean)validateResult.get("success") == true) {
			User existing = userService.getByExternalId(uao.getUniqueId());
			if(existing == null) {
				
				UserRequest userRequest = new UserRequest();
				userRequest.setId(null);
				userRequest.setAction("INSERT");
				
				userRequest = setCommonFields(userRequest, uao, assessmentProgramId, createdUserId);
								
				Map<String, Object> saveResult = saveApiUser(userRequest, uao.getCourseAccreditation(), assessmentProgramId);
				
				if(!saveResult.containsKey("success") || (Boolean)saveResult.get("success") != true) {
					success = false;
					messages.addAll(getUserErrorMessages(saveResult));
					String name = uao.getFirstName() + " " + uao.getLastName();
					List<Organization> orgs = rolesToOrgsList(uao.getRoles());
					List<APIDashboardError> apes = createUserErrorRecords(ApiRequestTypeEnum.POST, ApiRecordTypeEnum.USER, 
							uao.getUniqueId(), name, orgs, createdUserId, messages);
					errors.addAll(apes);
					String message  = CollectionUtils.isNotEmpty(messages) ? messages.get(0) : "";
					throw new APIRuntimeException(message);			
				}else {
					//update educator identifier with row id
					if(saveResult.containsKey("id")) {
						userRequest.setId(Long.parseLong((saveResult.get("id").toString())));
						Long userId = (Long) saveResult.get("id");
						if(userId != null) {
							userRequest.setEducatorIdentifier("ED" + userId);
							saveResult = saveApiUser(userRequest, uao.getCourseAccreditation(), assessmentProgramId);
						}
					}
				}		
			}else {
				success = false;
				messages.add("POST user already exists");
				String name = uao.getFirstName() + " " + uao.getLastName();
				List<Organization> orgs = rolesToOrgsList(uao.getRoles());
				List<APIDashboardError> apes = createUserErrorRecords(ApiRequestTypeEnum.POST, ApiRecordTypeEnum.USER, 
						uao.getUniqueId(), name, orgs, createdUserId, messages);
				errors.addAll(apes);
			}
		}else {
			success = false;
			messages.addAll(getUserErrorMessages(validateResult));
			String name = uao.getFirstName() + " " + uao.getLastName();
			List<Organization> orgs = rolesToOrgsList(uao.getRoles());
			List<APIDashboardError> apes = createUserErrorRecords(ApiRequestTypeEnum.POST, ApiRecordTypeEnum.USER, 
					uao.getUniqueId(), name, orgs, createdUserId, messages);
			errors.addAll(apes);
		}
		
		returnValues.put("success", success);
		returnValues.put("messages", messages);
		returnValues.put("errors", errors);
		return returnValues;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> putUser(UserAPIObject uao, Long assessmentProgramId, Long modifiedUserId) throws APIRuntimeException{
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Boolean success = true;
		List<String> messages = new ArrayList<String>();
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		Map<String, Object> validateResult =  validateUserAPIObject(uao, assessmentProgramId);
		if(validateResult.containsKey("success") && (Boolean)validateResult.get("success") == true) {
			User existing = userService.getByExternalId(uao.getUniqueId());
			if(existing != null) {
				
				UserRequest userRequest = new UserRequest();
				userRequest.setId(existing.getId());
				userRequest.setEducatorIdentifier(existing.getUniqueCommonIdentifier());
				userRequest.setAction("UPDATE");
				
				userRequest = setCommonFields(userRequest, uao, assessmentProgramId, modifiedUserId);

				Map<String, Object> saveResult = saveApiUser(userRequest, uao.getCourseAccreditation(), assessmentProgramId);
				
				if(!saveResult.containsKey("success") || (Boolean)saveResult.get("success") != true) {
					success = false;
					messages.addAll(getUserErrorMessages(saveResult));
					String name = uao.getFirstName() + " " + uao.getLastName();
					List<Organization> orgs = rolesToOrgsList(uao.getRoles());
					List<APIDashboardError> apes = createUserErrorRecords(ApiRequestTypeEnum.PUT, ApiRecordTypeEnum.USER, 
							uao.getUniqueId(), name, orgs, modifiedUserId, messages);
					errors.addAll(apes);
					String message  = CollectionUtils.isNotEmpty(messages) ? messages.get(0) : "";
					throw new APIRuntimeException(message);
				}
				
			}else {
				success = false;
				messages.add("PUT user does not exist");
				String name = uao.getFirstName() + " " + uao.getLastName();
				List<Organization> orgs = rolesToOrgsList(uao.getRoles());
				List<APIDashboardError> apes = createUserErrorRecords(ApiRequestTypeEnum.PUT, ApiRecordTypeEnum.USER, 
						uao.getUniqueId(), name, orgs, modifiedUserId, messages);
				errors.addAll(apes);
			}
		}else {
			success = false;
			messages.addAll(getUserErrorMessages(validateResult));
			String name = uao.getFirstName() + " " + uao.getLastName();
			List<Organization> orgs = rolesToOrgsList(uao.getRoles());
			List<APIDashboardError> apes = createUserErrorRecords(ApiRequestTypeEnum.PUT, ApiRecordTypeEnum.USER, 
					uao.getUniqueId(), name, orgs, modifiedUserId, messages);
			errors.addAll(apes);
		}
		returnValues.put("success", success);
		returnValues.put("messages", messages);
		returnValues.put("errors", errors);
		return returnValues;
	}

	@Override
	public Map<String, Object> deleteUsers(List<UserAPIObject> userAPIObjects, Long assessmentProgramId, Long modifiedUserId) {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Boolean success = true;
		List<String> messages = new ArrayList<String>();
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		for(UserAPIObject uao : userAPIObjects) {
			User existing = userService.getByExternalId(uao.getUniqueId());
			if(existing != null) {
				Long defaultUserGroupId = existing.getDefaultUserGroupsId();
				logger.debug(existing.getIdentifier());
				UserRequest userRequest = new UserRequest();
				userRequest.setId(existing.getId());
				userRequest.setExternalId(uao.getUniqueId());
				userRequest.setAction("UPDATE");
				userRequest.setActiveflag(false);
				userRequest.setEmail(existing.getEmail());
				userRequest.setLastName(existing.getSurName());
				userRequest.setFirstName(existing.getFirstName());
				userRequest.setEducatorIdentifier(existing.getUniqueCommonIdentifier());
				OrganizationRoleRequest[] noOrgs = {};
				userRequest.setOrganizations(noOrgs);
				userRequest.setCreateModifierId(modifiedUserId);
				Long[] assessmentProgram = { assessmentProgramId };
				userRequest.setAssessmentProgramsId(assessmentProgram);
				
				Map<String, Object> saveResult = saveApiUser(userRequest, uao.getCourseAccreditation(), assessmentProgramId);
				if(!saveResult.containsKey("success") || (Boolean)saveResult.get("success") != true) {
					success = false;
					messages.addAll(getUserErrorMessages(saveResult));
					String name = existing.getFirstName() + " " + existing.getSurName();
					List<Organization> orgs = userService.getOrganizations(existing.getId());
					List<APIDashboardError> apes = createUserErrorRecords(ApiRequestTypeEnum.DELETE, ApiRecordTypeEnum.USER, 
							uao.getUniqueId(), name, orgs, modifiedUserId, messages);
					errors.addAll(apes);
				}else {
					try {
						User deletedUser = userService.getByExternalId(uao.getUniqueId());
						deletedUser.setDefaultUserGroupsId(defaultUserGroupId);
						userDao.updateSelectiveByPrimaryKey(deletedUser);
					}catch(Exception e) {
						logger.debug(e.getMessage());
					}
				}
			}else {
				success = true;
				messages.add("DELETE user does not exist");
			}
		}
		
		returnValues.put("success", success);
		returnValues.put("messages", messages);
		returnValues.put("errors", errors);
		return returnValues;
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private Map<String, Object> saveApiUser(UserRequest userRequest, List<String> courseAccreditations, Long assessmentProgramId) {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Boolean success = true;
		List<String> messages = new ArrayList<String>();
		Long newUserId = null;
		
		if(userRequest.getOrganizations() == null) {
			success = false;
			messages.add("No valid organizations for user");
		}else {
			try {
				userRequest = userService.saveUser(userRequest, true, false);
				logger.info(userRequest.getErrorCode());
				if(!StringUtils.isEmpty(userRequest.getErrorCode())) {
					success = false;
					messages.add(userRequest.getErrorCode());
				}else {
					newUserId = userService.getByExternalId(userRequest.getExternalId()).getId();
					Boolean setDefaultSuccess = setDefaultOrgs(newUserId, assessmentProgramId, userRequest.getCreateModifierId());
					logger.debug("setDefaultUser successful: " + setDefaultSuccess);
					Boolean validContentAreas = userContentAreasService.setUserContentAreas(newUserId, courseAccreditations, userRequest.getCreateModifierId());
					if(!validContentAreas) {
						success = false;
						messages.add("Course Accreditations not valid.");
					}
				}
			} catch (ServiceException e) {
				logger.error("Could not save user");
				e.printStackTrace();
				success = false;
				messages.add("Unknown error saving user");
			}
		}
		if(newUserId != null) {
			returnValues.put("id", newUserId);
		}
		returnValues.put("success", success);
		returnValues.put("messages", messages);
		return returnValues;
	}
	
	@Override
	public Map<String, Object> validateUserAPIObject(UserAPIObject userAPIObject, Long assessmentProgramId) {
		Map<String, Object> returnValues = new HashMap<String, Object>();
		Boolean success = true;
		List<String> messages = new ArrayList<String>();
		
		if(StringUtils.isEmpty(userAPIObject.getRecordType()) 
				|| !StringUtils.equalsIgnoreCase(userAPIObject.getRecordType(), "user")) {
			success = false;
			messages.add("Record type is missing or not valid.");
		}
		if(StringUtils.isEmpty(userAPIObject.getUniqueId()) || StringUtils.length(userAPIObject.getUniqueId()) > 30) {
			success = false;
			messages.add("Unique ID is missing or not valid.");
		}
		if(StringUtils.isEmpty(userAPIObject.getLastName()) || StringUtils.length(userAPIObject.getLastName()) > 30) {
			success = false;
			messages.add("Last Name is missing or not valid.");
		}
		if(StringUtils.isEmpty(userAPIObject.getFirstName()) || StringUtils.length(userAPIObject.getFirstName()) > 30) {
			success = false;
			messages.add("First Name is missing or not valid.");
		}
		if(StringUtils.isEmpty(userAPIObject.getEmail()) || StringUtils.length(userAPIObject.getEmail()) > 250) {
			success = false;
			messages.add("Email is missing or not valid.");
		}
		if(userAPIObject.getRoles() == null || userAPIObject.getRoles().size() < 1) {
			success = false;
			messages.add("No roles provided for user.");
		}else if( userAPIObject.getRoles().size() > 0) {
			for(UserRolesAPIObject urao : userAPIObject.getRoles()) {
				if(StringUtils.isEmpty(urao.getOrganizationId()) || StringUtils.length(urao.getOrganizationId()) > 30) {
					success = false;
					messages.add("Role does not include organization.");
				}
				else if(StringUtils.isEmpty(urao.getRole()) || StringUtils.length(urao.getRole()) > 30) {
					success = false;
					messages.add("Role does not include role code.");
				}else {
					List<String> codesList = new ArrayList<String>();
					List<AppConfiguration> allowedGroupCodes = appConfigService.selectByAttributeTypeAndAssessmentProgramId("api-allowed-groupcodes", assessmentProgramId);
					if (CollectionUtils.isNotEmpty(allowedGroupCodes)) {
						String codesStr = allowedGroupCodes.get(0).getAttributeValue();
						logger.info("allowed api roles: " + codesStr);
						String[] splitStr = codesStr.split(",");
						for(String str : splitStr) {
							codesList.add(str);
						}
					}
					
					if(CollectionUtils.isEmpty(codesList)) {
						success = false;
						messages.add("Error getting Roles configuration.");
					}else {
						List<Groups> groups = groupsService.getGroupsByGroupCode(urao.getRole());
						if(CollectionUtils.isNotEmpty(groups)) {
							for(Groups group : groups) {
								Organization org = organizationDao.getOrgByExternalID(urao.getOrganizationId());
								if(org == null) {
									success = false;
									messages.add("Organization " + urao.getOrganizationId() + " is not found.");
								}else {
									if(codesList.stream().noneMatch(str -> str.trim().equals(urao.getRole()))) {
										success = false;
										messages.add("Role " + urao.getRole() + " is not allowed.");
									}else if(!org.getOrganizationType().getTypeCode().equals(group.getOrganizationType().getTypeCode())) {
										logger.debug("org is a " + org.getTypeName() + " and group is for " + group.getOrganizationType().getTypeName());
										success = false;
										messages.add("Organization provided is a " + org.getTypeName() + " and role provided is for a " + group.getOrganizationType().getTypeName() + ".");
									}
								}
							}
						}else {
							success = false;
							messages.add("Role " + urao.getRole() + " does not exist.");
						}
					}
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(userAPIObject.getCourseAccreditation())) {
			List<String> cas = userAPIObject.getCourseAccreditation();
			for(String caAbvr :  cas) {
				ContentArea ca = contentAreaService.findByAbbreviatedName(caAbvr);
				if(ca == null) {
					success = false;
					messages.add("Course Accreditation is not valid for course " + caAbvr);
				}
			}
		}
		returnValues.put("success", success);
		returnValues.put("messages", messages);
		return returnValues;
	}
	
	private Boolean setDefaultOrgs(Long userId, Long assessmentProgramId, Long modifiedUser) {
		
			User user = userService.get(userId);			
			List<UserOrganizationsGroups> userOrgGroups = userOrganizationsGroupsService.getUserOrganizationsGroupsByUserId(userId);
			if(CollectionUtils.isEmpty(userOrgGroups)){
				return false;
			}else {
				UserOrganizationsGroups userOrgGroup = userOrgGroups.get(0);

				Long userOrgGroupId = userOrgGroup.getId();
	
				user.setDefaultUserGroupsId(userOrgGroupId);
				user.setModifiedUser(modifiedUser);
				user.setAssessmentProgramId(assessmentProgramId);
	
				try {
					userDao.updateSelectiveByPrimaryKey(user);
					userService.updateUserAssessmentProgramToDefault(user, userOrgGroup.getOrganizationId(), userOrgGroup.getGroupId(), true, userOrgGroupId);
				}catch (Exception e) {
					logger.debug(e.getMessage());
					return false;
				}
				userDao.resetDefaultOrganization(userId, modifiedUser);
				
				List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService.getUserOrganizationsGroupsByUserId(userId);
				for(UserOrganizationsGroups uog : userOrganizationsGroups) {
					UserOrganizationsGroups userGroup = userOrganizationsGroupsService.
		                    getUserOrganizationsGroups(uog.getId()); 
		            userGroup.setStatus(UserStatus.ACTIVE);
		            userGroup.setCurrentContextUserId(userGroup.getUserId());
		            userGroup.setCreatedUser(modifiedUser);
		            userGroup.setModifiedUser(modifiedUser);
		            userOrganizationsGroupsService.updateUserOrganizationsGroups(userGroup);
				}
			}
			return true;
	}
	
	private UserRequest setCommonFields(UserRequest userRequest, UserAPIObject uao, 
			Long assessmentProgramId, Long modifiedUserId) {
		
		userRequest.setExternalId(uao.getUniqueId());
		userRequest.setLastName(uao.getLastName());
		userRequest.setFirstName(uao.getFirstName());
		userRequest.setEmail(uao.getEmail());
		userRequest.setDefaultAssessmentProgramId(assessmentProgramId);
		
		if(uao.getActive() != null && uao.getActive() == false) {
			userRequest.setActiveflag(false);
		}else {
			userRequest.setActiveflag(true);
		}
		userRequest.setCreateModifierId(modifiedUserId);
		Long[] assessmentProgram = { assessmentProgramId };
		userRequest.setAssessmentProgramsId(assessmentProgram);
		
		OrganizationRoleRequest[] organizations = setOrgRoleRequest(uao.getRoles(), assessmentProgramId);
		userRequest.setOrganizations(organizations);
		
		return userRequest;
	}
	
	private OrganizationRoleRequest[] setOrgRoleRequest(List<UserRolesAPIObject> roles, Long assessmentProgramId) {
				
		List<OrganizationRoleRequest> orgRoles = new ArrayList<OrganizationRoleRequest>();
		
		if(roles == null) {
			OrganizationRoleRequest[] noOrgs = {};
			return noOrgs;
		}
		
		for(UserRolesAPIObject role : roles) {
			OrganizationRoleRequest orgRole = new OrganizationRoleRequest();
			
			Organization org = organizationDao.getOrgByExternalID(role.getOrganizationId());
			
			orgRole.setOrganizationId(org.getId());
			
			List<Groups> groups = groupsService.getGroupsByGroupCode(role.getRole());
			
			if(groups.size() > 0) {
				Long roleId = groups.get(0).getGroupId();
			
				Long[] rolesIds = { roleId };
				orgRole.setRolesIds(rolesIds);
				
				orgRole.setAssessmentProgramId(assessmentProgramId);
				
				orgRoles.add(orgRole);
			}
		}
		
		OrganizationRoleRequest[] organizations = orgRoles.toArray(new OrganizationRoleRequest[orgRoles.size()]);
		return organizations;
	}
	
	@Override
	public List<APIDashboardError> createUserErrorRecords(ApiRequestTypeEnum requestType, ApiRecordTypeEnum recordType,
			String externalId, String name, List<Organization> orgs, Long createModifyId, List<String> messages){
		List<APIDashboardError> apes = new ArrayList<APIDashboardError>();
		if(CollectionUtils.isNotEmpty(orgs)) {
			for(Organization org : orgs) {
				for(String message : messages) {
					APIDashboardError ape = apiDashboardErrorService.buildDashboardError(
							requestType, recordType, externalId, name, org, null, createModifyId, message);
					apes.add(ape);
				}
			}
		}else {
			for(String message : messages) {
				APIDashboardError ape = apiDashboardErrorService.buildDashboardError(
						requestType, recordType, externalId, name, null, null, createModifyId, message);
				apes.add(ape);
			}
		}
			
		return apes;
	}
	
	@Override
	public List<Organization> rolesToOrgsList(List<UserRolesAPIObject> roles){
		List<Organization> orgs = new ArrayList<Organization>();
		if(CollectionUtils.isNotEmpty(roles)){
			for(UserRolesAPIObject role : roles) {
				Organization org = organizationDao.getOrgByExternalID(role.getOrganizationId());
				orgs.add(org);
			}
		}
		return orgs;
	}
	
	/*
	private List<Organization> rolesRequestToOrgsList(OrganizationRoleRequest[] roles){
		List<Organization> orgs = new ArrayList<Organization>();
		
		for(OrganizationRoleRequest role : roles) {
			Organization org = organizationDao.get(role.getOrganizationId());
			orgs.add(org);
		}
		
		return orgs;
	}
	*/
	
	@Override
	public List<String> getUserErrorMessages(Map<String, Object> map){
		List<String> messages = new ArrayList<String>();
		
		if(map.containsKey("messages")) {
			try {
				@SuppressWarnings("unchecked")
				List<String> mapMessages = (List<String>)map.get("messages");
				/*logger.debug(mapMessages.toArray().toString());
				for(String message : mapMessages) {
					logger.debug(message);
					messages.add(message);
				}*/
				messages.addAll(mapMessages);
			}catch(Exception e) {
				logger.info("unable to get messages from object");
				messages.add("Unexpected error.");
			}
		}else if(map.containsKey("message")) {
			messages.add(map.get("message").toString());
		}
		
		return messages;
	}
		
}
