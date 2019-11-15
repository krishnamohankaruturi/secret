package edu.ku.cete.domain.report;

import java.io.Serializable;

import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.util.ParsingConstants;

public class TestCutScoresRecord extends ValidateableRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1327020548567006775L;
	
	private TestCutScores testCutScores = new TestCutScores();

	public TestCutScores getTestCutScores() {
		return testCutScores;
	}

	public void setTestCutScores(TestCutScores testCutScores) {
		this.testCutScores = testCutScores;
	}
	
	public Long getTestId() {
		return testCutScores.getTestId();
	}

	public void setTestId(Long testId) {
		testCutScores.setTestId(testId);
	}
	
	public String getAchievementLevelName() {
		return testCutScores.getAchievementLevelName();
	}

	public void setAchievementLevelName(String achievementLevelName) {
		testCutScores.setAchievementLevelName(achievementLevelName);
	}
    
	public String getAchievementLevelLabel() {
		return testCutScores.getAchievementLevelLabel();
	}


	public void setAchievementLevelLabel(String achievementLevelLabel) {
		testCutScores.setAchievementLevelLabel(achievementLevelLabel);
	}


	public String getAchievementLevelDescription() {
		return testCutScores.getAchievementLevelDescription();
	}


	public void setAchievementLevelDescription(String achievementLevelDescription) {
		testCutScores.setAchievementLevelDescription(achievementLevelDescription);
	}


	public String getLowerLevelCutScore() {
		return testCutScores.getLowerLevelCutScore();
	}


	public void setLowerLevelCutScore(String lowerLevelCutScore) {
		testCutScores.setLowerLevelCutScore(lowerLevelCutScore);
	}


	public String getUpperLevelCutScore() {
		return testCutScores.getUpperLevelCutScore();
	}


	public void setUpperLevelCutScore(String upperLevelCutScore) {
		testCutScores.setUpperLevelCutScore(upperLevelCutScore);
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getId(int order) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStringIdentifier(int order) {
		// TODO Auto-generated method stub
		return null;
	}

	public Student getStudent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIdentifier() {
		return this.testCutScores.getTestId() + ParsingConstants.BLANK;
	}


}
