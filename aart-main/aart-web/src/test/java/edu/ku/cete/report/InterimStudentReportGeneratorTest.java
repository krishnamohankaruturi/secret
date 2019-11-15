package edu.ku.cete.report;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class InterimStudentReportGeneratorTest extends BaseTest {
	@Autowired
	private InterimStudentReportGenerator generator;
	@Autowired
	private AwsS3Service s3;
	
	@Test
	public void testS3OperationsForGenerateInterimStudentReportFile() throws Exception {
		LevelDescription ld = new LevelDescription();
		ld.setAssessmentProgram("KAP");
		ld.setGrade("10");
		List<LevelDescription> lds = new ArrayList<>();
		List<TestCutScores> scores = new ArrayList<>();
		TestCutScores tcs = new TestCutScores();
		tcs.setLevelLowCutScore(1L);
		tcs.setLevelHighCutScore(10L);
		scores.add(tcs);
		Map<String, InterimStudentReport> testingCycleRecordMap = new HashMap<>();
		String reportCycle = "cycle";
		InterimStudentReport report = new InterimStudentReport();
		testingCycleRecordMap.put(reportCycle, report);
		report.setStateId(51L);
		report.setDistrictId(100L);
		report.setDistrictName("District100");
		report.setSchoolYear(2018L);
		report.setAttendanceSchoolId(100100L);
		report.setContentAreaCode("440");
		report.setGradeCode("10");
		report.setStudentId(123456L);
		report.setScaleScore(100L);
		report.setStandardError(new BigDecimal("4.5"));
		report.setStateStudentIdentifier("123456");
		StudentReportQuestionInfo questionInfo = new StudentReportQuestionInfo();
		List<StudentReportQuestionInfo> infos = new ArrayList<>();
		infos.add(questionInfo);
		InterimStudentReport path = generator.generateInterimStudentReportFile(testingCycleRecordMap, reportCycle, infos, scores, lds);
		String fullPath = FileUtil.buildFilePath(generator.getRootOutputDir(), path.getFilePath());
		assertTrue(s3.doesObjectExist(fullPath));
		//remove the test file from s3
		s3.deleteObject(fullPath);
	}

}