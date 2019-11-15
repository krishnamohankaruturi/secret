package edu.ku.cete.batch.reportprocess;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import edu.ku.cete.batch.BaseBatchProcessStarter;
import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.model.ReportProcessMapper;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.GroupAuthoritiesService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.OrganizationGrfCalculationService;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.SourceTypeEnum;

@Component
public class BatchReportProcessStarter extends BaseBatchProcessStarter {

    private final static Log logger = LogFactory.getLog(BatchReportProcessStarter.class);
	
    @Autowired
	private ReportProcessMapper reportProcessDao;
    
    @Resource
   	private Job batchReportAllJob;
       
    @Resource
	private Job batchReportProcessAllScoreCalcJob;
    
    @Resource
    private Job batchReportProcessStudentReportJob;
    
    @Resource
    private Job batchReportProcessOrganizationReportJob;
    
    @Resource
    private Job batchSchoolPDFOfStudentReportstAndZipJob;
    
    @Resource
    private Job batchDistrictPDFOfStudentReportstAndZipJob;
    
    @Resource
    private Job batchReportProcessAllScoreCalcStudentReportJob;
    
     @Resource
    private Job batchReportProcessAllScoreCalcSchoolDistrictJob;
    
    @Resource
    private Job batchReportProcessStudentReportSchoolDistrictJob;
    
    @Resource 
    private Job batchReportProcessAllScoreCalcPDFZIPJob;
   
    @Resource 
    private Job batchReportProcessStudentReportPDFZIPJob;
    
    @Resource 
    private Job batchReportProcessSchoolDistrictPDFZIPJob;
    
    @Resource 
    private Job batchReportProcessAllScoreCalcStudentReportPDFZIPJob;
    
    @Resource 
    private Job batchReportProcessStudentReportSchoolDistrictPDFZIPJob;
    
    @Resource 
    private Job batchReportProcessAllScoreCalcSchoolDistrictPDFZIPJob;
    
    @Resource 
    private Job batchReportProcessMedianScoreCalcJob;
    
    @Resource 
    private Job batchStudentSummaryBundledProcessJob;
    
    @Resource 
    private Job batchSchoolSummaryBundledProcessJob;
    
    @Resource 
    private Job batchDLMSummaryReportAllJob;
    
    @Resource
    private Job kelpaBatchReportProcessScoreCalcJob;
    
    @Resource
    private Job batchExternalStudentReportJob;
    
    @Resource
    private Job batchExternalSchoolDetailReportJob;
    
    @Resource
    private Job KELPAStudentReportProcessJob;

    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private GroupAuthoritiesService groupAuthoritiesService;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private OrganizationGrfCalculationService orgGrfCalculationService;
	
	@Autowired
	private BatchUploadService batchUploadService;
 	
 	@Autowired
	private OrganizationService organizationService;
 	
    @Autowired
    private UploadGrfFileWriterProcessService uploadGrfFileWriterProcessService;
    
    @Autowired
	protected AppConfigurationService appConfigService;
	
    @Value("${GRF.district.report.process.string}")
	private String GrfDistrictReportProcessString;
	
