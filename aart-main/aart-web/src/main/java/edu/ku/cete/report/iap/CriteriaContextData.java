package edu.ku.cete.report.iap;

import java.util.ArrayList;
import java.util.List;

public class CriteriaContextData {
	private Integer criteria;
	private String criteriaText;
	
	private Boolean metCriteria;
	
	private List<ClaimContextData> claims;
	
	public CriteriaContextData() {
		this.claims = new ArrayList<ClaimContextData>(0);
	}
	
	@Override
	public String toString() {
		return getCriteria() + " - " + getCriteriaText() +
				" (" +
				getNumberOfClaims() + " claims, " +
				getNumberOfConceptualAreas() + " conceptual areas, " +
				getNumberOfEEs() + " EEs)";
	}
	
	public boolean equals(CriteriaContextData other) {
		if (this.criteria == null && other.criteria != null) return false;
		if (this.criteria != null && other.criteria == null) return false;
		return this.criteria.compareTo(other.criteria) == 0;
	}
	
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
	public Boolean getMetCriteria() {
		return metCriteria;
	}

	public void setMetCriteria(Boolean metCriteria) {
		this.metCriteria = metCriteria;
	}
	public List<ClaimContextData> getClaims() {
		return claims;
	}
	public void setClaims(List<ClaimContextData> claims) {
		this.claims = claims;
	}
	public void addClaim(ClaimContextData toAdd) {
		if (this.claims == null) {
			this.claims = new ArrayList<ClaimContextData>();
		}
		if (toAdd != null) {
			claims.add(toAdd);
		}
	}
	public int getNumberOfClaims() {
		return (this.claims == null ? 0 : this.claims.size());
	}
	public int getNumberOfConceptualAreas() {
		int ret = 0;
		if (this.claims != null && !this.claims.isEmpty()) {
			for (ClaimContextData claim : this.claims) {
				ret += claim.getNumberOfConceptualAreas();
			}
		}
		return ret;
	}
	public int getNumberOfEEs() {
		int ret = 0;
		if (this.claims != null && !this.claims.isEmpty()) {
			for (ClaimContextData claim : this.claims) {
				if (claim.getNumberOfConceptualAreas() > 0) {
					for (ConceptualAreaContextData conceptualArea : claim.getConceptualAreas()) {
						ret += conceptualArea.getNumberOfEEs();
					}
				}
			}
		}
		return ret;
	}
}
