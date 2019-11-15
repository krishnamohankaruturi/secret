package edu.ku.cete.domain;

import java.util.Date;
import java.util.List;

public class ScoringAssignment {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column scoringassignment.id
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column scoringassignment.testsessionid
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    private Long testSessionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column scoringassignment.createddate
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column scoringassignment.createduser
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    private Integer createdUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column scoringassignment.active
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    private Boolean active;

    
    private String ccqTestName;
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column scoringassignment.id
     *
     * @return the value of scoringassignment.id
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    
    private List<ScoringAssignmentStudent> scoringAssignmentStudent;
    
    private List<ScoringAssignmentScorer> scoringAssignmentScorer; 
    
    private Long rosterId;  
    
    private String source;
    
    private Date modifiedDate;
    
    private Long modifiedUser;
    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column scoringassignment.id
     *
     * @param id the value for scoringassignment.id
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column scoringassignment.testsessionid
     *
     * @return the value of scoringassignment.testsessionid
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public Long getTestSessionId() {
        return testSessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column scoringassignment.testsessionid
     *
     * @param testSessionId the value for scoringassignment.testsessionid
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public void setTestSessionId(Long testSessionId) {
        this.testSessionId = testSessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column scoringassignment.createddate
     *
     * @return the value of scoringassignment.createddate
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column scoringassignment.createddate
     *
     * @param createdDate the value for scoringassignment.createddate
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column scoringassignment.createduser
     *
     * @return the value of scoringassignment.createduser
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public Integer getCreatedUser() {
        return createdUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column scoringassignment.createduser
     *
     * @param createdUser the value for scoringassignment.createduser
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column scoringassignment.active
     *
     * @return the value of scoringassignment.active
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column scoringassignment.active
     *
     * @param active the value for scoringassignment.active
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

	public List<ScoringAssignmentStudent> getScoringAssignmentStudent() {
		return scoringAssignmentStudent;
	}

	public void setScoringAssignmentStudent(
			List<ScoringAssignmentStudent> scoringAssignmentStudent) {
		this.scoringAssignmentStudent = scoringAssignmentStudent;
	}

	public List<ScoringAssignmentScorer> getScoringAssignmentScorer() {
		return scoringAssignmentScorer;
	}

	public void setScoringAssignmentScorer(List<ScoringAssignmentScorer> scoringAssignmentScorer) {
		this.scoringAssignmentScorer = scoringAssignmentScorer;
	}

	public String getCcqTestName() {
		return ccqTestName;
	}

	public void setCcqTestName(String ccqTestName) {
		this.ccqTestName = ccqTestName;
	}

	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifiedUser
	 */
	public Long getModifiedUser() {
		return modifiedUser;
	}

	/**
	 * @param modifiedUser the modifiedUser to set
	 */
	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	
	
}