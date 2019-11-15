/**
 * 
 */
package edu.ku.cete.batch.reportprocess.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.impl.report.SchoolReportBundleUtil;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.SchoolReportDateUtil;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 10:50:11 AM
 */
public class DynamicBundleofStudentSummaryReportsProcessor implements ItemProcessor<Long, Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchSchoolPdfReportsProcessor.class);
	 
	 private Long requestProcessId;
	 private String assessmentProgramCode;
	 private OrganizationBundleReport bundleRequest;	
	 private List<Long> schoolIds;
	 private List<Long> gradeIds;
	 
	@Autowired
	BatchReportProcessService batchReportProcessService;
  
	private final static String PDF = ".PDF";
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Value("${sort.option.school}")
	private String school;
	
	@Value("${sort.option.grade}")
	private String grade;
  
	@Value("${external.import.reportType.studentSummary}")
	private String REPORT_TYPE_STUDENT_SUMMARY;	
    
	@Value("${external.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED;
	
	@Autowired
	private SchoolReportDateUtil schoolReportDateUtil;
	
	@Autowired
	private AwsS3Service s3;
	
	@Override
	public StudentReport process(Long separationId) throws Exception {		
		LOGGER.info("Inside the dynamic pdf report of student summary reports process for organization - " + bundleRequest.getOrganizationId() + " by user = " + bundleRequest.getCreatedUser());
		StudentReport studentReport = new StudentReport();
		studentReport.setAssessmentProgramId(bundleRequest.getAssessmentProgramId());
		studentReport.setSchoolYear(bundleRequest.getSchoolYear());
		studentReport.setAttendanceSchoolId(bundleRequest.getOrganizationId());
		List<StudentReport> studentReports = null;		
		studentReport.setReportType(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
		
		if(separationId.longValue() > 0){
			if(grade.equalsIgnoreCase(bundleRequest.getSort1())){
				studentReports = batchReportProcessService.getStudentSummaryReportsForDynamicBundleReport(schoolIds.size() > 0 ? schoolIds: Arrays.asList(bundleRequest.getOrganizationId()), Arrays.asList(separationId), bundleRequest.getAssessmentProgramId(),
						assessmentProgramCode, bundleRequest.getSchoolYear(), bundleRequest.getSort1(),bundleRequest.getSort2(),bundleRequest.getSort3(), REPORT_TYPE_STUDENT_SUMMARY);
			}else if(school.equalsIgnoreCase(bundleRequest.getSort1())){
				studentReports = batchReportProcessService.getStudentSummaryReportsForDynamicBundleReport(Arrays.asList(separationId), gradeIds, bundleRequest.getAssessmentProgramId(),
						assessmentProgramCode, bundleRequest.getSchoolYear(), bundleRequest.getSort1(),bundleRequest.getSort2(),bundleRequest.getSort3(), REPORT_TYPE_STUDENT_SUMMARY);

			}
		}else{
			studentReports = batchReportProcessService.getStudentSummaryReportsForDynamicBundleReport(schoolIds.size() > 0 ? schoolIds: Arrays.asList(bundleRequest.getOrganizationId()), gradeIds, bundleRequest.getAssessmentProgramId(),
					assessmentProgramCode, bundleRequest.getSchoolYear(), bundleRequest.getSort1(),bundleRequest.getSort2(),bundleRequest.getSort3(), REPORT_TYPE_STUDENT_SUMMARY);
		}
	
		if(CollectionUtils.isNotEmpty(studentReports)) {
			studentReport.setAttendanceSchoolName(studentReports.get(0).getAttendanceSchoolName());
			studentReport.setStateId(studentReports.get(0).getStateId());
			studentReport.setGradeCode(studentReports.get(0).getGradeCode());
			studentReport.setDistrictId(studentReports.get(0).getDistrictId());
			studentReport.setAttSchDisplayIdentifier(studentReports.get(0).getAttSchDisplayIdentifier());
			studentReport.setContentAreaCode(studentReports.get(0).getContentAreaCode());
			studentReport.setDistrictDisplayIdentifier(studentReports.get(0).getDistrictDisplayIdentifier());
			studentReport.setDistrictName(studentReports.get(0).getDistrictName());
			
			String generatedFileName = getSchoolPdfFileNameOfSchoolReports(studentReport);
			String generatedFilePath = getSchoolPdfFilePathOfSchoolReports(studentReport);
			String fullPath = FileUtil.buildFilePath(generatedFilePath, generatedFileName);
			String[] splitFullPath = fullPath.split("\\.");
			File dstFile = File.createTempFile(splitFullPath[0], PDF);
           
			Document document = new Document();
			try {
		        // step 2
		        PdfCopy copy = new PdfCopy(document, new FileOutputStream(dstFile));
		        // step 3
		        document.open();
		        // step 4
		        PdfReader reader = null;
		        int n;
		        // loop over the documents you want to concatenate
		        for(int i = 0; i < studentReports.size(); i++){
		        	try {
		        		String path = REPORT_PATH + studentReports.get(i).getFilePath();
		        		if (s3.doesObjectExist(path)){
		        			S3ObjectInputStream input = s3.getObject(path).getObjectContent();
			        		reader = new PdfReader(input);
			        		//close the object stream from s3
			        		input.close();
				            // loop over the pages in that document
				            n = reader.getNumberOfPages();
				            for (int page = 0; page < n; ) {
				                copy.addPage(copy.getImportedPage(reader, ++page));
				            }
				            if(n % 2 != 0) {
				            	copy.addPage(reader.getPageSize(1), reader.getPageRotation(1));
				            }
				            copy.freeReader(reader);
		        		} else {
			        		LOGGER.error("PDF "+path+" file not found in S3.");
			        	}
		        	} catch(Exception e) {
		        		LOGGER.error("PDF "+REPORT_PATH + studentReports.get(i).getFilePath()+" parse error", e);
		        	} finally {
		        		if(reader != null) {
		        			reader.close();
		        		}
		        	}
		        }
		        
		        //close before sending to s3
				if(document.isOpen()) {
					document.close();
				}
		        s3.synchMultipartUpload(fullPath, dstFile);
				studentReport.setDetailedReportPath(getDbFilePath(fullPath));			
				studentReport.setSchoolReportPdfSize(dstFile.length());
		        FileUtils.deleteQuietly(dstFile);
			} finally {
		        // step 5
				//also close if needed in case of an exception
				if(document.isOpen()) {
					document.close();
				}
			}
		} else {
			throw new SkipBatchException("Skipping dynamic pdf report of student summary reports process for organization - " + bundleRequest.getOrganizationId() + " by user = " + bundleRequest.getCreatedUser());
		}
  	return studentReport;
	}

  private String getDbFilePath(String destinationFileName) {
		String path = destinationFileName.replaceAll(REPORT_PATH, "");
		if(!path.startsWith(File.separator)) {
			path = File.separator+path;
		}
		return path;
	}
  
	private String getSchoolPdfFilePathOfSchoolReports(StudentReport studentReport) throws IOException {
		String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+"external"+File.separator+assessmentProgramCode+File.separator+studentReport.getSchoolYear()+File.separator+ "StudentSummaryBundled" + File.separator
				+ studentReport.getStateId() + File.separator + studentReport.getDistrictId()
				+ (getSchoolIds().size()  == 0 ? File.separator + studentReport.getAttendanceSchoolId() : "");
		return orgDir;

	}

	private String getSchoolPdfFileNameOfSchoolReports(
			StudentReport studentReport) {
		if(bundleRequest.getSeparateFile()){
			if(grade.equalsIgnoreCase(bundleRequest.getSort1())){
				    return (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictDisplayIdentifier()) : SchoolReportBundleUtil.sanitizeValues(studentReport.getAttSchDisplayIdentifier()))
						    + "_" + (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictName())) : SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName())))
						    +"_Kite_"+SchoolReportBundleUtil.sanitizeValues(studentReport.getGradeCode())+ "_" +studentReport.getSchoolYear()+ "_"+ schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;
				    
			}else if(school.equalsIgnoreCase(bundleRequest.getSort1())){
					return (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictDisplayIdentifier()) : SchoolReportBundleUtil.sanitizeValues(studentReport.getAttSchDisplayIdentifier()))
						    + "_" + (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictName())) : SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName())))
						    +"_Kite_"+SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName())+ "_" +studentReport.getSchoolYear()+ "_"+ schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;
			}else{
				return (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictDisplayIdentifier()) : SchoolReportBundleUtil.sanitizeValues(studentReport.getAttSchDisplayIdentifier()))
					    + "_" + (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictName())) : SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName())))
					    +"_Kite_"+studentReport.getSchoolYear()+ "_"+ schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;
			}
		}else{
		        return (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictDisplayIdentifier()) : SchoolReportBundleUtil.sanitizeValues(studentReport.getAttSchDisplayIdentifier()))
					    + "_" + (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictName())) : SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName())))
					    +"_Kite_"+studentReport.getSchoolYear()+ "_"+ schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;	
		}
			
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public Long getRequestProcessId() {
		return requestProcessId;
	}

	public void setRequestProcessId(Long requestProcessId) {
		this.requestProcessId = requestProcessId;
	}

	public OrganizationBundleReport getBundleRequest() {
		return bundleRequest;
	}

	public void setBundleRequest(OrganizationBundleReport bundleRequest) {
		this.bundleRequest = bundleRequest;
	}

	public List<Long> getSchoolIds() {
		return schoolIds;
	}

	public void setSchoolIds(List<Long> schoolIds) {
		this.schoolIds = schoolIds;
	}

	public List<Long> getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(List<Long> gradeIds) {
		this.gradeIds = gradeIds;
	}

}
