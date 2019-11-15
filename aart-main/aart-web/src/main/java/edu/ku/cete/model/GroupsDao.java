/**
 * 
 */
package edu.ku.cete.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.GroupRestrictions;
import edu.ku.cete.domain.Groups;

/**
 * @author neil.howerton
 *
 */
public interface GroupsDao {

    void addGroup(Groups group);
    
    void updateGroup(Groups group);

    void delete(@Param("groupId") long groupId);
    
    Groups getGroup(@Param("groupId") long groupId);
    
    List<Groups> getAllGroups();
        
    List<Groups> getAllGroupsForReport();
    
    List<Groups> getAllGroupsBelowLevel(@Param("groupId") Long groupId);
    
    List<Groups> getExceptionalGroupsBelowLevel(@Param("groupId") Long groupId);
    
    Groups getGroupByName(@Param("name") String name);
    
    List<Groups> getByOrganization(@Param("organizationId")long organizationId);
    
    Groups getDefaultByOrganization(@Param("organizationId")long organizationId);
    
    List<Groups> getByDefaultRoleAndOrganizationTree(@Param("defaultRole")boolean defaultRole,
    		@Param("organizationId") long organizationId);
    
    long getLastId();
    /**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
    ArrayList<Groups> getUserRolesByUserId(@Param("userIdParam") Long userIdParam);
    
    List<String> getGroupNames();
    
	List<Groups> getGroupsByGroupCode(@Param("groupCode") String groupCode);

	void updateUsersLimitPerRole(GroupRestrictions groupRestriction);

	Boolean isUsersLimitedToOnePerRole(@Param("groupCode") String groupCode,@Param("organizationId") Long organizationId,
		@Param("assessmentProgramId") Long assessmentProgramId);

	Boolean isUsersLimitedToOnePerRoleExists(@Param("groupCode") String groupCode,@Param("organizationId") Long organizationId,
		@Param("assessmentProgramId") Long assessmentProgramId);

	void insertUsersLimitPerRole(GroupRestrictions groupRestriction);

	List<Groups> getRolesForNotifications();

	List<String> getPermissionExtractGroupNames();

	Groups getGroupByCode(@Param("groupCode") String groupCode);

}
