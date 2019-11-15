/**
 * 
 */
package edu.ku.cete.domain.common;

import edu.ku.cete.domain.JsonSummaryData;

/**
 * @author mahesh
 *
 */
public class StudentRosterSummaryData extends JsonSummaryData {
	/**
	 * @return the numberOfStudents
	 */
	public double getNumberOfStudents() {
		return getFieldSummary1();
	}

	/**
	 * @param numberOfStudents the numberOfStudents to set
	 */
	public void setNumberOfStudents(double numberOfStudents) {
		this.setFieldSummary1(numberOfStudents);
	}
	
}
