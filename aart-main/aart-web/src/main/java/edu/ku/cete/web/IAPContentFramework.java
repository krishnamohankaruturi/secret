package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;

/*
 * This class (during initial implementation) is generally for Claim -> Conceptual Area -> Essential Element
 */
public class IAPContentFramework {
	
	private Integer criteria;
	private String criteriaText;
	
	private Long claimId;
	private String claimContentCode;
	private String claimDescription;
	
	private Long conceptualAreaId;
	private String conceptualAreaContentCode;
	private String conceptualAreaDescription;
	
	private Long eeId;
	private Boolean isWritingTestlet;
	private ContentFrameworkDetail ee;
	
	private List<MicroMapResponseDTO> linkageLevels;
	
	private List<ItiTestSessionHistory> itiEntries;

	public Integer getCriteria() {
		return criteria;
	}

	public void setCriteria(Integer criteria) {
		this.criteria = criteria;
	}

	public String getCriteriaText() {
		return criteriaText;
	}

	public void setCriteriaText(String criteriaText) {
		this.criteriaText = criteriaText;
	}

	public Long getClaimId() {
		return claimId;
	}

	public void setClaimId(Long claimId) {
		this.claimId = claimId;
	}

	public String getClaimContentCode() {
		return claimContentCode;
	}

	public void setClaimContentCode(String claimContentCode) {
		this.claimContentCode = claimContentCode;
	}

	public String getClaimDescription() {
		return claimDescription;
	}

	public void setClaimDescription(String claimDescription) {
		this.claimDescription = claimDescription;
	}

	public Long getConceptualAreaId() {
		return conceptualAreaId;
	}

	public void setConceptualAreaId(Long conceptualAreaId) {
		this.conceptualAreaId = conceptualAreaId;
	}

	public String getConceptualAreaContentCode() {
		return conceptualAreaContentCode;
	}

	public void setConceptualAreaContentCode(String conceptualAreaContentCode) {
		this.conceptualAreaContentCode = conceptualAreaContentCode;
	}

	public String getConceptualAreaDescription() {
		return conceptualAreaDescription;
	}

	public void setConceptualAreaDescription(String conceptualAreaDescription) {
		this.conceptualAreaDescription = conceptualAreaDescription;
	}

	public Long getEeId() {
		return eeId;
	}

	public void setEeId(Long eeId) {
		this.eeId = eeId;
	}

	public ContentFrameworkDetail getEE() {
		return ee;
	}

	public void setEE(ContentFrameworkDetail ee) {
		this.ee = ee;
	}

	public Boolean getIsWritingTestlet() {
		return isWritingTestlet;
	}

	public void setIsWritingTestlet(Boolean isWritingTestlet) {
		this.isWritingTestlet = isWritingTestlet;
	}

	public List<MicroMapResponseDTO> getLinkageLevels() {
		return linkageLevels;
	}

	public void setLinkageLevels(List<MicroMapResponseDTO> linkageLevels) {
		this.linkageLevels = linkageLevels;
	}

	public List<ItiTestSessionHistory> getItiEntries() {
		return itiEntries;
	}

	public void setItiEntries(List<ItiTestSessionHistory> itiEntries) {
		this.itiEntries = itiEntries;
	}
	
	public void addItiEntry(ItiTestSessionHistory iti) {
		if (this.itiEntries == null) this.itiEntries = new ArrayList<ItiTestSessionHistory>();
		this.itiEntries.add(iti);
	}
	
}
