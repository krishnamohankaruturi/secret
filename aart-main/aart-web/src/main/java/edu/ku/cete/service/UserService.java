/**
 * 
 */
package edu.ku.cete.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.UserAssessmentPrograms;
import edu.ku.cete.domain.UserSecurityAgreement;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganization;
import edu.ku.cete.domain.user.UserPasswordReset;
import edu.ku.cete.domain.user.UserRecord;
import edu.ku.cete.domain.user.UserRequest;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.web.DLMPDTrainingDTO;
import edu.ku.cete.web.ScorersAssignScorerDTO;
import edu.ku.cete.web.UserDTO;

/**
 * @author nicholas.studt
 */
public interface UserService extends Serializable {

    /**
     * @param toAdd object to add.
     * @return {@link User}
     */
    @PreAuthorize(value = "hasRole('PERM_USER_CREATE')")
    User add(User toAdd);
    /**
     * @param register object to register.
     * @return {@link User}
     */
    User register(User toAdd,long organizationId) throws ServiceException;

    /**
     * @param id Id of the object to delete.
     * @return boolean was the object deleted.
     */
    @PreAuthorize(value = "hasRole('PERM_USER_DELETE')")
    boolean delete(Long id);

    /**
     * Get user by id.
     *
     * @param userId
     *            Id of the user.
     * @return {@link User}
     */
    User get(Long userId);
    
    /**
	 * Biyatpragyan Mohanty : US11240 : User Management ViewEdit User
	 * Get User details, add organization hierarchies.
     * @param userId
     * @return
     */
    User getAllDetails(Long userId);

    /**
     * Get list of user's organizations.
     * @param userId {@link Long}
     * @return List<Organization>
     */
    //@PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getOrganizations(Long userId);

    /**
     * Get list of user's groups.
     * @param userId {@link Long}
     * @return List<Groups>
     */
    //@PreAuthorize(value = "hasRole('PERM_ROLE_VIEW')")
    List<Groups> getGroups(Long userId);

    /**
     * Validate user login information.
     * @param userName User userName.
     * @return {@link User}
     */
    User getByUserName(String userName);

    /**
     * Find a user by their email address. The email should be unique so there
     * is only one user per email address.
     *@param email {@link String}
     *@return {@link User}
     */
    User getByEmail(String email);
    
    /**
     * DE10344 : user validation done either by username or emailid in db fields
     * @param email
     * @return
     */
    User getByEmailorUserName(String email);
    
    /**
     *
     *@param organizationId long
     *@param uniqueCommonIdentifier String
     *@return List<User>
     */
    //@PreAuthorize(value = "hasRole('PERM_USER_VIEW')")
    List<User> getByOrganizationAndUniqueCommonIdentifier(long organizationId, String uniqueCommonIdentifier);
    
    
    /**
     * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
     * Get users list by selected organization.
     * @param criteria
     * @param sortByColumn
     * @param sortType
     * @param offset
     * @param limitCount
     * @return
     */
    //@PreAuthorize(value = "hasRole('PERM_USER_VIEW')")
    List<User> getByOrganization(Map<String, Object> criteria, String sortByColumn, String sortType, Integer offset, Integer limitCount);
    
    /**
     * Get users list by selected organization
     * @param criteria
     * @param sortByColumn
     * @param sortType
     * @param offset
     * @param limitCount
     * @param rosterId
     * @return
     */
    //@PreAuthorize(value = "hasRole('PERM_USER_VIEW')")
    List<User> getByOrganizationWithRosterAssigned(Map<String, Object> criteria, String sortByColumn, String sortType, Integer offset, Integer limitCount, long rosterId);
    
    
    /**
     * Biyatpragyan Mohanty : US13736 : User Management: View Users in Record Browser View
     * Get count of users list by selected organization.
     * @param organizationId
     * @return
     */
    Integer countAllUsersToView(Map<String, Object> criteria);

    /**
     * @param saveOrUpdate the object to be saved or updated.
     * @return {@link User}
     * @throws ServiceException {@link ServiceException}
     */
    User saveOrUpdate(User saveOrUpdate) throws ServiceException;

