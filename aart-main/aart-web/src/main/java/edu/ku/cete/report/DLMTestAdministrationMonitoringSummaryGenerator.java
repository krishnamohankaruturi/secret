package edu.ku.cete.report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.transform.TransformerException;

import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.FOPException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.web.DLMTestAdminMonitoringSummaryDTO;

@Component
public class DLMTestAdministrationMonitoringSummaryGenerator extends ReportGenerator {
	@Value("/templates/xslt/reports/dlmTestAdminMonitoringSummary.xsl")
	private Resource monitoringSummaryXsl;
	
	@SuppressWarnings("unchecked")
	public File generateMonitoringSummary(String summaryLevel, HashMap<String, Object> resultMap) throws Exception {
		ReportContext reportContext = new ReportContext();
		reportContext.setLogoPath(getSmallDLMLogo().getURI().toString());
		reportContext.setData(resultMap.get("resultData"));
		reportContext.setSummaryLevel(summaryLevel);
		reportContext.setIsIEState((Boolean) resultMap.get("isIEState"));
		reportContext.setTestingCycleName1((String) resultMap.get("testingCycleName1"));
		reportContext.setTestingCycleName2((String) resultMap.get("testingCycleName2"));
		
		XStream xstream = new XStream();
		xstream.alias("reportContext", ReportContext.class);
		xstream.aliasField("data", ReportContext.class, "data");
		xstream.aliasField("summaryLevel", ReportContext.class, "summaryLevel");
		xstream.aliasField("dataSize", ReportContext.class, "dataSize");
		xstream.aliasField("isIEState", ReportContext.class, "isIEState");
		xstream.aliasField("testingCycleName1", ReportContext.class, "testingCycleName1");
		xstream.aliasField("testingCycleName2", ReportContext.class, "testingCycleName2");

		TraxSource traxSource = new TraxSource(reportContext, xstream);
		
		File pdfFile = new File("/tmp/DLM Test Administration Monitoring Summary_" + summaryLevel + "_" +
				new Date().getTime() + "_" + ((int) (Math.random() * 1000000)) + ".pdf");
		
		generatePdf(pdfFile, monitoringSummaryXsl.getFile(), traxSource);
		
		return pdfFile;
	}
}