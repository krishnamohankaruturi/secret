package edu.ku.cete.service.impl.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.controller.sif.CEDSCodeMapping;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.ContentAreaDao;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.StudentAssessmentProgramMapper;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UploadFileService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * Added By Prasanth User Story: US16352: Spring batch upload for data
 * file(User)
 */
@SuppressWarnings("unused")
@Service
public class TECUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TECUploadCustomValidationServiceImpl.class);

	@Autowired
	private UserService userService;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private UploadFileService uploadFileService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private AssessmentProgramDao assessmentProgramDao;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private RosterService rosterService;

	@Autowired
	private GradeCourseService gradeCourseService;

	@Autowired
	private ContentAreaDao contentAreaDao;

	@Value("${user.organization.DLM}")
	private String dlmOrgName;

	@Autowired
	private PermissionUtil permissionUtil;
	
	@Autowired
	private StudentAssessmentProgramMapper studentAssessmentProgramMapper;

	@Autowired
	private AppConfigurationService appConfigurationService;

	final static Log logger = LogFactory.getLog(TECUploadCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		TecRecord uploadTecRecord = (TecRecord) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		String uploadTypeCode = (String) params.get("uploadTypeCode");
		
		boolean isTecRecord = true;
		String lineNumber = uploadTecRecord.getLineNumber();

		boolean validationPassed = true;

		ContractingOrganizationTree contractingOrganizationTree = null;

		UserDetailImpl userDetails = (UserDetailImpl) params.get("currentUser");

		Long currentContextuserId = userDetails.getUser().getId();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		Restriction restriction = (Restriction) params.get("restriction");

		if ("TEC_XML_RECORD_TYPE".equals(uploadTypeCode) && StringUtils.isNotEmpty(uploadTecRecord.getRecordType())
				&& uploadTecRecord.getRecordType().equalsIgnoreCase("EXIT")) {
			if (StringUtils.isNotBlank(uploadTecRecord.getExitWithdrawalType())) {
				Integer reason = new Integer(CEDSCodeMapping.getBycedsCodeSIF(uploadTecRecord.getExitWithdrawalType()));
				uploadTecRecord.setExitReason(reason);
			} else {
				uploadTecRecord.addInvalidField(FieldName.EXIT_REASON + "", "", true, InvalidTypes.EMPTY,
						" is not specified.");
				validationPassed = false;
			}

			if (StringUtils.isBlank(uploadTecRecord.getExitDateStr())) {
				uploadTecRecord.addInvalidField(FieldName.EXIT_DATE + "", "", true, InvalidTypes.EMPTY,
						" is not specified.");
				validationPassed = false;
			} else {
				if(uploadTecRecord.getExitDate() == null){
					uploadTecRecord.addInvalidField(FieldName.EXIT_DATE + "", "", true, InvalidTypes.EMPTY,
							" is not valid.");
					validationPassed = false;
				}
			}
		}
		
		if (uploadTecRecord.getStateStudentIdentifier() == null
				|| uploadTecRecord.getStateStudentIdentifier().equalsIgnoreCase("null")) {
			uploadTecRecord.addInvalidField(FieldName.STATE_STUDENT_IDENTIFIER + "", "studentRefId" + "", true,
					InvalidTypes.EMPTY, " is not specified.");
		}
		
		
		if(!uploadTecRecord.getStateStudentIdentifier().trim().isEmpty() ){
			String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
			int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
			if(uploadTecRecord.getStateStudentIdentifier().trim().length()>allowedLength){
    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
    			String errorMessage = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
				uploadTecRecord.addInvalidField("stateStudentIdentifier", uploadTecRecord.getStateStudentIdentifier(), true, 
						errorMessage);
				}
		}
		
		if (uploadTecRecord.getAttendanceSchoolProgramIdentifier() == null
				|| uploadTecRecord.getAttendanceSchoolProgramIdentifier().equalsIgnoreCase("null")) {
			uploadTecRecord.addInvalidField(FieldName.ATTENDANCE_SCHOOL + "", "programRefId" + "", true,
					InvalidTypes.EMPTY, " is not specified.");
		}

		if (StringUtils.isEmpty(uploadTecRecord.getStateStudentIdentifier())) {
			uploadTecRecord.addInvalidField(FieldName.STATE_STUDENT_IDENTIFIER + "", "", true, InvalidTypes.EMPTY,
					" is not specified.");
		}
		if (StringUtils.isEmpty(uploadTecRecord.getAttendanceSchoolProgramIdentifier())) {
			uploadTecRecord.addInvalidField(FieldName.ATTENDANCE_SCHOOL + "", "", true, InvalidTypes.EMPTY,
					" is not specified.");
		}

		List<TecRecord> tecRecords = new ArrayList<TecRecord>();
		tecRecords.add(uploadTecRecord);
		uploadFileService.checkTecRecordDependencies(tecRecords);
		String stateStudentIdent = uploadTecRecord.getStudent().getStateStudentIdentifier();
		contractingOrganizationTree = (ContractingOrganizationTree) params.get("contractingOrganizationTree");
		List<? extends StudentRecord> studentRecords = studentService.verifyStateStudentIdentifiersExist(
				contractingOrganizationTree.getContractingOrganizationTreeIds(),
				contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationIds(),
				contractingOrganizationTree.getDiffOrgIdsBetweenContractingOrgNUserOrgHierarchy(), tecRecords);

		uploadTecRecord = (TecRecord) studentRecords.get(0);
		if (uploadTecRecord.isDoReject()) {
			validationPassed = false;
		} else {

			uploadTecRecord.getEnrollment().setRestrictionId(restriction != null ? restriction.getId() : 2);

			KidRecord kidRecord = enrollmentService.convertToKidRecord(uploadTecRecord, contractingOrganizationTree);
			if (kidRecord.getRecordType().equalsIgnoreCase("EXIT")) {

				List<Integer> stateSupportExitCodes = studentService.getStateSpecificExitCodes((Long)params.get("stateId"), (Long)params.get("assessmentProgramIdOnUI"), currentSchoolYear);
				
				if (uploadTecRecord.getExitReason() == -9999) {
					uploadTecRecord.addInvalidField("exitReason", "", true,
							" Usage of given exit code is not allowed. ");
					validationPassed = false;
				} else {//F915 Exit code by State
					if(!stateSupportExitCodes.contains((Integer)uploadTecRecord.getExitReason())) {
						uploadTecRecord.addInvalidField("exitReason", "" + uploadTecRecord.getExitReason(), true,
								" This record is rejected because exit code \""+ uploadTecRecord.getExitReason() +"\" is not valid in your state. ");
						validationPassed = false;						
					}
				}
			}
			if (kidRecord.isDoReject()) {
				uploadTecRecord = enrollmentService.convertFromKidRecord(kidRecord);
				validationPassed = false;
			} else {
				// validating if attendance school is found.Needed if saving
				// enrollment.
				// find the organization only from at or below the user's
				// organization.
				Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
						.getOrganization(kidRecord.getAttendanceSchoolProgramIdentifier());
				// easier to add the multiple organization context here.
				if (attendanceSchool == null) {
					logger.debug("The record " + kidRecord + " is rejected because the organization is unrecognized");
					uploadTecRecord.addInvalidField(ParsingConstants.BLANK + FieldName.ATTENDANCE_SCHOOL,
							kidRecord.getAttendanceSchoolProgramIdentifier(), true, InvalidTypes.NOT_FOUND);
					validationPassed = false;
				} else {
					kidRecord.setAttendanceSchoolId(attendanceSchool.getId());
					// int contractingOrgSchoolYear =
					// enrollmentDao.getContractingOrgSchoolYear(attendanceSchool.getId());
					int contractingOrgSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization()
							.getCurrentSchoolYear();
					int enrollmentCurrentSchoolYear = kidRecord.getCurrentSchoolYear();
					if (contractingOrgSchoolYear != enrollmentCurrentSchoolYear) {
						FieldName fieldName = FieldName.CURRENT_SCHOOL_YEAR;
						if("TEC_XML_RECORD_TYPE".equals(uploadTypeCode)){
							fieldName = FieldName.SCHOOL_YEAR;
						}
						uploadTecRecord.addInvalidField(fieldName + ParsingConstants.BLANK,
								kidRecord.getCurrentSchoolYear() + ParsingConstants.BLANK, true,
								InvalidTypes.IN_CORRECT, " does not match the contracting organization school year.");
						
						validationPassed = false;
					} else {
						// set contracting organization on student
						Organization studentState = organizationService
								.getContractingOrganization(attendanceSchool.getId());
						kidRecord.getStudent().setStateId(studentState.getId());

						if (StringUtils.isNotEmpty(uploadTecRecord.getRecordType())
								&& !uploadTecRecord.getRecordType().equalsIgnoreCase("EXIT")) {
							// check validity of test type to assessment program
							String tecRecordTestType = uploadTecRecord.getTestType();
							List<AssessmentProgram> assessmentPrograms = assessmentProgramDao
									.findByTestTypeCode(tecRecordTestType);
							List<Student> stu = studentDao.getBySsidAndState(
									kidRecord.getStudent().getStateStudentIdentifier(), studentState.getId());
							if (stu != null && !stu.isEmpty()) {
								boolean studentIsInAssessmentProgram = false;
								for (int x = 0; x < assessmentPrograms.size() && !studentIsInAssessmentProgram; x++) {
									AssessmentProgram ap = assessmentPrograms.get(x);
									// KAP can't do uploads, so we exclude them
									// here to get an accurate check, since they
									// and CPASS can overlap a little
									
									//Commented code as part of US19459
									//if (!ap.getAbbreviatedname().equalsIgnoreCase("KAP")) {
										studentIsInAssessmentProgram = studentService
												.isStudentAssociatedWithAssessmentProgram(stu.get(0).getId(),
														ap.getAbbreviatedname());
									//}
								}
								if (!studentIsInAssessmentProgram) {
									uploadTecRecord.addInvalidField(FieldName.TEST_TYPE + ParsingConstants.BLANK,
											tecRecordTestType, true,
											" the student is not associated with a corresponding Assessment Program.");
									kidRecord.setDoReject(true);
									validationPassed = false;
								}
							}
						}

						// set the currentContextUserId to be used in
						// transaction
						kidRecord.getStudent().setCurrentContextUserId(currentContextuserId);

						// check for state id
						Student student = kidRecord.getStudent();
						if (enrollmentService.isDLMKid(kidRecord)) {
							AssessmentProgram ap = assessmentProgramDao.findByProgramName(dlmOrgName);
							student.setAssessmentProgramId(ap.getId());
						}
						Organization aypSchool = contractingOrganizationTree.getUserOrganizationTree()
								.getOrganization(kidRecord.getAypSchoolIdentifier());
						if (!isTecRecord) {
							if (aypSchool == null) {
								logger.debug("The record " + kidRecord
										+ " is rejected because the AYP School organization is unrecognized");
								uploadTecRecord.addInvalidField(ParsingConstants.BLANK + FieldName.AYP_SCHOOL,
										kidRecord.getAypSchoolIdentifier(), true, InvalidTypes.NOT_FOUND);
								validationPassed = false;

							} else {
								String mappedCompRace = null;
								if (StringUtils.isNotEmpty(student.getComprehensiveRace())) {
									mappedCompRace = studentDao
											.findMappedComprehensiveRaceCode(student.getComprehensiveRace());
								}
								kidRecord.getStudent().setComprehensiveRace(mappedCompRace);

								if (kidRecord.getRecordType().equalsIgnoreCase("TEST")) {
									// student =
									// studentService.addOrUpdate(kidRecord.getStudent(),
									// kidRecord.getAttendanceSchoolId());
								} else {
									student = studentService.validateIfStudentExists(kidRecord.getStudent());
								}
							}
						}
						if (student.isDoReject()) {
							kidRecord.getInValidDetails().addAll(student.getInValidDetails());
							kidRecord.setDoReject(true);
							validationPassed = false;
						} else {
							if (kidRecord.getRecordType().equalsIgnoreCase("EXIT")) {
								if (uploadTecRecord.getExitDate().compareTo(new Date()) > 0) {
									uploadTecRecord.addInvalidField("exitDateStr", uploadTecRecord.getExitDateStr(),
											true, " exit date cannot be future date ");
									validationPassed = false;
								}
							}
							if (validationPassed) {
								WebServiceRosterRecord rosterRecord = null;
								Enrollment enrollment = kidRecord.getEnrollment();
								if (kidRecord.getDlmELAProctorId() != null && kidRecord.getStateELAAssessment() != null
										&& !"0".equals(kidRecord.getStateELAAssessment())) {
									rosterRecord = fillAndValidateRoster(contractingOrganizationTree, attendanceSchool,
											student, enrollment, kidRecord.getDlmELAProctorId(),
											kidRecord.getDlmELAProctorName(), "ELA");
								}

								if (kidRecord.getDlmMathProctorId() != null && kidRecord.getStateMathAssess() != null
										&& !"0".equals(kidRecord.getStateMathAssess())) {
									rosterRecord = fillAndValidateRoster(contractingOrganizationTree, attendanceSchool,
											student, enrollment, kidRecord.getDlmMathProctorId(),
											kidRecord.getDlmMathProctorName(), "M");
								}

								if (kidRecord.getDlmSciProctorId() != null && kidRecord.getStateSciAssessment() != null
										&& !"0".equals(kidRecord.getStateSciAssessment())) {
									rosterRecord = fillAndValidateRoster(contractingOrganizationTree, attendanceSchool,
											student, enrollment, kidRecord.getDlmSciProctorId(),
											kidRecord.getDlmSciProctorName(), "Sci");
								}
								if (kidRecord.getCpassProctorId() != null) {
									if (kidRecord.getEndOfPathwaysAssessment() != null
											&& !"0".equals(kidRecord.getEndOfPathwaysAssessment())) {
										List<ContentArea> contentAreas = contentAreaDao.findBySubjectAreaTestType(
												"EOPA", kidRecord.getEndOfPathwaysAssessment());

										if (!CollectionUtils.isEmpty(contentAreas)) {
											rosterRecord = fillAndValidateRoster(contractingOrganizationTree,
													attendanceSchool, student, enrollment,
													kidRecord.getCpassProctorId(), kidRecord.getCpassProctorName(),
													contentAreas.get(0).getAbbreviatedName());
										}
									}

									if (kidRecord.getGeneralCTEAssessment() != null
											&& !"0".equals(kidRecord.getGeneralCTEAssessment())) {
										List<ContentArea> contentAreas = contentAreaDao.findBySubjectAreaTestType(
												"GCTEA", kidRecord.getGeneralCTEAssessment());

										if (!CollectionUtils.isEmpty(contentAreas)) {
											rosterRecord = fillAndValidateRoster(contractingOrganizationTree,
													attendanceSchool, student, enrollment,
													kidRecord.getCpassProctorId(), kidRecord.getCpassProctorName(),
													contentAreas.get(0).getAbbreviatedName());
										}
									}
								}
								uploadTecRecord.setRosterRecord(rosterRecord);
							}
						}
					}
				}
			}
			uploadTecRecord.setKidRecord(kidRecord);
		}
		if (!validationPassed) {
			if (StringUtils.isEmpty(stateStudentIdent)) {
				stateStudentIdent = ".";
			}
			for (InValidDetail inValidDetail : uploadTecRecord.getInValidDetails()) {
				String reason = inValidDetail.getReason();
				if (reason == null || reason.isEmpty())
					reason = inValidDetail.getInvalidType().name().toLowerCase().replaceAll("_", " ");

				String errMsg = new StringBuilder(stateStudentIdent).append("###")
						.append(inValidDetail.getFormattedFieldValue())
						// .append(" is ")
						.append(reason).toString();
				String fieldName = inValidDetail.getActualFieldName();
				if (FieldName.ATTENDANCE_SCHOOL.toString().equals(fieldName)) {
					fieldName = "attendanceSchoolProgramIdentifier";
				} else {
					if (fieldName.indexOf(",") > 0)
						fieldName = fieldName.split(",")[0];

					fieldName = fieldName.replaceAll(" ", "").replaceAll("_", "");
					fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
					if ("exitDate".equals(fieldName))
						fieldName = "exitDateStr";
				}
				validationErrors.rejectValue(fieldName, "",
						new String[] { lineNumber, mappedFieldNames.get(fieldName) }, errMsg);
			}
		} else {
			uploadTecRecord.setCreatedUser(userDetails.getUserId());
			uploadTecRecord.setModifiedUser(userDetails.getUserId());
			uploadTecRecord.setUser(userDetails.getUser());
		}
		// uploadedUser.setBatchUploadId(batchUploadId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", uploadTecRecord);
		// logger.debug("Completed validation completed.");
		return customValidationResults;
	}

	private WebServiceRosterRecord fillAndValidateRoster(ContractingOrganizationTree contractingOrganizationTree,
			Organization attendanceSchool, Student student, Enrollment enrollment, String proctorId, String proctorName,
			String stateSubjectCode) {

		WebServiceRosterRecord rosterRecord = new WebServiceRosterRecord();
		rosterRecord.setEducatorIdentifier(proctorId);
		rosterRecord.setEducatorSchoolIdentifier(enrollment.getAttendanceSchoolProgramIdentifier());
		if (proctorName != null && proctorName.trim().length() > 0) {

			proctorName = proctorName.trim();
			int spaceIndex = proctorName.indexOf(' ');
			if (spaceIndex > -1) {
				rosterRecord.setEducatorFirstName(proctorName.substring(0, spaceIndex));
				rosterRecord.setEducatorLastName(proctorName.substring(spaceIndex + 1));
			} else {
				rosterRecord.setEducatorFirstName(proctorName);
				rosterRecord.setEducatorLastName(null);
			}
		}
		rosterRecord.setRosterRecordType("STCO");
		rosterRecord.setSourceType(SourceTypeEnum.TESTWEBSERVICE.getCode());
		rosterRecord.setCurrentSchoolYear(enrollment.getCurrentSchoolYear());
		rosterRecord.setSchoolIdentifier(enrollment.getAttendanceSchoolProgramIdentifier());
		rosterRecord.setStudent(student);
		rosterRecord.setStateStudentIdentifier(student.getStateStudentIdentifier());
		rosterRecord.setCourseSection(stateSubjectCode);
		rosterRecord.setStateCourseCode(stateSubjectCode);
		rosterRecord.setStateSubjectAreaCode(stateSubjectCode);
		rosterRecord.setEnrollment(enrollment);
		rosterRecord.setCurrentSchoolYear(enrollment.getCurrentSchoolYear());

		rosterRecord = (WebServiceRosterRecord) rosterValidationsSTCO(rosterRecord, contractingOrganizationTree);

		return rosterRecord;
	}

	private final RosterRecord rosterValidationsSTCO(RosterRecord scrsRecord,
			ContractingOrganizationTree contractingOrganizationTree) {

		RecordSaveStatus scrsRecordSaveStatus = RecordSaveStatus.BEGIN_ROSTER_SAVE;
		Long currentContextUserId = scrsRecord.getCurrentContextUserId();
		// validating if attendance school is found.Needed if saving enrollment.
		// find the organization only from at or below the user's organization.
		Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
				.getOrganization(scrsRecord.getSchoolIdentifier());
		// easier to add the multiple organization context here.
		if (attendanceSchool == null) {
			scrsRecord.addInvalidField(FieldName.SCHOOL_IDENTIFIER + ParsingConstants.BLANK,
					ParsingConstants.BLANK_SPACE + scrsRecord.getSchoolIdentifier() + ParsingConstants.BLANK_SPACE,
					true, InvalidTypes.SCHOOL_IDENTIFIER_NOT_FOUND + ParsingConstants.BLANK_SPACE);
			scrsRecord.setSaveStatus(RecordSaveStatus.AYP_SCHOOL_NOT_FOUND);
			return scrsRecord;
		}
		attendanceSchool.setCurrentContextUserId(scrsRecord.getCurrentContextUserId());

		Organization aypSchool = contractingOrganizationTree.getUserOrganizationTree()
				.getOrganization(scrsRecord.getEnrollment().getAypSchoolIdentifier());

		if (aypSchool == null) {
			scrsRecord.addInvalidField(FieldName.AYP_SCHOOL_IDENTIFIER + ParsingConstants.BLANK,
					ParsingConstants.BLANK_SPACE + scrsRecord.getEnrollment().getAypSchoolIdentifier()
							+ ParsingConstants.BLANK_SPACE,
					true, InvalidTypes.SCHOOL_IDENTIFIER_NOT_FOUND + ParsingConstants.BLANK_SPACE);
			scrsRecord.setSaveStatus(RecordSaveStatus.ATTENDANCE_SCHOOL_NOT_FOUND);
			return scrsRecord;
		}
		// This is for Webservice SCTO, where we would be getting Educator
		// School Identifier.
		Organization educatorSchool = null;
		educatorSchool = contractingOrganizationTree.getUserOrganizationTree()
				.getOrganization(((WebServiceRosterRecord) scrsRecord).getEducatorSchoolIdentifier());
		// easier to add the multiple organization context here.
		if (educatorSchool != null) {
			educatorSchool.setCurrentContextUserId(scrsRecord.getCurrentContextUserId());
		}

		Student student = scrsRecord.getStudent();

		scrsRecord.setSchool(attendanceSchool);

		// This is done here because student needs to be selectively updated,
		// which is different from enrollment upload.
		student = scrsRecord.getStudent();

		String mappedCompRace = null;
		if (StringUtils.isNotEmpty(student.getComprehensiveRace())) {
			mappedCompRace = studentDao.findMappedComprehensiveRaceCode(student.getComprehensiveRace());
		}
		student.setComprehensiveRace(mappedCompRace);

		if (student.getStateId() == null) {
			// set contracting organization on student
			Organization studentState = organizationService.getContractingOrganization(attendanceSchool.getId());
			student.setStateId(studentState.getId());
			scrsRecord.getStudent().setStateId(studentState.getId());
		}

		/*
		 * if (scrsRecord.getRosterRecordType() != null &&
		 * scrsRecord.getRosterRecordType().equalsIgnoreCase( STCORecordType)) {
		 * student = studentService.addOrUpdateSelective(student,
		 * attendanceSchool.getId()); } else { student =
		 * studentService.validateIfStudentExists(student); }
		 */
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

		// validate if the teacher is found.Needed if saving roster.
		User educator = scrsRecord.getEducator();
		scrsRecord.appendSchoolIdentifier(attendanceSchool.getId());
		educator.setActiveFlag(false);

		// Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value
		// violates unique constraint "uk_email"
		User dbEducator = userService.getByEmail(educator.getEmail());
		if (dbEducator != null
				&& !dbEducator.getUniqueCommonIdentifier().equals(educator.getUniqueCommonIdentifier())) {
			scrsRecord.addInvalidField(
					FieldName.EDUCATOR_IDENTIFIER + ParsingConstants.BLANK, "xml: " + scrsRecord.getEducatorIdentifier()
							+ "db:" + dbEducator.getUniqueCommonIdentifier() + ParsingConstants.BLANK,
					false, InvalidTypes.MIS_MATCH);
			scrsRecord.setDoReject(true);
			return scrsRecord;
		}
		// End Cause: org.postgresql.util.PSQLException: ERROR: duplicate key
		// value violates unique constraint "uk_email"

		// This is for SCTO Webservice, where we would be getting Educator
		// School Identifier.
		if (educatorSchool != null) {
			educator = userService.addIfNotPresent(educator,
					contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationTree(), educatorSchool);
			if (educator.getSaveStatus().equals(RecordSaveStatus.EDUCATOR_ADDED)) {
				scrsRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER + ParsingConstants.BLANK,
						scrsRecord.getEducatorIdentifier() + ParsingConstants.BLANK, false, InvalidTypes.CREATED_NEW);
				scrsRecord.setCreated(true);
			}
			scrsRecord.setEducator(educator);
		}

		// set course enrollment status and state subject area and state course.
		/*
		 * Category scrsEnrollmentStatus = getCategory(
		 * courseEnrollmentStatusType.getId(),
		 * scrsRecord.getEnrollmentStatusCodeStr());
		 * scrsRecord.setEnrollmentStatus(scrsEnrollmentStatus);
		 */
		ContentArea scrsStateSubjectArea = rosterService.getContentArea(scrsRecord.getStateSubjectAreaCode(),
				attendanceSchool.getId());
		if (scrsStateSubjectArea == null) {
			scrsRecord.setSaveStatus(scrsRecordSaveStatus);
			// could not find the subject area
			scrsRecord.addInvalidField(FieldName.SUBJECT + ParsingConstants.BLANK,
					scrsRecord.getStateSubjectAreaCode() + ParsingConstants.BLANK_SPACE, true,
					InvalidTypes.SUBJECT_NOT_FOUND + ParsingConstants.BLANK);
			return scrsRecord;
		}
		scrsRecord.setStateSubjectArea(scrsStateSubjectArea);
		return scrsRecord;

	}

}