    /**
     *
     * @param groupIds List<Long>
     * @param userId long
     * @param organizationId long
     * @throws ServiceException {@link ServiceException}
     */
    @PreAuthorize("hasRole('PERM_ROLE_ASSIGN')")
    void saveUserOrganizationsGroups(List<Long> groupIds, long userId, long organizationId) throws ServiceException;


    /**
     * @param newUser
     * @param organization
     * @param contractingOrganizationTree
     * @param currentUser
     * @return
     */
    @PreAuthorize("hasRole('PERM_USER_UPLOAD')")
    UploadedUser createOrAddUserOrganizationsGroups(UploadedUser newUser, User existingUser, Organization organization,
            ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser);

    /**
     * @param toUpdate the object to update.
     * @return {@link User}
     * @throws ServiceException {@link ServiceException}
     */
   // @PreAuthorize(value = "hasRole('PERM_USER_MODIFY')") - to allow update displayname
    User update(User toUpdate) throws ServiceException;

    /**
     * @param toUpdate {@link User}
     * @param isReset {@link boolean}
     * @param authToken {@link String}
     * @return {@link User}
     * @throws ServiceException ServiceException
     */
    User updateWithEncodedPassword(User toUpdate, boolean isReset, String authToken) throws ServiceException;
   
    /**
     * Added for US-14985
     * For setting password in user password history table
     * @param user
     * @return
     * @throws ServiceException
     */
    void setpasswordHistory(User user) throws ServiceException;
   
    /**
     * Added for US-14985
     * For getting last one year password history.
     * @param days 
     */
    ArrayList<User> getPastYearPasswordHistory (Long userId, int days) throws ServiceException;
    
    /**
     * @param educator {@link User}
     * @param organizations {@link List}
     * @param userOrganization {@link Organization}
     * @return {@link User}
     */
    @PreAuthorize(value = "hasRole('PERM_USER_SEARCH') and hasRole('PERM_USER_VIEW') and hasRole('PERM_USER_MODIFY') ")
    User addIfNotPresent(User educator,
            Collection<Organization> organizations, Organization userOrganization);

    @PreAuthorize(value = "hasRole('PERM_USER_SEARCH') and hasRole('PERM_USER_VIEW') and hasRole('PERM_USER_MODIFY') ")
    User addIfNotPresentForTASC(User educator, Collection<Organization> organizations, Organization userOrganization, String courseStatus);
    
    
    /**
     * This method checks each uploaded user to make sure that it is a valid user record.
     * TODO document more.
     * 1. Checks users against user organization tree..
     * 2. Checks users against contracting organization tree etc.. 
     * @param uploadedUsers List<UploadedUser>
     * @param invalidUsers List<UploadedUser>
     * @param currentOrg {@link Organization}
     * @param contractingOrganizationTree {@link ContractingOrganizationTree}
     * @param currentUser {@link UserDetailImpl}
     * @throws ServiceException ServiceException
     * @return createdUsers - number of users that were created from the file.
     */
    int checkUploadedUsers(List<UploadedUser> uploadedUsers, List<UploadedUser> invalidUsers, Organization currentOrg,
    		ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser, List<UploadedUser> successUsers, Long parentOrgId)
            throws ServiceException;
    

    int checkUploadedPDTrainingResults(List<User> uploadedUsers, List<User> invalidUsers, Organization currentOrg,
    		ContractingOrganizationTree contractingOrganizationTree, UserDetailImpl currentUser, List<User> successUsers, Long parentOrgId)
            throws ServiceException;
    
    /**
     * Biyatpragyan Mohanty : US14307 : User Management: Add Users Manually
     * Save User information including user, organization and roles data.
     * @param userRequest
     * @param overwrite
     * @return
     * @throws ServiceException
     */
    UserRequest saveUser(UserRequest userRequest, boolean overwrite, Boolean isFindUser)
            throws ServiceException;
    
    /**
     * @param userPasswordReset {@link UserPasswordReset}
     * @return List<UserPasswordReset>
     */
    List<UserPasswordReset> getUserPasswordResetDetails(UserPasswordReset userPasswordReset);
    
