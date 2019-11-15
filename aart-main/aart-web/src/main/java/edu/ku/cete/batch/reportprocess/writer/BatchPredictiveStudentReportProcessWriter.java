package edu.ku.cete.batch.reportprocess.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;


public class BatchPredictiveStudentReportProcessWriter implements ItemWriter<InterimStudentReport>{
	private final static Log logger = LogFactory .getLog(BatchPredictiveStudentReportProcessWriter.class);
	@Autowired 
	BatchReportProcessService batchReportProcessService;

	
	private String processByStudentId;
	private Long assessmentProgramId;
	private Long schoolYear;
	private Long testingProgramId;
	private Long gradeId;
	private Long contentAreaId;
	
	private String generateSpecificISROption; 
	@Override
	public void write(List<? extends InterimStudentReport> reports)
			throws Exception {
		
		for (InterimStudentReport interimStudentReport : reports) {
			writeReason("InterimStudentReport writer started with studentid - "+interimStudentReport.getStudentId());
			if(interimStudentReport.getFilePath() != null){
				batchReportProcessService.updateInterimStudentReportFilePath(interimStudentReport);
				batchReportProcessService.updateStudentReportReprocessByStudentIdByIsrOption(interimStudentReport.getStudentId(),getAssessmentProgramId(), getContentAreaId(), getGradeId(), getSchoolYear(), generateSpecificISROption, getTestingProgramId());
		      }else{
		    	  writeReason("No report created for studentid - "+interimStudentReport.getStudentId());
		      }
		}
		
	}

	public String getProcessByStudentId() {
		return processByStudentId;
	}

	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}
	
	private void writeReason(String msg) {
		logger.debug(msg);
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

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getGenerateSpecificISROption() {
		return generateSpecificISROption;
	}

	public void setGenerateSpecificISROption(String generateSpecificISROption) {
		this.generateSpecificISROption = generateSpecificISROption;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}


}
