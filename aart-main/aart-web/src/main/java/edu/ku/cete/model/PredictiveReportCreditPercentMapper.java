package edu.ku.cete.model;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.PredictiveReportCreditPercent;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 25, 2017 4:06:01 PM
 */
public interface PredictiveReportCreditPercentMapper {
    
    int insert(PredictiveReportCreditPercent record);

    
    int insertSelective(PredictiveReportCreditPercent record);

    
    PredictiveReportCreditPercent selectByPrimaryKey(Long id);

    
    PredictiveReportCreditPercent selectByTestIdandOrganizationId(
    		@Param("orgTypeCode") String orgTypeCode,
    		@Param("currentSchoolYear") Long currentSchoolYear,
    		@Param("assessmentProgramId")Long assessmentProgramId,
    		@Param("testSessionId")Long testSessionId
    		);
    
    int updateByPrimaryKeySelective(PredictiveReportCreditPercent record);

    
    int updateByPrimaryKey(PredictiveReportCreditPercent record);
    
    List<PredictiveReportCreditPercent> getQuestionCreditPercentCountByOrganizatonId(@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle,
			@Param("schoolYear") Long schoolYear,
			@Param("contentAreaId")Long contentAreaId, 
			@Param("gradeId")Long gradeId, 
			@Param("testId")Long testId,
			@Param("organizationId") Long organizationId,
			@Param("orgTypeCode") String orgTypeCode,
			@Param("creditTypeId")Long creditTypeId,
			@Param("testsStatusIds") List<Long> testsStatusIds);
    
    int deleteAllSchoolDistrictSummaryCalculations(@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("testingProgramId") Long testingProgramId,
			@Param("reportCycle") String reportCycle,
			@Param("schoolYear") Long schoolYear,
			@Param("contentAreaId")Long contentAreaId, 
			@Param("gradeId")Long gradeId);
    
}