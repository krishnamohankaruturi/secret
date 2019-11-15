package edu.ku.cete.batch.reportprocess.processor;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.impl.AwsS3ServiceTestConfig;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.SchoolReportDateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AwsS3ServiceTestConfig.class)
@WebAppConfiguration
public class StudentSummaryBundledReportProcessorTest {

	@Mock
	SchoolReportDateUtil schoolReportDateUtil;
	
    @Mock
    StepExecution stepExecution;
	
	@Mock
	BatchReportProcessService batchReportProcessService;
	
	@InjectMocks
	private StudentSummaryBundledReportProcessor processor = new StudentSummaryBundledReportProcessor();
	
	@Autowired
	private AwsS3Service s3;

    @Before
    public void setUp() throws Exception {
    	MockitoAnnotations.initMocks(this);
    	processor.setAssessmentProgramCode("DLM");
    	processor.setAssessmentProgramId(3L);
    	processor.setSchoolYear(2018L);
    	processor.setBundledReportType("STD_SUM_BUNDLED_SCH_LVL");
    	processor.setGradeCourseAbbrName("10");
    	ReflectionTestUtils.setField(processor, "REPORT_PATH", "ep-data-store");
    	ReflectionTestUtils.setField(processor, "REPORT_TYPE_STUDENT_SUMMARY", "StudentSummary");
    	ReflectionTestUtils.setField(processor, "REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL", "STD_SUM_BUNDLED_SCH_LVL");
    	ReflectionTestUtils.setField(processor, "REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL", "STD_SUM_BUNDLED_DT_LVL");
    	ReflectionTestUtils.setField(processor, "REPORT_TYPE_STUDENT_SUMMARY_BUNDLED", "STUDENT_SUMMARY_BUNDLED");
    	ReflectionTestUtils.setField(processor, "s3", s3);
    	List<StudentReport> studentReports = new ArrayList<>();
    	StudentReport report = new StudentReport();
    	report.setAssessmentProgramId(3L);
    	report.setAttendanceSchoolName("School100100");
    	report.setStateId(51L);
    	report.setDistrictId(100L);
    	report.setAttendanceSchoolId(100100L);
    	report.setAttSchDisplayIdentifier("School100100");
    	report.setContentAreaCode("ABC");
    	report.setFilePath("/test/pdf-sample.pdf");
    	StudentReport report2 = new StudentReport();
    	report2.setAssessmentProgramId(3L);
    	report2.setAttendanceSchoolName("School100100");
    	report2.setStateId(51L);
    	report2.setDistrictId(100L);
    	report2.setAttendanceSchoolId(100100L);
    	report2.setAttSchDisplayIdentifier("School100100");
    	report2.setContentAreaCode("DEF");
    	report2.setFilePath("/test/pdf-sample.pdf");
    	studentReports.add(report);	
    	studentReports.add(report2);	
    	when(batchReportProcessService.getExternalStudentReportsForStudentSummaryBundledReport(3L, "10", 2018L, 1311L, "StudentSummary"))
    		.thenReturn(studentReports);
    }
	
	@Test
	public void testS3OperationsForProcess() throws Exception {
		StudentReport report = processor.process(1311L);
		String fullPath = report.getDetailedReportPath();
		assertTrue(s3.doesObjectExist(FileUtil.buildFilePath(processor.getBaseReportPath(), fullPath)));
		s3.deleteObject(fullPath);
	}

}
