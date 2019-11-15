/**
 * 
 */
package edu.ku.cete.batch.kelpa.auto;

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

/**
 * @author ktaduru_sta
 *
 */
public class KELPAReader<T> extends AbstractPagingItemReader<T> {
	private final static Log logger = LogFactory.getLog(KELPAReader.class);

	private String assessmentProgramCode;
	private GradeCourse gradeCourse;
	private ContentArea contentArea;
	private TestType testType;
	private Assessment assessment;
	private Organization contractingOrganization;

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
		
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsForKELPABatchRegistration(testType.getId(), contentArea.getId(), gradeCourse.getId(), 
				contractingOrganization.getId(), assessment.getId(), contractingOrganization.getCurrentSchoolYear(), offset, pageSize);
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
}
