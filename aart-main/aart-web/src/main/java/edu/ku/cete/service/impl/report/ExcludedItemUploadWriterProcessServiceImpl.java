package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.ExcludedItemsService;

@Service
public class ExcludedItemUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(ExcludedItemUploadWriterProcessServiceImpl.class);

	@Autowired
	private ExcludedItemsService excludedItemsService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - Excluded items");
		for( Object object : objects){
			ExcludedItems excludedItem = (ExcludedItems) object;
			excludedItemsService.insertSelectiveExcludedItems(excludedItem);
		} 
	}

	 

}
