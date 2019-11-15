package edu.ku.cete.batch;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.TestEnrollmentMethod;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchRegistrationService;

@Component("batchProcessor")
public class BatchRegistrationProcessStarter extends BaseBatchProcessStarter {

    private final static Log logger = LogFactory.getLog(BatchRegistrationProcessStarter.class);
	  
	@Resource
	private Job cpassDacJob;
	  
	@Resource
	private Job ampDacJob;
	
	@Resource
	private Job kapDacJob;
	
	@Resource
	@Qualifier("dlmAutoJob")
	private Job dlmJob;
	
	@Resource
	@Qualifier("dlmMultiAssignAutoJob")
	private Job dlmMultiAssignJob;
	
	@Resource
	@Qualifier("dlmFixedAssignAutoJob")
	private Job dlmFXDJob;
	
	@Resource	
	private Job dlmResearchSurveyJob;
	
	@Resource
	private Job dlmAllJobs;
	
	@Resource
	private Job studentTrackerJob;
	
	@Resource
	private Job stOnlyUntrackedJob;	
	
	@Resource
	private Job questarJob;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    @Autowired
    private BatchRegistrationService brService;

    @Resource
	private Job kapAdaptiveDacJob;
    
    @Resource
	private Job kelpaDacJob;
    
    @Resource
    private Job kapPredictiveJob;
    
    @Resource
    private Job iSmartAutoJob;
    
    @Resource
    private Job pltwDacJob;
    
    @Value("${ismart.assessmentProgram.abbreviatedName}")
	private String ISMART_PROGRAM_ABBREVIATEDNAME;
    
    @Value("${pltw.assessmentProgram.abbreviatedName}")
	private String PLTW_PROGRAM_ABBREVIATEDNAME;
    
    @Value("${testEnrollmentMethod.multiStage.methodCode}")
	private String TEST_ENROLLMENT_METHOD_MULTISTAGE;
    
	public Long startBatchRegProcess(Long assessmentProgramId, Long testingProgramId,
			Long assessmentId, Long testTypeId, Long contentAreaId, Long gradeCourseId, Long testEnrollmentMethodId, Long testWindowId, String dlmFixedGradeAbbrName, Long batchUiJobId) throws Exception {
		logger.debug("--> startBatchRegProcess");
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		TestEnrollmentMethod testEnrollmentMethod = brService.getTestEnrollmentMethod(testEnrollmentMethodId);
		String assessmentProgramCode = assessmentProgram.getAbbreviatedname();
		
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addString("assessmentProgramCode", assessmentProgram.getAbbreviatedname());
		builder.addLong("assessmentProgramId", assessmentProgramId);
		builder.addLong("testingProgramId", testingProgramId);
		builder.addLong("assessmentId", assessmentId);
		builder.addLong("testTypeId", testTypeId);
		builder.addLong("contentAreaId", contentAreaId);
		builder.addLong("gradeCourseId", gradeCourseId);
		builder.addLong("operationalTestWindowId", testWindowId);
		builder.addString("dlmFixedGradeAbbrName", dlmFixedGradeAbbrName);
		builder.addLong("enrollmentMethodId", testEnrollmentMethodId);
		builder.addString("enrollmentMethod", testEnrollmentMethod.getMethodCode());
		builder.addLong("batchUiJobId", batchUiJobId);

		//need unique job parameter to rerun the completed job
		builder.addDate("run date", new Date());
		builder.addString("recordType", getUploadType());
		Long jobId = null;
		if(testEnrollmentMethod != null) {
			if(assessmentProgramCode.equalsIgnoreCase("CPASS")) {
				if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("MLTSTG")){
					jobId = startJob(cpassDacJob, builder.toJobParameters());
				}
			} else if(assessmentProgramCode.equalsIgnoreCase("AMP")) {
				if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("MLTSTG")){
					jobId = startJob(ampDacJob, builder.toJobParameters());
				}
			} else if(assessmentProgramCode.equalsIgnoreCase("KAP")) {
				if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("MLTSTG")){
					jobId = startJob(kapDacJob, builder.toJobParameters());
				}else if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("ADP")){
					jobId = startJob(kapAdaptiveDacJob, builder.toJobParameters());
				}else if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("PREDICTIVE")){
					jobId = startJob(kapPredictiveJob, builder.toJobParameters());
				}else{
					throw new IllegalArgumentException(String.format("Auto enrollment is not supported for: %s, %d (%s)", testEnrollmentMethod.getMethodCode(), assessmentProgramId, assessmentProgramCode));
				}
			} else if(assessmentProgramCode.equalsIgnoreCase("DLM")) {
				if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("MLTASGN")){
					jobId = startJob(dlmMultiAssignJob, builder.toJobParameters());
				}else if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("STDNTTRKR")){
					jobId = startJob(dlmAllJobs, builder.toJobParameters());	
				}else if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("FXD")){ //fixed
					jobId = startJob(dlmFXDJob, builder.toJobParameters());
				} else if(StringUtils.equalsIgnoreCase(testEnrollmentMethod.getMethodCode(), "RESEARCHSURVEY")){
					jobId = startJob(dlmResearchSurveyJob, builder.toJobParameters());
				}
			} else if(assessmentProgramCode.equalsIgnoreCase("KELPA2")) {
				if(testEnrollmentMethod.getMethodCode().equalsIgnoreCase("MLTSTG")){
					jobId = startJob(kelpaDacJob, builder.toJobParameters());
				}
			} else if(ISMART_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(assessmentProgramCode)){
				if("FXD".equalsIgnoreCase(testEnrollmentMethod.getMethodCode())){
					jobId = startJob(iSmartAutoJob, builder.toJobParameters());
				} else if("RESEARCHSURVEY".equalsIgnoreCase(testEnrollmentMethod.getMethodCode())){
					jobId = startJob(dlmResearchSurveyJob, builder.toJobParameters());
				}
			} else if(PLTW_PROGRAM_ABBREVIATEDNAME.equalsIgnoreCase(assessmentProgramCode)){
				if(TEST_ENROLLMENT_METHOD_MULTISTAGE.equalsIgnoreCase(testEnrollmentMethod.getMethodCode())){
					jobId = startJob(pltwDacJob, builder.toJobParameters());
				}
				
			} else {
				throw new IllegalArgumentException(String.format("Auto enrollment is not supported for assessmentprogram: %d (%s)", assessmentProgramId, assessmentProgramCode));
			}
		} 
		logger.debug("<-- startBatchRegProcess");
		return jobId;
	}
	
	public Long startStudentTracker() throws Exception {
		JobParametersBuilder builder = new JobParametersBuilder();
		//need unique job parameter to rerun the completed job
		builder.addDate("run date", new Date());
		return startJob(studentTrackerJob, builder.toJobParameters());
	}
	
	public Long startSTOnlyUntracked() throws Exception {
		JobParametersBuilder builder = new JobParametersBuilder();
		//need unique job parameter to rerun the completed job
		builder.addDate("run date", new Date());
		return startJob(stOnlyUntrackedJob, builder.toJobParameters());
	}
	
	public Long startQuestarJob(Long operationalTestWindowId) throws Exception {
		JobParametersBuilder builder = new JobParametersBuilder();
		builder.addDate("run date", new Date());
		builder.addLong("operationalTestWindowId", operationalTestWindowId);
		
		return startJob(questarJob, builder.toJobParameters());
	}

	@Override
	public String getUploadType() {
		return "CSV_RECORD_TYPE";
	}
}
