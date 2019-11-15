package edu.ku.cete.test;

import static org.mockito.Mockito.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.impl.StudentsTestsServiceImpl;
import edu.ku.cete.web.support.DailyAccessCodePdfGenerator;

public class DailyAccessCodePdfTest extends BaseTest {

	@Value("/templates/xslt/dailyaccesscode.xsl")
	private Resource xslFile;

	@Value("${print.test.file.path}")
	private String outputBaseDir;
	
	@Autowired
	private StudentsTestsService stService;
	
	@Autowired
	private DailyAccessCodePdfGenerator dacPdfGenerator;
	
	@Test
	public void testAccessCodeQuery() throws Exception {
		List<Map<Long, Long>> cgList = new ArrayList<Map<Long, Long>>();
		Map<Long, Long> row1 = new HashMap<Long, Long>();
		row1.put(440l,91l);
		cgList.add(row1);
		row1 = new HashMap<Long, Long>();
		row1.put(3l,91l);
		cgList.add(row1);
		
		List<DailyAccessCode> accessCodes = stService.getAccessCodes(12l, new Date(), cgList, 51l,false);
		Assert.assertNull(accessCodes);
	}
	
	@Test
	public void testAccessCodeMockito() throws Exception {
		List<Map<Long, Long>> cgList = new ArrayList<Map<Long, Long>>();
		Map<Long, Long> row1 = new HashMap<Long, Long>();
		row1.put(440l,91l);
		cgList.add(row1);
		row1 = new HashMap<Long, Long>();
		row1.put(3l,91l);
		cgList.add(row1);
		StudentsTestsService serviceMock = mock(StudentsTestsServiceImpl.class);
		List<DailyAccessCode> raccessCodes = new ArrayList<DailyAccessCode>();
		//addMathCode(raccessCodes, 1, 7);
		Date edate = new Date();
		when(serviceMock.getAccessCodes(12l, edate, cgList, 51l,false)).thenReturn(raccessCodes);
		
		List<DailyAccessCode> accessCodes = serviceMock.getAccessCodes(12l, edate, cgList, 51l,false);
		Assert.assertNotNull(accessCodes);
		Assert.assertEquals(0, accessCodes.size());
	}

	@Test
	public void testGeneratePdf() throws Exception {
		List<DailyAccessCode> accessCodes = new ArrayList<DailyAccessCode>();
		addMathCode(accessCodes, 1, 7);
		addMathCode(accessCodes, 3, 7);
		addMathCode(accessCodes, 2, 7);
		addMathCode(accessCodes, 0, 7);
		addMathCode(accessCodes, 4, 7);
		
		addMathCode(accessCodes, 1, 10);
		addMathCode(accessCodes, 4, 10);
		addMathCode(accessCodes, 2, 10);
		addMathCode(accessCodes, 3, 10);
		
		addElaCode(accessCodes, 1, 10);
		addElaCode(accessCodes, 4, 10);
		addElaCode(accessCodes, 2, 10);
		addElaCode(accessCodes, 3, 10);
		
		OutputStream out = null;
		try {
			File pdfFile = new File(outputBaseDir, "DailyAccessCode.pdf");

			out = new FileOutputStream(pdfFile);
			out = new BufferedOutputStream(out);
			dacPdfGenerator.generatePdf(accessCodes, out, outputBaseDir, TimeZone.getTimeZone("America/Chicago"));
			Assert.assertTrue(pdfFile.exists());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	private void addMathCode(List<DailyAccessCode> accessCodes, int partNumber, int grade) {
		DailyAccessCode dac = new DailyAccessCode();
		dac.setContentAreaId(440l);
		dac.setGradeCourseId(new Long(grade));
		dac.setContentAreaName("Math");
		dac.setGradeCode(""+grade);
		dac.setAccessCode("bscdf21" + partNumber);
		dac.setBeginDate(new Date());
		dac.setEndDate(new Date());
		dac.setEffectiveDate(new Date());
		dac.setStageId(new Long(partNumber));
		dac.setPartNumber(partNumber);
		dac.setOperationalTestwindowId(123l);
		if(partNumber ==0) {
			dac.setStageName("Performance");
			dac.setStageCode("Prfrm");
		} else {
			dac.setStageName("Stage "+partNumber);
			dac.setStageCode("stg"+partNumber);
		}
		accessCodes.add(dac);
	}
	
	private void addElaCode(List<DailyAccessCode> accessCodes, int partNumber, int grade) {
		DailyAccessCode dac = new DailyAccessCode();
		dac.setContentAreaId(3l);
		dac.setGradeCourseId(new Long(grade));
		dac.setContentAreaName("English");
		dac.setGradeCode(""+grade);
		dac.setAccessCode("bscdf21" + partNumber);
		dac.setBeginDate(new Date());
		dac.setEndDate(new Date());
		dac.setEffectiveDate(new Date());
		dac.setStageId(new Long(partNumber));
		dac.setPartNumber(partNumber);
		dac.setOperationalTestwindowId(123l);
		if(partNumber ==0) {
			dac.setStageName("Performance");
			dac.setStageCode("Prfrm");
		} else {
			dac.setStageName("Stage "+partNumber);
			dac.setStageCode("stg"+partNumber);
		}
		accessCodes.add(dac);
	}
}
