package edu.ku.cete.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.UploadFileRecord;
import edu.ku.cete.model.UploadFileRecordMapper;
import edu.ku.cete.service.UploadFileRecordService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UploadFileRecordServiceImpl implements UploadFileRecordService {
	
	@Autowired
	private UploadFileRecordMapper uploadFileRecordDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertUploadFile(UploadFileRecord record) {
		return uploadFileRecordDao.insert(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertUploadFileSelective(UploadFileRecord record) {
		return uploadFileRecordDao.insertSelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateUploadFile(UploadFileRecord record) {
		return uploadFileRecordDao.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateUploadFileSelective(UploadFileRecord record) {
		return uploadFileRecordDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public UploadFileRecord selectByPrimaryKey(UploadFileRecord record) {
		return uploadFileRecordDao.selectByPrimaryKey(record.getId());
	}
	
	@Override
	public UploadFileRecord selectByPrimaryKey(Long id) {
		return uploadFileRecordDao.selectByPrimaryKey(id);
	}

	@Override
	public UploadFileRecord selectByPrimaryKeyAndCreateUserId(Long id, Long userId) {
		return uploadFileRecordDao.selectByPrimaryKeyAndCreateUserId(id, userId);
	}
	
	@Override
	public String getUploadFile(Long id) {
		return uploadFileRecordDao.getUploadFile(id);
	}
}
