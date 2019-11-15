package edu.ku.cete.report;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.DLMOrganizationSummaryGrade;
import edu.ku.cete.domain.report.DLMOrganizationSummaryReport;
import edu.ku.cete.domain.report.DLMOrganizationSummarySubject;
import edu.ku.cete.domain.report.OrganizationGrfCalculation;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class DLMOrganizationSummaryReportGeneratorTest extends BaseTest {
	@Autowired
	private DLMOrganizationSummaryReportGenerator generator;
	@Autowired
	private AwsS3Service s3;
	@Test
	public void testS3OperationsForGenerateDLMOrganizationSummaryReportFile() throws Exception {
		DLMOrganizationSummaryReport data = getReportData();
		DLMOrganizationSummaryReport path = generator.generateDLMOrganizationSummaryReportFile(data);
		String fullPath = FileUtil.buildFilePath(generator.getRootOutputDir(), path.getFilePath());
		assertTrue(s3.doesObjectExist(fullPath));
		//remove the test file from s3
		s3.deleteObject(fullPath);
	}

	@Test
	public void testS3OperationsForGenerateDLMOrganizationGrfExtract() throws Exception {
		DLMOrganizationSummaryReport data = getReportData();
		String path = generator.generateDLMOrganizationGrfExtract(data);
		String fullPath = FileUtil.buildFilePath(generator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		//remove the test file from s3
		s3.deleteObject(fullPath);
	}	
	
	private DLMOrganizationSummaryReport getReportData() {
		DLMOrganizationSummaryReport report = new DLMOrganizationSummaryReport();
		OrganizationGrfCalculation calc = new OrganizationGrfCalculation();
		calc.setReportYear(2018L);
		calc.setStateId(51L);
		calc.setStateName("Kansas");
		calc.setDistrictId(100L);
		calc.setDistrictName("District100");
		calc.setDistrictCode("100");
		List<DLMOrganizationSummaryGrade> orgSummaryGradeLists = new ArrayList<>();
		List<DLMOrganizationSummarySubject> orgSummarySubjectLists = new ArrayList<>();
		DLMOrganizationSummaryGrade grade = new DLMOrganizationSummaryGrade();
		grade.setGradeLevel("10");
		orgSummaryGradeLists.add(grade);
		DLMOrganizationSummarySubject subject = new DLMOrganizationSummarySubject();
		subject.setGradeId(10L);
		subject.setSubjectName("Math");
		subject.setNoOfStudentsTested(10L);
		subject.setNumberOfAdvanced(2L);
		subject.setNumberOfApproachingTarget(3L);
		subject.setNumberOfAtTarget(3L);
		subject.setNumberOfEmerging(2L);
		subject.setPercentageAtTargetAdvanced(50);
		orgSummarySubjectLists.add(subject);
		grade.setOrgSummarySubjectLists(orgSummarySubjectLists);
		calc.setOrgSummaryGradeLists(orgSummaryGradeLists);
		report.setOrgGrfCalculation(calc);
		
		return report;
	}
}