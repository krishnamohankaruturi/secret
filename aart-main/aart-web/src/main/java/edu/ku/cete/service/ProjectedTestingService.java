package edu.ku.cete.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.ProjectedTesting;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.TestingProjectionDTO;

/**
 * Added By Sudhansu Feature: f183 Projected Testing
 */
public interface ProjectedTestingService {

	void addorUpdateProjectedTesting(ProjectedTesting projectedTesting);

	boolean isHavingWeekends(ProjectedTesting projectedTesting);

	ProjectedTesting convertColumnToDates(ProjectedTesting projectedTesting);

	List<ProjectedTesting> getViewMyCalendarRecords(String sortByColumn, String sortType,
			Map<String, String> viewMyCalendarCriteriaMap, int offset, int limitCount);

	Collection<TestingProjectionDTO> getProjectTestingChart(Long currentSchoolYear);

	int countViewMyCalendarRecords(Map<String, String> viewMyCalendarCriteriaMap);

	Map<String, String> getValidMonthsFortesting(UserDetailImpl userDetails);

	void generateDistrictSummaryReportCSV() throws IOException;

	Map<String, Object> getBarChartAssessmentPrograms();

}
