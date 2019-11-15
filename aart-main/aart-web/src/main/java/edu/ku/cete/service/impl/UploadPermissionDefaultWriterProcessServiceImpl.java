package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadedPermissionRecord;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.UploadPermissionWriteService;

@Service
public class UploadPermissionDefaultWriterProcessServiceImpl implements BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(UploadPermissionDefaultWriterProcessServiceImpl.class);
	@Autowired
	private UploadPermissionWriteService uploadPermissionWriteService; 
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - permissions upload");
		for( Object object : objects){
			UploadedPermissionRecord uploadPermission = (UploadedPermissionRecord) object;
			uploadPermissionWriteService.updatePermission(uploadPermission);
		}
	}

}
