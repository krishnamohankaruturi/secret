package edu.ku.cete.report;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.domain.report.AlternateAggregateStudents;
import edu.ku.cete.domain.report.AlternateAggregateSubject;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class AlternateAggregateReportGeneratorTest extends BaseTest {
	@Autowired
	private AlternateAggregateReportGenerator generator;
	@Autowired
	private AwsS3Service s3;

	@Test
	public void testS3OperationsForGenerateAlternateAggregateReportFile() throws Exception {
		AlternateAggregateReport data = new AlternateAggregateReport();
		data = getReportData(data);
		AlternateAggregateReport report = generator.generateAlternateAggregateReportFile(data);
		String fullPath = FileUtil.buildFilePath(generator.getRootOutputDir(), report.getFilePath());
		assertTrue(s3.doesObjectExist(fullPath));
		//remove the test file from s3
		s3.deleteObject(fullPath);
	}

	
	@Test
	public void testS3OperationsForGenerateAlternateAggregateExtract() throws Exception {
		AlternateAggregateReport data = new AlternateAggregateReport();
		data = getReportData(data);
		String extract = generator.generateAlternateAggregateExtract(data);
		String fullPath = FileUtil.buildFilePath(generator.getRootOutputDir(), extract);
		assertTrue(s3.doesObjectExist(fullPath));
		//remove the test file from s3
		s3.deleteObject(fullPath);
	}

	private AlternateAggregateReport getReportData(AlternateAggregateReport data){
		data.setReportYear(2018L);
		data.setSchoolId(100100L);
		data.setSchoolName("School100100");
		data.setDistrictId(100L);
		data.setDistrictName("District100");
		data.setResidenceDistrictIdentifier("100");
		data.setKiteEducatorIdentifier("987654321");
		data.setState("Kansas");
		data.setStateCode("KS");
		data.setStateId(52L);
		List<AlternateAggregateStudents> alternateAggregateStudents = new ArrayList<>();
		AlternateAggregateStudents students = new AlternateAggregateStudents();
		students.setEducatorFirstName("Bill");
		students.setEducatorLastName("Self");
		students.setCurrentGradelevel("10");
		students.setLegalFirstName("John");
		students.setLegalLastName("Snoe");
		students.setStudentId(1L);
		alternateAggregateStudents.add(students);
		data.setAlternateAggregateStudents(alternateAggregateStudents);
		AlternateAggregateSubject alternateAggregateSubject = new AlternateAggregateSubject();
		alternateAggregateSubject.setSubject("Math");
		alternateAggregateSubject.setSubjectCode("M");
		alternateAggregateSubject.setAchievementLevel("-");
		List<AlternateAggregateSubject> subjects = new ArrayList<>();
		subjects.add(alternateAggregateSubject);
		students.setAlternateAggregateSubject(subjects);
		data.setAlternateAggregateStudents(alternateAggregateStudents);
		return data;
	}
}