package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.model.UploadResultFileMapper;
import edu.ku.cete.service.report.UploadScCodeFileWriterProcessService;

@Service
public class UploadScCodeFileWriterProcessServiceImpl implements
		UploadScCodeFileWriterProcessService {
	@Autowired
	private	UploadResultFileMapper uploadResultFileMapper;
	@Override
	public Integer insertScCodeFileRecords(
			UploadScCodeFile uploadScCodeFileRecord) {
		// TODO Auto-generated method stub
		return uploadResultFileMapper.insertScCodeFileRecord(uploadScCodeFileRecord);
	}
	@Override
	public void updateScCodeFileRecordsByStateAndReportYear(Long state, Long reportYear,Long uploadedUserId, Long batchProcessId,boolean status) {
		// TODO Auto-generated method stub
		uploadResultFileMapper.updateScCodeFileRecordsByStateAndReportYear(state, reportYear, uploadedUserId, batchProcessId, status);
	}
	@Override
	public void deleteBatchFailedScCodeFileRecords(Long batchUploadId) {
		// TODO Auto-generated method stub
		uploadResultFileMapper.deleteBatchFailedScCodeFileRecords( batchUploadId);
	}

}
