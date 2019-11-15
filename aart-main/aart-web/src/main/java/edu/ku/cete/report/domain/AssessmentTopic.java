package edu.ku.cete.report.domain;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;

public class AssessmentTopic extends AuditableDomain implements Identifiable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3414220927396351286L;
	private Long id;
	private String topicName;
	private Long schoolYear;
	private String testType;
	private Long gradeId;
	private Long contentareaId;
	private String grade;
	private String subject;
	private String topicCode;
	private Integer totalNoOfItems;
	private int topicPercentage;
	private String lineNumber;
	private String assessmentName;
	private long batchUploadId;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the topicName
	 */
	public String getTopicName() {
		return topicName;
	}
	/**
	 * @param topicName the topicName to set
	 */
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	/**
	 * @return the schoolYear
	 */
	public Long getSchoolYear() {
		return schoolYear;
	}
	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	/**
	 * @return the assessmentCode
	 */
	public String getTestType() {
		return testType;
	}
	/**
	 * @param assessmentCode the assessmentCode to set
	 */
	public void setTestType(String testType) {
		this.testType = testType;
	}
	/**
	 * @return the gradeId
	 */
	public Long getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	/**
	 * @return the contentareaId
	 */
	public Long getContentareaId() {
		return contentareaId;
	}
	/**
	 * @param contentareaId the contentareaId to set
	 */
	public void setContentareaId(Long contentareaId) {
		this.contentareaId = contentareaId;
	}
	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}
	/**
	 * @param grade the grade to set
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the topicCode
	 */
	public String getTopicCode() {
		return topicCode;
	}
	/**
	 * @param topicCode the topicCode to set
	 */
	public void setTopicCode(String topicCode) {
		this.topicCode = topicCode;
	}
	/**
	 * @return the topicPercentage
	 */
	public int getTopicPercentage() {
		return topicPercentage;
	}
	/**
	 * @param topicPercentage the topicPercentage to set
	 */
	public void setTopicPercentage(int topicPercentage) {
		this.topicPercentage = topicPercentage;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getAssessmentName() {
		return assessmentName;
	}
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}
	public long getBatchUploadId() {
		return batchUploadId;
	}
	public void setBatchUploadId(long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}
	@Override
	public Long getId(int order) {
		// TODO Auto-generated method stub
		return getId();
	}
	@Override
	public String getStringIdentifier(int order) {
		// TODO Auto-generated method stub
		return null;
	}
	public Integer getTotalNoOfItems() {
		return totalNoOfItems;
	}
	public void setTotalNoOfItems(Integer totalNoOfItems) {
		this.totalNoOfItems = totalNoOfItems;
	}

}



