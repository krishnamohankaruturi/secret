package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.UploadIncidentFileWriterProcessService;
@Service
public class UploadIncidentFileDefaultWriterProcessServiceImpl implements	BatchUploadWriterService {
	
	final static Log logger = LogFactory.getLog(UploadIncidentFileDefaultWriterProcessServiceImpl.class);
	
	@Autowired
	private UploadIncidentFileWriterProcessService uploadIncidentFileWriterProcessService; 
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - UploadInsedentFileWrite");
	
		for( Object object : objects){
			UploadIncidentFile uploadIncidentFile = (UploadIncidentFile) object;
			uploadIncidentFileWriterProcessService.insertIncidentFileRecords(uploadIncidentFile);
		}
	}
		
	

}
