package edu.ku.cete.service.report;

import java.util.List;

import edu.ku.cete.domain.report.TestCutScores;

public interface TestCutScoresService {
	Integer deleteAllTestCutScores(Long schoolYear);
	Integer deleteTestCutScores(Long assessmentProgramId, Long contentAreaId, Long schoolYear, Long testingProgramId, String reportCycle);
	Integer insertSelectiveTestCutScores(TestCutScores testCutScore);
	TestCutScores checkDuplicateLevels(Long level, Long assessmentProgramId, Long subjectId, Long schoolYear, String domain, Long testingProgramId, String reportCycle);
	
	
	List<TestCutScores> getCutScoresBasedOnAssessmentProgramSubjectYear(Long assessmentProgramId, Long subjectId, Long schoolYear, Long gradeCourseId, Long testingProgramId);
	List<TestCutScores> getTestCutScoresByReportCycle(Long assessmentProgramId,
			Long gradeId, Long contentAreaId, Long schoolYear,
			Long testingProgramId, String reportCycle);
	
	List<TestCutScores> getExternalStudentReportTestCutScores(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long schoolYear, Long testingProgramId, String reportCycle, String assessmentCode);
}
