package edu.ku.cete.report.iap;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.web.IAPContentFramework;

public class ConceptualAreaContextData {
	private Long conceptualAreaId;
	private String conceptualAreaContentCode;
	private String conceptualAreaDescription;
	
	private List<IAPContentFramework> ees;
	
	public ConceptualAreaContextData() {
		this.ees = new ArrayList<IAPContentFramework>(0);
	}
	
	@Override
	public String toString() {
		return getConceptualAreaContentCode() + " " + getConceptualAreaDescription() + " (" + (getNumberOfEEs()) + " EEs)";
	}
	
	public boolean equals(ConceptualAreaContextData other) {
		if (this.conceptualAreaId == null && other.conceptualAreaId != null) return false;
		if (this.conceptualAreaId != null && other.conceptualAreaId == null) return false;
		return this.conceptualAreaId.compareTo(other.conceptualAreaId) == 0;
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
	public List<IAPContentFramework> getEes() {
		return ees;
	}
	public void setEes(List<IAPContentFramework> ees) {
		this.ees = ees;
	}
	public void addEE(IAPContentFramework toAdd) {
		if (this.ees == null) {
			this.ees = new ArrayList<IAPContentFramework>();
		}
		if (toAdd != null) {
			this.ees.add(toAdd);
		}
	}
	public int getNumberOfEEs() {
		return (this.ees == null ? 0 : this.ees.size());
	}
}
