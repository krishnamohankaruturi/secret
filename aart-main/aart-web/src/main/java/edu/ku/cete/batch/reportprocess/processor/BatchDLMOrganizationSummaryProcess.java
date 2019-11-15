package edu.ku.cete.batch.reportprocess.processor;
 
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.report.DLMOrganizationSummaryReport;
import edu.ku.cete.domain.report.OrganizationGrfCalculation;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.report.DLMOrganizationSummaryReportGenerator;
import edu.ku.cete.service.report.OrganizationGrfCalculationService;

public class BatchDLMOrganizationSummaryProcess implements ItemProcessor<Long,Object>
{
 
	@Autowired
	private DLMOrganizationSummaryReportGenerator dlmOrgSummaryGenerator;
	
	final static Log logger = LogFactory.getLog(BatchDLMOrganizationSummaryProcess.class);
	
	@Autowired 
	OrganizationGrfCalculationService orgGrfCalculationService;
    
	@Autowired
	private OrganizationDao organizationDao;
	
	private Long stateId;
	private Long reportYear;
	private Long assessmentProgramId;
	private String reportDate;
	
	@Override
    public DLMOrganizationSummaryReport process(Long organizationId) throws Exception {
		logger.debug("Inside BatchDLMOrganizationSummaryProcess for organization report Generation...Orgid -" + organizationId);
		DLMOrganizationSummaryReport data = null;		
		Organization orgDetails = organizationDao.get(organizationId);
		OrganizationGrfCalculation orgGrfCalculation = orgGrfCalculationService.getOrganizationGrfCalculationByOrgId(organizationId,stateId,orgDetails.getOrganizationType().getTypeCode(),reportYear,assessmentProgramId);
		if(orgGrfCalculation!=null){
			data= new DLMOrganizationSummaryReport();
			data.setOrgGrfCalculation(orgGrfCalculation);		
			data.setReportDate(reportDate);
			dlmOrgSummaryGenerator.generateDLMOrganizationSummaryReportFile(data);	
			if(data.getOrgGrfCalculation().getDistrictId() != null) {
				data.setCsvFilePath(dlmOrgSummaryGenerator.generateDLMOrganizationGrfExtract(data));
			}
			if(data.getOrgGrfCalculation().getDistrictId() == null) {
				data.setCsvFilePath(dlmOrgSummaryGenerator.generateDLMOrganizationGrfExtract(data));
			}
		 }	
		return data;		
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
