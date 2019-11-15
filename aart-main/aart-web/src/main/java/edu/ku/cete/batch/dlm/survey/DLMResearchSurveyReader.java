package edu.ku.cete.batch.dlm.survey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.DLMStudentSurveyRosterDetailsDto;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.util.CommonConstants;

public class DLMResearchSurveyReader<T> extends AbstractPagingItemReader<T>{

	private final static Log logger = LogFactory.getLog(DLMResearchSurveyReader.class);
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	private ContentArea contentArea;
	private Organization contractingOrganization;
	private Long operationalWindowId;
	private String assessmentProgramCode;
	private StepExecution stepExecution;	
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		results.addAll(getDLMStudentsForResearchSurvey());
	}
	
	@SuppressWarnings("unchecked")
	private Collection<? extends T> getDLMStudentsForResearchSurvey() {
		List<ContentArea> contentAreasToLookUp = new ArrayList<>();
		if (CommonConstants.ASSESSMENT_PROGRAM_I_SMART.equals(assessmentProgramCode)) {
			contentAreasToLookUp.add(contentArea);
		} else if (CommonConstants.ASSESSMENT_PROGRAM_DLM.equals(assessmentProgramCode)) {
			contentAreasToLookUp = contentAreaService.getForStudentTracker(contractingOrganization.getId());
		}
		List<DLMStudentSurveyRosterDetailsDto> studentSurveyRosterDetails = enrollmentService.getDLMStudentsForResearchSurvey(contractingOrganization, 
				contentAreasToLookUp, operationalWindowId, getPage() * getPageSize(), getPageSize());
		return (Collection<? extends T>) studentSurveyRosterDetails;
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
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}
	
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getOperationalWindowId() {
		return operationalWindowId;
	}

	public void setOperationalWindowId(Long operationalWindowId) {
		this.operationalWindowId = operationalWindowId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
}
