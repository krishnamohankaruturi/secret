/**
 * 
 */
package edu.ku.cete.batch;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.ReportProcess;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * Aug 18, 2017 3:15:13 PM
 */
@Component
public class InterimPredictiveReportProcessStarter extends BaseBatchProcessStarter {
	private final static Log logger = LogFactory.getLog(InterimPredictiveReportProcessStarter.class);
	
	@Resource
	private Job batchInterimPredictiveReportJob;
	
	@Resource
	private Job batchPredictiveReportScoreCalcJob;
	
	@Resource
	private Job batchPredictiveStudentReportJob;
	
	@Resource
	private Job batchInterimPredictiveOrganizationSummaryJob;	
	
	@Resource
	private Job batchInterimPredictiveReportISRAndSummaryReportJob;
	
	@Resource
	private Job batchIPISRCalcOnlyAndSummaryReportJob;
	
	@Resource
	private Job batchIPISROnlyAndSummaryReportJob;
	
	private String isScheduleOn;
	
	private String assessmentProgramCode;
	
	private String testingProgramAbbrName;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private AssessmentProgramDao assessmentProgramDao;
	
	@Value("${wsAdminUserName}")
	private String wsAdminUserName;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TestingProgramService testingProgramService;
	
	@Value("${category.categoryCode.currentInterimReportCycle}")
	private String currentReportCycleCategory;
	
	@Value("${categoryType.typeCode.reportCycle}")
	private String reportCycleCategoryType;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
    private AssessmentProgramService assessmentProgramService;
	
