package edu.ku.cete.batch.upload.writer;
 
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
import edu.ku.cete.util.TimerUtil;
/**
 * Writes products to a database
 * @param <T>
 */
public class BatchGRFProcessWriter implements ItemWriter<UploadGrfFile>
{
	final static Log logger = LogFactory.getLog(BatchGRFProcessWriter.class);

	@Autowired
	private UploadGrfFileWriterProcessService uploadGrfFileWriterProcessService;
	 
     private Long batchReportProcessId;
     private Long userId;
 
	
	@Override
	public void write(List<? extends UploadGrfFile> grfs) throws Exception {
		TimerUtil timerUtil = TimerUtil.getInstance();
		timerUtil.start();
		for (UploadGrfFile uploadGrfFile : grfs) {
			uploadGrfFileWriterProcessService.insertGrfFileRecords(uploadGrfFile);
		}
		timerUtil.resetAndLogInfo(logger, "### Insertion UploadGRF Table Duration : ");
	}


	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	 
	

}
