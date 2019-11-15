package edu.ku.cete.web;

public class MonitorScorerStudentRemainderDTO {
	private Long scorerId;
	private Long studentId;
	private String type;
	private Long scoringAssignmentScorerId;
	private Long scoringAssignmentStudentId;
	
	public Long getScoringAssignmentScorerId() {
		return scoringAssignmentScorerId;
	}
	public void setScoringAssignmentScorerId(Long scoringAssignmentScorerId) {
		this.scoringAssignmentScorerId = scoringAssignmentScorerId;
	}
	public Long getScoringAssignmentStudentId() {
		return scoringAssignmentStudentId;
	}
	public void setScoringAssignmentStudentId(Long scoringAssignmentStudentId) {
		this.scoringAssignmentStudentId = scoringAssignmentStudentId;
	}
	public Long getScorerId() {
		return scorerId;
	}
	public void setScorerId(Long scorerId) {
		this.scorerId = scorerId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
