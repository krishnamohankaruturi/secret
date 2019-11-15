/**
 * 
 */
package edu.ku.cete.ksde.tasc.result;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.enrollment.TASCRecord;

/**
 * @author ktaduru_sta
 *
 */
public class TASCData {
	private List<TASCRecord> rosterRecords = new ArrayList<TASCRecord>();

	public List<TASCRecord> getRosterRecords() {
		return rosterRecords;
	}

	public void setRosterRecords(List<TASCRecord> rosterRecords) {
		this.rosterRecords = rosterRecords;
	}
	
	public void addRecord(TASCRecord rosterRecord) {
		rosterRecords.add(rosterRecord);
	}
}
