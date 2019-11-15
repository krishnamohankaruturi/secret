package edu.ku.cete.domain.professionaldevelopment;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTestResponse extends UserTestResponseKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.userid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Long userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.testid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Long testId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.testsectionid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Long testSectionId;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.foilid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Long foilId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.response
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private String response;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.createddate
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.modifieddate
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Date modifiedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.score
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private BigDecimal score;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.activeflag
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Boolean activeFlag;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.userid
     *
     * @return the value of usertestresponse.userid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.userid
     *
     * @param userId the value for usertestresponse.userid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.testid
     *
     * @return the value of usertestresponse.testid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public Long getTestId() {
        return testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.testid
     *
     * @param testId the value for usertestresponse.testid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setTestId(Long testId) {
        this.testId = testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.testsectionid
     *
     * @return the value of usertestresponse.testsectionid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public Long getTestSectionId() {
        return testSectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.testsectionid
     *
     * @param testSectionId the value for usertestresponse.testsectionid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setTestSectionId(Long testSectionId) {
        this.testSectionId = testSectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.foilid
     *
     * @return the value of usertestresponse.foilid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    @JsonProperty("foil")
    public Long getFoilId() {
        return foilId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.foilid
     *
     * @param foilId the value for usertestresponse.foilid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    @JsonProperty("foil")
    public void setFoilId(Long foilId) {
        this.foilId = foilId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.response
     *
     * @return the value of usertestresponse.response
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public String getResponse() {
        return response;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.response
     *
     * @param response the value for usertestresponse.response
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setResponse(String response) {
        this.response = response == null ? null : response.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.createddate
     *
     * @return the value of usertestresponse.createddate
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.createddate
     *
     * @param createdDate the value for usertestresponse.createddate
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.modifieddate
     *
     * @return the value of usertestresponse.modifieddate
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.modifieddate
     *
     * @param modifiedDate the value for usertestresponse.modifieddate
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.score
     *
     * @return the value of usertestresponse.score
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public BigDecimal getScore() {
        return score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.score
     *
     * @param score the value for usertestresponse.score
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setScore(BigDecimal score) {
        this.score = score;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.activeflag
     *
     * @return the value of usertestresponse.activeflag
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.activeflag
     *
     * @param activeFlag the value for usertestresponse.activeflag
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
    
    private String foilText;
    
    public String getFoilText() {
        return foilText;
    }

    public void setFoilText(String foilText) {
        this.foilText = foilText;
    }
    
    private String taskTypeCode;

	public String getTaskTypeCode() {
		return taskTypeCode;
	}

	public void setTaskTypeCode(String taskTypeCode) {
		this.taskTypeCode = taskTypeCode;
	}    
}