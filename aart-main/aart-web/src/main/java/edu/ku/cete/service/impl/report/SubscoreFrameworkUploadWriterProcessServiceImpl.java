package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.SubscoreFramework;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.SubscoreFrameworkService;;

@Service
public class SubscoreFrameworkUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(SubscoreFrameworkUploadWriterProcessServiceImpl.class);

	@Autowired
	private SubscoreFrameworkService subscoreFrameworkService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - Level Description");
		for( Object object : objects){
			SubscoreFramework subscoreFramework = (SubscoreFramework) object;
			subscoreFrameworkService.insertSelectiveSubscoreFramework(subscoreFramework);
		} 
	}

	 

}
