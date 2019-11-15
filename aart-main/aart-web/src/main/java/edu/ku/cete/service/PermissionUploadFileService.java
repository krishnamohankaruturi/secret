package edu.ku.cete.service;

import edu.ku.cete.domain.PermissionUploadFile;

public interface PermissionUploadFileService {
	
	int insert(PermissionUploadFile permissionUploadFile);
	
	PermissionUploadFile getUplodFileDetails(Long uploadId);
}