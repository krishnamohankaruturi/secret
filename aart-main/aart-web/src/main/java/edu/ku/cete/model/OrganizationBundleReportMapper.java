package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.OrganizationBundleReport;

public interface OrganizationBundleReportMapper {

	OrganizationBundleReport getLatestPendingReqest();

	OrganizationBundleReport selectByPrimaryKey(@Param("id") Long id);

	void updateByPrimaryKeySelective(OrganizationBundleReport bundleReport);

	void deActivateForSameOrganizationAndStatus(
			OrganizationBundleReport bundleReport);

	void insert(OrganizationBundleReport bundleReport);

	List<OrganizationBundleReport> selectByOrganizationAndAssessment(@Param("organizationId") Long organizationId,
			                               @Param("assessmentProgramId") Long assessmentProgramId, 
			                               @Param("currentSchoolYear") Long currentSchoolYear,
			                               @Param("reportCode") String reportCode);

	Integer getInprogressRequest(@Param("organizationId") Long organizationId,
            @Param("assessmentProgramId") Long assessmentProgramId, 
            @Param("currentSchoolYear") Long currentSchoolYear, 
            @Param("status") Long status,
            @Param("reportCode") String reportCode);
	
	OrganizationBundleReport getLatestPendingReqestForStudentBundledReport(@Param("reportType") String reportType);
}
