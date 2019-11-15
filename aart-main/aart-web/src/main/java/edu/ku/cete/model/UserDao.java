package edu.ku.cete.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserOrganization;
import edu.ku.cete.domain.user.UserPasswordReset;
import edu.ku.cete.web.DLMPDTrainingDTO;
import edu.ku.cete.web.ScorersAssignScorerDTO;
import edu.ku.cete.web.UserDTO;
import edu.ku.cete.web.UserDetailsAndRolesDTO;
import edu.ku.cete.web.UserSecurityAgreemntDTO;

/** user Dao. */
public interface UserDao {

	/**
	 * Create a new user.
	 * 
	 * @param toAdd
	 *            object to add.
	 * @return number of rows affected.
	 */
	int add(User toAdd);

	/**
	 * @param id
	 *            Id of the user.
	 * @return number of rows affected.
	 */
	int delete(@Param("id") long id);

	/**
	 * Get user by id.
	 *
	 * @param userId
	 *            Id of the user.
	 * @return {@link User}
	 */
	User get(@Param("id") long userId);

	/**
	 * Biyatpragyan Mohanty : US11240 : User Management ViewEdit User Get User
	 * Details
	 * 
	 * @param userId
	 * @return
	 */
	List<User> getAllDetails(@Param("id") Long userId);

	/**
	 * Biyatpragyan Mohanty : US11240 : User Management ViewEdit User Get
	 * Organization Hierarchy
	 * 
	 * @param orgId
	 * @return
	 */
	List<Organization> getOrganizationHierarchy(@Param("id") long orgId);

	/**
	 * Get list of user's organizations.
	 * 
	 * @param userId
	 * @return
	 */
	List<Organization> getOrganizations(@Param("userId") long userId);

	/**
	 * Get list of user's groups.
	 * 
	 * @param userId
	 * @return
	 */
	List<Groups> getGroups(@Param("userId") long userId);

	/**
	 * Check a user's login.
	 * 
	 * @param username
	 *            User name of the user.
	 * @return {@link User}
	 */
	User getByUserName(@Param("userName") String username);

	/**
	 *
	 * @param email
	 *            {@link String}
	 * @return {@link User}
	 */
	User getByEmail(String email);

	/**
	 * DE10344 : user validation done either by username or emailid in db fields
	 * 
	 * @param email
	 * @return
	 */
	User getByEmailorUserName(String email);

	/**
	 * if active flag is passed it is included in the search. if not it is not
	 * included as a search parameter.
	 * 
	 * @param uniqueCommonIdentifier
	 *            long
	 * @param organizations
	 *            {@link List}
	 * @param activeFlag
	 *            {@link Boolean}
	 * @return List<{@link User}>
	 */
	List<User> getByUniqueCommonIdentifierAndTree(@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier,
			@Param("organizationIds") Collection<Long> organizationIds, @Param("activeFlag") Boolean activeFlag);

	User getByUniqueCommonIdentifierAndState(@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier,
			@Param("stateId") Long stateId, @Param("activeFlag") Boolean activeFlag);

	/**
	 * Biyatpragyan Mohanty : DE6303 : Error found in TC109720:
	 * US14140_052_AddStudentManually_SuccessfulSaveonlyReq Get list of users
	 * per combination of educator identifier and organization ids.
	 * 
	 * @param uniqueCommonIdentifier
	 * @param organizationIds
	 * @return
	 */
	List<User> getByUniqueCommonIdentifierAndOrgList(@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier,
			@Param("organizationIds") Collection<Long> organizationIds);

	/**
	 *
	 * @param organizationId
	 *            long
	 * @param uniqueCommonIdentifier
	 *            long
	 * @return List<User>
	 */
	List<User> getByOrganizationAndUniqueCommonIdentifier(@Param("organizationId") long organizationId,
			@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier);

	/**
	 * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record
	 * Browser View Get users list by selected organization.
	 * 
	 * @param organizationId
	 * @return
	 */
	List<User> getByOrganization(Map<String, Object> filters);

	List<User> getByOrganizationWithRosterAssigned(Map<String, Object> filters);

