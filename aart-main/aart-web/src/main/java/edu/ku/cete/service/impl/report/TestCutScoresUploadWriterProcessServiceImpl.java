package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.TestCutScoresService;

@Service
public class TestCutScoresUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(TestCutScoresUploadWriterProcessServiceImpl.class);

	@Autowired
	private TestCutScoresService testCutScoresService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - Excluded items");
		for( Object object : objects){
			TestCutScores testCutScore = (TestCutScores) object;
			testCutScoresService.insertSelectiveTestCutScores(testCutScore);
		} 
	}

	 

}
