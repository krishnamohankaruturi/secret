package edu.ku.cete.report.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class OrganizationPrctTopicReportsDTO {
	private Long id;
	private String typeCode;
	private Long scalescore;
	private String percents;
	private List<String> percentsCorrect = new ArrayList<String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getScalescore() {
		return scalescore;
	}

	public void setScalescore(Long scalescore) {
		this.scalescore = scalescore;
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

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}
