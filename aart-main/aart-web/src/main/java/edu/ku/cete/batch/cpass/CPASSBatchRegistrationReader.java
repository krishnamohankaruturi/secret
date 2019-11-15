package edu.ku.cete.batch.cpass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.service.EnrollmentService;

public class CPASSBatchRegistrationReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(CPASSBatchRegistrationReader.class);

	private String assessmentProgramCode;
	private TestType testType;
	private Assessment assessment;
	
	private List<Organization> contractingOrganizations;
	
	@Autowired
	private EnrollmentService enrollmentService;	
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		results.addAll(getEnrollments(getPage() * getPageSize(), getPageSize()));
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		//no-op
		logger.debug("NO-OP");
	}
	
	@SuppressWarnings("unchecked")
	private List<T> getEnrollments(Integer offset, Integer pageSize) {
		Long schoolYear = 0L;
		List<Long> orgIds = new ArrayList<Long>();
		for (Organization org : contractingOrganizations) {
			orgIds.add(org.getId());
			if (org.getCurrentSchoolYear() > schoolYear) {
				schoolYear = org.getCurrentSchoolYear();
			}
		}
		List<Enrollment> enrollments = enrollmentService.getCPASSEnrollmentsForBatchRegistration(testType.getId(), 
				orgIds, assessment.getId(), schoolYear, offset, pageSize);
			
		return (List<T>) enrollments;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public TestType getTestType() {
		return testType;
	}

	public void setTestType(TestType testType) {
		this.testType = testType;
	}

	public List<Organization> getContractingOrganizations() {
		return contractingOrganizations;
	}

	public void setContractingOrganizations(
			List<Organization> contractingOrganizations) {
		this.contractingOrganizations = contractingOrganizations;
	}
}