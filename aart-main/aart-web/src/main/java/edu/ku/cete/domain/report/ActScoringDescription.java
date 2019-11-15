package edu.ku.cete.domain.report;

import edu.ku.cete.domain.audit.AuditableDomain;

public class ActScoringDescription extends AuditableDomain{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String description;
	
	private Long levelId;
	
	private Long actDescriptionId;
		
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Integer maxvalue;
	
	private Integer minvalue;

	public Integer getMaxvalue() {
		return maxvalue;
	}

	public void setMaxvalue(Integer maxvalue) {
		this.maxvalue = maxvalue;
	}

	public Integer getMinvalue() {
		return minvalue;
	}

	public void setMinvalue(Integer minvalue) {
		this.minvalue = minvalue;
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
