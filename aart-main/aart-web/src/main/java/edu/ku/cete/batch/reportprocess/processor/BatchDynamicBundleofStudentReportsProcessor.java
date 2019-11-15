package edu.ku.cete.batch.reportprocess.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/* sudhansu.b
 * Added for F460 - Dynamic bundle report
 */	
public class BatchDynamicBundleofStudentReportsProcessor implements ItemProcessor<Long,Object>{
	 private static final Logger LOGGER = LoggerFactory.getLogger(BatchSchoolPdfReportsProcessor.class);
	 
	 private Long requestProcessId;
	 private String assessmentProgramCode;
	 private OrganizationBundleReport bundleRequest;	
	 private List<Long> schoolIds;
	 private List<Long> subjectIds;
	 private List<Long> gradeIds;
	 
   @Autowired
	BatchReportProcessService batchReportProcessService;
   
   @Value("${print.test.file.path}")
	private String REPORT_PATH;
   
   @Value("${sort.option.subject}")
	private String subject;
	
	@Value("${sort.option.school}")
	private String school;
	
	@Value("${sort.option.grade}")
	private String grade;
	
	private final static String PDF = ".PDF"; 
   
    @Autowired
	private SchoolReportDateUtil schoolReportDateUtil;
    
    @Autowired
    private AwsS3Service s3;
    
   
   final static Log logger = LogFactory.getLog(BatchSchoolPdfReportsProcessor.class);
   
   @Override
   public StudentReport process(Long separationId) throws Exception {		
		logger.info("Inside the dynamic pdf report of student reports process for organization - " + bundleRequest.getOrganizationId() + " by user = " + bundleRequest.getCreatedUser());
		StudentReport studentReport = new StudentReport();
		studentReport.setAssessmentProgramId(bundleRequest.getAssessmentProgramId());
		studentReport.setSchoolYear(bundleRequest.getSchoolYear());
		studentReport.setAttendanceSchoolId(bundleRequest.getOrganizationId());
		List<StudentReport> studentReports = null;		

		if(separationId.longValue() > 0){
			if(subject.equalsIgnoreCase(bundleRequest.getSort1())){
				studentReports = batchReportProcessService.getStudentReportsForDynamicBundleReport(Arrays.asList(separationId), schoolIds.size() > 0 ? schoolIds: Arrays.asList(bundleRequest.getOrganizationId()), gradeIds, bundleRequest.getAssessmentProgramId(),
						assessmentProgramCode, bundleRequest.getSchoolYear(), bundleRequest.getSort1(),bundleRequest.getSort2(),bundleRequest.getSort3());
			}else if(grade.equalsIgnoreCase(bundleRequest.getSort1())){
				studentReports = batchReportProcessService.getStudentReportsForDynamicBundleReport(subjectIds, schoolIds.size() > 0 ? schoolIds: Arrays.asList(bundleRequest.getOrganizationId()), Arrays.asList(separationId), bundleRequest.getAssessmentProgramId(),
						assessmentProgramCode, bundleRequest.getSchoolYear(), bundleRequest.getSort1(),bundleRequest.getSort2(),bundleRequest.getSort3());
			}else if(school.equalsIgnoreCase(bundleRequest.getSort1())){
				studentReports = batchReportProcessService.getStudentReportsForDynamicBundleReport(subjectIds, Arrays.asList(separationId), gradeIds, bundleRequest.getAssessmentProgramId(),
						assessmentProgramCode, bundleRequest.getSchoolYear(), bundleRequest.getSort1(),bundleRequest.getSort2(),bundleRequest.getSort3());

			}
		}else{
			studentReports = batchReportProcessService.getStudentReportsForDynamicBundleReport(subjectIds, schoolIds.size() > 0 ? schoolIds: Arrays.asList(bundleRequest.getOrganizationId()), gradeIds, bundleRequest.getAssessmentProgramId(),
					assessmentProgramCode, bundleRequest.getSchoolYear(), bundleRequest.getSort1(),bundleRequest.getSort2(),bundleRequest.getSort3());
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
			throw new SkipBatchException("Skipping dynamic pdf report of student reports process for organization - " + bundleRequest.getOrganizationId() + " by user = " + bundleRequest.getCreatedUser());
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
		if("CPASS".equalsIgnoreCase(assessmentProgramCode) || "DLM".equalsIgnoreCase(assessmentProgramCode)){
			String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+"external"+File.separator+assessmentProgramCode+File.separator+studentReport.getSchoolYear()+File.separator+ "SB" + File.separator
					+ studentReport.getStateId() + File.separator + studentReport.getDistrictId()
					+ (getSchoolIds().size()  == 0 ? File.separator + studentReport.getAttendanceSchoolId() : "");
			return orgDir;
		}else{
			String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+studentReport.getSchoolYear()+File.separator+ "SB" + File.separator
					+ studentReport.getStateId() + File.separator + studentReport.getDistrictId()
					+ (getSchoolIds().size()  == 0 ? File.separator + studentReport.getAttendanceSchoolId() : "");
			return orgDir;
		}
	}

	private String getSchoolPdfFileNameOfSchoolReports(
			StudentReport studentReport) {
			
			
		if(bundleRequest.getSeparateFile()){
			if(subject.equalsIgnoreCase(bundleRequest.getSort1())){
				    return (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictDisplayIdentifier()) : SchoolReportBundleUtil.sanitizeValues(studentReport.getAttSchDisplayIdentifier()))
				    + "_" + (getSchoolIds().size() > 0 ? SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictName())) : SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getAttendanceSchoolName())))
				    +"_Kite_"+SchoolReportBundleUtil.sanitizeValues(studentReport.getContentAreaCode())+ "_" +studentReport.getSchoolYear()+ "_"+ schoolReportDateUtil.getDate(studentReport.getStateId())+PDF;
				    
			}else if(grade.equalsIgnoreCase(bundleRequest.getSort1())){
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

	public List<Long> getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(List<Long> subjectIds) {
		this.subjectIds = subjectIds;
	}

	public List<Long> getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(List<Long> gradeIds) {
		this.gradeIds = gradeIds;
	}

}