    /**
     * @param userPasswordReset {@link UserPasswordReset}
     * @return int
     */
    int updateUserPasswordResetDetails(UserPasswordReset userPasswordReset);
	/**
	 * pass in the list of educators and it returns the educators with valid and
	 * invalid records marked.
	 * 
	 * @param userRecords
	 * @param diffContractingOrganizationIds
	 * @return
	 */
	List<? extends UserRecord> verifyEducatorIds(List<? extends UserRecord> userRecords,
			Collection<Long> diffContractingOrganizationIds);
	
	/**
	 * @param userId
	 * @return
	 */
	List<Organization> getOrganizationsByUserId(Long userId);
	
	/**
	 * @param organizationId
     * @param sortByColumn
     * @param sortType
     * @param offset
 
	 * @return
	 */
	List<User> getUsersByOrgId(Long organizationId, String sortByColumn, String sortType, 
			Integer offset, Integer limitCount);
	
	/**
	 * @param organizationId
	 * @return
	 */
	Integer countUsersByOrgId(Long organizationId);
	
	/**
	 * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
	 * Save user security agreement details for assessment program. 
	 * @param aartUserId
	 * @param assessmentProgramId
	 * @param schoolYear
	 * @param agreementElection
	 * @param agreementSignedDate
	 * @param expireDate
	 * @param signerName
	 * @return
	 */
	Integer saveSecurityAgreementInfo(Long aartUserId, Long assessmentProgramId, Long schoolYear, boolean agreementElection, Date agreementSignedDate, String expireDate, String signerName);
	
	/**
	 * Biyatpragyan Mohanty : US14095 : Profile: User Security Agreement on Profile Modal
	 * Retrieve user security agreement details for given user and assessment program.
	 * @param aartUserId
	 * @param assessmentProgramId
	 * @param currentSchoolYear 
	 * @return
	 */
	List<UserSecurityAgreement> getSecurityAgreementInfo(Long aartUserId, Long assessmentProgramId, Long currentSchoolYear);
	
	List<UserSecurityAgreement> getSecurityAgreement(Long aartUserId);
	
	void updateDefaultOrgAndRole(Long aartUserId, Long userOrganizationId, Long userOrganizationGroupId) throws ServiceException;
	
	List<User> getByUniqueCommonIdentifierAndOrgIds(String uci, List<Long> orgIds);
	/**
	 * 
	 * @param userPasswordReset
	 * @return
	 */
	int insertUserPasswordResetDetails(UserPasswordReset userPasswordReset);
	
 	/**
 	 * 
 	 * @param userId
 	 * @return
 	 */
 	Date selectLastExpiredPasswordResetDateById(Long userId);
   
 	/**
 	 * 
 	 * @param userId
 	 * @return
 	 */
 	int updateLastExpiredPasswordResetDate(Long userId);

 	List<UserSecurityAgreement> getDLMActiveUserSecurityAgreementInfo(Long aartUserId, Long assessmentProgramId, Long currentSchoolYear);

    /**
	 * Prasanth :  US16352 : To upload data file using spring batch     
	 */
 	UploadedUser isValidUserIdentifierAndOrg(UploadedUser uploadedUser,
			Organization org,
			ContractingOrganizationTree contractingOrganizationTree,
			Long parentOrgId, boolean isForInsert);
 	

    /**
	 * Prasanth :  US16352 : To upload data file using spring batch and to implement queue      
	 */
 	UploadedUser createOrAddUserOrganizationsGroupsUpload(
			UploadedUser newUser, User existingUser, Organization organization,
			ContractingOrganizationTree contractingOrganizationTree,
			UserDetailImpl currentUser);
 	/**
	 * Prasanth :  US16246 :validations while uploading primary and secondary roles      
	 */
 	void validatePrimarySecondaryRole(List<Groups> groupsList, UploadedUser uploadedUser, boolean isForInsert);

 	/**
 	 * Added for US16243 to check whether user is DLMAuthenticated or not
 	 * @param currentSchoolYear 
 	 * **/
	boolean checkDLMAuthentication(User user, int currentSchoolYear);
 	
