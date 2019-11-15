package edu.ku.cete.batch.reportprocess.processor;

 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import edu.ku.cete.domain.SuppressedLevel;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.ActScoringDescription;
import edu.ku.cete.domain.report.ActScoringLevel;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.StudentReportGenerator;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.LevelDescriptionService;
public class BatchStudentReportProcess implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;
    private Long assessmentProgramId;
    private String assessmentProgramCode;
    private ContentArea contentArea;
	private GradeCourse gradeCourse;
    private Long batchReportProcessId;
    private Long schoolYear;
    private Long testingProgramId;
    
    @Autowired
	private StudentReportGenerator isrGenerator;
    
    final static Log logger = LogFactory.getLog(BatchStudentReportProcess.class);
   
    @Autowired
	private LevelDescriptionService levelDescriptionService;
	
    @Autowired 
    private BatchReportProcessService batchReportProcessService;
 
    @Value("${report.minimum.required.amp}")
   	private String reportMinimumRequiredAMP;
    
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.info("Inside getTest for Student Report Generation....Student ID -" + studentReport.getStudentId() );
		List<Long> testIds = new ArrayList<Long>();
		testIds.add(studentReport.getExternalTest1Id());
		if(studentReport.getExternalTest2Id() !=null)
			testIds.add(studentReport.getExternalTest2Id());
		
		//get Level Details with min and max test cut scores
		//TODO US17665 - Removing testid1, testid2 columns from leveldescription table so, this has to be revisited
		List<LevelDescription> levelDescriptions = new ArrayList<LevelDescription>();
		
