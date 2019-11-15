package edu.ku.cete.domain;

import java.util.List;


public class ItiStudentReport {
    private Long stateID;
    private String stateName;
    private String poolType;
    
    private String claimConceptualArea;
    private String claim;
    private String conceptualArea;
    private List<ItiStudentReportEE> itiStudentReportEE;
    
	public String getClaimConceptualArea() {
		return claimConceptualArea;
	}
	public void setClaimConceptualArea(String claimConceptualArea) {
		this.claimConceptualArea = claimConceptualArea;
	}
	public List<ItiStudentReportEE> getItiStudentReportEE() {
		return itiStudentReportEE;
	}
	public void setItiStudentReportEE(List<ItiStudentReportEE> itiStudentReportEE) {
		this.itiStudentReportEE = itiStudentReportEE;
	}
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
	public Long getStateID() {
		return stateID;
	}
	public void setStateID(Long stateID) {
		this.stateID = stateID;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getPoolType() {
		return poolType;
	}
	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}
    
	 
}