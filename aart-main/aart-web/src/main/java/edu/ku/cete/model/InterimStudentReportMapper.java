package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.PredictiveReportOrganization;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 5, 2017 12:40:13 PM
 */
public interface InterimStudentReportMapper {
    
    int insert(InterimStudentReport record);
    
    int insertSelective(InterimStudentReport record);
    
    InterimStudentReport selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(InterimStudentReport record);
    
    int updateByPrimaryKey(InterimStudentReport record);
    
    int deleteInterimStudentReports(@Param("assessmentProgramId")Long assessmentProgramId,
    		@Param("reportCycle")String reportCycle,
    		@Param("contentAreaId")Long contentAreaId, 
			@Param("gradeId")Long gradeId, 
			@Param("schoolYear") Long schoolYear, 
			@Param("studentId") Long studentId);
    
    List<InterimStudentReport> selectAllInterimStudentReports(@Param("assessmentProgramId") Long assessmentProgramId, 
    		@Param("gradeId") Long gradeId,
			@Param("contentAreaId") Long contentAreaId,
			@Param("schoolYear") Long schoolYear,
			@Param("reportCycle")String reportCycle,			 
			@Param("studentId") Long studentId);

	List<Long> getContentAreaIdsFromInterimStudentReport(
			@Param("assessmentProgramId")  Long assessmentProgramId,
			@Param("schoolYear")  Long schoolYear,
			@Param("reportCycle") String reportCycle, 
			@Param("testingProgramId") Long testingProgramId);

	List<Long> getGradeIdsFromInterimStudentReport(
			@Param("assessmentProgramId")  Long assessmentProgramId,
			@Param("schoolYear")  Long schoolYear, 
			@Param("contentAreaId") Long contentAreaId,
			@Param("reportCycle") String reportCycle,
			@Param("testingProgramId") Long testingProgramId);

	List<Long> getInterimStudentIdsForReportGeneration(@Param("gradeId") Long gradeId,
			@Param("contentAreaId") Long contentAreaId, 
			@Param("assessmentProgramId") Long assessmentProgramId, 
			@Param("schoolYear") Long schoolYear,
			@Param("reportCycle") String reportCycle,
			@Param("testingProgramId") Long testingProgramId,
			@Param("processByStudentId") String processByStudentId,
			@Param("reprocessEntireDistrict") String reprocessEntireDistrict,
			@Param("offset") int offset, 
			@Param("pageSize") int pageSize);

	List<InterimStudentReport> getInterimStudentsForReportGeneration(
			@Param("assessmentProgramId") Long assessmentProgramId, 
			@Param("gradeId") Long gradeId,
			@Param("contentAreaId") Long contentAreaId,
			@Param("studentId") Long studentId, 
			@Param("schoolYear") Long schoolYear,
			@Param("testingProgramId") Long testingProgramId, 
			@Param("processByStudentId") String processByStudentId);

	void updateInterimStudentReportFilePath(InterimStudentReport interimStudentReport);  
	
	List<String> getInterimStudentReportPathByTestsession(
			@Param("testSessionId") Long testSessionId, 
			@Param("userId") Long userId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("organizationId") Long organizationId, 
			@Param("isTeacher") Boolean isTeacher);

	Integer reportCountByTestsessionAndUser(@Param("testSessionId") Long testSessionId, 
			@Param("userId") Long userId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("organizationId") Long organizationId, 
			@Param("isTeacher") Boolean isTeacher);
	
	List<PredictiveReportOrganization> selectAllOrgsFromInterimStudentReport(@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle,
			@Param("schoolYear") Long schoolYear,
			@Param("contentAreaId")Long contentAreaId, 
			@Param("gradeId")Long gradeId, 
			@Param("offset") Integer offset, 
			@Param("pageSize") Integer pageSize);
	

	Integer getTestAttemptedStudentCount(@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle,
			@Param("schoolYear") Long schoolYear,
			@Param("contentAreaId")Long contentAreaId, 
			@Param("gradeId")Long gradeId,
			@Param("organizationId") Long organizationId,
			@Param("orgTypeCode") String orgTypeCode,
			@Param("externalTestId")Long externalTestId,
			@Param("testsStatusIds") List<Long> testsStatusIds);
	
	Integer getUnAnsweredStudentCount(@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle,
			@Param("schoolYear") Long schoolYear,
			@Param("contentAreaId")Long contentAreaId, 
			@Param("gradeId")Long gradeId,
			@Param("organizationId") Long organizationId,
			@Param("orgTypeCode") String orgTypeCode,
			@Param("externalTestId")Long externalTestId);
	
	List<InterimStudentReport> getPredictiveReportsByReActivatedTestSessionId(
			@Param("testSessionId") Long testSessionId, 
			@Param("studentIds") List<Long> studentIds);
	
	int deleteInterimStudentReportById(@Param("Id")Long Id);
}