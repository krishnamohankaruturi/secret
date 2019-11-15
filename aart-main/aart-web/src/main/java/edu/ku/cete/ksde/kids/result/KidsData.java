package edu.ku.cete.ksde.kids.result;

import java.util.ArrayList;
import java.util.List;

public class KidsData {
	
	/**
	 * kids.
	 */
	private List<KidRecord> kids = new ArrayList<KidRecord>();

	/**
	 * @return the kids
	 */
	public List<KidRecord> getKids() {
		return kids;
	}

	/**
	 * @param kids the kids to set
	 */
	public void setKids(List<KidRecord> kids) {
		this.kids = kids;
	}

	public void addKid(KidRecord kidRecord) {
		kids.add(kidRecord);
	}

}
