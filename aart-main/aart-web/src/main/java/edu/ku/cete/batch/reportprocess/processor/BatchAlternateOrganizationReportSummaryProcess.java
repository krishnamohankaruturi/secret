package edu.ku.cete.batch.reportprocess.processor;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.report.AlternateAggregateReportGenerator;

public class BatchAlternateOrganizationReportSummaryProcess implements ItemProcessor<AlternateAggregateReport,Object>
{
 
	@Autowired
	private AlternateAggregateReportGenerator alternateAggregateReportGenerator;
	
	final static Log logger = LogFactory.getLog(BatchAlternateOrganizationReportSummaryProcess.class);
	
	private Long stateId;
	private Long reportYear;
	private Long assessmentProgramId;
	private String reportDate;
	
	@Override
    public AlternateAggregateReport process(AlternateAggregateReport alternateAggregateReport) throws Exception {
		logger.debug("Inside BatchAlternateOrganizationReportSummaryProcess for School report Generation...TeacherId -" + alternateAggregateReport.getKiteEducatorIdentifier()+" - SchoolId -"+alternateAggregateReport.getSchoolId());
		alternateAggregateReport.setReportDate(reportDate);
		alternateAggregateReportGenerator.generateAlternateAggregateReportFile(alternateAggregateReport);		
		alternateAggregateReport.setCsvFilePath(alternateAggregateReportGenerator.generateAlternateAggregateExtract(alternateAggregateReport));
		return alternateAggregateReport;		
       }
		
	
	public Long getReportYear() {
		return reportYear;
	}
	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}	
}
