package edu.ku.cete.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.CcqScore;
import edu.ku.cete.domain.CcqScoreItem;
import edu.ku.cete.domain.ScoringAssignmentScorer;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.test.StimulusVariant;
import edu.ku.cete.model.CcqScoreItemMapper;
import edu.ku.cete.model.CcqScoreMapper;
import edu.ku.cete.model.ScoringAssignmentScorerMapper;
import edu.ku.cete.model.test.StimulusVariantDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.CcqScoreService;
import edu.ku.cete.web.MonitorCcqScorersDetailsDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class CcqScoreItemServiceImpl implements CcqScoreService {

	@Autowired
	private CcqScoreMapper ccqScoreDao;
	
	@Autowired
	private CcqScoreItemMapper ccqScoreTestStudentRubricScoreDao;
	
	@Autowired
	private ScoringAssignmentScorerMapper assignmentScorerMapper; 

	@Autowired
	private	CategoryService categoryService;
	
	@Autowired
	private	StimulusVariantDao stimulusVariantDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public CcqScore add(CcqScore ccqScoreTestStudent,
			List<CcqScoreItem> ccqScoreTestStudentRubricScores) {
		
		CcqScore ccqSc = ccqScoreDao.getByScorerAndStudent(ccqScoreTestStudent.getScoringAssignmentScorerId(), ccqScoreTestStudent.getScoringAssignmentStudentId());
		if(ccqSc != null){			
			ccqScoreTestStudent.setId(ccqSc.getId());
			if(ccqSc.getStatus() == null){
				ccqScoreTestStudent.setStatus(ccqScoreTestStudent.getStatus());				
			}
			ccqScoreDao.updateCCQScoreStatus(ccqScoreTestStudent);
		}else{
		   ccqScoreDao.insert(ccqScoreTestStudent);
		}
		Long id = ccqScoreTestStudent.getId(); 
		for(CcqScoreItem ccqScoreTestStudentRubricScore : ccqScoreTestStudentRubricScores){
			ccqScoreTestStudentRubricScore.setCcqScoreId(id);
			ccqScoreTestStudentRubricScoreDao.insert(ccqScoreTestStudentRubricScore);
		}
		if(ccqScoreTestStudent.getTestId() != null){
			updateCCQscoreStatusAfterCountingItems(ccqScoreTestStudent.getId(),ccqScoreTestStudent.getTestId());
		}
		return ccqScoreTestStudent;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getCountOfAllTestSessionStudentsToScoreForScorer(Long scorerId){
		return ccqScoreDao.getCountOfAllTestSessionStudentsToScoreForScorer(scorerId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int getCountOfStudentsInTestSessionToScoreForScorer(Long scorerId, Long scoringAssignmentid){
    	return ccqScoreDao.getCountOfTestSessionStudentsToScoreForScorer(scorerId, scoringAssignmentid);
    }

	@Override
	public List<MonitorCcqScorersDetailsDTO> getMonitorCcqScoresDetails(
			Long scoringAssignmentId, int currentSchoolYear,
			String sortByColumn, String sortType, Integer i,
			Integer limitCount,
			Map<String, String> scorerTestStudentRecordCriteriaMap) {

		return ccqScoreDao.getMonitorCcqScoresDetails(scoringAssignmentId, 
				currentSchoolYear, sortByColumn, sortType, i,
				limitCount, scorerTestStudentRecordCriteriaMap);
	}

	@Override
	public Long isCheckStudentAndScorer(Long scoringAssignmentScorerId,
			Long scoringAssignmentStudentId) {
		// TODO Auto-generated method stub
		return ccqScoreDao.isCheckScorerAndStudent(scoringAssignmentScorerId,scoringAssignmentStudentId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateScorerAndStudent(CcqScore ccqScore) {
		// TODO Auto-generated method stub
		ccqScoreDao.updateScorerAndStudent(ccqScore);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void add(CcqScore ccqScore) {
		// TODO Auto-generated method stub
		ccqScoreDao.insert(ccqScore);
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void update(CcqScore ccqScore, List<CcqScoreItem> ccqScoreItemList){
		List<CcqScore> ccqscorers = ccqScoreDao.updateScoreAndStatus(ccqScore);
		
		if(ccqscorers.size() > 0){
			//ccqScore = ccqscorers.get(0);
			Long id = ccqscorers.get(0).getId(); 
			for(CcqScoreItem ccqScoreTestStudentRubricScore : ccqScoreItemList){
				ccqScoreTestStudentRubricScore.setCcqScoreId(id);
				ccqScoreTestStudentRubricScoreDao.insert(ccqScoreTestStudentRubricScore);
			}
			if(ccqScore.getTestId() != null){
				updateCCQscoreStatusAfterCountingItems(ccqscorers.get(0).getId(),ccqScore.getTestId());
			}
		}else{
			add(ccqScore,ccqScoreItemList);
		}	
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addForAllScorer(CcqScore ccqScore, List<CcqScoreItem> ccqScoreItemList, Long scoringAssignmentId, Integer userId){
		List<ScoringAssignmentScorer> scorers = assignmentScorerMapper.getByScoringAssignmentId(scoringAssignmentId);
		List<CcqScore> ccqscorers = null;
		CcqScore ccqSc = null;
		Integer ccqScoreItemCount = null;
		
		Category scorinigCompleted = categoryService.selectByCategoryCodeAndType("COMPLETED","SCORING_STATUS");
		
		for (ScoringAssignmentScorer scoringAssignmentScorer : scorers) {//Iterate each scorer 
			ccqScore.setScoringAssignmentScorerId(scoringAssignmentScorer.getId());			
			ccqSc = ccqScoreDao.getByScorerAndStudent(ccqScore.getScoringAssignmentScorerId(), ccqScore.getScoringAssignmentStudentId());
			if(ccqSc != null){//Check If entry present in CCQscore table
				
				if(ccqSc.getStatus() == null ){
					ccqScoreDao.updateCCQScoreStatus(ccqScore);
					ccqSc.setStatus(ccqScore.getStatus());
				}
				if((ccqSc.getStatus() != null ) && ccqSc.getStatus().longValue() != scorinigCompleted.getId().longValue()){//If scoring completed no need to change anything
					ccqscorers = ccqScoreDao.updateScoreAndStatus(ccqScore);
					if(ccqscorers.size() > 0){
						Long id = ccqscorers.get(0).getId();
						ccqScoreItemCount = ccqScoreTestStudentRubricScoreDao.checkByCcqScoreAndTaskVariant(id,ccqScoreItemList.get(0).getTaskVariantId());
						if(ccqScoreItemCount != null && ccqScoreItemCount == 0){
							for(CcqScoreItem ccqScoreTestStudentRubricScore : ccqScoreItemList){
								ccqScoreTestStudentRubricScore.setCcqScoreId(id);
								ccqScoreTestStudentRubricScoreDao.insert(ccqScoreTestStudentRubricScore);
							}
						}
					}
					if(ccqScore.getTestId() != null){
						updateCCQscoreStatusAfterCountingItems(ccqscorers.get(0).getId(),ccqScore.getTestId());
					}
				}
			}else{
				add(ccqScore,ccqScoreItemList);
			}
		}
	}
	/**
	 * Added By Sudhansu.b
	 * Feature: f430
	 * Scoring Upload
	 */
	public CcqScore getByEducatorIdAndStateStudentId(String assignmentName,
			String educatorId, String stateStudentId){
		return ccqScoreDao.getByEducatorIdAndStateStudentId(assignmentName, educatorId, stateStudentId);
	}
	
	public void deleteScoredItems(Long ccqScoreid, Long taskVariantsId){
		ccqScoreTestStudentRubricScoreDao.deleteScoredItems(ccqScoreid, taskVariantsId);
	}

	public void updateCCQscoreStatusAfterCountingItems(Long ccqScoreId, Long testId){
		ccqScoreDao.updateCCQscoreStatusAfterCountingItems(ccqScoreId,testId);
	}

	@Override
	public  List<StimulusVariant> getStimulusVariant(Long testletId) {
		// TODO Auto-generated method stub
		 List<StimulusVariant> stimulusVariant=stimulusVariantDao.getStimulusVariantByTestletId(testletId);
		return stimulusVariant;
	}
	
	@Override
	public List<StimulusVariant> getStimulusVariantByTaskVariantId(
			Long taskVariantId) {
		List<StimulusVariant> stimulusVariant = null;
		stimulusVariant = stimulusVariantDao.getStimulusVariantByTaskVariantIdFortestlet(taskVariantId);
		if(stimulusVariant.size() == 0){
			stimulusVariant = stimulusVariantDao.getStimulusVariantByTaskVariantId(taskVariantId);
		}
		return stimulusVariant;
	}
}
