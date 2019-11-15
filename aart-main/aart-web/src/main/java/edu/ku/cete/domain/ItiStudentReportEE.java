package edu.ku.cete.domain;

import java.util.List;


public class ItiStudentReportEE {
   private String essentialElement;
   private long essentialElementId;
   private List<ItiTestSessionHistory> itiTestSessionHistory;
	public String getEssentialElement() {
		return essentialElement;
	}
	public void setEssentialElement(String essentialElement) {
		this.essentialElement = essentialElement;
	}
	public List<ItiTestSessionHistory> getItiTestSessionHistory() {
		return itiTestSessionHistory;
	}
	public void setItiTestSessionHistory(
			List<ItiTestSessionHistory> itiTestSessionHistory) {
		this.itiTestSessionHistory = itiTestSessionHistory;
	}
	public long getEssentialElementId() {
		return essentialElementId;
	}
	public void setEssentialElementId(long essentialElementId) {
		this.essentialElementId = essentialElementId;
	}
   	 
	
}