/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.GroupRestrictions;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserOrganizationsGroups;
import edu.ku.cete.model.UserOrganizationsGroupsDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserOrganizationsGroupsService;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.UserStatus;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UserOrganizationsGroupsServiceImpl implements UserOrganizationsGroupsService {
	
	private Logger LOGGER = LoggerFactory.getLogger(UserOrganizationsGroupsServiceImpl.class);

    @Autowired
    private UserOrganizationsGroupsDao userOrganizationsGroupsDao;
    
    @Autowired
    private DomainAuditHistoryMapper domainAuditHistoryDao ;
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserOrganizationsGroups addUserOrganizationsGroups(UserOrganizationsGroups userOrganizationsGroups) {
    	userOrganizationsGroupsDao.addUsersOrganizations(userOrganizationsGroups);
        if(userOrganizationsGroups.getGroupId() != null) {
            userOrganizationsGroupsDao.addUserOrganizationsGroups(userOrganizationsGroups);
        }
        return userOrganizationsGroups;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserOrganizationsGroups addUserOrganizationsGroupsForKidsProcess(UserOrganizationsGroups userOrganizationsGroups) {
    	int rowsUpdated = userOrganizationsGroupsDao.updateUsersOrganizationsForKidsProcess(userOrganizationsGroups);
    	if(rowsUpdated == 0){
    		userOrganizationsGroupsDao.addUsersOrganizations(userOrganizationsGroups);
    	}
    	
        if(userOrganizationsGroups.getGroupId() != null) {
        	rowsUpdated = userOrganizationsGroupsDao.updateUserOrganizationsGroupsForKidsProcess(userOrganizationsGroups);
        	if(rowsUpdated == 0){
        		userOrganizationsGroupsDao.addUserOrganizationsGroups(userOrganizationsGroups);
        	}else{
        		userOrganizationsGroups = userOrganizationsGroupsDao.getByUserIdOrganizationIdGroupId(userOrganizationsGroups.getUserId(), 
        				userOrganizationsGroups.getOrganizationId(), userOrganizationsGroups.getGroupId());
        	}
        }
        return userOrganizationsGroups;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void updateUserOrganizationsGroups(UserOrganizationsGroups userOrganizationsGroups) {
        if (userOrganizationsGroups.getUserOrganizationId() != 0) {
            userOrganizationsGroupsDao.updateUserOrganizationsGroups(userOrganizationsGroups);
        } else {
        	LOGGER.error("Found userOrganizationId=0 when trying to update userorganizationgroup records for userId="+
        			userOrganizationsGroups.getUserId()+" and organizationId="+userOrganizationsGroups.getOrganizationId());
        }
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deleteUserOrganizationsGroups(UserOrganizationsGroups userOrganizationsGroups) {
        deleteUserOrganizationsGroupsById(userOrganizationsGroups.getId());
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deleteUserOrganizationsGroupsById(long id) {
        userOrganizationsGroupsDao.deleteUserOrganizationsGroups(id);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserOrganizationsGroups getUserOrganizationsGroups(long id) {
        return userOrganizationsGroupsDao.getUserOrganizationsGroups(id);
    }
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<UserOrganizationsGroups> getUserOrganizationsGroupsByUserId(long userId) {
        return userOrganizationsGroupsDao.getByUserId(userId);
    }
    /**
     * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
     * Get role details by user list.
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<UserOrganizationsGroups> getUserOrganizationsGroupsByUserIds(Set<Long> userIds) {
        return userOrganizationsGroupsDao.getByUserIds(userIds);
    }
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<UserOrganizationsGroups> getByUserIdAndOrganization(long userId, long organizationId) {
        return userOrganizationsGroupsDao.getByUserIdAndOrganization(userId, organizationId);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean hasDefaultOrganization(Long userId) {
    	if(userOrganizationsGroupsDao.hasDefaultOrganization(userId) > 0)
    		return true;
    	else
    		return false;
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserOrganizationsGroups getByActivationNo(String activationNo) {
        return userOrganizationsGroupsDao.getByActivationNo(activationNo);
    }
    /**
     * @param userId long
     * @param status int
     * @return List<UserOrganizationsGroups>
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<UserOrganizationsGroups> getByUserAndStatus(long userId, Integer status) {
        return userOrganizationsGroupsDao.getByUserAndStatus(userId, status);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<UserOrganizationsGroups> getByOrganizationAndStatus(Set<Long> organizationIds, Integer status) {
        return userOrganizationsGroupsDao.getByOrganizationAndStatus(organizationIds, status);
    }
    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void changeUserStatusByIds(HashSet<Long> userIds,
			Integer status,Long loggedInUserId, Long currentGroupId,Long assessmentProgramId) throws ServiceException {
		//Added during US16368: To check selected user is authorized or not
		if (userIds.size() > 0) {
				for (Long userId : userIds) {
					//Work around by checking the usage of both hierarchy and roleorgtypeid, also covers special case of DTC able to delete DU  
					Long count=userOrganizationsGroupsDao.checkIfAuthorizedUser(userId,assessmentProgramId,currentGroupId);
					if(count>0)
							throw new ServiceException("Selected User is not Authorized");
				}
			//Added during US17270 -- User audit json creation
			User user = new User();
			for (Long id : userIds) {
				user.setId(id);
				long existingStatus = userOrganizationsGroupsDao.checkUserStatusById(id);
				if(existingStatus == UserStatus.ACTIVE){
					 user.setStatusCode("Active");
				 }else if(existingStatus == UserStatus.INACTIVE){
					 user.setStatusCode("In-Active");
				 }else{
					 user.setStatusCode("Pending");
				 }
		        String userBeforUpdate = user.buildJsonString();
		        
		        userOrganizationsGroupsDao.changeUserStatusByIds(new StringBuilder(id.toString()),
						status,loggedInUserId,currentGroupId,assessmentProgramId,new Date());
		        
				 if(status == UserStatus.PENDING){
					 user.setStatusCode("Pending");
				 }else{
					 user.setStatusCode("In-Active");
				 }
				 
				String userAfterUpdate = user.buildJsonString();
				
		        DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
				
				domainAuditHistory.setSource(SourceTypeEnum.MANUAL.getCode());
				domainAuditHistory.setObjectType("USER");
				domainAuditHistory.setObjectId(id);
				domainAuditHistory.setCreatedUserId(loggedInUserId.intValue());
				domainAuditHistory.setCreatedDate(new Date());
				domainAuditHistory.setAction(EventTypeEnum.ACTIVATED_DEACTIVATED.getCode());
				domainAuditHistory.setObjectBeforeValues(userBeforUpdate);
				domainAuditHistory.setObjectAfterValues(userAfterUpdate);
				
				domainAuditHistoryDao.insert(domainAuditHistory);							
			}
		}

	}

    
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public  void updatepdtrainingflag(long userid) {
    	
         userOrganizationsGroupsDao.updatepdtrainingflag(userid);
    }


    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final long IsValidPDTrainingUser(String firstname,String lastname,String uniqidentifer) {
    	
        return userOrganizationsGroupsDao.IsValidPDTrainingUser(firstname, lastname, uniqidentifer);
    }
    /**
     * Sudhansu.b : Added for US_16821(provide UI TO move Users)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public List<Long> getAllOrganizationsByUserId(Long userId){    	
    	return userOrganizationsGroupsDao.getAllOrganizationsByUserId(userId);
    }

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
	public List<Groups> getGroupsByUserOrganization(Long userId) {
		
		return userOrganizationsGroupsDao.getGroupsByUserOrganization(userId);
	}
	
	// Per US18825 & US18927
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
	public List<UserOrganizationsGroups> getUserOrganizationGroups(GroupRestrictions groupRestriction) {
		return userOrganizationsGroupsDao.getUserOrganizationsGroupsByOrgType(groupRestriction);
	}

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void downgradeUserTo(String downgradeUserGroupType, Set<Long> organizationUserGroupIds, GroupRestrictions groupRestriction) {
		for(Long organizationUserGroupId : organizationUserGroupIds){
			userOrganizationsGroupsDao.downgradeUserTo(downgradeUserGroupType, organizationUserGroupId, groupRestriction);
		}
	}

	@Override
	public List<Long> getUserIdsByUserOrgGroupIds(Set<Long> userOrgGroupIds) {
		// TODO Auto-generated method stub
		return userOrganizationsGroupsDao.getUserIdsByUserOrgGroupIds(userOrgGroupIds);
	}

	@Override
	public List<UserOrganizationsGroups> getDistinctGroupCodesBasedOnUserIds(List<Long> userIds) {
		return userOrganizationsGroupsDao.getDistinctGroupCodesBasedOnUserIds(userIds);
	}
}
