package edu.ku.cete.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import edu.ku.cete.controller.InterimController;
import edu.ku.cete.controller.InterimTestDTO;
import edu.ku.cete.domain.InterimPredictiveQuestionExtractDTO;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.content.TestCollectionTests;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.content.TestSectionsTaskVariants;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.interim.AutoAssignInterim;
import edu.ku.cete.domain.interim.InterimPredictiveStudentScore;
import edu.ku.cete.domain.interim.InterimTest;
import edu.ku.cete.domain.interim.InterimTestTests;
import edu.ku.cete.domain.interim.StudentActivityReport;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ContentGroupDao;
import edu.ku.cete.model.InterimStudentReportMapper;
import edu.ku.cete.model.InterimTestMapper;
import edu.ku.cete.model.OperationalTestWindowDao;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TaskVariantDao;
import edu.ku.cete.model.TestCollectionDao;
import edu.ku.cete.model.TestCollectionTestsDao;
import edu.ku.cete.model.TestDao;
import edu.ku.cete.model.TestSectionDao;
import edu.ku.cete.model.TestSectionsTaskVariantsDao;
import edu.ku.cete.model.TestSessionDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.mapper.StudentsTestSectionsDao;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.InterimService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.QuestionInformationService;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * This is an implementation class for Interim Service and will help us create
 * the Interim Tests and Manipulate the Interim tests.
 * 
 * @author venkat
 *
 */
@Service
public class InterimServiceImpl implements InterimService {

	@Autowired
	private TestDao testDAO;
	@Autowired
	private OperationalTestWindowDao otwDao;
	@Autowired
	private StudentsTestsDao studentsTestsDao;
	@Autowired
	private TaskVariantDao taskVariantDao;
	@Autowired
	private TestSessionDao testSessionDao;

	@Autowired
	private StudentsTestSectionsDao studentsTestSectionDao;
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private InterimTestMapper interimMapper;
	@Autowired
	private StudentDao studentDao;
	@Autowired
	private RosterDao rosterDao;
	@Autowired
	private TestService testService;

	@Autowired
	private TestSectionDao testSectionDao;

	@Autowired
	private TestSectionsTaskVariantsDao testSectionsTaskVariantsDao;

	@Autowired
	private TestCollectionDao testCollectionDao;

	@Autowired
	private TestCollectionTestsDao testCollectionTestsDao;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private EnrollmentService enrollmentService;


	@Autowired
	private UserService userService;
	
	@Autowired
	private InterimStudentReportMapper interimStudentReportMapper;
	
	@Autowired
	private QuestionInformationService questionInformationService;
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Autowired
	private AwsS3Service s3;
	
	@Autowired
	private ContentGroupDao contentGroupDao;
	
	private Logger LOGGER = LoggerFactory.getLogger(InterimController.class);

	@Override
	public InterimTest createTest(InterimTest interimTest) {

		LOGGER.debug("Entered createTest Method ");
		// Create the InterimTestTests Object from the InterimTest and Insert
		// into the InterimTestTests Table as well.

		interimMapper.save(interimTest);
		for (int i = 0; i < interimTest.getMiniTestIds().length; i++) {
			InterimTestTests mapping = new InterimTestTests();
			mapping.setActiveFlag(true);
			mapping.setCreatedDate(new Date());
			mapping.setModifiedDate(new Date());
			mapping.setCreatedUser(interimTest.getCreatedUser());
			mapping.setModifiedUser(interimTest.getCreatedUser());
			mapping.setTestId(interimTest.getMiniTestIds()[i]);
			mapping.setInterimTestId(interimTest.getId());
			interimMapper.saveInterimTestMapping(mapping);
		}
		interimTest.setId(interimTest.getId());
		// We Really Need to Create the Data In Other Tables as Well to Make
		// Sure to be available in TDE.
		LOGGER.debug("getting interimTest ids" + interimTest);

		return interimTest;
	}

	@Override
	public InterimTest getInterimTest(Long interimTestId) {
		LOGGER.debug("Entered getInterimTest Method ");
		return interimMapper.getInterimTest(interimTestId);
	}

	@Override
	public List<Test> getMiniTests(Long purpose, Long contentAreaId, Long gradeCourseId, Boolean isInterim,
			String createdBy, String schoolName, String contentCode, String testName, Long organizationId,
			Long assessmentProgramId, Long schoolYear) {
		if (createdBy != null && (createdBy.equalsIgnoreCase("null") || createdBy.equalsIgnoreCase(""))) {
			createdBy = null;
		} else {
			createdBy = "%" + createdBy + "%";
		}
		if (contentCode != null && (contentCode.equalsIgnoreCase("null") || contentCode.equalsIgnoreCase(""))) {
			contentCode = null;
		}
		if (schoolName != null && (schoolName.equalsIgnoreCase("null") || schoolName.equalsIgnoreCase("")
				|| schoolName.equalsIgnoreCase("select"))) {
			schoolName = null;
		}

		if (testName != null && (testName.equalsIgnoreCase("null") || testName.equalsIgnoreCase(""))) {
			testName = null;
		} else {
			testName = "%" + testName + "%";
		}
		return testDAO.getInterimTest(contentAreaId, gradeCourseId, isInterim, createdBy, schoolName, contentCode,
				purpose, testName, organizationId,assessmentProgramId,schoolYear);
	}

	@Override
	public List<InterimTest> getInterimTestsByUser(long userid) {
		LOGGER.debug("Entered getInterimTestsByUser Method ");
		return interimMapper.getInterimTestsByUser(userid);
	}

	@Override
	public List<String> getRosterName(Long userId, Long currentSchoolYear, Long organizationId, Long assessmentProgramId, Long interimTestId) {
		LOGGER.info("Entered getRosterName Method ");
		return testDAO.getRosterName(userId, currentSchoolYear, organizationId, assessmentProgramId, interimTestId);
	}

	@Override
	public List<String> getRosterNameByUserAndOrgList(List<Long> userList, Long organizationId, Long currentSchoolYear, Long assessmentProgramId) {
		LOGGER.debug("Entered getRosterNameByUserAndOrgList Method ");
		return testDAO.getRosterNameByUserAndOrgList(userList, organizationId, currentSchoolYear, assessmentProgramId);
	}

	@Override
	public List<String> getRosterSubject() {
		LOGGER.debug("Entered getRosterSubject Method ");
		return testDAO.getRosterSubject();
	}

