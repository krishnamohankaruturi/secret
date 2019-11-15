package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.StudentResponseAudit;
import edu.ku.cete.domain.StudentResponseTaskVariantGroup;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.report.model.QuestarStagingFileMapper;
import edu.ku.cete.report.model.QuestarStagingRecordMapper;
import edu.ku.cete.report.model.QuestarStagingResponseMapper;
import edu.ku.cete.model.StudentResponseAuditMapper;
import edu.ku.cete.model.StudentResponseTaskVariantGroupMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TaskVariantDao;
import edu.ku.cete.model.TestDao;
import edu.ku.cete.report.domain.QuestarStagingFile;
import edu.ku.cete.report.domain.QuestarStagingRecord;
import edu.ku.cete.report.domain.QuestarStagingResponse;
import edu.ku.cete.service.QuestarService;
import edu.ku.cete.web.QuestarStagingDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class QuestarServiceImpl implements QuestarService {
	@Autowired
	private StudentResponseAuditMapper studentResponseAuditDao;
	
	@Autowired
	private StudentResponseTaskVariantGroupMapper studentResponseTaskVariantGroupDao;
	
	@Autowired
	private TaskVariantDao taskVariantDao;
	
	@Autowired
	private StudentsTestsDao studentsTestsDao;
	
	@Autowired
	private QuestarStagingFileMapper questarStagingFileDao;
	
	@Autowired
	private QuestarStagingRecordMapper questarStagingRecordDao;
	
	@Autowired
	private QuestarStagingResponseMapper questarStagingResponseDao;
	
	@Autowired
	private TestDao testDao;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertAuditSelective(StudentResponseAudit qr) {
		return studentResponseAuditDao.insertSelective(qr);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateAuditSelective(StudentResponseAudit qr) {
		return studentResponseAuditDao.updateByPrimaryKeySelective(qr);
	}

	@Override
	public StudentResponseAudit selectAuditByPimaryKey(Long id) {
		return studentResponseAuditDao.selectByPrimaryKey(id);
	}
	
	@Override
	public int getFirstVariantGroupWithCriteria(Map<String, Object> criteria) {
		return studentResponseTaskVariantGroupDao.selectFirstGroupWithCriteria(criteria);
	}
	
	@Override
	public List<TaskVariant> getTaskVariantsInGroup(long groupNumber, List<String> taskTypeCodes) {
		return taskVariantDao.getTaskVariantsInQuestarGroup(groupNumber, taskTypeCodes);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateGroupToProcessed(long groupNumber) {
		return studentResponseTaskVariantGroupDao.updateGroupToProcessed(groupNumber);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertStagingFileRecord(QuestarStagingFile record) {
		return questarStagingFileDao.insertSelective(record);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateStagingFileRecord(QuestarStagingFile record) {
		return questarStagingFileDao.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean insertStagingInfo(QuestarStagingRecord record,
			List<QuestarStagingResponse> responses) {
		questarStagingRecordDao.insert(record);
		Long id = record.getId();
		for (QuestarStagingResponse info : responses) {
			info.setQuestarStagingId(id);
			questarStagingResponseDao.insert(info);
		}
		return true;
	}

	@Override
	public List<QuestarStagingDTO> getNonProcessedStagingRecords(Integer offset, Integer pageSize) {
		return questarStagingRecordDao.getNonProcessed(offset, pageSize);
	}
	
	@Override
	public List<QuestarStagingResponse> getResponsesForStagingRecord(Long questarStagingId) {
		return questarStagingResponseDao.getByQuestarStagingId(questarStagingId);
	}

	@Override
	public int updateStagingRecord(QuestarStagingRecord record) {
		return questarStagingRecordDao.updateByPrimaryKey(record);
	}

	@Override
	public Long mapTestExternalId(Long formNumber) {
		Long mappedId = testDao.mapExternalId(formNumber);
		return mappedId;
	}

	@Override
	public int findDuplicateRecords(QuestarStagingRecord record) {
		return questarStagingRecordDao.getDuplicateCount(record);
	}
	
	public HashMap<Long, List<Long>> getQuestarTaskvariantMapByExternalId() {
		return taskVariantDao.getQuestarTaskvariantMapByExternalId();
	}

	@Override
	public int updateStagingRecordsToBeProcessed() {
		return questarStagingRecordDao.updateReadyForProcessing();
	}

	@Override
	public int updateStagingRecordsToProcessed() {
		return questarStagingRecordDao.updateToBeProcessedToProcessed();
	}

	@Override
	public List<StudentResponseTaskVariantGroup> getTaskVariantsInGroups(
			List<String> questarTaskTypesList) {
		return studentResponseTaskVariantGroupDao.selectAllNonProcessed(questarTaskTypesList);
	}
}