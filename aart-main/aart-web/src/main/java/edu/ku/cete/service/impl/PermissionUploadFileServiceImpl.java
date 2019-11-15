package edu.ku.cete.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.PermissionUploadFile;
import edu.ku.cete.model.PermissionUploadMapper;
import edu.ku.cete.service.PermissionUploadFileService;


@Service
public class PermissionUploadFileServiceImpl implements PermissionUploadFileService{

	@Autowired
	private PermissionUploadMapper PermissionUploadMapper;
	
	@Override
	public int insert(PermissionUploadFile permissionUploadFile) {		
		return PermissionUploadMapper.insert(permissionUploadFile);
	}
	
	@Override
	public PermissionUploadFile getUplodFileDetails(Long uploadId) {		
		return PermissionUploadMapper.getUplodFileDetails(uploadId);
	}



}