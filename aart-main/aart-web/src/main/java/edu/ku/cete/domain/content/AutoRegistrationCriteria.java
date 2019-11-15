package edu.ku.cete.domain.content;

import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.util.ParsingConstants;

public class AutoRegistrationCriteria extends AuditableDomain{
 
    private Long id;
    
    private Long assessmentProgramId;
    
    private Long testingProgramId;
    
    private Long assessmentId;

    private Long contentAreaTestTypeSubjectAreaid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public Long getContentAreaTestTypeSubjectAreaid() {
		return contentAreaTestTypeSubjectAreaid;
	}

	public void setContentAreaTestTypeSubjectAreaid(
			Long contentAreaTestTypeSubjectAreaid) {
		this.contentAreaTestTypeSubjectAreaid = contentAreaTestTypeSubjectAreaid;
	}

	public Long getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}


}