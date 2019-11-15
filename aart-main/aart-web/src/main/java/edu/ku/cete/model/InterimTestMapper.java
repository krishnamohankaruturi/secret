/**
 * 
 */
package edu.ku.cete.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.controller.InterimTestDTO;
import edu.ku.cete.domain.InterimPredictiveQuestionExtractDTO;
import edu.ku.cete.domain.interim.AutoAssignInterim;
import edu.ku.cete.domain.interim.InterimPredictiveStudentScore;
import edu.ku.cete.domain.interim.InterimTest;
import edu.ku.cete.domain.interim.InterimTestTests;
import edu.ku.cete.domain.interim.StudentActivityReport;

/**
 * @author venkat
 *
 */
public interface InterimTestMapper {

	public List<InterimTest> getInterimTestsByUser(@Param("userId") Long userId);

	public int save(InterimTest interimTest);

	public int saveInterimTestMapping(InterimTestTests interimTestTests);

	public int updateById(InterimTest interimTest);

	public InterimTest getInterimTest(@Param("interimTestId") Long interimTestId);

	public void deleteInterimtTestTestByInterimTestId(Long interimTestId);

	public void softDeleteInterimTestByInterimTestID(Long interimTestId);

	public List<InterimTestDTO> getTotalTests(Long organizationId, @Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType, @Param("offset") int offset, @Param("limitCount") int limitCount,
			Map<String, Object> criteria,@Param("schoolYear") Long schoolYear);

	public int testCountDetails(Map<String, Object> criteria);

	public Long getInterimTestsCount(@Param("id") Long id);

	public void setTestAssigned(@Param("interimTestId") Long interimTestId);

	public InterimTest getInterimTestByTestTestId(@Param("testTestId") Long testId);

	public Long getAttemptedCount(@Param("testTestId") Long testTestId);

	public List<InterimTestDTO> getTotalTestsByUserId(@Param("userIds") List<Long> userIds,
			@Param("organizationId") Long orgId,
			@Param("schoolYear") Long schoolYear, 
			@Param("forReports") Boolean forReports,
			@Param("assessmentProgramId") Long assessmentProgramId);

	public List<String> getSchoolNames(@Param("organizationId") Long contractingOrgId, @Param("schoolYear")Long schoolYear);

	public List<InterimTest> getInterimTestsByNameAndUser(@Param("testName") String testName,
			@Param("userId") Long currentContextUserId ,@Param("schoolYear") Long schoolYear, @Param("orgId")Long orgId);

	public List<InterimTestDTO> getTotalTestsForTeacher(@Param("userId") Long userId, 
			                                            @Param("schoolYear") Long schoolYear, 
			                                            @Param("organizationId")Long organizationId,
			                                            @Param("forReports") Boolean forReports, 
			                                            @Param("assessmentProgramId") Long assessmentProgramId);

	public int updateTestSession(@Param("name") String name, @Param("interimTestId") Long interimTestId,
			@Param("testCollectionId") Long testCollectionId,@Param("testTestId") Long testTestId,@Param("testSessionId") Long testSessionId);

	public void cleanUpTest(@Param("id")Long id);

	public InterimTestDTO getTotalTestSessionDetails(@Param("testSessionId")Long testSessionId, @Param("assessmentProgramId") Long assessmentProgId);

	public Integer suspendTestWindow(@Param("suspend") Boolean suspend,@Param("testSessionId") Long testSessionId , @Param("modifiedUser") Long modifiedUser, @Param("modifiedDate") Date modifiedDate);

	public int insertAutoAssignInterim(AutoAssignInterim autoassign);

	public List<StudentActivityReport> getStudentReportActivityDetails(@Param("studentIds") List<Long> studentIds,
			@Param("orgIds") List<Long> orgIds, @Param("currentSchoolYear") Long schoolYear,
			@Param("isPLTWUser") Boolean isPLTWUser, @Param("isTeacher") Boolean isTeacher, @Param("userId") Long userId);

	public List<Long> getInterimOrgIds(@Param("orgIds")List<Long> orgIds,@Param("currentSchoolYear") Long schoolYear);
	public List<Long> getAutoAssignedTestSessions(@Param("rosterId") Long rosterId,
						@Param("gradeCourseId") Long gradeCourseId, @Param("contentAreaId")Long contentAreaId, @Param("orgIds")List<Long> orgIds);

	public Long getDistrictAndBuildingLevelUserCountForUserByOrg(@Param("userId") Long userId,@Param("orgId") Long orgId);

	public List<InterimPredictiveStudentScore> getInterimPredictiveStudentScores(@Param("studentIds")List<Long> studentIds,
			@Param("orgIds")List<Long> orgIds, @Param("schoolYear")Long schoolYear, @Param("contentAreaIds")List<Long> contentAreaIds,
			@Param("gradeCourseIds")List<Long> gradeCourseIds,@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("isTeacher")Boolean isTeacher, @Param("userId")Long userId);

	public List<InterimPredictiveQuestionExtractDTO> getInterimPredictiveQuestionCSVBytestsessionAndUser(
			@Param("testSessionId") Long testSessionId, 
			@Param("isTeacher") Boolean isTeacher, 
			@Param("districtUser") Boolean districtUser, 
			@Param("userId") Long userId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("organizationId") Long organizationId);

	public List<? extends InterimTestDTO> getTotalTestsForOrgs(@Param("userIds") List<Long> userIds,
			@Param("schoolYear") Long schoolYear, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("organizationIds") List<Long> orgIds);

	public void deleteById(Long id);
	
	public List<Long> unassignUnusedStudentsTestsByInterimTestId(@Param("interimTestId") Long interimTestId);

}
