package edu.ku.cete.web;

public class ScorersAssignScorerDTO {
	
	private Long scorerid;
	private String scorerLastName;
	private String MI;
	private String scorerFirstName;
	private String email;
	private String districtName;
	private String schoolName;
	private String testingProgramName;
	private Long groupId;
	private String groupCode;
	private Integer limitCount;
	
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	public Long getScorerid() {
		return scorerid;
	}
	public void setScorerid(Long scorerid) {
		this.scorerid = scorerid;
	}
	public String getMI() {
		return MI;
	}
	public void setMI(String mI) {
		MI = mI;
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
	public String getTestingProgramName() {
		return testingProgramName;
	}
	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getLimitCount() {
		return limitCount;
	}
	public void setLimitCount(Integer limitCount) {
		this.limitCount = limitCount;
	}
	/**
	 * @return the scorerLastName
	 */
	public String getScorerLastName() {
		return scorerLastName;
	}
	/**
	 * @param scorerLastName the scorerLastName to set
	 */
	public void setScorerLastName(String scorerLastName) {
		this.scorerLastName = scorerLastName;
	}
	/**
	 * @return the scorerFirstName
	 */
	public String getScorerFirstName() {
		return scorerFirstName;
	}
	/**
	 * @param scorerFirstName the scorerFirstName to set
	 */
	public void setScorerFirstName(String scorerFirstName) {
		this.scorerFirstName = scorerFirstName;
	}
	
	

}
