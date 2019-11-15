package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.SubscoresDescription;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.SubscoresDescriptionService;

@Service
public class SubscoresDescriptionUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(SubscoresDescriptionUploadWriterProcessServiceImpl.class);

	@Autowired
	private SubscoresDescriptionService subscoresDescriptionService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - Subscores Description items");
		for( Object object : objects){
			SubscoresDescription subscoresDescription = (SubscoresDescription) object;
			subscoresDescriptionService.insertSelectiveSubscoresDescription(subscoresDescription);
		} 
	}

	 

}
