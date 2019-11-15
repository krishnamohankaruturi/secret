package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;

@Service
public class SubscoreRawToScaleScoresUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(SubscoreRawToScaleScoresUploadWriterProcessServiceImpl.class);

	@Autowired
	private SubscoreRawToScaleScoresService subscorerawToScaleScoresService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - RawToScaleScores items");
		for( Object object : objects){
			SubscoreRawToScaleScores subscorerawToScaleScores = (SubscoreRawToScaleScores) object;
			subscorerawToScaleScoresService.insertSelectiveRawToScaleScores(subscorerawToScaleScores);
		} 
	}

	 

}
