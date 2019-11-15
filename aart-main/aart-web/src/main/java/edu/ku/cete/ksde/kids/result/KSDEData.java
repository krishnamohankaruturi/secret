package edu.ku.cete.ksde.kids.result;

import java.util.ArrayList;
import java.util.List;

public class KSDEData {
	
	/**
	 * kids.
	 */
	private List<KSDERecord> kids = new ArrayList<KSDERecord>();

	/**
	 * @return the kids
	 */
	public List<KSDERecord> getKids() {
		return kids;
	}

	/**
	 * @param kids the kids to set
	 */
	public void setKids(List<KSDERecord> kids) {
		this.kids = kids;
	}

	public void addKid(KSDERecord KSDERecord) {
		kids.add(KSDERecord);
	}

}
