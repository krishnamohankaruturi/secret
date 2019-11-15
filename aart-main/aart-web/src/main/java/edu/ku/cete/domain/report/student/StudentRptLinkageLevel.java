package edu.ku.cete.domain.report.student;

public class StudentRptLinkageLevel {
	private String linkageLevel;
	private String sessionLevel;
    private String administered;
    private String desc;
    private double percentCorrectScoreOfScoreableItems;
    private boolean writingType;
    private String testStatus;
    
    public String getLinkageLevel() {
		return linkageLevel;
	}
	public void setLinkageLevel(String linkageLevel) {
		this.linkageLevel = linkageLevel;
	}
	public String getSessionLevel() {
		return sessionLevel;
	}
	public void setSessionLevel(String sessionLevel) {
		this.sessionLevel = sessionLevel;
	}
	public String getAdministered() {
		return administered;
	}
	public void setAdministered(String administered) {
		this.administered = administered;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public boolean isWritingType() {
		return writingType;
	}
	public void setWritingType(boolean writingType) {
		this.writingType = writingType;
	}
	public double getPercentCorrectScoreOfScoreableItems() {
		return percentCorrectScoreOfScoreableItems;
	}
	public void setPercentCorrectScoreOfScoreableItems(double percentCorrectScoreOfScoreableItems) {
		this.percentCorrectScoreOfScoreableItems = percentCorrectScoreOfScoreableItems;
	}
	public String getTestStatus() {
		return testStatus;
	}
	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	} 
	
	
}