 	/**
 	 * Added for US-14985
 	 * This method gives the list of users whose password is going to expire. 
 	 */
 	ArrayList<User> getPasswordExpirationAlertUsers(int noOfDays);
	
 	/**
 	 * Added for US-14985
 	 * This method gives the last password update of that user
 	 */
 	ArrayList<User> lastUserPasswordUpdate(User user) throws ServiceException;
 	
 	/**
 	 * Added for US-14985
 	 *This method gives the rules to be followed for validating password changing. 
 	 */
	ArrayList<Category> getPasswordPolicyRules(String typeCode) throws ServiceException;

	/**
	 * Added for US16239 : To validate the assessment program of user organization
	 * @param uploadedUser
	 * @return
	 */
	void validateUserAssessmentProgram(UploadedUser uploadedUser);
	
	/**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
	int moveRoster(UserRequest userRosterToMove, boolean overwrite, String rosterMoveuserIdParam)  throws ServiceException;
	/**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
	 * @param userDetails 
	 * @return 
     */
	Integer purgeUser(String purgeUserId, UserDetailImpl userDetails);
	//Added to fix DE10302
	boolean checkRosterByUserId(String puserId);
	
	boolean isPdTrainingCompleted(long userId,  int currentSchoolYear);
	//DE11071 - count number of teacher to view
	Integer countAllTeacherToView(Map<String, Object> criteria);	
	List<ScorersAssignScorerDTO> getScorersByAssessmentProgramId(
			Long stateId, Long assessmentPrgId, Long districtId,
			Long schoolId);	
	Integer getCountScorersByAssessmentProgramId(
			Long assessmentProgramId,Long stateId,
			Long districtId,Long schoolId,Long testingProgramId,
			String scorerRoleCode,	Map<String,String> scorerRecordCriteriaMap
			);

	/**
	 * Sudhansu.b : Added for US_16821(provide UI TO move Users)
	 */
	boolean moveUser(Long userId, Long newOrganizationId, Long oldOrganizationId, UserDetailImpl loggedInUser)
			throws ServiceException;

	void addIfUserAssesmentIsNotPresent(User user, Long userOrgGrpId, Boolean setDefault);
	
	void insertIntoDomainAuditHistory(Long objectId, Long createdUserId,String action, String source, String beforeUpdate, String afterUpdate);
	
	UserDTO getMonitorCCQScoresDetails(Long userId);
	User addIfNotPresent(User educator, Long stateId,
			Organization userOrganization);
	
	//Us17687 :For user audit history
	Date getLastJobRunDateTime();
	
	ArrayList<DomainAuditHistory> getAuditLoggedUser(String dateForMySql);
	
	boolean addToUserAuditTrailHistory(DomainAuditHistory domainAuditHistory);
	
	List<Long> getUnProcessedAuditLoggedUser();
	
	void changeStatusToCompletedProcessedAuditLoggedUser(Long successIds,String status);
	
	void updateUserAssessmentProgramToDefault(User user, Long organizationId, Long groupId, boolean isDefault, Long userOrganizationGroupId);

	UserAssessmentPrograms getUserDefaultAssessmentProgram(Long userId);
	
	List<UserAssessmentPrograms> getUserRoles(long userId, String organizationStateCode, String organizationDistrictCode, String organizationSchoolCode);
	List<Authorities> getByUserAndGroupExcludeLockdown(Long userId, Long groupId, Long organizationId, Long stateId,
			Long AssessmentProgramId);
	
	public List<Authorities> getGroupExcludeLockdownForGlobalAdmin(Long userId, Long groupId, Long organizationId);
	
	@PreAuthorize(value = "hasRole('PERM_USER_SEARCH') and hasRole('PERM_USER_VIEW') and hasRole('PERM_USER_MODIFY') ")
	User addEducatorIfNotPresentForTASCByEmailUserId(User educator,	Collection<Organization> organizations, Organization userOrganization, String courseStatus);
	
	List<Long> getUsersByOrgIdForInterim(Long organizationId, Long assessmentProgId);
	
