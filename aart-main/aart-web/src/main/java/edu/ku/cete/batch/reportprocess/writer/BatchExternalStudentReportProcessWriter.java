package edu.ku.cete.batch.reportprocess.writer;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.web.ExternalStudentReportDTO;

public class BatchExternalStudentReportProcessWriter implements ItemWriter<ExternalStudentReportDTO>{

	private final static Log logger = LogFactory .getLog(BatchExternalStudentReportProcessWriter.class);
	
	@Autowired 
	StudentReportService studentReportService;
		
	@Autowired 
	BatchReportProcessService batchReportProcessService;

	private Long schoolYear;
	private Long assessmentProgramId;
	private String processByStudentId;
	private String generateSpecificISROption; 
	private Long testingProgramId;
	private Long gradeId;
	private Long contentAreaId;
	
	@Override
	public void write(List<? extends ExternalStudentReportDTO> reports)
			throws Exception {
		
		for (ExternalStudentReportDTO externalStudentReportDTO : reports) {
			writeReason("CpassExternalStudentReportDTO writer started with studentid - "+externalStudentReportDTO.getStudentId());
			if(externalStudentReportDTO.getFilePath() != null){
				studentReportService.updateStudentReportFilePath(externalStudentReportDTO);
				if(processByStudentId.equalsIgnoreCase("TRUE")){
					batchReportProcessService.updateStudentReportReprocessByStudentIdByIsrOption(externalStudentReportDTO.getStudentId(),getAssessmentProgramId(), getContentAreaId(), getGradeId(), getSchoolYear(), generateSpecificISROption, getTestingProgramId());	
				}				
		      }else{
		    	  writeReason("No report created for studentid - "+externalStudentReportDTO.getStudentId());
		      }
		}
		
	}
	
	private void writeReason(String msg) {
		logger.debug(msg);
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getGenerateSpecificISROption() {
		return generateSpecificISROption;
	}

	public void setGenerateSpecificISROption(String generateSpecificISROption) {
		this.generateSpecificISROption = generateSpecificISROption;
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

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public String getProcessByStudentId() {
		return processByStudentId;
	}

	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}


}
