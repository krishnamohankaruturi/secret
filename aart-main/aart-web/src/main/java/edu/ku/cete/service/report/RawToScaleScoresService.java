package edu.ku.cete.service.report;

import java.math.BigDecimal;
import java.util.List;

import edu.ku.cete.domain.report.RawToScaleScores;


public interface RawToScaleScoresService {

	Integer deleteAllRawToScaleScores(Long schoolYear);
	Integer deleteRawToScaleScores(Long assessmentProgramId, Long subjectId, Long schoolYear, Long testingProgramId, String reportCycle);
	Integer insertSelectiveRawToScaleScores(RawToScaleScores excludedItem);
	boolean checkDuplicateTestIdsWithRawScore(Long testId1, Long testId2, Long testId3, Long testId4, Long performanceTestId, BigDecimal rawScore, Long scaleScore, Long assessmentProgramId, Long subjectId, Long gradeId, String domain, Long schoolYear, String testingProgramName, String reportCycle);
	List<Long> selectDistinctTestIds(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, Long testingProgramId, String reportCycle);
	RawToScaleScores getByTestId1TestId2RawScore(List<Long> testIds, BigDecimal rawScore, Long assessmentProgramId, Long subjectId, Long schoolYear);
	RawToScaleScores selectByTestIdsAPSubjIdGradeId(List<Long> testIds, Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, Long testId1, Long testId2, Long testId3, Long testId4, Long performanceTestId, BigDecimal rawScore, Long testingProgramId, String reportCycle);
	RawToScaleScores getFirstIncludePerformanceFlagForAssessmentProgramSubjectGrade(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, String testingProgram, String reportCycle);

}
