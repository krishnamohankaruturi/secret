/**
 * 
 */
package edu.ku.cete.domain.common;

import edu.ku.cete.domain.JsonSummaryData;

/**
 * @author mahesh
 * This is the summary of total number of correct/incorrect and total responses.
 */
public class NodeSummaryData extends JsonSummaryData {
	/**
	 * @return the noOfCorrectResponses
	 */
	public final double getNoOfCorrectResponses() {
		return this.getFieldSummary1();
	}
	/**
	 * @param noOfCorrectResponses the noOfCorrectResponses to set
	 */
	public final void setNoOfCorrectResponses(double noOfCorrectResponses) {
		setFieldSummary1(noOfCorrectResponses);
	}
	/**
	 * @return the noOfIncorrectResponses
	 */
	public final double getNoOfIncorrectResponses() {
		return this.getFieldSummary2();
	}
	/**
	 * @param noOfIncorrectResponses the noOfIncorrectResponses to set
	 */
	public final void setNoOfIncorrectResponses(double noOfIncorrectResponses) {
		setFieldSummary2(noOfIncorrectResponses);
	}
	/**
	 * @return the totalNoOfResponses
	 */
	public final double getTotalNoOfResponses() {
		return this.getFieldSummary3();
	}
	/**
	 * @param totalNoOfResponses the totalNoOfResponses to set
	 */
	public final void setTotalNoOfResponses(double totalNoOfResponses) {
		setFieldSummary3(totalNoOfResponses);
	}	
}
