package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.model.LevelDescriptionMapper;
import edu.ku.cete.service.report.LevelDescriptionService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class LevelDescriptionServiceImpl implements LevelDescriptionService {

	/**
	 * userReportUploadMapper
	 */
	@Autowired
	private LevelDescriptionMapper levelDescriptionMapper;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllLevelDescription(Long schoolYear) {
		return levelDescriptionMapper.deleteAll(schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteLevelDescriptions(Long assessmentProgramId, Long subjectId, Long schoolYear, Long testingProgramId, String reportCycle){
		return levelDescriptionMapper.deleteLevelDescriptions(assessmentProgramId, subjectId, schoolYear, testingProgramId, reportCycle);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveLevelDescription(LevelDescription testCutScore) {
		return levelDescriptionMapper.insertSelective(testCutScore);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<LevelDescription> selectLevelsByAssessmentProgramSubjectGradeYearLevel(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeId, Long level, Long testingProgramId) {
		return levelDescriptionMapper.selectLevelsByAssessmentProgramSubjectGradeYearLevel(schoolYear, assessmentProgramId, subjectId, gradeId, level, testingProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<LevelDescription> getLevelsAndTestCutMinMaxByTestId1TestId2(List<Long> testIds, Long assessmentProgramId, Long subjectId, Long gradeId) {
		return levelDescriptionMapper.selectLevelsAndTestCutMinMaxByTestId1TestId2(testIds, assessmentProgramId, subjectId, gradeId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<LevelDescription> getDistinctLevelsAndTestCutMinMax(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear) {
		return levelDescriptionMapper.selectDistinctLevelsAndTestCutMinMax(assessmentProgramId, subjectId, gradeId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public LevelDescription getById(Long levelDescriptionId) {
		return levelDescriptionMapper.selectByPrimaryKey(levelDescriptionId);
	}

	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<LevelDescription> getLevelDescriptionByLevelId(Long schoolYear,
			Long assessmentProgramId, Long contentAreaId, Long gradeId,
			String descriptionType, Long testingProgramId) {
		return levelDescriptionMapper.selectLevelDescriptionByLevelId(schoolYear, assessmentProgramId, contentAreaId, gradeId, descriptionType, testingProgramId);
	}

	@Override
	public List<LevelDescription> getLevelDescriptionByLevelIdMdptIdCombinedId(
			Long levelId, Long mdptLevelId, Long combinedLevelId) {
		// TODO Auto-generated method stub
		return levelDescriptionMapper.selectLevelDescriptionByLevelIdMdptIdCombinedId(levelId,mdptLevelId,combinedLevelId);
	}

	@Override
	public List<LevelDescription> selectLevelsByAssessmentProgramSubjectGradeYearCycle(
			Long schoolYear, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long testingProgramId, String reportCycle) {
		return levelDescriptionMapper.selectLevelsByAssessmentProgramSubjectGradeYearLevelCycle(schoolYear, assessmentProgramId, contentAreaId, gradeId, testingProgramId, reportCycle);
	}

	@Override
	public List<LevelDescription> getLevelDescriptionByLevelIdAndTestType(
			Long schoolYear, Long assessmentProgramId, String testTypeCode,
			Long LevelNumber) {
		return levelDescriptionMapper.getLevelDescriptionByLevelIdAndTestType(schoolYear, assessmentProgramId, testTypeCode, LevelNumber);
	}
	
}
