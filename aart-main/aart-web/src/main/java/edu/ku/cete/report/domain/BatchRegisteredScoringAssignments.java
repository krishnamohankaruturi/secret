package edu.ku.cete.report.domain;

import java.io.Serializable;

public class BatchRegisteredScoringAssignments implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3507930273347035239L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BatchRegisteredScoringAssignments.batchregistrationid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    private Long batchRegistrationId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column BatchRegisteredScoringAssignments.scoringAssignmentId
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    private Long scoringAssignmentId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BatchRegisteredScoringAssignments.batchregistrationid
     *
     * @return the value of BatchRegisteredScoringAssignments.batchregistrationid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public Long getBatchRegistrationId() {
        return batchRegistrationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BatchRegisteredScoringAssignments.batchregistrationid
     *
     * @param batchRegistrationId the value for BatchRegisteredScoringAssignments.batchregistrationid
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public void setBatchRegistrationId(Long batchRegistrationId) {
        this.batchRegistrationId = batchRegistrationId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column BatchRegisteredScoringAssignments.scoringAssignmentId
     *
     * @return the value of BatchRegisteredScoringAssignments.scoringAssignmentId
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public Long getScoringAssignmentId() {
        return scoringAssignmentId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column BatchRegisteredScoringAssignments.scoringAssignmentId
     *
     * @param scoringAssignmentId the value for BatchRegisteredScoringAssignments.scoringAssignmentId
     *
     * @mbggenerated Mon Sep 14 16:42:42 CDT 2015
     */
    public void setScoringAssignmentId(Long scoringAssignmentId) {
        this.scoringAssignmentId = scoringAssignmentId;
    }
}