package edu.ku.cete.service;

import org.springframework.stereotype.Service;

import edu.ku.cete.domain.UploadFileRecord;

@Service
public interface UploadFileRecordService {
	public int insertUploadFile(UploadFileRecord record);
	
	public int insertUploadFileSelective(UploadFileRecord record);
	
	public int updateUploadFile(UploadFileRecord record);
	
	public int updateUploadFileSelective(UploadFileRecord record);
	
	public UploadFileRecord selectByPrimaryKey(UploadFileRecord record);
	
	public UploadFileRecord selectByPrimaryKey(Long id);
	
	public UploadFileRecord selectByPrimaryKeyAndCreateUserId(Long id, Long userId);
	
	public String getUploadFile(Long id);
}