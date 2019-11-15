/**
 * 
 */
package edu.ku.cete.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.StudentTestInfo;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.TestSessionExample;
import edu.ku.cete.domain.UserSecurityAgreement;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.testsession.AutoRegisteredTestSessionDTO;
import edu.ku.cete.domain.testsession.TestSessionRoster;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.DailyAccessCodeMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TestSessionDao;
import edu.ku.cete.model.UserModuleDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.tde.webservice.client.TDEWebClient;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.web.AssignScorerTestSessionDTO;
import edu.ku.cete.web.KELPATestAdministrationDTO;
import edu.ku.cete.web.ScorerTestSessionStudentDTO;
import edu.ku.cete.web.StudentTestDTO;
import edu.ku.cete.web.StudentTestSectionsDTO;
import edu.ku.cete.web.StudentTestSessionInfoDTO;
import edu.ku.cete.web.TestAdminPartDetails;
import edu.ku.cete.web.TestFormMediaResourceDTO;
import edu.ku.cete.web.TestSessionPdfDTO;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestSessionServiceImpl implements TestSessionService {

    private static final int MAX_NUM_TEST_PARTS_ADMIN = 1;
    private static final int MAX_NUM_TEST_PARTS_TICKETS = 4;
    private static final NullComparator NULL_COMPARATOR = new NullComparator(false);
    public static final String GRADE_CHANGE_INACTIVATED_PREFIX = "gradechangeinactivated-";

	/**
     * Code value for the category table to identify the records that represents the closed test status.
     */
    @Value("${testsession.status.closed}")
    private String closedCategoryCode;

    @Value("${testsession.status.fcunenrolled}")
    private String fcUnenrolledCategoryCode;
    
    @Value("${testsession.status.fcmidunenrolled}")
    private String fcMidUnenrolledCategoryCode;
    
    @Value("${testsession.status.pnpunenrolled}")
    private String pnpUnenrolledCategoryCode;
    
    @Value("${session.name.date.format}")
    private String unenrollTimestamp;
   
    /**
     * Code value for the category type table that represents the test session status type.
     */
    @Value("${testsession.status.type}")
    private String testStatusType;

    @Value("${testsession.status.closed}")
    private String completeStatusType;
    
    @Value("${error.agreement.not.signed}")
    private String errorAgreementNotSigned;
    
    @Value("${error.pdtraining.not.completed}")
    private String errorPdtrainingNotCompleted;
    
    @Value("${error.agreement.and.pdtraining.not.completed}")
    private String errorAgreementAndPdtrainingNotCompleted;
    
    @Value("${testsession.status.gradechangeinactivated}")
    private String gradeChangeInactivatedPrefix;
    
    /**
     * The Data Access Object for testSession.
     */
    @Autowired
    private TestSessionDao testSessionDao;
    
	@Autowired
	private UserModuleDao userModuleDao;

    /**
     * Web client for connecting to TDE web services.
     */
    @Autowired
    private TDEWebClient webClient;

    /**
     * Category service used for finding the category that represents a closed test session.
     */
    @Autowired
    private CategoryService categoryService;
    
	@Autowired
	private OrganizationService organizationService;
	
	/**
	 * enrollmentDao
	 */
	@Autowired
    private EnrollmentDao enrollmentDao;
	/**
	 * permissionUtil
	 */
	@Autowired
	private PermissionUtil permissionUtil;
	
	@Autowired
	private UserService userService;	
    
	@Autowired
	private StudentsTestsDao studentsTestsDao;
    
	@Autowired
    private DailyAccessCodeMapper dacDao;
	
	@Autowired
	private DataExtractService dataExtractService;
	
	@Autowired
	private AssessmentProgramDao assessmentProgramDao;
	
	/* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#countByExample(edu.ku.cete.domain.TestSessionExample)
     */
    @Override
    public final int countByExample(TestSessionExample example) {
        return testSessionDao.countByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#deleteByExample(edu.ku.cete.domain.TestSessionExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int deleteByExample(TestSessionExample example) {
        return testSessionDao.deleteByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#insert(edu.ku.cete.domain.TestSession)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestSession insert(TestSession record) throws DuplicateTestSessionNameException {
    	Category closedSession = categoryService.selectByCategoryCodeAndType(completeStatusType,testStatusType);
    	TestSessionExample example = new TestSessionExample();
        TestSessionExample.Criteria criteria = example.createCriteria();
        criteria.andRosterIdEqualTo(record.getRosterId());
        criteria.andNameILike(record.getName());
        criteria.andStatusIdNotEqualTo(closedSession.getId());
        criteria.andActiveFlagEqualTo(true);
        List<TestSession> sessions = testSessionDao.selectByExample(example);
        
        if (sessions.size() == 0) {
            testSessionDao.insert(record);
            return record;
        } else {
            throw new DuplicateTestSessionNameException();
        }
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#insertSelective(edu.ku.cete.domain.TestSession)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int insertSelective(TestSession record) {
        return testSessionDao.insertSelective(record);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#selectByExample(edu.ku.cete.domain.TestSessionExample)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestSession> selectByExample(TestSessionExample example) {
        return testSessionDao.selectByExample(example);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestSession> getAutoRegisteredSessions(Long assessmentId, Long testTypeId,
    		Long contentAreaId, Long gradeCourseId, Long attendanceSchoolId,
    		Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId) {
        return getAutoRegisteredSessions(assessmentId, testTypeId,contentAreaId,
        		gradeCourseId,attendanceSchoolId,currentSchoolYear, stageId, sourceType, operationalTestWindowId, null);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestSession> getAutoRegisteredSessions(Long assessmentId, Long testTypeId,
    		Long contentAreaId, Long gradeCourseId, Long attendanceSchoolId,
    		Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId, String testSessionPrefix) {
    	return testSessionDao.findByAutoRegistrationCriteria(assessmentId, testTypeId,contentAreaId,
        		gradeCourseId,attendanceSchoolId,currentSchoolYear, stageId, sourceType.getCode(), operationalTestWindowId, testSessionPrefix);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final String getPredecessorStageStatus(Long assessmentId, Long testTypeId,
    		Long contentAreaId, Long gradeCourseId, Long attendanceSchoolId,Long currentSchoolYear, Long stageId, Long studentId, Long operationalTestWindowId) {
		return testSessionDao.findPredecessorStageStatus(assessmentId, testTypeId,contentAreaId,
        		gradeCourseId,attendanceSchoolId,currentSchoolYear, stageId, studentId, operationalTestWindowId);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestSession> selectByExampleAndCategory(Long rosterId,Long categoryId) {
        return testSessionDao.selectByExampleAndCategory(rosterId, categoryId);
    }
    
    @Override
	public final List<TestSession> selectByRosterAndCategory(Long rosterId, Long... categoryIds) {
		if (categoryIds != null && categoryIds.length > 0) {
			return testSessionDao.selectByRosterAndCategory(rosterId, categoryIds);
		}
		return new ArrayList<TestSession>();
	}
    

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#updateByExampleSelective(edu.ku.cete.domain.TestSession,
     *  edu.ku.cete.domain.TestSessionExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExampleSelective(TestSession record, TestSessionExample example) {
        return testSessionDao.updateByExampleSelective(record, example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#updateByExample(edu.ku.cete.domain.TestSession,
     *  edu.ku.cete.domain.TestSessionExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExample(TestSession record, TestSessionExample example) {
        return testSessionDao.updateByExample(record, example);
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#removeEmptySessions(long)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void removeEmptySessions(long rosterId) {
        testSessionDao.removeEmptySessions(rosterId);
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#cancelTestSession(long)
     */
    @Override
    @Transactional
    public final boolean cancelTestSession(long testSessionId, User user) {
        // Get the TestSession object from the database, set it's status to closed.
        TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);

        //Set the TestSession's status to closed.
        Category category = categoryService.selectByCategoryCodeAndType(closedCategoryCode, testStatusType);
        testSession.setStatus(category.getId());
        testSession.setStatusCategory(category);
        testSession.setModifiedDate(new Date());
        testSession.setModifiedUser(user.getId());
        testSession.setActiveFlag(false);

        //Commented for US14114 Technical: Fix Test Monitor and other TDE services dependency
        /*// make a call to the TDE web services, telling it which test session needs to be canceled.
        boolean successful = webClient.updateStudentsTestsStatus(testSession);

        // when the call returns, update the test session in AART to show that it has been ended.
        if (successful) {
            TestSessionExample example = new TestSessionExample();
            TestSessionExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(testSession.getId());
            
            testSession.setModifiedDate(new Date());
            testSession.setModifiedUser(user.getId());
            testSession.setActiveFlag(true);//TODO is this correct or do we inactivate it???
            
            testSessionDao.updateByExampleSelective(testSession, example);
        }*/

        boolean successful = false;
        TestSessionExample example = new TestSessionExample();
        TestSessionExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(testSession.getId());

        testSessionDao.updateByExampleSelective(testSession, example);
        successful = true;
        
        return successful;
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#findByPrimaryKey(long)
     */
    @Override    
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestSession findByPrimaryKey(@Param("testSessionId") long testSessionId) {
        return testSessionDao.findByPrimaryKey(testSessionId);
    }
    
    @Override    
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestSession findByPrimaryKeyWithStage(@Param("testSessionId") Long testSessionId) {
        return testSessionDao.findByPrimaryKeyStage(testSessionId);
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#findWithAssociationsByPrimaryKey(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestSession findWithAssociationsByPrimaryKey(long testSessionId) {
        return testSessionDao.findWithAssociationsByPrimaryKey(testSessionId);
    }
      

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestSessionService#findTestSessionTicketDetailsById(long)
     */
    @Override    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)  
    public List<TestSessionPdfDTO> findTestSessionTicketDetailsById(
    		List<Long> testSessionIds, Boolean isAutoRegistered
    		, List<Long> studentIds, UserDetailImpl userDetails) {
    	List<TestSessionPdfDTO> tempTestSessionDtoList = new ArrayList<TestSessionPdfDTO>();
    	List<TestSessionPdfDTO> testSessionDtoList = new ArrayList<TestSessionPdfDTO>();
    	Long organizationId = userDetails.getUser().getCurrentOrganizationId();
    	if(isAutoRegistered == null || !isAutoRegistered) {
			tempTestSessionDtoList = testSessionDao.findTestSessionTicketDetailsById(testSessionIds,
					userDetails.getUserId(), userDetails.getUser().isTeacher(), organizationId);
    	} else if(isAutoRegistered) {
    		tempTestSessionDtoList = testSessionDao.findAutoRegisteredTestSessionTicketsById(testSessionIds, studentIds, organizationId);
    	}
    	HashSet<Long> studentTestId = new HashSet<Long>();

    	Long currentStudentTestId = null;
    	int currentSectionOrder = 0;
    	int currentIndexOrder = 0;
    	boolean isB4SectionHardBreak = false;
    	TestSessionPdfDTO currentTestSessionPdfDTO = null;
		if (!tempTestSessionDtoList.isEmpty() && tempTestSessionDtoList.size() > 0) {
			for (TestSessionPdfDTO testSessionPdfDTO : tempTestSessionDtoList) {
				if (testSessionPdfDTO.getTestSectionTicketNumber() == null) {
					if (!studentTestId.contains(testSessionPdfDTO.getStudentTestId())) {
						studentTestId.add(testSessionPdfDTO.getStudentTestId());
						if (testSessionPdfDTO.getTestSectionCount() > 1) {
							testSessionPdfDTO.setTestSectionName("Section 1-"
									+ testSessionPdfDTO.getTestSectionCount());
						} else {
							// INFO: if it has no sections then it will be an invalid test.
							testSessionPdfDTO.setTestSectionName("Section 1");
						}
						testSessionDtoList.add(testSessionPdfDTO);
					}
				} else {
					if (currentStudentTestId == null) {
						currentTestSessionPdfDTO = testSessionPdfDTO;
						currentSectionOrder = testSessionPdfDTO.getSectionOrder();
						currentIndexOrder = testSessionPdfDTO.getSectionOrder();
						currentStudentTestId = testSessionPdfDTO.getStudentTestId();
						isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
					} else {
						if(currentStudentTestId.equals(testSessionPdfDTO.getStudentTestId())) {
							if(isB4SectionHardBreak){//hardbreak
								if(currentSectionOrder - currentIndexOrder > 0){
									currentTestSessionPdfDTO.setTestSectionName("Sections " + currentTestSessionPdfDTO.getSectionOrder() + "-" + currentSectionOrder);
								}
								testSessionDtoList.add(currentTestSessionPdfDTO);
								currentTestSessionPdfDTO = testSessionPdfDTO;
								currentIndexOrder = testSessionPdfDTO.getSectionOrder();
								currentSectionOrder = testSessionPdfDTO.getSectionOrder();
								isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
							} else {
								currentSectionOrder = testSessionPdfDTO.getSectionOrder();
								isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
							}
						} else {
							if(currentSectionOrder - currentIndexOrder > 0){
								currentTestSessionPdfDTO.setTestSectionName("Sections " + currentTestSessionPdfDTO.getSectionOrder() + "-" + currentSectionOrder);
							}				
							testSessionDtoList.add(currentTestSessionPdfDTO);
							currentTestSessionPdfDTO = testSessionPdfDTO;
							currentSectionOrder = testSessionPdfDTO.getSectionOrder();
							currentIndexOrder = testSessionPdfDTO.getSectionOrder();
							currentStudentTestId = testSessionPdfDTO.getStudentTestId();
							isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
						}
					}
				}
			}
			
			if(currentTestSessionPdfDTO != null) {
				if(currentSectionOrder - currentIndexOrder > 0){
					currentTestSessionPdfDTO.setTestSectionName("Sections " + currentTestSessionPdfDTO.getSectionOrder() + "-" + currentSectionOrder);
				}
				testSessionDtoList.add(currentTestSessionPdfDTO);
			}
		}
   	 return testSessionDtoList;
    }

	/* (non-Javadoc)
	 * @see edu.ku.cete.service.EnrollmentService#getEnrollmentWithRoster(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, boolean, java.lang.String, int, int, java.util.Map)
	 */
    /**
	 * Bishnupriya Nayak :US19343 : Test Management TestSession Completion Status Display
	  */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestSessionRoster> getTestSessionWithRoster(
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String sortByColumn, String sortType, int offset,
    		int limitCount,	Map<String,String> studentRosterCriteriaMap, Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId, Boolean hasViewHighStakesTestSessionsPermission, Boolean qcComplete,
			Long assessmentProgramId, Long testingProgramId, Long contentAreaId,
			Long gradeCourseId, Long schoolOrgId, Boolean showExpiredFlag, Boolean hasQCCompletePermission, Boolean includeCompletedTestSessionsFlag, Boolean includeInProgressTestSession) {
		
		
		AssessmentProgram asp = assessmentProgramDao.findByAssessmentProgramId(assessmentProgramId);
		String assessmentProgramAbbrName = StringUtils.EMPTY;
		if(asp != null) {
			assessmentProgramAbbrName = asp.getAbbreviatedname();
		}
		List<TestSessionRoster> testSessionRosters = testSessionDao.getTestSessionWithRoster(
				userDetails.getUser().getCurrentOrganizationId(),
				userDetails.getUser().getContractingOrganization().getCurrentSchoolYear(),
				userDetails.getUserId(), hasViewAllPermission, sortByColumn, sortType, offset,
				limitCount, studentRosterCriteriaMap, 
				systemEnrollmentRuleId, manualEnrollmentRuleId,
				hasViewHighStakesTestSessionsPermission, qcComplete,assessmentProgramId, assessmentProgramAbbrName,
				testingProgramId, contentAreaId, gradeCourseId, schoolOrgId, 
				SourceTypeEnum.ITI.getCode(), showExpiredFlag, hasQCCompletePermission, includeCompletedTestSessionsFlag, includeInProgressTestSession);
		/*
		// determine deletableflag for each testSessionId-- performance improvement
		if(CollectionUtils.isNotEmpty(testSessionRosters)) {
			Set<Long> testSessionIds = new HashSet<Long>();
			for (TestSessionRoster testSessionRoster : testSessionRosters) {
				testSessionIds.add(testSessionRoster.getTestSession().getId());
			}
			
			//retrieve result now
			Map<Long, Map<String,String>> tsDelFlags = testSessionDao.findTestSessionDelFlags(testSessionIds);
			if(tsDelFlags != null) {
				for(TestSessionRoster testSessionRoster : testSessionRosters) {
					if(tsDelFlags.get(testSessionRoster.getTestSession().getId()) != null) {
						testSessionRoster.setTestSessionDeletableFlag(tsDelFlags.get(testSessionRoster.getTestSession().getId()).get("testsessiondeletableflag"));
					}
				}
			}			
		}*/
		
	    return testSessionRosters;
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.EnrollmentService#getEnrollmentWithRoster(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, boolean, java.lang.String, int, int, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int countTestSessionWithRoster(
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		Map<String,String> studentRosterCriteriaMap, Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId, Boolean hasViewHighStakesTestSessionsPermission, Boolean qcComplete,
    		Long assessmentProgramId, Long testingProgramId, Long contentAreaId, 
    		Long gradeCourseId, Long schoolOrgId, Boolean showExpiredFlag, Boolean hasQCCompletePermission) {
		return testSessionDao.countTestSessionWithRoster(
				userDetails.getUser().getCurrentOrganizationId(),
				userDetails.getUser().getContractingOrganization().getCurrentSchoolYear(),
				userDetails.getUserId(),
				hasViewAllPermission,
				studentRosterCriteriaMap, systemEnrollmentRuleId, manualEnrollmentRuleId,
				hasViewHighStakesTestSessionsPermission, qcComplete,assessmentProgramId, 
				testingProgramId, contentAreaId, gradeCourseId, schoolOrgId, 
				SourceTypeEnum.ITI.getCode(), showExpiredFlag, hasQCCompletePermission);
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestSessionRoster> getQCTestSessionWithRoster(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String orderByClause, int offset,
    		int limitCount,	Map<String,String> studentRosterCriteriaMap, Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId, 
    		Boolean hasHighStakesPermission) {
		List<TestSessionRoster> testSessionRosters = testSessionDao.getQCTestSessionWithRoster(
	    		attendanceSchoolIds,
				userOrganizationIds,
				userDetails.getUserId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				orderByClause, offset,
				limitCount, studentRosterCriteriaMap, systemEnrollmentRuleId, manualEnrollmentRuleId, hasHighStakesPermission);
	    return testSessionRosters;
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int countQCTestSessionWithRoster(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		Map<String,String> studentRosterCriteriaMap, Long systemEnrollmentRuleId,
    		Long manualEnrollmentRuleId,
    		Boolean hasHighStakesPermission) {
		return testSessionDao.countQCTestSessionWithRoster(
	    		attendanceSchoolIds,
				userOrganizationIds,
				userDetails.getUserId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				studentRosterCriteriaMap, systemEnrollmentRuleId, manualEnrollmentRuleId, hasHighStakesPermission);
	}

	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestSession insertTestSessionForAutoRegistration(TestSession record) throws DuplicateTestSessionNameException {
		
		testSessionDao.insert(record);
        return record;
        
/*        TestSessionExample example = new TestSessionExample();
        TestSessionExample.Criteria criteria = example.createCriteria();
        //criteria.andRosterIdEqualTo(record.getRosterId());
        criteria.andNameILike(record.getName());

        List<TestSession> sessions = testSessionDao.selectByExample(example);

        if (sessions.size() == 0) {
            testSessionDao.insert(record);
            return record;
        } else {
            throw new DuplicateTestSessionNameException();
        }*/
    }

	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.TestSessionService#getAutoRegisteredTestSessions(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, java.lang.String, int, int, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AutoRegisteredTestSessionDTO> getAutoRegisteredTestSessions(
    		UserDetailImpl userDetails, Long currentSchoolYear, String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, Long contentAreaId, 
    		Long gradeCourseId, Long schoolOrgId, Boolean showExpired) {
    		 
		List<AutoRegisteredTestSessionDTO> testSessionRosters = testSessionDao.getAutoRegisteredTestSessions(
				userDetails.getUser().getCurrentOrganizationId(), currentSchoolYear,
				sortByColumn, sortType, offset, limitCount, studentRosterCriteriaMap, 
				assessmentProgramId, assessmentProgramAbbr, testingProgramId, contentAreaId,
				gradeCourseId, schoolOrgId,	showExpired);
		
	    return testSessionRosters;
	}
	
	@Override
	public String getAutoPrintTestFiles(Long testsessionId) {
		List<Long> sessionIds = new ArrayList<Long>();
			sessionIds.add(testsessionId);
		Map<Long, Map<String,String>> printFiles = testSessionDao.findAutoPrintFiles(sessionIds);
		if(printFiles !=null && printFiles.get(testsessionId) != null) {
			return printFiles.get(testsessionId).get("printtestfiles");
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.TestSessionService#countAutoRegisteredTestSessions(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer countAutoRegisteredTestSessions(
    		UserDetailImpl userDetails, Long currentSchoolYear, Map<String,String> studentRosterCriteriaMap, 
    		Long assessmentProgramId, Long testingProgramId, Long contentAreaId, 
    		Long gradeCourseId, Long schoolOrgId, Boolean showExpired) {
		return testSessionDao.countAutoRegisteredTestSessions(
				userDetails.getUser().getCurrentOrganizationId(), currentSchoolYear, 
				studentRosterCriteriaMap, assessmentProgramId, testingProgramId, 
				contentAreaId, gradeCourseId, schoolOrgId, showExpired);
	}

	/**
	 * @param testSessionName
	 * @param studentId
	 * @return
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Long> selectTestSessionByStudentId(String testSessionName, Long studentId) {
		return testSessionDao.selectTestSessionByStudentId(testSessionName, studentId);
	}

	@Override
	public List<AutoRegisteredTestSessionDTO> getAutoregisteredTestSessionForActiveRosters(
			UserDetailImpl userDetails, String sortByColumn, String sortType, Integer offset,
			Integer limitCount, Map<String, String> studentRosterCriteriaMap,
			Long userId, Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long contentAreaId, Long gradeCourseId, Long schoolOrgId, Boolean showExpired) {
		List<AutoRegisteredTestSessionDTO> testSessionRosters = testSessionDao.getAutoregisteredTestSessionForActiveRosters(
				userDetails.getUser().getCurrentOrganizationId(),
				sortByColumn, sortType, offset, limitCount, studentRosterCriteriaMap,userId,
				assessmentProgramId, assessmentProgramAbbr, testingProgramId, contentAreaId, gradeCourseId, schoolOrgId, showExpired);

	    return testSessionRosters;
	}
	
	@Override
	public int countAutoregisteredTestSessionForActiveRosters(
			UserDetailImpl userDetails,
			Map<String, String> studentRosterCriteriaMap, Long userId,
			Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long contentAreaId, Long gradeCourseId, Long schoolOrgId, Boolean showExpired) {
		return testSessionDao.countAutoregisteredTestSessionForActiveRosters(
				userDetails.getUser().getCurrentOrganizationId(),
				studentRosterCriteriaMap,userId,assessmentProgramId, assessmentProgramAbbr,
				testingProgramId, contentAreaId, gradeCourseId, schoolOrgId, showExpired);
	}
	
	@Override
	public List<TestSession> selectForStudentGradeSubjectAndPartialName(Long studentId, String gradeCourseCode,	String contentAreaCode,	String name, Integer limit){
		return testSessionDao.selectForStudentGradeSubjectAndPartialName(studentId, gradeCourseCode, contentAreaCode, name, limit);
	}
	
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean isTestSessionDeletable(Long testSessionId) {

    	boolean isDeletable = true;
		// determine deletableflag for each testSessionId-- performance improvement
    	if(!isPracticeTest(testSessionId)){
		if(testSessionId != null && testSessionId > 0) {			
			//retrieve result now
			boolean tsDelFlag = testSessionDao.findTestSessionDelFlag(testSessionId);
			isDeletable = tsDelFlag;
		}
    	}else{
    		isDeletable=true;
    	}
		
		return isDeletable;
    }
    
    private boolean isPracticeTest(Long testSessionId) {
    	List<String> testingProgramNames=testSessionDao.getTestingProgramNames(testSessionId);
    	boolean isPractiseTest=false;
    	for(String testingProgramName:testingProgramNames)
    	{
    		if(testingProgramName.equalsIgnoreCase("Practice"))
    		{
    			isPractiseTest=true;
    			break;
    		}
    	}
    	
		return isPractiseTest;
    	
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateTestSession(Long testSessionId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		testSessionDao.deactivateByPrimaryKey(testSessionId, userDetails.getUserId(), new Date());
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateWithStatus(List<Long> testSessionIds, String newStatusPrefix, Long modifiedUserId) {
    	for (Long testSessionId : testSessionIds) {
    		testSessionDao.deactivateByPrimaryKeyWithStatus(testSessionId, newStatusPrefix, modifiedUserId);
    	}
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateForGradeChange(List<Long> testSessionIds, Long modifiedUserId) {
    	deactivateWithStatus(testSessionIds, GRADE_CHANGE_INACTIVATED_PREFIX, modifiedUserId);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void reactivateForGradeChange(List<Long> testSessionIds, Long modifiedUserId) {
    	for (Long testSessionId : testSessionIds) {
    		testSessionDao.reactivateByPrimaryKeyForGradeChange(testSessionId, modifiedUserId);
    	}
    }    
    
    @Override
    @Transactional
    public final boolean fcUnenrollTestSession(long testSessionId, User user) {

        TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);

        Category category = categoryService.selectByCategoryCodeAndType(fcUnenrolledCategoryCode, testStatusType);
        testSession.setStatus(category.getId());
        testSession.setStatusCategory(category);
        testSession.setModifiedDate(new Date());
        testSession.setModifiedUser(user.getId());
        testSession.setActiveFlag(false);
        addTimestampToSessionName(testSession);

        TestSessionExample example = new TestSessionExample();
        TestSessionExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(testSession.getId());

        testSessionDao.updateByExampleSelective(testSession, example);
        
        return true;
    }
    
    @Override
    @Transactional
    public final boolean fcMidUnenrollTestSession(long testSessionId, User user) {

        TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);

        Category category = categoryService.selectByCategoryCodeAndType(fcMidUnenrolledCategoryCode, testStatusType);
        testSession.setStatus(category.getId());
        testSession.setStatusCategory(category);
        testSession.setModifiedDate(new Date());
        testSession.setModifiedUser(user.getId());
        testSession.setActiveFlag(false);
        addTimestampToSessionName(testSession);
        
        TestSessionExample example = new TestSessionExample();
        TestSessionExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(testSession.getId());

        testSessionDao.updateByExampleSelective(testSession, example);
        
        return true;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean pnpUnenrollTestSession(long testSessionId, User user) {
    	
    	List<StudentsTests> studentsTests = studentsTestsDao.findByTestSession(testSessionId);
    	if(CollectionUtils.isEmpty(studentsTests)){
    		 TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);
    	     Category category = categoryService.selectByCategoryCodeAndType(pnpUnenrolledCategoryCode, testStatusType);
    	     testSession.setStatus(category.getId());
    	     testSession.setStatusCategory(category);
    	     testSession.setModifiedDate(new Date());
    	     testSession.setModifiedUser(user.getId());
    	     testSession.setActiveFlag(false);
    	     addTimestampToSessionName(testSession);

    	     TestSessionExample example = new TestSessionExample();
    	     TestSessionExample.Criteria criteria = example.createCriteria();
    	     criteria.andIdEqualTo(testSession.getId());
    	     testSessionDao.updateByExampleSelective(testSession, example);
    	}
        return true;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean unenrollTestSession(long testSessionId, String statusCode, Long userId) {
    	List<StudentsTests> studentsTests = studentsTestsDao.findByTestSession(testSessionId);
    	if(CollectionUtils.isEmpty(studentsTests)) {
    		 TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);
    	     Category category = categoryService.selectByCategoryCodeAndType(statusCode, testStatusType);
    	     testSession.setStatus(category.getId());
    	     testSession.setStatusCategory(category);
    	     testSession.setModifiedDate(new Date());
    	     if(userId!=null) {
    	    	 testSession.setModifiedUser(userId);	 
    	     }
    	     testSession.setActiveFlag(false);
    	     addTimestampToSessionName(testSession);

    	     TestSessionExample example = new TestSessionExample();
    	     TestSessionExample.Criteria criteria = example.createCriteria();
    	     criteria.andIdEqualTo(testSession.getId());
    	     testSessionDao.updateByExampleSelective(testSession, example);
    	}
        return true;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean transferTestSessionToNewRoster(Long testSessionId, Long newRosterId, Long modifiedUserId, Long currentEnrollmentId){
    	
    	if(testSessionId!=null && newRosterId!=null){
    	
    		
    		Category testInProgressStatus = categoryService.selectByCategoryCodeAndType("inprogress", "STUDENT_TEST_STATUS");
    		Category testInProgressTimedOutStatus = categoryService.selectByCategoryCodeAndType("inprogresstimedout", "STUDENT_TEST_STATUS");
    		Category testPendingStatus = categoryService.selectByCategoryCodeAndType("pending", "STUDENT_TEST_STATUS");
    		Category testUnusedStatus = categoryService.selectByCategoryCodeAndType("unused", "STUDENT_TEST_STATUS");
    		Category testCompleteStatus = categoryService.selectByCategoryCodeAndType("complete", "STUDENT_TEST_STATUS");
    		Category testExitCompleteStatus = categoryService.selectByCategoryCodeAndType("exitclearunenrolled-complete", "STUDENT_TEST_STATUS");
    		Category testExitInProgressStatus = categoryService.selectByCategoryCodeAndType("exitclearunenrolled-inprogress", "STUDENT_TEST_STATUS");
    		Category testExitInProgressTimedOutStatus = categoryService.selectByCategoryCodeAndType("exitclearunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
    		Category testExitPendingStatus = categoryService.selectByCategoryCodeAndType("exitclearunenrolled-pending", "STUDENT_TEST_STATUS");
    		Category testExitUnusedStatus = categoryService.selectByCategoryCodeAndType("exitclearunenrolled-unused", "STUDENT_TEST_STATUS");
    		Category testUnrosteredCompleteStatus = categoryService.selectByCategoryCodeAndType("rosterunenrolled-complete", "STUDENT_TEST_STATUS");
    		Category testUnrosteredInProgressStatus = categoryService.selectByCategoryCodeAndType("rosterunenrolled-inprogress", "STUDENT_TEST_STATUS");
    		Category testUnrosteredInProgressTimedOutStatus = categoryService.selectByCategoryCodeAndType("rosterunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
    		Category testUnrosteredPendingStatus = categoryService.selectByCategoryCodeAndType("rosterunenrolled-pending", "STUDENT_TEST_STATUS");
    		Category testUnrosteredUnusedStatus = categoryService.selectByCategoryCodeAndType("rosterunenrolled-unused", "STUDENT_TEST_STATUS");
    		
    		 Enrollment newEnrollment = enrollmentDao.getEnrollmentById(currentEnrollmentId);
			 TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);
		     testSession.setModifiedDate(new Date());
		     if(modifiedUserId!=null) {
		    	 testSession.setModifiedUser(modifiedUserId);	 
		     }
		     testSession.setRosterId(newRosterId);
		     
		     ifExistsRemoveTimestampFromSessionName(testSession);
		     
		     if (newEnrollment != null){
		    	 Long attendanceSchoolId = newEnrollment.getAttendanceSchoolId();
		    	 if (testSession.getAttendanceSchoolId() != null && !testSession.getAttendanceSchoolId().equals(attendanceSchoolId)) {
		    		 testSession.setAttendanceSchoolId(attendanceSchoolId);
		    	 }
		     }
		     
		     if (testSession.getStatus().equals(testCompleteStatus.getId())) {
		    	 //leave in status and activate if inactive
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testExitCompleteStatus.getId())) {
		    	 testSession.setStatus(testCompleteStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testExitInProgressStatus.getId())) {
		    	 testSession.setStatus(testInProgressStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testExitInProgressTimedOutStatus.getId())) {
		    	 testSession.setStatus(testInProgressTimedOutStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testExitPendingStatus.getId())) {
		    	 testSession.setStatus(testPendingStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else	if (testSession.getStatus().equals(testExitUnusedStatus.getId())) {
		    	 testSession.setStatus(testUnusedStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testUnrosteredInProgressStatus.getId())) {
		    	 testSession.setStatus(testInProgressStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testUnrosteredInProgressTimedOutStatus.getId())) {
		    	 testSession.setStatus(testInProgressTimedOutStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testUnrosteredPendingStatus.getId())) {
		    	 testSession.setStatus(testPendingStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testUnrosteredUnusedStatus.getId())) {
		    	 testSession.setStatus(testUnusedStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } else if (testSession.getStatus().equals(testUnrosteredCompleteStatus.getId())) {
		    	 testSession.setStatus(testCompleteStatus.getId());
		    	 if (!testSession.getActiveFlag()) {
		    		 testSession.setActiveFlag(true);
		    	 }
		     } 
		     
		     
		     TestSessionExample example = new TestSessionExample();
		     TestSessionExample.Criteria criteria = example.createCriteria();
		     criteria.andIdEqualTo(testSession.getId());
		     testSessionDao.updateByExampleSelective(testSession, example);
		     
		     return true;
    	}
    	
		return false;   
    }
    
    private void addTimestampToSessionName(TestSession session){
    	SimpleDateFormat sdf = new SimpleDateFormat(unenrollTimestamp);
        String now = sdf.format(new Date());
        session.setName(session.getName()+"-"+now);
    }
    
    private void ifExistsRemoveTimestampFromSessionName(TestSession session){
    	String sessionName = session.getName();
    	String[] splitName = sessionName.split("-");
    	String lastSegment = splitName[splitName.length-1];
    	try{
    		Long.parseLong(lastSegment);
    		splitName[splitName.length-1] = "";
    		String newSessionName = sessionName.replace("-"+lastSegment, "");
    		session.setName(newSessionName);
    	}catch(NumberFormatException e){
    		//only those without the timestamp at the end will get to this block
    	}
    }
    
    /* (non-Javadoc)
     * Checking for user access to TestManagement tab
     * DLM user must complete the security agreement and PD training
     * ISMART/ISMART2 user must complete the security agreement and do not have to complete the DLM training
     * @see edu.ku.cete.service.TestSessionService#verifyTestManagementTabAccessCriteria(edu.ku.cete.domain.user.User)
     */
    @Override
    public final String verifyTestManagementTabAccessCriteria(User user) {
    	
    	String result="valid";
    	Organization contractingOrg = user.getContractingOrganization();
    	
		int currentSchoolYear = (int) (long) contractingOrg.getCurrentSchoolYear();
    	List<UserSecurityAgreement> agreements = userService.getDLMActiveUserSecurityAgreementInfo(user.getId(), user.getCurrentAssessmentProgramId(), (long) currentSchoolYear);
    	
    	if(CollectionUtils.isEmpty(agreements)) {
    		result = errorAgreementNotSigned;
    	}  
    	// verify user has completed PDTraining
    	if (user.getCurrentAssessmentProgramName() != null 
				&& user.getCurrentAssessmentProgramName().equalsIgnoreCase("DLM")) {
    		if(!userService.isPdTrainingCompleted(user.getId(), currentSchoolYear)) {	
        		if(result.equals(errorAgreementNotSigned))
        			result = errorAgreementAndPdtrainingNotCompleted;
        		else
        			result = errorPdtrainingNotCompleted;
        	}
    	}    	
		
		return result;
    }
    
    /**
     * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15738 : Test Coordination - enhance Test Information PDF functionality
     * Get media files associated to a test session.
     */
    @Override
	public Map<Long, Map<String,String>> getResourceByTestSessionId(List<Long> testsessionIds) {
		return testSessionDao.selectResourceByTestSessionId(testsessionIds);
	}
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<TestFormMediaResourceDTO> getTestFormMediaResource(String fromDate,
			String toDate, List<Long> assesmentPrograms, String qcStatus,String media){
    	List<TestFormMediaResourceDTO> testFormMediaResource = dataExtractService.getTestFormMediaResource(fromDate,
    			toDate, assesmentPrograms, qcStatus,media);
    
    	
    	return testFormMediaResource;
    }
    
    
    
    
    /**
     * 
     * @author Venkata Krishna Jagarlamudi
     * US15630: Data extract - Test administration for KAP and AMP
     * US15741: Data Extract - Test Tickets for KAP and AMP
     * Common for both extracts.
     * F606: split into two service methods
     */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentTestSessionInfoDTO> getTicketDetailsExtract(Long organionId,
			Long currentSchoolYear, boolean shouldOnlySeeRosteredStudents, Long userId, String assesmentProgramCode, int offset, int limit, List<Long> assessmentPrograms) {

		List<StudentTestSessionInfoDTO> studentTestInfoDTOs = dataExtractService.getStudentsTestsInfo(organionId, currentSchoolYear, 
				shouldOnlySeeRosteredStudents, userId, assesmentProgramCode, offset, limit, assessmentPrograms,false);
		
		List<Long> studentTestIds = new ArrayList<Long>();
		for (StudentTestSessionInfoDTO studentTestSessionInfoDTO : studentTestInfoDTOs) {
			studentTestIds.add(studentTestSessionInfoDTO.getStudentTestId());
		}
		
		Map<Long, List<StudentTestSectionsDTO>> studentsParts = new HashMap<Long, List<StudentTestSectionsDTO>>();
		List<StudentTestDTO> studentTestDTOs = new ArrayList<StudentTestDTO>();
		if(studentTestIds != null && studentTestIds.size() > 0) {
			studentTestDTOs = dataExtractService.getAllStudentsTestSectionDetails(studentTestIds);
		}
		if(studentTestDTOs != null && studentTestDTOs.size() > 0) {
			for(StudentTestDTO studentTestDTO :studentTestDTOs) {
				studentsParts.put(studentTestDTO.getStuTestId(), studentTestDTO.getStudentTestSectionDtos());
			}
		}
		
		if(CollectionUtils.isNotEmpty(studentTestInfoDTOs) && studentTestInfoDTOs.size() > 0) {			
			for(int studentTestInfoCount = 0;  studentTestInfoCount < studentTestInfoDTOs.size(); studentTestInfoCount++) {				
				List<StudentTestSectionsDTO> studentTestSectionsDtos = studentsParts.get(studentTestInfoDTOs.get(studentTestInfoCount).getStudentTestId());
				if(CollectionUtils.isNotEmpty(studentTestSectionsDtos) && studentTestSectionsDtos.size() > 0) {	
					
					//Sort by section order
					Collections.sort(studentTestSectionsDtos, new Comparator<StudentTestSectionsDTO>() {
						@Override
						public int compare(StudentTestSectionsDTO o1, StudentTestSectionsDTO o2) {
							return NULL_COMPARATOR.compare(o1.getSectionOrder(), o2.getSectionOrder());
						}
					});
					
					Map<Integer, List<StudentTestSectionsDTO>> parts = new HashMap<Integer, List<StudentTestSectionsDTO>>();
					int partNum = 1;//keep 1																
					for(int i=0; i<studentTestSectionsDtos.size();i++) {	
						 StudentTestSectionsDTO studentTestSection = studentTestSectionsDtos.get(i);
						 if(null == parts.get(partNum)) {
							 parts.put(partNum, new ArrayList<StudentTestSectionsDTO>());
						 }
						 parts.get(partNum).add(studentTestSection); 
						 if(studentTestSection.isHardBreak()) {
							 partNum++;
						 }							 
					}
					if(parts.size() > 0) {
						for(int partName = 1; partName <= parts.size(); partName++) {
							List<StudentTestSectionsDTO> studentTestSections = parts.get(partName);	
							List<String> partStatus = new ArrayList<String>();
							int numOfItemsInPart = 0;
							int numOfAnswredItemsInPart = 0;
							TestAdminPartDetails stuTestParts = new TestAdminPartDetails();
							stuTestParts.setStudentLoginTicket(studentTestSections.get(0).getTicketNumber());;
							stuTestParts.setStartDate(studentTestSections.get(0).getStuTestSectionStartDate());
							stuTestParts.setEndDate(studentTestSections.get(studentTestSections.size() - 1).getStuTestSectionEndDate());
							if(studentTestSections.size() > 1) {								
								stuTestParts.setTicketSections(studentTestSections.get(0).getSectionOrder() + "--" + studentTestSections.get(studentTestSections.size() - 1).getSectionOrder());
							} else {
								stuTestParts.setTicketSections(studentTestSections.get(0).getSectionOrder() + StringUtils.EMPTY);
							}
							for(StudentTestSectionsDTO stuTestScec : studentTestSections) {
								numOfItemsInPart =  numOfItemsInPart + stuTestScec.getNumofItems();
								numOfAnswredItemsInPart = (int) (numOfAnswredItemsInPart + stuTestScec.getNumOfItemsAnswered());
								partStatus.add(stuTestScec.getStuTestSectionStatus());
							}
							stuTestParts.setTotalItems(numOfItemsInPart);
							stuTestParts.setOmmitedItems(numOfItemsInPart - numOfAnswredItemsInPart);
							stuTestParts.setStatus(getCurrentPartStatus(partStatus));
							studentTestInfoDTOs.get(studentTestInfoCount).getPartsDetails().add(stuTestParts);
						}						
					}
					  
				}
				for(int testPartCount = 1; testPartCount <= MAX_NUM_TEST_PARTS_TICKETS; testPartCount++) {
					if(testPartCount > studentTestInfoDTOs.get(studentTestInfoCount).getPartsDetails().size()) {
						TestAdminPartDetails stuTestParts = new TestAdminPartDetails();
						stuTestParts.setStatus("N/A");
						studentTestInfoDTOs.get(studentTestInfoCount).getPartsDetails().add(stuTestParts);
					}
				}
				if(StringUtils.isNotEmpty(studentTestInfoDTOs.get(studentTestInfoCount).getTestSatus())) {
					studentTestInfoDTOs.get(studentTestInfoCount).
				     setTestSatus(getTestStatus(studentTestInfoDTOs.get(studentTestInfoCount).getTestSatus()));
				}				
			}		
		}
		return studentTestInfoDTOs;
	}

    /**
     * 
     * @author Venkata Krishna Jagarlamudi
     * US15630: Data extract - Test administration for KAP and AMP
     * US15741: Data Extract - Test Tickets for KAP and AMP
     * Common for both extracts.
     * F606: split into two service methods
     */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentTestSessionInfoDTO> getTestAdminExtract(Long organionId,
			Long currentSchoolYear, boolean shouldOnlySeeRosteredStudents, Long userId, String assesmentProgramCode, int offset, int limit, List<Long> assessmentPrograms,boolean isPltw) {

		List<StudentTestSessionInfoDTO> studentTestInfoDTOs = dataExtractService.getStudentsTestsInfo(organionId, currentSchoolYear, 
				shouldOnlySeeRosteredStudents, userId, assesmentProgramCode, offset, limit, assessmentPrograms , isPltw);
		
		List<Long> studentTestIds = new ArrayList<Long>();
		for (StudentTestSessionInfoDTO studentTestSessionInfoDTO : studentTestInfoDTOs) {
			studentTestIds.add(studentTestSessionInfoDTO.getStudentTestId());
		}
		
		Map<Long, List<StudentTestSectionsDTO>> studentsParts = new HashMap<Long, List<StudentTestSectionsDTO>>();
		List<StudentTestDTO> studentTestDTOs = new ArrayList<StudentTestDTO>();
		if(studentTestIds != null && studentTestIds.size() > 0) {
			studentTestDTOs = dataExtractService.getAllStudentsTestSectionDetails(studentTestIds);
		}
		if(studentTestDTOs != null && studentTestDTOs.size() > 0) {
			for(StudentTestDTO studentTestDTO :studentTestDTOs) {
				studentsParts.put(studentTestDTO.getStuTestId(), studentTestDTO.getStudentTestSectionDtos());
			}
		}
		
		if(CollectionUtils.isNotEmpty(studentTestInfoDTOs) && studentTestInfoDTOs.size() > 0) {			
			for(int studentTestInfoCount = 0;  studentTestInfoCount < studentTestInfoDTOs.size(); studentTestInfoCount++) {				
				List<StudentTestSectionsDTO> studentTestSectionsDtos = studentsParts.get(studentTestInfoDTOs.get(studentTestInfoCount).getStudentTestId());
				if(CollectionUtils.isNotEmpty(studentTestSectionsDtos) && studentTestSectionsDtos.size() > 0) {	
					
					//Sort by section order
					Collections.sort(studentTestSectionsDtos, new Comparator<StudentTestSectionsDTO>() {
						@Override
						public int compare(StudentTestSectionsDTO o1, StudentTestSectionsDTO o2) {
							return NULL_COMPARATOR.compare(o1.getSectionOrder(), o2.getSectionOrder());
						}
					});
					
					Map<Integer, List<StudentTestSectionsDTO>> parts = new HashMap<Integer, List<StudentTestSectionsDTO>>();
					int partNum = 1;//keep 1																
					for(int i=0; i<studentTestSectionsDtos.size();i++) {	
						 StudentTestSectionsDTO studentTestSection = studentTestSectionsDtos.get(i);
						 if(null == parts.get(partNum)) {
							 parts.put(partNum, new ArrayList<StudentTestSectionsDTO>());
						 }
						 parts.get(partNum).add(studentTestSection); 
						 if(studentTestSection.isHardBreak()) {
							 partNum++;
						 }							 
					}
					if(parts.size() > 0) {
						TestAdminPartDetails stuTestParts = new TestAdminPartDetails();
						for(int partName = 1; partName <= parts.size(); partName++) {
							List<StudentTestSectionsDTO> studentTestSections = parts.get(partName);	
							int numOfItemsInPart = 0;
							int numOfAnswredItemsInPart = 0;
							
							stuTestParts.setStartDate(studentTestSections.get(0).getStuTestSectionStartDate());
							stuTestParts.setEndDate(studentTestSections.get(studentTestSections.size() - 1).getStuTestSectionEndDate());
							String ticketSections = null;
							if(studentTestSections.size() > 1) {
								if (stuTestParts.getTicketSections() != null && !stuTestParts.getTicketSections().isEmpty()){
									ticketSections = stuTestParts.getTicketSections() + ", "+studentTestSections.get(0).getSectionOrder() + "--" + studentTestSections.get(studentTestSections.size() - 1).getSectionOrder();
								}else{
									ticketSections = studentTestSections.get(0).getSectionOrder() + "--" + studentTestSections.get(studentTestSections.size() - 1).getSectionOrder();
								}
							} else {
								if (stuTestParts.getTicketSections() != null && !stuTestParts.getTicketSections().isEmpty()){
									ticketSections = stuTestParts.getTicketSections() + ", "+studentTestSections.get(0).getSectionOrder() + StringUtils.EMPTY;
								}else{
									ticketSections = studentTestSections.get(0).getSectionOrder() + StringUtils.EMPTY;
								}
							}
							stuTestParts.setTicketSections(ticketSections);
							for(StudentTestSectionsDTO stuTestScec : studentTestSections) {
								numOfItemsInPart =  numOfItemsInPart + stuTestScec.getNumofItems();
								numOfAnswredItemsInPart = (int) (numOfAnswredItemsInPart + stuTestScec.getNumOfItemsAnswered());
							}
							stuTestParts.setTotalItems(stuTestParts.getTotalItems() + numOfItemsInPart);//sum across parts
							stuTestParts.setOmmitedItems(stuTestParts.getOmmitedItems() + (numOfItemsInPart - numOfAnswredItemsInPart));//sum across parts
						}	
						studentTestInfoDTOs.get(studentTestInfoCount).getPartsDetails().add(stuTestParts);
					}
					  
				}
				for(int testPartCount = 1; testPartCount <= MAX_NUM_TEST_PARTS_ADMIN; testPartCount++) {
					if(testPartCount > studentTestInfoDTOs.get(studentTestInfoCount).getPartsDetails().size()) {
						TestAdminPartDetails stuTestParts = new TestAdminPartDetails();
						stuTestParts.setStatus("N/A");
						studentTestInfoDTOs.get(studentTestInfoCount).getPartsDetails().add(stuTestParts);
					}
				}
				if(StringUtils.isNotEmpty(studentTestInfoDTOs.get(studentTestInfoCount).getTestSatus())) {
					studentTestInfoDTOs.get(studentTestInfoCount).
				     setTestSatus(getTestStatus(studentTestInfoDTOs.get(studentTestInfoCount).getTestSatus()));
				}				
			}		
		}
		return studentTestInfoDTOs;
	}	
	
	private String getCurrentPartStatus(List<String> currentPartStauses) {				
		if(CollectionUtils.isNotEmpty(currentPartStauses)) {
			List<String> completeStatus = new ArrayList<String>();
			List<String> unusedStatus = new ArrayList<String>();
			for(int statusCount= 0; statusCount < currentPartStauses.size(); statusCount++ ) {
				completeStatus.add("Complete");
				unusedStatus.add("Unused");
			}
			if(currentPartStauses.contains("In Progress") || currentPartStauses.contains("inprogress")) {
				return "In progress";
			} 
			else if(currentPartStauses.contains("In Progress Timed Out") || currentPartStauses.contains("inprogresstimedout")) {
				return "In Progress Timed Out";
			}
			else if(currentPartStauses.containsAll(completeStatus)) {
				return "Complete";
			} else if(currentPartStauses.containsAll(unusedStatus)) {
				return "Not started";
			} else {
				return "In progress";
			}
		}
		return StringUtils.EMPTY;
	}
	
	private String getTestStatus(String status) {
		if(status!=null){
			if(status.equalsIgnoreCase("Unused")) {
				return "Not Started";
			} 
			else if(status.equalsIgnoreCase("In Progress") || status.equalsIgnoreCase("inprogress")){
				return "In progress";
			} 
			else if(status.equalsIgnoreCase("In Progress Timed Out") || status.equalsIgnoreCase("inprogresstimedout")) {
				return "In Progress Timed Out";
			}
			else if(status.equalsIgnoreCase("Complete")) {
				return "Complete";
			}
			else if (status.equalsIgnoreCase("Pending")) {
				return "Pending";
			} 
			else if (status.equalsIgnoreCase("PROCESS_LCS_RESPONSES")) {
				return "Processing LCS Responses";
			}
		}
		else
			return StringUtils.EMPTY;
		return status;
	}
	
	/**
	 * @param source
	 * @param studentId
	 * @return
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Long> selectTestSessionByStudentIdAndSource(String source, Long studentId) {
		return testSessionDao.selectTestSessionByStudentIdAndSource(source, studentId);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<TestSession> selectTestSessionByStudentIdAndSourceWithActiveOTW(String source, Long studentId, String assessmentProgramAbbrName) {
		return testSessionDao.selectAllTestSessionsByStudentIdSource(source, studentId, assessmentProgramAbbrName);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Long> selectTestSessionByStudentIdAndSourceAndAssessmentProgramWithActiveOTW(String source,
			Long studentId, Long assessmentProgramId) {
		return testSessionDao.selectTestSessionByStudentIdAndSourceWithActiveOTW(source, studentId, assessmentProgramId);
	}
	
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AssignScorerTestSessionDTO> getTestSessionAndStudentCountForScorer(Long stateId, 
			Long districtId, Long schoolId,Long assessmentProgramId, String assessmentProgramCode, Long testingProgramId,Long contentAreaId,
			Long gradeId, String sortByColumn, String sortType,Integer i,Integer limitCount,
			Map<String,String> testsessionRecordCriteriaMap){
			
			return testSessionDao.getTestSessionAndStudentCountForScorer(stateId, districtId, 
					schoolId, assessmentProgramId, assessmentProgramCode, testingProgramId, contentAreaId,
					gradeId, sortByColumn, sortType, i, limitCount, testsessionRecordCriteriaMap);
	}
	
	
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getCountTestSessionAndStudentCountForScorer(Long stateId,
			Long districtId, Long schoolId,Long assessmentProgramId, String assessmentProgramCode,
			Long testingProgramId, Long contentAreaId,
			Long gradeId, String sortByColumn, String sortType,
			Map<String,String> testsessionRecordCriteriaMap){
		
			return testSessionDao.getCountTestSessionAndStudentCountForScorer(
						stateId,districtId, schoolId,
						assessmentProgramId, assessmentProgramCode, testingProgramId, contentAreaId, gradeId, 
						sortByColumn, sortType, testsessionRecordCriteriaMap);
	}
	
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScorerTestSessionStudentDTO> getTestSectionStudents(Long testSessionId,Long subjectId, Long schoolyear, Long gradeId){
	
		return testSessionDao.selectTestSectionStudents(testSessionId,subjectId,schoolyear,gradeId);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentTestInfo getPredecessorTestInfo(Long assessmentId, Long testTypeId,
    		Long contentAreaId, Long gradeCourseId, Long attendanceSchoolId,Long currentSchoolYear, Long stageId, Long studentId, Long operationalTestWindowId) {
        return testSessionDao.findPredecessorTestInfo(assessmentId, testTypeId,contentAreaId,
        		gradeCourseId,attendanceSchoolId,currentSchoolYear, stageId, studentId, operationalTestWindowId);
    }
    
	@Override
	public Stage selectTestSessionStageByPrimaryKey(Long testSessionStageId) {
		return testSessionDao.selectTestSessionStageByPrimaryKey(testSessionStageId);
	}

	@Override
	public List<Stage> selectStageByContentAreaTestTypeAssessment(
			Long contentAreaId, Long testTypeId, Long assessmentId) {
		return testSessionDao.selectStageByContentAreaTestTypeAssessment(contentAreaId, testTypeId, assessmentId);
	}
	
	@Override
	public List<DailyAccessCode> getDailyAccessCodes(Long assessmentProgramId, String testDate, String sortByColumn, String sortType, Map<String, String> dacCriteriaMap, Integer offset, Integer limitCount, Long stateId,Boolean includeGradeBand, Long userId, Long currentSchoolyear) {		
		return dacDao.getDailyAccessCodes(assessmentProgramId, testDate, sortByColumn, sortType, dacCriteriaMap, offset, limitCount, stateId, includeGradeBand, userId,  currentSchoolyear);
	}

	@Override
	public int getCountDailyAccessCode(Long assessmentProgramId, String testDate, Long stateId,Boolean includeGradeBand,Long userId, Long currentSchoolyear) {
		return dacDao.getCountDailyAccessCode(assessmentProgramId, testDate, stateId, includeGradeBand, userId, currentSchoolyear);
	}


    @Override    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)  
    public List<TestSessionPdfDTO> findTestSessionTicketDetailsByIdInterim(
    		List<Long> testSessionIds, Boolean isAutoRegistered, Boolean isPredictive,
    		List<Long> studentIds, UserDetailImpl userDetails) {
    	List<TestSessionPdfDTO> tempTestSessionDtoList = new ArrayList<TestSessionPdfDTO>();
    	List<TestSessionPdfDTO> testSessionDtoList = new ArrayList<TestSessionPdfDTO>();
    	Long organizationId = userDetails.getUser().getCurrentOrganizationId();
    	if(Boolean.FALSE.equals(isAutoRegistered)) {
    		tempTestSessionDtoList = testSessionDao.findTestSessionTicketDetailsByIdInterim(testSessionIds, userDetails.getUserId(), userDetails.getUser().isTeacher(), organizationId);
    	} else if (Boolean.TRUE.equals(isAutoRegistered)) {
    		if (Boolean.TRUE.equals(isPredictive)) {
    			tempTestSessionDtoList = testSessionDao.findTestSessionTicketDetailsForPredictive(testSessionIds, userDetails.getUserId(), userDetails.getUser().isTeacher(), organizationId);
    		} else {
    			tempTestSessionDtoList = testSessionDao.findAutoRegisteredTestSessionTicketsById(testSessionIds, studentIds, organizationId);
    		}
		}
    	HashSet<Long> studentTestId = new HashSet<Long>();

    	Long currentStudentTestId = null;
    	int currentSectionOrder = 0;
    	int currentIndexOrder = 0;
    	boolean isB4SectionHardBreak = false;
    	TestSessionPdfDTO currentTestSessionPdfDTO = null;
		if (!tempTestSessionDtoList.isEmpty() && tempTestSessionDtoList.size() > 0) {
			for (TestSessionPdfDTO testSessionPdfDTO : tempTestSessionDtoList) {
				if (testSessionPdfDTO.getTestSectionTicketNumber() == null) {
					if (!studentTestId.contains(testSessionPdfDTO.getStudentTestId())) {
						studentTestId.add(testSessionPdfDTO.getStudentTestId());
						if (testSessionPdfDTO.getTestSectionCount() > 1) {
							testSessionPdfDTO.setTestSectionName("Section 1-"
									+ testSessionPdfDTO.getTestSectionCount());
						} else {
							// INFO: if it has no sections then it will be an invalid test.
							testSessionPdfDTO.setTestSectionName("Section 1");
						}
						testSessionDtoList.add(testSessionPdfDTO);
					}
				} else {
					if (currentStudentTestId == null) {
						currentTestSessionPdfDTO = testSessionPdfDTO;
						currentSectionOrder = testSessionPdfDTO.getSectionOrder();
						currentIndexOrder = testSessionPdfDTO.getSectionOrder();
						currentStudentTestId = testSessionPdfDTO.getStudentTestId();
						isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
					} else {
						if(currentStudentTestId.equals(testSessionPdfDTO.getStudentTestId())) {
							if(isB4SectionHardBreak){//hardbreak
								if(currentSectionOrder - currentIndexOrder > 0){
									currentTestSessionPdfDTO.setTestSectionName("Sections " + currentTestSessionPdfDTO.getSectionOrder() + "-" + currentSectionOrder);
								}
								testSessionDtoList.add(currentTestSessionPdfDTO);
								currentTestSessionPdfDTO = testSessionPdfDTO;
								currentIndexOrder = testSessionPdfDTO.getSectionOrder();
								currentSectionOrder = testSessionPdfDTO.getSectionOrder();
								isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
							} else {
								currentSectionOrder = testSessionPdfDTO.getSectionOrder();
								isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
							}
						} else {
							if(currentSectionOrder - currentIndexOrder > 0){
								currentTestSessionPdfDTO.setTestSectionName("Sections " + currentTestSessionPdfDTO.getSectionOrder() + "-" + currentSectionOrder);
							}				
							testSessionDtoList.add(currentTestSessionPdfDTO);
							currentTestSessionPdfDTO = testSessionPdfDTO;
							currentSectionOrder = testSessionPdfDTO.getSectionOrder();
							currentIndexOrder = testSessionPdfDTO.getSectionOrder();
							currentStudentTestId = testSessionPdfDTO.getStudentTestId();
							isB4SectionHardBreak = testSessionPdfDTO.getHardBreak();
						}
					}
				}
			}
			
			if(currentTestSessionPdfDTO != null) {
				if(currentSectionOrder - currentIndexOrder > 0){
					currentTestSessionPdfDTO.setTestSectionName("Sections " + currentTestSessionPdfDTO.getSectionOrder() + "-" + currentSectionOrder);
				}
				testSessionDtoList.add(currentTestSessionPdfDTO);
			}
		}
   	 return testSessionDtoList;
    }
    
	public final List<TestSessionPdfDTO> findTestSessionTicketDetailsByIdPredictive(List<Long> testSessionIds, UserDetailImpl userDetails) {
		User user = userDetails.getUser();
		Long organizationId = userDetails.getUser().getCurrentOrganizationId();
		List<TestSessionPdfDTO> tempTestSessionDtoList = testSessionDao.findTestSessionTicketDetailsForPredictive(testSessionIds, user.getId(), user.isTeacher(), organizationId);
		
		if (CollectionUtils.isNotEmpty(tempTestSessionDtoList)) {
			
		}
		return null;
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<TestSession> selectTestSessionByStudentIdTestCollectionSource(Long studentId, String source, Long testCollectionId) {
		return testSessionDao.getTestSessionByStudentIdTestCollectionSource(studentId, source, testCollectionId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KELPATestAdministrationDTO> getKELPATestAdministrationExtract(Long orgId, Long currentSchoolYear,
			List<Integer> assessmentPrograms) {
		
		return testSessionDao.getKELPATestAdministrationExtract(orgId,currentSchoolYear,assessmentPrograms);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Long> findTestSessionsInactivatedBy(String inactivationType, Long studentId, Integer schoolYear, Long attendanceSchoolId, Long gradeId) {
		//if the inactivationType is gradechangeinactivated, then the student is still enrolled at the same school
		//if the inactivationType is exitclearunenrolled, then the student is not enrolled at a school
		return testSessionDao.findTestSessionsInactivatedBy(inactivationType, studentId, schoolYear, attendanceSchoolId, gradeId, inactivationType.equals(gradeChangeInactivatedPrefix));
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findTestSessionsToDeactivateForGradeChange(Long studentId, Integer currentSchoolYearEnrollment,
			Long attendanceSchoolId, Long oldGrade, Long newGrade) {
		return testSessionDao.findTestSessionsToDeactivateForGradeChange(studentId, currentSchoolYearEnrollment, attendanceSchoolId, oldGrade, newGrade);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean doesStudentHaveTestSessionsForCurrentGrade(Long studentId, Integer currentSchoolYear, Long attendanceSchoolId, Long gradeId) {
		return testSessionDao.doesStudentHaveTestSessionsForCurrentGrade(studentId, currentSchoolYear, attendanceSchoolId, gradeId);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AutoRegisteredTestSessionDTO> getKELPAAutoRegisteredTestSessionsByGradeCourseGradeBand(
    		UserDetailImpl userDetails, Long currentSchoolYear, String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, Long contentAreaId, 
    		Long gradeCourseId, Long schoolOrgId, Boolean showExpired,Boolean includeCompletedTestSession,
    		Boolean includeInProgressTestSession) {
    	
		//Get based on gradeband
		List<AutoRegisteredTestSessionDTO> autoRegTestSessions = testSessionDao.getKELPAAutoRegisteredTestSessionsByGradeCourseGradeBand(
				userDetails.getUser().getCurrentOrganizationId(), currentSchoolYear,
				sortByColumn, sortType, offset, limitCount, studentRosterCriteriaMap, 
				assessmentProgramId, assessmentProgramAbbr, testingProgramId, contentAreaId,
				gradeCourseId, schoolOrgId,	showExpired,includeCompletedTestSession,includeInProgressTestSession);
		
		if(CollectionUtils.isNotEmpty(autoRegTestSessions)) {
			return autoRegTestSessions;
		}
		//Get based on gradecourse
		autoRegTestSessions = testSessionDao.getAutoRegisteredTestSession(
				userDetails.getUser().getCurrentOrganizationId(), currentSchoolYear,
				sortByColumn, sortType, offset, limitCount, studentRosterCriteriaMap, 
				assessmentProgramId, assessmentProgramAbbr, testingProgramId, contentAreaId,
				gradeCourseId, schoolOrgId,	showExpired,includeCompletedTestSession,includeInProgressTestSession);
				
		
	    return autoRegTestSessions;
	}
	
	@Override
	public List<AutoRegisteredTestSessionDTO> getKELPAAutoregisteredTestSessionForActiveRosters(
			UserDetailImpl userDetails, String sortByColumn, String sortType, Integer offset,
			Integer limitCount, Map<String, String> studentRosterCriteriaMap,
			Long userId, Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long contentAreaId, Long gradeCourseId, Long schoolOrgId, Boolean showExpired) {
		List<AutoRegisteredTestSessionDTO> testSessionRosters = testSessionDao.getKELPAAutoregisteredTestSessionForActiveRosters(
				userDetails.getUser().getCurrentOrganizationId(),
				sortByColumn, sortType, offset, limitCount, studentRosterCriteriaMap,userId,
				assessmentProgramId, assessmentProgramAbbr, testingProgramId, contentAreaId, gradeCourseId, schoolOrgId, showExpired);

		if(CollectionUtils.isNotEmpty(testSessionRosters)) {
			return testSessionRosters;
		}
		
		testSessionRosters = testSessionDao.getAutoregisteredTestSessionForActiveRosters(
				userDetails.getUser().getCurrentOrganizationId(),
				sortByColumn, sortType, offset, limitCount, studentRosterCriteriaMap,userId,
				assessmentProgramId, assessmentProgramAbbr, testingProgramId, contentAreaId, gradeCourseId, schoolOrgId, showExpired);
		
	    return testSessionRosters;
	}
	
	/* sudhansu.b
	 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
	 */	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestSession> getSessionsForAutoScoring(Long assessmentProgramId){
		return testSessionDao.getSessionsForAutoScoring(assessmentProgramId);
	}
	
	/**
	 * Bishnupriya Nayak :US19343 : Test Management TestSession Completion
	 * Status Display
	 */
	@Override
	public List<AutoRegisteredTestSessionDTO> getAutoRegisteredTestSessionsForExtendedStatus(
			UserDetailImpl userDetails, Long currentSchoolYear,
			String sortByColumn, String sortType, Integer offset,
			Integer limitCount, Map<String, String> studentRosterCriteriaMap,
			Long assessmentProgramId, String assessmentProgramAbbr,
			Long testingProgramId, Long contentAreaId, Long gradeCourseId,
			Long schoolOrgId, Boolean showExpired,
			Boolean includeCompletedTestSession,
			Boolean includeInProgressTestSession) {
		List<AutoRegisteredTestSessionDTO> testSessionRosters = testSessionDao.getAutoRegisteredTestSessionsForExtendedStatus(
						userDetails.getUser().getCurrentOrganizationId(), currentSchoolYear,
						sortByColumn, sortType, offset, limitCount,
						studentRosterCriteriaMap, assessmentProgramId,
						assessmentProgramAbbr, testingProgramId, contentAreaId,
						gradeCourseId, schoolOrgId, showExpired,
						includeCompletedTestSession,
						includeInProgressTestSession);

		return testSessionRosters;
	}

	@Override
	public List<TestSessionRoster> getTestSessionsByStudentId(
			UserDetailImpl userDetails, boolean hasViewAllPermission,
			String sortByColumn, String sortType, int offset, int limitCount,
			Map<String, String> studentRosterCriteriaMap,
			Long systemEnrollmentRuleId, Long manualEnrollmentRuleId,
			boolean hasViewHighStakesTestSessionsPermission,
			Boolean qcComplete, Long assessmentProgramId, Boolean showExpired,
			Boolean hasQCCompletePermission,
			Boolean includeCompletedTestSession,
			Boolean includeInProgressTestSession, Long studentId) {
		// TODO Auto-generated method stub
		List<TestSessionRoster> testSessionRosters = testSessionDao
				.getTestSessionsByStudentId(userDetails.getUser()
						.getCurrentOrganizationId(), userDetails.getUser()
						.getContractingOrganization().getCurrentSchoolYear(),
						userDetails.getUserId(), hasViewAllPermission,
						sortByColumn, sortType, offset, limitCount,
						studentRosterCriteriaMap, systemEnrollmentRuleId,
						manualEnrollmentRuleId,
						hasViewHighStakesTestSessionsPermission, qcComplete,
						assessmentProgramId, SourceTypeEnum.ITI.getCode(),
						showExpired, hasQCCompletePermission,
						includeCompletedTestSession,
						includeInProgressTestSession, studentId);
		return testSessionRosters;

	}
	
	@Override
	public List<TestSessionRoster> getTestSessionsByStudentIdForLCS(
			UserDetailImpl userDetails, boolean hasViewAllPermission,
			String sortByColumn, String sortType, int offset, int limitCount,
			Map<String, String> studentRosterCriteriaMap,
			Long systemEnrollmentRuleId, Long manualEnrollmentRuleId,
			boolean hasViewHighStakesTestSessionsPermission,
			Boolean qcComplete, Long assessmentProgramId, Boolean showExpired,
			Boolean hasQCCompletePermission,
			Boolean includeCompletedTestSession,
			Boolean includeInProgressTestSession, Long studentId) {
		// TODO Auto-generated method stub
		List<TestSessionRoster> testSessionRosters = testSessionDao
				.getTestSessionsByStudentIdForLCS(userDetails.getUser()
						.getCurrentOrganizationId(), userDetails.getUser()
						.getContractingOrganization().getCurrentSchoolYear(),
						userDetails.getUserId(), hasViewAllPermission,
						sortByColumn, sortType, offset, limitCount,
						studentRosterCriteriaMap, systemEnrollmentRuleId,
						manualEnrollmentRuleId,
						hasViewHighStakesTestSessionsPermission, qcComplete,
						assessmentProgramId, SourceTypeEnum.ITI.getCode(),
						showExpired, hasQCCompletePermission,
						includeCompletedTestSession,
						includeInProgressTestSession, studentId);
		return testSessionRosters;

	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deactivateLcsStudentsTestsForStudentOnly(Long studentId){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		testSessionDao.deactivateLcsStudentsTestsForStudentOnly(studentId, new Date(), userDetails.getUserId());
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deactivateLcsStudentsTestsForLcsOnly(String lcsId){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		testSessionDao.deactivateLcsStudentsTestsForLcsOnly(lcsId, new Date(), userDetails.getUserId());
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deactivateLcsTests(Long studentId, Long testSessionId){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		testSessionDao.deactivateLcsTests(studentId, testSessionId, new Date(), userDetails.getUserId());
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void resetDLMTestlet(Long studentId, Long testSessionId, Long contentAreaId){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user=userDetails.getUser();
		testSessionDao.resetDLMTestlet(studentId,testSessionId,contentAreaId,user.getId());
	}
	@Override
	public List<Long> checkIsStudentPresent(String stateStudentIdentifier,Long contractingOrgId) {
		List<Long>  studentIds=testSessionDao.checkIsStudentPresent(stateStudentIdentifier,contractingOrgId);
		return studentIds;
	}

	@Override
	public Long getContentAreaIdByTestSession(Long testSessionId, Long studentId) {
		Long contentAreaId=testSessionDao.getContentAreaIdByTestSession(testSessionId, studentId);
		return contentAreaId;
	}

	

	@Override
	public List<Long> getStudentsIdsByIncompleteTestSessionId(Long testSessionId, Long currentSchoolYear) {
		return testSessionDao.getStudentsIdsByIncompleteTestSessionId(testSessionId, currentSchoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteCurrentTest(Long testSessionId, Long studentId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user= userDetails.getUser();
		testSessionDao.deleteCurrentTest(testSessionId,studentId,user.getId());
		
	}

	@Override
	public Boolean isLCSIdPresent(String lcsId) {
		Long lcsCount= testSessionDao.findLCSIdCount(lcsId);
		if(lcsCount>0){
			return true;
		}
		return false;
	}

	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestSession> findTestSessionByGradeBandContentArea(Long contentAreaId, Long gradeBandId, Long attendanceSchoolId,
    		Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId, Long rosterId, String testSessionName) {
    	return testSessionDao.findTestSessionByGradeBandContentArea(contentAreaId, gradeBandId,attendanceSchoolId,currentSchoolYear, 
    			stageId, sourceType.getCode(), operationalTestWindowId, rosterId, testSessionName);
    }

	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final String getPredecessorStageStudentsTestStatus(Long contentAreaId, Long gradeBandId, Long currentSchoolYear, Long stageId, Long studentId, Long operationalTestWindowId) {
		return testSessionDao.findPredecessorStageStudentsTestStatus(contentAreaId, gradeBandId, currentSchoolYear, stageId, studentId, operationalTestWindowId);
    }
	
	@Override
	public List<AutoRegisteredTestSessionDTO> getAutoregisteredTestSessionForActiveRostersPltw(
			UserDetailImpl userDetails, String sortByColumn, String sortType, Integer offset,
			Integer limitCount, Map<String, String> studentRosterCriteriaMap,
			Long userId, Long assessmentProgramId, String assessmentProgramAbbr, Long testingProgramId, 
			Long schoolOrgId, Boolean showExpired, Boolean includeCompletedTestSession) {
		List<AutoRegisteredTestSessionDTO> testSessionRosters = testSessionDao.getAutoregisteredTestSessionForActiveRostersPltw(
				userDetails.getUser().getCurrentOrganizationId(),
				sortByColumn, sortType, offset, limitCount, studentRosterCriteriaMap,userId,
				assessmentProgramId, assessmentProgramAbbr, testingProgramId, schoolOrgId, showExpired, includeCompletedTestSession);

	    return testSessionRosters;
	}

	
}