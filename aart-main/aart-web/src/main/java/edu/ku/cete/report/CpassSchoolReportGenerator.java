package edu.ku.cete.report;



import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.report.ExternalSchoolDetailReportDTO;
import edu.ku.cete.service.AwsS3Service;

/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
@Component
public class CpassSchoolReportGenerator extends ReportGenerator {	
	@Value("/templates/xslt/reports/cpassschoolreport.xsl")
	private Resource schoolReportXslFile;
	
	@Value("/images/reports/cPass_logo_registered.jpg")
	private Resource cpassLogoNewFile;
	
	@Autowired
	private AwsS3Service s3;
	
	private String createOrgDir(ExternalSchoolDetailReportDTO data) throws IOException {
		String orgDir = getRootOutputDir() + File.separator + "reports"+File.separator+"external"+File.separator+ "CPASS" + File.separator
					+data.getSchoolYear()+File.separator+ sanitizeForPath(data.getStateDisplayIdentifier()) + File.separator + sanitizeForPath(data.getDistrictDisplayIdentifier())
				+ File.separator + sanitizeForPath(data.getSchoolDisplayIdentifier())
				+ File.separator+sanitizeForPath(data.getSubject())+File.separator+sanitizeForPath(data.getGradeName())+File.separator+sanitizeForPath(data.getAssessmentCode());
		
		return orgDir;
	}
	public String generateTableFile(ExternalSchoolDetailReportDTO data) throws Exception {
		List<File> gradeChartFiles = new ArrayList<File>();
		File axisLineSubScore = null;
		File axisLinePercentLevel = null;
		try {	
			String outDir = createOrgDir(data);	
			ReportContext reportData = new ReportContext();
			reportData.setLogoPath(cpassLogoNewFile.getFile().toURI().toString());
			reportData.setData(data);
			
			ArrayList<String> standardErrorList =  new ArrayList<String>();
			standardErrorList.add("Test 1");
			standardErrorList.add("Test 2");
			reportData.setMdptPercentLevelCharts(standardErrorList);
			
			XStream xstream = new XStream();
			xstream.alias("reportDetails", ReportContext.class);
			xstream.aliasField("data", ReportContext.class, "data");			
			TraxSource source = new TraxSource(reportData, xstream);	
			String filenameWithoutExtension = sanitizeForPath(data.getStateDisplayIdentifier())+"_" +sanitizeForPath(data.getDistrictDisplayIdentifier())+"_"+ sanitizeForPath(data.getSchoolDisplayIdentifier()) +
            "_"+ sanitizeForPath(data.getSubject())+"_"+ sanitizeForPath(data.getGradeName())+"_"+data.getSchoolYear();
			String filename = filenameWithoutExtension + ".pdf";
			String filePath = outDir + File.separator + filename;
			
            File pdfFile = File.createTempFile(outDir + filenameWithoutExtension, ".pdf");		

			generatePdf(pdfFile, schoolReportXslFile.getFile(), source);
			
			String path = getPathForDB(filePath);
	        s3.synchMultipartUpload(filePath, pdfFile);
	        FileUtils.deleteQuietly(pdfFile);
			return path;
		} finally {
			for(File f: gradeChartFiles) {
				FileUtils.deleteQuietly(f);
			}
			FileUtils.deleteQuietly(axisLineSubScore);
			FileUtils.deleteQuietly(axisLinePercentLevel);
		}		
	}	
}
