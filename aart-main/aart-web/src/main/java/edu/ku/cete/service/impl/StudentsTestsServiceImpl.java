/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.thirdparty.jackson.databind.deser.Deserializers.Base;
import com.fasterxml.jackson.databind.JsonNode;

import edu.ku.cete.configuration.StudentsTestSectionsStatusConfiguration;
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.RosterAutoScoringStudentsTestsMap;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.StudentsTestSectionsCriteria;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.StudentsTestsExample;
import edu.ku.cete.domain.StudentsTestsHistory;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.TestSessionExample;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.api.scoring.ScoringAPIObject;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.AutoRegistrationCriteria;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.content.TestSectionExample;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.StudentRosterKeyDto;
import edu.ku.cete.domain.enrollment.ViewMergeStudentDetailsDTO;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.model.AutoRegistrationCriteriaDao;
import edu.ku.cete.model.DailyAccessCodeMapper;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.GradeCourseDao;
import edu.ku.cete.model.InterimStudentReportMapper;
import edu.ku.cete.model.StudentPasswordDao;
import edu.ku.cete.model.StudentReportQuestionInfoMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.StudentsTestsHistoryMapper;
import edu.ku.cete.model.SubjectAreaDao;
import edu.ku.cete.model.TestCollectionDao;
import edu.ku.cete.model.TestDao;
import edu.ku.cete.model.TestSectionDao;
import edu.ku.cete.model.TestSessionDao;
import edu.ku.cete.model.TestTypeDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.mapper.StudentsTestSectionsDao;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.service.exception.SyncStudentException;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StageEnum;
import edu.ku.cete.util.StudentUtil;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

/**
 * @author neil.howerton
 *
 */
