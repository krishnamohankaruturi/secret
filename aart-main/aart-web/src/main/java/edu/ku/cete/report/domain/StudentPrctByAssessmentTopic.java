package edu.ku.cete.report.domain;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class StudentPrctByAssessmentTopic extends ExternalUploadResult {
	/**
	 * 
	 */
private static final long serialVersionUID = -5801467499096358735L;
private String stateStudentIdentifier;
private Long studentId;
private String topicCode;
private Long topicId;
private Float percentCorrect;
/**
 * @return the stateStudentIdentifier
 */
public String getStateStudentIdentifier() {
	return stateStudentIdentifier;
}
/**
 * @param stateStudentIdentifier the stateStudentIdentifier to set
 */
public void setStateStudentIdentifier(String stateStudentIdentifier) {
	this.stateStudentIdentifier = stateStudentIdentifier;
}
/**
 * @return the studentId
 */
public Long getStudentId() {
	return studentId;
}
/**
 * @param studentId the studentId to set
 */
public void setStudentId(Long studentId) {
	this.studentId = studentId;
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
 * @return the topicId
 */
public Long getTopicId() {
	return topicId;
}
/**
 * @param topicId the topicId to set
 */
public void setTopicId(Long topicId) {
	this.topicId = topicId;
}
/**
 * @return the percentCorrect
 */
public Float getPercentCorrect() {
	return percentCorrect;
}
/**
 * @param percentCorrect the percentCorrect to set
 */
public void setPercentCorrect(Float percentCorrect) {
	this.percentCorrect = percentCorrect;
}

}
