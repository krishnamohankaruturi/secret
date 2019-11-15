package edu.ku.cete.controller.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import edu.ku.cete.util.DateUtil;

public class DateTestClass {

	public static void main(String[] args) {
		System.out.println("hey man");
		
		System.out.println(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat("08/03/2017 09:45 PM", "UTC",  "MM/dd/yyyy hh:mm a"));
		System.out.println(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat("08/03/2017 09:45 PM", "CST",  "MM/dd/yyyy hh:mm a"));
		Date date = DateUtil.convertStringDatetoSpecificTimeZoneDateFormat("08/03/2017 09:45 PM", "CST",  "MM/dd/yyyy hh:mm a");
		
		   DateFormat dateFormatLocal = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		   dateFormatLocal.setTimeZone(TimeZone.getTimeZone("CST"));
		   
		   try {
			java.util.Date parsedDate = dateFormatLocal.parse("08/03/2017 09:45 PM");
			System.out.println(parsedDate.toString());
			Timestamp time = new Timestamp(parsedDate.getTime());
			System.out.println(time.toString());
			dateFormatLocal.setTimeZone(TimeZone.getTimeZone("UTC"));
			System.out.println(dateFormatLocal.format(parsedDate));
			System.out.println(dateFormatLocal.parse(dateFormatLocal.format(parsedDate)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
