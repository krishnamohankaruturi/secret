/**
 * @author bmohanty_sta
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
 * Get list of student ids by passing a list of state student identifiers.
 */
package edu.ku.cete.domain.report.roster;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ItiRosterReportEE {
	private String essentialElement;
	private String essentialElementDesc;
	private String raLinkageLevel;
	private String raLinkageLevelShortDesc;
	private String cigLinkageLevel;
	private String cigLinkageLevelShortDesc;
	private String studentFirstName;
	private String studentMiddleName;
	private String studentLastName;
	private String gradeName;
	private Long studentId;
	private Long rosterId;
	private Date endDateTime;
	private String icon;	
	private String endDateTimeStr;
	
	public String getIcon() {
		return this.icon;
	}
	/**
	 * Custom method, Do not delete.
	 * @return
	 */
	public String getIconBySubject(String contentAreaName) {
		Long ciRank = 0L;
		Long riRank = 0L;
		if(StringUtils.equalsIgnoreCase("SCIENCE", contentAreaName)) {
			if("Initial".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 1L;
			} else if("Precursor".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 2L;
			} else if("Target".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 3L;
			}
			
			if("Initial".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 1L;
			} else if("Precursor".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 2L;
			} else if("Target".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 3L;
			}
			
		} else {
			if("Initial Precursor".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 1L;
			} else if("Distal Precursor".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 2L;
			} else if("Proximal Precursor".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 3L;
			} else if("Target".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 4L;
			} else if("Successor".equalsIgnoreCase(getCigLinkageLevel())){
				ciRank = 5L;
			}
			
			if("Initial Precursor".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 1L;
			} else if("Distal Precursor".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 2L;
			} else if("Proximal Precursor".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 3L;
			} else if("Target".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 4L;
			} else if("Successor".equalsIgnoreCase(getRaLinkageLevel())){
				riRank = 5L;
			}
		} 
		if(riRank.longValue() != 0 && ciRank.longValue() != 0 && ciRank.longValue() > riRank.longValue()){
			return "plus";
		} else if(riRank.longValue() != 0 && ciRank.longValue() != 0 && ciRank.longValue() == riRank.longValue()){
			
		} else if(riRank.longValue() != 0 && ciRank.longValue() != 0 && ciRank.longValue() < riRank.longValue()){
			return "minus";
		}
		return "";
	}
	
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) {
		this.icon = icon;
	}


	/**
	 * Custom method, Do not delete.
	 * @return
	 */
	public String getEndDateTimeStr() {
		if(endDateTime != null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			return dateFormat.format(endDateTime);			
		} else {
			return "";
		}
	}
	
	/**
	 * @param endDateTimeStr the endDateTimeStr to set
	 */
	public void setEndDateTimeStr(String endDateTimeStr) {
		this.endDateTimeStr = endDateTimeStr;
	}

	/**
	 * Custom method, Do not delete.
	 * @return
	 */
	public String getStudentName(){
		String edn = getStudentFirstName();
		if(getStudentMiddleName() != null && !"".equals(getStudentMiddleName())){
			edn = edn + " " + getStudentMiddleName();
		}
		return edn + " " + getStudentLastName();
	}
	
	/**
	 * @return the essentialElement
	 */
	public String getEssentialElement() {
		return essentialElement;
	}
	/**
	 * @param essentialElement the essentialElement to set
	 */
	public void setEssentialElement(String essentialElement) {
		this.essentialElement = essentialElement;
	}
	/**
	 * @return the essentialElementDesc
	 */
	public String getEssentialElementDesc() {
		return essentialElementDesc;
	}
	/**
	 * @param essentialElementDesc the essentialElementDesc to set
	 */
	public void setEssentialElementDesc(String essentialElementDesc) {
		this.essentialElementDesc = essentialElementDesc;
	}
	/**
	 * @return the raLinkageLevel
	 */
	public String getRaLinkageLevel() {
		return raLinkageLevel;
	}
	/**
	 * @param raLinkageLevel the raLinkageLevel to set
	 */
	public void setRaLinkageLevel(String raLinkageLevel) {
		this.raLinkageLevel = raLinkageLevel;
	}
	/**
	 * @return the raLinkageLevelShortDesc
	 */
	public String getRaLinkageLevelShortDesc() {
		return raLinkageLevelShortDesc;
	}
	/**
	 * @param raLinkageLevelShortDesc the raLinkageLevelShortDesc to set
	 */
	public void setRaLinkageLevelShortDesc(String raLinkageLevelShortDesc) {
		this.raLinkageLevelShortDesc = raLinkageLevelShortDesc;
	}
	/**
	 * @return the cigLinkageLevel
	 */
	public String getCigLinkageLevel() {
		return cigLinkageLevel;
	}
	/**
	 * @param cigLinkageLevel the cigLinkageLevel to set
	 */
	public void setCigLinkageLevel(String cigLinkageLevel) {
		this.cigLinkageLevel = cigLinkageLevel;
	}
	/**
	 * @return the cigLinkageLevelShortDesc
	 */
	public String getCigLinkageLevelShortDesc() {
		return cigLinkageLevelShortDesc;
	}
	/**
	 * @param cigLinkageLevelShortDesc the cigLinkageLevelShortDesc to set
	 */
	public void setCigLinkageLevelShortDesc(String cigLinkageLevelShortDesc) {
		this.cigLinkageLevelShortDesc = cigLinkageLevelShortDesc;
	}
	/**
	 * @return the studentFirstName
	 */
	public String getStudentFirstName() {
		return studentFirstName;
	}
	/**
	 * @param studentFirstName the studentFirstName to set
	 */
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	/**
	 * @return the studentMiddleName
	 */
	public String getStudentMiddleName() {
		return studentMiddleName;
	}
	/**
	 * @param studentMiddleName the studentMiddleName to set
	 */
	public void setStudentMiddleName(String studentMiddleName) {
		this.studentMiddleName = studentMiddleName;
	}
	/**
	 * @return the studentLastName
	 */
	public String getStudentLastName() {
		return studentLastName;
	}
	/**
	 * @param studentLastName the studentLastName to set
	 */
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	/**
	 * @return the gradeName
	 */
	public String getGradeName() {
		return gradeName;
	}
	/**
	 * @param gradeName the gradeName to set
	 */
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the rosterId
	 */
	public Long getRosterId() {
		return rosterId;
	}

	/**
	 * @param rosterId the rosterId to set
	 */
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	/**
	 * @return the endDateTime
	 */
	public Date getEndDateTime() {
		return endDateTime;
	}

	/**
	 * @param endDateTime the endDateTime to set
	 */
	public void setEndDateTime(Date endDateTime) {
		this.endDateTime = endDateTime;
	}
	
	
	
	
	 
}