	@Override
	public List<String> getSubjectNameByUserAndOrgList(List<Long> userList, Long organizationId, Long currentSchoolYear, Long assessmentProgramId) {
		LOGGER.debug("Entered getSubjectNameByUserAndOrgList Method ");
		return testDAO.getSubjectNameByUserAndOrgList(userList, organizationId, currentSchoolYear, assessmentProgramId);
	}

	@Override
	public List<String> getRostergrade(Long organizationId, Long userId, Long currentSchoolYear, Long assessmentProgramId) {
		LOGGER.debug("Entered getRostergrade Method ");
		return testDAO.getRosterGrade(organizationId, userId, currentSchoolYear, assessmentProgramId);
	}

	/*
	 * Update follows following flow Soft Delete Mintest id's softdelete tests,
	 * soft delete test collection soft delete test sections then create new
	 * mintests new testcollections new test (Follow create flow)
	 */

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public InterimTest updateTest(InterimTest interimTest) {
		LOGGER.debug("Entered updateTest Method ");
		// Get Tests and Test Collections and Test Sections
		Test oldTest = testService.findById(interimTest.getTestTestId());
		TestCollection tct = testCollectionTestsDao.findByTestId(oldTest.getId());
		List<TestSection> testSectionListOld = testSectionDao.selectByTestId(oldTest.getId());

		interimTest.setGradeCourseId(oldTest.getGradeCourseId());
		interimTest.setContentAreaId(oldTest.getContentAreaId());
		// Delete Existing Values for test sections and task variants
		for (TestSection testSection : testSectionListOld) {
			testSectionsTaskVariantsDao.deleteByTestSectionId(testSection.getId());
			testSectionDao.deleteByPrimaryKeyInterim(testSection.getId());
		}
		// Set New Values
		tct.setName(interimTest.getName());
		oldTest.setTestName(interimTest.getName());

		// Create New Test Section and New TestSection Task Variant
		createTestSection(interimTest.getCreateDate(), interimTest.getCreatedUser(), interimTest.getModifiedUser(),
				new Date(), oldTest.getId(), true, 1, interimTest.getName(), interimTest.getMiniTestIds());
		testService.updateByPrimaryKeySelective(oldTest);
		testCollectionDao.updateByPrimaryKey(tct);
		interimMapper.updateById(interimTest);
		saveInterimTestTests(interimTest);
		interimTest.setId(interimTest.getId());

		LOGGER.debug("getting interimTest ids" + interimTest);
		return interimTest;
	}

	public void saveInterimTestTests(InterimTest interimTest) {
		LOGGER.debug("Entered saveInterimTestTests Method ");
		for (int i = 0; i < interimTest.getMiniTestIds().length; i++) {
			InterimTestTests mapping = new InterimTestTests();
			mapping.setActiveFlag(true);
			mapping.setCreatedDate(new Date());
			mapping.setModifiedDate(new Date());
			mapping.setCreatedUser(interimTest.getCreatedUser());
			mapping.setModifiedUser(interimTest.getCreatedUser());
			mapping.setTestId(interimTest.getMiniTestIds()[i]);
			mapping.setInterimTestId(interimTest.getId());
			interimMapper.saveInterimTestMapping(mapping);
		}
	}

	@Override
	public void deleteInterimTestTestByInterimTestID(Long interimTestId) {
		LOGGER.debug("Entered deleteInterimTestTestByInterimTestID Method ");
		interimMapper.deleteInterimtTestTestByInterimTestId(interimTestId);

	}

	@Override
	public void softDeleteInterimTestByInterimTestID(Long interimTestId) {
		LOGGER.debug("Entered softDeleteInterimTestByInterimTestID Method ");
		interimMapper.softDeleteInterimTestByInterimTestID(interimTestId);
	}

	@Override
	public List<InterimTestDTO> getTotalTests(Long organizationId, String sortByColumn, String sortType, int offset,
			int limitCount, Map<String, Object> criteria, Long schoolYear) {
		LOGGER.debug("Entered getTotalTests Method ");

		return interimMapper.getTotalTests(organizationId, sortByColumn, sortType, offset, limitCount, criteria,
				schoolYear);
	}

	@Override
	public List<InterimTestDTO> getTotalTestsForTeacher(User user, Long currentSchoolYear, Boolean forReports,  Long assessmentProgramId) {
		LOGGER.debug("Entered getTotalTestsForTeacher Method ");
		/*
		 * List<Roster> rosList =
		 * rosterDao.findRostersforTeacherIdInCurrentYear(user.
		 * getCurrentContextUserId(), user.getCurrentOrganizationId(),
		 * currentSchoolYear); Set<Long> studentIds = new HashSet<Long>(); for
		 * (Roster ros : rosList) { List<Student> studList =
		 * studentDao.findByRosterInCurrentYear(ros.getId(), currentSchoolYear);
		 * for (Student s : studList) { studentIds.add(s.getId()); } }
		 * List<Long> studList = new ArrayList<Long>();
		 * studList.addAll(studentIds); List<InterimTestDTO> ret = new
		 * ArrayList<InterimTestDTO>(); if (studentIds.size() > 0) { ret =
		 * interimMapper.getTotalTestsForTeacher(studList,
		 * user.getCurrentContextUserId()); } return ret;
		 */
		return interimMapper.getTotalTestsForTeacher(user.getCurrentContextUserId(), currentSchoolYear,
				user.getCurrentOrganizationId(), forReports, assessmentProgramId);

	}

	@Override
	public int testCountDetails(Map<String, Object> criteria) {
		LOGGER.debug("Entered testCountDetails Method ");
		return interimMapper.testCountDetails(criteria);

	}

	@Override
	public int getStudentCount(Long rosterId, Long contentAreaId, Long gradeCourseId, Map<String, Object> criteria) {
		LOGGER.debug("Entered getStudentCount Method ");
		return testDAO.testCountDetails(rosterId, contentAreaId, gradeCourseId, criteria);

	}

	@Override
	public Long createTestCollection(String name, Date createdDate, Long createdUser, Long modifiedUser,
			Date modifiedDate, Long gradeCourseId, Long contentAreaId, String oiginationCode,
			String randomizationType) {

		LOGGER.debug("Entered createTestCollection Method ");

		TestCollection testCollection = new TestCollection();

		testCollection.setName(name);
		testCollection.setCreatedDate(createdDate);
		testCollection.setCreatedUser(createdUser);
		testCollection.setModifiedUser(modifiedUser);
		testCollection.setModifiedDate(modifiedDate);
		testCollection.setGradeCourseId(gradeCourseId);
		testCollection.setContentAreaId(contentAreaId);
		testCollection.setOriginationCode(oiginationCode);
		testCollection.setRandomizationType(randomizationType);

		testCollectionDao.insert(testCollection);
		LOGGER.debug("getting testCollection ids" + testCollection.getId());
		return testCollection.getId();

	}

