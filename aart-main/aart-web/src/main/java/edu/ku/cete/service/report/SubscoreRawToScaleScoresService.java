package edu.ku.cete.service.report;

import java.math.BigDecimal;
import java.util.List;

import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.domain.report.SubScoresMissingStages;

public interface SubscoreRawToScaleScoresService {

	Integer deleteAllSubscoreRawToScaleScores(Long schoolYear);
	Integer deleteSubscoreRawToScaleScores(Long assessmentProgramId, Long contentAreaId, Long schoolYear);
	Integer insertSelectiveRawToScaleScores(SubscoreRawToScaleScores subScoreRawToScaleScoresRecord);
	boolean checkIfSubscoreDefinitionNameExists(String subScoreDefinitionName, Long grade, Long subject, Long assessmentProgramId);
	List<String> getDistinctSubscoreMapping(Long assessmentProgramId, Long subjectId, Long gradeId, Long testId);
	List<Long> selectDistinctTestIds(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear);
	SubscoreRawToScaleScores getRatingForSubscoreRawScore(List<Long> testIds, String subScoreDefinitionName, BigDecimal rawScore, Long assessmentProgramId, Long subjectId, Long gradeId,
			Long schoolYear, Long testId1, Long testId2, Long testId3, Long testId4, Long performanceTestId);
	List<SubScoresMissingStages> getDefaultTestIDForSubscoreMissingStages(Long subjectId, Long gradeId, Long assessmentProgramId, Long schoolYear);
	
}
