package edu.ku.cete.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.owasp.esapi.StringUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.ScoringAssignment;
import edu.ku.cete.domain.ScoringAssignmentScorer;
import edu.ku.cete.domain.ScoringAssignmentStudent;
import edu.ku.cete.domain.ScoringUploadDto;
import edu.ku.cete.domain.ScoringUploadFile;
import edu.ku.cete.domain.StudentsTestScore;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.test.TestSectionTaskVariantDetails;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.CcqScoreItemMapper;
import edu.ku.cete.model.CcqScoreMapper;
import edu.ku.cete.model.OperationalTestWindowDao;
import edu.ku.cete.model.ScoringAssignmentMapper;
import edu.ku.cete.model.ScoringAssignmentScorerMapper;
import edu.ku.cete.model.ScoringAssignmentStudentMapper;
import edu.ku.cete.model.ScoringTestMetaDataMapper;
import edu.ku.cete.model.ScoringUploadMapper;
import edu.ku.cete.model.StudentsTestScoreMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TestSessionDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.ScoringAssignmentServices;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.web.AssignScorerStudentScorerCriteriaDTO;
import edu.ku.cete.web.ScoreTestScoringCriteriaDTO;
import edu.ku.cete.web.ScoreTestSelfToHarmDTO;
import edu.ku.cete.web.ScoreTestTestSessionDTO;
import edu.ku.cete.web.ScorerStudentResourcesDTO;
import edu.ku.cete.web.ScorerTestStudentsSessionDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ScoringAssignmentServiceImpl implements ScoringAssignmentServices {

	private final static Log logger = LogFactory.getLog(ScoringAssignmentServiceImpl.class);
	String sep = java.io.File.separator;

	@Value("${session.name.date.format}")
	private String unenrollTimestamp;

	@Autowired
	private ScoringAssignmentMapper scoringAssignmentDao;

	@Autowired
	private ScoringAssignmentStudentMapper scoringAssignmentStudentDao;

	@Autowired
	private ScoringAssignmentScorerMapper scoringAssignmentScorerDao;

	@Autowired
	private StudentsTestsDao studentsTestsDao;

	@Autowired
	private RosterDao rosterDao;

	@Autowired
	private TestSessionDao testSessionDao;

	@Autowired
	private CcqScoreMapper ccqScoreMapper;

	@Autowired
	private CcqScoreItemMapper ccqScoreItemMapper;

	@Autowired
	private OperationalTestWindowDao operationalTestWindowDao;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private StudentsResponsesService studentsResponsesService;

	@Autowired
	private ScoringUploadMapper scoringUploadMapper;

	@Autowired
	private TestService testService;

	@Autowired
	private ScoringTestMetaDataMapper scoringTestMetaDataMapper;

	@Autowired
	private StudentsTestScoreMapper studentsTestScoreMapper;

	@Autowired
	private EnrollmentService enrollmentService;
	@Autowired
	private PermissionUtil permissionUtil;

	@Autowired
	private TestSessionService testSessionService;

	@Autowired
	private StudentsTestsService studentsTestsService;

	@Value("${print.test.file.path}")
	private String DOWNLOAD_FILEPATH;

	@Value("${scoring.testsession.completed}")
	private String SCORING_COMPLETED;

	@Value("${scoring.testsession.multipleTests}")
	private String MULTIPLE_TESTS;
	
	@Autowired
	private AwsS3Service s3;

	public String getRootOutputDir() {
		return DOWNLOAD_FILEPATH;
	}

	private String headerJsonStr;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final ScoringAssignment addOrUpdate(ScoringAssignment scoringAssignment) {
		scoringAssignmentDao.insert(scoringAssignment);
		Long id = scoringAssignment.getId();
		Set<Long> testIds = new HashSet<Long>();
		List<Long> studentsTestIds = new ArrayList<Long>();
		List<ScoringAssignmentStudent> scoringAssignmentStudentList = scoringAssignment.getScoringAssignmentStudent();
		for (ScoringAssignmentStudent scoringAssignmentStudent : scoringAssignmentStudentList) {
			studentsTestIds.add(scoringAssignmentStudent.getStudentsTestsId());
			scoringAssignmentStudent.setScoringAssignmentId(scoringAssignment.getId());
			testIds.add(scoringAssignmentStudent.getTestId());
			scoringAssignmentStudentDao.insert(scoringAssignmentStudent);
		}
		List<ScoringAssignmentScorer> ScoringAssignmentsScorerList = scoringAssignment.getScoringAssignmentScorer();
		for (ScoringAssignmentScorer scoringAssignmentsScore : ScoringAssignmentsScorerList) {
			scoringAssignmentsScore.setScoringAssignmentId(id);
			scoringAssignmentScorerDao.insert(scoringAssignmentsScore);
		}

		createScoringTestMetaData(testIds);
		updateKelpaScoringStatus(studentsTestIds, Long.valueOf(scoringAssignment.getCreatedUser()));
		return scoringAssignment;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void createScoringTestMetaData(Set<Long> testIds) {
		for (Long testId : testIds) {
			if (scoringTestMetaDataMapper.countNoOfItemInScoringTestMetaData(testId) == 0)
				scoringTestMetaDataMapper.insertScoringTestMetaData(testId);
		}

	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getCountTestSessionAndStudentCountForScorer(Long userId, Long stateId, Long assessmentPrgId,
			Long subjectId, Long gradeId, String sortByColumn, String sortType,
			Map<String, String> scorerTestSessionRecordCriteriaMap, boolean isScoreAllTest) {

		return scoringAssignmentDao.getCountTestSessionAndStudentCountForScorer(userId, stateId, assessmentPrgId,
				subjectId, gradeId, sortByColumn, sortType, scorerTestSessionRecordCriteriaMap, isScoreAllTest);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScoreTestTestSessionDTO> getTestSessionAndStudentCountForScorer(Long userId, Long stateId,
			Long assessmentPrgId, Long subjectId, Long gradeId, String sortByColumn, String sortType, int i,
			int limitCount, Map<String, String> scorerTestSessionRecordCriteriaMap, boolean isScoreAllTest,
			String assessmentProgramCode, Long schoolyear) {

		return scoringAssignmentDao.getTestSessionAndStudentCountForScorer(userId, stateId, assessmentPrgId, subjectId,
				gradeId, sortByColumn, sortType, i, limitCount, scorerTestSessionRecordCriteriaMap, isScoreAllTest,
				assessmentProgramCode, schoolyear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> findByContentArea(Long assessmentProgramId, Long schoolId, int currentSchoolYear) {
		return scoringAssignmentDao.findByContentArea(assessmentProgramId, schoolId, currentSchoolYear);
	}

	@Override
	public List<GradeCourse> getScoreTestGradeCourseByContentAreaId(Long contentAreaId, int currentSchoolYear,
			Long assessmentProgramId, Long schoolId) {
		return scoringAssignmentDao.selectGradeCourseByContentAreaId(contentAreaId, currentSchoolYear,
				assessmentProgramId, schoolId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScorerTestStudentsSessionDTO> getScorerStudentsAppearsForScoreTests(Long scoringAssignmentId,
			Long assessmentProgramId, Long scorerId, int currentSchoolYear, boolean isScoreAllTest) {
		return scoringAssignmentDao.getScorerStudentsAppearsForScoreTests(scoringAssignmentId, assessmentProgramId,
				scorerId, currentSchoolYear, isScoreAllTest);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getScorerStudentsAppearsForScoreTestsCount(Long scoringAssignmentId, Long assessmentProgramId,
			Long scorerId, Map<String, String> scorerTestStudentRecordCriteriaMap, int currentSchoolYear,
			boolean isScoreAllTest) {

		return scoringAssignmentDao.getScorerStudentsAppearsForScoreTestsCount(scoringAssignmentId, assessmentProgramId,
				scorerId, scorerTestStudentRecordCriteriaMap, currentSchoolYear, isScoreAllTest);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScoreTestScoringCriteriaDTO> getStudentTestScoringCriteria(Long testSessionId, Long studentId,
			Long testsId, Long variantValue) {
		// TODO Auto-generated method stub
		return scoringAssignmentDao.getStudentTestScoringCriteria(testSessionId, studentId, testsId, variantValue);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AssignScorerStudentScorerCriteriaDTO> checkAssignScoringStudentScorer(Long testSessionId,
			Long[] studentIds, Long[] scorerIds) {
		// TODO Auto-generated method stub

		return scoringAssignmentDao.checkAssignScoringStudentScorer(testSessionId, studentIds, scorerIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer checkUniqueCcqTestName(String testName) {
		// TODO Auto-generated method stub
		return scoringAssignmentDao.checkUniqueCcqTestName(testName);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getCountTestSessionAndStudentCountForScorer(Long districtId, Long schoolId, Long stateId,
			Long assessmentPrgId, Long subjectId, Long gradeId, String sortByColumn, String sortType,
			Map<String, String> scorerTestSessionRecordCriteriaMap, Long distictId, int currentSchoolYear,
			String assessmentProgramCode) {

		return scoringAssignmentDao.getCountTestSessionAndStudentCountForMonitorScorer(districtId, schoolId, stateId,
				assessmentPrgId, subjectId, gradeId, sortByColumn, sortType, scorerTestSessionRecordCriteriaMap,
				distictId, currentSchoolYear, assessmentProgramCode);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScoreTestTestSessionDTO> getTestSessionAndStudentCountForScorer(Long districtId, Long schoolId,
			Long stateId, Long assessmentPrgId, Long subjectId, Long gradeId, String sortByColumn, String sortType,
			int i, int limitCount, Map<String, String> scorerTestSessionRecordCriteriaMap, Long distictId,
			int currentSchoolYear, String assessmentProgramCode) {

		return scoringAssignmentDao.getTestSessionAndStudentCountForMonitorScorer(districtId, schoolId, stateId,
				assessmentPrgId, subjectId, gradeId, sortByColumn, sortType, i, limitCount,
				scorerTestSessionRecordCriteriaMap, distictId, currentSchoolYear, assessmentProgramCode);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScoreTestSelfToHarmDTO> getScoresHarmToSelfDetails(Long nonscorerid, Long orgId, String sortByColumn,
			String sortType, Map<String, String> monitorSelfToHarmFilters, int i, int limitCount, int currentSchoolYear,
			Long assessmentPrgId) {
		return scoringAssignmentDao.getScoresHarmToSelfDetails(nonscorerid, orgId, sortByColumn, sortType,
				monitorSelfToHarmFilters, i, limitCount, currentSchoolYear, assessmentPrgId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScorerTestStudentsSessionDTO> getMonitorCCQStudentDetails(List<Long> studentId) {

		return scoringAssignmentDao.getMonitorCCQStudentDetails(studentId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScorerTestStudentsSessionDTO> getscorerTestStudentsSessionDTOForDynamicColumn(Long scoringAssignmentId,
			Long testsessionId, Integer currentSchoolYear, Long scorerId, boolean isScoreAllTest) {
		return scoringAssignmentDao.getscorerTestStudentsSessionDTOForDynamicColumn(scoringAssignmentId, testsessionId,
				currentSchoolYear, scorerId, isScoreAllTest);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScorerStudentResourcesDTO> getScorerStudentResources(Long testSessionId) {

		return scoringAssignmentDao.getScorerStudentResources(testSessionId);
	}

	/*
	 * vkrishna_sta Interface method to suport KAP HGSS as part of feature F509
	 * Single point entry
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void reassignStudentsOnRosterChangeKapSS(Long oldEnrollmentId, Long newEnrollmentId, Long newRosterId) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		User modifiedUser = userDetails.getUser();
		List<Long> oldRosterIds = rosterDao.getOldRosterIdsByEnrollmentIdForHGSS(oldEnrollmentId);
		for (Long oldRosterId : oldRosterIds) {
			if(oldRosterId.equals(newRosterId)){
				break;
			}
			Enrollment oldEnrollment = enrollmentService.getAttendanceSchoolAndDistrictByEnrollmentId(oldEnrollmentId);
			List<StudentsTests> studentsTests = studentsTestsService.findKapSSByEnrollmentId(
					oldEnrollment.getAttendanceSchoolId(), oldEnrollment.getCurrentSchoolYear(), oldEnrollment.getId());
			if(studentsTests==null || studentsTests.isEmpty()){
				logger.debug("No StudentsTest found for enrollment"+ oldEnrollmentId.toString());
			}
			Category studentsTestsComplete = categoryService.selectByCategoryNameAndType("Complete",
					"STUDENT_TEST_STATUS");
			Category scoringComplete = categoryService.selectByCategoryCodeAndType("COMPLETED", "SCORING_STATUS");
			Boolean humanScoringTestSessionComplete = Boolean.FALSE;
			Boolean computerScoringTestSessionComplete = Boolean.FALSE;
			Boolean humanScoringComplete = Boolean.FALSE;
			StudentsTests humanScoringTest = null;
			TestSession humanScoringTestSession = null;
			Roster roster = null;
			Roster oldRoster = null;
			if(newRosterId != null){
				roster = rosterDao.getRosterJsonFormatData(newRosterId);
			}			
			
			if(oldRosterId != null){
				oldRoster = rosterDao.getRosterJsonFormatData(oldRosterId);
			}
			
			for (StudentsTests st : studentsTests) {
				TestSession ts = testSessionService.findByPrimaryKeyWithStage(st.getTestSessionId());
				if (ts.getStageCode().equalsIgnoreCase("Prfrm")) {
					if (st.getScoringAssignmentId() != null) {
						ScoringAssignment scoringAssignment = scoringAssignmentDao
								.getByTestSessionAndRoster(st.getTestSessionId(), oldRosterId);
						if (scoringAssignment != null) {
							ScoringAssignmentStudent scoringAssignmentStudent = scoringAssignmentStudentDao
									.getByScoringAssignmentByIdAndstudentTestid(scoringAssignment.getId(), st.getId());
							if (scoringAssignmentStudent != null
									&& scoringAssignmentStudent.getKelpaScoringStatus() != null
									&& scoringAssignmentStudent.getKelpaScoringStatus()
											.equals(scoringComplete.getId())) {
								humanScoringComplete = Boolean.TRUE;
							}
							if (st.getStatus().equals(studentsTestsComplete.getId())) {
								humanScoringTestSessionComplete = Boolean.TRUE;
							}
							humanScoringTest = st;
							humanScoringTestSession = testSessionDao
									.getTestSessionByTestSessionId(st.getTestSessionId());
						}

					} else
						logger.debug("No Scoring Assignment Mapped to Stage 2 Test");

				} else {
					if (st.getStatus().equals(studentsTestsComplete.getId())) {
						computerScoringTestSessionComplete = Boolean.TRUE;
					}
				}

			}
			if (newEnrollmentId == null) {
				/*
				 * Case1: Exit Scenario We will delete scoring assignment if
				 * test is incomplete
				 */
				if (!humanScoringTestSessionComplete) {
					if(humanScoringTest != null){
						deleteScoringAssignment(humanScoringTest, modifiedUser);
					}					
				}
				return;
			}
			if(humanScoringTest== null){
				logger.debug("No Human scoring test found for Endollment: " +oldEnrollmentId.toString() );
				return;
			}

			Enrollment newEnrollment = enrollmentService.getAttendanceSchoolAndDistrictByEnrollmentId(newEnrollmentId);

			/*
			 * Case 2: Transfer inside a district
			 * 
			 */
			if (oldEnrollment.getAttendanceSchoolDistrictId().equals(newEnrollment.getAttendanceSchoolDistrictId())) {

				if (humanScoringTestSessionComplete && !humanScoringComplete) {
					/*
					 * 1.1 If Human Scoring test is complete and Scoring
					 * assignment is not complete, Scoring Assignment should
					 * stay in old school. So No Code Change Required, As No New
					 * Student Test will be created for the Student as it is
					 * already complete
					 */
				}
				if (!computerScoringTestSessionComplete && !humanScoringTestSessionComplete) {
					/*
					 * 1.2 If both tests are not complete, Create New
					 * TestSession, Will be taken care in KIDS, NO Code Change
					 * Required for now
					 */
					/*
					 * Deleting Old Scoring Assignment
					 */
					if(humanScoringTest != null){
						deleteScoringAssignment(humanScoringTest, modifiedUser);
					}					
				}

				if (computerScoringTestSessionComplete && !humanScoringTestSessionComplete) {
					/*
					 * 1.3, If Computer Scoring is complete, Human Scoring Stage
					 * is Incomplete, Create a New Scoring Assignment for new
					 * Roster
					 */
					// Deleting old Scoring Assignment
					if(humanScoringTest != null){
						deleteScoringAssignment(humanScoringTest, modifiedUser);

						createScoringAssignment(humanScoringTest, newRosterId, modifiedUser, newEnrollmentId,
								humanScoringTestSession, roster, oldRoster);
					}					

				}

			} else {
				/*
				 * Case 3: Transfer OutSide district
				 */
				if (!humanScoringTestSessionComplete && !computerScoringTestSessionComplete) {
					/*
					 * 2.2 When Both Stages are incomplete, New Test Session and
					 * Scoring Assignment are created So we will not need to
					 * handle code in this function- just dellete existing
					 * scoring assignment
					 */
					if(humanScoringTest != null){
						deleteScoringAssignment(humanScoringTest, modifiedUser);
					}					

				}
				if (humanScoringTestSessionComplete && !computerScoringTestSessionComplete) {
					/*
					 * 2.4 If Human Scoring Test Session is Complete, It will be
					 * moved to new school and new Test Assignment is needed
					 */
					if(humanScoringTest != null){
						deleteScoringAssignment(humanScoringTest, modifiedUser);
						createScoringAssignment(humanScoringTest, newRosterId, modifiedUser, newEnrollmentId,
								humanScoringTestSession, roster, oldRoster);
					}
					
				}

			}
		}

	}

	/*
	 * sudhansu.b Added for US19233 - KELPA2 Auto Assign Teachers Scoring
	 * Assignment
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void reAssignStudentsOnChangeRoster(Long enrollmentId, Long oldRosterId, Long newRosterId,
			User modifiedUser) {
		logger.debug("reAssignStudentsOnChangeRoster Started with  enrollmentId: " + enrollmentId + " oldRosterId: "
				+ oldRosterId + " newRosterId: " + newRosterId);

		if (enrollmentId != null && modifiedUser != null && newRosterId != null) {
			List<StudentsTests> studentsTests = studentsTestsDao.findKELPAByEnrollmentId(enrollmentId);

			Date now = new Date();
			Roster roster = rosterDao.getRosterJsonFormatData(newRosterId);
			Roster oldRoster = rosterDao.getRosterJsonFormatData(oldRosterId);
			TestSession testSession = null;
			for (StudentsTests studentsTest : studentsTests) {
				testSession = testSessionDao.getTestSessionByTestSessionId(studentsTest.getTestSessionId());
				/*
				 * If scoringAssignmentId is null, then this a new student added
				 * to test session so assignment will be created in auto scoring
				 * scheduler..
				 * 
				 * If scoringAssignmentId is not null then already student is
				 * mapped to test session so we need to change the assignment
				 */
				if (roster != null && testSession != null) {
					// Getting old Scored items
					List<ScoringAssignment> scoringAssignments = scoringAssignmentDao
							.getAssignmentsByStudentsTestId(studentsTest.getId());// for
																					// multiple
																					// roster
																					// scenario

					for (ScoringAssignment scoringAssignment : scoringAssignments) {
						if (scoringAssignment.getRosterId() != null) {
							scoringAssignmentStudentDao.deleteByScoringAssignmentByIdAndstudentTestid(
									scoringAssignment.getId(), studentsTest.getId(), modifiedUser.getId());
						}
					}
					createScoringAssignment(studentsTest, newRosterId, modifiedUser, enrollmentId, testSession, roster,
							oldRoster);

				}
			}
		}
		logger.debug("reAssignStudentsOnChangeRoster End");
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteScoringAssignment(StudentsTests studentsTest, User modifiedUser) {
		List<ScoringAssignment> scoringAssignments = scoringAssignmentDao
				.getAssignmentsByStudentsTestId(studentsTest.getId());// for
																		// multiple
																		// roster
																		// scenario
		Set<Long> affectedScoringassignmentIds = new HashSet<Long>();

		for (ScoringAssignment scoringAssignment : scoringAssignments) {
			if (scoringAssignment.getRosterId() != null) {
				scoringAssignmentStudentDao.deleteByScoringAssignmentByIdAndstudentTestid(scoringAssignment.getId(),
						studentsTest.getId(), modifiedUser.getId());
				affectedScoringassignmentIds.add(scoringAssignment.getId());
			}
		}
		removeAssignmentOnLastStudentexit(affectedScoringassignmentIds,modifiedUser.getId());
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void createScoringAssignment(StudentsTests studentsTest, Long newRosterId, User modifiedUser,
			Long enrollmentId, TestSession testSession, Roster roster, Roster oldRoster) {
		Date now = new Date();
		ScoringAssignment scoringAssignment = scoringAssignmentDao
				.getByTestSessionAndRoster(studentsTest.getTestSessionId(), newRosterId);

		if (scoringAssignment != null) {// Already one assignment is
										// present with the roster
										// and test session
			ScoringAssignmentStudent existingScoringAssignmentStudent = scoringAssignmentStudentDao
					.getByScoringAssignmentByIdAndstudentTestid(scoringAssignment.getId(), studentsTest.getId());
			if (existingScoringAssignmentStudent != null) {
				if (!existingScoringAssignmentStudent.getActive()) {
					existingScoringAssignmentStudent.setActive(true);
					scoringAssignmentStudentDao.updateByPrimaryKey(existingScoringAssignmentStudent);
				}

			} else {
				ScoringAssignmentStudent newScoringAssignmentStudent = new ScoringAssignmentStudent();
				newScoringAssignmentStudent.setActive(true);
				newScoringAssignmentStudent.setCreatedDate(new Date());
				newScoringAssignmentStudent.setCreatedUser(modifiedUser.getId().intValue());
				newScoringAssignmentStudent.setScoringAssignmentId(scoringAssignment.getId());
				newScoringAssignmentStudent.setStudentId(studentsTest.getStudentId());
				newScoringAssignmentStudent.setStudentsTestsId(studentsTest.getId());
				newScoringAssignmentStudent.setEnrollmentId(enrollmentId);
				newScoringAssignmentStudent.setTestId(studentsTest.getTestId());

				scoringAssignmentStudentDao.insert(newScoringAssignmentStudent);
			}
		} else {// Creating new assignment with new roster
			if (studentsTest.getScoringAssignmentId() != null) { // To
																	// avoid
																	// non
																	// scorable
																	// test
																	// sessions
				scoringAssignment = new ScoringAssignment();
				scoringAssignment.setCreatedUser(modifiedUser.getId().intValue());
				scoringAssignment.setTestSessionId(studentsTest.getTestSessionId());
				scoringAssignment.setCreatedDate(now);
				scoringAssignment.setActive(true);
				scoringAssignment
						.setCcqTestName(testSession.getName().trim() + "_" + roster.getCourseSectionName().trim() + "_"
								+ roster.getTeacher().getUniqueCommonIdentifier().trim());
				scoringAssignment.setRosterId(newRosterId);
				scoringAssignment.setSource(SourceTypeEnum.TANSFER_ROSTER.getCode());

				scoringAssignmentDao.insertSelective(scoringAssignment);

				ScoringAssignmentStudent scoringAssignmentStudent = new ScoringAssignmentStudent();
				scoringAssignmentStudent.setScoringAssignmentId(scoringAssignment.getId());
				scoringAssignmentStudent.setStudentsTestsId(studentsTest.getId());
				scoringAssignmentStudent.setStudentId(studentsTest.getStudentId());
				scoringAssignmentStudent.setCreatedUser(modifiedUser.getId().intValue());
				scoringAssignmentStudent.setCreatedDate(now);
				scoringAssignmentStudent.setEnrollmentId(enrollmentId);
				scoringAssignmentStudent.setTestId(studentsTest.getTestId());
				scoringAssignmentStudent.setActive(true);

				scoringAssignmentStudentDao.insertSelective(scoringAssignmentStudent);

				ScoringAssignmentScorer scoringAssignmentScorer = new ScoringAssignmentScorer();
				scoringAssignmentScorer.setScoringAssignmentId(scoringAssignment.getId());
				scoringAssignmentScorer.setScorerid(roster.getTeacher().getId());
				scoringAssignmentScorer.setCreatedUser(modifiedUser.getId().intValue());
				scoringAssignmentScorer.setCreatedDate(now);
				scoringAssignmentScorer.setActive(true);

				scoringAssignmentScorerDao.insertSelective(scoringAssignmentScorer);
				createScoringTestMetaData(new HashSet<Long>(Arrays.asList(studentsTest.getTestId())));
			}
		}

		updateKelpaScoringStatus(Arrays.asList(studentsTest.getId()), modifiedUser.getId());

		studentsTestScoreMapper.moveScoreOnChangeRoster(studentsTest.getId(), oldRoster.getTeacher().getId(),
				roster.getTeacher().getId(), modifiedUser.getId());

		// Update the new scoring assignment in studentstest table
		if (scoringAssignment != null) {
			List<Long> studentsTestsList = new ArrayList<Long>();
			studentsTestsList.add(studentsTest.getId());
			studentsTestsDao.updateScoringAssignment(scoringAssignment.getId(), studentsTestsList,
					modifiedUser.getId());
		}
	}

	/*
	 * sudhansu.b Added for US19233 - KELPA2 Auto Assign Teachers Scoring
	 * Assignment
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void reAssignStudentsOnExitStudent(Long enrollmentId, Date exitDate, User modifiedUser) {
		logger.debug("reAssignStudentsOnExitStudent Started with enrollmentId : " + enrollmentId + " exitDate : "
				+ exitDate.toString());

		List<StudentsTests> studentsTests = studentsTestsDao.findKELPAByEnrollmentId(enrollmentId);
		Date now = new Date();

		Category scorinigCompleted = categoryService.selectByCategoryCodeAndType("COMPLETED", "SCORING_STATUS");

		Set<Long> affectedScoringassignmentIds = new HashSet<Long>();

		boolean isTestCompleted = true;
		Long testCompleteId = null;
		Long testInprogressId = null;
		Long testUnusedId = null;

		List<Category> testCategories = categoryService.selectByCategoryType("STUDENT_TEST_STATUS");
		for (Category category : testCategories) {
			if ("complete".equalsIgnoreCase(category.getCategoryCode())) {
				testCompleteId = category.getId();
			}
			if ("inprogress".equalsIgnoreCase(category.getCategoryCode())) {
				testInprogressId = category.getId();
			}
			if ("unused".equalsIgnoreCase(category.getCategoryCode())) {
				testUnusedId = category.getId();
			}
		}
		List<ScoringAssignmentStudent> removeAssignmentList = new ArrayList<ScoringAssignmentStudent>();
		TestSession testSession = null;
		List<OperationalTestWindow> otws = new ArrayList<OperationalTestWindow>();

		for (StudentsTests sTest : studentsTests) {
			testSession = testSessionDao.getTestSessionByTestSessionId(sTest.getTestSessionId());
			otws = operationalTestWindowDao.selectTestWindowById(testSession.getOperationalTestWindowId());
			if (otws.size() > 0 && now.getTime() < otws.get(0).getExpiryDate().getTime()) {// Check
																							// operationaltestwindow
																							// expired
																							// or
																							// not
				if (otws.get(0).getEffectiveDate().getTime() < exitDate.getTime()
						&& otws.get(0).getExpiryDate().getTime() > exitDate.getTime()) {// Then
																						// check
																						// exit
																						// date
																						// in
																						// between
					List<ScoringAssignmentStudent> scoringAssignmentStudentList = scoringAssignmentStudentDao
							.getByStudentsTestId(sTest.getId());
					if (scoringAssignmentStudentList.size() > 0) {
						removeAssignmentList.addAll(scoringAssignmentStudentList);
						if (scoringAssignmentStudentList.get(0).getKelpaScoringStatus() != null
								&& scoringAssignmentStudentList.get(0).getKelpaScoringStatus()
										.longValue() == scorinigCompleted.getId().longValue()) {
							if (studentsResponsesService.getNoOfNotResponsesMachineScoreItems(sTest.getId()) == 0) {// Check
																													// All
																													// other
																													// non-rubric
																													// items
																													// are
																													// completed
																													// or
																													// not
								if (sTest.getStatus().longValue() == testInprogressId.longValue()
										|| sTest.getStatus().longValue() == testUnusedId.longValue()) {// if
																										// all
																										// items
																										// completed
																										// then
																										// mark
																										// the
																										// test
																										// as
																										// complete
									sTest.setModifiedDate(now);
									sTest.setModifiedUser(modifiedUser.getId());
									sTest.setStatus(testCompleteId);
									sTest.setActiveFlag(true);
									studentsTestsDao.updateByPrimaryKeySelective(sTest);
								}
							}
						}
					}

					if (sTest.getStatus().longValue() != testCompleteId.longValue()) {// check
																						// student
																						// completed
																						// the
																						// test
																						// for
																						// this
																						// studentstest
						isTestCompleted = false;
					}

				}
			}
		}

		if (!isTestCompleted) {// To confirm test completed by student or not
			Set<Long> removeStudentsTestId = new HashSet<Long>();
			for (ScoringAssignmentStudent scoringAssignmentStudent : removeAssignmentList) {
				affectedScoringassignmentIds.add(scoringAssignmentStudent.getScoringAssignmentId());
				removeStudentsTestId.add(scoringAssignmentStudent.getStudentsTestsId());
				scoringAssignmentStudent.setActive(false);
				scoringAssignmentStudent.setModifiedUser(modifiedUser.getId());
				scoringAssignmentStudentDao.updateByPrimaryKeySelective(scoringAssignmentStudent);
			}

			for (Long studentsTestId : removeStudentsTestId) {
				studentsTestScoreMapper.removeScore(studentsTestId, modifiedUser.getId());
			}

			removeAssignmentOnLastStudentexit(affectedScoringassignmentIds, modifiedUser.getId());
		}

		logger.debug("reAssignStudentsOnExitStudent End");
	}

	private void removeAssignmentOnLastStudentexit(Set<Long> affectedScoringassignmentIds, Long modifiedUser) {
		List<Long> students = new ArrayList<Long>();
		ScoringAssignment scoringAssignment = null;
		for (Long id : affectedScoringassignmentIds) {
			students.clear();
			students = scoringAssignmentStudentDao.getStudentsListByScoringAssignmentId(id);
			if (students.isEmpty()) {
				scoringAssignment = scoringAssignmentDao.selectByPrimaryKey(id);

				scoringAssignment.setActive(false);
				scoringAssignment.setModifiedUser(modifiedUser);
				addTimestampToAssignmentName(scoringAssignment);

				scoringAssignmentDao.updateByPrimaryKey(scoringAssignment);
			}
		}
	}

	@Override
	public ScoringAssignment getByTestSessionAndRoster(Long testSessionId, Long rosterId) {
		return scoringAssignmentDao.getByTestSessionAndRoster(testSessionId, rosterId);
	}

	@Override
	public List<Long> getStudentsListByScoringAssignmentId(Long scoringAssignmentId) {
		return scoringAssignmentStudentDao.getStudentsListByScoringAssignmentId(scoringAssignmentId);
	}

	@Override
	public String getMonitorStageDetails(Long prevScoringAssignmentScorerId) {
		return scoringAssignmentStudentDao.getMonitorStageDetails(prevScoringAssignmentScorerId);
	}

	private void addTimestampToAssignmentName(ScoringAssignment scoringAssignment) {
		SimpleDateFormat sdf = new SimpleDateFormat(unenrollTimestamp);
		String now = sdf.format(new Date());
		scoringAssignment.setCcqTestName(scoringAssignment.getCcqTestName() + "-" + now);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Stage> getUploadScoresStage(Long[] schoolId, Long subjectId, Long gradeId) {
		return testSessionDao.getUploadScoresStage(schoolId, subjectId, gradeId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Stage> getStageByGradeIdForAssignScorers(Long assessmentProgramId, Long schoolId,
			Long contentAreaId, Long gradeId, int currentSchoolYear, String assessmentCode) {
		return testSessionDao.getStageByGradeIdForAssignScorers(assessmentProgramId, schoolId, contentAreaId, gradeId,
				currentSchoolYear, assessmentCode);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<TestSession> getUploadScoresTestSessions(Long assessmentProgramId, String assessmentCode,
			Long subjectId, Long gradeId, Long stageId, Long[] schoolIds, Long scorerId, boolean isScoreAllTest,
			Long currentSchoolYear) {
		return testSessionDao.getUploadScoresTestSessions(assessmentProgramId, assessmentCode, subjectId, gradeId,
				stageId, schoolIds, scorerId, isScoreAllTest, currentSchoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<TestSession> getAssignScoresTestSessions(Long assessmentPrgId, Long schoolId, Long stageId,
			Long subjectId, Long gradeId, int currentSchoolYear, String assessmentProgramCode) {
		return testSessionDao.getAssignScoresTestSessions(assessmentPrgId, schoolId, stageId, subjectId, gradeId,
				currentSchoolYear, assessmentProgramCode);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getUploadScoreSubject(Long assessmentProgramId, Long scorerId, boolean isScoreAllTest,
			Long[] schoolId) {
		return scoringAssignmentDao.getUploadScoreSubject(assessmentProgramId, scorerId, isScoreAllTest, schoolId);
	}

	@Override
	public List<GradeCourse> getUploadScoresGradeBySubjectId(Long contentAreaId, Long scorerId, boolean isScoreAllTest,
			Long[] schoolId, int currentSchoolYear) {
		return scoringAssignmentDao.getUploadScoresGradeBySubjectId(contentAreaId, scorerId, isScoreAllTest, schoolId,
				currentSchoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ScoringUploadFile getScoringAssignmentUploadFile(String fileName) {

		return scoringUploadMapper.getScoringAssignmentUploadFile(fileName);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String getScoringAssignmentStudentsList(Long districtId, Long[] schoolIds, Long[] testSessionIds,
			Long contentAreaId, Long gradeId, Long stageId, Long currentSchoolYear, boolean includeItem)
			throws IOException {
		String filePath = "";

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<String> staticColumn = new ArrayList<String>();

		Long countTestIds = scoringAssignmentDao.getCountTestSessionIds(testSessionIds);
		Long countStageIds = scoringAssignmentDao.getCountStageIds(testSessionIds);
		if (countTestIds.longValue() > 1) {
			MULTIPLE_TESTS = "multiple_test";
			return "{\"errorFound_multiplecounttests\":\"" + MULTIPLE_TESTS + "\"}";
		} else if (countStageIds.longValue() > 1) {
			MULTIPLE_TESTS = "multiple_test";
			return "{\"errorFound_multiplecounttests\":\"" + MULTIPLE_TESTS + "\"}";
		} else {
			boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
			List<ScorerTestStudentsSessionDTO> scoringAssignmentMappedStudents = scoringAssignmentDao
					.getScoringAssignmentsMappedStudentsToScore(districtId, schoolIds, testSessionIds, contentAreaId,
							gradeId, stageId, currentSchoolYear, includeItem, isScoreAllTest,
							userDetails.getUser().getId());
			List<String[]> extractRecords = new ArrayList<String[]>();

			String[] columnHeadersForStudentScoringAssignmentDownloadFile = { "" };
			staticColumn.addAll(Arrays.asList("Document ID", "Test ID", "State", "District ID", "School ID",
					"Assignment Name", "Student Last Name", "Student First Name", "Student Middle Name",
					"State Student Identifier", "Subject", "Grade", "Stage"));

			List<Category> nonScoringReason = categoryService.selectByCategoryType("KELPA_NON_SCORE_REASON_CODE");
			Collections.sort(nonScoringReason, alphabetical);

			String nonScoringReasonCodes = "";
			for (Category categoryList : nonScoringReason) {
				if (nonScoringReasonCodes.trim().isEmpty())
					nonScoringReasonCodes = categoryList.getCategoryCode();
				else {
					nonScoringReasonCodes = nonScoringReasonCodes + "," + categoryList.getCategoryCode();
				}
			}

			if (scoringAssignmentMappedStudents.size() > 0) {

				List<TestSectionTaskVariantDetails> columnDetails = getNumberOfDynamicColumn(
						scoringAssignmentMappedStudents.get(0).getTaskVariantDetails());
				ObjectMapper mapper = new ObjectMapper();
				JsonNode root = mapper.createObjectNode();
				ArrayNode jsonArray = mapper.createArrayNode();

				for (TestSectionTaskVariantDetails columnDetail : columnDetails) {
					staticColumn.add(columnDetail.getTaskVariantPosition());
					staticColumn.add(columnDetail.getTaskVariantPosition() + "-Non Score Reason");

					ObjectNode formDetailsJson = mapper.createObjectNode();
					formDetailsJson.put("mappedName", columnDetail.getTaskVariantPosition());
					formDetailsJson.put("fieldName",
							FieldName.SCORING_ITEMS.toString() + columnDetail.getTaskVariantPosition());
					formDetailsJson.put("fieldType", "number");
					formDetailsJson.put("rejectIfInvalid", "true");
					formDetailsJson.put("showError", "true");
					formDetailsJson.put("rejectIfEmpty", "false");
					if (StringUtilities.notNullOrEmpty(columnDetail.getRubricMaxScore(), true))
						formDetailsJson.put("maximum", Long.parseLong(columnDetail.getRubricMaxScore()));
					if (StringUtilities.notNullOrEmpty(columnDetail.getRubricMinScore(), true))
						formDetailsJson.put("minimum", Long.parseLong(columnDetail.getRubricMinScore()));
					jsonArray.add(formDetailsJson);

					ObjectNode nonScoreReasonDetailsJson = mapper.createObjectNode();
					nonScoreReasonDetailsJson.put("mappedName",
							columnDetail.getTaskVariantPosition() + "-Non Score Reason");
					nonScoreReasonDetailsJson.put("fieldName",
							columnDetail.getTaskVariantPosition() + FieldName.SCORING_ITEMS_REASONS.toString());
					nonScoreReasonDetailsJson.put("fieldType", "string");
					nonScoreReasonDetailsJson.put("rejectIfInvalid", "true");
					nonScoreReasonDetailsJson.put("rejectIfEmpty", "false");
					nonScoreReasonDetailsJson.put("showError", "true");
					nonScoreReasonDetailsJson.put("allowableValues", "{'',C," + nonScoringReasonCodes + "}");
					jsonArray.add(nonScoreReasonDetailsJson);
				}

				((ObjectNode) root).set("headerColumnDetails", jsonArray);

				// Convert the whole json object to string
				headerJsonStr = mapper.writeValueAsString(root);

				columnHeadersForStudentScoringAssignmentDownloadFile = staticColumn
						.toArray(columnHeadersForStudentScoringAssignmentDownloadFile);
				String downloadScoreFileName = getDownloadScoreFileName(
						scoringAssignmentMappedStudents.get(0).getDistrictDisplayIdentifier(),
						scoringAssignmentMappedStudents.get(0).getSubjectAbbreviatedName(),
						scoringAssignmentMappedStudents.get(0).getGrade());
				String folderPath = getFolderPath(userDetails, downloadScoreFileName);
				folderPath = folderPath.replaceAll(DOWNLOAD_FILEPATH, "");
				filePath = folderPath + sep + downloadScoreFileName;
				ScoringUploadFile scoringUploadFile = new ScoringUploadFile();

				Date date = new Date();
				scoringUploadFile.setActiveFlag(true);
				scoringUploadFile.setAssessmentProgramId(userDetails.getUser().getCurrentAssessmentProgramId());
				scoringUploadFile.setCreatedDate(date);
				scoringUploadFile.setCreatedUser(userDetails.getUserId());
				scoringUploadFile.setFileName(downloadScoreFileName);
				scoringUploadFile.setFilePath(filePath);
				scoringUploadFile.setHeaderColumn(headerJsonStr);
				scoringUploadFile.setTestId(scoringAssignmentMappedStudents.get(0).getTestsId());

				scoringUploadMapper.insert(scoringUploadFile);
				extractRecords.add(columnHeadersForStudentScoringAssignmentDownloadFile);

				for (ScorerTestStudentsSessionDTO scorerTestStudentsSessionDTO : scoringAssignmentMappedStudents) {
					if (scoringUploadFile.getTestId().longValue() != scorerTestStudentsSessionDTO.getTestsId()
							.longValue()) {
						MULTIPLE_TESTS = MULTIPLE_TESTS.replace("TEST_SESSION_NAME",
								scorerTestStudentsSessionDTO.getTestSessionName());
						return "{\"errorFound_multipletests\":\"" + MULTIPLE_TESTS + "\"}";
					}

					String[] dynamicColumnsItemScore = { "" };
					List<String> itemScore = new ArrayList<String>();
					List<TestSectionTaskVariantDetails> itemScoreWithColumnDetails = getNumberOfDynamicColumn(
							scorerTestStudentsSessionDTO.getTaskVariantDetails());

					itemScore.add(wrapForCSV(String.valueOf(scoringUploadFile.getId())));
					itemScore.add(wrapForCSV(String.valueOf(scorerTestStudentsSessionDTO.getTestsId())));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getStateDisplayIdentifier()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getDistrictDisplayIdentifier()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getSchoolDisplayIdentifier()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getScoringAssignmentName()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getLastName()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getFirstName()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getMi()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getStateStudentIdentifier()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getSubjectName()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getGrade()));
					itemScore.add(wrapForCSV(scorerTestStudentsSessionDTO.getStageName()));

					for (TestSectionTaskVariantDetails itemScores : itemScoreWithColumnDetails) {
						itemScore.add(
								StringUtils.isBlank(itemScores.getVariantScore()) ? "" : itemScores.getVariantScore());
						itemScore.add(StringUtils.isBlank(itemScores.getVariantNonScoreReason()) ? ""
								: itemScores.getVariantNonScoreReason());
					}
					dynamicColumnsItemScore = itemScore.toArray(dynamicColumnsItemScore);
					extractRecords.add(dynamicColumnsItemScore);
				}
				writeTOCSV(extractRecords, userDetails, downloadScoreFileName);
				return "{\"downloadScoreFileName\":\"" + downloadScoreFileName + "\"}";
			} else {
				return "{\"errorFound_scoringcompleted\":\"" + SCORING_COMPLETED + "\"}";
			}
		}
	}

	private String getDownloadScoreFileName(String districtDisplayIdentiFier, String subjectAbbreviatedName,
			String grade) {
		String fileName = "";
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMddyyyy_mm_ss");
		String currentDateStr = DATE_FORMAT.format(new Date());
		fileName = districtDisplayIdentiFier + subjectAbbreviatedName.replace("&", "") + "_" + grade + "_"
				+ currentDateStr + ".csv";
		fileName = fileName.replaceAll(" ", "_");
		return fileName;
	}

	private void writeTOCSV(List<String[]> lines, UserDetailImpl userDetails, String fileName) throws IOException {
		CSVWriter csvWriter = null;
		try {
			String folderPath = getFolderPath(userDetails, fileName);
			String csvFile = FileUtil.buildFilePath(folderPath, fileName);
			String[] splitFileName = csvFile.split("\\.");
			File file = File.createTempFile(splitFileName[0], "."+splitFileName[1]);
			csvWriter = new CSVWriter(new FileWriter(file, true), ',', CSVWriter.NO_QUOTE_CHARACTER);
			csvWriter.writeAll(lines);
			csvWriter.flush();
			s3.synchMultipartUpload(csvFile, file);
			FileUtils.deleteQuietly(file);
		} catch (IOException ex) {
			logger.error("IOException Occured:", ex);
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
	}

	private String getFolderPath(UserDetailImpl userDetails, String FileName) {
		Long folderName = userDetails.getUserId();
		String folderPath = DOWNLOAD_FILEPATH + sep + "Scoring Assignments" + sep
				+ userDetails.getUser().getContractingOrgId() + sep + folderName;
		return folderPath;
	}

	Comparator<Category> alphabetical = new Comparator<Category>() {
		public int compare(Category o1, Category o2) {
			return o1.getCategoryName().compareTo(o2.getCategoryName());
		}
	};

	private List<TestSectionTaskVariantDetails> getNumberOfDynamicColumn(
			List<TestSectionTaskVariantDetails> testSectionTaskVariantDetails) {
		List<String> distinctcolumns = new ArrayList<String>();
		List<TestSectionTaskVariantDetails> columnDetails = new ArrayList<TestSectionTaskVariantDetails>();
		Map<Object, String> studentTestletColumn = new HashMap<Object, String>();
		String groupColName = "";
		String clusterRubricMinScore = "";
		String clusterRubricMaxScore = "";
		String clusterVariantScore = "";
		String clusterVariantNonScoreReason = "";

		for (TestSectionTaskVariantDetails taskVariant : testSectionTaskVariantDetails) {
			if (!taskVariant.isClusterScoring()) {
				String tempdistinctcolumn = null;

				if (!distinctcolumns.contains(taskVariant.getTaskVariantPosition())) {
					tempdistinctcolumn = taskVariant.getTaskVariantPosition();
				}
				if (StringUtilities.notNullOrEmpty(tempdistinctcolumn, true)) {
					if (!groupColName.trim().isEmpty()) {
						String[] groupName = groupColName.toString().split(",");
						groupColName = groupName[0] + "--" + groupName[groupName.length - 1];
						TestSectionTaskVariantDetails task = new TestSectionTaskVariantDetails();
						task.setTaskVariantPosition(groupColName);
						task.setRubricMinScore(clusterRubricMinScore);
						task.setRubricMaxScore(clusterRubricMaxScore);
						
						if(StringUtils.isNumeric(clusterRubricMinScore) && StringUtils.isNumeric(clusterRubricMaxScore) && StringUtils.isNumeric(clusterVariantScore)
								&& Integer.parseInt(clusterRubricMinScore) <= Integer.parseInt(clusterVariantScore) 
								&& Integer.parseInt(clusterRubricMaxScore) >= Integer.parseInt(clusterVariantScore))
						  task.setVariantScore(clusterVariantScore);
						
						task.setVariantNonScoreReason(clusterVariantNonScoreReason);
						columnDetails.add(task);
						groupColName = "";
						clusterRubricMinScore = "";
						clusterRubricMaxScore = "";
						clusterVariantScore = "";
						clusterVariantNonScoreReason = "";
					}
					TestSectionTaskVariantDetails task = new TestSectionTaskVariantDetails();
					task.setTaskVariantPosition(tempdistinctcolumn);
					task.setRubricMinScore(taskVariant.getRubricMinScore());
					task.setRubricMaxScore(taskVariant.getRubricMaxScore());
					
					if(StringUtils.isNumeric(taskVariant.getRubricMinScore()) && StringUtils.isNumeric(taskVariant.getRubricMaxScore()) && StringUtils.isNumeric(taskVariant.getVariantScore())
							&& Integer.parseInt(taskVariant.getRubricMinScore()) <= Integer.parseInt(taskVariant.getVariantScore()) 
							&& Integer.parseInt(taskVariant.getRubricMaxScore()) >= Integer.parseInt(taskVariant.getVariantScore()))
					  task.setVariantScore(taskVariant.getVariantScore());
					
					task.setVariantNonScoreReason(taskVariant.getVariantNonScoreReason());
					columnDetails.add(task);
				}
			} else {
				if (studentTestletColumn.containsKey(taskVariant.getTestletId())) {
					groupColName = studentTestletColumn.get(taskVariant.getTestletId());
					groupColName = groupColName + "," + taskVariant.getTaskVariantPosition();
					clusterRubricMinScore = taskVariant.getRubricMinScore();
					clusterRubricMaxScore = taskVariant.getRubricMaxScore();
					
					if(StringUtils.isNumeric(taskVariant.getRubricMinScore()) && StringUtils.isNumeric(taskVariant.getRubricMaxScore()) && StringUtils.isNumeric(taskVariant.getVariantScore())
							&& Integer.parseInt(taskVariant.getRubricMinScore()) <= Integer.parseInt(taskVariant.getVariantScore()) 
							&& Integer.parseInt(taskVariant.getRubricMaxScore()) >= Integer.parseInt(taskVariant.getVariantScore()))
					clusterVariantScore = taskVariant.getVariantScore();
					
					clusterVariantNonScoreReason = taskVariant.getVariantNonScoreReason();
					studentTestletColumn.put(taskVariant.getTestletId(), groupColName);
				} else {
					studentTestletColumn.put(taskVariant.getTestletId(), taskVariant.getTaskVariantPosition());
					if (!groupColName.trim().isEmpty()) {
						String[] groupName = groupColName.toString().split(",");
						groupColName = groupName[0] + "--" + groupName[groupName.length - 1];
						TestSectionTaskVariantDetails task = new TestSectionTaskVariantDetails();
						task.setTaskVariantPosition(groupColName);
						task.setRubricMinScore(clusterRubricMinScore);
						task.setRubricMaxScore(clusterRubricMaxScore);
						
						if(StringUtils.isNumeric(clusterRubricMinScore) && StringUtils.isNumeric(clusterRubricMaxScore) && StringUtils.isNumeric(clusterVariantScore)
								&& Integer.parseInt(clusterRubricMinScore) <= Integer.parseInt(clusterVariantScore) 
								&& Integer.parseInt(clusterRubricMaxScore) >= Integer.parseInt(clusterVariantScore))
						task.setVariantScore(clusterVariantScore);
						
						task.setVariantNonScoreReason(clusterVariantNonScoreReason);
						columnDetails.add(task);
						groupColName = "";
						clusterRubricMinScore = "";
						clusterRubricMaxScore = "";
						clusterVariantScore = "";
						clusterVariantNonScoreReason = "";
					}
				}
			}
		}
		// Code added for Defect DE DE14890

		if (!groupColName.trim().isEmpty()) {
			String[] groupName = groupColName.toString().split(",");
			groupColName = groupName[0] + "--" + groupName[groupName.length - 1];
			TestSectionTaskVariantDetails task = new TestSectionTaskVariantDetails();
			task.setTaskVariantPosition(groupColName);
			task.setRubricMinScore(clusterRubricMinScore);
			task.setRubricMaxScore(clusterRubricMaxScore);
			task.setVariantScore(clusterVariantScore);
			task.setVariantNonScoreReason(clusterVariantNonScoreReason);
			columnDetails.add(task);
			groupColName = "";
		}

		return columnDetails;
	}

	static Comparator<String> columnComparator = new Comparator<String>() {
		public int compare(String gc1, String gc2) {
			return gc1.compareToIgnoreCase(gc2);
		}
	};

	private String wrapForCSV(String s) {
		if (s != null && !s.isEmpty() && s.indexOf(',') > -1) {
			return "\"" + s + "\"";
		}
		return s;
	}

	/**
	 * Added By Sudhansu.b Feature: f430 Scoring Upload
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ScoringUploadDto> getAssignmentsForUpload(String assignmentName, boolean scoreAllpermission,
			Long scorerId) {
		return scoringAssignmentDao.getAssignmentsForUpload(assignmentName, scoreAllpermission, scorerId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ScoringAssignment getByAssessmentNameAndStudentAndEducator(String assignmentName,
			String stateStudentIdentifier, String educatorIdentifier) {
		return scoringAssignmentDao.getByAssessmentNameAndStudentAndEducator(assignmentName, stateStudentIdentifier,
				educatorIdentifier);
	}

	@Override
	public List<Stage> getStageByAssessmentProgram(Long assessmentProgramId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TestSession> getTestSessionsByScorerAssessmentProgram(Long assessmentProgramId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GradeCourse> getGradeCourseByContentAreaIdForAssignScorers(Long assessmentProgramId, Long schoolAreaId,
			Long contentAreaId, int currentSchoolYear) {
		return scoringAssignmentDao.getGradeCourseByContentAreaIdForAssignScorers(assessmentProgramId, schoolAreaId,
				contentAreaId, currentSchoolYear);

	}

	@Override
	public List<Student> getMappedstudentForScorers(List<Long> studentIdList, List<Long> scorerIds) {
		return scoringAssignmentDao.getMappedstudentForScorers(studentIdList, scorerIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getNoOfScoredItemsByStudentTest(Long studentsTestId) {
		return scoringAssignmentDao.getNoOfUnScoredItemsByStudentTest(studentsTestId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void setKelpaScoringStatus(Long studentsTestId, Long completedCategoryCode, Long modifiedUserId) {
		scoringAssignmentStudentDao.setKelpaScoringStatus(studentsTestId, completedCategoryCode, modifiedUserId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateKelpaScoringStatus(List<Long> studentIdList, Long modifiedUser) {
		for (Long studentsTestsId : studentIdList) {
			scoringAssignmentStudentDao.updateKelpaScoringStatus(studentsTestsId, modifiedUser);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void soreTest(List<StudentsTestScore> studentsTestScoreList) {
		Integer score = 0;
		for (StudentsTestScore studentsTestScore : studentsTestScoreList) {
			score = studentsTestScoreMapper.getScoreByStudentsTestAndTaskvariantId(
					studentsTestScore.getStudensTsestId(), studentsTestScore.getTaskvariantid(),
					studentsTestScore.getRubriccategoryid());
			if (score == null) {
				studentsTestScoreMapper.insert(studentsTestScore);
			} else {
				studentsTestScoreMapper.update(studentsTestScore);
			}
			studentsTestScoreMapper.insertToHistory(studentsTestScore);
		}

		if (studentsTestScoreList.size() > 0)
			calculateAndSetScoringStatus(studentsTestScoreList.get(0).getStudensTsestId(),
					studentsTestScoreList.get(0).getTestId(), studentsTestScoreList.get(0).getScorerid());
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void calculateAndSetScoringStatus(Long studentsTestId, Long testId, Long modifiedUserId) {

		Long inProgressCategoryCode = new Long(0);
		Long completedCategoryCode = new Long(0);

		List<Category> scoringStatusList = categoryService.selectByCategoryType("SCORING_STATUS");
		if (scoringStatusList.size() > 0) {
			for (Category category : scoringStatusList) {
				if (category.getCategoryCode().equals("IN_PROGRESS")) {
					inProgressCategoryCode = category.getId();
				}
				if (category.getCategoryCode().equals("COMPLETED")) {
					completedCategoryCode = category.getId();
				}

			}
		}

		int totalNoOfItems = scoringTestMetaDataMapper.countNoOfItemInScoringTestMetaData(testId);
		int noOfItemScored = scoringAssignmentDao.getNoOfUnScoredItemsByStudentTest(studentsTestId);
		if (noOfItemScored == 0) {
			setKelpaScoringStatus(studentsTestId, null, modifiedUserId);
		} else if (noOfItemScored == totalNoOfItems) {
			setKelpaScoringStatus(studentsTestId, completedCategoryCode, modifiedUserId);
		} else {
			setKelpaScoringStatus(studentsTestId, inProgressCategoryCode, modifiedUserId);
		}
	}

	public ScoringAssignmentStudent getStudentByNameAndStudentIdentifier(String assignmentName, String stateStudentId) {
		return scoringAssignmentStudentDao.getStudentByNameAndStudentIdentifier(assignmentName, stateStudentId);
	}

	@Override
	public List<ScorerTestStudentsSessionDTO> getStudentTestMonitorScore(Long assessmentProgram, Long school,
			Long contentArea, Long grade, Long stage, int currentSchoolYear) {
		return scoringAssignmentDao.getStudentTestMonitorScore(assessmentProgram, school, contentArea, grade, stage,
				currentSchoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void removeStudentsTestScore(List<StudentsTestScore> removedStudentsScores) {
		for (StudentsTestScore studentsTestScore : removedStudentsScores) {
			studentsTestScoreMapper.removeStudentsTestScore(studentsTestScore.getId());
			studentsTestScore.setScore(null);
			studentsTestScore.setNonscorereason(null);
			studentsTestScoreMapper.insertToHistory(studentsTestScore);
		}

		if (removedStudentsScores.size() > 0)
			calculateAndSetScoringStatus(removedStudentsScores.get(0).getStudensTsestId(),
					removedStudentsScores.get(0).getTestId(), removedStudentsScores.get(0).getScorerid());

	}

}
