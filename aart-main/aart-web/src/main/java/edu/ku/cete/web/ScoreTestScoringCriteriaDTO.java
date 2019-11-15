package edu.ku.cete.web;

public class ScoreTestScoringCriteriaDTO {
	private long rubricCatergoryId;
	private String rubricCatName;
	private String rubricInfoDesc;
	private Float score;
	private String rubricType;
	private boolean lastRow;
	private Float rubricScoreWeight;
	
	public boolean isLastRow() {
		return lastRow;
	}
	public void setLastRow(boolean lastRow) {
		this.lastRow = lastRow;
	}
	public String getRubricType() {
		return rubricType;
	}
	public void setRubricType(String rubricType) {
		this.rubricType = rubricType;
	}
	public long getRubricCatergoryId() {
		return rubricCatergoryId;
	}
	public void setRubricCatergoryId(long rubricCatergoryId) {
		this.rubricCatergoryId = rubricCatergoryId;
	}
	public String getRubricCatName() {
		return rubricCatName;
	}
	public void setRubricCatName(String rubricCatName) {
		this.rubricCatName = rubricCatName;
	}
	public String getRubricInfoDesc() {
		return rubricInfoDesc;
	}
	public void setRubricInfoDesc(String rubricInfoDesc) {
		this.rubricInfoDesc = rubricInfoDesc;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Float getRubricScoreWeight() {
		return rubricScoreWeight;
	}
	public void setRubricScoreWeight(Float rubricScoreWeight) {
		this.rubricScoreWeight = rubricScoreWeight;
	}	
}
