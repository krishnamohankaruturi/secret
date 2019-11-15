package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.UploadScCodeFileWriterProcessService;
@Service
public class UploadScCodeFileDefaultWriterProcessServiceImpl implements
		BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(UploadScCodeFileDefaultWriterProcessServiceImpl.class);
	
	@Autowired
	private UploadScCodeFileWriterProcessService uploadScCodeFileWriterProcessService; 
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		// TODO Auto-generated method stub
			logger.debug("Started writing for batchupload - upLoadScCodeFileWrite");
			for( Object object : objects){
				UploadScCodeFile uploadScCodeFile = (UploadScCodeFile) object;
				uploadScCodeFileWriterProcessService.insertScCodeFileRecords(uploadScCodeFile);
			}
		}


}
