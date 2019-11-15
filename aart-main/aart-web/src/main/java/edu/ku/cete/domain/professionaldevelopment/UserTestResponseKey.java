package edu.ku.cete.domain.professionaldevelopment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTestResponseKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.usertestsectionid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Long userTestSectionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column usertestresponse.taskvariantid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    private Long taskVariantId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.usertestsectionid
     *
     * @return the value of usertestresponse.usertestsectionid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    @JsonProperty("studentTestSectionId")
    public Long getUserTestSectionId() {
        return userTestSectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.usertestsectionid
     *
     * @param userTestSectionId the value for usertestresponse.usertestsectionid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    @JsonProperty("studentTestSectionId")
    public void setUserTestSectionId(Long userTestSectionId) {
        this.userTestSectionId = userTestSectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column usertestresponse.taskvariantid
     *
     * @return the value of usertestresponse.taskvariantid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    @JsonProperty("taskId")
    public Long getTaskVariantId() {
        return taskVariantId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column usertestresponse.taskvariantid
     *
     * @param taskVariantId the value for usertestresponse.taskvariantid
     *
     * @mbggenerated Tue May 27 16:24:28 CDT 2014
     */
    @JsonProperty("taskId")
    public void setTaskVariantId(Long taskVariantId) {
        this.taskVariantId = taskVariantId;
    }
}