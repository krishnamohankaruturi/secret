/**
 * @author bmohanty_sta
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
 * Domain object to store educator information.
 */
package edu.ku.cete.domain;

import java.util.List;


public class Educator {
	private String educatorId;
	private String educatorFirstName;
	private String educatorMiddleName;
	private String educatorLastName;
	
	public String getEducatorName(){
		String edn = getEducatorFirstName();
		if(getEducatorMiddleName() != null && !"".equals(getEducatorMiddleName())){
			edn = edn + " " + getEducatorMiddleName();
		}
		return edn + " " + getEducatorLastName();
	}

	/**
	 * @return the educatorId
	 */
	public String getEducatorId() {
		return educatorId;
	}

	/**
	 * @param educatorId the educatorId to set
	 */
	public void setEducatorId(String educatorId) {
		this.educatorId = educatorId;
	}

	/**
	 * @return the educatorFirstName
	 */
	public String getEducatorFirstName() {
		return educatorFirstName;
	}

	/**
	 * @param educatorFirstName the educatorFirstName to set
	 */
	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}

	/**
	 * @return the educatorMiddleName
	 */
	public String getEducatorMiddleName() {
		return educatorMiddleName;
	}

	/**
	 * @param educatorMiddleName the educatorMiddleName to set
	 */
	public void setEducatorMiddleName(String educatorMiddleName) {
		this.educatorMiddleName = educatorMiddleName;
	}

	/**
	 * @return the educatorLastName
	 */
	public String getEducatorLastName() {
		return educatorLastName;
	}

	/**
	 * @param educatorLastName the educatorLastName to set
	 */
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}
	
	 
}