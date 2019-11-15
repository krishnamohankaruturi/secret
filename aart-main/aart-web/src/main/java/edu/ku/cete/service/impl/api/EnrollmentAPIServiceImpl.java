/**
 * 
 */
package edu.ku.cete.service.impl.api;

import java.util.Date;
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

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.api.EnrollmentAPIObject;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.service.api.EnrollmentAPIService;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * @author v090n216
 *
 */
@Service
public class EnrollmentAPIServiceImpl implements EnrollmentAPIService {

	@Autowired
	EnrollmentService enrollmentService;

	/**
	 * API Dashboard error
	 */
	@Autowired
	private APIDashboardErrorService apiErrorService;

	@Autowired
	private EnrollmentsRostersService erService;

	@Autowired
	private EnrollmentsRostersDao erDao;

	@Autowired
	private OrganizationDao orgDao;

	@Autowired
	private StudentService studentService;

	@Autowired
	private RosterService rosterService;

	private Logger logger = LoggerFactory.getLogger(OrganizationAPIServiceImpl.class);

	protected final ApiRecordTypeEnum RECORD_TYPE = ApiRecordTypeEnum.ENROLLMENT;

	/**
	 * 
	 */
	@Override
	public Map<String, Object> postEnrollment(Map<String, Object> response, EnrollmentAPIObject enrollmentAPIObject,
			Long userId) {
		boolean errorEncountered = false;
		APIDashboardError error = null;
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.POST;

		// Validate the enrollmentAPIObject before processing
		response = validateEnrollment(response, enrollmentAPIObject, REQUEST_TYPE, userId);
		if (response != null && response.containsKey("success") && (Boolean) response.get("success") == true) {

			Student student = studentService.getByExternalId(enrollmentAPIObject.getUniqueStudentId());

			// Check if the student exists
			if (student != null && student.getId() != null) {

				// search for rosters
				List<Roster> rosters = rosterService.getRosterByClassroomId(enrollmentAPIObject.getClassroomId());
				Roster roster = CollectionUtils.isEmpty(rosters) ? null : rosters.get(0);

				if (roster != null) {
					// check if student is already enrolled in another roster for same course
					// (stateSubjectAreaid)
					Organization org = orgDao.get(roster.getAttendanceSchoolId());
					Organization contractingOrganization = orgDao.getContractingOrg(org.getId());
					int schoolYear = Math.toIntExact(contractingOrganization.getCurrentSchoolYear());
					
					  //Commenting this code as pltw wants to be able to enroll a student in multiple
					  /* rosters for same course int sameCourseEnrollments = erDao
					  .selectsByStudentIdAndStateSubjectAreaId(roster.getStateSubjectAreaId(),
					  student.getId(), schoolYear); // Proceed only if student is not rostered for
					  same subject multiple times if (sameCourseEnrollments == 0) { */
					 
					List<Enrollment> enrollments = enrollmentService.getCurrentEnrollmentsByStudentId(student.getId(),
							org.getId(), schoolYear,true); 
					// only create the enrollment if it a new enrollment ID
					if (CollectionUtils.isNotEmpty(enrollments)) {
						// verify if the enrollment Roster record already exist
						List<EnrollmentsRosters> enrollmentRosters = erService
								.getByExternalId(enrollmentAPIObject.getEnrollmentId());

						if (CollectionUtils.isEmpty(enrollmentRosters)) {
							// save enrollment rosters only if the no entry exists with given externalID
							EnrollmentsRosters ers = erDao.getEnrollmentInfoByRosterIdEnrollmentId(roster.getId(),
									enrollments.get(0).getId());
							// check if student is already enrolled in provided classroomId
							if (ers != null && ers.getId() != null) {
								// Error: Students cannot be enrolled in multiple time in the same roster
								errorEncountered = true;
								error = (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
										enrollmentAPIObject.getEnrollmentId().toString(),
										new StringBuilder().append(student.getLegalFirstName()).append(" ")
												.append(student.getLegalLastName()).toString(),
										org, enrollmentAPIObject.getClassroomId(), userId,
										new StringBuilder().append("Student ")
												.append(enrollmentAPIObject.getUniqueStudentId())
												.append(" already enrolled in the same classroomID - ")
												.append(String.valueOf(enrollmentAPIObject.getClassroomId()))
												.toString()));
							} else {
								// Save if all the business rules above are satisfied
								EnrollmentsRosters er = saveEnrollmentsRosters(enrollments.get(0).getId(), roster,
										enrollmentAPIObject.getEnrollmentId(), userId);
								logger.debug("new enrollmentsrosters id = " + er.getId());
							}
						} else {
							// Error: student is already enrolled
							errorEncountered = true;
							error = (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
									enrollmentAPIObject.getEnrollmentId().toString(),
									new StringBuilder().append(student.getLegalFirstName()).append(" ")
											.append(student.getLegalLastName()).toString(),
									org, enrollmentAPIObject.getClassroomId(), userId,
									new StringBuilder().append("Student")
											.append(enrollmentAPIObject.getUniqueStudentId())
											.append(" is already enrolled with given enrollmentId")
											.append(String.valueOf(enrollmentAPIObject.getEnrollmentId())).toString()));
						}
					} else {
						// Error: Student is not enrolled in given school for current year
						errorEncountered = true;
						error = (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
								enrollmentAPIObject.getEnrollmentId().toString(), student.getAbbreviatedName(), org,
								enrollmentAPIObject.getClassroomId(), userId,
								new StringBuilder().append("Student").append(enrollmentAPIObject.getUniqueStudentId())
										.append("is not enrolled in School Id: ").append(org.getExternalid())
										.append(" for school year : ").append(schoolYear).toString()));
					}
					
					  //Commenting this code as pltw wants to be able to enroll a student in multiple
					  /* rosters for same course might change in future hence keeping it in comment
					  rather than deleting it }else { //Error: Students cannot be enrolled in
					  multiple rosters for the same course. errorEncountered = true; error =
					  (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
					  enrollmentAPIObject.getEnrollmentId().toString(), null, null,
					  enrollmentAPIObject.getClassroomId(), userId, new
					  StringBuilder().append("Student ").append(enrollmentAPIObject.
					  getUniqueStudentId())
					  .append(" is already enrolled in another roster for same course as classroomId "
					  ) .append(enrollmentAPIObject.getClassroomId()).append(" Has.")
					  .toString())); } */
					 
				} else {
					// Error: roster doesnt exist
					errorEncountered = true;
					error = (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
							enrollmentAPIObject.getEnrollmentId().toString(), null, null, enrollmentAPIObject.getClassroomId(),
							userId,
							new StringBuilder().append("Roster for classroom Id ")
									.append(enrollmentAPIObject.getClassroomId()).append(" doesn't exist.")
									.toString()));
				}
			} else {
				// Error: student does not exist.
				errorEncountered = true;
				error = (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
						enrollmentAPIObject.getEnrollmentId().toString(), null, null, enrollmentAPIObject.getClassroomId(),
						userId,
						new StringBuilder().append("Student with Id ").append(enrollmentAPIObject.getUniqueStudentId())
								.append(" doesn't exist.").toString()));
			}
			response.put("success", !errorEncountered);
			response.put("error", error);
		} // else response is already created in validate Enrollment method
		return response;
	}

	@Override
	public Map<String, Object> putEnrollment(Map<String, Object> serviceResponse,
			EnrollmentAPIObject enrollmentAPIObject, Long userId) {
		boolean errorFound = false;
		APIDashboardError error = null;
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.PUT;
		serviceResponse = validateEnrollment(serviceResponse, enrollmentAPIObject, REQUEST_TYPE, userId);
		if (serviceResponse != null && serviceResponse.containsKey("success")
				&& (Boolean) serviceResponse.get("success") == true) {
			Student student = studentService.getByExternalId(enrollmentAPIObject.getUniqueStudentId());

			// Check if the student exists
			if (student != null && student.getId() != null) {

				// search for rosters
				List<Roster> rosters = rosterService.getRosterByClassroomId(enrollmentAPIObject.getClassroomId());
				Roster roster = CollectionUtils.isEmpty(rosters) ? null : rosters.get(0);

				if (roster != null) {
					List<EnrollmentsRosters> enrollmentRosters = erDao
							.getDeletedByExternalId(enrollmentAPIObject.getEnrollmentId());
					EnrollmentsRosters er = CollectionUtils.isEmpty(enrollmentRosters) ? null : enrollmentRosters.get(0);
					if (er != null && er.getId() != null) {
						erDao.reactivateById(er.getId(), userId);
					} else {
						// No Enrollemnt for Roster Found with current external enrollmentID
						errorFound = true;
						error = apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
								String.valueOf(enrollmentAPIObject.getEnrollmentId()), "enrollment", null,
								enrollmentAPIObject.getClassroomId(), userId,
								new StringBuilder().append("No Enrollemnt for enrollemntId:")
										.append(enrollmentAPIObject.getEnrollmentId()).append(" and classroom Id:")
										.append(enrollmentAPIObject.getClassroomId()).append("Found").toString());
					}
				}else {
					// Error: roster doesnt exist
					errorFound = true;
					error = (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
							enrollmentAPIObject.getEnrollmentId().toString(), null, null, enrollmentAPIObject.getClassroomId(),
							userId,
							new StringBuilder().append("Roster for classroom Id ")
									.append(enrollmentAPIObject.getClassroomId()).append(" doesn't exist.")
									.toString()));
				}
			}else {
				// Error: student does not exist.
				errorFound = true;
				error = (apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE,
						enrollmentAPIObject.getEnrollmentId().toString(), null, null, enrollmentAPIObject.getClassroomId(),
						userId,
						new StringBuilder().append("Student with Id ").append(enrollmentAPIObject.getUniqueStudentId())
								.append(" doesn't exist.").toString()));
			}
			serviceResponse.put("success", !errorFound);
			serviceResponse.put("error", error);
		} // else response is already created in validate Enrollment method
		return serviceResponse;
	}

	@Override
	public Map<String, Object> deleteEnrollment(Map<String, Object> serviceResponse,
			EnrollmentAPIObject enrollmentAPIObject, Long userId) {
		boolean errorFound = false;
		APIDashboardError error = null;
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.PUT;
		if (StringUtils.isNotEmpty(enrollmentAPIObject.getUniqueStudentId())) {
			if (!studentService.hasCompletedTestByExtId(enrollmentAPIObject.getUniqueStudentId())) {
				Student student = studentService.getByExternalId(enrollmentAPIObject.getUniqueStudentId());
				if (student != null && student.getId() != null) {

					// if enrollment Id is not provided, delete all the roster mappings
					if (enrollmentAPIObject.getEnrollmentId() == null) {
						// inactivating all enrollmentRoster Records for this student
						erService.deactivateByStudentIdExternalIdAndRosterStateSubjectAreaCode(student.getId(), null,
								null, userId, SourceTypeEnum.API);
					} else {
						// inactivating matching enrollmentRoster Records
						List<EnrollmentsRosters> enrollmentRosters = erService
								.getByExternalId(enrollmentAPIObject.getEnrollmentId());
						EnrollmentsRosters er = CollectionUtils.isEmpty(enrollmentRosters) ? null
								: enrollmentRosters.get(0);
						if (er != null && er.getId() != null) {
							erDao.inactivateById(er.getId(), userId);
						}
					}
				}else {
					//Error: Student with provided Id not found
					errorFound = true;
					error = apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE, null, "enrollment", null,
							enrollmentAPIObject.getClassroomId(), userId,
							new StringBuilder("Can not find student ")
							.append(enrollmentAPIObject.getUniqueStudentId()).toString());
				}
			} else {
				// Error: Student has already completed a test
				errorFound = true;
				error = apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE, null, "enrollment", null,
						enrollmentAPIObject.getClassroomId(), userId,
						new StringBuilder("Can not delete enrollment, student ")
						.append(enrollmentAPIObject.getUniqueStudentId())
						.append(" has already taken a test.").toString());
			}
		} else {
			// Student Id mandatory
			errorFound = true;
			error = apiErrorService.buildDashboardError(REQUEST_TYPE, RECORD_TYPE, null, "enrollment", null,
					enrollmentAPIObject.getClassroomId(), userId,
					"uniqueStudentId is a mandatory field, please provide a student Id");
		}
		serviceResponse.put("success", !errorFound);
		serviceResponse.put("error", error);
		return serviceResponse;
	}

	// method to perform basic validations like not null, string length.
	// business validaitons are handled in postEnrollment Method
	public Map<String, Object> validateEnrollment(Map<String, Object> response, EnrollmentAPIObject enrollmentAPIObject,
			ApiRequestTypeEnum REQUEST_TYPE, Long userId) {
		boolean isValid = true;
		APIDashboardError error = null;
		StringBuilder errorMsg = new StringBuilder();

		if (StringUtils.isEmpty(enrollmentAPIObject.getRecordType())) {
			isValid = false;
			errorMsg.append(" recordType is mandatory field for the Enrollment request.");
		}
		if (StringUtils.isEmpty(enrollmentAPIObject.getUniqueStudentId())) {
			isValid = false;
			errorMsg.append(" uniqueStudentId is mandatory field for the Enrollment request.");
		}
		if (enrollmentAPIObject.getClassroomId() == null || enrollmentAPIObject.getClassroomId() == 0) {
			isValid = false;
			errorMsg.append(" classroomId is mandatory field for the Enrollment request.");
		}
		if (enrollmentAPIObject.getEnrollmentId() == null || enrollmentAPIObject.getEnrollmentId() == 0) {
			isValid = false;
			errorMsg.append(" enrollmentId is mandatory field for the Enrollment request.");
		}

		if (!isValid) {
			error = apiErrorService.buildDashboardError(REQUEST_TYPE, ApiRecordTypeEnum.ENROLLMENT,
					String.valueOf(enrollmentAPIObject.getEnrollmentId()), "", null, enrollmentAPIObject.getClassroomId(), userId,
					errorMsg.toString());
			logger.error(errorMsg.toString());
			response.put("error", error);
		}
		response.put("success", isValid);
		return response;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private EnrollmentsRosters saveEnrollmentsRosters(long enrollmentId, Roster roster, Long enrExtId, Long userId) {
		Date now = new Date();
		EnrollmentsRosters er = new EnrollmentsRosters();
		er.setRosterId(roster.getId());
		er.setEnrollmentId(enrollmentId);
		er.setExternalId(enrExtId);
		er.setCreatedDate(now);
		er.setModifiedDate(now);
		er.setCreatedUser(userId);
		er.setModifiedUser(userId);
		er.setActiveFlag(true);
		er.setSourceType(SourceTypeEnum.API.getCode());
		erDao.insert(er);
		rosterService.addStudentToRosterEventToDomainAuditHistory(er, roster);
		return er;
	}

}
