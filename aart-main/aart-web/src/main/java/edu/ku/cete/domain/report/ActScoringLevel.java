package edu.ku.cete.domain.report;

import java.util.ArrayList;
import java.util.List;

public class ActScoringLevel {
	
	private Long id;
		
	private Long levelId;
	
	private Long actDescriptionId;
	
	private List<ActScoringDescription> actScoringDescriptions = new ArrayList<ActScoringDescription>();

	public List<ActScoringDescription> getActScoringDescriptions() {
		return actScoringDescriptions;
	}

	public void setActScoringDescriptions(List<ActScoringDescription> actScoringDescriptions) {
		this.actScoringDescriptions = actScoringDescriptions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}

	public Long getActDescriptionId() {
		return actDescriptionId;
	}

	public void setActDescriptionId(Long actDescriptionId) {
		this.actDescriptionId = actDescriptionId;
	}
}
