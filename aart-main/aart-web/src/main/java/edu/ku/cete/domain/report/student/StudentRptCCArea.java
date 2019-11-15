package edu.ku.cete.domain.report.student;

import java.util.List;

public class StudentRptCCArea {
	private String claim;
	private String conceptualArea;
	private String targetLogo; 
	
	private List<StudentRptEssentialElement> eEList;
	public String getClaim() {
		return claim;
	}
	public void setClaim(String claim) {
		this.claim = claim;
	}
	public String getConceptualArea() {
		return conceptualArea;
	}
	public void setConceptualArea(String conceptualArea) {
		this.conceptualArea = conceptualArea;
	}
	public List<StudentRptEssentialElement> geteEList() {
		return eEList;
	}
	public void seteEList(List<StudentRptEssentialElement> eEList) {
		this.eEList = eEList;
	}
	public String getTargetLogo() {
		return targetLogo;
	}
	public void setTargetLogo(String targetLogo) {
		this.targetLogo = targetLogo;
	}
	
	 
}