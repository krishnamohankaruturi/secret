package edu.ku.cete.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author m802r921
 * For parsing.
 */
public class NumericUtil {
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(NumericUtil.class);
	/**
	 * upload spec.
	 * TODO remove inferences  
	 */
	private static UploadSpecification uploadSpecification;
	/**
	 * @return {@link UploadSpecification}
	 */
	public static UploadSpecification getUploadSpecification() {
		return uploadSpecification;
	}

	/**
	 * @param uploadSpec {@link UploadSpecification}
	 */
	@Autowired
	public static synchronized void setUploadSpecification(UploadSpecification uploadSpec) {
		NumericUtil.uploadSpecification = uploadSpec;
	}

	/**
	 * @param str {@link String}
	 * @return {@link Integer}
	 * @throws AartParseException AartParseException
	 */
	public static int parseAndFail(String str) throws AartParseException {
		int out = Integer.MIN_VALUE;
		try {
			out = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			throw new AartParseException(e);
		} catch (NullPointerException e) {
			throw new AartParseException(e);
		}
		return out;
	}
	/**
	 * @param str {@link String}
	 * @return {@link Integer}
	 */
	public static int parse(String str) {
		int out = Integer.MIN_VALUE;
		if(str != null && !"".equals(str)) {
			try {
				out = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				LOGGER.debug("Exception parsing string", e );
			}
		}
		return out;
	}
	/**
	 * @param str {@link String}
	 * @return {@link Integer}
	 */
	public static int parse(String str,int defaultValue) {
		int out = defaultValue;
		if(str != null && !"".equals(str)) {
			try {
				out = Integer.parseInt(str);
			} catch (NumberFormatException e) {
				LOGGER.debug("Exception parsing string", e );
			}
		}
		return out;
	}
	/**
	 * @return
	 */
	public static int getPageCount(int totalCount, int noOfRows) {
		int pageCount = 0;
		boolean validInput = false;
		if(totalCount > 0
				&& noOfRows > 0) {
			validInput = true;
		} else {
			LOGGER.debug("Input is not valid totalCount" + totalCount +
					"noOfRows" + noOfRows);
		}
		if(validInput) {
			pageCount = (int) totalCount / noOfRows;
			if(totalCount % noOfRows > 0) {
				pageCount ++;
			}
		}
		return pageCount;
	}
	/**
	 * @param allowableValues {@link Set}
	 * @param value {@link Integer}
	 * @return {@link Boolean}
	 */
	public static final boolean isAllowed(int[] allowableValues, int value) {
		for (int allowableValue:allowableValues) {
			if (value == allowableValue) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param minimum {@link Integer}
	 * @param maximum {@link Integer}
	 * @param val {@link Integer}
	 * @return {@link Boolean}
	 */
	public static boolean isAllowed(int minimum,
			int maximum, int val) {
		return (minimum <= val
				&& val <= maximum);
	}

	/**
	 * @param maximum {@link Long}
	 * @param minimum {@link Long}
	 * @return {@link Boolean}
	 */
	public static boolean validateRange(Long maximum, Long minimum) {
		return (maximum != null && minimum != null && maximum > minimum);
	}

	/**
	 * @param minimum {@link Long}
	 * @param maximum {@link Long}
	 * @param inValue {@link String}
	 * @return {@link Boolean}
	 */
	public static boolean isAllowed(Long minimum, Long maximum, String inValue) {
		Long inLong = null;
		inLong = NumericUtil.parse(inValue, inLong);
		return (inLong != null && minimum <= inLong
				&& inLong <= maximum);
	}

	/**
	 * @param inValue {@link String}
	 * @param inLong {@link Long}
	 * @return {@link Long}
	 */
	private static Long parse(String inValue, Long inLong) {
		inLong = null;
		if(inValue != null && !"".equals(inValue)) {
			try {
				inLong = Long.parseLong(inValue);
			} catch (Exception e) {
				LOGGER.debug("Exception parsing string", e);
			}
		}
		return inLong;
	}
	/**
	 * @param inValue {@link String}
	 * @param inLong {@link Long}
	 * @return {@link Long}
	 */
	public static Long parse(String inValue, long inLong) {
		if(inValue != null && !"".equals(inValue)) {
			try {
				inLong = Long.parseLong(inValue);
			} catch (Exception e) {
				LOGGER.debug("Exception parsing string", e);
			}
		}
		return inLong;
	}
	/**
	 * @param in {@link Long}
	 * @param other {@link String}
	 * @return {@link Boolean}
	 */
	public static boolean compare(Long in, String other) {
		Long otherLong = null;
		boolean returnResult = false;
		otherLong = parse(other, otherLong);
		if (in != null && otherLong != null) {
			returnResult = in.equals(otherLong);
		}
		return returnResult;
	}
	/**
	 * @param inValue {@link String}
	 * @param inLong {@link Long}
	 * @param valueOnError {@link Long}
	 * @return {@link Long}
	 */
	public static Long parse(String inValue, long inLong, long valueOnError) {
		if(inValue != null && !"".equals(inValue)) {
			try {
				inLong = Long.parseLong(inValue);
			} catch (Exception e) {
				LOGGER.debug("Unable to parse the passed string to long", e);
				inLong = valueOnError;
			}
		} else {
			inLong = valueOnError;
		}
		return inLong;
	}
	/**
	 * @param in {@link Long}
	 * @param other {@link String}
	 * @param valueOnError {@link Long}
	 * @return {@link Boolean}
	 */
	public static boolean compare(Long in, String other, long valueOnError) {
		Long otherLong = valueOnError;
		boolean returnResult = false;
		otherLong = parse(other, otherLong, valueOnError);
		if (in != null && otherLong != null) {
			returnResult = in.equals(otherLong);
		}
		return returnResult;
	}

	/**
	 * @param in {@link Long}
	 * @param other {@link Long}
	 * @return {@link Boolean}
	 */
	public static boolean compare(long in, long other) {
		return (in == other);
	}
	
	public static boolean isLong(String inStr) {
		Long out = null;
		if(inStr != null && !"".equals(inStr)) {
			try {
				out = Long.parseLong(inStr);
			} catch (NumberFormatException e) {
				LOGGER.debug("Not a valid long"+inStr);
			}
		}
		return (out != null);
	}

	public static List<Long> convert(String[] inList) {
		List<Long> outList = new ArrayList<Long>();
		boolean validInput = false;
		if(inList != null && inList.length > 0) {
			validInput = true;
		}
		if (validInput) {
			for (String inStr : inList) {
				if (isLong(inStr)) {
					outList.add(Long.parseLong(inStr));
				}
			}
		}
		return outList;
	}
	
	public static int getCustomRoundedNumber(BigDecimal value){
		int roundedNumber = 0;		
		if(value != null){
			try{
				String strValue = value.toPlainString();
				int decimalIndex = strValue.indexOf('.');
				String strValueBeforeDecimalPoint = strValue.substring(0, decimalIndex);
				String decimalPart = strValue.substring(decimalIndex+1);
				if(decimalIndex > -1 ){
					if(decimalPart.length() > 0){
						int digitRightAfterDecimalPoint = Integer.parseInt(decimalPart.substring(0,1));
						if(digitRightAfterDecimalPoint >=1){
							roundedNumber = Integer.parseInt(strValueBeforeDecimalPoint)+1;
						}else{
							roundedNumber = Integer.parseInt(strValueBeforeDecimalPoint);
						}
					}else{
						roundedNumber = Integer.parseInt(strValueBeforeDecimalPoint);
					}					
				}else{
					roundedNumber = Integer.parseInt(strValueBeforeDecimalPoint);
				}				
			}catch(Exception e){
				LOGGER.debug("Unable to round the passed in value as needed, so applying normal rounding", e);
			}
		}		
		return roundedNumber;
	}
	
}
