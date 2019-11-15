package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.SubScoresMissingStages;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.SubScoresMissingStagesService;

@Service
public class SubScoreDefaultStageIdsWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(SubScoreDefaultStageIdsWriterProcessServiceImpl.class);
	
	@Autowired
	private SubScoresMissingStagesService subScoresMissingStagesService; 
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - SubScoreMissingStages");
		for( Object object : objects){
			SubScoresMissingStages subScoreMissingStages = (SubScoresMissingStages) object;
			subScoresMissingStagesService.insertSelectiveSubScoreMissingStages(subScoreMissingStages);
		}
	}

}
