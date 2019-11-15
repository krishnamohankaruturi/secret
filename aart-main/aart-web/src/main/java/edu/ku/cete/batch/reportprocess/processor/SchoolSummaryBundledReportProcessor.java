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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.impl.report.SchoolReportBundleUtil;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.util.FileUtil;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 1:59:20 PM
 */
public class SchoolSummaryBundledReportProcessor implements ItemProcessor<Long, Object> {

	private final static Log logger = LogFactory .getLog(SchoolSummaryBundledReportProcessor.class);
	 
	private StepExecution stepExecution;
    private Long assessmentProgramId;
    private String assessmentProgramCode;
    private Long schoolYear;
    private Long batchReportProcessId;
    private String bundledReportType;
    
    @Autowired
	BatchReportProcessService batchReportProcessService;
    
    @Value("${print.test.file.path}")
	private String REPORT_PATH;
    
    @Value("${external.import.reportType.school}")
	private String REPORT_TYPE_SCHOOL;   
    
    @Autowired
    private AwsS3Service s3;
    
    @Override
    public OrganizationReportDetails process(Long organizationId) throws Exception {		
		logger.debug("Inside the SchoolSummary Bundled reports process for organizationid - " + organizationId);
		OrganizationReportDetails orgBundledReport = new OrganizationReportDetails();
		orgBundledReport.setAssessmentProgramId(assessmentProgramId);
		orgBundledReport.setSchoolYear(schoolYear);
		
		List<OrganizationReportDetails> schoolReports = batchReportProcessService.getExternalSchoolReportsForDistrictBundledReport(assessmentProgramId, schoolYear, assessmentProgramCode, organizationId, REPORT_TYPE_SCHOOL);
				
		if(CollectionUtils.isNotEmpty(schoolReports)) {
			orgBundledReport.setOrganizationId(schoolReports.get(0).getDistrictId());
			orgBundledReport.setOrganizationName(schoolReports.get(0).getOrganizationName());
			orgBundledReport.setStateId(schoolReports.get(0).getStateId());
			orgBundledReport.setDistrictId(schoolReports.get(0).getDistrictId());
			orgBundledReport.setDistrictDisplayIdentifier(schoolReports.get(0).getDistrictDisplayIdentifier());
			orgBundledReport.setReportType(bundledReportType);
			
			String generatedFileName = getDistrictPdfFileNameOfStudentSummaryReports(orgBundledReport);
			String generatedFilePath = getDistrictPdfFilePathOfStudentSummaryReports(orgBundledReport);
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
		        for(int i = 0; i < schoolReports.size(); i++){
		        	try {
		        		String path = REPORT_PATH + schoolReports.get(i).getDetailedReportPath();
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
				            /*if(n % 2 != 0) {
				            	copy.addPage(reader.getPageSize(1), reader.getPageRotation(1));
				            }*/
				            copy.freeReader(reader);
		        		} else {
		        			logger.error("PDF "+path+" file not found in S3.");
			        	}
		        	} catch(Exception e) {
		        		logger.error("PDF "+REPORT_PATH + schoolReports.get(i).getDetailedReportPath()+" parse error", e);
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
		        orgBundledReport.setDetailedReportPath(getDbFilePath(fullPath));			
		        orgBundledReport.setSchoolReportPdfSize(dstFile.length());
		        FileUtils.deleteQuietly(dstFile);
 			} finally {
		        // step 5
 				//also close if needed in case of an exception
 				if(document.isOpen()) {
 					document.close();
 				}
 			}
		} else {
			throw new SkipBatchException("Skipping organization - " + organizationId);
		}
		
		logger.debug("Leaving School Summary Bundled report process for organizationid - " + organizationId);
		
    	return orgBundledReport;
	}

    private String getDbFilePath(String destinationFileName) {
		String path = destinationFileName.replaceAll(REPORT_PATH, "");
		if(!path.startsWith(File.separator)) {
			path = File.separator+path;
		}
		return path;
	}    

	private String getDistrictPdfFileNameOfStudentSummaryReports(OrganizationReportDetails orgReport) {
		 return SchoolReportBundleUtil.sanitizeValues(assessmentProgramCode)+ "_" +orgReport.getDistrictDisplayIdentifier() + "_" + 
					SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(orgReport.getOrganizationName()))+".pdf";
	}

	private String getDistrictPdfFilePathOfStudentSummaryReports(OrganizationReportDetails orgReport) throws IOException {	
		String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+"external"+File.separator+assessmentProgramCode+File.separator+orgReport.getSchoolYear()+File.separator+ "SchoolSummaryBundled" + File.separator
				+ orgReport.getStateId() + File.separator + orgReport.getDistrictId();
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
}