/*		List<LevelDescription> levelDetails = levelDescriptionService.getLevelsAndTestCutMinMaxByTestId1TestId2(testIds, 
				studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId());
*/
		List<LevelDescription> levelDetails = levelDescriptionService.getLevelDescriptionByLevelId(schoolYear,
				studentReport.getAssessmentProgramId(),studentReport.getContentAreaId(),studentReport.getGradeId(),"Main",testingProgramId);
		if(CollectionUtils.isNotEmpty(levelDetails)){
			levelDescriptions.addAll(levelDetails);	
		}
		studentReport.setLevels(levelDescriptions);
		
		List<LevelDescription> alllevelDescription = new ArrayList<LevelDescription>();
		
		List<LevelDescription> allLevelDescriptions = levelDescriptionService.getLevelDescriptionByLevelIdMdptIdCombinedId(studentReport.getLevelId(),
				studentReport.getMdptLevelId(),studentReport.getCombinedLevelId());
		if(CollectionUtils.isNotEmpty(allLevelDescriptions)){
			alllevelDescription.addAll(allLevelDescriptions);	
		}
		
		studentReport.setAllLevelDescriptions(alllevelDescription);

		//state, district, school medain, standard devation, standard error details
		List<Long> organizationIds = new ArrayList<Long>();
		organizationIds.add(studentReport.getAttendanceSchoolId());
		organizationIds.add(studentReport.getDistrictId());
		organizationIds.add(studentReport.getStateId());
		
		if(assessmentProgramCode.equals("KAP") &&  (contentArea.getAbbreviatedName().equals("ELA") || contentArea.getAbbreviatedName().equals("M")) &&
				(gradeCourse.getAbbreviatedName().equals("10") || gradeCourse.getAbbreviatedName().equals("8"))) {
		
		List<ActScoringDescription> actScoringDescription = batchReportProcessService.getActScoreDescriptionByOrgIdsAssessmentContentGradeSchoolYear(assessmentProgramId,
				studentReport.getGradeId(), studentReport.getContentAreaId(), schoolYear);
		
		List<String> actd1=new ArrayList<String>();
		for (ActScoringDescription actScoringDescription2 : actScoringDescription) {
			actd1.add(actScoringDescription2.getDescription());
		}
		
		List<Long> getAllLevels = batchReportProcessService.getActLevelsForActScoring();		

		ArrayList<ActScoringLevel> actScoringLevel=new ArrayList<ActScoringLevel>();

		for(Long levelId : getAllLevels) {
			ActScoringLevel actScoringLevelAll = new ActScoringLevel();
			List<ActScoringDescription> minmaxvalues = batchReportProcessService.getActScoreByOrgIdsAssessmentContentGradeSchoolYear(assessmentProgramId,
					 studentReport.getGradeId(), studentReport.getContentAreaId(), schoolYear, levelId);
			
			actScoringLevelAll.setLevelId(levelId);
			List<ActScoringDescription> lctScoringDescriptionList1=new ArrayList<ActScoringDescription>();
			for (ActScoringDescription actScoringLevel2 : minmaxvalues) {	
					ActScoringDescription actScoringDescriptionAll=new ActScoringDescription();
					actScoringDescriptionAll.setMaxvalue(actScoringLevel2.getMaxvalue());
					actScoringDescriptionAll.setMinvalue(actScoringLevel2.getMinvalue());
					actScoringDescriptionAll.setDescription(actScoringLevel2.getDescription());
					
					lctScoringDescriptionList1.add(actScoringDescriptionAll);
				}
			actScoringLevelAll.setActScoringDescriptions(lctScoringDescriptionList1);

			actScoringLevel.add(actScoringLevelAll);
			}
			

		studentReport.setActScoringDescription(actd1);
		studentReport.setNoOfActlevels(actScoringLevel.size() +1);
		studentReport.setActScoringLevel(actScoringLevel);
		}
		
		List<ReportsMedianScore> medianScores = batchReportProcessService.getMedainScoreByOrgIdsAssessmentContentGradeSchoolYear(organizationIds, assessmentProgramId,
				studentReport.getContentAreaId(), Arrays.asList(studentReport.getGradeId()), schoolYear);
		if(CollectionUtils.isNotEmpty(medianScores)){
			for(ReportsMedianScore medians : medianScores){
				if(medians.getStudentCount() < Integer.parseInt(reportMinimumRequiredAMP)){
					medians.setScore(null);
					medians.setStandardDeviation(null);
					medians.setStandardError(null);
				}
			}
		}
		studentReport.setMedianScores(medianScores);
		studentReport.setAssessmentProgramCode(assessmentProgramCode);
		studentReport.setContentAreaCode(contentArea.getAbbreviatedName());
		studentReport.setContentAreaName(contentArea.getName());
		studentReport.setGradeCode(gradeCourse.getAbbreviatedName());
		studentReport.setGradeName(gradeCourse.getName());
		studentReport.setCurrentSchoolYear(schoolYear);
		studentReport.setSchoolYear(schoolYear);
		
		List<ReportSubscores> studentSubScores = new ArrayList<ReportSubscores>();
		List<ReportSubscores> studentScores = batchReportProcessService.selectSubscoresDetailsByStudentReportIdAndReportType(studentReport.getId(), "Student",studentReport.getContentAreaId(),studentReport.getAttendanceSchoolId());

		
		if(CollectionUtils.isNotEmpty(studentScores)){
			studentSubScores.addAll(studentScores);
		}
		
		studentReport.setTestLevelSubscoreBuckets(studentSubScores);
		Collections.sort(studentReport.getTestLevelSubscoreBuckets());
		/*
		List<ReportSubscores> schoolSubScores = batchReportProcessService.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, studentReport.getAttendanceSchoolId(), "Student");
		if(CollectionUtils.isNotEmpty(schoolSubScores)){
			subScores.addAll(schoolSubScores);
		}
		List<ReportSubscores> districtSubScores = batchReportProcessService.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, studentReport.getDistrictId(), "Student");
		if(CollectionUtils.isNotEmpty(districtSubScores)){
			subScores.addAll(districtSubScores);
		}
		List<ReportSubscores> stateSubScores = batchReportProcessService.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, studentReport.getStateId(), "Student");
		if(CollectionUtils.isNotEmpty(stateSubScores)){
			subScores.addAll(stateSubScores);
		}
		studentReport.setSubscoreBuckets(subScores);*/
		
		// determine if the previous year's level should be suppressed or not
		SuppressedLevel supp = batchReportProcessService.getSuppressedLevel(contentArea.getId(), gradeCourse.getId());
		if (supp != null) {
			studentReport.setPrevLevelString("Suppressed");
		} else if (studentReport.getPreviousYearLevelId() == null) {
			studentReport.setPrevLevelString("N/A");
		} else {
			LevelDescription level = levelDescriptionService.getById(studentReport.getPreviousYearLevelId());
			studentReport.setPrevLevelString(level.getLevel().toString());
		}
		String mdptScorableFlag = String.valueOf(studentReport.isMdptScorableFlag());
		String suppressmdptScore = String.valueOf(studentReport.getSuppressMdptScore());
		
		if("true".equalsIgnoreCase(suppressmdptScore) && !("true".equalsIgnoreCase(mdptScorableFlag))){
			studentReport.setScCodeExist(batchReportProcessService.checkNonScorableMDPT(studentReport.getStudentId(),studentReport.getStudentPerformanceTestId()));
		}
			
		if((studentReport.getMetametricsMeasure()==null || studentReport.getMetametricsMeasure().equals("")) && (contentArea.getAbbreviatedName().equals("ELA") || contentArea.getAbbreviatedName().equals("M"))){			
			String msg = "Metametric Score is not available for student - "+studentReport.getStudentId();
			logger.debug(msg);
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());		
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
		}	
		
		String filePath = "";
		Boolean status = true;
		
		try {
			filePath = isrGenerator.generateReportFile(studentReport);
			studentReport.setFilePath(filePath);
		} catch (Exception e) {
			logger.error("Exception for file not generation --> :" +e.getLocalizedMessage(), e);
			status = false;
		}
		
		studentReport.setGenerated(status);
		if(!status){
			studentReport.setFilePath(null);
		}
		return studentReport;
    }
	
	
		public StepExecution getStepExecution() {
			return stepExecution;
		}

		public void setStepExecution(StepExecution stepExecution) {
			this.stepExecution = stepExecution;
		}

		public Long getAssessmentProgramId() {
			return assessmentProgramId;
		}

		public void setAssessmentProgramId(Long assessmentProgramId) {
			this.assessmentProgramId = assessmentProgramId;
		}

		public String getAssessmentProgramCode() {
			return assessmentProgramCode;
		}

		public void setAssessmentProgramCode(String assessmentProgramCode) {
			this.assessmentProgramCode = assessmentProgramCode;
		}

		public Long getBatchReportProcessId() {
			return batchReportProcessId;
		}

		public void setBatchReportProcessId(Long batchReportProcessId) {
			this.batchReportProcessId = batchReportProcessId;
		}


		public ContentArea getContentArea() {
			return contentArea;
		}


		public void setContentArea(ContentArea contentArea) {
			this.contentArea = contentArea;
		}


		public GradeCourse getGradeCourse() {
			return gradeCourse;
		}


		public void setGradeCourse(GradeCourse gradeCourse) {
			this.gradeCourse = gradeCourse;
		}


		public Long getSchoolYear() {
			return schoolYear;
		}


		public void setSchoolYear(Long schoolYear) {
			this.schoolYear = schoolYear;
		}


		public Long getTestingProgramId() {
			return testingProgramId;
		}


		public void setTestingProgramId(Long testingProgramId) {
			this.testingProgramId = testingProgramId;
		}
		
		
}
