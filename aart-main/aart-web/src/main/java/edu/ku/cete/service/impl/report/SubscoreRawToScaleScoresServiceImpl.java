package edu.ku.cete.service.impl.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.SubScoresMissingStages;
import edu.ku.cete.domain.report.SubscoreRawToScaleScores;
import edu.ku.cete.model.SubScoresMissingStagesMapper;
import edu.ku.cete.model.SubscoreRawToScaleScoresMapper;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SubscoreRawToScaleScoresServiceImpl implements SubscoreRawToScaleScoresService {

	@Autowired
	private SubscoreRawToScaleScoresMapper subScoreRawToScaleScoresMapper;
	
	@Autowired
	private SubScoresMissingStagesMapper subscoresMissingStagesMapper;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllSubscoreRawToScaleScores(Long schoolYear) {
		return subScoreRawToScaleScoresMapper.deleteAll(schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteSubscoreRawToScaleScores(Long assessmentProgramId, Long subjectId, Long schoolYear){
		return subScoreRawToScaleScoresMapper.deleteSubscoreRawToScaleScores(assessmentProgramId, subjectId, schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveRawToScaleScores(SubscoreRawToScaleScores subScoreRawToScaleScoresRecord) {
		return subScoreRawToScaleScoresMapper.insertSelective(subScoreRawToScaleScoresRecord);
	}

	 
	@Override
	public boolean checkIfSubscoreDefinitionNameExists(String subScoreDefinitionName, Long grade, Long subject, Long assessmentProgramId){
		return (subScoreRawToScaleScoresMapper.checkIfSubscoreDefinitionNameExists(assessmentProgramId, subject, grade, subScoreDefinitionName) > 0);
	}
	
	@Override
	public List<String> getDistinctSubscoreMapping(Long assessmentProgramId, Long subjectId, Long gradeId, Long testId){
		List<String> subscoredefinitionnames = new ArrayList<String>();    	
		subscoredefinitionnames = subScoreRawToScaleScoresMapper.getDistinctSubscoreMapping(assessmentProgramId, subjectId, gradeId, testId);    	
    	return subscoredefinitionnames;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> selectDistinctTestIds(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear) {
		return subScoreRawToScaleScoresMapper.selectDistinctTestIds(assessmentProgramId, subjectId, gradeId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public SubscoreRawToScaleScores getRatingForSubscoreRawScore(List<Long> testIds, String subScoreDefinitionName, BigDecimal rawScore, Long assessmentProgramId, Long subjectId, Long gradeId, 
			Long schoolYear, Long testId1, Long testId2, Long testId3, Long testId4, Long performanceTestId) {
		return subScoreRawToScaleScoresMapper.getRatingForSubscoreRawScore(testIds, subScoreDefinitionName, rawScore, assessmentProgramId, subjectId, gradeId, schoolYear, testId1, testId2, testId3, testId4, performanceTestId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SubScoresMissingStages> getDefaultTestIDForSubscoreMissingStages(Long subjectId, Long gradeId, Long assessmentProgramId, Long schoolYear){
		return subscoresMissingStagesMapper.getDefaultTestIDForSubscoreMissingStages(subjectId, gradeId, assessmentProgramId, schoolYear);
	}
}
