package edu.ku.cete.model;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.ScoringUploadFile;

public interface ScoringUploadMapper {

	int insert(ScoringUploadFile scoringUploadFile);

	ScoringUploadFile getScoringAssignmentUploadFile(@Param("fileName") String fileName);

	ScoringUploadFile getUplodFileDetails(@Param("uploadFileId") Long uploadFileId);

}