/**
 * @author vittaly
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class StudentsTestsServiceImpl implements StudentsTestsService {

    /**
     * logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentsTestsServiceImpl.class);
 
    private static final NullComparator NULL_COMPARATOR = new NullComparator(false);
    
    @Autowired
    private StudentsTestsDao studentsTestsDao;
    
    @Autowired
    private TestCollectionService testCollectionService;

    @Autowired
    private TestService testService;

    @Autowired
    private TestSessionService testSessionService;
    
    @Autowired
    private EnrollmentDao enrollmentDao;

    @Value("${DoNotRandomize}")
    private String doNotRandomize;
    
    @Value("${Randomize}")
    private String randomizationAtEnrollment;
    
    @Value("${ticketnumber.length}")
    private int ticketNumberLength;
        
    @Autowired
    private TestStatusConfiguration testStatusConfiguration;
    
    @Autowired
    private StudentsTestsStatusConfiguration studentsTestsStatusConfiguration;
    
    @Autowired
    private StudentsTestSectionsStatusConfiguration studentsTestSectionsStatusConfiguration;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private TestSectionDao testSectionDao;
    
    @Autowired
    private StudentsTestSectionsDao studentsTestSectionDao;
    
    @Autowired
    private StudentUtil studentUtil;
    
    @Autowired
    private SubjectAreaDao subjectAreaDao;
    
	@Autowired
	private StudentTrackerService studentTrackerService;
    
    @Autowired
    private TestTypeDao testTypeDao;
    
    @Autowired
    private GradeCourseDao gradeCourseDao;
        
    @Autowired
    private StudentsTestsHistoryMapper studentsTestsHistoryDao;
    
    /**
     * Source Type
     */
    @Value("${source.type}")
    private String SOURCE_TYPE;
    
    /**
     * ITI Source
     */
    @Value("${source.iti.code}")
    private String SOURCE_CODE_ITI;
	/**
	 * studentDao.
	 */
    @Autowired
	private StudentDao studentDao;
    
    /**
     * The Data Access Object for testSession.
     */
    @Autowired
    private TestSessionDao testSessionDao;    
     
    @Value("${adaptive.test.code}")
    private String adaptiveTestCode;
    
    /**
     * Code value for the category table to identify the records that represents
     * the closed test session status.
     * TODO use TestSessionStatusConfiguration
     * TODO change to studentstests.closed instead of test session status.closed. 
     */
    @Value("${studentstests.status.closed}")
    private String STUDENT_TEST_STATUS_CLOSED;

    /**
     * Code value for the category table to identify the records that represents
     * the open student test status.
     */
    @Value("${studentstests.status.unused}")
    private String STUDENT_TEST_STATUS_UNUSED;

    /**
     * Code value for the category type table that represents the student test
     * status type.
     */
    @Value("${studentstests.status.type}")
    private String STUDENT_TEST_STATUS_TYPE;
    
    @Value("${studentstests.status.fcunenrolled}")
    private String fcUnenrolledStudentTest;
    
    @Value("${studentstests.status.fcmidunenrolled}")
    private String fcMidUnenrolledStudentTest;
    
    @Value("${studentstests.status.pnpunenrolled}")
    private String pnpUnenrolledStudentTest;
    
    @Value("${studentstestsections.status.type}")
    private String STUDENT_TEST_SECTION_STATUS_TYPE;
	
    @Value("${studentstestsections.status.fcunenrolled}")
    private String fcUnenrolledStudentTestSection;
    
    @Value("${studentstestsections.status.fcmidunenrolled}")
    private String fcMidUnenrolledStudentTestSection;
    
    @Value("${studentstestsections.status.pnpunenrolled}")
    private String pnpUnenrolledStudentTestSection;
    
    @Value("${testsession.status.exitunenrolled}")
    private String testsessionExitUnenrolledCategoryCode;
    
    @Value("${studentstests.status.exitunenrolled}")
    private String studentstestsExitUnenrolledCategoryCode;
    
    @Value("${studentstestsections.status.exitunenrolled}")
    private String studentstestsectionsExitUnenrolledCategoryCode;    
    
    @Value("${testsession.status.rosterunenrolled}")
    private String testsessionRosterUnenrolledCategoryCode;
    
    @Value("${studentstests.status.rosterunenrolled}")
    private String studentstestsRosterUnenrolledCategoryCode;
    
    @Value("${studentstestsections.status.rosterunenrolled}")
    private String studentstestsectionsRosterUnenrolledCategoryCode;   
    
    @Value("${studentstests.stage1.completed}")
    private String STAGE1_COMPLETED;
    
    @Value("${studentstests.stage2.completed}")
    private String STAGE2_COMPLETED;
    
    @Value("${studentstests.nostage}")
    private String NO_STAGE;
    
    @Value("${studentstests.stage.exists}")
    private String STAGE_EXISTS;
    
    @Value("${studentstests.all.stages.completed}")
    private String ALL_STAGES_COMPLETED;
    
	@Autowired(required = true)
	private RestTemplate restTemplate;
	
	@Value("${epservice.url}")
	private String epServiceURL;
    /**
     * generator
     */
    private Random generator = new Random();    

    /**
     * testCollectionsSessionRulesDao.
     */
    @Autowired
	private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
    /**
     * studentSessionRuleConverter.
     */
    @Autowired
    private StudentSessionRuleConverter studentSessionRuleConverter;
    /**
     * studentService.
     */
    @Autowired
	private StudentService studentService;

	/**
	 * enrollmentsRostersDao.
	 */
    @Autowired
	private EnrollmentsRostersDao enrollmentsRostersDao;

	/**
	 * testCollectionDao.
	 */
    @Autowired
	private TestCollectionDao testCollectionDao;
    
    /**
	 * AutoRegistrationCriteriaDao.
	 */
    @Autowired
	private AutoRegistrationCriteriaDao autoRegistrationCriteriaDao;

	/**
	 * 
	 */
	@Autowired
    private TestDao testDao;
    
	@Autowired
	private CategoryDao categoryDao;
    
    /**
     * TEST_SESSION_STATUS_UNUSED
     */
    @Value("${testsession.status.unused}")
    private String TEST_SESSION_STATUS_UNUSED;
    
    /**
     * TEST_SESSION_STATUS_TYPE
     */
    @Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;
    		
    /**
     * AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG
     */
    @Value("${autoregistration.varianttypeid.code.eng}")
    private String AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG;
    
    /**
     * iti TestSession HistoryService
     */
    @Autowired
	private ItiTestSessionService itiTestSessionService;
    /**
     * permissionUtil
     */
    @Autowired
    private PermissionUtil permissionUtil;
    
    /**
     * enrollmentTestTypeSubjectAreaDao
     */
    @Autowired
    private EnrollmentTestTypeSubjectAreaDao enrollmentTestTypeSubjectAreaDao;
    
    
    @Autowired
	private StudentProfileService studentProfileService;
    
    /**
     * Data access object for retrieving the word part of the student password.
     */
    @Autowired
    private StudentPasswordDao studentPasswordDao;
    
    @Autowired
    private DailyAccessCodeMapper dailyAccessCodeDao;
    
    @Autowired
    private InterimStudentReportMapper interimStudentReportMapper;
    
    @Autowired
    private StudentReportQuestionInfoMapper studentReportQuestionInfoMapper;
    
    @Value("${print.test.file.path}")
	private String REPORT_PATH;
    
    private SqlSessionFactory sqlSessionFactory;
    
    @Autowired
    private AwsS3Service s3;

		
	@Autowired
	@Qualifier("sqlSessionFactory")
	public void setSqlStageSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
    
    /**
     * @param example
     *            {@link StudentsTestsExample}
     * @return int
     */
    @Override
    public final int countByExample(StudentsTestsExample example) {
        return studentsTestsDao.countByExample(example);
    }

    /**
     * @param example
     *            {@link StudentsTestsExample}
     * @return int
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int deleteByExample(StudentsTestsExample example) {
        return studentsTestsDao.deleteByExample(example);
    }

    /**
     * @param id
     *            {@link Long}
     * @return int
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int deleteByPrimaryKey(Long id) {
        return studentsTestsDao.deleteByPrimaryKey(id);
    }

    /**
     * @param record
     *            {@link StudentsTests}
     * @return {@link StudentsTests}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentsTests insert(StudentsTests record) {
        studentsTestsDao.insert(record);
        return record;
    }

    /**
     * @param record
     *            {@link StudentsTests}
     * @return {@link StudentsTests}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentsTests insertSelective(StudentsTests record) {
        studentsTestsDao.insertSelective(record);
        return record;
    }

    /**
     * @param example
     *            {@link StudentsTestsExample}
     * @return List<StudentsTests>
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<StudentsTests> selectByExample(StudentsTestsExample example) {
        return studentsTestsDao.selectByExample(example);
    }

    /**
     * @param id
     *            {@link Long}
     * @return {@link StudentsTests}
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentsTests selectByPrimaryKey(Long id) {
        return studentsTestsDao.selectByPrimaryKey(id);
    }

    /**
     * @param record
     *            {@link StudentsTests}
     * @param example
     *            {@link StudentsTestsExample}
     * @return int
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExampleSelective(StudentsTests record, StudentsTestsExample example) {
        return studentsTestsDao.updateByExampleSelective(record, example);
    }

    /**
     * @param record
     *            {@link StudentsTests}
     * @param example
     *            {@link StudentsTestsExample}
     * @return int
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExample(StudentsTests record, StudentsTestsExample example) {
        return studentsTestsDao.updateByExample(record, example);
    }

/**
     * @param record {@link StudentsTests
     * @return int
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByPrimaryKeySelective(StudentsTests record) {
        return studentsTestsDao.updateByPrimaryKeySelective(record);
    }

    /**
     * @param record
     *            {@link StudentsTests}
     * @return int
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByPrimaryKey(StudentsTests record) {
        return studentsTestsDao.updateByPrimaryKey(record);
    }
   
    /**
     * This method creates the test session for a given test collection for both system defined enrollment 
     * and manual enrollment type. 
     */    
    /**
     * @param enrollmentRosterIds
     *            List<Long>
     * @param testCollectionId
     *            long
     * @param testId
     *            long            
     * @param testSession
     *            {@link TestSession}
     * @param testingProgramId
     *            {@link Long}
     * @return boolean whether the list of students and tests contained a
     *         duplicate key that already exists.
     * @throws DuplicateTestSessionNameException
     *             DuplicateTestSessionNameException
     */
    
 
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean createTestSessions(List<Long> enrollmentRosterIds, Long testCollectionId, Long inputTestId, 
    			TestSession testSession, List<Long> sensitivityTags, Long stateId) throws DuplicateTestSessionNameException {
        //INFO new student test enrollments will be created with a status of unused.
        long studentsTestsStatusId = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId();
        List<StudentRosterKeyDto> studentRosterDtoKeys = enrollmentsRostersDao.selectStudentRosterKeysById(enrollmentRosterIds);
        Map<Long,List<Long>> rosterStudentMap = new HashMap<Long, List<Long>>();
        Map<Long,Long> rosterTestSessionMap = new HashMap<Long, Long>();
        Map<Long,List<StudentRosterKeyDto>> rosterStudentKeyMap = new HashMap<Long, List<StudentRosterKeyDto>>();
        List<Long> studentIds = new ArrayList<Long>();
        List<Enrollment> enrollments = new ArrayList<Enrollment>();
        
        for(StudentRosterKeyDto studentRosterKeyDto:studentRosterDtoKeys) {
        	if(!rosterStudentMap.containsKey(studentRosterKeyDto.getRosterId())) {
        		rosterStudentMap.put(studentRosterKeyDto.getRosterId(), new ArrayList<Long>());
        		rosterStudentKeyMap.put(studentRosterKeyDto.getRosterId(),
        				new ArrayList<StudentRosterKeyDto>());
        	}
        	rosterStudentMap.get(studentRosterKeyDto.getRosterId()).add(studentRosterKeyDto.getStudentId());
        	rosterStudentKeyMap.get(studentRosterKeyDto.getRosterId()).add(studentRosterKeyDto);
        	studentIds.add(studentRosterKeyDto.getStudentId());
        	enrollments.add(enrollmentDao.getByEnrollmentId(studentRosterKeyDto.getEnrollmentId()));
        }

        if (enrollmentRosterIds == null || testSession == null) {
            return false;
        }


        LOGGER.trace("Entering the createTestSessions method.");
        LOGGER.debug("Creating test sessions for students: {}, test collection: {}, and test session : {}",
        		new Object[] {enrollmentRosterIds, testSession.getTestCollectionId(), testSession.getId()});
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Boolean hasQCCompletePermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getQualityControlCompletePermission());
        
		 boolean isTestTicketed;
		 boolean isTestSectionTicketed;
		 StudentSessionRule studentSessionRule = new StudentSessionRule();
		 Map<Long, String> testNameMap = new HashMap<Long, String>();
		 
        // get the test collection associated with state
        TestCollection testCollection = testCollectionService.getTestCollectionById(testSession.getTestCollectionId(), stateId);
        if(testCollection.getOperationalTestWindowId() == null && hasQCCompletePermission) {
        	if(testCollection.getSystemSelectoptionId() != null) {
        		studentSessionRule.setSystemDefinedEnrollment(true);
        	} else {
        		studentSessionRule.setManualEnrollment(true);
        	}
        	isTestTicketed = false;
        	isTestSectionTicketed = false;
        } else {
        	List<TestCollectionsSessionRules> testCollectionsSesionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(
        			testCollection.getOperationalTestWindowId());
        	studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule( testCollectionsSesionsRulesList);        	
        	isTestTicketed = studentSessionRule.isTicketedAtTest();
        	isTestSectionTicketed = studentSessionRule.isTicketedAtSection();
        }
        
        if(studentSessionRule.isSystemDefinedEnrollment()) {
        	LOGGER.debug("studentSessionRule is system defined enrollment");
        	//US13123 - Defect resolution (DE4894) : If the user is a QCAdmin, then there is no test randomization, 
        	//just assign the same test as selected by QCAdmin.
			if (hasQCCompletePermission && inputTestId != null) {
				Test test = testService.findById(inputTestId);
	            if (test != null) {
	            	LOGGER.debug("hasQCCompletePermission and assigning selected test: "+inputTestId);
					for (Long rosterId : rosterStudentKeyMap.keySet()) {
	                	List<StudentRosterKeyDto> studentRosterKeyDtosForRoster = rosterStudentKeyMap.get(rosterId);
	                	testSession.setRosterId(rosterId);
	                	testSession.setId(null);
	                	testSession.setOperationalTestWindowId(testCollection.getOperationalTestWindowId());
	                	testSession.setSchoolYear(userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
	                	if(enrollments!=null && enrollments.size()>0)
	                		testSession.setAttendanceSchoolId(enrollments.get(0).getAttendanceSchoolId());
	                	else
	                		LOGGER.debug("No enrollments found.");
	                	testSession = testSessionService.insert(testSession);
	                    rosterTestSessionMap.put(rosterId, testSession.getId());
	                    for(StudentRosterKeyDto studentRosterKeyDto:studentRosterKeyDtosForRoster) {
	                    	studentRosterKeyDto.setTestSessionId(testSession.getId());
	                    } 
						insertStudentTests(testSession, studentsTestsStatusId, rosterStudentMap,
								rosterTestSessionMap, isTestSectionTicketed, isTestTicketed,
								(new ArrayList<Long>(Arrays.asList(inputTestId))), rosterId,
								studentRosterKeyDtosForRoster);
					}
					return true;
	            }
			} else {
	            List<Test> tests = testService.findByTestCollectionAndStatus(testSession.getTestCollectionId(), 
	            		testStatusConfiguration.getPublishedTestStatusCategory().getId());
	            
	            //INFO if randomization do not ticket.
	            //If no randomization then go by the flag at the test level.
	            List<Long> testIds = AARTCollectionUtil.getIds(tests);
	            LOGGER.debug("testids are =" +  testIds);
	            
	            if(tests != null && CollectionUtils.isNotEmpty(tests) 
	            		&& testIds != null && CollectionUtils.isNotEmpty(testIds)) {
	                if (randomizationAtEnrollment.equals(testCollection.getRandomizationType())) {
	                    //INFO: randomization at enrollment.
	                     for (Long rosterId : rosterStudentKeyMap.keySet()) {
	                    	List<StudentRosterKeyDto> studentRosterKeyDtosForRoster = rosterStudentKeyMap.get(rosterId);
	              			Long studentId = studentRosterKeyDtosForRoster.get(0).getStudentId();
	              			LOGGER.debug("randomizationAtEnrollment Student ID = " + studentId);
	                    	//US14261 - exclude tests that the student has already taken
	            			StudentsTestsExample example = new StudentsTestsExample();
	            			List<Long> studentTestStats = new ArrayList<Long>();
	            			studentTestStats.add(studentsTestsStatusConfiguration.getInProgressStudentsTestsStatusCategory().getId());
	            			studentTestStats.add(studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId());
	            			studentTestStats.add(studentsTestsStatusConfiguration.getClosedStudentsTestsStatusCategory().getId());
	            			example.createCriteria().andStatusIn(studentTestStats).andTestCollectionIdEqualTo(testCollectionId).
	            			andTestIdIn(testIds).andStudentIdEqualTo(studentId).andIsActive();
	            			//if studentstests exist with these ids then we want to remove them from 
	            			//the list of tests to select from 
	            			List<StudentsTests> studentsTests = selectByExample(example);
	            			for (StudentsTests st : studentsTests){
	                			Iterator<Test> it = tests.iterator();
	            				if (testIds.contains(st.getTestId())){
	            					while (it.hasNext()){
	            						Test test = it.next();
	            						if (st.getTestId().equals(test.getId())){
	            							it.remove();
	            							break;
	            						}
	            					}
	            				}
	            			}
	            			List<Long> testIdsFromTests = AARTCollectionUtil.getIds(tests);
	                    	LOGGER.debug("randomizationAtEnrollment testids after filtering already assigned tests =" +  testIdsFromTests);
	                		testSession.setRosterId(rosterId);
	                    	testSession.setId(null);
		                	testSession.setOperationalTestWindowId(testCollection.getOperationalTestWindowId());
		                	testSession.setSchoolYear(userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
		                	if(enrollments!=null && enrollments.size()>0)
		                		testSession.setAttendanceSchoolId(enrollments.get(0).getAttendanceSchoolId());
		                	else
		                		LOGGER.debug("No enrollments found.");
		                	if(testSession.getSource() != null && testSession.getSource().equalsIgnoreCase(SourceTypeEnum.ITI.getCode())){                        	
	            				LOGGER.debug("Iti Source. Filtering based on sensitivity tag and PNP");
	            				testIdsFromTests = itiSessionFilterTests(tests, sensitivityTags, studentId);
	            				if(testIdsFromTests.size() == 0){
	                            	LOGGER.debug("Randomization at enrollement. No tests found after filter returning false");
	            					return false;
	            				}
	            				testSession.setCreatedDate(new Date());
	            				testSession.setModifiedDate(new Date());
	            				testSession.setCreatedUser(userDetails.getUserId());
	            				testSession.setModifiedUser(userDetails.getUserId());
	            				testSession.setActiveFlag(true);
	            				testSessionDao.insert(testSession);
	            			}else{
		                    	testSession = testSessionService.insert(testSession);
	            			}
	                        rosterTestSessionMap.put(rosterId, testSession.getId());
	                        for(StudentRosterKeyDto studentRosterKeyDto:studentRosterKeyDtosForRoster) {
	                        	studentRosterKeyDto.setTestSessionId(testSession.getId());
	                        } 
	
	                        insertStudentTests(testSession, studentsTestsStatusId, rosterStudentMap,
	    								rosterTestSessionMap, isTestSectionTicketed, isTestTicketed,
	    								testIdsFromTests, rosterId, studentRosterKeyDtosForRoster);
	                     }
	                     return true;
	                } 
	                else {
	                    //INFO: randomization at login.
	                    LOGGER.debug("No Test will be set for this test collection" + testSession.getTestCollectionId());
	                    for (Long rosterId : rosterStudentKeyMap.keySet()) {
	                    	testSession.setRosterId(rosterId);
	                    	testSession.setId(null);
	                    	testSession.setOperationalTestWindowId(testCollection.getOperationalTestWindowId());
	                    	testSession.setSchoolYear(userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
	                    	if(enrollments!=null && enrollments.size()>0)
		                		testSession.setAttendanceSchoolId(enrollments.get(0).getAttendanceSchoolId());
		                	else
		                		LOGGER.debug("No enrollments found.");
		                	testSession = testSessionService.insert(testSession);
	                        List<StudentRosterKeyDto> studentRosterKeyDtosForRoster = rosterStudentKeyMap.get(rosterId);
	                        rosterTestSessionMap.put(rosterId, testSession.getId());
	                        for(StudentRosterKeyDto studentRosterKeyDto:studentRosterKeyDtosForRoster) {
	                        	studentRosterKeyDto.setTestSessionId(testSession.getId());
	                        } 
	                        insertStudentTests(testSession, studentsTestsStatusId, rosterStudentMap,
										rosterTestSessionMap, isTestSectionTicketed, isTestTicketed,
										null, rosterId, studentRosterKeyDtosForRoster);
	                    }
	                    return true;
	                }   
	            } else {
	                LOGGER.debug("No Tests for test collection is found" + testSession.getTestCollectionId());
	            }
			}
        } else if(studentSessionRule.isManualEnrollment()) {
            Test test = testService.findById(inputTestId);
            if (test != null) {
            	testNameMap.put(inputTestId, test.getTestName());
				for (Long rosterId : rosterStudentKeyMap.keySet()) {
                	testSession.setRosterId(rosterId);
                	testSession.setId(null);
                	testSession.setOperationalTestWindowId(testCollection.getOperationalTestWindowId());
                	testSession.setTestId(inputTestId);
                	testSession.setSchoolYear(userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
                	if(enrollments!=null && enrollments.size()>0)
                		testSession.setAttendanceSchoolId(enrollments.get(0).getAttendanceSchoolId());
                	else
                		LOGGER.debug("No enrollments found.");
                	testSession = testSessionService.insert(testSession);
                    List<StudentRosterKeyDto>
                    studentRosterKeyDtosForRoster = rosterStudentKeyMap.get(rosterId);
                    rosterTestSessionMap.put(rosterId, testSession.getId());
                    for(StudentRosterKeyDto studentRosterKeyDto:studentRosterKeyDtosForRoster) {
                    	studentRosterKeyDto.setTestSessionId(testSession.getId());
                    } 
                    
					insertStudentTests(testSession, studentsTestsStatusId, rosterStudentMap,
							rosterTestSessionMap, isTestSectionTicketed, isTestTicketed,
							AARTCollectionUtil.getList(inputTestId), rosterId, studentRosterKeyDtosForRoster);
				}
				return true;
			}
        } else {
            LOGGER.debug("Enrollment Type is not defined on test collection: " + testSession.getTestCollectionId());
        }

        return false;
    }


	private List<StudentsTests> insertStudentTests(TestSession testSession,
			long studentsTestsStatusId, Map<Long, List<Long>> rosterStudentMap,
			Map<Long, Long> rosterTestSessionMap, 
			boolean isTestSectionTicketed, boolean isTestTicketed, List<Long> testIds, Long rosterId,
			List<StudentRosterKeyDto> studentRosterKeyDtosForRoster) {
		List<StudentsTests> tempStudentsTestsList = new ArrayList<StudentsTests>();
		List<Long> tempSids = rosterStudentMap.get(rosterId);
		for(Long studentId: tempSids) { 
			Long enrollmentId = null;
			for(StudentRosterKeyDto studentRosterKeyDto:studentRosterKeyDtosForRoster) {
				if(null != studentRosterKeyDto.getStudentId() && studentId.equals(studentRosterKeyDto.getStudentId())) {
					enrollmentId = studentRosterKeyDto.getEnrollmentId();
					break;
				}
		     }   
			tempStudentsTestsList.add(insertStudentsTests(studentId, studentsTestsStatusId,
					testSession.getTestCollectionId(),
		    		AARTCollectionUtil.getRandomElement(testIds),
		    		rosterTestSessionMap.get(rosterId),isTestSectionTicketed,isTestTicketed,
		    		enrollmentId, testSession.getFinalBandId()));			
		}
		return tempStudentsTestsList;
	}
    


	/**
     * @param example
     *            {@link StudentsTestsExample}
     * @param assessmentId
     *            long
     * @param rosterId
     *            long
     * @return List<StudentsTests>
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public final List<StudentsTests> selectByExampleAssessmentIdAndRoster(StudentsTestsExample example,
            long assessmentProgramId, long rosterId) {
        return studentsTestsDao.selectByExampleAssessmentIdAndRoster(example, assessmentProgramId, rosterId,
        		studentsTestsStatusConfiguration.getClosedStudentsTestsStatusCategory().getId());
    }

    /**
     * @param studentId
     *            long
     * @param testSessionIds
     *            List<Long>
     * @param testCollectionIds
     *            List<Long>
     * @param assessmentId
     *            long
     * @return boolean
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private final boolean createTestSessions(long studentId, long studentsTestsStatusId,
    		List<Long> testSessionIds, List<Long> testCollectionIds, long assessmentId, Long enrollmentId) {
        if (studentId <= 0 || testSessionIds == null) {
            return false;
        }

        boolean successful = true;
        for (int i = 0; i < testSessionIds.size(); i++) {
        	Long testSessionId = testSessionIds.get(i); 
        	//TODO remove the sql inside for loop after refactor to record browser on manage test session.
            long testCollectionId = testCollectionIds.get(i);
            TestCollection testCollection = testCollectionService.selectByPrimaryKey(testCollectionId);
            List<Test> tests = testService.findByTestCollectionAndStatus(testCollection.getId(), testStatusConfiguration
                    .getPublishedTestStatusCategory().getId());
            Test test = null;
            // TODO testCollectionsSessionRulesDao.selectByTestCollection should not be used, but this is legacy code that doesn't use
			// operationaltestwindow, to my knowledge
			// testCollectionsSessionRulesDao.selectByOperationalTestWindowId should be used instead
            List<TestCollectionsSessionRules>
            testCollectionsSesionsRulesList = testCollectionsSessionRulesDao.selectByTestCollection(testCollectionId);
    		StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(testCollectionsSesionsRulesList);
    		
            if (tests != null && CollectionUtils.isNotEmpty(tests)) {
                if (tests.size() == 1) {
                    // only one test is found, so it cannot be possibly
                    // randomized.
                    test = tests.get(0);
                } else if (studentSessionRule.isManualEnrollment()) {
                	TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);
                	Long testId = testSession.getTestId();
					for (Test test1 : tests) {
						//Long testId1 = test1.getId();
						if (test1.getId().equals(testId)) {
							test = test1;
						}
					}
                } else if(studentSessionRule.isSystemDefinedEnrollment()) {
                    if (randomizationAtEnrollment.equals(testCollection.getRandomizationType())) {
                        // randomization has to be done inside this loop.
                        if (tests != null && tests.size() > 1) {
                            test = tests.get(generator.nextInt(tests.size()));
                        }
                    } else {
                        LOGGER.debug("No Test will be set for this test collection" + testCollection.getId());
                    }
                }

            } else {
                LOGGER.debug("No Tests for test collection is found" + testCollection.getId());
            } 
            
            

    		
            boolean isTestSectionTicketed = studentSessionRule.isTicketedAtSection();
            
            boolean isTestLevelTicketed = studentSessionRule.isTicketedAtTest();
          
            successful = successful && createTestSession(studentId, studentsTestsStatusId,
            		testCollection.getId(), test,
            		testSessionIds.get(i), isTestSectionTicketed, isTestLevelTicketed, enrollmentId);
        }

        return successful;
    }

    /**
     * @param studentIds
     *            {@link Set}
     * @param studentTestSessionMap
     *            {@link Map}
     * @param studentTestCollectionMap
     *            {@link Map}
     * @param assessmentId
     *            {@link Long}
     * @param rosterId
     *            {@link Long}
     * @return {@link Boolean}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean manageTestSessions(List<Long> studentIds, Map<Long, List<Long>> studentTestSessionMap,
            Map<Long, List<Long>> studentTestCollectionMap, long assessmentId, long rosterId)
    {
        boolean success = true;
        boolean perRecordSuccess = false;
        if (studentIds != null && CollectionUtils.isNotEmpty(studentIds)) {
        	List<StudentRosterKeyDto> studentRosterDtoKeys = enrollmentsRostersDao.selectStudentRosterKeysById(
            		AARTCollectionUtil.getList(rosterId));
        	long studentsTestsStatusId = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId();
        	
            for (Long studentId : studentIds) {
            	Long enrollmentId=null;
            	for(StudentRosterKeyDto studentRosterKeyDto:studentRosterDtoKeys) {
            		if(studentRosterKeyDto.getStudentId() != null && studentRosterKeyDto.getStudentId().equals(studentId)) {
            			enrollmentId = studentRosterKeyDto.getEnrollmentId();
            			break;
            		}
            	}
                perRecordSuccess = manageTestSessions(studentId,
                		studentsTestsStatusId, studentTestSessionMap.get(studentId),
                        studentTestCollectionMap.get(studentId), assessmentId, rosterId,enrollmentId);
                success = success && perRecordSuccess;
            }
        }
        testSessionService.removeEmptySessions(rosterId);
        return success;
    }

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean editTestSession(List<Long> toBeEnrolledEnrollmentRosterIds,
			List<Long> toBeUnEnrolledEnrollmentRosterIds, Long testSessionId, Boolean qcComplete) {
		boolean result = false;
		
		if(toBeEnrolledEnrollmentRosterIds != null && CollectionUtils.isNotEmpty(toBeEnrolledEnrollmentRosterIds)) {
			List<StudentRosterKeyDto> toBeEnrolledStudentRosterKeys = enrollmentsRostersDao.selectStudentRosterKeysById(toBeEnrolledEnrollmentRosterIds);
		
			result = enrollToTestSession(toBeEnrolledStudentRosterKeys, testSessionId, false, qcComplete);
		}
		if(toBeUnEnrolledEnrollmentRosterIds != null && CollectionUtils.isNotEmpty(toBeUnEnrolledEnrollmentRosterIds)) {
			List<Long> toBeUnEnrolledStudentIds = studentDao
					.getStudentIdsByEnrollmentRosterIds(toBeUnEnrolledEnrollmentRosterIds);
			
			//DE6554 if the list passed to unEnrollFromTestSession is empty it causes a
			if (toBeUnEnrolledStudentIds != null && CollectionUtils.isNotEmpty(toBeUnEnrolledStudentIds)){
				result = unEnrollFromTestSession(toBeUnEnrolledStudentIds, testSessionId);
			}
		}		
		return result;
		
	}

	private boolean unEnrollFromTestSession(
			List<Long> toBeUnEnrolledStudentIds, Long testSessionId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		boolean status = false;
		StudentsTestsExample studentsTestsExample = new StudentsTestsExample();
		StudentsTestsExample.Criteria studentsTestsCriteria = studentsTestsExample.createCriteria();
		studentsTestsCriteria.andStudentIdIn(toBeUnEnrolledStudentIds);
		studentsTestsCriteria.andTestSessionIdEqualTo(testSessionId);
		List<StudentsTests> studentsTestsToBeUnEnrolledList = studentsTestsDao.selectByExample(studentsTestsExample);
		if(!studentsTestsToBeUnEnrolledList.isEmpty()){
			studentsTestSectionDao.deleteByStudentsTestsIds(
					AARTCollectionUtil.getIds(studentsTestsToBeUnEnrolledList)
					);
			studentsTestsDao.deleteByStudentsTestsIds(
					AARTCollectionUtil.getIds(studentsTestsToBeUnEnrolledList),userDetails.getUserId()
					);

			status = true;
		}else{
			LOGGER.debug("No students tests were found to delete.");
		}
		return status;
	}

	private boolean enrollToTestSession(List<StudentRosterKeyDto> toBeEnrolledStudentRosterKeys, Long testSessionId, Boolean accessibleForm, Boolean qcComplete) {		
		//INFO find rules on test collection.
		TestSession testSession = testSessionDao.findByPrimaryKey(testSessionId);
		TestCollection testCollection = testCollectionDao.selectByPrimaryKey(
				testSession.getTestCollectionId());
        List<Test> testsInCollection = null;
        
        if(accessibleForm == null) {
        	testsInCollection = testDao.findByTestCollectionAndStatusByAccessibleForm(
        			testCollection.getId(),
        			testStatusConfiguration
        			.getPublishedTestStatusCategory().getId(), accessibleForm);
        } else if(accessibleForm) {
        	testsInCollection = testDao.findByTestCollectionAndStatusByAccessibleForm(
        			testCollection.getId(),
        			testStatusConfiguration
        			.getPublishedTestStatusCategory().getId(), accessibleForm);
        } else {
        	//if the accessibleForm is false, then its manual edit test session process.
        	testsInCollection = testDao.findByTestCollectionAndStatusAndQC(
        			testCollection.getId(),
        			testStatusConfiguration
        			.getPublishedTestStatusCategory().getId(), qcComplete);
        }

        if(testsInCollection != null && testsInCollection.size()>0){
        	StudentSessionRule studentSessionRule= null;
        	if(testSession.getOperationalTestWindowId() != null) {    	
        	List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(testSession.getOperationalTestWindowId());
	        studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
        	}
	        //INFO find all test sections of each test in order to create student test section enrollments
	    	TestSectionExample tsExample = new TestSectionExample();
	    	TestSectionExample.Criteria tsCriteria = tsExample.createCriteria();
	    	tsCriteria.andTestIdIn(AARTCollectionUtil.getIds(testsInCollection));
	    	List<TestSection> testSectionsInCollection = testSectionDao.selectByExample(tsExample);
	    	Map<Long,List<TestSection>> testAndSectionMap = new HashMap<Long, List<TestSection>>();
	    	for(TestSection testSectionInCollection:testSectionsInCollection) {
	    		if(!testAndSectionMap.containsKey(testSectionInCollection.getTestId())) {
	    			testAndSectionMap.put(
	    					testSectionInCollection.getTestId(
	    							), new ArrayList<TestSection>());
	    		}
	    		testAndSectionMap.get(
	    				testSectionInCollection.getTestId()
	    				).add(testSectionInCollection);
	    	}
	        
			List<StudentsTests> toBeEnrolledStudentsTestsList = new ArrayList<StudentsTests>();
			for(StudentRosterKeyDto srKeyDto:toBeEnrolledStudentRosterKeys) {
				StudentsTests toBeEnrolledStudentsTests = new StudentsTests();
				toBeEnrolledStudentsTests.setTestSessionId(testSessionId);
				toBeEnrolledStudentsTests.setStudentId(srKeyDto.getStudentId());
				toBeEnrolledStudentsTests.setTestCollectionId(testSession.getTestCollectionId());
				//INFO: in case of randomization (login or enrollment) test id won't be set and that is ok.
				toBeEnrolledStudentsTests.setTestId(testSession.getTestId());
				toBeEnrolledStudentsTests.setEnrollmentId(srKeyDto.getEnrollmentId());
				toBeEnrolledStudentsTests.setStatus(
						studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory(
								).getId());
				toBeEnrolledStudentsTests.setAuditColumnProperties();
				
				//INFO setting the test id based on test session rule.
				if(testSession.getTestId() != null) {
					//INFO: This is a case of manual enrollment.So no ticket number is necessary.
					LOGGER.debug("Test Session Id "+testSessionId + " is for manual enrollment");
				} else {
					//INFO: This is a case of randomization.
					LOGGER.debug("Test Session Id "+testSessionId + " is for randomization");
					if(testCollection.getRandomizationType(
							).equalsIgnoreCase(randomizationAtEnrollment)) {
						//INFO randomization at enrollment.
						//any case of no single test is found in a collection etc.
						//should result in abrupt failure.
	                    Test test = testsInCollection.get(
	                    		generator.nextInt(testsInCollection.size())
	                    		);
						toBeEnrolledStudentsTests.setTestId(test.getId());
					} else if(testCollection.getRandomizationType(
							).equalsIgnoreCase(doNotRandomize)) {
						//INFO randomization at login.
						LOGGER.debug("Randomization at login for "
						+ testCollection.getId() + " No test id will be set");
					} else {
						//INFO some error case.
						LOGGER.debug("Test collection has incorrect randomization set"
						+ testCollection.getId());
					}
				}
				//commenting out this code for now and likely needs to be removed later.
				//students should always be put in a new test rather than reenroll - we can not determine the state the test was in previously
				//int reenrolled = studentsTestsDao.reEnrollByStudentsIdAndSessionId(srKeyDto.getStudentId(), testSessionId);
				//if(reenrolled == 0)
				studentsTestsDao.insert(toBeEnrolledStudentsTests);
				toBeEnrolledStudentsTestsList.add(toBeEnrolledStudentsTests);
				
				if (toBeEnrolledStudentsTests.getTestId() != null && 
						testAndSectionMap != null && testAndSectionMap.size() > 0) {
					//INFO this means this is a case of randomization at enrollment or manual.
					//TODO insert into students test section
					String ticketNo = null;
					boolean isPrevSectionHardBreak = false;
					for (TestSection sectionInCollection : testAndSectionMap
							.get(toBeEnrolledStudentsTests.getTestId())) {
				    			StudentsTestSections studentsTestSections = new StudentsTestSections();
				    			studentsTestSections.setStudentsTestId(
				    					toBeEnrolledStudentsTests.getId());
				    			studentsTestSections.setTestSectionId(
				    					sectionInCollection.getId());
				    			if(studentSessionRule != null && studentSessionRule.isTicketedAtSection()) { 
				    				if(isPrevSectionHardBreak || ticketNo == null) {
				    					ticketNo = studentUtil.generateRandomWord(ticketNumberLength);
				    				}
				    				isPrevSectionHardBreak = sectionInCollection.getHardBreak();
				    				studentsTestSections.setTicketNo(ticketNo);
				    			}
				    			studentsTestSections.setStatusId(	    					
										studentsTestSectionsStatusConfiguration.
										getUnUsedStudentsTestSectionsStatusCategory(
												).getId());
				    			studentsTestSections.setAuditColumnProperties();
				    			studentsTestSectionDao.insertSelective(studentsTestSections);
				    			LOGGER.debug("Students Test Sections inserted successfully ");
					}
				}
			}
			return true;
        } else {
        	LOGGER.debug("Retrieved testsInCollection is empty.");
        	return false;
        }
        
	}
	
	private boolean enrollToTestSession(Long toBeEnrolledStudentId, TestSession testSession, Boolean accessibleForm, Long enrollmentId) {
        Map<Long, String> testNameMap = new HashMap<Long, String>();
		
		//INFO find rules on test collection.
		TestCollection testCollection = testCollectionDao.selectByPrimaryKey(
				testSession.getTestCollectionId());
        List<Test> testsInCollection = null;
        
        if(accessibleForm == null) {
        	testsInCollection = testDao.findByTestCollectionAndStatusByAccessibleForm(
        			testCollection.getId(),
        			testStatusConfiguration
        			.getPublishedTestStatusCategory().getId(), accessibleForm);
        } else if(accessibleForm) {
        	testsInCollection = testDao.findByTestCollectionAndStatusByAccessibleForm(
        			testCollection.getId(),
        			testStatusConfiguration
        			.getPublishedTestStatusCategory().getId(), accessibleForm);
        } else {
        	//if the accessibleForm is false, then its manual edit test session process.
        	testsInCollection = testDao.findByTestCollectionAndStatus(
        			testCollection.getId(),
        			testStatusConfiguration
        			.getPublishedTestStatusCategory().getId());
        }
        
        if(testsInCollection != null && testsInCollection.size()>0){
        	for(Test test:testsInCollection) {
                testNameMap.put(test.getId(), test.getTestName());
            }
        	
        	// TODO testCollectionsSessionRulesDao.selectByTestCollection should not be used, but this is legacy code that doesn't use
			// operationaltestwindow, to my knowledge
			// testCollectionsSessionRulesDao.selectByOperationalTestWindowId should be used instead
            List<TestCollectionsSessionRules> testCollectionsSesionsRulesList 
            	= testCollectionsSessionRulesDao.selectByTestCollection(testSession.getTestCollectionId());
            StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(
                    testCollectionsSesionsRulesList);
            //INFO find all test sections of each test in order to create student test section enrollments
        	TestSectionExample tsExample = new TestSectionExample();
        	TestSectionExample.Criteria tsCriteria = tsExample.createCriteria();
        	tsCriteria.andTestIdIn(AARTCollectionUtil.getIds(testsInCollection));
        	List<TestSection> testSectionsInCollection = testSectionDao.selectByExample(tsExample);
        	Map<Long,List<TestSection>> testAndSectionMap = new HashMap<Long, List<TestSection>>();
        	for(TestSection testSectionInCollection:testSectionsInCollection) {
        		if(!testAndSectionMap.containsKey(testSectionInCollection.getTestId())) {
        			testAndSectionMap.put(
        					testSectionInCollection.getTestId(
        							), new ArrayList<TestSection>());
        		}
        		testAndSectionMap.get(
        				testSectionInCollection.getTestId()
        				).add(testSectionInCollection);
        	}
            
    		List<StudentsTests> toBeEnrolledStudentsTestsList = new ArrayList<StudentsTests>();
    		
    		StudentsTests toBeEnrolledStudentsTests = new StudentsTests();
    		toBeEnrolledStudentsTests.setTestSessionId(testSession.getId());
    		toBeEnrolledStudentsTests.setStudentId(toBeEnrolledStudentId);
    		toBeEnrolledStudentsTests.setTestCollectionId(testSession.getTestCollectionId());
    		//INFO: in case of randomization (login or enrollment) test id won't be set and that is ok.
    		toBeEnrolledStudentsTests.setTestId(testSession.getTestId());
    		toBeEnrolledStudentsTests.setStatus(
    				studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory(
    						).getId());
    		toBeEnrolledStudentsTests.setEnrollmentId(enrollmentId);
    		toBeEnrolledStudentsTests.setAuditColumnProperties();
    		
    		//INFO setting the test id based on test session rule.
    		  //if(testSession.getTestId() != null) {
    		   //INFO: This is a case of manual enrollment.So no ticket number is necessary.
    		   //LOGGER.debug("Test Session Id "+testSessionId + " is for manual enrollment");
    		  //} else {
    		   //INFO: This is a case of randomization.
    		   LOGGER.debug("Test Session Id "+ testSession.getId() + " is for randomization");
    		   if(testCollection.getRandomizationType(
    		     ).equalsIgnoreCase(randomizationAtEnrollment)) {
    		    //INFO randomization at enrollment.
    		    //any case of no single test is found in a collection etc.
    		    //should result in abrupt failure.
    		                Test test = testsInCollection.get(
    		                  generator.nextInt(testsInCollection.size())
    		                  );
    		    toBeEnrolledStudentsTests.setTestId(test.getId());
    		   } else if(testCollection.getRandomizationType(
    		     ).equalsIgnoreCase(doNotRandomize)) {
    		    //INFO randomization at login.
    		    LOGGER.debug("Randomization at login for "
    		    + testCollection.getId() + " No test id will be set");
    		    toBeEnrolledStudentsTests.setTestId(null);
    		   } else if(testSession.getTestId() != null) {
    		    toBeEnrolledStudentsTests.setTestId(testSession.getTestId());
    		   } else {
    		    //INFO some error case.
    		    LOGGER.debug("Test collection has incorrect randomization set"
    		    + testCollection.getId());
    		   }
    		  //}
    		  //commenting out this code for now and likely needs to be removed later.
    		  //students should always be put in a new test rather than reenroll - we can not determine the state the test was in previously
    		  //int reenrolled = studentsTestsDao.reEnrollByStudentsIdAndSessionId(toBeEnrolledStudentId, testSessionId);
    		  //if(reenrolled == 0)  
    		  studentsTestsDao.insert(toBeEnrolledStudentsTests);
    		  toBeEnrolledStudentsTestsList.add(toBeEnrolledStudentsTests);
    		  
    		  if (toBeEnrolledStudentsTests.getTestId() != null && 
    				testAndSectionMap != null && testAndSectionMap.size() > 0) {
    			//INFO this means this is a case of randomization at enrollment or manual.
    			//TODO insert into students test section
    			String ticketNo = null;
    			boolean isPrevSectionHardBreak = false;
    			for (TestSection sectionInCollection : testAndSectionMap
    					.get(toBeEnrolledStudentsTests.getTestId())) {
    		    			StudentsTestSections studentsTestSections = new StudentsTestSections();
    		    			studentsTestSections.setStudentsTestId(
    		    					toBeEnrolledStudentsTests.getId());
    		    			studentsTestSections.setTestSectionId(
    		    					sectionInCollection.getId());
    		    			if(studentSessionRule.isTicketedAtSection()) { 
    		    				if(isPrevSectionHardBreak || ticketNo == null) {
    		    					ticketNo = studentUtil.generateRandomWord(ticketNumberLength);
    		    				}
    		    				isPrevSectionHardBreak = sectionInCollection.getHardBreak();
    		    				studentsTestSections.setTicketNo(ticketNo);
    		    			}
    		    			studentsTestSections.setStatusId(	    					
    								studentsTestSectionsStatusConfiguration.
    								getUnUsedStudentsTestSectionsStatusCategory(
    										).getId());
    		    			studentsTestSections.setAuditColumnProperties();
    		    			studentsTestSectionDao.insertSelective(studentsTestSections);
    		    			LOGGER.debug("Students Test Sections inserted successfully ");
    			}
    		}
    		
    		return true;
        } else {
        	LOGGER.debug("Retrieved testsInCollection is empty.");
        	return false;
        }
        
	}
	//BR
	private boolean enrollToTestSession(Long toBeEnrolledStudentId, TestSession testSession, TestCollection testCollection, List<Test> testsInCollection, Long enrollmentId,
			Long studentTestStatusId, Long previousStudentsTestId) {
        Map<Long, String> testNameMap = new HashMap<Long, String>();
        
        for(Test test:testsInCollection) {
            testNameMap.put(test.getId(), test.getTestName());
        }

        List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(
        		testCollection.getOperationalTestWindowId());
        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
        
        //INFO find all test sections of each test in order to create student test section enrollments
    	TestSectionExample tsExample = new TestSectionExample();
    	TestSectionExample.Criteria tsCriteria = tsExample.createCriteria();
    	tsCriteria.andTestIdIn(AARTCollectionUtil.getIds(testsInCollection));
    	
    	List<TestSection> testSectionsInCollection = testSectionDao.selectByExample(tsExample);
    	if(testSectionsInCollection != null && testSectionsInCollection.size()>0){
    		Map<Long,List<TestSection>> testAndSectionMap = new HashMap<Long, List<TestSection>>();
        	for(TestSection testSectionInCollection:testSectionsInCollection) {
        		if(!testAndSectionMap.containsKey(testSectionInCollection.getTestId())) {
        			testAndSectionMap.put(testSectionInCollection.getTestId(), new ArrayList<TestSection>());
        		}
        		testAndSectionMap.get(testSectionInCollection.getTestId()).add(testSectionInCollection);
        	}
        	if(studentTestStatusId == null) {
        		studentTestStatusId = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId();
        	}
    		List<StudentsTests> toBeEnrolledStudentsTestsList = new ArrayList<StudentsTests>();
    		
    		StudentsTests toBeEnrolledStudentsTests = new StudentsTests();
    		toBeEnrolledStudentsTests.setTestSessionId(testSession.getId());
    		toBeEnrolledStudentsTests.setStudentId(toBeEnrolledStudentId);
    		toBeEnrolledStudentsTests.setTestCollectionId(testSession.getTestCollectionId());
    		//INFO: in case of randomization (login or enrollment) test id won't be set and that is ok.
    		toBeEnrolledStudentsTests.setTestId(testSession.getTestId());
    		toBeEnrolledStudentsTests.setStatus(studentTestStatusId);
    		toBeEnrolledStudentsTests.setEnrollmentId(enrollmentId);
    		toBeEnrolledStudentsTests.setAuditColumnProperties();
    		
    		//INFO: This is a case of randomization.
    		LOGGER.debug("Test Session Id "+ testSession.getId() + " is for randomization");
    		if(testCollection.getRandomizationType().equalsIgnoreCase(randomizationAtEnrollment)) {
    		    //INFO randomization at enrollment.
    		    //any case of no single test is found in a collection etc.
    		    //should result in abrupt failure.
    		    Test test = testsInCollection.get(generator.nextInt(testsInCollection.size()));
    		    toBeEnrolledStudentsTests.setTestId(test.getId());
    		} else if(testCollection.getRandomizationType().equalsIgnoreCase(doNotRandomize)) {
    		    //INFO randomization at login.
    		    LOGGER.debug("Randomization at login for "+ testCollection.getId() + " No test id will be set");
    		    toBeEnrolledStudentsTests.setTestId(null);
    		} else if(testSession.getTestId() != null) {
    		    toBeEnrolledStudentsTests.setTestId(testSession.getTestId());
    		} else {
    		    //INFO some error case.
    		    LOGGER.debug("Test collection has incorrect randomization set"+ testCollection.getId());
    		}
    		
    		if(previousStudentsTestId != null){
    			toBeEnrolledStudentsTests.setPreviousStudentsTestId(previousStudentsTestId);
    		}
    		//commenting out this code for now and likely needs to be removed later.
    		//students should always be put in a new test rather than reenroll - we can not determine the state the test was in previously
    		//int reenrolled = studentsTestsDao.reEnrollByStudentsIdAndSessionId(toBeEnrolledStudentId, testSessionId);
    		//if(reenrolled == 0)  
    		studentsTestsDao.insert(toBeEnrolledStudentsTests);
    		toBeEnrolledStudentsTestsList.add(toBeEnrolledStudentsTests);
    		  
    		if (toBeEnrolledStudentsTests.getTestId() != null && testAndSectionMap != null && testAndSectionMap.size() > 0) {
    			//INFO this means this is a case of randomization at enrollment or manual.
    			//TODO insert into students test section
    			String ticketNo = null;
    			boolean isPrevSectionHardBreak = false;
    			for (TestSection sectionInCollection : testAndSectionMap.get(toBeEnrolledStudentsTests.getTestId())) {
    				StudentsTestSections studentsTestSections = new StudentsTestSections();
    		    	studentsTestSections.setStudentsTestId(toBeEnrolledStudentsTests.getId());
    		    	studentsTestSections.setTestSectionId(sectionInCollection.getId());
    		    	if(studentSessionRule != null && studentSessionRule.isTicketedAtSection()) { 
    		    		if(isPrevSectionHardBreak || ticketNo == null) {
    		    			ticketNo = studentUtil.generateRandomWord(ticketNumberLength);
    		    		}
    		    		isPrevSectionHardBreak = sectionInCollection.getHardBreak();
    		    		studentsTestSections.setTicketNo(ticketNo);
    		    	}
    		    	studentsTestSections.setStatusId(studentsTestSectionsStatusConfiguration.getUnUsedStudentsTestSectionsStatusCategory().getId());
    		    	studentsTestSections.setAuditColumnProperties();
    		    	studentsTestSectionDao.insertSelective(studentsTestSections);
    		    	LOGGER.debug("Students Test Sections inserted successfully ");
    			}
    		}
    		
    		return true;
    	} else {
    		LOGGER.debug("Retrieved testsInCollection is empty.");
    		return false;
    	}
    	
	}
	
    /**
     * TODO
     * just for deleting why get the data and compare it.
     * 
     * @param studentId
     *            long
     * @param testSessions
     *            List<Long>
     * @param testCollections
     *            List<Long>
     * @param assessmentId
     *            long
     * @param rosterId
     *            long
     * @return boolean true if successful, false otherwise.
     */    
     private final boolean manageTestSessions(long studentId,
    		 long studentsTestsStatusId, List<Long> testSessions, List<Long> testCollections,
            long assessmentId, long rosterId, Long enrollmentId){
    	 boolean deleteSuccessful = true;
        if (studentId <= 0 || testSessions == null || assessmentId <= 0 || rosterId <= 0) {
            return false;
        }

        StudentsTestsExample example = new StudentsTestsExample();
        StudentsTestsExample.Criteria criteria = example.createCriteria();
        criteria.andStudentIdEqualTo(studentId);

        List<StudentsTests> existingStudentsTestsList
        = studentsTestsDao.selectByExampleAssessmentIdAndRoster(example, assessmentId,
                rosterId, studentsTestsStatusConfiguration.getClosedStudentsTestsStatusCategory().getId());
        List<Long> toDeleteStudentsTestsIdsList = new ArrayList<Long>();

        for (int i = 0, length = existingStudentsTestsList.size(); i < length; i++) {
            boolean found = false;
            for (int j = 0, jLen = testSessions.size(); j < jLen && !found; j++) {
                if (existingStudentsTestsList.get(i).getTestSessionId().equals(testSessions.get(j))) {
                    // the test sessions and test collection should be the same
                    // size.
                    testSessions.remove(j);
                    testCollections.remove(j);
                    found = true;
                }
            }

            if ( !found && studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory(
            		).getId().equals(existingStudentsTestsList.get(i).getStatus())
            		|| !found && existingStudentsTestsList.get(i).getStatus() == null
            		) {
                toDeleteStudentsTestsIdsList.add(existingStudentsTestsList.get(i).getId());
            }
        }

        if (toDeleteStudentsTestsIdsList.size() > 0) {
            if (!toDeleteStudentsTestsIdsList.isEmpty()) {
            	
            	if (CollectionUtils.isNotEmpty(toDeleteStudentsTestsIdsList)) { 
                 	StudentsTestSectionsCriteria studentsTestSectionsCriteria = new StudentsTestSectionsCriteria();
 	            	StudentsTestSectionsCriteria.Criteria stsCriteria = studentsTestSectionsCriteria.createCriteria(); 
 	            	stsCriteria.andStudentsTestIdIn(toDeleteStudentsTestsIdsList);
 	            	studentsTestSectionDao.deleteByStudentsTestSectionsCriteria(studentsTestSectionsCriteria);	 
                 }
            	 
                StudentsTestsExample stExample = new StudentsTestsExample();
                StudentsTestsExample.Criteria stCriteria = stExample.createCriteria();
                stCriteria.andIdIn(toDeleteStudentsTestsIdsList);
                studentsTestsDao.deleteByExample(stExample);
                if(toDeleteStudentsTestsIdsList.size()
                		!= toDeleteStudentsTestsIdsList.size()) {
                	LOGGER.debug("unable to remove student enrollments to test on TDE" +
                			" for students tests id " + toDeleteStudentsTestsIdsList);
                	deleteSuccessful = false;
                }
            } else {
            	LOGGER.debug("unable to remove student enrollments to test on TDE" +
            			" for students tests id " + toDeleteStudentsTestsIdsList);
            	deleteSuccessful = false;
            }
        }

        boolean ret = true;
        if (testSessions.size() > 0) {
            ret = createTestSessions(studentId, studentsTestsStatusId,
            		testSessions, testCollections, assessmentId, enrollmentId);
        }
        if( !(ret && deleteSuccessful) ) {
        	throw new SyncStudentException();
        }

        return ret && deleteSuccessful;
    }

    /**
     * @param testSessionId
     *            long
     * @return List<StudentsTests>
     */
     @Override
     @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<StudentsTests> findByTestSession(long testSessionId) {
        return studentsTestsDao.findByTestSession(testSessionId);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<StudentsTests> findByTestSessionAndStudent(long testSessionId, long studentId) {     
    	return studentsTestsDao.findByTestSessionAndStudentId(testSessionId, studentId);
    }
    
    /**
     * @param assessmentProgramId
     *            long
     * @param rosterId
     *            long
     * @return List<StudentsTests>
     */
     @Override
     @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<StudentsTests> findByAssessmentProgramAndRoster(long assessmentProgramId, long rosterId) {
        return studentsTestsDao.findByAssessmentProgramAndRoster(assessmentProgramId, rosterId, STUDENT_TEST_STATUS_CLOSED);
    }

    /**
     *
     * @param studentId
     *            long
     * @param testCollectionId
     *            long
     * @param test {@link Test}
     * @param testSessionId
     *            {@link Long}
     * @param isSectionLevelTicketed
     *            boolean
     * @param isTestLevelTicketed
     *            boolean
     * @return boolean whether the StudentsTests object was successfully
     *         created.
     */
     @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private boolean createTestSession(long studentId, long studentsTestsStatusId, long testCollectionId,
    		Test test, Long testSessionId, boolean isSectionLevelTicketed,
    		boolean isTestLevelTicketed, Long enrollmentId) {
        boolean retVal = false;

        Long testId = null;

        Map<Long, String> testNameMap = new HashMap<Long, String>();

        if (test != null) {
            testId = test.getId();
            testNameMap.put(testId, test.getTestName());
        }

        StudentsTests toBeSyncedStudentsTests = insertStudentsTests(studentId, studentsTestsStatusId,
        		testCollectionId, testId, testSessionId, isSectionLevelTicketed, isTestLevelTicketed,
        		enrollmentId, null);

        if (toBeSyncedStudentsTests != null) {
            retVal = true;

            /*List<StudentsTests> toBeSyncedStudentsTestsList = new ArrayList<StudentsTests>();
            toBeSyncedStudentsTestsList.add(toBeSyncedStudentsTests);

            List<StudentsTests>
            syncedStudentsTestsList
            = externalSync(testCollectionId, testNameMap, toBeSyncedStudentsTestsList);
            
            if(syncedStudentsTestsList != null &&
            		toBeSyncedStudentsTests != null &&
            		syncedStudentsTestsList.size() == toBeSyncedStudentsTestsList.size()) {
                LOGGER.debug(" all students are synced " + syncedStudentsTestsList + 
                		" to be synced " + toBeSyncedStudentsTestsList +
                		"Tests for test collection " + testCollectionId);            	
            } else {
            	LOGGER.error("Not all students are synced " +
            			" synced " + syncedStudentsTestsList +
            			" toBeSynced " + toBeSyncedStudentsTestsList +
            			" Tests for test collection " + testCollectionId);
            	retVal = false;
            }*/
        }

        return retVal;
    }
     
     @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
     private StudentsTests insertStudentsTests(long studentId, long studentsTestsStatusId,
     		long testCollectionId, Long testId,
     		Long testSessionId, boolean isSectionLevelTicketed, boolean isTestLevelTicketed, 
     		Long enrollmentId, Long finalBandId) {
    	 return insertStudentsTests(studentId, studentsTestsStatusId, testCollectionId, testId,
    	     testSessionId, isSectionLevelTicketed, isTestLevelTicketed, enrollmentId, finalBandId, null, true, null, null);
     }

    /**
     *
     * @param studentId
     *            long
     * @param testCollectionId
     *            long    
     * @param testId
     *            {@link Long}
     * @param testSessionId
     *            {@link Long}
     * @param isSectionLevelTicketed
     *            boolean
     * @param isTestLevelTicketed
     *            boolean
     * @return {@link StudentsTests} 
     * 				StudentsTests object that was successfully created.
     * 
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private StudentsTests insertStudentsTests(long studentId, long studentsTestsStatusId,
    		long testCollectionId, Long testId,
    		Long testSessionId, boolean isSectionLevelTicketed, boolean isTestLevelTicketed, 
    		Long enrollmentId, Long finalBandId, Long userId, boolean checkForExistingAndUpdate, Long previousStudentsTestId, TestSession testSession) {
        StudentsTests studentsTests = null;
        String ticketNo = null;
        
        if (checkForExistingAndUpdate) {
        	// Check to see if a record already exists for the student-test session
            // pair.
        	StudentsTestsExample example = new StudentsTestsExample();
            StudentsTestsExample.Criteria criteria = example.createCriteria();
            criteria.andStudentIdEqualTo(studentId);
            criteria.andTestSessionIdEqualTo(testSessionId);
            List<StudentsTests> temp = studentsTestsDao.selectByExample(example);
            if (!temp.isEmpty()) {
            	studentsTests = temp.get(0);
            } else {
            	studentsTests = new StudentsTests();
                studentsTests.setCreatedUser(userId);
                studentsTests.setModifiedUser(userId);            
                studentsTests.setAuditColumnProperties();
                studentsTests.setTestCollectionId(testCollectionId);
                studentsTests.setStudentId(studentId);
                studentsTests.setTestSessionId(testSessionId);
                studentsTests.setStatus(studentsTestsStatusId);
                studentsTests.setFinalBandId(finalBandId);
                
                if(testSession != null) {
                	studentsTests.setNumberOfTestsRequired(testSession.getNumberOfTestsRequired());
                	studentsTests.setCurrentTestNumber(testSession.getCurrentTestNumber());                	
                }

                //INFO if testId is null,
                //then set the ticketing for either section level or test level.
                if(isTestLevelTicketed || (testId == null && (isTestLevelTicketed || isSectionLevelTicketed))) {
                	ticketNo = studentUtil.generateRandomWord(ticketNumberLength);                	studentsTests.setTicketNo(ticketNo);
                }
                if (testId != null) {
                    studentsTests.setTestId(testId);
                }
                studentsTests.setEnrollmentId(enrollmentId);
                if(previousStudentsTestId != null){
                	studentsTests.setPreviousStudentsTestId(previousStudentsTestId);
                }
                studentsTestsDao.insert(studentsTests);
            }
        } else {
        	studentsTests = new StudentsTests();
            studentsTests.setCreatedUser(userId);
            studentsTests.setModifiedUser(userId);            
            studentsTests.setAuditColumnProperties();
            studentsTests.setTestCollectionId(testCollectionId);
            studentsTests.setStudentId(studentId);
            studentsTests.setTestSessionId(testSessionId);
            studentsTests.setStatus(studentsTestsStatusId);
            studentsTests.setFinalBandId(finalBandId);
            
            if(testSession != null) {
            	studentsTests.setNumberOfTestsRequired(testSession.getNumberOfTestsRequired());
            	studentsTests.setCurrentTestNumber(testSession.getCurrentTestNumber());                	
            }

            //INFO if testId is null,
            //then set the ticketing for either section level or test level.
            if(isTestLevelTicketed || (testId == null && (isTestLevelTicketed || isSectionLevelTicketed))) {
            	ticketNo = studentUtil.generateRandomWord(ticketNumberLength);
            	studentsTests.setTicketNo(ticketNo);
            }
            if (testId != null) {
                studentsTests.setTestId(testId);
            }
            studentsTests.setEnrollmentId(enrollmentId);
            if(previousStudentsTestId != null){
            	studentsTests.setPreviousStudentsTestId(previousStudentsTestId);
            }
            studentsTestsDao.insert(studentsTests);
        }
   	
    	StudentsTestSections studentsTestSections = null; 

    	if(testId != null){
	    	TestSectionExample tsExample = new TestSectionExample();
	    	TestSectionExample.Criteria tsCriteria = tsExample.createCriteria();
	    	tsCriteria.andTestIdEqualTo(testId);
	    	tsExample.setOrderByClause("sectionorder");
	    	List<TestSection> testSections = testSectionDao.selectByExample(tsExample);
	    	
	    	Test test = testDao.findById(testId);
	
	    	StudentsTestSectionsCriteria studentsTestSectionsCriteria = new StudentsTestSectionsCriteria();
	    	StudentsTestSectionsCriteria.Criteria stsCriteria = studentsTestSectionsCriteria.createCriteria();
	    	stsCriteria.andStudentsTestIdEqualTo(studentsTests.getId());
	    	studentsTestSectionDao.deleteByStudentsTestSectionsCriteria(studentsTestSectionsCriteria);
	
	    	if(CollectionUtils.isNotEmpty(testSections) &&
	    			!adaptiveTestCode.equalsIgnoreCase(test.getTestformatcode())) {
	    		boolean isPrevSectionHardBreak = false;
	    		Collections.sort(testSections, new Comparator<TestSection>() {
	    			public int compare(TestSection objOne, TestSection objTwo) {
						return NULL_COMPARATOR.compare(objOne.getSectionOrder(), objTwo.getSectionOrder());
	    			}
	    		}); 
	    		for(int i=0;i<testSections.size();i++) {
	    			TestSection ts = testSections.get(i);
	    			studentsTestSections = new StudentsTestSections();
	    			studentsTestSections.setStudentsTestId(studentsTests.getId());
	    			studentsTestSections.setTestSectionId(ts.getId());
	    			studentsTestSections.setCreatedUser(studentsTests.getCreatedUser());
	    			studentsTestSections.setModifiedUser(studentsTests.getModifiedUser());
	    			if(isSectionLevelTicketed && ts.getTicketed()){ 
	    				if(isPrevSectionHardBreak || ticketNo == null) {
	    					ticketNo = studentUtil.generateRandomWord(ticketNumberLength);
	    				}
	    				isPrevSectionHardBreak = ts.getHardBreak();
	    				studentsTestSections.setTicketNo(ticketNo);
	    			}
	    			studentsTestSections.setStatusId(	    					
							studentsTestSectionsStatusConfiguration.getUnUsedStudentsTestSectionsStatusCategory().getId());
	    			studentsTestSections.setAuditColumnProperties();
	    			studentsTestSectionDao.insertSelective(studentsTestSections);
	    		} 
    		}
    	} 
       
        return studentsTests;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsTestsService#findByAssessmentIdAndRoster(java.lang.Long, java.lang.Long)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<StudentsTests> findByAssessmentIdAndRoster(Long assessmentId, Long rosterId){
    	 return studentsTestsDao.findByAssessmentIdAndRoster(assessmentId, rosterId,
    			 studentsTestsStatusConfiguration.getClosedStudentsTestsStatusCategory().getId());
    }
    

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void updateStudentsTestsStatus(List<Long> studentIds,Long testSessionId,Long studentsTestsStatusId){    	
    	/*StudentsTestsExample studentsTestsExample = new StudentsTestsExample();    	
    	StudentsTestsExample.Criteria studentsTestsCriteria = studentsTestsExample.createCriteria();
    	studentsTestsCriteria.andStudentIdIn(studentIds);
    	studentsTestsCriteria.andTestSessionIdEqualTo(testSessionId);
    	*/
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    	StudentsTests studentsTests = new StudentsTests();
    	studentsTests.setStatus(studentsTestsStatusId);
    	studentsTests.setAuditColumnPropertiesForUpdate();
    	//studentsTestsDao.updateStatusByExample(studentsTests,studentsTestsExample) ;
    	
    	studentsTestsDao.updateStatusForByStudentTestSession(studentsTests, studentIds, testSessionId);
    	studentsTestsDao.releaseDependentTestSessions(studentIds, testSessionId,userDetails.getUserId());
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void updateStudentsTestsSectionsStatus(List<Long> studentIds,Long testSessionId, Long studentsTestSectionsStatusId){
    	StudentsTestSections studentsTestSections = new StudentsTestSections();
    	studentsTestSections.setStatusId(studentsTestSectionsStatusId);
    	studentsTestSections.setAuditColumnPropertiesForUpdate();
    	
    	studentsTestSectionDao.updateStatusByStudentAndTestSession(studentsTestSections, studentIds, testSessionId);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public void closeStudentsTestsStatus(List<Long> studentIds,Long testSessionId){
    	updateStudentsTestsStatus(studentIds, testSessionId,
    		studentsTestsStatusConfiguration.getClosedStudentsTestsStatusCategory().getId());
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public void closeStudentsTestAtEndTestSession(List<Long> studentIds,Long testSessionId){
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    	//update TestSections status and previous status
    	StudentsTestSections studentsTestSections = new StudentsTestSections();
    	studentsTestSections.setStatusId(studentsTestSectionsStatusConfiguration.getClosedStudentsTestSectionsStatusCategory().getId());
    	studentsTestSections.setAuditColumnPropertiesForUpdate();
    	studentsTestSectionDao.updateStatusAndPreviousStatusByStudentAndTestSession(studentsTestSections, studentIds, testSessionId);
    	
    	//update StudentsTests
    	StudentsTests studentsTests = new StudentsTests();
    	studentsTests.setStatus(studentsTestsStatusConfiguration.getClosedStudentsTestsStatusCategory().getId());
    	studentsTests.setAuditColumnPropertiesForUpdate();
    	studentsTestsDao.completeStudentTestSession(studentsTests, studentIds, testSessionId);
    	
    	//Release next testssion
    	studentsTestsDao.releaseDependentTestSessions(studentIds, testSessionId,userDetails.getUserId());
    	
    	//audit end session history
    	auditStudentsTestsHistory(testSessionId, studentIds, studentsTests, "END TEST SESSION");
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)    
    public void updateInterimThetaForAdaptiveStudents(List<Long> studentIds,Long testSessionId, Long stageId){
    	Stage stg = testSessionService.selectTestSessionStageByPrimaryKey(stageId);
    	if(stg != null &&
    		(StageEnum.STAGE1.getCode().equalsIgnoreCase(stg.getCode()) || StageEnum.STAGE2.getCode().equalsIgnoreCase(stg.getCode()))){
			for(Long studentId : studentIds){
				List<StudentsTests> stTests= findByTestSessionAndStudent(testSessionId, studentId);
				if(stTests!= null && !stTests.isEmpty()){
					String[] parameters = new String[] { "studenttest", "updateinterimthetavalue",
							stTests.get(0).getId().toString()};
					restTemplate.put(servicePath(epServiceURL, parameters), null);
					LOGGER.debug("Interim theta set for student: " + studentId + ", on stage :"+ stg.getName());
				}
			}
    	}
    }
    
    @Override
    public void closeStudentsTestSectionsStatus(List<Long> studentIds,Long testSessionId){
    	updateStudentsTestsSectionsStatus(studentIds, testSessionId,
			studentsTestSectionsStatusConfiguration.getClosedStudentsTestSectionsStatusCategory().getId());
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean reactivateStudentsTestSectionsStatus(List<Long> studentsIdsList, Long testSessionId) {
    	boolean updateSuccessful = false;
    	if (studentsIdsList != null
    			&& CollectionUtils.isNotEmpty(studentsIdsList)) {
    		StudentsTestSections studentTestSection = new StudentsTestSections();
    		studentTestSection.setAuditColumnPropertiesForUpdate();
    		studentTestSection.setStatusId(
    				studentsTestSectionsStatusConfiguration.getReactivatedStudentsTestSectionsStatusCategory(
    						).getId());

    		//Commented for US14114 Technical: Fix Test Monitor and other TDE services dependency
    		//Push students reactivation status's to TDE
    		/*List<StudentsTestSectionsStatusRequest> modifiedStudentsTestSectionsStatusRequestList = 
    				pushStatusByStudentsTestsIdsToTDE(studentsTestsIdsList, testSessionId);*/
    		
    		/*List<StudentsTestSections> modifiedStudentsTestSectionsStatusRequestList = studentsTestSectionDao.
    				selectByStudentsTestIdAndStatus(studentsTestsIdsList,
    				studentsTestSectionsStatusConfiguration.getAllTimedOutStatusIds());
    		    		
    		List<Long> modifiedStudentsTestIdsList = new ArrayList<Long>();
    		for (StudentsTestSections modified : modifiedStudentsTestSectionsStatusRequestList){
    			modifiedStudentsTestIdsList.add(modified.getStudentsTestId());
    		}*/

           
            StudentsTests studentTest = new StudentsTests();
            studentTest.setAuditColumnPropertiesForUpdate();
            studentTest.setStatus(
    				studentsTestsStatusConfiguration.getInProgressStudentsTestsStatusCategory(
    	            		).getId());
    		
            // Update all student test section to reactivated status &  Update all student tests to inprogress status
    		int rowsUpdated = studentsTestSectionDao.updateStatusForReactivationByStudentTest(
    				studentTest, studentTestSection, studentsIdsList, testSessionId);
    		
    		//Delete previously generated interim predictive results and reports
    		if(rowsUpdated > 0){
    			
    			List<InterimStudentReport> interimPredictiveStudentReports = interimStudentReportMapper.getPredictiveReportsByReActivatedTestSessionId(testSessionId, studentsIdsList);
        		
        		if(CollectionUtils.isNotEmpty(interimPredictiveStudentReports)){
        			for(InterimStudentReport isr : interimPredictiveStudentReports){
        				if(isr.getFilePath() != null && isr.getFilePath().length() > 0){
        					if (s3.doesObjectExist(REPORT_PATH + isr.getFilePath()))
        						s3.deleteObject(REPORT_PATH + isr.getFilePath());
        				}
        				//delete from interimstudentreport
        				studentReportQuestionInfoMapper.deleteStudentReportQuestionInfoByInterimStudentReportId(isr.getId());
        				interimStudentReportMapper.deleteInterimStudentReportById(isr.getId());
        			}
        		}
    		}    		
    		
    		/**
             * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15652 : Monitor test session on Test Coordination - capture reactivation history
             * Insert reactivation history for students and the test session.
             */
    		auditStudentsTestsHistory(testSessionId, studentsIdsList, studentTest, "REACTIVATION");
    		LOGGER.debug("No. of rowsUpdated "+ rowsUpdated
					+" for students tests ids "+ studentsIdsList);

			updateSuccessful= true;
		}
    	
    	return updateSuccessful;
	}

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsTestsService#getByStudentName(java.lang.String)
     */
    //Get testSessions for a student and pull all the responses from TDE for each testSession.
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getByStudentName(String studentNameKeyword, String testNameKeyword, Long organizationId) {
    	
    	List<Long> testSessionIds = null;
    	
    	if (StringUtils.hasText(studentNameKeyword) &&
    			StringUtils.hasText(testNameKeyword)) {         	
    		studentNameKeyword = '%'+studentNameKeyword.trim()+'%';
    		testNameKeyword = '%'+testNameKeyword+'%';
    	   	 
    		testSessionIds = studentsTestsDao.getByStudentName(studentNameKeyword, testNameKeyword, organizationId);
		
    		//US14114 Technical: Fix Test Monitor and other TDE services dependency
			/*for(Long testSessionId: testSessionIds) {
	            //Retrieve student responses.
	            studentsResponsesService.retrieveStudentsResponses(testSessionId);
			}*/
    	} 
		return testSessionIds;
	}
    

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsTestsService#createAutoRegistrationTestSessions(edu.ku.cete.domain.enrollment.Enrollment)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Boolean createAutoRegistrationTestSessions(KidRecord kidRecord, Enrollment enrollment) throws DuplicateTestSessionNameException {
    	boolean successful = true;
    	Map<String, String> subjectAreaMap = getAutoSubjectAreaNameMap();
    	
    	/*
    	if(StringUtils.hasText(kidRecord.getStateMathAssess())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getStateMathAssess(), subjectAreaMap.get("D74"), "D74");
    	}

    	if(StringUtils.hasText(kidRecord.getStateReadingAssess())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getStateReadingAssess(),subjectAreaMap.get("D75"), "D75");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getK8StateScienceAssess())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getK8StateScienceAssess(), subjectAreaMap.get("D76"), "D76");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getHsStateLifeScienceAssess())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getHsStateLifeScienceAssess(), subjectAreaMap.get("D77"), "D77");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getHsStatePhysicalScienceAssess())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getHsStatePhysicalScienceAssess(), subjectAreaMap.get("D78"), "D78");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getK8StateHistoryGovAssess())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getK8StateHistoryGovAssess(), subjectAreaMap.get("D79"), "D79");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getHsStateHistoryGovWorld())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getHsStateHistoryGovWorld(), subjectAreaMap.get("D80"), "D80");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getHsStateHistoryGovState())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getHsStateHistoryGovState(), subjectAreaMap.get("D81"), "D81");
    	}*/
    	
    	if(StringUtils.hasText(kidRecord.getStateMathAssess())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getStateMathAssess(), subjectAreaMap.get("D74"), "D74");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getStateELAAssessment())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getStateELAAssessment(),subjectAreaMap.get("SELAA"), "SELAA");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getStateSciAssessment())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getStateSciAssessment(), subjectAreaMap.get("SSCIA"), "SSCIA");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getStateHistGovAssessment())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getStateHistGovAssessment(), subjectAreaMap.get("SHISGOVA"), "SHISGOVA");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getGeneralCTEAssessment())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getGeneralCTEAssessment(), subjectAreaMap.get("GCTEA"), "GCTEA");
    	}
    	
    	if(StringUtils.hasText(kidRecord.getEndOfPathwaysAssessment())) {
    		successful = addOrUpdateTestSessions(kidRecord, enrollment, kidRecord.getEndOfPathwaysAssessment(), subjectAreaMap.get("EOPA"), "EOPA");
    	}
    	
    	return successful;
    }
    
    @Override
    public Map<String, String> getAutoSubjectAreaNameMap() {
    	//FIXME: move to db subject area table
    	Map<String, String> subjectAreaMap = new HashMap<String, String>();
    	/*
    	subjectAreaMap.put("D74", "StateMathAssess");
    	subjectAreaMap.put("D75", "StateReadingAssess");
    	subjectAreaMap.put("D76", "K8StateSciAssess");
    	subjectAreaMap.put("D77", "HSStateLifeSciAssess");
    	subjectAreaMap.put("D78", "HSStatePhysSciAssess");
    	subjectAreaMap.put("D79", "K8StateHistGovAssess");
    	subjectAreaMap.put("D80", "HSStateHistGovWorld");
    	subjectAreaMap.put("D81", "HSStateHistGovState");
    	*/
    	subjectAreaMap.put("D74", "StateMathAssess");
    	subjectAreaMap.put("SELAA", "StateELAAssess");
    	subjectAreaMap.put("SSCIA", "StateSciAssess");
    	subjectAreaMap.put("SHISGOVA", "StateHistGovAssess");
    	subjectAreaMap.put("GCTEA", "StateCTEAssess ");
    	subjectAreaMap.put("EOPA", "StatePathwaysAssess ");
    	
    	return subjectAreaMap;
    }
    
    /**
     * @param kidRecord
     * @param enrollment
     * @param testTypeCode
     * @param subjectAreaName
     * @param subjectAreaCode
     * @return
     * @throws DuplicateTestSessionNameException
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Boolean addOrUpdateTestSessions(KidRecord kidRecord, Enrollment enrollment, 
    		String testTypeCode, String subjectAreaName, String subjectAreaCode) throws DuplicateTestSessionNameException {
    	TestType type = testTypeDao.selectByCode(testTypeCode);
    	if (type == null){
    		LOGGER.debug("An invalid test type code was sent: "+testTypeCode+" for student: "+kidRecord.getStateStudentIdentifier());
    		return false;
    	}
    	Map<String, Object> processResult = new HashMap<String, Object>();
        boolean successful = true;
        String gradeLevel = "";
        enrollment.setCurrentContextUserId(kidRecord.getCurrentContextUserId());
        Map<String, TestType> testTypesMap = getTestTypeMap();
 
        if(kidRecord.getCurrentGradeLevel() != null) {
        	gradeLevel = kidRecord.getCurrentGradeLevel().toString();
        	if(gradeLevel.length() == 1)
        		gradeLevel = "0" + gradeLevel;
        }
        
        String testSessionName = getSessionNamePrefix(kidRecord.getAttendanceSchoolProgramIdentifier(),
        		kidRecord.getCurrentSchoolYear() , gradeLevel, subjectAreaName) +"_"+ buildTestTypeName(testTypeCode, testTypesMap);
        
        if(testTypeCode.equalsIgnoreCase("C") || testTypeCode.equalsIgnoreCase("N")) { //FOR "N" - remove any auto reg sessions
        	unEnrollStudents(kidRecord, gradeLevel, subjectAreaName );
        } else if(!testTypeCode.equals("3")) { //SKIP "3" - Alternate assessments - DLM reg
        	successful = addOrUpdateAutoSession(enrollment, testTypeCode, subjectAreaCode,
					gradeLevel, testTypesMap, testSessionName, processResult);
        }
        return successful;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Boolean removeTestSessionsOnClear(Enrollment enrollment, String testTypeCode, String subjectAreaCode)  {
        
        List<StudentsTests> studentsTests = studentsTestsDao.findByEnrollmentIdTestTypeCodeSubjectAreaCodeWithActiveOTW(
				        					enrollment.getId(), testTypeCode, subjectAreaCode, 
				        					new Integer(enrollment.getCurrentSchoolYear()));
    	
		unEnrollStudentOnExitClearRecord(enrollment, studentsTests, false);
    	return true;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Boolean removeEndOfPathwaysTestSessionsOnClear(Enrollment enrollment, String subjectAreaCode)  {
        
        List<StudentsTests> studentsTests = studentsTestsDao.findEndOfPathwaysTestsByEnrollmentIdSubjectAreaCodeWithActiveOTW(
        		enrollment.getId(), subjectAreaCode, new Integer(enrollment.getCurrentSchoolYear()).toString());
    	
		unEnrollStudentOnExitClearRecord(enrollment, studentsTests, false);
    	return true;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsTestsService#createAutoRegistrationTestSessions(edu.ku.cete.domain.enrollment.Enrollment)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public final Map<String, Object> batchCreateAutoRegistrationTestSessions(Enrollment enrollment, Long subjectAreaId, Long testTypeId, Long gradeCourseId, Long assessmentId) {

    	SubjectArea enrollmentSubjectArea = subjectAreaDao.selectByPrimaryKey(subjectAreaId);
    	TestType enrollmentTestType = testTypeDao.selectByPrimaryKey(testTypeId);
    	String gradeLevel = gradeCourseDao.findGradeCodeByGradeCourseId(gradeCourseId);
    	//Assessment assessment = assessmentDao.selectByPrimaryKey(assessmentId);	
       
    	//New student test enrollments will be created with a status of unused.
    	Map<String, Object> processResult = new HashMap<String, Object>();
    	int successCnt = 0;
    	int failedCnt = 0;
		List<Map<String, Object>> failedRecords = new ArrayList<Map<String, Object>>();
    	List<Long> successTestSessions = new ArrayList<Long>();

    	String subjectAreaCode = enrollmentSubjectArea.getSubjectAreaCode();
    	String testTypeCode = enrollmentTestType.getTestTypeCode();
    	Map<String, TestType> testTypesMap = getTestTypeMap();
        Map<String, String> subjectAreaMap = getAutoSubjectAreaNameMap();
        
    	AutoRegistrationCriteria autoRegistrationrec = getAutoRegistrationCriteria(
				testTypeCode, subjectAreaCode, gradeLevel, assessmentId);
    		
		//Get the test collection
		if(autoRegistrationrec != null) {
			List<TestCollection> testCollections = getTestCollections(autoRegistrationrec,
					gradeLevel, subjectAreaCode, testTypeCode);
		    
		    if (testCollections == null || testCollections.size() == 0) {
	        	String errorMsg = "No test collections found with this criteria - AssessmentProgramId: " + autoRegistrationrec.getAssessmentProgramId() + 
	            		" TestingProgramId: " + autoRegistrationrec.getTestingProgramId() + " AssessmentId: " + autoRegistrationrec.getAssessmentId() +
	            		"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
	        	failedCnt++;
	        	LOGGER.debug(errorMsg);
	        	processResult.put("success", false);
	        	processResult.put("reason", errorMsg);
	        	failedRecords.add(processResult);	        	
	        } else {
	    		if(enrollmentTestType.getTestTypeCode().equalsIgnoreCase("C") 
            		|| enrollmentTestType.getTestTypeCode().equalsIgnoreCase("N")) { //FOR "N" - remove any auto reg sessions
	    			//for (Enrollment enrollment : enrollments) {
    	            	unEnrollStudents(enrollment, gradeLevel, enrollmentSubjectArea.getSubjectAreaName());
	    			//}

	            	processResult.put("success", true);
	            	processResult.put("reason", "Successfully unenrolled");
	            } else if(!enrollmentTestType.getTestTypeCode().equals("3")) { //SKIP "3" - Alternate assessments - DLM reg
					Category unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
				    
					Boolean accessibleForm = null;
			    	String accessibilityFlagCode = null;
			        
			        //Check for accessible tests
			        if(testTypeCode.equals("1") 
			        		|| testTypeCode.equals("2")
			        		//|| testTypeCode.equals("R")
			        		//|| testTypeCode.equals("A")
			        		) {
			    		accessibleForm = null;
			    	} else {
				        if(subjectAreaCode.equalsIgnoreCase("D74") && 
			    				CommonConstants.AUTO_REGISTRATION_D74_TESTTYPES.contains(testTypeCode)) {
				        	accessibleForm = true;
			    		} else if(subjectAreaCode.equalsIgnoreCase("D75") && 
			    				CommonConstants.AUTO_REGISTRATION_D75_TESTTYPES.contains(testTypeCode)) {
			    			accessibleForm = true;
			    		} else if(subjectAreaCode.equalsIgnoreCase("D76") && 
			    				CommonConstants.AUTO_REGISTRATION_D76_TESTTYPES.contains(testTypeCode)) {
			    			accessibleForm = true;
			    			accessibilityFlagCode = testTypesMap.get(testTypeCode).getAccessibilityFlagCode();
			    		} else if(subjectAreaCode.equalsIgnoreCase("D77") && 
			    				CommonConstants.AUTO_REGISTRATION_D77_TESTTYPES.contains(testTypeCode)) {
			    			accessibilityFlagCode = testTypesMap.get(testTypeCode).getAccessibilityFlagCode();
			    			accessibleForm = true;
			    		} else if(subjectAreaCode.equalsIgnoreCase("D78") && 
			    				CommonConstants.AUTO_REGISTRATION_D78_TESTTYPES.contains(testTypeCode)) {
			    			accessibleForm = true;
			    			accessibilityFlagCode = testTypesMap.get(testTypeCode).getAccessibilityFlagCode();
			    		}
			    	}
			        String ttName = buildTestTypeName(enrollmentTestType.getTestTypeCode(), testTypesMap);
			        TestCollection testCollection = testCollections.get(0);
			        
			        //Get testCollection configuration values like randomization,ticketing flags.
			        // TODO testCollectionsSessionRulesDao.selectByTestCollection should not be used, but this is legacy code that doesn't use
					// operationaltestwindow, to my knowledge
					// testCollectionsSessionRulesDao.selectByOperationalTestWindowId should be used instead
			        List<TestCollectionsSessionRules> testCollectionsSesionsRulesList = testCollectionsSessionRulesDao.selectByTestCollection(testCollection.getId());
			        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(testCollectionsSesionsRulesList);
			        
			    	if(studentSessionRule.isSystemDefinedEnrollment()) {
			    		List<Test> tests = testService.findQCTestsByTestCollectionAndStatus(testCollection.getId(), 
		        				testStatusConfiguration.getPublishedTestStatusCategory().getId(), 
		        				AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, accessibleForm, accessibilityFlagCode);
			    		
			    		if(tests != null && CollectionUtils.isNotEmpty(tests)) {
			            	//for (Enrollment enrollment : enrollments) {
				            		processResult.clear();
				            		try {
				    	            	String testSessionName = getSessionNamePrefix(enrollment.getAttendanceSchoolProgramIdentifier(),
				    	                		enrollment.getCurrentSchoolYear() , gradeLevel, subjectAreaMap.get(enrollmentSubjectArea.getSubjectAreaCode())) 
				    	                		+"_"+ttName;
				    			        
				    			    	//Check if the test session already exists or not.
				    			        TestSessionExample testSessionExample = new TestSessionExample();
				    			        TestSessionExample.Criteria testSessionCriteria = testSessionExample.createCriteria();
				    			        testSessionCriteria.andNameEqualTo(testSessionName);
				    			        List<TestSession> testSessions = testSessionService.selectByExample(testSessionExample);
				    			        
				    			        if(testSessions != null && testSessions.size() == 1) {
				    			        	TestSession testSession = testSessions.get(0);
				    			        	if(testSession != null && testSession.getId() != null) {
					    			        	addStudentExistingSession(enrollment, testSession, accessibleForm);
				    			        	
				    			        		processResult.put("success", true); 
				    			        		processResult.put("testsessionid", testSession.getId());
				    			        	} 
				    			        } else {
			    				            
					    				        //If the test session does not exists, create a new test session (kind of setup test session functionality).
					    				    	TestSession testSession = new TestSession();
					    			        	testSession.setName(testSessionName);
					    			        	testSession.setStatus(unusedSession.getId());
					    				        testSession.setTestCollectionId(testCollection.getTestCollectionId());
					    				        testSession.setSource(SourceTypeEnum.BATCHAUTO.getCode());
					    				        testSession.setSchoolYear(new Long(enrollment.getCurrentSchoolYear()));
					    				        testSession = addNewTestSession(enrollment, testSession, testCollection, AARTCollectionUtil.getIds(tests), studentSessionRule, null); 
			    				                
			    				                if(testSession != null && testSession.getId() != null) {
				    				        		processResult.put("success", true); 
				    				        		processResult.put("testsessionid", testSession.getId());
				    				        	}
				    			        }
				    	            	
				    	            	if((Boolean) processResult.get("success")){
						    				successCnt++;
						    				successTestSessions.add((Long) processResult.get("testsessionid"));
						    			}else{
						    				failedCnt++;
						    				if(enrollment.getStudent() != null) {
						    					processResult.put("firstname", enrollment.getStudent().getLegalFirstName());
						    					processResult.put("lastname", enrollment.getStudent().getLegalLastName());
						    					processResult.put("studentid", enrollment.getStudent().getId());
						    				}
						    				failedRecords.add(processResult);
						    			}
			    	            	} catch(Exception e){
			    	    				LOGGER.debug("Test Session creation failed for enrollment: ", enrollment.getId(), e);
			    	    				if(enrollment.getStudent() != null) {
			    	    					processResult.put("firstname", enrollment.getStudent().getLegalFirstName());
			    	    					processResult.put("lastname", enrollment.getStudent().getLegalLastName());
			    	    					processResult.put("studentid", enrollment.getStudent().getId());
			    	    				}
			    	    				failedCnt++;
			    	    				processResult.put("success", false);
			    	    	        	processResult.put("reason", e.getMessage());
			    	    	        	failedRecords.add(processResult);
			    	    			}
				            	//}
				            } else {
				        		String errorMsg = "No tests found with this criteria : " +
					            		"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
				        		failedCnt++;
					        	processResult.put("success", false); 
					        	processResult.put("reason", errorMsg);
					            LOGGER.debug(errorMsg);
				        	}
			        } else {
			        	String errorMsg = "test collection is managed by manual, Id:" + testCollection.getId() + 
			        				"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
			        	failedCnt++;
			        	//YSC need to check why true
			        	processResult.put("success", false); 
			        	processResult.put("reason", errorMsg);
			            LOGGER.debug(errorMsg);
			        }
	            }
		    }
		} else {
        	String errorMsg = "No auto registration criteria found with : " +
        			"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
        	failedCnt++;
        	//YSC need to check why true
        	processResult.put("success", false); 
        	processResult.put("reason", errorMsg);
            LOGGER.debug(errorMsg);
        }
        //Map<String, Object> processResult = new HashMap<String, Object>();
		processResult.put("successdetails", successTestSessions);
		processResult.put("faileddetails", failedRecords);
		processResult.put("success", successCnt);
		processResult.put("failed", failedCnt);
        return processResult;
    }

	private List<TestCollection> getTestCollections(
			AutoRegistrationCriteria autoRegistrationrec, String gradeLevel,
			String subjectAreaCode, String testTypeCode) {
		List<TestCollection> testCollections;
		if(subjectAreaCode.equalsIgnoreCase("D77")) {
			testCollections =  testCollectionDao.selectTestCollectionForAutoRegistrationByCourse(autoRegistrationrec.getAssessmentProgramId(),
					autoRegistrationrec.getTestingProgramId(), autoRegistrationrec.getAssessmentId(),
					testStatusConfiguration.getPublishedTestStatusCategory().getId(),
					"LIFSCI", testTypeCode, subjectAreaCode); //"Life Science"
		} else if(subjectAreaCode.equalsIgnoreCase("D78")) {
			testCollections =  testCollectionDao.selectTestCollectionForAutoRegistrationByCourse(autoRegistrationrec.getAssessmentProgramId(),
					autoRegistrationrec.getTestingProgramId(), autoRegistrationrec.getAssessmentId(),
					testStatusConfiguration.getPublishedTestStatusCategory().getId(),
					"Phys Sci", testTypeCode, subjectAreaCode); //"Physical Science"
		} else if(subjectAreaCode.equalsIgnoreCase("D80") || subjectAreaCode.equalsIgnoreCase("D81")) {
			testCollections =  testCollectionDao.selectTestCollectionForAutoRegistrationByCourse(autoRegistrationrec.getAssessmentProgramId(),
					autoRegistrationrec.getTestingProgramId(), autoRegistrationrec.getAssessmentId(),
					testStatusConfiguration.getPublishedTestStatusCategory().getId(),
					"Am Hist", testTypeCode, subjectAreaCode); //"American History"
			
		} else {
			testCollections =  testCollectionDao.selectTestCollectionForAutoRegistrationById(autoRegistrationrec.getAssessmentProgramId(),
					autoRegistrationrec.getTestingProgramId(), autoRegistrationrec.getAssessmentId(),
					testStatusConfiguration.getPublishedTestStatusCategory().getId(),
					gradeLevel, testTypeCode, subjectAreaCode);
		}
		return testCollections;
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addStudentExistingSession(Enrollment enrollment, TestSession testSession, Boolean accessibleForm) {
		//If the test session already exists, then add students to that (kind of edit test session functionality).
		List<StudentsTests> studentsTestsList = studentsTestsDao.findByTestSessionAndStudentId(testSession.getId(), enrollment.getStudentId());
		try {
			if(studentsTestsList == null || CollectionUtils.isEmpty(studentsTestsList)) {
				enrollToTestSession(enrollment.getStudentId(), 
						testSession, accessibleForm, enrollment.getId());
			} else {
				for(StudentsTests record : studentsTestsList) {
					if(null != record) {
						record.setActiveFlag(true);
		    			record.setEnrollmentId(enrollment.getId());
		    			record.setCurrentContextUserId(enrollment.getCurrentContextUserId());
		    			record.setAuditColumnPropertiesForUpdate();
		    			studentsTestsDao.updateByPrimaryKeySelective(record);
					}
				}
			}
		} catch(Exception ex) {
			LOGGER.error("Exception occurred while editing  test session : " + testSession.getId());
		}
	}
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addStudentExistingSession(Enrollment enrollment, TestSession testSession, TestCollection testCollection, List<Test> testsInCollection, Long studentTestStatusId) {
		addStudentExistingSession(enrollment, testSession, testCollection, testsInCollection, studentTestStatusId, null);
	}
	
	//new BR
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addStudentExistingSession(Enrollment enrollment, TestSession testSession, TestCollection testCollection, List<Test> testsInCollection, Long studentTestStatusId, Long overrideUserId) {
		addStudentExistingSession(enrollment, testSession, testCollection, testsInCollection, studentTestStatusId, overrideUserId, null);
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addStudentExistingSession(Enrollment enrollment, TestSession testSession, TestCollection testCollection, List<Test> testsInCollection, Long studentTestStatusId, Long overrideUserId, Long previousStudentsTestId) {
		//If the test session already exists, then add students to that (kind of edit test session functionality).
		List<StudentsTests> studentsTestsList = studentsTestsDao.findByTestSessionAndStudentId(testSession.getId(), enrollment.getStudentId());
		if(studentsTestsList == null || CollectionUtils.isEmpty(studentsTestsList)) {
			enrollToTestSession(enrollment.getStudentId(), testSession, testCollection,testsInCollection, enrollment.getId(), studentTestStatusId, previousStudentsTestId);
		} else {
			for(StudentsTests record : studentsTestsList) {
				if(null != record) {
					record.setActiveFlag(true);
	    			record.setEnrollmentId(enrollment.getId());
	    			record.setCurrentContextUserId(enrollment.getCurrentContextUserId());
	    			record.setAuditColumnPropertiesForUpdate();
	    			if (overrideUserId != null) {
	    				record.setCreatedUser(overrideUserId);
		    			record.setModifiedUser(overrideUserId);
	    			}
	    			if(previousStudentsTestId != null){
	    				record.setPreviousStudentsTestId(previousStudentsTestId);
	    			}
	    			if(studentTestStatusId != null)
	    				record.setStatus(studentTestStatusId);
	    			studentsTestsDao.updateByPrimaryKeySelective(record);
				}
			}
		}
	}
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public TestSession addNewTestSession(Enrollment enrollment,
			TestSession testSession, TestCollection testCollection,
			List<Long> tests, StudentSessionRule studentSessionRule, Long studentsTestsStatusId, Long previousStudentsTestId)
			throws DuplicateTestSessionNameException {
		Boolean isTestTicketed = studentSessionRule.isTicketedAtTest();
		Boolean isTestSectionTicketed = studentSessionRule.isTicketedAtSection();
		if(studentsTestsStatusId == null) {
			studentsTestsStatusId = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId();
		}
		if(enrollment.getAttendanceSchoolId() > 0) {
			testSession.setAttendanceSchoolId(enrollment.getAttendanceSchoolId());
		}
	    if (randomizationAtEnrollment.equals(testCollection.getRandomizationType())) {
		    //Randomization at enrollment.
		    testSession.setId(null);
		    testSession = testSessionService.insertTestSessionForAutoRegistration(testSession);
		    Long test = tests.get(generator.nextInt(tests.size()));
		    StudentsTests studentsTests = insertStudentsTests(enrollment.getStudentId(), studentsTestsStatusId,
		       		testSession.getTestCollectionId(),test,
		       		testSession.getId(), isTestSectionTicketed, isTestTicketed,enrollment.getId(), testSession.getFinalBandId(), testSession.getCreatedUser(),
		       		true, previousStudentsTestId, testSession);
		    LOGGER.debug("Created student test: "+studentsTests.getId());
		} 
		else {
		    //Randomization at login.
		    testSession.setId(null);
		    testSession = testSessionService.insertTestSessionForAutoRegistration(testSession);                    	
		    
		    StudentsTests studentsTests = insertStudentsTests(enrollment.getStudentId(),
		    		studentsTestsStatusId, testSession.getTestCollectionId(),
		    		null, testSession.getId(), isTestSectionTicketed, isTestTicketed,enrollment.getId(), testSession.getFinalBandId(), testSession.getCreatedUser(),
		    		true, previousStudentsTestId, testSession);
		    LOGGER.debug("Created student test: "+studentsTests.getId());
		}
		return testSession;
	}
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public TestSession addNewTestSession(Enrollment enrollment,
			TestSession testSession, TestCollection testCollection,
			List<Long> tests, StudentSessionRule studentSessionRule, Long studentsTestsStatusId)
			throws DuplicateTestSessionNameException {
    	return addNewTestSession(enrollment, testSession, testCollection, tests, studentSessionRule, studentsTestsStatusId, null);
	}
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private boolean addOrUpdateAutoSession(Enrollment enrollment, String testTypeCode,
			String subjectAreaCode, String gradeLevel,
			Map<String, TestType> testTypesMap, String testSessionName, Map<String, Object> processResult)
			throws DuplicateTestSessionNameException {
		AutoRegistrationCriteria autoRegistrationrec = getAutoRegistrationCriteria(
				testTypeCode, subjectAreaCode, gradeLevel, null);
		
		//Get the test collection
		if(autoRegistrationrec != null) {
			List<TestCollection> testCollections = getTestCollections(autoRegistrationrec,
					gradeLevel, subjectAreaCode, testTypeCode);
		    
		    if (enrollment == null || testCollections == null || testCollections.size()==0) {
	        	String errorMsg = "No test collections found with this criteria - AssessmentProgramId: " + autoRegistrationrec.getAssessmentProgramId() + 
	            		" TestingProgramId: " + autoRegistrationrec.getTestingProgramId() + " AssessmentId: " + autoRegistrationrec.getAssessmentId() +
	            		"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;	        	
	        	LOGGER.debug(errorMsg);
	        	processResult.put("success", false);
	        	processResult.put("reason", errorMsg);
	        	if(enrollment != null){
		        	processResult.put("firstname", enrollment.getLegalFirstName());
					processResult.put("lastname", enrollment.getLegalLastName());
					processResult.put("studentid", enrollment.getStudentId());
	        	}
				return false;
	        }

		    //Always there should be only one testCollection for the above parameters.
		    if(testCollections != null && CollectionUtils.isNotEmpty(testCollections)) {
		    	Boolean accessibleForm = false;
		    	String accessibilityFlagCode = null;
		        
		        //Check for accessible tests
		        if(testTypeCode.equals("1") 
		        		|| testTypeCode.equals("2")
		        		//|| testTypeCode.equals("R")
		        		//|| testTypeCode.equals("A")
		        		) {
		    		accessibleForm = null;
		    	} else {
			        if(subjectAreaCode.equalsIgnoreCase("D74") && 
		    				CommonConstants.AUTO_REGISTRATION_D74_TESTTYPES.contains(testTypeCode)) {
			        	accessibleForm = true;
		    		} else if(subjectAreaCode.equalsIgnoreCase("D75") && 
		    				CommonConstants.AUTO_REGISTRATION_D75_TESTTYPES.contains(testTypeCode)) {
		    			accessibleForm = true;
		    		} else if(subjectAreaCode.equalsIgnoreCase("D76") && 
		    				CommonConstants.AUTO_REGISTRATION_D76_TESTTYPES.contains(testTypeCode)) {
		    			accessibleForm = true;
		    			accessibilityFlagCode = testTypesMap.get(testTypeCode).getAccessibilityFlagCode();
		    		} else if(subjectAreaCode.equalsIgnoreCase("D77") && 
		    				CommonConstants.AUTO_REGISTRATION_D77_TESTTYPES.contains(testTypeCode)) {
		    			accessibilityFlagCode = testTypesMap.get(testTypeCode).getAccessibilityFlagCode();
		    			accessibleForm = true;
		    		} else if(subjectAreaCode.equalsIgnoreCase("D78") && 
		    				CommonConstants.AUTO_REGISTRATION_D78_TESTTYPES.contains(testTypeCode)) {
		    			accessibleForm = true;
		    			accessibilityFlagCode = testTypesMap.get(testTypeCode).getAccessibilityFlagCode();
		    		}
		    	}
		        
		    	//Check if the test session already exists or not.
		        TestSessionExample testSessionExample = new TestSessionExample();
		        TestSessionExample.Criteria testSessionCriteria = testSessionExample.createCriteria();
		        testSessionCriteria.andNameEqualTo(testSessionName);
		        testSessionCriteria.andSchoolYearEqualTo(new Long(enrollment.getCurrentSchoolYear()));
		        List<TestSession> testSessions = testSessionService.selectByExample(testSessionExample);
		        
		        if(testSessions != null && testSessions.size() == 1) {
		        	//If the test session already exists, then add students to that (kind of edit test session functionality).
		        	List<StudentsTests> studentsTestsList = studentsTestsDao.findByTestSessionAndStudentId(testSessions.get(0).getId(), enrollment.getStudentId());
		        	try {
		        		if(studentsTestsList == null || CollectionUtils.isEmpty(studentsTestsList)) {
		        			enrollToTestSession(enrollment.getStudentId(), 
		        					testSessions.get(0), accessibleForm, enrollment.getId());
			        	} else {
			        		for(StudentsTests record : studentsTestsList) {
			        			if(null != record) {
				        			record.setEnrollmentId(enrollment.getId());
				        			record.setCurrentContextUserId(enrollment.getCurrentContextUserId());
				        			record.setAuditColumnPropertiesForUpdate();
				        			studentsTestsDao.updateByPrimaryKeySelective(record);
			        			}
			        		}
			        	}
		        	} catch(Exception ex) {
		    			LOGGER.error("Exception occurred while editing  test session : ", testSessions.get(0).getId());
		    		}
		        	if(testSessions.get(0) != null && testSessions.get(0).getId() != null) {
		        		processResult.put("success", true); 
		        		processResult.put("testsessionid", testSessions.get(0).getId());
		        	} else {
		        		String errorMsg = "No tests found with this criteria : " +
			            		"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
			        	processResult.put("success", false); 
			        	processResult.put("reason", errorMsg);
			            LOGGER.debug(errorMsg);
		        	}
		        } else {
					Category unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED,
				    		TEST_SESSION_STATUS_TYPE);
		        	//If the test session does not exists, create a new test session (kind of setup test session functionality).
		        	TestCollection testCollection = testCollections.get(0);

			    	TestSession testSession = new TestSession();
		        	testSession.setName(testSessionName);
		        	testSession.setStatus(unusedSession.getId());
			        testSession.setTestCollectionId(testCollection.getTestCollectionId());
			        testSession.setSchoolYear(new Long(enrollment.getCurrentSchoolYear()));
					if(enrollment.getAttendanceSchoolId() > 0) {
						testSession.setAttendanceSchoolId(enrollment.getAttendanceSchoolId());
					}
					
			        //Get testCollection configuration values like randomization,ticketing flags.
					// TODO testCollectionsSessionRulesDao.selectByTestCollection should not be used, but this is legacy code that doesn't use
					// operationaltestwindow, to my knowledge
					// testCollectionsSessionRulesDao.selectByOperationalTestWindowId should be used instead
			        List<TestCollectionsSessionRules> testCollectionsSesionsRulesList = 
			        		testCollectionsSessionRulesDao.selectByTestCollection(testSession.getTestCollectionId());
			        StudentSessionRule studentSessionRule = 
			        		studentSessionRuleConverter.convertToStudentSessionRule(testCollectionsSesionsRulesList);
			        Boolean isTestTicketed = studentSessionRule.isTicketedAtTest();
			    	Boolean isTestSectionTicketed = studentSessionRule.isTicketedAtSection();
			        
			        if(studentSessionRule.isSystemDefinedEnrollment()) {
						//New student test enrollments will be created with a status of unused.
					    Long studentsTestsStatusId = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId();
					    
			        	List<Test> tests = testService.findQCTestsByTestCollectionAndStatus(testSession.getTestCollectionId(), 
		        				testStatusConfiguration.getPublishedTestStatusCategory().getId(), 
		        				AUTO_REGISTRATION_VARIANT_TYPEID_CODE_ENG, accessibleForm, accessibilityFlagCode);
			        	StudentsTests studentsTests = null;
			        	if(tests != null && CollectionUtils.isNotEmpty(tests)) {
			                if (tests.size() == 1) {
			                    //Only one test is found, so it cannot be possibly randomized.
			                    testSession.setId(null);
			                    testSession.setTestId(tests.get(0).getId());
			                    testSession = testSessionService.insertTestSessionForAutoRegistration(testSession);
			
			                    studentsTests = insertStudentsTests(enrollment.getStudentId(), studentsTestsStatusId,
			                       		testSession.getTestCollectionId(), AARTCollectionUtil.getRandomElement(AARTCollectionUtil.getIds(tests)),
			                       		testSession.getId(), isTestSectionTicketed, isTestTicketed,enrollment.getId(), testSession.getFinalBandId());
			                    LOGGER.debug("Created student test: "+studentsTests.getId());
			                } else if (randomizationAtEnrollment.equals(testCollection.getRandomizationType())) {
			                    //Randomization at enrollment.
			                    testSession.setId(null);
			                    testSession = testSessionService.insertTestSessionForAutoRegistration(testSession);
			                    
			                    studentsTests = insertStudentsTests(enrollment.getStudentId(), studentsTestsStatusId,
			                       		testSession.getTestCollectionId(), AARTCollectionUtil.getRandomElement(AARTCollectionUtil.getIds(tests)),
			                       		testSession.getId(), isTestSectionTicketed, isTestTicketed,enrollment.getId(), testSession.getFinalBandId());
			                    LOGGER.debug("Created student test: "+studentsTests.getId());
			                } 
			                else {
			                    //Randomization at login.
			                    testSession.setId(null);
			                    testSession = testSessionService.insertTestSessionForAutoRegistration(testSession);                    	
			                    
			                    studentsTests = insertStudentsTests(enrollment.getStudentId(),
			                    		studentsTestsStatusId, testSession.getTestCollectionId(),
			                    		null, testSession.getId(), isTestSectionTicketed, isTestTicketed,enrollment.getId(), testSession.getFinalBandId());
			                    LOGGER.debug("Created student test: "+studentsTests.getId());
			                }                           
			            }
			        	
			        	if(testSession != null && testSession.getId() != null) {
			        		processResult.put("success", true); 
			        		processResult.put("testsessionid", testSession.getId());
			        	} else {
			        		String errorMsg = "No tests found with this criteria : " +
				            		"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
				        	processResult.put("success", false); 
				        	processResult.put("reason", errorMsg);
				            LOGGER.debug(errorMsg);
			        	}
			        } else {
			        	String errorMsg = "test collection is managed by manual, Id:" + testSession.getTestCollectionId() + " name: " + testSession.getName();
			        	//YSC need to check why true
			        	processResult.put("success", false); 
			        	processResult.put("reason", errorMsg);
			            LOGGER.debug(errorMsg);
			        }
		        }
	        } else {
	        	String errorMsg = "More than one test collection found with this criteria : " +
	            		"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
	        	//YSC need to check why true
	        	processResult.put("success", false); 
	        	processResult.put("reason", errorMsg);
	            LOGGER.debug(errorMsg);
	        }
        } else {
        	String errorMsg = "No auto registration criteria found with : " +
        			"grade: " + gradeLevel + " TestTypeCode: " + testTypeCode + " SubjectAreaCode: " + subjectAreaCode;
        	//YSC need to check why true
        	processResult.put("success", false); 
        	processResult.put("reason", errorMsg);
            LOGGER.debug(errorMsg);
        }
		return true;
	}

	private AutoRegistrationCriteria getAutoRegistrationCriteria(
			String testTypeCode, String subjectAreaCode, String gradeLevel, Long assessmentId) {
		AutoRegistrationCriteria autoRegistrationrec = null;
		if(subjectAreaCode.equalsIgnoreCase("D76") || subjectAreaCode.equalsIgnoreCase("D79")) {
			autoRegistrationrec = autoRegistrationCriteriaDao.selectByTestTypeAndSubjectAreaCode(testTypeCode, subjectAreaCode, gradeLevel, assessmentId);
		} else {
			autoRegistrationrec = autoRegistrationCriteriaDao.selectByTestTypeAndSubjectAreaCode(testTypeCode, subjectAreaCode, null, assessmentId);
		}
		return autoRegistrationrec;
	}
	
	//Get testTypeName for testTypeCode
    private String buildTestTypeName(String testTypeCode, Map<String, TestType> testTypes) {
    	//String testTypeName = testService.getTestTypeName(testTypeCode);
    	String testTypeName = "";
    	
    	if(testTypes != null && testTypes.get(testTypeCode) != null) {
    		testTypeName = testTypes.get(testTypeCode).getTestTypeName();
    		if(StringUtils.hasText(testTypeName)) {
    			testTypeName = testTypeName.substring(testTypeName.indexOf('-') + 1).replace("/", "").trim();
    		}
    	}
    	
    	return testTypeName;
    }
    
	private String getSessionNamePrefix(String attendanceSchoolProgramId,
			int currentSchoolYear, String gradeLevel, String subjectAreaName) {
		return currentSchoolYear +"_"+ attendanceSchoolProgramId +"_"+ gradeLevel +"_"+ subjectAreaName;
	}
	
	private Map<String, TestType> getTestTypeMap() {
		Map<String, TestType> testTypesMap = new HashMap<String, TestType>();
    	
        List<TestType> testTypes = enrollmentTestTypeSubjectAreaDao.selectAllTestTypes();    	
    	for(TestType testType : testTypes) {
    		testTypesMap.put(testType.getTestTypeCode(), testType);
    	}
		return testTypesMap;
	}
    
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private void unEnrollStudents(KidRecord kidRecord, String gradeLevel, String subjectAreaName) {
    	unEnrollStudent(kidRecord.getStudent().getId(),  kidRecord.getAttendanceSchoolProgramIdentifier(), 
    					kidRecord.getCurrentSchoolYear(), gradeLevel, subjectAreaName);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private void unEnrollStudents(Enrollment enrollment, String gradeLevel, String subjectAreaName) {
    	unEnrollStudent(enrollment.getStudentId(),  enrollment.getAttendanceSchoolProgramIdentifier(), 
    					enrollment.getCurrentSchoolYear(), gradeLevel, subjectAreaName);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private void unEnrollStudent(Long studentId, String attendanceSchoolProgramId, int currentSchoolYear, String gradeLevel, String subjectAreaName) {
    	String testSessionName = null;
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    	//Long studentsTestId;
    	
    	testSessionName = "%" + getSessionNamePrefix(attendanceSchoolProgramId,
				currentSchoolYear, gradeLevel, subjectAreaName) + "%";
    			
    	//Check if the test session already exists or not.
        List<Long> studentsTestIds = testSessionService.selectTestSessionByStudentId(testSessionName, studentId);
        
        if(studentsTestIds != null && studentsTestIds.size()>0) {
        	for(Long studentsTestId:studentsTestIds) {
		        //Delete studentsTestSections record
		    	studentsTestSectionDao.deleteByStudentsTestId(studentsTestId);
		    	
		        //Delete studentsTests record.
		    	studentsTestsDao.deleteByStudentsTestsIds(new ArrayList<Long>(Arrays.asList(studentsTestId)),userDetails.getUserId());
        	}
        } else {
        	LOGGER.debug("Student : " +
        			studentId + " not Enrolled to Any Test to unenroll: ");
        }
    }
    
    @Override
    public void unEnrollStudent(Enrollment enrollment) {
    	//Check if the test session already exists or not.
    	Long enrollmentId = enrollment.getId();
        List<StudentsTests> studentsTests = studentsTestsDao.findByEnrollmentIdWithActiveOTW(enrollmentId);
 		unEnrollStudentOnExitClearRecord(enrollment, studentsTests, true);	
    }

    @Override
    public void unEnrollStudentFromKidsProcess(Enrollment enrollment) {
    	//Check if the test session already exists or not.
        List<StudentsTests> studentsTests = studentsTestsDao.findByEnrollmentIdWithActiveOTW(enrollment.getId());

		unEnrollStudentOnExitClearRecord(enrollment, studentsTests, true);
		
		//unEnroll from PredictiveTests
		removeStudentPredictiveTests(enrollment.getId(), new Long(enrollment.getCurrentSchoolYear()), null);
    }
    
	@Override
    public void rosterUnEnrollStudent(Long enrollmentId, Long rosterId, Long userId) {
        List<StudentsTests> studentsTests = studentsTestsDao.findByEnrollmentRosterWithActiveOTW(enrollmentId, rosterId);
		//This method is only called after checking if the student is DLM
		//DE14479 - IF this changes the below methods must be changed to only work for DLM students
        unEnrollStudentOnRosterClearRecord(rosterId, studentsTests, userId);
        itiTestSessionService.unEnrollPendingITIsByEnrlAndRosterId(enrollmentId, rosterId, testsessionRosterUnenrolledCategoryCode+"-STARTED", userId);
    }
    
	private void unEnrollStudentOnRosterClearRecord(Long rosterId,
			List<StudentsTests> studentsTests, Long userId) {

        if(studentsTests != null && studentsTests.size() > 0) {
    		Long inProgressStatusid = categoryDao.getCategoryId("inprogress", "STUDENT_TEST_STATUS");
    		Long pendingStatusid = categoryDao.getCategoryId("pending", "STUDENT_TEST_STATUS");
    		Long unusedStatusid = categoryDao.getCategoryId("unused", "STUDENT_TEST_STATUS");
    		Long completeStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
    		Long testInProgressTimedOutStatusid = categoryDao.getCategoryId("inprogresstimedout", "STUDENT_TEST_STATUS");
    		    		
        	for(StudentsTests studentsTest: studentsTests) {
        		
        		//Verify if test is in progress or unused
        		if(studentsTest != null 
        				&& (studentsTest.getStatus().equals(inProgressStatusid) 
        						|| studentsTest.getStatus().equals(testInProgressTimedOutStatusid)        						
        						|| studentsTest.getStatus().equals(unusedStatusid)
        						|| studentsTest.getStatus().equals(pendingStatusid)
        						|| studentsTest.getStatus().equals(completeStatusid))) {
        			
			        //Delete studentsTestSections record
            		List<StudentsTestSections> studentTestsSections = findStudentsTestSectionsByStudentsTestsId(studentsTest.getId());
            		if (studentTestsSections.size() > 0) {
            			for (StudentsTestSections sts : studentTestsSections) {
            				String tempSTSStatusName = generateRestorableStatusName(studentstestsectionsRosterUnenrolledCategoryCode, sts.getStatusId());
            				studentsTestSectionDao.unenrollByStudentsTestSectionsId(sts.getId(), tempSTSStatusName, 
            						sts.getCurrentContextUserId());
            			}
            		}
			    	
			        //Delete studentsTests record.
			    	String tempSTStatusName = generateRestorableStatusName(studentstestsRosterUnenrolledCategoryCode, studentsTest.getStatus());
			    	studentsTestsDao.unenrollByStudentsTestsId(studentsTest.getId(), tempSTStatusName, 
			    			studentsTest.getCurrentContextUserId());
			    	
			    	//Delete testsession record
    			    TestSession ts = testSessionService.findByPrimaryKey(studentsTest.getTestSessionId());
    			    String tempTSStatusName = generateRestorableStatusName(testsessionRosterUnenrolledCategoryCode, ts.getStatus());
			    	testSessionService.unenrollTestSession(studentsTest.getTestSessionId(), tempTSStatusName, 
			    			studentsTest.getCurrentContextUserId());
			    	
			    	//Clear StudentTracker TestSession
			    	/** studentTrackerService.clearTestSessionByStudentIdAndTestSessionId(studentsTest.getStudentId(), 
			    			studentsTest.getTestSessionId(), userId); **/
			    	
		    		//Unenroll ITI Plans
	    			ItiTestSessionHistory history = itiTestSessionService.selectITISessionHistoryByStudentIdAndTestSessionId(studentsTest.getStudentId(),studentsTest.getTestSessionId());
	    			if (history != null) {
		    			String tempITIStatusName = generateRestorableStatusName(testsessionRosterUnenrolledCategoryCode, history.getStatus());
				    	itiTestSessionService.unenrollITIPlansByStudentAndTestSessionId(studentsTest.getStudentId(),tempITIStatusName, 
				    			studentsTest.getTestSessionId(), studentsTest.getCurrentContextUserId());
	    			}
			    	LOGGER.debug("Student roster : " + rosterId + " successfully unenrolled.");
        		}
        	}
        } else {
        	LOGGER.debug("Student : " + rosterId + " not Enrolled to Any Test to unenroll.");
        }
	}    
	
	private void unEnrollStudentOnExitClearRecord(Enrollment enrollment,
			List<StudentsTests> studentsTests, boolean exitProcess) {
		Long enrollmentId = enrollment.getId();
		Long studentId = enrollment.getStudentId();
		boolean isStudentOnlyInDLM = studentService.isStudentOnlyInDLM(studentId);
		
        if(studentsTests != null && studentsTests.size() > 0) {
    		Long testInProgressStatusid = categoryDao.getCategoryId("inprogress", "STUDENT_TEST_STATUS");
    		Long testPendingStatusid = categoryDao.getCategoryId("pending", "STUDENT_TEST_STATUS");
    		Long testUnusedStatusid = categoryDao.getCategoryId("unused", "STUDENT_TEST_STATUS");
    		Long testCompleteStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
    		Long testInProgressTimedOutStatusid = categoryDao.getCategoryId("inprogresstimedout", "STUDENT_TEST_STATUS");
    		
        	for(StudentsTests studentsTest: studentsTests) {
        		
        		//Verify if test is in progress, unused, in progress/timed out, pending or complete
        		if(studentsTest != null 
        				&& (studentsTest.getStatus().equals(testInProgressStatusid)    						
        						|| studentsTest.getStatus().equals(testUnusedStatusid)
        						|| studentsTest.getStatus().equals(testInProgressTimedOutStatusid)
        						|| studentsTest.getStatus().equals(testPendingStatusid)
        						|| (studentsTest.getStatus().equals(testCompleteStatusid) && isStudentOnlyInDLM))) {
        			
            		String testsectionsExitUnenrolledCategoryCode = studentstestsectionsExitUnenrolledCategoryCode;
            		String testsExitUnenrolledCategoryCode = studentstestsExitUnenrolledCategoryCode;
            		String sessionExitUnenrolledCategoryCode = testsessionExitUnenrolledCategoryCode;
        			
			        //Delete studentsTestSections record
            		List<StudentsTestSections> studentTestsSections = findStudentsTestSectionsByStudentsTestsId(studentsTest.getId());
            		if (studentTestsSections.size() > 0) {
            			for (StudentsTestSections sts : studentTestsSections) {
            				testsectionsExitUnenrolledCategoryCode = studentstestsectionsExitUnenrolledCategoryCode;
            				if (isStudentOnlyInDLM) {
            					testsectionsExitUnenrolledCategoryCode = generateRestorableStatusName(testsectionsExitUnenrolledCategoryCode, sts.getStatusId());
            				}
            				studentsTestSectionDao.unenrollByStudentsTestSectionsId(sts.getId(), testsectionsExitUnenrolledCategoryCode, 
            						sts.getCurrentContextUserId());
            			}
            		}
			        //Delete studentsTests record.
    				if (isStudentOnlyInDLM) {
    					testsExitUnenrolledCategoryCode = generateRestorableStatusName(testsExitUnenrolledCategoryCode, studentsTest.getStatus());
    				}
			    	studentsTestsDao.unenrollByStudentsTestsId(studentsTest.getId(), testsExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
			    	
			    	//Delete testsession record
    				if (isStudentOnlyInDLM) {
    			    	TestSession ts = testSessionService.findByPrimaryKey(studentsTest.getTestSessionId());
    					sessionExitUnenrolledCategoryCode = generateRestorableStatusName(sessionExitUnenrolledCategoryCode, ts.getStatus());
    				}
			    	testSessionService.unenrollTestSession(studentsTest.getTestSessionId(), sessionExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
			    	
			    	//Clear StudentTracker TestSession
			    	/** studentTrackerService.clearTestSessionByStudentIdAndTestSessionId(studentsTest.getStudentId(), 
			    			studentsTest.getTestSessionId(), null); */
			    	
			    	if(exitProcess) {
			    		//Unenroll ITI Plans
			    		if (isStudentOnlyInDLM) {
			    			ItiTestSessionHistory history = itiTestSessionService.selectITISessionHistoryByStudentIdAndTestSessionId(studentsTest.getStudentId(),studentsTest.getTestSessionId());
					    	//reset the status for ITI
			    			if (history != null) {
			    				sessionExitUnenrolledCategoryCode = testsessionExitUnenrolledCategoryCode;
			    				sessionExitUnenrolledCategoryCode = generateRestorableStatusName(sessionExitUnenrolledCategoryCode, history.getStatus());
			    			}
			    		}
				    	itiTestSessionService.unenrollITIPlansByStudentAndTestSessionId(studentsTest.getStudentId(),sessionExitUnenrolledCategoryCode, 
				    			studentsTest.getTestSessionId(), studentsTest.getCurrentContextUserId());
			    	}
			    	
			    	LOGGER.debug("Student enrollment : " + enrollmentId + " successfully unenrolled.");
        		}
        	}
        } else {
        	LOGGER.debug("Student : " + enrollmentId + " not Enrolled to Any Test to unenroll.");
        }
        if(exitProcess) {
        	String sessionExitUnenrolledCategoryCode = testsessionExitUnenrolledCategoryCode;
        	if (isStudentOnlyInDLM) {
        		sessionExitUnenrolledCategoryCode += "-STARTED";
        	}
        	itiTestSessionService.unEnrollPendingITIsByEnrollmentId(enrollmentId, sessionExitUnenrolledCategoryCode);
        }
	}
    
	private String generateRestorableStatusName(String statusBase, Long currentStatusId) {
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
		map.put("STUDENT_TEST_STATUS", Arrays.asList("inprogress", "inprogresstimedout", "pending", "unused", "complete"));
		map.put("STUDENT_TESTSECTION_STATUS", Arrays.asList("inprogress", "inprogresstimedout", "pending", "unused", "complete"));
		map.put(CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE, Arrays.asList(
			CommonConstants.IAP_STATUS_STARTED,
			CommonConstants.IAP_STATUS_COMPLETED_WITH_NO_TESTLET,
			CommonConstants.IAP_STATUS_COMPLETED_WITH_TESTLET,
			CommonConstants.IAP_STATUS_CANCELED
		));
		
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			List<Category> statuses = categoryDao.getCategoriesByCategoryTypeCodeAndCategoryCodes(entry.getKey(), entry.getValue());
			if (CollectionUtils.isNotEmpty(statuses)) {
				for (Category status : statuses) {
					if (status != null && status.getId() != null && status.getId().equals(currentStatusId)) {
						return statusBase + "-" + status.getCategoryCode();
					}
				}
			}
		}
		
		//if it's not one of the above statuses, then leave it in the original status
		return statusBase;
	}
	
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<StudentsTests> getByStateStudentIdAndTest(String stateStudentIdentifier, Long testId, Long attendanceSchoolId) {
    	return studentsTestsDao.getByStateStudentIdAndTest(stateStudentIdentifier, testId, attendanceSchoolId);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean fcUnenrollStudentsTests(long testSessionId, User user) {
    	
    	List<StudentsTests> studentsTestsList = studentsTestsDao.findByTestSession(testSessionId);
    	List<Long> studentsTestsIds = new ArrayList<Long>();
    	for (StudentsTests test : studentsTestsList){
    		if (test.getId() != null)
    		 studentsTestsIds.add(test.getId());
    	}
    	
    	Category fcunSectionCategory = categoryService.selectByCategoryCodeAndType(fcUnenrolledStudentTestSection, STUDENT_TEST_SECTION_STATUS_TYPE);
    	
    	StudentsTestSections studentTestSection = new StudentsTestSections();
    	studentTestSection.setStatusId(fcunSectionCategory.getId());
    	studentTestSection.setStatus(fcunSectionCategory);
    	studentTestSection.setModifiedDate(new Date());
    	studentTestSection.setModifiedUser(user.getId());
    	
    	studentsTestSectionDao.updateStatusByStudentTestIds(studentTestSection, studentsTestsIds);
    	
        StudentsTests studentsTests = new StudentsTests();

        Category fcunCategory = categoryService.selectByCategoryCodeAndType(fcUnenrolledStudentTest, STUDENT_TEST_STATUS_TYPE);
        studentsTests.setStatus(fcunCategory.getId());
        studentsTests.setStudentTestStatus(fcunCategory);
        studentsTests.setModifiedDate(new Date());
        studentsTests.setModifiedUser(user.getId());
        studentsTests.setActiveFlag(false);

        
        StudentsTestsExample example = new StudentsTestsExample();
        StudentsTestsExample.Criteria criteria = example.createCriteria();
        criteria.andTestSessionIdEqualTo(testSessionId);

        studentsTestsDao.updateByExampleSelective(studentsTests, example);
        
        return true;
    }
    
    @Override
    @Transactional
    public final boolean fcMidUnenrollStudentsTests(long testSessionId, User user) {

    	List<StudentsTests> studentsTestsList = studentsTestsDao.findByTestSession(testSessionId);
    	List<Long> studentsTestsIds = new ArrayList<Long>();
    	for (StudentsTests test : studentsTestsList){
    		if (test.getId() != null)
    		 studentsTestsIds.add(test.getId());
    	}
    	
    	Category fcunSectionCategory = categoryService.selectByCategoryCodeAndType(fcMidUnenrolledStudentTestSection, STUDENT_TEST_SECTION_STATUS_TYPE);
    	
    	StudentsTestSections studentTestSection = new StudentsTestSections();
    	studentTestSection.setStatusId(fcunSectionCategory.getId());
    	studentTestSection.setStatus(fcunSectionCategory);
    	studentTestSection.setModifiedDate(new Date());
    	studentTestSection.setModifiedUser(user.getId());
    	
    	studentsTestSectionDao.updateStatusByStudentTestIds(studentTestSection, studentsTestsIds);    	
    	
        StudentsTests studentsTests = new StudentsTests();

        Category fcmidunCategory = categoryService.selectByCategoryCodeAndType(fcMidUnenrolledStudentTest, STUDENT_TEST_STATUS_TYPE);
        studentsTests.setStatus(fcmidunCategory.getId());
        studentsTests.setStudentTestStatus(fcmidunCategory);
        studentsTests.setModifiedDate(new Date());
        studentsTests.setModifiedUser(user.getId());
        studentsTests.setActiveFlag(false);

        
        StudentsTestsExample example = new StudentsTestsExample();
        StudentsTestsExample.Criteria criteria = example.createCriteria();
        criteria.andTestSessionIdEqualTo(testSessionId);

        studentsTestsDao.updateByExampleSelective(studentsTests, example);
        
        return true;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean pnpUnenrollStudentsTests(long testSessionId, long studentId, User user) {
    	
    	List<StudentsTests> studentsTestsList = studentsTestsDao.findByTestSessionAndStudentId(testSessionId, studentId);
    	List<Long> studentsTestsIds = new ArrayList<Long>();
    	for (StudentsTests test : studentsTestsList){
    		if (test.getId() != null)
    		 studentsTestsIds.add(test.getId());
    	}
    	
    	if (CollectionUtils.isNotEmpty(studentsTestsIds)) {
	    	Category pnpUnenrollSectionCategory = categoryService.selectByCategoryCodeAndType(pnpUnenrolledStudentTestSection, STUDENT_TEST_SECTION_STATUS_TYPE);
	    	StudentsTestSections studentTestSection = new StudentsTestSections();
	    	studentTestSection.setStatusId(pnpUnenrollSectionCategory.getId());
	    	studentTestSection.setStatus(pnpUnenrollSectionCategory);
	    	studentTestSection.setModifiedDate(new Date());
	    	studentTestSection.setModifiedUser(user.getId());
	    	studentTestSection.setActiveFlag(false);
	    	studentsTestSectionDao.updateStatusByStudentTestIds(studentTestSection, studentsTestsIds);
	    	
	        StudentsTests studentsTests = new StudentsTests();
	        Category pnpUnenrollTestCategory = categoryService.selectByCategoryCodeAndType(pnpUnenrolledStudentTest, STUDENT_TEST_STATUS_TYPE);
	        studentsTests.setStatus(pnpUnenrollTestCategory.getId());
	        studentsTests.setStudentTestStatus(pnpUnenrollTestCategory);
	        studentsTests.setModifiedDate(new Date());
	        studentsTests.setModifiedUser(user.getId());
	        studentsTests.setActiveFlag(false);
	
	        StudentsTestsExample example = new StudentsTestsExample();
	        StudentsTestsExample.Criteria criteria = example.createCriteria();
	        criteria.andTestSessionIdEqualTo(testSessionId);
	        criteria.andStudentIdEqualTo(studentId);
	        studentsTestsDao.updateByExampleSelective(studentsTests, example);
    	}
        return true;
    }
    
    private List<Long> itiSessionFilterTests(List<Test> tests, List<Long> sensitivityTags, long studentId){
    	List<Long> testIdsFromTests = new ArrayList<Long>();
    	if(sensitivityTags != null && !sensitivityTags.isEmpty()){
    		tests = itiTestSessionService.filterTestIdsOnSensitivityTags(tests,sensitivityTags);
		}
    	if(CollectionUtils.isNotEmpty(tests)){
    		List<Test> filteredTests = dlmFilterTestsBasedOnPNP(studentId, tests);
			testIdsFromTests = AARTCollectionUtil.getIds(filteredTests);
    	}
    	LOGGER.debug("testIdsFromTests after all filter = " + testIdsFromTests);
    	return testIdsFromTests;
    }
	
	@Override
	public List<Test> dlmFilterTestsBasedOnPNP(long studentId, List<Test> tests){
		LOGGER.debug("--> filterTestsBasedOnPNP" );
		List<String> itemAttributeList = new ArrayList<String>();
		itemAttributeList.add("onscreenKeyboard");
		itemAttributeList.add("Spoken");
		itemAttributeList.add("Braille");
		List<StudentProfileItemAttributeDTO> pnpAttribuites = studentProfileService.getStudentProfileItemAttribute(studentId, itemAttributeList);
		itemAttributeList.clear();
		itemAttributeList.add("visualImpairment");//Alternate Form - Visual Impairment
		pnpAttribuites.addAll(studentProfileService.getStudentProfileItemContainer(studentId, itemAttributeList));
		Map<String, String> pnpAttributeMap = new HashMap<String, String>();
		for(StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites){
			if(pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0)
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), "false");
			else
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), pnpAttribute.getSelectedValue());
		}
		
		List<Test> filteredTests = new ArrayList<Test>();
 		List<Test> brailleTestList = new ArrayList<Test>();
		List<Test> visualImpairedTestList = new ArrayList<Test>();
		List<Test> readAloudTestList = new ArrayList<Test>();
		List<Test> otherTestList = new ArrayList<Test>();
		
		for(Test test : tests){
			if(test.getAccessibilityFlagCode() == null){
				otherTestList.add(test);
			}else if(test.getAccessibilityFlagCode().equalsIgnoreCase("braille")){
				brailleTestList.add(test);
			}else if(test.getAccessibilityFlagCode().equalsIgnoreCase("visual_impairment")){
				visualImpairedTestList.add(test);
			}else if(test.getAccessibilityFlagCode().equalsIgnoreCase("read_aloud")){
				readAloudTestList.add(test);
			} 
		}
		boolean brailleExists = pnpAttributeMap.get("Braille") != null && pnpAttributeMap.get("Braille").equalsIgnoreCase("true");
		boolean viExists = pnpAttributeMap.get("visualImpairment") != null && pnpAttributeMap.get("visualImpairment").equalsIgnoreCase("true");
		boolean spokenExists = pnpAttributeMap.get("Spoken") != null && pnpAttributeMap.get("Spoken").equalsIgnoreCase("true");

		//Braille
		if(brailleExists){
			if(brailleTestList.size() > 0){   //Only Braille = Braille Tests - VI - Other
				filteredTests.addAll(brailleTestList);
			}else{
				if(viExists){	//Braille + VI =  Braille Test - VI - Other
					if(visualImpairedTestList.size() > 0){
						filteredTests.addAll(visualImpairedTestList);
					} else if(spokenExists){	//Braille + VI + Spoken = Braille - VI - Read Aloud - Other
						if(readAloudTestList.size() > 0){
							filteredTests.addAll(readAloudTestList);
						}else{
							filteredTests.addAll(otherTestList);
						}
					}
				}
				if(!viExists && spokenExists){	//Braille + spoken =  Braille Test - Spoken - Other
					if(readAloudTestList.size() > 0){
						filteredTests.addAll(readAloudTestList);
					}else{
						filteredTests.addAll(otherTestList);
					}  
				}
				if(filteredTests.isEmpty()){ //Braille - Braille/VI/Other
					if(visualImpairedTestList.size() > 0){
						filteredTests.addAll(visualImpairedTestList);
					}else if(otherTestList.size() > 0){
						filteredTests.addAll(otherTestList);
					}
				}
			}
		} else if(viExists){ // visual Impairment
			if(visualImpairedTestList.size() > 0){ 
				filteredTests.addAll(visualImpairedTestList);
			}else{
				if(spokenExists){	 // Spoken
					if(readAloudTestList.size() > 0){
						filteredTests.addAll(readAloudTestList);
					}else{ //other
						filteredTests.addAll(otherTestList);
					}
				}
			}
			if(filteredTests.isEmpty()){ //VI - VI/Other
				if(otherTestList.size() > 0){
					filteredTests.addAll(otherTestList);
				}
			}
		} else if(spokenExists){ // Spoken
			if(readAloudTestList.size() > 0){ 
				filteredTests.addAll(readAloudTestList);
			}else{ //other
				filteredTests.addAll(otherTestList);
			}
		}else{
			filteredTests.addAll(otherTestList);
		}
		LOGGER.debug("<-- filterTestsBasedOnPNP" );
 		return  filteredTests;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findCompletedIdsByTestExternalIdWithTaskVariants(Long testExternalId, List<Long> taskVariantIds, Long limit) {
		return studentsTestsDao.findCompletedIdsByTestExternalIdWithTaskVariants(testExternalId, taskVariantIds, limit);
	}
	
	@Override
	public List<StudentsTests> getExistingMultiAssignSessions(Long studentId, Long contentAreaId, Long gradeCourseId, Long multiAssignTestWindowId) {
		return studentsTestsDao.findExistingMultiAssignTestSessions(studentId, contentAreaId, gradeCourseId, multiAssignTestWindowId);
	}

	@Override
	public List<StudentsTestSections> findStudentsTestSectionsByStudentsTestsId(Long studentsTestsId) {
		return studentsTestSectionDao.selectByStudentsTestsId(studentsTestsId);
	}

	@Override
	public TestSession addNewTestSessionForAllTestsInCollection(Enrollment enrollment,
			TestSession testSession, TestCollection testCollection,
			List<Long> tests, StudentSessionRule studentSessionRule, Long studentsTestsStatusId)
			throws DuplicateTestSessionNameException {
		boolean isTestTicketed = studentSessionRule == null ? false : studentSessionRule.isTicketedAtTest();
		boolean isTestSectionTicketed = studentSessionRule == null ? false : studentSessionRule.isTicketedAtSection();
		if(studentsTestsStatusId == null) {
			studentsTestsStatusId = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId();
		}
		if(enrollment.getAttendanceSchoolId() > 0) {
			testSession.setAttendanceSchoolId(enrollment.getAttendanceSchoolId());
		}
		testSession.setId(null);
		testSession = testSessionService.insertTestSessionForAutoRegistration(testSession);
		insertAllStudentsTests(enrollment.getStudentId(), studentsTestsStatusId, testSession.getTestCollectionId(), tests,
			testSession.getId(), isTestSectionTicketed, isTestTicketed,enrollment.getId(), testSession.getFinalBandId(), testSession.getCreatedUser());
		return testSession;
	}
	
	/**
	 * This is mainly for CPASS enrollment. There was no unique way to identify enrollments to a specific test in a test session if the student
	 * was supposed to be enrolled to every test in a collection...so we have to change the process a bit (delete old ones, then re-add). Slightly
	 * hacky and redundant, but it works.
	 * @param studentId
	 * @param studentsTestsStatusId
	 * @param testCollectionId
	 * @param testIds
	 * @param testSessionId
	 * @param isSectionLevelTicketed
	 * @param isTestLevelTicketed
	 * @param enrollmentId
	 * @param finalBandId
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private List<StudentsTests> insertAllStudentsTests(long studentId, long studentsTestsStatusId,
    		long testCollectionId, List<Long> testIds,
    		Long testSessionId, boolean isSectionLevelTicketed, boolean isTestLevelTicketed, 
			Long enrollmentId, Long finalBandId, Long userId) {
		LOGGER.trace("--> insertAllStudentsTests");
		List<StudentsTests> ret = new ArrayList<StudentsTests>();
		
		// Check to see if a record already exists for the student-test session
		// pair.
		List<StudentsTests> existingStudentsTests = studentsTestsDao.findByTestSessionAndStudentId(testSessionId, studentId);
		
		if (!existingStudentsTests.isEmpty()) {
			for (StudentsTests existingStudentsTest : existingStudentsTests) {
				LOGGER.debug("removing existing studentstestsid " + existingStudentsTest.getId());
				
				StudentsTestSectionsCriteria studentsTestSectionsCriteria = new StudentsTestSectionsCriteria();
				StudentsTestSectionsCriteria.Criteria stsCriteria = studentsTestSectionsCriteria.createCriteria();
				stsCriteria.andStudentsTestIdEqualTo(existingStudentsTest.getId());
				studentsTestSectionDao.deleteByStudentsTestSectionsCriteria(studentsTestSectionsCriteria);
				
				studentsTestsDao.deleteByPrimaryKey(existingStudentsTest.getId());
			}
		}
		for (Long testId : testIds) {
			LOGGER.debug("adding enrollment id " + enrollmentId + " to test session id " + testSessionId + " with test id " + testId);
			StudentsTests st = insertStudentsTests(studentId, studentsTestsStatusId, testCollectionId, testId, testSessionId, isSectionLevelTicketed, isTestLevelTicketed, 
				enrollmentId, finalBandId, userId, false, null, null);
			ret.add(st);
		}
		LOGGER.trace("<-- insertAllStudentsTests");
		return ret;
	}

	@Override
	public void addStudentExistingSessionAllTests(Enrollment enrollment, TestSession testSession, TestCollection testCollection,
			List<Test> testsInCollection, Long studentTestStatusId) {
		//If the test session already exists, then add students to that (kind of edit test session functionality).
		List<StudentsTests> studentsTestsList = studentsTestsDao.findByTestSessionAndStudentId(testSession.getId(), enrollment.getStudentId());
		if(studentsTestsList == null || CollectionUtils.isEmpty(studentsTestsList)) {
			List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(testSession.getOperationalTestWindowId());
	        StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
	        boolean ticketedAtSection = false, ticketedAtTest = false;
	        if (studentSessionRule != null) {
	        	ticketedAtSection = studentSessionRule.isTicketedAtSection();
	        	ticketedAtTest = studentSessionRule.isTicketedAtTest();
	        }
	        if(studentTestStatusId == null) {
				studentTestStatusId = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory().getId();
			}
	        insertAllStudentsTests(enrollment.getStudentId(), studentTestStatusId, testCollection.getId(), AARTCollectionUtil.getIds(testsInCollection), testSession.getId(),
	        		ticketedAtSection, ticketedAtTest, enrollment.getId(), testSession.getFinalBandId(), null);
		} else {
			for(StudentsTests record : studentsTestsList) {
				if(null != record) {
					record.setActiveFlag(true);
	    			record.setEnrollmentId(enrollment.getId());
	    			record.setCurrentContextUserId(enrollment.getCurrentContextUserId());
	    			record.setAuditColumnPropertiesForUpdate();
	    			studentsTestsDao.updateByPrimaryKeySelective(record);
				}
			}
		}
	}
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean reactivateStudentsTestSections(List<Long> studentIds, Long testSessionId) {
		boolean updateSuccessful = false;
    	if (studentIds != null && CollectionUtils.isNotEmpty(studentIds)) {
    		List<Long> eligibleStudentIds = new ArrayList<Long>();
    		List<Long> inEligibleStudentIds = findInEligibleStudentsForReactivation(studentIds, testSessionId);
    		for(Long studentId : studentIds){
    			// Check if not in ineligiblestudentids
    			if(!inEligibleStudentIds.contains(studentId)){
    				eligibleStudentIds.add(studentId);
    			}
    		}
	    	TestSession testSession = testSessionService.findByPrimaryKey(testSessionId);
	    	boolean isAdaptiveTest = (testSession.getTestPanelId() != null) ? true: false;
	    	// For adaptive tests only reactivate test sections/stages.
	    	if(isAdaptiveTest){
	    		if(!eligibleStudentIds.isEmpty()) {
	    			StudentsTestSections studentTestSection = new StudentsTestSections();
		    		studentTestSection.setAuditColumnPropertiesForUpdate();
		    		Category reactivationCategory = studentsTestSectionsStatusConfiguration.getReactivatedStudentsTestSectionsStatusCategory();
		    		studentTestSection.setStatusId(reactivationCategory.getId());
		
		            StudentsTests studentTest = new StudentsTests();
		            studentTest.setAuditColumnPropertiesForUpdate();
		            Category inProgressCategory = studentsTestsStatusConfiguration.getInProgressStudentsTestsStatusCategory();
		            studentTest.setStatus(inProgressCategory.getId());
		    		
		            // Update all student test section to reactivated status &  Update all student tests to inprogress status
		    		int rowsUpdated = studentsTestSectionDao.updateStatusForReactivationByStudentTest(
		    				studentTest, studentTestSection, eligibleStudentIds, testSessionId);
		    		if(!eligibleStudentIds.isEmpty()){
		    			int resetRowCount = studentsTestsDao.resetSuccessorStudentTestOnReactivation(studentTest.getModifiedUser(), 
			    				new Date(), testSessionId, eligibleStudentIds);
		    			LOGGER.debug("No. of rows updated to unused status for inprogress successor studenttests : "+ resetRowCount);
		    		}
		    		
		    		/**
		             * Monitor test session on Test Coordination - capture reactivation history
		             * Insert reactivation history for students and the test session.
		             */
		    		auditStudentsTestsHistory(testSessionId, eligibleStudentIds, studentTest, "REACTIVATION");
		    		LOGGER.debug("No. of rowsUpdated " + rowsUpdated + " for students tests ids "+ studentIds);
	    		}
				updateSuccessful= true;
	    	} 
    	}
	    	return updateSuccessful;
	}

	private void auditStudentsTestsHistory(Long testSessionId, List<Long> eligibleStudentIds, StudentsTests studentTest, String source) {
		if(eligibleStudentIds != null && eligibleStudentIds.size()>0){
			for(Long studentId : eligibleStudentIds){
				List<StudentsTests> stests = studentsTestsDao.findByTestSessionAndStudentId(testSessionId, studentId);
				if(stests != null && stests.size()>0){
					StudentsTests stest = stests.get(0);
					
					if(stest != null){
		    			StudentsTestsHistory studentsTestsHistory = new StudentsTestsHistory();
		    			studentsTestsHistory.setStudentsTestsId(stest.getId());
		    			studentsTestsHistory.setAction(source);
		    			studentsTestsHistory.setStudentsTestsStatusId(stest.getStatus());
		    			studentsTestsHistory.setActedUser((int) (long)studentTest.getModifiedUser());
		    			studentsTestsHistory.setActedDate(studentTest.getModifiedDate());
		                
		                studentsTestsHistoryDao.insert(studentsTestsHistory);
					} else {
						LOGGER.debug("There is not any students test available for given studentId="+studentId+" and testSessionId="+testSessionId);
					}
				} else {
					LOGGER.debug("There is not any students tests available for given studentId="+studentId+" and testSessionId="+testSessionId);
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findInEligibleStudentsForReactivation(List<Long> studentsIds, Long testSessionId) {
		List<Long> ineligibleStudentIds = new ArrayList<Long>();
		for(Long studentId : studentsIds){
			Long successorCompleteCount = studentsTestsDao.getCompleteSuccessorStageCount(testSessionId, studentId);
			// If successor stage is reactivated then it is ineligible for reactivation
			Boolean isSuccessorReactivated = studentsTestsDao.isSuccessorStageReactivated(testSessionId, studentId);
			if(successorCompleteCount > 0 || isSuccessorReactivated){
				ineligibleStudentIds.add(studentId);
			}
		}
		return ineligibleStudentIds;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public void saveDailyAccessCodes(List<DailyAccessCode> accessCodes) {
	    SqlSession sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
		for (DailyAccessCode accessCode : accessCodes) {
			sqlSession.insert("edu.ku.cete.model.DailyAccessCodeMapper.insertSelective", accessCode);
		}
	}
	
	@Override
	public String getRandomWord() {
		//do not change..any changes check DAC processer and run process to make sure word is not repeated
		int totalWords = studentPasswordDao.selectWordCount(); 
		String pwd = studentPasswordDao.selectRandomWord(generator.nextInt(totalWords));
		LOGGER.debug("Retrieved word from database {}", pwd);
		return pwd;
	}
	
	@Override
	public String getRandomWordForStudentPassword(int passwordLength) {
		//do not change..any changes check DAC processer and run process to make sure word is not repeated
		int totalWords = studentPasswordDao.selectWordCountForStudentPassword(passwordLength); 
		String pwd = studentPasswordDao.selectRandomWordForStudentPassword(generator.nextInt(totalWords),passwordLength);
		LOGGER.debug("Retrieved word from database {}", pwd);
		return pwd;
	}
	
	/**
	 * @param uri
	 *            {@link String}
	 * @param parameters
	 *            {@link String}
	 * @return servicePath {@link String}
	 */
	private String servicePath(String uri, String... parameters) {
		LOGGER.trace("Entering the servicePath method");
		LOGGER.debug("Parameters: uri: {}, parameters: {}", new Object[] { uri,
				parameters });

		StringBuilder sb = new StringBuilder(uri);

		for (String parameter : parameters) {
			sb.append("/");
			sb.append(parameter);
		}

		LOGGER.debug("Returning string: {}", sb.toString());
		LOGGER.trace("Leaving the servicePath method");
		return sb.toString();
	}
	
	@Override
	public List<StudentsTests> getCompletedStudentsTests(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long studentId, Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId) {
		return studentsTestsDao.findCompletedStudentsTests(assessmentId, testTypeId, contentAreaId,gradeCourseId, studentId,currentSchoolYear,stageId,sourceType.getCode(), operationalTestWindowId);
	}

	@Override
	public List<StudentsTests> findExistingFixedAssignTestSessions(Long studentId, Long enrollmentId, Long contentAreaId,
			String gradeCourseAbbrName, Long operationalTestWindowId) {		
		return studentsTestsDao.findExistingFixedAssignTestSessions(studentId, enrollmentId, contentAreaId, gradeCourseAbbrName, operationalTestWindowId);
	}
	
	@Override
	public List<DailyAccessCode> getAccessCodes(Long assessmentProgramId, Date effectiveDate, List<Map<Long, Long>> cgList, Long stateId, Boolean isPLTW) {
		return dailyAccessCodeDao.findByApDateSubjectAndGrade(assessmentProgramId, effectiveDate, cgList, stateId, isPLTW);
	}
	
	@Override
	public List<DailyAccessCode> getAccessCodes(Long assessmentProgramId, Date effectiveDate) {
		return dailyAccessCodeDao.findByApAndDate(assessmentProgramId, effectiveDate);
	}
	
	@Override
	public Stage getNextStageByTestSession(Long testSessionId) {
		return studentsTestsDao.getNextStageByTestSession(testSessionId);
	}
	
	@Override
	public List<Stage> getAllStagesByTestSession(Long testSessionId, Long studentId, Long operationalTestWindowId) {
		return studentsTestsDao.getAllStagesByTestSession(testSessionId, studentId, operationalTestWindowId);
	}
	
	@Override
    public final String getStudentsTestStageStatus(Enrollment enrollment, Long testTypeId, Long subjectAreaId, Boolean isElpa)  {
        
        List<StudentsTests> studentsTests = studentsTestsDao.findByEnrollmentIdTestTypeIdSubjectAreaIdWithActiveOTW(
				        					enrollment.getId(), testTypeId, subjectAreaId, 
				        					new Integer(enrollment.getCurrentSchoolYear()));
        
        if(studentsTests == null || studentsTests.isEmpty()){
        	return NO_STAGE;
        }else{
        	Long completedStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
        	Long processLCSStatusid = categoryDao.getCategoryId("PROCESS_LCS_RESPONSES", "STUDENT_TEST_STATUS");
        	String stageCompletion = NO_STAGE;
        	if(isElpa){
        		for(StudentsTests studentsTest: studentsTests) {
	        		if(studentsTest != null) {
	        			if(!(studentsTest.getStatus().equals(completedStatusid) || studentsTest.getStatus().equals(processLCSStatusid))){
	        				return STAGE_EXISTS; 
	        			}
	        		}
	        	}
        		return ALL_STAGES_COMPLETED;
        	}else{
        		Long inprogressStatusid = categoryDao.getCategoryId("inprogress", "STUDENT_TEST_STATUS");
            	Long unusedStatusid = categoryDao.getCategoryId("unused", "STUDENT_TEST_STATUS");
 	        	for(StudentsTests studentsTest: studentsTests) {
	        		//Verify if test is completed
	        		if(studentsTest != null) {
	        			if((studentsTest.getStatus().equals(completedStatusid)) && studentsTest.getTestSession().getStageCode().equals(StageEnum.STAGE2.getCode())){
	        				return STAGE2_COMPLETED;
	        			}else if(studentsTest.getStatus().equals(completedStatusid) && studentsTest.getTestSession().getStageCode().equals(StageEnum.STAGE1.getCode())){
	        				stageCompletion = STAGE1_COMPLETED;
	        			}else if((studentsTest.getStatus().equals(inprogressStatusid) || studentsTest.getStatus().equals(unusedStatusid)) 
	        					&& studentsTest.getTestSession().getStageCode().equals(StageEnum.STAGE1.getCode())){
	        				return STAGE_EXISTS;
	        			}
	        		}
	        	}
	        	return stageCompletion;
	        }
        }
    }
	
	/**
	 * 
	 * @param studentsTests
	 * @return boolean
	 */
	private boolean hasIncompleteKelpaTests(List<StudentsTests> studentsTests){
		if(studentsTests != null && studentsTests.size() > 0) {
        	long completedStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
        	long processLCSStatusid = categoryDao.getCategoryId("PROCESS_LCS_RESPONSES", "STUDENT_TEST_STATUS");
    		for(StudentsTests studentsTest: studentsTests) {
        		if(studentsTest != null && studentsTest.getTestSession().getStageCode() != null) {
        			if((studentsTest.getStatus() != completedStatusid && studentsTest.getStatus() != processLCSStatusid) &&
        					(studentsTest.getTestSession().getStageCode().equals(StageEnum.READING.getCode())
        					|| studentsTest.getTestSession().getStageCode().equals(StageEnum.SPEAKING.getCode())
        					|| studentsTest.getTestSession().getStageCode().equals(StageEnum.WRITING.getCode())
        					|| studentsTest.getTestSession().getStageCode().equals(StageEnum.LISTENING.getCode()))){
        				return true;
        			}
        		}
    		}
    		return false;
		}
   		return false;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void removeKelpaTestSessionsOnClear(Enrollment enrollment, String testTypeCode, String subjectAreaCode)  {
        
        List<StudentsTests> studentsTests = studentsTestsDao.findByEnrollmentIdTestTypeCodeSubjectAreaCodeWithActiveOTW(
				        					enrollment.getId(), testTypeCode, subjectAreaCode, 
				        					new Integer(enrollment.getCurrentSchoolYear()));
    	
        if(hasIncompleteKelpaTests(studentsTests)) {
        	LOGGER.debug("Some of the stages are not completed. Clear all tests on transfer/exit/clear");
    		unEnrollKelpaStudentOnExitClearRecord(enrollment.getId(), studentsTests, false);
    	}
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void unEnrollKelpaStudentOnExitClearRecord(Long enrollmentId,
			List<StudentsTests> studentsTests, boolean exitProcess) {
        if(studentsTests != null && studentsTests.size() > 0) {
        	for(StudentsTests studentsTest: studentsTests) {
        		if(studentsTest != null) {
        			//Delete studentsTestSections record
			    	studentsTestSectionDao.unenrollByStudentsTestId(studentsTest.getId(), studentstestsectionsExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
			    	
			        //Delete studentsTests record.
			    	studentsTestsDao.unenrollByStudentsTestsId(studentsTest.getId(), studentstestsExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
			    	
			    	//Delete testsession record
			    	testSessionService.unenrollTestSession(studentsTest.getTestSessionId(), testsessionExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
			    	LOGGER.debug("Student enrollment : " + enrollmentId + " successfully unenrolled tests.");
        		}
        	}
        } else {
        	LOGGER.debug("Student : " + enrollmentId + " not Enrolled to Any Test to unenroll.");
        }
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findIdsByTestSession(List<Long> testSessionIds) {
		return studentsTestsDao.findIdsByTestSession(testSessionIds);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findStudentTestSectionsIdsByStudentsTests(List<Long> studentsTestsIds) {
		return studentsTestSectionDao.findIdsByStudentsTests(studentsTestsIds);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findIdsByTestSessionForDeactivation(List<Long> testSessionIdsForDeactivation) {
		return studentsTestsDao.findIdsByTestSessionForDeactivation(testSessionIdsForDeactivation);
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> findStudentTestSectionsIdsByStudentsTestsForDeactivation(List<Long> studentsTestIdsForDeactivation) {
		return studentsTestSectionDao.findStudentTestSectionsIdsByStudentsTestsForDeactivation(studentsTestIdsForDeactivation);
	}
	
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateSectionsWithStatus(List<Long> studentsTestSectionsIds, String newStatusPrefix, Long modifiedUserId) {
    	for (Long studentsTestSectionsId : studentsTestSectionsIds) {
    		studentsTestSectionDao.deactivateByPrimaryKeyWithStatus(studentsTestSectionsId, newStatusPrefix, modifiedUserId);
    	}
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateSectionsForGradeChange(List<Long> studentsTestSectionsIds, Long modifiedUserId) {
    	deactivateSectionsWithStatus(studentsTestSectionsIds, TestSessionServiceImpl.GRADE_CHANGE_INACTIVATED_PREFIX, modifiedUserId);
    }
	
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateWithStatus(List<Long> studentsTestIdsForDeactivation, String newStatusPrefix, Long modifiedUserId) {
    	for (Long studentsTestIdForDeactivation : studentsTestIdsForDeactivation) {
    		studentsTestsDao.deactivateByPrimaryKeyWithStatus(studentsTestIdForDeactivation, newStatusPrefix, modifiedUserId);
    	}
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deactivateForGradeChange(List<Long> studentsTestIdsForDeactivation, Long modifiedUserId) {
    	deactivateWithStatus(studentsTestIdsForDeactivation, TestSessionServiceImpl.GRADE_CHANGE_INACTIVATED_PREFIX, modifiedUserId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void reactivateForGradeChange(List<Long> studentsTestIdsForDeactivation, Long modifiedUserId) {
    	for (Long studentsTestIdForDeactivation : studentsTestIdsForDeactivation) {    	
    		studentsTestsDao.reactivateByPrimaryKeyForGradeChange(studentsTestIdForDeactivation, modifiedUserId);
    	}
    }    
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void reactivateSectionsForGradeChange(List<Long> studentsTestSectionsIds, Long modifiedUserId) {
    	for (Long studentsTestSectionsId : studentsTestSectionsIds) {    	
    		studentsTestSectionDao.reactivateByPrimaryKeyForGradeChange(studentsTestSectionsId, modifiedUserId);
    	}
    }  
    
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> getCompletedStudentsTestsForKELPAStudent(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long studentId, Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId) {
		return studentsTestsDao.findCompletedStudentsTestsForKELPAStudent(assessmentId, testTypeId, contentAreaId,gradeCourseId, studentId,currentSchoolYear,stageId,sourceType.getCode(), operationalTestWindowId);
	}
	
	/* sudhansu.b
	 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
	 */	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<RosterAutoScoringStudentsTestsMap> getStudentsTestForAutoScoring(Long testSessionId, Integer schoolYear, Long subjectAreaId,
			Integer offset, Integer pageSize){       		
		return studentsTestsDao.getStudentsTestForAutoScoring(testSessionId,schoolYear,subjectAreaId,offset,pageSize);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateTransferedTestSessionId(Long studentsTestsId, Long transferedTestsessionId, Long transferedEnrollmentId){
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		studentsTestsDao.updateTransferedTestSessionId(studentsTestsId, transferedTestsessionId, transferedEnrollmentId,userDetails.getUserId());
	}
	
	@Override
	public final String gets3Credentials(String filename) {
		LOGGER.trace("Entering the getS3Credentials method");
			
			String uriString = null;
			
			String[] parameters = new String[] { 
					"gets3credentials", filename };
			
			try {
				String url = servicePath(epServiceURL, parameters);

				ResponseEntity<JsonNode> responseEntity = restTemplate.exchange(
						url, HttpMethod.POST, null, JsonNode.class);
				LOGGER.info("s3_credentials rest call completed:"
						+ responseEntity.getStatusCode().toString());
				if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
					JsonNode response = responseEntity.getBody();
					uriString = response.toString();
				}
			} catch (Exception e) {
				LOGGER.error("error s3_credentials :", e);
			}
			return uriString;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> getStudentsWithRostersWithEnrollmentAutoScoring(List<Long> testSessionIds){       		
		return studentsTestsDao.getStudentsWithRostersWithEnrollmentAutoScoring(testSessionIds);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> getCompletedTestsInOtherSchool(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long studentId, Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId, Long enrollmentId) {
		return studentsTestsDao.findCompletedTestsInOtherSchool(assessmentId, testTypeId, contentAreaId,gradeCourseId, studentId,currentSchoolYear,stageId,sourceType.getCode(), operationalTestWindowId, enrollmentId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void inactivateStudentsTestsTestBytestSessionId(Long testSessionId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		studentsTestsDao.updateActiveFlagBytestSessionId(testSessionId,userDetails.getUserId());
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void unEnrollStudentTestsOnGradeChange(Enrollment enrollment) {
    	
        List<StudentsTests> studentsTests = studentsTestsDao.findByEnrollmentIdWithActiveOTW(enrollment.getId());
        if(CollectionUtils.isNotEmpty(studentsTests) && studentsTests.size() > 0) {    		
        	for(StudentsTests studentsTest: studentsTests) {
        		
        		//Verify if test is in progress, unused, in progress/timed out, pending or complete
        		if(studentsTest != null) {       		
        			
        			//Delete studentsTestSections record
			    	studentsTestSectionDao.unenrollByStudentsTestId(studentsTest.getId(), studentstestsectionsExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
			    	
			        //Delete studentsTests record.    				
			    	studentsTestsDao.unenrollByStudentsTestsId(studentsTest.getId(), studentstestsExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());			    	
			    	
			    	testSessionService.unenrollTestSession(studentsTest.getTestSessionId(), testsessionExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
			    	
			    	LOGGER.debug("Student enrollmentId : " + enrollment.getId() + " - StudentsTestsId: " + studentsTest.getId() + " successfully unenrolled.");
        		}
        	}
        } else {
        	LOGGER.debug("Student enrollmentId: " + enrollment.getId() + " not Enrolled to Any Test to unenroll.");
        }  
        
        
    }

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> getPredictiveTestsByEnrollmentId(Long enrollmentId, Long currentSchoolYear, Long contentAreaId) {		
		return studentsTestsDao.getPredictiveTestsByEnrollmentId(enrollmentId, currentSchoolYear, contentAreaId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void removeStudentPredictiveTests(Long enrollmentId, Long currentSchoolYear, Long contentAreaId) {
		List<StudentsTests> studentsTests = getPredictiveTestsByEnrollmentId(enrollmentId, currentSchoolYear, contentAreaId);
		
		String testsectionsExitUnenrolledCategoryCode = studentstestsectionsExitUnenrolledCategoryCode;
		String testsExitUnenrolledCategoryCode = studentstestsExitUnenrolledCategoryCode;
		String sessionExitUnenrolledCategoryCode = testsessionExitUnenrolledCategoryCode;
		for(StudentsTests studentsTest: studentsTests) {
			if(studentsTest != null){
				//Delete studentsTestSections record
	    		List<StudentsTestSections> studentTestsSections = findStudentsTestSectionsByStudentsTestsId(studentsTest.getId());
	    		if (studentTestsSections.size() > 0) {
	    			for (StudentsTestSections sts : studentTestsSections) {
	    				testsectionsExitUnenrolledCategoryCode = studentstestsectionsExitUnenrolledCategoryCode;
	    				
	    				studentsTestSectionDao.unenrollByStudentsTestSectionsId(sts.getId(), testsectionsExitUnenrolledCategoryCode, 
	    						sts.getCurrentContextUserId());
	    			}
	    			
	    			 //Delete studentsTests record.
	    			studentsTestsDao.unenrollByStudentsTestsId(studentsTest.getId(), testsExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
	    			
	    			//Delete testsession record
	    			testSessionService.unenrollTestSession(studentsTest.getTestSessionId(), sessionExitUnenrolledCategoryCode, 
			    			studentsTest.getCurrentContextUserId());
	    		}
			}    		
		}
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> findCompletedPredictiveTestsInOtherSchool(Long assessmentId, Long testTypeId,
			Long contentAreaId, Long gradeCourseId, Long studentId, Long currentSchoolYear, Long stageId,
			SourceTypeEnum sourceType, Long operationalTestWindowId, Long enrollmentId) {
		
		return studentsTestsDao.findCompletedPredictiveTestsInOtherSchool(assessmentId, testTypeId, contentAreaId, gradeCourseId, studentId, currentSchoolYear, stageId, sourceType.getCode(), operationalTestWindowId, enrollmentId);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> findCompletedPredictiveStudentsTests(Long assessmentId,Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long studentId, Long currentSchoolYear, Long stageId, SourceTypeEnum sourceType, Long operationalTestWindowId) {
		return studentsTestsDao.findCompletedPredictiveStudentsTests(assessmentId, testTypeId, contentAreaId,gradeCourseId, studentId,currentSchoolYear,stageId,sourceType.getCode(), operationalTestWindowId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> findKapSSByEnrollmentId(Long attendanceSchoolId, Integer currentSchoolYear,
			Long oldEnrollmentId) {
		List<StudentsTests> sts= studentsTestsDao.selectKapSSByEnrollmentId(oldEnrollmentId);
		/*List<StudentsTests> ret= new ArrayList<>();
		for(StudentsTests st: sts){
			if(st.getActiveFlag()){
				ret.add(st);
			}
		}*/
		return sts;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> selectKapSSByTestSessionIdAndStudentId(Long testSessionId, Long studentId) {

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Long schoolYear=userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		List<StudentsTests> sts = studentsTestsDao.selectKapSSByTestSessionIdAndStudentId(testSessionId, studentId,schoolYear);
		return sts;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Test> getFilteredTestsBasedOnStudentPNPForISmartBatchAuto(long studentId, List<Test> tests){
		LOGGER.debug("--> filterTestsBasedOnPNP for ISMART batch auto enrollment" );
		List<String> itemAttributeList = new ArrayList<String>();
		itemAttributeList.add("onscreenKeyboard");
		itemAttributeList.add("Spoken");
		List<StudentProfileItemAttributeDTO> pnpAttribuites = studentProfileService.getStudentProfileItemAttribute(studentId, itemAttributeList);
		itemAttributeList.clear();
		itemAttributeList.add("visualImpairment");//Alternate Form - Visual Impairment
		pnpAttribuites.addAll(studentProfileService.getStudentProfileItemContainer(studentId, itemAttributeList));
		Map<String, String> pnpAttributeMap = new HashMap<String, String>();
		for(StudentProfileItemAttributeDTO pnpAttribute : pnpAttribuites){
			if(pnpAttribute.getSelectedValue() == null || pnpAttribute.getSelectedValue().length() == 0)
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), "false");
			else
				pnpAttributeMap.put(pnpAttribute.getAttributeName(), pnpAttribute.getSelectedValue());
		}
		
		List<Test> filteredTests = new ArrayList<Test>();
		List<Test> visualImpairedTestList = new ArrayList<Test>();
		List<Test> readAloudTestList = new ArrayList<Test>();
		List<Test> otherTestList = new ArrayList<Test>();
		
		for(Test test : tests){
			if(test.getAccessibilityFlagCode() == null){
				otherTestList.add(test);
			}else if(test.getAccessibilityFlagCode().equalsIgnoreCase("visual_impairment")){
				visualImpairedTestList.add(test);
			}else if(test.getAccessibilityFlagCode().equalsIgnoreCase("read_aloud")){
				readAloudTestList.add(test);
			} 
		}
		
		boolean viExists = pnpAttributeMap.get("visualImpairment") != null && pnpAttributeMap.get("visualImpairment").equalsIgnoreCase("true");
		boolean spokenExists = pnpAttributeMap.get("Spoken") != null && pnpAttributeMap.get("Spoken").equalsIgnoreCase("true");

		if(viExists){ // visual Impairment
			if(visualImpairedTestList.size() > 0){ 
				filteredTests.addAll(visualImpairedTestList);
			}else{
				if(spokenExists){	 // Spoken
					if(readAloudTestList.size() > 0){
						filteredTests.addAll(readAloudTestList);
					}else{ //other
						filteredTests.addAll(otherTestList);
					}
				}
			}
			if(filteredTests.isEmpty()){ //VI - VI/Other
				if(otherTestList.size() > 0){
					filteredTests.addAll(otherTestList);
				}
			}
		} else if(spokenExists){ // Spoken
			if(readAloudTestList.size() > 0){ 
				filteredTests.addAll(readAloudTestList);
			}else{ //other
				filteredTests.addAll(otherTestList);
			}
		}else{
			filteredTests.addAll(otherTestList);
		}
		LOGGER.debug("<-- filterTestsBasedOnPNP for ISMART batch auto enrollment" );
 		return  filteredTests;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsTests> findCompletedTestsInOtherSchoolByGradeBand(Long contentAreaId, Long gradeBandId, Long studentId, Long currentSchoolYear, Long stageId,
			SourceTypeEnum sourceType, Long operationalTestWindowId, Long enrollmentId) {
		
		return studentsTestsDao.findCompletedTestsInOtherSchoolByGradeBand(contentAreaId, gradeBandId, studentId, currentSchoolYear, stageId, sourceType.getCode(), operationalTestWindowId, enrollmentId);
	}

	@Override
	public List<ViewMergeStudentDetailsDTO> getStudentTestsBasedOnStudentIds(Long[] studentIds) {
		Calendar now = Calendar.getInstance();   // Gets the current date and time
		Long currentSchoolYear = Long.valueOf(now.get(Calendar.YEAR));   		
		String categoryTypeCode = "STUDENT_TEST_STATUS";
		List<String> categoryCodes = new ArrayList<String>();
		categoryCodes.add("rosterunenrolled");
		categoryCodes.add("rosterunenrolled-inprogress");
		categoryCodes.add("rosterunenrolled-pending");
		categoryCodes.add("rosterunenrolled-unused");
		categoryCodes.add("rosterunenrolled-inprogresstimedout");
		categoryCodes.add("rosterunenrolled-complete");
			
		List<Long> specialCaseInactiveStudentTestStatus = categoryDao.getCategoryIdByCode(categoryTypeCode, categoryCodes);
		
		return studentsTestsDao.getStudentTestsBasedOnStudentIds(studentIds, specialCaseInactiveStudentTestStatus, currentSchoolYear);
	}
	
	@Override
	public List<ScoringAPIObject> getPLTWScoringData(Long assessmentProgramId, Date lastRuntime, Integer pageSize, Integer offset, boolean isNightlyRun, boolean isReprocess) {
		return studentsTestsDao.getPLTWScoringData(assessmentProgramId, lastRuntime, pageSize, offset, isNightlyRun, isReprocess);
	}
	
}