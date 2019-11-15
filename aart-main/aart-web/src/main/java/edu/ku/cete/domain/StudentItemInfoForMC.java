package edu.ku.cete.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentItemInfoForMC {
  private long studentId;
  private long testId;
  private long testletId;
  private String linkageLevel;
  private long taskVariantId;
  private String response;
  private double score;
  private String taskName;
  private long maxScore;
  private String taskTypeCode;
  private String gradeCourseName;
  private String nodeCode;
  private String correctResponse;
  private String taskSubType;
  private String responseDateTime;
public long getStudentId() {
	return studentId;
}
public void setStudentId(long studentId) {
	this.studentId = studentId;
}
public long getTestId() {
	return testId;
}
public void setTestId(long testId) {
	this.testId = testId;
}

public long getTestletId() {
	return testletId;
}
public void setTestletId(long testletId) {
	this.testletId = testletId;
}
public String getLinkageLevel() {
	return linkageLevel;
}
public void setLinkageLevel(String linkageLevel) {
	this.linkageLevel = linkageLevel;
}
public long getTaskVariantId() {
	return taskVariantId;
}
public void setTaskVariantId(long taskVariantId) {
	this.taskVariantId = taskVariantId;
}
public String getResponse() {
	return response;
}
public void setResponse(String response) {
	this.response = response;
}
public double getScore() {
	return score;
}
public void setScore(double score) {
	this.score = score;
}
public String getTaskName() {
	return taskName;
}
public void setTaskName(String taskName) {
	this.taskName = taskName;
}
public long getMaxScore() {
	return maxScore;
}
public void setMaxScore(long maxScore) {
	this.maxScore = maxScore;
}
public String getTaskTypeCode() {
	return taskTypeCode;
}
public void setTaskTypeCode(String taskTypeCode) {
	this.taskTypeCode = taskTypeCode;
}
public String getGradeCourseName() {
	return gradeCourseName;
}
public void setGradeCourseName(String gradeCourseName) {
	this.gradeCourseName = gradeCourseName;
}
public String getNodeCode() {
	return nodeCode;
}
public void setNodeCode(String nodeCode) {
	this.nodeCode = nodeCode;
}
public String getCorrectResponse() {
	return correctResponse;
}
public void setCorrectResponse(String correctResponse) {
	this.correctResponse = correctResponse;
}
public String getTaskSubType() {
	return taskSubType;
}
public void setTaskSubType(String taskSubType) {
	this.taskSubType = taskSubType;
}
public String getResponseDateTime() {
	return responseDateTime;
}
public void setResponseDateTime(Date responseDateTime) {
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
	this.responseDateTime = dateFormat.format(responseDateTime);
}
  	 
	 
}
