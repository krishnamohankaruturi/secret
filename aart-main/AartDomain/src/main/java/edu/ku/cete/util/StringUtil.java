package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author m802r921
 * For parsing.
 */
public class StringUtil {
	
	/**
	 * upload spec.
	 */
	private static UploadSpecification uploadSpecification;
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(StringUtil.class);
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
		StringUtil.uploadSpecification = uploadSpec;
	}
	
	/**
	 * @param in {@link String}
	 * @return {@link String}
	 */
	public static String convertToNormalCase(String in) {
		if (StringUtils.hasText(in)) {
			in = in.replaceAll(
					String.format("%s|%s|%s",
							"(?<=[A-Z])(?=[A-Z][a-z])",
							"(?<=[^A-Z])(?=[A-Z])",
							"(?<=[A-Za-z])(?=[^A-Za-z])"
							),
							" "
							);
			in = in.replaceFirst(
					in.charAt(0) + ParsingConstants.BLANK,
					(in.charAt(0) + ParsingConstants.BLANK).toUpperCase());
			}
		return in;
	}

	/**
	 * @param str {@link String}
	 * @param limit {@link Integer}
	 * @param isNull
	 * @return {@link String}
	 * @throws AartParseException AartParseException
	 */
	public static String parseAndFail(String str, int limit) throws AartParseException {
		if (!StringUtils.hasText(str)) {
			throw new AartParseException(str, limit);
		} else {
			if (str.length() > limit) {
				throw new AartParseException(str, limit);
			}
			return str;
		}
	}
	/**
	 * @param str {@link String}
	 * @param limit {@link Integer}
	 * @param isNull
	 * @return {@link String}
	 */
	public static String parse(String str, int limit) {
		if (!StringUtils.hasText(str)) {
			return str;
		} else {
			str = str.trim();
			if (str.length() > limit) {
				return str.substring(0,limit);
			}
			return str;
		}
	}

	/**
	 * @param str {@link String}
	 * @param limit {@link Integer}
	 * @param isNull
	 * @return {@link Boolean}
	 */
	public static boolean isValid(String str, int limit) {
		boolean valid = true;
		if (str == null || str.trim() == null) {
			valid = false;
		} else {
			if (str.length() > limit) {
				valid = false;
			}
		}
		return valid;
	}
	/**
	 * @param allowableValues {@link Set}
	 * @param value {@link Integer}
	 * @return {@link Boolean}
	 */
	public static final boolean isAllowed(String[] allowableValues, String value) {
		if (!StringUtils.hasText(value)) {
			return false;
		}
		for (String allowableValue:allowableValues) {
			if (value.equalsIgnoreCase(allowableValue)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param inString {@link String}
	 * @param regex {@link String}
	 * @param replace  {@link String}
	 * @return {@link String}
	 */
	public static String replace(String inString, String regex,
			String replace) {
		if (StringUtils.hasText(inString) && 
				StringUtils.hasText(regex) &&
				StringUtils.hasText(replace)) {
			return inString.replaceAll(regex, replace);
		}
		return inString;
	}
	
	/**
	 * @param in {@link String}
	 * @return {@link String}
	 */
	public static String convert(Long in) {
		String returnStr = null;
		if (in != null) {
			returnStr = ParsingConstants.BLANK + in;
		}
		return returnStr;
	}
	/**
	 * if the input is not null then use toString, convert it and return it.
	 * if the input is null return the text that needs to be used when it 
	 * is empty.
	 * @param in {@link String}
	 * @return {@link String}
	 */
	public static String convert(Object in,String textWhenEmpty) {
		String returnStr = null;
		if (in != null) {
			returnStr = ParsingConstants.BLANK + in;
		}
		if(returnStr == null || !StringUtils.hasText(returnStr)) {
			returnStr = textWhenEmpty;
		}
		return returnStr;
	}
	/**
	 * @param in {@link String}
	 * @return 
	 * @return 
	 * @return {@link String}
	 */
	public static <T> T convert(T in,T objectWhenEmpty) {
		if (in == null) {
			return objectWhenEmpty;
		} else {
			return in;
		}
	}	
	/**
	 * @param inLine {@link String}
	 * @return {@link Boolean}
	 */
	public static boolean isNonEmptyCsvLine(String inLine) {
		String outLine = null;
		if (StringUtils.hasText(inLine)) {
			outLine = inLine.replaceAll(ParsingConstants.OUTER_DELIM, ParsingConstants.BLANK);
		}
		return StringUtils.hasText(outLine);
	}

	/**
	 * TODO analyze if this is fast enough..?
	 * @param inLine {@link String}
	 * @return {@link Boolean}
	 */
	public static final boolean isValidCsvLine(String[] inLine) {
		boolean isValid;
		if (inLine != null) {
			for (String inLineStr : inLine) {
				isValid = StringUtils.hasText(inLineStr);
				if (isValid) {
					return isValid;
				}
			}
		}
		return false;
	}
	/**
	 * converts 01/01/2012 to 1/1/2012.
	 * If any stage of cleaning fails, it returns a partially cleaned date.
	 * @param strDate {@link String}
	 * @return {@link String}
	 */
	public static String getCleanedDateString(String strDate) {
		try {
			//month day and year cannot be zero.
			strDate = strDate.replaceAll("/0", "/");
			//for minutes and seconds in hh:mm:ss format.
			strDate = strDate.replaceAll(":00", ":0");
			//for hrs in MM/dd/yyyy hh:mm:ss format.
			strDate = strDate.replaceAll(" 00", " 0");
			//Don't do this.
			//strDate = strDate.replaceAll(" 0", " ");
			if (strDate.indexOf("0") == 0) {
				strDate = strDate.substring(1);
			}
		} catch (Exception e) {
			LOGGER.debug("Cleaning -" + strDate + " Failed", e);
		}
		return strDate;
	}

	/**
	 * @param in {@link String}
	 * @param other {@link String}
	 * @return result {@link Boolean}
	 */
	public static boolean compare(
			String in, String other) {
		boolean result = false;
		if (in != null && other != null) {
			result = in.equals(other);
		}
		return result;
	}
	
	/**
	 * @param inputString {@link String}
	 * @return
	 */
	public static List<String> split(String inputString) {
		List<String> outStrings = new ArrayList<String>();
		if (inputString != null & StringUtils.hasText(inputString)) {
			String[] outStringsArray = inputString.split(ParsingConstants.OUTER_DELIM);
			if (outStringsArray != null && ArrayUtils.isNotEmpty(outStringsArray)) {
				for (String outString : outStringsArray) {
					if(outString != null && StringUtils.hasText(outString)) {
						outStrings.add(outString);
					}
				}
			}
		}
		return outStrings;
	}
	
	public static String convertToString(Set<Long> inList) {
		String result = ParsingConstants.BLANK;
		if(inList != null && CollectionUtils.isNotEmpty(inList)) {
			for(Long inLong:inList) {
				if(StringUtils.hasText(result)) {
					result = result + ParsingConstants.OUTER_DELIM +inLong;
				} else {
					result = result + inLong;
				}
			}
		}
		return result;
	}
	public static String convertStringCollectionToString(Set<String> inList) {
		String result = ParsingConstants.BLANK;
		if(inList != null && CollectionUtils.isNotEmpty(inList)) {
			for(String inLong:inList) {
				if(StringUtils.hasText(result)) {
					result = result + ParsingConstants.OUTER_DELIM +inLong;
				} else {
					result = result + inLong;
				}
			}
		}
		return result;
	}
	public static String convertCamelCaseToUnderScore(String sortColumn) {
		String result = ParsingConstants.BLANK;
		if(sortColumn != null && StringUtils.hasText(sortColumn)) {
			int charIndex = 0;
			while (charIndex < sortColumn.length()) {
				if(Character.isUpperCase(sortColumn.charAt(charIndex))) {
					result = result
							+ ParsingConstants.VIEW_COLUMN_SEP +
							sortColumn.charAt(charIndex);
				} else {
					result = result + 
							sortColumn.charAt(charIndex);
				}
				charIndex ++;
			}
		}
		return result;
	}

	/**
	 * Compares 2 strings ignoring case.
	 * For any empty string conditions returns false.
	 * @param in1
	 * @param in2
	 * @return
	 */
	public static boolean compareLikeIgnoreCase(String in1,
			String in2) {
		boolean result = false;
		if(in1 != null && in2 != null && StringUtils.hasText(in1) && StringUtils.hasText(in2)) {
			in1 = in1.trim();
			in2 = in2.trim();
			result = in1.equalsIgnoreCase(in2);
		}
		return result;
	}
	
}
