package edu.ku.cete.domain.student.survey;

import java.util.Date;

import edu.ku.cete.domain.audit.AuditableDomain;

public class SurveyPageStatus extends AuditableDomain {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.id
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.is_completed
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Boolean isCompleted;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.survey_id
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Long surveyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.global_page_num
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Integer globalPageNum;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.created_date
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Date createdDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.created_user
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Long createdUser;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.active_flag
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Boolean activeFlag;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.modified_date
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Date modifiedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.survey_page_status.modified_user
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    private Long modifiedUser;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.id
     *
     * @return the value of public.survey_page_status.id
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.id
     *
     * @param id the value for public.survey_page_status.id
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.is_completed
     *
     * @return the value of public.survey_page_status.is_completed
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Boolean getIsCompleted() {
        return isCompleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.is_completed
     *
     * @param isCompleted the value for public.survey_page_status.is_completed
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.survey_id
     *
     * @return the value of public.survey_page_status.survey_id
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Long getSurveyId() {
        return surveyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.survey_id
     *
     * @param surveyId the value for public.survey_page_status.survey_id
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.global_page_num
     *
     * @return the value of public.survey_page_status.global_page_num
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Integer getGlobalPageNum() {
        return globalPageNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.global_page_num
     *
     * @param globalPageNum the value for public.survey_page_status.global_page_num
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setGlobalPageNum(Integer globalPageNum) {
        this.globalPageNum = globalPageNum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.created_date
     *
     * @return the value of public.survey_page_status.created_date
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.created_date
     *
     * @param createdDate the value for public.survey_page_status.created_date
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.created_user
     *
     * @return the value of public.survey_page_status.created_user
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Long getCreatedUser() {
        return createdUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.created_user
     *
     * @param createdUser the value for public.survey_page_status.created_user
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.active_flag
     *
     * @return the value of public.survey_page_status.active_flag
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Boolean getActiveFlag() {
        return activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.active_flag
     *
     * @param activeFlag the value for public.survey_page_status.active_flag
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.modified_date
     *
     * @return the value of public.survey_page_status.modified_date
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.modified_date
     *
     * @param modifiedDate the value for public.survey_page_status.modified_date
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.survey_page_status.modified_user
     *
     * @return the value of public.survey_page_status.modified_user
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public Long getModifiedUser() {
        return modifiedUser;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.survey_page_status.modified_user
     *
     * @param modifiedUser the value for public.survey_page_status.modified_user
     *
     * @mbggenerated Wed Jun 05 07:56:10 CDT 2013
     */
    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }
}