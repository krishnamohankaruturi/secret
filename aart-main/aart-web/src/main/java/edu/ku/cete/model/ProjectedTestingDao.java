package edu.ku.cete.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.DistrictSummaryReport;

/**
 * Added By Sudhansu Feature: f183 Projected Testing
 */
public interface ProjectedTestingDao {

	void save(ProjectedTestingDTO projectedTestingDto);

	List<ProjectedTestingDTO> getProjectedTesting(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("schoolId") Long schoolId, @Param("month") String month,
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("gradeId") Long gradeId,
			@Param("projectionType") String projectionType);

	void updateByPrimaryKeySelective(ProjectedTestingDTO projectedTestingDto);

	List<ProjectedTestingDTO> getProjectedTestingForLoggedInUser(@Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType,
			@Param("viewMyCalendarCriteriaMap") Map<String, String> viewMyCalendarCriteriaMap);

	List<ProjectedTestingChartDTO> getProjectedTestingForChart(@Param("currentSchoolYear") Long currentSchoolYear);

	int countViewMyCalendarRecords(@Param("viewMyCalendarCriteriaMap") Map<String, String> viewMyCalendarCriteriaMap);

	List<DistrictSummaryReport> getDistrictSummaryReportDetails(@Param("currentSchoolYear") Long currentSchoolYear);

	List<ProjectedTestingDTO> getSchoolProjectionsBy(@Param("testDate") Date testDate,
			@Param("districtId") Long districtId, @Param("projectionType") String projectionType,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("currentSchoolYear") Long currentSchoolYear);

	Long getEnrollmentCountBy(@Param("schoolId") Long schoolId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("currentSchoolYear") Long currentSchoolYear, @Param("gradeId") Long gradeId);

	Long getNoOfDaysProjectedBy(@Param("schoolId") Long schoolId, @Param("projectionType") String projectionType,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("currentSchoolYear") Long currentSchoolYear,
			@Param("gradeId") Long gradeId);

	String getDtcEmailAddress(@Param("districtId") Long districtId,
			@Param("assessmentProgram") String assessmentProgram);
}
