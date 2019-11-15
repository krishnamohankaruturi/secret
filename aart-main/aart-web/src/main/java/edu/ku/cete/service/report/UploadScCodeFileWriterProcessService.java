package edu.ku.cete.service.report;

import edu.ku.cete.domain.UploadScCodeFile;

public interface UploadScCodeFileWriterProcessService {
	Integer insertScCodeFileRecords(UploadScCodeFile uploadScCodeFileRecord);
	
	void updateScCodeFileRecordsByStateAndReportYear(Long state, Long reportYear,Long uploadedUserId, Long batchProcessId, boolean status);

	void deleteBatchFailedScCodeFileRecords(Long batchUploadId);
}
