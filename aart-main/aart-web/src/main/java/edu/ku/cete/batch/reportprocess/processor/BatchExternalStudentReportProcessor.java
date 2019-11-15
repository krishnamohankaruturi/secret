package edu.ku.cete.batch.reportprocess.processor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.report.CpassStudentReportGenerator;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.web.ExternalStudentReportDTO;

public class BatchExternalStudentReportProcessor implements ItemProcessor<Long, Object> {
	
	private final static Log logger = LogFactory .getLog(BatchExternalStudentReportProcessor.class);
	
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Long schoolYear;
	private Long stateId;
    private String assessmentCode;
	private String reportCycle;
	private Long testingProgramId;
	private String processByStudentId;
	private String reprocessEntireDistrict;
	private String generateSpecificISROption;
    private List<TestCutScores> testCutScores;
    private StepExecution stepExecution;
    private Long reportProcessId;
    
	@Autowired
	private StudentReportService studentReportService;
		
	@Autowired
	private CpassStudentReportGenerator cpassStudentReportGenerator;
	
	@Override
	public ExternalStudentReportDTO process(Long studentId) throws Exception {
	 logger.info("Cpass StudentReportDto process started with studentid - "+studentId);
	 
	if (getGenerateSpecificISROption()!=null && StringUtils.equalsIgnoreCase(getGenerateSpecificISROption(), "2")){
			setReprocessEntireDistrict("TRUE");
	}else{
			setReprocessEntireDistrict("FALSE");
	}	 
	 
	 ExternalStudentReportDTO studentReport = studentReportService.getStudentForReportGeneration(assessmentProgramId, schoolYear, stateId, assessmentCode, reportCycle, testingProgramId, processByStudentId, getReprocessEntireDistrict(), studentId);
	 if(studentReport != null){			 
		 try{
			 studentReport.setAssessmentProgram(assessmentProgramCode); 
		     if(getTestCutScores().size() == 0){
		    	 throw new SkipBatchException("TestCut score is not present for assessmentCode - "+assessmentCode);
	         }			
		     
		     if(studentReport.getDistrictShortName()!=null && !studentReport.getDistrictShortName().equals(""))
		     studentReport.setDistrictName(studentReport.getDistrictShortName());
		     
		     if(studentReport.getSchoolShortName()!=null && !studentReport.getSchoolShortName().equals(""))
		     studentReport.setSchoolName(studentReport.getSchoolShortName());
					     
		     studentReport.setTestCutScores(getTestCutScores());		     		     
			 //call report generation method	
			 return cpassStudentReportGenerator.generateReportFile(studentReport);
			
		 }catch(Exception e){
			 logger.error("Exception for file not generation --> :" +e.getLocalizedMessage(), e);
			 writeReason("PDF geneartion problem for student - "+studentId+" with exception "+ e.getLocalizedMessage());
			 return null; 
		 }		
	 }	 
	 logger.info("externalstudentreports process completed with studentid - "+studentId);
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
	public String getAssessmentCode() {
		return assessmentCode;
	}
	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}
	public List<TestCutScores> getTestCutScores() {
		return testCutScores;
	}
	public void setTestCutScores(List<TestCutScores> testCutScores) {
		this.testCutScores = testCutScores;
	}
	public String getReportCycle() {
		return reportCycle;
	}
	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}
	public Long getTestingProgramId() {
		return testingProgramId;
	}
	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
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

}
