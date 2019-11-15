package edu.ku.cete.domain.report;

import java.io.Serializable;

public class ReportProcessReason implements Serializable{

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reportprocessreason.reportprocessid
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	private Long reportProcessId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reportprocessreason.studentid
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	private Long studentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reportprocessreason.reason
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	private String reason;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reportprocessreason.testid1
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	private Long testId1;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reportprocessreason.testid2
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	private Long testId2;

	
	private Long testId3;
	private Long testId4;
	private Long performanceTestExternalId;
	
	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reportprocessreason.reportprocessid
	 * @return  the value of reportprocessreason.reportprocessid
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public Long getReportProcessId() {
		return reportProcessId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reportprocessreason.reportprocessid
	 * @param reportProcessId  the value for reportprocessreason.reportprocessid
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public void setReportProcessId(Long reportProcessId) {
		this.reportProcessId = reportProcessId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reportprocessreason.studentid
	 * @return  the value of reportprocessreason.studentid
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reportprocessreason.studentid
	 * @param studentId  the value for reportprocessreason.studentid
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reportprocessreason.reason
	 * @return  the value of reportprocessreason.reason
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reportprocessreason.reason
	 * @param reason  the value for reportprocessreason.reason
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reportprocessreason.testid1
	 * @return  the value of reportprocessreason.testid1
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public Long getTestId1() {
		return testId1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reportprocessreason.testid1
	 * @param testId1  the value for reportprocessreason.testid1
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public void setTestId1(Long testId1) {
		this.testId1 = testId1;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reportprocessreason.testid2
	 * @return  the value of reportprocessreason.testid2
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public Long getTestId2() {
		return testId2;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reportprocessreason.testid2
	 * @param testId2  the value for reportprocessreason.testid2
	 * @mbggenerated  Wed May 20 16:37:21 CDT 2015
	 */
	public void setTestId2(Long testId2) {
		this.testId2 = testId2;
	}

	public Long getTestId3() {
		return testId3;
	}

	public void setTestId3(Long testId3) {
		this.testId3 = testId3;
	}

	public Long getTestId4() {
		return testId4;
	}

	public void setTestId4(Long testId4) {
		this.testId4 = testId4;
	}

	public Long getPerformanceTestExternalId() {
		return performanceTestExternalId;
	}

	public void setPerformanceTestExternalId(Long performanceTestExternalId) {
		this.performanceTestExternalId = performanceTestExternalId;
	}
	
}