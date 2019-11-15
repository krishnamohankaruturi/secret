package edu.ku.cete.service.impl.report;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class SchoolReportBundleUtil {
	
	public static String sanitizeValues(String value) {
		  return value.replaceAll("[^a-zA-Z0-9.-]", StringUtils.EMPTY);		  
	}
	
	public static String getShortName(String value) {
		if(value.length() > 24) {
			return value.substring(0,24);
		}
		return value;
	}
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		return formatter.format(new Date());
		
	}
}
