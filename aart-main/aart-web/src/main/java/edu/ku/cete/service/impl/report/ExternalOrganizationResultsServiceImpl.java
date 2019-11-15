package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.model.OrganizationReportDetailsMapper;
import edu.ku.cete.report.domain.OrganizationReportResults;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.report.ExternalOrganizationResultsService;

@Service
public class ExternalOrganizationResultsServiceImpl implements
		ExternalOrganizationResultsService {

	@Autowired
	OrganizationReportDetailsMapper organizationReportDetailsMapper;
	
	@Autowired
	private AwsS3Service s3;
	
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertOrUpdateOrganizationReportDetail(
			OrganizationReportResults organizationReportDetail) {
		Integer results = null;
		results = organizationReportDetailsMapper.updateResults(organizationReportDetail);
		if(results == 0)		
			results = organizationReportDetailsMapper.inserResults(organizationReportDetail);
		return results;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteOrganizationReportDetail(Long stateId, Long assessmentProgramId,
			Long subjectId, Long schoolYear, Long testingProgramId,
			String reportCycle, String reportType) {
		// TODO Auto-generated method stub
		
		return organizationReportDetailsMapper.deleteOrganizationReportDetailForCpassReport(stateId, assessmentProgramId,
				 subjectId,  null, schoolYear,  testingProgramId,
				 reportCycle, reportType);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void clearOrganizationReportPDF(Long stateId,
			Long assessmentProgramId, Long contentAreaId, Long reportYear,
			Long testingProgramId, String reportCycle,
			String reportType, Long uploadedUser) {
		
	   List<OrganizationReportDetails> reports = organizationReportDetailsMapper.getOrganizationReportDetailByReportCycle(stateId,
			   assessmentProgramId, contentAreaId, null, reportYear, testingProgramId, reportCycle, reportType);
	   
		if(CollectionUtils.isNotEmpty(reports)){
			for(OrganizationReportDetails organizationReportDetail : reports){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}			
			 organizationReportDetailsMapper.clearOrganizationReportDetail(stateId,
					   assessmentProgramId, contentAreaId, reportYear, testingProgramId, reportCycle, reportType, uploadedUser);
		}			   
		
	}

}
