package edu.ku.cete.web;

import java.util.List;

import edu.ku.cete.domain.BluePrintCriteriaDescription;
import edu.ku.cete.domain.test.EEConceptualAndClaimDetailsDTO;

public class BlueprintCriteriaAndEEDTO extends BluePrintCriteriaDescription {
	private List<EEConceptualAndClaimDetailsDTO> ees;
	
	public List<EEConceptualAndClaimDetailsDTO> getEes() {
		return ees;
	}

	public void setEes(List<EEConceptualAndClaimDetailsDTO> ees) {
		this.ees = ees;
	}
	
	public String toString() {
		String str = String.valueOf(getCriteria()) + " - ";
		for (int x = 0; x < ees.size(); x++) {
			str += (x > 0 ? ", " : "") + "\"" + ees.get(x).getEeConetnetCode() + "\"";
		}
		return str;
	}
}