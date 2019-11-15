package edu.ku.cete.web;

import java.util.Date;

/**
 * 
 * @author Venkata Krishna Jagarlamudi
 * US15630: Data extract - Test administration for KAP and AMP
 * US15741: Data Extract - Test Tickets for KAP and AMP
 * Common for both extracts.
 *
 */
public class StudentTestSectionsDTO {
	
	private String ticketNumber;
	private Date stuTestSectionStartDate;
	private Date stuTestSectionEndDate;
	private String stuTestSectionStatus;
	private int numofItems;
	private boolean hardBreak;
	private int sectionOrder;
	private Long numOfItemsAnswered;
	private Long stuTestId;
		
	/**
	 * @return the stuTestId
	 */
	public Long getStuTestId() {
		return stuTestId;
	}
	/**
	 * @param stuTestId the stuTestId to set
	 */
	public void setStuTestId(Long stuTestId) {
		this.stuTestId = stuTestId;
	}
	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		return ticketNumber;
	}
	/**
	 * @param ticketNumber the ticketNumber to set
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	/**
	 * @return the stuTestSectionStartDate
	 */
	public Date getStuTestSectionStartDate() {
		return stuTestSectionStartDate;
	}
	/**
	 * @param stuTestSectionStartDate the stuTestSectionStartDate to set
	 */
	public void setStuTestSectionStartDate(Date stuTestSectionStartDate) {
		this.stuTestSectionStartDate = stuTestSectionStartDate;
	}
	/**
	 * @return the stuTestSectionEndDate
	 */
	public Date getStuTestSectionEndDate() {
		return stuTestSectionEndDate;
	}
	/**
	 * @param stuTestSectionEndDate the stuTestSectionEndDate to set
	 */
	public void setStuTestSectionEndDate(Date stuTestSectionEndDate) {
		this.stuTestSectionEndDate = stuTestSectionEndDate;
	}
	/**
	 * @return the stuTestSectionStatus
	 */
	public String getStuTestSectionStatus() {
		return stuTestSectionStatus;
	}
	/**
	 * @param stuTestSectionStatus the stuTestSectionStatus to set
	 */
	public void setStuTestSectionStatus(String stuTestSectionStatus) {
		this.stuTestSectionStatus = stuTestSectionStatus;
	}
	/**
	 * @return the numofItems
	 */
	public int getNumofItems() {
		return numofItems;
	}
	/**
	 * @param numofItems the numofItems to set
	 */
	public void setNumofItems(int numofItems) {
		this.numofItems = numofItems;
	}
	/**
	 * @return the hardBreak
	 */
	public boolean isHardBreak() {
		return hardBreak;
	}
	/**
	 * @param hardBreak the hardBreak to set
	 */
	public void setHardBreak(boolean hardBreak) {
		this.hardBreak = hardBreak;
	}
	/**
	 * @return the sectionOrder
	 */
	public int getSectionOrder() {
		return sectionOrder;
	}
	/**
	 * @param sectionOrder the sectionOrder to set
	 */
	public void setSectionOrder(int sectionOrder) {
		this.sectionOrder = sectionOrder;
	}
	/**
	 * @return the numOfItemsAnswered
	 */
	public Long getNumOfItemsAnswered() {
		return numOfItemsAnswered;
	}
	/**
	 * @param numOfItemsAnswered the numOfItemsAnswered to set
	 */
	public void setNumOfItemsAnswered(Long numOfItemsAnswered) {
		this.numOfItemsAnswered = numOfItemsAnswered;
	}
	
	
}
