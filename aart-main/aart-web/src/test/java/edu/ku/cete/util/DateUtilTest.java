/**
 * 
 */
package edu.ku.cete.util;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mahesh
 *
 */
public class DateUtilTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#isBefore(java.util.Date, java.lang.Long, java.util.Date)}.
	 */
	@Test
	public final void testIsOnOrAfterFalse() {
		Long timeInMillis = (long) 2000;
		Date inDate = new Date((new Date()).getTime() - 2*timeInMillis);
		boolean result = DateUtil.isOnOrAfter(inDate, timeInMillis, new Date());
		Assert.assertFalse(" In Date " + inDate +
				" plus time " + timeInMillis + 
				" is not after " + new Date(), result);
	}
	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#isBefore(java.util.Date, java.lang.Long, java.util.Date)}.
	 */
	@Test
	public final void testIsOnOrAfterTrue() {
		Long timeInMillis = (long) 2000;
		Date inDate = new Date((new Date()).getTime() - timeInMillis);
		boolean result = DateUtil.isOnOrAfter(inDate, 2*timeInMillis, new Date());
		Assert.assertTrue(" In Date " + inDate +
				" plus time " + timeInMillis + 
				" is not after " + new Date(), result);
	}
	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#isBefore(java.util.Date, java.lang.Long, java.util.Date)}.
	 */
	@Test
	public final void testIsBeforeTrue() {
		Long timeInMillis = (long) 2000;
		Date inDate = new Date((new Date()).getTime() - 2*timeInMillis);
		boolean result = DateUtil.isBefore(inDate, timeInMillis, new Date());
		Assert.assertTrue(" In Date " + inDate +
				" plus time " + timeInMillis + 
				" is not after " + new Date(), result);
	}
	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#isBefore(java.util.Date, java.lang.Long, java.util.Date)}.
	 */
	@Test
	public final void testIsBeforeFalse() {
		Long timeInMillis = (long) 2000;
		Date inDate = new Date((new Date()).getTime() - timeInMillis);
		boolean result = DateUtil.isBefore(inDate, 2*timeInMillis, new Date());
		Assert.assertFalse(" In Date " + inDate +
				" plus time " + timeInMillis + 
				" is not after " + new Date(), result);
	}
	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#parseAndFail(java.lang.String)}.
	 * @throws AartParseException 
	 */
	//@Test
	public final void testGetNextPeriod() throws AartParseException {
		Date fromDate = DateUtil.parseAndFail(
				"02/22/2013 09:00:00 am","MM/dd/yyyy hh:mm:ss a");
		Date toDate = DateUtil.parseAndFail(
				"02/22/2013 11:00:00 am","MM/dd/yyyy hh:mm:ss a");
		long frequencyDelta = (long) 600000;
		long frequency = (long) 300000;
		List<Date> endingPeriod
		= DateUtil.getNextPeriod(fromDate, toDate, frequencyDelta, frequency);
		System.out.println("Dates are "+endingPeriod);
		Assert.assertNotNull("Ending period not correct",
				endingPeriod);
		Assert.assertTrue("Ending period not correct", endingPeriod.size() == 2);
		Assert.assertTrue("Ending period not correct" + endingPeriod.get(0) +
				"is not before " + endingPeriod.get(1),
				endingPeriod.get(0).before(endingPeriod.get(1)));
		Assert.assertTrue("From Date not correct",
				endingPeriod.get(0).after(fromDate));
		Assert.assertTrue("To Date not correct",
				endingPeriod.get(1).after(toDate));
		Assert.assertTrue("Interval not maintained",
				(endingPeriod.get(1).getTime() - endingPeriod.get(0).getTime()) ==
				(toDate.getTime() - fromDate.getTime())
				);
	}
	//@Test
	public final void testGetNextPeriodReduceMidNight() throws AartParseException, InterruptedException {
		System.out.println("**Next Test testGetNextPeriodReduceMidNight **" + new Date());
		Date fromDate = DateUtil.parseAndFail(
				"02/21/2013 10:00:00 pm","MM/dd/yyyy hh:mm:ss a");
		Date toDate = DateUtil.parseAndFail(
				"02/21/2013 11:00:00 pm","MM/dd/yyyy hh:mm:ss a");
		long frequencyDelta = (long) 120000;
		long frequency = (long) 60000;
		// 3 to 4.
		List<Date> endingPeriod = testDatePair(fromDate, toDate,
				frequencyDelta, frequency);
		String endDateStr = DateUtil.format(endingPeriod.get(1), "MM/dd/yyyy hh:mm:ss a", null);
		System.out.println("Formatting "+endingPeriod.get(1) + "resulted in "+endDateStr);
	}
	//@Test
	public final void testGetNextPeriodReduceNoon() throws AartParseException, InterruptedException {
		System.out.println("**Next Test testGetNextPeriodReduceNoon **" + new Date());
		Date fromDate = DateUtil.parseAndFail(
				"02/21/2013 10:00:00 am","MM/dd/yyyy hh:mm:ss a");
		Date toDate = DateUtil.parseAndFail(
				"02/21/2013 11:00:00 am","MM/dd/yyyy hh:mm:ss a");
		long frequencyDelta = (long) 120000;
		long frequency = (long) 60000;
		// 3 to 4.
		List<Date> endingPeriod = testDatePair(fromDate, toDate,
				frequencyDelta, frequency);
		String endDateStr = DateUtil.format(endingPeriod.get(1), "MM/dd/yyyy hh:mm:ss a", null);
		System.out.println("Formatting "+endingPeriod.get(1) + "resulted in "+endDateStr);
	}	
	@Test
	public final void testGetNextPeriodReduceToFrequency() throws AartParseException, InterruptedException {
		System.out.println("**Next testGetNextPeriodReduceToFrequency **" + new Date());
		Date fromDate = DateUtil.parseAndFail(
				"02/25/2013 09:00:00 am","MM/dd/yyyy hh:mm:ss a");
		Date toDate = DateUtil.parseAndFail(
				"02/25/2013 10:00:00 am","MM/dd/yyyy hh:mm:ss a");
		long frequencyDelta = (long) 120000;
		long frequency = (long) 60000;
		// 3 to 4.
		List<Date> endingPeriod = testDatePair(fromDate, toDate,
				frequencyDelta, frequency);
		//1 to 3.
		endingPeriod = testDatePair(endingPeriod.get(0), endingPeriod.get(1),
				 frequencyDelta, frequency);	
		//3 to 3:40
		endingPeriod = testDatePair(endingPeriod.get(0), endingPeriod.get(1),
				 frequencyDelta, frequency);
		//
		endingPeriod = testDatePair(endingPeriod.get(0), endingPeriod.get(1),
				 frequencyDelta, frequency);
		Thread.sleep(frequency);
		//3:40 to 3:40 + 500 ms
		endingPeriod = testDatePair(endingPeriod.get(0), endingPeriod.get(1),
				frequencyDelta, frequency);	
		Thread.sleep(frequency);
		//3:40 to 3:40 + 500 ms
		endingPeriod = testDatePair(endingPeriod.get(0), endingPeriod.get(1),
				frequencyDelta, frequency);	
	}
	
	public List<Date> testDatePair(Date fromDate,Date toDate, long frequencyDelta, long frequency) {
		List<Date> endingPeriod
		= DateUtil.getNextPeriod(fromDate, toDate,frequencyDelta, frequency);
		System.out.println("Dates are "+endingPeriod);
		Assert.assertNotNull("Ending period not correct",
				endingPeriod);
		Assert.assertTrue("Ending period not correct", endingPeriod.size() == 2);
		Assert.assertTrue("Ending period not correct" + endingPeriod.get(0) +
				"is not before " + endingPeriod.get(1),
				endingPeriod.get(0).before(endingPeriod.get(1)));
		Assert.assertTrue("From Date not correct",
				endingPeriod.get(0).after(fromDate));
		Assert.assertTrue("To Date not correct",
				endingPeriod.get(1).after(toDate));
//		Assert.assertTrue("Interval not maintained i/p" + fromDate + " to " + toDate +
//				" output "+endingPeriod,
//				(endingPeriod.get(1).getTime() - endingPeriod.get(0).getTime()) ==
//				(toDate.getTime() - fromDate.getTime())
//				);
		return endingPeriod;
	}
	
	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#parseAndFail(java.lang.String, java.lang.String)}.
	 * @throws AartParseException 
	 */
	//@Test
	public final void testParseAndFailStringString() throws AartParseException {
		DateUtil.parseAndFail(
				"02/21/2013 00:00:00 am",
				"MM/dd/yyyy hh:mm:ss a");
	}

	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#parse(java.lang.String)}.
	 */
	//@Test
	public final void testParseString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#format(java.util.Date, java.lang.String, java.util.Date)}.
	 */
	//@Test
	public final void testFormatDateStringDate() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#format(java.util.Date, java.lang.String)}.
	 */
	//@Test
	public final void testFormatDateString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#format(java.lang.String)}.
	 */
	//@Test
	public final void testFormatString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#formatCurrentTimeStamp()}.
	 */
	//@Test
	public final void testFormatCurrentTimeStamp() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.ku.cete.util.DateUtil#parse(java.lang.String, java.lang.String, java.util.Date)}.
	 */
	//@Test
	public final void testParseStringStringDate() {
		fail("Not yet implemented"); // TODO
	}

}