	public Long startBatchReportProcess(boolean processReportFlag, boolean onlyMedianProcess, boolean schoolDistrictReportFlag, boolean studentReportFlag, 
			boolean schoolFilesOfStudentReport, boolean districtFilesOfStudentReport, Long assessmentProgramId, Long subjectId, Long gradeId, Long reportProcessId, Long studentId, boolean createPSFile, Long organizationid,
			boolean processByStudentId, String generateSpecificISROption, boolean studentSummaryBundledReportBySchool, boolean studentSummaryBundledReportByDistrict, boolean schoolSummaryBundledReport, String reportType, Long schoolYear, 
			Long testingProgramId, String reportCycle, Long createdUserId)
			throws Exception {
 		logger.debug("--> startBatchUploadProcess");
		List<AppConfiguration> skipStudentCalcForUncompletedStages= appConfigService.selectByAttributeType(CommonConstants.SKIP_STUDENT_CALC_FOR_UNCOMPLETED_STAGES);
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		JobParametersBuilder builder = new JobParametersBuilder();
		if(CollectionUtils.isNotEmpty(skipStudentCalcForUncompletedStages)) {
			builder.addString("skipStudentCalcForUncompletedStages", skipStudentCalcForUncompletedStages.get(0).getAttributeValue());
		}else {
			builder.addString("skipStudentCalcForUncompletedStages", "false");
		}
		builder.addLong("assessmentProgramId", assessmentProgramId);
		builder.addString("assessmentProgramCode", assessmentProgram.getAbbreviatedname());
		builder.addLong("contentAreaId", subjectId);
		builder.addLong("gradeCourseId", gradeId);
		builder.addLong("reportProcessId", reportProcessId);
		builder.addLong("studentId", studentId);
		builder.addLong("organizationId", organizationid);
		String processByStudentIdStr = processByStudentId ? "TRUE" : "FALSE";
		builder.addString("processByStudentId", processByStudentIdStr);
		builder.addString("generateSpecificISROption", generateSpecificISROption);
		builder.addLong("testingProgramId", testingProgramId);
		builder.addString("reportCycle", reportCycle);
		if(createPSFile)
			builder.addString("createPSFile", "true");
		else
			builder.addString("createPSFile", "false");

		//need unique job parameter to rerun the completed job
		builder.addDate("run date", new Date());		
		builder.addString("bundledReportType", reportType);
		builder.addLong("createdUserId", createdUserId);
		
		//test
		builder.addLong("schoolYear", schoolYear);
		
		Long jobId = null;
		
		if(assessmentProgram.getAbbreviatedname().equalsIgnoreCase("KELPA2")){//For KELPA
			if(processReportFlag){
				logger.debug("KELPA2 Calculations triggered");
				jobId = startJob(kelpaBatchReportProcessScoreCalcJob, builder.toJobParameters());
		    }
			else if(studentReportFlag){
				logger.debug("KELPA2 Report Generation Triggered");
				jobId = startJob(KELPAStudentReportProcessJob, builder.toJobParameters());
			}
			else if(schoolFilesOfStudentReport){
				logger.debug("KELPA2 School Bundle Report Generation Triggered");
				jobId = startJob(batchSchoolPDFOfStudentReportstAndZipJob, builder.toJobParameters());
			}
		}else if(assessmentProgram.getAbbreviatedname().equalsIgnoreCase("CPASS")){//For CPASS
			if(studentReportFlag){
				logger.debug("CPASS Report Generation Triggered");
				jobId = startJob(batchExternalStudentReportJob, builder.toJobParameters());
			}
			else if(schoolFilesOfStudentReport){
				logger.debug("CPASS School Bundle Report Generation Triggered");
				jobId = startJob(batchSchoolPDFOfStudentReportstAndZipJob, builder.toJobParameters());
			}
			else if(districtFilesOfStudentReport){
				logger.debug("CPASS District Bundle Report Generation Triggered");
				jobId = startJob(batchDistrictPDFOfStudentReportstAndZipJob, builder.toJobParameters());
			}
			else if(schoolDistrictReportFlag){
				logger.debug("CPASS School Details Report Generation Triggered");
				jobId = startJob(batchExternalSchoolDetailReportJob, builder.toJobParameters());
			}
		}else {//For KAP
			//All
			if(processReportFlag && schoolDistrictReportFlag && studentReportFlag){
				logger.debug(" 1 : All");
				jobId = startJob(batchReportAllJob, builder.toJobParameters()); 
			}
	
			//Cal Score with median
			if(processReportFlag && !schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 2 : Calculate score with median");			
				jobId = startJob(batchReportProcessAllScoreCalcJob, builder.toJobParameters()); 
			}
			
			//Only median with out Cal Score
			if(onlyMedianProcess && !processReportFlag && !schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 3 : Only median with out Cal Score");
				jobId = startJob(batchReportProcessMedianScoreCalcJob, builder.toJobParameters()); 
			}
					
			//State/District/School Detail/Summary Report 
			if(!processReportFlag && schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 4 : State/District/School Report");				
				jobId = startJob(batchReportProcessOrganizationReportJob, builder.toJobParameters());
			}
			
			//Generate Student Report
			if(!processReportFlag && !schoolDistrictReportFlag && studentReportFlag){
				logger.debug(" 5 : Generate Student Report");				
				jobId = startJob(batchReportProcessStudentReportJob, builder.toJobParameters());				
			}
			
			//Calc and School/District/State Report Generation
			if(processReportFlag && schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 6 : Calculation and school/district/state report generation");
				jobId = startJob(batchReportProcessAllScoreCalcSchoolDistrictJob, builder.toJobParameters()); 
			}
			
			//Calc and Student Report Generation
			if(processReportFlag && !schoolDistrictReportFlag && studentReportFlag){
				logger.debug(" 7 : Calculation and Student report generation");
				jobId = startJob(batchReportProcessAllScoreCalcStudentReportJob, builder.toJobParameters()); 
			}
			
			//School/District/State and Student Report Generation
			if(!processReportFlag && schoolDistrictReportFlag && studentReportFlag){
				logger.debug(" 8 : student and school/district/state report generation");
				jobId = startJob(batchReportProcessStudentReportSchoolDistrictJob, builder.toJobParameters()); 
			}
			
			//package student reports into school files (creating pdf's and zip/sqllite files)
			if(schoolFilesOfStudentReport && !processReportFlag && !schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 9 : package student reports into school files (creating pdf's and zip/sqllite files)");
				jobId = startJob(batchSchoolPDFOfStudentReportstAndZipJob, builder.toJobParameters()); 
			}
			
			//Calc and PDF/ZIP Generation
			if(processReportFlag && schoolFilesOfStudentReport && !schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 10 : Calc and PDF/ZIP Generation)");
				jobId = startJob(batchReportProcessAllScoreCalcPDFZIPJob, builder.toJobParameters()); 
			}
			
			//Student Report Generation and PDF/ZIP Generation
			if(!processReportFlag && schoolFilesOfStudentReport && !schoolDistrictReportFlag && studentReportFlag){
				logger.debug(" 11 : Student Report Generation and PDF/ZIP Generation");
				jobId = startJob(batchReportProcessStudentReportPDFZIPJob, builder.toJobParameters()); 
			}
			
			//School/District/State and PDF/ZIP Generation
			if(!processReportFlag && schoolFilesOfStudentReport && schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 12 : School/District/State and PDF/ZIP Generation");
				jobId = startJob(batchReportProcessSchoolDistrictPDFZIPJob, builder.toJobParameters()); 
			}
			
			//Calc and Student Report And PDF/ZIp
			if(processReportFlag && schoolFilesOfStudentReport && !schoolDistrictReportFlag && studentReportFlag){
				logger.debug(" 13 : Calc and Student Report And PDF/ZIp");
				jobId = startJob(batchReportProcessAllScoreCalcStudentReportPDFZIPJob, builder.toJobParameters()); 
			}
			
			//Student Report  And School/District/State And PDF/ZIp
			if(!processReportFlag && schoolFilesOfStudentReport && schoolDistrictReportFlag && studentReportFlag){
				logger.debug(" 14 : Student Report  And School/District/State And PDF/ZIp");
				jobId = startJob(batchReportProcessStudentReportSchoolDistrictPDFZIPJob, builder.toJobParameters()); 
			}
					
			//Calc and School/District/State And PDF/ZIp
			if(processReportFlag && schoolFilesOfStudentReport && schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 15 : Student Report  And School/District/State And PDF/ZIp");
				jobId = startJob(batchReportProcessAllScoreCalcSchoolDistrictPDFZIPJob, builder.toJobParameters()); 
			}
			
			//package student reports into district files (creating pdf's files)
			if(districtFilesOfStudentReport && !processReportFlag && !schoolDistrictReportFlag && !studentReportFlag){
				logger.debug(" 16 : package student reports into district files (creating pdf's files)");
				jobId = startJob(batchDistrictPDFOfStudentReportstAndZipJob, builder.toJobParameters()); 
			}			
			
			//Student Summary Bundled Reports at school level
			if(!districtFilesOfStudentReport && !processReportFlag && !schoolDistrictReportFlag 
					&& !studentReportFlag && (studentSummaryBundledReportBySchool || studentSummaryBundledReportByDistrict) && !schoolSummaryBundledReport){
				logger.debug(" 17 : Create Student Summary Bundled reports at school/district level ");
				jobId = startJob(batchStudentSummaryBundledProcessJob, builder.toJobParameters()); 
			}
			//School Summary Bundled Reports at district level
			if(!districtFilesOfStudentReport && !processReportFlag && !schoolDistrictReportFlag 
					&& !studentReportFlag && !studentSummaryBundledReportBySchool && !studentSummaryBundledReportByDistrict && schoolSummaryBundledReport){
				logger.debug(" 18 : Create School Summary Bundled reports at district level");
				jobId = startJob(batchSchoolSummaryBundledProcessJob, builder.toJobParameters()); 
			}
		}
		
		logger.debug("<-- startBatchUploadProcess");
		return jobId;
	}
	
