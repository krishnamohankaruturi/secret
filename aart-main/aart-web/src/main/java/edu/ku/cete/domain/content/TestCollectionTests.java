package edu.ku.cete.domain.content;

import edu.ku.cete.domain.audit.AuditableDomain;

public class TestCollectionTests extends AuditableDomain {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollectionstests.testcollectionid
     *
     * @mbggenerated Wed Sep 12 16:14:55 CDT 2012
     */
    private Long testCollectionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollectionstests.testid
     *
     * @mbggenerated Wed Sep 12 16:14:55 CDT 2012
     */
    private Long testId;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollectionstests.testcollectionid
     *
     * @return the value of public.testcollectionstests.testcollectionid
     *
     * @mbggenerated Wed Sep 12 16:14:55 CDT 2012
     */
    public Long getTestCollectionId() {
        return testCollectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollectionstests.testcollectionid
     *
     * @param testCollectionId the value for public.testcollectionstests.testcollectionid
     *
     * @mbggenerated Wed Sep 12 16:14:55 CDT 2012
     */
    public void setTestCollectionId(Long testCollectionId) {
        this.testCollectionId = testCollectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollectionstests.testid
     *
     * @return the value of public.testcollectionstests.testid
     *
     * @mbggenerated Wed Sep 12 16:14:55 CDT 2012
     */
    public Long getTestId() {
        return testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollectionstests.testid
     *
     * @param testId the value for public.testcollectionstests.testid
     *
     * @mbggenerated Wed Sep 12 16:14:55 CDT 2012
     */
    public void setTestId(Long testId) {
        this.testId = testId;
    }
}