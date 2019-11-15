package edu.ku.cete.service.impl.report;


import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.service.report.RawToScaleScoresService;

public class RawToScaleScoresUploadWriterProcessServiceImplTest {

	final static Log logger = LogFactory.getLog(RawToScaleScoresUploadWriterProcessServiceImplTest.class);

	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testcase1()
	{
		RawToScaleScores rawToScaleScores = new RawToScaleScores();
		
		rawToScaleScores.setSchoolYear(2016L);
		rawToScaleScores.setAssessmentProgram("AMP");
		rawToScaleScores.setLineNumber("5");
		rawToScaleScores.setGrade("10");
		rawToScaleScores.setGradeId(126L);
		rawToScaleScores.setSubjectId(3L);
		rawToScaleScores.setSubject("ELA");
		rawToScaleScores.setTestId1(5031L);
		rawToScaleScores.setTestId2(5100L);
		rawToScaleScores.setRawScore(new BigDecimal("25"));
		rawToScaleScores.setScaleScore(50L);
		rawToScaleScores.setStandardError(new BigDecimal("2.1"));
		
		testWriterProcess(rawToScaleScores);
	}
	
	public void testWriterProcess(RawToScaleScores rawToScaleScores) {
		//fail("Not yet implemented");
		int count = rawToScaleScoresService.insertSelectiveRawToScaleScores(rawToScaleScores);
		if(count > 1)
			logger.debug("Successful");
		 
	}

}
