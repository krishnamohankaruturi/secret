package edu.ku.cete.domain.report;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InterimStudentResponse {
	private String score;
	private String studentAnswer;
	private String correctAnswer;
	private String percentage;
	private String ItemType;
	private String ScoringType;
	private String pointValue;
	private String questionName;
	private String alignment;

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public String getStudentAnswer() {
		return studentAnswer;
	}

	public void setStudentAnswer(String studentAnswer) {
		this.studentAnswer = studentAnswer;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String itemType) {
		ItemType = itemType;
	}

	public String getScoringType() {
		return ScoringType;
	}

	public void setScoringType(String scoringType) {
		ScoringType = scoringType;
	}

	public String getPointValue() {
		return pointValue;
	}

	public void setPointValue(String pointValue) {
		this.pointValue = pointValue;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

}