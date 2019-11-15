package edu.ku.cete.domain.common;

import edu.ku.cete.domain.JsonSummaryData;

/**
 * Class for Student Information Records.
 * @author vittaly
 *
 */
public class StudenInformationRecordSummaryData extends JsonSummaryData {
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