	@Override
	public Long createTest(String name, Long assessmentId, String originationCode, String uiTypeCode, Long status,
			Long gradeCourseId, Long contentAreaId, Date createdDate, Long createdUser, Long modifiedUser,
			Date modifiedDate, Boolean isInterimTest, Boolean qcComplete, Long testspecificationid) {

		LOGGER.debug("Entered createTest Method ");

		Test testCreate = new Test();

		testCreate.setTestName(name);
		testCreate.setAssessmentId(assessmentId);
		testCreate.setOriginationCode(originationCode);
		testCreate.setUiTypeCode(uiTypeCode);
		testCreate.setStatus(status);
		if (gradeCourseId != null ) {
			testCreate.setGradeCourseId(gradeCourseId);
		}
		testCreate.setContentAreaId(contentAreaId);
		testCreate.setCreatedDate(createdDate);
		testCreate.setCreatedUser(createdUser);
		testCreate.setModifiedUser(modifiedUser);
		testCreate.setModifiedDate(modifiedDate);
		testCreate.setIsInterimTest(isInterimTest);
		testCreate.setQcComplete(qcComplete);
		testCreate.setTestspecificationid(testspecificationid);

		testService.createInterimTest(testCreate);

		LOGGER.debug("getting testCreate ids" + testCreate.getId());
		return testCreate.getId();

	}

	@Override
	public void createInterimTestCollectionTests(Long createdCollectionId, Long createdTestId, Date createdDate,
			Long createdUser, Long modifiedUser, Date modifiedDate) {

		LOGGER.debug("Entered createInterimTestCollectionTests Method ");

		TestCollectionTests tct = new TestCollectionTests();

		tct.setTestCollectionId(createdCollectionId);
		tct.setTestId(createdTestId);
		tct.setCreatedDate(createdDate);
		tct.setCreatedUser(createdUser);
		tct.setModifiedUser(modifiedUser);
		tct.setModifiedDate(modifiedDate);

		testCollectionTestsDao.insertInterim(tct);

	}

