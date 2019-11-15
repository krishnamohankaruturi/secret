package edu.ku.cete.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.UploadFileRecordService;
import edu.ku.cete.service.UploadFileService;

@Component("uploadFileProcessor")
public class UploadFileProcessor {
	
	private Logger LOGGER = LoggerFactory.getLogger(UploadFileProcessor.class);
	
	@Autowired
	private UploadFileService uploadFileService;
	
	@Autowired
	private UploadFileRecordService uploadFileRecordService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Async
	public Future<Map<String,Object>> create(UploadFile uploadFile, Errors errors, Long uploadFileRecordId) {
		Map<String,Object> uploadFileMap = new HashMap<String,Object>();
		try {
			uploadFileMap = uploadFileService.create(uploadFile, errors, uploadFileRecordId);
		} catch (Exception e) {
			UploadFileRecord uploadFileRecord = uploadFileRecordService.selectByPrimaryKey(uploadFileRecordId);
			Category failedStatus = categoryService.selectByCategoryCodeAndType("FAILED", "PD_REPORT_STATUS");
			uploadFileRecord.setStatusId(failedStatus.getId());
			uploadFileRecord.setModifiedDate(new Date());
			uploadFileRecord.setJsonData("{\"errorFound\":\"true\", \"uploadFileStatus\": \"FAILED\"}");
			uploadFileRecordService.updateUploadFile(uploadFileRecord);
			
            LOGGER.error("create Exception:", e);
		}
		return new AsyncResult<Map<String,Object>>(uploadFileMap);
	}

}
