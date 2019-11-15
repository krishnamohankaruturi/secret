package edu.ku.cete.test.reports;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.report.CpassStudentReportGenerator;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.web.ExternalStudentReportDTO;

public class CpassStudentReportGeneratorTest  extends BaseTest{
	@Autowired
	private CpassStudentReportGenerator sdGenerator;
	@Autowired
	private AwsS3Service s3;
	
	@Test
	public void testSchoolGraph() throws Exception{
		ExternalStudentReportDTO data = new ExternalStudentReportDTO();
		data.setContentAreaCode("ELA");
		data = getReportData("KAP", false,data);		 
		data.setLegalFirstName("Nicholas");
		data.setLegalLastName("Gerard Ast");
		ExternalStudentReportDTO path = sdGenerator.generateReportFile(data);
		}
	
	@Test
	public void testS3OperationsForSchoolGraph() throws Exception{
		ExternalStudentReportDTO data = new ExternalStudentReportDTO();
		data.setContentAreaCode("ELA");
		data = getReportData("KAP", false,data);		 
		data.setLegalFirstName("Nicholas");
		data.setLegalLastName("Gerard Ast");
		ExternalStudentReportDTO path = sdGenerator.generateReportFile(data);
		String fullPath = FileUtil.buildFilePath(sdGenerator.getRootOutputDir(), path.getFilePath());
		assertTrue(s3.doesObjectExist(fullPath));
		s3.deleteObject(fullPath);
	}
	
	private ExternalStudentReportDTO getReportData(String code, boolean district,ExternalStudentReportDTO data) {	
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		data.setGradeLevel("12");
		data.setStudentId(1039551L);
		data.setStateStudentIdentifier("1039551");
		data.setReportCycle("December");
		data.setAssessmentProgram("CPASS");
		data.setAssessmentCode(code);
		data.setGradeCode("11");
		data.setAssessmentCode("General CETE");
		data.setAssessmentName("General CETE");
		data.setStateId(52L);
		data.setStateDisplayIdentifier("KS");
		data.setStateName("Kansas");
		data.setCompleted(true);
		data.setDistrictId(62L);
		data.setDistrictDisplayIdentifier("D0290");
		data.setSchoolId(1011L);
		data.setSchoolDisplayIdentifier("1290");
		data.setSchoolName("Cimarron Ensign");
		data.setDistrictName("Cimarron High");		
		data.setSchoolYear(2018L);
		data.setReportDate(dateFormat.format(date));
		data.setStudentScore(400L);
		data.setDistrictAverageScore(440L);
		data.setStateAverageScore(0L);
		data.setAllStatesAvgScore(435L);
		data.setAchievementLevel("Approaches");			
		data.setDistrictStandardError(BigDecimal.valueOf(10L));
		data.setStateStandardError(BigDecimal.valueOf(10L));
		data.setAllStateStandardError(BigDecimal.valueOf(10L));
		data.setStandardError(BigDecimal.valueOf(10L));
			
		data.setTestCutScores(new ArrayList<TestCutScores>());
		data.getTestCutScores().add(new TestCutScores());
		data.getTestCutScores().get(0).setLevel(1L);
		data.getTestCutScores().get(0).setLevelLowCutScore(200L);
		data.getTestCutScores().get(0).setLevelHighCutScore(370L);
		data.getTestCutScores().get(0).setLevelName("Developing");

		data.getTestCutScores().add(new TestCutScores());
		data.getTestCutScores().get(1).setLevel(2L);
		data.getTestCutScores().get(1).setLevelLowCutScore(371L);
		data.getTestCutScores().get(1).setLevelHighCutScore(400L);
		data.getTestCutScores().get(1).setLevelName("Approaches");
	
		data.getTestCutScores().add(new TestCutScores());
		data.getTestCutScores().get(2).setLevel(3L);
		data.getTestCutScores().get(2).setLevelLowCutScore(401L);
		data.getTestCutScores().get(2).setLevelHighCutScore(450L);
		data.getTestCutScores().get(2).setLevelName("Meets");
		
		data.getTestCutScores().add(new TestCutScores());
		data.getTestCutScores().get(3).setLevel(4L);
		data.getTestCutScores().get(3).setLevelLowCutScore(451L);
		data.getTestCutScores().get(3).setLevelHighCutScore(500L);
		data.getTestCutScores().get(3).setLevelName("Exceeds");
		
		return data;
	}




}
