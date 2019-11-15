package edu.ku.cete.web;

public class KSDEStudentTestSectionsDTO {
	private Long sectionQuestionsCount;
	private String sectionStatus;
	private Long numofExludedItems;
	
	/**
	 * @return the sectionQuestionsCount
	 */
	public Long getSectionQuestionsCount() {
		return sectionQuestionsCount;
	}
	/**
	 * @param sectionQuestionsCount the sectionQuestionsCount to set
	 */
	public void setSectionQuestionsCount(Long sectionQuestionsCount) {
		this.sectionQuestionsCount = sectionQuestionsCount;
	}
	/**
	 * @return the sectionStatus
	 */
	public String getSectionStatus() {
		return sectionStatus;
	}
	/**
	 * @param sectionStatus the sectionStatus to set
	 */
	public void setSectionStatus(String sectionStatus) {
		this.sectionStatus = sectionStatus;
	}
	/**
	 * @return the numofExludedItems
	 */
	public Long getNumofExludedItems() {
		return numofExludedItems;
	}
	/**
	 * @param numofExludedItems the numofExludedItems to set
	 */
	public void setNumofExludedItems(Long numofExludedItems) {
		this.numofExludedItems = numofExludedItems;
	}	
	
}
