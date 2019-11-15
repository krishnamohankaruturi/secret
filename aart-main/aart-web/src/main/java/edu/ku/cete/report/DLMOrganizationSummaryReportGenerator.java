package edu.ku.cete.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.report.DLMOrganizationSummaryGrade;
import edu.ku.cete.domain.report.DLMOrganizationSummaryReport;
import edu.ku.cete.domain.report.DLMOrganizationSummarySubject;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.util.FileUtil;

@Component
public class DLMOrganizationSummaryReportGenerator  extends ReportGenerator {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DLMOrganizationSummaryReportGenerator.class);
	
	
	@Value("/images/reports/dlm_logo-2017.png")
	private Resource dlmLogoFile;
	
	@Value("/templates/xslt/reports/dlmStateSummary.xsl")
	private Resource dlmStateSummaryXslFile;
	
	@Value("/templates/xslt/reports/dlmDistrictSummary.xsl")
	private Resource dlmDistrictSummaryXslFile;
	
	@Autowired
	private AwsS3Service s3;
	
	public DLMOrganizationSummaryReport generateDLMOrganizationSummaryReportFile(DLMOrganizationSummaryReport data) throws Exception {
			
		String outDir = createDLMOrganizationSummaryDir(data);							
		data.setDlmLogo(dlmLogoFile.getFile().toURI().toString());				
		XStream xstream = new XStream();
		xstream.alias("dlmSummaryReport", DLMOrganizationSummaryReport.class);
		TraxSource source = new TraxSource(data, xstream);					
		File pdfFile = null;
		File foFile = null;
		String tempFilePath = "";
		if(data.getOrgGrfCalculation().getDistrictId() != null) {
			tempFilePath = FileUtil.buildFilePath(outDir, "DLM_DISTRICT_SUMMARY_" + sanitizeForPath(data.getOrgGrfCalculation().getDistrictName()) + ".pdf");
			foFile = dlmDistrictSummaryXslFile.getFile();
		} else {
			tempFilePath = FileUtil.buildFilePath(outDir, "DLM_STATE_SUMMARY_" + sanitizeForPath(data.getOrgGrfCalculation().getStateName()) + ".pdf");
			foFile = dlmStateSummaryXslFile.getFile();
		}
		String[] splitTempFilePath = tempFilePath.split("\\.");
		pdfFile = File.createTempFile(splitTempFilePath[0], ".pdf");
		generatePdf(pdfFile, foFile, source);
		data.setPdfFileSize(pdfFile.length());
		s3.synchMultipartUpload(tempFilePath, pdfFile);
		data.setFilePath(getPathForDB(tempFilePath));		
		FileUtils.deleteQuietly(pdfFile);
		return data;	
	}	

	private String createDLMOrganizationSummaryDir(DLMOrganizationSummaryReport data) throws IOException {
		String dir = null;
		if(data.getOrgGrfCalculation().getDistrictId() != null) {
			dir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+data.getOrgGrfCalculation().getReportYear()+File.separator+ "DDTS" + File.separator
					+ data.getOrgGrfCalculation().getStateId() + File.separator + data.getOrgGrfCalculation().getDistrictId());
		}else{
			dir = FileUtil.buildFilePath(getRootOutputDir(), "reports"+File.separator+data.getOrgGrfCalculation().getReportYear()+File.separator+ "DSTS" + File.separator
					+ data.getOrgGrfCalculation().getStateId());
			
		}
		return dir;
	}
	
	public String generateDLMOrganizationGrfExtract(DLMOrganizationSummaryReport data) throws IOException{

		List<String[]> lines = new ArrayList<String[]>();
		
		List<String> columnHeadersList = new ArrayList<String>();
		columnHeadersList.add("School_Year");
		columnHeadersList.add("State");
		if (data.getOrgGrfCalculation().getDistrictId() != null) {
			columnHeadersList.add("District_Code");
			columnHeadersList.add("District");
		}
		columnHeadersList.add("Current_Grade_Level");
		columnHeadersList.add("Subject");
		columnHeadersList.add("Number_Students_Tested");
		columnHeadersList.add("Number_Emerging");
		columnHeadersList.add("Number_Approaching_Target");
		columnHeadersList.add("Number_At_Target");
		columnHeadersList.add("Number_Advanced");
		columnHeadersList.add("Percentage_At_Target_Advanced");		
		String[] columnHeaders = columnHeadersList.toArray(new String[columnHeadersList.size()]);
		
		lines.add(columnHeaders);
		if(data.getOrgGrfCalculation()!=null){		
				for(DLMOrganizationSummaryGrade orgSummaryGrade : data.getOrgGrfCalculation().getOrgSummaryGradeLists()){					
					for(DLMOrganizationSummarySubject orgSummarySubject : orgSummaryGrade.getOrgSummarySubjectLists()){
						List<String> columnsList = new ArrayList<String>();
						columnsList.add(wrapForCSV(String.valueOf(data.getOrgGrfCalculation().getReportYear())));
						columnsList.add(wrapForCSV(data.getOrgGrfCalculation().getStateName()));
						if(data.getOrgGrfCalculation().getDistrictId() != null) {
						columnsList.add(wrapForCSV(data.getOrgGrfCalculation().getDistrictCode()));
						columnsList.add(wrapForCSV(data.getOrgGrfCalculation().getDistrictName()));
						}
						columnsList.add(wrapForCSV(orgSummaryGrade.getGradeLevel()));							
						columnsList.add(wrapForCSV(orgSummarySubject.getSubjectName()));
						columnsList.add(wrapForCSV(String.valueOf(orgSummarySubject.getNoOfStudentsTested())));
						columnsList.add(wrapForCSV(String.valueOf(orgSummarySubject.getNumberOfEmerging())));
						columnsList.add(wrapForCSV(String.valueOf(orgSummarySubject.getNumberOfApproachingTarget())));
						columnsList.add(wrapForCSV(String.valueOf(orgSummarySubject.getNumberOfAtTarget())));
						columnsList.add(wrapForCSV(String.valueOf(orgSummarySubject.getNumberOfAdvanced())));
						columnsList.add(wrapForCSV(String.valueOf(orgSummarySubject.getPercentageAtTargetAdvanced())));
						lines.add(columnsList.toArray(new String[columnsList.size()]));
				}
			}	
		}	
				
		String filePath = writeCSV(lines, data);
		lines.clear();
		return filePath;		
	}
	
	private String writeCSV(List<String[]> lines, DLMOrganizationSummaryReport data) throws IOException {
       
		CSVWriter csvWriter = null;          	
    	String folderPath="";    	
    	if(data.getOrgGrfCalculation().getDistrictId() != null) {
    		folderPath="reports"+File.separator+data.getOrgGrfCalculation().getReportYear()+File.separator+ "DDTS" + File.separator
    		+ data.getOrgGrfCalculation().getStateId() + File.separator + data.getOrgGrfCalculation().getDistrictId();
    	}
    	if(data.getOrgGrfCalculation().getDistrictId() == null) {
    		folderPath="reports"+File.separator+data.getOrgGrfCalculation().getReportYear()+File.separator+ "DSTS" + File.separator
    		+ data.getOrgGrfCalculation().getStateId();
    	}
    	File csvFile = null;
    	String fileName = getFileName(data);
    	String  filePath = null;
        try {      	
        	filePath= FileUtil.buildFilePath(getRootOutputDir()+File.separator+folderPath,fileName); 
        	String[] splitFilePath = filePath.split("\\."); 
        	csvFile = File.createTempFile(splitFilePath[0], ".csv");
    	  	csvWriter = new CSVWriter(new FileWriter(csvFile, false), ',');
            csvWriter.writeAll(lines);
            csvWriter.flush();
            s3.synchMultipartUpload(filePath, csvFile);
            FileUtils.deleteQuietly(csvFile);
        } catch (IOException ex) {
   	 		LOGGER.error("IOException Occured:", ex);
   	 		throw ex;
        } finally {
        	if(csvWriter != null) {
        		csvWriter.close();
        		csvWriter.flush();
        	}
        }
        return getPathForDB(filePath);
	}
	
	private String wrapForCSV(String s) {
		if (s != null && !s.isEmpty() && s.indexOf(',') > -1) {
			return "\"" + s + "\"";
		}
		return s;
	}
	
	private String getFileName(DLMOrganizationSummaryReport data) {		
		String fileName = "";
		if(data.getOrgGrfCalculation().getDistrictId() != null) {
			fileName =   "DLM_DISTRICT_SUMMARY_" + sanitizeForPath(data.getOrgGrfCalculation().getDistrictName()) + ".csv";
		}else{
			fileName =   "DLM_STATE_SUMMARY_" + sanitizeForPath(data.getOrgGrfCalculation().getStateName()) + ".csv";			
		}		
        return fileName;
	}	
	
	
}
