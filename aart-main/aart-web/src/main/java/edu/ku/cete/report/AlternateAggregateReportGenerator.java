package edu.ku.cete.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.domain.report.AlternateAggregateStudents;
import edu.ku.cete.domain.report.AlternateAggregateSubject;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.FileUtil;

@Component
public class AlternateAggregateReportGenerator extends ReportGenerator{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AlternateAggregateReportGenerator.class);	
	
	@Value("/images/reports/dlm_logo-2017.svg")
	private Resource dlmLogoFile;
	
	@Value("/templates/xslt/reports/alternateClassroomReport.xsl")
	private Resource alternateClassRoomXslFile;
	
	@Value("/templates/xslt/reports/alternateSchoolReport.xsl")
	private Resource alternateSchoolXslFile;
	
	@Autowired
	private AwsS3Service s3;
	
	private final static String PDF = ".pdf";
	private final static String CSV = ".csv";
	
	public AlternateAggregateReport generateAlternateAggregateReportFile(AlternateAggregateReport data) throws Exception {
		
		String outDir = createAlternateAggregateReportDirName(data);							
		data.setDlmLogo(dlmLogoFile.getFile().toURI().toString());				
		XStream xstream = new XStream();
		xstream.alias("alternateAggregateReport", AlternateAggregateReport.class);
		TraxSource source = new TraxSource(data, xstream);					
		File pdfFile = null;
		String filenameWithExtension = null;
		if(data.getKiteEducatorIdentifier() != null) {
			String filename = outDir + File.separator + sanitizeForPath(data.getStateCode()+"-"+data.getDistrictName()) +"("+sanitizeForPath(data.getResidenceDistrictIdentifier())+")-"+sanitizeForPath(data.getSchoolName()+"-"+data.getAlternateAggregateStudents().get(0).getEducatorFirstName()+"_"+data.getAlternateAggregateStudents().get(0).getEducatorLastName());
			filenameWithExtension = filename + PDF;
			pdfFile = File.createTempFile(filename, PDF);
			generatePdf(pdfFile, alternateClassRoomXslFile.getFile(), source);
		} else {
			String filename = outDir + File.separator + sanitizeForPath(data.getStateCode()+"-"+data.getDistrictName())+"("+sanitizeForPath(data.getResidenceDistrictIdentifier())+")-"+sanitizeForPath(data.getSchoolName());
			filenameWithExtension = filename + PDF;
			pdfFile = File.createTempFile(filename, PDF);
			generatePdf(pdfFile, alternateSchoolXslFile.getFile(), source);
		}
		
		data.setPdfFileSize(pdfFile.length());		
		data.setFilePath(getPathForDB(filenameWithExtension));	
		s3.synchMultipartUpload(filenameWithExtension, pdfFile);
		FileUtils.deleteQuietly(pdfFile);
		return data;	
	}	
	private String createAlternateAggregateReportDirName(AlternateAggregateReport data) throws IOException {
		String dir = getRootOutputDir();
		if(data.getKiteEducatorIdentifier()!= null) {
			dir += File.separator+"reports"+File.separator+data.getReportYear()+File.separator+ "DCRS" + File.separator
					+ data.getStateId() + File.separator + data.getDistrictId()+ File.separator + data.getSchoolId()+ File.separator + data.getKiteEducatorIdentifier();
		}else{
			dir += File.separator+"reports"+File.separator+data.getReportYear()+File.separator+ "DSCS" + File.separator
					+ data.getStateId() + File.separator + data.getDistrictId()+ File.separator + data.getSchoolId();
			
		}
		return dir;
	}
	
	public String generateAlternateAggregateExtract(AlternateAggregateReport data) throws IOException{

		List<String[]> lines = new ArrayList<String[]>();
		List<String> columnHeadersList = new ArrayList<String>();

		columnHeadersList.add("Student Name");
		if(data.getKiteEducatorIdentifier() == null) {
			columnHeadersList.add("Teacher");
		}
		
		columnHeadersList.add("Grade");
		columnHeadersList.add("Subject");
		columnHeadersList.add("EEs Tested");
		columnHeadersList.add("EEs at or above Target");
		columnHeadersList.add("Skills Mastered");
		columnHeadersList.add("Achievement Level");

		String[] columnHeaders = columnHeadersList.toArray(new String[columnHeadersList.size()]);
		lines.add(columnHeaders);
		if(data.getAlternateAggregateStudents()!=null){		
				for(AlternateAggregateStudents alternateAggregateStudent : data.getAlternateAggregateStudents()){					
					for(AlternateAggregateSubject alternateAggregateSubject : alternateAggregateStudent.getAlternateAggregateSubject()){
						List<String> columnsList = new ArrayList<String>();
						columnsList.add(wrapForCSV(alternateAggregateStudent.getLegalLastName()+", "+alternateAggregateStudent.getLegalFirstName()));
						if(data.getKiteEducatorIdentifier() == null) {
							columnsList.add(wrapForCSV(alternateAggregateStudent.getEducatorLastName()));
						}						
						columnsList.add(wrapForCSV(alternateAggregateStudent.getCurrentGradelevel()));
						columnsList.add(wrapForCSV(StringUtils.isEmpty(alternateAggregateSubject.getSubject())?"--":alternateAggregateSubject.getSubject()));
						if(alternateAggregateSubject.getAchievementLevel().equals("-")){
							columnsList.add(wrapForCSV("--"));
							columnsList.add(wrapForCSV("--"));
							columnsList.add(wrapForCSV("--"));
							columnsList.add(wrapForCSV("--"));
						}else {
							columnsList.add(wrapForCSV(alternateAggregateSubject.getEESTested()==CommonConstants.GRF_AGGREGATE_REPORT_SCORE_ZERO?CommonConstants.GRF_AGGREGATE_REPORT_NO_SCORE:String.valueOf(alternateAggregateSubject.getEESTested())));
							columnsList.add(wrapForCSV(alternateAggregateSubject.getAboveTarget()==CommonConstants.GRF_AGGREGATE_REPORT_SCORE_ZERO?CommonConstants.GRF_AGGREGATE_REPORT_NO_SCORE:String.valueOf(alternateAggregateSubject.getAboveTarget())));
							columnsList.add(wrapForCSV(alternateAggregateSubject.getSkillsMastered()==CommonConstants.GRF_AGGREGATE_REPORT_SCORE_ZERO?CommonConstants.GRF_AGGREGATE_REPORT_NO_SCORE:String.valueOf(alternateAggregateSubject.getSkillsMastered())));
							columnsList.add(wrapForCSV(String.valueOf(alternateAggregateSubject.getAchievementLevel())));
						}											
						lines.add(columnsList.toArray(new String[columnsList.size()]));
				}
			}	
		}	
				
		String filePath = writeCSV(lines, data);
		lines.clear();
		return filePath;		
	}
	
	private String writeCSV(List<String[]> lines, AlternateAggregateReport data) throws IOException {
	       
		CSVWriter csvWriter = null;         	
    	String folderPath="";    	
    	if(data.getKiteEducatorIdentifier() != null) {    		
    		folderPath="reports"+File.separator+data.getReportYear()+File.separator+ "DCRS" + File.separator
    		+ data.getStateId() + File.separator + data.getDistrictId()+ File.separator + data.getSchoolId()+ File.separator + data.getKiteEducatorIdentifier();
    	}else{
    		folderPath="reports"+File.separator+data.getReportYear()+File.separator+ "DSCS" + File.separator
					+ data.getStateId() + File.separator + data.getDistrictId()+ File.separator + data.getSchoolId();
    	}
    	    	
    	File csvFile = null;
    	String fileName = getFileName(data);
    	String filePath= FileUtil.buildFilePath(getRootOutputDir()+File.separator+folderPath,fileName); 
        try {      	
        	String[] splitFilePath = filePath.split("\\.");
        	csvFile = File.createTempFile(splitFilePath[0], CSV);    	    	
    	  	csvWriter = new CSVWriter(new FileWriter(csvFile, false), ',');
            csvWriter.writeAll(lines);        	
        } catch (IOException ex) {
   	 		LOGGER.error("IOException Occured:", ex);
   	 		throw ex;
        } finally {
        	if(csvWriter != null) {
        		csvWriter.close();
        		csvWriter.flush();
        	}
        }
        String dbPath = getPathForDB(filePath);
        s3.synchMultipartUpload(filePath, csvFile);
        FileUtils.deleteQuietly(csvFile);
        return dbPath;
	}
	
	private String wrapForCSV(String s) {
		/*if (s != null && !s.isEmpty() && s.indexOf(',') > -1) {
			return "\"" + s + "\"";
		}*/
		return s;
	}
	
	private String getFileName(AlternateAggregateReport data) {		
		String fileName = "";
		if(data.getKiteEducatorIdentifier() != null) {
			fileName =  sanitizeForPath(data.getStateCode()+"-"+data.getDistrictName())+"("+sanitizeForPath(data.getResidenceDistrictIdentifier())+")-"+sanitizeForPath(data.getSchoolName()+"-"+data.getAlternateAggregateStudents().get(0).getEducatorFirstName()+"_"+data.getAlternateAggregateStudents().get(0).getEducatorLastName()) + CSV;
		}else{
			fileName =  sanitizeForPath(data.getStateCode()+"-"+data.getDistrictName())+"("+sanitizeForPath(data.getResidenceDistrictIdentifier())+")-"+sanitizeForPath(data.getSchoolName()) + CSV;			
		}		
        return fileName;
	}
}