	@Override
	public InterimTest createInterimTest(Long[] minTestIds, String name, String description, Long gradeCourseId,
			Long contentAreaId, Long createdUser, Long modifiedUser, Date modifiedDate, Date createdDate,
			Long createdTestId, String displayName, Long testCollectionId, Long orgId, Boolean isCopy,
			Long currentSchoolYear) {

		LOGGER.debug("Entered createInterimTest Method ");

		InterimTest interimTest = new InterimTest();

		interimTest.setMiniTestIds(minTestIds);
		interimTest.setName(name);
		interimTest.setDescription(description);
		if(gradeCourseId != null) {
			interimTest.setGradeCourseId(gradeCourseId);
		}
		interimTest.setContentAreaId(contentAreaId);
		interimTest.setCreatedUser(createdUser);
		interimTest.setModifiedUser(modifiedUser);
		interimTest.setModifiedDate(modifiedDate);
		interimTest.setCreatedDate(createdDate);
		interimTest.setTestTestId(createdTestId);
		interimTest.setAssembledBy(displayName);
		interimTest.setActiveFlag(true);
		interimTest.setTestCollectionId(testCollectionId);
		interimTest.setOrganizationId(orgId);
		interimTest.setIsTestCopied(isCopy);
		interimTest.setCurrentSchoolYear(currentSchoolYear);
		this.createTest(interimTest);

		LOGGER.debug("getting interimTest ids" + interimTest);
		return interimTest;

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> createTestSection(Date createdDate, Long createdUser, Long modifiedUser, Date modifiedDate,
			Long createdTestId, Boolean hardBreak, int sectionOrder, String testSectionName, Long[] testIdArray)

	{

		LOGGER.debug("Entered createTestSection Method ");
		List<Long> returnList = new ArrayList<Long>();
		List<TestSection> testSectionList = new ArrayList<TestSection>();
		for (Long testId : testIdArray) {
			testSectionList.addAll(testSectionDao.getTestSectionsByTestInterim(testId));

		}
		Integer testSectionNumber = 1;

		for (TestSection ts : testSectionList) {

			TestSection nts = new TestSection();
			nts.setCreatedDate(createdDate);
			nts.setCreatedUser(createdUser);
			nts.setModifiedUser(modifiedUser);
			nts.setModifiedDate(modifiedDate);
			nts.setTestId(createdTestId);
			nts.setHardBreak(Boolean.FALSE);
			nts.setContextStimulusId(ts.getContextStimulusId());
			nts.setHelpNotes(ts.getHelpNotes());
			nts.setTicketed(ts.getTicketed());
			nts.setTaskDeliveryRuleId(ts.getTaskDeliveryRuleId());
			nts.setBeginInstructions(ts.getBeginInstructions());
			nts.setEndInstructions(ts.getEndInstructions());
			nts.setNumberOfTestItems(ts.getNumberOfTestItems());
			nts.setToolsUsageId(ts.getToolsUsageId());
			nts.setExternalId(ts.getExternalId());

			nts.setSectionOrder(testSectionNumber);
			nts.setTestSectionName(testSectionName + "_section" + testSectionNumber.toString());

			testSectionDao.insert(nts);
			testSectionDao.insertAuxillaryTestRoles(nts.getId(), ts.getId());
			createTestSectionTaskVariant(testSectionsTaskVariantsDao.selectBySectionId(ts.getId()), nts.getId(),
					"AART");
			contentGroupDao.createContentGroupForInterimTest(createdTestId, nts.getId(),
					SourceTypeEnum.INTERIMTEST.getCode(), createdUser, modifiedUser);
			returnList.add(nts.getId());
			testSectionNumber++;
		}
		return returnList;

	}

	public void createTestSectionTaskVariant(List<Long> idList, Long testSectionId, String originationCode) {
		Integer taskPos = 1;
		for (Long taskId : idList) {
			TestSectionsTaskVariants newTestSectionsTaskVariants = new TestSectionsTaskVariants();
			TestSectionsTaskVariants old = testSectionsTaskVariantsDao.selectByTestSectionsTaskVariantsId(taskId);
			newTestSectionsTaskVariants.setTaskVariantId(old.getTaskVariantId());
			newTestSectionsTaskVariants.setTestSectionId(testSectionId);
			newTestSectionsTaskVariants.setTaskVariantPosition(taskPos);
			newTestSectionsTaskVariants.setOriginationCode(originationCode);
			newTestSectionsTaskVariants.setTestletId(old.getTestletId());
			newTestSectionsTaskVariants.setSortOrder(old.getSortOrder());
			newTestSectionsTaskVariants.setGroupNumber(old.getGroupNumber());
			testSectionsTaskVariantsDao.insertInterim(newTestSectionsTaskVariants);
			taskPos++;

		}
	}

	@Override
	public String deleteInterimTest(Long interimTestId, Long testSessionId) {
		InterimTest it = getInterimTestByTestTestId(interimTestId);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Date modifiedDate = new Date();
		Long modifiedUser = user.getCurrentContextUserId();
		if (interimTestId != null && testSessionId == null) {
			LOGGER.debug("Entered deleteInterimTest Method ");

			Test oldTest = testService.findById(it.getTestTestId());
			/*
			 * @Author vkrishna_sta For DE15860 Commented out
			 * softDeleteTestCollectionDao as it is not needed after F565
			 */
			TestCollection tct = testCollectionTestsDao.findByTestId(oldTest.getId());
			if (tct != null) {
				testCollectionTestsDao.softDeleteByTestIdAndTestCollectionId(oldTest.getId(), tct.getId());
				// testCollectionDao.softDeleteById(tct.getId());
			}

			testService.softDeleteById(oldTest.getId());
			List<TestSection> testSectionListOld = testSectionDao.selectByTestId(oldTest.getId());

			// Delete Existing Values for test sections and task variants
			for (TestSection testSection : testSectionListOld) {
				testSectionsTaskVariantsDao.deleteByTestSectionId(testSection.getId());
				testSectionDao.deleteByPrimaryKeyInterim(testSection.getId());
			}

			softDeleteInterimTestByInterimTestID(it.getId());
			interimMapper.softDeleteInterimTestByInterimTestID(it.getId());
			interimMapper.deleteById(it.getId());
			LOGGER.debug("Deleted interim test id" + interimTestId);
		} else if (interimTestId != null && testSessionId != null) {
			testSessionDao.deleteForInterim(testSessionId, modifiedUser, modifiedDate);
			studentsTestsDao.updateActiveFlagBytestSessionId(testSessionId, modifiedUser);
			cleanUpOldTest(it);
		}
		return null;
	}

	@Override
	public Long getInterimTestsCount(Long id) {
		return interimMapper.getInterimTestsCount(id);
	}

	@Override
	public Long insertTestSession(Long interimTestId, Long testCollectionId, User user, String testName,
			Long schoolYear) {

		OperationalTestWindow otw = otwDao.selectOperTestWindowForInterim(testCollectionId);

		TestSession ts = new TestSession();
		ts.setName(testName);
		ts.setStatus((long) 84);
		ts.setCreatedDate(new Date());
		ts.setCreatedUser(user.getCurrentContextUserId());
		ts.setModifiedDate(new Date());
		ts.setModifiedUser(user.getCurrentContextUserId());
		ts.setActiveFlag(true);
		ts.setTestId(interimTestId);
		ts.setTestCollectionId(testCollectionId);
		ts.setOperationalTestWindowId(otw.getId());
		ts.setSource("INTERIM");
		ts.setSchoolYear(schoolYear);

		testSessionDao.insertInterim(ts);
		Long testSessionId = ts.getId();

		return testSessionId;
	}

	@Override
	public List<Student> getStudentInfo(Long rosterId, Long contentAreaId, Long gradeCourseId, String sortByColumn,
			String sortType, int i, int limitCount, Map<String, Object> criteria) {
		LOGGER.debug("Entered getStudentInfo Method ");
		return testDAO.getStudentinfoInterim(rosterId, contentAreaId, gradeCourseId, sortByColumn, sortType, i,
				limitCount, criteria);
	}

	@Override
	public void insertStudentTestsRecords(Long testSessionId, List<Long> studentIds, Long testId, Long testCollectionId,
			Long interimTestId, Long currentSchoolYear) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		List<TestSection> tsList = testSectionDao.getTestSectionsByTestInterim(testId);
		if (studentIds != null) {
			for (int i = 0; i < studentIds.size(); i++) {
				List<StudentsTests> stsp = studentsTestsDao.findByTestSessionAndStudentId(testSessionId,
						Long.valueOf(studentIds.get(i)));
				if (stsp == null || stsp.isEmpty()) {
					StudentsTests sTests = new StudentsTests();
					sTests.setStudentId(Long.valueOf(studentIds.get(i)));
					sTests.setTestId(testId);
					sTests.setTestSessionId(testSessionId);
					sTests.setTestCollectionId(testCollectionId);
					sTests.setStatus((long) 84);
					sTests.setCreatedDate(new Date());
					sTests.setModifiedDate(new Date());
					sTests.setCreatedUser(user.getCurrentContextUserId());
					sTests.setModifiedUser(user.getCurrentContextUserId());
					sTests.setActiveFlag(true);
					sTests.setCurrentSchoolYear(currentSchoolYear);
					studentsTestsDao.insertInterim(sTests);
					for (TestSection ts : tsList) {
						StudentsTestSections sts = new StudentsTestSections();
						sts.setTestSectionId(ts.getId());
						sts.setStudentsTestId(sTests.getId());
						sts.setCreatedDate(new Date());
						sts.setModifiedDate(new Date());
						sts.setCreatedUser(user.getCurrentContextUserId());
						sts.setModifiedUser(user.getCurrentContextUserId());
						studentsTestSectionDao.insertInterim(sts);
					}

				}
			}
		}
		interimMapper.setTestAssigned(interimTestId);

	}

	@Override
	public List<Student> getStudentInfoNew(List<Long> rosterIds, List<Long> gradeCourseIds, List<Long> orgIds,
			List<Long> userList, Long schoolYear,Long assessmentProgramId) {
		if (rosterIds.isEmpty()) {
			rosterIds = null;
		}
		if (gradeCourseIds.isEmpty()) {
			gradeCourseIds = null;
		}
		if (userList.isEmpty()) {
			userList = null;
		}

		return testDAO.getStudentinfo(rosterIds, gradeCourseIds, orgIds, userList, schoolYear,assessmentProgramId);
	}

	@Override
	public List<TestSession> getInterimTestSession(Long testTestId) {

		return studentsTestsDao.getInterimTestSession(testTestId);
	}

	@Override
	public List<TestSession> getTestSessionByTestID(Long testId) {
		return testSessionDao.getTestSessionByTestId(testId);
	}

	@Override
	public List<Student> getStudentId(Long testSessionId) {
		return studentsTestsDao.getStudentIdByTestSessionIdAll(testSessionId);
	}

