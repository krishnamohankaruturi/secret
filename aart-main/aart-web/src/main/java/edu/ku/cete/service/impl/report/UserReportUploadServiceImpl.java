package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.UserReportUpload;
import edu.ku.cete.model.report.UserReportUploadMapper;
import edu.ku.cete.service.report.UserReportUploadService;

/**
 * @author vittaly
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UserReportUploadServiceImpl implements UserReportUploadService {

	/**
	 * userReportUploadMapper
	 */
	@Autowired
	private UserReportUploadMapper userReportUploadMapper;
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.report.UserReportUploadService#getLatestUploadedDataByUser(java.lang.Long)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<UserReportUpload> getLatestUploadedDataByUser(Long userId, String sortByColumn, String sortType) {
		return userReportUploadMapper.selectLatestUploadedDataByUser(userId, sortByColumn, sortType);
	}
	
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.report.UserReportUploadService#insertReportFileData(
	 *  java.lang.String, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public UserReportUpload insertReportFileData(String fileName, String fileContent, Long fileTypeId, Long userId) {
		Integer success = null;
		UserReportUpload userReportUpload = new UserReportUpload();
		userReportUpload.setUploadedFileName(fileName);
		userReportUpload.setFileData(fileContent);
		userReportUpload.setFileTypeId(fileTypeId);		
		userReportUpload.setUserId(userId);
		userReportUpload.setAuditColumnProperties();
		
		success = userReportUploadMapper.insert(userReportUpload);
		
		return userReportUpload;
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.report.UserReportUploadService#insertReportFileData(
	 *  java.lang.String, java.lang.String, java.lang.Long, java.lang.Long)
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteReportFileData(Long reportId) {
		userReportUploadMapper.deleteByPrimaryKey(reportId);
	}
	
}
