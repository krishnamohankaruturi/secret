package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.RawToScaleScoresService;

@Service
public class RawToScaleScoresUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(RawToScaleScoresUploadWriterProcessServiceImpl.class);

	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - RawToScaleScores items");
		for( Object object : objects){
			RawToScaleScores rawToScaleScores = (RawToScaleScores) object;
			rawToScaleScoresService.insertSelectiveRawToScaleScores(rawToScaleScores);
		} 
	}

	 

}
