/**
 * 
 */
package edu.ku.cete.batch.reportprocess.writer;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 10:56:32 AM
 */
public class DynamicBundleofStudentSummaryReportsProcessWriter implements ItemWriter<StudentReport> {
	final static Log logger = LogFactory.getLog(DynamicBundleofStudentSummaryReportsProcessWriter.class);

	@Autowired 
	BatchReportProcessService batchReportProcessService;

	private OrganizationBundleReport bundleRequest;
   
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
		if(!studentReportList.isEmpty()){
           
			//UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//User user = userDetails.getUser();
			
			for(StudentReport studentReport : studentReportList){
				logger.debug("Inside  DynamicBundleofStudentSummaryReportsProcessWriter ....");
				
				OrganizationReportDetails studenSummaryBundleReoport = new OrganizationReportDetails();
				studenSummaryBundleReoport.setAssessmentProgramId(studentReport.getAssessmentProgramId());
				studenSummaryBundleReoport.setSchoolYear(studentReport.getSchoolYear());
				studenSummaryBundleReoport.setGradeCourseAbbrName(studentReport.getGradeCode());
				studenSummaryBundleReoport.setOrganizationId(studentReport.getAttendanceSchoolId());
				studenSummaryBundleReoport.setCreatedDate(new Date());
				studenSummaryBundleReoport.setModifiedDate(new Date());
				studenSummaryBundleReoport.setCreatedUser(12l);
				studenSummaryBundleReoport.setModifiedUser(12l);
				studenSummaryBundleReoport.setBatchReportProcessId(bundleRequest.getId());
				studenSummaryBundleReoport.setDetailedReportPath(studentReport.getDetailedReportPath());
				studenSummaryBundleReoport.setReportType(studentReport.getReportType());
				studenSummaryBundleReoport.setSchoolReportPdfSize(studentReport.getSchoolReportPdfSize());
				batchReportProcessService.insertSchoolReportOfStudentFilesPdf(studenSummaryBundleReoport);
				
				logger.debug("Completed DynamicBundleofStudentSummaryReportsProcessWriter Org Id - " + studentReport.getAttendanceSchoolId());
			}
		}
		
	}
	public OrganizationBundleReport getBundleRequest() {
		return bundleRequest;
	}

	public void setBundleRequest(OrganizationBundleReport bundleRequest) {
		this.bundleRequest = bundleRequest;
	}
}
