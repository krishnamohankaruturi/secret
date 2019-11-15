package edu.ku.cete.domain.common;

import java.io.Serializable;
import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;

/**
 *
 * @author mrajannan
 *
 */
public class ActivationEmailTemplateState extends AuditableDomain implements Serializable, Identifiable {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 4163701807053372620L;

    /** The id of the email template. */
    private Long templateId;
    /**
     * name of the email template.
     */
 
    private Long assessmentProgramId;

    private Long stateId;    
   
    private String stateIds;
     
    private ActivationEmailTemplate activationEmailTemplate;
    
    
	public ActivationEmailTemplate getActivationEmailTemplate() {
		return activationEmailTemplate;
	}


	public void setActivationEmailTemplate(
			ActivationEmailTemplate activationEmailTemplate) {
		this.activationEmailTemplate = activationEmailTemplate;
	}

	public Long getTemplateId() {
		return templateId;
	}


	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}


	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}


	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}


	public Long getStateId() {
		return stateId;
	}


	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	
	public String getStateIds() {
		return stateIds;
	}


	public void setStateIds(String stateIds) {
		this.stateIds = stateIds;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Long getId(int order) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getStringIdentifier(int order) {
		// TODO Auto-generated method stub
		return null;
	}
		
}
