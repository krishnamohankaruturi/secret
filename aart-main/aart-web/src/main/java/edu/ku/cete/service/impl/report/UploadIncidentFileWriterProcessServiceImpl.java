package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.model.UploadResultFileMapper;
import edu.ku.cete.service.report.UploadIncidentFileWriterProcessService;

@Service
public class UploadIncidentFileWriterProcessServiceImpl implements
		UploadIncidentFileWriterProcessService {
	@Autowired
	private	UploadResultFileMapper uploadResultFileMapper;

	@Override
	public Integer insertIncidentFileRecords(
			UploadIncidentFile uploadIncidentFileRecord) {
		// TODO Auto-generated method stub
		
		return uploadResultFileMapper.insertIncidentFileRecord(uploadIncidentFileRecord);
	}

	@Override
	public void updateIncidentFileRecordsByStateAndReportYear(Long state,
			Long reportYear, Long uploadedUserId, Long batchProcessId,
			boolean status) {
		// TODO Auto-generated method stub
		uploadResultFileMapper.updateIncidentFileFileRecordsByStateAndReportYear(state, reportYear,uploadedUserId,batchProcessId,status);
		
	}

	@Override
	public void deleteFailedBatchIncidentFileRecords(Long batchUploadId) {
		// TODO Auto-generated method stub
		uploadResultFileMapper.deleteFailedBatchIncidentFileRecords(batchUploadId);
	}


	
}
