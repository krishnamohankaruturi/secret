package edu.ku.cete.web;

public class AmpExtractStudentSujectSectionItemCountDTO {

	private Long studentId;
	private Long enrollmentId;
    private Long contentAreaId;
	private Long stageId;
    private Long totalCount;
    private String stageCode;
    private String gradeCourse;
    
    public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

	public String getGradeCourse() {
		return gradeCourse;
	}

	public void setGradeCourse(String gradeCourse) {
		this.gradeCourse = gradeCourse;
	}
	
}
