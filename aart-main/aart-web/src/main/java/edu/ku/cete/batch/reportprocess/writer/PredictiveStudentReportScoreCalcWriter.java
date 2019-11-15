/**
 * 
 */
package edu.ku.cete.batch.reportprocess.writer;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 8, 2017 6:43:01 PM
 */
public class PredictiveStudentReportScoreCalcWriter implements ItemWriter<InterimStudentReport> {

	final static Log logger = LogFactory.getLog(PredictiveStudentReportScoreCalcWriter.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
    private Long batchReportProcessId;
    
    @Autowired
	private EnrollmentService enrollmentService;
    
	@Override
	public void write(List<? extends InterimStudentReport> studentReportList) throws Exception {
		
		if(!studentReportList.isEmpty()){
			
			for(InterimStudentReport studentReport : studentReportList){
				logger.debug("Inside PredictiveStudentReportScoreCalcWriter ....writing for Student Id - " + studentReport.getStudentId());
				studentReport.setExitStatus(determineExitStatus(studentReport));
				studentReport.setTransferred(setTransferredFlag(studentReport));
				studentReport.setBatchReportProcessId(batchReportProcessId);
				
				batchReportProcessService.insertInterimPreditiveStudentReport(studentReport);
				
				List<StudentReportQuestionInfo> studentReportQnInfoList = studentReport.getReportQuestionInformation();
				if(CollectionUtils.isNotEmpty(studentReportQnInfoList)){
					for(StudentReportQuestionInfo studentReportQnInfo : studentReportQnInfoList){
						studentReportQnInfo.setInterimStudentReportId(studentReport.getId());
						studentReportQnInfo.setCreatedUser(studentReport.getCreatedUser());
						studentReportQnInfo.setModifiedUser(studentReport.getCreatedUser());
						batchReportProcessService.insertPredictiveStudentReportQuestionInfo(studentReportQnInfo);
					}
				}
				if(studentReport.getIsProcessBySpecificStudentId() != null && studentReport.getIsProcessBySpecificStudentId()){
					batchReportProcessService.updatePreditviceStudentReportReprocessByStudentId(studentReport);
				}
				
				logger.debug("Completed PredictiveStudentReportScoreCalcWriter for Student Id - " + studentReport.getStudentId());
			}
		}
		
	}

	private Boolean determineExitStatus(InterimStudentReport studentReport) {
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
	
	private Boolean setTransferredFlag(InterimStudentReport studentReport){
		Boolean transferred = false;
		List<Enrollment> rosteredEnrollments = enrollmentService.getRosteredEnrollmentsByStudentIdSubjectSchYr(studentReport.getStudentId(), 
				studentReport.getSchoolYear(), studentReport.getContentAreaId());
		
		if(CollectionUtils.isNotEmpty(rosteredEnrollments) && rosteredEnrollments.size() > 1){
			for(Enrollment en : rosteredEnrollments){
				if(en.getId() != studentReport.getEnrollmentId()){
					transferred = true;
					break;
				}
			}
		}
		return transferred;
	}
	
	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

}
