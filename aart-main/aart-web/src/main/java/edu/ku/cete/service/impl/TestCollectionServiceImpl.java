/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.dlm.iti.BPCriteriaAndGroups;
import edu.ku.cete.dlm.iti.BPGroupsInfo;
import edu.ku.cete.domain.QCTestCollectionDTO;
import edu.ku.cete.domain.TestCollectionDTO;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.content.TestCollectionExample;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.BluePrintMapper;
import edu.ku.cete.model.ComplexityBandDao;
import edu.ku.cete.model.TaskVariantDao;
import edu.ku.cete.model.TestCollectionDao;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.studentsession.TestCollectionsSessionRulesService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.PoolTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.web.ITIBPCoverageRosteredStudentsDTO;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestCollectionServiceImpl implements TestCollectionService {

    @Autowired
    private TestCollectionDao testCollectionDao;

    @Autowired
    private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
    
    @Autowired
    private TaskVariantDao taskVariantDao;    

    @Autowired
    private TestCollectionsSessionRulesService testCollectionsSessionRulesService;
    
    @Autowired
    private TestStatusConfiguration testStatusConfiguration;
    
    @Autowired
    private BluePrintMapper bluePrintMapper;
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#countByExample(edu.ku.cete.domain.TestCollectionExample)
     */
    @Override
    public final int countByExample(TestCollectionExample example) {
        return testCollectionDao.countByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#deleteByExample(edu.ku.cete.domain.TestCollectionExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int deleteByExample(TestCollectionExample example) {
        return testCollectionDao.deleteByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#deleteByPrimaryKey(java.lang.Long)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int deleteByPrimaryKey(Long id) {
        return testCollectionDao.deleteByPrimaryKey(id);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#insert(edu.ku.cete.domain.TestCollection)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestCollection insert(TestCollection record) {
        testCollectionDao.insert(record);
        return record;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#insertSelective(edu.ku.cete.domain.TestCollection)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestCollection insertSelective(TestCollection record) {
        testCollectionDao.insertSelective(record);
        return record;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#selectByExample(edu.ku.cete.domain.TestCollectionExample)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestCollection> selectByExample(TestCollectionExample example) {
        return testCollectionDao.selectByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#selectByPrimaryKey(java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestCollection selectByPrimaryKey(Long id) {
        return testCollectionDao.selectByPrimaryKey(id);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#updateByExampleSelective(edu.ku.cete.domain.TestCollection, edu.ku.cete.domain.TestCollectionExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int updateByExampleSelective(TestCollection record, TestCollectionExample example) {
        return testCollectionDao.updateByExampleSelective(record, example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#updateByExample(edu.ku.cete.domain.TestCollection, edu.ku.cete.domain.TestCollectionExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExample(TestCollection record, TestCollectionExample example) {
        return testCollectionDao.updateByExample(record, example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#updateByPrimaryKeySelective(edu.ku.cete.domain.TestCollection)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByPrimaryKeySelective(TestCollection record) {
        return testCollectionDao.updateByPrimaryKeySelective(record);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#updateByPrimaryKey(edu.ku.cete.domain.TestCollection)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByPrimaryKey(TestCollection record) {
        return testCollectionDao.updateByPrimaryKey(record);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestCollection selectByTestSession(Long testSessionId) {
        if (testSessionId != null) {
            return testCollectionDao.selectByTestSession(testSessionId);
        }

        return null;
    }

    /**
     * This method selects test collections and tests of deployed
     * status for both system defined enrollment and manual enrollment type. 
     */
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#selectByExampleAndAssessmentId(edu.ku.cete.domain.TestCollectionExample, long, long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
	public List<TestCollectionDTO> selectTestsAndTestCollections(Long testStatusId, 
			Long systemEnrollmentRuleId, Long manualEnrollmentRuleId, Long organizationId, 
			Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap,
			String orderByClause, Integer offset, Integer limit, String taskTypeCode) {		
		
		List<TestCollectionDTO> testCollections = new ArrayList<TestCollectionDTO>();
		if(hasHighStakesPermission) {
			testCollections = testCollectionDao.selectTestsAndTestCollections(testStatusId,systemEnrollmentRuleId, manualEnrollmentRuleId, organizationId, 
	                qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap,
	                orderByClause, offset, limit); 
			
			if (testCollections != null
					&& CollectionUtils.isNotEmpty(testCollections)) {
				
				//INFO these test collections cannot be previewed.
					//Doing it as a separate query to avoid left outer join.
				List<Long> nonPreviewableTestCollectionIds = testCollectionsSessionRulesDao.selectByTestCollectionIdsAndSessionRuleId(
						AARTCollectionUtil.getIds(testCollections, 1), systemEnrollmentRuleId);
								
				if (nonPreviewableTestCollectionIds != null && 
						CollectionUtils.isNotEmpty(nonPreviewableTestCollectionIds)) {
					for (TestCollectionDTO testCollection : testCollections) {
						//Mark the testcollections as system if they are.
						if (nonPreviewableTestCollectionIds.contains(testCollection.getTestCollectionId())) {
							testCollection.setCanPreview(false);
						}
					}
				}
	
				//Set the ManualScoring based on ExtendedResponse.
				setExtendedResponse(testCollections, nonPreviewableTestCollectionIds, manualEnrollmentRuleId, taskTypeCode);
			}
		} 
		else 
		{
			testCollections = testCollectionDao.selectTestsAndManualTestCollections(testStatusId, manualEnrollmentRuleId, organizationId, 
	                qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap,
	                orderByClause, offset, limit); 
					
			if (testCollections != null
					&& CollectionUtils.isNotEmpty(testCollections)) {
				
				//Set the ManualScoring based on ExtendedResponse.
				setExtendedResponse(testCollections, null, manualEnrollmentRuleId, taskTypeCode);			
			}
		}
		
		return testCollections;
	}
	
	private void setExtendedResponse(List<TestCollectionDTO> testCollections, 
			List<Long> systemTestCollectionIds, Long manualEnrollmentRuleId, String taskTypeCode) {
		
		List<Long>  manualScoringTestCollectionIds = null;
		List<Long>  manualScoringTestIds = null;
				
		//Get the testId's for manual testCollections.
		List<Long> manualTestIds = testCollectionsSessionRulesService.selectByTestIdsAndSessionRuleId(
				AARTCollectionUtil.getIds(testCollections, 2), manualEnrollmentRuleId);
		
		if(systemTestCollectionIds != null && CollectionUtils.isNotEmpty(systemTestCollectionIds)) {
			manualScoringTestCollectionIds = taskVariantDao.getTestCollectionsByTaskTypeId(taskTypeCode, systemTestCollectionIds);
		}
		
		if(manualTestIds != null && CollectionUtils.isNotEmpty(manualTestIds)) {
			manualScoringTestIds = taskVariantDao.getTestsByTaskTypeId(taskTypeCode, manualTestIds);		
		}
		
		for (TestCollectionDTO testCollection : testCollections) {

			if( (manualScoringTestCollectionIds != null && CollectionUtils.isNotEmpty(manualScoringTestCollectionIds) && 
					manualScoringTestCollectionIds.contains(testCollection.getTestCollectionId())) || 
					(manualScoringTestIds != null && CollectionUtils.isNotEmpty(manualScoringTestIds) &&  
					manualScoringTestIds.contains(testCollection.getTestId()))) {
				testCollection.setManualScoring(true);
			}
		}
	}
	 
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestCollectionDTO> selectTestsAndManualTestCollections(Long testStatusId, 
    		Long manualEnrollmentRuleId, Long organizationId, 
            Boolean qcComplete, Boolean hasHighStakesPermission,
            Map<String,String> testsAndTestCollectionsCriteriaMap,
            String orderByClause, Integer offset, Integer limitCount) {
 
        return testCollectionDao.selectTestsAndManualTestCollections(testStatusId, 
        		manualEnrollmentRuleId, organizationId, qcComplete, hasHighStakesPermission, 
        		testsAndTestCollectionsCriteriaMap, orderByClause, offset, limitCount);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#countTestsAndTestCollections(java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    public final Integer countTestsAndTestCollections(Long testStatusId,Long systemEnrollmentRuleId, Long manualEnrollmentRuleId, Long organizationId, 
            Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap) {
 
        return testCollectionDao.countTestsAndTestCollections(testStatusId,systemEnrollmentRuleId, manualEnrollmentRuleId, organizationId, 
                qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap);
    }
    
    @Override
    public final Integer countTestsAndManualTestCollections(Long testStatusId, Long manualEnrollmentRuleId, Long organizationId, 
            Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap) {
 
        return testCollectionDao.countTestsAndManualTestCollections(testStatusId, manualEnrollmentRuleId, organizationId, 
                qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap);
    }    

    /**
     * This method selects tests of deployed
     * status for both system defined enrollment and manual enrollment type.
     * 
     */
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#selectTestsAndTestCollectionsForQCAdmin(java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.Boolean)
     */
    @Override    
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestCollectionDTO> selectTestsAndTestCollectionsForQCAdmin(Long organizationId, Long systemEnrollmentRuleId,Long testStatusId, 
			Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap,
			String orderByClause, Integer offset, Integer limit, String taskTypeCode) {		
		
		List<TestCollectionDTO> testCollections = new ArrayList<TestCollectionDTO>();		
		testCollections = testCollectionDao.selectTestsAndTestCollectionsForQCAdmin(testStatusId, organizationId, 
                qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap,
                orderByClause, offset, limit);
		
		if (testCollections != null
				&& CollectionUtils.isNotEmpty(testCollections)) {

			List<Long> nonPreviewableTestCollectionIds = testCollectionsSessionRulesDao.selectByTestCollectionIdsAndSessionRuleId(
					AARTCollectionUtil.getIds(testCollections, 1), systemEnrollmentRuleId); 

			
			if (nonPreviewableTestCollectionIds != null
					&& CollectionUtils.isNotEmpty(nonPreviewableTestCollectionIds)) {
				for (TestCollectionDTO testCollection : testCollections) {
					//Mark the testcollections as system if they are.
					if (nonPreviewableTestCollectionIds.contains(testCollection.getTestCollectionId())) {
						testCollection.setSystemFlag(true);
					}
				}
			}

			//Set the ManualScoring based on ExtendedResponse.
			setExtendedResponseForQCAdmin(testCollections, taskTypeCode);
		}		
		return testCollections;
	}    
    
	private void setExtendedResponseForQCAdmin(List<TestCollectionDTO> testCollections, String taskTypeCode) {

		List<Long>  manualScoringTestIds = null;

		if(testCollections != null && CollectionUtils.isNotEmpty(testCollections)) {
			manualScoringTestIds = taskVariantDao.getTestsByTaskTypeId(taskTypeCode, AARTCollectionUtil.getIds(testCollections, 2));		
		}
		
		for (TestCollectionDTO testCollection : testCollections) {
			if(manualScoringTestIds != null && CollectionUtils.isNotEmpty(manualScoringTestIds) &&  
					manualScoringTestIds.contains(testCollection.getTestId())) {
				testCollection.setManualScoring(true);
			}
		}
	}
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#countTestsAndTestCollectionsForQCAdmin(java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.Boolean)
     */
    @Override
    public final Integer countTestsAndTestCollectionsForQCAdmin(Long testStatusId, Long organizationId, 
            Boolean qcComplete, Boolean hasHighStakesPermission, Map<String,String> testsAndTestCollectionsCriteriaMap) {
 
        return testCollectionDao.countTestsAndTestCollectionsForQCAdmin(testStatusId, organizationId, 
                qcComplete, hasHighStakesPermission, testsAndTestCollectionsCriteriaMap);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#selectTestsAndTestCollectionsForQCControl(java.lang.Long, java.lang.Long, java.lang.Boolean, java.lang.Boolean)
     */
    public final List<QCTestCollectionDTO> selectTestsAndTestCollectionsForQCControl(Long testStatusId, Long currentAssessmentProgramId, 
            Boolean hasHighStakesPermission, int offset, int limitCount, Map<String,Object> criteria) {
 
        return testCollectionDao.selectTestsAndTestCollectionsForQCControl(testStatusId, currentAssessmentProgramId, hasHighStakesPermission, offset, limitCount, criteria);
    }
    
    public final Integer countTestsAndTestCollectionsForQCControl(Long testStatusId, Long currentAssessmentProgramId, Boolean hasHighStakesPermission, Map<String,Object> criteria) {
 
        return testCollectionDao.countTestsAndTestCollectionsForQCControl(testStatusId, currentAssessmentProgramId, hasHighStakesPermission,  criteria);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#findMatchingTestCollection(java.lang.Float, java.lang.Float, java.lang.String, java.lang.String, int)
     */
	@Override
	public List<TestCollection> findMatchingTestCollections(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, String contentAreaCode, int limit, List<String> excludeCollections, String poolType) {
		//test collections with grade have higher priority than
		//test collections with grade band
		if (CollectionUtils.isEmpty(excludeCollections))
			excludeCollections = null;
		List<TestCollection> matches = testCollectionDao.selectByAverageLinkageLevelAndContentArea(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaCode, excludeCollections, poolType);
		if (matches != null && matches.size() > limit){
			matches = selectRandomlyFrom(matches, limit);
		}
		if (matches != null && matches.size() < limit){
			int remainingLimit = limit - matches.size();
			List<TestCollection> bandMatches = testCollectionDao.selectByAverageLinkageLevelAndContentAreaWithGradeBand(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaCode, excludeCollections, poolType);
			if (bandMatches != null && !bandMatches.isEmpty()){
				matches.addAll(selectRandomlyFrom(bandMatches, remainingLimit));
			}
		}
		return matches;
	}
	
	/* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#findMatchingTestCollection(java.lang.Float, java.lang.Float, java.lang.String, java.lang.String, int)
     */
	@Override
	public List<TestCollection> getMultiAssignTestCollections(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, String contentAreaCode, int limit, List<Long> excludeCollections, String poolType, Long multiAssignTestWindowId) {
		//test collections with grade have higher priority than
		//test collections with grade band
		if (CollectionUtils.isEmpty(excludeCollections))
			excludeCollections = null;
		List<TestCollection> matches = testCollectionDao.selectByAverageLinkageLevelAndContentAreaForDLMMultiAssign(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaCode, excludeCollections, poolType, multiAssignTestWindowId);
		if (matches != null && matches.size() > limit){
			matches = selectRandomlyFrom(matches, limit);
		}
		if (matches != null && matches.size() < limit){
			int remainingLimit = limit - matches.size();
			List<TestCollection> bandMatches = testCollectionDao.selectByAverageLinkageLevelAndContentAreaWithGradeBandForDLMMultiAssign(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaCode, excludeCollections, poolType, multiAssignTestWindowId);
			if (bandMatches != null && !bandMatches.isEmpty()){
				matches.addAll(selectRandomlyFrom(bandMatches, remainingLimit));
			}
		}
		return matches;
	}
	
	 /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#findMatchingTestCollection(java.lang.Float, java.lang.Float, java.lang.String, java.lang.String, int)
     */
	@Override
	public List<TestCollection> findMatchingTestCollectionsIti(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, Long contentAreaId, long studentId, int limit, List<String> excludeCollections, List<String> eElements, String pnpAttributeName) {
		//DE8273.
		//test collections with grade band have higher priority than
		//test collections with grade 
		List<TestCollection> bandMatches = testCollectionDao.itiSelectByAverageLinkageLevelAndContentAreaWithGradeBand(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, studentId, excludeCollections, eElements, pnpAttributeName, SourceTypeEnum.ITI.getCode());
		if (bandMatches != null && !bandMatches.isEmpty()){
			bandMatches.addAll(selectRandomlyFrom(bandMatches, limit));
		}
		
		if (bandMatches != null && bandMatches.size() < limit){
			int remainingLimit = limit - bandMatches.size();
			List<TestCollection> matches = testCollectionDao.itiSelectByAverageLinkageLevelAndContentArea(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, studentId, excludeCollections, eElements, pnpAttributeName, SourceTypeEnum.ITI.getCode());
			if (matches != null && matches.size() >= limit){
				bandMatches.addAll(selectRandomlyFrom(matches, remainingLimit));
			}
		}
		return bandMatches;
	}

	@Override
	public List<TestCollection> getTestCollectionsForDlmAuto(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, String contentAreaCode, Long studentId, List<String> eElements, String poolType, Long testSpecId, Long operationalWindowId) {
		List<TestCollection> bandMatches = testCollectionDao.selectForDlmAutoRegistrationGradeBand(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaCode, studentId, eElements, poolType, testSpecId, operationalWindowId);
		
		if (bandMatches != null && bandMatches.isEmpty() ){
			bandMatches = testCollectionDao.selectForDlmAutoRegistrationGradeCourse(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaCode, studentId, eElements, poolType, testSpecId, operationalWindowId);
		}
		return bandMatches;
	}
	
	/* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionService#findMatchingTestCollection(java.lang.Float, java.lang.Float, java.lang.String, java.lang.String, int)
     */
	@Override
	public Long itiNoTestCollectionReason(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, Long contentAreaId, long studentId, int limit, List<String> excludeCollections, String eElement, String pnpAttributeName) {
		 
		return testCollectionDao.itiNoTestCollectionReason(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, studentId, excludeCollections, eElement, pnpAttributeName);
	}
	
	@Override
	public List<ContentFrameworkDetail> selectContentCodeUsedInITIElgibleTestCollections(String gradeCourseCode, Long contentAreaId, Long studentId, Long rosterId, String statePoolType, String contentAreaAbbrName) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Long stateId=userDetails.getUser().getContractingOrganization().getId();
		
		List<Long> testOverviewIds = testCollectionDao.getAvailableTestOverviewsForGradeAndCourse(gradeCourseCode, contentAreaId, stateId);
		Long testSpecExternalId = 0L;
		if(CollectionUtils.isNotEmpty(testOverviewIds)) {
			testSpecExternalId = testOverviewIds.get(0);
			for(Long testOverviewId : testOverviewIds){
				if(testOverviewId == null || (long) testOverviewId != (long) testSpecExternalId) {
					return getConFwDetailsWithReason(true);
				}
			}
		} else {
			return getConFwDetailsWithReason(false);
		}
		
		if(StringUtils.equalsIgnoreCase(PoolTypeEnum.SINGLEEE.name(),statePoolType) 
				&& !StringUtils.equalsIgnoreCase("SCI", contentAreaAbbrName)) {
			return getBluePrintCoverageForStudentAndRoster(gradeCourseCode, contentAreaId,
					studentId, rosterId, testSpecExternalId, stateId);
		}	
		
		return testCollectionDao.itiSelectContentCodeUsedInElgibleTestCollections(testSpecExternalId, stateId);
	}

	public List<ContentFrameworkDetail> getBluePrintCoverageForStudentAndRoster(
			String gradeCourseCode, Long contentAreaId, Long studentId,
			Long rosterId, Long testSpecExternalId, Long stateId) {
		Map<Long, BPCriteriaAndGroups> bpCriteriasMap = new HashMap<Long, BPCriteriaAndGroups>();
		List<BPCriteriaAndGroups> bpCriterias = bluePrintMapper.getBluePrintCriteriasByGradeAndSub(contentAreaId, gradeCourseCode);
		for(BPCriteriaAndGroups bpcriteria: bpCriterias) {
			if(!bpCriteriasMap.containsKey(bpcriteria.getCriteriaNum())) {
				bpCriteriasMap.put(bpcriteria.getCriteriaNum(), bpcriteria);
			}
		}
		List<ContentFrameworkDetail> listOfEEsToShow = testCollectionDao.itiSelectContentCodeUsedInElgibleTestCollectionsForSingleEE(testSpecExternalId
				, gradeCourseCode, contentAreaId, studentId, rosterId, stateId);
		
		List<ContentFrameworkDetail> listOfEEsNotAssigned = new ArrayList<ContentFrameworkDetail>();
		for(ContentFrameworkDetail ee:listOfEEsToShow) {
			if(ee.isStudentTestFlagExists() && ee.isEeInBluePrint()) {
				ee.setEeMetCriteria(true);
				List<BPGroupsInfo> bpGroups = bpCriteriasMap.get(ee.getCriteriaNumber()).getGrouspInfos();
				for(Long groupNum : ee.getGroupsNumbers()) {
					for(BPGroupsInfo bpg :bpGroups) {
						if(bpg.getGroupNumber().equals(groupNum)) {
							bpg.incrementNumberOfITIEEsCompleted();
						}
					}
				}
			} else if(ee.isEeInBluePrint()){
				listOfEEsNotAssigned.add(ee);
			}
		}
		for(ContentFrameworkDetail eeNotAssigned : listOfEEsNotAssigned) {
			if(bpCriteriasMap.get(eeNotAssigned.getCriteriaNumber()).isCriteriaRequirmentMetForITI()) {
				eeNotAssigned.setEeMetCriteria(true);				
			} else {
				boolean eeMetAllGroupsCriteria = true;
				List<BPGroupsInfo> bpGroups = bpCriteriasMap.get(eeNotAssigned.getCriteriaNumber()).getGrouspInfos();
				for(BPGroupsInfo bpg : bpGroups) {
					if(eeNotAssigned.getGroupsNumbers().contains(bpg.getGroupNumber())) {
						if(!bpg.isITIGroupRequiremtMet()) {
							eeMetAllGroupsCriteria = false;
							break;
						}
					}
				}
				eeNotAssigned.setEeMetCriteria(eeMetAllGroupsCriteria);				
			}
		}
		return listOfEEsToShow;		
	}

	private List<ContentFrameworkDetail> getConFwDetailsWithReason(boolean exits) {
		List<ContentFrameworkDetail> contentFrameWorkDetails = new ArrayList<ContentFrameworkDetail>();
		ContentFrameworkDetail contentFrameWork = new ContentFrameworkDetail();
		contentFrameWork.setMultipleTestOverviewsExists(exits);
		contentFrameWorkDetails.add(contentFrameWork);
		return contentFrameWorkDetails;
	}
	
	private List<TestCollection> selectRandomlyFrom(List<TestCollection> matches, int limit) {
		Collections.shuffle(matches);
		int matchCount = matches.size();
		int calculatedLimit = limit;
		if (matches.size() < limit){
			calculatedLimit = matchCount;
		}
		ArrayList<TestCollection> newList = new ArrayList<TestCollection>();
		for (int i = 0; i < calculatedLimit; i++){
			newList.add(matches.get(i));
		}
		return newList;
	}
	
	@Override
	public List<TestCollection> getTestCollectionForAuto(String assessmentProgramCode, Long assessmentId, Long gradeCourseId,
			Long testTypeId, Long contentAreaId, String randomizationTypeCode, String contentAreaCode, String assessmentCode,
			Long operationalTestWindowId, Long stageId) {
		//First check grade band 
		List<TestCollection> testCollections = testCollectionDao.selectForAutoGradeBand(assessmentProgramCode,randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode,
				operationalTestWindowId, stageId);
		
		if(CollectionUtils.isNotEmpty(testCollections)) {
			return testCollections;
		}
		//Get based on grade course
		testCollections = testCollectionDao.selectForAutoGradeCourse(assessmentProgramCode, randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode,
				operationalTestWindowId, stageId);
		
		return testCollections;
	}

	@Override
	public ComplexityBandDao getComplexityBandById(Long complexityBandId) {		
		return testCollectionDao.getComplexityBandById(complexityBandId);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public TestCollection getTestCollectionById(Long testCollectionId, Long stateId) {
		return testCollectionDao.selectTestCollectionById(testCollectionId, stateId);
	}

	@Override
	public List<TestCollection> getTestCollectionForAutoWithTestExternalId(
			String assessmentProgramCode, Long assessmentId,
			Long gradeCourseId, Long testTypeId, Long contentAreaId,
			String randomizationTypeCode, String contentAreaCode,
			String assessmentCode, Long testExternalId) {
		//First check grade band 
		List<TestCollection> testCollections = testCollectionDao.selectForAutoGradeBandWithTestExternalId(assessmentProgramCode,randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode,
				testExternalId);
		
		if(CollectionUtils.isNotEmpty(testCollections)) {
			return testCollections;
		}
		//Get based on grade course
		testCollections = testCollectionDao.selectForAutoGradeCourseWithTestExternalId(assessmentProgramCode, randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode,
				testExternalId);
		
		return testCollections;
	}
	
	@Override
	public List<TestCollection> getTestCollectionForAMPPPWithTestExternalId(
			String assessmentProgramCode, Long assessmentId,
			Long gradeCourseId, Long testTypeId, Long contentAreaId,
			String randomizationTypeCode, String contentAreaCode,
			String assessmentCode, Long testExternalId, Long operationalTestWindowId) {
		//First check grade band 
		List<TestCollection> testCollections = testCollectionDao.selectForAMPPPGradeBandWithTestExternalId(assessmentProgramCode,randomizationTypeCode, assessmentId,
				gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode, testExternalId, operationalTestWindowId);
		
		if(CollectionUtils.isNotEmpty(testCollections)) {
			return testCollections;
		}
		//Get based on grade course
		testCollections = testCollectionDao.selectForAMPPPGradeCourseWithTestExternalId(assessmentProgramCode, randomizationTypeCode, assessmentId,
				gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode, testExternalId, operationalTestWindowId);
		
		return testCollections;
	}	
	
	@Override
	public List<TestCollection> getTestCollectionForAutoAdaptive(String assessmentProgramCode, Long assessmentId,
			Long gradeCourseId, Long testTypeId, Long contentAreaId, String randomizationTypeCode,
		String contentAreaCode, String assessmentCode, Long otwId, String enrollmentMethodCode) {
		//Get based on grade course
		List<TestCollection> testCollections = testCollectionDao.selectForAutoGradeCourseForAdaptive(assessmentProgramCode, randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode, otwId, enrollmentMethodCode);
		
		return testCollections;
	}
	
	@Override
	public List<TestCollection> getTestCollectionForAutoPredictive(String assessmentProgramCode, Long assessmentId,
			Long gradeCourseId, Long testTypeId, Long contentAreaId, String randomizationTypeCode,
		String contentAreaCode, String assessmentCode, Long otwId, String enrollmentMethodCode) {
		//Get based on grade course
		List<TestCollection> testCollections = testCollectionDao.selectForAutoGradeCourseForPredictive(assessmentProgramCode, randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode, otwId, enrollmentMethodCode);
		
		return testCollections;
	}
	
	@Override
	public Integer getTestCollectionPanelCount(Long testCollectionId) {
		return (testCollectionDao.countTestCollectionPanels(testCollectionId));
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestCollection> getFixedAssignTestCollections(String gradeCourseAbbrName, Long contentAreaId,List<Long> excludeTestCollections, 
			Long operationalTestWindowId) {
		if (CollectionUtils.isEmpty(excludeTestCollections))
			excludeTestCollections = null;
		return testCollectionDao.selectTestCollectionsForDLMFixedAssign(gradeCourseAbbrName, contentAreaId, excludeTestCollections,
				operationalTestWindowId);		
	}

	@Override
	public List<TestCollection> getTestCollectionForKELPABatchAuto(String assessmentProgramCode, Long assessmentId,
			Long gradeCourseId, Long testTypeId, Long contentAreaId, String randomizationTypeCode,
			String contentAreaCode, String assessmentCode, Long operationalTestWindowId) {
		//First check grade band 
		List<TestCollection> testCollections = testCollectionDao.getTestCollectionsForBatchAutoGradeBand(assessmentProgramCode,randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode,
				operationalTestWindowId);
		
		if(CollectionUtils.isNotEmpty(testCollections)) {
			return testCollections;
		}
		//Get based on grade course
		testCollections = testCollectionDao.getTestCollectionsForBatchAutoGradeCourse(assessmentProgramCode, randomizationTypeCode, assessmentId,
				testStatusConfiguration.getPublishedTestStatusCategory().getId(), gradeCourseId, testTypeId, contentAreaId, contentAreaCode, assessmentCode,
				operationalTestWindowId);
		
		return testCollections;
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getNumberOfStudentsMetITIBPCrietria(Long criteria, Long contentAreaId, String gradeCourseAbbrName,
			List<ITIBPCoverageRosteredStudentsDTO> rosteredStudentsDetailsList, int schoolYear) {
		int numberOfStudentsMetCriteria = 0;		
		for(ITIBPCoverageRosteredStudentsDTO itiBPRosteredStudentDTO : rosteredStudentsDetailsList) {
			if(isStudentMetCriteria(criteria, contentAreaId, gradeCourseAbbrName, itiBPRosteredStudentDTO.getStudentId(), 
					schoolYear)) {
				numberOfStudentsMetCriteria++;
			}
		}
		return numberOfStudentsMetCriteria;
	}
	
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private boolean isStudentMetCriteria(Long criteria, Long contentAreaId, String gradeCourseAbbrName, 
			Long studentId, int schoolYear) {		
		Map<Long, BPCriteriaAndGroups> bpCriteriasMap = new HashMap<Long, BPCriteriaAndGroups>();
		List<BPCriteriaAndGroups> bpCriterias = getCriteriaAndGroupsBySubGradeAndCriteria(criteria, contentAreaId, gradeCourseAbbrName);
		for(BPCriteriaAndGroups bpcriteria: bpCriterias) {
			for(BPGroupsInfo bpGroupInfo : bpcriteria.getGrouspInfos()) {
				bpGroupInfo.resetNumberOfITIEEsCompleted();
			}
			if(!bpCriteriasMap.containsKey(bpcriteria.getCriteriaNum())) {
				bpCriteriasMap.put(bpcriteria.getCriteriaNum(), bpcriteria);
			}
		}
		List<ContentFrameworkDetail> listOfEEsStudentCompleted = testCollectionDao.getStudentITITestsForSubGradeAndCriteria(criteria, gradeCourseAbbrName, contentAreaId, studentId, schoolYear);
		for(ContentFrameworkDetail ee:listOfEEsStudentCompleted) {
			List<BPGroupsInfo> bpGroups = bpCriteriasMap.get(ee.getCriteriaNumber()).getGrouspInfos();
			for(Long groupNum : ee.getGroupsNumbers()) {
				for(BPGroupsInfo bpg :bpGroups) {
					if(bpg.getGroupNumber().equals(groupNum)) {
						bpg.incrementNumberOfITIEEsCompleted();
					}
				}
			}
		}				
		
		if(bpCriteriasMap.get(criteria).isCriteriaRequirmentMetForITI()) {
			return true;
		}
		
		return false;
	}
	
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private List<BPCriteriaAndGroups> getCriteriaAndGroupsBySubGradeAndCriteria(
			Long criteria, Long contentAreaId, String gradeCourseAbbrName) {
		return bluePrintMapper.getBluePrintCriteriasByGradeAndSubAndCriteria(contentAreaId, gradeCourseAbbrName, criteria);
	}

	@Override
	public String getStudentCriteriaStatus(Long criteria, Long contentAreaId,
			String gradeCourseAbbrName, Long studentId, int schoolYear) {
		Map<Long, BPCriteriaAndGroups> bpCriteriasMap = new HashMap<Long, BPCriteriaAndGroups>();
		List<BPCriteriaAndGroups> bpCriterias = getCriteriaAndGroupsBySubGradeAndCriteria(criteria, contentAreaId, gradeCourseAbbrName);
		for(BPCriteriaAndGroups bpcriteria: bpCriterias) {
			for(BPGroupsInfo bpGroupInfo : bpcriteria.getGrouspInfos()) {
				bpGroupInfo.resetNumberOfITIEEsCompleted();
			}
			if(!bpCriteriasMap.containsKey(bpcriteria.getCriteriaNum())) {
				bpCriteriasMap.put(bpcriteria.getCriteriaNum(), bpcriteria);
			}
		}
		List<ContentFrameworkDetail> listOfEEsStudentCompleted = testCollectionDao.getStudentITITestsForSubGradeAndCriteria(criteria, gradeCourseAbbrName, contentAreaId, studentId, schoolYear);
		for(ContentFrameworkDetail ee:listOfEEsStudentCompleted) {
			List<BPGroupsInfo> bpGroups = bpCriteriasMap.get(ee.getCriteriaNumber()).getGrouspInfos();
			for(Long groupNum : ee.getGroupsNumbers()) {
				for(BPGroupsInfo bpg :bpGroups) {
					if(bpg.getGroupNumber().equals(groupNum)) {
						bpg.incrementNumberOfITIEEsCompleted();
					}
				}
			}
		}
		
		if(bpCriteriasMap.get(criteria).isCriteriaRequirmentMetForITI()) {
			return "complete";
		}
		
		return CollectionUtils.isNotEmpty(listOfEEsStudentCompleted) ? "partial" : "none";
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestCollection> getDLMResearchSurveyTestCollections(
			Long operationalTestWindowId, String gradeAbbrName, Long contentAreaId, List<String> stageCodes) {
		return testCollectionDao.selectTestCollectionsForDLMResearchSurvey(operationalTestWindowId, 
				gradeAbbrName, contentAreaId, stageCodes);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Map<String, String>> getActualAndMappedLikageLevelsByContentArea(String contentAreaAbbrName) {		
		return testCollectionDao.getActualAndMappedLikageLevelsByContentArea(contentAreaAbbrName);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentFrameworkDetail> getContentCodesForFieldTests(Long multiAssignTestWindowId, String orgPoolType,
			String gradeCode, Long contentAreaId) {
		return testCollectionDao.getContentCodesForFieldTests(multiAssignTestWindowId, orgPoolType, gradeCode, contentAreaId);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long getStageIdByStageCode(String stageCode) {
		return testCollectionDao.getStageIdByStageCode(stageCode);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestCollection> getTestCollectionsForISmartBatchAuto(Float linkageLevelLowerBound, Float linkageLevelUpperBound, String gradeCourseCode, Long contentAreaId, Long operationalWindowId) {
		//check by gradeband
		List<TestCollection> testCollections = testCollectionDao.getTestCollectionsForISmartAutoGradeBand(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, operationalWindowId);
		
		if (CollectionUtils.isEmpty(testCollections)){//check by gradecourse
			testCollections = testCollectionDao.getTestCollectionsForISmartAutoGradeCourse(linkageLevelLowerBound, linkageLevelUpperBound, gradeCourseCode, contentAreaId, operationalWindowId);
		}
		return testCollections;
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestCollection> getTestCollectionsForBatchAutoByGradeBandOTWId(Long assessmentProgramId,
			Long contentAreaId, Long gradeBandId, Long operationalTestWindowId, String randomizationTypeCode,
			Long testStatusId, String stageCode) {
		
		return testCollectionDao.getTestCollectionsForBatchAutoByGradeBandOTWId(
				assessmentProgramId, contentAreaId, gradeBandId, operationalTestWindowId, randomizationTypeCode, testStatusId, stageCode);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Stage getStageByCode(String stageCode) {
		return testCollectionDao.getStageByCode(stageCode);
	}
}
