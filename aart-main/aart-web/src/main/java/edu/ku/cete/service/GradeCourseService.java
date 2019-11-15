/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.GradeCourseExample;

/**
 * @author neil.howerton
 * 
 */
public interface GradeCourseService {

	/**
	 * 
	 * @param example
	 *            {@link GradeCourseExample}
	 * @return int - the number of records referenced by the example.
	 */
	int countByExample(GradeCourseExample example);

	/**
	 * 
	 * @param example
	 *            {@link GradeCourseExample}
	 * @return int - the number of records deleted.
	 */
	int deleteByExample(GradeCourseExample example);

	/**
	 * 
	 * @param id
	 *            {@link Long}
	 * @return int - the number of reocrds deleted.
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * 
	 * @param example
	 *            {@link GradeCourseExample}
	 * @return List<GradeCourse> the GradeCourse records that match the
	 *         corresponding example.
	 */
	List<GradeCourse> selectByExample(GradeCourseExample example);

	/**
	 * 
	 * @param id
	 *            {@link Long}
	 * @return {@link GradeCourse} - the GradeCourse referenced by the primary
	 *         key parameter.
	 */
	GradeCourse selectByPrimaryKey(Long id);

	/**
	 * 
	 * @param record
	 *            {@link GradeCourse}
	 * @param example
	 *            {@link GradeCourseExample}
	 * @return int - the number of records updated.
	 */
	int updateByExampleSelective(GradeCourse record, GradeCourseExample example);

	/**
	 * 
	 * @param record
	 *            {@link GradeCourse}
	 * @param example
	 *            {@link GradeCourseExample}
	 * @return int - the number of records updated.
	 */
	int updateByExample(GradeCourse record, GradeCourseExample example);

	/**
	 * 
	 * @param record
	 *            {@link GradeCourse}
	 * @return int - the number of records updated.
	 */
	int updateByPrimaryKeySelective(GradeCourse record);

	/**
	 * 
	 * @param record
	 *            {@link GradeCourse}
	 * @return int - the number of records updated.
	 */
	int updateByPrimaryKey(GradeCourse record);

	/**
	 * 
	 * @param assessmentProgramId
	 *            long
	 * @return List<GradeCourse>
	 */
	List<GradeCourse> findByAssessmentProgram(long assessmentProgramId);

	/**
	 * 
	 * @param assessmentId
	 *            long
	 * @return List<GradeCourse>
	 */
	List<GradeCourse> findByAssessmentId(Long assessmentId);

	/**
	 * 
	 * @param gradeCourse
	 *            {@link GradeCourse}
	 * @return {@link GradeCourse}
	 */
	GradeCourse getWebServiceGradeCourseByCode(GradeCourse gradeCourse);

	/**
	 * @return
	 */
	List<GradeCourse> selectAllGradeCourses();

	/**
	 * @return
	 */
	List<GradeCourse> selectGradeCourseByContentAreaId(Long contentAreaId);

	/**
	 * 
	 * @param subjectAreaId
	 * @return
	 */
	List<GradeCourse> selectGradeCourseByTestTypeIdSubjectAreaId(Long subjectAreaId, Long testTypeId);

	List<GradeCourse> selectGradeCourseOfKansasBreakDay();

	List<GradeCourse> getAllIndependentGrades();

	GradeCourse getIndependentGradeByAbbreviatedName(String abbreviatedName);

	GradeCourse getByContentAreaAndAbbreviatedName(Long contentAreaId, String abbreviatedName);

	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15421 : Roster - refactor
	 * Add Roster manually page Get all courses that are related a content area,
	 * This is achieved by getting the gradecourse data that has course column
	 * as true i.e. they are only courses.
	 * 
	 * @param contentAreaId
	 * @return
	 */
	List<GradeCourse> getCoursesByContentArea(Long contentAreaId);

	List<GradeCourse> getGradeCourseForAuto(Long contentAreaId, Long testTypeId, Long assessmentId);

	List<GradeCourse> getGradeCourseByContentAreaIdForTestCoordination(Long assessmentProgramId, Long testingProgramId,
			Long contentAreaId);

	List<GradeCourse> getGradeCourseByContentAreaIdForTestManagement(Long assessmentProgramId, Long testingProgramId,
			Long contentAreaId);

	List<GradeCourse> getGradesUsingAssessmentProgramAndCourse(Long assessmentProgramId, Long contentAreaId);

	List<GradeCourse> getGradesWhereReportsHaveProcessed(Map<String, Object> parameters);

	List<GradeCourse> selectOrgGradeCourses(Long schoolId);

	List<GradeCourse> getCoursesListByOtwId(Long operationalTestWindowId);

	List<GradeCourse> getDistinctGradesByAssessmentPrgmId(Long assessmentProgramId);
	
	List<GradeCourse> getDistinctGradesByAssessmentPrgmIdBanded(Long assessmentProgramId);

	List<GradeCourse> getGradesUsingAssessmentProgramAndCourseForExternalReport(Long assessmentProgramId,
			Long contentAreaId, String assessmentCode, String reportType, Long reportYear);

	public List<GradeCourse> getGradeCourse();
	
	List<GradeCourse> getGradeCourseInterim(Long contentAreaId, Long purpose, Boolean isInterim, Long organizationId,Long assessmentProgramId);

	List<GradeCourse> getGradesForStudentScoreExtract(List<Long> contentAreaId);

	
	List<GradeCourse> getGradesForItiBPCoverageExtract(Long contentAreaId, Long narrowOrgId, Long schoolYear,
			boolean isTeacher, Long educatorId);

	List<GradeCourse> getGradesForBundledReporting(Long districtId, Long[] schoolIds, Long[] subjectIds,
			Long assessmentProgId, String assessmentProgCode, int schoolYear, String reportTypeCode);

	GradeCourse findByContentAreaAbbreviatedNameAndGradeCourseAbbreviatedName(String contentAreaAbbreviatedName,
			String gradeCourseAbbreviatedName);

	List<GradeCourse> getGradesForDynamicStudentSummaryBundledReport(Long districtId, Long[] schoolIds,
			Long assessmentProgId, String assessmentProgCode, int schoolYear);

	GradeCourse selectGradeByAbbreviatedNameAndContentAreaId(String abbreviatedName, Long contentAreaId);

	List<GradeCourse> findGradeByAbbreviatedName(String currentGradelevel);

	List<GradeCourse> selectAllGradeCoursesDropdown(Long currentAssessmentProgramId);

	GradeCourse getGradesForScoring(String grade);

	List<GradeCourse> getGradesForReportGeneration(Long subjectId,
			Long assessmentProgramId, Long schoolyear, Long stateId);

	List<GradeCourse> selectGradeByContentAreaId(Long subjectId);
	
	List<GradeCourse> getGradeBandsByContentAreaIdAndOTWId(Long operationalTestWindowId, Long contentAreaId);
	
	Map<Long, Long> getGradeCourseToGradeBandMap(Long operationalTestWindowId, Long contentAreaId);
	
	GradeCourse getGradeCourseByEnrollmentsRostersId(Long enrollmentId);
	
	List<GradeCourse> getGradeBandsBySchoolIDAndAssesmentProgrammIDAndYear(Long schoolID, Long currentAssessmentProgramId, Long schoolYear , Long teacherID);
}