	List<Long> getKIDSEmailUsersPerSchoolIds(String aypSchoolId, String attendanceSchoolId);
	
	User getUserDetailsByIdOrgAssessmentProgram(Long userId, Long assessmentProgramId, Long userOrgId, Long groupId);
	User getByUserNameIncludeSuperUser(String userName, boolean superUser);

	List<UserOrganization> getUserOrganizationsByOrgId(Long sourceSchoolId);
	
	void transferUserOrganization(UserOrganization userOrganization, Long destinationSchool);
	
	void disableUserOrganization(UserOrganization userOrganization);
	
	Long getCountByOrganizationId(Long organizationId);
	@PreAuthorize(value = "hasRole('PERM_USER_SEARCH') and hasRole('PERM_USER_VIEW') and hasRole('PERM_USER_MODIFY') ")
	User checkEducatorExistsForKELPA(User educator,	Organization userOrganization);
	
	List<UserOrganization> getDestUserOrganizationsByOrgId(Long destinationSchool, Long aartUserId);
	
	List<User> getTeachersWithRosteredDLMKidsInSchoolSubjectsAndGrades(Long schoolId, List<Long> contentAreaIds, List<Long> gradeIds, Long schoolYear);
	List<UserOrganization> getDeactiveUserOrganizationsByOrgId(Long schoolId);
	void enableUserOrganization(UserOrganization userOrganization);
	void setStatusToInactive(Long userOrganizationId);
	Long getDeactivateCountByOrganizationId(Long organizationId);

	List<User> getInactiveUsersForAdmin(Map<String, Object> criteria, String sortByColumn, String sortType, int i,
			Integer limitCount);

	Integer getInactiveUsersCountForAdmin(Map<String, Object> criteria);

	User getAllInactiveUserDetailsForAdmin(long userId);

	List<UserAssessmentPrograms> getInactiveUserRolesForAdmin(long userId);

	Integer deleteInvalidUserContext(Long userId);

	Boolean doesUserHaveHighRoles(Long userId, long currentGroupsId, long currentOrganizationId, Long currentAssessmentProgramId);
	List<User> getUsersByUserType(Map<String, Object> criteria, String sortByColumn, String sortType, Integer offset, Integer limitCount);
	Integer countAllInternalUsersToView(Map<String, Object> criteria);
	Boolean updateInternalUserFlag(Long userId, Boolean internalUserFlag);
	List<User> getAllUsersBySearch(Map<String, Object> criteria, String sortByColumn, String sortType, int i,
			Integer limitCount);
	Integer getCountForFindUsers(Map<String, Object> criteria);
	Integer updateInvalidUserContext(Long claimUserId, Long userId);
	String getDistrictNames(Long districtId);
	List<Long> getDTCUserIdsByOrganizationName(String districtName);
	List<Organization> getUsersOrganizationsByUserId(Long userId);
	User getActiveOrInactiveUser(Long userId);
	User getByUserIdForUploadResuts(Long  userId, Long orgId);
	Long checkUserOrganizationCount(Long userId);
	void deactivateUser(Long userId);
	void activateUser(Long userId);
	List<User> getAllUsersByAssessmentProgramAndOrgId(Long organizationId,
			Long assessmentProgramId);
	void removeRolesfromUsersByOrgAndAssessment(Long organizationId,
			Long assessmentProgramId, Long modifiedUserId);
	void resetDefaultOrganization(Long id, Long userId);
	List<Long> getDistrictLevelUserIdsByOrgIdAndAP(Long userId, String assessmentCode);
	void auditClaimUserDetails(Long userIdToBeClaimed);
	List<User> getByEducatorIdentifierForGRF(String educatorIdentifier, Long stateId);
	void setITIStatusForCurrentAssementProgram(User user);
	Integer getTeacherCount(Long userId, Long schoolId, String teacherCode);
	User getByExternalId(String externalid);
	User getUserByExternalIddAndOrgId(String externalId,Long userId);
	Long getUserStateId(Long organizationId);
	 List<DLMPDTrainingDTO> getTrainingDetails(Long userId);

}
