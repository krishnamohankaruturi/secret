package edu.ku.cete.batch.reportprocess.processor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ghost4j.document.DocumentException;
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
import edu.ku.cete.util.PdfToPostScript;
import edu.ku.cete.util.SchoolReportDateUtil;

public class BatchSchoolCompressedFileReportProcess implements ItemProcessor<Long,Object>{

	private StepExecution stepExecution;
    private Long assessmentProgramId;
    private String assessmentProgramCode;
    private Long schoolYear;
    private Long batchReportProcessId;
	private String createPSFile;
    
    @Autowired
    PdfToPostScript pdfToPostScript;
    
    @Autowired
	BatchReportProcessService batchReportProcessService;
    
    @Value("${print.test.file.path}")
	private String REPORT_PATH;
    
	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
		
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;

	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
    
    @Autowired
	private SchoolReportDateUtil schoolReportDateUtil;
    
    @Autowired
    private AwsS3Service s3;
	
    final static Log logger = LogFactory.getLog(BatchSchoolCompressedFileReportProcess.class);
    
	@Override
	public OrganizationReportDetails process(Long organizationId) throws Exception {
		logger.info("Inside the School compressed file report process for organizationid - " + organizationId);
		OrganizationReportDetails organizationDetails = new OrganizationReportDetails();
		organizationDetails.setAssessmentProgramId(assessmentProgramId);
		organizationDetails.setSchoolYear(schoolYear);
		organizationDetails.setBatchReportProcessId(batchReportProcessId);			
		organizationDetails.setOrganizationId(organizationId);
		
		String reportType="";
		if(assessmentProgramCode!=null && "CPASS".equals(assessmentProgramCode))
			reportType = cpassStudentBundledReportTypeCode;
		else if(assessmentProgramCode!=null && "DLM".equals(assessmentProgramCode))
			reportType = alternateStudentBundledReportTypeCode;
		else if(assessmentProgramCode!=null && "KAP".equals(assessmentProgramCode))
			reportType = generalStudentBundledReportTypeCode;
				
		List<OrganizationReportDetails> schoolReportsPdfsFromOrgreportDetails = batchReportProcessService.getOrganizationReportDetailsForZip(assessmentProgramId, organizationId, schoolYear, reportType);
		if(CollectionUtils.isNotEmpty(schoolReportsPdfsFromOrgreportDetails)) {
			organizationDetails.setOrganizationName(schoolReportsPdfsFromOrgreportDetails.get(0).getOrganizationName());
			organizationDetails.setStateId(schoolReportsPdfsFromOrgreportDetails.get(0).getStateId());
			organizationDetails.setDistrictId(schoolReportsPdfsFromOrgreportDetails.get(0).getDistrictId());
			organizationDetails.setDistrictDisplayIdentifier(schoolReportsPdfsFromOrgreportDetails.get(0).getDistrictDisplayIdentifier());
			organizationDetails.setAttSchDisplayIdentifier(schoolReportsPdfsFromOrgreportDetails.get(0).getAttSchDisplayIdentifier());
			List<String> filePaths = new ArrayList<String>();
			if(assessmentProgramCode.equalsIgnoreCase("KAP")) {
				for(OrganizationReportDetails orgReportDetails : schoolReportsPdfsFromOrgreportDetails) {
					String filePath = FileUtil.buildFilePath(REPORT_PATH, orgReportDetails.getDetailedReportPath());
					filePaths.add(filePath);
				}
				organizationDetails = generateZipAndSetSizeAndDetailPath(organizationDetails, filePaths);
			} else if(assessmentProgramCode.equalsIgnoreCase("AMP")) {
				Document document = new Document();
				String dstFilePath = null;
				try {
					dstFilePath = getTempPdfFileNameOfSchoolReports(organizationDetails);
	 				String[] splitFileName = dstFilePath.split("\\.");
	 				File tempFile = File.createTempFile(splitFileName[0], ".pdf");
	 				// step 2
			        PdfCopy copy = new PdfCopy(document, new FileOutputStream(tempFile));
			        // step 3
			        document.open();
			        // step 4
			        PdfReader reader = null;
			        int n;
			        // loop over the documents you want to concatenate
			        for(int i = 0; i < schoolReportsPdfsFromOrgreportDetails.size(); i++){
			        	try {
			        		String path = REPORT_PATH + schoolReportsPdfsFromOrgreportDetails.get(i).getDetailedReportPath();
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
					            if(n == 1) {
					            	copy.addPage(reader.getPageSize(1), reader.getPageRotation(1));
					            }
					            copy.freeReader(reader);
			        		} else {
			        			logger.error("PDF "+path+" file not found in S3.");
				        	}
			        	} catch(Exception e) {
			        		logger.error("PDF "+REPORT_PATH + schoolReportsPdfsFromOrgreportDetails.get(i).getDetailedReportPath()+" parse error", e);
			        	} finally {
			        		if(reader != null) {
			        			reader.close();
			        		}
			        	}
			        }
        		if(copy != null) {
        			copy.close();
        		}
    			organizationDetails.setDetailedReportPath(getDbFilePath(dstFilePath));
    			organizationDetails.setSchoolReportZipSize(tempFile.length());
    			
		        //close before sending to s3
				if(document.isOpen()) {
					document.close();
				}
    			
        		s3.synchMultipartUpload(dstFilePath, tempFile);
        		//do not delete tempFile until we see if the PS file needs to be created.
        		if(createPSFile.equalsIgnoreCase("true")){
        			organizationDetails = generatePSAndSetSizeAndDetailPath(organizationDetails, tempFile);
        		}
    			FileUtils.deleteQuietly(tempFile);
			} finally {
			        // step 5
					//also close if needed in case of an exception
	 				if(document.isOpen()) {
	 					document.close();
	 				}
	 			}
		}else {
			if(!"CPASS".equalsIgnoreCase(assessmentProgramCode) && !"DLM".equalsIgnoreCase(assessmentProgramCode)){
			   throw new SkipBatchException("Skipping attenanceschool - " + organizationId);
			}
		}
		}
		return organizationDetails;
	}
	private OrganizationReportDetails generateZipAndSetSizeAndDetailPath(OrganizationReportDetails organizationDetails, List<String> filePaths) throws IOException {
		String generatedPathAndFileName = getSchoolZipFileNameOfSchoolReports(organizationDetails);
		String[] splitFileName = generatedPathAndFileName.split("\\.");
		File tempFile = File.createTempFile(splitFileName[0], "."+splitFileName[1]);
		byte[] buffer = new byte[1024];
		FileOutputStream fos = new FileOutputStream(tempFile);
		ZipOutputStream zos = new ZipOutputStream(fos);
		for(String detailReportPath : filePaths) {
			InputStream is = s3.getObject(detailReportPath).getObjectContent();
			zos.putNextEntry(new ZipEntry(StringUtils.substringAfterLast(detailReportPath, File.separator)));
			int length;
			while((length = is.read(buffer)) > 0)  {
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
			is.close();
		}
		zos.close();
		organizationDetails.setDetailedReportPath(getDbFilePath(generatedPathAndFileName));
		organizationDetails.setSchoolReportZipSize(tempFile.length());
		s3.synchMultipartUpload(generatedPathAndFileName, tempFile);
		FileUtils.deleteQuietly(tempFile);
		return organizationDetails;
	}
	
	private OrganizationReportDetails generatePSAndSetSizeAndDetailPath(OrganizationReportDetails organizationDetails, File pdfFile) throws DocumentException, Exception {
		String generatedPathAndFileName = getSchoolPSFileNameOfSchoolReports(organizationDetails);
		//covertPdfToPS will create/delete the file and post to S3
		boolean psSuccess = pdfToPostScript.convertPdfToPS(pdfFile, generatedPathAndFileName);
		if(psSuccess) {
			organizationDetails.setSummaryReportPath(getDbFilePath(generatedPathAndFileName));
		}
		return organizationDetails;
	}

	private String getDbFilePath(String generatedPathAndFileName) {		
		String path = generatedPathAndFileName.replaceAll(REPORT_PATH, "");
		if(!path.startsWith(File.separator)) {
			path = File.separator+path;
		}
		return path;
	}

	private String getSchoolZipFileNameOfSchoolReports(OrganizationReportDetails organizationDetails) throws IOException {		
		String fileName = "Kite_" + schoolYear + "_" + SchoolReportBundleUtil.sanitizeValues(organizationDetails.getAttSchDisplayIdentifier()) + "_" + 
				SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(organizationDetails.getOrganizationName())) + "_" + schoolReportDateUtil.getDate(organizationDetails.getStateId()) +".zip";
		String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+organizationDetails.getSchoolYear()+File.separator+ "SB" + File.separator
				+ organizationDetails.getStateId() + File.separator + organizationDetails.getDistrictId()
				+ File.separator + organizationDetails.getOrganizationId();
		return orgDir + File.separator + fileName;
	}
	
	private String getSchoolPSFileNameOfSchoolReports(OrganizationReportDetails organizationDetails) throws IOException {		
		String fileName = "Kite_" + schoolYear + "_" + SchoolReportBundleUtil.sanitizeValues(organizationDetails.getDistrictDisplayIdentifier()) + "_"  + SchoolReportBundleUtil.sanitizeValues(organizationDetails.getAttSchDisplayIdentifier()) + "_" + 
				SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(organizationDetails.getOrganizationName())) + "_" + schoolReportDateUtil.getDate(organizationDetails.getStateId()) +".ps";
		String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+organizationDetails.getSchoolYear()+File.separator+ "SB" + File.separator
				+ organizationDetails.getStateId() + File.separator + organizationDetails.getDistrictId()
				+ File.separator + organizationDetails.getOrganizationId();
		return orgDir + File.separator + fileName;
	}
	
	private String getTempPdfFileNameOfSchoolReports(OrganizationReportDetails organizationDetails) throws IOException {		
		String fileName = "Kite_" + schoolYear + "_" + SchoolReportBundleUtil.sanitizeValues(organizationDetails.getDistrictDisplayIdentifier()) + "_"  + SchoolReportBundleUtil.sanitizeValues(organizationDetails.getAttSchDisplayIdentifier()) + "_" + 
				SchoolReportBundleUtil.getShortName(SchoolReportBundleUtil.sanitizeValues(organizationDetails.getOrganizationName())) + "_" + schoolReportDateUtil.getDate(organizationDetails.getStateId()) +".pdf";
		String orgDir = REPORT_PATH + File.separator + "reports"+File.separator+organizationDetails.getSchoolYear()+File.separator+ "SB" + File.separator
				+ organizationDetails.getStateId() + File.separator + organizationDetails.getDistrictId()
				+ File.separator + organizationDetails.getOrganizationId();
		return orgDir + File.separator + fileName;
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
	public String getCreatePSFile() {
		return createPSFile;
	}
	public void setCreatePSFile(String createPSFile) {
		this.createPSFile = createPSFile;
	}
	
	
	
}
