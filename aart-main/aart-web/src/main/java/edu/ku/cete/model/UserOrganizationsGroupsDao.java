/**
 * 
 */
package edu.ku.cete.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.GroupRestrictions;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.user.UserOrganization;
import edu.ku.cete.domain.user.UserOrganizationsGroups;

/**
 * @author neil.howerton
 *
 */
public interface UserOrganizationsGroupsDao {
    void addUserOrganizationsGroups(UserOrganizationsGroups userGroup);
    
    void updateUserOrganizationsGroups(UserOrganizationsGroups userGroup);
    
    int updateUserOrganizationsGroupsForKidsProcess(UserOrganizationsGroups userGroup);
    
    void deleteUserOrganizationsGroups(@Param("id") long id);
    
    void deleteUserOrganizations(@Param("id") long id);
    
    UserOrganizationsGroups getUserOrganizationsGroups(@Param("id") long id);
  
    List<UserOrganizationsGroups> getByUserId(@Param("userId") long userId);
    
    List<UserOrganizationsGroups> getByUserIds(@Param("userIds") Set<Long> userIds);
  
    List<UserOrganizationsGroups> getByUserIdAndOrganization(@Param("userId")long userId, @Param("organizationId")long organizationId);
    
    UserOrganizationsGroups getByUserIdOrganizationIdGroupId(@Param("userId")long userId, @Param("organizationId")long organizationId, @Param("groupId")long groupId);
    
    UserOrganizationsGroups getByActivationNo(String activationNo);
    
    List<UserOrganizationsGroups> getByUserAndStatus(@Param("userId")long userId, @Param("status")Integer status);
    
    List<UserOrganizationsGroups> getByOrganizationAndStatus(@Param("organizationIds")Set<Long> organizationIds,
    		@Param("status")Integer status);
    
    List<UserOrganizationsGroups> getByGroupIdAndStatus(@Param("groupId")long groupId, @Param("status")Integer status);
    
    List<UserOrganizationsGroups> getByUserOrgAndOrgChildren(@Param("userId")long userId, @Param("organizationId")long organizationId);
    
    long getLastId();

	void addUsersOrganizations(UserOrganizationsGroups userOrganizationsGroups);
	
	int updateUsersOrganizationsForKidsProcess(UserOrganizationsGroups userOrganizationsGroups);
	
	void addUsersOrganization(UserOrganization userOrganization);
	
	
	/**
	 * @param organizationId
	 * @param aartUserId
	 */
	void updateUsersOrganizations(@Param("organizationId")long organizationId, 
			@Param("aartUserId")long aartUserId,
			@Param("modifiedUser")long modifiedUser,
			@Param("modifiedDate")Date modifiedDate);
	
    int updateUserOrgSelectiveByPrimaryKey(UserOrganizationsGroups userOrganizationsGroups);
    int updateUserOrgGrpSelectiveByPrimaryKey(UserOrganizationsGroups userOrganizationsGroups);
    List<UserOrganizationsGroups> getUserOrganizationsByUserId(@Param("aartUserId")long aartUserId);
    List<UserOrganizationsGroups> getUserOrganizationsGroupsByUserId(@Param("aartUserId")long aartUserId);
    void insertUserOrgGroup(UserOrganizationsGroups userOrganizationsGroups);
    
	void updateDefaultUserOrganizationAndGroup(@Param("userId") Long userId, 
			@Param("userOrganizationGroupId") Long userOrganizationGroupId, 
			@Param("userOrganizationId") Long userOrganizationId,
			@Param("modifiedUser")long modifiedUser,
			@Param("modifiedDate")Date modifiedDate);
	
    //US16245 : Added to activate and Deactivate the User Manually
	void changeUserStatusByIds(@Param("aartUserIds")StringBuilder aartUserIds,
			                   @Param("status")Integer status,
			                   @Param("modifiedUser")long modifiedUser,
			                   @Param("currentGroupId")long currentGroupId,
			                   @Param("assessmentProgramId")long assessmentProgramId,
			       			   @Param("modifiedDate")Date modifiedDate);
	
	long IsValidPDTrainingUser(@Param("firstname") String firstname,  @Param("lastname") String lastname, @Param("uniqidentifier")String uniqidentifier );
	void updatepdtrainingflag(@Param("userid") long userid);
		
	//US16368 : Added to check user is authorized or not
	Long checkIsUserAuthorized(@Param("userId") Long userId);		

	/**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
	void purgeUser(@Param("id") Long id);
	void deleteUserOrganizationsbyUserId(@Param("id")Long id);
	
	/**
	 *US16533:  
	 *This will update users default role based on user id, groupId(Instead of user organization group Id) and organizationId  
	 */
	void updateUserDefaultRoleByOrganizationAndGroup(@Param("userId") Long userId, 
			@Param("userGroupId") Long userGroupId, 
			@Param("userOrganizationId") Long userOrganizationId,
			@Param("modifiedUser")long modifiedUser,
			@Param("modifiedDate")Date modifiedDate);
	
	/**
     * Sudhansu.b : Added for US_16821(provide UI TO move Users)
     */
	
	List<Long> getAllOrganizationsByUserId(@Param("userId") Long userId);

	void updateUserOrganizationByUserIdAndOrganizationId(@Param("userId") Long userId,
			@Param("newOrganizationId") Long newOrganizationId,
			@Param("oldOrganizationId")  Long oldOrganizationId,
			@Param("modifiedUser")long modifiedUser,
			@Param("modifiedDate")Date modifiedDate);
  //US17687 :for audit user story
	List<UserOrganization> getAllUserOrganizationAndRole(@Param("userId") long userid);

	Long checkUserStatusById(@Param("userId") Long id);

	List<Groups> getGroupsByUserOrganization(@Param("userId") Long userId);

	List<UserOrganizationsGroups> getUserOrganizationsGroupsByOrgType(GroupRestrictions groupRestrictions);

	void downgradeUserTo(@Param("downgradeUserGroupType") String downgradeUserGroupType, 
		@Param("organizationUserGroupId") Long organizationUserGroupId,
		@Param("groupRestriction") GroupRestrictions groupRestriction);

	List<Long> getUserIdsByUserOrgGroupIds(@Param("userOrgGroupIds") Set<Long> userOrgGroupIds);

	List<Long> getAllUserOrganiztionGroupIds(@Param("userId") Long userId);

	UserOrganizationsGroups getByOrganizationRoleId(@Param("userId")Long userId, @Param("organizationId")Long organizationId, 
		@Param("groupId")Long groupId);

	String getActivationNoByUserId(@Param("userId")Long userId);

	UserOrganizationsGroups getInactiveUserOrganizationsGroups(@Param("id")Long id);
	
	Integer hasDefaultOrganization(@Param("userId")Long userId);
	
	List<UserOrganizationsGroups> getDistinctGroupCodesBasedOnUserIds(@Param("userIds") List<Long> userIds);
	
	Long checkIfAuthorizedUser(@Param("userId") Long userId, @Param("assessmentProgramId") Long assessmentProgramId,@Param("currentGroupId")Long currentGroupId);

}
