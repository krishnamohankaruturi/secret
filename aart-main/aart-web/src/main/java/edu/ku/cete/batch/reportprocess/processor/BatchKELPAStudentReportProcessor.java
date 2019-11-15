package edu.ku.cete.batch.reportprocess.processor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.report.KelpaStudentReportGenerator;
import edu.ku.cete.service.StudentReportService;


public class BatchKELPAStudentReportProcessor implements ItemProcessor<Long, Object> {
	
	private final static Log logger = LogFactory .getLog(BatchKELPAStudentReportProcessor.class);
	
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Long schoolYear;
	private Long stateId;
	private Long gradeCourseId;
	private Long contentAreaId;
    private StepExecution stepExecution;
    private Long reportProcessId;
    private String processByStudentId;
	private String reprocessEntireDistrict;
	private String generateSpecificISROption;
    private List<LevelDescription> levelDescriptions;
    private Map<Long, String> scoringStatusMap;
    private Map<Long, String> testStatusMap;
    private Map<String, String> staticPDFcontentMap;
    private Map<String, String> progressionTextMap;
    private List<Category> domainPerfLevel;  

    @Autowired
	private StudentReportService studentReportService;
		
	@Autowired
	private KelpaStudentReportGenerator kelpaStudentReportGenerator;
	
	@Override
	public StudentReport process(Long studentId) throws Exception {
	 logger.info("KELPA StudentReportDto process started with studentid - "+studentId);
	 
    if (getGenerateSpecificISROption()!=null && StringUtils.equalsIgnoreCase(getGenerateSpecificISROption(), "2")){
			setReprocessEntireDistrict("TRUE");
	 }else{
			setReprocessEntireDistrict("FALSE");
	}
	 
	 List<StudentReport> studentReport = studentReportService.getStudentForKELPAReport(assessmentProgramId, schoolYear, stateId, studentId, gradeCourseId, contentAreaId, processByStudentId, reprocessEntireDistrict);
	 
	 if(studentReport != null && studentReport.size()>0){			 
	   //Call generator method 
		  return kelpaStudentReportGenerator.generateReportFile(studentReport, staticPDFcontentMap, domainPerfLevel, testStatusMap, scoringStatusMap, levelDescriptions, progressionTextMap);
	 }else{
		 writeReason("No details for the studentid - "+studentId);
	 }
	 
	 logger.info("KELPA StudentReportDto process completed with studentid - "+studentId);
	return null;
  }
	private void writeReason(String msg) {
		ReportProcessReason reportProcessReason = new ReportProcessReason();
		reportProcessReason.setReason(msg);
		reportProcessReason.setReportProcessId(getReportProcessId());
		((CopyOnWriteArrayList<ReportProcessReason>) getStepExecution().getExecutionContext().get("stepReasons")).add(reportProcessReason);
		throw new SkipBatchException(msg);
		
	}
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	public Long getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}
	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
	public StepExecution getStepExecution() {
		return stepExecution;
	}
	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
	public Long getReportProcessId() {
		return reportProcessId;
	}
	public void setReportProcessId(Long reportProcessId) {
		this.reportProcessId = reportProcessId;
	}
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	public Long getContentAreaId() {
		return contentAreaId;
	}
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	public List<LevelDescription> getLevelDescriptions() {
		return levelDescriptions;
	}
	public void setLevelDescriptions(List<LevelDescription> levelDescriptions) {
		this.levelDescriptions = levelDescriptions;
	}
	public Map<Long, String> getScoringStatusMap() {
		return scoringStatusMap;
	}
	public void setScoringStatusMap(Map<Long, String> scoringStatusMap) {
		this.scoringStatusMap = scoringStatusMap;
	}
	public Map<Long, String> getTestStatusMap() {
		return testStatusMap;
	}
	public void setTestStatusMap(Map<Long, String> testStatusMap) {
		this.testStatusMap = testStatusMap;
	}
	public Map<String, String> getStaticPDFcontentMap() {
		return staticPDFcontentMap;
	}
	public void setStaticPDFcontentMap(Map<String, String> staticPDFcontentMap) {
		this.staticPDFcontentMap = staticPDFcontentMap;
	}
	public Map<String, String> getProgressionTextMap() {
		return progressionTextMap;
	}
	public void setProgressionTextMap(Map<String, String> progressionTextMap) {
		this.progressionTextMap = progressionTextMap;
	}
	public List<Category> getDomainPerfLevel() {
		return domainPerfLevel;
	}
	public void setDomainPerfLevel(List<Category> domainPerfLevel) {
		this.domainPerfLevel = domainPerfLevel;
	}
	public String getProcessByStudentId() {
		return processByStudentId;
	}
	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}
	public String getReprocessEntireDistrict() {
		return reprocessEntireDistrict;
	}
	public void setReprocessEntireDistrict(String reprocessEntireDistrict) {
		this.reprocessEntireDistrict = reprocessEntireDistrict;
	}
	public String getGenerateSpecificISROption() {
		return generateSpecificISROption;
	}
	public void setGenerateSpecificISROption(String generateSpecificISROption) {
		this.generateSpecificISROption = generateSpecificISROption;
	}

}
