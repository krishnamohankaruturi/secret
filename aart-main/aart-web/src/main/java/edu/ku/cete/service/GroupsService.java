/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import edu.ku.cete.domain.GroupRestrictions;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.report.domain.DomainAuditHistory;

/**
 * @author neil.howerton
 *
 */
public interface GroupsService {

    @PreAuthorize("hasRole('PERM_ROLE_CREATE')")
    public Groups addGroup(Groups group);
    
    @PreAuthorize("hasRole('PERM_ROLE_MODIFY')")
    public void updateGroup(Groups group);
    
    @PreAuthorize("hasRole('PERM_ROLE_DELETE')")
    public void deleteGroup(Groups group);
    
    @PreAuthorize("hasRole('PERM_ROLE_DELETE')")
    public void deleteGroupById(long groupId);
    
    //@PreAuthorize("hasRole('PERM_ROLE_VIEW')")
    public List<Groups> getAllGroups();

    public List<Groups> getAllGroupsForReport();
    
    List<Groups> getAllGroupsBelowLevel(Long groupId);
    
    List<Groups> getExceptionalGroupsBelowLevel(Long groupId);
    
    public  Groups getGroupByName(String name);
    
    //@PreAuthorize("hasRole('PERM_ROLE_VIEW')")
    public Groups getGroup(long groupId);
    
    //@PreAuthorize("hasRole('PERM_ROLE_VIEW')")
    public List<Groups> getGroupsByOrganization(long organizationId);
    
    //@PreAuthorize("hasRole('PERM_ROLE_VIEW')")
    public Groups getDefaultByOrganization(long organizationId);
    
    //@PreAuthorize("hasRole('PERM_ROLE_VIEW')")
    public List<Groups> getByDefaultRoleAndOrganizationTree(boolean defaultRole,
    		Long organizationId);
    
    // Getting groupNames for the organization
    public List<String> getGroupNames();
    
    //created for US18005 : For compring json object 
	boolean addToGroupsAuditTrailHistory(DomainAuditHistory domainAuditHistory);

	public List<Groups> getGroupsByGroupCode(String groupCode);

	// Per User story US18825 & US18927
	public void updateUsersLimitPerRole(GroupRestrictions groupRestriction);

	public Boolean isUsersLimitedToOnePerRole(String groupCode,Long organizationId, Long assessmentProgramId);

	public void restrictUsersLimitPerRole(GroupRestrictions groupRestriction);

	public Boolean isUsersLimitedToOnePerRoleExists(String groupCode, Long organizationId, Long assessmentProgramId);

	public void insertUsersLimitPerRole(GroupRestrictions groupRestriction);

	public List<Groups> getRolesForNotifications();

	public List<String> getPermissionExtractGroupNames();

	public Groups getGroupByCode(String groupCode);

}