	public Long startPredictiveRptCalcProcess() throws Exception {
 		logger.debug("--> Entering InterimPredictiveReportProcessStarter isScheduleOn: " + isScheduleOn);
		Long jobId = null;
		ReportProcess report = new ReportProcess();
		Date now = new Date();
		AssessmentProgram kapAp = assessmentProgramDao.findByAbbreviatedName("KAP");
		User user = userService.getByUserNameIncludeSuperUser(wsAdminUserName, true);		
		
		report.setAssessmentProgramId(kapAp.getId());
		report.setSubjectId(-1L);
		report.setGradeId(-1L);
		
		if(testingProgramAbbrName != null){
			List<TestingProgram> testingPrograms = testingProgramService.getByAssessmentProgIdAndTestingProgAbbr(kapAp.getId(), testingProgramAbbrName);			
			report.setTestingProgramId(testingPrograms.get(0).getId());
			report.setTestingProgramName(testingPrograms.get(0).getProgramName());
		}		
		
		Category reportCycleCategory = categoryService.selectByCategoryCodeAndType(currentReportCycleCategory,  reportCycleCategoryType);
		
		report.setProcess("Process Data: Generate student report");
		report.setSubmissionDate(now);
		report.setCreatedUser(user.getId());
		report.setStatus("In Progress");
		report.setActiveFlag(true);
		report.setReportCycle(reportCycleCategory.getCategoryName());
		
		batchReportProcessService.insertBatchReport(report);
		
		if(report != null){
			JobParametersBuilder builder = new JobParametersBuilder();
			
			//set all the requested parameter as job parameter
			builder.addDate("run date", new Date());
			builder.addLong("assessmentProgramId", kapAp.getId());
			builder.addString("assessmentProgramCode", assessmentProgramCode);
			builder.addLong("testingProgramId", report.getTestingProgramId());
			builder.addLong("contentAreaId", null);
			builder.addLong("gradeCourseId", null);
			builder.addLong("assessmentId", null);
			builder.addLong("testTypeId", null);
			builder.addLong("uiBatchJobId", null);
			builder.addLong("reportProcessId", report.getId());
			builder.addString("processByStudentId", null);
			builder.addString("reportCycle", reportCycleCategory.getCategoryName());
			builder.addLong("createdUserId", null);
			
			//trigger the job
			jobId = startJob(batchInterimPredictiveReportJob, builder.toJobParameters());
		}
		
		logger.debug("<-- Leaving InterimPredictiveReportProcessStarter -->");
		return jobId;
	}

	
	public Long startPredictiveReportBatchProcess(Long assessmentProgramId, Long subjectId, Long gradeId, Long testingProgramId, String reportCycle, Long schoolYear, boolean processCalculations, boolean generateISR, 
			boolean generateSchoolDistrictSummaryReport, Long reportProcessId, Long studentId, Long organizationid,
			boolean processByStudentId, String generateSpecificISROption, Long createdUserId)
			throws Exception {
 		logger.debug("--> startPredictiveReportBatchProcess");
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addLong("assessmentProgramId", assessmentProgramId);
		builder.addString("assessmentProgramCode", assessmentProgram.getAbbreviatedname());
		builder.addLong("testingProgramId", testingProgramId);
		builder.addString("reportCycle", reportCycle);
		builder.addLong("contentAreaId", subjectId);
		builder.addLong("gradeCourseId", gradeId);
		builder.addLong("assessmentId", null);
		builder.addLong("testTypeId", null);
		builder.addLong("uiBatchJobId", null);
		builder.addLong("reportProcessId", reportProcessId);
		builder.addLong("studentId", studentId);
		builder.addLong("organizationId", organizationid);
		String processByStudentIdStr = processByStudentId ? "TRUE" : "FALSE";
		builder.addString("processByStudentId", processByStudentIdStr);
		builder.addString("generateSpecificISROption", generateSpecificISROption);		
		builder.addLong("createdUserId", createdUserId);		
		//need unique job parameter to rerun the completed job
		builder.addDate("run date", new Date());
		
		Long jobId = null;
		
		//Calculations Only
		if(processCalculations && !generateISR && !generateSchoolDistrictSummaryReport){
			jobId = startJob(batchPredictiveReportScoreCalcJob, builder.toJobParameters());
		}
		
		//ISR generation Only
		if(!processCalculations && generateISR && !generateSchoolDistrictSummaryReport){
			jobId = startJob(batchPredictiveStudentReportJob, builder.toJobParameters());
		}
		
		//Calculations and ISR generation
		if(processCalculations && generateISR && !generateSchoolDistrictSummaryReport){
			jobId = startJob(batchInterimPredictiveReportJob, builder.toJobParameters());
		}
		
		//Generate school and district summary reports only
		if(generateSchoolDistrictSummaryReport && !processCalculations && !generateISR){
			builder.addLong("schoolYear", schoolYear);
			
			jobId = startJob(batchInterimPredictiveOrganizationSummaryJob, builder.toJobParameters());
		}
		
		//ISR Calculations, report generation and school/district summary calculations and summary reports
		if(processCalculations && generateISR && generateSchoolDistrictSummaryReport){
			builder.addLong("schoolYear", schoolYear);
			jobId = startJob(batchInterimPredictiveReportISRAndSummaryReportJob, builder.toJobParameters());
		}
		
		//ISR calculations only and school/district summary calculations and summary reports
		if(processCalculations && !generateISR && generateSchoolDistrictSummaryReport){
			builder.addLong("schoolYear", schoolYear);
			jobId = startJob(batchIPISRCalcOnlyAndSummaryReportJob, builder.toJobParameters());
		}
		
		//Generate student report and school/district summary calculations and summary reports
		if(!processCalculations && generateISR && generateSchoolDistrictSummaryReport){
			builder.addLong("schoolYear", schoolYear);
			jobId = startJob(batchIPISROnlyAndSummaryReportJob, builder.toJobParameters());
		}
				
		logger.debug("<-- startPredictiveReportBatchProcess");
		
		return jobId;
	}
	
	public Job getBatchInterimPredictiveReportJob() {
		return batchInterimPredictiveReportJob;
	}


	public void setBatchInterimPredictiveReportJob(Job batchInterimPredictiveReportJob) {
		this.batchInterimPredictiveReportJob = batchInterimPredictiveReportJob;
	}


	public String getIsScheduleOn() {
		return isScheduleOn;
	}


	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}


	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}


	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}


	@Override
	public String getUploadType() {
		return null;
	}


	public String getTestingProgramAbbrName() {
		return testingProgramAbbrName;
	}


	public void setTestingProgramAbbrName(String testingProgramAbbrName) {
		this.testingProgramAbbrName = testingProgramAbbrName;
	}



}
