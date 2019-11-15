package edu.ku.cete.test.reports;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.Rating;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.OrganizationSummaryReportGenerator;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class OrganizationSummaryReportGeneratorTest extends BaseTest {
	@Autowired
	private OrganizationSummaryReportGenerator sdGenerator;
	
	@Autowired
	private AwsS3Service s3;

	@Test
	public void testSchoolKap() throws Exception{
		StudentReport data = new StudentReport();
		data.setContentAreaName("English Language Arts");
		data.setContentAreaCode("ELA");
		data = getReportData("KAP", false,data);
		data.setStudentFirstName("Matthews");
		data.setStudentLastName("Zoe");
		String path = sdGenerator.generateReportFile(data);
	}
	
	@Test
	public void testS3OperationsForSchoolKap() throws Exception{
		StudentReport data = new StudentReport();
		data.setContentAreaName("English Language Arts");
		data.setContentAreaCode("ELA");
		data = getReportData("KAP", false,data);
		data.setStudentFirstName("Matthews");
		data.setStudentLastName("Zoe");
		String path = sdGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(sdGenerator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}
	
	
	/*
	

	public void testSchoolAmp() throws Exception{
		StudentReportDto data = new StudentReportDto(); 
		data.setContentAreaName("Mathemetics");
		data.setContentAreaCode("M");
		data = getReportData("KAP", false,data);
		String path = sdGenerator.generateReportFile(data);
	}

	public void testSchoolKAPSS() throws Exception{
		StudentReportDto data = new StudentReportDto(); 
		data.setContentAreaName("Socialstudies");
		data.setContentAreaCode("SS");
		data = getReportData("KAP", false,data);
		String path = sdGenerator.generateReportFile(data);
	}
	*/
	/*
	@Test
	public void testDistrictKap() throws Exception{
		StudentReportDto data = new StudentReportDto();
		data.setAttendanceSchoolId(null);		 
		data.setContentAreaName("Science");
		data.setContentAreaCode("Sci");
		 data = getReportData("KAP", true,data);
		String path = sdGenerator.generateReportFile(data);
	}*/
	
	@Test
	public void testDistrictAmp() throws Exception{
		StudentReport data = new StudentReport();
		data.setAttendanceSchoolId(null);
		data.setContentAreaName("English Language Arts");
		data.setContentAreaCode("ELA");
		 data = getReportData("KAP", true,data);
		String path = sdGenerator.generateReportFile(data);
	}
	
	@Test
	public void testS3OperationsForDistrictAmp() throws Exception{
		StudentReport data = new StudentReport();
		data.setAttendanceSchoolId(null);
		data.setContentAreaName("English Language Arts");
		data.setContentAreaCode("ELA");
		 data = getReportData("KAP", true,data);
		String path = sdGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(sdGenerator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}
	


	private StudentReport getReportData(String code, boolean district,StudentReport data) {
		
		data.setAssessmentProgramCode(code);
		data.setStateId(52L);
		data.setDistrictId(62L);
		if(!district) {
			data.setAttendanceSchoolId(72L);
			data.setAttendanceSchoolName("Lakewood Elementry School");
		}
		data.setDistrictName("Bluevalley School District");
		data.setDistrictDisplayIdentifier("1242");		
		data.setCurrentSchoolYear(2017L);
		
		int minMeter = 220;
		int maxMeter = 380;

		data.setMedianScores(new ArrayList<ReportsMedianScore>());
		
		getMedianScore(data, minMeter, maxMeter, "Grade 3",3l, false,17);
		getMedianScore(data, minMeter, maxMeter, "Grade 4",4l, true,9);
		getMedianScore(data, minMeter, maxMeter, "Grade 5",5l, true,15);
		getMedianScore(data, minMeter, maxMeter, "Grade 6",6l, false,14);
		getMedianScore(data, minMeter, maxMeter, "Grade 7",7l, false,30);
		getMedianScore(data, minMeter, maxMeter, "Grade 8",8l, false,20);
		getMedianScore(data, minMeter, maxMeter, "Grade 9",9l, false,12);
		getMedianScore(data, minMeter, maxMeter, "Grade 10",10l, false,24);

		data.setLevels(new ArrayList<LevelDescription>());
		data.getLevels().add(new LevelDescription());
		data.getLevels().add(new LevelDescription());
		data.getLevels().add(new LevelDescription());
		data.getLevels().add(new LevelDescription());

		data.getLevels().get(0).setId(1L);
		data.getLevels().get(0).setLevel(1L);
		data.getLevels().get(0).setLevelName("Partially Meets Standards");
		data.getLevels().get(0).setLevelLowCutScore(new Long(minMeter));
		data.getLevels().get(0).setLevelHighCutScore(260L);
		data.getLevels().get(1).setId(2L);
		data.getLevels().get(1).setLevel(2L);
		data.getLevels().get(1).setLevelName("Partially Meets Standards");
		data.getLevels().get(1).setLevelLowCutScore(261L);
		data.getLevels().get(1).setLevelHighCutScore(320L);
		data.getLevels().get(2).setId(3L);
		data.getLevels().get(2).setLevel(3L);
		data.getLevels().get(2).setLevelName("Meets Standards");
		data.getLevels().get(2).setLevelLowCutScore(321L);
		data.getLevels().get(2).setLevelHighCutScore(345L);
		data.getLevels().get(3).setId(4L);
		data.getLevels().get(3).setLevel(4L);
		data.getLevels().get(3).setLevelName("Meets Standards");
		data.getLevels().get(3).setLevelLowCutScore(346L);
		data.getLevels().get(3).setLevelHighCutScore(new Long(maxMeter));
		
		data.setPercentByLevels(new ArrayList<ReportsPercentByLevel>());
		populatePercentByLevels(data, 72L, 7, "Grade 3");
		populatePercentByLevels(data, 62L, 3020, "Grade 3");
		populatePercentByLevels(data, 52L,10343, "Grade 3");
		
		populatePercentByLevels(data, 72L, 17, "Grade 4");
		populatePercentByLevels(data, 62L, 3320, "Grade 4");
		populatePercentByLevels(data, 52L,11343, "Grade 4");
		
		populatePercentByLevels(data, 72L, 17, "Grade 5");
		populatePercentByLevels(data, 62L, 3320, "Grade 5");
		populatePercentByLevels(data, 52L,11343, "Grade 5");
		
		populatePercentByLevels(data, 72L, 17, "Grade 6");
		populatePercentByLevels(data, 62L, 3320, "Grade 6");
		populatePercentByLevels(data, 52L,11343, "Grade 6");
		
		populatePercentByLevels(data, 72L, 17, "Grade 7");
		populatePercentByLevels(data, 62L, 3320, "Grade 7");
		populatePercentByLevels(data, 52L,11343, "Grade 7");
		
		populatePercentByLevels(data, 72L, 17, "Grade 8");
		populatePercentByLevels(data, 62L, 3320, "Grade 8");
		populatePercentByLevels(data, 52L,11343, "Grade 8");
		
		
		populatePercentByLevels(data, 72L, 17, "Grade 9");
		populatePercentByLevels(data, 62L, 3320, "Grade 9");
		populatePercentByLevels(data, 52L,11343, "Grade 9");
		
		populatePercentByLevels(data, 72L, 17, "Grade 10");
		populatePercentByLevels(data, 62L, 3320, "Grade 10");
		populatePercentByLevels(data, 52L,11343, "Grade 10");
		
		data.setTestLevelSubscoreBuckets(new ArrayList<ReportSubscores>());
		if(data.getContentAreaCode().equals("ELA")) {
			ArrayList<String> gradesratings=new ArrayList<>();
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("OVERALL READING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("The reading portion requires students to read and analyze literary and informational texts and answer questions related to main ideas, text structure, language use, word meanings, and making and supporting conclusions.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
			
			ArrayList<Rating> ratings=new ArrayList<Rating>();			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
						
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(2L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("Literary Texts");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("This portion requires students to answer questions based on literary texts (such as stories and poems).");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
							
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(true);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(3L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("Informational Texts");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("This portion requires students to answer questions based on informational texts (such as science articles and historical speeches).");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(true);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(4L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("Making and Supporting Conclusions");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to read literary and informational texts and then make conclusions and use details and evidence to support ideas.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(true);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(5L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("Main Idea");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(5);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to read literary and informational texts and then determine central ideas, key events, and topics and identify supporting details.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(true);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
						
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(true);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(6L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("OVERALL WRITING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(6);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("The writing portion requires students to read short writing samples and answer questions related to revising, editing, vocabulary, and language use. (This portion does not include the on-demand writing task.)");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
						
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(7L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("Revising");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(7);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to revise provided text by applying writing skills, including using specific story-telling strategies, revising text into a logical order, adding context and detail, and identifying words or phrases to strengthen the text.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(4);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(true);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(8L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("Editing");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(8);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to clarify messages in a variety of texts by following grade-appropriate grammar, capitalization, punctuation, and spelling rules.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(true);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(9L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("Vocabulary and Language Use");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(9);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to revise texts by using accurate language and vocabulary that is appropriate to a text’s purpose and audience.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(true);
			
			//data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(10L);
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("OVERALL LISTENING");
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(9);
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("The listening portion requires students to listen to a recording and show understanding by interpreting the speaker’s point of view, identifying central ideas and supporting evidence, and making conclusions.");
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			
			//ratings=populateRatings();			
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
						
			//gradesratings=Populategraderatings(data.getMedianScores().size());
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			//data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
		}else if(data.getContentAreaCode().equals("M")){
			ArrayList<String> gradesratings=new ArrayList<>();
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CONCEPTS AND PROCEDURES: Geometry");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to describe the features of geometric figures, compare figures, apply geometric theorems, and solve real-world problems by knowing formulas and applying them to figures.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);
			
			ArrayList<Rating> ratings=new ArrayList<Rating>();			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
						
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(2L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CONCEPTS AND PROCEDURES: Algebra");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to solve complex equations; construct, interpret, and graph equations that model data and represent relationships; and use equations to solve real-world problems.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
						
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(3L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("PROBLEM SOLVING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to solve a range of complex problems using knowledge, problem-solving strategies, and mathematical tools.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(4L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("MODELING AND DATA ANALYSIS");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to analyze complex, real-world situations, to construct and use mathematical models to solve problems, and to interpret results in the context of a situation.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(5L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("COMMUNICATING REASONING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(5);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to explain their reasoning, defend their answers, critique the reasoning of others, and ask clarifying questions.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			
			ratings=populateRatings();			
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
			
			gradesratings=Populategraderatings(data.getMedianScores().size());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
			
		}
		else if(data.getContentAreaCode().equals("Sci")){			
				ArrayList<String> gradesratings=new ArrayList<>();
				data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("PHYSICAL AND CHEMICAL SCIENCES");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions ask students about chemical reactions and the structure and properties of matter. They require students to understand and apply practices in science and engineering, their core ideas, and concepts that crosscut science disciplines.");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);

				ArrayList<Rating> ratings=new ArrayList<Rating>();			
				ratings=populateRatings();			
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
							
				gradesratings=Populategraderatings(data.getMedianScores().size());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
				
				
				data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(2L);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("LIFE SCIENCES");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions ask about the use and movement of matter and energy among organisms and relationships between organisms and ecosystems. They require students to understand and apply practices in science and engineering, their core ideas, and concepts that crosscut science disciplines.");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);

				ratings=populateRatings();			
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
				
				gradesratings=Populategraderatings(data.getMedianScores().size());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
				
				
				data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(3L);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("EARTH AND SPACE SCIENCES");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions ask about the systems of the Earth, human impact on the environment, and the solar system and universe. They require students to understand and apply practices in science and engineering, their core ideas, and concepts that crosscut science disciplines.");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(true);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
				
				ratings=populateRatings();			
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
				
				gradesratings=Populategraderatings(data.getMedianScores().size());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
				
				data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("PHYSICAL AND CHEMICAL SCIENCES");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions ask students about chemical reactions and the structure and properties of matter. They require students to understand and apply practices in science and engineering, their core ideas, and concepts that crosscut science disciplines.");
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);

				ratings=populateRatings();			
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
							
				gradesratings=Populategraderatings(data.getMedianScores().size());
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
				data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
				
		}
		
		
		else {
		ArrayList<String> gradesratings=new ArrayList<>();
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 1: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
		
		ArrayList<Rating> ratings=new ArrayList<Rating>();			
		ratings=populateRatings();			
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
		
		gradesratings=Populategraderatings(data.getMedianScores().size());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
		
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(2L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 2: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
		
		ratings=populateRatings();			
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
		
		gradesratings=Populategraderatings(data.getMedianScores().size());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
	
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(3L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 3: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(true);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);
		
		ratings=populateRatings();			
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
		
		gradesratings=Populategraderatings(data.getMedianScores().size());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
		
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(4L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 4: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
		
		ratings=populateRatings();			
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
		
		gradesratings=Populategraderatings(data.getMedianScores().size());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
		
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(5L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 5: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(4);
		
		ratings=populateRatings();			
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setGradeRatings(ratings);
		
		gradesratings=Populategraderatings(data.getMedianScores().size());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubsoreGradeRating(gradesratings);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setChildsubscore(false);
		
		}
		
		return data;
	}

	protected void getMedianScore(StudentReport data, int minMeter, int maxMeter, String grade,Long gradeId, boolean privacy,Integer studentCount) {
		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(data.getMedianScores().size()-1).setOrganizationId(72L);
		data.getMedianScores().get(data.getMedianScores().size()-1).setStandardError(new BigDecimal("3.5"));
		data.getMedianScores().get(data.getMedianScores().size()-1).setOrgTypeCode("SCH");
		data.getMedianScores().get(data.getMedianScores().size()-1).setStudentCount(studentCount);
		data.getMedianScores().get(data.getMedianScores().size()-1).setGradeId(gradeId);
		data.getMedianScores().get(data.getMedianScores().size()-1).setGradeName(grade);
		if(!privacy)
			data.getMedianScores().get(data.getMedianScores().size()-1).setScore(randLong(minMeter,maxMeter));

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(data.getMedianScores().size()-1).setOrganizationId(62L);
		data.getMedianScores().get(data.getMedianScores().size()-1).setStandardError(new BigDecimal("2.5"));
		data.getMedianScores().get(data.getMedianScores().size()-1).setOrgTypeCode("DT");
		data.getMedianScores().get(data.getMedianScores().size()-1).setStudentCount(165);
		data.getMedianScores().get(data.getMedianScores().size()-1).setGradeName(grade);
		data.getMedianScores().get(data.getMedianScores().size()-1).setGradeId(gradeId);
		if(!(data.getAttendanceSchoolId() == null && privacy)) {
			data.getMedianScores().get(data.getMedianScores().size()-1).setScore(randLong(minMeter,maxMeter));
		}

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(data.getMedianScores().size()-1).setOrganizationId(52L);
		data.getMedianScores().get(data.getMedianScores().size()-1).setStandardError(new BigDecimal("4.5"));
		data.getMedianScores().get(data.getMedianScores().size()-1).setOrgTypeCode("ST");
		data.getMedianScores().get(data.getMedianScores().size()-1).setStudentCount(4545);
		data.getMedianScores().get(data.getMedianScores().size()-1).setGradeName(grade);
		data.getMedianScores().get(data.getMedianScores().size()-1).setGradeId(gradeId);
		data.getMedianScores().get(data.getMedianScores().size()-1).setScore(randLong(minMeter,maxMeter));
	}

	private void populatePercentByLevels(StudentReport data, Long organizationId, int studentCount, String grade) {
		int firstNumber;
		int secondNumber;
		int thirdNumber;
		int fourthNumber;
		while (true) {
            firstNumber = (int)((Math.random())*51);
            secondNumber = (int)((Math.random())*51);
            thirdNumber= (int)((Math.random())*51);
            fourthNumber= (int)((Math.random())*51);
            if (firstNumber + secondNumber + thirdNumber + fourthNumber == 100 && firstNumber>0 && secondNumber>0&& thirdNumber>0&& fourthNumber>0){
                break;
            }
        }
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(1L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(firstNumber);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Main");
		
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(2L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(secondNumber);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Main");
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(3L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(thirdNumber);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Main");
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(4L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(fourthNumber);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Main");
	
	
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(1L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(50);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("MDPT");
		
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(2L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(1);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("MDPT");
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(3L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(25);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("MDPT");
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(4L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(24);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("MDPT");


		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(1L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(10);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Combined");
		
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(2L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(10);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Combined");
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(3L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(30);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Combined");
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(4L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(50);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setGradeName(grade);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevelType("Combined");

	
	}

	public long randLong(int min, int max) {

		return (long) (Math.floor(Math.random() * (max - min + 1)) + min);
	}
	public ArrayList<String> Populategraderatings(int x){
		ArrayList<String> gradeRatings=new ArrayList<>();
		for(int i=0;i<8;i++){
			Random r = new Random();
			Integer Low = 1;
			Integer High = 5;
			Integer Result = r.nextInt(High-Low) + Low;
			gradeRatings.add(Result.toString());
		}
		return gradeRatings;
	}
	
	public ArrayList<Rating> populateRatings(){
		ArrayList<Rating> ratings=new ArrayList<Rating>();
		for(int i=0;i<8;i++){
			Random r = new Random();
			Integer Low = 1;
			Integer High = 5;
			Integer Result = r.nextInt(High-Low) + Low;			
			Rating rating = new Rating();
			rating.setGradeID(new Long(i));
			rating.setRating(Result);
			rating.setRatingid(new Long(i));
			ratings.add(rating);		
		}
		return ratings;
	}
}
