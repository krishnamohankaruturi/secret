/**
 * @author bmohanty_sta
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
 * Domain object to hold the roster report child section to be displayed for DLM organization.
 */
package edu.ku.cete.domain.report.roster;

import java.util.List;



public class RosterReportGradeStudentData {
	private String gradeCourseName;
	private String studentName;
	
	List<ItiRosterReportEE> eeDataList;

	/**
	 * @return the gradeCourseName
	 */
	public String getGradeCourseName() {
		return gradeCourseName;
	}

	/**
	 * @param gradeCourseName the gradeCourseName to set
	 */
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}

	/**
	 * @return the studentName
	 */
	public String getStudentName() {
		return studentName;
	}

	/**
	 * @param studentName the studentName to set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * @return the eeDataList
	 */
	public List<ItiRosterReportEE> getEeDataList() {
		return eeDataList;
	}

	/**
	 * @param eeDataList the eeDataList to set
	 */
	public void setEeDataList(List<ItiRosterReportEE> eeDataList) {
		this.eeDataList = eeDataList;
	}
	
	
	
	

	
}