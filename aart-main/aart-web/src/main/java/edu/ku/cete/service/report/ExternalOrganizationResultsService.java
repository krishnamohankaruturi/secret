package edu.ku.cete.service.report;

import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.OrganizationReportResults;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
@Service
public interface ExternalOrganizationResultsService {

	public Integer insertOrUpdateOrganizationReportDetail(OrganizationReportResults organizationReportDetail);
	
	public Integer deleteOrganizationReportDetail(Long stateId,Long assessmentProgramId,
			Long subjectId, Long schoolYear, Long testingProgramId,
			String reportCycle, String reportType);

	public void clearOrganizationReportPDF(Long stateId,
			Long assessmentProgramId, Long contentAreaId, Long reportYear,
			Long testingProgramId, String reportCycle,
			String reportType, Long uploadedUser);

}
