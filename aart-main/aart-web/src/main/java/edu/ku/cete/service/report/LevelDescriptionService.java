package edu.ku.cete.service.report;

import java.util.List;

import edu.ku.cete.domain.report.LevelDescription;

public interface LevelDescriptionService {
	Integer deleteAllLevelDescription(Long schoolYear);
	Integer deleteLevelDescriptions(Long assessmentProgramId, Long contentAreaId, Long schoolYear, Long testingProgramId, String reportCycle);
	Integer insertSelectiveLevelDescription(LevelDescription levelDescription);
	List<LevelDescription> selectLevelsByAssessmentProgramSubjectGradeYearLevel(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeId, Long level, Long testingProgramId);
	List<LevelDescription> getLevelsAndTestCutMinMaxByTestId1TestId2(List<Long> testIds, Long assessmentProgramId, Long subjectId, Long gradeId);
	List<LevelDescription> getDistinctLevelsAndTestCutMinMax(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear);
	
	LevelDescription getById(Long levelDescriptionId);
	List<LevelDescription> getLevelDescriptionByLevelId(Long schoolYear,
			Long assessmentProgramId, Long contentAreaId, Long gradeId,
			String descriptionType, Long testingProgramId);
	List<LevelDescription> getLevelDescriptionByLevelIdMdptIdCombinedId(
			Long levelId, Long mdptLevelId, Long combinedLevelId);
	List<LevelDescription> selectLevelsByAssessmentProgramSubjectGradeYearCycle(
			Long schoolYear, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long testingProgramId, String reportCycle);
	List<LevelDescription> getLevelDescriptionByLevelIdAndTestType(Long schoolYear, Long assessmentProgramId,
			String testType, Long LevelNumber);
	
}
