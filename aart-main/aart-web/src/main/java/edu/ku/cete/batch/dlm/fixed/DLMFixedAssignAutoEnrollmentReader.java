package edu.ku.cete.batch.dlm.fixed;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.service.EnrollmentService;

public class DLMFixedAssignAutoEnrollmentReader<T> extends AbstractPagingItemReader<T> {
	
	private final static Log logger = LogFactory.getLog(DLMFixedAssignAutoEnrollmentReader.class);
	
	private Organization contractingOrganization;
	private Long operationalTestWindowId;
	private Long assessmentProgramId;
	private String enrollmentMethod;
	private ContentArea contentArea;
	private GradeCourse course;
	private String gradeAbbrName;
	
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
		results.addAll(getEnrollments(contentArea, contractingOrganization, course, getPage() * getPageSize(), getPageSize()));
	}
	
	@SuppressWarnings("unchecked")
	private List<T> getEnrollments(ContentArea contentArea, Organization contractingOrganization, GradeCourse course, Integer offset, Integer pageSize) {		
		List<StudentRoster> studentRosters = enrollmentService.getDlmFixedAssignEnrollments(contractingOrganization.getId(), assessmentProgramId,
				contractingOrganization.getCurrentSchoolYear(),contentArea.getId(), course.getId(), gradeAbbrName,  offset, pageSize);		
		return (List<T>) studentRosters;
	}

	@Override
	protected void doJumpToPage(int itemIndex) {		
		logger.debug("NO-OP");
	}
	
	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}	
	
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}	

	public String getGradeAbbrName() {
		return gradeAbbrName;
	}

	public void setGradeAbbrName(String gradeAbbrName) {
		this.gradeAbbrName = gradeAbbrName;
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public GradeCourse getCourse() {
		return course;
	}

	public void setCourse(GradeCourse course) {
		this.course = course;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}		
}
