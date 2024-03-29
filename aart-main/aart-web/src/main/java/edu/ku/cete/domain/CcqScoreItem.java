package edu.ku.cete.domain;

import java.util.Date;

public class CcqScoreItem {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ccqscoreteststudentrubricscore.id
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ccqscoreteststudentrubricscore.ccqscoretestid
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    private Long ccqScoreId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ccqscoreteststudentrubricscore.rubriccategoryid
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    private Long rubricCategoryId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ccqscoreteststudentrubricscore.score
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    private Integer score;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ccqscoreteststudentrubricscore.createddate
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ccqscoreteststudentrubricscore.createduser
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    private Integer createdUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ccqscoreteststudentrubricscore.active
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    private Boolean active;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ccqscoreteststudentrubricscore.id
     *
     * @return the value of ccqscoreteststudentrubricscore.id
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    
    private Long taskVariantId;
    
    private Long nonScoringReason;
    
    private Long externalScorer;
    
    private String source;
    
    private Date modifiedDate;
    
    private Integer modifiedUser;
    
    

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public Integer getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Integer modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

    
    public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Long getExternalScorer() {
		return externalScorer;
	}

	public void setExternalScorer(Long externalScorer) {
		this.externalScorer = externalScorer;
	}

	public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ccqscoreteststudentrubricscore.id
     *
     * @param id the value for ccqscoreteststudentrubricscore.id
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ccqscoreteststudentrubricscore.ccqscoretestid
     *
     * @return the value of ccqscoreteststudentrubricscore.ccqscoretestid
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public Long getCcqScoreId() {
        return ccqScoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ccqscoreteststudentrubricscore.ccqscoretestid
     *
     * @param ccqScoreTestId the value for ccqscoreteststudentrubricscore.ccqscoretestid
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public void setCcqScoreId(Long ccqScoreId) {
        this.ccqScoreId = ccqScoreId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ccqscoreteststudentrubricscore.rubriccategoryid
     *
     * @return the value of ccqscoreteststudentrubricscore.rubriccategoryid
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public Long getRubricCategoryId() {
        return rubricCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ccqscoreteststudentrubricscore.rubriccategoryid
     *
     * @param rubricCategoryId the value for ccqscoreteststudentrubricscore.rubriccategoryid
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public void setRubricCategoryId(Long rubricCategoryId) {
        this.rubricCategoryId = rubricCategoryId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ccqscoreteststudentrubricscore.score
     *
     * @return the value of ccqscoreteststudentrubricscore.score
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public Integer getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ccqscoreteststudentrubricscore.score
     *
     * @param score the value for ccqscoreteststudentrubricscore.score
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ccqscoreteststudentrubricscore.createddate
     *
     * @return the value of ccqscoreteststudentrubricscore.createddate
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ccqscoreteststudentrubricscore.createddate
     *
     * @param createdDate the value for ccqscoreteststudentrubricscore.createddate
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ccqscoreteststudentrubricscore.createduser
     *
     * @return the value of ccqscoreteststudentrubricscore.createduser
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public Integer getCreatedUser() {
        return createdUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ccqscoreteststudentrubricscore.createduser
     *
     * @param createdUser the value for ccqscoreteststudentrubricscore.createduser
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ccqscoreteststudentrubricscore.active
     *
     * @return the value of ccqscoreteststudentrubricscore.active
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ccqscoreteststudentrubricscore.active
     *
     * @param active the value for ccqscoreteststudentrubricscore.active
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

	public Long getTaskVariantId() {
		return taskVariantId;
	}

	public void setTaskVariantId(Long taskVariantId) {
		this.taskVariantId = taskVariantId;
	}

	public Long getNonScoringReason() {
		return nonScoringReason;
	}

	public void setNonScoringReason(Long nonScoringReason) {
		this.nonScoringReason = nonScoringReason;
	}    
    
}