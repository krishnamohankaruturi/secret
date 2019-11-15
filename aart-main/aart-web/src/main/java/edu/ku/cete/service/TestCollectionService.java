/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.QCTestCollectionDTO;
import edu.ku.cete.domain.TestCollectionDTO;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.content.TestCollectionExample;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.model.ComplexityBandDao;
import edu.ku.cete.web.ITIBPCoverageRosteredStudentsDTO;

/**
 * @author neil.howerton
 *
 */
public interface TestCollectionService {

    /**
     *
     *@param example {@link TestCollectionExample}
     *@return int - the number of records that match the example.
     */
    int countByExample(TestCollectionExample example);

    /**
     *
     *@param example {@link TestCollectionExample}
     *@return int - the number of records deleted based on the example.
     */
    int deleteByExample(TestCollectionExample example);

    /**
     *
     *@param id {@link Long}
     *@return int - the number of records deleted.
     */
    int deleteByPrimaryKey(Long id);

    /**
     *
     *@param record {@link TestCollection}
     *@return int
     */
    TestCollection insert(TestCollection record);

    /**
     *
     *@param record {@link TestCollection}
     *@return int
     */
    TestCollection insertSelective(TestCollection record);

    /**
     *
     *@param example {@link TestCollectionExample}
     *@return List<TestCollection>
     */
    List<TestCollection> selectByExample(TestCollectionExample example);

    /**
     *
     *@param id {@link Long}
     *@return TestCollection
     */
    TestCollection selectByPrimaryKey(Long id);

    /**
     *
     *@param record {@link TestCollection}
     *@param example {@link TestCollectionExample}
     *@return int
     */
    int updateByExampleSelective(@Param("record") TestCollection record, @Param("example") TestCollectionExample example);

    /**
     *
     *@param record {@link TestCollection}
     *@param example {@link TestCollectionExample}
     *@return int
     */
    int updateByExample(@Param("record") TestCollection record, @Param("example") TestCollectionExample example);

    /**
     *
     *@param record {@link TestCollection}
     *@return int
     */
    int updateByPrimaryKeySelective(TestCollection record);

    /**
     *
     *@param record {@link TestCollection}
     *@return int
     */
    int updateByPrimaryKey(TestCollection record);

    /**
     *
     * @param testSessionId {@link Long}
     * @return {@link TestCollection}
     */
    TestCollection selectByTestSession(Long testSessionId);

    
    /**
     * This method selects test collections and tests of deployed
     * status for both system defined enrollment and manual enrollment type. 
     * 
     */
	List<TestCollectionDTO> selectTestsAndTestCollections(Long testStatusId, 
			Long systemEnrollmentRuleId, Long manualEnrollmentRuleId, Long organizationId, 
			Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap,
			String orderByClause, Integer offset, Integer limit, String taskTypeCode);
    
    List<TestCollectionDTO> selectTestsAndManualTestCollections(Long testStatusId,  
    		Long manualEnrollmentRuleId, Long organizationId, Boolean qcComplete, 
    		Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap,
    		String orderByClause, Integer offset, Integer limit);
        
    /**
     * @param testStatusId
     * @param systemEnrollmentRuleId
     * @param manualEnrollmentRuleId
     * @param organizationId
     * @param qcComplete
     * @param hasHighStakesPermission
     * @return
     */
    Integer countTestsAndTestCollections(Long testStatusId,Long systemEnrollmentRuleId, Long manualEnrollmentRuleId,
    		Long organizationId, Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap);
   
    Integer countTestsAndManualTestCollections(Long testStatusId, Long manualEnrollmentRuleId, Long organizationId, 
            Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap);
    
    /**
     * This method selects tests of deployed
     * status for both system defined enrollment and manual enrollment type.
     * 
     * @return
     */
	List<TestCollectionDTO> selectTestsAndTestCollectionsForQCAdmin(Long organizationId, Long systemEnrollmentRuleId,Long testStatusId,
			Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap,
			String orderByClause, Integer offset, Integer limit, String taskTypeCode);    
    
    /**
     * @param testStatusId
     * @param organizationId
     * @param qcComplete
     * @param hasHighStakesPermission
     * @return
     */
    Integer countTestsAndTestCollectionsForQCAdmin(Long testStatusId,
    		Long organizationId, Boolean qcComplete, Boolean hasHighStakesPermission,
    		Map<String,String> testsAndTestCollectionsCriteriaMap);

    /**
     * @param testStatusId
     * @param organizationId
     * @param qcComplete
     * @param hasHighStakesPermission
     * @return
     */
    List<QCTestCollectionDTO> selectTestsAndTestCollectionsForQCControl(Long testStatusId,
    		Long currentAssessmentProgramId, Boolean hasHighStakesPermission, int offset, int limitCount, Map<String,Object> criteria);
    
    Integer countTestsAndTestCollectionsForQCControl(Long testStatusId,
    		Long currentAssessmentProgramId, Boolean hasHighStakesPermission, Map<String,Object> criteria);

