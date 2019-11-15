package edu.ku.cete.test.reports;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.OrganizationDetailReportGenerator;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class OrganizationDetailReportGeneratorTest extends BaseTest {
	@Autowired
	private OrganizationDetailReportGenerator sdGenerator;
	
	@Autowired
	private AwsS3Service s3;

	@Test
	public void testSchoolKap() throws Exception{
		StudentReport data = getReportData("KAP");
		String path = sdGenerator.generateReportFile(data);
	}
	
	@Test
	public void testSchoolAmp() throws Exception{
		StudentReport data = getReportData("AMP");
		String path = sdGenerator.generateReportFile(data);
	}
	
	@Test
	public void testDistrictKap() throws Exception{
		StudentReport data = getReportData("KAP");
		data.setAttendanceSchoolId(null);
		String path = sdGenerator.generateReportFile(data);
	}
	
	@Test
	public void testDistrictAmp() throws Exception{
		StudentReport data = getReportData("AMP");
		data.setAttendanceSchoolId(null);
		String path = sdGenerator.generateReportFile(data);
	}
	
	@Test
	public void testS3OperationsForSchoolKap() throws Exception{
		StudentReport data = getReportData("KAP");
		String path = sdGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(sdGenerator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}
	
	@Test
	public void testS3OperationsForSchoolAmp() throws Exception{
		StudentReport data = getReportData("AMP");
		String path = sdGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(sdGenerator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}
	
	@Test
	public void testS3OperationsForDistrictKap() throws Exception{
		StudentReport data = getReportData("KAP");
		data.setAttendanceSchoolId(null);
		String path = sdGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(sdGenerator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}
	
	@Test
	public void testS3OperationsForDistrictAmp() throws Exception{
		StudentReport data = getReportData("AMP");
		data.setAttendanceSchoolId(null);
		String path = sdGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(sdGenerator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}

	private StudentReport getReportData(String code) {
		StudentReport data = new StudentReport();
		data.setAssessmentProgramCode(code);
		//data.setStudentId(1234L);
		//data.setStudentFirstName("AdAM");
		//data.setStudentLastName("Evan");
		//data.setStateStudentIdentifier("23232323232");
		data.setStateId(52L);
		data.setDistrictId(62L);
		data.setAttendanceSchoolId(72L);
		data.setAttendanceSchoolName("Lakewood Elementry School");
		data.setDistrictName("Blue vally School District");
		data.setDistrictDisplayIdentifier("1242");
		data.setGradeName("Grade 4");
		data.setGradeCode("4");
		data.setContentAreaName("English Language Arts");
		data.setContentAreaCode("ELA");
		data.setCurrentSchoolYear(2015L);
		//data.setStandardError(new BigDecimal("3.5"));
		//data.setScaleScore((long)((Math.random())*250));
		data.setLevelId(3L);
		data.setMedianScores(new ArrayList<ReportsMedianScore>());
		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(0).setOrganizationId(72L);
		data.getMedianScores().get(0).setStandardError(new BigDecimal("3.5"));
		data.getMedianScores().get(0).setStudentCount(7);
		data.getMedianScores().get(0).setScore(randLong(100,250));

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(1).setOrganizationId(62L);
		data.getMedianScores().get(1).setStandardError(new BigDecimal("2.5"));
		data.getMedianScores().get(1).setStudentCount(145);
		data.getMedianScores().get(1).setScore(randLong(100,250));

		data.getMedianScores().add(new ReportsMedianScore());
		data.getMedianScores().get(2).setOrganizationId(52L);
		data.getMedianScores().get(2).setStandardError(new BigDecimal("4.5"));
		data.getMedianScores().get(2).setStudentCount(4455);
		data.getMedianScores().get(2).setScore(randLong(100,250));

		data.setLevels(new ArrayList<LevelDescription>());
		data.getLevels().add(new LevelDescription());
		data.getLevels().add(new LevelDescription());
		data.getLevels().add(new LevelDescription());
		data.getLevels().add(new LevelDescription());
		data.getLevels().add(new LevelDescription());

		data.getLevels().get(0).setId(1L);
		data.getLevels().get(0).setLevel(1L);
		data.getLevels().get(0).setLevelName("Partially Meets Standards");
		data.getLevels().get(0).setLevelLowCutScore(100L);
		data.getLevels().get(0).setLevelHighCutScore(130L);
		data.getLevels().get(1).setId(2L);
		data.getLevels().get(1).setLevel(2L);
		data.getLevels().get(1).setLevelName("Partially Meets Standards");
		data.getLevels().get(1).setLevelLowCutScore(130L);
		data.getLevels().get(1).setLevelHighCutScore(170L);
		data.getLevels().get(2).setId(3L);
		data.getLevels().get(2).setLevel(3L);
		data.getLevels().get(2).setLevelName("Meets Standards");
		data.getLevels().get(2).setLevelLowCutScore(170L);
		data.getLevels().get(2).setLevelHighCutScore(200L);
		data.getLevels().get(3).setId(4L);
		data.getLevels().get(3).setLevel(4L);
		data.getLevels().get(3).setLevelName("Meets Standards");
		data.getLevels().get(3).setLevelLowCutScore(200L);
		data.getLevels().get(3).setLevelHighCutScore(250L);
		 data.getLevels().get(4).setId(44L);
		 data.getLevels().get(4).setLevel(5L);
		 data.getLevels().get(4).setLevelName("Level 5");
		 data.getLevels().get(4).setLevelLowCutScore(260L);
		 data.getLevels().get(4).setLevelHighCutScore(300L);
		
		data.setPercentByLevels(new ArrayList<ReportsPercentByLevel>());
		populatePercentByLevels(data, 72L, 7);
		data.getPercentByLevels().remove(data.getPercentByLevels().size()-1);
		data.getPercentByLevels().remove(data.getPercentByLevels().size()-1);
		
		populatePercentByLevels(data, 62L, 3020);
		data.getPercentByLevels().remove(data.getPercentByLevels().size()-1);
		data.getPercentByLevels().remove(data.getPercentByLevels().size()-1);
		data.getPercentByLevels().remove(data.getPercentByLevels().size()-1);
		
		populatePercentByLevels(data, 52L,10343);
		
		int minSubScore = 101,maxSubScore = 119;
		data.setSubscoreBuckets(new ArrayList<ReportSubscores>());
		
		//school
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(72L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("2,3,4");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(72L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("4,5");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(72L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("6,7");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(72L);
		
		//district
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("2,3,4");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);
		
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("4,5");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("6,7");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(62L);
		
		//state
		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("1");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(119l);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("2,3,4");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);
		data.getSubscoreBuckets().add(new ReportSubscores());
		
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setId(1L);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("4,5");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(1);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(119l);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);

		data.getSubscoreBuckets().add(new ReportSubscores());
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreReportDisplayName("6,7");
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubScoreDisplaySequence(2);
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreScaleScore(randLong(minSubScore,maxSubScore));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setSubscoreStandardError(new BigDecimal("3.5"));
		data.getSubscoreBuckets().get(data.getSubscoreBuckets().size()-1).setOrganizationId(52L);
		return data;
	}

	private void populatePercentByLevels(StudentReport data, Long organizationId, int studentCount) {
		int firstNumber = 0;
		int secondNumber = 0;
		int thirdNumber= 0;
		int fourthNumber= 0;
		int fifthNumber= 0;
		while (true) {
            firstNumber = (int)((Math.random())*51);
            secondNumber = (int)((Math.random())*51);
            thirdNumber= (int)((Math.random())*51);
            fourthNumber= (int)((Math.random())*51);
            fifthNumber= (int)((Math.random())*51);
            if (firstNumber + secondNumber + thirdNumber + fourthNumber +fifthNumber == 100){
                break;
            }
        }
//		data.getPercentByLevels().add(new ReportsPercentByLevel());
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(1L);
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(6);
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
//		
//		data.getPercentByLevels().add(new ReportsPercentByLevel());
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(2L);
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(88);
//		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(3L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(55);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(4L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(40);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
		
		data.getPercentByLevels().add(new ReportsPercentByLevel());
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setId(Math.round(Math.random()));
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setLevel(5L);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setOrganizationId(organizationId);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setPercent(5);
		data.getPercentByLevels().get(data.getPercentByLevels().size()-1).setStudentCount((int)((Math.random())*studentCount));
	}

	public long randLong(int min, int max) {

		return (long) (Math.floor(Math.random() * (max - min + 1)) + min);
	}
}
