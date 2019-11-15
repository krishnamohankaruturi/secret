package edu.ku.cete.dlm.iti;

public class BPGroupsInfo {	
	private Long criteriaId;
	private Long groupNumber;	
	private Long numberOfEEsRequired;
	private Integer numOfITIEEsCompleted = 0;
	private Integer numOfSTEEsCompleted = 0;
	
	public Long getCriteriaId() {
		return criteriaId;
	}
	public void setCriteriaId(Long criteriaId) {
		this.criteriaId = criteriaId;
	}
	public Long getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(Long groupNumber) {
		this.groupNumber = groupNumber;
	}
	
	public boolean isITIGroupRequiremtMet() {		
		return numOfITIEEsCompleted >= numberOfEEsRequired;
	}	
	public Long getNumberOfEEsRequired() {
		return numberOfEEsRequired;
	}
	public void setNumberOfEEsRequired(Long numberOfEEsRequired) {
		this.numberOfEEsRequired = numberOfEEsRequired;
	}
	public int getNumOfEEsCompleted() {
		return numOfITIEEsCompleted;
	}
	
	public void resetNumberOfITIEEsCompleted() {
		this.numOfITIEEsCompleted = 0;
	}
	
	public void incrementNumberOfITIEEsCompleted() {
		this.numOfITIEEsCompleted = this.numOfITIEEsCompleted + 1;
	}
	
	public boolean isSTGroupRequiremtMet() {
		return numOfSTEEsCompleted >= numberOfEEsRequired;
	}
	
	public void resetSTNumberOfEEsCompleted() {
		this.numOfSTEEsCompleted = 0;
	}
	
	public void incrementNumberOfSTEEsCompleted() {
		this.numOfSTEEsCompleted = this.numOfSTEEsCompleted + 1;
	}
	
	public Integer getNumOfITIEEsCompleted() {
		return numOfITIEEsCompleted;
	}
	
	public Integer getNumOfSTEEsCompleted() {
		return numOfSTEEsCompleted;
	}
	
}
