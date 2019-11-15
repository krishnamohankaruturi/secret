package edu.ku.cete.report;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.web.DLMBlueprintCoverageReportDTO;

@Component
public class DLMBlueprintCoverageReportGenerator extends ReportGenerator {
	@Value("/templates/xslt/reports/itiBlueprintCoverage.xsl")
	private Resource xsl;
	
	@Value("/images/icon_met.png")
	private Resource iconMetCriterion;
	
	@Value("/images/icon_partially.png")
	private Resource iconPartiallyMetCriterion;
	
	@Value("/images/icon_completed.png")
	private Resource iconCompletedTestlet;
	
	@Value("/images/icon_plancreated.png")
	private Resource iconNotTested;
	
	public File generateBlueprintCoverage(Organization org, List<DLMBlueprintCoverageReportDTO> dtos, boolean byTeacher) throws Exception {
		ReportContext reportContext = new ReportContext();
		reportContext.setLogoPath(getSmallDLMLogo().getURI().toString());
		reportContext.setData(dtos);
		
		XStream xstream = new XStream();
		xstream.alias("reportContext", ReportContext.class);
		
		// this keeps all objects completely separate, no references to throw off context when using the XSL
		xstream.setMode(XStream.NO_REFERENCES);
		
		TraxSource traxSource = new TraxSource(reportContext, xstream);
		
		Map<String, Object> paramsMap = getParamsMap();
		paramsMap.put("orgName", org.getOrganizationName());
		paramsMap.put("byTeacher", byTeacher);
		
		String fileName = "/tmp/ITI_Blueprint_Coverage_" +
				org.getId() + "_" +
				new Date().getTime() + "_" +
				((int) (Math.random() * 100000)) + ".pdf";
		File pdfFile = new File(fileName);
		generatePdf(pdfFile, xsl.getFile(), traxSource, paramsMap);
		
		return pdfFile;
	}
	
	private Map<String, Object> getParamsMap() throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dateString", new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		map.put("metCriterionImage", iconMetCriterion.getURI().toString());
		map.put("partiallyMetCriterionImage", iconPartiallyMetCriterion.getURI().toString());
		map.put("completedTestletImage", iconCompletedTestlet.getURI().toString());
		map.put("notTestedImage", iconNotTested.getURI().toString());
		return map;
	}
}