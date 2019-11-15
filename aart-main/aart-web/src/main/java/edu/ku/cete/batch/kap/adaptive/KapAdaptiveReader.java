package edu.ku.cete.batch.kap.adaptive;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.service.EnrollmentService;

public class KapAdaptiveReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(KapAdaptiveReader.class);

	private String assessmentProgramCode;
	private GradeCourse gradeCourse;
	private ContentArea contentArea;
	private TestType testType;
	private Assessment assessment;
	private Organization contractingOrganization;
	private String interimFlag;
	private Date jobLastSubmissionDate;
	private Long testCompletedStatusId;
	private Long stage1Id;
	private Long assessmentProgramId;

	@Autowired
	private EnrollmentService enrollmentService;
		
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getEnrollments(assessment, testType, contentArea, gradeCourse, contractingOrganization,
				getPage() * getPageSize(), getPageSize()));
	}

	@Override
	protected void doJumpToPage(int arg0) {
		logger.debug("NO-OP");
	}

	@SuppressWarnings("unchecked")
	private List<T> getEnrollments(Assessment assessment, TestType testType, ContentArea contentArea,
			GradeCourse gradeCourse, Organization contractingOrganization, Integer offset, Integer pageSize) {
		
		List<Enrollment> enrollments = null;
		
		if(!isInterimFlag()){
			enrollments = enrollmentService.getEnrollmentsForKapAdaptiveRegistration(testType.getId(),
					contentArea.getId(), gradeCourse.getId(), contractingOrganization.getId(), assessment.getId(),
					contractingOrganization.getCurrentSchoolYear(), offset, pageSize, isInterimFlag(), jobLastSubmissionDate, assessmentProgramId);
		}else{
			enrollments = enrollmentService.findEnrollmentsForKAPAdaptiveStage2Assignment(contentArea.getId(), gradeCourse.getId(), contractingOrganization.getId(), 
						contractingOrganization.getCurrentSchoolYear(), assessmentProgramCode, assessmentProgramId, contractingOrganization.getOperationalWindowId(), 
						testCompletedStatusId, stage1Id, jobLastSubmissionDate, offset, pageSize);
		}	
		
		return (List<T>) enrollments;
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
	public String getInterimFlag() {
		return interimFlag;
	}

	public void setInterimFlag(String interimFlag) {
		this.interimFlag = interimFlag;
	}
	
	private boolean isInterimFlag(){
		if(interimFlag != null && interimFlag.equalsIgnoreCase("true")){
			return true;
		}else{
			return false;
		}
	}

	public Date getJobLastSubmissionDate() {
		return jobLastSubmissionDate;
	}

	public void setJobLastSubmissionDate(Date jobLastSubmissionDate) {
		this.jobLastSubmissionDate = jobLastSubmissionDate;
	}

	public Long getTestCompletedStatusId() {
		return testCompletedStatusId;
	}

	public void setTestCompletedStatusId(Long testCompletedStatusId) {
		this.testCompletedStatusId = testCompletedStatusId;
	}

	public Long getStage1Id() {
		return stage1Id;
	}

	public void setStage1Id(Long stage1Id) {
		this.stage1Id = stage1Id;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	
}
