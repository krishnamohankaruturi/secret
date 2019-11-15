/**
 * 
 */
package edu.ku.cete.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.GroupRestrictions;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.user.UserOrganizationsGroups;

/**
 * @author neil.howerton
 *
 */
public interface UserOrganizationsGroupsService {

    /**
     *
     *@param userOrganizationsGroups {@link UserOrganizationsGroups}
     *@return {@link UserOrganizationsGroups}
     */
   // @PreAuthorize("hasRole('PERM_ROLE_ASSIGN')")
    UserOrganizationsGroups addUserOrganizationsGroups(UserOrganizationsGroups userOrganizationsGroups);

    UserOrganizationsGroups addUserOrganizationsGroupsForKidsProcess(UserOrganizationsGroups userOrganizationsGroups);
    /**
     *
     *@param userOrganizationsGroups {@link UserOrganizationsGroups}
     */
    void updateUserOrganizationsGroups(UserOrganizationsGroups userOrganizationsGroups);

    /**
     *
     *@param userOrganizationsGroups {@link UserOrganizationsGroups}
     */
    void deleteUserOrganizationsGroups(UserOrganizationsGroups userOrganizationsGroups);

    /**
     *
     *@param id {@link UserOrganizationsGroups}
     */
    void deleteUserOrganizationsGroupsById(long id);

    /**
     *
     *@param id {@link UserOrganizationsGroups}
     *@return {@link UserOrganizationsGroups}
     */
    UserOrganizationsGroups getUserOrganizationsGroups(long id);
    /**
     *
     *@param userId {@link UserOrganizationsGroups}
     *@return {@link UserOrganizationsGroups}
     */
    List<UserOrganizationsGroups> getUserOrganizationsGroupsByUserId(long userId);
    
    /**
     * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
     * Get role details by user list.
     * @param userIds
     * @return
     */
    List<UserOrganizationsGroups> getUserOrganizationsGroupsByUserIds(Set<Long> userIds);

    /**
     *
     *@param userId long
     *@param organizationId long
     *@return List<UserOrganizationsGroups>
     */
    List<UserOrganizationsGroups> getByUserIdAndOrganization(long userId, long organizationId);
    /**
     *
     *@param userId long
     *@param status int
     *@return List<UserOrganizationsGroups>
     */
    List<UserOrganizationsGroups> getByUserAndStatus(long userId, Integer status);

    /**
     *
     *@param organizationIds {@link List}
     *@param status int
     *@return List<UserOrganizationsGroups>
     */
    List<UserOrganizationsGroups> getByOrganizationAndStatus(@Param("organizationIds")Set<Long> organizationIds,
    		@Param("status")Integer status);
    /**
     * @param activationNo
     * @return {@link UserOrganizationsGroups}
     */
    public UserOrganizationsGroups getByActivationNo(String activationNo);
    
    /**
     * @param userIds {@Hash set}
     * @param status int
     */
    //US16245 : Added to activate and deactivate user(s) manually
    void changeUserStatusByIds(HashSet<Long> userIds, Integer status,Long loggedInUserId, Long currentGroupId, Long assessmentProgramId)throws ServiceException;
    
    public void updatepdtrainingflag(long userid) ;
    
    public long IsValidPDTrainingUser(String firstname,String lastname,String uniqidentifer);
    /**
     * Sudhansu.b : Added for US_16821(provide UI TO move Users)
     */
	List<Long> getAllOrganizationsByUserId(Long userId);

	List<Groups> getGroupsByUserOrganization(Long userId);

	List<UserOrganizationsGroups> getUserOrganizationGroups(GroupRestrictions groupRestriction);

	void downgradeUserTo(String downgradeUserGroupType, Set<Long> organizationUserGroupIds,GroupRestrictions groupRestriction);

	List<Long> getUserIdsByUserOrgGroupIds(Set<Long> userOrgGroupIds);
	
	boolean hasDefaultOrganization(Long userId);

	List<UserOrganizationsGroups> getDistinctGroupCodesBasedOnUserIds(List<Long> userIds);

}
