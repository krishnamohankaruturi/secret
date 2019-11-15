/**
 * 
 */
package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;

/**
 * @author neil.howerton
 *
 */
public class OrgAssessmentProgram extends AuditableDomain {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8753147188268667320L;
	
	private long id;
    private long organizationId;
    private long assessmentProgramId;
    private Organization organization;
    private AssessmentProgram assessmentProgram;
    private Boolean isDefault; 
    /**
     * @return the id
     */
    public final long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(long id) {
        this.id = id;
    }
    /**
     * @return the organizationId
     */
    public final long getOrganizationId() {
        return organizationId;
    }
    /**
     * @param organizationId the organizationId to set
     */
    public final void setOrganizationId(long organizationId) {
        this.organizationId = organizationId;
    }
    /**
     * @return the assessmentProgramId
     */
    public final long getAssessmentProgramId() {
        return assessmentProgramId;
    }
    /**
     * @param assessmentProgramId the assessmentProgramId to set
     */
    public final void setAssessmentProgramId(long assessmentProgramId) {
        this.assessmentProgramId = assessmentProgramId;
    }
    /**
     * @return the organization
     */
    public Organization getOrganization() {
        return organization;
    }
    /**
     * @param organization the organization to set
     */
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    /**
     * @return the assessmentProgram
     */
    public AssessmentProgram getAssessmentProgram() {
        return assessmentProgram;
    }
    /**
     * @param assessmentProgram the assessmentProgram to set
     */
    public void setAssessmentProgram(AssessmentProgram assessmentProgram) {
        this.assessmentProgram = assessmentProgram;
    }
	public Boolean getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
