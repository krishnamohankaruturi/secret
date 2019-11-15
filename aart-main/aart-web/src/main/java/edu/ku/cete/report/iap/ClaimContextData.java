package edu.ku.cete.report.iap;

import java.util.ArrayList;
import java.util.List;

public class ClaimContextData {
	private Long claimId;
	private String claimContentCode;
	private String claimDescription;
	
	private List<ConceptualAreaContextData> conceptualAreas;
	
	public ClaimContextData() {
		this.conceptualAreas = new ArrayList<ConceptualAreaContextData>(0);
	}
	
	@Override
	public String toString() {
		return getClaimContentCode() + " " + getClaimDescription() + " (" + getNumberOfConceptualAreas() + " conceptual areas)";
	}
	
	public boolean equals(ClaimContextData other) {
		if (this.claimId == null && other.claimId != null) return false;
		if (this.claimId != null && other.claimId == null) return false;
		return this.claimId.compareTo(other.claimId) == 0;
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
	public List<ConceptualAreaContextData> getConceptualAreas() {
		return conceptualAreas;
	}
	public void setConceptualAreas(List<ConceptualAreaContextData> conceptualAreas) {
		this.conceptualAreas = conceptualAreas;
	}
	public void addConceptualArea(ConceptualAreaContextData toAdd) {
		if (this.conceptualAreas == null) {
			this.conceptualAreas = new ArrayList<ConceptualAreaContextData>();
		}
		if (toAdd != null) {
			conceptualAreas.add(toAdd);
		}
	}
	public int getNumberOfConceptualAreas() {
		return (this.conceptualAreas == null ? 0 : this.conceptualAreas.size());
	}
}
