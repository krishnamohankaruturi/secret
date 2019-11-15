package edu.ku.cete.report;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.pdf.PDFGeneratorUtil;
import edu.ku.cete.report.iap.IAPReportContext;
import edu.ku.cete.web.IAPContentFramework;

@Component
public class IAPReportGenerator {
	
	@Value("/templates/xslt/reports/IAP/student-ee-view.xsl")
	private Resource xslFile;
	
	public File generateReport(IAPReportContext context, String serverPath) throws Exception {
		File file = File.createTempFile("iap_print", ".pdf");
		XStream xstream = new XStream();
		xstream.alias("reportDetails", IAPReportContext.class);
		xstream.aliasField("criteria", List.class, "criteria");
		xstream.aliasField("allLinkageLevels", List.class, "allLinkageLevels");
		
		TraxSource source = new TraxSource(context, xstream);
		PDFGeneratorUtil.generatePDF(source, xslFile.getFile(), new FileOutputStream(file), serverPath);
		return file;
	}
}
