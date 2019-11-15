package edu.ku.cete.batch.auto.ismart;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.service.EnrollmentService;
import net.jawr.web.util.StringUtils;

/**
 * @author Kiran Reddy Taduru
 * Jun 1, 2018 4:22:19 PM
 */
public class ISmartAutoEnrollmentReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(ISmartAutoEnrollmentReader.class);
	private Organization contractingOrganization;
	private Long operationalTestWindowId;
	private ContentArea contentArea;
	private String gradeAbbrName;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	
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
		results.addAll(getEnrollments(contentArea, contractingOrganization, getPage() * getPageSize(), getPageSize()));
	}
	
	@SuppressWarnings("unchecked")
	private List<T> getEnrollments(ContentArea contentArea, Organization contractingOrganization, Integer offset, Integer pageSize) {
		StringBuffer msg = new StringBuffer();
		msg.append(">>>>>> Inside ISmartAutoEnrollmentReader::getEnrollments() for State: ");
		msg.append(contractingOrganization.getId());
		msg.append(" ");
		msg.append(contractingOrganization.getDisplayIdentifier());
		msg.append(" - SchoolYear: ");
		msg.append(contractingOrganization.getCurrentSchoolYear());
		msg.append(" - ContentAreaId: ");
		msg.append(contentArea.getId());
		msg.append(" - Grade: ");
		msg.append(gradeAbbrName);
		msg.append(" - OTW Id: ");
		msg.append(operationalTestWindowId);
		
		logger.debug(msg.toString());

		List<StudentRoster> studentRosters = enrollmentService.getEnrollmentsForISmartBatchAuto(contractingOrganization.getId(), assessmentProgramId,
				contractingOrganization.getCurrentSchoolYear(),contentArea.getId(), gradeAbbrName,  operationalTestWindowId, offset, pageSize);
		
		msg = new StringBuffer();
		msg.append("<<<<<< Exiting ISmartAutoEnrollmentReader::getEnrollments() for State: ");
		msg.append(contractingOrganization.getId());
		msg.append(" ");
		msg.append(contractingOrganization.getDisplayIdentifier());
		msg.append(" - SchoolYear: ");
		msg.append(contractingOrganization.getCurrentSchoolYear());
		msg.append(" - ContentAreaId: ");
		msg.append(contentArea.getId());
		msg.append(" - Grade: ");
		msg.append(gradeAbbrName);
		msg.append(" - OTW Id: ");
		msg.append(operationalTestWindowId);
		msg.append(" - Enrollment count: ");
		msg.append((CollectionUtils.isNotEmpty(studentRosters) ? studentRosters.size() : 0));
		logger.debug(msg.toString());
		
		return (List<T>) studentRosters;
	}
	
	@Override
	protected void doJumpToPage(int itemIndex) {
		logger.debug("NO-OP");
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
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

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
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

}
