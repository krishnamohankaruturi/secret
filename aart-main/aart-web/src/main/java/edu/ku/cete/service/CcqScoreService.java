package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.CcqScore;
import edu.ku.cete.domain.CcqScoreItem;
import edu.ku.cete.domain.test.StimulusVariant;
import edu.ku.cete.web.MonitorCcqScorersDetailsDTO;

public interface CcqScoreService {
	
	CcqScore add(CcqScore ccqScoreTestStudent,List<CcqScoreItem> ccqScoreTestStudentRubricScores);
	
	int getCountOfAllTestSessionStudentsToScoreForScorer(Long scorerId);
    int getCountOfStudentsInTestSessionToScoreForScorer(Long scorerId, Long scoringAssignmentid);
    
    List<MonitorCcqScorersDetailsDTO> getMonitorCcqScoresDetails(Long scoringAssignmentId,
    		int currentSchoolYear,
			String sortByColumn,
			String sortType,
			Integer i, 
			Integer limitCount,
			Map<String,String> scorerTestStudentRecordCriteriaMap
			);
    Long isCheckStudentAndScorer(Long scoringAssignmentScorerId,Long scoringAssignmentStudentId);

	void updateScorerAndStudent(CcqScore ccqScore);
	void add(CcqScore ccqScore);

	void update(CcqScore ccqScore, List<CcqScoreItem> ccqScoreItemList);

	void addForAllScorer(CcqScore ccqScore, List<CcqScoreItem> ccqScoreItemList, Long scoringAssignmentId, Integer userId);

	CcqScore getByEducatorIdAndStateStudentId(String assignmentName,
			String educatorId, String stateStudentId);

	void deleteScoredItems(Long id, Long taskVariantsId);

	 List<StimulusVariant> getStimulusVariant(Long testletId);

	List<StimulusVariant> getStimulusVariantByTaskVariantId(Long taskVariantId);
	
}
