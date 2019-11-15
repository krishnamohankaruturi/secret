package edu.ku.cete.service.impl.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.api.StudentAPIObject;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.student.StudentJson;
import edu.ku.cete.model.GradeCourseDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.service.api.StudentAPIService;
import edu.ku.cete.service.api.exception.APIRuntimeException;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.StudentUtil;

@Service
public class StudentAPIServiceImpl implements StudentAPIService {

	private Logger logger = LoggerFactory.getLogger(StudentAPIServiceImpl.class);

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private StudentUtil studentUtil;

	@Autowired
	private EnrollmentsRostersService erService;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private RosterService rosterService;

	@Autowired
	private OrganizationDao orgDao;

	@Autowired
	private OrgAssessmentProgramService orgApService;

	@Autowired
	private GradeCourseDao gradeDao;

	@Autowired
	private APIDashboardErrorService apiErrorService;

	protected final ApiRecordTypeEnum RECORD_TYPE = ApiRecordTypeEnum.STUDENT;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> postStudent(Map<String, Object> response, StudentAPIObject studentAPIObject,
			Long assessmentProgramId, Long userId) throws RuntimeException {
		boolean errorEncountered = false;
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.POST;

		Student apiStudent = convertAPIObjectToStudentObject(studentAPIObject);
		apiStudent.setAssessmentProgramId(assessmentProgramId);
		Organization org = orgDao.getOrgByExternalID(studentAPIObject.getSchoolIdentifier());
		if (org != null && CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(org.getOrganizationType().getTypeCode())
				&& org.getActiveFlag()) {
			Organization contractingOrganization = orgDao.getContractingOrg(org.getId());
			int schoolYear = Math.toIntExact(contractingOrganization.getCurrentSchoolYear());
			
			//checking if the same student and school combination was posted before.
			if (!studentService.isEnrolledInSameSchoolBefore(studentAPIObject.getUniqueStudentId(), org.getId(),schoolYear)) {
				OrgAssessmentProgram oap = orgApService
						.findByOrganizationAndAssessmentProgram(contractingOrganization.getId(), assessmentProgramId);
				if (oap != null) {
					Student dbStudent = studentService.getByExternalId(studentAPIObject.getUniqueStudentId());
					apiStudent.setStateId(contractingOrganization.getId());

					// this is to allow posting same student multiple times
					// Don't save if student exists OR
					// if student exists but is active.
					Enrollment enrollment = null;
					if ((dbStudent != null && !dbStudent.getActiveFlag()) || dbStudent == null) {
						Long newStudentId = (dbStudent != null && dbStudent.getId() != null) ? dbStudent.getId() : 0L;
						String usernameExists = studentService.checkStudentUsername(studentAPIObject.getUserName(), newStudentId);
						if(StringUtils.isEmpty(usernameExists)) {
							saveStudent(apiStudent, userId, dbStudent);
							enrollment = saveEnrollment(enrollment, apiStudent, studentAPIObject, org.getId(), userId,
									schoolYear);
						}else {
							errorEncountered = true;
							errors.add(apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
									studentAPIObject.getUniqueStudentId(),
									new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
											.append(studentAPIObject.getLastName()).toString(),
									org, null, userId,
									new StringBuilder()
										.append("Username '")
										.append(studentAPIObject.getUserName())
										.append("' is already in use, Student '")
										.append(studentAPIObject.getUniqueStudentId())
										.append("' will need a different username.")
										.toString()));
						}
					} else {
						enrollment = saveEnrollment(enrollment, dbStudent, studentAPIObject, org.getId(), userId,
								schoolYear);
					}
					logger.debug("new enrollment id = " + enrollment.getId());

				} else { // oap == null
					errorEncountered = true;
					errors.add(
							apiErrorService
									.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
											studentAPIObject.getUniqueStudentId(),
											new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
													.append(studentAPIObject.getLastName()).toString(),
											org, null, userId,
											new StringBuilder().append("Error linking organization ")
													.append(studentAPIObject.getSchoolIdentifier())
													.append(" to your assessment program").toString()));
				}
			} else { 
				// error : Student already exists, cant post same student again
				errorEncountered = true;
				errors.add(apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
						studentAPIObject.getUniqueStudentId(),
						new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
								.append(studentAPIObject.getLastName()).toString(),
						org, null, userId,
						new StringBuilder().append("student '").append(studentAPIObject.getUniqueStudentId())
								.append("' is already enrolled in school '").append(studentAPIObject.getSchoolIdentifier()).append("'")
								.toString()));
			}
		} else {// org == null
			errorEncountered = true;
			errors.add(apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
					studentAPIObject.getUniqueStudentId(),
					new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
							.append(studentAPIObject.getLastName()).toString(),
					org, null, userId,
					new StringBuilder().append("Could not find ").append(org == null ? "" : "active ")
							.append("school '").append(studentAPIObject.getSchoolIdentifier())
							.append("' when attempting to enroll student '")
							.append(studentAPIObject.getUniqueStudentId()).append("'").toString()));
		}

		response.put("success", !errorEncountered);
		response.put("errors", errors);
		if (errorEncountered) {
			// this is mainly for a rollback, so that we don't have incomplete data
			throw new APIRuntimeException("Placeholder exception!");
		}
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> putStudent(Map<String, Object> response, StudentAPIObject studentAPIObject,
			Long assessmentProgramId, Long userId) throws APIRuntimeException {
		boolean errorEncountered = false;
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.PUT;

		Student apiStudent = convertAPIObjectToStudentObject(studentAPIObject);
		apiStudent.setAssessmentProgramId(assessmentProgramId);

		Organization org = orgDao.getOrgByExternalID(studentAPIObject.getSchoolIdentifier());
		
		Student dbStudent = studentService.getByExternalId(studentAPIObject.getUniqueStudentId());
		if (dbStudent != null) {

			if (org != null
					&& CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(org.getOrganizationType().getTypeCode())) {
				Organization contractingOrganization = orgDao.getContractingOrg(org.getId());
				OrgAssessmentProgram oap = orgApService
						.findByOrganizationAndAssessmentProgram(contractingOrganization.getId(), assessmentProgramId);
				if (oap != null) {
					int schoolYear = Math.toIntExact(contractingOrganization.getCurrentSchoolYear());
					 
					String usernameExists = studentService.checkStudentUsername(studentAPIObject.getUserName(), dbStudent.getId());
					if(StringUtils.isEmpty(usernameExists)) {
						saveStudent(apiStudent, userId, dbStudent);

						boolean enrollmentFlag = false;
						if(dbStudent.getActiveFlag()) {
							enrollmentFlag = true;
						}
						// search for enrollments which are 
						List<Enrollment> enrollments = enrollmentService
								.getCurrentEnrollmentsByStudentId(apiStudent.getId(), org.getId(), schoolYear,enrollmentFlag);
						Enrollment enrollment = CollectionUtils.isEmpty(enrollments) ? null : enrollments.get(0);
						// this will take care of a grade change, if necessary, but since enrollment
						// isn't null, it won't insert a new one
						saveEnrollment(enrollment, apiStudent, studentAPIObject, org.getId(), userId, schoolYear);					
					}else {
						errorEncountered = true;
						errors.add(apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
								studentAPIObject.getUniqueStudentId(),
								new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
										.append(studentAPIObject.getLastName()).toString(),
								org, null, userId,
								new StringBuilder()
									.append("Username '")
									.append(studentAPIObject.getUserName())
									.append("' is already in use, Student '")
									.append(studentAPIObject.getUniqueStudentId())
									.append("' will need a different username.")
									.toString()));
					}
				} else { // oap == null
					errorEncountered = true;
					errors.add(
							apiErrorService
									.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
											studentAPIObject.getUniqueStudentId(),
											new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
													.append(studentAPIObject.getLastName()).toString(),
											org, null, userId,
											new StringBuilder().append("Error linking organization ")
													.append(studentAPIObject.getSchoolIdentifier())
													.append(" to your assessment program").toString()));
				}
			} else { // org == null
				errorEncountered = true;
				errors.add(apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
						studentAPIObject.getUniqueStudentId(),
						new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
								.append(studentAPIObject.getLastName()).toString(),
						org, null, userId,
						new StringBuilder().append("Could not find school '")
								.append(studentAPIObject.getSchoolIdentifier())
								.append("' when attempting to modify enrollment for student '")
								.append(studentAPIObject.getUniqueStudentId()).append("'").toString()));
			}
		}
		else {
			//Error: Could not find student
			errorEncountered = true;
			errors.add(apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
					studentAPIObject.getUniqueStudentId(),
					new StringBuilder().append(studentAPIObject.getFirstName()).append(" ")
							.append(studentAPIObject.getLastName()).toString(),
					org, null, userId,
					new StringBuilder().append("Could not find student '")
							.append(studentAPIObject.getSchoolIdentifier())
							.append("'.").toString()));
		}
		response.put("success", !errorEncountered);
		response.put("errors", errors);
		if (errorEncountered) {
			// this is mainly for a rollback, so that we don't have incomplete data
			throw new APIRuntimeException("Placeholder exception!");
		}
		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> deleteStudent(Map<String, Object> response, StudentAPIObject studentAPIObject,
			Long assessmentProgramId, Long userId) throws APIRuntimeException {
		boolean errorEncountered = false;
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();

		Student student = studentService.getByExternalId(studentAPIObject.getUniqueStudentId());

		if (student == null) {
			logger.info(new StringBuilder().append("/students -- Attempted to DELETE student ")
					.append(studentAPIObject.getUniqueStudentId()).append(" but they do not exist").toString());
		} else if (Boolean.TRUE.equals(student.getActiveFlag())) {
			deactivateStudent(student, null, userId);
		}

		response.put("success", !errorEncountered);
		response.put("errors", errors);
		if (errorEncountered) {
			// this is mainly for a rollback, so that we don't have incomplete data
			throw new APIRuntimeException("Placeholder exception!");
		}
		return response;
	}

	private Student convertAPIObjectToStudentObject(StudentAPIObject studentAPIObject) {
		Student student = new Student();
		student.setStateStudentIdentifier(studentAPIObject.getStateStudentId());
		student.setExternalId(studentAPIObject.getUniqueStudentId());
		student.setLegalFirstName(studentAPIObject.getFirstName());
		student.setLegalLastName(studentAPIObject.getLastName());
		student.setDateOfBirth(studentAPIObject.getDateOfBirth());
		student.setUsername(studentAPIObject.getUserName());
		// Commented because activeflag doesn't mean much in the student API,
		// apparently?
		// But leaving it here just for posterity in case we need it again.
		// student.setActiveFlag(studentAPIObject.getActive() == null ? true :
		// studentAPIObject.getActive());
		if ("male".equalsIgnoreCase(studentAPIObject.getGender())) {
			student.setGender(1);
			
		} else if ("female".equalsIgnoreCase(studentAPIObject.getGender())) {
			student.setGender(0);
		} else {
			student.setGender(null);
		}
		student.setSourceType(SourceTypeEnum.API.getCode());
		return student;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private boolean saveStudent(Student student, Long userId, Student existingStudent) {
		String beforeJson = null;
		String afterJson = null;
		EventTypeEnum event = EventTypeEnum.INSERT;
		if (existingStudent == null) {
			student.setCreatedUser(userId);
			student.setModifiedUser(userId);
			student.setPassword(studentUtil.generatePasswordString());
			student.setActiveFlag(true);
			studentDao.add(student);
			student.setId(studentDao.lastid());
			studentService.addStudentAssmntIfNotPresent(student);
		} else {
			event = EventTypeEnum.UPDATE;
			StudentJson studentJson = studentDao.getStudentjsonData(student.getId());
			beforeJson = studentJson == null ? null : studentJson.buildjsonString();
			student.setId(existingStudent.getId());
			student.setCreatedDate(existingStudent.getCreatedDate());
			student.setCreatedUser(existingStudent.getCreatedUser());
			student.setModifiedUser(userId);
			student.setActiveFlag(true);
			StudentExample example = new StudentExample();
			example.createCriteria().andIdEqualTo(student.getId());
			studentService.updateByExampleSelective(student, example);
		}
		StudentJson studentJson = studentDao.getStudentjsonData(student.getId());
		afterJson = studentJson == null ? null : studentJson.buildjsonString();
		studentService.insertIntoDomainAuditHistory(student.getId(), userId, event.getCode(),
				SourceTypeEnum.API.getCode(), beforeJson, afterJson);
		return true;
	}

	private boolean deactivateStudent(Student student, String[] courseCodes, Long userId) {
		final boolean DEACTIVATE_EVERYTHING = ((courseCodes == null || courseCodes.length == 0));
		if (student != null && student.getId() != null) {
			List<String> contentAreaAbbrNames = new ArrayList<String>();
			if (courseCodes != null) {
				for (String courseCode : courseCodes) {
					contentAreaAbbrNames.add(courseCode);
				}
			}
			Long studentId = student.getId();
			erService.deactivateByStudentIdExternalIdAndRosterStateSubjectAreaCode(studentId, contentAreaAbbrNames,
					null, userId, SourceTypeEnum.API);
			if (DEACTIVATE_EVERYTHING) {
				enrollmentService.deactivateByStudentId(studentId, userId);
				deactivateStudentRecord(student, userId);
			}
		}
		return true;
	}

	private boolean deactivateStudentRecord(Student student, Long userId) {
		StudentJson studentJson = studentDao.getStudentjsonData(student.getId());
		String beforeJson = studentJson == null ? null : studentJson.buildjsonString();

		student.setActiveFlag(false);
		student.setModifiedDate(new Date());
		student.setModifiedUser(userId);
		StudentExample example = new StudentExample();
		example.createCriteria().andIdEqualTo(student.getId());
		studentService.updateByExampleSelective(student, example);

		studentJson = studentDao.getStudentjsonData(student.getId());
		String afterJson = studentJson == null ? null : studentJson.buildjsonString();
		studentService.insertIntoDomainAuditHistory(student.getId(), userId, EventTypeEnum.UPDATE.getCode(),
				SourceTypeEnum.API.getCode(), beforeJson, afterJson);
		return true;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private Enrollment saveEnrollment(Enrollment existingEnrollment, Student student, StudentAPIObject studentAPIObject,
			Long attendanceSchoolId, Long userId, int schoolYear) {
		Date now = new Date();
		Organization district = orgDao.getDistrictBySchoolOrgId(attendanceSchoolId);
		if (existingEnrollment == null) {
			GradeCourse gc = gradeDao
					.basicFindIndependentGradeByAbbreviatedName(studentAPIObject.getCurrentGradeLevel());
			Enrollment enrollment = new Enrollment();
			enrollment.setStudentId(student.getId());
			enrollment.setCurrentGradeLevel(gc == null ? null : gc.getId());
			enrollment.setAttendanceSchoolId(attendanceSchoolId);
			enrollment.setResidenceDistrictIdentifier(district == null ? null : district.getDisplayIdentifier());
			enrollment.setCurrentSchoolYear(schoolYear);
			enrollment.setRestrictionId(1L); // pretty sure this data is never used, but it's a non-nullable
												// column...so...
			enrollment.setCreatedDate(now);
			enrollment.setModifiedDate(now);
			enrollment.setCreatedUser(userId);
			enrollment.setModifiedUser(userId);
			enrollment.setSourceType(SourceTypeEnum.API.getCode());
			enrollment.setActiveFlag(Boolean.TRUE);
			enrollmentService.add(enrollment);
			enrollmentService.addNewEnrollmentDomainAuditHistory(enrollment);
			return enrollment;
		} else {
			GradeCourse gc = gradeDao
					.basicFindIndependentGradeByAbbreviatedName(studentAPIObject.getCurrentGradeLevel());
			Long oldGradeId = existingEnrollment.getCurrentGradeLevel();
			existingEnrollment
					.setResidenceDistrictIdentifier(district == null ? null : district.getDisplayIdentifier());
			existingEnrollment.setModifiedDate(now);
			existingEnrollment.setModifiedUser(userId);
			existingEnrollment.setCurrentContextUserId(userId); // to control the modifieduser, for some reason the
																// above line didn't play nice
			//Setting enrollmentId as true as this can be a request for reactivating a student and hence their enrollment
			existingEnrollment.setActiveFlag(true);
			// at time of implementation, this call doesn't update grade if it's null, which
			// is why we have the grade update lower.
			enrollmentService.update(existingEnrollment);
			boolean gradeChanged = (gc != null && oldGradeId == null) || (gc == null && oldGradeId != null)
					|| (gc != null && oldGradeId != null && gc.getId().longValue() != oldGradeId.longValue());
			if (gradeChanged) {
				existingEnrollment.setCurrentGradeLevel(gc == null ? null : gc.getId());
				enrollmentService.updateGradeLevel(existingEnrollment);
				enrollmentService.addGradeChangeEventDomainAuditHistory(existingEnrollment, oldGradeId);
			}
			return existingEnrollment;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private EnrollmentsRosters saveEnrollmentsRosters(Enrollment enrollment, Roster roster,
			StudentAPIObject studentAPIObject, Long userId) {
		Date now = new Date();
		EnrollmentsRosters er = new EnrollmentsRosters();
		er.setEnrollmentId(enrollment.getId());
		er.setRosterId(roster.getId());
		er.setCreatedDate(now);
		er.setModifiedDate(now);
		er.setCreatedUser(userId);
		er.setModifiedUser(userId);
		er.setSourceType(SourceTypeEnum.API.getCode());
		erService.addEnrollmentToRoster(er);
		rosterService.addStudentToRosterEventToDomainAuditHistory(er, roster);
		return er;
	}

	@Override
	public Map<String, Object> validateStudentAPIObject(ApiRequestTypeEnum method, StudentAPIObject studentAPIObject) {
		Map<String, Object> ret = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		// these first fields are required for every type of request
		if (!StringUtils.equalsIgnoreCase(studentAPIObject.getRecordType(), "student")) {
			errors.add("Record type did not match 'student'");
		}
		if (StringUtils.isEmpty(studentAPIObject.getUniqueStudentId())
				|| StringUtils.length(studentAPIObject.getUniqueStudentId()) > 30) {
			errors.add("Unique student ID was empty or longer than 30 characters");
		}
		// POST and PUT need a lot more validations
		if (method == ApiRequestTypeEnum.POST || method == ApiRequestTypeEnum.PUT) {
			if (StringUtils.isEmpty(studentAPIObject.getLastName())
					|| StringUtils.length(studentAPIObject.getLastName()) > 30) {
				errors.add("Last name was empty or longer than 30 characters");
			}
			if (StringUtils.isEmpty(studentAPIObject.getFirstName())
					|| StringUtils.length(studentAPIObject.getFirstName()) > 30) {
				errors.add("First name was empty or longer than 30 characters");
			}
			if (!StringUtils.equalsIgnoreCase("male", studentAPIObject.getGender())
					&& !StringUtils.equalsIgnoreCase("female", studentAPIObject.getGender())) {
				errors.add("Gender was not a valid value");
			}
			if (StringUtils.isEmpty(studentAPIObject.getUserName())
					|| StringUtils.length(studentAPIObject.getUserName()) > 100) {
				errors.add("Username was empty or longer than 100 characters");
			}
			if (StringUtils.isEmpty(studentAPIObject.getCurrentGradeLevel())
					|| StringUtils.length(studentAPIObject.getCurrentGradeLevel()) > 10) {
				errors.add("Current grade level was empty or longer than 10 characters");
			}
			if (StringUtils.isEmpty(studentAPIObject.getSchoolIdentifier())
					|| StringUtils.length(studentAPIObject.getSchoolIdentifier()) > 30) {
				errors.add("School identifier was empty or longer than 30 characters");
			}
		}
		ret.put("errors", errors);
		return ret;
	}
}
