/**
 * 
 */
package edu.ku.cete.service.impl.enrollment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Educator;
import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.StudentsTestsExample;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentExample;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.EnrollmentsRostersExample;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.RosterExample;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.StudentTrackerBandMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.mapper.StudentsTestSectionsDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.RosterAuditTrailHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.impl.OrganizationServiceImpl;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.UploadSpecification;
import edu.ku.cete.util.json.EnrlAndRosterEventTrackerConverter;
import edu.ku.cete.web.RosterDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class RosterServiceImpl implements RosterService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RosterServiceImpl.class);

	@Autowired
    private StudentsTestsDao studentsTestsDao;
	
	@Autowired
    private StudentsTestSectionsDao studentsTestSectionsDao;
	
	@Value("${testsession.status.rosterunenrolled}")
	private String testsessionStatusRosterUnenrolledCategoryCode;  
	
	@Value("${studentstests.status.rosterunenrolled}")
	private String studentstestsStatusRosterUnenrolledCategoryCode;  
	
	@Value("${studentstestsections.status.rosterunenrolled}")
	private String studentsTestSectionsStatusRosterUnenrolledCategoryCode;  
	
    @Autowired
    private TestSessionService testSessionService;
	
    @Autowired
	private ItiTestSessionService itiTestSessionService;
    
	
	/**
	 * rosterDao.
	 */
	@Autowired
	private RosterDao rosterDao;
	/**
	 * organizationDao.
	 */
	@Autowired
	private OrganizationService organizationService;
	/**
	 * categoryDao.
	 */
	@Autowired
	private CategoryDao categoryDao;
	/**
	 * categoryTypeDao.
	 */
	@Autowired
	private CategoryTypeDao categoryTypeDao;
	/**
	 * enrollmentsRostersDao
	 */
	@Autowired
	private EnrollmentsRostersService enrollmentsRostersService;
	/**
	 * uploadSpecification.
	 */
	@Autowired
	private UploadSpecification uploadSpecification;
	/**
	 * userService.
	 */
	@Autowired
	private UserService userService;
	/**
	 * studentService.
	 */
	@Autowired
	private StudentService studentService;
	/**
	 * enrollmentService.
	 */
	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private ContentAreaService contentAreaService;
	/**
	 * courseEnrollmentStatusType.
	 */
	private CategoryType courseEnrollmentStatusType = null;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;

	/**
	 * 
	 */
	private final String STCORecordType = "STCO";

	private final String SCRS_RECORD_TYPE = "SCRS_RECORD_TYPE";
	/**
	 * gradeCourseService.
	 */
	@Autowired
	private GradeCourseService gradeCourseService;

	@Autowired
	private StudentsTestsService studentsTestsService;	

	@Autowired
	private EnrollmentsRostersDao enrollmentsRostersDao;

	@Autowired
	private StudentDao studentDao;

	/**
	 * Added for enrollment upload using spring batch to find out restricted
	 ****/
	@Autowired
	private RestrictedResourceConfiguration restrictedResourceConfiguration;
	@Autowired
	private ResourceRestrictionService resourceRestrictionService;
	@Autowired
	private PermissionUtil permissionUtil;
	/**
	 * AuditTrailHistoryMapper.
	 */
	@Autowired
	private UserAuditTrailHistoryMapper userAuditTrailHistoryMapperDao;

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;
	
    @Autowired
    private AuthoritiesDao authoritiesDao;
    
    @Autowired
    private StudentTrackerBandMapper studentTrackerBandMapper;
    
  
    @Autowired
    private OrganizationDao organizationDao;
	/**
	 * logger.
	 */
	private final Logger
	logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	/**
	 * setRosterMetaData.
	 */
	@PostConstruct
	public final void setRosterMetaData() {
		// TODO do it in post construct ?
		List<String> categoryTypeCodesForRoster = new ArrayList<String>();
		categoryTypeCodesForRoster.add(uploadSpecification
				.getCourseEnrollmentStatusCode());
		CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
		categoryTypeExample.createCriteria().andTypeCodeIn(
				categoryTypeCodesForRoster);
		List<CategoryType> categoryTypesForRoster = categoryTypeDao
				.selectByExample(categoryTypeExample);
		for (CategoryType categoryTypeForRoster : categoryTypesForRoster) {
			if (categoryTypeForRoster.getTypeCode().equalsIgnoreCase(
					uploadSpecification.getCourseEnrollmentStatusCode())) {
				courseEnrollmentStatusType = categoryTypeForRoster;
			}
		}
	}

	@Override
	public final int countByExample(RosterExample example) {
		return rosterDao.countByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int deleteByExample(RosterExample example) {
		return rosterDao.deleteByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int deleteByPrimaryKey(Long id) {
		return rosterDao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Roster insert(Roster roster) {		
		rosterDao.insert(roster);		
		return roster;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Roster insertSelective(Roster record) {
		rosterDao.insertSelective(record);
		return record;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Roster> selectByExample(RosterExample example) {
		return rosterDao.selectByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Roster selectByPrimaryKey(Long id) {
		return rosterDao.selectByPrimaryKey(id);
	}

	/**
	 * @param categoryTypeId
	 *            {@link Long}
	 * @param categoryCode
	 *            {@link String}
	 * @return {@link Category}
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private Category getCategory(Long categoryTypeId, String categoryCode) {
		if (StringUtils.isEmpty(categoryCode) || categoryTypeId == null) {
			return null;
		}
		CategoryExample categoryExample = new CategoryExample();
		CategoryExample.Criteria categoryCriteria = categoryExample
				.createCriteria();
		categoryCriteria.andCategoryTypeIdEqualTo(categoryTypeId);
		categoryCriteria.andCategoryCodeEqualTo(categoryCode);
		List<Category> categories = categoryDao
				.selectByExample(categoryExample);
		if (CollectionUtils.isNotEmpty(categories)) {
			// since category code is unique.
			return categories.get(0);
		}
		return null;
	}

	/**
	 * 
	 * @param name
	 *            {@link String}
	 * @return {@link ContentArea} - The content area object
	 */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ContentArea getContentArea(String name, Long organizationId) {
		ContentArea contentArea = null;
		if (!StringUtils.isEmpty(name) && organizationId != null) {
			contentArea = contentAreaService.findByContractingOrg(
					organizationId, name);
			// if no mapped contentarea found get by name or abbreviated name
			if (null == contentArea) {
				contentArea = contentAreaService
						.findByNameOrAbbreviatedName(name);
			}
		}

		return contentArea;
	}

	/**
	 * @param scrsRecord
	 *            {@link ScrsRecord}
	 * @return {@link RecordSaveStatus}
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final RosterRecord cascadeAddOrUpdate(RosterRecord scrsRecord,
			ContractingOrganizationTree contractingOrganizationTree) {
		LOGGER.debug("--> cascadeAddOrUpdate");
		long mStartTime = System.currentTimeMillis();
		RecordSaveStatus scrsRecordSaveStatus = RecordSaveStatus.BEGIN_ROSTER_SAVE;
		Long currentContextUserId = scrsRecord.getCurrentContextUserId();
		// validating if attendance school is found.Needed if saving enrollment.
		// find the organization only from at or below the user's organization.
		Organization attendanceSchool = contractingOrganizationTree
				.getUserOrganizationTree().getOrganization(
						scrsRecord.getSchoolIdentifier());
		// easier to add the multiple organization context here.
		if (attendanceSchool == null) {
			scrsRecord.addInvalidField(FieldName.SCHOOL_IDENTIFIER
					+ ParsingConstants.BLANK, ParsingConstants.BLANK_SPACE
					+ scrsRecord.getSchoolIdentifier()
					+ ParsingConstants.BLANK_SPACE, true,
					InvalidTypes.SCHOOL_IDENTIFIER_NOT_FOUND + ParsingConstants.BLANK_SPACE);
			scrsRecord
			.setSaveStatus(RecordSaveStatus.AYP_SCHOOL_NOT_FOUND);
			return scrsRecord;
		}
		attendanceSchool.setCurrentContextUserId(scrsRecord
				.getCurrentContextUserId());

		Organization aypSchool = contractingOrganizationTree
				.getUserOrganizationTree().getOrganization(
						scrsRecord.getEnrollment().getAypSchoolIdentifier());		
		if (scrsRecord.getRosterRecordType() != null
				&& scrsRecord.getRosterRecordType().equalsIgnoreCase(
						STCORecordType)) {
			if(aypSchool == null) {
				scrsRecord.addInvalidField(FieldName.AYP_SCHOOL_IDENTIFIER
						+ ParsingConstants.BLANK, ParsingConstants.BLANK_SPACE
						+ scrsRecord.getEnrollment().getAypSchoolIdentifier()
						+ ParsingConstants.BLANK_SPACE, true,
						InvalidTypes.SCHOOL_IDENTIFIER_NOT_FOUND + ParsingConstants.BLANK_SPACE);
				scrsRecord
				.setSaveStatus(RecordSaveStatus.ATTENDANCE_SCHOOL_NOT_FOUND);
				return scrsRecord;
			}			
		}				
		// This is for Webservice SCTO, where we would be getting Educator
		// School Identifier.
		Organization educatorSchool = null;
		if (scrsRecord.getRosterRecordType() != null
				&& scrsRecord.getRosterRecordType().equalsIgnoreCase(
						STCORecordType)) {
			educatorSchool = contractingOrganizationTree
					.getUserOrganizationTree().getOrganization(
							((WebServiceRosterRecord) scrsRecord)
							.getEducatorSchoolIdentifier());
			// easier to add the multiple organization context here.
			if (educatorSchool != null) {
				educatorSchool.setCurrentContextUserId(scrsRecord
						.getCurrentContextUserId());
			}
		}

		// TODO Find a similar pattern called content area context. This is
		// needed for exit upload as well.
		/*
		 * ContentArea scrsStateCourse = null; String stateCourseCode =
		 * scrsRecord.getStateCourseCode(); if
		 * (StringUtils.isNotEmpty(stateCourseCode)) { scrsStateCourse = new
		 * ContentArea(); if
		 * (scrsRecord.getStateCourse().getCurrentContextUserId() != null) {
		 * scrsStateCourse.setCurrentContextUserId(scrsRecord
		 * .getStateCourse().getCurrentContextUserId()); } scrsStateCourse =
		 * getContentArea(stateCourseCode, attendanceSchool.getId()); }
		 */

		/*
		 * if (scrsRecord.getStateCourseCode() == null ||
		 * "".equals(scrsRecord.getStateCourseCode())) {
		 * scrsRecord.setSaveStatus(scrsRecordSaveStatus); // could not find the
		 * state course scrsRecord.addInvalidField(FieldName.STATE_COURSE +
		 * ParsingConstants.BLANK, scrsRecord.getStateCourseCode() +
		 * ParsingConstants.BLANK, true, InvalidTypes.NOT_FOUND); return
		 * scrsRecord; }
		 */
		// scrsRecord.setStateCourse(scrsStateCourse);
		scrsRecord.setSchool(attendanceSchool);

		// This is done here because student needs to be selectively updated,
		// which is different from enrollment upload.
		Student student = scrsRecord.getStudent();

		String mappedCompRace = null; //TODO need to check whether this functionality is needed or not
		if(StringUtils.isNotEmpty(student.getComprehensiveRace())){
			mappedCompRace = studentDao.findMappedComprehensiveRaceCode(student.getComprehensiveRace());
		}
		student.setComprehensiveRace(mappedCompRace);

		if(student.getStateId() == null) {
			// set contracting organization on student
			Organization studentState = organizationService.getContractingOrganization(attendanceSchool.getId());
			student.setStateId(studentState.getId());
			scrsRecord.getStudent().setStateId(studentState.getId());
		}

		long startTime = System.currentTimeMillis();
		if (scrsRecord.getRosterRecordType() != null
				&& scrsRecord.getRosterRecordType().equalsIgnoreCase(
						STCORecordType)) {
			student = studentService.addOrUpdateSelective(student, attendanceSchool.getId());
		} else {
			student = studentService.validateIfStudentExists(student);
		}		

		LOGGER.debug("student Time Taken :"
				+ (System.currentTimeMillis() - startTime));
		// this is necessary because some where student object is being
		// re-copied by Spring.
		if (student.isDoReject()) {
			scrsRecord.getInValidDetails().addAll(student.getInValidDetails());
			scrsRecord.setDoReject(true);
			return scrsRecord;
		}
		scrsRecord.setStudent(student);
		if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_ADDED)) {
			scrsRecord.setCreated(true);
		}

		// if for stco recordtype; process stco if student doesn't exist
		if (scrsRecord.getRosterRecordType() != null
				&& !scrsRecord.getRosterRecordType().equalsIgnoreCase(
						STCORecordType)) {
			if (!student.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
				scrsRecord.setDoReject(true);
				scrsRecord
				.addInvalidField(FieldName.STUDENT_STATE_ID
						+ ParsingConstants.BLANK,
						"not enrolled in school ",							
						true, ParsingConstants.BLANK_SPACE);
				return scrsRecord;
			}
		}

		// validate if the teacher is found.Needed if saving roster.
		User educator = scrsRecord.getEducator();
		scrsRecord.appendSchoolIdentifier(attendanceSchool.getId());
		educator.setActiveFlag(false);
		startTime = System.currentTimeMillis();

		//check with unique common identifier and state.
		//if exits check if org exists or not, if not add and populate teacher role -- TASC and TEST
		//if not found with unique common identifier and state, check with email


		// Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value
		// violates unique constraint "uk_email"
		User dbEducator = userService.getByEmail(educator.getEmail());
		if (dbEducator != null
				&& !dbEducator.getUniqueCommonIdentifier().equals(
						educator.getUniqueCommonIdentifier())) {
			scrsRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER
					+ ParsingConstants.BLANK,
					"xml: " + scrsRecord.getEducatorIdentifier() + "db:"
							+ dbEducator.getUniqueCommonIdentifier()
							+ ParsingConstants.BLANK, false,
							InvalidTypes.MIS_MATCH);
			scrsRecord.setDoReject(true);
			if (!(scrsRecord.getRosterRecordType().equalsIgnoreCase(SCRS_RECORD_TYPE))){ // TODO need to verify whether this step is needed or not
				return scrsRecord;
			}
		}
		// End Cause: org.postgresql.util.PSQLException: ERROR: duplicate key
		// value violates unique constraint "uk_email"

		// This is for SCTO Webservice, where we would be getting Educator
		// School Identifier.
		if (educatorSchool != null) {
			educator = userService.addIfNotPresent(educator, student.getStateId(), educatorSchool);
			LOGGER.debug("educator Time Taken  :"
					+ (System.currentTimeMillis() - startTime));
			if (educator.getSaveStatus()
					.equals(RecordSaveStatus.EDUCATOR_ADDED)) {
				scrsRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER
						+ ParsingConstants.BLANK,
						scrsRecord.getEducatorIdentifier()
						+ ParsingConstants.BLANK, false,
						InvalidTypes.CREATED_NEW);
				scrsRecord.setCreated(true);
			}
			scrsRecord.setEducator(educator);
		} else {
			// This is because either we are not getting educatorSchool or it
			// can be csv roster upload.
			/*
			 * DE5842 removed the logic that is creating a new user on roster
			 * upload now just rejecting and sending message to create new user
			 * for the teacher. educator = userService.addIfNotPresent(educator,
			 * contractingOrganizationTree.getUserOrganizationTree()
			 * .getUserOrganizationTree(), attendanceSchool);
			 */
			List<User> foundEducators = userService
					.getByOrganizationAndUniqueCommonIdentifier(
							attendanceSchool.getId(),
							educator.getUniqueCommonIdentifier());
			User foundEducator = null;
			if (!foundEducators.isEmpty()) {
				foundEducator = foundEducators.get(0);
				if (foundEducator == null) {
					if (scrsRecord.getRosterRecordType().equalsIgnoreCase(SCRS_RECORD_TYPE) 
							|| scrsRecord.getRosterRecordType().equalsIgnoreCase(STCORecordType)){
						scrsRecord.addInvalidField(
								ParsingConstants.BLANK
								+ FieldName.EDUCATOR_IDENTIFIER,
								ParsingConstants.BLANK_SPACE
								+ scrsRecord.getEducatorIdentifier()
								+ ParsingConstants.BLANK_SPACE, true,
								InvalidTypes.EDUCATOR_ID_NOT_FOUND + ParsingConstants.BLANK_SPACE);
						scrsRecord.setDoReject(true);
						return scrsRecord;
					} else {
						scrsRecord
						.addInvalidField("Please create a new user for attendance school: "+attendanceSchool.getDisplayIdentifier()+" because the "+FieldName.EDUCATOR_IDENTIFIER
								+ ParsingConstants.BLANK,
								scrsRecord.getEducatorIdentifier()
								+ ParsingConstants.BLANK, true,
								InvalidTypes.NOT_FOUND);
						scrsRecord.setDoReject(true);
						return scrsRecord;
					}
				}
			} else {
				Organization residenceDistrict = contractingOrganizationTree
						.getUserOrganizationTree().getOrganization(
								scrsRecord.getResidenceDistrictIdentifier());
				String district = "";
				if (residenceDistrict != null) {
					foundEducators = userService
							.getByOrganizationAndUniqueCommonIdentifier(
									residenceDistrict.getId(),
									educator.getUniqueCommonIdentifier());
					district = residenceDistrict.getDisplayIdentifier();
				}

				if (!foundEducators.isEmpty()) {
					foundEducator = foundEducators.get(0);
				}

				if (foundEducator == null) {
					if (scrsRecord.getRosterRecordType().equalsIgnoreCase(SCRS_RECORD_TYPE) 
							|| scrsRecord.getRosterRecordType().equalsIgnoreCase(STCORecordType)){
						scrsRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER
								+ ParsingConstants.BLANK,
								scrsRecord.getEducatorIdentifier()
								+ ParsingConstants.BLANK_SPACE, true,
								InvalidTypes.EDUCATOR_ID_NOT_FOUND + ParsingConstants.BLANK_SPACE);
						scrsRecord.setDoReject(true);
						return scrsRecord;
					} else {
						scrsRecord
						.addInvalidField("Please create a new user for the educator at the residence district: "+ district + " or attendance school: "+attendanceSchool.getDisplayIdentifier()+" because the "+FieldName.EDUCATOR_IDENTIFIER
								+ ParsingConstants.BLANK,
								scrsRecord.getEducatorIdentifier()
								+ ParsingConstants.BLANK, false,
								InvalidTypes.NOT_FOUND);
						scrsRecord.setDoReject(true);
						return scrsRecord;
					}
				}
			}
			LOGGER.debug("educator Time Taken  :"
					+ (System.currentTimeMillis() - startTime));
			scrsRecord.setEducator(foundEducator);
		}

		// set course enrollment status and state subject area and state course.
		Category scrsEnrollmentStatus = getCategory(
				courseEnrollmentStatusType.getId(),
				scrsRecord.getEnrollmentStatusCodeStr());
		scrsRecord.setEnrollmentStatus(scrsEnrollmentStatus);

		ContentArea scrsStateSubjectArea = getContentArea(
				scrsRecord.getStateSubjectAreaCode(), attendanceSchool.getId());
		if (scrsStateSubjectArea == null) {
			scrsRecord.setSaveStatus(scrsRecordSaveStatus);
			// could not find the subject area
			scrsRecord.addInvalidField(FieldName.SUBJECT
					+ ParsingConstants.BLANK,
					scrsRecord.getStateSubjectAreaCode()
					+ ParsingConstants.BLANK_SPACE, true,
					InvalidTypes.SUBJECT_NOT_FOUND+ParsingConstants.BLANK);
			return scrsRecord;
		}
		scrsRecord.setStateSubjectArea(scrsStateSubjectArea);

		GradeCourse gradeCourse = null;
		GradeCourse grade = null;
		if (scrsRecord.getEnrollment().getCurrentGradeLevel() != null) {
			gradeCourse = new GradeCourse();
			String strGradeLevel = "0"
					+ scrsRecord.getEnrollment().getCurrentGradeLevel();
			// add a zero to the beginning and then if there are more than 2
			// digits remove the first digit
			strGradeLevel = strGradeLevel.length() > 2 ? strGradeLevel
					.substring(1) : strGradeLevel;
					// store in abbr name to send to query
					gradeCourse.setAbbreviatedName(strGradeLevel);
					gradeCourse.setCurrentContextUserId(scrsRecord.getUser().getId());
					grade = gradeCourseService
							.getWebServiceGradeCourseByCode(gradeCourse);
		}

		Long rosterCourseId = null;
		if (scrsRecord.getRosterRecordType().equalsIgnoreCase(SCRS_RECORD_TYPE) && scrsRecord.getStateCourseCode() != null
				&& scrsRecord.getStateSubjectArea() != null) {
			GradeCourse validGC = findValidCourse(scrsRecord.getStateCourseCode(), 
					scrsRecord.getStateSubjectArea().getId()); 
			if (validGC == null) {
				scrsRecord.addInvalidField(FieldName.COURSE_AREA
						+ ParsingConstants.BLANK,
						scrsRecord.getStateCourseCode()
						+ ParsingConstants.BLANK_SPACE, true,
						InvalidTypes.COURSE_NOT_FOUND + ParsingConstants.BLANK);
				return scrsRecord;
			} else {				
				rosterCourseId = validGC.getId();
			}
		}

		if (grade != null) {
			scrsRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
		} else {
			scrsRecord.getEnrollment().setCurrentGradeLevel(null);
		}			
		startTime = System.currentTimeMillis();
		Enrollment newEnrollment = scrsRecord.getEnrollment();
		newEnrollment.setAttendanceSchoolId(attendanceSchool.getId());
		newEnrollment.setStudentId(student.getId());
		newEnrollment.setCurrentContextUserId(currentContextUserId);
		if(aypSchool != null && aypSchool.getId() != null) {
			newEnrollment.setAypSchoolId(aypSchool.getId());
		} else {
			newEnrollment.setAypSchoolId(attendanceSchool.getId());
		}		
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		// if student exists in the system then try to find his enrollments for
		// the school.
		if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
			enrollments = enrollmentService.addIfNotPresentSTCO(newEnrollment);
			// even if one enrollment is added, it means we have added new
			// enrollments.
			for (Enrollment enrollment : enrollments) {
				if (enrollment.getSaveStatus().equals(
						RecordSaveStatus.ENROLLMENT_ADDED)) {
					scrsRecordSaveStatus = RecordSaveStatus.ENROLLMENT_ADDED;
				}
			}
		}
		// DO not look for enrollments as the student is just created.
		else {
			scrsRecord.setCreated(true);
			newEnrollment = enrollmentService.add(newEnrollment);
			enrollments.add(newEnrollment);
			scrsRecordSaveStatus = RecordSaveStatus.ENROLLMENT_ADDED;
		}
		// find rosters if any for the given school i.e. enrollments for given
		// school
		// but not necessarily for the given student.
		LOGGER.debug("enrollment Time Taken  :"
				+ (System.currentTimeMillis() - startTime));
		startTime = System.currentTimeMillis();

		Roster roster = new Roster();
		roster.setCourseSectionName(scrsRecord.getCourseSection());
		roster.setAttendanceSchoolId(attendanceSchool.getId());
		roster.setTeacherId(scrsRecord.getEducator().getId());
		roster.setStateSubjectAreaId(scrsRecord.getStateSubjectAreaId());
		roster.setStateCourseCode(scrsRecord.getStateCourseCode());
		roster.setStateCoursesId(rosterCourseId);
		roster.setRestrictionId(scrsRecord.getRoster().getRestrictionId());
		roster.setCurrentSchoolYear(scrsRecord.getCurrentSchoolYear());
		roster.setSourceType(scrsRecord.getSourceType());
		if(aypSchool != null && aypSchool.getId() != null) {
			roster.setAypSchoolId(aypSchool.getId());			
		} else {
			roster.setAypSchoolId(attendanceSchool.getId());			
		}
		if (roster.getRestrictionId() == null) {
			roster.setRestrictionId(scrsRecord.getEnrollment()
					.getRestrictionId());
		}
		roster.setCurrentContextUserId(currentContextUserId);

		// This is for populating the
		// KCCID,LocalCourseId,Educatorschooldisplayidentifier which we would be
		// getting in STCO webservice data only.
		if (scrsRecord.getRosterRecordType() != null
				&& scrsRecord.getRosterRecordType().equalsIgnoreCase(
						STCORecordType)) {
			roster.setStateSubjectCourseIdentifier(((WebServiceRosterRecord) scrsRecord)
					.getStateSubjectCourseIdentifier());
			roster.setLocalCourseId(((WebServiceRosterRecord) scrsRecord)
					.getLocalCourseId());
			roster.setEducatorschooldisplayidentifier(((WebServiceRosterRecord) scrsRecord)
					.getEducatorSchoolIdentifier());
			if(StringUtils.isNotEmpty(scrsRecord.getSourceType()) 
					&& scrsRecord.getSourceType().equalsIgnoreCase(STCORecordType)) {
				String courseSectionName = getWebServiceCourseSectionName(scrsRecord.getEducator().getFirstName(), 
						scrsRecord.getEducator().getSurName(), scrsRecord.getStateSubjectArea().getAbbreviatedName(),
						scrsRecord.getSourceType());
				roster.setCourseSectionName(courseSectionName);
			}
		}
		roster.setAuditColumnProperties();
		String removeFromRosterStatus = scrsRecord.getRemovefromroster();
		boolean removeFromRosterFlag = false;
		if (scrsRecord.getRosterRecordType().equalsIgnoreCase(SCRS_RECORD_TYPE) 
				&& removeFromRosterStatus != null && (removeFromRosterStatus.equalsIgnoreCase("Yes")
						|| removeFromRosterStatus.equalsIgnoreCase("Remove"))) {
			scrsRecord.setRoster(getRosterMatchedWithStateCourseCode(roster, attendanceSchool.getId()));			
			if (!isRosterExitsWithStateCourseCode(roster,
					attendanceSchool.getId()) || scrsRecord.getRoster().getId() == null) {
				scrsRecord.addInvalidField(FieldName.REMOVE_FROM_ROSTER + ParsingConstants.BLANK,
						ParsingConstants.BLANK, true,
						InvalidTypes.REMOVE_FROM_ROSTER_ERROR+ ParsingConstants.BLANK);
				return scrsRecord;
			}
			removeFromRosterFlag = true;
		} else {
			List<Roster> rosters = addIfNotPresent(roster,
					attendanceSchool.getId());
			LOGGER.debug("roster Time Taken  :"
					+ (System.currentTimeMillis() - startTime));
			startTime = System.currentTimeMillis();
			if (rosters.size() > 1) {
				scrsRecord.setSaveStatus(scrsRecordSaveStatus);

				// the same teacher is teaching more than one class/roster.
				// in the same school and they have the same section name.
				scrsRecord.addInvalidField(FieldName.UPLOAD
						+ ParsingConstants.BLANK,
						ParsingConstants.BLANK, true,
						InvalidTypes.ROSTER_NOT_UNIQUE + ParsingConstants.BLANK);
				return scrsRecord;
			}
			if (rosters.get(0).getSaveStatus()
					.equals(RecordSaveStatus.ROSTER_ADDED)) {
				scrsRecord.setCreated(true);
			}
			scrsRecord.setRoster(rosters.get(0));
		}
		// add enrollment to roster.
		for (Enrollment enrollment : enrollments) {
			EnrollmentsRosters enrollmentsRosters = new EnrollmentsRosters(
					enrollment.getId(), scrsRecord.getRoster().getId());

			if (null != scrsRecord.getEnrollmentStatusCode()
					&& (2 == scrsRecord.getEnrollmentStatusCode())) {
				enrollmentsRosters.setActiveFlag(false);
			}

			if (removeFromRosterFlag) {				
				enrollmentsRosters.setActiveFlag(false);
				if (scrsRecord.getEnrollmentStatus() != null) {
					enrollmentsRosters.setCourseEnrollmentStatusId(scrsRecord
							.getEnrollmentStatus().getId());
				}
				enrollmentsRosters.setCurrentContextUserId(currentContextUserId);								
				int noOfRostersUpdated = enrollmentsRostersService.updateEnrollementRosterToInActive(enrollmentsRosters);
				scrsRecord
				.setSaveStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS);
				if (noOfRostersUpdated > 0) {
					scrsRecord.setRemoved(true);
				}
			} else {

				if (scrsRecord.getEnrollmentStatus() != null) {
					enrollmentsRosters.setCourseEnrollmentStatusId(scrsRecord
							.getEnrollmentStatus().getId());
				}
				enrollmentsRosters.setCurrentContextUserId(currentContextUserId);
				enrollmentsRostersService.addEnrollmentToRoster(enrollmentsRosters);
				if(StringUtils.isNotEmpty(scrsRecord.getSourceType())) {
					scrsRecord.getRoster().setSourceType(scrsRecord.getSourceType());					
				}
				addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, scrsRecord.getRoster());				
				scrsRecord
				.setSaveStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS);				
			}
		}
		LOGGER.debug("enrollment to roster Time Taken  :"
				+ (System.currentTimeMillis() - startTime));

		LOGGER.debug("Total Time Taken  :"
				+ (System.currentTimeMillis() - mStartTime));
		scrsRecord.setSaveStatus(RecordSaveStatus.SCRS_RECORD_UPLOAD_COMPLETE);
		return scrsRecord;
	}

	@Override
	public String getWebServiceCourseSectionName(String educatorFirstName, String educatorLastName, String subjectAreaAbbrName, String source) {
		String courseSectionName = StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(educatorFirstName) && 
				StringUtils.isNotEmpty(educatorLastName) 
				&& StringUtils.isNotEmpty(subjectAreaAbbrName) && StringUtils.isNotEmpty(source)) {
			if(educatorLastName.length() > 10) {
				educatorLastName = educatorLastName.substring(0, 10);
			}
			courseSectionName = subjectAreaAbbrName + "-" + educatorFirstName.substring(0,1) + "." +
					educatorLastName + " " + source;
		}
		return courseSectionName;
	}

	public GradeCourse findValidCourse(String stateCourseCode, Long contentAreaId) {
		List<GradeCourse> gradeCourses = gradeCourseService
				.getCoursesByContentArea(contentAreaId);
		if (gradeCourses != null && gradeCourses.size() > 0) {
			for (GradeCourse gc : gradeCourses) {
				if (gc != null && gc.getAbbreviatedName().equalsIgnoreCase(stateCourseCode)) {
					return gc;
				}
			}
		}
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Roster> addIfNotPresent(Roster roster,
			Long attendanceSchoolId) {				
		List<Roster> rosters = getRosterByRosterAndAttendanceSchool(roster, attendanceSchoolId);
		if (CollectionUtils.isEmpty(rosters)) {
			rosters = new ArrayList<Roster>();
			Roster newRoster = insert(roster);
			newRoster.setSaveStatus(RecordSaveStatus.ROSTER_ADDED);
			rosters.add(newRoster);
		}
		return rosters;
	}

	public List<Roster> getAllRostersToCheckForUpdateDuplicates(Roster roster, Long attendanceSchoolId) {
		// TODO Need to change from distinct. add limit.
		RosterExample rosterExample = new RosterExample();
		rosterExample.setDistinct(true);
		RosterExample.Criteria rosterCriteria = rosterExample.createCriteria();
		if(roster.getCourseSectionName() != null) {
			rosterCriteria.andCourseSectionNameEqualTo(roster
					.getCourseSectionName());
		}
		rosterCriteria.andIdNotEqualTo(roster.getId());
		rosterCriteria.andTeacherIdEqualTo(roster.getTeacherId());
		rosterCriteria.andStateCoursesIdEqualTo(roster.getStateCoursesId());

		rosterCriteria.andStateSubjectAreaIdEqualTo(roster
				.getStateSubjectAreaId());

		rosterCriteria.andCurrentSchoolYearEqualTo(roster.getCurrentSchoolYear());
		rosterCriteria.andAttendanceSchoolIdEqualTo(attendanceSchoolId);
		rosterCriteria.andActiveFlagEqualTo(true);	


		List<Roster> rosters = rosterDao.selectByEnrollment(rosterExample);
		return rosters;
	}

	public List<Roster> getRostersByCriteria(Roster roster,
			Long attendanceSchoolId) {
		// TODO Need to change from distinct. add limit.
		RosterExample rosterExample = new RosterExample();
		rosterExample.setDistinct(true);
		RosterExample.Criteria rosterCriteria = rosterExample.createCriteria();
		if(roster.getCourseSectionName() != null) {
			rosterCriteria.andCourseSectionNameEqualTo(roster
					.getCourseSectionName());
		}
		rosterCriteria.andTeacherIdEqualTo(roster.getTeacherId());
		rosterCriteria.andStateSubjectAreaIdEqualTo(roster
				.getStateSubjectAreaId());
		if (StringUtils.isNotBlank(roster.getStateCourseCode())) {
			rosterCriteria.andStateCourseCodeEqualTo(roster
					.getStateCourseCode());
		} else {
			rosterCriteria.andStateCourseCodeEqualTo(StringUtils.EMPTY);
		}

		rosterCriteria.andCurrentSchoolYearEqualTo(roster.getCurrentSchoolYear());
		rosterCriteria.andAttendanceSchoolIdEqualTo(attendanceSchoolId);
		rosterCriteria.andAypSchoolIdEqualTo(roster.getAypSchoolId());
		rosterCriteria.andActiveFlagEqualTo(true);		

		List<Roster> rosters = rosterDao.selectByEnrollment(rosterExample);
		return rosters;
	}

	public Roster getRosterMatchedWithStateCourseCode(Roster roster, Long attendeSchoolId) {		
		List<Roster> existingRosters = getRostersByCriteria(roster,
				attendeSchoolId);
		if (CollectionUtils.isNotEmpty(existingRosters)) {
			for (Roster existingRoster : existingRosters) {
				if(StringUtils.isEmpty(existingRoster.getStateCourseCode()) && StringUtils.isEmpty(roster.getStateCourseCode())) {
					return existingRoster;
				}

				if(StringUtils.isNotEmpty(existingRoster.getStateCourseCode()) && StringUtils.isNotEmpty(roster.getStateCourseCode())) {
					if(existingRoster.getStateCourseCode().equalsIgnoreCase(
							roster.getStateCourseCode())) {
						return existingRoster;
					}
				}
			}
		}

		return roster;
	}

	public boolean isRosterExitsWithStateCourseCode(Roster roster,
			Long attendanceSchoolId) {		
		List<Roster> existingRosters = getRostersByCriteria(roster,
				attendanceSchoolId);
		if (CollectionUtils.isNotEmpty(existingRosters)) {			
			for (Roster existingRoster : existingRosters) {
				if (StringUtils.isEmpty(existingRoster.getStateCourseCode()) && StringUtils.isEmpty(roster.getStateCourseCode())) {
					return true;
				}

				if(StringUtils.isNotEmpty(existingRoster.getStateCourseCode()) && StringUtils.isNotEmpty(roster.getStateCourseCode())) {
					if(existingRoster.getStateCourseCode().equalsIgnoreCase(
							roster.getStateCourseCode())) {
						return true;
					}
				}

			}
		}
		return false;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByExampleSelective(Roster record,
			RosterExample example) {
		return rosterDao.updateByExampleSelective(record, example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByExample(Roster record, RosterExample example) {
		return rosterDao.updateByExample(record, example);
	}


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByPrimaryKeySelective(Roster record) {
		return rosterDao.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateByPrimaryKey(Roster record) {
		String rosterBeforeInsertString = null;
		String rosterAfterInsertString = null;
		Roster beforeRoster = rosterDao.getRosterJsonFormatData(record.getId());

		int updated = rosterDao.updateByPrimaryKey(record);

		Roster afterRoster = rosterDao.getRosterJsonFormatData(record.getId());

		rosterBeforeInsertString = beforeRoster.buildJsonString();
		rosterAfterInsertString = afterRoster.buildJsonString();
		insertIntoDomainAuditHistory(beforeRoster.getId(), beforeRoster.getModifiedUser(),
				EventTypeEnum.UPDATE.getCode(), SourceTypeEnum.MANUAL.getCode(), rosterBeforeInsertString,
				rosterAfterInsertString);
		return updated;
	}

	/**
	 * @param teacherId
	 *            long
	 * @param organizationId
	 *            long
	 * @return List<Roster>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Roster> findByTeacherId(long teacherId,
			long organizationId) {
		return rosterDao.findByTeacherId(teacherId, organizationId);
	}

	/**
	 * Restriction should not be null.
	 * 
	 * @param userId
	 *            {@link Long}
	 * @param selectedOrganizationId
	 *            {@link Long}
	 * @param restriction
	 *            {@link Restriction}
	 * @return List<RosterDTO>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<RosterDTO> getRosterDtoByUserAndOrg(long userId,
			long selectedOrganizationId, Restriction restriction) {

		return rosterDao.getRosterDtoByUserAndOrg(userId,
				selectedOrganizationId, restriction);
	}

	/**
	 * Restriction should not be null.
	 * 
	 * @param userId
	 *            {@link Long}
	 * @param restriction
	 *            {@link Restriction}
	 * @param userOrganizationId
	 *            {@link Long}
	 * @return List<RosterDTO>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Roster> getRosterByUserRestrictionAndOrganization(
			Long userId, Restriction restriction, Long userOrganizationId) {
		List<Roster> rosters = new ArrayList<Roster>();
		if (userId != null && userOrganizationId != null) {
			rosters = rosterDao.getRosterByUserRestrictionAndOrganization(
					userId, restriction, userOrganizationId);
		}
		return rosters;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Roster> getRostersForReports(Map<String, Object> parameters) {
		List<Roster> rosters = new ArrayList<Roster>();
		if (parameters.get("userId") != null && parameters.get("schoolId") != null) {
			rosters = rosterDao.getRosterForReports(parameters);
		}
		return rosters;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Roster> getRostersForTeacherReports(Long userId, Long currentSchoolYear, Long apId, Long groupId, Long currentOrganizationId) {
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(apId);
		String permissionName="NOTFOUND";
		if ("DLM".equalsIgnoreCase(ap.getAbbreviatedname())){
			permissionName = "VIEW_ALT_YEAREND_STD_IND_REP";
		}else if ("CPASS".equalsIgnoreCase(ap.getAbbreviatedname())){
			permissionName = "VIEW_CPASS_ASMNT_STUDENT_IND_REP";
		}
		Authorities auth = authoritiesDao.getByAuthority(permissionName);
		List<Roster> rosters = new ArrayList<Roster>();
		if (userId != null && currentSchoolYear != null && auth != null) {
			rosters = rosterDao.getRosterForTeacherReports(userId, currentSchoolYear, apId, auth.getAuthoritiesId(), groupId, currentOrganizationId);
		}
		return rosters;
	}
	
	/**
	 * @param organizationId
	 *            long
	 * @return List<RosterDTO>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<RosterDTO> getRosterDtoByOrg(long organizationId) {
		return rosterDao.getRosterDtoByOrg(organizationId);
	}

	/**
	 * @param List
	 *            <Long> rosterIds
	 * @return List<RosterDTO>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<RosterDTO> getRosterDtoInRosterIds(List<Long> rosterIds) {
		return rosterDao.getRosterDtoInRosterIds(rosterIds);
	}

	/**
	 * @param rosterId
	 *            long
	 * @return {@link Long}
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Integer getNumberOfStudents(long rosterId) {
		// TODO change it to group by and in clause sql.
		return rosterDao.getNumberOfStudents(rosterId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.enrollment.RosterService#getRostersToViewByOrg(java
	 * .lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */

	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<RosterDTO> getRostersToViewByOrg(Long organizationId,
			String orderByColumn, String order, Integer offset,
			Integer limitCount) {
		return rosterDao.getRostersToViewByOrg(organizationId, orderByColumn,
				order, offset, limitCount);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.enrollment.RosterService#countRostersToViewByOrg(
	 * java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Integer countRostersToViewByOrg(Long organizationId) {
		return rosterDao.countRostersToViewByOrg(organizationId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.enrollment.RosterService#getRostersToViewByOrg(java
	 * .lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<RosterDTO> getRostersToViewByOrg(
			Map<String, Object> criteria, String orderByColumn, String order,
			Integer offset, Integer limitCount) {
		criteria.put("limit", limitCount);
		criteria.put("offset", offset);
		criteria.put("orderByColumn", orderByColumn);
		criteria.put("order", order);
		return rosterDao.getRostersToViewByOrg(criteria);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<RosterDTO> findRostersforTeacherIdInCurrentYearForMergeUser(
			Map<String, Object> criteria, String orderByColumn, String order,
			Integer offset, Integer limitCount) {
		criteria.put("limit", limitCount);
		criteria.put("offset", offset);
		criteria.put("orderByColumn", orderByColumn);
		criteria.put("order", order);
		return rosterDao.findRostersforTeacherIdInCurrentYearForMergeUser(criteria);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.enrollment.RosterService#countRostersToViewByOrg(
	 * java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Integer countRostersToViewByOrg(Map<String, Object> criteria) {
		return rosterDao.countRostersToViewByOrg(criteria);
	}

		@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addRmStuFromRostEventToDomainAduidtHistory(EnrollmentsRosters er, Roster upRoster) {
		if(StringUtils.isNotEmpty(upRoster.getSourceType())) {
			er.setSourceType(upRoster.getSourceType());
		} else {
			er.setSourceType("ANONYMOUS");
		}
		DomainAuditHistory domainAuditHistory = getDomainAduditHostoryForEnRoster(er);
		domainAuditHistory.setAction("RM_STUDENT_FROM_ROSTER");
		if(er.getModifiedUser() != null) {
			domainAuditHistory.setCreatedUserId(er.getModifiedUser().intValue());			
		} else {
			domainAuditHistory.setCreatedUserId(0);
		}		
		domainAuditHistory.setObjectAfterValues(EnrlAndRosterEventTrackerConverter.getAddOrRemoveStudentToRosterEvent(er));
		domainAuditHistoryDao.insert(domainAuditHistory);
	}		

	private Roster getExistingRosterDetails(Roster upRoster) {
		Roster existingRoster = new Roster();
		existingRoster.setTeacherId(upRoster.getTeacherId());
		existingRoster.setStateSubjectAreaId(upRoster.getStateSubjectAreaId());
		existingRoster.setStateCourseCode(upRoster.getStateCourseCode());
		existingRoster.setStateCoursesId(upRoster.getStateCoursesId());		
		return existingRoster;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addRosterTeacherChangeEventToDomainAuditHistory(
			Roster upRoster, Long oldEducatorId) {
		DomainAuditHistory domainAuditHistory = getDomainAduditHostoryForRoster(upRoster);
		domainAuditHistory.setAction("TEACHER_CHANGE");		
		domainAuditHistory.setObjectAfterValues(EnrlAndRosterEventTrackerConverter.getTeacherChangeEvent(upRoster, oldEducatorId));
		domainAuditHistoryDao.insert(domainAuditHistory);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addRosterSubOrGradeChangeEventToDomainAuditHistory(Roster upRoster,
			Roster existingRoster) {
		DomainAuditHistory domainAuditHistory = getDomainAduditHostoryForRoster(upRoster);
		domainAuditHistory.setAction("SUBJECT_COURSE_CHANGE");		
		domainAuditHistory.setObjectAfterValues(EnrlAndRosterEventTrackerConverter.getSubjectOrCourseChangeEvent(upRoster, existingRoster));
		domainAuditHistoryDao.insert(domainAuditHistory);
	}

	private DomainAuditHistory getDomainAduditHostoryForRoster(Roster roster) {
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		domainAuditHistory.setObjectType("ROSTER");
		if(roster.getModifiedUser() != null) {
			domainAuditHistory.setCreatedUserId(roster.getModifiedUser().intValue());
		} else {
			domainAuditHistory.setCreatedUserId(0);
		}
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setObjectId(roster.getId());
		if(StringUtils.isNotEmpty(roster.getSourceType())) {
			domainAuditHistory.setSource(roster.getSourceType());
		} else {
			domainAuditHistory.setSource("ANONYMOUS");
		}		
		return domainAuditHistory;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int createRoster(Roster record, Long[] enrollmentIds) {

		int status = 0;
		record.setAuditColumnProperties();
		record.setActiveFlag(true);
		GradeCourse gradeCourse = gradeCourseService.selectByPrimaryKey(record.getStateCoursesId());
		if(gradeCourse != null && StringUtils.isNotEmpty(gradeCourse.getAbbreviatedName())) {
			record.setStateCourseCode(gradeCourse.getAbbreviatedName());			
		} else {
			record.setStateCourseCode(StringUtils.EMPTY);
		}
		List<Roster> existingRosters = getRostersByCriteria(record, record.getAttendanceSchoolId());
		if(CollectionUtils.isEmpty(existingRosters)) {
			status = rosterDao.insert(record);
			// Add Enrollments
			for (Long eId : enrollmentIds) {
				EnrollmentsRosters er = new EnrollmentsRosters();
				er.setRosterId(record.getId());
				er.setEnrollmentId(eId);
				er.setAuditColumnProperties();
				er.setModifiedUser(record.getModifiedUser());
				er.setActiveFlag(true);
				EnrollmentsRostersExample enrollmentsRostersExample = new EnrollmentsRostersExample();
				EnrollmentsRostersExample.Criteria enrollmentsRostersCriteria = enrollmentsRostersExample
						.createCriteria();
				enrollmentsRostersCriteria.andEnrollmentIdEqualTo(er
						.getEnrollmentId());
				enrollmentsRostersCriteria.andRosterIdEqualTo(er.getRosterId());
				int updates = enrollmentsRostersDao.updateByExample(er,
						enrollmentsRostersExample);
				if (updates < 1) {
					enrollmentsRostersDao.insert(er);
					addStudentToRosterEventToDomainAuditHistory(er, record);
				}
			}
		}
		/*
		 * UdayaKiran :Created for US18182 -To save roster json in audit table 
		 * */
		if(status==1){

			String rosterBeforeInsertString = null;
			String rosterAfterInsertString = null;
			Roster inSertedRoster= rosterDao.getRosterJsonFormatData(record.getId());			
			inSertedRoster.buildJsonString();
			rosterAfterInsertString=inSertedRoster.getRosterJsonStr();
			insertIntoDomainAuditHistory(inSertedRoster.getId(),inSertedRoster.getModifiedUser(),EventTypeEnum.INSERT.getCode(),SourceTypeEnum.MANUAL.getCode(),rosterBeforeInsertString,rosterAfterInsertString);			

		}

		return status;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addStudentToRosterEventToDomainAuditHistory(EnrollmentsRosters er, Roster roster) {
		if(StringUtils.isNotEmpty(roster.getSourceType())) {
			er.setSourceType(roster.getSourceType());
		} else {
			er.setSourceType("ANONYMOUS");
		}
		DomainAuditHistory domainAuditHistory = getDomainAduditHostoryForEnRoster(er);
		domainAuditHistory.setAction("ADD_STUDENT_TO_ROSTER");
		if(er.getCreatedUser() != null) {
			domainAuditHistory.setCreatedUserId(er.getCreatedUser().intValue());			
		} else {
			domainAuditHistory.setCreatedUserId(0);			
		}
		domainAuditHistory.setObjectAfterValues(EnrlAndRosterEventTrackerConverter.getAddOrRemoveStudentToRosterEvent(er));
		domainAuditHistoryDao.insert(domainAuditHistory);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private DomainAuditHistory getDomainAduditHostoryForEnRoster(EnrollmentsRosters er) {
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		domainAuditHistory.setObjectType("ENROLLMENT_ROSTER");
		domainAuditHistory.setCreatedDate(new Date());
		if(er.getId() != null) {
			domainAuditHistory.setObjectId(er.getId());
		} else {
			EnrollmentsRosters enrls = enrollmentsRostersDao.getEnrollmentInfoByRosterIdEnrollmentId(er.getRosterId(), er.getEnrollmentId());
			domainAuditHistory.setObjectId(enrls.getId());
			er.setId(enrls.getId());			
		}
		domainAuditHistory.setSource(er.getSourceType());		
		return domainAuditHistory;		
	}

	/**
	 * @param teacherId
	 *            long
	 * @param organizationId
	 *            long
	 * @return List<Roster>
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<Roster> findRostersforTeacherId(long teacherId, long organizationId, int currentSchoolYear) {
		return rosterDao.findRostersforTeacherId(teacherId, organizationId, currentSchoolYear);
	}
  
	
	@Override
	public void validateSchoolIdentifiers(RosterRecord rosterRecord,
			Long orgId, int currSchoolYear) {
		// need to check the school identifiers in the file against the
		// student's enrollments for the same year
		// and need to check the school identifier against any related to the
		// teacher.
		// use the school identifier in the record to find the teacher with the
		// educator identifier
		// need to check the whole tree for the attendance school to be sure and
		// find the educator or
		// fail the validation if not matched, if more than one educator comes
		// back and are active
		String schoolDisplayIdentifier = rosterRecord.getSchool()
				.getDisplayIdentifier();
		Organization attendanceSchool = organizationService
				.getByDisplayIdWithContext(schoolDisplayIdentifier, orgId);
		Long studentAttendanceSchoolId = null;
		if (attendanceSchool != null) {
			studentAttendanceSchoolId = attendanceSchool.getId();
		} else {
			rosterRecord.addInvalidField(FieldName.SCHOOL_IDENTIFIER
					+ ParsingConstants.BLANK, ParsingConstants.BLANK_SPACE
					+ schoolDisplayIdentifier, true,
					InvalidTypes.SCHOOL_IDENTIFIER_NOT_FOUND
					+ ParsingConstants.BLANK);
			rosterRecord.setDoReject(true);
		}

		if (rosterRecord.getCurrentSchoolYear() != currSchoolYear) {
			rosterRecord.addInvalidField(
					ParsingConstants.BLANK + FieldName.CURRENT_SCHOOL_YEAR,
					ParsingConstants.BLANK_SPACE
					+ rosterRecord.getCurrentSchoolYear(), true,
					InvalidTypes.CURRENT_SCHOOL_YEAR_NOT_CORRECT
					+ ParsingConstants.BLANK + currSchoolYear);
			rosterRecord.setDoReject(true);
		}
		if(StringUtils.isBlank(rosterRecord.getStateStudentIdentifier())) {
			rosterRecord.addInvalidField(ParsingConstants.BLANK+FieldName.STATE_STUDENT_IDENTIFIER, 
					ParsingConstants.BLANK, true,
					InvalidTypes.STATE_STUDENT_IDENTIFIER_EMPTY +ParsingConstants.BLANK);

		} else if (studentAttendanceSchoolId != null) {
			String educatorIdentifier = rosterRecord.getEducatorIdentifier();
			EnrollmentExample enrl = new EnrollmentExample();
			enrl.createCriteria()
			.andAttendanceschoolidEqualTo(studentAttendanceSchoolId)
			.andCurrentschoolyearEqualTo(
					rosterRecord.getCurrentSchoolYear())
					.andStateStudentIdentifierEqualTo(
							rosterRecord.getStateStudentIdentifier());

			List<Enrollment> enrollments = enrollmentService
					.getByCriteria(enrl);
			if (enrollments == null || enrollments.isEmpty()) {
				rosterRecord
				.addInvalidField(FieldName.STUDENT_STATE_ID
						+ ParsingConstants.BLANK,
						"not enrolled in school "
								+ schoolDisplayIdentifier + " for "
								+ rosterRecord.getCurrentSchoolYear(),
								true, ParsingConstants.BLANK_SPACE);
				rosterRecord.setDoReject(true);
			} else {
				List<Organization> orgs = organizationService
						.getAllParents(studentAttendanceSchoolId);
				List<Long> orgIds = AARTCollectionUtil.getIds(orgs);
				orgIds.add(studentAttendanceSchoolId);
				List<User> educators = userService
						.getByUniqueCommonIdentifierAndOrgIds(
								educatorIdentifier, orgIds);

				if (educators.size() > 1) {
					rosterRecord.addInvalidField(ParsingConstants.BLANK
							+ FieldName.EDUCATOR_IDENTIFIER,
							ParsingConstants.BLANK_SPACE + educatorIdentifier,
							true, InvalidTypes.MULTIPLE_EDUCATORS);
					rosterRecord.setDoReject(true);
				} else if (educators.size() == 0) {
					rosterRecord.addInvalidField(ParsingConstants.BLANK
							+ FieldName.EDUCATOR_IDENTIFIER,
							ParsingConstants.BLANK_SPACE + educatorIdentifier
							+ ParsingConstants.BLANK_SPACE, true,
							InvalidTypes.EDUCATOR_ID_NOT_FOUND									
							+ ParsingConstants.BLANK_SPACE);
				} else {
					User educator = educators.get(0);
					List<Long> oIds = AARTCollectionUtil.getIds(educator
							.getOrganizations());
					if (!CollectionUtils.containsAny(orgIds, oIds)) {
						// if not found then failed validation
						rosterRecord.addInvalidField(ParsingConstants.BLANK
								+ FieldName.EDUCATOR_IDENTIFIER,
								ParsingConstants.BLANK_SPACE
								+ educatorIdentifier
								+ ParsingConstants.BLANK_SPACE, true,
								InvalidTypes.EDUCATOR_ID_NOT_FOUND										
								+ ParsingConstants.BLANK_SPACE);
					}
				}
			}
		}
	}

	/**
	 * @author Venkata Krishna Jagarlamudi US15407: Roster - Associate with
	 *         school year
	 * @param loggedInUserOrgId
	 * @return currentSchoolYear
	 */
	@Override
	public int getCurrentSchoolYear(long loggedInUserOrgId) {
		return rosterDao.getCurrentSchoolYear(loggedInUserOrgId);
	}

	/**
	 * @author bmohanty_sta
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get educator details by roster id.
	 */
	public Educator getEducatorByRosterId(Long rosterId){
		return rosterDao.selectEducatorByRosterId(rosterId);
	}

	@Override
	public List<Roster> getRostersByContentAreaAndEnrollment(Long contentAreaId, Enrollment enrollment) {
		return rosterDao.selectByContentAreaAndEnrollment(contentAreaId, enrollment);
	}

	/**
	 * US16352: enrollment upload using spring batch
	 * @param userDetails {@link UserDetailImpl}
	 * @return {@link Restriction}
	 */
	public Restriction getRosterRestriction(UserDetailImpl userDetails) {
		//Find the restriction for what the user is trying to do on this page.
		Restriction restriction = resourceRestrictionService.getResourceRestriction(
				userDetails.getUser().getOrganization().getId(),
				permissionUtil.getAuthorityId(
						userDetails.getUser().getAuthoritiesList(),
						RestrictedResourceConfiguration.getUploadRosterPermissionCode()),
						permissionUtil.getAuthorityId(
								userDetails.getUser().getAuthoritiesList(),
								RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
								restrictedResourceConfiguration.getRosterResourceCategory().getId());
		return restriction;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Roster> getRosterByRosterAndAttendanceSchool(Roster roster, Long attendanceSchoolId) {
		return rosterDao.selectRosterByRosterAndAttendanceSchool(roster.getCourseSectionName(), roster.getTeacherId(), 
				roster.getStateSubjectAreaId(), roster.getStateCourseCode(), roster.getCurrentSchoolYear(), roster.getAypSchoolId(), attendanceSchoolId);
	}

	@Override
	public List<Roster> getRostersBySubject(Long subjectId, Long schoolId
			, Long rosterCurrentSchoolYear, boolean isTeacher, Long educatorId) {
		return rosterDao.getRostersBySubject(subjectId, schoolId, rosterCurrentSchoolYear.intValue(), isTeacher, educatorId);
	}

	@Override
	public Roster getRostersByStudentEnrollInformation(Long studentId, String attendanceSchoolIdentifier,
			String subjectAbbreviatedName, String courseAbbreviatedName, Long currentSchoolYear) {
		return rosterDao.getRostersByStudentEnrollInformation(studentId, attendanceSchoolIdentifier,
				subjectAbbreviatedName, courseAbbreviatedName, currentSchoolYear);
	}
	/*
	 * Udaya Kiran Jagana  : created for US18182 for comparing rpster objects...
	 */
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addToRosterAuditTrailHistory(DomainAuditHistory domainAuditHistory){
		JsonNode before = null;
		JsonNode after = null;
		boolean changed= false;
		boolean isProcessed= false;
		ObjectMapper beforeObjectMapper = new ObjectMapper();
		ObjectMapper afterObjectMapper=new ObjectMapper();
		JsonNode beforeObjectRoot = beforeObjectMapper.createObjectNode();
		JsonNode afterObjectRoot = afterObjectMapper.createObjectNode();
		StringBuffer sb=new StringBuffer();
		try {
			if(domainAuditHistory.getObjectBeforeValues() == null && domainAuditHistory.getObjectAfterValues() == null){
				logger.debug("In-valid entry in Domainaudithistory table"+ domainAuditHistory.getObjectId());

			}else if(domainAuditHistory.getObjectBeforeValues() == null ){
				//To make an entry into rosterTrailHistory when roster created or uploaded 
				if (SourceTypeEnum.MANUAL.getCode().equals(domainAuditHistory.getSource())||SourceTypeEnum.UPLOAD.getCode().equals(domainAuditHistory.getSource())){			
					insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
							,"Roster Created",null,domainAuditHistory.getObjectAfterValues());
				}else {
					insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
							,domainAuditHistory.getAction(),null,domainAuditHistory.getObjectAfterValues());
				}
			}else if(domainAuditHistory.getObjectAfterValues() == null ){
				//To make an entry into rosterTrailHistory when roster inactivated 
				if (SourceTypeEnum.MANUAL.getCode().equals(domainAuditHistory.getSource())||SourceTypeEnum.UPLOAD.getCode().equals(domainAuditHistory.getSource())){			
					insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
							,"Roster Inactivated",domainAuditHistory.getObjectBeforeValues(),null);
				}
			}else{
				//To make an entry into rosterTrailHistory when roster edited 
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());
				//Assuming always the json structure for before object and after object is same 			
				for(Iterator<Entry<String, JsonNode>> it = before.fields();it.hasNext();){

					Entry<String, JsonNode> entry = it.next();
					String key = entry.getKey();					
					//students added or removed start							
					if(!"ModifiedDate".equalsIgnoreCase(key) && !"ModifiedUser".equalsIgnoreCase(key)){
						if("Students".equals(key)&&!before.get(key).isNull() &&!after.get(key).isNull()){

							ArrayNode beforeStudentArray = (ArrayNode) before.get(key);
							ObjectMapper removemapper = new ObjectMapper();
							JsonNode removeroot = removemapper.createObjectNode();
							ArrayNode removedStudents = removemapper.createArrayNode();
							List<ObjectNode> beforeStudentList = new ArrayList<ObjectNode>(); 
							for (int i = 0; i < beforeStudentArray.size(); i++) {
								beforeStudentList.add((ObjectNode)beforeStudentArray.get(i));
							} 

							ArrayNode afterStudentArray = (ArrayNode) after.get(key);
							ObjectMapper addmapper = new ObjectMapper();
							JsonNode addroot = addmapper.createObjectNode();
							ArrayNode addedStudents = addmapper.createArrayNode();
							List<ObjectNode> afterStudentList = new ArrayList<ObjectNode>();
							for (int i = 0; i < afterStudentArray.size(); i++) {
								afterStudentList.add((ObjectNode)afterStudentArray.get(i));
							}									   

							//Removed students
							for (ObjectNode beforeStudent : beforeStudentList) {
								boolean isItContains=true;
								for(ObjectNode afterStudent : afterStudentList){
									if(beforeStudent.get("StudentStateID").asText().equalsIgnoreCase(afterStudent.get("StudentStateID").asText())){
										isItContains=false;
									}
								}
								if(isItContains){
									removedStudents.add(beforeStudent);
								}
							}
							if(removedStudents!=null&& removedStudents.size()>0){
								
								((ObjectNode)removeroot).set(key, removedStudents);
								insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
										,"Roster "+key+" Removed",removemapper.writeValueAsString(removeroot),null);
							}
							//Added students
							for(ObjectNode afterStudent : afterStudentList){
								boolean isItContains=true;
								for (ObjectNode beforeStudent : beforeStudentList) {
									if(beforeStudent.get("StudentStateID").asText().equalsIgnoreCase(afterStudent.get("StudentStateID").asText())){
										isItContains=false;
									}
								}
								if(isItContains){
									addedStudents.add(afterStudent);
								}
							}

							if(addedStudents!=null&&addedStudents.size()>0){
								
								((ObjectNode)addroot).set(key, addedStudents);
								insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
										,"Roster "+key+" Added",null,addmapper.writeValueAsString(addroot));
							}
						}else if("Students".equals(key)&&!before.get(key).isNull()&&after.get(key).isNull()){
							
							insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
									,"Roster "+key+" Added",null,after.get(key).asText());
						}else if("Students".equals(key)&&!after.get(key).isNull()&& before.get(key).isNull()){
							
							insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
									,"Roster "+key+" Removed",before.get(key).asText(),null);
						}
						//students added or removed end
						if(!"Students".equals(key) && (!StringUtils.isEmpty(before.get(key)==null? null :before.get(key).asText())|| !StringUtils.isEmpty(after.get(key)==null? null:after.get(key).asText()) )&& 
								   !StringUtils.equalsIgnoreCase(before.get(key)==null? null :before.get(key).asText()
										   ,after.get(key)==null? null: after.get(key).asText())){
							if(!before.get(key).isArray()){
								  ((ObjectNode)beforeObjectRoot) .put(key,before.get(key));
								  ((ObjectNode)afterObjectRoot) .put(key, after.get(key));
								changed=true;
							}
						}
						
					}
				}	

				if(changed){
					insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
							,"Roster Changed",beforeObjectMapper.writeValueAsString(beforeObjectRoot),afterObjectMapper.writeValueAsString(afterObjectRoot));   					
				}
			}
			isProcessed= true;
		} catch (JsonProcessingException e) {
			logger.error("value inserted in rosteraudittrail table Failed for " + domainAuditHistory.getObjectId());
			isProcessed= false;
		} catch (IOException e) {
			logger.error("value inserted in rosteraudittrail table Failed for " + domainAuditHistory.getObjectId());
			isProcessed= false;
		}catch (Exception e) {
			logger.error("value inserted in rosteraudittrail table Failed for " + domainAuditHistory.getObjectId());
			isProcessed= false;
		}
		if (isProcessed){
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"COMPLETED");
		}
		else{
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"FAILED");
		}
		return isProcessed;
	}
	/*
	 * Udaya Kiran Jagana :Created for US18182 - For  roster audit story
	 * */
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToAuditTrailHistory(Long objectId,Long domainAuditHistoryId,Long modifiedUserId,String eventName,String beforeValue,String currentValue){
		Roster rosterAuditInformation=rosterDao.rosterInformationForAudit(objectId);
		if(rosterAuditInformation==null){
			rosterAuditInformation=new Roster();
		}
		String stateName=new String();
		Long stateId=null;
		String districtName=new String();
		Long districtId=null;
		String schoolName=new String();
		Long schoolId=null;
		List<Organization> parentList=organizationDao.getInactiveActiveParentOrgDetailsById(rosterAuditInformation.getAttendanceSchoolId());
		for(Organization org:parentList){
			if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_STATE_CODE)){
				stateName=org.getOrganizationName();
				stateId=org.getId();
			}
			else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_DISTRICT_CODE)){
				districtName=org.getOrganizationName();
				districtId=org.getId();
			}else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				schoolName=org.getOrganizationName();
				schoolId=org.getId();
			} 
		}
		User user=userService.get(modifiedUserId);
		if(user==null){
			user=new User();
		}
		RosterAuditTrailHistory rosterAuditTrailHistory = new RosterAuditTrailHistory();
		rosterAuditTrailHistory.setAffectedroster(objectId);
		rosterAuditTrailHistory.setCreatedDate(new Date());
		rosterAuditTrailHistory.setModifiedUser(modifiedUserId);
		rosterAuditTrailHistory.setEventName(eventName);
		rosterAuditTrailHistory.setBeforeValue(beforeValue);
		rosterAuditTrailHistory.setCurrentValue(currentValue);
		rosterAuditTrailHistory.setDomainAuditHistoryId(domainAuditHistoryId);
		rosterAuditTrailHistory.setRosterName(rosterAuditInformation.getCourseSectionName());
		rosterAuditTrailHistory.setEducatorName(rosterAuditInformation.getEducatorName());
		rosterAuditTrailHistory.setEducatorId(rosterAuditInformation.getEducatorId());
		rosterAuditTrailHistory.setEducatorInternalId(rosterAuditInformation.getTeacherId());
		rosterAuditTrailHistory.setSubject(rosterAuditInformation.getSubjectName());
		rosterAuditTrailHistory.setSubjectId(rosterAuditInformation.getStateSubjectAreaId());
		rosterAuditTrailHistory.setState(stateName);
		rosterAuditTrailHistory.setStateId(stateId);
		rosterAuditTrailHistory.setDistrict(districtName);
		rosterAuditTrailHistory.setDistrictId(districtId);
		rosterAuditTrailHistory.setSchool(schoolName);
		rosterAuditTrailHistory.setSchoolId(schoolId);
		rosterAuditTrailHistory.setModifiedUserName(user.getUserName());
		rosterAuditTrailHistory.setModifiedUserFirstName(user.getFirstName());
		rosterAuditTrailHistory.setModifiedUserLastName(user.getSurName());
		rosterAuditTrailHistory.setModifiedUserEducatorIdentifier(user.getUniqueCommonIdentifier());
		userAuditTrailHistoryMapperDao.insertRosterAuditTrail(rosterAuditTrailHistory);
		logger.trace("value inserted in organizationaudittrail table ");
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertIntoDomainAuditHistory(Long objectId, Long createdUserId,String action, String source,String rosterBeforUpdate,String rosterAfterUpdate){
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();

		domainAuditHistory.setSource(source);
		domainAuditHistory.setObjectType("ROSTER");
		domainAuditHistory.setObjectId(objectId);
		domainAuditHistory.setCreatedUserId( createdUserId.intValue() );
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(action);
		domainAuditHistory.setObjectBeforeValues(rosterBeforUpdate);
		domainAuditHistory.setObjectAfterValues(rosterAfterUpdate);
		domainAuditHistoryDao.insert(domainAuditHistory);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Roster> selectRosterByRosterAndAttendanceSchool(Roster roster, Long attendanceSchoolId) {
		return rosterDao.getRosterByRosterAndAttendanceSchool(roster.getCourseSectionName(), roster.getTeacherId(), 
				roster.getStateSubjectAreaId(), roster.getStateCourseCode(), roster.getCurrentSchoolYear(), roster.getAypSchoolId(), attendanceSchoolId);
	}


	@Override
	public List<Roster> getRostersByOrgId(Long sourceSchoolId, Long currentSchoolYear) {
		return rosterDao.getRostersByOrgId(sourceSchoolId,currentSchoolYear);
	}
	@Override
	public List<Roster> getRostersByEnrollmentId(Long enrollmentId){
		return rosterDao.getRostersByEnrollmentId(enrollmentId);

	}


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void transferRoster(Roster roster, Long destinationSchool) {
		roster.setAuditColumnPropertiesForUpdate();
		roster.setAttendanceSchoolId(destinationSchool);
		rosterDao.transferRoster(roster);
	}

	@Override
	public void disableRoster(Roster roster) {
		roster.setAuditColumnPropertiesForDelete();
		roster.setActiveFlag(false);
		rosterDao.disableRoster(roster);
	}
	
	@Override
	public Long getCountByOrganizationId(Long organizationId, Long schoolYear) {
		return rosterDao.getCountByOrganizationId(organizationId, schoolYear);
	}
	
	@Override
	public List<StudentRoster> checkIfRosterExistsForEnrollmentSubjectCourse(Long enrollmentId, Long subjectId, Long courseId, Long schoolYear, String assessmentProgramCode){
		return rosterDao.checkIfRosterExistsForEnrollmentSubjectCourse(enrollmentId, subjectId, courseId, schoolYear, assessmentProgramCode);
	}
	
	@Override
	public List<StudentRoster> checkForTestSessionsOnExitedRostersAlso(Long enrollmentId, Long subjectId, Long courseId, Long schoolYear, String assessmentProgramCode){
		return rosterDao.checkForTestSessionsOnExitedRostersAlso(enrollmentId, subjectId, courseId, schoolYear, assessmentProgramCode);
	}
	
	@Override
	public int checkIfRosterHasEnrollments(Long rosterId){
		return rosterDao.checkIfRosterHasEnrollments(rosterId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public HashMap<String, Boolean> automatedRosterTransferUpdatesToCreateRoster(Roster newRoster, Long[] enrollmentIds, List<StudentRoster> studentsAlreadyOnExistingRosters, Long modifiedUserId, String assessmentProgramCode){
		
		HashMap<String, Boolean> transferStatus = new HashMap<String, Boolean>();

		//Adding student to roster B
		int status = createRoster(newRoster, enrollmentIds);
		if(status == 1)
			transferStatus.put("status", true);
		else if(status == 0){
			transferStatus.put("duplicate", true);
			return transferStatus;
		}
		else{
			transferStatus.put("status", false);
			return transferStatus;
		}

		// The system should automatically remove the student from the old roster A 
		// and transfer complete, pending, unused, or in progress testlets to the new roster.
		if(studentsAlreadyOnExistingRosters.size()>0){
		
			for(StudentRoster enrollmentsRoster : studentsAlreadyOnExistingRosters){
				
				Long currentEnrollmentId = enrollmentsRoster.getStudentEnrlId(); 
				Long oldEnrollmentId = enrollmentsRoster.getEnrollmentId();
				Long oldRosterId = enrollmentsRoster.getRoster().getId();
				
    			List<StudentsTests> studentsTests = studentsTestsDao.getTestSessionsForDLDAutomationWithinActiveOTW(oldEnrollmentId, oldRosterId);
    			
				if(studentsTests != null && studentsTests.size() > 0) {
						Long inProgressStatusid = categoryDao.getCategoryId("inprogress", "STUDENT_TEST_STATUS");
						Long inProgressTimedOutStatusid = categoryDao.getCategoryId("inprogresstimedout", "STUDENT_TEST_STATUS");
						Long pendingStatusid = categoryDao.getCategoryId("pending", "STUDENT_TEST_STATUS");
						Long unusedStatusid = categoryDao.getCategoryId("unused", "STUDENT_TEST_STATUS");
						Long completeStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledCompleteStatusid = categoryDao.getCategoryId("exitclearunenrolled-complete", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledInProgressStatusid = categoryDao.getCategoryId("exitclearunenrolled-inprogress", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledInProgressTimedOutStatusid = categoryDao.getCategoryId("exitclearunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledPendingStatusid = categoryDao.getCategoryId("exitclearunenrolled-pending", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledUnusedStatusid = categoryDao.getCategoryId("exitclearunenrolled-unused", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledCompleteStatusid = categoryDao.getCategoryId("rosterunenrolled-complete", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledInProgressStatusid = categoryDao.getCategoryId("rosterunenrolled-inprogress", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledInProgressTimedOutStatusid = categoryDao.getCategoryId("rosterunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledPendingStatusid = categoryDao.getCategoryId("rosterunenrolled-pending", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledUnusedStatusid = categoryDao.getCategoryId("rosterunenrolled-unused", "STUDENT_TEST_STATUS");
						
						Map<Integer, Long> testSessionOrderMap = new HashMap<Integer, Long>();
						
						for(StudentsTests studentsTest: studentsTests) {
							
							Long oldTestSessionId = studentsTest.getTestSessionId();
							if (studentsTest.getTestSession().getSource() != null && 
									studentsTest.getTestSession().getSource().equals("BATCHAUTO")) {
								testSessionOrderMap.put(studentsTest.getCurrentTestNumber(), oldTestSessionId);
							}
							
							//Verify if test is in progress or unused
							if(studentsTest != null 
								&& (studentsTest.getStatus().equals(inProgressStatusid) 
										|| studentsTest.getStatus().equals(inProgressTimedOutStatusid)
										|| studentsTest.getStatus().equals(unusedStatusid)
										|| studentsTest.getStatus().equals(pendingStatusid)
										|| studentsTest.getStatus().equals(completeStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledCompleteStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressTimedOutStatusid)	
										|| studentsTest.getStatus().equals(exitClearUnenrolledPendingStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledUnusedStatusid)
										|| studentsTest.getStatus().equals(rosterUnenrolledCompleteStatusid)
										|| studentsTest.getStatus().equals(rosterUnenrolledInProgressStatusid)	
										|| studentsTest.getStatus().equals(rosterUnenrolledInProgressTimedOutStatusid)
										|| studentsTest.getStatus().equals(rosterUnenrolledPendingStatusid)
										|| studentsTest.getStatus().equals(rosterUnenrolledUnusedStatusid))) 
							{
														
								    //Transfer test sessions (summative and field testlets in pending,unused, inprogress, completed status)
									testSessionService.transferTestSessionToNewRoster(oldTestSessionId, newRoster.getId(), modifiedUserId, currentEnrollmentId);
	
									if(studentsTest.getStatus().equals(exitClearUnenrolledCompleteStatusid)
											|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressStatusid)		
											|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressTimedOutStatusid)	
											|| studentsTest.getStatus().equals(exitClearUnenrolledPendingStatusid)
											|| studentsTest.getStatus().equals(exitClearUnenrolledUnusedStatusid)
											|| studentsTest.getStatus().equals(rosterUnenrolledCompleteStatusid)
											|| studentsTest.getStatus().equals(rosterUnenrolledInProgressStatusid)	
											|| studentsTest.getStatus().equals(rosterUnenrolledInProgressTimedOutStatusid)
											|| studentsTest.getStatus().equals(rosterUnenrolledPendingStatusid)
											|| studentsTest.getStatus().equals(rosterUnenrolledUnusedStatusid))
									{									
									
										studentsTestsDao.reactivateByPrimaryKeyForRosterChange(studentsTest.getId(), modifiedUserId, currentEnrollmentId);
										List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(studentsTest.getId()));
										for (Long studentsTestSectionsId : studentsTestSectionIds) {
											studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
										}
									
									} else if (studentsTest.getStatus().equals(inProgressStatusid) 
											|| studentsTest.getStatus().equals(inProgressTimedOutStatusid)
											|| studentsTest.getStatus().equals(unusedStatusid)
											|| studentsTest.getStatus().equals(pendingStatusid)
											|| studentsTest.getStatus().equals(completeStatusid)) 
									{
										StudentsTests st = new StudentsTests();
										st.setEnrollmentId(currentEnrollmentId);
										st.setId(studentsTest.getId());
										st.setModifiedUser(modifiedUserId);
										st.setActiveFlag(true);
										studentsTestsDao.updateByPrimaryKeySelective(st);
									}
									LOGGER.debug("The testsessions of student "+ enrollmentsRoster.getStudentId()+" with enrollment " + currentEnrollmentId+" transferred successfully from old roster "+oldRosterId+" to new roster "+newRoster.getId());
							}
						}
						/**
						Map<Integer, Long> studentTrackerBandOrderMap = new HashMap<>();
						
						List<Long> bandIds = studentTrackerBandMapper.getBandsForStudentAndContentArea(enrollmentsRoster.getStudentId(), newRoster.getStateSubjectAreaId());
						int count = 0;
						for (Long bandId : bandIds) {
							count++;
							studentTrackerBandOrderMap.put(count, bandId);
						}
						
						 if (studentTrackerBandOrderMap.size() ==  testSessionOrderMap.size()){
							for (int i = 1; i <= testSessionOrderMap.size(); i++) {
								long bandId = studentTrackerBandOrderMap.get(i);
								long testSessionId = testSessionOrderMap.get(i);
								StudentTrackerBand band = new StudentTrackerBand();
								band.setId(bandId);
								band.setTestSessionId(testSessionId);
								band.setModifiedUser(modifiedUserId);
								studentTrackerBandMapper.updateByPrimaryKeySelective(band);
							}
						} else {
	    					LOGGER.debug("The number of student tracker bands of student "+ enrollmentsRoster.getStudentId()+" did not match the number of test sessions.");
	    				} **/
						
				} else {
					LOGGER.debug("The testsessions of student "+ enrollmentsRoster.getStudentId()+" with enrollment " + currentEnrollmentId+" not transferred from old roster "+oldRosterId+" to new roster "+newRoster.getId());
				}
				
				if("DLM".equalsIgnoreCase(assessmentProgramCode)){
					//Move saved ITI plan to the new roster ==> Inactive the pending plan on the old roster and insert the same iti record with the new roster id
					List<ItiTestSessionHistory> pendingITIPlansOfTheOldRoster =  itiTestSessionService.getPendingITIsByEnrlAndRosterId(oldEnrollmentId, oldRosterId);
					if(pendingITIPlansOfTheOldRoster!=null && pendingITIPlansOfTheOldRoster.size()>0){
						itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(pendingITIPlansOfTheOldRoster, currentEnrollmentId, newRoster.getId(), modifiedUserId);
						LOGGER.debug("The pending ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
					}
					
					//Move unused, inprogress, completed ITI plans to the new roster from roster. Do not inactivate but transfer based on old rosterid and enrollmentid. 
					List<ItiTestSessionHistory> itiPlansExceptPending =  itiTestSessionService.getITIPlansExceptPendingUsingEnrlAndRosterId(oldEnrollmentId, oldRosterId);

					if(itiPlansExceptPending!=null && itiPlansExceptPending.size()>0){
						itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(itiPlansExceptPending, currentEnrollmentId, newRoster.getId(), modifiedUserId);
						Long completeStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
						for(ItiTestSessionHistory itiPlansOfTheOldRoster : itiPlansExceptPending){
							//Transfer test sessions (ITI testlets in unused, inprogress, completed status)
							testSessionService.transferTestSessionToNewRoster(itiPlansOfTheOldRoster.getTestSessionId(), newRoster.getId(), modifiedUserId, currentEnrollmentId);
							
				        	StudentsTestsExample example = new StudentsTestsExample();
				            StudentsTestsExample.Criteria criteria = example.createCriteria();
				            criteria.andStudentIdEqualTo(itiPlansOfTheOldRoster.getStudentId());
				            criteria.andTestSessionIdEqualTo(itiPlansOfTheOldRoster.getTestSessionId());
				            if (itiPlansOfTheOldRoster.getStudentsTestStatusId() != null && itiPlansOfTheOldRoster.getStudentsTestStatusId().equals(completeStatusid)){
				            	criteria.andIsActive();
				            } else {
				            	criteria.andIsInactive();
				            }
							List<StudentsTests> itiStudentsTests = studentsTestsDao.selectByExample(example);
							
							for (StudentsTests st : itiStudentsTests) {
								studentsTestsDao.reactivateByPrimaryKeyForRosterChange(st.getId(), modifiedUserId, currentEnrollmentId);
								List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(st.getId()));
								for (Long studentsTestSectionsId : studentsTestSectionIds) {
									studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
								}
							}
						}
						
						LOGGER.debug("The unused, inprogress and complete ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
					}
					
					List<ItiTestSessionHistory> exitedITIPlansOfTheOldRoster =  itiTestSessionService.getUnenrolledITIsByEnrlAndRosterId(oldEnrollmentId, oldRosterId);
					
					if(exitedITIPlansOfTheOldRoster!=null && exitedITIPlansOfTheOldRoster.size()>0){
						itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(exitedITIPlansOfTheOldRoster, currentEnrollmentId, newRoster.getId(), modifiedUserId);
						Long rosterUnenrolledPendingStatusid = categoryDao.getCategoryId("rosterunenrolled-pending", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledPendingStatusid = categoryDao.getCategoryId("exitclearunenrolled-pending", "STUDENT_TEST_STATUS");
						for(ItiTestSessionHistory itiPlansOfTheOldRoster : exitedITIPlansOfTheOldRoster){
							if (!itiPlansOfTheOldRoster.getStatus().equals(exitClearUnenrolledPendingStatusid) &
									!itiPlansOfTheOldRoster.getStatus().equals(rosterUnenrolledPendingStatusid)) {
								//Transfer test sessions (ITI testlets in unused, inprogress, completed status)
								testSessionService.transferTestSessionToNewRoster(itiPlansOfTheOldRoster.getTestSessionId(), newRoster.getId(), modifiedUserId, currentEnrollmentId);
					        	StudentsTestsExample example = new StudentsTestsExample();
					            StudentsTestsExample.Criteria criteria = example.createCriteria();
					            criteria.andStudentIdEqualTo(itiPlansOfTheOldRoster.getStudentId());
					            criteria.andTestSessionIdEqualTo(itiPlansOfTheOldRoster.getTestSessionId());
					            criteria.andIsInactive();
								List<StudentsTests> itiStudentsTests = studentsTestsDao.selectByExample(example);
								
								for (StudentsTests st : itiStudentsTests) {
									studentsTestsDao.reactivateByPrimaryKeyForRosterChange(st.getId(), modifiedUserId, currentEnrollmentId);
									List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(st.getId()));
									for (Long studentsTestSectionsId : studentsTestSectionIds) {
										studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
									}
								}
							}
						}
						
						LOGGER.debug("The exited, transferred, and unrostered ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
					}
				}				
				
				//Inactivate EnrollmentRosters on Old Roster
    			EnrollmentsRosters oldEnrlRoster = enrollmentsRostersDao.getEnrollmentInfoByRosterIdEnrollmentId(oldRosterId, oldEnrollmentId);
    			if(oldEnrlRoster!=null && Boolean.TRUE.equals(oldEnrlRoster.getActiveFlag())){
	    			oldEnrlRoster.setActiveFlag(Boolean.FALSE);
	    			oldEnrlRoster.setModifiedUser(modifiedUserId);
	    			oldEnrlRoster.setModifiedDate(new Date());
    			
    			
	    			EnrollmentsRostersExample example = new EnrollmentsRostersExample();
	    			EnrollmentsRostersExample.Criteria criteria = example.createCriteria();
	    			criteria.andEnrollmentIdEqualTo(oldEnrollmentId);
	    			criteria.andRosterIdEqualTo(oldRosterId);
	    			
	    			enrollmentsRostersDao.updateByExampleSelective(oldEnrlRoster, example);
	    			logger.debug("Inactivated enrollmentroster: "+oldEnrollmentId+" "+oldRosterId);
    			}

				//Before returning, we must now check if there are any students rostered in the roster after the update. For DLM only 
				//If none present, inactivate the roster as well. This roster should no longer be shown in the View Roster grid.
				Roster oldRoster = rosterDao.selectByPrimaryKey(oldRosterId);
				if(Boolean.TRUE.equals(oldRoster.getActiveFlag()) && checkIfRosterHasEnrollments(oldRosterId)<=0){
					disableRoster(oldRoster);
				}
    		}
			transferStatus.put("automatedRosterTransferComplete", true);
		}
		else
			transferStatus.put("automatedRosterTransferComplete", true);
		
		//Before returning, we must now check if there are any students rostered in the roster after the update. For DLM only 
		//If none present, inactivate the roster as well. This roster should no longer be shown in the View Roster grid.
		if(checkIfRosterHasEnrollments(newRoster.getId())<=0){
			disableRoster(newRoster);
		}
		
		return transferStatus;
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public HashMap<String, Boolean> automatedRosterTransferUpdatesToEditRoster(Roster newRoster, Long[] addEnrollmentIds, Long[] delEnrollmentIds, List<StudentRoster> studentsAlreadyOnExistingRosters, Long modifiedUserId, String assessmentProgramCode){
		
		HashMap<String, Boolean> transferStatus = new HashMap<String, Boolean>();

		int status = updateRosterWithEnrollments(newRoster, addEnrollmentIds, delEnrollmentIds);
		
		if(status == 1)
			transferStatus.put("status", true);
		else if(status == -1){
			transferStatus.put("duplicate", true);
			return transferStatus;
		}
		else{
			transferStatus.put("status", false);
			return transferStatus;
		}

		// The system should automatically remove the student from the old roster A 
		// and transfer complete, pending, unused, or in progress testlets to the new roster.
		if(studentsAlreadyOnExistingRosters.size()>0){
		
			for(StudentRoster enrollmentsRoster : studentsAlreadyOnExistingRosters){
				
				Long currentEnrollmentId = enrollmentsRoster.getStudentEnrlId(); 
				Long oldEnrollmentId = enrollmentsRoster.getEnrollmentId();
				Long oldRosterId = enrollmentsRoster.getRoster().getId();
				
    			List<StudentsTests> studentsTests = studentsTestsDao.getTestSessionsForDLDAutomationWithinActiveOTW(oldEnrollmentId, oldRosterId);
    			//Commented out for DE16037 - this code was preventing a student from getting tests back when they are moved 
    			//back to the same roster
    			//if(!oldRosterId.equals(newRoster.getId())) {
    				if(studentsTests != null && studentsTests.size() > 0) {
						Long inProgressStatusid = categoryDao.getCategoryId("inprogress", "STUDENT_TEST_STATUS");
						Long inProgressTimedOutStatusid = categoryDao.getCategoryId("inprogresstimedout", "STUDENT_TEST_STATUS");
						Long pendingStatusid = categoryDao.getCategoryId("pending", "STUDENT_TEST_STATUS");
						Long unusedStatusid = categoryDao.getCategoryId("unused", "STUDENT_TEST_STATUS");
						Long completeStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledCompleteStatusid = categoryDao.getCategoryId("exitclearunenrolled-complete", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledInProgressStatusid = categoryDao.getCategoryId("exitclearunenrolled-inprogress", "STUDENT_TEST_STATUS");	
						Long exitClearUnenrolledInProgressTimedOutStatusid = categoryDao.getCategoryId("exitclearunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledPendingStatusid = categoryDao.getCategoryId("exitclearunenrolled-pending", "STUDENT_TEST_STATUS");
						Long exitClearUnenrolledUnusedStatusid = categoryDao.getCategoryId("exitclearunenrolled-unused", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledCompleteStatusid = categoryDao.getCategoryId("rosterunenrolled-complete", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledInProgressStatusid = categoryDao.getCategoryId("rosterunenrolled-inprogress", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledInProgressTimedOutStatusid = categoryDao.getCategoryId("rosterunenrolled-inprogresstimedout", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledPendingStatusid = categoryDao.getCategoryId("rosterunenrolled-pending", "STUDENT_TEST_STATUS");
						Long rosterUnenrolledUnusedStatusid = categoryDao.getCategoryId("rosterunenrolled-unused", "STUDENT_TEST_STATUS");
						
						Map<Integer, Long> testSessionOrderMap = new HashMap<Integer, Long>();
						
						for(StudentsTests studentsTest: studentsTests) {
							
							Long oldTestSessionId = studentsTest.getTestSessionId();
							if (studentsTest.getTestSession().getSource() != null && 
									studentsTest.getTestSession().getSource().equals("BATCHAUTO")) {
								testSessionOrderMap.put(studentsTest.getCurrentTestNumber(), oldTestSessionId);
							}
								
							//Verify if test is in progress or unused
							if(studentsTest != null 
								&& (studentsTest.getStatus().equals(inProgressStatusid)
										|| studentsTest.getStatus().equals(inProgressTimedOutStatusid)
										|| studentsTest.getStatus().equals(unusedStatusid)
										|| studentsTest.getStatus().equals(pendingStatusid)
										|| studentsTest.getStatus().equals(completeStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledCompleteStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressStatusid)		
										|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressTimedOutStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledPendingStatusid)
										|| studentsTest.getStatus().equals(exitClearUnenrolledUnusedStatusid)
										|| studentsTest.getStatus().equals(rosterUnenrolledCompleteStatusid)
										|| studentsTest.getStatus().equals(rosterUnenrolledInProgressStatusid)		
										|| studentsTest.getStatus().equals(rosterUnenrolledInProgressTimedOutStatusid)												
										|| studentsTest.getStatus().equals(rosterUnenrolledPendingStatusid)
										|| studentsTest.getStatus().equals(rosterUnenrolledUnusedStatusid))) 
							{
					
								
								    //Transfer test sessions (summative and field testlets in pending,unused, inprogress, completed status)
									testSessionService.transferTestSessionToNewRoster(oldTestSessionId, newRoster.getId(), modifiedUserId, currentEnrollmentId);
									
									if(studentsTest.getStatus().equals(exitClearUnenrolledCompleteStatusid)
											|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressStatusid)
											|| studentsTest.getStatus().equals(exitClearUnenrolledInProgressTimedOutStatusid)											
											|| studentsTest.getStatus().equals(exitClearUnenrolledPendingStatusid)
											|| studentsTest.getStatus().equals(exitClearUnenrolledUnusedStatusid)
											|| studentsTest.getStatus().equals(rosterUnenrolledCompleteStatusid)
											|| studentsTest.getStatus().equals(rosterUnenrolledInProgressStatusid)	
											|| studentsTest.getStatus().equals(rosterUnenrolledInProgressTimedOutStatusid)											
											|| studentsTest.getStatus().equals(rosterUnenrolledPendingStatusid)
											|| studentsTest.getStatus().equals(rosterUnenrolledUnusedStatusid))
									{
	
										studentsTestsDao.reactivateByPrimaryKeyForRosterChange(studentsTest.getId(), modifiedUserId, currentEnrollmentId);
										List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(studentsTest.getId()));
										for (Long studentsTestSectionsId : studentsTestSectionIds) {
											studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
										}
									} else if (studentsTest.getStatus().equals(inProgressStatusid) 
											|| studentsTest.getStatus().equals(inProgressTimedOutStatusid)
											|| studentsTest.getStatus().equals(unusedStatusid)
											|| studentsTest.getStatus().equals(pendingStatusid)
											|| studentsTest.getStatus().equals(completeStatusid)) 
									{
										StudentsTests st = new StudentsTests();
										st.setEnrollmentId(currentEnrollmentId);
										st.setId(studentsTest.getId());
										st.setModifiedUser(modifiedUserId);
										st.setActiveFlag(true);
										studentsTestsDao.updateByPrimaryKeySelective(st);
									}
									LOGGER.debug("The testsessions of student "+ enrollmentsRoster.getStudentId()+" with enrollment " + currentEnrollmentId+" transferred successfully from old roster "+oldRosterId+" to new roster "+newRoster.getId());
							}
						}
						
						/**
						Map<Integer, Long> studentTrackerBandOrderMap = new HashMap<>();
						
						List<Long> bandIds = studentTrackerBandMapper.getBandsForStudentAndContentArea(enrollmentsRoster.getStudentId(), newRoster.getStateSubjectAreaId());
						int count = 0;
						for (Long bandId : bandIds) {
							count++;
							studentTrackerBandOrderMap.put(count, bandId);
						}
						
						 if (studentTrackerBandOrderMap.size() ==  testSessionOrderMap.size()){
							for (int i = 1; i <= testSessionOrderMap.size(); i++) {
								long bandId = studentTrackerBandOrderMap.get(i);
								long testSessionId = testSessionOrderMap.get(i);
								StudentTrackerBand band = new StudentTrackerBand();
								band.setId(bandId);
								band.setTestSessionId(testSessionId);
								band.setModifiedUser(modifiedUserId);
								studentTrackerBandMapper.updateByPrimaryKeySelective(band);
							}
						} else {
	    					LOGGER.debug("The number of student tracker bands of student "+ enrollmentsRoster.getStudentId()+" did not match the number of test sessions.");
	    				}**/
    				} else {
    					LOGGER.debug("The testsessions of student "+ enrollmentsRoster.getStudentId()+" with enrollment " + currentEnrollmentId+" not transferred from old roster "+oldRosterId+" to new roster "+newRoster.getId());
    				}

    				if("DLM".equalsIgnoreCase(assessmentProgramCode)){
    					//Move saved ITI plan to the new roster ==> Inactive the pending plan on the old roster and insert the same iti record with the new roster id
        				List<ItiTestSessionHistory> pendingITIPlansOfTheOldRoster =  itiTestSessionService.getPendingITIsByEnrlAndRosterId(oldEnrollmentId, oldRosterId);
        				if(pendingITIPlansOfTheOldRoster!=null && pendingITIPlansOfTheOldRoster.size()>0){
        					itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(pendingITIPlansOfTheOldRoster, currentEnrollmentId, newRoster.getId(), modifiedUserId);
        					LOGGER.debug("The pending ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
        				}
    				
        				//Move exited, unused, inprogress, completed ITI plans to the new roster from roster. Do not inactivate but transfer based on old rosterid and enrollmentid. 
        				List<ItiTestSessionHistory> itiPlansExceptPending =  itiTestSessionService.getITIPlansExceptPendingUsingEnrlAndRosterId(oldEnrollmentId, oldRosterId);

        				if(itiPlansExceptPending!=null && itiPlansExceptPending.size()>0){
        					itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(itiPlansExceptPending, currentEnrollmentId, newRoster.getId(), modifiedUserId);
        					Long completeStatusid = categoryDao.getCategoryId("complete", "STUDENT_TEST_STATUS");
        					for(ItiTestSessionHistory itiPlansOfTheOldRoster : itiPlansExceptPending){
        						//Transfer test sessions (ITI testlets in unused, inprogress, completed status)
        						testSessionService.transferTestSessionToNewRoster(itiPlansOfTheOldRoster.getTestSessionId(), newRoster.getId(), modifiedUserId, currentEnrollmentId);

        						StudentsTestsExample example = new StudentsTestsExample();
        						StudentsTestsExample.Criteria criteria = example.createCriteria();
        						criteria.andStudentIdEqualTo(itiPlansOfTheOldRoster.getStudentId());
        						criteria.andTestSessionIdEqualTo(itiPlansOfTheOldRoster.getTestSessionId());
        						if(itiPlansOfTheOldRoster.getStudentsTestStatusId() != null && itiPlansOfTheOldRoster.getStudentsTestStatusId().equals(completeStatusid)){
        							criteria.andIsActive();
        						} else {
        							criteria.andIsInactive();
        						}
        						List<StudentsTests> itiStudentsTests = studentsTestsDao.selectByExample(example);
    						
        						for (StudentsTests st : itiStudentsTests) {
        							studentsTestsDao.reactivateByPrimaryKeyForRosterChange(st.getId(), modifiedUserId, currentEnrollmentId);
        							List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(st.getId()));
        							for (Long studentsTestSectionsId : studentsTestSectionIds) {
        								studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
        							}
        						}
        					}
    					
        					LOGGER.debug("The unused, inprogress and complete ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
        				}
    				
        				List<ItiTestSessionHistory> exitedITIPlansOfTheOldRoster =  itiTestSessionService.getUnenrolledITIsByEnrlAndRosterId(oldEnrollmentId, oldRosterId);
    				
    				
        				if(exitedITIPlansOfTheOldRoster!=null && exitedITIPlansOfTheOldRoster.size()>0){
        					itiTestSessionService.transferITIsToNewRosterByEnrlAndOldRosterId(exitedITIPlansOfTheOldRoster, currentEnrollmentId, newRoster.getId(), modifiedUserId);
        					Long exitClearUnenrolledPendingStatusid = categoryDao.getCategoryId("exitclearunenrolled-STARTED", CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
        					Long rosterUnenrolledPendingStatusid = categoryDao.getCategoryId("rosterunenrolled-STARTED", CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
        					List<Long> startedStatuses = Arrays.asList(exitClearUnenrolledPendingStatusid, rosterUnenrolledPendingStatusid);
        					for(ItiTestSessionHistory itiPlansOfTheOldRoster : exitedITIPlansOfTheOldRoster){
        						if (!startedStatuses.contains(itiPlansOfTheOldRoster.getStatus())) {
        							//Transfer test sessions (ITI testlets in unused, inprogress, completed status)
        							testSessionService.transferTestSessionToNewRoster(itiPlansOfTheOldRoster.getTestSessionId(), newRoster.getId(), modifiedUserId, currentEnrollmentId);
    	
        							StudentsTestsExample example = new StudentsTestsExample();
        							StudentsTestsExample.Criteria criteria = example.createCriteria();
        							criteria.andStudentIdEqualTo(itiPlansOfTheOldRoster.getStudentId());
        							criteria.andTestSessionIdEqualTo(itiPlansOfTheOldRoster.getTestSessionId());
        							criteria.andIsInactive();
        							List<StudentsTests> itiStudentsTests = studentsTestsDao.selectByExample(example);
    							
        							for (StudentsTests st : itiStudentsTests) {
        								studentsTestsDao.reactivateByPrimaryKeyForRosterChange(st.getId(), modifiedUserId, currentEnrollmentId);
        								List<Long> studentsTestSectionIds = studentsTestSectionsDao.findIdsByStudentsTests(Collections.singletonList(st.getId()));
        								for (Long studentsTestSectionsId : studentsTestSectionIds) {
        									studentsTestSectionsDao.reactivateByPrimaryKeyForRosterChange(studentsTestSectionsId, modifiedUserId);
        								}
        							}
        						}
        					}
    					
        					LOGGER.debug("The exited, transfered, and unrostered ITI plans of student "+ enrollmentsRoster.getStudentId()+" transferred successfully from enrollment: "+oldEnrollmentId+" and old roster "+oldRosterId+" to current enrollment: "+currentEnrollmentId+" new roster "+newRoster.getId());
        				}
    				}
    				

    				//Inactivate EnrollmentRosters on Old Roster
    				EnrollmentsRosters oldEnrlRoster = enrollmentsRostersDao.getEnrollmentInfoByRosterIdEnrollmentId(oldRosterId, oldEnrollmentId);
    				if(oldEnrlRoster!=null && Boolean.TRUE.equals(oldEnrlRoster.getActiveFlag()) && !oldRosterId.equals(newRoster.getId())){
    					oldEnrlRoster.setActiveFlag(Boolean.FALSE);
    					oldEnrlRoster.setModifiedUser(modifiedUserId);
    					oldEnrlRoster.setModifiedDate(new Date());
    			
    					EnrollmentsRostersExample example = new EnrollmentsRostersExample();
    					EnrollmentsRostersExample.Criteria criteria = example.createCriteria();
    					criteria.andEnrollmentIdEqualTo(oldEnrollmentId);
    					criteria.andRosterIdEqualTo(oldRosterId);
	    			
    					enrollmentsRostersDao.updateByExampleSelective(oldEnrlRoster, example);
    					logger.debug("Inactivated enrollmentroster: "+oldEnrollmentId+" "+oldRosterId);
    				}
				
    				//Before returning, we must now check if there are any students rostered in the roster after the update. For DLM only 
    				//If none present, inactivate the roster as well. This roster should no longer be shown in the View Roster grid.
    				Roster oldRoster = rosterDao.selectByPrimaryKey(oldRosterId);
    				if(Boolean.TRUE.equals(oldRoster.getActiveFlag()) && checkIfRosterHasEnrollments(oldRosterId)<=0){
    					disableRoster(oldRoster);
    					transferStatus.put("disabledRoster", true);
    				}
    			//}
    		}
			transferStatus.put("automatedRosterTransferComplete", true);
		}
		else{
			
			transferStatus.put("automatedRosterTransferComplete", true);
		}
		
		//Before returning, we must now check if there are any students rostered in the roster after the update. For DLM only 
		//If none present, inactivate the roster as well. This roster should no longer be shown in the View Roster grid.
		if(checkIfRosterHasEnrollments(newRoster.getId())<=0){
			disableRoster(newRoster);
			transferStatus.put("disabledRoster", true);
		}
		
		return transferStatus;
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int updateRosterWithEnrollments(Roster record, Long[] addEnrollmentIds, Long[] delEnrollmentIds) {
		int status = 0;
		// Update Roster
		List<Roster> allExistingRosters = getAllRostersToCheckForUpdateDuplicates(record, record.getAttendanceSchoolId());
		if(!CollectionUtils.isEmpty(allExistingRosters)) 
			return -1;
	
		Roster upRoster = rosterDao.selectByPrimaryKey(record.getId());
		//Roster existingRoster = getExistingRosterDetails(upRoster);
		upRoster.setCourseSectionName(record.getCourseSectionName());
		upRoster.setAuditColumnPropertiesForUpdate();
		upRoster.setTeacherId(record.getTeacherId());
		upRoster.setActiveFlag(true);
		upRoster.setStateSubjectAreaId(record.getStateSubjectAreaId());
		upRoster.setStateCoursesId(record.getStateCoursesId());
		GradeCourse gradeCourse = gradeCourseService.selectByPrimaryKey(record.getStateCoursesId());
		if(gradeCourse != null && StringUtils.isNotEmpty(gradeCourse.getAbbreviatedName())) {
			upRoster.setStateCourseCode(gradeCourse.getAbbreviatedName());
		} else {
			upRoster.setStateCourseCode(StringUtils.EMPTY);
		}
		Roster beforeUpdate=rosterDao.getRosterJsonFormatData(record.getId());
		status = rosterDao.updateByPrimaryKey(upRoster);

		upRoster.setSourceType("MANUAL_ROSTER_EDIT");
		/*if(((existingRoster.getStateSubjectAreaId() != null && !existingRoster.getStateSubjectAreaId().equals(record.getStateSubjectAreaId()))
				|| (existingRoster.getStateCoursesId() != null && !existingRoster.getStateCoursesId().equals(record.getStateCoursesId()))
				|| (record.getStateCoursesId() != null && !record.getStateCoursesId().equals(existingRoster.getStateCoursesId())))
				&& !StringUtils.equalsIgnoreCase(existingRoster.getStateCourseCode(), upRoster.getStateCourseCode())) {
			addRosterSubOrGradeChangeEventToDomainAuditHistory(upRoster, existingRoster);
		}*/
		/*if((existingRoster.getTeacherId() != null && record.getTeacherId() != null) 
				&& !existingRoster.getTeacherId().equals(record.getTeacherId())) {	
			addRosterTeacherChangeEventToDomainAuditHistory(upRoster, existingRoster.getTeacherId());
		}*/
		
		// Add Enrollments
		for (Long eId : addEnrollmentIds) {
			EnrollmentsRosters er = new EnrollmentsRosters();
			er.setRosterId(record.getId());
			er.setEnrollmentId(eId);
			er.setAuditColumnProperties();
			er.setModifiedUser(record.getModifiedUser());
			er.setActiveFlag(true);
			EnrollmentsRostersExample enrollmentsRostersExample = new EnrollmentsRostersExample();
			EnrollmentsRostersExample.Criteria enrollmentsRostersCriteria = enrollmentsRostersExample.createCriteria();
			enrollmentsRostersCriteria.andEnrollmentIdEqualTo(er.getEnrollmentId());
			enrollmentsRostersCriteria.andRosterIdEqualTo(er.getRosterId());
			int updates = enrollmentsRostersDao.updateByExample(er, enrollmentsRostersExample);
			if (updates < 1) {
				status = enrollmentsRostersDao.insert(er);
			}
			if(updates > 0 || status > 0) {
				addStudentToRosterEventToDomainAuditHistory(er, upRoster);
			}
		}
		
		//Remove Students from Roster
		for (Long eId : delEnrollmentIds) {
			EnrollmentsRosters er = new EnrollmentsRosters();
			er.setRosterId(record.getId());
			er.setEnrollmentId(eId);
			er.setAuditColumnProperties();
			er.setModifiedUser(record.getModifiedUser());
			er.setActiveFlag(false);
			EnrollmentsRostersExample enrollmentsRostersExample = new EnrollmentsRostersExample();
			EnrollmentsRostersExample.Criteria enrollmentsRostersCriteria = enrollmentsRostersExample
					.createCriteria();
			enrollmentsRostersCriteria.andEnrollmentIdEqualTo(er
					.getEnrollmentId());
			enrollmentsRostersCriteria.andRosterIdEqualTo(er.getRosterId());
			status = enrollmentsRostersDao.updateByExampleSelective(er,
					enrollmentsRostersExample);
			if(status > 0) {
				addRmStuFromRostEventToDomainAduidtHistory(er, upRoster);
			}
			boolean isStudentDLM = (studentDao.checkIfDLMByEnrollmentId(eId) > 0) ? true : false;
			if(isStudentDLM){	
				studentsTestsService.rosterUnEnrollStudent(eId, record.getId(), record.getModifiedUser());				
			}
		}
		
		
		if(status==1){
			Roster afterUpdate = rosterDao.getRosterJsonFormatData(record.getId());
			String rosterBeforeUpdateString=null;
			String	rosterAfterUpdateString=null;
			if(beforeUpdate != null){
				beforeUpdate.buildJsonString();
				rosterBeforeUpdateString=beforeUpdate.getRosterJsonStr();
			}
			if(afterUpdate != null){
				afterUpdate.buildJsonString();
				rosterAfterUpdateString=afterUpdate.getRosterJsonStr();
			}
			insertIntoDomainAuditHistory(afterUpdate.getId(),afterUpdate.getModifiedUser(),EventTypeEnum.UPDATE.getCode(),SourceTypeEnum.MANUAL.getCode(),rosterBeforeUpdateString,rosterAfterUpdateString);
		}
		return status;
	}

	@Override
	public Long getDeactivateCountByOrganizationId(Long organizationId, Long schoolYear) {
		
		return rosterDao.getDeactivateCountByOrganizationId(organizationId, schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void removeRoster(long rosterId, Long userId, String sourceType) {
		Date now = new Date();
		
		Roster beforeUpdate = null;
		if (sourceType != null) {
			beforeUpdate = rosterDao.getRosterJsonFormatData(rosterId);
		}
		
		EnrollmentsRostersExample erEx = new EnrollmentsRostersExample();
		EnrollmentsRostersExample.Criteria c = erEx.createCriteria();
		c.andRosterIdEqualTo(rosterId);
		c.andActiveFlagEqualTo(true);
		
		EnrollmentsRosters er = new EnrollmentsRosters();
		er.setRosterId(rosterId);
		er.setActiveFlag(false);
		er.setModifiedDate(now);
		er.setModifiedUser(userId);
		
		logger.debug("Deactivating enrollmentsrosters entries for rosterid " + rosterId);
		enrollmentsRostersDao.updateByExampleSelective(er, erEx);
		
		// deactivate the roster
		Roster roster = new Roster();
		roster.setId(rosterId);
		roster.setActiveFlag(false);
		roster.setModifiedDate(now);
		roster.setModifiedUser(userId);
		logger.debug("Deactivating roster entry for id " + rosterId);
		rosterDao.updateByPrimaryKeySelective(roster);
		
		Roster afterUpdate = null;
		if (sourceType != null) {
			afterUpdate = rosterDao.getRosterJsonFormatData(rosterId);
		}
		
		if (sourceType != null) {
			insertIntoDomainAuditHistory(rosterId, userId, EventTypeEnum.UPDATE.getCode(), sourceType,
				beforeUpdate == null ? null : beforeUpdate.buildJsonString(),
				afterUpdate == null ? null : afterUpdate.buildJsonString());
		}
	}

	@Override
	public List<Long> removeRostersByEnrollmentId(Long enrollmentId, Long modifiedUserId) {
		return enrollmentsRostersDao.removeRostersByEnrollmentId(enrollmentId, modifiedUserId);
	}
	
	@Override
	public Long removeEnrollmentsRostersByRosterId(Long rosterId, Long modifiedUserId) {
		return (long) enrollmentsRostersDao.removeEnrollmentsRostersByRosterId(rosterId, modifiedUserId);
	}
	
	@Override
	public int removeStudentsTestSectionsByRosterId(Long rosterId, Long modifiedUserId) { 
		return studentDao.updateStudentsTestSectionsByRosterId(rosterId, modifiedUserId);
	}


	@Override
	public void deleteIfNoStudentPresent(Long rosterId, Long modifiedUserId) {
		rosterDao.deleteIfNoStudentPresent(rosterId, modifiedUserId);		
	}
	
	public List<Long> getRosterIdByTeacherIdTestSessionId(Long teacherId, Long testSessionId) {
		return rosterDao.getRosterIdByTeacherIdTestSessionId(teacherId, testSessionId);
	}

	@Override
	public List<Roster> getRosterByClassroomId(Long classroomId) {
		return rosterDao.getByClassroomId(classroomId);
	}
	
	@Override
	public boolean checkActiveStudentsCountOnRoster(Long classroomId,int schoolYear) {
		return rosterDao.checkActiveStudentsCountOnRoster(classroomId, schoolYear);
	}
	
	@Override
	public Roster getJsonRosterData(Long rosterId) {
		return rosterDao.getRosterJsonFormatData(rosterId);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getClassroomIds(Long userId, Long schoolYear) {
		return rosterDao.getClassroomIds(userId, schoolYear);
	}

	@Override
	public List<RosterDTO> findRostersforTeacherIdInCurrentYear(long teacherId, long organizationId,
			long currentSchoolYear) {
		// TODO Auto-generated method stub
		return null;
	}




	
}
