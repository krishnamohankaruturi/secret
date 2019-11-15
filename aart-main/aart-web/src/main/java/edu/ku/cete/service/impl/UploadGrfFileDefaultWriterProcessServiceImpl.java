package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
@Service
public class UploadGrfFileDefaultWriterProcessServiceImpl implements
		BatchUploadWriterService {
final static Log logger = LogFactory.getLog(UploadGrfFileDefaultWriterProcessServiceImpl.class);
	
	@Autowired
	private UploadGrfFileWriterProcessService uploadGrfFileWriterProcessService; 
	@Override
	public void writerProcess(List<? extends Object> objects) {
		// TODO Auto-generated method stub
					logger.debug("Started writing for batchupload - upLoadScCodeFileWrite");
					for( Object object : objects){
						UploadGrfFile uploadGrfFile = (UploadGrfFile) object;
						uploadGrfFileWriterProcessService.insertGrfFileRecords(uploadGrfFile);
					}
	}

}
