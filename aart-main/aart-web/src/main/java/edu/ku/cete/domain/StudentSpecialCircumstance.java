package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;

public class StudentSpecialCircumstance extends AuditableDomain{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6485701945919559532L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column studentspecialcircumstance.studenttestid
     *
     * @mbggenerated Thu Jan 02 14:11:45 CST 2014
     */
    private Long studentTestid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column studentspecialcircumstance.specialcircumstanceid
     *
     * @mbggenerated Thu Jan 02 14:11:45 CST 2014
     */
    private Long specialCircumstanceId;

    private Long status;
    
    private Long approvedBy;

    private String statusName;

    private String approverName;

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column studentspecialcircumstance.studenttestid
     *
     * @return the value of studentspecialcircumstance.studenttestid
     *
     * @mbggenerated Thu Jan 02 14:11:45 CST 2014
     */
    public Long getStudentTestid() {
        return studentTestid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column studentspecialcircumstance.studenttestid
     *
     * @param studentTestid the value for studentspecialcircumstance.studenttestid
     *
     * @mbggenerated Thu Jan 02 14:11:45 CST 2014
     */
    public void setStudentTestid(Long studentTestid) {
        this.studentTestid = studentTestid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column studentspecialcircumstance.specialcircumstanceid
     *
     * @return the value of studentspecialcircumstance.specialcircumstanceid
     *
     * @mbggenerated Thu Jan 02 14:11:45 CST 2014
     */
    public Long getSpecialCircumstanceId() {
        return specialCircumstanceId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column studentspecialcircumstance.specialcircumstanceid
     *
     * @param specialCircumstanceId the value for studentspecialcircumstance.specialcircumstanceid
     *
     * @mbggenerated Thu Jan 02 14:11:45 CST 2014
     */
    public void setSpecialCircumstanceId(Long specialCircumstanceId) {
        this.specialCircumstanceId = specialCircumstanceId;
    }
    
    public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(Long approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}