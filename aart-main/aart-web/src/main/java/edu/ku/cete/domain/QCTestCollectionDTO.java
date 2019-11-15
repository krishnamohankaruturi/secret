package edu.ku.cete.domain;

public class QCTestCollectionDTO {
	private String id;
	private boolean qcComplete;
	private Long testCollectionId;
	private Long testId;
	private Long externalId;
	private String collectionName;
	private String name;
	private String testInternalName;
	private Long gradeCourseId;
	private String gradeCourseName;
	private String gradeCourseAbbreviatedName;
	private int gradeCourseGradeLevel;
	private Long contentAreaId;
	private String contentAreaName;
	private String assessmentProgramName;
	private String testingProgramName;
	private String assessmentName;	
	
	public Long getExternalId() {
		return externalId;
	}
	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isQcComplete() {
		return qcComplete;
	}
	public void setQcComplete(boolean qcComplete) {
		this.qcComplete = qcComplete;
	}
	public Long getTestCollectionId() {
		return testCollectionId;
	}
	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTestInternalName() {
		return testInternalName;
	}
	public void setTestInternalName(String testInternalName) {
		this.testInternalName = testInternalName;
	}
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public String getGradeCourseName() {
		return gradeCourseName;
	}
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}
	public String getGradeCourseAbbreviatedName() {
		return gradeCourseAbbreviatedName;
	}
	public void setGradeCourseAbbreviatedName(String gradeCourseAbbreviatedName) {
		this.gradeCourseAbbreviatedName = gradeCourseAbbreviatedName;
	}
	public int getGradeCourseGradeLevel() {
		return gradeCourseGradeLevel;
	}
	public void setGradeCourseGradeLevel(int gradeCourseGradeLevel) {
		this.gradeCourseGradeLevel = gradeCourseGradeLevel;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public String getContentAreaName() {
		return contentAreaName;
	}
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}
	public String getAssessmentProgramName() {
		return assessmentProgramName;
	}
	public void setAssessmentProgramName(String assessmentProgramName) {
		this.assessmentProgramName = assessmentProgramName;
	}
	public String getTestingProgramName() {
		return testingProgramName;
	}
	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}
	public String getAssessmentName() {
		return assessmentName;
	}
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}
	
	
}

	 
