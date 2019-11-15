package edu.ku.cete.report.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class StudentPrctTopicReportsDTO {
	private Long id;
	private String stateStudentIdentifier;
	private String legalFirstName;
	private String legalLastName;
	private Long scalescore;
	private String achievementlevel;
	private String percents;
	private List<String> percentsCorrect = new ArrayList<String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getLegalLastName() {
		return legalLastName;
	}

	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}

	public Long getScalescore() {
		return scalescore;
	}

	public void setScalescore(Long scalescore) {
		this.scalescore = scalescore;
	}

	public String getAchievementlevel() {
		return achievementlevel;
	}

	public void setAchievementlevel(String achievementlevel) {
		this.achievementlevel = achievementlevel;
	}

	public String getPercents() {
		return percents;
	}

	public void setPercents(String percents) {
		this.percents = percents;
		if(this.percents != null && this.percents.length() > 0)
		   percentsCorrect = Arrays.asList(percents.split(","));
	}

	public List<String> getPercentsCorrect() {					
		return percentsCorrect;		
	}

	public void setPercentsCorrect(List<String> percentsCorrecr) {
		this.percentsCorrect = percentsCorrecr;
	}
}
