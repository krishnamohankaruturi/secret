package edu.ku.cete.warehouse.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.report.DashboardReactivations;
import edu.ku.cete.domain.report.DashboardScoringSummary;
import edu.ku.cete.domain.report.DashboardShortDurationTesting;
import edu.ku.cete.domain.report.DashboardTestingOutsideHours;
import edu.ku.cete.domain.report.DashboardTestingSummary;

public interface DashboardMapper {
	List<DashboardTestingSummary> getTestingSummary(
			@Param("stateOrgId") Long stateOrgId,
			@Param("districtOrgId") Long districtOrgId, 
			@Param("schoolOrgId") Long schoolOrgId,
			@Param("assessmentProgramIds") List<Long> assessmentProgramIds,
			@Param("showClassroomId") Boolean showClassroomId,
			@Param("classroomIds") List<Long> classroomIds);
	
	List<DashboardScoringSummary> getScoringSummary(
			@Param("orgId") Long orgId,
			@Param("assessmentProgramIds") List<Long> assessmentProgramIds);
	
	List<DashboardReactivations> getReactivationsSummary(
			@Param("orgId") Long stateOrgId,
			@Param("apIds") List<Long> apIds,
			@Param("timeframe") String timeframe, 
			@Param("orgTimeZone") String orgTimeZone,
			@Param("schoolStartDate") Date schoolStartDate);
	
	List<DashboardReactivations> getReactivationsDetail(
			@Param("orgId") Long stateOrgId,
			@Param("apIds") List<Long> apIds,
			@Param("timeframe") String timeframe, 
			@Param("criteria") Map<String, Object> criteria, 
			@Param("sortByColumn") String sortByColumn, 
			@Param("sortType") String sortType, 
			@Param("skipCount") Integer skipCount, 
			@Param("limitCount") Integer limitCount, 
			@Param("orgTimeZone") String orgTimeZone,
			@Param("schoolStartDate") Date schoolStartDate);
	
	Long getReactivationsDetailCount(
			@Param("orgId") Long stateOrgId,
			@Param("apIds") List<Long> apIds,
			@Param("timeframe") String timeframe, 
			@Param("criteria") Map<String, Object> criteria, 
			@Param("sortByColumn") String sortByColumn, 
			@Param("sortType") String sortType, 
			@Param("orgTimeZone") String orgTimeZone,
			@Param("schoolStartDate") Date schoolStartDate);
	
	List<DashboardTestingOutsideHours> getTestingOutsideHours(
			@Param("orgId") Long stateOrgId,
			@Param("apIds") List<Long> apIds,
			@Param("timeframe") String timeframe,
			@Param("orgTimeZone") String orgTimeZone,
			@Param("schoolStartDate") Date schoolStartDate);
	
	List<DashboardShortDurationTesting> getShortDurationTesting(
			@Param("orgId") Long stateOrgId,
			@Param("apIds") List<Long> apIds,
			@Param("timeframe") String timeframe,
			@Param("orgTimeZone") String orgTimeZone,
			@Param("schoolStartDate") Date schoolStartDate);
	
	Boolean hasMiddayRunCompleted();

	Date getMostRecentRunTime();
	
	List<DashboardTestingSummary> getTestingSummaryAllStates(
			@Param("stateOrgId") Long stateOrgId,
			@Param("assessmentProgramIds") List<Long> assessmentProgramIds,
			@Param("showClassroomId") Boolean showClassroomId,
			@Param("classroomIds") List<Long> classroomIds,
			@Param("districtIds") List<Long> districtIds);
}