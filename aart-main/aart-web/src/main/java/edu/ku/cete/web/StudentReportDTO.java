package edu.ku.cete.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.report.StudentReport;

public class StudentReportDTO extends StudentReport {
	private String stateStudentIdentifier;
	private String legalFirstName;
	private String legalMiddleName;
	private String legalLastName;
	
	private String writingResponse;
	private String writingResponseFO;
	
	private String districtName;
	private String schoolName;
	private String schoolIdentifier;
	private String stateName;
	
	private String gradeName;
	
	private Integer totalRecords;
	
	private Integer positionInTest;
	private BigDecimal taskScore;
	private Long nonScoreReasonId;
	
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getLegalFirstName() {
		return legalFirstName;
	}
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	public String getLegalMiddleName() {
		return legalMiddleName;
	}
	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}
	public String getLegalLastName() {
		return legalLastName;
	}
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	public String getWritingResponse() {
		return writingResponse;
	}
	public void setWritingResponse(String writingResponse) {
		this.writingResponse = writingResponse;
	}
	public String getWritingResponseFO() {
		return writingResponseFO;
	}
	public void setWritingResponseFO(String writingResponseFO) {
		this.writingResponseFO = writingResponseFO;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public List<String> buildAllStudentReportNameSearchJsonRow() {
		List<String> cells = new ArrayList<String>();
		cells.add("");//empty for the select box
		cells.add(this.getLegalLastName());
		cells.add(this.getLegalFirstName());
		cells.add(this.getLegalMiddleName());
		cells.add(this.getStateStudentIdentifier());
		cells.add(this.getGradeName());
		cells.add(this.getSchoolName());
		
		return cells;
	}
	
	public List<String> buildAllStudentReportSSIDSearchJsonRowForKAP() {
		List<String> cells = new ArrayList<String>();
		cells.add(""+this.getSchoolYear());
		cells.add(this.getSchoolName());
		cells.add(this.getContentAreaName());
		cells.add(this.getGradeName());
		if (this.getGenerated()) {
			cells.add( this.getScaleScore() == -10000 ? "" : ""+this.getScaleScore());
			cells.add( this.getLevel() == -10000 ? "" : ""+this.getLevel());
			cells.add("");//empty for report link
		} else {
			cells.add("N/A");
			cells.add("N/A");
			cells.add("N/A");
		}
		
		return cells;
	}
	
	public List<String> buildAllStudentReportSSIDSearchJsonRowForDLMOrCPASS() {
		List<String> cells = new ArrayList<String>();
		cells.add(""+this.getSchoolYear());
		cells.add(this.getSchoolName());
		cells.add(this.getContentAreaName());
		cells.add(this.getGradeName());
		cells.add("");//empty for report link
		
		return cells;
	}
	public Integer getPositionInTest() {
		return positionInTest;
	}
	public void setPositionInTest(Integer positionInTest) {
		this.positionInTest = positionInTest;
	}
	public BigDecimal getTaskScore() {
		return taskScore;
	}
	public void setTaskScore(BigDecimal taskScore) {
		this.taskScore = taskScore;
	}
	public Long getNonScoreReasonId() {
		return nonScoreReasonId;
	}
	public void setNonScoreReasonId(Long nonScoreReasonId) {
		this.nonScoreReasonId = nonScoreReasonId;
	}
}