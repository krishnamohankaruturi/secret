package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.content.AssessmentProgram;

/**
 * Added for US16239 (add assessment program to user)
 * @author prashanth.a
 *
 */

public class UserAssessmentPrograms extends AuditableDomain {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8145782695055018986L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userassessmentprograms.id
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     *////Primary key
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userassessmentprograms.aartUserId
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    private Long aartUserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userassessmentprograms.assessmentprogramid
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    private Long assessmentProgramId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userassessmentprograms.activeflag
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    private Boolean activeFlag;
    
    private Boolean isDefault;
    
    private Long userOrganizationGroupId;
    
    private Long groupId;
    
    private Long organizationId;
    
    private String groupName;
    
    private String abbreviatedName;
    
    private AssessmentProgram assessmentProgram;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userassessmentprograms.id
     *
     * @return the value of userassessmentprograms.id
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userassessmentprograms.id
     *
     * @param id the value for userassessmentprograms.id
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userassessmentprograms.aartUserId
     *
     * @return the value of userassessmentprograms.aartUserId
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public Long getAartUserId() {
        return aartUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userassessmentprograms.aartUserId
     *
     * @param aartUserId the value for userassessmentprograms.aartUserId
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public void setAartUserId(Long aartUserId) {
        this.aartUserId = aartUserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userassessmentprograms.assessmentprogramid
     *
     * @return the value of userassessmentprograms.assessmentprogramid
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public Long getAssessmentProgramId() {
        return assessmentProgramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userassessmentprograms.assessmentprogramid
     *
     * @param assessmentProgramId the value for userassessmentprograms.assessmentprogramid
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public void setAssessmentProgramId(Long assessmentProgramId) {
        this.assessmentProgramId = assessmentProgramId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userassessmentprograms.activeflag
     *
     * @return the value of userassessmentprograms.activeflag
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userassessmentprograms.activeflag
     *
     * @param activeFlag the value for userassessmentprograms.activeflag
     *
     * @mbggenerated Fri Jul 03 12:41:12 IST 2015
     */
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Long getUserOrganizationGroupId() {
		return userOrganizationGroupId;
	}

	public void setUserOrganizationGroupId(Long userOrganizationGroupId) {
		this.userOrganizationGroupId = userOrganizationGroupId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAbbreviatedName() {
		return abbreviatedName;
	}

	public void setAbbreviatedName(String abbreviatedName) {
		this.abbreviatedName = abbreviatedName;
	}

	public AssessmentProgram getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(AssessmentProgram assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
}