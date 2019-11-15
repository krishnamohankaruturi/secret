/**
 * 
 */
package edu.ku.cete.batch.reportprocess.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.impl.report.SchoolReportBundleUtil;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.SchoolReportDateUtil;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 1:57:29 PM
 */
public class StudentSummaryBundledReportProcessor implements ItemProcessor<Long, Object> {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentSummaryBundledReportProcessor.class);
	 
	private StepExecution stepExecution;
    private Long assessmentProgramId;
    private String assessmentProgramCode;
    private Long schoolYear;
    private Long batchReportProcessId;
    private String bundledReportType;
    private String gradeCourseAbbrName;
    
    private final static String PDF = ".pdf";
    
    @Autowired
	BatchReportProcessService batchReportProcessService;
    
    @Value("${print.test.file.path}")
	private String REPORT_PATH;    
    
    @Value("${external.import.reportType.studentSummary}")
	private String REPORT_TYPE_STUDENT_SUMMARY;
    
    @Value("${external.schoolLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL;
    
    @Value("${external.districtLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL;
    
    @Value("${external.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED;
    
    @Autowired
	private SchoolReportDateUtil schoolReportDateUtil;
    
    @Autowired
    private AwsS3Service s3;
	
    @Override
    public StudentReport process(Long organizationId) throws Exception {		
    	LOGGER.debug("Inside the StudentSummary Bundled reports process for organizationid - " + organizationId + " , gradeCourseAbbrName = " + gradeCourseAbbrName);
		StudentReport studentReport = new StudentReport();
		studentReport.setAssessmentProgramId(assessmentProgramId);
		studentReport.setSchoolYear(schoolYear);
		studentReport.setGradeCode(gradeCourseAbbrName);
		List<StudentReport> studentReports = null;
		
		if("CPASS".equalsIgnoreCase(assessmentProgramCode) || "DLM".equalsIgnoreCase(assessmentProgramCode)){
			if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL.equalsIgnoreCase(bundledReportType)){	
				studentReports = batchReportProcessService.getExternalStudentReportsForStudentSummaryBundledReport(assessmentProgramId, gradeCourseAbbrName, schoolYear, organizationId, REPORT_TYPE_STUDENT_SUMMARY);
			
			}else if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL.equalsIgnoreCase(bundledReportType)){
				studentReports = batchReportProcessService.getExternalStudentReportsForStudentSummaryDistrictBundledReport(assessmentProgramId, schoolYear, assessmentProgramCode, organizationId, REPORT_TYPE_STUDENT_SUMMARY);
			}		   
		}
		
		if(CollectionUtils.isNotEmpty(studentReports)) {
			String generatedFileName = null;
			String generatedFilePath = null;
			String fullPath = null;
			
			if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL.equalsIgnoreCase(bundledReportType)){
				studentReport.setAttendanceSchoolName(studentReports.get(0).getAttendanceSchoolName());
				studentReport.setStateId(studentReports.get(0).getStateId());
				studentReport.setDistrictId(studentReports.get(0).getDistrictId());
				studentReport.setAttendanceSchoolId(organizationId);
				studentReport.setAttSchDisplayIdentifier(studentReports.get(0).getAttSchDisplayIdentifier());
				studentReport.setContentAreaCode(studentReports.get(0).getContentAreaCode());
				studentReport.setReportType(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
				
				generatedFileName = getSchoolPdfFileNameOfStudentSummaryReports(studentReport);
				generatedFilePath = getSchoolPdfFilePathOfStudentSummaryReports(studentReport);
			}else if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL.equalsIgnoreCase(bundledReportType)){
				studentReport.setDistrictName(studentReports.get(0).getDistrictName());
				studentReport.setStateId(studentReports.get(0).getStateId());
				studentReport.setDistrictId(studentReports.get(0).getDistrictId());
				studentReport.setDistrictDisplayIdentifier(studentReports.get(0).getDistrictDisplayIdentifier());
				studentReport.setReportType(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
				
				generatedFileName = getDistrictPdfFileNameOfStudentSummaryReports(studentReport);
				generatedFilePath = getDistrictPdfFilePathOfStudentSummaryReports(studentReport);
			}		
			fullPath = FileUtil.buildFilePath(generatedFilePath, generatedFileName);
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
			throw new SkipBatchException("Skipping gradecourse  - " + gradeCourseAbbrName + " in organization - " + organizationId);
		}
		
		LOGGER.debug("Leaving StudentSummary Bundled report process for organizationid - " + organizationId);
		
    	return studentReport;
	}

    private String getDbFilePath(String destinationFileName) {
		String path = destinationFileName.replaceAll(REPORT_PATH, "");
		if(!path.startsWith(File.separator)) {
			path = File.separator+path;
		}
		return path;
	}
    
	private String getSchoolPdfFilePathOfStudentSummaryReports(StudentReport studentReport) throws IOException {
		String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+"external"+File.separator+assessmentProgramCode+File.separator+studentReport.getSchoolYear()+File.separator+ "StudentSummaryBundled" + File.separator
				+ studentReport.getStateId() + File.separator + studentReport.getDistrictId()
				+ File.separator + studentReport.getAttendanceSchoolId();
		return orgDir;
	}

	private String getSchoolPdfFileNameOfStudentSummaryReports(StudentReport studentReport) {
		if("CPASS".equalsIgnoreCase(assessmentProgramCode)){
		    return SchoolReportBundleUtil.sanitizeValues(assessmentProgramCode)+ "_" +studentReport.getAttSchDisplayIdentifier() + "_" + 
			SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName()))+"_" + 
				SchoolReportBundleUtil.sanitizeValues(studentReport.getContentAreaCode()) +"_"+SchoolReportBundleUtil.sanitizeValues(studentReport.getGradeCode())+ "_Kite_"+studentReport.getSchoolYear()+ "_" + "Bundled Student Summary" + "_" + schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;
		}else if("DLM".equalsIgnoreCase(assessmentProgramCode)){
			return SchoolReportBundleUtil.sanitizeValues(assessmentProgramCode)+ "_" +studentReport.getAttSchDisplayIdentifier() + "_" + 
					SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName()))+"_" + 
					SchoolReportBundleUtil.sanitizeValues(studentReport.getGradeCode())+ "_Kite_"+studentReport.getSchoolYear()+ "_" + "Bundled Student Summary" + "_" + schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;
		}else{
			return SchoolReportBundleUtil.sanitizeValues(studentReport.getAttSchDisplayIdentifier()) + "_" + 
			SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName()))+"_" + 
				SchoolReportBundleUtil.sanitizeValues(studentReport.getGradeCode()) + "_Kite_"+studentReport.getSchoolYear()+ "_"+ schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;

		}
	}

	private String getDistrictPdfFileNameOfStudentSummaryReports(StudentReport studentReport) {
		 return SchoolReportBundleUtil.sanitizeValues(assessmentProgramCode)+ "_" +studentReport.getDistrictDisplayIdentifier() + "_" + 
					SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictName()))+PDF;
	}

	private String getDistrictPdfFilePathOfStudentSummaryReports(StudentReport studentReport) throws IOException {	
		String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+"external"+File.separator+assessmentProgramCode+File.separator+studentReport.getSchoolYear()+File.separator+ "StudentSummaryBundled" + File.separator
				+ studentReport.getStateId() + File.separator + studentReport.getDistrictId();

		return orgDir;		
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public String getBundledReportType() {
		return bundledReportType;
	}

	public void setBundledReportType(String bundledReportType) {
		this.bundledReportType = bundledReportType;
	}

	public String getGradeCourseAbbrName() {
		return gradeCourseAbbrName;
	}

	public void setGradeCourseAbbrName(String gradeCourseAbbrName) {
		this.gradeCourseAbbrName = gradeCourseAbbrName;
	}
	
	public String getBaseReportPath(){
		return REPORT_PATH;
	}

}
