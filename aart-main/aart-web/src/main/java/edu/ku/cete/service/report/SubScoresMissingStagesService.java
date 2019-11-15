package edu.ku.cete.service.report;

import edu.ku.cete.domain.report.SubScoresMissingStages;

public interface SubScoresMissingStagesService {
	
	Integer deleteSubScoreDefaultStages(Long assessmentProgramId, Long contentAreaId, Long schoolYear);

	Integer insertSelectiveSubScoreMissingStages(SubScoresMissingStages subScoreMissingStages);
	
}