	@Override
	public InterimTest getInterimTestByTestTestId(Long testId) {
		return interimMapper.getInterimTestByTestTestId(testId);
	}

	@Override
	public List<InterimTest> getTestsForReports(Long id) {

		List<InterimTest> itList = interimMapper.getInterimTestsByUser(id);
		Iterator<InterimTest> interimIterator = itList.iterator();

		while (interimIterator.hasNext()) {
			InterimTest it = interimIterator.next();
			if (it.getIsTestAssigned()) {
				Long count = interimMapper.getAttemptedCount(it.getTestTestId());
				if (count == null || count <= 0) {
					it.setIsScoringComplete(Boolean.TRUE);
				} else {
					it.setIsScoringComplete(Boolean.FALSE);
				}
			} else {
				interimIterator.remove();
			}
		}
		return itList;
	}

	@Override
	public void softDeleteStudentsTestsByTestSessionId(Long testSessionId, List<Long> studentIds, Long userId) {
		studentsTestsDao.updateActiveFlagBytestSessionIdInterim(testSessionId, studentIds, userId);

	}

	@Override
	public List<Test> getMiniTestsByInterimTestId(Long interimTestId) {
		return testDAO.getMiniTestsByInterimTestId(interimTestId);

	}

	@Override
	public List<InterimTestDTO> getTotalTests(List<Long> userIds, Long organizationId, Long schoolYear,
			Boolean forReports, Long assessmentProgramId) {
		return interimMapper.getTotalTestsByUserId(userIds, organizationId, schoolYear, forReports, assessmentProgramId);
	}

	@Override
	public List<Student> getStudentIdByTestsessionId(Long testSessionId) {
		List<TestSession> tsl = testSessionDao.getTestSessionByTestId(testSessionId);
		return studentsTestsDao.getStudentIdByTestSessionIdIncomplete(tsl.get(0).getId());
	}

	@Override
	public List<Student> getStudentIdByTestsessionIdInterim(Long testSessionId, Long orgId, Long userId,
			Boolean isTeacher, Long currentSchoolyear) {
			return studentsTestsDao.getStudentIdByTestSessionIdAllByTeacher(testSessionId, orgId, userId, isTeacher,currentSchoolyear);
	}

	@Override
	public List<Student> getStudentIdByTestsessionIdInterim(Long testSessionId) {

		return studentsTestsDao.getStudentIdByTestSessionIdAll(testSessionId);
	}

	@Override
	public List<TaskVariant> getQuestionsForScoring(Long testTestId) {
		List<TestSection> tsl = testSectionDao.selectByTestId(testTestId);
		List<TaskVariant> tvl = new ArrayList<TaskVariant>();
		for (TestSection ts : tsl) {
			tvl.addAll(taskVariantDao.getTaskVariantsInSection(ts.getId()));
		}
		return tvl;
	}

