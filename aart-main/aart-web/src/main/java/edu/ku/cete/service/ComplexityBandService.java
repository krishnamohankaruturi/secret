package edu.ku.cete.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.user.User;

public interface ComplexityBandService {
	Set<Long> findSurveysForBatchAutoRegistration(Long contractingOrgId, Long assessmentProgramId, User user);

	Map<String, Object> calculateFinalComplexityBandsForSurvey(Long surveyId, User user);

	Map<String, Object> calculateComplexityBandsForSurvey(Long surveyId, User user,boolean isCalculatingCommBand);

	Map<String, Object> calculateComplexityBandsForBatch(Long surveyId,	User user);
	
	Integer getElaSessionLimit();
	
	Integer getMathSessionLimit();

	List<String> checkPoolTypes();

	List<ComplexityBand> getAllBands();

	void fcUnenrollStudent(Set<Long> testSessionIds, User user);

	List<ComplexityBand> getAllBandsWithContentArea(Long assessmentProgramId);
	
	List<ComplexityBand> getBandsByAssessmentProgramIdContentAreaId(Long assessmentProgramId, Long contentAreaId);

	void insertIntoDomainAuditHistory(Long objectId, String action, String source, String objectBeforUpdate,
			String objectAfterUpdate);	
	
	List<ComplexityBand> getAll();
}
