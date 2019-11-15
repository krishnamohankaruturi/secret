package edu.ku.cete.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.OperationalTestWindowMultiAssignDetail;
import edu.ku.cete.domain.test.OperationalTestWindowDTO;
import edu.ku.cete.web.AssessmentProgramTCDTO;


public interface OperationalTestWindowDao {

	/**
     * Create a new OperationalTestWindow.
     * @param toAdd object to add.
     * @return number of rows affected.
     */
	int addOperTestWindowData(OperationalTestWindow toAdd);
	
	/**
	 * @param id
	 * @return
	 */
	OperationalTestWindow selectOperTestWindowDataById(Long id);
		
	/**
	 * @param id
	 * @return
	 */
	int updateOperTestWindowByPrimaryKey(OperationalTestWindow operationalTestWindow);
	
	/**
	 * @param testCollectionId
	 * @return
	 */
	List<OperationalTestWindow> selectTestCollectionById(Long testCollectionId);
	
	/**	 
	 * @param operationalTestWindow
	 * @return
	 */
	int addOperTestWindowTestCollectionData(
			OperationalTestWindow operationalTestWindow);	   
	
	int addOperTestWindowStateData(
			OperationalTestWindow operationalTestWindow);	   
	
	int extendTestSessionWindow(OperationalTestWindow operationalTestWindow);

	List<OperationalTestWindow> selectTestWindowById(Long id);

	List<OperationalTestWindow> selectTestViewWindowById(Long testWindowId);

	List<AssessmentProgramTCDTO> selectAssessmentProgramAndTCListByWindowId(Long testWindowId);
	
	List<OperationalTestWindow> selectByAssessmentProgramAndHighStakes(@Param("assessmentProgramId") Long id,
			@Param("highStakes") boolean highStakes);
	/* Added for US16553 */
	List<OperationalTestWindowDTO> getSelectTestWindowByAssessmentProgram(
			@Param("userId") Long userId,
			@Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType,
			@Param("offset") Integer offset,
			@Param("limit") Integer limitCount,
			@Param("testWindowCriteriaMap") Map<String,String> testWindowCriteriaMap,
			@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("organizationId")Long organizationId);
	
	int countSelectTestWindowByAssessmentProgram(@Param("userId") Long userId,
			@Param("testWindowCriteriaMap") Map<String, String> testWindowCriteriaMap,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("organizationId")Long organizationId
			);
	List<AssessmentProgramTCDTO> existOperationalWindowTestCollection(
			@Param("windowId") Long windowId);

	void deleteBYWindowId(@Param("windowId") Long windowId);

	List<AssessmentProgramTCDTO> selectAssessmentProgramAndTCList(
			@Param("userId") Long userId,
			@Param("sortByColumn") String sortByColumn,
			@Param("sortType") String sortType,
			@Param("offset") Integer i, 
			@Param("limit") Integer limitCount,
			@Param("testWindowCriteriaMap") Map<String,String> testWindowCriteriaMap,
			@Param("assessmentProgramId")Long assessmentProgramId,
			@Param("randomizationType") String randomizationType,
			@Param("categoryCode") String categoryCode,
			@Param("windowId") Long windowId);




	int countAssessmentProgramAndTCList(
		@Param("userId") Long userId,
		@Param("testWindowCriteriaMap") Map<String,String> testWindowCriteriaMap,
		@Param("assessmentProgramId")Long assessmentProgramId,
		@Param("randomizationType") String randomizationType,
		@Param("categoryCode") String categoryCode,
		@Param("windowId") Long windowId);
	
	List<Long> selectOverlappingTestCollectionIds(@Param("beginTimeStamp")  Date beginTimeStamp,
				@Param("endTimeStamp")  Date endTimeStamp,
				@Param("windowId")  Long windowId,
				@Param("testCollectionIds") List<Long>  testCollectionIds,@Param("multiStateIds") List<Long>  multiStateIds);

	Integer isTestWidowNameExist(@Param("windowName")String windowName,@Param("id") Long id);

	List<OperationalTestWindow> getOperationalTestWindowsMultipleState(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("userId") Long userId);

	List<OperationalTestWindow> getMultipleStateByUserIdAndAssessmentProgramId(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("userId") Long userId);
	
	void deleteOperTestWindowStateData(Long operationalWindowId);

	List<OperationalTestWindow> getOperationalTestWindowsSelectedState(@Param("operationalTestWindowId") Long operationalTestWindowId);
	
	List<OperationalTestWindow> selectWindowsForBatchRegistration(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("testEnrollmentMethodId") Long autoEnrollmentMethodId);
	
	List<OperationalTestWindow> selectEffectiveWindowsForBatchRegistration(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("testEnrollmentMethodId") Long autoEnrollmentMethodId);
	
	List<OperationalTestWindow> selectEffectiveStateWindows(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("testEnrollmentMethodId") Long autoEnrollmentMethodId);
	
	List<OperationalTestWindow> getOperationalTestWindowsTestEnrollmentMethod(@Param("assessmentProgramId") Long assessmentProgramId);

	void addOperationalTestWindowMultiAssignDetail(OperationalTestWindowMultiAssignDetail operationalTestWindowMultiAssignDetail);

	List<OperationalTestWindowMultiAssignDetail> selectOperationalTestWindowMultiAssignDetailList(Long id);
	
	OperationalTestWindowMultiAssignDetail selectOTWMultiAssignDetail(@Param("operationalTestWindowId") Long operationalTestWindowId, 
			@Param("contentAreaId") Long contentAreaId);

	void updateOperationalTestWindowMultiAssignDetail(
			OperationalTestWindowMultiAssignDetail operationalTestWindowMultiAssignDetail);

	void deleteOperationalTestWindowMultiAssignDetail(Long windowId);
	
	List<DailyAccessCode> findDacTestStagesByWindow(@Param("operationalTestWindowId") Long operationalTestWindowId);
	
	List<DailyAccessCode> findDacTestStagesByGradeBand(@Param("operationalTestWindowId") Long operationalTestWindowId);
	
	OperationalTestWindow selectOverlappingOperationalTestWindowAutoEnrollmentIds(@Param("beginTimeStamp")  Date beginTimeStamp,
			@Param("endTimeStamp")  Date endTimeStamp,
			@Param("windowId")  Long windowId,
			@Param("testEnrollmentId") Long  testEnrollmentId,@Param("multiStateIds") List<Long>  multiStateIds);

	OperationalTestWindow selectOperTestWindowForInterim(@Param("testCollectionId")Long testCollectionId);	
	
	List<OperationalTestWindow> fetchOperationalTestWindowsByAssessmentProgram(@Param("assessmentProgramName") String assessmentProgramName);

	List<OperationalTestWindow> getOperationalTestWindowScoringWindowMethod(
			Long assessmentProgramId);

	int suspendOperTestWindowByPrimaryKey(OperationalTestWindow operationalTestWindow);
	
	OperationalTestWindowDTO getOpenInstructionAssessmentPlannerWindow(
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("stateId") Long stateId,
			@Param("contentAreaId") Long contentAreaId,
			@Param("otwId") Long otwId);
	
	List<Long> getStateIdsForOTWId(@Param("operationalTestWindowId") Long operationalTestWindowId);

	OperationalTestWindow getLatestActiveOperationalTestWindowForStudentTracker(@Param("assessmentProgramCode") String assessmentProgramCode,
			@Param("stateId") Long stateId);
	
	List<OperationalTestWindowDTO> getPreviousInstructionPlannerWindowsByStateAssessmentProgramAndSchoolYear(
			@Param("stateId") Long stateId,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("schoolYear") int schoolYear);
}