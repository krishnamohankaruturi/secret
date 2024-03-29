package edu.ku.cete.report.domain;

import java.util.Date;

public class QuestarStagingFile {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.id
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.processeddate
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private Date processedDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.filename
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private String fileName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.assessmentrefid
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private String assessmentRefId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.assessmentname
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private String assessmentName;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.result
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private String result;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.successcount
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private Integer successCount;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column questar_staging_file.skipcount
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	private Integer skipCount;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.id
	 * @return  the value of questar_staging_file.id
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.id
	 * @param id  the value for questar_staging_file.id
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.processeddate
	 * @return  the value of questar_staging_file.processeddate
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public Date getProcessedDate() {
		return processedDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.processeddate
	 * @param processedDate  the value for questar_staging_file.processeddate
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.filename
	 * @return  the value of questar_staging_file.filename
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.filename
	 * @param fileName  the value for questar_staging_file.filename
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName == null ? null : fileName.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.assessmentrefid
	 * @return  the value of questar_staging_file.assessmentrefid
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public String getAssessmentRefId() {
		return assessmentRefId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.assessmentrefid
	 * @param assessmentRefId  the value for questar_staging_file.assessmentrefid
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setAssessmentRefId(String assessmentRefId) {
		this.assessmentRefId = assessmentRefId == null ? null : assessmentRefId
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.assessmentname
	 * @return  the value of questar_staging_file.assessmentname
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public String getAssessmentName() {
		return assessmentName;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.assessmentname
	 * @param assessmentName  the value for questar_staging_file.assessmentname
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName == null ? null : assessmentName
				.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.result
	 * @return  the value of questar_staging_file.result
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public String getResult() {
		return result;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.result
	 * @param result  the value for questar_staging_file.result
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setResult(String result) {
		this.result = result == null ? null : result.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.successcount
	 * @return  the value of questar_staging_file.successcount
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public Integer getSuccessCount() {
		return successCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.successcount
	 * @param successCount  the value for questar_staging_file.successcount
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column questar_staging_file.skipcount
	 * @return  the value of questar_staging_file.skipcount
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public Integer getSkipCount() {
		return skipCount;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column questar_staging_file.skipcount
	 * @param skipCount  the value for questar_staging_file.skipcount
	 * @mbggenerated  Wed Jun 10 14:12:10 CDT 2015
	 */
	public void setSkipCount(Integer skipCount) {
		this.skipCount = skipCount;
	}
}