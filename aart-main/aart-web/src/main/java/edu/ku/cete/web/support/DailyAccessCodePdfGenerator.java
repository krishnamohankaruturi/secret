package edu.ku.cete.web.support;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.pdf.PDFGeneratorUtil;
import edu.ku.cete.util.CommonConstants;

@Component
public class DailyAccessCodePdfGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(DailyAccessCodePdfGenerator.class);

	@Value("/templates/xslt/dailyaccesscode.xsl")
	private Resource xslFile;

	public void generatePdf(List<DailyAccessCode> accessCodes, OutputStream out, String serverPath, TimeZone timeZone) throws Exception {
		LOGGER.debug("--> generatePdf");
		Map<Integer, List<DailyAccessCode>> pages = convertDacData(accessCodes);
		
		XStream xstream = new XStream();
		xstream.alias("accessCodePages", pages.getClass());
		xstream.alias("dailyAccessCode", DailyAccessCode.class);
		
		xstream.registerLocalConverter(DailyAccessCode.class, "effectiveDate", new DateConverter("MM/dd/yyyy", new String[] {}));
		xstream.registerLocalConverter(DailyAccessCode.class, "beginDate", new DateConverter("hh:mm:ss a", new String[] {}, timeZone));
		xstream.registerLocalConverter(DailyAccessCode.class, "endDate", new DateConverter("hh:mm:ss a", new String[] {}, timeZone));

		LOGGER.debug(" xml: "+xstream.toXML(pages));
		
		TraxSource source = new TraxSource(pages, xstream);
		LOGGER.debug("calling PDFGeneratorUtil.generatePDF");
		PDFGeneratorUtil.generatePDF(source, xslFile.getFile(), out, serverPath);
		LOGGER.debug("<-- generatePdf");
	}

	private Map<Integer, List<DailyAccessCode>> convertDacData(List<DailyAccessCode> accessCodes) {
		LOGGER.debug("--> convertDacData");
		Assert.notNull(accessCodes, "Access codes list was null.");
		Assert.notEmpty(accessCodes, "Access codes list was empty.");
		Assert.notNull(accessCodes.get(0), "Access codes had null first element.");
		
		String assessmentProgramCode = accessCodes.get(0).getAssessmentProgramCode();
		for (DailyAccessCode accessCode : accessCodes) {
			if (accessCode.getStageCode().equals("Prfrm")) {
				accessCode.setPartNumber(accessCodes.size());
			}
		}
		Collections.sort(accessCodes, new Comparator<DailyAccessCode>() {
			@Override
			public int compare(DailyAccessCode o1, DailyAccessCode o2) {
				int c;
				c = o1.getOperationalTestwindowId().compareTo(o2.getOperationalTestwindowId());
				if (c == 0) {
					c = o1.getContentAreaId().compareTo(o2.getContentAreaId());
				}
				if (c == 0) {
					if (StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
						c = o1.getGradeBandId().compareTo(o2.getGradeBandId());
					} else {
						c = o1.getGradeCourseId().compareTo(o2.getGradeCourseId());
					}
				}
				if (c == 0) {
					c = o1.getPartNumber().compareTo(o2.getPartNumber());
				}
				return c;
			}
		});

		Map<Integer, List<DailyAccessCode>> pages = new HashMap<Integer, List<DailyAccessCode>>();

		Integer pageNumber = 1;
		DailyAccessCode prevAccessCode = null;
		
		if(StringUtils.equalsIgnoreCase(assessmentProgramCode, CommonConstants.ASSESSMENT_PROGRAM_PLTW)){
			for (DailyAccessCode accessCode : accessCodes) {
				if (prevAccessCode == null || (prevAccessCode != null 
							&& prevAccessCode.getContentAreaId().equals(accessCode.getContentAreaId()) 
							&& prevAccessCode.getGradeBandId().equals(accessCode.getGradeBandId()))) {
					if (prevAccessCode == null) {
						pages.put(pageNumber, new ArrayList<DailyAccessCode>());
					}
					pages.get(pageNumber).add(accessCode);
				} else {
					pages.put(++pageNumber, new ArrayList<DailyAccessCode>());
					pages.get(pageNumber).add(accessCode);
				}
				prevAccessCode = accessCode;
			}
		} else {
			for (DailyAccessCode accessCode : accessCodes) {
				if (prevAccessCode == null || (prevAccessCode != null 
							&& prevAccessCode.getContentAreaId().equals(accessCode.getContentAreaId()) 
							&& prevAccessCode.getGradeCourseId().equals(accessCode.getGradeCourseId()))) {
					if (prevAccessCode == null) {
						pages.put(pageNumber, new ArrayList<DailyAccessCode>());
					}
					pages.get(pageNumber).add(accessCode);
				} else {
					pages.put(++pageNumber, new ArrayList<DailyAccessCode>());
					pages.get(pageNumber).add(accessCode);
				}
				prevAccessCode = accessCode;
			}
		}

		LOGGER.debug("number of pages: "+pages.size());
		LOGGER.debug("<-- convertDacData");
		
		return pages;
	}
}
