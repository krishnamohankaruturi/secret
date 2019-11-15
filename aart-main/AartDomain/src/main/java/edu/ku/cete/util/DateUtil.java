package edu.ku.cete.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {

	/**
	 * simpleDateFormat.
	 */
	private static final String simpleDateFormat = "MM/dd/yyyy";
	/**
	 * simpleDateFormat.
	 */
	private static final String simpleTimeStampFormat = "MM-dd-yyyy_hh-mm_a";
	/**
	 * date format.
	 */
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			simpleDateFormat);
	/**
	 * logger.
	 */
	private static final Log LOGGER = LogFactory.getLog(DateUtil.class);
	
	// List of all date formats that we want to parse.
    // Add your own format here.
    @SuppressWarnings("serial")
	private static List<SimpleDateFormat> 
            DATE_FORMATS = new ArrayList<SimpleDateFormat>() {
    	{
            add(new SimpleDateFormat("MM/dd/yyyy"));
            add(new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a"));
            add(new SimpleDateFormat("MM/dd/yy"));
            add(new SimpleDateFormat("MM/dd/yy hh:mm:ss a"));
        }
    };

	/**
	 * 1. Add the inDate to time in millis. 2. If the result is before the
	 * compareToDate, then it is true. 3. if input is invalid or above condition
	 * is false, then false.
	 * 
	 * @param inDate
	 * @param timeInMillis
	 * @param compareTo
	 * @return
	 */
	public static boolean isBefore(Date inDate, Long timeInMillis,
			Date compareTo) {
		boolean result = false;
		boolean validInput = false;
		if (inDate != null && timeInMillis != null && compareTo != null) {
			validInput = true;
		} else {
			LOGGER.error("Input inDate," + inDate + "timeInMillis,"
					+ timeInMillis + "compareTo" + compareTo + " is invalid");
		}
		if (validInput) {
			result = (inDate.getTime() + timeInMillis) < (compareTo.getTime());
		}
		return result;
	}

	/**
	 * 1. Add the inDate to time in millis. 2. If the result is before the
	 * compareToDate, then it is false. 3. if input is invalid or above
	 * condition is true, then true.
	 * 
	 * @param inDate
	 * @param timeInMillis
	 * @param compareTo
	 * @return
	 */
	public static boolean isOnOrAfter(Date inDate, Long timeInMillis,
			Date compareTo) {
		return !(isBefore(inDate, timeInMillis, compareTo));
	}

	/**
	 * If any stage of parsing fails or the entered date has to be converted
	 * (say 12/32/2011 to 01/01/2012) then it throws an error. The date entered
	 * has to be of format MM/dd/yyyy
	 * 
	 * @param str
	 *            {@link String}
	 * @return {@link Date}
	 * @throws AartParseException
	 *             AartParseException
	 */
	public static Date parseAndFail(String str) throws AartParseException {
		Date dt = null;
		String outDate = null;
		if (str == null || str.trim() == null) {
			throw new AartParseException(str, Date.class, 0);
		} else {
			try {
				// TODO as of now all input dates are of the same format.
				// change it to accept date formats if input dates can be of
				// different format.
				dt = sdf.parse(str);
				outDate = sdf.format(dt);
				str = StringUtil.getCleanedDateString(str);
				outDate = StringUtil.getCleanedDateString(outDate);
				if (!outDate.equalsIgnoreCase(str)) {
					throw new AartParseException(
							"Date has to be autoformated for conversion");
				}
			} catch (ParseException e) {
				throw new AartParseException(e);
			} catch (Exception e) {
				throw new AartParseException(e);
			}
		}
		return dt;
	}
	
	public static Date newParseAndFail(String str) throws AartParseException {
		Date dt = null;
		if (str == null || str.trim() == null) {
			throw new AartParseException(str, Date.class, 0);
		} else {
			String input = StringUtil.getCleanedDateString(str);
	        for (SimpleDateFormat format : DATE_FORMATS) {
	            try {
	                format.setLenient(false);
	                dt = format.parse(input);
	            } catch (ParseException e) {
	                //Shhh.. try other formats
	            }
	            if (dt != null) {
	                break;
	            }
	        }
		}
		if(null == dt) {
			throw new AartParseException(str, Date.class, 0);
		}
		return dt;
	}


	/**
	 * If any stage of parsing fails or the entered date has to be converted
	 * (say 12/32/2011 to 01/01/2012) then it throws an error.
	 * 
	 * @param str
	 *            {@link String}
	 * @param dateFormat
	 *            {@link String}
	 * @return {@link Date}
	 * @throws AartParseException
	 *             AartParseException
	 */
	public static Date parseAndFail(String str, String dateFormat)
			throws AartParseException {
		Date dt = null;
		String outDate = null;
		if (str == null || str.trim() == null) {
			throw new AartParseException(str, Date.class, 0);
		} else {
			try {
				// TODO as of now all input dates are of the same format.
				// change it to accept date formats if input dates can be of
				// different format.
				SimpleDateFormat inputDateFormt = new SimpleDateFormat(
						dateFormat);
				dt = inputDateFormt.parse(str);
				outDate = inputDateFormt.format(dt);
				str = StringUtil.getCleanedDateString(str);
				outDate = StringUtil.getCleanedDateString(outDate);
				if (!outDate.equalsIgnoreCase(str)) {
					throw new AartParseException("Date " + str
							+ " has to be autoformated to " + outDate
							+ "for conversion");
				}
			} catch (IllegalArgumentException e) {
				throw new AartParseException(e);
			} catch (ParseException e) {
				throw new AartParseException(e);
			} catch (Exception e) {
				throw new AartParseException(e);
			}
		}
		return dt;
	}

	/**
	 * @param str
	 *            {@link String}
	 * @return {@link Date}
	 */
	public static Date parse(String str) {
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			LOGGER.warn("Error parsing string", e);
		} catch (NullPointerException e) {
			LOGGER.warn("Error parsing string", e);
		}
		return null;
	}

	/**
	 * @param date
	 *            {@link Date}
	 * @param dateFormat
	 *            {@link String}
	 * @param defaultDate
	 *            {@link Date}
	 * @return {@link Date}
	 */
	public static String format(Date date, String dateFormat, Date defaultDate) {
		String result = null;
		SimpleDateFormat inputDateFormat = null;
		if(date !=null) {
			try {
				inputDateFormat = new SimpleDateFormat(dateFormat);
				result = inputDateFormat.format(date);
			} catch (IllegalArgumentException e) {
				LOGGER.warn("Error parsing string", e);
			} catch (NullPointerException e) {
				LOGGER.warn("Error parsing string", e);
			} finally {
				// try to format the default value and return it.
				if (inputDateFormat != null && defaultDate != null
						&& result == null) {
					result = inputDateFormat.format(defaultDate);
				}
			}
		}
		return result;
	}

	/**
	 * @param date
	 *            {@link Date}
	 * @param dateFormat
	 *            {@link Date}
	 * @return {@link Date}
	 */
	public static String format(Date date, String dateFormat) {
		return format(date, dateFormat, null);
	}

	/**
	 * @param dateFormat
	 *            {@link Date}
	 * @return {@link Date}
	 */
	public static String format(String dateFormat) {
		return format(new Date(), dateFormat, null);
	}

	/**
	 * @return {@link Date}
	 */
	public static String formatCurrentTimeStamp() {
		return format(new Date(), simpleTimeStampFormat, null);
	}

	/**
	 * @param str
	 *            {@link String}
	 * @param dateFormat
	 *            {@link Date}
	 * @param defaultDate
	 *            {@link Date}
	 * @return {@link Date}
	 */
	public static Date parse(String str, String dateFormat, Date defaultDate) {
		Date parsedDate = defaultDate;
		if(StringUtils.isNotBlank(str)) {
			try {
				SimpleDateFormat inputDateFormt = new SimpleDateFormat(dateFormat);
				parsedDate = inputDateFormt.parse(str);
			} catch (IllegalArgumentException e) {
				LOGGER.warn("The date format is not correct", e);
			} catch (ParseException e) {
				LOGGER.warn("Error parsing string", e);
			} catch (NullPointerException e) {
				LOGGER.warn("Empty or Invalid string", e);
			} catch (Exception e) {
				LOGGER.warn("Unknown Error parsing string", e);
			}
		}
		return parsedDate;
	}

	public static List<Date> getNextPeriod(Date fromDate, Date toDate,
			long frequencyDelta, long frequency) {
		List<Date> endingPeriod = new ArrayList<Date>();
		Date newFromDate = null;
		Date newToDate = null;
		boolean validInput = true;
		if (fromDate == null || toDate == null || fromDate.after(toDate)) {
			validInput = false;
		}
		if (validInput) {
			long difference = 0;

			try {
				Calendar strToDateCalendar = Calendar.getInstance();
				Calendar strFromDateCalendar = Calendar.getInstance();
				Calendar newStrToDateCalendar = Calendar.getInstance();
				Calendar newStrFromDateCalendar = Calendar.getInstance();

				strToDateCalendar.setTime(toDate);
				newStrToDateCalendar.setTime(toDate);
				strFromDateCalendar.setTime(fromDate);
				newStrFromDateCalendar.setTime(fromDate);
				// 1. compute the difference in interval.
				difference = strToDateCalendar.getTimeInMillis()
						- strFromDateCalendar.getTimeInMillis();
				// 2. compute the next period for the current interval.
				newFromDate = strToDateCalendar.getTime();
				newStrToDateCalendar.setTimeInMillis(strToDateCalendar
						.getTimeInMillis() + difference);
				newToDate = newStrToDateCalendar.getTime();
				//TODO: Wouldn't there be a chance of dates difference 
					// greater than the frequencyDelta..?
				// Make sure strToDate is less than the current DateTime
				// 3. If to date is after current date - lag, reduce to to date to
				// current date, minus lag.
				if (
						( (new Date()).getTime() ) 
						-
						newToDate.getTime()
						< frequencyDelta
					) {
					strToDateCalendar.setTime(new Date());
					newStrToDateCalendar.setTime(new Date());
					newStrToDateCalendar.setTimeInMillis(strToDateCalendar
							.getTimeInMillis() - frequencyDelta);
					newToDate = newStrToDateCalendar.getTime();
				}
				//if from date is after current date - lag, reduce from date to
				// to date minus lag.
				if (
						( newToDate.getTime() ) 
						-
						newFromDate.getTime()
						< frequency
					) {
					newStrFromDateCalendar.setTime(new Date());
					//frequency is the lowest interval it will run for.
					// so now for every 5 mins it will pull for 5 mins.
					newStrFromDateCalendar.setTimeInMillis(newStrToDateCalendar
							.getTimeInMillis() - frequency);
					newFromDate = newStrFromDateCalendar.getTime();
				}
				endingPeriod.add(newFromDate);
				endingPeriod.add(newToDate);
			} catch (Exception e) {
				// instead of adding null check for every line handle it here.
				LOGGER.error("Error setting parameters for next run", e);
			} finally {
				// for faster long comparison.
				if (difference < 1) {
					// this.strToDate = new Date();
					// this.strFromDate = new Date(strToDate.getTime() -
					// buffer);
					LOGGER.error("from and to date set to " + newFromDate
							+ " and " + newToDate);
				}
			}
		}
		return endingPeriod;
	}
	
 	public static Date convertStringDatetoSpecificTimeZoneDateFormat(String date, String timeZone,String format){
        Date theResult = null;
		try {
			DateFormat formatter = new SimpleDateFormat(format);
		    TimeZone obj = TimeZone.getTimeZone(timeZone);
		    formatter.setTimeZone(obj);
			theResult = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}               		
		return theResult;   		
   	}
   	
 	public static String convertDatetoSpecificTimeZoneStringFormat(Date date, String timeZone,String format){
        String currentCSTDateString = "";
		try {
			 DateFormat cstTimeZoneFormatter = new SimpleDateFormat(format);
  	       	 TimeZone obj = TimeZone.getTimeZone(timeZone);
  	       	 cstTimeZoneFormatter.setTimeZone(obj);
  	       	 currentCSTDateString = cstTimeZoneFormatter.format(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}               		
		return currentCSTDateString;   		
   	}
	
}
