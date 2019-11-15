package edu.ku.cete.domain.property;

import java.io.Serializable;

import edu.ku.cete.util.RecordSaveStatus;

/**
 * @author m802r921
 * Traceable Entity.
 * All traceable entities are validateable i.e. it tracks what stage it is in in the save process
 * and if not valid during save indicates why it is not valid.
 */
public abstract class TraceableEntity extends ValidateableRecord implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5894912088146875592L;
	
	private String sourceType;
	
	/**
	 * save status {@link RecordSaveStatus}.
	 */
	private RecordSaveStatus saveStatus = RecordSaveStatus.BEGIN;
	/**
	 * @return the saveStatus
	 */
	public final RecordSaveStatus getSaveStatus() {
		return saveStatus;
	}
	/**
	 * @param saveState the saveStatus to set
	 */
	public final void setSaveStatus(RecordSaveStatus saveState) {
		this.saveStatus = saveState;
	}
	/**
	 * Set the record save status for created.
	 */
	public final void setCreatedStatus() {
		this.saveStatus = RecordSaveStatus.SDR_ADDED;
	}
	
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	/**
	 * @return {@link Boolean}
	 */
	public final boolean isCreatedStatus() {
		return (this.saveStatus.equals(RecordSaveStatus.SDR_ADDED) ||
				this.saveStatus.equals(RecordSaveStatus.STUDENT_ADDED) ||
				this.saveStatus.equals(RecordSaveStatus.ENROLLMENT_ADDED) ||
				this.saveStatus.equals(RecordSaveStatus.ROSTER_ADDED) ||
				this.saveStatus.equals(RecordSaveStatus.EDUCATOR_ADDED) );
	}
}
