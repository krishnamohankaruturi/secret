package edu.ku.cete.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.report.domain.StateSpecificFile;

public interface StateSpecificFileDao {

	int insertStateSpecificFileData(StateSpecificFile stateSpecificFiles);

	List<StateSpecificFile> getStateSpecificFileData(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("stateId") Long stateId, @Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType, @Param("offset") Integer offset,
			@Param("limit") Integer limitCount, @Param("recordCriteriaMap") Map<String,String> recordCriteriaMap);

	int removeStateSpecificFile(@Param("id") Long id, @Param("modifiedUserId") Long modifiedUserId,
			@Param("modifiedDate") Date modifiedDate);

	StateSpecificFile getStateSpecificFileById(@Param("id") Long id);
}
