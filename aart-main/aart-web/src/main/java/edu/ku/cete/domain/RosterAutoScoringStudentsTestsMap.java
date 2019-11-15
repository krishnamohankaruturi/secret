package edu.ku.cete.domain;

import java.util.List;

import edu.ku.cete.model.AutoScoringStudentstest;

/* sudhansu.b
 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
 */	
public class RosterAutoScoringStudentsTestsMap {
	private Long rosterId;

	private List<AutoScoringStudentstest> autoScoringStudentstests;

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	public List<AutoScoringStudentstest> getAutoScoringStudentstests() {
		return autoScoringStudentstests;
	}

	public void setAutoScoringStudentstests(
			List<AutoScoringStudentstest> autoScoringStudentstests) {
		this.autoScoringStudentstests = autoScoringStudentstests;
	}

}
