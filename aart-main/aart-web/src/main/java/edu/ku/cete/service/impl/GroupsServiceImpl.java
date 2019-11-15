/**
 * 
 */
package edu.ku.cete.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.GroupRestrictions;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.UserAuditTrailHistory;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.web.AartSessionListener;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class GroupsServiceImpl implements GroupsService {

    @Autowired
    private GroupsDao groupsDao;
    
    @Autowired
    private UserOrganizationsGroupsService userOrganizationsGroupsService;
    
	 @Autowired
	 private UserService userService;

    /**
     * logger.
     */
    private final Logger
    logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    
    private ObjectMapper mapper = new ObjectMapper();
    
	@Autowired
	private UserAuditTrailHistoryMapper userAuditTrailHistoryMapperDao;
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupsService#addGroup(edu.ku.cete.domain.Groups)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Groups addGroup(Groups group) {
    	groupsDao.addGroup(group);
        return group;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupsService#updateGroup(edu.ku.cete.domain.Groups)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void updateGroup(Groups group) {
    	groupsDao.updateGroup(group);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupsService#deleteGroup(edu.ku.cete.domain.Groups)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deleteGroup(Groups group) {
       deleteGroupById(group.getGroupId());
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupsService#deleteGroupById(long)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deleteGroupById(long groupId) {
        groupsDao.delete(groupId);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Groups> getAllGroups() {
        return groupsDao.getAllGroups();
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Groups> getAllGroupsForReport() {
        return groupsDao.getAllGroupsForReport();
    }
    public final List<Groups> getAllGroupsBelowLevel(Long groupId) {
        return groupsDao.getAllGroupsBelowLevel(groupId);
    }
    
    public final List<Groups> getExceptionalGroupsBelowLevel(Long groupId) {
        return groupsDao.getExceptionalGroupsBelowLevel(groupId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupsService#getGroup(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Groups getGroup(long groupId) {
        return groupsDao.getGroup(groupId);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
   public final Groups getGroupByName(String name) {
        return groupsDao.getGroupByName(name);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupsService#getGroupsByOrganization(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Groups> getGroupsByOrganization(long organizationId) {
        return (List<Groups>) groupsDao.getByOrganization(organizationId);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Groups getDefaultByOrganization(long organizationId) {
        return groupsDao.getDefaultByOrganization(organizationId);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Groups> getByDefaultRoleAndOrganizationTree(boolean defaultRole,Long organizationId) {
    	List<Groups> groupsList = new ArrayList<Groups>();
    	if(organizationId != null && organizationId > 0) {
    		groupsList = groupsDao.getByDefaultRoleAndOrganizationTree(defaultRole, organizationId.longValue());    		
    	}
    	return groupsList;
    }
    
    @Override
    public final List<String> getGroupNames() {
    	List<String> groupNames = new ArrayList<String>();    	
    	groupNames = groupsDao.getGroupNames();    	
    	return groupNames;
    }
    
    /*
	 * sudhansu : created for US18005 for comparing groups objects...
	 */
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addToGroupsAuditTrailHistory(DomainAuditHistory domainAuditHistory){
		JsonNode before = null;
		JsonNode after = null;
		boolean isProcessed= false;
		try {
		if(domainAuditHistory.getObjectBeforeValues() == null && domainAuditHistory.getObjectAfterValues() == null){
			logger.debug("In-valid entry in Domainaudithistory table"+ domainAuditHistory.getObjectId());
			
		}else{
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());			
				  if(before.get(0).isObject() && after.get(0).isObject()){							 
						   List<JsonNode> beforeList = new ArrayList<JsonNode>();
						   List<JsonNode> afterList = new ArrayList<JsonNode>();
						   for (int i = 0; i < before.size(); i++) {
								 beforeList.add(before.get(i));
							 }	
							 for (int i = 0; i < after.size(); i++) {
								 afterList.add(after.get(i));
							 }									
							 boolean isExist = true;
							 List<Long> addedPerms = new ArrayList<Long>();
							 for (JsonNode jsonNodeA : afterList) {
								 isExist = false;
								 for (JsonNode jsonNodeB : beforeList) {									 
									 	 if(jsonNodeA.get("id").asLong() == jsonNodeB.get("id").asLong() ){	
									 	 addedPerms.add(jsonNodeA.get("id").asLong());										
										 isExist = true;
									     break;
									 }									 
								 }								
								 if(!isExist){
									  addedPerms.add(jsonNodeA.get("id").asLong());									  
insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue(),
		"Role Edited - Permission Added In "+"Organization name="+jsonNodeA.get("orgName").asText()+" FOR assessmentProgramId"+jsonNodeA.get("assessmentProgramId").asLong(),null,jsonNodeA.get("authorityName").asText(),jsonNodeA.get("groupName").asText(),jsonNodeA.get("assessmentProgramId").asLong(),jsonNodeA.get("assessmentProgram").asText(),jsonNodeA.get("organizationId").asLong(),jsonNodeA.get("orgName").asText());
										 }
							 }							
							 for (JsonNode jsonNodeB : beforeList) {
								 	 if(!addedPerms.contains(jsonNodeB.get("id").asLong())){
									 insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
							                   ,"Role Edited - Permission Removed"+"Organization name="+jsonNodeB.get("orgName").asText()+" FOR assessmentProgramId"+jsonNodeB.get("assessmentProgramId").asLong(),jsonNodeB.get("authorityName").asText(),null,jsonNodeB.get("groupName").asText(),jsonNodeB.get("assessmentProgramId").asLong(),jsonNodeB.get("assessmentProgram").asText(),jsonNodeB.get("organizationId").asLong(),jsonNodeB.get("orgName").asText());
								 }
							 }
				  }	         	
		}
		isProcessed= true;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("value inserted in roleaudittrailhistory table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();
			isProcessed= false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("value inserted in roleaudittrailhistory table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();
			isProcessed= false;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("value inserted in roleaudittrailhistory table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();			
			isProcessed= false;
		}
		if (isProcessed){
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"COMPLETED");
		}
		else{
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"FAILED");
		}
		return isProcessed;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToAuditTrailHistory(Long objectId,Long domainAuditHistoryId,Long modifiedUserId,String eventName,String beforeValue,String currentValue, String groupName, Long assessmentProgramId, String assessmentProgramName, Long stateId, String stateName){
		UserAuditTrailHistory userAuditTrailHistory = new UserAuditTrailHistory();
		User user=userService.get(modifiedUserId);
		userAuditTrailHistory.setAffectedUser(objectId);
		userAuditTrailHistory.setCreatedDate(new Date());
		userAuditTrailHistory.setModifiedUser(modifiedUserId);
		userAuditTrailHistory.setEventName(eventName);
		userAuditTrailHistory.setBeforeValue(beforeValue);
		userAuditTrailHistory.setCurrentValue(currentValue);
		userAuditTrailHistory.setAffectedrolename(groupName);
		userAuditTrailHistory.setDomainAuditHistoryid(domainAuditHistoryId);
		userAuditTrailHistory.setAssessmentProgramId(assessmentProgramId);
		userAuditTrailHistory.setAssessmentProgram(assessmentProgramName);
		userAuditTrailHistory.setStateIds(stateId);
		userAuditTrailHistory.setStateNames(stateName);
		userAuditTrailHistory.setModifiedUserName(user==null ? null:user.getUserName());
		userAuditTrailHistory.setModifiedUserFirstName(user==null ? null:user.getFirstName());
		userAuditTrailHistory.setModifiedUserLastName(user==null ? null:user.getSurName());
		userAuditTrailHistory.setModifiedUserEducatorIdentifier(user==null ? null:user.getUniqueCommonIdentifier());
		

		userAuditTrailHistoryMapperDao.insertRoleAuditTrail(userAuditTrailHistory);
		logger.trace("value inserted in organizationaudittrail table ");
	}
	// Per US18825 & US18927
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void updateUsersLimitPerRole(GroupRestrictions groupRestriction){
		List<Groups> groups = getGroupsByGroupCode(groupRestriction.getGroupCode());
		groupRestriction.setGroupId(groups.get(0).getGroupId());
		groupRestriction.setAuditColumnPropertiesForUpdate();
    	groupsDao.updateUsersLimitPerRole(groupRestriction);
    }

	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Boolean isUsersLimitedToOnePerRole(String groupCode,Long organizationId, Long assessmentProgramId){
    	return groupsDao.isUsersLimitedToOnePerRole(groupCode,organizationId, assessmentProgramId);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void restrictUsersLimitPerRole(GroupRestrictions groupRestriction) {
		// Get a map of userorganizationgroups and filter which district has more than one DTC/BTC
		String downgradeUserGroupType = "";
		if(groupRestriction.getGroupCode().equals("DTC")){
			groupRestriction.setOrganizationType("DT");
			downgradeUserGroupType = "DUS";
		} else if(groupRestriction.getGroupCode().equals("BTC")){
			groupRestriction.setOrganizationType("SCH");
			downgradeUserGroupType = "BUS";
		}
		Map<Long, Set<Long>> orgGroupsMap = new HashMap<Long, Set<Long>>();
		List<Long> downgradeUserIds = new ArrayList<Long>();
		if(StringUtils.isNotBlank(groupRestriction.getOrganizationType())){
			List<UserOrganizationsGroups> userOrganizationGroups = userOrganizationsGroupsService.getUserOrganizationGroups(groupRestriction);
			// Iterate over the above list and map the userorganizationgroup ids to organizations type
			for(UserOrganizationsGroups userOrganizationGroup : userOrganizationGroups){
				if(!orgGroupsMap.containsKey(userOrganizationGroup.getOrganizationId())){
					orgGroupsMap.put(userOrganizationGroup.getOrganizationId(), new HashSet<Long>());
				} 
				orgGroupsMap.get(userOrganizationGroup.getOrganizationId()).add(userOrganizationGroup.getId());
			}
			
			// From the above map iterate over the keys and check if an organization has more than 2 entries. 
			// If it is then down-grade the role.
			logger.warn("Downgrading following users: ");
			Set<Long> userOrganizationGroupIds = new HashSet<Long>();
			for(Long organizationId : orgGroupsMap.keySet()){
				if(orgGroupsMap.get(organizationId) != null & orgGroupsMap.get(organizationId).size() > 1){
					logger.warn(organizationId + " : " + orgGroupsMap.get(organizationId));
					userOrganizationGroupIds = orgGroupsMap.get(organizationId);
					userOrganizationsGroupsService.downgradeUserTo(downgradeUserGroupType, userOrganizationGroupIds, groupRestriction);
				}
			}
			
			// Get downgrading user ids 
			if(userOrganizationGroupIds != null && !userOrganizationGroupIds.isEmpty()){
				downgradeUserIds = userOrganizationsGroupsService.getUserIdsByUserOrgGroupIds(userOrganizationGroupIds);
			}
			List<String> tobeRemovedSessions = new ArrayList<String>();
			for(Iterator<HttpSession> iterator = AartSessionListener.getTotalActiveSessions().iterator(); iterator.hasNext(); ){
				HttpSession session = iterator.next();
				User user = (User)session.getAttribute("user");
				if(user != null){
					Long userId = user.getId();
					for(Long usrId : downgradeUserIds){
						if(usrId != null){
							if(userId.longValue() == usrId.longValue()){
								tobeRemovedSessions.add(session.getId());
							}
						}
					}
				}
			}
			for(String sessionId: tobeRemovedSessions){
				HttpSession session = AartSessionListener.find(sessionId);
				if(session != null){
					session.invalidate();
				}
			}
		}
	}

	@Override
	public List<Groups> getGroupsByGroupCode(String groupCode) {
		return groupsDao.getGroupsByGroupCode(groupCode);
	}

	@Override
	public Boolean isUsersLimitedToOnePerRoleExists(String groupCode, Long organizationId, Long assessmentProgramId) {
		return groupsDao.isUsersLimitedToOnePerRoleExists(groupCode, organizationId, assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertUsersLimitPerRole(GroupRestrictions groupRestriction) {
		List<Groups> groups = getGroupsByGroupCode(groupRestriction.getGroupCode());
		groupRestriction.setGroupId(groups.get(0).getGroupId());
		groupRestriction.setAuditColumnProperties();
		groupsDao.insertUsersLimitPerRole(groupRestriction);
		
	}

	@Override
	public List<Groups> getRolesForNotifications() {
		return groupsDao.getRolesForNotifications();
	}

	@Override
	public List<String> getPermissionExtractGroupNames() {
		return groupsDao.getPermissionExtractGroupNames();
	}

	@Override
	public Groups getGroupByCode(String groupCode) {
		return groupsDao.getGroupByCode(groupCode);
	}

}
