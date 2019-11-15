package edu.ku.cete.batch.reportprocess.writer;
 
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.service.report.BatchReportProcessService;
 
/**
 * Writes products to a database
 * @param <T>
 */
public class BatchAlternateOrganizationReportProcessWriter implements ItemWriter<AlternateAggregateReport>
{
	final static Log logger = LogFactory.getLog(BatchAlternateOrganizationReportProcessWriter.class);

	@Autowired 
	BatchReportProcessService batchReportProcessService;
	 
    private Long batchReportProcessId;
    private Long userId;
 	
	@Value("${schoolReportsImportReportType}")
	private String  schoolReportsImportReportType;
	
	@Override
	public void write(List<? extends AlternateAggregateReport> alternateAggregateReportList) throws Exception {
		
		logger.debug("Inside BatchAlternateOrganizationReportProcessWriter for School report Generation" );
		if(!alternateAggregateReportList.isEmpty()){
			for(AlternateAggregateReport alternateAggregateReport : alternateAggregateReportList){
				logger.debug("Inside BatchAlternateOrganizationReportProcessWriter ....for Organization Id - "+alternateAggregateReport.getSchoolId());
				OrganizationReportDetails orgRptDetails = new OrganizationReportDetails();
				orgRptDetails.setAssessmentProgramId(alternateAggregateReport.getAssessmentProgramId());				
				orgRptDetails.setSchoolYear(alternateAggregateReport.getReportYear());
				if(alternateAggregateReport.getKiteEducatorIdentifier()!=null && !alternateAggregateReport.getKiteEducatorIdentifier().equals("")) orgRptDetails.setTeacherId(new Long(alternateAggregateReport.getKiteEducatorIdentifier()));
				orgRptDetails.setOrganizationId(alternateAggregateReport.getSchoolId());				
				orgRptDetails.setReportType(schoolReportsImportReportType);			
				orgRptDetails.setDetailedReportPath(alternateAggregateReport.getFilePath());
				orgRptDetails.setCreatedDate(new Date());	
				orgRptDetails.setBatchReportProcessId(batchReportProcessId);
				orgRptDetails.setSchoolReportPdfSize(alternateAggregateReport.getPdfFileSize());
				orgRptDetails.setCsvDetailedReportPath(alternateAggregateReport.getCsvFilePath());
				orgRptDetails.setCreatedUser(userId);
				orgRptDetails.setModifiedUser(userId);
				batchReportProcessService.insertOrganizationSummaryReportFiles(orgRptDetails);
				logger.debug("Completed BatchAlternateClassRoomReportProcessWriter ....for Organization Id - "+alternateAggregateReport.getSchoolId());
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
