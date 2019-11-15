package edu.ku.cete.model;

import edu.ku.cete.domain.PermissionUploadFile;
import org.apache.ibatis.annotations.Param;

public interface PermissionUploadMapper {
	int insert(PermissionUploadFile scoringUploadFile);
	PermissionUploadFile getUplodFileDetails(@Param("uploadId") Long uploadId);
}