	List<TestCollection> findMatchingTestCollections(Float linkageLevelLowerBound, Float linkageLevelUpperBound,
			String gradeCourseCode, String contentAreaCode, int limit,
			List<String> excludeCollections, String poolType);
	
	List<TestCollection> findMatchingTestCollectionsIti(Float linkageLevelLowerBound, Float linkageLevelUpperBound,
			String gradeCourseCode, Long contentAreaId, long studentId, int limit,
			List<String> excludeCollections, List<String> eElements,String pnpAttributeName);
	
	List<ContentFrameworkDetail> selectContentCodeUsedInITIElgibleTestCollections(String gradeCourseCode, Long contentAreaId, Long studentId, Long rosterId, String statePoolType, String contentAreaAbbrName);	
	
	ComplexityBandDao getComplexityBandById(Long complexityBandId);
	
	Long itiNoTestCollectionReason(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, Long contentAreaId, long studentId, int limit, List<String> excludeCollections, String eElement, String pnpAttributeName);

	List<TestCollection> getTestCollectionForAuto(String assessmentProgramCode,
			Long assessmentId, Long gradeCourseId, Long testTypeId,
			Long contentAreaId, String randomizationTypeCode, String contentAreaCode, String assessmentCode, Long operationalTestWindowId, Long stageId);

	List<TestCollection> getTestCollectionsForDlmAuto(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, String contentAreaCode, Long studentId,
			List<String> eElements, String poolType, Long testSpecId, Long operationalWindowId);

	TestCollection getTestCollectionById(Long testCollectionId, Long stateId);

	List<TestCollection> getMultiAssignTestCollections(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, String contentAreaCode, int limit, List<Long> excludeCollections, String poolType, Long multiAssignTestWindowId);

	List<TestCollection> getTestCollectionForAutoWithTestExternalId(String assessmentProgramCode,
			Long assessmentId, Long gradeCourseId, Long testTypeId,
			Long contentAreaId, String randomizationTypeCode, String contentAreaCode, String assessmentCode, Long testExternalId);
	
	List<TestCollection> getTestCollectionForAMPPPWithTestExternalId(String assessmentProgramCode,
			Long assessmentId, Long gradeCourseId, Long testTypeId,
			Long contentAreaId, String randomizationTypeCode, String contentAreaCode, String assessmentCode, Long testExternalId, Long operationalTestWindowId);
	
	//US16879
	List<TestCollection> getTestCollectionForAutoAdaptive(String assessmentProgramCode, Long assessmentId,
			Long gradeCourseId, Long testTypeId, Long contentAreaId, String randomizationTypeCode,
		String contentAreaCode, String assessmentCode, Long otwId, String enrollmentMethodCode);
	
	List<TestCollection> getTestCollectionForAutoPredictive(
			String assessmentProgramCode, Long assessmentId,
			Long gradeCourseId, Long testTypeId, Long contentAreaId,
			String randomizationTypeCode, String contentAreaCode,
			String assessmentCode, Long otwId, String enrollmentMethodCode);
	
	Integer getTestCollectionPanelCount(Long testCollectionId);

	List<TestCollection> getFixedAssignTestCollections(String gradeCourseAbbrName, Long contentAreaId, List<Long> excludeTestCollections,
			Long operationalTestWindowId);
	
	List<TestCollection> getTestCollectionForKELPABatchAuto(String assessmentProgramCode,
			Long assessmentId, Long gradeCourseId, Long testTypeId,
			Long contentAreaId, String randomizationTypeCode, String contentAreaCode, String assessmentCode, Long operationalTestWindowId);

	int getNumberOfStudentsMetITIBPCrietria(Long criteria, Long contentAreaId, String gradeCourseAbbrName,
			List<ITIBPCoverageRosteredStudentsDTO> rosteredStudentsDetailsList, int schoolYear);
	
	String getStudentCriteriaStatus(Long criteria, Long contentAreaId, String gradeCourseAbbrName, Long studentId, int schoolYear);

	List<TestCollection> getDLMResearchSurveyTestCollections(Long operationalTestWindowId, String gradeAbbrName, Long contentAreaId, List<String> stageCode);

	Map<String, Map<String, String>> getActualAndMappedLikageLevelsByContentArea(String contentAreaAbbrName);

	List<ContentFrameworkDetail> getContentCodesForFieldTests(Long multiAssignTestWindowId, String orgPoolType,
			String gradeCode, Long contentAreaId);
	
	Long getStageIdByStageCode(String stageCode);
	
	List<TestCollection> getTestCollectionsForISmartBatchAuto(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, Long contentAreaId, Long operationalWindowId);
	
	List<TestCollection> getTestCollectionsForBatchAutoByGradeBandOTWId(Long assessmentProgramId,			
			Long contentAreaId,  
			Long gradeBandId, 
			Long operationalTestWindowId, 
			String randomizationTypeCode, 
			Long testStatusId,
			String stageCode);

	Stage getStageByCode(String stageCode);
}
