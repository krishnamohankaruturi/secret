package edu.ku.cete.web;

public class ScoreTestSelfToHarmDTO {

	private String legalFirstName;
	private String districtName;
	private String schoolName;
	private String name;
	private String testName;
	private Long item;
	private String scorer;
	private Integer totalRecords;
	private String gridRowId;
	
	public String getLegalFirstName() {
		return legalFirstName;
	}
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Long getItem() {
		return item;
	}
	public void setItem(Long item) {
		this.item = item;
	}
	public String getScorer() {
		return scorer;
	}
	public void setScorer(String scorer) {
		this.scorer = scorer;
	}
	public Integer getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getGridRowId() {
		return gridRowId;
	}
	public void setGridRowId(String gridRowId) {
		this.gridRowId = gridRowId;
	}
	
	
}
