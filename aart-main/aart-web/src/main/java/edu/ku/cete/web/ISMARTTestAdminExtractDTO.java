/**
 * 
 */
package edu.ku.cete.web;

import java.io.Serializable;

/**
 * @author Kiran Reddy Taduru
 * May 31, 2018 12:30:35 PM
 */
public class ISMARTTestAdminExtractDTO extends DLMTestStatusExtractDTO implements Serializable{
	
	private static final long serialVersionUID = -5500532097837875193L;
	
	private int pilotTestsNotStarted;
	private int pilotTestsInProgress;
	private int pilotTestsCompleted;
	private int pilotTestsRequired;
	
	public int getPilotTestsNotStarted() {
		return pilotTestsNotStarted;
	}
	public void setPilotTestsNotStarted(int pilotTestsNotStarted) {
		this.pilotTestsNotStarted = pilotTestsNotStarted;
	}
	public int getPilotTestsInProgress() {
		return pilotTestsInProgress;
	}
	public void setPilotTestsInProgress(int pilotTestsInProgress) {
		this.pilotTestsInProgress = pilotTestsInProgress;
	}
	public int getPilotTestsCompleted() {
		return pilotTestsCompleted;
	}
	public void setPilotTestsCompleted(int pilotTestsCompleted) {
		this.pilotTestsCompleted = pilotTestsCompleted;
	}
	public int getPilotTestsRequired() {
		return pilotTestsRequired;
	}
	public void setPilotTestsRequired(int pilotTestsRequired) {
		this.pilotTestsRequired = pilotTestsRequired;
	}
	
	
}
