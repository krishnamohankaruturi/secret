package edu.ku.cete.test.reports;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.KelpaStudentReportGenerator;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class KelpaStudentReportGeneratorTest extends BaseTest {
	@Autowired
	private KelpaStudentReportGenerator isrGenerator;
	
	@Autowired
	private AwsS3Service s3;
	
	@Test
	public void testKELPA2() throws Exception{
		List<StudentReport> studentReportList = new ArrayList<StudentReport>();
		studentReportList.add(getReportData("KELPA2", 2016L));
		studentReportList.add(getReportData("KELPA2", 2017L));
		
		Map<Long, String> testStatusMap = new HashMap<Long, String>();
		testStatusMap.put(86l, "complete");
		testStatusMap.put(84l, "unused");
		testStatusMap.put(85l, "inprogress");
		
		Map<Long, String> scoringStatusMap = new HashMap<Long, String>();
		scoringStatusMap.put(629l, "IN_PROGRESS");
		scoringStatusMap.put(630l, "PENDING");
		scoringStatusMap.put(631l, "COMPLETED");
		
		Map<String, String> progressionTextMap = new HashMap<String, String>();
		progressionTextMap.put("PF", "Proficient");
		progressionTextMap.put("BLANK", "PENDING");
		progressionTextMap.put("PND", "Progress not Demonstrated");
		progressionTextMap.put("SP", "Satisfactory Progress");
			   
		isrGenerator.generateReportFile(studentReportList, getReportHeader(),getReportDomainPerformanceLevelLists(), testStatusMap, scoringStatusMap, getLevelDescriptions(), progressionTextMap);
	}
	
	@Test
	public void testS3OperationsForKELPA2() throws Exception{
		List<StudentReport> studentReportList = new ArrayList<StudentReport>();
		studentReportList.add(getReportData("KELPA2", 2016L));
		studentReportList.add(getReportData("KELPA2", 2017L));
		
		Map<Long, String> testStatusMap = new HashMap<Long, String>();
		testStatusMap.put(86l, "complete");
		testStatusMap.put(84l, "unused");
		testStatusMap.put(85l, "inprogress");
		
		Map<Long, String> scoringStatusMap = new HashMap<Long, String>();
		scoringStatusMap.put(629l, "IN_PROGRESS");
		scoringStatusMap.put(630l, "PENDING");
		scoringStatusMap.put(631l, "COMPLETED");
		
		Map<String, String> progressionTextMap = new HashMap<String, String>();
		progressionTextMap.put("PF", "Proficient");
		progressionTextMap.put("BLANK", "PENDING");
		progressionTextMap.put("PND", "Progress not Demonstrated");
		progressionTextMap.put("SP", "Satisfactory Progress");
			   
		StudentReport report = isrGenerator.generateReportFile(studentReportList, getReportHeader(),getReportDomainPerformanceLevelLists(), testStatusMap, scoringStatusMap, getLevelDescriptions(), progressionTextMap);
		String fullPath = FileUtil.buildFilePath(isrGenerator.getRootOutputDir(), report.getFilePath());
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}
	
	private StudentReport getReportData(String code,Long schoolYear) {
		StudentReport data = new StudentReport();
		data.setAssessmentProgramCode(code);
		data.setStudentId(1234L);
		data.setStudentFirstName("Naseem");
		data.setStudentLastName("Taylor");
		data.setStateStudentIdentifier("000000000");
		data.setStateId(52L);
		data.setDistrictId(62L);
		data.setAttendanceSchoolName("Marysville Elementary");
		data.setAttendanceSchoolId(123L);
		data.setDistrictName("Lorem ipsum Lorem ipsum orem");
		data.setDistrictDisplayIdentifier("D0511");
		
		data.setPerformanceRawscoreIncludeFlag(false);
			
		data.setGradeName("Grade 3");
		data.setGradeCode("3");
		
		data.setContentAreaName("English Language Proficiency");
		data.setContentAreaCode("ELP");
		
		data.setLevel(3l);	
		data.setSchoolYear(schoolYear);
			
		return getDomainPerofrmanceLevelLists(data);
	}
	
	private List<LevelDescription> getLevelDescriptions(){
		
		List<LevelDescription> levelDescriptions = new ArrayList<LevelDescription>();

		LevelDescription levelDescription1 = new LevelDescription(); 
		levelDescription1.setId(1L);
		levelDescription1.setLevel(1L);
		levelDescription1.setDescriptionType("Main");
		levelDescription1.setLevelName("Not proficient");
		levelDescription1.setLevelDescription("Students that are not yet proficient have not attained a level of English language skill necessary to produce, interpret, and collaborate on grade-level content-related academic tasks in English. This is indicated by attaining performance levels of Beginning and Intermediate in all four domains. Students not proficient are eligible for ongoing program support.");

		levelDescriptions.add(levelDescription1);
		
		LevelDescription levelDescription2 = new LevelDescription();
		levelDescription2.setId(2L);
		levelDescription2.setLevel(2L);
		levelDescription2.setDescriptionType("Main");
		levelDescription2.setLevelName("Nearly proficient");
		levelDescription2.setLevelDescription("Students are nearly proficient when they approach a level of English language skill necessary to produce, interpret, and collaborate, on grade-level content related academic tasks in English. This is indicated by attaining performance levels with above Early Intermediate that does not meet the requirements to be proficient. Nearly proficient students are eligible for ongoing program support.");

		levelDescriptions.add(levelDescription2);
		
		LevelDescription levelDescription3 = new LevelDescription();
		levelDescription3.setId(3L);
		levelDescription3.setLevel(3L);
		levelDescription3.setDescriptionType("Main");
		levelDescription3.setLevelName("Proficient");
		levelDescription3.setLevelDescription("Students are proficient when they attain a level of English language skill necessary to independently produce, interpret, collaborate on, and succeed in grade-level content-related academic tasks in English. This is indicated by attaining performance levels of Early Advanced or higher in all domains.");
		
		levelDescriptions.add(levelDescription3);
		
		return levelDescriptions;
	}

	private Map<String, String> getReportHeader(){
		 Map<String, String> staticPDFcontentMap = new HashMap<String, String>();
		 staticPDFcontentMap.put("KEPLA_P_2", "This report shows and explains the student’s performance on the Kansas English Language Proficiency Assessment (KELPA2). The KELPA2 measures growth in English language proficiency to ensure all English learners (ELs) are prepared for academic success. This report provides performance levels on each domain tested: speaking, writing, listening, and reading, as well as an overall proficiency determination. These results are used by the teachers, the school, and the school district in planning the student’s level of support and participation in the EL program.");
		return staticPDFcontentMap;
	}
	
	private List<Category> getReportDomainPerformanceLevelLists(){
		List<Category> domainPerformanceLevelLists = new ArrayList<Category>();
		
		Category category5 = new Category(); 
		category5.setCategoryName("Advanced");
		category5.setCategoryCode("5");
		category5.setCategoryDescription("Exhibits superior English language skills.");
		
		Category category4 = new Category(); 
		category4.setCategoryName("Early Advanced");
		category4.setCategoryCode("4");
		category4.setCategoryDescription("Demonstrates English language skills required for engagement with grade-level academic content instruction at a level comparable to non-ELs.");
		
		
		Category category3 = new Category(); 
		category3.setCategoryName("Intermediate");
		category3.setCategoryCode("3");
		category3.setCategoryDescription("Applies some grade-level English language skills and will benefit from EL program support.");
		
		
		Category category2 = new Category(); 
		category2.setCategoryName("Early Intermediate");
		category2.setCategoryCode("2");
		category2.setCategoryDescription("Presents evidence of developing grade-level English language skills and will benefit from EL Program support.");
		
		Category category1 = new Category(); 
		category1.setCategoryName("Beginning");
		category1.setCategoryCode("1");
		category1.setCategoryDescription("Displays few grade-level English language skills and will benefit from EL program support.");
		
		domainPerformanceLevelLists.add(category5);
		domainPerformanceLevelLists.add(category4);
		domainPerformanceLevelLists.add(category3);
		domainPerformanceLevelLists.add(category2);
		domainPerformanceLevelLists.add(category1);
		
		return domainPerformanceLevelLists;
	}
	
	private StudentReport getDomainPerofrmanceLevelLists(StudentReport data){
		
		if(data.getSchoolYear()==2016L){
			
			data.setSchoolYear(2017l);
			data.setSpeakingLevel(2l);
			data.setWritingLevel(3l);
			data.setListeningLevel(2l);
			data.setReadingLevel(2l);		
			
			data.setSpeakingTestStatus(86l);
			data.setWritingTestStatus(86l);
			data.setListeningTestStatus(86l);
			data.setReadingTestStatus(86l);	
			
			data.setSpeakingScoringStatus(631l);
			data.setWritingScoringStatus(631l);
			
		}
		else if(data.getSchoolYear()==2017L){
			
			data.setSchoolYear(2017l);
			data.setSpeakingLevel(2l);
			data.setWritingLevel(3l);
			data.setListeningLevel(2l);
			data.setReadingLevel(2l);		
			
			data.setSpeakingTestStatus(86l);
			data.setWritingTestStatus(86l);
			data.setListeningTestStatus(86l);
			data.setReadingTestStatus(86l);	
			
			data.setSpeakingScoringStatus(631l);
			data.setWritingScoringStatus(631l);
			
		}else if(data.getSchoolYear()==2018L){
			
			data.setSchoolYear(2018l);
			data.setSpeakingLevel(3l);
			data.setWritingLevel(3l);
			data.setListeningLevel(3l);
			data.setReadingLevel(2l);
			
			data.setSpeakingTestStatus(84l);
			data.setWritingTestStatus(84l);
			data.setListeningTestStatus(84l);
			data.setReadingTestStatus(84l);
			
			data.setSpeakingScoringStatus(631l);
			data.setWritingScoringStatus(631l);
			
			data.setLevel(data.getLevel());
		}
		
		return data;
	}
	
	public long randLong(int min, int max) {
		return (long) (Math.floor(Math.random() * (max - min + 1)) + min);
	}	
}
