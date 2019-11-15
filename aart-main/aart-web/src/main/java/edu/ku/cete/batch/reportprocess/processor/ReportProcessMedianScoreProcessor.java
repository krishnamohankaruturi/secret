package edu.ku.cete.batch.reportprocess.processor;

 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;
public class ReportProcessMedianScoreProcessor implements ItemProcessor<ReportsMedianScore,Object>
{
    private StepExecution stepExecution;
    private Long batchReportProcessId;
    private String assessmentProgramCode;
    
    @Value("${report.minimum.required.kap}")
	private String reportMinimumRequiredKAP;
    
    @Value("${report.minimum.required.amp}")
   	private String reportMinimumRequiredAMP;
    
    @Value("${levelDescriptionType.mdpt}")
   	private String levelDescriptionTypeMDPT;
    
    @Value("${levelDescriptionType.main}")
   	private String levelDescriptionTypeMain;
    
	@Value("${levelDescriptionType.combined}")
   	private String levelDescriptionTypeCombined;
    
    @Autowired
	private BatchReportProcessService batchReportProcessService;

    final static Log logger = LogFactory.getLog(ReportProcessMedianScoreProcessor.class);
    
	@Override
    public Map<String, Object> process(ReportsMedianScore reportsMedianScore) throws Exception {
		logger.info("Inside Calculate School, State, District Median Score process....Org -" + reportsMedianScore.getOrganizationId() );
		Map<String, Object> reportsMap = new HashMap<String, Object>();
		List<StudentReport> studentsByOrg = new ArrayList<StudentReport>();
		//List<StudentReportDto> studentsByOrgForMdptLevel = new ArrayList<StudentReportDto>();
		//List<StudentReportDto> studentsByOrgForCombinedLevel = new ArrayList<StudentReportDto>();
		Integer levelCount;
		
		if(reportsMedianScore.getOrgTypeCode().equalsIgnoreCase("SCH")){
			studentsByOrg = batchReportProcessService.getStudentsBySchoolAssessmentGradeSubject(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(),
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());
			
			/*studentsByOrgForMdptLevel = batchReportProcessService.getStudentsBySchoolAssessmentGradeSubjectForMdptLevel(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(),
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());
			
			studentsByOrgForCombinedLevel = batchReportProcessService.getStudentsBySchoolAssessmentGradeSubjectForCombinedLevel(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(),
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());*/
			
		}else if(reportsMedianScore.getOrgTypeCode().equalsIgnoreCase("DT")){
			studentsByOrg = batchReportProcessService.getStudentsByDistrictAssessmentGradeSubject(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(), 
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());
			
			/*studentsByOrgForMdptLevel = batchReportProcessService.getStudentsByDistrictAssessmentGradeSubjectForMdptLevel(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(), 
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());	
			
			studentsByOrgForCombinedLevel = batchReportProcessService.getStudentsByDistrictAssessmentGradeSubjectForCombinedLevel(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(), 
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());*/
			
		}else if(reportsMedianScore.getOrgTypeCode().equalsIgnoreCase("ST")){
			studentsByOrg = batchReportProcessService.getStudentsByStateAssessmentGradeSubject(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(), 
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());
			
			/*studentsByOrgForMdptLevel = batchReportProcessService.getStudentsByStateAssessmentGradeSubjectForMdptLevel(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(), 
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());
			
			studentsByOrgForCombinedLevel = batchReportProcessService.getStudentsByStateAssessmentGradeSubjectForCombinedLevel(reportsMedianScore.getOrganizationId(), 
					reportsMedianScore.getAssessmentProgramId(),
					reportsMedianScore.getContentAreaId(), 
					reportsMedianScore.getGradeId(),
					reportsMedianScore.getSchoolYear());*/
		}
		List<Long> studentScaleScores = new ArrayList<Long>();
		for(StudentReport studentReport : studentsByOrg){
			reportsMedianScore.setSchoolYear(studentReport.getSchoolYear());
			studentScaleScores.add(studentReport.getScaleScore());
		}
		boolean calcMedainFlag = true;
		// Calculate median scores
		int studentCount = studentScaleScores.size();
		if(assessmentProgramCode.equalsIgnoreCase("KAP")){
			if(studentCount < Integer.parseInt(reportMinimumRequiredKAP)){
				calcMedainFlag = false;
			}
		}
		
		if(calcMedainFlag  && studentCount > 0){
			reportsMedianScore.setScore(batchReportProcessService.calcMedianScore(studentScaleScores, studentCount));
			double standardDeviation = batchReportProcessService.calcStandardDeviation(studentScaleScores);
			reportsMedianScore.setStandardDeviation(BigDecimal.valueOf(standardDeviation));
			double standardError = standardDeviation / Math.sqrt(studentCount);
			// US16347 - Multiplying standard error with 1.253 
			reportsMedianScore.setStandardError(BigDecimal.valueOf(standardError * 1.253));
		}
		reportsMedianScore.setStudentCount(studentCount);
		reportsMap.put("median", reportsMedianScore);
		logger.debug("Completed Calculate School, State, District Median Score and standard Error.");

		// Calculate percentages by main level		
		Map<Long, Integer> levelMap = new HashMap<Long, Integer>();
		for(StudentReport studentRep : studentsByOrg){
			levelCount = levelMap.get(studentRep.getLevel());
			if(levelCount == null){
				levelMap.put(studentRep.getLevel(), 1);
			}else{
				levelMap.put(studentRep.getLevel(), levelCount + 1);
			}
		}
		List<ReportsPercentByLevel> reportsPercentByLevelList = new ArrayList<ReportsPercentByLevel>();
		for(Long level : levelMap.keySet()){
				ReportsPercentByLevel reportsPercentByLevel = new ReportsPercentByLevel();
				reportsPercentByLevel.setAssessmentProgramId(reportsMedianScore.getAssessmentProgramId());
				reportsPercentByLevel.setContentAreaId(reportsMedianScore.getContentAreaId());
				reportsPercentByLevel.setGradeId(reportsMedianScore.getGradeId());
				reportsPercentByLevel.setOrganizationId(reportsMedianScore.getOrganizationId());
				reportsPercentByLevel.setOrganizationTypeId(reportsMedianScore.getOrganizationTypeId());
				reportsPercentByLevel.setBatchReportProcessId(batchReportProcessId);
				Integer count = levelMap.get(level);
				reportsPercentByLevel.setStudentCount(count);
				reportsPercentByLevel.setLevel(level);
				reportsPercentByLevel.setSchoolYear(reportsMedianScore.getSchoolYear());
				reportsPercentByLevel.setPercent(Math.round((count/ (float) studentsByOrg.size()) *100));
				reportsPercentByLevel.setLevelType(levelDescriptionTypeMain);
				reportsPercentByLevelList.add(reportsPercentByLevel);
		}
		reportsMap.put("mainlevelpercentages", reportsPercentByLevelList);
		
		//calculate percentages by MDPT level
		/*Map<Long, Integer> mdptLevelMap = new HashMap<Long, Integer>();
		for(StudentReportDto studentRep : studentsByOrgForMdptLevel){
			levelCount = mdptLevelMap.get(studentRep.getLevel());
			if(levelCount == null){
				mdptLevelMap.put(studentRep.getLevel(), 1);
			}else{
				mdptLevelMap.put(studentRep.getLevel(), levelCount + 1);
			}
		}
		List<ReportsPercentByLevel> reportsMdptPercentByLevelList = new ArrayList<ReportsPercentByLevel>();
		for(Long level : mdptLevelMap.keySet()){
				ReportsPercentByLevel reportsPercentByLevel = new ReportsPercentByLevel();
				reportsPercentByLevel.setAssessmentProgramId(reportsMedianScore.getAssessmentProgramId());
				reportsPercentByLevel.setContentAreaId(reportsMedianScore.getContentAreaId());
				reportsPercentByLevel.setGradeId(reportsMedianScore.getGradeId());
				reportsPercentByLevel.setOrganizationId(reportsMedianScore.getOrganizationId());
				reportsPercentByLevel.setOrganizationTypeId(reportsMedianScore.getOrganizationTypeId());
				reportsPercentByLevel.setBatchReportProcessId(batchReportProcessId);
				Integer count = mdptLevelMap.get(level);
				reportsPercentByLevel.setStudentCount(count);
				reportsPercentByLevel.setLevel(level);
				reportsPercentByLevel.setSchoolYear(reportsMedianScore.getSchoolYear());
				reportsPercentByLevel.setPercent(Math.round((count/ (float) studentsByOrgForMdptLevel.size()) *100));
				reportsPercentByLevel.setLevelType(levelDescriptionTypeMDPT);
				reportsMdptPercentByLevelList.add(reportsPercentByLevel);
		}
		reportsMap.put("mdptlevelpercentages", reportsMdptPercentByLevelList);
		
		
		//Calculate percentages by CombinedLevel
		Map<Long, Integer> combinedLevelMap = new HashMap<Long, Integer>();
		for(StudentReportDto studentRep : studentsByOrgForCombinedLevel){
			levelCount = combinedLevelMap.get(studentRep.getLevel());
			if(levelCount == null){
				combinedLevelMap.put(studentRep.getLevel(), 1);
			}else{
				combinedLevelMap.put(studentRep.getLevel(), levelCount + 1);
			}
		}
		List<ReportsPercentByLevel> reportsCombinedPercentByLevelList = new ArrayList<ReportsPercentByLevel>();
		for(Long level : combinedLevelMap.keySet()){
				ReportsPercentByLevel reportsPercentByLevel = new ReportsPercentByLevel();
				reportsPercentByLevel.setAssessmentProgramId(reportsMedianScore.getAssessmentProgramId());
				reportsPercentByLevel.setContentAreaId(reportsMedianScore.getContentAreaId());
				reportsPercentByLevel.setGradeId(reportsMedianScore.getGradeId());
				reportsPercentByLevel.setOrganizationId(reportsMedianScore.getOrganizationId());
				reportsPercentByLevel.setOrganizationTypeId(reportsMedianScore.getOrganizationTypeId());
				reportsPercentByLevel.setBatchReportProcessId(batchReportProcessId);
				Integer count = combinedLevelMap.get(level);
				reportsPercentByLevel.setStudentCount(count);
				reportsPercentByLevel.setLevel(level);
				reportsPercentByLevel.setSchoolYear(reportsMedianScore.getSchoolYear());
				reportsPercentByLevel.setPercent(Math.round((count/ (float) studentsByOrgForCombinedLevel.size()) *100));
				reportsPercentByLevel.setLevelType(levelDescriptionTypeCombined);
				reportsCombinedPercentByLevelList.add(reportsPercentByLevel);
		}
		reportsMap.put("combinedlevelpercentages", reportsCombinedPercentByLevelList);*/
		
    	return reportsMap;
    }
	 	
		public StepExecution getStepExecution() {
			return stepExecution;
		}

		public void setStepExecution(StepExecution stepExecution) {
			this.stepExecution = stepExecution;
		}


		public Long getBatchReportProcessId() {
			return batchReportProcessId;
		}


		public void setBatchReportProcessId(Long batchReportProcessId) {
			this.batchReportProcessId = batchReportProcessId;
		}

		public String getAssessmentProgramCode() {
			return assessmentProgramCode;
		}

		public void setAssessmentProgramCode(String assessmentProgramCode) {
			this.assessmentProgramCode = assessmentProgramCode;
		}
}
