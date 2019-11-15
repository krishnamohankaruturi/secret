package edu.ku.cete.batch.reportprocess.writer;
 
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.domain.report.DLMOrganizationSummaryReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.service.report.BatchReportProcessService;
 
/**
 * Writes products to a database
 * @param <T>
 */
public class BatchDLMOrganizationReportProcessWriter implements ItemWriter<DLMOrganizationSummaryReport>
{
	final static Log logger = LogFactory.getLog(BatchDLMOrganizationReportProcessWriter.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
     private Long batchReportProcessId;
     private Long userId;
 
	
	@Override
	public void write(List<? extends DLMOrganizationSummaryReport> orgSummaryReportList) throws Exception {
		
		logger.debug("Inside BatchDLMOrganizationReportProcessWriter for organization report Generation" );
		if(!orgSummaryReportList.isEmpty()){
			for(DLMOrganizationSummaryReport orgSummaryReport : orgSummaryReportList){
				logger.debug("Inside BatchOrganizationReportProcessWriter ....writing for Org Id - "+orgSummaryReport.getOrgGrfCalculation().getAssessmentProgramId());
				OrganizationReportDetails orgRptDetails = new OrganizationReportDetails();
				orgRptDetails.setAssessmentProgramId(orgSummaryReport.getOrgGrfCalculation().getAssessmentProgramId());				
				orgRptDetails.setSchoolYear(orgSummaryReport.getOrgGrfCalculation().getReportYear());
				orgRptDetails.setBatchReportProcessId(batchReportProcessId);
				if(orgSummaryReport.getOrgGrfCalculation().getDistrictId()!= null){					
					orgRptDetails.setOrganizationId(orgSummaryReport.getOrgGrfCalculation().getDistrictId());
					orgRptDetails.setReportType("ALT_DS");
				}else {
					orgRptDetails.setOrganizationId(orgSummaryReport.getOrgGrfCalculation().getStateId());
					orgRptDetails.setReportType("ALT_SS");
				}
				orgRptDetails.setDetailedReportPath(orgSummaryReport.getFilePath());
				orgRptDetails.setCreatedDate(new Date());	
				orgRptDetails.setBatchReportProcessId(batchReportProcessId);
				orgRptDetails.setSchoolReportPdfSize(orgSummaryReport.getPdfFileSize());
				orgRptDetails.setSummaryReportCsvPath(orgSummaryReport.getCsvFilePath());
				orgRptDetails.setCreatedUser(userId);
				orgRptDetails.setModifiedUser(userId);
				batchReportProcessService.insertOrganizationSummaryReportFiles(orgRptDetails);
				logger.debug("Completed BatchOrganizationReportProcessWriter Org Id ");
			}
		}
		
	}


	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	 
	

}
