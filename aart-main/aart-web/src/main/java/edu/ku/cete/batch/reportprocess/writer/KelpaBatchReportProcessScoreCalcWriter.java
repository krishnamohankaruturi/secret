package edu.ku.cete.batch.reportprocess.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;

 
/**
 * Writes products to a database
 * @param <T>
 */
public class KelpaBatchReportProcessScoreCalcWriter implements ItemWriter<StudentReport>
{
	final static Log logger = LogFactory.getLog(KelpaBatchReportProcessScoreCalcWriter.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
     private Long batchReportProcessId;
 
     private Long testingProgramId;
     
     private String enableTroubleShooting;
   
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
		if(!studentReportList.isEmpty()){
			for(StudentReport studentReport : studentReportList){
				logger.debug("Inside KelpaBatchReportProcessWriter ....writing for Student Id - " + studentReport.getStudentId());
				studentReport.setExitStatus(determineExitStatus(studentReport));
				writeReportToDb(studentReport);
				logger.debug("Completed BatchReportProcessWriter for Student Id - " + studentReport.getStudentId());
			}
		}
	}

	private Boolean determineExitStatus(StudentReport studentReport) {
		Long enrollmentId = studentReport.getEnrollmentId();
		if (enrollmentId != null) {
			for (Enrollment enrl : studentReport.getEnrollments()) {
				if (enrollmentId.equals(enrl.getId())) {
					return enrl.getExitWithdrawalDate() != null;
				}
			}
		}
		return false;
	}
	
	private void writeReportToDb(StudentReport studentReport) {
		studentReport.setBatchReportProcessId(batchReportProcessId);
		batchReportProcessService.insertSelectiveStudentReport(studentReport);
		
		if(studentReport.getIsProcessBySpecificStudentId() != null && studentReport.getIsProcessBySpecificStudentId()){
			studentReport.setTestingProgramId(testingProgramId);
			batchReportProcessService.updateStudentReportReprocessByStudentId(studentReport);
		}
		
		// KELPA return file relies on these inserts, at least in 2018, so these records must be inserted
		//if(!"FALSE".equalsIgnoreCase(enableTroubleShooting)){
			if (studentReport.getStudentReportTestScores() != null) {
				studentReport.getStudentReportTestScores().setStudentReportId(studentReport.getId());
				batchReportProcessService.insertSelectiveStudentReportTestScores(studentReport.getStudentReportTestScores());
			}
		//}
		
	}


	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public String getEnableTroubleShooting() {
		return enableTroubleShooting;
	}

	public void setEnableTroubleShooting(String enableTroubleShooting) {
		this.enableTroubleShooting = enableTroubleShooting;
	}
	
}
