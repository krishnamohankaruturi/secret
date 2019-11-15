package edu.ku.cete.service.impl.organizationbundle;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.model.OrganizationBundleReportMapper;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
/* sudhansu.b
 * Added for F460 - Dynamic bundle report
 */	
@Service
public class OrganizationBundleReportServiceImpl implements OrganizationBundleReportService{
	
	@Autowired
	OrganizationBundleReportMapper organizationBundleReportMapper; 
	
	@Autowired
	CategoryDao categoryDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OrganizationBundleReport getLatestPendingReqest() {
		return organizationBundleReportMapper.getLatestPendingReqest();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OrganizationBundleReport selectByPrimaryKey(Long id) {
		return organizationBundleReportMapper.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateByPrimaryKeySelective(
			OrganizationBundleReport bundleReport) {
		//Deactivate old entries for same organization and status
		Long inqueueId = categoryDao.getCategoryId("IN_QUEUE", "PD_REPORT_STATUS");
		if(inqueueId.longValue() != bundleReport.getStatus())
		  organizationBundleReportMapper.deActivateForSameOrganizationAndStatus(bundleReport);
		
		organizationBundleReportMapper.updateByPrimaryKeySelective(bundleReport);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insert(OrganizationBundleReport bundleReport) {		
		organizationBundleReportMapper.insert(bundleReport);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationBundleReport> selectByOrganizationAndAssessment(
			Long organizationId, Long assessmentProgramId,
			Long currentSchoolYear,String reportCode) {
		
		return organizationBundleReportMapper.selectByOrganizationAndAssessment(organizationId, assessmentProgramId,
				 currentSchoolYear, reportCode);
	}

	@Override
	public int getRequestByStatus(Long organizationId,
			Long assessmentProgramId, Long currentSchoolYear, Long status, String reportCode) {
		return organizationBundleReportMapper.getInprogressRequest(organizationId, assessmentProgramId,
				 currentSchoolYear, status, reportCode);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OrganizationBundleReport getLatestPendingReqestForStudentBundledReport(String reportType) {
		return organizationBundleReportMapper.getLatestPendingReqestForStudentBundledReport(reportType);
	}
}
