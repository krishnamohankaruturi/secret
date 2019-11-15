package edu.ku.cete.controller.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.web.support.DailyAccessCodeCSVGenerator;

public class ManageTestSessionControllerTest  extends BaseTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ManageTestSessionControllerTest.class);
	
	@Autowired
	private DailyAccessCodeCSVGenerator dacCSVGenerator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public final void testCase1() throws Exception {
		//fail("Not yet implemented"); // TODO
		
		//Case 1: 
		//Null case
		generateCSVForDAC(null);
	}
	
	@Test
	public final void testCase2() throws Exception {
		//fail("Not yet implemented"); // TODO
		
		//Case 2: 
		//Line 1 has only performance codes (Earlier headers were retrieved from the first line)
		//If only performance codes available, other header columns not displayed
		 List<DailyAccessCode> accessCodes = new ArrayList<DailyAccessCode>();
		 
		 DailyAccessCode code = new DailyAccessCode();
		 code.setId(275L);
		 code.setOperationalTestwindowId(10131L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(7L);
		 code.setStageId(3L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("brim3305");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("5");
		 code.setStageName("Performance");
		 code.setStageCode("Prfrm");
		 accessCodes.add(code);
		 
		 generateCSVForDAC(accessCodes);
	}
	
	@Test
	public final void testCase3() throws Exception {
		//fail("Not yet implemented"); // TODO
		
		//Case 3: 
		//All stages available - 5 stages in this case (Stage 1, 2, 3, 4, Performance)
		//Notice the order of columns and their codes
		 List<DailyAccessCode> accessCodes = new ArrayList<DailyAccessCode>();
		 
		 DailyAccessCode code = new DailyAccessCode();
		 code.setId(7179L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line11");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		  
		 code = new DailyAccessCode();   
		 code.setId(7117L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(3L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line15");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Performance");
		 code.setStageCode("Prfrm");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(7180L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line12");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 2");
		 code.setStageCode("Stg2");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(7181L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(4L);
		 code.setPartNumber(3);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line13");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("5");
		 code.setStageName("Stage 3");
		 code.setStageCode("Stg3");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(7182L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(5L);
		 code.setPartNumber(4);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line14");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 4");
		 code.setStageCode("Stg4");
		 accessCodes.add(code); 
		 
		 generateCSVForDAC(accessCodes);
	}
	
	@Test
	public final void testCase4() throws Exception {
		//fail("Not yet implemented"); // TODO
		
		//Case 4: 
		//Line 1- Only performance code
		//Line 2- All 4 stages and no performance code
		 List<DailyAccessCode> accessCodes = new ArrayList<DailyAccessCode>();
		 
		 DailyAccessCode code = new DailyAccessCode();
		 code.setId(275L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(7L);
		 code.setStageId(3L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line1");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("5");
		 code.setStageName("Performance");
		 code.setStageCode("Prfrm");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(7179L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line21");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		  
		 code = new DailyAccessCode(); 
		 code.setId(7180L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line22");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 2");
		 code.setStageCode("Stg2");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(7181L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(4L);
		 code.setPartNumber(3);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line23");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("5");
		 code.setStageName("Stage 3");
		 code.setStageCode("Stg3");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(7182L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(5L);
		 code.setPartNumber(4);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line24");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 4");
		 code.setStageCode("Stg4");
		 accessCodes.add(code); 
		 
		 generateCSVForDAC(accessCodes);
	}
	
	
	@Test
	public final void testCase5() throws Exception {
		//fail("Not yet implemented"); // TODO
		
		//Case 5: 
		//Line 1: Only performance code
		//Line 2: All 5 stages
		//Line 2: 4 stages and no performance code
		
		 List<DailyAccessCode> accessCodes = new ArrayList<DailyAccessCode>();
		 //Line1
		 DailyAccessCode code = new DailyAccessCode();
		 code.setId(111L);
		 code.setOperationalTestwindowId(101L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(7L);
		 code.setStageId(3L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line11");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("5");
		 code.setStageName("Performance");
		 code.setStageCode("Prfrm");
		 accessCodes.add(code);
		 
		 //Line 2
		 code = new DailyAccessCode();  
		 code.setId(211L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line21");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		  
		 code = new DailyAccessCode();   
		 code.setId(212L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(3L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line25");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Performance");
		 code.setStageCode("Prfrm");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(213L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line22");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 2");
		 code.setStageCode("Stg2");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(214L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(4L);
		 code.setPartNumber(3);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line23");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("5");
		 code.setStageName("Stage 3");
		 code.setStageCode("Stg3");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(215L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(5L);
		 code.setPartNumber(4);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line24");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 4");
		 code.setStageCode("Stg4");
		 accessCodes.add(code); 
		 
		 //Line 3
		 code = new DailyAccessCode(); 
		 code.setId(311L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line31");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("10");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(312L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line32");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("10");
		 code.setStageName("Stage 2");
		 code.setStageCode("Stg2");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(313L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(4L);
		 code.setPartNumber(3);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line33");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("5");
		 code.setStageName("Stage 3");
		 code.setStageCode("Stg3");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(314L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(5L);
		 code.setPartNumber(4);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line34");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("10");
		 code.setStageName("Stage 4");
		 code.setStageCode("Stg4");
		 accessCodes.add(code); 
		 
		 generateCSVForDAC(accessCodes);
	}
	
	@Test
	public final void testCase6() throws Exception {
		//fail("Not yet implemented"); // TODO
		
		//Case 6: 
		//Line 1: Only performance code
		//Line 2: All 5 stages
		//Line 3: 4 stages and no performance code
		//Line 4: Only 2 stages
		
		 List<DailyAccessCode> accessCodes = new ArrayList<DailyAccessCode>();
		 //Line1
		 DailyAccessCode code = new DailyAccessCode();
		 code.setId(111L);
		 code.setOperationalTestwindowId(101L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(7L);
		 code.setStageId(3L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line11");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("5");
		 code.setStageName("Performance");
		 code.setStageCode("Prfrm");
		 accessCodes.add(code);
		 
		 //Line 2
		 code = new DailyAccessCode();  
		 code.setId(211L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line21");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		  
		 code = new DailyAccessCode();   
		 code.setId(212L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(3L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line25");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Performance");
		 code.setStageCode("Prfrm");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(213L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line22");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 2");
		 code.setStageCode("Stg2");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(214L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(4L);
		 code.setPartNumber(3);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line23");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("5");
		 code.setStageName("Stage 3");
		 code.setStageCode("Stg3");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(215L);
		 code.setOperationalTestwindowId(1013L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(126L);
		 code.setStageId(5L);
		 code.setPartNumber(4);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line24");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 4");
		 code.setStageCode("Stg4");
		 accessCodes.add(code); 
		 
		 //Line 3
		 code = new DailyAccessCode(); 
		 code.setId(311L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line31");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("10");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(312L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line32");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("10");
		 code.setStageName("Stage 2");
		 code.setStageCode("Stg2");
		 accessCodes.add(code);
		   
		 code = new DailyAccessCode(); 
		 code.setId(313L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(4L);
		 code.setPartNumber(3);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line33");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("5");
		 code.setStageName("Stage 3");
		 code.setStageCode("Stg3");
		 accessCodes.add(code);
		 
		 code = new DailyAccessCode(); 
		 code.setId(314L);
		 code.setOperationalTestwindowId(2486L);
		 code.setContentAreaId(3L);
		 code.setGradeCourseId(126L);
		 code.setStageId(5L);
		 code.setPartNumber(4);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line34");
		 code.setContentAreaName("English Language Arts");
		 code.setGradeCode("10");
		 code.setStageName("Stage 4");
		 code.setStageCode("Stg4");
		 accessCodes.add(code); 
		 
		 //Line4
		 code = new DailyAccessCode(); 
		 code.setId(411L);
		 code.setOperationalTestwindowId(3401L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(64L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line41");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("10");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		  
		 code = new DailyAccessCode(); 
		 code.setId(412L);
		 code.setOperationalTestwindowId(3401L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(64L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line42");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("7");
		 code.setStageName("Stage 2");
		 code.setStageCode("Stg2");
		 accessCodes.add(code);
		 
		 //Line5
		 code = new DailyAccessCode(); 
		 code.setId(511L);
		 code.setOperationalTestwindowId(5401L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(133L);
		 code.setStageId(1L);
		 code.setPartNumber(1);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line51");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("3");
		 code.setStageName("Stage 1");
		 code.setStageCode("Stg1");
		 accessCodes.add(code);
		  
		 code = new DailyAccessCode(); 
		 code.setId(512L);
		 code.setOperationalTestwindowId(5401L);
		 code.setContentAreaId(440L);
		 code.setGradeCourseId(133L);
		 code.setStageId(2L);
		 code.setPartNumber(2);
		 code.setEffectiveDate(java.sql.Date.valueOf("2016-03-17"));
		 code.setAccessCode("line53");
		 code.setContentAreaName("Mathematics");
		 code.setGradeCode("3");
		 code.setStageName("Stage 3");
		 code.setStageCode("Stg3");
		 accessCodes.add(code);
		 
		 generateCSVForDAC(accessCodes);
	}
	
	private void generateCSVForDAC(List<DailyAccessCode> accessCodes) throws Exception 
	{
		String fileName= "Kite_SECURE_Daily_Access_Codes.csv";
		File file = new File("/Users/akommineni/Downloads/"+fileName);
		OutputStream out = new FileOutputStream(file);
		dacCSVGenerator.generateCSV(accessCodes, out);
	}


	@Test
	public final void testGetTestDaysForDailyAccessCodes() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testGetDailyAccessCodes() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDownloadDailyAccessCodesAsPDF() {
		//fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testDownloadDailyAccessCodesAsCSV() throws Exception {
		//fail("Not yet implemented"); // TODO
	}

	private String wrapForCSV(String s) {
		if (s != null && !s.isEmpty() && s.indexOf(',') > -1) {
			return "\"" + s + "\"";
		}
		return s;
	}
}

