/**
 * 
 */
package edu.ku.cete.service.enrollment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PreAuthorize;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Educator;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.RosterExample;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.web.RosterDTO;

/**
 * @author neil.howerton
 *
 */
public interface RosterService {
	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	int countByExample(RosterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_DELETE')")
	int deleteByExample(RosterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_DELETE')")
	int deleteByPrimaryKey(Long id);

	/**
	 * @param roster             {@link Roster}
	 * @param attendanceSchoolId {@link Long}
	 * @return {@link List}
	 */
	// @PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_CREATE')")
	List<Roster> addIfNotPresent(Roster roster, Long attendanceSchoolId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	// @PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_CREATE')")
	Roster insert(Roster record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	// @PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_CREATE')")
	Roster insertSelective(Roster record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_VIEW')")
	List<Roster> selectByExample(RosterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	// @PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_VIEW')")
	Roster selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_MODIFY')")
	int updateByExampleSelective(@Param("record") Roster record, @Param("example") RosterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	// @PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_MODIFY')")
	int updateByExample(@Param("record") Roster record, @Param("example") RosterExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_MODIFY')")
	int updateByPrimaryKeySelective(Roster record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table public.roster
	 *
	 * @mbggenerated Mon May 07 11:35:01 CDT 2012
	 */
	// @PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_MODIFY')")
	int updateByPrimaryKey(Roster record);

	/**
	 *
	 * @param teacherId      long
	 * @param organizationId long
	 * @return List<Roster>
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_VIEW')")
	List<Roster> findByTeacherId(long teacherId, long organizationId);

	/**
	 * @param organizationId {@link Long}
	 * @return {@link List}
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_VIEW')")
	List<RosterDTO> getRosterDtoByOrg(long organizationId);

	/**
	 * @param organizationId {@link Long}
	 * @return {@link List}
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_VIEW')")
	List<RosterDTO> getRosterDtoInRosterIds(List<Long> rosterIds);

	/**
	 *
	 * @param rosterId long
	 * @return {@link Long}
	 */
	Integer getNumberOfStudents(long rosterId);

	/**
	 * @param scrsRecord                  {@link ScrsRecord}
	 * @param contractingOrganizationTree {@link ContractingOrganizationTree}
	 * @return {@link ScrsRecord}
	 */
	RosterRecord cascadeAddOrUpdate(RosterRecord scrsRecord, ContractingOrganizationTree contractingOrganizationTree);

	/**
	 * @param userId                 {@link Long}
	 * @param selectedOrganizationId {@link Long}
	 * @param restriction            {@link Restriction}
	 * @return {@link List}
	 */
	List<RosterDTO> getRosterDtoByUserAndOrg(long userId, long selectedOrganizationId, Restriction restriction);

	/**
	 * @param userId             {@link Long}
	 * @param restriction        {@link Restriction}
	 * @param userOrganizationId {@link Long}
	 * @return {@link List}
	 */
	List<Roster> getRosterByUserRestrictionAndOrganization(Long userId, Restriction restriction,
			Long userOrganizationId);

	List<Roster> getRostersForReports(Map<String, Object> parameters);

	/**
	 * @param organizationId
	 * @return
	 */
	Integer countRostersToViewByOrg(Long organizationId);

	/**
	 * @param criteria
	 * @param orderByClause
	 * @param offset
	 * @param limitCount
	 * @return
	 */
	List<RosterDTO> getRostersToViewByOrg(Map<String, Object> criteria, String orderByClolumn, String order,
			Integer offset, Integer limitCount);

	/**
	 * @param criteria
	 * @return
	 */
	Integer countRostersToViewByOrg(Map<String, Object> criteria);

	int updateRosterWithEnrollments(Roster record, Long[] addEnrollmentIds, Long[] delEnrollmentIds);

	int createRoster(Roster record, Long[] enrollmentIds);

	/**
	 *
	 * @param teacherId      long
	 * @param organizationId long
	 * @return List<Roster>
	 */
	@PreAuthorize(value = "hasRole('PERM_ROSTERRECORD_VIEW')")
	List<Roster> findRostersforTeacherId(long teacherId, long organizationId, int currentSchoolYear);

	void validateSchoolIdentifiers(RosterRecord rosterRecord, Long orgId, int currSchoolYear);

	/**
	 * @author Venkata Krishna Jagarlamudi US15407: Roster - Associate with school
	 *         year
	 * @param loggedInUserOrgId
	 * @return currentSchoolYear
	 */
	int getCurrentSchoolYear(long loggedInUserOrgId);

	/**
	 * @author bmohanty_sta Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 :
	 *         DLM Class Roster Report - online report Get educator details by
	 *         roster id.
	 * @param rosterId
	 * @return
	 */
	Educator getEducatorByRosterId(Long rosterId);

	List<Roster> getRostersByContentAreaAndEnrollment(Long contentAreaId, Enrollment enrollment);

	ContentArea getContentArea(String name, Long organizationId);

	GradeCourse findValidCourse(String stateCourseCode, Long contentAreaId);

	Roster getRosterMatchedWithStateCourseCode(Roster roster, Long attendeSchoolId);

	boolean isRosterExitsWithStateCourseCode(Roster roster, Long attendanceSchoolId);

	List<Roster> getRostersByCriteria(Roster roster, Long attendanceSchoolId);

	Restriction getRosterRestriction(UserDetailImpl userDetails);

	String getWebServiceCourseSectionName(String educatorFirstName, String educatorLastName, String subjectAreaAbbrName,
			String source);

	List<Roster> getRosterByRosterAndAttendanceSchool(Roster roster, Long attendanceSchoolId);

	void addRmStuFromRostEventToDomainAduidtHistory(EnrollmentsRosters er, Roster upRoster);

	void addStudentToRosterEventToDomainAuditHistory(EnrollmentsRosters er, Roster roster);

	List<Roster> getRostersBySubject(Long subjectId, Long schoolId, Long rosterCurrentSchoolYear, boolean isTeacher,
			Long educatorId);

	Roster getRostersByStudentEnrollInformation(Long valueOf, String attendanceSchoolIdentifier,
			String subjectAbbreviatedName, String courseAbbreviatedName, Long currentSchoolYear);

	void insertIntoDomainAuditHistory(Long objectId, Long createdUserId, String action, String source,
			String rosterBeforUpdate, String rosterAfterUpdate);

	boolean addToRosterAuditTrailHistory(DomainAuditHistory domainAuditHistory);

	List<Roster> selectRosterByRosterAndAttendanceSchool(Roster roster, Long attendanceSchoolId);

	List<Roster> getRostersByOrgId(Long sourceSchoolId, Long currentSchoolYear);

	List<Roster> getRostersByEnrollmentId(Long enrollmentId);

	void transferRoster(Roster roster, Long destinationSchool);

	void disableRoster(Roster roster);
//	boolean enableRoster(Roster roster);

	Long getCountByOrganizationId(Long organizationId, Long schoolYear);

	int checkIfRosterHasEnrollments(Long rosterId);

	List<StudentRoster> checkForTestSessionsOnExitedRostersAlso(Long enrollmentId, Long subjectId, Long courseId,
			Long schoolYear, String assessmentProgramCode);

	List<StudentRoster> checkIfRosterExistsForEnrollmentSubjectCourse(Long enrollmentId, Long subjectId, Long courseId,
			Long schoolYear, String assessmentProgramCode);

	HashMap<String, Boolean> automatedRosterTransferUpdatesToCreateRoster(Roster newRoster, Long[] addStudentIds,
			List<StudentRoster> studentsAlreadyOnExistingRosters, Long modifiedUserId, String assessmentProgramCode);

	HashMap<String, Boolean> automatedRosterTransferUpdatesToEditRoster(Roster newRoster, Long[] addStudentIds,
			Long[] delStudentIds, List<StudentRoster> studentsAlreadyOnExistingRosters, Long modifiedUserId,
			String assessmentProgramCode);

	Long getDeactivateCountByOrganizationId(Long organizationId, Long schoolYear);

	void removeRoster(long rosterId, Long userId, String sourceType);

	List<Roster> getRostersForTeacherReports(Long userId, Long currentSchoolYear, Long apId, Long groupId,
			Long currentOrganizationId);

	List<Long> removeRostersByEnrollmentId(Long id, Long modifiedUserId);

	Long removeEnrollmentsRostersByRosterId(Long id, Long modifiedUserId);

	int removeStudentsTestSectionsByRosterId(Long id, Long modifiedUserId);

	void deleteIfNoStudentPresent(Long roster, Long modifiedUserId);

	public List<Long> getRosterIdByTeacherIdTestSessionId(Long teacherId, Long testSessionId);

	public List<Roster> getRosterByClassroomId(Long classroomId);

	boolean checkActiveStudentsCountOnRoster(Long classroomId, int schoolYear);

	Roster getJsonRosterData(Long rosterId);

	List<Long> getClassroomIds(Long userId, Long schoolYear);

	List<RosterDTO> findRostersforTeacherIdInCurrentYear(long teacherId, long organizationId, long currentSchoolYear);

	List<RosterDTO> findRostersforTeacherIdInCurrentYearForMergeUser(Map<String, Object> criteria, String orderByClolumn,
			String order, Integer offset, Integer limitCount);
	

}