package edu.ku.cete.ksde.rosters.result;

import java.util.ArrayList;
import java.util.List;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;


public class RosterData {

	/**
	 * Roster.
	 */
	private List<WebServiceRosterRecord> rosterRecords = new ArrayList<WebServiceRosterRecord>();

	public List<WebServiceRosterRecord> getRosterRecords() {
		return rosterRecords;
	}

	public void setRosterRecords(List<WebServiceRosterRecord> rosterRecords) {
		this.rosterRecords = rosterRecords;
	}

	public void addRoster(WebServiceRosterRecord rosterRecord) {
		rosterRecords.add(rosterRecord);
	}
}
