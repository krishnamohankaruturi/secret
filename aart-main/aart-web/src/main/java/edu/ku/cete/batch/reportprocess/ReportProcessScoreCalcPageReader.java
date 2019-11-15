package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;

public class ReportProcessScoreCalcPageReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(ReportProcessScoreCalcPageReader.class);
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Organization contractingOrganization;
	private StepExecution stepExecution;
    private Long batchReportProcessId;
    private List<Long> rawScaleExternalTestIds;
    private Long testsCompletedStatusId;
    private Long testsInprogressStatusId;
    private Long testStatusInprogressTimedoutId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long studentId;
    private Long assessmentId;
    private String processByStudentId;
    private List<Long> studentIdListForReprocess;
    private Long createdUserId;
    private String gradeCourseAbbreviatedName;
    
	@Autowired
	private BatchReportProcessService batchReportProcessService;	
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		logger.debug("Report Process -->> Student Reader: assessmentProgramId:-" + assessmentProgramId
				+ ", assessmentId:-" + assessmentId
				+ ", assessmentProgramCode:-" + assessmentProgramCode
				+ ", contentAreaId:-" + contentAreaId
				+ ", gradeCourseId:-" + gradeCourseId
				+ ", contractingOrganizationId:-" + contractingOrganization.getDisplayIdentifier()
				+ ", pageNumber:-" + getPage()
				+ ", getPageSize:-" + getPageSize());
		results.addAll(getStudents(assessmentProgramId, assessmentId, assessmentProgramCode, contentAreaId, gradeCourseId, contractingOrganization, getPage() * getPageSize(), getPageSize()));
		logger.debug("Report Process <<-- Student Reader: contentAreaId:-"+ contentAreaId + ",gradeCourseId:-" + gradeCourseId 
				+ ",contractingOrganizationId:-" + contractingOrganization.getDisplayIdentifier() + ", results Size :-" + results.size());
	}

	@SuppressWarnings("unchecked")
	private List<T> getStudents(Long assessmentProgramId, Long assessmentId, String assessmentProgramCode, Long contentAreaId, Long gradeCourseId, Organization contractingOrganization, Integer offset, Integer pageSize) {
		List<Long> testsStatusIds = new ArrayList<Long>();
		testsStatusIds.add(testsCompletedStatusId);		
		if(assessmentProgramCode.equalsIgnoreCase("KAP") || assessmentProgramCode.equalsIgnoreCase("KELPA2")){
			testsStatusIds.add(testsInprogressStatusId);
			testsStatusIds.add(testStatusInprogressTimedoutId);
			testsStatusIds.add(testStatusUnusedId);
			testsStatusIds.add(testStatusPendingId);
		}
		
		List<StudentReport> studentReports = new ArrayList<StudentReport>();
		if(CollectionUtils.isNotEmpty(rawScaleExternalTestIds)){
			//studentId = 520081l;//1177714l;
			if("TRUE".equals(processByStudentId)){
				if(CollectionUtils.isNotEmpty(studentIdListForReprocess)){
					//List<Long> studentIdListForReprocess = batchReportProcessService.getStudentsForReportProcessByStudentId(assessmentProgramId, contentAreaId, gradeCourseId, Long.valueOf(contractingOrganization.getReportYear()));
					studentReports = batchReportProcessService.getStudentsForReportProcess(studentId, contractingOrganization.getId(), assessmentId, assessmentProgramId, assessmentProgramCode, gradeCourseId, contentAreaId, Long.valueOf(contractingOrganization.getReportYear()), rawScaleExternalTestIds, testsStatusIds, studentIdListForReprocess, offset, pageSize);				
				}
			}else{
				studentReports = batchReportProcessService.getStudentsForReportProcess(studentId, contractingOrganization.getId(), assessmentId, assessmentProgramId, assessmentProgramCode, gradeCourseId, contentAreaId, Long.valueOf(contractingOrganization.getReportYear()), rawScaleExternalTestIds, testsStatusIds, null, offset, pageSize);
			}
			
			if(CollectionUtils.isNotEmpty(studentReports)){
				for(StudentReport studentReport : studentReports){
					studentReport.setAssessmentProgramCode(assessmentProgramCode);
					studentReport.setSchoolYear(Long.valueOf(contractingOrganization.getReportYear()));
					studentReport.setCreatedUserId(createdUserId);
					studentReport.setModifiedUserId(createdUserId);
					
					if("TRUE".equals(processByStudentId)){
						studentReport.setIsProcessBySpecificStudentId(Boolean.TRUE);
					}
					
					StudentReport previousYearReport = batchReportProcessService.getPreviousYearReport(studentReport);
					if (previousYearReport!= null && previousYearReport.getLevelId()!=null) {
						studentReport.setPreviousYearLevelId(previousYearReport.getLevelId());
					}
				}
			}
			
		}else{
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "No data found in raw to scale scores. Assessment Program - " + assessmentProgramCode 
					+ " Grade Id - " + gradeCourseId + " (Grade "+gradeCourseAbbreviatedName+")"
					+ " Subject Id - " + contentAreaId
					+ " OrganizationId - " + contractingOrganization.getId()
					+ " OrganizationName - " + contractingOrganization.getOrganizationName();
			
			reportProcessReason.setReason(msg);
			reportProcessReason.setReportProcessId(batchReportProcessId);
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
		}
		return (List<T>) studentReports;
	} 
	@Override
	protected void doJumpToPage(int arg0) {
		//no-op
		logger.debug("NO-OP");
	};

	 

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
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

	public List<Long> getRawScaleExternalTestIds() {
		return rawScaleExternalTestIds;
	}

	public void setRawScaleExternalTestIds(List<Long> rawScaleExternalTestIds) {
		this.rawScaleExternalTestIds = rawScaleExternalTestIds;
	}

	public Long getTestsCompletedStatusId() {
		return testsCompletedStatusId;
	}

	public void setTestsCompletedStatusId(Long testsCompletedStatusId) {
		this.testsCompletedStatusId = testsCompletedStatusId;
	}

	public Long getTestsInprogressStatusId() {
		return testsInprogressStatusId;
	}

	public void setTestsInprogressStatusId(Long testsInprogressStatusId) {
		this.testsInprogressStatusId = testsInprogressStatusId;
	}

	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}

	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}

	public Long getTestStatusUnusedId() {
		return testStatusUnusedId;
	}

	public void setTestStatusUnusedId(Long testStatusUnusedId) {
		this.testStatusUnusedId = testStatusUnusedId;
	}

	public Long getTestStatusPendingId() {
		return testStatusPendingId;
	}

	public void setTestStatusPendingId(Long testStatusPendingId) {
		this.testStatusPendingId = testStatusPendingId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	public String getProcessByStudentId() {
		return processByStudentId;
	}

	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}

	public List<Long> getStudentIdListForReprocess() {
		return studentIdListForReprocess;
	}

	public void setStudentIdListForReprocess(List<Long> studentIdListForReprocess) {
		this.studentIdListForReprocess = studentIdListForReprocess;
	}

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getGradeCourseAbbreviatedName() {
		return gradeCourseAbbreviatedName;
	}

	public void setGradeCourseAbbreviatedName(String gradeCourseAbbreviatedName) {
		this.gradeCourseAbbreviatedName = gradeCourseAbbreviatedName;
	}

	 
}
