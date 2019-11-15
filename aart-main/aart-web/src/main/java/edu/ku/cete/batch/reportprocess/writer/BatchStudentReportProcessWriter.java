package edu.ku.cete.batch.reportprocess.writer;

 
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;

 
/**
 * Writes products to a database
 * @param <T>
 */
public class BatchStudentReportProcessWriter implements ItemWriter<StudentReport>
{
	final static Log logger = LogFactory.getLog(BatchUploadJobListener.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
     private Long batchReportProcessId;
     private String  processByStudentId;
     private Long createdUserId;
     
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
			if(!studentReportList.isEmpty()){
				for(StudentReport studentReport : studentReportList){
					logger.debug("Inside BatchStudentReportProcessWriter ....writing for Student Id - " + studentReportList.get(0).getStudentId());
					logger.debug("Inside BatchStudentReportProcessWriter ....Path for  Student report - " + studentReport.getFilePath());
					studentReport.setModifiedUserId(createdUserId);	
					batchReportProcessService.updateStudentReportFilePath(studentReport.getFilePath(),studentReport.getGenerated(), studentReport.getId(), batchReportProcessId,studentReport.getAttendanceSchoolName(),studentReport.getDistrictName(),null);
					logger.debug("Completed BatchStudentReportProcessWriter for Student Id - " + studentReportList.get(0).getStudentId());
					if(StringUtils.equalsIgnoreCase(processByStudentId, "true") && studentReport.getStudentReportReprocessId()!=null){
						batchReportProcessService.UpdateStudentReportReprocessStatusById(studentReport.getStudentReportReprocessId(),studentReport.getStatus());
					}					
				}
			}
	}


	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}


	public String getProcessByStudentId() {
		return processByStudentId;
	}


	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}


	public Long getCreatedUserId() {
		return createdUserId;
	}


	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}
	
	 
	

}