	public Long startBatchReportProcessFromGrfUpload(Long stateId,Long reportYear,Long assessmentProgramId,Long userId, String userDisplayname) throws Exception{
		Long jobId = null;
		 JobParametersBuilder builder = new JobParametersBuilder();
		 builder.addLong("stateId", stateId);
		 builder.addLong("reportYear", reportYear);
		 builder.addLong("assessmentProgramId",  assessmentProgramId);
		 builder.addLong("userId",  userId);	
		 builder.addString("userDisplayname", userDisplayname);	
		 
		 //Need to delete calculated GRF data for State and District summary Reports
		 orgGrfCalculationService.deleteOrganizationGrfCalculation(stateId,reportYear,assessmentProgramId);
			
		 //Need to delete All DLM organization reports for the uploaded state and year
		 batchReportProcessService.deleteAllOrganizationReportsOnGRFUpload(stateId, reportYear, assessmentProgramId);
		 
		Date now = new Date(); 
		StringBuffer processString = new StringBuffer();
		processString.append(GrfDistrictReportProcessString);
		BatchUpload upload = new BatchUpload();
		upload.setAssessmentProgramId(assessmentProgramId);
		upload.setContentAreaId(0L);
		upload.setSubmissionDate(now);
		upload.setCreatedUser(userId);
		upload.setCreatedUserDisplayName(userDisplayname);
		upload.setStatus("STARTED");
		upload.setFileName("N/A");
		upload.setFilePath("");
		upload.setActiveFlag(true);		 
		upload.setStateId(stateId);
		upload.setReportYear(reportYear);
		upload.setGrfProcessType(processString.toString());
		batchUploadService.insertBatchUpload(upload);
		
		//Insert entry in GRF audit table
		 GrfStateApproveAudit audit = new GrfStateApproveAudit();
		 audit.setActiveFlag(true);
		 audit.setStateId(stateId);
		 audit.setSchoolYear(reportYear);
		 audit.setAuditColumnProperties();
		 audit.setUpdatedUserId(userId);
		 audit.setOperation("APPROVE");
		 audit.setSource(SourceTypeEnum.MANUAL.getCode());				 
		 uploadGrfFileWriterProcessService.setGRFAuditInfo(audit);
		
		builder.addString("reportDate", getDateTimeBasedOnOrgId(stateId));	
        builder.addLong("reportProcessId",  upload.getId());
		 
		 jobId = startJob(batchDLMSummaryReportAllJob, builder.toJobParameters());
		return jobId;
	}
	
	public String getDateTimeBasedOnOrgId(Long stateId){
		TimeZone tz = organizationService.getTimeZoneForOrganization(stateId);
		Date createDate = new Date();
		if (tz == null) {
			// default to central, if necessary
			tz = TimeZone.getTimeZone("US/Central");
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		dateFormat.setTimeZone(tz);
		// Server is in GMT and need to convert to US/Central time else showing future date.
		return dateFormat.format(createDate);
	}
	
	public String getJobStatus(Long reportProcessId) {
		return reportProcessDao.getReportProcessStatus((reportProcessId));
	}

	@Override
	public String getUploadType() {
		return "CSV_RECORD_TYPE";
	}
}
