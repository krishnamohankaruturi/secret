package edu.ku.cete.service.organizationbundle;

import java.util.List;

import edu.ku.cete.domain.OrganizationBundleReport;

public interface OrganizationBundleReportService {

	OrganizationBundleReport getLatestPendingReqest();

	OrganizationBundleReport selectByPrimaryKey(Long id);

	void updateByPrimaryKeySelective(OrganizationBundleReport bundleReport);

	void insert(OrganizationBundleReport bundleReport);

	List<OrganizationBundleReport> selectByOrganizationAndAssessment(Long organizationId,
			Long assessmentProgramId, Long currentSchoolYear, String reportCode);

	int getRequestByStatus(Long organizationId, Long assessmentProgramId,
			Long currentSchoolYear, Long status, String reportCode);

	OrganizationBundleReport getLatestPendingReqestForStudentBundledReport(String reportType);
	
}
