package edu.ku.cete.domain.report.student;

import java.util.List;

public class StudentRptEssentialElement {
	 private String eeCode;
	 private String eeDesc;
	 private List<StudentRptLinkageLevel> levelDetails;
	public String getEeCode() {
		return eeCode;
	}
	public void setEeCode(String eeCode) {
		this.eeCode = eeCode;
	}
	public String getEeDesc() {
		return eeDesc;
	}
	public void setEeDesc(String eeDesc) {
		this.eeDesc = eeDesc;
	}
	public List<StudentRptLinkageLevel> getLevelDetails() {
		return levelDetails;
	}
	public void setLevelDetails(List<StudentRptLinkageLevel> levelDetails) {
		this.levelDetails = levelDetails;
	}
	 
	 
}