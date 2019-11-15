/**
 * 
 */
package edu.ku.cete.domain.studentsession;

import java.io.Serializable;

/**
 * @author mahesh
 *
 */
public class StudentSessionRule implements Serializable{
	
	private static final long serialVersionUID = -8186992279029121427L;
	
	private Long testCollectionId;
	private boolean systemDefinedEnrollment;
	private boolean manualEnrollment;
	private boolean ticketedAtTest;
	private boolean ticketedAtSection;
	private boolean isGracePeriodSet;
	private Long gracePeriod;
	private boolean notRequiredToCompleteTest;
	/**
	 * @return the testCollectionId
	 */
	public Long getTestCollectionId() {
		return testCollectionId;
	}
	/**
	 * @param testCollectionId the testCollectionId to set
	 */
	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}
	/**
	 * @return the systemDefinedEnrollment
	 */
	public final boolean isSystemDefinedEnrollment() {
		return systemDefinedEnrollment;
	}
	/**
	 * @param systemDefinedEnrollment the systemDefinedEnrollment to set
	 */
	public final void setSystemDefinedEnrollment(boolean systemDefinedEnrollment) {
		if(!this.systemDefinedEnrollment) {
			this.systemDefinedEnrollment = systemDefinedEnrollment;
		}
	}
	/**
	 * @return the manualEnrollment
	 */
	public final boolean isManualEnrollment() {
		return manualEnrollment;
	}
	/**
	 * @param manualEnrollment the manualEnrollment to set
	 */
	public final void setManualEnrollment(boolean manualEnrollment) {
		if(!this.manualEnrollment) {
			this.manualEnrollment = manualEnrollment;
		}
	}
	/**
	 * @return the ticketedAtTest
	 */
	public final boolean isTicketedAtTest() {
		return ticketedAtTest;
	}
	/**
	 * @param ticketedAtTest the ticketedAtTest to set
	 */
	public final void setTicketedAtTest(boolean ticketedAtTest) {
		if(!this.ticketedAtTest) {
			this.ticketedAtTest = ticketedAtTest;
		}
	}
	/**
	 * @return the ticketedAtSection
	 */
	public final boolean isTicketedAtSection() {
		return ticketedAtSection;
	}
	/**
	 * @param ticketedAtSection the ticketedAtSection to set
	 */
	public final void setTicketedAtSection(boolean ticketedAtSection) {
		if(!this.ticketedAtSection) {
			this.ticketedAtSection = ticketedAtSection;
		}
	}
	/**
	 * @return the isGracePeriodSet
	 */
	public final boolean isGracePeriodSet() {
		return isGracePeriodSet;
	}
	/**
	 * @param isGracePeriodSet the isGracePeriodSet to set
	 */
	public final void setGracePeriodSet(boolean isGracePeriodSet) {
		if(!this.isGracePeriodSet) {
			this.isGracePeriodSet = isGracePeriodSet;
		}
	}
	/**
	 * @return the gracePeriod
	 */
	public final Long getGracePeriod() {
		return gracePeriod;
	}
	/**
	 * @param gracePeriod the gracePeriod to set
	 */
	public final void setGracePeriod(Long gracePeriod) {
		this.gracePeriod = gracePeriod;
	}
	/**
	 * @param notRequiredToCompleteTest
	 */
	public final void setNotRequiredToCompleteTest(boolean notRequiredToCompleteTest) {
		this.notRequiredToCompleteTest = notRequiredToCompleteTest;
	}
	/**
	 * @return
	 */
	public boolean isNotRequiredToCompleteTest() {
		return this.notRequiredToCompleteTest;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StudentSessionRule [");
		if (testCollectionId != null) {
			builder.append("testCollectionId=");
			builder.append(testCollectionId);
			builder.append(", ");
		}
		builder.append("systemDefinedEnrollment=");
		builder.append(systemDefinedEnrollment);
		builder.append(", manualEnrollment=");
		builder.append(manualEnrollment);
		builder.append(", ticketedAtTest=");
		builder.append(ticketedAtTest);
		builder.append(", ticketedAtSection=");
		builder.append(ticketedAtSection);
		builder.append(", notRequiredToCompleteTest=");
		builder.append(notRequiredToCompleteTest);
		builder.append(", isGracePeriodSet=");
		builder.append(isGracePeriodSet);
		builder.append(", ");
		if (gracePeriod != null) {
			builder.append("gracePeriod=");
			builder.append(gracePeriod);
		}
		builder.append("]");
		return builder.toString();
	}

	
}
