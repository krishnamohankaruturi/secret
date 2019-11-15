package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.model.GrfStateApproveAuditMapper;
import edu.ku.cete.model.UploadResultFileMapper;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
@Service
public class UploadGrfFileWriterProcessServiceImpl implements
		UploadGrfFileWriterProcessService {
	
	@Autowired
	private	UploadResultFileMapper uploadResultFileMapper;
	
	@Autowired
	private	GrfStateApproveAuditMapper auditMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertGrfFileRecords(UploadGrfFile uploadGrfFileRecord) {
		return uploadResultFileMapper.insertGrfFileRecord(uploadGrfFileRecord);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateGrfFileRecordsByStateAndReportYear(Long state, Long reportYear,Long uploadedUserId,Long assessmentProgramId,Long batchProcessId,boolean status) {
		uploadResultFileMapper.updateGrfFileRecordsByStateAndReportYear(state, reportYear,uploadedUserId,assessmentProgramId,batchProcessId,status);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteFailedBatchGrfFileRecords(Long batchUploadId) {
		uploadResultFileMapper.deleteFailedBatchGrfFileRecords(batchUploadId); 
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void clearRecordsOnOriginalGRFUpload(Long stateId, Long reportYear,
			Long assessmentProgramId) {
		uploadResultFileMapper.clearRecordsOnOriginalGRFUpload(stateId, reportYear, assessmentProgramId);		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void setGRFAuditInfo(GrfStateApproveAudit audit) {
	   auditMapper.insert(audit);		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void setRecentFlag(Long batchUploadId, Long reportYear) {		
		uploadResultFileMapper.setRecentFlag(batchUploadId, reportYear);
	}
	
	
}
