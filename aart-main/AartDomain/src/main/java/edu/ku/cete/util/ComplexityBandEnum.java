package edu.ku.cete.util;

import org.apache.commons.lang3.StringUtils;

public enum ComplexityBandEnum {
	INITIAL_PRECURSOR("Initial Precursor", 227, "FOUNDATIONAL", "0", "ELA, M"),
	DISTAL_PRECURSOR("Distal Precursor", 228, "BAND_1", "1", "ELA, M"),
	PROXIMAL_PRECURSOR("Proximal Precursor", 229, "BAND_2", "2", "ELA, M"),
	TARGET("Target", 230, "BAND_3", "3", "ELA, M"),
	SUCCESSOR("Successor", 231, "BAND_4", "4", "ELA, M"),
	SCI_INITIAL_PRECURSOR("Initial Precursor", 232, "FOUNDATIONAL", "0", "Sci"),
	SCI_DISTAL_PRECURSOR("Initial Precursor", 233, "BAND_1", "1", "Sci"),
	SCI_PROXIMAL_PRECURSOR("Proximal Precursor", 234, "BAND_2", "2", "Sci"),
	SCI_TARGET("Target", 235, "BAND_3", "3", "Sci"),;
	
	private String linkageLevel;
	private long bandId;
	private String bandName;
	private String bandCode;
	private String contentAreaAbbrName;
	
	ComplexityBandEnum(String linkageLevel, long bandId, String bandName, String bandCode, String contentAreaAbbrName) {
		this.linkageLevel = linkageLevel;
		this.bandId = bandId;
		this.bandName = bandName;
		this.bandCode = bandCode;
		this.contentAreaAbbrName = contentAreaAbbrName;
	}
		
	public String getLinkageLevel() {
		return linkageLevel;
	}
	public void setLinkageLevel(String linkageLevel) {
		this.linkageLevel = linkageLevel;
	}
	public long getBandId() {
		return bandId;
	}
	public void setBandId(long bandId) {
		this.bandId = bandId;
	}
	public String getBandName() {
		return bandName;
	}
	public void setBandName(String bandName) {
		this.bandName = bandName;
	}
	public String getBandCode() {
		return bandCode;
	}
	public void setBandCode(String bandCode) {
		this.bandCode = bandCode;
	}	
	public String getContentAreaAbbrName() {
		return contentAreaAbbrName;
	}
	public void setContentAreaAbbrName(String contentAreaAbbrName) {
		this.contentAreaAbbrName = contentAreaAbbrName;
	}

	public static ComplexityBandEnum getByLinkageLevel(String linkageLevel, String contentAreaAbbrName) {
		if(linkageLevel != null) {
			for(ComplexityBandEnum rule : ComplexityBandEnum.values()) {
				if(rule.getLinkageLevel().equalsIgnoreCase(linkageLevel) && isContentAreaAbbrNameMatched(rule, contentAreaAbbrName)) {
					return rule;
				}
			}
		}
		return null;
	}
	
	public static ComplexityBandEnum getByBandCode(String bandCode, String contentAreaAbbrName) {
		if(bandCode != null && contentAreaAbbrName != null) {
			for(ComplexityBandEnum rule : ComplexityBandEnum.values()) {
				if(rule.getBandCode().equals(bandCode) && isContentAreaAbbrNameMatched(rule, contentAreaAbbrName)) {					
					return rule;
				}
			}
		}
		return null;
	}
	
	private static boolean isContentAreaAbbrNameMatched(ComplexityBandEnum rule, String contentAreaAbbrName) {
		return StringUtils.equalsIgnoreCase(rule.getContentAreaAbbrName(),contentAreaAbbrName)
				|| StringUtils.containsIgnoreCase(rule.getContentAreaAbbrName(), contentAreaAbbrName);
	}
	
}
