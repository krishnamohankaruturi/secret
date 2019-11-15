package edu.ku.cete.batch.pltw.auto;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.api.scoring.ScoringAPIObject;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.service.StudentsTestsService;

public class PLTWScoringReader<T> extends AbstractPagingItemReader<T> {
	
	private final static Log logger = LogFactory.getLog(PLTWScoringReader.class);
	
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private GradeCourse gradeCourse;
	private ContentArea contentArea;
	private TestType testType;
	private Assessment assessment;
	private Organization contractingOrganization;
	
	private BatchRegistration batchRegistration;
	private String interimFlag;
	private Date jobLastSubmissionDate;
	
	private String reprocess;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Override
	@SuppressWarnings("unchecked")
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		List<ScoringAPIObject> objects = studentsTestsService.getPLTWScoringData(
				assessmentProgramId, jobLastSubmissionDate, getPageSize(), getPage() * getPageSize(), isNightlyRun(), isReprocess());
		results.addAll((List<T>) objects);
	}

	@Override
	protected void doJumpToPage(int arg0) {
		logger.debug("NO-OP");
	}
	
	private boolean isReprocess() {
		return "true".equalsIgnoreCase(this.reprocess);
	}

	private boolean isDaytimeRun() {
		return "true".equalsIgnoreCase(interimFlag);
	}
	
	private boolean isNightlyRun() {
		return !isDaytimeRun();
	}
	
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public GradeCourse getGradeCourse() {
		return gradeCourse;
	}

	public void setGradeCourse(GradeCourse gradeCourse) {
		this.gradeCourse = gradeCourse;
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public BatchRegistration getBatchRegistration() {
		return batchRegistration;
	}

	public void setBatchRegistration(BatchRegistration batchRegistration) {
		this.batchRegistration = batchRegistration;
	}

	public String getInterimFlag() {
		return interimFlag;
	}

	public void setInterimFlag(String interimFlag) {
		this.interimFlag = interimFlag;
	}

	public Date getJobLastSubmissionDate() {
		return jobLastSubmissionDate;
	}

	public void setJobLastSubmissionDate(Date jobLastSubmissionDate) {
		this.jobLastSubmissionDate = jobLastSubmissionDate;
	}

	public String getReprocess() {
		return reprocess;
	}

	public void setReprocess(String reprocess) {
		this.reprocess = reprocess;
	}

}
