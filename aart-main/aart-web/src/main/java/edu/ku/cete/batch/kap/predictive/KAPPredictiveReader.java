package edu.ku.cete.batch.kap.predictive;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.util.TimerUtil;

public class KAPPredictiveReader<T> extends AbstractPagingItemReader<T> {
	
	private final static Log logger = LogFactory.getLog(KAPPredictiveReader.class);
	
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private GradeCourse gradeCourse;
	private ContentArea contentArea;
	private TestType testType;
	private Assessment assessment;
	private Organization contractingOrganization;
	private Date jobLastSubmissionDate;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getEnrollments(contentArea, gradeCourse, contractingOrganization, getPage() * getPageSize(), getPageSize()));
	}

	@Override
	protected void doJumpToPage(int arg0) {
		logger.debug("NO-OP");
	}

	@SuppressWarnings("unchecked")
	private List<T> getEnrollments(ContentArea contentArea,	GradeCourse gradeCourse, Organization contractingOrganization, Integer offset, Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assessmentProgramId", assessmentProgramId);
		params.put("contentAreaId", contentArea.getId());
		params.put("gradeCourseId", gradeCourse.getId());
		params.put("contractingOrgId", contractingOrganization.getId());
		params.put("currentSchoolYear", contractingOrganization.getCurrentSchoolYear());
		params.put("operationalTestWindowId", contractingOrganization.getOperationalWindowId());
		params.put("pageSize", pageSize);
		params.put("offset", null); // null because the query handles eliminating students from the results once they have a test
		params.put("jobLastSubmissionDate", jobLastSubmissionDate);
		
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsForKapPredictiveRegistration(params);
		return (List<T>) enrollments;
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
	
	public Date getJobLastSubmissionDate() {
		return jobLastSubmissionDate;
	}

	public void setJobLastSubmissionDate(Date jobLastSubmissionDate) {
		this.jobLastSubmissionDate = jobLastSubmissionDate;
	}
	
}
