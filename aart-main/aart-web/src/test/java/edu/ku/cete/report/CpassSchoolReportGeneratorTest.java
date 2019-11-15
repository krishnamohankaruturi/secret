package edu.ku.cete.report;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.ExternalSchoolDetailReportDTO;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.test.BaseTest;
import edu.ku.cete.util.FileUtil;

public class CpassSchoolReportGeneratorTest extends BaseTest {
	@Autowired
	private CpassSchoolReportGenerator generator;
	@Autowired
	private AwsS3Service s3;
	@Test
	public void testS3OperationsForGenerateTableFile() throws Exception {
		ExternalSchoolDetailReportDTO data = new ExternalSchoolDetailReportDTO();
		data = getReportData(data);
		String path = generator.generateTableFile(data);
		String fullPath = FileUtil.buildFilePath(generator.getRootOutputDir(), path);
		assertTrue(s3.doesObjectExist(fullPath));
		//remove the test file from s3
		s3.deleteObject(fullPath);
	}
	private ExternalSchoolDetailReportDTO getReportData(ExternalSchoolDetailReportDTO data) {
		data.setStateDisplayIdentifier("1234567");
		data.setDistrictDisplayIdentifier("100");
		data.setSchoolDisplayIdentifier("100100");
		data.setSubject("M");
		data.setGradeName("10");
		data.setSchoolYear(2018L);
		data.setAssessmentCode("ABC");
		return data;
	}

}