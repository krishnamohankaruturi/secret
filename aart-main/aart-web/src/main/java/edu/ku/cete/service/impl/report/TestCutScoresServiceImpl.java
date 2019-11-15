package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.model.TestCutScoresMapper;
import edu.ku.cete.service.report.TestCutScoresService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestCutScoresServiceImpl implements TestCutScoresService {

	/**
	 * userReportUploadMapper
	 */
	@Autowired
	private TestCutScoresMapper testCutScoresMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllTestCutScores(Long schoolYear) {
		return testCutScoresMapper.deleteAll(schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteTestCutScores(Long assessmentProgramId, Long subjectId, Long schoolYear, Long testingProgramId, String reportCycle){
		return testCutScoresMapper.deleteTestCutScores(assessmentProgramId, subjectId, schoolYear, testingProgramId, reportCycle);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveTestCutScores(TestCutScores testCutScore) {
		return testCutScoresMapper.insertSelective(testCutScore);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public TestCutScores checkDuplicateLevels(Long level, Long assessmentProgramId, Long subjectId, Long schoolYear, String domain, Long testingProgramId, String reportCycle) {
		return testCutScoresMapper.checkDuplicateTestIdsWithLevel(level, assessmentProgramId, subjectId, schoolYear, domain, testingProgramId, reportCycle);
	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestCutScores> getCutScoresBasedOnAssessmentProgramSubjectYear(Long assessmentProgramId, Long subjectId, Long schoolYear, Long gradeCourseId, Long testingProgramId) {
		return testCutScoresMapper.getCutScoresBasedOnAssessmentProgramSubjectYear(assessmentProgramId, subjectId, schoolYear, gradeCourseId, testingProgramId);
	}

	@Override
	public List<TestCutScores> getTestCutScoresByReportCycle(
			Long assessmentProgramId, Long gradeId, Long contentAreaId,
			Long schoolYear, Long testingProgramId, String reportCycle) {
		return testCutScoresMapper.getInterimCutScoresBasedOnAssessmentProgramSubjectYear(assessmentProgramId, contentAreaId, schoolYear, gradeId, reportCycle, testingProgramId);
	}
	
	@Override
	public List<TestCutScores> getExternalStudentReportTestCutScores(
			Long assessmentProgramId, Long gradeId, Long contentAreaId,
			Long schoolYear, Long testingProgramId, String reportCycle, String assessmentCode) {
		return testCutScoresMapper.getExternalStudentReportTestCutScores(assessmentProgramId, contentAreaId, schoolYear, gradeId, reportCycle, testingProgramId, assessmentCode);
	}
	
}
