package edu.ku.cete.batch.reportprocess.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

public class BatchDistrictPdfReportsProcessor implements ItemProcessor<Long,Object>{
	 private static final Logger LOGGER = LoggerFactory.getLogger(BatchDistrictPdfReportsProcessor.class);
	 
	private StepExecution stepExecution;
    private Long assessmentProgramId;
    private String assessmentProgramCode;
    private Long schoolYear;
    private Long batchReportProcessId;
	
    @Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;

    @Value("${cpass.student.individual.report.type.code}")
	private String dbCPASSStudentReportsImportReportType;		
	
    @Autowired
	BatchReportProcessService batchReportProcessService;
    
    @Value("${print.test.file.path}")
	private String REPORT_PATH;
    
    @Autowired
    private AwsS3Service s3;
    
    final static Log logger = LogFactory.getLog(BatchDistrictPdfReportsProcessor.class);
    
    @Override
    public StudentReport process(Long districtId) throws Exception {		
		logger.info("Inside the District pdf report of student reports process for districtId - " + districtId);
		StudentReport studentReport = new StudentReport();
		studentReport.setAssessmentProgramId(assessmentProgramId);
		studentReport.setSchoolYear(schoolYear);
		studentReport.setDistrictId(districtId);
		List<StudentReport> studentReports = null;
		String reportType="";
		if(assessmentProgramCode!=null && "CPASS".equals(assessmentProgramCode))
			reportType = dbCPASSStudentReportsImportReportType;
		else if(assessmentProgramCode!=null && "DLM".equals(assessmentProgramCode))
			reportType = dbDLMStudentReportsImportReportType;
		
		if("CPASS".equalsIgnoreCase(assessmentProgramCode) || "DLM".equalsIgnoreCase(assessmentProgramCode)){
		   studentReports = batchReportProcessService.getExternalStudentReportsForDistrictReportPdf(assessmentProgramId, schoolYear, assessmentProgramCode,districtId, reportType);
		}else{		
		   studentReports = batchReportProcessService.getStudentReportsForDistrictReportPdf(assessmentProgramId, schoolYear, districtId);
		}
		if(CollectionUtils.isNotEmpty(studentReports)) {
			studentReport.setDistrictName(studentReports.get(0).getDistrictName());
			studentReport.setStateId(studentReports.get(0).getStateId());
			studentReport.setDistrictId(studentReports.get(0).getDistrictId());
			studentReport.setDistrictDisplayIdentifier(studentReports.get(0).getDistrictDisplayIdentifier());
			
			String generatedFileName = getDistrictPdfFileNameOfSchoolReports(studentReport);
			String generatedFilePath = getDistrictPdfFilePathOfSchoolReports(studentReport);
			String fullPath = FileUtil.buildFilePath(generatedFilePath, generatedFileName);
			String[] splitFullPath = fullPath.split("\\.");
			File dstFile = File.createTempFile(splitFullPath[0], ".pdf");
						
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
				            if(n % 2 != 0) {//If odd page then add one empty page
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
			throw new SkipBatchException(" in district - " + districtId);
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
    
	private String getDistrictPdfFilePathOfSchoolReports(StudentReport studentReport) throws IOException {
		if("CPASS".equalsIgnoreCase(assessmentProgramCode) || "DLM".equalsIgnoreCase(assessmentProgramCode)){
			String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+"external"
					+File.separator+assessmentProgramCode+File.separator+studentReport.getSchoolYear()+File.separator+ "SB" + File.separator
					+ studentReport.getStateId() + File.separator + studentReport.getDistrictId();
			return orgDir;

		}else{
			String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+studentReport.getSchoolYear()+File.separator+ "SB" + File.separator
					+ studentReport.getStateId() + File.separator + studentReport.getDistrictId();
			return orgDir;
			
		}
	}

	private String getDistrictPdfFileNameOfSchoolReports(
			StudentReport studentReport) {
		 return SchoolReportBundleUtil.sanitizeValues(assessmentProgramCode)+ "_" +studentReport.getDistrictDisplayIdentifier() + "_" + 
					SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(studentReport.getDistrictName()))+".pdf";
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
}