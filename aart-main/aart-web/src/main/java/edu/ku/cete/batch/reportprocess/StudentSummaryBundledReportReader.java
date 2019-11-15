/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.report.BatchReportProcessService;


/**
 * @author Kiran Reddy Taduru
 *
 * May 19, 2017 2:00:05 PM
 */
public class StudentSummaryBundledReportReader<T> extends AbstractPagingItemReader<T> {
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
    private String gradeCourseAbbrName;
    
    @Value("${external.import.reportType.studentSummary}")
	private String REPORT_TYPE_STUDENT_SUMMARY;
    
    @Value("${external.schoolLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL;
    
    @Value("${external.districtLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL;
    
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getOrganizationIdsForStudentSummaryBundledReports(stateId, assessmentProgramId, schoolYear, gradeCourseAbbrName, getPage() * getPageSize(), getPageSize()));
	}

	@SuppressWarnings("unchecked")
	private List<T> getOrganizationIdsForStudentSummaryBundledReports(Long stateId, Long assessmentProgramId, Long currentSchoolYear, String gradeCourseAbbrName, Integer offset, Integer pageSize) {
		logger.debug("Entering SchoolSummaryBundledReportReader for AssessmentProgram - " + assessmentProgramCode + " and OrganizationId - " + stateId + " and School Year - " + currentSchoolYear);		
		List<T> results = null;
		
		if("CPASS".equalsIgnoreCase(assessmentProgramCode) || "DLM".equalsIgnoreCase(assessmentProgramCode)){
			if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL.equalsIgnoreCase(bundledReportType)){				
				results = (List<T>) batchReportProcessService.getSchoolIdsForStudentSummaryBundledReports(stateId, assessmentProgramId, currentSchoolYear, gradeCourseAbbrName, REPORT_TYPE_STUDENT_SUMMARY, offset, pageSize);
			
			}else if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL.equalsIgnoreCase(bundledReportType)){
				results = (List<T>) batchReportProcessService.getDistrictIdsForStudentSummaryBundledReports(stateId, assessmentProgramId, currentSchoolYear, REPORT_TYPE_STUDENT_SUMMARY, offset, pageSize);
			}				
		}
		
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

	public String getGradeCourseAbbrName() {
		return gradeCourseAbbrName;
	}

	public void setGradeCourseAbbrName(String gradeCourseAbbrName) {
		this.gradeCourseAbbrName = gradeCourseAbbrName;
	}	

}
