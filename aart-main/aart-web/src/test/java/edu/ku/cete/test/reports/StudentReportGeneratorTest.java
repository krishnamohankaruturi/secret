package edu.ku.cete.test.reports;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.ActScoringDescription;
import edu.ku.cete.domain.report.ActScoringLevel;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.StudentReportGenerator;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class StudentReportGeneratorTest extends BaseTest {
	@Autowired
	private StudentReportGenerator isrGenerator;
	
	@Autowired
	private AwsS3Service s3;
	
	@Test
	public void testKap() throws Exception{
		StudentReport data = getReportData("KAP");
		isrGenerator.generateReportFile(data);
	}
	
	@Test
	public void testS3OperationsKap() throws Exception{
		StudentReport data = getReportData("KAP");
		String path = isrGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(isrGenerator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		//remove the test file from s3
		s3.deleteObject(fullPath);
	}
	
	/*@Test
	public void testMath() throws Exception{
		StudentReportDto data = getReportData("KAP");
		isrGenerator.generateMathReportFile(data);
	}*/
	/*
	@Test
	public void testAmp() throws Exception{
		StudentReportDto data = getReportData("AMP");
		isrGenerator.generateReportFile(data);
	}
*/
	private StudentReport getReportData(String code) {
		StudentReport data = new StudentReport();
		data.setAssessmentProgramCode(code);
		data.setStudentId(1234L);
		data.setStudentFirstName("Matthews");
		data.setStudentLastName("Zoe");
		data.setStateStudentIdentifier("000000000");
		data.setStateId(52L);
		data.setDistrictId(62L);
		data.setAttendanceSchoolName("Marysville High");
		data.setAttendanceSchoolId(123L);
		data.setDistrictName(" Lorem ipsum Lorem ipsum orem");
		data.setDistrictDisplayIdentifier("D0511");
		
		data.setPerformanceRawscoreIncludeFlag(false);
		//data.setPerformanceRawscoreIncludeFlag(true);
		
		data.setGradeName("Grade 3");
		data.setGradeCode("3");
		
		//data.setGradeName("Grade 10");
		//data.setGradeCode("10");
		
		data.setContentAreaName("English Language Arts");
		data.setContentAreaCode("ELA");
		
		//data.setContentAreaName("Mathematics");
		//data.setContentAreaCode("M");
		
		//data.setContentAreaName("History, Government and Social Studies");
		//data.setContentAreaCode("SS");
				
		
		data.setCurrentSchoolYear(2018L);
		data.setStandardError(new BigDecimal(".545"));
		
		//data.setScaleScore(null);
		data.setScaleScore(360l);
		//data.setIncompleteStatus(true);
		//data.setLevelId(3L);
		
		
		data.setPreviousYearLevelId(21L);
		data.setPrevLevelString("3");
		data.setCombinedLevel(new BigDecimal("3.0"));
		data.setMdptScore(new BigDecimal("2.0"));
		
		/*SuppressedLevel supp = batchReportProcessService.getSuppressedLevel(440L, 170L);
		if (supp != null) {
			data.setPrevLevelString("Suppressed");
		} else {
			data.setPrevLevelString("3");
		}*/
		
		if(code.equals("AMP")) {
			//data.setContentAreaName("English Language Arts");
			//data.setContentAreaCode("ELA");
		}
		
		//DE18542 add two line
		data.setTransferred(false);
		data.setIncompleteStatus(true);
		//data.setTransferred(false);
		//data.setIncompleteStatus(false);
		
		data.setMedianScores(new ArrayList<ReportsMedianScore>());
		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(0).setOrganizationId(123L);
		data.getMedianScores().get(0).setStandardError(new BigDecimal("3.5"));
		data.getMedianScores().get(0).setStudentCount(45);
		data.getMedianScores().get(0).setScore(randLong(220,380));

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(1).setOrganizationId(62L);
		data.getMedianScores().get(1).setStandardError(new BigDecimal("2.5"));
		data.getMedianScores().get(1).setStudentCount(145);
		data.getMedianScores().get(1).setScore(randLong(220,380));

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(2).setOrganizationId(52L);
		data.getMedianScores().get(2).setStandardError(new BigDecimal("4.5"));
		data.getMedianScores().get(2).setStudentCount(44455);
		data.getMedianScores().get(2).setScore(randLong(220,380));
		
		//data.setLevels(null);
		//data.getLevels().add(null);
		data.setLevels(new ArrayList<LevelDescription>());
		data.getLevels().add(new LevelDescription());

		data.getLevels().get(data.getLevels().size()-1).setId(1L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(1L);
		data.getLevels().get(data.getLevels().size()-1).setDescriptionType("Main");
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Partially Meets Standards");
//		data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ read and understand moderately complex grade-level texts ~ summarize themes ~ identify implied or clear details to support an idea ~ determine meanings of more difficult words and complex figurative language ~ identify literary elements and text structures and their impact on meaning ~ determine point of view or purpose ~ revise or edit a text to use academic language and correct grammar, punctuation, and spelling ~ organize a text using sequence and logic ~ determine if information is relevant ~ use strategies to elaborate on ideas and structure texts");
//		data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ lorem ipsum dolor sit amet, consectetur adipiscing. ~ fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.  ~ morbi imperdiet erat at arcu efficitur ornare. Cras ut luctus ligula,  ~ finibus tristique diam. Mauris non placerat libero. quisque egestas ~ euismod orci, id lacinia magna. Etiam congue feugiat orci, eget sollicitudin ante. Mauris ut tellus sed dui condimentum m ~ olestie. Mauris fringilla tempor dui, sed dapibus erat eleifend a. Aliqu ~ am maximus ante ut tortor luctus, vel elementum ex ornare. Nam tincidunt scelerisque augue, sed porta nibh rhoncus con");
		
		if(data.getContentAreaCode().equals("ELA")) {
			if(data.getLevels()== null || data.getLevels().size()==0 ){
				data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this ");
			}else{
				data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ read and understand moderately complex grade-level texts ~ summarize themes ~ identify implied or clear details to support an idea ~ determine meanings of more difficult words and complex figurative language ~ identify literary elements and text structures and their impact on meaning ~ determine point of view or purpose ~ revise or edit a text to use academic language and correct grammar, punctuation, and spelling ~ organize a text using sequence and logic ~ determine if information is relevant ~ use strategies to elaborate on ideas and structure texts");	
			}
			
		} else if(data.getContentAreaCode().equals("M")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ add, subtract, and multiply polynomials (expressions that include variables and exponents) ~ create and use linear, quadratic, simple rational, and exponential equations to model situations ~ graph exponential, quadratic, and absolute-value functions and systems of linear inequalities or quadratic equations ~ solve equations for a given quantity ~ interpret key features of graphs ~ determine rate of change over an interval ~ identify errors in proofs ~ solve real-world volume problems ~ use appropriate statistics to compare sets of data");
		}else{
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ lorem ipsum dolor sit amet, consectetur adipiscing. ~ fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.  ~ morbi imperdiet erat at arcu efficitur ornare. Cras ut luctus ligula,  ~ finibus tristique diam. Mauris non placerat libero. quisque egestas ~ euismod orci, id lacinia magna. Etiam congue feugiat orci, eget sollicitudin ante. Mauris ut tellus sed dui condimentum m ~ olestie. Mauris fringilla tempor dui, sed dapibus erat eleifend a. Aliqu ~ am maximus ante ut tortor luctus, vel elementum ex ornare. Nam tincidunt scelerisque augue, sed porta nibh rhoncus con");
		}
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(220L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(274L);

		data.getLevels().add(new LevelDescription());
		data.getLevels().get(data.getLevels().size()-1).setId(2L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(2L);
		data.getLevels().get(data.getLevels().size()-1).setDescriptionType("Main");
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Partially Meets Standards");
		//data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ read and understand moderately complex grade-level texts ~ summarize themes ~ identify implied or clear details to support an idea ~ determine meanings of more difficult words and complex figurative language ~ identify literary elements and text structures and their impact on meaning ~ determine point of view or purpose ~ revise or edit a text to use academic language and correct grammar, punctuation, and spelling ~ organize a text using sequence and logic ~ determine if information is relevant ~ use strategies to elaborate on ideas and structure texts");
		//data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ lorem ipsum dolor sit amet, consectetur adipiscing. ~ fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.  ~ morbi imperdiet erat at arcu efficitur ornare. Cras ut luctus ligula,  ~ finibus tristique diam. Mauris non placerat libero. quisque egestas ~ euismod orci, id lacinia magna. Etiam congue feugiat orci, eget sollicitudin ante. Mauris ut tellus sed dui condimentum m ~ olestie. Mauris fringilla tempor dui, sed dapibus erat eleifend a. Aliqu ~ am maximus ante ut tortor luctus, vel elementum ex ornare. Nam tincidunt scelerisque augue, sed porta nibh rhoncus con");
		if(data.getContentAreaCode().equals("ELA")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically  read and ~ understand moderately complex grade-level texts ~ summarize themes ~ identify implied or clear details to support an idea ~ determine meanings of more difficult words and complex figurative language ~ identify literary elements and text structures and their impact on meaning ~ determine point of view or purpose ~ revise or edit a text to use academic language and correct grammar, punctuation, and spelling ~ organize a text using sequence and logic ~ determine if information is relevant ~ use strategies to elaborate on ideas and structure texts");
		} else if(data.getContentAreaCode().equals("M")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ add, subtract, and multiply polynomials (expressions that include variables and exponents) ~ create and use linear, quadratic, simple rational, and exponential equations to model situations ~ graph exponential, quadratic, and absolute-value functions and systems of linear inequalities or quadratic equations ~ solve equations for a given quantity ~ interpret key features of graphs ~ determine rate of change over an interval ~ identify errors in proofs ~ solve real-world volume problems ~ use appropriate statistics to compare sets of data");
		}else{
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ lorem ipsum dolor sit amet, consectetur adipiscing. ~ fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.  ~ morbi imperdiet erat at arcu efficitur ornare. Cras ut luctus ligula,  ~ finibus tristique diam. Mauris non placerat libero. quisque egestas ~ euismod orci, id lacinia magna. Etiam congue feugiat orci, eget sollicitudin ante. Mauris ut tellus sed dui condimentum m ~ olestie. Mauris fringilla tempor dui, sed dapibus erat eleifend a. Aliqu ~ am maximus ante ut tortor luctus, vel elementum ex ornare. Nam tincidunt scelerisque augue, sed porta nibh rhoncus con");
		}
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(275L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(299L);


		data.getLevels().add(new LevelDescription());
		data.getLevels().get(data.getLevels().size()-1).setId(3L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(3L);
		data.getLevels().get(data.getLevels().size()-1).setDescriptionType("Main");
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Meets Standards");
		//data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ read and understand moderately complex grade-level texts ~ summarize themes ~ identify implied or clear details to support an idea ~ determine meanings of more difficult words and complex figurative language ~ identify literary elements and text structures and their impact on meaning ~ determine point of view or purpose ~ revise or edit a text to use academic language and correct grammar, punctuation, and spelling ~ organize a text using sequence and logic ~ determine if information is relevant ~ use strategies to elaborate on ideas and structure texts");
		data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ lorem ipsum dolor sit amet, consectetur adipiscing. ~ fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.  ~ morbi imperdiet erat at arcu efficitur ornare. Cras ut luctus ligula,  ~ finibus tristique diam. Mauris non placerat libero. quisque egestas ~ euismod orci, id lacinia magna. Etiam congue feugiat orci, eget sollicitudin ante. Mauris ut tellus sed dui condimentum m ~ olestie. Mauris fringilla tempor dui, sed dapibus erat eleifend a. Aliqu ~ am maximus ante ut tortor luctus, vel elementum ex ornare. Nam tincidunt scelerisque augue, sed porta nibh rhoncus con");
		if(data.getContentAreaCode().equals("ELA")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ read and understand moderately complex grade-level texts ~ summarize themes ~ identify implied or clear details to support an idea ~ determine meanings of more difficult words and complex figurative language ~ identify literary elements and text structures and their impact on meaning ~ determine point of view or purpose ~ revise or edit a text to use academic language and correct grammar, punctuation, and spelling ~ organize a text using sequence and logic ~ determine if information is relevant ~ use strategies to elaborate on ideas and structure texts");
		} else if(data.getContentAreaCode().equals("M")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ add, subtract, and multiply polynomials (expressions that include variables and exponents) ~ create and use linear, quadratic, simple rational, and exponential equations to model situations ~ graph exponential, quadratic, and absolute-value functions and systems of linear inequalities or quadratic equations ~ solve equations for a given quantity ~ interpret key features of graphs ~ determine rate of change over an interval ~ identify errors in proofs ~ solve real-world volume problems ~ use appropriate statistics to compare sets of data");
		}else{
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ lorem ipsum dolor sit amet, consectetur adipiscing. ~ fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.  ~ morbi imperdiet erat at arcu efficitur ornare. Cras ut luctus ligula,  ~ finibus tristique diam. Mauris non placerat libero. quisque egestas ~ euismod orci, id lacinia magna. Etiam congue feugiat orci, eget sollicitudin ante. Mauris ut tellus sed dui condimentum m ~ olestie. Mauris fringilla tempor dui, sed dapibus erat eleifend a. Aliqu ~ am maximus ante ut tortor luctus, vel elementum ex ornare. Nam tincidunt scelerisque augue, sed porta nibh rhoncus con");
		}
		
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(300L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(334L);

		data.getLevels().add(new LevelDescription());
		data.getLevels().get(data.getLevels().size()-1).setId(4L);
		data.getLevels().get(data.getLevels().size()-1).setLevel(4L);
		data.getLevels().get(data.getLevels().size()-1).setDescriptionType("Main");
		data.getLevels().get(data.getLevels().size()-1).setLevelName("Meets Standards");
		if(data.getContentAreaCode().equals("ELA")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ read and understand moderately complex grade-level texts ~ summarize themes ~ identify implied or clear details to support an idea ~ determine meanings of more difficult words and complex figurative language ~ identify literary elements and text structures and their impact on meaning ~ determine point of view or purpose ~ revise or edit a text to use academic language and correct grammar, punctuation, and spelling ~ organize a text using sequence and logic ~ determine if information is relevant ~ use strategies to elaborate on ideas and structure texts");
		} else if(data.getContentAreaCode().equals("M")) {
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ add, subtract, and multiply polynomials (expressions that include variables and exponents) ~ create and use linear, quadratic, simple rational, and exponential equations to model situations ~ graph exponential, quadratic, and absolute-value functions and systems of linear inequalities or quadratic equations ~ solve equations for a given quantity ~ interpret key features of graphs ~ determine rate of change over an interval ~ identify errors in proofs ~ solve real-world volume problems ~ use appropriate statistics to compare sets of data");
		}else{
			data.getLevels().get(data.getLevels().size()-1).setLevelDescription("Students who perform at this level can typically ~ lorem ipsum dolor sit amet, consectetur adipiscing. ~ fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.  ~ morbi imperdiet erat at arcu efficitur ornare. Cras ut luctus ligula,  ~ finibus tristique diam. Mauris non placerat libero. quisque egestas ~ euismod orci, id lacinia magna. Etiam congue feugiat orci, eget sollicitudin ante. Mauris ut tellus sed dui condimentum m ~ olestie. Mauris fringilla tempor dui, sed dapibus erat eleifend a. Aliqu ~ am maximus ante ut tortor luctus, vel elementum ex ornare. Nam tincidunt scelerisque augue, sed porta nibh rhoncus con");
		}
		
		data.getLevels().get(data.getLevels().size()-1).setLevelLowCutScore(335L);
		data.getLevels().get(data.getLevels().size()-1).setLevelHighCutScore(380L);


		int minSubScore = 101,maxSubScore = 119;
		
		
		
		data.setTestLevelSubscoreBuckets(new ArrayList<ReportSubscores>());
		
		
		if(data.getContentAreaCode().equals("ELA")) {
		
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("OVERALL READING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("The reading portion requires students to read and analyze literary and informational texts and answer questions related to main ideas, text structure, language use, word meanings, and making and supporting conclusions.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
			
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(2L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("READING: Literary Texts");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("This portion requires students to answer questions based on literary texts (such as stories and poems).");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(3L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("READING: Informational Texts");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("This portion requires students to answer questions based on informational texts (such as science articles and historical speeches).");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(4L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("READING: Making and Supporting Conclusions");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to read literary and informational texts and then make conclusions and use details and evidence to support ideas.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(5L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("READING: Main Idea");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(5);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to read literary and informational texts and then determine central ideas, key events, and topics and identify supporting details.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(true);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(6L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("OVERALL WRITING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(6);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("The writing portion requires students to read short writing samples and answer questions related to revising, editing, vocabulary, and language use. (This portion does not include the on-demand writing task.)");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(7L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("WRITING: Revising");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(7);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to revise provided text by applying writing skills, including using specific story-telling strategies, revising text into a logical order, adding context and detail, and identifying words or phrases to strengthen the text.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(4);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(8L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("WRITING: Editing");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(8);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to clarify messages in a variety of texts by following grade-appropriate grammar, capitalization, punctuation, and spelling rules.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(9L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("WRITING: Vocabulary and Language Use");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(9);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to revise texts by using accurate language and vocabulary that is appropriate to a text’s purpose and audience.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(true);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(10L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("OVERALL LISTENING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(9);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("The listening portion requires students to listen to a recording and show understanding by interpreting the speaker’s point of view, identifying central ideas and supporting evidence, and making conclusions.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			
			List<String> actd1=new ArrayList<String>();
			actd1.add("Student's actual KAP grade 10 ELA score");
		
			actd1.add("Student's projected ACT Reading score");
			
			actd1.add("Student's projected ACT English score");
			
			data.setActScoringDescription(actd1);
			
			
			
			ArrayList<ActScoringLevel> actScoringLevel=new ArrayList<ActScoringLevel>();
			
			//Act Level 1
			ActScoringLevel actScoringLevel1=new ActScoringLevel();
			actScoringLevel1.setId(1l);
			actScoringLevel1.setLevelId((long)1);
			ActScoringDescription actScoringDescription1=new ActScoringDescription();
			actScoringDescription1.setMaxvalue(220);
			actScoringDescription1.setMinvalue(170);
			actScoringDescription1.setDescription("Student's actual KAP grade 10 ELA score");
			ActScoringDescription actScoringDescription2=new ActScoringDescription();
			actScoringDescription2.setMaxvalue(380);
			actScoringDescription2.setMinvalue(240);
			actScoringDescription2.setDescription("Student's projected ACT Reading score");
			ActScoringDescription actScoringDescription3=new ActScoringDescription();
			actScoringDescription3.setMaxvalue(450);
			actScoringDescription3.setMinvalue(427);
			actScoringDescription3.setDescription("Student's projected ACT English score");
			
			List<ActScoringDescription> lctScoringDescriptionList1=new ArrayList<ActScoringDescription>();
			lctScoringDescriptionList1.add(actScoringDescription1);
			lctScoringDescriptionList1.add(actScoringDescription2);
			lctScoringDescriptionList1.add(actScoringDescription3);
			actScoringLevel1.setActScoringDescriptions(lctScoringDescriptionList1);
			
			//Act Level 2
			ActScoringLevel actScoringLevel2=new ActScoringLevel();
			actScoringLevel2.setId(2l);
			actScoringLevel2.setLevelId((long)2);
			ActScoringDescription actScoringDescription4=new ActScoringDescription();
			actScoringDescription4.setMaxvalue(382);
			actScoringDescription4.setMinvalue(222);
			actScoringDescription4.setDescription("Student's actual KAP grade 10 ELA score");
			ActScoringDescription actScoringDescription5=new ActScoringDescription();
			actScoringDescription5.setMaxvalue(263);
			actScoringDescription5.setMinvalue(143);
			actScoringDescription5.setDescription("Student's projected ACT Reading score");
			ActScoringDescription actScoringDescription6=new ActScoringDescription();
			actScoringDescription6.setMaxvalue(290);
			actScoringDescription6.setMinvalue(240);
			actScoringDescription6.setDescription("Student's projected ACT English score");
			
			List<ActScoringDescription> lctScoringDescriptionList2=new ArrayList<ActScoringDescription>();
			lctScoringDescriptionList2.add(actScoringDescription4);
			lctScoringDescriptionList2.add(actScoringDescription5);
			lctScoringDescriptionList2.add(actScoringDescription6);
			actScoringLevel2.setActScoringDescriptions(lctScoringDescriptionList2);
	
			//Act Level 3
			ActScoringLevel actScoringLevel3=new ActScoringLevel();
			actScoringLevel3.setId(3l);
			actScoringLevel3.setLevelId((long)3);
			ActScoringDescription actScoringDescription7=new ActScoringDescription();
			actScoringDescription7.setMaxvalue(382);
			actScoringDescription7.setMinvalue(222);
			actScoringDescription7.setDescription("Student's actual KAP grade 10 ELA score");
			ActScoringDescription actScoringDescription8=new ActScoringDescription();
			actScoringDescription8.setMaxvalue(263);
			actScoringDescription8.setMinvalue(143);
			actScoringDescription8.setDescription("Student's projected ACT Reading score");
			ActScoringDescription actScoringDescription9=new ActScoringDescription();
			actScoringDescription9.setMaxvalue(290);
			actScoringDescription9.setMinvalue(240);
			actScoringDescription9.setDescription("Student's projected ACT English score");
			
			
			List<ActScoringDescription> lctScoringDescriptionList3=new ArrayList<ActScoringDescription>();
			lctScoringDescriptionList3.add(actScoringDescription7);
			lctScoringDescriptionList3.add(actScoringDescription8);
			lctScoringDescriptionList3.add(actScoringDescription9);
			actScoringLevel3.setActScoringDescriptions(lctScoringDescriptionList3);
		
			
			//Act Level 4
			ActScoringLevel actScoringLevel4=new ActScoringLevel();
			actScoringLevel4.setId(4l);
			actScoringLevel4.setLevelId((long)4);			
			
			ActScoringDescription actScoringDescription10=new ActScoringDescription();
			actScoringDescription10.setMaxvalue(356);
			actScoringDescription10.setMinvalue(125);
			actScoringDescription10.setDescription("Student's actual KAP grade 10 ELA score");
			ActScoringDescription actScoringDescription11=new ActScoringDescription();
			actScoringDescription11.setMaxvalue(326);
			actScoringDescription11.setMinvalue(236);
			actScoringDescription11.setDescription("Student's projected ACT Reading score");
			ActScoringDescription actScoringDescription12=new ActScoringDescription();
			actScoringDescription12.setMaxvalue(280);
			actScoringDescription12.setMinvalue(122);
			actScoringDescription12.setDescription("Student's projected ACT English score");
			
			List<ActScoringDescription> lctScoringDescriptionList4=new ArrayList<ActScoringDescription>();
			lctScoringDescriptionList4.add(actScoringDescription10);
			lctScoringDescriptionList4.add(actScoringDescription11);
			lctScoringDescriptionList4.add(actScoringDescription12);
			actScoringLevel4.setActScoringDescriptions(lctScoringDescriptionList4);
			
			
			actScoringLevel.add(actScoringLevel1);
			actScoringLevel.add(actScoringLevel2);
			actScoringLevel.add(actScoringLevel3);
			actScoringLevel.add(actScoringLevel4);
			
			
			data.setNoOfActlevels(actScoringLevel.size()+1);
			data.setActScoringLevel(actScoringLevel);
			
			
			
			
		}else if(data.getContentAreaCode().equals("M")){
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CONCEPTS AND PROCEDURES: Geometry");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to describe the features of geometric figures, compare figures, apply geometric theorems, and solve real-world problems by knowing formulas and applying them to figures.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(2L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CONCEPTS AND PROCEDURES: Algebra");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to solve complex equations; construct, interpret, and graph equations that model data and represent relationships; and use equations to solve real-world problems.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(3L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("PROBLEM SOLVING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to solve a range of complex problems using knowledge, problem-solving strategies, and mathematical tools.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
			
			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(4L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("MODELING AND DATA ANALYSIS");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(4);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to analyze complex, real-world situations, to construct and use mathematical models to solve problems, and to interpret results in the context of a situation.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);

			data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(5L);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("COMMUNICATING REASONING");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(5);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("These questions require students to explain their reasoning, defend their answers, critique the reasoning of others, and ask clarifying questions.");
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
			data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
						
		} else {
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(1L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 1: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
				
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(2L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 2: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(1);
		
	
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(3L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 3: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(3);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(true);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(3);
		
		
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(4L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 4: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(2);
		
		
		data.getTestLevelSubscoreBuckets().add(new ReportSubscores());
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setId(5L);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDisplayName("CLAIM 5: Subclaim");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSubScoreReportDescription("Lorem ipsum dolor sit amet, consectetur adipiscing. Fusce eget vestibulum magna. Aenean tempus laoreet tortor sit amet euismod.");
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setSectionLineBelow(false);
		data.getTestLevelSubscoreBuckets().get(data.getTestLevelSubscoreBuckets().size()-1).setRating(4);
			
		}
		return data;
	}

	public long randLong(int min, int max) {
		return (long) (Math.floor(Math.random() * (max - min + 1)) + min);
	}
}
