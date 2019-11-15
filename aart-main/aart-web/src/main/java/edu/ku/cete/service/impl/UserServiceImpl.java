package edu.ku.cete.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.OrganizationTree;
import edu.ku.cete.domain.UserAssessmentPrograms;
import edu.ku.cete.domain.UserSecurityAgreement;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.user.OrganizationRoleRequest;
import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganization;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.domain.user.UserPasswordReset;
import edu.ku.cete.domain.user.UserPasswordResetCriteria;
import edu.ku.cete.domain.user.UserRecord;
import edu.ku.cete.domain.user.UserRequest;
import edu.ku.cete.domain.user.UserRoles;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.OrgAssessmentProgramDao;
import edu.ku.cete.model.UserAssessmentProgramsMapper;
import edu.ku.cete.model.UserDao;
import edu.ku.cete.model.UserOrganizationsGroupsDao;
import edu.ku.cete.model.UserSecurityAgreementMapper;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.mapper.UserPasswordResetMapper;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.UserAuditTrailHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.EventNameForAudit;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.UserStatus;
import edu.ku.cete.web.DLMPDTrainingDTO;
import edu.ku.cete.web.ScorersAssignScorerDTO;
import edu.ku.cete.web.UserDTO;


/**
 * @author nicholas.studt
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UserServiceImpl implements UserService {

	/** Generated serialVersionUID. */
	private static final long serialVersionUID = 6729294708668693990L;

	/**
	 * Logger for class.
	 */
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	/** UserDao holder. */
	@Autowired
	private UserDao userDao;

	@Autowired
	private UserOrganizationsGroupsDao userOrganizationsGroupsDao;

	@Autowired
	private UserOrganizationsGroupsService userOrganizationsGroupsService;

	@Autowired
	private GroupsDao groupsDao;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthoritiesDao authoritiesDao;

	@Autowired
	private OrganizationTypeService orgTypeService;
	 
	@Autowired
	private UserAuditTrailHistoryMapper userAuditTrailHistoryMapperDao;
	
	@Autowired
	private GroupsService groupsService;

	@Autowired
	private UserPasswordResetMapper userPasswordResetDao;

	@Autowired
	private UserSecurityAgreementMapper userSecurityAgreementDao;
    
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserPasswordReset userPasswordResetObj;

	 @Autowired
	 private OrgAssessmentProgramDao orgAssessProgDao;
	 
	 @Autowired
	 private UserAssessmentProgramsMapper userAssessProgDao;
	
	 @Autowired
	 private AssessmentProgramDao assessProgDao;

	 @Autowired
	 private RosterService rosterService;
	 
	 @Autowired
	 private RosterDao rosterDao;
	 
	 @Autowired
	 private DomainAuditHistoryMapper domainAuditHistoryDao;
	 

	 
	 @Autowired
	 private UserSecurityAgreementMapper userSecurityAgreementMapper;
	 
	 @Autowired
	 private AssessmentProgramService assessmentProgramService;
	/**
	 * ceteAuthorityName.
	 */
	@Value("${ceteAuthorityName}")
	private String ceteAuthorityName;
	/*
	 * limit the size of an in clause being sent to query
	 */
	@Value("${in.clause.split.limit}")
	private int inClauseLimit;

	/**Added By Prasanth:
	 * Story:US16352:
	 */
	@Value("${user.activation.period}")
	private int numActivationDays;
	/**
	 * @return the userDao
	 */
	public final UserDao getUserDao() {
		return userDao;
	}
	
	/**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
	@Value("${user.Role}")
	private String userRole;
	

	@Value("${user.internalusers.emaildomains}")
	private String internalUsersDomains;
	
	/**
	 * @param newUserDao
	 *            The userDao to set.
	 */
	public final void setUserDao(final UserDao newUserDao) {
		this.userDao = newUserDao;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User add(User toAdd) {
		toAdd.setPassword(passwordEncoder.encodePassword(toAdd.getPassword(),
				toAdd.getSalt()));
		if (StringUtils.isNotEmpty(toAdd.getUserName())) {
			toAdd.setUserName(toAdd.getUserName().toLowerCase());
		}
		if (StringUtils.isNotEmpty(toAdd.getEmail())) {
			toAdd.setEmail(toAdd.getEmail().toLowerCase());
		}
		userDao.add(toAdd);
		return toAdd;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User register(User toAdd, long organizationId)
			throws ServiceException {

		User user1 = userDao.getByUserName("cetesysadmin");
		toAdd.setUserName(toAdd.getEmail());
		toAdd.setDisplayName(toAdd.getFirstName() + " "+toAdd.getSurName());
		Date today = new Date();
		toAdd.setCreatedDate(today);
		toAdd.setModifiedDate(today);
		toAdd.setCreatedUser(user1.getId());
		toAdd.setModifiedUser(user1.getId());
		toAdd.setPassword(passwordEncoder.encodePassword(toAdd.getPassword(),
				toAdd.getSalt()));
		if (StringUtils.isNotEmpty(toAdd.getUserName())) {
			toAdd.setUserName(toAdd.getUserName().toLowerCase());
		}
		if (StringUtils.isNotEmpty(toAdd.getEmail())) {
			toAdd.setEmail(toAdd.getEmail().toLowerCase());
		}
		toAdd.setActiveFlag(false);
		userDao.add(toAdd);
		addUserToOrganization(toAdd, organizationId, user1.getId(), true);

		UserOrganizationsGroups userGroup = new UserOrganizationsGroups();
		Groups grp = groupsService.getGroupByName("PD User");
		if (grp != null) {
			userGroup.setGroupId(grp.getGroupId());
			userGroup.setUserId(toAdd.getId());
			userGroup.setOrganizationId(organizationId);
			userGroup.setIsdefault(true);

			userGroup.setStatus(UserStatus.ACTIVE);
			userOrganizationsGroupsService
					.addUserOrganizationsGroups(userGroup);
		}
		emailService.sendNewUserRegistration(toAdd);
		return toAdd;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final boolean delete(final Long id) {
		if (userDao.delete(id) == 1) {
			return true;
		}
		return false;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User get(final Long userId) {
		return userDao.get(userId);
	}

	/**
	 * Biyatpragyan Mohanty : US11240 : User Management ViewEdit User Get User
	 * details, add organization hierarchies.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User getAllDetails(Long userId) {
		User user = userDao.get(userId);
		user.setOrganizations(userDao.getOrganizations(userId));
		user.setGroupsList(userDao.getGroups(userId));
		List<Organization> orgs = user.getOrganizations();
		if(orgs != null && orgs.size() > 0){
			for(Organization org : orgs){
				List<Organization> hierarchy = userDao
						.getOrganizationHierarchy(org.getId());
				org.setHierarchy(hierarchy);
			}
		}

		return user;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Organization> getOrganizations(final Long userId) {
		return userDao.getOrganizations(userId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Groups> getGroups(final Long userId) {
		return userDao.getGroups(userId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User getByUserName(final String userName) {
		return getByUserNameIncludeSuperUser(userName, false);
	}
	
	
	public final void setITIStatusForCurrentAssementProgram(User user) {
		boolean iapNavStatus = false;
		boolean iapModStatus = false;
		if(user!=null) { 
			if(user.getCurrentAssessmentProgramName() != null && user.getCurrentAssessmentProgramName().equalsIgnoreCase("DLM")) {
				Long stateId = organizationService.getStateIdByUserOrgId(user.getCurrentOrganizationId());
				List<OrganizationDetail> stateDetail = organizationService.getOrganizationDetailByOrgId(stateId, null);
				if(CollectionUtils.isNotEmpty(stateDetail)){
					for (OrganizationDetail od : stateDetail) {
						Date now = new Date();
						Date windowEnd = od.getWindowExpiryDate();
						if (now.before(windowEnd)) { // can view IAP as long as there is a window that expires later than now
							iapNavStatus = true;
						}
						Date start = od.getItiStartDate();
						Date end = od.getItiEndDate();
						if(start != null && end != null){
							Calendar c = Calendar.getInstance();
							c.setTime(end);
							c.add(Calendar.DATE, 1);
							end = c.getTime();
							if (now.after(start) && now.before(end)) {
								iapModStatus = true;
							}
						}
					}
				}
			}
			user.setIAPNavigationStatus(iapNavStatus);
			user.setIAPModificationStatus(iapModStatus);
		}
	}
	
	/**
	 * @param userName
	 *            {@link String}
	 * @return {@link User}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User getByUserNameIncludeSuperUser(final String userName, boolean superUser) {
		// TODO this is getting called for every single navigation across
		// pages.Remove it
		User user = userDao.getByUserName(userName);
		if (user != null) {
		  if(!user.isSystemIndicator()){
			List<Organization> organizations = userDao.getOrganizations(user
					.getId());
			if (organizations != null && CollectionUtils.isNotEmpty(organizations)) {
				user.setOrganizations(organizations);
			}
			List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService
					.getUserOrganizationsGroupsByUserId(user.getId());
			
			List<Groups> groups = new ArrayList<Groups>();

			// populate groups
			for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
				if (!groups.contains(userGroup.getGroup())) {
					Groups g = userGroup.getGroup();
					g.setDefaultGroup(userGroup.isIsdefault());
					groups.add(g);
				}
			}

			// populate orgs
			for (Organization organization : organizations) {
				organization.setGroupsList(new ArrayList<Groups>());
				for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
					if (userGroup.getOrganizationId().equals(organization.getId())) {
						userGroup.setOrganization(organization);
						organization.setDefaultOrg(userGroup.isUserOrganizationDefault());
						organization.getGroupsList().add(userGroup.getGroup());
						if (userGroup.isUserOrganizationDefault()) {
							user.setDefaultOrganizationId(organization.getId());
							user.setCurrentOrganizationType(organization.getOrganizationType().getTypeCode());
							if (userGroup.isIsdefault()) {
								user.setDefaultUserGroupsId(userGroup.getGroup().getGroupId());
							}
						}
					}
				}
			}

			user.setGroupsList(groups);
			user.setOrganizations(organizations);
			
			List<Groups> userGroups = userOrganizationsGroupsService.getGroupsByUserOrganization(user.getId());
			user.setUserGroups(userGroups);
			user.setUserOrganizationsGroups(userOrganizationsGroups);
			
	    	Long stateId = getStateId(user.getCurrentOrganizationId());
			
	        long defaultOrgId = 0;
			if(user.getDefaultOrganizationId() == 0 && user.getOrganizations().size() > 0) {
				defaultOrgId = user.getOrganizations().get(0).getId();
				user.setCurrentOrganizationType(user.getOrganizations().get(0).getOrganizationType().getTypeCode());
			} else {
				defaultOrgId = user.getDefaultOrganizationId();
			}
			Organization contractingOrg = organizationService.getContractingOrganization(defaultOrgId);
			if(contractingOrg != null) {
				user.setContractingOrgDisplayIdentifier(contractingOrg.getDisplayIdentifier());
				user.setContractingOrgId(contractingOrg.getId());
				user.setContractingOrganization(contractingOrg);
			}
			if(stateId == null && user.getCurrentOrganizationType().equals("CONS")){
				// set CETE as contracting organization for GSAD
				Organization ceteOrg = organizationService.getCeteOrganization();
				if(ceteOrg != null) {
					user.setContractingOrgDisplayIdentifier(ceteOrg.getDisplayIdentifier());
					user.setContractingOrgId(ceteOrg.getId());
					user.setContractingOrganization(ceteOrg);
				}
				// If user is Global System Administrator
				UserAssessmentPrograms userDefaultAssessmentProgram = getUserDefaultAssessmentProgram(user.getId());
	            
				if(userDefaultAssessmentProgram != null){
	            	user.setDefaultAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
	            	user.setCurrentAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
	            	user.setCurrentGroupsId(userDefaultAssessmentProgram.getGroupId());
	            	user.setCurrentOrganizationId(userDefaultAssessmentProgram.getOrganizationId());
	            	
	            	List<UserAssessmentPrograms> userAssessmentProgramsList = userAssessProgDao.getByUserGroupIdOrgId(user.getId(), 
							user.getCurrentGroupsId(), user.getCurrentOrganizationId());
					if(userAssessmentProgramsList != null && !userAssessmentProgramsList.isEmpty()){
						List<AssessmentProgram> userAssessmentPrograms = assessmentProgramService.getAllActive();
				        for(UserAssessmentPrograms userAssessmentProgram : userAssessmentProgramsList){
				        	if(userAssessmentProgram.getIsDefault().booleanValue()){
				        		user.setCurrentAssessmentProgramId(userAssessmentProgram.getAssessmentProgram().getId());
				        		AssessmentProgram assessmentProgram=assessmentProgramService.findByAssessmentProgramId(userAssessmentProgram.getAssessmentProgram().getId());
				        		user.setCurrentAssessmentProgramName(assessmentProgram.getAbbreviatedname());
				        	}
				        }
				        user.setUserAssessmentPrograms(userAssessmentPrograms);
					}
					// Populate user authorities here so that current assigned role(current group, organization and assessment program) is fully defined
					stateId = getStateId(user.getCurrentOrganizationId());
					List<Authorities> authorities = null;
					if(superUser){
						authorities = authoritiesDao.getAll();
					}else{
					 authorities = authoritiesDao.getGroupExcludeLockdownForGlobalAdmin(
							user.getId(), user.getCurrentGroupsId(), 
							user.getCurrentOrganizationId());
					}
            		user.setAuthoritiesList(authorities);
	            } else {
		            // In case if Global System Administrator has no assessment program set as default  
            		List<AssessmentProgram> userAssessmentPrograms = assessmentProgramService.getAllActive();
            		user.setUserAssessmentPrograms(userAssessmentPrograms);
            		if(user.getCurrentGroupsId() == 0){
            			user.setCurrentGroupsId(user.getDefaultUserGroupsId());
            		}
            		
            		if(user.getCurrentOrganizationId() == 0){
            			user.setCurrentOrganizationId(user.getDefaultOrganizationId());
            		}
            		List<Authorities> authorities = null;
            		if(superUser){
            			authorities=authoritiesDao.getAll();
            		}else{
            		 authorities = authoritiesDao.getGroupExcludeLockdownForGlobalAdmin(
							user.getId(), user.getCurrentGroupsId(), 
							user.getCurrentOrganizationId());
            		}
            		user.setAuthoritiesList(authorities);
				}
			} else {
		        UserAssessmentPrograms userDefaultAssessmentProgram = getUserDefaultAssessmentProgram(user.getId());
	            if(userDefaultAssessmentProgram != null){
	            	user.setDefaultAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
	            	user.setCurrentAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
	            	user.setCurrentGroupsId(userDefaultAssessmentProgram.getGroupId());
	            	user.setCurrentOrganizationId(userDefaultAssessmentProgram.getOrganizationId());
	            	AssessmentProgram assessmentProgram=assessmentProgramService.findByAssessmentProgramId(user.getCurrentAssessmentProgramId());
	            	 user.setCurrentAssessmentProgramName(assessmentProgram.getAbbreviatedname());
	            }
	            
				List<UserAssessmentPrograms> userAssessmentProgramsList = userAssessProgDao.getByUserGroupIdOrgId(user.getId(), 
					user.getCurrentGroupsId(), user.getCurrentOrganizationId());
				Set<AssessmentProgram> userAssessmentPrograms = new HashSet<AssessmentProgram>();
		        for(UserAssessmentPrograms userAssessmentProgram : userAssessmentProgramsList){
		        	userAssessmentPrograms.add(userAssessmentProgram.getAssessmentProgram());
		        	if(userAssessmentProgram.getIsDefault().booleanValue()){
		        		user.setCurrentAssessmentProgramId(userAssessmentProgram.getAssessmentProgram().getId());
		        		AssessmentProgram assessmentProgram=assessmentProgramService.findByAssessmentProgramId(user.getCurrentAssessmentProgramId());
		            	 user.setCurrentAssessmentProgramName(assessmentProgram.getAbbreviatedname());
		        	}
		        }
		        user.setUserAssessmentPrograms(new ArrayList<AssessmentProgram>(userAssessmentPrograms));
		        
		        stateId = getStateId(user.getCurrentOrganizationId());
		        // Populate user authorities here so that current assigned role(current group, organization and assessment program) is fully defined
		        List<Authorities> authorities = null;
		        if(superUser){
		        	authorities = authoritiesDao.getAll();
		        }else{
		        	authorities = authoritiesDao.getByUserAndGroupExcludeLockdown(
						user.getId(), user.getCurrentGroupsId(), 
						user.getCurrentOrganizationId(), stateId, user.getCurrentAssessmentProgramId());
		        }
	        	user.setAuthoritiesList(authorities);
			}

			List<AssessmentProgram> userAPs = assessProgDao.getAllAssessmentProgramByUserId(user.getId());
			if(CollectionUtils.isNotEmpty(userAPs)){
				List<String> userAPCodes = new ArrayList<String>(userAPs.size());
				for(AssessmentProgram userAP:userAPs) {
					userAPCodes.add(userAP.getAbbreviatedname());
				}
				user.setAssessmentProgramCodes(userAPCodes);
			}
		  }
		  setITIStatusForCurrentAssementProgram(user);
		 } else {
			throw new UsernameNotFoundException("Username " + userName + " was not found.");
		}
		return user;
	}

	private Long getStateId(Long organizationId){
		List<Organization> parents = organizationService.getAllParents(organizationId);
    	Long stateId = null;
		//in the case the user is a state level user add the state
    	Organization defaultOrg=organizationService.get(organizationId);
		if(defaultOrg!=null){
			parents.add(defaultOrg);
		}
		for (Organization org : parents){
			if (org.getOrganizationType()!=null && org.getOrganizationType().getTypeCode()!=null && org.getOrganizationType().getTypeCode().equals("ST")){
				stateId = org.getId();
				break;
			}
		}
		return stateId;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Authorities> getByUserAndGroupExcludeLockdown(Long userId,
			Long groupId, Long organizationId, Long stateId,  Long AssessmentProgramId){
		return authoritiesDao.getByUserAndGroupExcludeLockdown(
				userId, groupId, organizationId, stateId, AssessmentProgramId);
	}
	/**
	 * @param email
	 *            {@link String}
	 * @return {@link User}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User getByEmail(String email) {
		return userDao.getByEmail(email);
	}
	
	/**
     * DE10344 : user validation done either by username or emailid in db fields
     * @param email
     * @return
     */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User getByEmailorUserName(String email) {
		return userDao.getByEmailorUserName(email);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User saveOrUpdate(User toSaveorUpdate) throws ServiceException {
		if (toSaveorUpdate.getId() == null) {
			return add(toSaveorUpdate);
		} else {
			return update(toSaveorUpdate);
		}
	}

	/**
	 * This method determines whether a group(Role) needs to be added, or
	 * removed from the database.
	 * 
	 * @param groupIds
	 *            List<Long>
	 * @param userId
	 *            long
	 * @param organizationId
	 *            long
	 * @throws ServiceException
	 *             ServiceException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void saveUserOrganizationsGroups(List<Long> groupIds,
			long userId, long organizationId) throws ServiceException {
		if (userId > 0 && groupIds != null) {
			// finding existing ones.
			List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsDao
					.getByUserId(userId);

			// delete the ones that need to be deleted.
			List<UserOrganizationsGroups> deletableUserOrganizationsGroups = new ArrayList<UserOrganizationsGroups>();
			boolean hasActiveRole = false;
			boolean hasOnlyDefaultRoles = true;
			if (groupIds.size() == 0) {
				deletableUserOrganizationsGroups = userOrganizationsGroups;
			} else {
				for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
					if (!hasActiveRole
							&& !userGroup.getGroup().isDefaultGroup()
							&& userGroup.getStatus() != null
							&& userGroup.getStatus() == UserStatus.ACTIVE) {
						hasActiveRole = true;
						hasOnlyDefaultRoles = false;
					} else if (hasOnlyDefaultRoles
							&& !userGroup.getGroup().isDefaultGroup()) {
						hasOnlyDefaultRoles = false;
					}
					// retain the ones that need to be retained.
					if (groupIds.contains(userGroup.getGroupId())) {
						groupIds.remove(userGroup.getGroupId());
					} else if (userGroup.getOrganizationId() != null
							&& userGroup.getOrganizationId().longValue() == organizationId) {
						deletableUserOrganizationsGroups.add(userGroup);
					}
				}
			}

			for (UserOrganizationsGroups temp : deletableUserOrganizationsGroups) {
				userOrganizationsGroupsDao.deleteUserOrganizationsGroups(temp
						.getId());
			}
			UUID activationNo = UUID.randomUUID();
			UserOrganizationsGroups userGroup;
			for (Long groupId : groupIds) {
				userGroup = new UserOrganizationsGroups();
				userGroup.setGroupId(groupId);
				userGroup.setUserId(userId);
				userGroup.setOrganizationId(organizationId);
				if (hasActiveRole) {
					userGroup.setStatus(UserStatus.ACTIVE);
				} else {
					userGroup.setStatus(UserStatus.PENDING);
				}

				userGroup = userOrganizationsGroupsService.addUserOrganizationsGroups(userGroup);
				userGroup.setUser(get(userGroup.getUserId()));
				List<UserOrganizationsGroups> userOrgGroups=userOrganizationsGroupsDao.getUserOrganizationsGroupsByUserId(userGroup.getUserId());
				String activationNumber =null;
				for(UserOrganizationsGroups ug:userOrgGroups){
					if (ug.getActivationNo() != null) {
						activationNumber=ug.getActivationNo();
					}
				}
				if(activationNumber != null){
					// Using existing activation number
					saveActivationNo(userGroup,activationNumber);
				}else{
					// Generate new activation number if not found one in DB.
					saveActivationNo(userGroup,activationNo.toString());
				}
				if (!hasActiveRole) {
					// send activation email.
					if (hasOnlyDefaultRoles) {
						emailService.sendUserActivationMsg(userGroup);
					} else {
						// send notification email for new organization
						// association.
						emailService.setUserOrgNotificationMsg(userGroup);
					}
				} else {
					// send notification email for new organization association.
					emailService.setUserOrgNotificationMsg(userGroup);
				}
			}

		} else {
			throw new IllegalArgumentException("Invalid userId parameter");
		}
	}

	/**
	 * This method checks each uploaded user to make sure that it is a valid
	 * user record.
	 * 
	 * @param uploadedUsers
	 *            List<UploadedUser>
	 * @param invalidUsers
	 *            List<UploadedUser>
	 * @param currentOrg
	 *            {@link Organization}
	 * @param organizationTree
	 *            {@link OrganizationTree}
	 * @throws ServiceException
	 *             ServiceException
	 * @return createdUsers - number of users that were created from the file.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int checkUploadedUsers(List<UploadedUser> uploadedUsers,
			List<UploadedUser> invalidUsers, Organization currentOrg,
			ContractingOrganizationTree contractingOrganizationTree,
			UserDetailImpl loggedInUser, List<UploadedUser> successUsers,
			Long parentOrgId) throws ServiceException {
		logger.trace("Entering the checkUploadedUsers method.");

		int createdUsers = 0;
		User existingUser = null;
		for (UploadedUser uploadedUser : uploadedUsers) {
			logger.debug("Checking if uploadedUser {} is valid.", uploadedUser);
			// check if the uploaded user's organization is within the logged in
			// user's ability to upload.
			Organization org = contractingOrganizationTree
					.getUserOrganizationTree().getOrganization(
							uploadedUser.getDisplayIdentifier());
			logger.debug(
					"Found referenced organization {} for user's organization.",
					org);
			if (!uploadedUser.isInValid() && !uploadedUser.isDoReject()) {
				existingUser = userDao.getByEmail(uploadedUser.getEmail());
				uploadedUser = isValidUser(uploadedUser, existingUser);
			}
			if (!uploadedUser.isInValid() && !uploadedUser.isDoReject()) {
				uploadedUser = isValidUserIdentifierAndOrg(uploadedUser, org,
						contractingOrganizationTree, parentOrgId,true);
			}
			if (!uploadedUser.isInValid() && !uploadedUser.isDoReject()) {
				uploadedUser = createOrAddUserOrganizationsGroups(uploadedUser,
						existingUser, org, contractingOrganizationTree,
						loggedInUser);
				createdUsers++;
			}
			if (uploadedUser.isInValid() || uploadedUser.isDoReject()) {
				logger.debug("User with email address {} rejected because {}",
						uploadedUser.getEmail(), uploadedUser
								.getInValidDetails().toString());
				invalidUsers.add(uploadedUser);
			} else {
				successUsers.add(uploadedUser);
			}
		}
		logger.trace("Leaving the checkUploadedUsers method.");
		return createdUsers;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int checkUploadedPDTrainingResults(List<User> uploadedUsers,
			List<User> invalidUsers, Organization currentOrg,
			ContractingOrganizationTree contractingOrganizationTree,
			UserDetailImpl loggedInUser, List<User> successUsers,
			Long parentOrgId) throws ServiceException {
		logger.trace("Entering the checkUploadedPDTrainingResults method.");

		int createdUsers = 0;
		for (User uploadedUser : uploadedUsers) {
			logger.debug("Checking if uploadedUser {} is valid.", uploadedUser);
			// This validation should be done, even if parser returns valid user data.
			if(uploadedUser.isRtComplete() && (uploadedUser.getRtCompleteDate()==null || uploadedUser.getRtCompleteDate().equals(""))){
				if(uploadedUser.getInValidFields() != null && !uploadedUser.getInValidFields().contains("rtCompleteDate")){
					uploadedUser.addInvalidField(FieldName.RT_COMPLETE_DATE.toString(), 
							DateUtil.format(uploadedUser.getRtCompleteDate(), "MM/dd/yyyy"), false, " is required and is null.");
					invalidUsers.add(uploadedUser);
				}
			}
			
			if (uploadedUser.isInValid() || uploadedUser.isDoReject()) {
				// Setting rejectRecord to true (Shouldn't be doing this, but the parsing validation sets rejectRecord to false
				// need to use this field to distinguish between rejections and below fields with values are forcing for display
				// purpose)
				uploadedUser.addInvalidField(FieldName.USER_FIRST_NAME.toString(), uploadedUser.getFirstName(), true);
				uploadedUser.addInvalidField(FieldName.USER_LAST_NAME.toString(), uploadedUser.getSurName(), true);
				if(null == uploadedUser.getId()){
					uploadedUser.addInvalidField(FieldName.ID_NUMBER.toString(), StringUtils.EMPTY, true);
				} else {
					uploadedUser.addInvalidField(FieldName.ID_NUMBER.toString(), uploadedUser.getId().toString(), true);
				}
				if(!invalidUsers.contains(uploadedUser)){
					invalidUsers.add(uploadedUser);
				}
			} else {
				try{
					User user = userDao.getByIdentifierAndFirstLastName(
												uploadedUser.getId(), 
												uploadedUser.getFirstName(),
												uploadedUser.getSurName());
					
					if(user == null || user.getId() == null){
						uploadedUser.addInvalidField(FieldName.USER_FIRST_NAME.toString(), uploadedUser.getFirstName(), true);
						uploadedUser.addInvalidField(FieldName.USER_LAST_NAME.toString(), uploadedUser.getSurName(), true);
						String id = uploadedUser.getId() == null ? "": uploadedUser.getId().toString();
						uploadedUser.addInvalidField(FieldName.ID_NUMBER.toString(), id, true);
						uploadedUser.setFailedReason("User with given ID Number:" + uploadedUser.getId() + ", First Name:" + uploadedUser.getFirstName() + " and Last Name:" + uploadedUser.getSurName() +" not found in the system");
						if(!invalidUsers.contains(uploadedUser)){
							invalidUsers.add(uploadedUser);
						}
					}else{
						//if(uploadedUser.isRtComplete()){				
							user.setRtComplete(uploadedUser.isRtComplete());
							user.setRtCompleteDate(uploadedUser.getRtCompleteDate());
							user.setCreatedUser(loggedInUser.getUserId());
							user.setModifiedUser(loggedInUser.getUserId());
							user.setContractingOrganization(loggedInUser.getUser().getContractingOrganization());
							userDao.insertUserPDTrainingDetail(user);
							successUsers.add(uploadedUser);
							createdUsers++;
						//}
					}
				}catch(Exception e){
					uploadedUser.addInvalidField(FieldName.USER_FIRST_NAME.toString(), uploadedUser.getFirstName(), true);
					uploadedUser.addInvalidField(FieldName.USER_LAST_NAME.toString(), uploadedUser.getSurName(), true);
					uploadedUser.addInvalidField(FieldName.ID_NUMBER.toString(), (uploadedUser.getId() == null) ? "": uploadedUser.getId().toString(), true);
					uploadedUser.setFailedReason("User with given Last Name:" + uploadedUser.getSurName() + ", First Name:" + uploadedUser.getFirstName() + " and ID Number:" + uploadedUser.getId() +" is not Valid");
					if(!invalidUsers.contains(uploadedUser)){
						invalidUsers.add(uploadedUser);
					}
				}
			}
		}
		logger.trace("Leaving the checkUploadedPDTrainingResults method.");
		return createdUsers;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Boolean isUserCreationRestricted(String groupCode, Long organizationId, Long assessmentProgramId,
		String organizationType, Long userId){
		
		List<Organization> parents = organizationService.getAllParents(organizationId);
    	Long stateId = null;
		//in the case the user is a state level user add the state
		parents.add(organizationService.get(organizationId));
		for (Organization org : parents){
			if (org.getOrganizationType().getTypeCode().equals("ST")){
				stateId = org.getId();
				break;
			}
		}
		
		Boolean isRestrictedToSingleUser = userDao.getIsRestrictedToSingleUser(groupCode, stateId, assessmentProgramId);
		if(isRestrictedToSingleUser == null){
			// If there is no entry in restrictions then it defaults to multiple users exists per role.
			isRestrictedToSingleUser = new Boolean(false);
		}
		Boolean isSingleUserExists = userDao.getIsSingleUserExists(groupCode, organizationId, assessmentProgramId, organizationType, userId);
		return new Boolean(isRestrictedToSingleUser.booleanValue() && isSingleUserExists.booleanValue());
	}

	/**
	 * Biyatpragyan Mohanty : US14307 : User Management: Add Users Manually Save
	 * User information including user, organization and roles data. Have to
	 * handle below conditions - Active user is modified - Deleted user is
	 * re-created (we never hard-delete so record exists) - Should not conflict
	 * with new user creation.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final UserRequest saveUser(UserRequest userRequest, boolean overwrite, Boolean isFindUser)
			throws ServiceException {
		logger.trace("Entering the addUser method.");

		User user = new User();
		user.setId(userRequest.getId());
		user.setUniqueCommonIdentifier(userRequest.getEducatorIdentifier()==null ? "" : userRequest.getEducatorIdentifier());		
		user.setFirstName(userRequest.getFirstName());
		user.setSurName(userRequest.getLastName());
		user.setEmail(userRequest.getEmail());
		user.setUserName(userRequest.getEmail());
		user.setModifiedUser(userRequest.getCreateModifierId());
		user.setActiveFlag(userRequest.getActiveflag());
		user.setExternalId(userRequest.getExternalId());
		user.setDefaultOrganizationId(userRequest.getDefaultUserOrgId()==null ? 0L : userRequest.getDefaultUserOrgId());
		
		//Code Added for US19304
		String email = user.getEmail();
	   	String userDomainName = email.substring(email.indexOf('@') + 1); 
	   	
	   	boolean internalUser = false;	
	   	if(internalUsersDomains!=null && !internalUsersDomains.equals("") && !internalUsersDomains.isEmpty()){
	   		String[] domainNamesList =  internalUsersDomains.split(",");
		    if(domainNamesList!=null && domainNamesList.length>0){
		    	for(String internalDomainName : domainNamesList){					
					if(userDomainName.equals(internalDomainName)){
						internalUser=true;
					}
				}
		    } 		
	   	}
	   	user.setInternaluserindicator(internalUser);
	   	
		String userBeforUpdate = null;
		String userAfterUpdate = null;
		if (StringUtils.isNotEmpty(user.getUserName())) {
			user.setUserName(user.getUserName().toLowerCase());
		}
		if (StringUtils.isNotEmpty(user.getEmail())) {
			user.setEmail(user.getEmail().toLowerCase());
		}
		
		boolean issueFound = false;
		List<Long> orglist = new ArrayList<Long>();

		boolean isNewUser = true;
		if (user != null && user.getId() != null && user.getId() > 0) {
			isNewUser = false;
		}else {
			user.setCreatedUser(userRequest.getCreateModifierId());
		}

		User existingUser = null;
		try{
		  existingUser = userDao.getByEmail(user.getEmail());
		}catch(Exception e){   
		   userRequest.setErrorCode("USER_EXISTS");
		   logger.debug(
		     "User with same email address is found in the system. "
		       + "Reporting error.", user.getEmail());
		   issueFound = true;
		}	
		
		if (existingUser != null
				&& (isNewUser || (!isNewUser && !existingUser.getId().equals(
						user.getId())))) {
			userRequest.setId(existingUser.getId());
			userRequest.setErrorCode("USER_EXISTS");
			logger.debug(
					"User with same email address is found in the system. "
							+ "Reporting error.", existingUser.getEmail());
			issueFound = true;
		}

		//issueFound = validateUniqueCommonIdentifier(userRequest, user, issueFound, existingUser);
		OrganizationRoleRequest[] organizations = userRequest.getOrganizations();
		Groups dtcGroup = groupsService.getGroupsByGroupCode("DTC").get(0);
		Groups btcGroup = groupsService.getGroupsByGroupCode("BTC").get(0);
        if (organizations != null && organizations.length > 0) {
			for (int i = 0; i < organizations.length; i++) {
				orglist.add(organizations[i].getOrganizationId());
				Long organizationId = organizations[i].getOrganizationId();
				for(Long groupId : organizations[i].getRolesIds()){
					Long assessmentProgramId=organizations[i].getAssessmentProgramId();
					Long userId = user != null ? user.getId(): null;
					if(assessmentProgramId != null){
						// Per US18825 && US18927 allow only one user for DTC/BTC based on the single user flag from groups table.
						String organizationType = "";
						if(dtcGroup != null && groupId.equals(dtcGroup.getGroupId())){
							String groupCode = "DTC";
							organizationType = "DT";
							if(isUserCreationRestricted(groupCode, organizationId, assessmentProgramId, organizationType, userId)){
								userRequest.setErrorCode("ONE_DTC_USER_EXISTS");
								issueFound = true;
								return userRequest;
							}
						}
						
						if(btcGroup != null && groupId.equals(btcGroup.getGroupId())){
							String groupCode = "BTC";
							organizationType = "SCH";
							if(isUserCreationRestricted(groupCode, organizationId, assessmentProgramId, organizationType, userId)){
								userRequest.setErrorCode("ONE_BTC_USER_EXISTS");
								issueFound = true;
								return userRequest;
							}
						}
					}
				}
			}
		}
        
		// DE6479 - allow empty educator id
		if (user.getUniqueCommonIdentifier() != null && !user.getUniqueCommonIdentifier().isEmpty() && CollectionUtils.isNotEmpty(orglist)) {
			ArrayList<User> existingUsers = (ArrayList<User>) userDao
					.getByUniqueCommonIdentifierAndOrgList(user.getUniqueCommonIdentifier(), orglist);
			// Changed for DE12626 - educator identifier not unique
			for (Long orgId : orglist) {
				existingUsers = (ArrayList<User>) userDao
						.getByUniqueCommonIdentifierAndDisplayIdForStateLevel(user.getUniqueCommonIdentifier(), orgId);
				if (existingUsers != null && existingUsers.size() > 0) {
					existingUser = existingUsers.get(0);
					if (existingUser != null
							&& (isNewUser || (!isNewUser && !existingUser.getId().equals(user.getId())))) {
						userRequest.setId(existingUser.getId());
							userRequest.setErrorCode("USER_ORG_EXISTS_" + existingUser.getOrgName());
							issueFound = true;
						logger.debug("User with organization {} was found in the system. " + "Reporting error.",
								existingUser.getOrgName());
						break;
					}
				}
			}
		} 
		
		if (issueFound) {
			return userRequest;
		}

		if (!isNewUser) {

			existingUser = userDao.getJsonFormatData(user.getId());
			if (existingUser != null)
				userBeforUpdate = existingUser.buildJsonString();
			
			// Update existing user.
			user.setAuditColumnPropertiesForUpdate();
			
			//make sure API created/modified users don't have modidifiedUserId set to null because of setAuditColumnProperties
			if(userRequest.getExternalId() != null && userRequest.getCreateModifierId() != null) {
				user.setModifiedUser(userRequest.getCreateModifierId());
			}
			
			
			if(userRequest.getToUpdateId() != null)
			{List<UserSecurityAgreement> userSecurityAgreementList = userSecurityAgreementDao.getSecurityAgreement(userRequest.getToUpdateId());
			Date newDate = (Date) userSecurityAgreementList.get(0).getAgreementSignedDate();
			userSecurityAgreementMapper.mergeUpdateDate(newDate,userRequest.getId());
				
			}
			if(userRequest.getEmailId()!=null && userRequest.getAction().equals("MERGE") )
			{
			String emailname= userDao.getEmailById(userRequest.getEmailId());
	        user.setEmail(emailname);
			}
		if(userRequest.getEducatorId()!= null && userRequest.getAction().equals("MERGE"))
		{
			
		String educatorid=userDao.getEducatorIdentifierById(userRequest.getEducatorId());
		user.setUniqueCommonIdentifier(educatorid);

		}
			userDao.updateSelectiveByPrimaryKey(user);

			// US17558- To stop hard delete
			saveUserOrganizationsAndRoles(userRequest, user, false, isFindUser, false);

			
		} else {
			// New user, add method call to generate the user's logical identifier
			user.setDisplayName(user.getFirstName() + ' ' + user.getSurName());
			user.setAuditColumnProperties();
			//make sure API created/modified users don't have modidifiedUserId set to null because of setAuditColumnProperties
			if(userRequest.getExternalId() != null && userRequest.getCreateModifierId() != null) {
				user.setModifiedUser(userRequest.getCreateModifierId());
			}
			String password = passwordEncoder.encodePassword(user.getPassword(), user.getSalt());
			user.setPassword(password);
			user.setSourceType(SourceTypeEnum.MANUAL.getCode());
			userDao.add(user);
			logger.debug("Created the user.");
			
			// Added during US16351-Add multiple assessment programs based on user Id
			if (organizations != null && organizations.length > 0) {
				saveUserOrganizationsAndRoles(userRequest, user, true, isFindUser, internalUser);
			}
		}
		
		//make sure API created/modified users don't have modidifiedUserId set to null because of setAuditColumnProperties
		if(userRequest.getExternalId() != null && userRequest.getCreateModifierId() != null) {
			user.setModifiedUser(userRequest.getCreateModifierId());
		}
		
		//Add the user record in domain audit history
		User userJson =  userDao.getJsonFormatData(user.getId());
		if(userJson != null)
			userAfterUpdate = userJson.buildJsonString();
		insertIntoDomainAuditHistory(user.getId(),user.getModifiedUser(),userRequest.getAction(),SourceTypeEnum.MANUAL.getCode(),userBeforUpdate,userAfterUpdate);

		logger.trace("Leaving the addUser method.");
		return userRequest;
	}

	private void createNewUserAssessmentProgram(UserOrganizationsGroups userOrg, Long assessmentProgramId, boolean apDefault) {
		UserAssessmentPrograms userAssessmentPrograms = new UserAssessmentPrograms();
		userAssessmentPrograms.setAartUserId(userOrg.getUserId());
		userAssessmentPrograms.setAssessmentProgramId(assessmentProgramId);
		userAssessmentPrograms.setIsDefault(apDefault);
		userAssessmentPrograms.setUserOrganizationGroupId(userOrg.getId());
		userAssessmentPrograms.setAuditColumnProperties();
		userAssessProgDao.insertSelective(userAssessmentPrograms);
	}

	private void saveActivationNo(UserOrganizationsGroups userOrganizationsGroups, String activationNo){
        Calendar now = GregorianCalendar.getInstance();

        //Pulling the number of days out of the properties file
        now.add(Calendar.DATE, numActivationDays);

        userOrganizationsGroups.setActivationNo(activationNo);
        userOrganizationsGroups.setActivationNoExpirationDate(now.getTime());
        userOrganizationsGroupsService.updateUserOrganizationsGroups(userOrganizationsGroups);
    }
	
	@SuppressWarnings("unused")
	private boolean validateUniqueCommonIdentifier(UserRequest userRequest, User user, boolean issueFound,
			User existingUser) {
		if(existingUser != null &&  existingUser.getUniqueCommonIdentifier()!=null && user.getUniqueCommonIdentifier() == null){
			
			userRequest.setId(existingUser.getId());
			userRequest.setErrorCode("EDUCATOR_IDENTIFIER_NOT_NULL");
			issueFound = true;

		}
		return issueFound;
	}

	/**
	 * Modify user, this method will also be called when a deactivated user is
	 * asked to be created again We never delete a user from database, just
	 * deactivate it, so technically when a user is created and it exists as a
	 * deactivated user, we should modify the information and resend email. For
	 * regular modifications, no need to to send email, since it's just
	 * information update.
	 * 
	 * @param userRequest
	 * @param existingUser
	 * @param user
	 * @param overwrite
	 * @return
	 * @throws ServiceException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private UserRequest modifyUser(UserRequest userRequest, User existingUser,
			User user, boolean overwrite) throws ServiceException {
		boolean newUploadedUserModify = false;
		user.setDisplayName(user.getFirstName() + ' ' + user.getSurName());
		Date now = new Date();
		user.setId(existingUser.getId());
		user.setModifiedUser(userRequest.getCreateModifierId());
		user.setModifiedDate(now);
		user.setModifiedUser(userRequest.getCreateModifierId());
		user.setActiveFlag(true);
		userDao.updateSelectiveByPrimaryKey(user);

		List<UserOrganizationsGroups> roles = userOrganizationsGroupsDao
				.getByUserId(user.getId());
		if (roles != null && roles.size() > 0) {
			for (UserOrganizationsGroups role : roles) {
				userOrganizationsGroupsDao.deleteUserOrganizationsGroups(role
						.getId());
			}

			for (UserOrganizationsGroups role : roles) {
				userOrganizationsGroupsDao.deleteUserOrganizations(role
						.getUserOrganizationId());
			}
		} else {
			// User is a uploaded new user, must be sent email
			newUploadedUserModify = true;
		}

		OrganizationRoleRequest[] organizations = userRequest
				.getOrganizations();
		UUID activationNo = UUID.randomUUID();
		if (organizations != null && organizations.length > 0) {
			for (int i = 0; i < organizations.length; i++) {
				UserOrganizationsGroups userOrg = new UserOrganizationsGroups();
				Date now1 = new Date();
				userOrg.setUserId(user.getId());
				userOrg.setOrganizationId(organizations[i].getOrganizationId());
				userOrg.setCreatedDate(now1);
				userOrg.setModifiedDate(now1);
				userOrg.setCreatedUser(userRequest.getCreateModifierId());
				userOrg.setModifiedUser(userRequest.getCreateModifierId());

				if (organizations[i].getOrganizationId().longValue() == userRequest
						.getDefaultOrgId().longValue()) {
					userOrg.setIsdefault(true);
				}

				userOrganizationsGroupsDao.addUsersOrganizations(userOrg);

				userOrg.setIsdefault(false);
				
				Long[] roleIds = organizations[i].getRolesIds();
				if (roleIds != null && roleIds.length > 0) {
					for (int j = 0; j < roleIds.length; j++) {
						userOrg.setStatus(UserStatus.PENDING);
						userOrg.setGroupId(roleIds[j]);
						if (organizations[i].getDefaultRoleId().longValue() == roleIds[j]
								.longValue()) {
							userOrg.setIsdefault(true);
						}
						userOrganizationsGroupsDao.addUserOrganizationsGroups(userOrg);
						userOrg.setIsdefault(false);
						saveActivationNo(userOrg, activationNo.toString());
					}
				}

				userOrg.setUser(user);
				if (!overwrite || newUploadedUserModify) {
					emailService.sendUserActivationMsg(userOrg);
				}

				logger.debug("Updated the user organization.");
			}
		}
		return userRequest;
	}

	/**
	 * 
	 * 5. IF the user is in soft deleted state then check if the uploaded user
	 * has the organization same as existing or for one of its parents.
	 * 
	 * 6. If 5 is satisfied then update the user's organization.
	 * 
	 * TODO service methods should not rely on call by value.
	 * 
	 * @param newUser
	 *            {@link User}
	 * @param organization
	 *            {@link Organization}
	 * @param invalidUsers
	 *            List<UploadedUser>
	 * @param contractingOrganizationTree
	 *            {@link OrganizationTree}
	 * @param currentUser
	 *            {@link User} - the user currently logged into the system.
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final UploadedUser createOrAddUserOrganizationsGroups(
			UploadedUser newUser, User existingUser, Organization organization,
			ContractingOrganizationTree contractingOrganizationTree,
			UserDetailImpl currentUser) {
		logger.trace("Entering the createOrAddUserOrganizationsGroups method.");
		logger.debug(
				"Received parameters newUser[UploadedUser]: {}, organization: {}, "
						+ " userOrganizationTree: {}, currentUser: {}",
				new Object[] { newUser, organization,
						contractingOrganizationTree, currentUser });

		User user = null;
		if (newUser != null && newUser.getId() != null && newUser.getId() > 0) {
			// Treat it like a generic update. The user is already found in
			// the system so updating it.
			// this means that there was a shell user created from an scrs
			// upload
			logger.debug(
					"Shell user was found for uploadedUser {}. Updating shell user'a email, username and active flag",
					newUser);

			// set the audit fields, update the record
			newUser.setAuditColumnPropertiesForUpdate();
			userDao.updateSelectiveByPrimaryKey(newUser.getUser());

			// Update UserOrganizations table with the uploaded user
			// organization - fix to the defect - DE3206
			if (organization.getId() != null && newUser.getId() != null) {
				userOrganizationsGroupsDao.updateUsersOrganizations(
						organization.getId(), newUser.getId(),newUser.getModifiedUser(),newUser.getModifiedDate());
			}
		} else {
			// Defect fix for - DE3720
			// Code fix for uploading the user without user_identifier
			// and then rejecting that user and re-uploading the same user.
			if (existingUser != null && existingUser.getActiveFlag() == false) {
				newUser.getUser().setActiveFlag(true);
				newUser.getUser().setId(existingUser.getId());
				newUser.getUser().setAuditColumnPropertiesForUpdate();
								
				userDao.updateSelectiveByPrimaryKey(newUser.getUser());
				logger.debug("Reactivated the user who was rejected previously, but for the users its a new user creation.");
			} else {
				/*
				 * This means that this is a new user for the system. So add
				 * them to the database, and create their userGroup.
				 */
				// No need to check in the system for the user's identifier as
				// that is already done.
				// logger.debug("UploadedUser's identifier is empty, and therefore no further checking needs to be done."
				// +
				// " Adding to the datbase");

				newUser.setUserName(newUser.getEmail());
			
				// add method call to generate the user's logical identifier
				user = addUserWithAudit(newUser.getUser(), currentUser);
				logger.debug("Created the user.");
				// INFO adding user group is not necessary as users are not
				// required to have groups.

				/**
				 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15323 : User
				 * upload - set role to teacher Assign default role as Teacher
				 * when user is uploaded.
				 */
				Groups grp = groupsService.getGroupByName("Teacher");
				UserOrganizationsGroups addedOrgGroup = addUserToOrganization(
						user, organization.getId(),
						(grp == null ? null : grp.getGroupId()), newUser,
						currentUser);
				UUID activationNo = UUID.randomUUID();
				try {
					saveActivationNo(addedOrgGroup, activationNo.toString());
					emailService.sendUserActivationMsg(addedOrgGroup);
				} catch (ServiceException e) {
					// do nothing.
					logger.error("Exception while sending activation mail : ", e);
				}
			}
		}
		return newUser;
	}
	/**
	 * Prasanth :  US16352 : User upload using spring batch and
	 * US16246:added  primary and Secondary role in the upload csv file  
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final UploadedUser createOrAddUserOrganizationsGroupsUpload(
			UploadedUser newUser, User existingUser, Organization organization,
			ContractingOrganizationTree contractingOrganizationTree,
			UserDetailImpl currentUser) {

		boolean insert = false;
		User user = null;
		
		//Code Added for US19304
		String email = newUser.getEmail();
		String userDomainName = email.substring(email.indexOf('@') + 1); 
			   	
		boolean internalUser = false;	
		if(internalUsersDomains!=null && !internalUsersDomains.equals("") && !internalUsersDomains.isEmpty()){
			String[] domainNamesList =  internalUsersDomains.split(",");
				if(domainNamesList!=null && domainNamesList.length>0){
				    for(String internalDomainName : domainNamesList){
						if(userDomainName.equals(internalDomainName)){
							internalUser=true;
						}
					}
				} 		
		}
		newUser.getUser().setInternaluserindicator(internalUser);
		
		String userBeforeUpdate =  null;
		String userAfterUpdate = null;
		if (newUser != null && newUser.getId() != null && newUser.getId() > 0) {
			// TODO treat it like a generic update. The user is already found in
			// the system so updating it.
			// this means that there was a shell user created from an scrs
			// upload
			// set the user's email, username.
			
			// set the audit fields.
			newUser.setModifiedUser(currentUser.getUserId());
			newUser.setModifiedDate(new Date());
			user = newUser.getUser();
			// update the record
			userDao.updateSelectiveByPrimaryKey(newUser.getUser());

			// Update UserOrganizations table with the uploaded user
			// organization - fix to the defect - DE3206
			if (organization.getId() != null && newUser.getId() != null) {
				userOrganizationsGroupsDao.updateUsersOrganizations(
						organization.getId(), newUser.getId(),newUser.getModifiedUser(),newUser.getModifiedDate());
			}
		} else {
			// Defect fix for - DE3720
			// Code fix for uploading the user without user_identifier
			// and then rejecting that user and re-uploading the same user.
			if (existingUser != null && existingUser.getActiveFlag() == false) {
				
				//Added for domain audit history table
				User userjson = userDao.getJsonFormatData(existingUser.getId());
				
				if(userjson != null)
				userBeforeUpdate = userjson.buildJsonString();
				
				newUser.getUser().setActiveFlag(true);
				newUser.getUser().setId(existingUser.getId());
				newUser.getUser().setAuditColumnPropertiesForUpdate();
				user = newUser.getUser();
				userDao.updateSelectiveByPrimaryKey(newUser.getUser());
				
				//Added for domain audit history table
				userjson = userDao.getJsonFormatData(user.getId());
				
				if(userjson != null)
				userAfterUpdate= userjson.buildJsonString();
				
				logger.debug("Reactivated the user who was rejected previously, but for the users its a new user creation.");
			}
			/**
			 * US16533 : To update the existing user
			 */ 
			else if( existingUser != null && existingUser.getId() != null ) { //
				
				//Added for domain audit history table
				User userJson = userDao.getJsonFormatData(existingUser.getId());
				
				if(userJson != null)
				userBeforeUpdate = userJson.buildJsonString();	
					
				Long userId = existingUser.getId();
				newUser.setEmail(newUser.getEmail().toLowerCase());
				newUser.setUserName(newUser.getEmail());
				newUser.getUser().setId(userId);
				newUser.getUser().setAuditColumnPropertiesForUpdate();
				newUser.getUser().setCreatedUser(existingUser.getId());
				userDao.updateSelectiveByPrimaryKey(newUser.getUser());
				user = newUser.getUser();				
				
				/*
				 * Changed during US17558 - To stop hard delete
				 * */
				
				if( newUser.getPrimaryGroups() != null ||  newUser.getSecondaryGroups() != null ){
				 List<UserOrganization> userOrganizations = userOrganizationsGroupsDao.getAllUserOrganizationAndRole(user.getId());
					boolean hasActiveRole = false;
					boolean isDeactivated = false;
					
					//Check for user status
					if (CollectionUtils.isNotEmpty(userOrganizations)) {
						for (Iterator<UserOrganization> uogItr = userOrganizations
								.iterator(); uogItr.hasNext();) {
							UserOrganization uog = (UserOrganization) uogItr
									.next();
							List<UserRoles> roles = uog.getRoles();
							for (UserRoles userRole : roles) {
								if (userRole.getStatus()  == UserStatus.ACTIVE) {
									hasActiveRole = true;
								}
								if (userRole.getStatus()  == UserStatus.INACTIVE) {
									isDeactivated = true;
								}
							}
						}
					}else{
						isDeactivated = true;
					}
				 boolean insertGroup = true;
				 int status = 0;
				 
				 if(hasActiveRole){
						status = UserStatus.ACTIVE;
					}else if(isDeactivated){
						status = UserStatus.INACTIVE;
					}else{
						status = UserStatus.PENDING;
					}
				 
				for (UserOrganization userOrganization : userOrganizations) {
					//Check already organization is present or not
					if(userOrganization.getOrganizationId().longValue() == organization.getId().longValue()){
						insertGroup = false;
						
						//Check active flag And check it's isdefault
						if(!userOrganization.getActiveFlag() || !userOrganization.isIsDefault()){ 
							updateUsersOrganizationsByPrimaryKey(userOrganization.getId(), true, true);					
						}
						
						//Roles
						boolean isNewGrp = true;
							if(newUser.getPrimaryGroups() != null){                      //primary roles
								
							  for (UserRoles userRole : userOrganization.getRoles()) {
								//Check already Roes is present or not
								  
								if(userRole.getGroupId() == newUser.getPrimaryGroups().getGroupId()){
									isNewGrp = false;
									
									//Check active flag And check it's isdefault
//									if(!userRole.getActiveFlag() || !userRole.isDefault()){
										updateUserOrgGroupByPrimaryKey(userRole.getId(), true, true);
										newUser.getUser().setAssessmentProgramId(newUser.getPrimaryAssessmentProgramId());
										List<UserAssessmentPrograms> uaps = userAssessProgDao.getByUserGroupIdOrgIds(userId, 
												userRole.getGroupId(), userOrganization.getOrganizationId());
										if(uaps != null && !uaps.isEmpty()){
											// update existing uap
											updateUserAssessmentProgramToDefault(newUser.getUser(), organization.getId(), userRole.getGroupId(), true, userRole.getId());
										} else {
											UserOrganizationsGroups addedOrgGroup = userOrganizationsGroupsService.getUserOrganizationsGroups(userRole.getId());
											inserUserAssessmentProgram(newUser, user, newUser.getPrimaryGroups(), addedOrgGroup, new Boolean(true));					
											}
										
								}else{//Make all other groups isDefault as false
									if(userRole.getActiveFlag() && userRole.isDefault()){
										updateUserOrgGroupByPrimaryKey(userRole.getId(), false, true);	
										newUser.getUser().setAssessmentProgramId(newUser.getPrimaryAssessmentProgramId());
										List<UserAssessmentPrograms> uaps = userAssessProgDao.getByUserGroupIdOrgIds(userId, 
												userRole.getGroupId(), userOrganization.getOrganizationId());
										if(uaps != null && !uaps.isEmpty()){
											// update existing uap
											updateUserAssessmentProgramToDefault(newUser.getUser(), organization.getId(), userRole.getGroupId(), false, userRole.getId());
										} else {
											UserOrganizationsGroups addedOrgGroup = userOrganizationsGroupsService.getUserOrganizationsGroups(userRole.getId());
											inserUserAssessmentProgram(newUser, user, newUser.getPrimaryGroups(), addedOrgGroup, new Boolean(true));	
											}
									}
								}
							}
								if(isNewGrp){  //Means new group , add as primary group									
									UserOrganizationsGroups addedOrgGroup = insertUserOrgGroup(user.getId(), organization.getId(), status, true, newUser.getPrimaryGroups().getGroupId());
									List<Long> allUserAssessmentPrgIds = userAssessProgDao.getAllUserAssessmentProgramIds(user.getId());
									for(Long userOrgApId : allUserAssessmentPrgIds){
										UserAssessmentPrograms userAssessmentProgram = new UserAssessmentPrograms();
										userAssessmentProgram.setId(userOrgApId);
										// don't use audit column properties here as it is fetching inactive one's
//										userAssessmentProgram.setAuditColumnPropertiesForUpdate();
										userAssessmentProgram.setModifiedDate(new Date());
										userAssessmentProgram.setModifiedUser(currentUser.getUserId());
										userAssessmentProgram.setIsDefault(false);
										userAssessProgDao.updateByPrimaryKeySelective(userAssessmentProgram);
									}
									inserUserAssessmentProgram(newUser, user, newUser.getPrimaryGroups(), addedOrgGroup, new Boolean(true));
								}					
								
								
							}							
							isNewGrp = true;
							if(newUser.getSecondaryGroups() != null){                    //Secondary Role
								for (UserRoles userRole : userOrganization.getRoles()) {
									//Check already Roes is present or not
									
									if(userRole.getGroupId() == newUser.getSecondaryGroups().getGroupId()){
										isNewGrp = false;
										if(!userRole.getActiveFlag()){    
											//Check active flag ,If present make activeflag as true 
											updateUserOrgGroupByPrimaryKey(userRole.getId(), false, true);						    
										}											
									}
								}
								if(isNewGrp){    ////Means new group , add as secondary group
									UserOrganizationsGroups addedSecOrgGroup = insertUserOrgGroup(user.getId(), organization.getId(), status, false, newUser.getSecondaryGroups().getGroupId());
									inserUserAssessmentProgram(newUser, user, newUser.getSecondaryGroups(), addedSecOrgGroup, new Boolean(false));
								}								
															
							}		
				}else{//Make all other organization's isDefault as false(Because latest uploaded one is default one) 
					updateUsersOrganizationsByPrimaryKey(userOrganization.getId(), false, true);
				}
					
				
				}
				
				if(insertGroup){   //Means new organization ,not present in DB
					//Add newOrganization
					addUsersOrganizations(user.getId(), organization.getId(), true);
					//Add primary roles
                   if(newUser.getPrimaryGroups() != null){
                	   UserOrganizationsGroups addedOrgGroup = insertUserOrgGroup(user.getId(), organization.getId(), status, false, newUser.getPrimaryGroups().getGroupId());
                	   List<Long> allUserAssessmentPrgIds = userAssessProgDao.getAllUserAssessmentProgramIds(user.getId());
						for(Long userOrgApId : allUserAssessmentPrgIds){
							UserAssessmentPrograms userAssessmentProgram = new UserAssessmentPrograms();
							userAssessmentProgram.setId(userOrgApId);
							// don't use audit column properties here as it is fetching inactive one's
							//  userAssessmentProgram.setAuditColumnPropertiesForUpdate();
							userAssessmentProgram.setModifiedDate(new Date());
							userAssessmentProgram.setModifiedUser(currentUser.getUserId());
							userAssessmentProgram.setIsDefault(false);
							userAssessProgDao.updateByPrimaryKeySelective(userAssessmentProgram);
						}
                	   inserUserAssessmentProgram(newUser, user, newUser.getPrimaryGroups(), addedOrgGroup, new Boolean(true));
					}
					//Add Secondary roles
					if(newUser.getSecondaryGroups() != null){
						if(newUser.getPrimaryGroups() == null || 
								(newUser.getPrimaryGroups() != null && newUser.getPrimaryGroups() != newUser.getSecondaryGroups())){
							UserOrganizationsGroups addedSecOrgGroup = insertUserOrgGroup(user.getId(), organization.getId(), status, false, newUser.getSecondaryGroups().getGroupId());
							inserUserAssessmentProgram(newUser, user, newUser.getSecondaryGroups(), addedSecOrgGroup, new Boolean(false));
      						}
					}
					
				}
				
				}
				
				//Added for domain audit history table
				userJson = userDao.getJsonFormatData(user.getId());
				
				if(userJson != null)
				userAfterUpdate= userJson.buildJsonString();
			}
			else {
				insert = true;
				/*
				 * This means that this is a new user for the system. So add
				 * them to the database, and create their userGroup.
				 */
				// No need to check in the system for the user's identifier as
				// that is already done.
				// logger.debug("UploadedUser's identifier is empty, and therefore no further checking needs to be done."
				// +
				// " Adding to the datbase");
				newUser.setUserName(newUser.getEmail());
				// add method call to generate the user's logical identifier
				Date now = new Date();
				user = newUser.getUser();
				user.setCreatedDate(now);
				user.setModifiedDate(now);
				user.setCreatedUser(currentUser.getUserId());
				user.setModifiedUser(currentUser.getUserId());
				user.setSourceType(SourceTypeEnum.UPLOAD.getCode());
				User toAdd = user ; 
				toAdd.setPassword(passwordEncoder.encodePassword(toAdd.getPassword(),
						toAdd.getSalt()));
				if (StringUtils.isNotEmpty(toAdd.getUserName())) {
					toAdd.setUserName(toAdd.getUserName().toLowerCase());
				}
				if (StringUtils.isNotEmpty(toAdd.getEmail())) {
					toAdd.setEmail(toAdd.getEmail().toLowerCase());
				}
				userDao.add(toAdd);
				user = toAdd;
				//user = addUserWithAudit(newUser.getUser(), currentUser);
				
				Groups grp = newUser.getPrimaryGroups();// Primary role
				/*if( grp == null)
					grp = groupsService.getGroupByName("Teacher");
				*/
				UserOrganizationsGroups addedOrgGroup = addUserOrganizationForUpload(user, organization.getId(),
				(grp == null ? null : grp.getGroupId()), currentUser,true);
				
				/**
				 * US16239 Add user assessment program
				 * */
				inserUserAssessmentProgram(newUser, user, grp, addedOrgGroup, new Boolean(true));
				
				Groups secondaryGrp = newUser.getSecondaryGroups();// Secondary role
				UserOrganizationsGroups addedSecOrgGroup=null;
				if( secondaryGrp != null ){
					addedSecOrgGroup = addUserOrganizationForUpload(user, organization.getId(),
							(secondaryGrp == null ? null : secondaryGrp.getGroupId()), currentUser, false);
					inserUserAssessmentProgram(newUser, user, secondaryGrp, addedSecOrgGroup, new Boolean(false));

				}
				
				//Added for domain audit history table
				User userJson = userDao.getJsonFormatData(user.getId());
				
				if(userJson != null)
				   userAfterUpdate= userJson.buildJsonString();
				
				try {
			        UUID activationNo = UUID.randomUUID();
			        Calendar nowC = GregorianCalendar.getInstance();

			        nowC.add(Calendar.DATE, numActivationDays);
			      
			        addedOrgGroup.setActivationNo(activationNo.toString());
			        addedOrgGroup.setActivationNoExpirationDate(nowC.getTime());
			        addedOrgGroup.setAuditColumnPropertiesForUpdate();
			        //userOrganizationsGroupsService.updateUserOrganizationsGroups(userOrganizationsGroups);
			        if (addedOrgGroup.getUserOrganizationId() != 0) {
			            userOrganizationsGroupsDao.updateUserOrganizationsGroups(addedOrgGroup);
			        } else {
			        	logger.debug("Found userOrganizationId=0 when trying to update userorganizationgroup records for userId="+
			        			addedOrgGroup.getUserId()+" and organizationId="+addedOrgGroup.getOrganizationId());
			        }
			        if((addedOrgGroup.getUserOrganizationId() != 0)  && (addedSecOrgGroup != null)){
			        	addedSecOrgGroup.setActivationNo(activationNo.toString());
			        	addedSecOrgGroup.setActivationNoExpirationDate(nowC.getTime());
			        	addedSecOrgGroup.setAuditColumnPropertiesForUpdate();
			        	 userOrganizationsGroupsDao.updateUserOrganizationsGroups(addedSecOrgGroup);
			        }
			        
			        Organization contractingOrganization=organizationService.getContractingOrganization(newUser.getOrganizationId());
			        addedOrgGroup.getUser().setAssessmentProgramId(newUser.getPrimaryAssessmentProgramId());
			        addedOrgGroup.getUser().setContractingOrganization(contractingOrganization);
			        emailService.sendUserActivationMsgUpload(addedOrgGroup);
					
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage());
				}
				
			}
		}
		
		/**
		 * US16533 : to track audit history 
		 */ 
		//addtoDomainAuditHistory(user.getId(), user.getModifiedUser(),insert, SourceTypeEnum.UPLOAD.getCode());
			
			insertIntoDomainAuditHistory(user.getId(),user.getModifiedUser(),insert ? EventTypeEnum.INSERT.getCode():EventTypeEnum.UPDATE.getCode(),SourceTypeEnum.UPLOAD.getCode(),userBeforeUpdate,userAfterUpdate);
		return newUser;
	}

	private void inserUserAssessmentProgram(UploadedUser newUser, User user, Groups grp,
			UserOrganizationsGroups addedOrgGroup, Boolean isDefault) {
		UserAssessmentPrograms userAssessmentPrograms = new UserAssessmentPrograms();
		userAssessmentPrograms.setAartUserId(user.getId());
		userAssessmentPrograms.setAssessmentProgramId(newUser.getPrimaryAssessmentProgramId());
		userAssessmentPrograms.setActiveFlag(true);
		userAssessmentPrograms.setUserOrganizationGroupId(addedOrgGroup.getId());
		userAssessmentPrograms.setGroupId(grp.getGroupId());
		userAssessmentPrograms.setIsDefault(isDefault);
		userAssessmentPrograms.setAuditColumnProperties();
		userAssessProgDao.insertSelective(userAssessmentPrograms);
	}

	/**
	 * US16533 : To add the audit history for upload
	 * @param objectId
	 * @param createdUserId
	 * @param isInsert
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addtoDomainAuditHistory(Long objectId, Long createdUserId,boolean isInsert, String source){
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(source);
		domainAuditHistory.setObjectType("USER");
		domainAuditHistory.setObjectId(objectId);
		domainAuditHistory.setCreatedUserId( createdUserId.intValue() );
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(isInsert ? "INSERT":"UPDATE");
		
		domainAuditHistoryDao.insert(domainAuditHistory);
	}
	 
	
	/**
	 * Prasanth :  US16246 &  US16252 : User upload using spring batch and
	 * added  Primary and Secondary role in the upload csv file  
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private UserOrganizationsGroups addUserOrganizationForUpload(User user, Long organizationId,
						Long groupId,UserDetailImpl currentUser, boolean isDefult ){
		UserOrganizationsGroups usersOrganizations = new UserOrganizationsGroups();
		usersOrganizations.setUserId(user.getId());
		usersOrganizations.setOrganizationId(organizationId);
		
		usersOrganizations.setGroupId(groupId);
		usersOrganizations.setStatus(UserStatus.PENDING);
		usersOrganizations.setUser(user);
		Date now = new Date();
		usersOrganizations.setCreatedDate(now);
		usersOrganizations.setModifiedDate(now);
		usersOrganizations.setCreatedUser(currentUser.getUserId());
		usersOrganizations.setModifiedUser(currentUser.getUserId());
		usersOrganizations.setIsdefault(isDefult);
		
		userOrganizationsGroupsDao.addUsersOrganizations(usersOrganizations);
        if(usersOrganizations.getGroupId() != null) {
            userOrganizationsGroupsDao.addUserOrganizationsGroups(usersOrganizations);
        }
        return usersOrganizations;
	}
	
		/**
	 * @param toUpdate
	 *            {@link User}
	 * @return {@link User}
	 * @throws ServiceException
	 *             ServiceException
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User update(User toUpdate) throws ServiceException {
		// TODO this will not do roll back.
		if (userDao.update(toUpdate) != 1) {
			throw new ServiceException("update() returned more than one row");
		}
		return toUpdate;
	}

	/**
	 * Changed for US-14985
	 * @param toUpdate
	 *            {@link User}
	 * @param isReset
	 *            {@link boolean}
	 * @param authToken
	 *            {@link String}
	 * @return {@link User}
	 * @throws ServiceException
	 *             ServiceException
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User updateWithEncodedPassword(User toUpdate, boolean isReset,
			String authToken) throws ServiceException {
		String password = toUpdate.getPassword();
		String encodedPassword = passwordEncoder.encodePassword(password,
				toUpdate.getSalt());
		//code added for DE14932 start
		toUpdate.setPassword(encodedPassword);
		String email = toUpdate.getEmail();
	   	String userDomainName = email.substring(email.indexOf('@') + 1); 
		boolean internalUser = false;	
	   	if(internalUsersDomains!=null && !internalUsersDomains.equals("") && !internalUsersDomains.isEmpty()){
	   		String[] domainNamesList =  internalUsersDomains.split(",");
		    if(domainNamesList!=null && domainNamesList.length>0){
		    	for(String internalDomainName : domainNamesList){					
					if(userDomainName.equals(internalDomainName)){
						internalUser=true;
					}
				}
		    } 		
	   	}
	   	toUpdate.setInternaluserindicator(internalUser);
		//code added for DE14932 end
		if (userDao.updateSelectiveByPrimaryKey(toUpdate) != 1) {
			throw new ServiceException(
					"updateWithEncodedPassword() returned more than one row");
		}
		if (isReset) {
			UserPasswordReset userPasswordReset = new UserPasswordReset();
			userPasswordReset.setAartUserId(toUpdate.getId());
			userPasswordReset.setActiveFlag(false);
			userPasswordReset.setAuthToken(authToken);
			userPasswordReset.setRequestType("ACTIVATION");
			updateUserPasswordResetDetails(userPasswordReset);
		}
		//for US17687 for audit user
		insertIntoDomainAuditHistory(toUpdate.getId(), toUpdate.getId(),EventTypeEnum.PASSWORD_CHANGE.getCode(), SourceTypeEnum.MANUAL.getCode(), null, null);	
		
		setpasswordHistory(toUpdate);
		return toUpdate;
	}
	/**
	 * Added for US-14985
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void setpasswordHistory(User user) throws ServiceException{
		
	
		userPasswordResetObj.setAartUserId(user.getId());
		userPasswordResetObj.setPassword(user.getPassword());
		userPasswordResetObj.setRequestType("HISTORY");
		userDao.setUserPasswordHistory(userPasswordResetObj);
	}
	
	/**
	 * Added for US-14985
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ArrayList<User> getPastYearPasswordHistory(Long userId, int days) throws ServiceException{
	    return userDao.getPastYearPasswordHistory(userId,days);
	}
	

	/**
	 * 
	 * 1. The educator is searched. 2. If found by educator identifier then the
	 * name in the roster upload is compared to the name of the user. 3. As a
	 * result of step 2, if names are different then the user's information is
	 * updated. 4. As a result of step 1, if no educator is found. Create a user
	 * in soft deleted state.
	 * 
	 * @param educator
	 *            User
	 * @param organizations
	 *            {@link List}
	 * @param userOrganization
	 *            {@link Organization}
	 * @return {@link User}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User addIfNotPresent(User educator,
			Collection<Organization> organizations,
			Organization userOrganization) {
		User userFoundOrAdded = null;
		if (educator != null
				&& StringUtils.isNotEmpty(educator.getUniqueCommonIdentifier())) {
			// find all users irrespective of active flag.

			/*
			 * List<User> usersFound =
			 * userDao.getByUniqueCommonIdentifierAndTree(
			 * educator.getUniqueCommonIdentifier(),
			 * AARTCollectionUtil.getIds(organizations), null );
			 */
			List<User> usersFound = userDao.getByUniqueCommonIdentifierAndTree(
					educator.getUniqueCommonIdentifier(), new ArrayList<Long>(
							Arrays.asList(userOrganization.getId())), null);
			if (CollectionUtils.isNotEmpty(usersFound)
					&& usersFound.size() == 1) {
				// The educator is found with in the user's tree and can be
				// reused.
				User userFound = usersFound.get(0);
				if (userFound.getFirstName() != null
						&& !userFound.getFirstName().equals(
								educator.getFirstName())
						|| userFound.getSurName() != null
						&& !userFound.getSurName()
								.equals(educator.getSurName())) {
					// INFO: State provided information in the roster takes
					// precedence over
					// User provided information when creating the account.
					userFound.setFirstName(educator.getFirstName());
					userFound.setSurName(educator.getSurName());
					userDao.updateSelectiveByPrimaryKey(userFound);
				}
				userFoundOrAdded = userFound;
				addUserOrganizationGroupsIfNotPresent(userFoundOrAdded, userOrganization);
			} else if (CollectionUtils.isNotEmpty(usersFound)) {
				// multiple found so don't change any names.
				logger.debug(" multiple users found for "
						+ educator.getUniqueCommonIdentifier());
				userFoundOrAdded = usersFound.get(0);
				addUserOrganizationGroupsIfNotPresent(userFoundOrAdded, userOrganization);
			} else {
				userFoundOrAdded = userDao.getByEmail(educator.getEmail());
				if (null == userFoundOrAdded) {
					// The check for if the educator's uniqueCommonIdentifier
					// exists already in the
					// entire contracting organization should have been done at
					// this point
					// The user is added in soft-deleted state.
					educator.setActiveFlag(true);
					userFoundOrAdded = add(educator);
					// The user is added to the organization in soft deleted
					// state.
					addUserToOrganization(userFoundOrAdded,
							userOrganization.getId(), educator.getCreatedUser(), true);
				} else {
					List<UserOrganizationsGroups> grps = userOrganizationsGroupsService
							.getByUserIdAndOrganization(
									userFoundOrAdded.getId(),
									userOrganization.getId());

					if (CollectionUtils.isEmpty(grps)) {
						// The user is added to the organization in soft deleted
						// state.
						addUserToOrganization(userFoundOrAdded,
								userOrganization.getId(),
								educator.getCreatedUser(), true);
					}
				}
			}
			
			//add assessment programs
			List<String> apCodes = educator.getAssessmentProgramCodes();
			if(userFoundOrAdded !=null && apCodes != null && apCodes.size() > 0) {
				List<UserAssessmentPrograms> userAssessmentPrgs = userAssessProgDao.selectByUserId(userFoundOrAdded.getId());
				List<Long> apIds = new ArrayList<Long>(apCodes.size());
				for(String apCode: apCodes) {
					AssessmentProgram ap = assessProgDao.findByAbbreviatedName(apCode);
					apIds.add(ap.getId());
				}
				boolean found=false;
				for(Long apId: apIds) {
					found=false;
					for(UserAssessmentPrograms uap: userAssessmentPrgs) {
						if(uap.getAssessmentProgramId().equals(apId)) {
							found=true;
							break;
						}
					}
					if(!found) {
						addAssessmentPrograms(userFoundOrAdded.getId(), apId, null, false);
					}
				}
			}
		}
		if(educator.getAssessmentProgramId() != null) {
			userFoundOrAdded.setAssessmentProgramId(educator.getAssessmentProgramId());	
			addIfUserAssesmentIsNotPresent(userFoundOrAdded, null, false);
		}
		return userFoundOrAdded;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User addIfNotPresent(User educator, Long stateId, Organization userOrganization) {
		User userFoundOrAdded = null;
		if (educator != null && StringUtils.isNotEmpty(educator.getUniqueCommonIdentifier())) {
			userFoundOrAdded = userDao.getByUniqueCommonIdentifierAndState(educator.getUniqueCommonIdentifier(), stateId, true); //find active user
			
			if (userFoundOrAdded != null) {
				addUserOrganizationGroupsIfNotPresent(userFoundOrAdded, userOrganization);
			} else {
				userFoundOrAdded = userDao.getByUniqueCommonIdentifierAndState(educator.getUniqueCommonIdentifier(), stateId, false); //if no active look for inactive
				if (userFoundOrAdded != null) {
					addUserOrganizationGroupsIfNotPresent(userFoundOrAdded, userOrganization);
				} else {
					userFoundOrAdded = userDao.getByEmail(educator.getEmail());
					if (userFoundOrAdded == null) {
						educator.setActiveFlag(true);
						userFoundOrAdded = add(educator);
						addUserToOrganization(userFoundOrAdded, userOrganization.getId(), educator.getCreatedUser(), true);
					} else {
						addUserOrganizationGroupsIfNotPresent(userFoundOrAdded, userOrganization);
					}
				}
			}
			
			//add assessment programs
			List<String> apCodes = educator.getAssessmentProgramCodes();
			if(userFoundOrAdded !=null && apCodes != null && apCodes.size() > 0) {
				List<UserAssessmentPrograms> userAssessmentPrgs = userAssessProgDao.selectByUserId(userFoundOrAdded.getId());
				List<Long> apIds = new ArrayList<Long>(apCodes.size());
				for(String apCode: apCodes) {
					AssessmentProgram ap = assessProgDao.findByAbbreviatedName(apCode);
					apIds.add(ap.getId());
				}
				boolean found=false;
				for(Long apId: apIds) {
					found=false;
					for(UserAssessmentPrograms uap: userAssessmentPrgs) {
						if(uap.getAssessmentProgramId().equals(apId)) {
							found=true;
							break;
						}
					}
					if(!found) {
						addAssessmentPrograms(userFoundOrAdded.getId(), apId, null, false);
					}
				}
			}
		}
		if(educator.getAssessmentProgramId() != null) {
			userFoundOrAdded.setAssessmentProgramId(educator.getAssessmentProgramId());	
			addIfUserAssesmentIsNotPresent(userFoundOrAdded, null, false);
		}
		return userFoundOrAdded;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private UserOrganizationsGroups addUserOrganizationGroupsIfNotPresent(User userFoundOrAdded, Organization userOrganization) {
		UserOrganizationsGroups userOrgGroups = new UserOrganizationsGroups();
		//Add user organization if user organization is not all ready present
		addUserOrganizationIfNotPresent(userFoundOrAdded, userOrganization);
		
		List<UserOrganizationsGroups> grps = userOrganizationsGroupsService
				.getByUserIdAndOrganization(
						userFoundOrAdded.getId(),
						userOrganization.getId());		
		if (CollectionUtils.isEmpty(grps) || !isTeacherRoleExists(grps)) {			
			Groups teacherGroup = groupsService.getGroupByName("Teacher");
			if(teacherGroup != null) {				
				userOrgGroups.setUserId(userFoundOrAdded.getId());
				userOrgGroups.setOrganizationId(userOrganization.getId());
				userOrgGroups.setUserOrganizationId(userOrganization.getId());
				userOrgGroups.setGroupId(teacherGroup.getGroupId());
				if(CollectionUtils.isNotEmpty(grps)){
					userOrgGroups.setIsdefault(false);
				}else{
				userOrgGroups.setIsdefault(true);
				}
				
				userOrgGroups.setStatus(3);							
				userOrganizationsGroupsDao.addUserOrganizationsGroups(userOrgGroups);
				saveActivationNo(userOrgGroups, UUID.randomUUID().toString());
			}			
		}
		return userOrgGroups;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addUserOrganizationIfNotPresent(User userFoundOrAdded, Organization userOrganization) {
		
		//Add user organization if user organization is not all ready present
		UserOrganizationsGroups userOrg = new UserOrganizationsGroups();
		Date now = new Date();
		userOrg.setUserId(userFoundOrAdded.getId());
		userOrg.setOrganizationId(userOrganization.getId());
		userOrg.setCreatedDate(now);
		userOrg.setModifiedDate(now);
		userOrg.setIsdefault(true);
		userOrganizationsGroupsDao.addUsersOrganizations(userOrg);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addIfUserAssesmentIsNotPresent(User userFound, Long userOrgGrpId, Boolean setDefault) {
		if(userFound.getAssessmentProgramId() != null && userFound.getId() != null) {
			boolean userHasAssessmentProgramExisted = userAssessProgDao.isUserInAssessmentProgram(userFound.getId(), userFound.getAssessmentProgramId(), userOrgGrpId);
			if(!userHasAssessmentProgramExisted) {				
				addAssessmentPrograms(userFound.getId(), userFound.getAssessmentProgramId(), userOrgGrpId, setDefault);
			}
		}
	}
	
	/**
	 * @param organizationId
	 *            long
	 * @param stateId
	 *            {@link String}
	 * @return List<User>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<User> getByOrganizationAndUniqueCommonIdentifier(
			long organizationId, String stateId) {
		return userDao.getByOrganizationAndUniqueCommonIdentifier(
				organizationId, stateId);
	}

	/**
	 * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record
	 * Browser View Get users list by selected organization.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<User> getByOrganization(Map<String, Object> criteria,
			String sortByColumn, String sortType, Integer offset, Integer limitCount) {
		criteria.put("limitCount", limitCount);
		criteria.put("offset", offset);
		criteria.put("sortByColumn", sortByColumn);
		criteria.put("sortType", sortType);
		return userDao.getByOrganization(criteria);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<User> getByOrganizationWithRosterAssigned(
			Map<String, Object> criteria, String sortByColumn, String sortType, Integer offset,
			Integer limitCount, long rosterId) {
		criteria.put("limitCount", limitCount);
		criteria.put("offset", offset);
		criteria.put("sortByColumn", sortByColumn);
		criteria.put("sortType", sortType);
		criteria.put("rosterId", rosterId);
		return userDao.getByOrganizationWithRosterAssigned(criteria);
	}

	/**
	 * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record
	 * Browser View Get count of users list by selected organization.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Integer countAllUsersToView(Map<String, Object> criteria) {
		return userDao.countAllUsersToView(criteria);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Integer countAllTeacherToView(Map<String, Object> criteria){
		return userDao.countAllTeacherToView(criteria);
	}

	/**
	 * This method finds shell users created by the SCRS upload.
	 * 
	 * @param uniqueCommonId
	 *            {@link String}
	 * @param organizations
	 *            Collection<Organization>
	 * @return {@link User}
	 */
	public final User findShellUserByIdentifierAndOrganization(
			String uniqueCommonId, Collection<Organization> organizations) {
		return userDao.findShellUserByIdentifierAndOrganization(uniqueCommonId,
				organizations);
	}

	/**
	 * 1. Verify if the referenced organization can be found. 2. Verify that
	 * organization type in file is valid. 3. Check if there are active users
	 * with that unique identifier.
	 * 
	 * @param uploadedUser
	 *            {@link UploadedUser}
	 * @param org
	 *            {@link Organization}
	 * @param organizationTree
	 *            {@link OrganizationTree}
	 * @return boolean
	 */
	public UploadedUser isValidUserIdentifierAndOrg(UploadedUser uploadedUser,
			Organization org,
			ContractingOrganizationTree contractingOrganizationTree,
			Long parentOrgId, boolean isForInsert) {
		logger.debug("Entering the isValidUserIdentifierAndOrg method");
		logger.debug("UploadedUser: {}", uploadedUser);
		if (org == null) {
			/*
			 * This means that the organization referenced in the file could not
			 * be found. Check if we already have an error for the display
			 * identifier, if not add it.
			 */
			logger.debug(
					"Organization {} not found in AART. Marking user as invalid.",
					uploadedUser.getDisplayIdentifier());
			boolean found = false;
			for (InValidDetail record : uploadedUser.getInValidDetails()) {
				if (FieldName.DISPLAY_IDENTIFIER.toString().equals(
						record.getFieldName())) {
					found = true;
				}
			}

			if (!found) {
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_ORG_IDENTIFIER.toString(),
						uploadedUser.getDisplayIdentifier(), true,
						InvalidTypes.NOT_FOUND);
			}
		} else {

			/**
			 * Biyatpragyan Mohanty : US14308 : User Management Upload Users CSV
			 * User organization should fall on the selected district hierarchy.
			 */
			boolean proceed = false;
			List<Organization> orgChildren = organizationService
					.getAllChildren(parentOrgId, true);
			if (orgChildren != null && orgChildren.size() > 0) {
				for (Organization orgg : orgChildren) {
					if (org.getId().longValue() == orgg.getId().longValue()) {
						proceed = true;
						break;
					}
				}
			}

			if (proceed) {
				/*
				 * Make sure that the organization type specified in the file
				 * matches the org type of the organization in the sytem. This
				 * allows us one more step of validation that the organization
				 * we pulled from the database, and the organization that the
				 * user is referring to are the same.
				 */
				OrganizationType orgtype = orgTypeService.get(org
						.getOrganizationTypeId());
				logger.debug(
						"Checking that the organization type {} specified in the file is valid.",
						uploadedUser.getOrganizationTypeCode());
				if (orgtype != null
						&& orgtype.getTypeCode().equals(
								uploadedUser.getOrganizationTypeCode())) {
					logger.debug("Org type is valid for " + uploadedUser);
				} else {
					if (uploadedUser.getOrganizationTypeCode() != null
							&& !uploadedUser.getOrganizationTypeCode().equals(
									StringUtils.EMPTY)) {
						logger.debug(
								"Organization type code {} does not exist in the system. Marking the user as invalid.",
								uploadedUser.getOrganizationTypeCode());
						uploadedUser.addInvalidField(
								FieldName.UPLOAD_ORG_TYPE_CODE.toString(),
								uploadedUser.getOrganizationTypeCode(), true,
								InvalidTypes.NOT_FOUND);
					}
				}
				// check if the unique common identifier is valid for the user's
				// organization context.
				// TODO we really need to change this to be for the entire
				// user's contracting organization.
				if (!uploadedUser.isInValid()
						&& uploadedUser.getEducatorIdentifier() != null && uploadedUser.getEducatorIdentifier().trim().length() > 0 ) {
					/*
					 * We only need to make the following checks if the user has
					 * a UserIdentifier that is not null. Otherwise we can
					 * freely add them to the database without worrying if their
					 * userIdentifier is unique.
					 */
					logger.debug(
							"Uploaded user has User Identifier {}. Checking that the identifier is valid.",
							uploadedUser.getEducatorIdentifier());
					List<User> foundUsers = new ArrayList<User>();
					
					foundUsers.addAll(userDao
							.getByEducatorIdentifierAndDisplayIdForStateLevel(
									uploadedUser.getEducatorIdentifier(),
									org.getId()));

					
					if (foundUsers != null
							&& CollectionUtils.isNotEmpty(foundUsers)) {
						if(foundUsers.size() == 1 ){
							for (User foundUser : foundUsers) {
								if( foundUser != null  && ! isForInsert ){
									uploadedUser.setExistingUser(foundUser);
								}
								else if (foundUser != null && foundUser.getActiveFlag()
										&& !uploadedUser.isDoReject()) {
									// This means that the unique common identifier
									// is not unique in the organization. Mark it as
									// invalid.
									logger.debug(
											"The user identifier for user {} already exists within the contracting organization.",
											uploadedUser);
									uploadedUser.addInvalidField(
											FieldName.FIELD_EDUCATOR_IDENTIFIER.toString(),
											uploadedUser.getEducatorIdentifier(),
											true, InvalidTypes.NOT_UNIQUE);
								} else if (foundUser != null
										&& !foundUser.getActiveFlag()
										&& !uploadedUser.isDoReject()) {
									logger.debug(
											"Shell user {} was found for uploadedUser {}. Setting shell user'a email, username and active flag",
											foundUser, uploadedUser);
									// set the audit fields.
									uploadedUser.setId(foundUser.getId());
									uploadedUser.setModifiedDate(new Date());
									uploadedUser.setActiveFlag(true);
									// set username same as email.
									uploadedUser.setUserName(uploadedUser
											.getEmail());
									uploadedUser.setFirstName(foundUser
											.getFirstName());
									uploadedUser
											.setLastName(foundUser.getSurName());
	
								}
							}
						}
						else{
							uploadedUser.addInvalidField(
									FieldName.FIELD_EDUCATOR_IDENTIFIER.toString(),
									uploadedUser.getEducatorIdentifier(),
									true, InvalidTypes.NOT_UNIQUE);
						}
					}
					
				}
			} else {
				logger.debug(
						"Organization code {} does not exist in hierarchy. Marking the user as invalid.",
						uploadedUser.getOrganizationTypeCode());
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_ORG_TYPE_CODE.toString(),//"OrganizationLevel",// FieldName.ORG_TYP_CODE.toString(),
						uploadedUser.getOrganizationTypeCode(), true,
						InvalidTypes.NOT_FOUND);
			}

		}
		return uploadedUser;
	}

	/**
	 * 1. Check if the user is found by that email.
	 * 
	 * TODO not done here. IF not found and the uploaded user has no state id,
	 * then create the user.
	 * 
	 * TODO not done here. If not found and the uploaded user has state id, then
	 * ensure there is no other teacher in the contract org tree with that state
	 * id.
	 * 
	 * 4. IF the user is found then the state identifiers must match.
	 * 
	 * @param newUser
	 * @param invalidUsers
	 * @return
	 */
	private UploadedUser isValidUser(UploadedUser newUser, User existingUser) {

		if (existingUser != null && existingUser.getId() > 0
				&& existingUser.getActiveFlag() == true) {

			// If the email already exists in the db, then reject the record.
			logger.debug("User with email {} was found in the system. "
					+ "Reporting error.", existingUser.getEmail());
			/*
			 * newUser.addInvalidField(ParsingConstants.BLANK + FieldName.,
			 * newUser.getEmail());
			 */
			newUser.addInvalidField(FieldName.EMAIL.toString(),
					newUser.getEmail(), true, InvalidTypes.ALREADY_EXISTS);

		}

		return newUser;
	}

	
	/**
	 * @param user
	 * @param organizationId
	 * @param currentUser
	 * @param setDefault
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private UserOrganizationsGroups addUserToOrganization(User user, long organizationId,
			Long currentUser, Boolean setDefault) {
		return addUserToOrganization(user,organizationId,currentUser,setDefault,3);
	}
	
	/**
	 * 
	 * @param user
	 *            User
	 * @param organizationId
	 *            long
	 * @param currentUser
	 *            {@link Long} - the id of the currently logged in user.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private UserOrganizationsGroups addUserToOrganization(User user, long organizationId,
			Long currentUser, Boolean setDefault, int status) {
		UserOrganizationsGroups userOrg = new UserOrganizationsGroups();
		Date now = new Date();
		userOrg.setUserId(user.getId());
		userOrg.setOrganizationId(organizationId);
		userOrg.setUserOrganizationId(organizationId);
		userOrg.setCreatedDate(now);
		userOrg.setModifiedDate(now);		
		Groups teacherGroup = groupsService.getGroupByName("Teacher");
		if(teacherGroup != null) {
			userOrg.setGroupId(teacherGroup.getGroupId());
			userOrg.setIsdefault(setDefault);
			userOrg.setStatus(status);			
		}
		if (currentUser != null) {
			userOrg.setCreatedUser(currentUser);
			userOrg.setModifiedUser(currentUser);
		}

		userOrg = userOrganizationsGroupsService.addUserOrganizationsGroupsForKidsProcess(userOrg);
		saveActivationNo(userOrg, UUID.randomUUID().toString());
		return userOrg;
	}


	/**
	 * Adds the user to the organization alone, if not present already.
	 * 
	 * @param user
	 *            User
	 * @param organizationId
	 *            long
	 * @param uploadedUser
	 *            {@link UploadedUser}
	 * @param currentUser
	 *            {@link UserDetailImpl}
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private UserOrganizationsGroups addUserToOrganization(User user,
			long organizationId, Long groupId, UploadedUser uploadedUser,
			UserDetailImpl currentUser) {
		UserOrganizationsGroups usersOrganizations = new UserOrganizationsGroups();
		usersOrganizations.setUserId(user.getId());
		usersOrganizations.setOrganizationId(organizationId);

		/**
		 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15323 : User upload -
		 * set role to teacher Assign default role as Teacher when user is
		 * uploaded.
		 */
		usersOrganizations.setGroupId(groupId);
		usersOrganizations.setStatus(UserStatus.PENDING);
		usersOrganizations.setUser(user);

		Date now = new Date();
		usersOrganizations.setCreatedDate(now);
		usersOrganizations.setModifiedDate(now);
		usersOrganizations.setCreatedUser(currentUser.getUserId());
		usersOrganizations.setModifiedUser(currentUser.getUserId());
		usersOrganizations.setIsdefault(true);
		return userOrganizationsGroupsService
			.addUserOrganizationsGroups(usersOrganizations);
	}

	/**
	 * Wrapper method around the add(User) method, for adding audit fields. For
	 * use by methods within this same service class.
	 * 
	 * @param user
	 *            {@link User} - the user to add to the database.
	 * @param currentUser
	 *            {@link UserDetailImpl} - the currently logged in user.
	 * @return {@link User} - the user that was added to the database.
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private User addUserWithAudit(User user, UserDetailImpl currentUser) {
		if (currentUser != null) {
			Date now = new Date();
			user.setCreatedDate(now);
			user.setModifiedDate(now);
			user.setCreatedUser(currentUser.getUserId());
			user.setModifiedUser(currentUser.getUserId());
			add(user);
		}
		return user;
	}

	/**
	 * This method checks the database for user records created via the SCRS
	 * upload by user identifier, and an organization.
	 * 
	 * @param uniqueCommonId
	 *            - The user defined id for a user.
	 * @param organizations
	 *            - the organizations the user is associated with.
	 * @return {@link User} - the shell user created via SCRS upload
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private User getScrsUser(String uniqueCommonId,
			Collection<Organization> organizations) {
		logger.trace("Entering the getScrsUser method.");
		User retUser = null;

		retUser = findShellUserByIdentifierAndOrganization(uniqueCommonId,
				organizations);
		logger.debug(
				"Returning shell user {} for uniqueCommonIdentifier {}, and organizations {}",
				new Object[] { retUser, uniqueCommonId, organizations });

		logger.trace("Leaving the getScrsUser method.");
		return retUser;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<UserPasswordReset> getUserPasswordResetDetails(
			UserPasswordReset userPasswordReset) {

		UserPasswordResetCriteria userPasswordResetCriteria = new UserPasswordResetCriteria();
		UserPasswordResetCriteria.Criteria criteria = userPasswordResetCriteria
				.createCriteria();
		criteria.andAartUserIdEqualTo(userPasswordReset.getAartUserId());
		criteria.andAuthTokenEqualTo(userPasswordReset.getAuthToken());
		return userPasswordResetDao
				.selectByUserPasswordResetCriteria(userPasswordResetCriteria);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateUserPasswordResetDetails(
			UserPasswordReset userPasswordReset) {

		UserPasswordResetCriteria userPasswordResetCriteria = new UserPasswordResetCriteria();
		UserPasswordResetCriteria.Criteria criteria = userPasswordResetCriteria
				.createCriteria();
		criteria.andAartUserIdEqualTo(userPasswordReset.getAartUserId());
		criteria.andAuthTokenEqualTo(userPasswordReset.getAuthToken());
		return userPasswordResetDao.updateByUserPasswordResetCriteriaSelective(
				userPasswordReset, userPasswordResetCriteria);

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertUserPasswordResetDetails(
			UserPasswordReset userPasswordReset) {
		return userPasswordResetDao.insert(userPasswordReset);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.UserService#
	 * verifyUserIfPresentOutsideCurrentUserOrgHierarchy
	 * (edu.ku.cete.domain.User, java.util.List)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<? extends UserRecord> verifyEducatorIds(
			List<? extends UserRecord> givenUserRecords,
			Collection<Long> diffContractingOrganizationIds) {

		List<User> educatorsFound = null;
		List<String> educatorIdentifiers = AARTCollectionUtil.getIds(
				givenUserRecords, FieldName.FIELD_EDUCATOR_IDENTIFIER
						+ ParsingConstants.BLANK);

		if (diffContractingOrganizationIds != null
				&& CollectionUtils.isNotEmpty(diffContractingOrganizationIds)
				&& CollectionUtils.isNotEmpty(educatorIdentifiers)) {
			educatorsFound = new ArrayList<User>();
			// DE5505 - added because the size of this list is greater than the
			// max size of an in clause
			// for the postgres java driver - it uses Integer for the size of
			// the clause
			// splitting up the calls in chunks
			int orgIdsLength = diffContractingOrganizationIds.size();
			if (orgIdsLength > inClauseLimit) {
				int numberOfCalls = orgIdsLength / inClauseLimit;
				int remainder = orgIdsLength % inClauseLimit;
				if (remainder > 0)
					numberOfCalls++;
				for (int i = 0; i < numberOfCalls; i++) {
					int from = i * inClauseLimit;
					int to = ((i + 1) * inClauseLimit) - 1;
					if (i == numberOfCalls - 1)
						to = orgIdsLength - 1;
					educatorsFound
							.addAll(userDao
									.getByUniqueCommonIdentifiersAndOrgIdsWithNoStatus(
											educatorIdentifiers,
											((ArrayList<Long>) diffContractingOrganizationIds)
													.subList(from, to)));
				}
			} else {
				educatorsFound.addAll(userDao
						.getByUniqueCommonIdentifiersAndOrgIdsWithNoStatus(
								educatorIdentifiers,
								diffContractingOrganizationIds));
			}
		} else if (diffContractingOrganizationIds != null
				&& CollectionUtils.isNotEmpty(diffContractingOrganizationIds)) {
			logger.debug("No educator identifiers need to be verified ");
		} else {
			logger.debug("User's organization tree is same or bigger than contracting org tree ");
		}
		if (educatorsFound != null
				&& CollectionUtils.isNotEmpty(educatorsFound)) {
			// put the given educators in a map for easy lookup.
			Map<String, List<UserRecord>> userRecordMap = new HashMap<String, List<UserRecord>>();
			for (UserRecord userRecord : givenUserRecords) {
				if (userRecord != null
						&& userRecord.getUser() != null
						&& userRecord.getUser().getUniqueCommonIdentifier() != null) {
					if (userRecordMap.containsKey(userRecord.getUser()
							.getUniqueCommonIdentifier())) {
						// check if it is in the map and add to the existing
						// map.
						userRecordMap.get(
								userRecord.getUser()
										.getUniqueCommonIdentifier()).add(
								userRecord);
					} else {
						// it is not in the map so add the educator to the map
						userRecordMap.put(userRecord.getUser()
								.getUniqueCommonIdentifier(),
								new ArrayList<UserRecord>());
						userRecordMap.get(
								userRecord.getUser()
										.getUniqueCommonIdentifier()).add(
								userRecord);
					}
				}
			}

			for (User educatorFound : educatorsFound) {
				// for each(invalid) educator found check if it needs to be
				// marked
				logger.debug("Found user -" + educatorFound);
				if (educatorFound != null) {
					// The user is found , active, inactive or shell is unknown.
					logger.debug("User is rejected because identifer"
							+ " exists for another organization under same contracting organization -"
							+ educatorFound.getUniqueCommonIdentifier());
					List<UserRecord> givenEducators = userRecordMap
							.get(educatorFound.getUniqueCommonIdentifier());
					for (UserRecord givenEducator : givenEducators) {
						if (educatorFound.getActiveFlag()) {
							givenEducator
									.addInvalidField(
											ParsingConstants.BLANK
													+ FieldName.EDUCATOR_OR_USER_IDENTIFIER,
											ParsingConstants.BLANK
													+ educatorFound
															.getUniqueCommonIdentifier(),
											true, InvalidTypes.NOT_UNIQUE);
						} else {
							logger.debug("User is rejected because identifer"
									+ " exists for another organization in inactive state under same contracting organization "
									+ " and needs to be activated by the state administrator"
									+ educatorFound.getUniqueCommonIdentifier());
							givenEducator
									.addInvalidField(
											ParsingConstants.BLANK
													+ FieldName.EDUCATOR_OR_USER_IDENTIFIER,
											ParsingConstants.BLANK
													+ educatorFound
															.getUniqueCommonIdentifier(),
											true,
											InvalidTypes.NOT_UNIQUE_INACTIVE);
						}
					}
				}
			}

		}

		return givenUserRecords;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.UserService#getOrganizationsByUserId(java.lang.Long)
	 */
	@Override
	public final List<Organization> getOrganizationsByUserId(final Long userId) {
		return userDao.getOrganizations(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.UserService#getUsersByOrgId(java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<User> getUsersByOrgId(Long organizationId,
			String sortByColumn, String sortType, Integer offset, Integer limitCount) {
		return userDao.getUsersByOrgId(organizationId, sortByColumn, sortType, offset,
				limitCount);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Long> getUsersByOrgIdForInterim(Long organizationId, Long assessmentProgramId) {
		return userDao.getUsersByOrgIdForInterim(organizationId, assessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Integer countUsersByOrgId(Long organizationId) {
		return userDao.countUsersByOrgId(organizationId);
	}

	/**
	 * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on
	 * Profile Modal Save user security agreement details for assessment
	 * program.
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Integer saveSecurityAgreementInfo(Long aartUserId,
			Long assessmentProgramId, Long schoolYear,
			boolean agreementElection, Date agreementSignedDate,
			String expireDate, String signerName) {
		UserSecurityAgreement userSecurityAgreement = new UserSecurityAgreement();
		userSecurityAgreement.setAartuserid(aartUserId);
		userSecurityAgreement.setAssessmentProgramId(assessmentProgramId);
		userSecurityAgreement.setAgreementElection(agreementElection);
		userSecurityAgreement.setAgreementSignedDate(agreementSignedDate);
		userSecurityAgreement.setExpireDate(expireDate);
		userSecurityAgreement.setSignerName(signerName);
		userSecurityAgreement.setSchoolYear(schoolYear);
		List<UserSecurityAgreement> userSecurityAgreements = userSecurityAgreementDao
				.getSecurityAgreementInfo(aartUserId, assessmentProgramId, schoolYear);
		User user = new User();
		user.setId(aartUserId);
		String beforeUpdate = null;
		String afterUpdate = null;
		int result = -1;
		if (userSecurityAgreements != null && userSecurityAgreements.size() > 0) {
			UserSecurityAgreement retrievedUserSecurityAgreement = userSecurityAgreements
					.get(0);			
			if (retrievedUserSecurityAgreement == null) {
				beforeUpdate = user.buildJsonString();
				result = userSecurityAgreementDao.insert(userSecurityAgreement);
			} else {
				user.setSecurityAgreementSigned(retrievedUserSecurityAgreement.getAgreementElection());
				userSecurityAgreement.setAgreementElection(agreementElection);
				beforeUpdate = user.buildJsonString();
				result = userSecurityAgreementDao
						.updateSelectiveByUserAndAssessment(userSecurityAgreement);
				// above list only contains accepted entries and if user rejects and there is no entry need to insert here. 
				if(result == 0){
					result = userSecurityAgreementDao.insert(userSecurityAgreement);
				}
			}
		} else {
			beforeUpdate = user.buildJsonString();
			result = userSecurityAgreementDao.insert(userSecurityAgreement);
		}
		
		user.setSecurityAgreementSigned(userSecurityAgreement.getAgreementElection());
		afterUpdate =user.buildJsonString();
		insertIntoDomainAuditHistory(aartUserId, aartUserId,EventTypeEnum.SECURITY_AGREEMENT.getCode(), SourceTypeEnum.MANUAL.getCode(), beforeUpdate, afterUpdate);
		
		return result;
	}

	/**
	 * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on
	 * Profile Modal Retrieve user security agreement details for given user and
	 * assessment.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<UserSecurityAgreement> getSecurityAgreementInfo(
			Long aartUserId, Long assessmentProgramId, Long currentSchoolYear) {
		return userSecurityAgreementDao.getSecurityAgreementInfo(aartUserId,
				assessmentProgramId, currentSchoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<UserSecurityAgreement> getSecurityAgreement(
			Long aartUserId) {
		return userSecurityAgreementDao.getSecurityAgreement(aartUserId);
	}
	
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateDefaultOrgAndRole(Long aartUserId,
			Long userOrganizationId, Long userOrganizationGroupId)
			throws ServiceException { 
		userOrganizationsGroupsDao.updateDefaultUserOrganizationAndGroup(
				aartUserId, userOrganizationGroupId, userOrganizationId,aartUserId,new Date());
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<User> getByUniqueCommonIdentifierAndOrgIds(String uci,
			List<Long> orgIds) {
		return userDao.getByUniqueCommonIdentifierAndOrgIdsWithNoStatus(uci,
				orgIds);
	}

	public Date selectLastExpiredPasswordResetDateById(Long userId) {
		return userDao.selectLastExpiredPasswordResetDateById(userId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateLastExpiredPasswordResetDate(Long userId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		return userDao.updateLastExpiredPasswordResetDate(userId,userDetails.getUserId(),new Date());
	}

	public List<UserSecurityAgreement> getDLMActiveUserSecurityAgreementInfo(Long aartUserId, Long assessmentProgramId, Long currentSchoolYear) {
		return userSecurityAgreementDao.getDLMActiveUserSecurityAgreementInfo(aartUserId, assessmentProgramId, currentSchoolYear);
	}
	
/**
 * Added by: Prasanth: User story:US16246: upload primary and secondary role from file
 */
	public void validatePrimarySecondaryRole(List<Groups> groupsList, UploadedUser uploadedUser, boolean isForInsert) {

		String primaryRoleName = uploadedUser.getPrimaryRole();
		String secondaryRoleName = uploadedUser.getSecondaryRole();
		if(  (primaryRoleName != null &&  ! StringUtils.isEmpty(primaryRoleName)) ){
			Groups primaryRole = getValidGroup(groupsList,primaryRoleName);
			if( primaryRole == null )
			{
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_PRIMARY_ROLE_IDENTIFIER.toString(),
						primaryRoleName, true,
						InvalidTypes.NOT_FOUND);
			}
			else{
				uploadedUser.setPrimaryGroups(primaryRole);
				validateSecondaryRole(secondaryRoleName,groupsList,  uploadedUser,primaryRole);
			}
		}
		else if( secondaryRoleName != null &&  ! StringUtils.isEmpty(secondaryRoleName) ){
			if( isForInsert ){
				/* Can not have Secondary role with out primary role */
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_PRIMARY_ROLE_IDENTIFIER.toString(),
						primaryRoleName, true,
						InvalidTypes.EMPTY);
			}
			else{
				/**
				 * If user for update then If Secondary Role need to add if it is not exist
				 */
				validateSecondaryRole(secondaryRoleName,groupsList,  uploadedUser,null);
			}
		}
		//return uploadedUser;
	}
	
	private void validateSecondaryRole(String secondaryRoleName,List<Groups> groupsList, UploadedUser uploadedUser,Groups primaryRole){
		if( secondaryRoleName != null &&  ! StringUtils.isEmpty(secondaryRoleName) ){
			Groups secondaryRole = getValidGroup(groupsList,secondaryRoleName);
			uploadedUser.setSecondaryGroups(secondaryRole);
			if( secondaryRole == null )
			{
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_SECONDARY_ROLE_IDENTIFIER.toString(),
						secondaryRoleName, true,
						InvalidTypes.NOT_FOUND);
			}
			else if(primaryRole != null && ( primaryRole.getGroupId() == secondaryRole.getGroupId() ) ){
				uploadedUser.addInvalidField(
						FieldName.UPLOAD_SECONDARY_ROLE_IDENTIFIER.toString(),
						secondaryRoleName, true,
						InvalidTypes.MULTIPLE_FOUND);
			}
		}
	}
	
	/**
	 * get the user group based on uploaded user role name 
	 */
	private Groups getValidGroup(List<Groups> groupsList, String groupCode){
		
		Groups selGroups = null; 
		for(Groups groups :groupsList){
			if( groupCode.equalsIgnoreCase(groups.getGroupCode()) ) {
				selGroups = groups;
				break;
			}
		}
		return selGroups ;
	}

	
	
	/**
	 * Added for US16243
	 * check the user is DLM authenticated or not 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final boolean checkDLMAuthentication(User user, int currentSchoolYear) {
		Long userId = user.getId();
		String assessmentProgramCode = user.getCurrentAssessmentProgramName();
		
		//securityAgreementFlag=userDao.isSecurityAgreementSigned(userId);
		
		//Changed during DE11643
		List<UserSecurityAgreement> agreements = getDLMActiveUserSecurityAgreementInfo(userId, user.getCurrentAssessmentProgramId(), (long) currentSchoolYear);
		
		boolean isDLMAuthenticated = CollectionUtils.isNotEmpty(agreements);
		
		// For DE17869: I-SMART teacher was not allowed to see student username/password.
		// At time of implementation, DLM is the only one that does PD training,
		// so we need to account for that here.
		if (Boolean.TRUE.equals(isDLMAuthenticated) && "DLM".equals(assessmentProgramCode)) {
			Boolean pdTrainingFlag = userDao.isPdTrainingCompleted(userId, currentSchoolYear);
			isDLMAuthenticated = Boolean.TRUE.equals(pdTrainingFlag);
		}
		return isDLMAuthenticated;
	}

	/**
	 * US16239: Add user associated assessment program in user upload file
	 * To validate the assessment program belongs to user's organization 
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void validateUserAssessmentProgram(UploadedUser uploadedUser){
		
		boolean isValid = false;
		List<OrgAssessmentProgram> orgsAssmentProgs = orgAssessProgDao.findByOrganizationId(uploadedUser.getOrganizationId());
		String assessmentProgramCode = uploadedUser.getPrimaryAssessmentProgram();
		for(OrgAssessmentProgram orgsAssmentProg : orgsAssmentProgs){
			AssessmentProgram assessmentProgram = orgsAssmentProg.getAssessmentProgram();
			if ( assessmentProgram.getAbbreviatedname().equalsIgnoreCase( assessmentProgramCode) ){
				isValid = true;
				uploadedUser.setPrimaryAssessmentProgramId(assessmentProgram.getId());
				break;
			}
		}
		if( !isValid){
			uploadedUser.addInvalidField(
					FieldName.UPLOAD_PRIMARY_ASSESSMENT_PRGM_IDENTIFIER.toString(),
					assessmentProgramCode, true,
					InvalidTypes.NOT_FOUND);
		}
		//return uploadedUser;
	}
	/**
	 * US16239: Add user associated assessment program in user upload file
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addAssessmentPrograms(Long userId, Long assessmentPrgmId, Long userOrgGrpId, Boolean setDefault){
		
		UserAssessmentPrograms userAssessmentPrograms = new UserAssessmentPrograms();
		userAssessmentPrograms.setAartUserId(userId);
		userAssessmentPrograms.setAssessmentProgramId(assessmentPrgmId);
		userAssessmentPrograms.setActiveFlag(true);
		userAssessmentPrograms.setIsDefault(setDefault);
		userAssessmentPrograms.setUserOrganizationGroupId(userOrgGrpId);
		userAssessmentPrograms.setAuditColumnProperties();
		
		int rowsUpdated = userAssessProgDao.updateUserAssessmentProgram(userAssessmentPrograms);
		if(rowsUpdated == 0){
			userAssessProgDao.insert(userAssessmentPrograms);
		}
		
	}
	
	/**
	 * Added for US-14985
	 */
	@Override
	public ArrayList<User> getPasswordExpirationAlertUsers(int noOfDays) {
		return userDao.getPasswordExpirationAlertUsers(noOfDays);
	}


	/**
	 * Added for US-14985
	 */
	@Override
	public ArrayList<User> lastUserPasswordUpdate(User user) throws ServiceException {
		Long userId=user.getId();
		return userDao.lastUserPasswordUpdate(userId);
	}


	/**
	 * Added for US-14985
	 */
	@Override
	public ArrayList<Category> getPasswordPolicyRules(String typeCode)
			throws ServiceException {
		return userDao.getPasswordPolicyRules(typeCode);
	}
	
	/**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int moveRoster(UserRequest userRosterToMove,
			boolean overwrite,String rosterMoveuserIdParam) throws ServiceException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		Long deleteUserRosterId = userRosterToMove.getId();
		
		ArrayList<Groups> saveUserRoleList =new ArrayList<Groups>();
		ArrayList<Groups> purgeUserRoleList = new ArrayList<Groups>();
		Long userIdParam = Long.parseLong(rosterMoveuserIdParam);
		saveUserRoleList = groupsDao.getUserRolesByUserId(userIdParam);
		boolean userGroupName =false;
		/**
		 * Added for Defect DE10318
		 */
		boolean hasUserRosters = false;
		for (Groups groups : saveUserRoleList) {
			if(userRole.equals(groups.getGroupName()))
				userGroupName = true;
		}
		if(!userGroupName)
			throw new ServiceException("User Don't have teacher role");
		
		userIdParam = deleteUserRosterId;
		purgeUserRoleList = groupsDao.getUserRolesByUserId(userIdParam);
		
		for (Groups groups : purgeUserRoleList) {
			if(userRole.equals(groups.getGroupName()))
				hasUserRosters =true;
		}
		if(!hasUserRosters)
			throw new ServiceException("User Don't have Rosters");
		
		Integer noOfRecordsUpdated =  userDao.moveUserRoster(deleteUserRosterId,Integer.parseInt(rosterMoveuserIdParam),userDetails.getUserId());
		return noOfRecordsUpdated;
	}
	
	/**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public  Integer purgeUser(String purgeUserId, UserDetailImpl userDetails) {	
		Long id = Long.parseLong(purgeUserId);
		User user = new User();
		user.setId(id);
		user.setActiveFlag(true);
        String userBeforUpdate = user.buildJsonString();       
		Integer purgeUser=userDao.purgeUser(id,userDetails.getUser().getId(),new Date());
		user.setActiveFlag(false);
		String userAfterUpdate = user.buildJsonString();
		
		insertIntoDomainAuditHistory(id,userDetails.getUser().getId(),EventTypeEnum.MERGE_USER_PURGED.getCode(),SourceTypeEnum.MANUAL.getCode(),userBeforUpdate,userAfterUpdate);
		return purgeUser;
	}
	//Added to fix DE10302
	@Override
	public boolean checkRosterByUserId(String puserId) {
		Long userIdParam = Long.parseLong(puserId);	
		Integer noOfRoster;
		
		noOfRoster=rosterDao.getRosterByTeacher(userIdParam);
		if(noOfRoster != null && noOfRoster >= 1){
			return true;
		}		
		
		return false;
	}
	
	public boolean isPdTrainingCompleted(long userId,  int currentSchoolYear) {
		
		Boolean pdTrainingFlag = userDao.isPdTrainingCompleted(userId, currentSchoolYear);
		if(pdTrainingFlag != null && pdTrainingFlag.equals(Boolean.TRUE)) {				
			return true;
		}
		return false;
	}
	
	public List<ScorersAssignScorerDTO> getScorersByAssessmentProgramId(Long stateId, Long assessmentPrgId, Long districtId,
			Long schoolId){		
		return userDao.getScorersByAssessmentProgramId(stateId, assessmentPrgId, 
				districtId, schoolId); 
	}
	
	public Integer getCountScorersByAssessmentProgramId(Long assessmentProgramId,Long stateId,
			Long districtId,Long schoolId,Long testingProgramId,
			String scorerRoleCode,	Map<String,String> scorerRecordCriteriaMap){
		
		return userDao.getCountScorersByAssessmentProgramId(assessmentProgramId, stateId, 
				districtId, schoolId, testingProgramId,
				scorerRoleCode, scorerRecordCriteriaMap);
	}
	/**
     * Sudhansu.b : Added for US_16821(provide UI TO move Users) & US-17558
	 * @throws ServiceException  
     */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public boolean moveUser(Long userId, Long newOrganizationId, Long oldOrganizationId, UserDetailImpl loggedInUser)
			throws ServiceException {
		int currentSchoolYear = (int) (long) loggedInUser.getUser().getContractingOrganization().getCurrentSchoolYear();

		// Use the same date for all record updates.
		Date modifiedDate = new Date();
		Long modifiedUserId = loggedInUser.getUserId();

		// Just bring rosters for user by org & currentschool year and
		// inactivate all the rosters from source school.
		List<Roster> rosters = rosterService.findRostersforTeacherId(userId, oldOrganizationId, currentSchoolYear);
		for (Roster roster : rosters) {
			roster.setModifiedUser(modifiedUserId);
			roster.setModifiedDate(modifiedDate);
			roster.setActiveFlag(false);
			rosterService.updateByPrimaryKey(roster);
		}

		// Move user roles from source organization to destination.
		User user = get(userId);
		UserRequest userUpdateRequest = new UserRequest();
		userUpdateRequest.setId(userId);
		userUpdateRequest.setAction(EventTypeEnum.MOVE.getCode());
		userUpdateRequest.setEducatorIdentifier(user.getUniqueCommonIdentifier());
		userUpdateRequest.setFirstName(user.getFirstName());
		userUpdateRequest.setLastName(user.getSurName());
		userUpdateRequest.setEmail(user.getEmail());

		// Get user roles and iterate them and prepare new user data
		List<UserAssessmentPrograms> userRoles = getUserRoles(userId,CommonConstants.ORGANIZATION_STATE_CODE,CommonConstants.ORGANIZATION_DISTRICT_CODE,CommonConstants.ORGANIZATION_SCHOOL_CODE);
		userUpdateRequest.setOrganizations(new OrganizationRoleRequest[userRoles.size()]);
		int index = 0;
		boolean foundRolesToUpdate = false;
		for (UserAssessmentPrograms userRole : userRoles) {
			OrganizationRoleRequest organizationRoleRequest = new OrganizationRoleRequest();
			// Replace with new organization id if exists
			if (oldOrganizationId.equals(userRole.getOrganizationId())) {
				organizationRoleRequest.setOrganizationId(newOrganizationId);
				foundRolesToUpdate = true;
			} else {
				organizationRoleRequest.setOrganizationId(userRole.getOrganizationId());
			}
			organizationRoleRequest.setAssessmentProgramId(userRole.getAssessmentProgramId());
			organizationRoleRequest.setRolesIds(new Long[] { userRole.getGroupId() });
			if (userRole.getIsDefault()) {
				organizationRoleRequest.setDefaultRoleId(userRole.getGroupId());
				userUpdateRequest.setDefaultOrgId(organizationRoleRequest.getOrganizationId());
				userUpdateRequest.setDefaultAssessmentProgramId(organizationRoleRequest.getAssessmentProgramId());
				userUpdateRequest.setDefaultRoleId(userRole.getGroupId());
			}
			userUpdateRequest.getOrganizations()[index++] = organizationRoleRequest;
		}
		// Update only when roles found for update.
		if(foundRolesToUpdate){
			saveUser(userUpdateRequest, true, false);
		}
		return foundRolesToUpdate;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User addIfNotPresentForTASC(User educator, Collection<Organization> organizations, Organization userOrganization, String courseStatus) {
		User userFoundOrAdded = null;
		boolean insert = false;
		String afterjsonString = null;
		String beforejsonString = null;
		UserOrganizationsGroups userOrgGroup = null;
		Long userOrgGroupId = null;
		boolean isDefault = false;
		Boolean addTeacherRole = false;
		
		if (educator != null && StringUtils.isNotEmpty(educator.getUniqueCommonIdentifier())) {
			
			//The KSDE rules for submitting TASC state that they may send only the educator ID 
			// and only have to send the email address when the educator ID is all 9's.
			
			//There can be more than one educator with all 9's as educatorid but with different emailids.
			//Educator with all 9s but email different. So must treat as 2 different educators and add this educator as a new educator.
			
			User userFoundByEmailId = userDao.getByEmail(educator.getEmail());
			
			//New EmailID --> New Educator with All 9s as EducatorID  
			if(userFoundByEmailId== null){
				if(!"99".equalsIgnoreCase(courseStatus)){
					//Add educator to system and then add to the respective organizations
					educator.setActiveFlag(true);
					userFoundOrAdded = add(educator);					
					userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_ADDED);
					// The user is added to the organization in soft deleted state.
					userOrgGroup = addUserToOrganization(userFoundOrAdded, userOrganization.getId(), educator.getCreatedUser(), true);
					isDefault = false;
					insert = true;
					addTeacherRole = true;
				}else{
					//Invalid course status
					educator.setSaveStatus(RecordSaveStatus.INVALID_COURSE_STATUS);
					return educator;
					
				}
			}
			else{
				//Educator with the email found in the system. Since not real educator id (all 9s), all verifications based on email. 
				//If email passed through TASC 
				
				if(!"9999999999".equals(userFoundByEmailId.getUniqueCommonIdentifier())){
					userFoundOrAdded = educator;
					userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_ID);
					return userFoundOrAdded;
				}
				else
				{
					if(!"99".equalsIgnoreCase(courseStatus)){
						//Educator with all 9's and email existing in the system. So use the same user. If not teacher for the current school, add as teacher.
						userFoundOrAdded = userFoundByEmailId;
						userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_FOUND);
						User userJson = userDao.getJsonFormatData(userFoundOrAdded.getId());
						if(userJson  != null)
						 beforejsonString = userJson.buildJsonString();
						
						addTeacherRoleForTASC(educator, userFoundOrAdded, userOrganization, courseStatus);
					}
					else{
						//Invalid course status
						educator.setSaveStatus(RecordSaveStatus.INVALID_COURSE_STATUS);
						return educator;
						
					}
					
				}
				
			}
			
			
			if(addTeacherRole){
				if(educator.getAssessmentProgramId() != null && !"99".equalsIgnoreCase(courseStatus)) {
					userFoundOrAdded.setAssessmentProgramId(educator.getAssessmentProgramId());
					if(userOrgGroup != null){
						userOrgGroupId = userOrgGroup.getId();
					}
					addIfUserAssesmentIsNotPresent(userFoundOrAdded, userOrgGroupId, !isDefault);
				}
			}
			
		}
		
		User userJson = userDao.getJsonFormatData(userFoundOrAdded.getId());
		
		if(userJson  != null)
		afterjsonString = userJson.buildJsonString();
		if((beforejsonString!=null && !beforejsonString.equals(afterjsonString)) || afterjsonString!=null)
			insertIntoDomainAuditHistory(userFoundOrAdded.getId(), educator.getModifiedUser(), insert?EventTypeEnum.INSERT.getCode():EventTypeEnum.UPDATE.getCode(), SourceTypeEnum.TASCWEBSERVICE.getCode(), beforejsonString, afterjsonString);
				
		return userFoundOrAdded;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final UserDTO getMonitorCCQScoresDetails(final Long userId) {
		return userDao.getMonitorCCQScoresDetails(userId);
	}
	
	/**
	 * Sudhansu :  US17558 : To stop hard delete   
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private UserOrganizationsGroups insertUserOrgGroup(Long userId,Long organizationId,int status,boolean isDefault,Long grpId){
		UserOrganizationsGroups	userOrgGrp = new UserOrganizationsGroups();
		userOrgGrp.setUserId(userId);
		userOrgGrp.setOrganizationId(organizationId);
		userOrgGrp.setStatus(status);
		userOrgGrp.setIsdefault(isDefault);
		userOrgGrp.setGroupId(grpId);					
		userOrgGrp.setAuditColumnProperties();
		String activationNo = userOrganizationsGroupsDao.getActivationNoByUserId(userId); 
		userOrgGrp.setActivationNo(activationNo);
		Calendar now = GregorianCalendar.getInstance();
	    //Pulling the number of days out of the properties file
	    now.add(Calendar.DATE, numActivationDays);
	    userOrgGrp.setActivationNoExpirationDate(now.getTime());

		userOrganizationsGroupsDao.insertUserOrgGroup(userOrgGrp);
		return userOrgGrp;

	}
	/**
	 * Sudhansu :  US17558 : To stop hard delete   
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addUsersOrganizations(Long userId,Long organizationId,boolean isDefault){
		UserOrganizationsGroups userOrg = new UserOrganizationsGroups();
		userOrg.setUserId(userId);
		userOrg.setOrganizationId(organizationId);
		userOrg.setAuditColumnProperties();
       	userOrg.setIsdefault(isDefault);
		userOrganizationsGroupsDao.addUsersOrganizations(userOrg);
	}
	
	private void addUserOrganization(UserOrganization userOrganization){
		userOrganization.setAuditColumnProperties();
		userOrganizationsGroupsDao.addUsersOrganization(userOrganization);
	}
	/**
	 * Sudhansu :  US17558 : To stop hard delete   
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void updateUsersOrganizationsByPrimaryKey(Long id, boolean status, boolean activeflag) {
		UserOrganizationsGroups userOrg = new UserOrganizationsGroups();
		userOrg = new UserOrganizationsGroups();
		userOrg.setId(id);
		userOrg.setOrganization(new Organization());
		userOrg.setAuditColumnPropertiesForUpdate();
		userOrg.setActiveFlag(activeflag);
		// Do not set to status. We are not using this flag.
		// userOrg.setUserOrganizationDefault(status);
		userOrganizationsGroupsDao.updateUserOrgSelectiveByPrimaryKey(userOrg);
	}
	/**
	 * Sudhansu :  US17558 : To stop hard delete   
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void updateUserOrgGroupByPrimaryKey(Long id,boolean isDefault,boolean activeflag){
		UserOrganizationsGroups	userOrgGrp = new UserOrganizationsGroups();
		userOrgGrp = new UserOrganizationsGroups();
		userOrgGrp.setId(id);
		userOrgGrp.setAuditColumnPropertiesForUpdate();
		// Following two setters required here and do not delete. They are overrides
		userOrgGrp.setActiveFlag(activeflag);						
		userOrgGrp.setIsdefault(isDefault);											
		userOrganizationsGroupsDao.updateUserOrgGrpSelectiveByPrimaryKey(userOrgGrp);
	}	
	/**
	 * Sudhansu :  US17558 : To stop hard delete   
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final void saveUserOrganizationsAndRoles(UserRequest userRequest, User dbUser, boolean newUser,
			Boolean isFindUser, Boolean internalUser) throws ServiceException {
		List<UserOrganization> allUserOrganizations = userOrganizationsGroupsDao
				.getAllUserOrganizationAndRole(dbUser.getId());
		boolean hasActiveRole = false;
		boolean isDeactivated = false;

		List<UserOrganization> newUserOrganizations = new ArrayList<UserOrganization>();
		// Check for user status
		if (CollectionUtils.isNotEmpty(allUserOrganizations) && !newUser) {
			for (UserOrganization uog : allUserOrganizations) {
				List<UserRoles> roles = uog.getRoles();
				for (UserRoles userRole : roles) {
					if (userRole.getStatus() == UserStatus.ACTIVE) {
						hasActiveRole = true;
					}
					if (userRole.getStatus() == UserStatus.INACTIVE) {
						isDeactivated = true;
					}
				}
			}
		} else if(!newUser){
			isDeactivated = true;
		}

		OrganizationRoleRequest[] organizations = userRequest.getOrganizations();

		List<Long> allUserOrganizationIds = new ArrayList<Long>();
		boolean existingUserOrganization = false;
		
		// First process all user organizations
		for (int i = 0; i < organizations.length; i++) {
			if(allUserOrganizations.isEmpty()){
				UserOrganization userOrganization = new UserOrganization();
				userOrganization.setUserId(dbUser.getId());
				userOrganization.setOrganizationId(organizations[i].getOrganizationId());
				userOrganization.setIsDefault(true);
				addUserOrganization(userOrganization);
				newUserOrganizations.add(userOrganization);
				allUserOrganizationIds.add(organizations[i].getOrganizationId());
				allUserOrganizations.add(userOrganization);
			}
			
			for (UserOrganization dbUserOrganization : allUserOrganizations) {
				existingUserOrganization = false;
				// Check organization is present in db or not
				if (organizations[i].getOrganizationId().longValue() == dbUserOrganization.getOrganizationId()
						.longValue()) {
					// Update existing organization as active and is default
					updateUsersOrganizationsByPrimaryKey(dbUserOrganization.getId(), true, true);
					allUserOrganizationIds.add(dbUserOrganization.getOrganizationId());
					existingUserOrganization = true;
				}
				
				// If not exists in user organizations then it should be added newly.
				if (!existingUserOrganization) {
					UserOrganization userOrganization = new UserOrganization();
					userOrganization.setUserId(dbUser.getId());
					userOrganization.setOrganizationId(organizations[i].getOrganizationId());
					userOrganization.setIsDefault(true);
					dbUserOrganization.setRoles(new ArrayList<UserRoles>());
					if(userOrganization.getOrganizationId()!= -999){
						addUserOrganization(userOrganization);
					}
					newUserOrganizations.add(userOrganization);
					allUserOrganizationIds.add(organizations[i].getOrganizationId());
				}
			}
		}
		
		// Add newly created user organizaiton to activelist
		for(UserOrganization userOrganizaiton : newUserOrganizations){
			allUserOrganizations.add(userOrganizaiton);
		}
        
        Set<Long> inactiveUserOrganizationIds = new HashSet<Long>();
		for (int i = 0; i < organizations.length; i++) {
			for (UserOrganization userorganization : allUserOrganizations) {
				if (organizations[i].getOrganizationId().longValue() == userorganization.getOrganizationId()
						.longValue()) {
					// Deactivate the user organizations. (Revoked access to organization for user if not present).
					boolean isActive = false;
					for(Long activeOrganization: allUserOrganizationIds){
						if(activeOrganization.longValue() == userorganization.getOrganizationId().longValue()){
							isActive = true;
						}
					}
					if(!isActive){
						inactiveUserOrganizationIds.add(userorganization.getId());
					}
				}
			}
		}
		for(Long inActiveOrganization: inactiveUserOrganizationIds){
			updateUsersOrganizationsByPrimaryKey(inActiveOrganization, true, false);
		}
		// Now process the user organization groups
				UserOrganizationsGroups activationUserOrg = null;
		List<Long> allActiveUserOrgGrpIds = new ArrayList<Long>();
		String activationNo = newUser ? UUID.randomUUID().toString() : userOrganizationsGroupsDao.getActivationNoByUserId(dbUser.getId());
		if(StringUtils.isBlank(activationNo)) {
			activationNo = UUID.randomUUID().toString();
		}
		for (int i = 0; i < organizations.length; i++) {
			// if an entry exists update to active
			logger.debug(dbUser.getId() +" " + 
					organizations[i].getAssessmentProgramId() +" " + organizations[i].getOrganizationId() +" " + organizations[i].getRolesIds()[0]);
			UserOrganizationsGroups persistedUserOrganizationsGroups = userOrganizationsGroupsDao.getByOrganizationRoleId(dbUser.getId(),
					organizations[i].getOrganizationId(), organizations[i].getRolesIds()[0]);
			// if not create an active entry
			UserOrganizationsGroups userOrganizationsGroups = new UserOrganizationsGroups();
			
			int status = 0;
			if (hasActiveRole) {
				status = UserStatus.ACTIVE;
			} else {
				status = UserStatus.PENDING;
			}
			if (isDeactivated) {
				status = UserStatus.INACTIVE;
			}
			
			if(persistedUserOrganizationsGroups == null){
				
				userOrganizationsGroups.setAuditColumnProperties();
				userOrganizationsGroups.setUserId(dbUser.getId());
				userOrganizationsGroups.setOrganizationId(organizations[i].getOrganizationId());
				userOrganizationsGroups.setGroupId(organizations[i].getRolesIds()[0]);
				userOrganizationsGroups.setIsdefault(true);
				userOrganizationsGroups.setStatus(status);
				userOrganizationsGroups.setUserOrganizationId(organizations[i].getOrganizationId());
				userOrganizationsGroupsDao.addUserOrganizationsGroups(userOrganizationsGroups);
				saveActivationNo(userOrganizationsGroups, activationNo.toString());
				activationUserOrg = userOrganizationsGroups;
			} else {
				userOrganizationsGroups.setId(persistedUserOrganizationsGroups.getId());
				userOrganizationsGroups.setAuditColumnPropertiesForUpdate();
				userOrganizationsGroups.setActiveFlag(true);
				userOrganizationsGroups.setStatus(status);
				if(isFindUser==true){
					userOrganizationsGroups.setUserId(dbUser.getId());
					//userOrganizationsGroups.setOrganizationId(organizations[i].getOrganizationId());
					//saveActivationNo(userOrganizationsGroups, activationNo.toString());
					Calendar now = GregorianCalendar.getInstance();
					now.add(Calendar.DATE, numActivationDays);
					userOrganizationsGroups.setActivationNo(activationNo);
					userOrganizationsGroups.setActivationNoExpirationDate(now.getTime());
					activationUserOrg = userOrganizationsGroups;
				}
				userOrganizationsGroupsDao.updateUserOrgGrpSelectiveByPrimaryKey(userOrganizationsGroups);
			}
			allActiveUserOrgGrpIds.add(userOrganizationsGroups.getId());
			
		}
		// Remove extra user organization groups
		List<Long> allUserOrgGrpIds = userOrganizationsGroupsDao.getAllUserOrganiztionGroupIds(dbUser.getId());
		List<Long> deActivateUserOrgGrpIds = new ArrayList<Long>();
		
		for(Long userOrgGrpId: allUserOrgGrpIds){
			boolean activeFlag = false;
			for(Long activeUserOrgGrpId: allActiveUserOrgGrpIds){
				if(userOrgGrpId.longValue() == activeUserOrgGrpId.longValue()){
					activeFlag = true;
				}
			}
			if(!activeFlag){
				deActivateUserOrgGrpIds.add(userOrgGrpId);
			}
		}
		for(Long userOrgGrpId : deActivateUserOrgGrpIds){
			updateUserOrgGroupByPrimaryKey(userOrgGrpId,false, false);
		}
		
		List<Long> activeUserAssessmentProgramIds = new ArrayList<Long>();
		
		UserAssessmentPrograms defaultUserAssessmentProgram = new UserAssessmentPrograms();
		
		for (int i = 0; i < organizations.length; i++) {
			// if an entry exists update to active
			logger.debug(dbUser.getId() +" " + 
					organizations[i].getAssessmentProgramId() +" " + organizations[i].getOrganizationId() +" " + organizations[i].getRolesIds()[0]);
			if(organizations[i].getOrganizationId()!= -999){
				UserAssessmentPrograms persistedUserAssessmentProgram = userAssessProgDao.selectByUserIdAssessmentProrgramId(dbUser.getId(),
						organizations[i].getAssessmentProgramId(), organizations[i].getOrganizationId(), organizations[i].getRolesIds()[0]);
				// if not create an active entry
				UserAssessmentPrograms userAssessmentProgram = new UserAssessmentPrograms();
				if(persistedUserAssessmentProgram == null){
					userAssessmentProgram.setAuditColumnProperties();
					userAssessmentProgram.setAartUserId(dbUser.getId());
					userAssessmentProgram.setAssessmentProgramId(organizations[i].getAssessmentProgramId());
					userAssessmentProgram.setOrganizationId(organizations[i].getOrganizationId());
					userAssessmentProgram.setGroupId(organizations[i].getRolesIds()[0]);
					userAssessProgDao.addUserAssessmentProgram(userAssessmentProgram);
				} else {
					userAssessmentProgram.setId(persistedUserAssessmentProgram.getId());
					userAssessmentProgram.setAuditColumnPropertiesForUpdate();
					userAssessmentProgram.setActiveFlag(true);
					userAssessProgDao.updateByPrimaryKeySelective(userAssessmentProgram);
				
					// safe check for org group activation if the group is inactive
					Long userOrgGroupId = persistedUserAssessmentProgram.getUserOrganizationGroupId();
					UserOrganizationsGroups userOrganizationsGroups = userOrganizationsGroupsDao.getInactiveUserOrganizationsGroups(userOrgGroupId);
					if(userOrganizationsGroups != null){
						userOrganizationsGroups.setAuditColumnPropertiesForUpdate();
						userOrganizationsGroups.setActiveFlag(true);
						userOrganizationsGroups.setActivationNo(activationNo);
						userOrganizationsGroupsDao.updateUserOrgGrpSelectiveByPrimaryKey(userOrganizationsGroups);
					}
				}
				if(organizations[i].getDefaultRoleId() != null){
					defaultUserAssessmentProgram.setAartUserId(dbUser.getId());
					defaultUserAssessmentProgram.setAuditColumnPropertiesForUpdate();
					defaultUserAssessmentProgram.setId(userAssessmentProgram.getId());
					defaultUserAssessmentProgram.setIsDefault(true);
				}
				activeUserAssessmentProgramIds.add(userAssessmentProgram.getId());
			}
		}
		
		// Remove extra user assessment programs
		List<Long> allUserAssessmentPrgIds = userAssessProgDao.getAllUserAssessmentProgramIds(dbUser.getId());
		List<Long> deActivateUserAssessmentPrgIds = new ArrayList<Long>();
		
		for(Long userAssessmentPrgId: allUserAssessmentPrgIds){
			boolean activeFlag = false;
			for(Long activeUserApId: activeUserAssessmentProgramIds){
				if(userAssessmentPrgId.longValue() == activeUserApId.longValue()){
					activeFlag = true;
				}
			}
			if(!activeFlag){
				deActivateUserAssessmentPrgIds.add(userAssessmentPrgId);
			}
		}
		for(Long userOrgApId : deActivateUserAssessmentPrgIds){
			UserAssessmentPrograms userAssessmentProgram = new UserAssessmentPrograms();
			userAssessmentProgram.setId(userOrgApId);
			userAssessmentProgram.setAuditColumnPropertiesForDelete();
			userAssessProgDao.deactivateAssessmentPrograms(userAssessmentProgram);
		}
		
		// Now set the default one here.
		userAssessProgDao.updateDefaultUserAssessmentProgram(defaultUserAssessmentProgram);
		
		if(newUser||(isFindUser==true)){
			// send activation email to the user
			try {
				Boolean isWelcomeMailRestricted = false;
				activationUserOrg.setUser(dbUser);
				List<AssessmentProgram> assessmentPrograms=assessProgDao.getAllAssessmentProgramByUserId(activationUserOrg.getUserId());
				if(internalUser.equals(false)) {
					for(AssessmentProgram ap:assessmentPrograms){
						if(CommonConstants.PLTW.equalsIgnoreCase(ap.getAbbreviatedname()))
						{
							isWelcomeMailRestricted = true;
							break;
						}
					}
				}
				activationUserOrg.getUser().setUserAssessmentPrograms(assessmentPrograms);
				// Restrict welcome emails for the PLTW external user only.
				if(isWelcomeMailRestricted.equals(false)){
					emailService.sendUserActivationMsg(activationUserOrg);
				}
			} catch (ServiceException e) {
				// eat the exception. Exception logged already and throws causing issue with activation number save
				// in organization groups
			}
		}
	}
	
	/*
	 * Sudhansu :Created for US17270 - For  uSer json story
	 * */
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertIntoDomainAuditHistory(Long objectId, Long createdUserId,String action, String source,String userBeforUpdate,String userAfterUpdate){
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(source);
		domainAuditHistory.setObjectType("USER");
		domainAuditHistory.setObjectId(objectId);
		domainAuditHistory.setCreatedUserId( createdUserId.intValue() );
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(action);
		domainAuditHistory.setObjectBeforeValues(userBeforUpdate);
		domainAuditHistory.setObjectAfterValues(userAfterUpdate);
		domainAuditHistoryDao.insert(domainAuditHistory);
	}
	
	/*
	 * Sudhansu :Created for US17687 - For  User Audit batch
	 * */

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Date getLastJobRunDateTime() {	
		// Date lastJobRunDateTime = userAuditTrailHistoryMapperDao.getLastJobRunDateTime();
		return userAuditTrailHistoryMapperDao.getLastJobRunDateTime();
	}
	
	/*
	 * Sudhansu :Created for US17687 - For  User Audit batch
	 * */
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ArrayList<DomainAuditHistory> getAuditLoggedUser(
			String getLastJobRunDatetime) {
		ArrayList<DomainAuditHistory>  auditLoggedUser = domainAuditHistoryDao.getUserForAuditLog(getLastJobRunDatetime);
		return auditLoggedUser;
	}
	
	/*
	 * Sudhansu :Created for US17687 - For User Audit json comparision
	 */

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addToUserAuditTrailHistory(DomainAuditHistory domainAuditHistory) {
		JsonNode before = null;
		JsonNode after = null;
		boolean isProcessed = false;
		try {
			if (EventTypeEnum.CLAIMUSER.getCode().equalsIgnoreCase(domainAuditHistory.getAction())) {
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(), domainAuditHistory.getId(),
						domainAuditHistory.getCreatedUserId().longValue(),
						EventNameForAudit.CLAIMUSER.getCode(), null, null);
			} else  if (EventTypeEnum.PASSWORD_CHANGE.getCode().equalsIgnoreCase(domainAuditHistory.getAction())) {
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(), domainAuditHistory.getId(),
						domainAuditHistory.getCreatedUserId().longValue(),
						EventNameForAudit.USER_PASSWORD_CHANGED.getCode(), null, null);
			} else if (domainAuditHistory.getObjectBeforeValues() == null
					&& domainAuditHistory.getObjectAfterValues() == null) {
				logger.debug("In-valid entry in Domainaudithistory table" + domainAuditHistory.getObjectId());
			} else if (domainAuditHistory.getObjectBeforeValues() == null && domainAuditHistory.getObjectAfterValues() != null) {
				insertToAuditTrailHistory(domainAuditHistory.getObjectId(), domainAuditHistory.getId(),
						domainAuditHistory.getCreatedUserId().longValue(), EventNameForAudit.USER_ADDED.getCode(), null,
						domainAuditHistory.getObjectAfterValues());
			} else {
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				if(after.equals(before)){
					// do nothing
				} else {
					// Assuming always the json structure for before object and
					// after object is same
					for (Iterator<Entry<String, JsonNode>> beforeFields = before.fields(); beforeFields.hasNext();) {

						Entry<String, JsonNode> beforeField = beforeFields.next();
						String field = beforeField.getKey();
						if (!"ModifiedDate".equalsIgnoreCase(field) && !"ModifiedUser".equalsIgnoreCase(field)) {
							if (before.get(field) != null && after.get(field) != null && before.get(field).isArray()) {
								ArrayNode afterArray = (ArrayNode) after.get(field);
								ArrayNode beforeArray = (ArrayNode) before.get(field);

								if (beforeArray.size() > 0 && afterArray.size() > 0) {
									if (beforeArray.get(0).isObject() && afterArray.get(0).isObject()) {
										// Organization
										List<JsonNode> beforeOrgList = new ArrayList<JsonNode>();
										List<JsonNode> afterOrgList = new ArrayList<JsonNode>();

										for (int i = 0; i < beforeArray.size(); i++) {
											beforeOrgList.add(beforeArray.get(i));
										}

										for (int i = 0; i < afterArray.size(); i++) {
											afterOrgList.add(afterArray.get(i));
										}

										List<Long> addedOrgList = new ArrayList<Long>();
										List<Long> addedOrgGrpList = new ArrayList<Long>();
										boolean isOrgPresent = false;
										boolean isGrpPresent = false;
										String oldDefaultRole = null;
										String newDefaultRole = null;
										String oldDefaultOrganization = null;
										String newDefaultOrganization = null;

										String oldOrgLevel = null;
										String newOrgLevel = null;

										for (JsonNode jsonNodeA : afterOrgList) {

											if (jsonNodeA.get("Default").asBoolean()) {
												newDefaultOrganization = jsonNodeA.get("OrgName").asText();
											}
											isOrgPresent = false;

											for (JsonNode jsonNodeB : beforeOrgList) {

												if (oldDefaultOrganization == null
														&& jsonNodeB.get("Default").asBoolean()) {
													oldDefaultOrganization = jsonNodeB.get("OrgName").asText();
												}

												if (jsonNodeA.get("Id").asLong() == jsonNodeB.get("Id").asLong()) {

													isOrgPresent = true;
													addedOrgList.add(jsonNodeB.get("Id").asLong());

													ArrayNode afterOrgGrpArray = (ArrayNode) jsonNodeA.get("Roles");
													ArrayNode beforeOrgGrpArray = (ArrayNode) jsonNodeB.get("Roles");

													List<JsonNode> beforeOrgGrpList = new ArrayList<JsonNode>();
													List<JsonNode> afterOrgGrpList = new ArrayList<JsonNode>();

													for (int i = 0; i < beforeOrgGrpArray.size(); i++) {
														beforeOrgGrpList.add(beforeOrgGrpArray.get(i));
													}
													for (int i = 0; i < afterOrgGrpArray.size(); i++) {
														afterOrgGrpList.add(afterOrgGrpArray.get(i));
													}

													oldDefaultRole = null;
													newDefaultRole = null;
													for (JsonNode jsonNodeGA : afterOrgGrpList) {
														if (jsonNodeGA.get("Default").asBoolean()) {
															newDefaultRole = jsonNodeA.get("OrgName").asText() + "-"
																	+ jsonNodeGA.get("Name").asText();
														}

														isGrpPresent = false;

														for (JsonNode jsonNodeGB : beforeOrgGrpList) {
															if (jsonNodeGB.get("Default").asBoolean()) {
																oldDefaultRole = jsonNodeB.get("OrgName").asText() + "-"
																		+ jsonNodeGB.get("Name").asText();
															}
															if (jsonNodeGA.get("Id").asLong() == jsonNodeGB.get("Id")
																	.asLong()) {
																isGrpPresent = true;
																addedOrgGrpList.add(jsonNodeGB.get("Id").asLong());
															}
														}

														if (!isGrpPresent) {
															insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
																	domainAuditHistory.getId(),
																	domainAuditHistory.getCreatedUserId().longValue(),
																	EventNameForAudit.USER_ROLE_ADDED.getCode(), null,
																	jsonNodeA.get("OrgName").asText() + "-"
																			+ jsonNodeGA.get("Name").asText());
														}

													}

													// Checking For deleted roles
													for (JsonNode jsonNodeGB : beforeOrgGrpList) {
														if (!addedOrgGrpList.contains(jsonNodeGB.get("Id").asLong())) {
															if (jsonNodeGB.get("Default").asBoolean()) {
																oldDefaultRole = jsonNodeB.get("OrgName").asText() + "-"
																		+ jsonNodeGB.get("Name").asText();
															}
															insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
																	domainAuditHistory.getId(),
																	domainAuditHistory.getCreatedUserId().longValue(),
																	EventNameForAudit.USER_ROLE_REMOVED.getCode(),
																	jsonNodeB.get("OrgName").asText() + "-"
																			+ jsonNodeGB.get("Name").asText(),
																	null);
														}
													}

													if (oldDefaultRole != null && newDefaultRole != null
															&& !oldDefaultRole.equalsIgnoreCase(newDefaultRole)) {
														insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
																domainAuditHistory.getId(),
																domainAuditHistory.getCreatedUserId().longValue(),
																EventNameForAudit.USER_DEFAULT_ROLE_CHANGED.getCode(),
																oldDefaultRole, newDefaultRole);
													}

												}

											}

											if (!isOrgPresent) {
												if (EventTypeEnum.MOVE.getCode()
														.equalsIgnoreCase(domainAuditHistory.getAction())) {
													newOrgLevel = jsonNodeA.get("OrgName").asText();
												} else {
													insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
															domainAuditHistory.getId(),
															domainAuditHistory.getCreatedUserId().longValue(),
															EventNameForAudit.USER_ORGANIZATION_ADDED.getCode(), null,
															jsonNodeA.get("OrgName").asText());

													for (int i = 0; i < ((ArrayNode) jsonNodeA.get("Roles")).size(); i++) {
														insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
																domainAuditHistory.getId(),
																domainAuditHistory.getCreatedUserId().longValue(),
																EventNameForAudit.USER_ROLE_ADDED.getCode(), null,
																jsonNodeA.get("OrgName").asText() + "-"
																		+ ((ArrayNode) jsonNodeA.get("Roles")).get(i)
																				.get("Name"));
													}

												}
											}

										}

										// Checking For deleted Organizations
										for (JsonNode jsonNodeB : beforeOrgList) {
											if (oldDefaultOrganization == null && jsonNodeB.get("Default").asBoolean()) {
												oldDefaultOrganization = jsonNodeB.get("OrgName").asText();
											}

											if (!addedOrgList.contains(jsonNodeB.get("Id").asLong())) {
												if (EventTypeEnum.MOVE.getCode()
														.equalsIgnoreCase(domainAuditHistory.getAction())) {
													oldOrgLevel = jsonNodeB.get("OrgName").asText();
												} else {
													insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
															domainAuditHistory.getId(),
															domainAuditHistory.getCreatedUserId().longValue(),
															EventNameForAudit.USER_ORGANIZATION_REMOVED.getCode(),
															jsonNodeB.get("OrgName").asText(), null);

													for (int i = 0; i < ((ArrayNode) jsonNodeB.get("Roles")).size(); i++) {
														insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
																domainAuditHistory.getId(),
																domainAuditHistory.getCreatedUserId()
																		.longValue(),
																EventNameForAudit.USER_ROLE_REMOVED.getCode(),
																jsonNodeB.get("OrgName").asText() + "-"
																		+ ((ArrayNode) jsonNodeB.get("Roles")).get(i)
																				.get("Name"),
																null);
													}
												}
											}
										}

										if (oldDefaultOrganization != null && newDefaultOrganization != null
												&& !oldDefaultOrganization.equalsIgnoreCase(newDefaultOrganization)) {
											insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
													domainAuditHistory.getId(),
													domainAuditHistory.getCreatedUserId().longValue(),
													EventNameForAudit.USER_DEFAULT_ORGANIZATION_CHANGED.getCode(),
													oldDefaultOrganization, newDefaultOrganization);
										}

										if (EventTypeEnum.MOVE.getCode().equalsIgnoreCase(domainAuditHistory.getAction())
												&& oldOrgLevel != newOrgLevel) {
											insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
													domainAuditHistory.getId(),
													domainAuditHistory.getCreatedUserId().longValue(),
													EventNameForAudit.USER_ORGANIZATION_LEVEL_CHANGED.getCode(),
													oldOrgLevel, newOrgLevel);
										}

									} else {
										// Assessment Programs
										List<String> beforeList = new ArrayList<String>();
										List<String> afterList = new ArrayList<String>();

										for (int i = 0; i < beforeArray.size(); i++) {
											beforeList.add(beforeArray.get(i).asText());
										}

										for (int i = 0; i < afterArray.size(); i++) {
											afterList.add(afterArray.get(i).asText());
										}

										if (!Arrays.equals(afterList.toArray(new String[afterList.size()]),
												beforeList.toArray(new String[beforeList.size()]))) {
											insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
													domainAuditHistory.getId(),
													domainAuditHistory.getCreatedUserId().longValue(),
													"User " + field + " Changed", before.get(field).toString(),
													after.get(field).toString());
										}

									}
								}

							} else { // Its a normal value
								if ((before.get(field) == null && after.get(field) != null)
										|| (before.get(field) != null && after.get(field) == null)
										|| before.get(field) != null && after.get(field) != null
												&& !before.get(field).asText().equalsIgnoreCase(after.get(field).asText())) {

									if (EventTypeEnum.MERGE_USER_PURGED.getCode()
											.equalsIgnoreCase(domainAuditHistory.getAction())) {
										insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
												domainAuditHistory.getId(),
												domainAuditHistory.getCreatedUserId().longValue(),
												EventNameForAudit.USER_DEACTIVATED.getCode(), before.get(field).asText(),
												after.get(field).asText());
									} else if (EventTypeEnum.ACTIVATED_DEACTIVATED.getCode()
											.equalsIgnoreCase(domainAuditHistory.getAction())) {
										insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
												domainAuditHistory.getId(),
												domainAuditHistory.getCreatedUserId().longValue(),
												"PENDING".equalsIgnoreCase(after.get(field).asText())
														? EventNameForAudit.USER_ACTIVATED.getCode()
														: EventNameForAudit.USER_DEACTIVATED.getCode(),
												before.get(field).asText(), after.get(field).asText());
									} else {
										insertToAuditTrailHistory(domainAuditHistory.getObjectId(),
												domainAuditHistory.getId(),
												domainAuditHistory.getCreatedUserId().longValue(),
												"User " + field + " Changed", before.get(field).asText(),
												after.get(field).asText());
									}

								}
							}
						}
					}
				}
			}
			isProcessed = true;
		} catch (JsonProcessingException e) {
			logger.error("value inserted in useraudittrail table Failed for " + domainAuditHistory.getObjectId());
			isProcessed = false;
		} catch (IOException e) {
			logger.error("value inserted in useraudittrail table Failed for " + domainAuditHistory.getObjectId());
			isProcessed = false;
		} catch (Exception e) {
			logger.error("value inserted in useraudittrail table Failed for " + domainAuditHistory.getObjectId());
			isProcessed = false;
		}
		if (isProcessed) {
			changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(), "COMPLETED");
		} else {
			changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(), "FAILED");
		}
		return isProcessed;
	}
	
	/*
	 * Sudhansu :Created for US17687 - For User json comparision value store in
	 * db
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToAuditTrailHistory(Long objectId, Long domainAuditHistoryId, Long modifiedUserId,
			String eventName, String beforeValue, String currentValue) {
		User modifiedUser=get(modifiedUserId);
		User effectedUser=getActiveOrInactiveUser(objectId);
		UserAuditTrailHistory userAuditTrailHistory = new UserAuditTrailHistory();
		userAuditTrailHistory.setAffectedUser(objectId);
		userAuditTrailHistory.setCreatedDate(new Date());
		userAuditTrailHistory.setModifiedUser(modifiedUserId);
		userAuditTrailHistory.setEventName(eventName);
		userAuditTrailHistory.setBeforeValue(beforeValue);
		userAuditTrailHistory.setCurrentValue(currentValue);
		userAuditTrailHistory.setDomainAuditHistoryid(domainAuditHistoryId);
		userAuditTrailHistory.setModifiedUserName(modifiedUser==null ? null:modifiedUser.getUserName());
		userAuditTrailHistory.setModifiedUserFirstName(modifiedUser==null ? null:modifiedUser.getFirstName());
		userAuditTrailHistory.setModifiedUserLastName(modifiedUser==null ? null:modifiedUser.getSurName());
		userAuditTrailHistory.setModifiedUserEducatorIdentifier(modifiedUser==null ? null:modifiedUser.getUniqueCommonIdentifier());
		userAuditTrailHistory.setUserName(effectedUser==null ? null:effectedUser.getUserName());
		userAuditTrailHistory.setUserFirstName(effectedUser==null ? null:effectedUser.getFirstName());
		userAuditTrailHistory.setUserLastName(effectedUser==null ? null:effectedUser.getSurName());
		userAuditTrailHistory.setUserEducatorIdentifier(effectedUser==null ? null:effectedUser.getUniqueCommonIdentifier());
		userAuditTrailHistoryMapperDao.insert(userAuditTrailHistory);
		logger.trace("value inserted in useraudittrail table ");
	}
	
	/*
	 * Sudhansu :Created for US17687 - For  User json comparision value store in db
	 * */
  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
  public List<Long> getUnProcessedAuditLoggedUser(){
		return domainAuditHistoryDao.getUnProcessedAuditLoggedUser();
	}
  
  @Override
  @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
  public void changeStatusToCompletedProcessedAuditLoggedUser(Long successId,String status){
	  domainAuditHistoryDao.changeStatusToCompletedProcessedAuditLoggedUser(successId,status);
  }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateUserAssessmentProgramToDefault(User user, Long organizationId, Long groupId, boolean isDefault, Long userOrganizationGroupId) {
		UserAssessmentPrograms userAssesssmentPrograms = new UserAssessmentPrograms();
		userAssesssmentPrograms.setAartUserId(user.getId());
		userAssesssmentPrograms.setAssessmentProgramId(user.getAssessmentProgramId());
		userAssesssmentPrograms.setActiveFlag(true);
		userAssesssmentPrograms.setIsDefault(isDefault);
		userAssesssmentPrograms.setGroupId(groupId);
		userAssesssmentPrograms.setOrganizationId(organizationId);
		UserAssessmentPrograms existingUserAssesssmentPrograms = userAssessProgDao.selectByUserIdAssessmentProrgramId(
				user.getId(), user.getAssessmentProgramId(), organizationId, groupId);
		if (existingUserAssesssmentPrograms != null) {
			userAssesssmentPrograms.setId(existingUserAssesssmentPrograms.getId());
			userAssesssmentPrograms.setAuditColumnPropertiesForUpdate();
			userAssessProgDao.updateDefaultByPrimaryKeySelective(userAssesssmentPrograms);
			userAssessProgDao.updateDefaultUserAssessmentProgram(userAssesssmentPrograms);
		} else {
			userAssesssmentPrograms.setAuditColumnProperties();
			userAssesssmentPrograms.setUserOrganizationGroupId(userOrganizationGroupId);
			userAssessProgDao.insertSelective(userAssesssmentPrograms);
			userAssessProgDao.updateDefaultUserAssessmentProgram(userAssesssmentPrograms);
		}
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public UserAssessmentPrograms getUserDefaultAssessmentProgram(Long userId){
		return userAssessProgDao.getUserDefaultAssessmentProgram(userId);
	}

	@Override
	public List<UserAssessmentPrograms> getUserRoles(long userId, String organizationStateCode,
			String organizationDistrictCode, String organizationSchoolCode) {
		return userAssessProgDao.getUserRoles(userId,organizationStateCode,organizationDistrictCode,organizationSchoolCode);
	}

	@Override
	public List<Authorities> getGroupExcludeLockdownForGlobalAdmin(Long userId, Long groupId, Long organizationId) {
		return authoritiesDao.getGroupExcludeLockdownForGlobalAdmin(userId, groupId, organizationId);
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User addEducatorIfNotPresentForTASCByEmailUserId(User educator,
			Collection<Organization> organizations,
			Organization userOrganization, String courseStatus) {
		
		User userFoundOrAdded = null;		
		boolean existsWithEducatorId = false;
		boolean existsWithEmailId = false;
		boolean insert = false;
		String beforejsonString = null;
		String afterjsonString = null;
		UserOrganizationsGroups userOrgGroup = null;
		Long userOrgGroupId = null;
		boolean isDefault = false;
		Boolean addTeacherRole = false;
		
		if (educator != null && StringUtils.isNotEmpty(educator.getUniqueCommonIdentifier())) {
			
			//find all users irrespective of active flag.
			//By Educator id
			List<User> usersFoundByEducatorId = userDao.getByUniqueCommonIdentifierAndTree(educator.getUniqueCommonIdentifier(), null, null);
			
			if (CollectionUtils.isNotEmpty(usersFoundByEducatorId)) {
				// The educator is found with in the user's tree and can be
				// reused.				
				userFoundOrAdded = usersFoundByEducatorId.get(0);
				userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_FOUND);
				existsWithEducatorId = true;				
				
			} else{
				existsWithEducatorId = false;
			}
			
			
			User userFoundByEmailId = userDao.getByEmail(educator.getEmail());
			
			if(userFoundByEmailId != null){
				if(existsWithEducatorId){
					if(userFoundByEmailId.getUniqueCommonIdentifier() != null && !userFoundByEmailId.getUniqueCommonIdentifier().equals(userFoundOrAdded.getUniqueCommonIdentifier())){						
						userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_ID);
						return userFoundOrAdded;
					}else if(userFoundByEmailId.getEmail() != null && userFoundOrAdded.getEmail() != null
							&& !userFoundByEmailId.getEmail().equals(userFoundOrAdded.getEmail())){
						userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_EMAIL_ADDRESS);
						return userFoundOrAdded;
					}
				}else{
					if(userFoundByEmailId.getUniqueCommonIdentifier() == null || (userFoundByEmailId.getUniqueCommonIdentifier() != null && "".equalsIgnoreCase(userFoundByEmailId.getUniqueCommonIdentifier().trim()))){
						userFoundByEmailId.setUniqueCommonIdentifier("9999999999");
						userDao.updateSelectiveByPrimaryKey(userFoundByEmailId);
					}					
				}
				
				existsWithEmailId = true;
				userFoundOrAdded = userFoundByEmailId;
				userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_FOUND);
			}
			
			if(existsWithEducatorId){
				if(existsWithEmailId){
					//***Must add this check here. compare the record with educatorid and the record with emailid. Both must be same.
//					if(educator.getEmail() != null && && userFoundByEmailId!=null && !userFoundByEmailId.getEmail().equalsIgnoreCase(userFoundOrAdded.getEmail())){
//						userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_EMAIL_ADDRESS);
//						return userFoundOrAdded;
//					}
					
					User userJson = userDao.getJsonFormatData(userFoundOrAdded.getId());
					if(userJson  != null)
					 beforejsonString = userJson.buildJsonString();
					
					 addTeacherRoleForTASC(educator, userFoundOrAdded, userOrganization, courseStatus);
					
				}else{
					userFoundOrAdded = usersFoundByEducatorId.get(0);
					//educator exists with id but not with email id, Do not update educator record, send email to DTC/BTC and reject TASC
					if(educator.getEmail() != null && !educator.getEmail().equalsIgnoreCase(userFoundOrAdded.getEmail())){
						userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_EMAIL_ADDRESS);
						return userFoundOrAdded;
					}
					
					addTeacherRoleForTASC(educator, userFoundOrAdded, userOrganization, courseStatus);
					
				}
			}else{ //if(existsWithEmailId || (!existsWithEducatorId && (educator.getEmail() == null ||educator.getEmail().isEmpty()))){
				if(userFoundByEmailId != null){
					//Email address is in use with more than one Educator ID, send an email to DTC/BTC, reject TASC
					if(!"9999999999".equals(userFoundByEmailId.getUniqueCommonIdentifier()) && !educator.getUniqueCommonIdentifier().equals(userFoundByEmailId.getUniqueCommonIdentifier())){						
						userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_ID);
						return userFoundOrAdded;
					}else{
						User userJson = userDao.getJsonFormatData(userFoundOrAdded.getId());
						if(userJson  != null)
						 beforejsonString = userJson.buildJsonString();
						
						 addTeacherRoleForTASC(educator, userFoundOrAdded, userOrganization, courseStatus);
						
					}
								
				}else{
					if(educator.getEmail() == null ||educator.getEmail().isEmpty()){
						String authInfo = educator.getUniqueCommonIdentifier()
								+ ParsingConstants.INNER_DELIM + userOrganization.getId();
						if (StringUtils.isNotEmpty(authInfo)) {								
							educator.setEmail(authInfo.substring(0,  Math.min(45, authInfo.length())));
						}								
					
					}					
					if(!"99".equalsIgnoreCase(courseStatus)){
						educator.setActiveFlag(true);
						userFoundOrAdded = add(educator);
						userFoundOrAdded.setSaveStatus(RecordSaveStatus.EDUCATOR_ADDED);
						userOrgGroup = addUserToOrganization(userFoundOrAdded, userOrganization.getId(), educator.getCreatedUser(), true);
						insert = true;
						isDefault = false;
						addTeacherRole = true;
					}else{
						educator.setSaveStatus(RecordSaveStatus.INVALID_COURSE_STATUS);
						return educator;
					}
					
					
				}
				
			}
			
			if(addTeacherRole){
				if(educator.getAssessmentProgramId() != null && !"99".equalsIgnoreCase(courseStatus)) {
					userFoundOrAdded.setAssessmentProgramId(educator.getAssessmentProgramId());
					if(userOrgGroup != null){
						userOrgGroupId = userOrgGroup.getId();
					}
					addIfUserAssesmentIsNotPresent(userFoundOrAdded, userOrgGroupId, !isDefault);
				}
			}
		}
		
		
		
		
		User userJson = userDao.getJsonFormatData(userFoundOrAdded.getId());
		
		if(userJson  != null)
			afterjsonString = userJson.buildJsonString();
		
		insertIntoDomainAuditHistory(userFoundOrAdded.getId(), educator.getModifiedUser(), insert?EventTypeEnum.INSERT.getCode():EventTypeEnum.UPDATE.getCode(), SourceTypeEnum.TASCWEBSERVICE.getCode(), beforejsonString, afterjsonString);
				
		return userFoundOrAdded;
	}
	
	private void addTeacherRoleForTASC(User tascEducator, User userFound, Organization userOrganization, String courseStatus){
		
		boolean addTeacherRole = false;
		boolean alreadyAddedTeacher = false;
		UserOrganizationsGroups userOrgGroup = null;
		Long userOrgGroupId = null;
		boolean isDefault = false;
 
		List<UserOrganizationsGroups> grps = userOrganizationsGroupsService.getUserOrganizationsGroupsByUserId(userFound.getId());
		//Look whether educator has teacher role
		if(CollectionUtils.isEmpty(grps)){
			addTeacherRole = true; 
		}else if(!CollectionUtils.isEmpty(grps)){
			//DE15192: addTeacherRole is also used to add to assessmentprogram which is used in many places. So creating alreadyAddedTeacher as an additional check.
			UserOrganizationsGroups teacherForCurrentSchool = getTeacherRoleExists(grps, userOrganization.getId());
			int statusOfTeacherRoleIfExists = getStatusOfTeacherRoleIfExists(grps);
			//DE15192: If user exists in other organization as teacher, update the current status of the teacher from that organization. 
			//If statusOfTeacherRoleIfExists=-1, the user doesn't exist as a teacher in any organization. 
			if(teacherForCurrentSchool == null && statusOfTeacherRoleIfExists!=-1 && !"99".equalsIgnoreCase(courseStatus)){
				addTeacherRole = true;
				userOrgGroup = addUserToOrganization(userFound, userOrganization.getId(), tascEducator.getCreatedUser(), false, statusOfTeacherRoleIfExists);
				userFound.setSaveStatus(RecordSaveStatus.TEACHER_ROLE_ADDED);
				alreadyAddedTeacher = true;
				isDefault = true;
			}
			else if(teacherForCurrentSchool == null && statusOfTeacherRoleIfExists==-1)
				addTeacherRole = true;
			
		}
		if (addTeacherRole && !"99".equalsIgnoreCase(courseStatus) && !alreadyAddedTeacher) {
			isDefault = userOrganizationsGroupsService.hasDefaultOrganization(userFound.getId());
			userOrgGroup = addUserToOrganization(userFound, userOrganization.getId(), tascEducator.getCreatedUser(), !isDefault);
		}
		
		if(addTeacherRole){
			if(tascEducator.getAssessmentProgramId() != null && userFound != null && !"99".equalsIgnoreCase(courseStatus)) {
				userFound.setAssessmentProgramId(tascEducator.getAssessmentProgramId());
				if(userOrgGroup != null){
					userOrgGroupId = userOrgGroup.getId();
				}
				 
				addIfUserAssesmentIsNotPresent(userFound, userOrgGroupId, !isDefault);
			}
		}
		
		return;
	}
	
	private UserOrganizationsGroups getTeacherRoleExists(List<UserOrganizationsGroups> grps, long organizationId){
		if(CollectionUtils.isNotEmpty(grps)){
			for(UserOrganizationsGroups uog : grps){
				if(uog.getGroup() != null && "TEA".equalsIgnoreCase(uog.getGroup().getGroupCode()) && uog.getOrganizationId() == organizationId ){
					return uog;
				}
			}
		}
		return null;
	}
	
	private boolean isTeacherRoleExists(List<UserOrganizationsGroups> grps){
		boolean exists = false;
		if(CollectionUtils.isNotEmpty(grps)){
			for(UserOrganizationsGroups uog : grps){
				if(uog.getGroup() != null && "TEA".equalsIgnoreCase(uog.getGroup().getGroupCode())){
					exists = true;
					break;
				}
			}
		}
		return exists;
	}
	
	private int getStatusOfTeacherRoleIfExists(List<UserOrganizationsGroups> grps){
		
		//DE15192: If user exists in other organization as teacher, update the current status of the teacher from that organization. 
		//Order of precedence of the status: Pending, Inactive, Active
		//If statusOfTeacherRoleIfExists=-1, the user doesn't exist as a teacher in any organization. 
		if(CollectionUtils.isNotEmpty(grps)){
			
			Map<Integer, Integer> teacherStatusCount = new HashMap<Integer, Integer>();
			teacherStatusCount.put(UserStatus.PENDING, 0);
			teacherStatusCount.put(UserStatus.INACTIVE, 0);
			teacherStatusCount.put(UserStatus.ACTIVE, 0);

			for(UserOrganizationsGroups uog : grps){
				if(uog.getGroup() != null && "TEA".equalsIgnoreCase(uog.getGroup().getGroupCode())){
					if(uog.getStatus()!=null){
						if(uog.getStatus().equals(UserStatus.PENDING))
							teacherStatusCount.put(UserStatus.PENDING, (teacherStatusCount.get(UserStatus.PENDING))+1);
						else if(uog.getStatus().equals(UserStatus.INACTIVE))
							teacherStatusCount.put(UserStatus.INACTIVE, (teacherStatusCount.get(UserStatus.INACTIVE))+1);
						else if(uog.getStatus().equals(UserStatus.ACTIVE))
							teacherStatusCount.put(UserStatus.ACTIVE, (teacherStatusCount.get(UserStatus.ACTIVE))+1);
					}
				}
			}
		
			if(teacherStatusCount.get(UserStatus.PENDING) > 0)
				return UserStatus.PENDING;
			else if(teacherStatusCount.get(UserStatus.INACTIVE) > 0)
				return UserStatus.INACTIVE;
			else if(teacherStatusCount.get(UserStatus.ACTIVE) > 0)
				return UserStatus.ACTIVE;
		
		}
		return -1;
	}
	
	@Override
	public List<Long> getKIDSEmailUsersPerSchoolIds(String aypSchoolId, String attendanceSchoolId){
		return userDao.getKIDSEmailUsersPerSchoolIds(aypSchoolId, attendanceSchoolId);
	}
	
	/**
	 * DE14034: QA-Not able to exit the student by uploading TEC file with new DLM exit code
	 * Developer: Navya Kooram @ EP KU Team
	 * This method is called from BatchUploadListener.beforeJob() to populate the User Object. 
	 * Instead of calling getByUsername() which was assigning the default assessment program and it's permissions to the uploadUser who initiated the batch process,
	 * 	this method will populate the User object based on the userId, the assessmentprogramId, organizationId and the RoleId (groupId - StateSystemAdmin etc) 
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User getUserDetailsByIdOrgAssessmentProgram(Long userId, Long assessmentProgramId, Long userOrgId, Long groupId) {
	
		User user = userDao.get(userId);
		
		if(user!=null){
		user.setCurrentOrganizationId(userOrgId);
		user.setCurrentGroupsId(groupId);
		user.setCurrentAssessmentProgramId(assessmentProgramId);
		AssessmentProgram assessmentProgram=assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		user.setCurrentAssessmentProgramName(assessmentProgram.getAbbreviatedname());
		
		Organization contractingOrg = organizationService.getContractingOrganization(userOrgId);
		if(contractingOrg != null) {
			user.setContractingOrgDisplayIdentifier(contractingOrg.getDisplayIdentifier());
			user.setContractingOrgId(contractingOrg.getId());	
			user.setContractingOrganization(contractingOrg);
		}
		
		List<Organization> organizations = userDao.getOrganizations(userId);
		if (organizations != null && CollectionUtils.isNotEmpty(organizations)) {
			user.setOrganizations(organizations);
		}
		List<UserOrganizationsGroups> userOrganizationsGroups = userOrganizationsGroupsService.getUserOrganizationsGroupsByUserId(user.getId());
		
		List<Groups> groups = new ArrayList<Groups>();
		// populate groups
		for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
			if (!groups.contains(userGroup.getGroup())) {
				Groups g = userGroup.getGroup();
				g.setDefaultGroup(userGroup.isIsdefault());
				groups.add(g);
			}
		}

		// populate orgs
		for (Organization organization : organizations) {
			organization.setGroupsList(new ArrayList<Groups>());
			for (UserOrganizationsGroups userGroup : userOrganizationsGroups) {
				if (userGroup.getOrganizationId().equals(organization.getId())) {
					userGroup.setOrganization(organization);
					organization.setDefaultOrg(userGroup.isUserOrganizationDefault());
					organization.getGroupsList().add(userGroup.getGroup());
					user.setCurrentOrganizationType(organization.getOrganizationType().getTypeCode());
					if (userGroup.isUserOrganizationDefault()) {
						user.setDefaultOrganizationId(organization.getId());
						if (userGroup.isIsdefault()) {
							user.setDefaultUserGroupsId(userGroup.getGroup().getGroupId());
						}
					}
				}
			}
		}
		
		user.setGroupsList(groups);
		user.setOrganizations(organizations);
		user.setUserOrganizationsGroups(userOrganizationsGroups);
		
		List<Organization> parents = organizationService.getAllParents(userOrgId);
    	Long stateId = null;
		//in the case the user is a state level user add the state
    	Organization defaultOrg=organizationService.get(userOrgId);
		if(defaultOrg!=null){
			parents.add(defaultOrg);
		}
		for (Organization org : parents){
			if (org.getOrganizationType()!=null && org.getOrganizationType().getTypeCode()!=null && org.getOrganizationType().getTypeCode().equals("ST")){
				stateId = org.getId();
				break;
			}
		}
		
		List<Authorities> authorities = null;
		// For users with GSAD role need to be handled when they switch role.
		if(user.getCurrentOrganizationType().equals("CONS")){
			authorities = getGroupExcludeLockdownForGlobalAdmin(userId, groupId,userOrgId);
		} else {
			authorities = getByUserAndGroupExcludeLockdown(userId, groupId, userOrgId, stateId, assessmentProgramId);
		}
		user.setAuthoritiesList(authorities);
		
		//Long userOrgId = userDetails.getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		user.setAllChilAndSelfOrgIds(childrenOrgIds);
		
		
		List<Groups> userGroups = userOrganizationsGroupsService.getGroupsByUserOrganization(userId);
		user.setUserGroups(userGroups);
			
	    	
		UserAssessmentPrograms userDefaultAssessmentProgram = getUserDefaultAssessmentProgram(userId);
        if(userDefaultAssessmentProgram != null){
        	user.setDefaultAssessmentProgramId(userDefaultAssessmentProgram.getAssessmentProgramId());
        }
            

    	List<AssessmentProgram> userAPs = assessProgDao.getAllAssessmentProgramByUserId(user.getId());
		if(CollectionUtils.isNotEmpty(userAPs)){
			List<String> userAPCodes = new ArrayList<String>(userAPs.size());
			for(AssessmentProgram userAP:userAPs) {
				userAPCodes.add(userAP.getAbbreviatedname());
			}
			user.setAssessmentProgramCodes(userAPCodes);
		}
	} else {
			throw new UsernameNotFoundException("Username " + userId + " was not found.");
	}
		
	return user;
	
}

	@Override
	public List<UserOrganization> getUserOrganizationsByOrgId(Long sourceSchoolId) {
		
		return userDao.getUserOrganizationsByOrgId(sourceSchoolId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void transferUserOrganization(UserOrganization userOrganization, Long destinationSchool) {
		userOrganization.setAuditColumnPropertiesForUpdate();
		userOrganization.setOrganizationId(destinationSchool);
		userDao.transferUserOrganization(userOrganization);
	}

	@Override
	public void disableUserOrganization(UserOrganization userOrganization) {
		userOrganization.setAuditColumnPropertiesForDelete();
		userOrganization.setActiveFlag(false);
		userDao.disableUserOrganization(userOrganization);
	}

	@Override
	public Long getCountByOrganizationId(Long organizationId) {
		return userDao.getCountByOrganizationId(organizationId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final User checkEducatorExistsForKELPA(User educator, Organization userOrganization) {
		
		User userFound = null;		
		UserOrganizationsGroups userOrgGroup = null;
		Long userOrgGroupId = null;

		if (educator != null && StringUtils.isNotEmpty(educator.getUniqueCommonIdentifier())) {
			// find all users irrespective of active flag, by Educator id
			List<User> usersFound = userDao.getByUniqueCommonIdentifierAndTree(educator.getUniqueCommonIdentifier(), null, null);
			if (CollectionUtils.isNotEmpty(usersFound)) {
				userFound = usersFound.get(0);
				userFound.setSaveStatus(RecordSaveStatus.EDUCATOR_FOUND);
				List<UserOrganizationsGroups> grps = userOrganizationsGroupsService.getUserOrganizationsGroupsByUserId(userFound.getId());
				//Look whether educator has teacher role
				if(CollectionUtils.isEmpty(grps)){
					userOrgGroup = addUserToOrganization(userFound, userOrganization.getId(), educator.getCreatedUser(), true,2);
					userFound.setSaveStatus(RecordSaveStatus.TEACHER_ROLE_ADDED);
				}else if(!CollectionUtils.isEmpty(grps)){
					userOrgGroup = getTeacherRoleExists(grps, userOrganization.getId());
					if(userOrgGroup == null){
						userOrgGroup = addUserToOrganization(userFound, userOrganization.getId(), educator.getCreatedUser(), false,2);
						userFound.setSaveStatus(RecordSaveStatus.TEACHER_ROLE_ADDED);
					}
				}
			}else{
				userFound = educator;
				userFound.setSaveStatus(RecordSaveStatus.EDUCATOR_NOT_FOUND);
			}
			
			if(educator.getAssessmentProgramId() != null && userFound != null && !userFound.getSaveStatus().equals(RecordSaveStatus.EDUCATOR_NOT_FOUND)) {
				userFound.setAssessmentProgramId(educator.getAssessmentProgramId());
				if(userOrgGroup != null){
					userOrgGroupId = userOrgGroup.getId();
				}
				addIfUserAssesmentIsNotPresent(userFound, userOrgGroupId,false);
			}
			User usr = userDao.getByUserName(userFound.getUserName());
			if(usr!= null && usr.getStatusCode()!= null)
				userFound.setStatusCode(usr.getStatusCode());
		}
		return userFound;
	}
	
	@Override
	public List<UserOrganization> getDestUserOrganizationsByOrgId(Long destinationSchoolId, Long aartUserId) {
		
		return userDao.getDestUserOrganizationsByOrgId(destinationSchoolId ,aartUserId);
	}
	
	@Override
	public List<User> getTeachersWithRosteredDLMKidsInSchoolSubjectsAndGrades(Long schoolId, List<Long> contentAreaIds, List<Long> gradeIds, Long schoolYear) {
		return userDao.getTeachersWithRosteredDLMKidsInSchoolSubjectsAndGrades(schoolId, contentAreaIds, gradeIds, schoolYear);
	}

	@Override
	public List<UserOrganization> getDeactiveUserOrganizationsByOrgId(Long sourceSchoolId) {
		
		return userDao.getDeactiveUserOrganizationsByOrgId(sourceSchoolId);
	}

	@Override
	public void enableUserOrganization(UserOrganization userOrganization) {
		userOrganization.setAuditColumnPropertiesForUpdate();
		userOrganization.setActiveFlag(true);
		userDao.enableUserOrganization(userOrganization);
	}

	@Override
	public void setStatusToInactive(Long userOrganizationId) {

		userDao.setStatusToInactive(userOrganizationId);
	}

	@Override
	public Long getDeactivateCountByOrganizationId(Long organizationId) {
		
		return userDao.getDeactivateCountByOrganizationId(organizationId);
	}

	@Override
	public List<User> getInactiveUsersForAdmin(Map<String, Object> criteria, String sortByColumn, String sortType,
			int i, Integer limitCount) {
		return userDao.getInactiveUsersForAdmin(criteria, sortByColumn, sortType, i, limitCount);
	}

	@Override
	public Integer getInactiveUsersCountForAdmin(Map<String, Object> criteria) {
		return userDao.getInactiveUsersCountForAdmin(criteria);
	}

	@Override
	public User getAllInactiveUserDetailsForAdmin(long userId) {
		User user = userDao.getForAdmin(userId);
		user.setOrganizations(userDao.getOrganizationsForAdmin(userId));
		user.setGroupsList(userDao.getGroupsForAdmin(userId));
		List<Organization> orgs = user.getOrganizations();
		if(orgs != null && orgs.size() > 0){
			for(Organization org : orgs){
				List<Organization> hierarchy = userDao
						.getOrganizationHierarchy(org.getId());
				org.setHierarchy(hierarchy);
			}
		}
		return user;
	}

	@Override
	public List<UserAssessmentPrograms> getInactiveUserRolesForAdmin(long userId) {
		return userAssessProgDao.getUserRoles(userId,CommonConstants.ORGANIZATION_STATE_CODE,CommonConstants.ORGANIZATION_DISTRICT_CODE,CommonConstants.ORGANIZATION_SCHOOL_CODE);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteInvalidUserContext(Long userId) {
		return userDao.deleteInvalidUserContext(userId);
	}

	@Override
	public Boolean doesUserHaveHighRoles(Long userId, long currentGroupsId, long currentOrganizationId,
			Long currentAssessmentProgramId) {
		return userDao.doesUserHaveHighRoles(userId, currentGroupsId, currentOrganizationId, currentAssessmentProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<User> getUsersByUserType(Map<String, Object> criteria,
			String sortByColumn, String sortType, Integer offset, Integer limitCount) {
		criteria.put("limitCount", limitCount);
		criteria.put("offset", offset);
		criteria.put("sortByColumn", sortByColumn);
		criteria.put("sortType", sortType);
		return userDao.getUsersByUserType(criteria);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer countAllInternalUsersToView(Map<String, Object> criteria) {
		
		return userDao.countAllInternalUsersToView(criteria);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Boolean updateInternalUserFlag(Long userId, Boolean internalUserFlag) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		Boolean isUserUpdate = false;
		if (userId != null && internalUserFlag != null) {
			userDao.updateInternalUserFlag(userId, internalUserFlag,userDetails.getUserId());
			isUserUpdate = true;
		}
		return isUserUpdate;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer updateInvalidUserContext(Long claimUserId, Long modifiedUser) {
		return userDao.updateInvalidUserContext(claimUserId,modifiedUser);
	}
	
	@Override
	public List<User> getAllUsersBySearch(Map<String, Object> criteria, String sortByColumn, String sortType, int i,
			Integer limitCount) {
		
		return userDao.getAllUsersBySearch(criteria,sortByColumn,sortType, i, limitCount);
	}

	@Override
	public Integer getCountForFindUsers(Map<String, Object> criteria) {
		
		return userDao.getCountForFindUsers(criteria);
	}

	@Override
	public String getDistrictNames(Long districtId) {
		
		return userDao.getDistrictNames(districtId);
	}

	@Override
	public List<Long> getDTCUserIdsByOrganizationName(String districtName) {
		return userDao.getDTCUserIdsByOrganizationName(districtName);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void auditClaimUserDetails(Long userIdToBeClaimed) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Long requestingUserId = userDetails.getUser().getId();
		User userJson = userDao.getJsonFormatData(userIdToBeClaimed);
		insertIntoDomainAuditHistory(userIdToBeClaimed, requestingUserId, EventTypeEnum.CLAIMUSER.getCode(),
				SourceTypeEnum.MANUAL.getCode(), userJson.buildJsonString(), userJson.buildJsonString());
	}

	@Override
	public List<Organization> getUsersOrganizationsByUserId(Long userId) {
		
		return userDao.getUsersOrganizationsByUserId(userId);
	}
	
	@Override
	public User getByUserIdForUploadResuts( Long userId, Long orgId){
	   return  userDao.getByUserIdForUploadResuts(userId, orgId);
	}
	 
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public User getActiveOrInactiveUser(Long userId) {
		return userDao.getActiveOrInactiveUser(userId);
	}
	
	@Override
	public Long checkUserOrganizationCount(Long userId) {
		
		return userDao.checkUserOrganizationCount(userId);
	}

	@Override
	public void deactivateUser(Long userId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		userDao.deactivateUser(userDetails.getUserId(),userId);
	}
	
	@Override
	public void activateUser(Long userId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		userDao.activateUser(userDetails.getUserId(),userId);
	}

	@Override
	public List<User> getAllUsersByAssessmentProgramAndOrgId(
			Long organizationId, Long assessmentProgramId) {
		return userDao.getAllUsersByAssessmentProgramAndOrgId(organizationId, assessmentProgramId);
	}

	@Override
	public void removeRolesfromUsersByOrgAndAssessment(Long organizationId,
			Long assessmentProgramId, Long modifiedUserId) {
		userDao.removeRolesfromUsersByOrgAndAssessment(organizationId, assessmentProgramId, modifiedUserId);
	}

	@Override
	public void resetDefaultOrganization(Long id, Long userId) {
		userDao.resetDefaultOrganization(id, userId);
		
	}

	@Override
	public List<Long> getDistrictLevelUserIdsByOrgIdAndAP(Long userId, String assessmentCode) {
	return userDao.getDistrictLevelUserIdsByOrgIdAndAP(userId,assessmentCode);
	}

	@Override
	public List<User> getByEducatorIdentifierForGRF(String educatorIdentifier, Long stateId) {
		return userDao.getByEducatorIdentifierForGRF(educatorIdentifier,stateId);
	}

	@Override
	public Integer getTeacherCount(Long userId, Long schoolId, String teacherCode) {
		return userDao.getTeacherCount(userId, schoolId, teacherCode);
	}
	
	@Override
	public User getByExternalId(String externalId) {
		return userDao.getByExternalId(externalId);
	}

	@Override
	public Long getUserStateId(Long organizationId) {
		return userDao.getUserStateId(organizationId);
	}
	
	@Override
	public User getUserByExternalIddAndOrgId(String externalId,Long orgid) {
		return userDao.getUserByExternalIdAndOrgId(externalId,orgid);
	}
	public List<DLMPDTrainingDTO> getTrainingDetails(Long userId)
	{
		return userDao.getTrainingDetails(userId);
	}

}
