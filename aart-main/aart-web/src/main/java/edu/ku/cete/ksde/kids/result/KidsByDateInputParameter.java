/**
 * 
 */
package edu.ku.cete.ksde.kids.result;

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
 * @author m802r921
 *
 * This is the class that eventually takes the input from
 * the UI for making the webservice call to getKidsByDate.
 * 
 * This class also bridges the gap of not having a complex type.
 * 
 * If the web service call fails then this will be updated
 *  with the new toDate value
 * and that needs to be displayed to the user.
 * 
 * As there are many WS operations , each with its own inputs,
 * these classes will convert making use of the utilities.
 * 
 */
public class KidsByDateInputParameter extends ValidateableRecord {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4882505216176917774L;

	/**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(KidsByDateInputParameter.class);
	
	/**
	 * This is the format kansas uses.
	 */
	private static final String KANSAS_DATE_FORMAT = "MM/dd/yyyy hh:mm:ss a";
	/**
	 * currentSchoolYear
	 */
	private int currentSchoolYear;
	/**
	 * date that is parsed from the string.
	 */
	private Date strFromDate;
	/**
	 * strToDate {@link Date}
	 */
	private Date strToDate;

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
	 * @return the strFrom
	 */
	public final String getStrFrom() {
		return DateUtil.format(strFromDate, KANSAS_DATE_FORMAT, null);
	}
	/**
	 * @return the strFromDate {@link Date}
	 */
	public final Date getStrFromDate() {
		return strFromDate;
	}
	/**
	 * @param strFromDt the strFromDate to set
	 */
	public final void setStrFromDate(Date strFromDt) {
		this.strFromDate = strFromDt;
	}
	/**
	 * @return the strTo
	 */
	public final String getStrTo() {
		return DateUtil.format(strToDate, KANSAS_DATE_FORMAT, null);
	}
	/**
	 * @return the strTo {@link Date}
	 */
	public final Date getStrToDate() {
		return strToDate;
	}
	/**
	 * @param strToDt the strToDate to set
	 */
	public final void setStrToDate(Date strToDt) {
		this.strToDate = strToDt;
	}
	/**
	 * Call before calling change input for next run.
	 * This way the email will reflect the right pull time.
	 * @param stringToDt the strToDate to set
	 */
	public final void setStrToDate(String stringToDt) {
		Date reducedToDt = null;
		//if the received date is valid use it, otherwise keep the one system has already.
		reducedToDt = DateUtil.parse(stringToDt, KANSAS_DATE_FORMAT, strToDate);
		if (!reducedToDt.after(strFromDate)) {
			LOGGER.debug("Not changing date because received date " + reducedToDt +
					"is not after from date" + stringToDt);
		} else {
			LOGGER.debug("Reducing date from " + strToDate +
					" to " + reducedToDt);
			this.strToDate = reducedToDt;
		}
	}
	
	/**
	 * @param scrsByDateFrequencyParameterMap
	 * @param uploadSpecification
	 */
	public final void changeInputForNextRun(
			Map<String, Category> kidsByDateFrequencyParameterMap,
			UploadSpecification uploadSpecification, String fromDate, boolean complete) {
		long frequencyDelta = 1800000; //30mins
		if(kidsByDateFrequencyParameterMap != null) {
			frequencyDelta = Long.parseLong(kidsByDateFrequencyParameterMap.get(
					uploadSpecification.getKansasWebServiceScheduleFrequencyDelta()).getCategoryName());
		}
		LOGGER.debug("before complete: "+complete);
		LOGGER.debug("before getStrFromDate: "+getStrFromDate());
		LOGGER.debug("before getStrToDate: "+getStrToDate());
		if(complete) { //if complete set from date as previous to date
			Calendar toDateCalendar = Calendar.getInstance();
			toDateCalendar.setTime(this.strToDate);
			toDateCalendar.setTimeInMillis(toDateCalendar.getTimeInMillis() + frequencyDelta);
			
			this.strFromDate = this.strToDate;
			this.strToDate = toDateCalendar.getTime();
		} else { //if not complete adjust the from date to be date received from KIDS and keep the same End Date
			this.strFromDate = DateUtil.parse(fromDate, KANSAS_DATE_FORMAT, this.strFromDate);
		}
		LOGGER.debug("after getStrFromDate: "+getStrFromDate());
		LOGGER.debug("after getStrToDate: "+getStrToDate());
	}
}
