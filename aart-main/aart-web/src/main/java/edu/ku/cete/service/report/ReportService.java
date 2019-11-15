package edu.ku.cete.service.report;

import java.util.List;
import java.util.Map;
import edu.ku.cete.domain.common.Category;

import edu.ku.cete.domain.professionaldevelopment.ModuleReport;

import edu.ku.cete.domain.report.ReportAssessmentProgram;
import edu.ku.cete.web.ReportAccessDTO;

public interface ReportService {

	public List<Category> getReports();
	
	public List<ReportAssessmentProgram> getReadyToViewFlagsForReports(Long userCurrentRoleId,Long orgId,String orgType);

	public boolean getEditReportAccessData(Long editReportAccessId, Long[] rolesId);

	public Map<Long, String> getAllGroupsSelectedPermission(Long groupAuthoirityId,
			Long assessmentProgId, Long stateId);

	public List<Long> getGroupNameSelectedPermission(
			Long reportassessmentgroupid);

	public List<ReportAccessDTO> getReportAccessData(Long assessmentPrgId, List<Long> stateId,
			Map<String, String> testSessionRecordCriteriaMap, String sortType, String sortByColumn,
			List<String> toRemoveStudentArchiveReport, List<String> reportCode);

	public boolean editReportAccessDataForMultipleState(String[] editReportAccessId, Long groupId, Boolean activeFlag);

	public List<ReportAccessDTO> getExtractAccessData(Long assessmentPrgId, List<Long> stateId,
			Map<String, String> testSessionRecordCriteriaMap, String sortType, String sortByColumn);

	public boolean editExtractAccessDataForMultipleState(String[] editReportAccessId, Long groupId, Boolean activeFlag);

	public List<Long> getDlmExtractAccessId(Long currentGroupId, Long currentStateId, Long currentAssessmentPgmId);

	public String getReportAccessPermissionForReports(Long currentGroupId, Long currentOrganizationId,
			Long currentAssessmentProgramId, String categoryCode);
	
}
