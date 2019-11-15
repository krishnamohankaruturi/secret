/**
 * 
 */
package edu.ku.cete.batch.pltw.auto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.EnrollmentService;

/**
 * @author Kiran Reddy Taduru
 *
 * Aug 23, 2018 4:01:04 PM
 */
public class PLTWBatchAutoReader<T> extends AbstractPagingItemReader<T> {
private final static Log logger = LogFactory.getLog(PLTWBatchAutoReader.class);
	
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private GradeCourse gradeBand;
	private ContentArea contentArea;
	private Organization contractingOrganization;
	private Long batchRegistrationId;
	private StepExecution stepExecution;
	private Stage stage;
	
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
		results.addAll(getEnrollments(contentArea, gradeBand, contractingOrganization, getPage() * getPageSize(), getPageSize()));
	}

	@SuppressWarnings("unchecked")
	private List<T> getEnrollments(ContentArea contentArea,	GradeCourse gradeBand, Organization contractingOrganization, Integer offset, Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assessmentProgramId", assessmentProgramId);
		params.put("contentAreaId", contentArea.getId());
		params.put("gradeBandId", gradeBand.getGradeBandId());
		params.put("contractingOrgId", contractingOrganization.getId());
		params.put("currentSchoolYear", contractingOrganization.getCurrentSchoolYear());
		params.put("operationalTestWindowId", contractingOrganization.getOperationalWindowId());
		params.put("pageSize", pageSize);
		params.put("offset", offset);		
		
		List<Enrollment> enrollments = enrollmentService.getEnrollmentsForPLTWBatchRegistration(params);
		
		if(CollectionUtils.isEmpty(enrollments)) {
			writeReason(String.format("Enrollments not found for Organization: %d (%s), OperationalTestWindowId: %d, ContentAreaId: %d (%s), SchoolYear: %d, BatchRegistrationId: %d",
					contractingOrganization.getId(), contractingOrganization.getDisplayIdentifier(), contractingOrganization.getOperationalWindowId(), contentArea.getId(), contentArea.getAbbreviatedName(), contractingOrganization.getCurrentSchoolYear(), batchRegistrationId));
		
		}
		
		return (List<T>) enrollments;
	}
	
	@Override
	protected void doJumpToPage(int itemIndex) {
		logger.debug("NO-OP");		
	}

	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		logger.debug(msg);
		
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
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

	public GradeCourse getGradeBand() {
		return gradeBand;
	}

	public void setGradeBand(GradeCourse gradeBand) {
		this.gradeBand = gradeBand;
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

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Stage getStageCode() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}
}
