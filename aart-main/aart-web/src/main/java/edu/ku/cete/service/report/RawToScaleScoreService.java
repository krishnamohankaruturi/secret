package edu.ku.cete.service.report;

import java.util.List;

import edu.ku.cete.domain.RawToScaleScore;

public interface RawToScaleScoreService {

 
	List<RawToScaleScore> cascadeAddOrUpdate(
			List<RawToScaleScore> rawToScaleScoresRecord, Long userReportUploadId);
	
	 
 
	Integer insert(RawToScaleScore rawToScaleScoresRecord, Long userReportUploadId);
}