	/**
	 * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record
	 * Browser View Get count of users list by selected organization.
	 * 
	 * @param organizationId
	 * @return
	 */
	Integer countAllUsersToView(Map<String, Object> criteria);

	/**
	 * @return the id of the sequence.
	 */
	long lastid();

	/**
	 * @param toUpdate
	 *            the object to update.
	 * @return number of rows affected.
	 */
	int update(User toUpdate);

	/**
	 * @param toUpdate
	 * @return
	 */
	int updateSelectiveByPrimaryKey(User toUpdate);

	/**
	 *
	 * @param uniqueCommonId
	 *            {@link String}
	 * @param organizationId
	 *            {@link Long}
	 * @return {@link User}
	 */
	User findShellUserByIdentifierAndOrganization(@Param("uniqueCommonIdentifier") String uniqueCommonId,
			@Param("organizations") Collection<Organization> organizations);

	/**
	 *
	 * @param uniqueCommonIdentifier
	 *            long
	 * @param organizationIds
	 *            {@link List<Long>}
	 * @return List<{@link User}>
	 */
	List<User> getByUniqueCommonIdentifierAndOrgIdsWithNoStatus(
			@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier,
			@Param("organizationIds") List<Long> organizationIds);

	/**
	 * @param uniqueCommonIdentifiers
	 *            {@link List}
	 * @param diffContractingOrganizationIds
	 *            {@link List}
	 * @return {@link List}
	 */
	List<User> getByUniqueCommonIdentifiersAndOrgIdsWithNoStatus(
			@Param("uniqueCommonIdentifiers") List<String> uniqueCommonIdentifiers,
			@Param("organizationIds") Collection<Long> diffContractingOrganizationIds);

	/**
	 * @param uniqueCommonIdentifier
	 * @param organizationDisplayId
	 * @param activeFlag
	 * @return
	 */
	List<User> getByUniqueCommonIdentifierAndDisplayId(@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier,
			@Param("organizationDisplayId") String organizationDisplayId, @Param("activeFlag") Boolean activeFlag);

	User getByIdentifierAndFirstLastName(@Param("userId") Long userId, @Param("firstName") String firstName,
			@Param("lastName") String lastName);

	void insertUserPDTrainingDetail(User toAdd);

	/**
	 * @param organizationId
	 * @return
	 */
	List<User> getUsersByOrgId(@Param("organizationId") Long organizationId, @Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType, @Param("offset") Integer offset,
			@Param("limitCount") Integer limitCount);

	List<Long> getUsersByOrgIdForInterim(@Param("organizationId") Long organizationId,@Param("assessmentProgramId") Long assessmentProgramId);

