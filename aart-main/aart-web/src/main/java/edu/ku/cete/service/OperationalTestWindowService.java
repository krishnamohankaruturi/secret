package edu.ku.cete.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.OperationalTestWindowMultiAssignDetail;
import edu.ku.cete.domain.test.OperationalTestWindowDTO;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.web.AssessmentProgramTCDTO;
import edu.ku.cete.web.IAPContentFramework;

/**
 * @author vittaly
 *
 */
public interface OperationalTestWindowService {
	
	/**
     * @param toAdd object to add.
     *
     */
    int addOrUpdateOperTestWindowData(OperationalTestWindow toAdd);
    
    
    /**
     * @param toAdd object to add.
     *
     */
    int updateOperTestWindowState(OperationalTestWindow toAdd);
    
    /**
     * @param testCollectionId
     * @return
     */
    OperationalTestWindow selectTestCollectionById(Long testCollectionId);
    
    /**
     * @param testWindowCriteriaMap 
     * @param limitCount 
     * @param i 
     * @param sortType 
     * @param sortByColumn 
     * @param userDetails 
     * @param categoryCode 
     * @param randomizationType 
     * @param windowId 
     * @return
     */
   List<AssessmentProgramTCDTO> selectAssessmentProgramAndTCList(UserDetailImpl userDetails, String sortByColumn, String sortType, int i, Integer limitCount, Map<String, String> testWindowCriteriaMap, Long assessmentProgramId, String randomizationType, String categoryCode, Long windowId);
   
   boolean extendTestSessionWindow(List<OperationalTestWindow> toUpdate) throws Exception;
   
	List<OperationalTestWindow> selectTestViewWindowById(Long testWindowId);
	
		List<OperationalTestWindow> selectByAssessmentProgramAndHighStakes(Long id, boolean highStakes);
	
	   List<OperationalTestWindowDTO> selectTestWindowByAssessmentProgram(	UserDetailImpl userDetails, String sortByColumn, String sortType,
			int i, Integer limitCount, Map<String, String> testSessionCriteriaMap,
			Long assessmentProgramId);
	
	int countTestWindowByAssessmentProgram(UserDetailImpl userDetails,
			Map<String, String> testSessionCriteriaMap, Long assessmentProgramId);
	
	List<AssessmentProgramTCDTO> selectAssessmentProgramAndTCListByWindowId(Long testWindowId);
	
	List<AssessmentProgramTCDTO> getExistingOpertioanlWindowTestCollection(
			Long windowId);
	
	int countAssessmentProgramAndTCList(UserDetailImpl userDetails,
			Map<String, String> testWindowCriteriaMap, Long assessmentProgramId,
			String randomizationType, String categoryCode, Long windowId);
	
	List<Long> getOverlappingTestCollectionIds(Date beginTimeStamp, Date endTimeStamp, Long windowId,List<Long>  testCollectionIds,List<Long> multiStateIds);
	
	Map<String,Object> getAdminOptionData(Long windowId);
	
	boolean isTestWindowNameExist(String windowName, Long id);
	
	Map<Long, String> getOperationalTestWindowMultipleStateByUserIdAndAssessmentProgramId(
			Long assessmentProgramId, Long userId);
	
	List<OperationalTestWindow> getMultipleStateByUserIdAndAssessmentProgramId(
			Long assessmentProgramId, Long userId);
	
	Map<Long, String> getOperationalTestWindowSelectedState(Long operationalTestWindowId);
	
	Map<Long,String> getOperationalTestWindowTestEnrollmentMethod(Long assessmentProgramId);
	
	void addOperationalTestWindowMultiAssignDetail(
			List<OperationalTestWindowMultiAssignDetail> operationalTestWindowMultiAssignDetailList);
	
	List<OperationalTestWindowMultiAssignDetail> selectOperationalTestWindowMultiAssignDetail(Long id);
	
	void updateOperationalTestWindowMultiAssignDetail(
			List<OperationalTestWindowMultiAssignDetail> operationalTestWindowMultiAssignDetailList);
	OperationalTestWindowMultiAssignDetail getOTWMultiAssignDetail( Long operationalTestWindowId,  Long contentAreaId);
	
	void deleteOperationalTestWindowMultiAssignDetail(Long id);

	List<DailyAccessCode> getDacTestStagesByWindow(Long operationalTestWindowId);

	List<DailyAccessCode> getExistingAccessCodes(DailyAccessCode criteria);

	String getOverlappingOperationalTestWindowAutoEnrollmentIds(Date beginTimeStamp, Date endTimeStamp, Long windowId,Long  testEnrollmentId,List<Long> multiStateIds);

	Map<Long, String> getOperationalTestWindowScoringAssignmentMethod(
			Long assessmentProgramId);

	List<DailyAccessCode> getDacTestStagesByGradeBand(Long operationalTestWindowId);
	
	OperationalTestWindowDTO getOpenInstructionAssessmentPlannerWindow(Long assessmentProgramId, Long stateId, Long contentAreaId, Long otwId);

	List<Long> getStateIdsForOTWId(Long operationalTestWindowId);
	
	List<IAPContentFramework> getIAPContentFrameworkForWindow(
		Long operationalTestWindowId,
		String contentAreaAbbreviatedName,
		String gradeCourseAbbreviatedName
	);


	OperationalTestWindow getActiveOperationalTestWindowByStateIdAndAssessmentProgramCode(String assessmentProgramCode,
			Long stateId);
	
	List<OperationalTestWindow> selectOperationalTestWindowById(Long operationalTestWindowId);
	
	List<OperationalTestWindowDTO> getPreviousInstructionPlannerWindowsByStateAssessmentProgramAndSchoolYear(Long stateId, Long assessmentProgramId, int schoolYear);
}
