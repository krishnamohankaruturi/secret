package edu.ku.cete.domain.api.scoring;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClusterScoreObject {
	private Long contentFrameworkDetailId;
	private String contentFrameworkDetailName;
	private BigDecimal rawScoreEarned;
	private BigDecimal scaleScoreEarned;
	
	@JsonProperty("id")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	public Long getContentFrameworkDetailId() {
		return contentFrameworkDetailId;
	}
	
	public void setContentFrameworkDetailId(Long contentFrameworkDetailId) {
		this.contentFrameworkDetailId = contentFrameworkDetailId;
	}
	
	@JsonIgnore
	public String getContentFrameworkDetailName() {
		return contentFrameworkDetailName;
	}
	
	public void setContentFrameworkDetailName(String contentFrameworkDetailName) {
		this.contentFrameworkDetailName = contentFrameworkDetailName;
	}
	
	@JsonProperty("rawScore")
	public BigDecimal getRawScoreEarned() {
		return rawScoreEarned;
	}
	
	public void setRawScoreEarned(BigDecimal rawScoreEarned) {
		this.rawScoreEarned = rawScoreEarned;
	}
	
	@JsonProperty("scaledScore")
	public BigDecimal getScaleScoreEarned() {
		return scaleScoreEarned;
	}
	
	public void setScaleScoreEarned(BigDecimal scaleScoreEarned) {
		this.scaleScoreEarned = scaleScoreEarned;
	}
}