	@Override
	public void updateTestsession(Long testSessionId) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		testSessionDao.updateTestsession(testSessionId, userDetails.getUserId(), new Date());
	}

	@Override
	public TestSession getTestSessionByTestSessionId(Long testSessionId) {
		return testSessionDao.getTestSessionByTestSessionId(testSessionId);
	}

	@Override
	public TestSession getTestSessionDetailsByTestSessionId(Long testSessionId) {
		return testSessionDao.getTestSessionDetailsByTestSessionId(testSessionId);
	}

	@Override
	public List<String> getSchoolNames(Long contractingOrgId, Long schoolYear) {
		return interimMapper.getSchoolNames(contractingOrgId, schoolYear);
	}

	@Override
	public List<Category> selectTestPurposeForInterim(Boolean isInterim, Long organizationId, Long assessmentProgramId,
			Long schoolYear) {
		return categoryDao.selectTestPurposeForInterim(isInterim, organizationId, assessmentProgramId, schoolYear);
	}

	@Override
	public Boolean isTestNameUnique(String testName, Long currentContextUserId, Long schoolYear ,Long orgId) {
		List<InterimTest> it = interimMapper.getInterimTestsByNameAndUser(testName, currentContextUserId, schoolYear, orgId);
		if (it != null && it.size() != 0) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	@Override
	public int updateInterim(String name, Long interimTestId, Long testCollectionId, Long testTestId,
			Long testSessionId) {
		return interimMapper.updateTestSession(name, interimTestId, testCollectionId, testTestId, testSessionId);
	}

	@Override
	public void cleanUpOldTest(InterimTest interimTest) {
		List<TestSession> ts = getTestSessionByTestID(interimTest.getTestTestId());
		if (ts == null || ts.isEmpty()) {
			deleteInterimTest(interimTest.getTestTestId(), null);
		}

	}

	@Override
	public InterimTestDTO getTotalTestSessionDetails(Long testSessionId, Long assessmentProgId) {
		return interimMapper.getTotalTestSessionDetails(testSessionId,assessmentProgId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer suspendTestWindow(Boolean suspend, Long testSessionId) {

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		return interimMapper.suspendTestWindow(suspend, testSessionId, userDetails.getUserId(), new Date());

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long insertAutoAssignInterim(List<Long> gradeCourseIds, List<Long> contentAreaIds, List<Long> rosterIds,
			Long testSessionId) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		AutoAssignInterim autoAssign = new AutoAssignInterim();
		if (rosterIds != null && !rosterIds.isEmpty()) {
			for (Long rosterId : rosterIds) {
				autoAssign.setRosterId(rosterId);
				autoAssign.setGradeCourseId(null);
				autoAssign.setContentAreaId(null);
			}
		}
		if ((gradeCourseIds != null && !gradeCourseIds.isEmpty())
				&& (contentAreaIds != null && !contentAreaIds.isEmpty())) {
			for (Long gradeCourseId : gradeCourseIds) {
				for (Long contentAreaId : contentAreaIds) {
					autoAssign.setRosterId(null);
					autoAssign.setGradeCourseId(gradeCourseId);
					autoAssign.setContentAreaId(contentAreaId);
				}
			}
		}
		autoAssign.setTestSessionId(testSessionId);
		autoAssign.setActiveFlag(true);
		autoAssign.setCreatedUser(user.getId());
		autoAssign.setModifiedUser(user.getId());
		Long updateIds = (long) interimMapper.insertAutoAssignInterim(autoAssign);
		return updateIds;
	}

	@Override
	public List<Long> getStudentsForAutoAssign(List<Long> rosterIds, List<Long> gradeCourseIds,
			List<Long> contentAreaIds, List<Long> orgIds, List<Long> userList, Long schoolYear, Long assessmentProgramId) {

		if (rosterIds == null || rosterIds.isEmpty()) {
			rosterIds = null;
		}
		if (gradeCourseIds == null || gradeCourseIds.isEmpty()) {
			gradeCourseIds = null;
		}
		if (userList == null || userList.isEmpty()) {
			userList = null;
		}
		if (contentAreaIds == null || contentAreaIds.isEmpty()) {
			contentAreaIds = null;
		}

		return studentDao.getStudentsForAutoAssign(rosterIds, gradeCourseIds, contentAreaIds, orgIds, userList,
				schoolYear,assessmentProgramId);
	}

	@Override
	public Long getTestCollectionId(Long testId) {
		TestCollection tc = testCollectionTestsDao.findByTestId(testId);

		return tc.getId();
	}

	@Override
	public List<StudentActivityReport> getStudentReportActivityDetails(List<Long> studentIds, List<Long> orgIds,
			Long schoolYear, Boolean isPLTWUser, Boolean isTeacher, Long userId) {

		return interimMapper.getStudentReportActivityDetails(studentIds, orgIds, schoolYear, isPLTWUser, isTeacher, userId);
	}

	@Override
	public List<ContentArea> getSubjectNamesByRosterAndOrgList(List<Long> orgIds, Long currentSchoolYear,
			Long teacherId,Boolean predictiveStudentScore, Long assessmentProgramId) {

		return testDAO.getSubjectNamesByRosterAndOrgListInterim(orgIds, currentSchoolYear, teacherId,predictiveStudentScore, assessmentProgramId);
	}

	@Override
	public List<GradeCourse> getGradesBySubjectsAndOrgList(List<Long> orgIds, Long currentSchoolYear,
			List<Long> subjectIds, Long teacherId,Boolean predictiveStudentScore, Long assessmentProgramId) {

		return testDAO.getGradesBySubjectsAndOrgList(orgIds, currentSchoolYear, subjectIds, teacherId, predictiveStudentScore,assessmentProgramId);
	}

	@Override
	public List<Student> getStudentDetailsByGradeAndSubjectAndRoster(List<Long> orgIds, Long currentSchoolYear,
			List<Long> subjectIds, List<Long> gradeIds, Long teacherId,Boolean predictiveStudentScore, Long assessmentProgramId) {

		return testDAO.getStudentDetailsByGradeAndSubjectAndRoster(orgIds, currentSchoolYear, subjectIds, gradeIds,
				teacherId, predictiveStudentScore,assessmentProgramId);
	}

	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void autoAssignKIDSInterim(Enrollment enrollment, Roster roster) {
		try {
			OrganizationTreeDetail otd = organizationService
					.getOrganizationDetailBySchoolId(enrollment.getAttendanceSchoolId());
			List<Long> orgIds = new ArrayList<>();
			orgIds.add(otd.getDistrictId());
			orgIds.add(otd.getSchoolId());
			
			List<Long> testSessionIds= new ArrayList<>();
			if (roster != null) {
				LOGGER.info("AutoAssignInterim, Getting TestSessionIds for" + roster.getId() + " "
						+ enrollment.getCurrentGradeLevelCode() + " " + orgIds.toString());
				
				testSessionIds = interimMapper.getAutoAssignedTestSessions(roster.getId(),
						enrollment.getCurrentGradeLevel(), roster.getStateSubjectAreaId(), orgIds);
			} else {
				LOGGER.info("AutoAssignInterim ENROLLMENT CHANGE");
				List<Roster> ro=rosterDao.getRostersByEnrollmentId(enrollment.getId());
				for(Roster r : ro){
					LOGGER.info("AutoAssignInterim, Getting TestSessionIds for" + r.getId() + " "
							+ enrollment.getCurrentGradeLevelCode() + " " + orgIds.toString());
					testSessionIds.addAll(interimMapper.getAutoAssignedTestSessions(null,
							enrollment.getCurrentGradeLevel(), r.getStateSubjectAreaId(), orgIds));
				}
			}
			LOGGER.info("AutoAssignInterim, Test Session Ids " + testSessionIds.toString());

			for (Long testSessionId : testSessionIds) {
				List<Long> studentIds = new ArrayList<>();
				studentIds.add(enrollment.getStudentId());
				TestSession ts = testSessionDao.findByPrimaryKey(testSessionId);
				insertStudentTestsRecords(testSessionId, studentIds, ts.getTestId(), ts.getTestCollectionId(), null,
						new Long(enrollment.getCurrentSchoolYear()));

			}
		} catch (Exception e) {
			String message = "AutoAssignInterim FAILED for School:" + enrollment.getAttendanceSchoolId() + "Roster ID:"
					+ roster.getId() + "Enrollmen Id:" + enrollment.getId() + e.getMessage();
			LOGGER.error(message, e);
		}

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void exitStudentRosterKIDSInterim(Enrollment enrollment, Roster roster) {

		// Step 1; Remove All Incomplete TestSessions for Student for test
		// sessions created by the teacher
		try {
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();
			/*
			 * vkrishna_sta: Count is the count of district and building roles
			 * held by user in that particular district. If more than one, we
			 * don not delete testsessi1ons and process the record
			 */
			/*
			 * Roster Is Null only when A Student changes A Grade inside a school
			 */

			if (roster == null) {
				OrganizationTreeDetail otd = organizationService
						.getOrganizationDetailBySchoolId(enrollment.getAttendanceSchoolId());
				List<Long> orgIds = new ArrayList<>();
				orgIds.add(otd.getDistrictId());
				orgIds.add(otd.getSchoolId());
				List<Long> testSessionIds= new ArrayList<>();
				LOGGER.info("GRADE CHANGE for KIDS, WILL DISABLE ONLY AUTOASSIGNTESTS FOR GRADE");
				List<Roster> ro=rosterDao.getRostersByEnrollmentId(enrollment.getId());
				for(Roster r : ro){
					LOGGER.info("AutoAssignInterim, Getting TestSessionIds for" + r.getId() + " "
							+ enrollment.getCurrentGradeLevelCode() + " " + orgIds.toString());
					testSessionIds.addAll(interimMapper.getAutoAssignedTestSessions(null,
							enrollment.getCurrentGradeLevel(), r.getStateSubjectAreaId(), orgIds));
				}
				if(!testSessionIds.isEmpty()){
				studentsTestsDao.softDeleteByStudentAndTestSession(enrollment.getStudentId(),enrollment.getId(),testSessionIds,user.getCurrentContextUserId());
				}
			} else {
				Long count = interimMapper.getDistrictAndBuildingLevelUserCountForUserByOrg(roster.getTeacherId(),
						enrollment.getAttendanceSchoolId());
				List<Long> teacherList = new ArrayList<>();
				teacherList.add(roster.getTeacherId());
				LOGGER.info("ExitStudentRosterKIDSInterim, Deleting Data for" + teacherList.toString() + "_"
						+ enrollment.getStudentId() + "_" + enrollment.getAttendanceSchoolId());
				if (count.equals(0l) || count == null) {
					studentsTestsDao.softDeleteByUserAndStudent(teacherList, enrollment.getStudentId(),
							enrollment.getId(), enrollment.getAttendanceSchoolId(),user.getCurrentContextUserId());
				}

				// Step 2; Check if there are no rosters for enrollment in the
				// School.
				// If there are no enrollments, Treat as exit
				List<Long> existingRosterIds = rosterDao.getRosterIdsByEnrollmentIdAndSchoolId(enrollment.getId(),
						enrollment.getAttendanceSchoolId());

				if (existingRosterIds == null || existingRosterIds.isEmpty()) {
					LOGGER.info("ExitStudentRosterKIDSInterim, For" + teacherList.toString() + "_"
							+ enrollment.getStudentId() + "_" + enrollment.getAttendanceSchoolId()
							+ "calling exit method as there are no rosters");

					exitStudentKIDSInterim(enrollment, roster);

				}
				if (existingRosterIds != null && !existingRosterIds.isEmpty()) {
					if (existingRosterIds.size() == 1 && existingRosterIds.get(0).equals(roster.getId())) {
						/*
						 * As there is only one roster, and it is the same
						 * roster which is getting removed, Treat it as exit !
						 * and remove all tests for the student as part of this
						 * enrollment school
						 */
						exitStudentKIDSInterim(enrollment, roster);
					}
					/*
					 * Since The Given Student has more than one roster for a
					 * given enrollment, All BTC's and DTC's can safely see the
					 * tests assigned by them
					 */
				}
			}
		} catch (Exception e) {
			String message = "ExitRoster FAILED for Enrollment:" + enrollment.getId() + "Roster ID:" + roster.getId()
					+ e.getMessage();
			LOGGER.error(message, e);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public void exitStudentKIDSInterim(Enrollment enrollment, Roster roster) {
		Long schoolId = enrollment.getAttendanceSchoolId();
		Long enrollmentId=enrollment.getId();
		try {
			//Enrollment enrollment = enrollmentService.getByEnrollmentId(enrollmentId);

			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = userDetails.getUser();
			// Long districtId= enrollment.getAttendanceSchoolDistrictId();
			Long studentId = enrollment.getStudentId();
			Long currentSchoolYear= new Long(enrollment.getCurrentSchoolYear());
			List<Long> userList = userService.getUsersByOrgIdForInterim(schoolId, user.getCurrentAssessmentProgramId());

			/*
			 * Step 1; As Student has exited from System totally, Set all
			 * students tests for that student in the school as inactive
			 */
			LOGGER.info("ExitStudent, For Data" + userList.toString() + "_" + enrollment.getStudentId() + "_"
					+ enrollment.getAttendanceSchoolId());
			studentsTestsDao.softDeleteByUserAndStudent(userList, studentId, enrollment.getId(), schoolId,user.getCurrentContextUserId());

			/*
			 * Step 2: Check if Student has any enrollments in same district
			 * apart from the given enrollment
			 * 
			 */
			
			List<Long> districtUsers = userService.getDistrictLevelUserIdsByOrgIdAndAP(schoolId, "KAP");
			List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentIdInDistrictBySchool(studentId, currentSchoolYear,schoolId);
			if (districtUsers != null && !districtUsers.isEmpty()) {
				if ((enrollments == null || enrollments.isEmpty())
						|| (enrollments.size() == 1 && enrollments.get(0).getId() == enrollment.getId())) {

					int log = studentsTestsDao.softDeleteByUserAndStudent(districtUsers, studentId, null, null, user.getCurrentContextUserId());
					LOGGER.info("ExitStudent, At District Level" + districtUsers.toString() + "_" + studentId + "_"
							+ log + "rows Updated");

				}
			}
		} catch (

		Exception e) {
			String message = "ExitStudent FAILED for Enrollment:" + enrollmentId + "School ID:" + schoolId
					+ e.getMessage();
			LOGGER.error(message, e);
		}
	}

	@Override
	public ByteArrayOutputStream generatePredictiveBundleByTestsessionAndUser(
			Long testSessionId, Boolean isTeacher, Long userId, Long currentSchoolYear, Long organizationId) {
		
		List<String> reportPaths = interimStudentReportMapper.getInterimStudentReportPathByTestsession(testSessionId, userId, currentSchoolYear, organizationId, isTeacher);
				
	    ByteArrayOutputStream baOut = new ByteArrayOutputStream();
		Document document = new Document();	
		if(reportPaths.size() > 0){
		try {   
		        PdfCopy copy = new PdfCopy(document, baOut);
		        document.open();
		        PdfReader reader = null;
		        int n;
		        // loop over the documents you want to concatenate
		        for(int i = 0; i < reportPaths.size(); i++){
		        	try {
		        		String path = REPORT_PATH+reportPaths.get(i);
		        		if (s3.doesObjectExist(path)){
				            reader = new PdfReader(s3.getObject(path).getObjectContent());
				            // loop over the pages in that document
				            n = reader.getNumberOfPages();
				            for (int page = 0; page < n; ) {
				                copy.addPage(copy.getImportedPage(reader, ++page));
				            }
				            if(n % 2 != 0) {
				            	copy.addPage(reader.getPageSize(1), reader.getPageRotation(1));
				            }
				            copy.freeReader(reader);
			        	} else {
			        		LOGGER.error("PDF "+path+" file not found in S3.");
			        	}
		        	} catch(Exception e) {
		        		LOGGER.error("PDF "+reportPaths.get(i)+" parse error", e);
		        	} finally {
		        		if(reader != null) {
		        			reader.close();
		        		}
		        	}
		        }
			}catch(Exception e){
				//LOGGER.error("PDF "+REPORT_PATH + studentReports.get(i).getFilePath()+" parse error", e);	
			}finally {			
				if(document.isOpen()) {
					document.close();
				}
			}
		}
	    return baOut;
	}

	@Override
	public int reportCountByTestsessionAndUser(Long testSessionId,
			Boolean isTeacher, Long userId, Long currentSchoolYear,
			Long organizationId) {		
		return interimStudentReportMapper.reportCountByTestsessionAndUser(testSessionId, userId, currentSchoolYear, organizationId, isTeacher);
	}	
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public InterimTest createInterimTestandTestSection(String name, String description, Long gradeCourseId, Long contentAreaId, 
			Long testspecificationid,User user,Long testCollectionId, Long[] testIdArray, 
			Long organizationId, boolean isTestCopied) {

			Long createdTestId = createTest(name, 1l, "aart", "genTest", 64l, gradeCourseId, contentAreaId,
					new Date(), user.getCurrentContextUserId(), user.getCurrentContextUserId(), new Date(), Boolean.TRUE,
					Boolean.TRUE, testspecificationid);
			LOGGER.debug("Created Test with id" + createdTestId);

			InterimTest it = createInterimTest(testIdArray, name, description, gradeCourseId, contentAreaId,
					user.getCurrentContextUserId(), user.getCurrentContextUserId(), new Date(), new Date(), createdTestId,
					user.getDisplayName(), testCollectionId, organizationId, isTestCopied,
					user.getContractingOrganization().getCurrentSchoolYear());
			it.setTestCollectionId(testCollectionId);
			LOGGER.debug("Created InterimTest with id" + it);

			createInterimTestCollectionTests(testCollectionId, createdTestId, new Date(),
					user.getCurrentContextUserId(), user.getCurrentContextUserId(), new Date());
			LOGGER.debug("Created InterimTestCollections with id" + it.getId());

			createTestSection(new Date(), user.getCurrentContextUserId(), user.getCurrentContextUserId(),
					new Date(), createdTestId, true, 1, name, testIdArray);		
		
		return it;
	}

	
	@Override
	public List<Organization> getInterimSchoolsInDistrict(Long districtId, Long currentSchoolYear,Boolean predictiveStudentScore,Long assessmentProgramId) {
		return organizationService.getInterimSchoolsInDistrict(districtId, currentSchoolYear, predictiveStudentScore,assessmentProgramId);
	}

	@Override
	public List<Organization> getInterimDistrictsInState(Long stateId,Long currentSchoolYear, Boolean predictiveStudentScore, Long assessmentProgramId) {
		return organizationService.getInterimDistrictsInState(stateId,currentSchoolYear, predictiveStudentScore,assessmentProgramId);
	}

	@Override
	public List<InterimPredictiveStudentScore> getInterimPredictiveStudentScores(List<Long> studentIds,
			List<Long> orgIds, Long schoolYear, List<Long> contentAreaIds, List<Long> gradeCourseIds, 
			Long assessmentProgramId, Boolean isTeacher, Long userId) {
		
		return interimMapper.getInterimPredictiveStudentScores(studentIds,orgIds,schoolYear,contentAreaIds,gradeCourseIds,
				assessmentProgramId, isTeacher, userId);
	}
	
	@Override
	public ByteArrayOutputStream getInterimPredictiveQuestionCSVBytestsessionAndUser(Long testSessionId,
			Boolean isTeacher, Boolean districtUser, Long userId, Long currentSchoolYear, Long organizationId, HttpHeaders headers) {
        
		List<InterimPredictiveQuestionExtractDTO> studentsResults = interimMapper.getInterimPredictiveQuestionCSVBytestsessionAndUser(testSessionId,
				isTeacher, districtUser, userId, currentSchoolYear, organizationId);
		
		String filename ="Interim_Predictive_Question";
		List<StudentReportQuestionInfo> questions = new ArrayList<StudentReportQuestionInfo>();
		if(studentsResults.size() > 0) {//Our understanding is a KAP interim predictive test session can not contain students from multiple grade. 
			 questions = questionInformationService.getStudentReportQuestionInfo(studentsResults.get(0).getInterimStudentReportId());
			 filename = filename+"_"+studentsResults.get(0).getReportCycle()+"_"+studentsResults.get(0).getGradeName()+"_"+studentsResults.get(0).getSubject();
		}
		
		StringBuffer header  = new StringBuffer("Student First Name,Student Last Name,Student State ID,");
		StringBuffer questionLine = new StringBuffer(",,,");//2nd line will have question text and other columns will be empty.
		int questionCount = 1;
		
		for (StudentReportQuestionInfo studentReportQuestionInfo : questions) {
			questionLine.append((studentReportQuestionInfo.getQuestionDescription().contains(",")?"\""+studentReportQuestionInfo.getQuestionDescription()+"\"":studentReportQuestionInfo.getQuestionDescription()) +",");
			header.append("Q"+questionCount+" ,");
			questionCount++;
		}
		
		header.append("District ID,District Name,School ID,School Name,Report Cycle,Subject,Grade \n");
		questionLine.append(" , , , , , , \n");
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();		
		
	    try {
			out.write(header.toString().getBytes());
			out.write(questionLine.toString().getBytes());
			
			// Write the content
			List<String> questionResult = new ArrayList<String>();
			for (InterimPredictiveQuestionExtractDTO data : studentsResults) {
				questionResult.add(data.getLegalFirstName());
				questionResult.add(data.getLegalLastName());
				questionResult.add(data.getStateStudentIdentifier());
				questionResult.add(data.getResults());
				questionResult.add(data.getDistrictDisplayIdentifier());
				questionResult.add(data.getDistrictName());
				questionResult.add(data.getSchoolDisplayIdentifier());
				questionResult.add(data.getSchoolName());
				questionResult.add(data.getReportCycle());
				questionResult.add(data.getSubject());
				questionResult.add(data.getGrade());
				questionResult.add("\n");
				out.write(String.join(",", questionResult).getBytes());
				questionResult.clear();
			}
		    out.flush();
		} catch (IOException e) {
			LOGGER.error("Error while generating result", e);
		}
	    
	    headers.setContentType(MediaType.parseMediaType("text/csv"));
	    filename = filename+"_"+DateUtil.convertDatetoSpecificTimeZoneStringFormat(new Date(), "US/Central", "MM-dd-yy_HH-mm-ss")+".CSV";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");		
		return out;
	}

	@Override
	public Collection<? extends InterimTestDTO> getTotalTestsForOrgs(List<Long> userList, Long schoolYear,
			Long assessmentProgId, List<Long> orgIds) {
		return interimMapper.getTotalTestsForOrgs(userList, schoolYear, assessmentProgId, orgIds);
	}
	
	@Override
	public List<Long> unassignUnusedStudentsTestsByInterimTestId(Long interimTestId) {
		return interimMapper.unassignUnusedStudentsTestsByInterimTestId(interimTestId);
	}
	
	@Override
	public void updateById(InterimTest interimTest) {
		interimMapper.updateById(interimTest);
	}
	
}