	/**
	 * @param organizationId
	 * @return
	 */
	Integer countUsersByOrgId(@Param("organizationId") Long organizationId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	Date selectLastExpiredPasswordResetDateById(@Param("userId") Long userId);

	/**
	 * 
	 * @param userId
	 * @return
	 */
	int updateLastExpiredPasswordResetDate(@Param("userId") Long userId,@Param("modifiedUser") Long modifiedUser,
			@Param("modifiedDate") Date modifiedDate);

	/**
	 * @author Venkata Krishna Jagarlamudi US15251: User Data Extract
	 * @param organizationId
	 * @return
	 */
	List<UserDetailsAndRolesDTO> getUserDetailsAndRolesByOrgId(@Param("organizationId") Long organizationId,
			@Param("assessmentPrograms") List<Long> assessmentPrograms,
			@Param("includeInternalUsers") boolean includeInternalUsers);

	/**
	 * Added During US16243 to check whether user completed PD training or not
	 * for current school year
	 * 
	 * @param currentSchoolYear
	 */
	Boolean isPdTrainingCompleted(@Param("id") long userId, @Param("currentSchoolYear") int currentSchoolYear);

	/**
	 * Added During US16243 to check whether user signed security agreement or
	 * not
	 **/
	Boolean isSecurityAgreementSigned(@Param("id") long userId);

	/**
	 * Added for US-14985
	 */
	User getActiveOrInactiveUser(@Param("id") long id);

	/**
	 * Added for US-14985
	 */
	int setUserPasswordHistory(UserPasswordReset user);

	/**
	 * Added for US-14985
	 */
	ArrayList<User> getPastYearPasswordHistory(@Param("id") Long userId, @Param("days") int days);

	/**
	 * Added for US-14985
	 */
	ArrayList<User> getPasswordExpirationAlertUsers(@Param("noOfDays") int noOfDays);

	/**
	 * Added for US-14985
	 */
	ArrayList<User> lastUserPasswordUpdate(@Param("id") Long userId);

	/**
	 * Added for US-14985
	 */
	ArrayList<Category> getPasswordPolicyRules(@Param("categorycode") String typeCode);

	/**
	 * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
	 */
	Integer moveUserRoster(@Param("deleteUserRosterId") Long deleteUserRosterId,
			@Param("rosterMoveuserIdParam") int rosterMoveuserIdParam, @Param("modifiedUser") Long modifiedUser);

	Integer purgeUser(@Param("id") Long id, @Param("modifiedUser") Long modifiedUser,
			@Param("modifiedDate") Date modifiedDate);

	// DE11071-To count number of teacher to show
	Integer countAllTeacherToView(Map<String, Object> criteria);

	List<ScorersAssignScorerDTO> getScorersByAssessmentProgramId(@Param("stateId") Long stateId,@Param("assessmentPrgId") Long assessmentPrgId,@Param("districtId") Long districtId,
			@Param("schoolId") Long schoolId);

	Integer getCountScorersByAssessmentProgramId(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("stateId") Long stateId, @Param("districtId") Long districtId, @Param("schoolId") Long schoolId,
			@Param("testingProgramId") Long testingProgramId, @Param("scorerRoleCode") String scorerRoleCode,
			@Param("scorerRecordCriteriaMap") Map<String, String> scorerRecordCriteriaMap);

	List<User> getByUniqueCommonIdentifierAndDisplayIdForStateLevel(
			@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier, @Param("orgId") Long orgId);

	List<User> getByEducatorIdentifierAndDisplayIdForStateLevel(
			@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier, @Param("orgId") Long orgId);

	UserDTO getMonitorCCQScoresDetails(@Param("id") long userId);

	// Us17270 : To create json data
	User getJsonFormatData(@Param("id") long userId);

	List<Groups> getAllGroups(@Param("id") long userId);

	Boolean getIsSingleUserExists(@Param("groupCode") String groupCode, @Param("organizationId") Long organizationId,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("organizationType") String organizationType,
			@Param("userId") Long userId);

	Boolean getIsRestrictedToSingleUser(@Param("groupCode") String groupCode, @Param("stateId") Long stateId,
			@Param("assessmentProgramId") Long assessmentProgramId);

	List<UserSecurityAgreemntDTO> getUserSecurityAgreementDetails(@Param("organizationIds") List<Long> organizationIds,
			@Param("assessmentPrograms") List<Long> assessmentPrograms,
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("includeInternalUsers") boolean includeInternalUsers);

	List<Long> getKIDSEmailUsersPerSchoolIds(@Param("aypSchoolId") String aypSchoolId,
			@Param("attendanceSchoolId") String attendanceSchoolId);

	List<UserOrganization> getUserOrganizationsByOrgId(@Param("sourceSchoolId") Long sourceSchoolId);

	void transferUserOrganization(UserOrganization userOrganization);

	void disableUserOrganization(UserOrganization userOrganization);

	Long getCountByOrganizationId(@Param("organizationId") Long organizationId);

	List<UserOrganization> getDestUserOrganizationsByOrgId(@Param("destinationSchoolId") Long destinationSchoolId,
			@Param("aartUserId") Long aartUserId);

	List<User> getTeachersWithRosteredDLMKidsInSchoolSubjectsAndGrades(@Param("schoolId") Long schoolId,
			@Param("contentAreaIds") List<Long> contentAreaIds, @Param("gradeIds") List<Long> gradeIds,
			@Param("schoolYear") Long schoolYear);

	List<UserOrganization> getDeactiveUserOrganizationsByOrgId(@Param("sourceSchoolId") Long sourceSchoolId);

	void enableUserOrganization(UserOrganization userOrganization);

	void setStatusToInactive(@Param("userOrganizationId") Long userOrganizationId);

	Long getDeactivateCountByOrganizationId(@Param("organizationId") Long organizationId);

	List<User> getInactiveUsersForAdmin(@Param("criteria") Map<String, Object> criteria,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType, @Param("offset") int offset,
			@Param("limitCount") Integer limitCount);

	Integer getInactiveUsersCountForAdmin(@Param("criteria") Map<String, Object> criteria);

	User getForAdmin(@Param("userId") long userId);

	List<Organization> getOrganizationsForAdmin(@Param("userId") long userId);

	List<Groups> getGroupsForAdmin(@Param("userId") long userId);

	Integer deleteInvalidUserContext(@Param("userId") Long userId);

	Boolean doesUserHaveHighRoles(@Param("userId") Long userId, @Param("currentGroupsId") long currentGroupsId,
			@Param("currentOrganizationId") long currentOrganizationId,
			@Param("currentAssessmentProgramId") Long currentAssessmentProgramId);

	List<User> getUsersByUserType(Map<String, Object> filters);

	Integer countAllInternalUsersToView(Map<String, Object> criteria);

	Boolean updateInternalUserFlag(@Param("userId") Long userId, @Param("internalUserFlag") Boolean internalUserFlag,
			@Param("modifiedUser") Long modifiedUser);

	List<User> getAllUsersBySearch(@Param("criteria") Map<String, Object> criteria,
			@Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType, @Param("offset") int offset,
			@Param("limitCount") Integer limitCount);

	Integer getCountForFindUsers(@Param("criteria") Map<String, Object> criteria);

	Integer updateInvalidUserContext(@Param("claimUserId") Long claimUserId, @Param("modifiedUser") Long modifiedUser);

	String getDistrictNames(@Param("districtId") Long districtId);

	List<Long> getDTCUserIdsByOrganizationName(@Param("districtName") String districtName);

	List<Organization> getUsersOrganizationsByUserId(@Param("userId") long userId);

	User getByUserIdForUploadResuts(@Param("userId") Long userId,@Param("orgId") Long orgId);

	Long checkUserOrganizationCount(@Param("userId") Long userId);

	void deactivateUser(@Param("modifiedUserId") Long modifiedUserId, @Param("userId") Long userId);

	void activateUser(@Param("modifiedUserId") Long modifiedUserId, @Param("userId") Long userId);

	List<User> getAllUsersByAssessmentProgramAndOrgId(@Param("organizationId") Long organizationId,
			@Param("assessmentProgramId") Long assessmentProgramId);

	void removeRolesfromUsersByOrgAndAssessment(@Param("organizationId") Long organizationId,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("modifiedUserId") Long modifiedUserId);

	void resetDefaultOrganization(@Param("id") Long id, @Param("userId") Long userId);


	List<Long> getDistrictLevelUserIdsByOrgIdAndAP(@Param("orgId")Long orgId, @Param("assessmentCode")String assessmentCode);

	List<User> getByEducatorIdentifierForGRF(@Param("uniqueCommonIdentifier") String uniqueCommonIdentifier, @Param("orgId") Long orgId);

	Integer getTeacherCount(@Param("userId") Long userId, @Param("schoolId") Long schoolId,@Param("teacherCode") String teacherCode);
	User getByExternalId(@Param("externalId") String externalId);
	
	Long getUserStateId(@Param("organizationId") Long organizationId);
	
	User getUserByExternalIdAndOrgId(@Param("externalId") String externalId, @Param("orgid") Long orgid);
	
	List<DLMPDTrainingDTO> getTrainingDetails(Long userId);

	String getEmailById(Long emailId);

	String getEducatorIdentifierById(Long educatorId);

	int updateEmailById(@Param ("emailname") String emailname,@Param("id")  Long id);

	int updateEducatorById(@Param ("educatorid") String educatorid,@Param("id") Long id);
}
