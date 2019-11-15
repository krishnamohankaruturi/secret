/**
 * 
 */
package edu.ku.cete.web;

import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;

import edu.ku.cete.domain.content.AssessmentProgram;

/**
 * @author bmohanty_sta
 *
 */
public class SecurityAgreementDTO {
	String schoolYear;
	boolean agreementElection;
	String agreementSignedDate;
	String expireDate;
	String signerName;
	boolean dlm;
	boolean expired;
	/**
	 * @return the schoolYear
	 */
	public String getSchoolYear() {
		return schoolYear;
	}
	/**
	 * @param schoolYear the schoolYear to set
	 */
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	/**
	 * @return the agreementElection
	 */
	public boolean isAgreementElection() {
		return agreementElection;
	}
	/**
	 * @param agreementElection the agreementElection to set
	 */
	public void setAgreementElection(boolean agreementElection) {
		this.agreementElection = agreementElection;
	}
	/**
	 * @return the agreementSignedDate
	 */
	public String getAgreementSignedDate() {
		return agreementSignedDate;
	}
	/**
	 * @param agreementSignedDate the agreementSignedDate to set
	 */
	public void setAgreementSignedDate(String agreementSignedDate) {
		this.agreementSignedDate = agreementSignedDate;
	}
	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}
	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	/**
	 * @return the signerName
	 */
	public String getSignerName() {
		return signerName;
	}
	/**
	 * @param signerName the signerName to set
	 */
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}
	/**
	 * @return the dlm
	 */
	public boolean isDlm() {
		return dlm;
	}
	/**
	 * @param dlm the dlm to set
	 */
	public void setDlm(boolean dlm) {
		this.dlm = dlm;
	}
	/**
	 * @return the expired
	 */
	public boolean isExpired() {
		return expired;
	}
	/**
	 * @param expired the expired to set
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
}
