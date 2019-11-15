package edu.ku.cete.domain.report;

import java.io.Serializable;

import edu.ku.cete.domain.property.TraceableEntity;

public class TestCutScores extends TraceableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8257375515500086201L;
	
	private Long testId;
	private String achievementLevelLabel;
	private String achievementLevelName;
	private String achievementLevelDescription;
	private String lowerLevelCutScore;
	private String upperLevelCutScore;
	
	public Long getTestId() {
		return testId;
	}

	public void setTestId(Long testId) {
		this.testId = testId;
	}

	public String getAchievementLevelName() {
		return achievementLevelName;
	}

	public void setAchievementLevelName(String achievementLevelName) {
		this.achievementLevelName = achievementLevelName;
	}

	public String getAchievementLevelLabel() {
		return achievementLevelLabel;
	}


	public void setAchievementLevelLabel(String achievementLevelLabel) {
		this.achievementLevelLabel = achievementLevelLabel;
	}


	public String getAchievementLevelDescription() {
		return achievementLevelDescription;
	}


	public void setAchievementLevelDescription(String achievementLevelDescription) {
		this.achievementLevelDescription = achievementLevelDescription;
	}


	public String getLowerLevelCutScore() {
		return lowerLevelCutScore;
	}


	public void setLowerLevelCutScore(String lowerLevelCutScore) {
		this.lowerLevelCutScore = lowerLevelCutScore;
	}


	public String getUpperLevelCutScore() {
		return upperLevelCutScore;
	}


	public void setUpperLevelCutScore(String upperLevelCutScore) {
		this.upperLevelCutScore = upperLevelCutScore;
	}
		
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
