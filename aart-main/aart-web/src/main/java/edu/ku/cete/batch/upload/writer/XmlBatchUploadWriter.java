package edu.ku.cete.batch.upload.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.service.report.BatchUploadService;

/**
 * Writes products to a database.
 * 
 * @param <T>
 */
public class XmlBatchUploadWriter implements ItemWriter<Object> {

	final static Log logger = LogFactory.getLog(BatchUploadJobListener.class);

	@Autowired
	private BatchUploadService batchUploadService;

	private String uploadTypeCode;

	@Override
	public void write(List<? extends Object> objects) throws Exception {
		logger.debug("Started Write process for uploadTypeCode -" + uploadTypeCode);
		if (!objects.isEmpty()) {
			batchUploadService.writeProcess(objects, uploadTypeCode);
		}
		logger.debug("Completed Write process for uploadTypeCode -" + uploadTypeCode);
	}

	public String getUploadTypeCode() {
		return uploadTypeCode;
	}

	public void setUploadTypeCode(String uploadTypeCode) {
		this.uploadTypeCode = uploadTypeCode;
	}

}
