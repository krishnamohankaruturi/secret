package edu.ku.cete.ksde.rosters.result;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.UploadSpecification;

/**
 * @author vittaly
 * Contians parameters like startdate, enddate etc. 
 * for STCO webservice upload.
 */
public class RosterByDateInputParameter extends ValidateableRecord {
	/**
	 * 
	 */
	private static final long serialVersionUID = 353212873584500661L;

	/**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(RosterByDateInputParameter.class);
	
	/**
	 * This is the format kansas uses.
	 */
	public static final String KANSAS_DATE_FORMAT = "MM/dd/yyyy hh:mm:ss a";
	/**
	 * currentSchoolYear
	 */
	private int currentSchoolYear;
	/**
	 * date that is parsed from the string.
	 */
	private Date rosterFromDate;
	/**
	 * rosterToDate
	 */
	private Date rosterToDate;

	@Override
	public String getIdentifier() {
		return null;
	}
	/* (non-Javadoc)
	 *  return the over riden date format.
	 */
	public String getDateFormat() {
		return KANSAS_DATE_FORMAT;
	}
	/**
	 * @return the currentSchoolYear
	 */
	public final int getCurrentSchoolYear() {
		return currentSchoolYear;
	}
	/**
	 * @return the currentSchoolYear
	 */
	public final String getStrCurrentSchoolYear() {
		return ParsingConstants.BLANK + currentSchoolYear;
	}
	/**
	 * @param curSchoolYear the currentSchoolYear to set
	 */
	public final void setCurrentSchoolYear(int curSchoolYear) {
		this.currentSchoolYear = curSchoolYear;
	}
	/**
	 * @return the rosterFromDate
	 */
	public final String getStrRosterFromDate() {
		return DateUtil.format(rosterFromDate, KANSAS_DATE_FORMAT, null);
	}
	/**
	 * @return the rosterFromDate {@link Date}
	 */
	public final Date getRosterFromDate() {
		return rosterFromDate;
	}
	/**
	 * @param strFromDt the rosterFromDate to set
	 */
	public final void setRosterFromDate(Date strFromDt) {
		this.rosterFromDate = strFromDt;
	}
	/**
	 * @return the rosterToDate
	 */
	public final String getStrRosterToDate() {
		return DateUtil.format(rosterToDate, KANSAS_DATE_FORMAT, null);
	}
	/**
	 * @return the rosterToDate {@link Date}
	 */
	public final Date getRosterToDate() {
		return rosterToDate;
	}
	/**
	 * @param strToDt the strToDate to set
	 */
	public final void setRosterToDate(Date strToDt) {
		this.rosterToDate = strToDt;
	}
	/**
	 * Call before calling change input for next run.
	 * This way the email will reflect the right pull time.
	 * @param stringToDt the strToDate to set
	 */
	public final void setStrToDate(String stringToDt) {
		Date reducedToDt = null;
		//if the received date is valid use it, otherwise keep the one system has already.
		reducedToDt = DateUtil.parse(stringToDt, KANSAS_DATE_FORMAT, rosterToDate);
		if (!reducedToDt.after(rosterFromDate)) {
			LOGGER.debug("Not changing date because received date " + reducedToDt +
					"is not after from date" + stringToDt);
		} else {
			LOGGER.debug("Reducing date from " + rosterToDate +
					" to " + reducedToDt);
			this.rosterToDate = reducedToDt;
		}
	}
	
	
	/**
	 * @param scrsByDateFrequencyParameterMap
	 * @param uploadSpecification
	 */
	public final void changeInputForNextRun(
			Map<String, Category> scrsByDateFrequencyParameterMap,
			UploadSpecification uploadSpecification, String fromDate, boolean complete) {
		long frequencyDelta = 1800000; //30mins
		if(scrsByDateFrequencyParameterMap != null) {
			frequencyDelta = Long.parseLong(scrsByDateFrequencyParameterMap.get(
					uploadSpecification.getKansasWebServiceScheduleFrequencyDelta()).getCategoryName());
		}
		LOGGER.debug("before complete: "+complete);
		LOGGER.debug("before getStrRosterFromDate: "+getStrRosterFromDate());
		LOGGER.debug("before getStrRosterToDate: "+getStrRosterToDate());
		if(complete) { //if complete set from date as previous to date
			Calendar toDateCalendar = Calendar.getInstance();
			toDateCalendar.setTime(this.rosterToDate);
			toDateCalendar.setTimeInMillis(toDateCalendar.getTimeInMillis() + frequencyDelta);
			
			this.rosterFromDate = this.rosterToDate;
			this.rosterToDate = toDateCalendar.getTime();
		} else { //if not complete adjust the from date to be date received from STCO and keep the same End Date
			this.rosterFromDate = DateUtil.parse(fromDate, KANSAS_DATE_FORMAT, this.rosterFromDate);
		}
		LOGGER.debug("after getStrRosterFromDate: "+getStrRosterFromDate());
		LOGGER.debug("after getStrRosterToDate: "+getStrRosterToDate());
	}
}
