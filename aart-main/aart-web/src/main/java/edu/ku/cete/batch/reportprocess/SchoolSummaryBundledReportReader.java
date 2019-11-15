/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 *
 * May 19, 2017 2:00:30 PM
 */
public class SchoolSummaryBundledReportReader<T> extends AbstractPagingItemReader<T> {
	private final static Log logger = LogFactory .getLog(SchoolSummaryBundledReportReader.class);
	@Autowired
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;	
	
	private Long stateId;
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Long schoolYear;
    private StepExecution stepExecution;
    private Long batchReportProcessId;
    private String bundledReportType;
    
    @Value("${external.import.reportType.school}")
	private String REPORT_TYPE_SCHOOL; 
    
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getOrganizationIdsForStudentSummaryBundledReports(stateId, assessmentProgramId, schoolYear, getPage() * getPageSize(), getPageSize()));
	}

	@SuppressWarnings("unchecked")
	private List<T> getOrganizationIdsForStudentSummaryBundledReports(Long stateId, Long assessmentProgramId, Long currentSchoolYear, Integer offset, Integer pageSize) {
		logger.debug("Entering SchoolSummaryBundledReportReader for AssessmentProgram - " + assessmentProgramCode + " and OrganizationId - " + stateId + " and School Year - " + currentSchoolYear);		
		
		List<T> results = (List<T>) batchReportProcessService.getDistrictIdsForSchoolSummaryBundledReports(stateId, assessmentProgramId, currentSchoolYear, REPORT_TYPE_SCHOOL, offset, pageSize);
		
		logger.debug("Leaving SchoolSummaryBundledReportReader for AssessmentProgram - " + assessmentProgramCode + " and OrganizationId - " + stateId + " and School Year - " + currentSchoolYear);
		return results;
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		// Nothing		
	}

	public BatchReportProcessService getBatchReportProcessService() {
		return batchReportProcessService;
	}

	public void setBatchReportProcessService(
			BatchReportProcessService batchReportProcessService) {
		this.batchReportProcessService = batchReportProcessService;
	}	

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public String getBundledReportType() {
		return bundledReportType;
	}

	public void setBundledReportType(String bundledReportType) {
		this.bundledReportType = bundledReportType;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

}
