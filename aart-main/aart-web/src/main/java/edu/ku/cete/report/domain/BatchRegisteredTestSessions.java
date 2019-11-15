package edu.ku.cete.report.domain;

import java.io.Serializable;

public class BatchRegisteredTestSessions implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3507930273347035239L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column batchregisteredtestsessions.batchregistrationid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    private Long batchRegistrationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column batchregisteredtestsessions.testsessionid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    private Long testSessionId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column batchregisteredtestsessions.batchregistrationid
     *
     * @return the value of batchregisteredtestsessions.batchregistrationid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public Long getBatchRegistrationId() {
        return batchRegistrationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column batchregisteredtestsessions.batchregistrationid
     *
     * @param batchRegistrationId the value for batchregisteredtestsessions.batchregistrationid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public void setBatchRegistrationId(Long batchRegistrationId) {
        this.batchRegistrationId = batchRegistrationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column batchregisteredtestsessions.testsessionid
     *
     * @return the value of batchregisteredtestsessions.testsessionid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public Long getTestSessionId() {
        return testSessionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column batchregisteredtestsessions.testsessionid
     *
     * @param testSessionId the value for batchregisteredtestsessions.testsessionid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public void setTestSessionId(Long testSessionId) {
        this.testSessionId = testSessionId;
    }
}