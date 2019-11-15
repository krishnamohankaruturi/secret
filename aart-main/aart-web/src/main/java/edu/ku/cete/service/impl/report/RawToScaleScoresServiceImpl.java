package edu.ku.cete.service.impl.report;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.model.RawToScaleScoresMapper;
import edu.ku.cete.service.report.RawToScaleScoresService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class RawToScaleScoresServiceImpl implements RawToScaleScoresService {

	@Autowired
	private RawToScaleScoresMapper rawToScaleScoresMapper;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllRawToScaleScores(Long schoolYear) {
		return rawToScaleScoresMapper.deleteAll(schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteRawToScaleScores(Long assessmentProgramId, Long subjectId, Long schoolYear, Long testingProgramId, String reportCycle){
		return rawToScaleScoresMapper.deleteRawToScaleScores(assessmentProgramId, subjectId, schoolYear, testingProgramId, reportCycle);
	}
   
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveRawToScaleScores(RawToScaleScores rawToScaleScoresRecord) {
		return rawToScaleScoresMapper.insertSelective(rawToScaleScoresRecord);
	}

	 
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean checkDuplicateTestIdsWithRawScore(Long testId1, Long testId2, Long testId3, Long testId4, Long performanceTestId, BigDecimal rawScore, Long scaleScore, Long assessmentProgramId, Long subjectId, Long gradeId, String domain, Long schoolYear, String testingProgramName, String reportCycle) {
		return (rawToScaleScoresMapper.checkDuplicateTestIdsWithRawScore(testId1, testId2, testId3, testId4, performanceTestId, rawScore, scaleScore, assessmentProgramId, subjectId, gradeId, domain, schoolYear, testingProgramName, reportCycle) > 0);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> selectDistinctTestIds(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, Long testingProgramId, String reportCycle) {
		return rawToScaleScoresMapper.selectDistinctTestIds(assessmentProgramId, subjectId, gradeId, schoolYear, testingProgramId, reportCycle);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public RawToScaleScores getByTestId1TestId2RawScore(List<Long> testIds, BigDecimal rawScore, Long assessmentProgramId, Long subjectId, Long schoolYear) {
		return rawToScaleScoresMapper.selectByTestId1TestId2RawScore(testIds, rawScore, assessmentProgramId, subjectId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public RawToScaleScores selectByTestIdsAPSubjIdGradeId(List<Long> testIds, Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear,
			Long testId1, Long testId2, Long testId3, Long testId4, Long performanceTestId, BigDecimal rawScore, Long testingProgramId, String reportCycle) {
		return rawToScaleScoresMapper.selectByTestIdsAPSubjIdGradeId(testIds, assessmentProgramId, subjectId, gradeId, schoolYear, testId1, testId2, testId3, testId4, performanceTestId, rawScore, testingProgramId, reportCycle);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public RawToScaleScores getFirstIncludePerformanceFlagForAssessmentProgramSubjectGrade(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, String testingProgram, String reportCycle){
		return rawToScaleScoresMapper.getFirstIncludePerformanceFlagForAssessmentProgramSubjectGrade(assessmentProgramId, subjectId, gradeId, schoolYear, testingProgram, reportCycle);
	}
	
	
}
