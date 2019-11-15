package edu.ku.cete.service.impl.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.controller.sif.CEDSCodeMapping;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.EnrollmentsOrganizationInfo;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.StudentAssessmentProgramMapper;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.BatchUploadCustomValidationForAlertService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.ParsingConstants;

@Service
public class UnEnrollmentUploadCustomValidationServiceImpl
		implements BatchUploadCustomValidationService, BatchUploadCustomValidationForAlertService {

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private OrganizationTypeService organizationTypeService;

	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private EnrollmentDao enrollmentDao;
	
	@Autowired
	private StudentAssessmentProgramMapper studentAssessmentProgramMapper;

	@Autowired
	private AppConfigurationService appConfigurationService;

	final static Log logger = LogFactory.getLog(UnEnrollmentUploadCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug(" student exit upload custom validation started");

		EnrollmentRecord enrollmentRecord = (EnrollmentRecord) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();

		String lineNumber = enrollmentRecord.getLineNumber();

		boolean validationPassed = true;

		Organization currentContext = (Organization) params.get("currentContext");
		Long currentOrgId = currentContext.getId();
		Long stateId = (Long) params.get("stateId");
		ContractingOrganizationTree contractingOrganizationTree = (ContractingOrganizationTree) params
				.get("contractingOrganizationTree");

		String uploadTypeCode = (String) params.get("uploadTypeCode");
		if ("UNENRL_XML_RECORD_TYPE".equals(uploadTypeCode)) {
			if (StringUtils.isNotBlank(enrollmentRecord.getResidenceDistrictIdentifier())) {
				OrganizationType organizationType = organizationTypeService.getByTypeCode("DT");
				Organization districtOrg = organizationService.getByDisplayIdAndType(
						enrollmentRecord.getResidenceDistrictIdentifier(), organizationType.getOrganizationTypeId(), stateId);
				Organization state = organizationService.getContractingOrganization(districtOrg.getId());
				contractingOrganizationTree = organizationService.getTree(state);
				currentOrgId = state.getId();
			}
		}

		UserDetailImpl userDetails = (UserDetailImpl) params.get("currentUser");

		Restriction restriction = (Restriction) params.get("restriction");

		Set<Long> contractingOrganizationIds = contractingOrganizationTree.getContractingOrganizationTreeIds();

		String stateStudentIdent = enrollmentRecord.getStudent().getStateStudentIdentifier();
		if (contractingOrganizationIds == null || CollectionUtils.isEmpty(contractingOrganizationIds)) {
			logger.debug("Invalid input organizations" + " when Enforcing uniqueness of state id in students"
					+ enrollmentRecord);

			if (enrollmentRecord != null && enrollmentRecord.getStudent() != null) {

				enrollmentRecord.addInvalidField("stateStudentIdentifier", stateStudentIdent, true,
						InvalidTypes.SCHOOL_NOT_CONTRACTING_FOR_ASSESSMENT);

				logger.debug(
						"Student is rejected because " + enrollmentRecord + "School not contracting for assessment");

				validationPassed = false;
			}
		} else {
			enrollmentRecord.getEnrollment().setRestrictionId(restriction == null ? 1 : restriction.getId());

			enrollmentRecord.getStudent().setStateId(userDetails.getUser().getContractingOrgId());
			Date currentDate = new Date();
			Date schoolExitDate = enrollmentRecord.getExitWithdrawalDate();
			if (schoolExitDate != null && schoolExitDate.compareTo(currentDate) > 0) {
				enrollmentRecord.addInvalidField("schoolExitDate", enrollmentRecord.getSchoolEntryDateStr(), true,
						" is not valid. Date is in future.");
				validationPassed = false;
			}
			List<Long> assessmentPgmIds = studentAssessmentProgramMapper.getStudentAssessPgmIds(enrollmentRecord.getStateStudentIdentifier(), stateId);
			List<Integer> stateSupportExitCodes = studentService.getStateSpecificExitCodesForKids(stateId, assessmentPgmIds, 
					userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());
			
			String  exitReason = enrollmentRecord.getExitWithdrawalType();
			String exitReasonCode = CEDSCodeMapping.getBycedsCode(exitReason).getEpCode();
			if(StringUtils.isBlank(exitReason)){
				enrollmentRecord.addInvalidField("exitReason", enrollmentRecord.getExitWithdrawalType(), true, " is mandatory");
				validationPassed = false;
			} else if(!stateSupportExitCodes.contains(Integer.valueOf(exitReasonCode))){
				enrollmentRecord.addInvalidField("exitReason", enrollmentRecord.getExitWithdrawalType(), true, " This record is rejected because exit code is not valid in your state.");
				validationPassed = false;
			}else {
				String epCode = CEDSCodeMapping.getBycedsCode(exitReason).getEpCode();
				if(StringUtils.isBlank(epCode) || "CANCEL".equals(epCode)){
					enrollmentRecord.addInvalidField("exitReason", enrollmentRecord.getExitWithdrawalType(), true, " is not valid");
					validationPassed = false;
				} else {
					enrollmentRecord.setExitWithdrawalType(epCode);
				}
			}

			List<Organization> residentDistOrg = null;
			residentDistOrg = organizationService.getDistrictInState(enrollmentRecord.getResidenceDistrictIdentifier(),
					currentOrgId);
			if (residentDistOrg == null || residentDistOrg.isEmpty()) {

				enrollmentRecord.addInvalidField("residenceDistrictIdentifier",
						enrollmentRecord.getResidenceDistrictIdentifier(), true, " not found.");
				validationPassed = false;
			}

			
			if(!enrollmentRecord.getStateStudentIdentifier().trim().isEmpty() ){

				String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
				int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
				if(enrollmentRecord.getStateStudentIdentifier().trim().length()>allowedLength){
	    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
	    			String errorMessage = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
					enrollmentRecord.addInvalidField("stateStudentIdentifier", enrollmentRecord.getStateStudentIdentifier(), true, 
							errorMessage);
					validationPassed = false;
				}
			}
			
			Organization attendanceSchool = null;
			attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
					.getOrganization(enrollmentRecord.getAttendanceSchoolProgramIdentifier());
			OrganizationType orgType = null;
			if (attendanceSchool == null) {
				enrollmentRecord.addInvalidField("attendanceSchoolProgramIdentifier",
						enrollmentRecord.getAttendanceSchoolProgramIdentifier(), true, " not found.");
				validationPassed = false;
			} else {
				orgType = organizationTypeService.get(attendanceSchool.getOrganizationType().getOrganizationTypeId());
				if (orgType != null && !"SCH".equals(orgType.getTypeCode())) {
					enrollmentRecord.addInvalidField("attendanceSchoolProgramIdentifier",
							enrollmentRecord.getAttendanceSchoolProgramIdentifier(), true, " is not a school.");
					validationPassed = false;
				}
				Long contractingOrgSchoolYear = userDetails.getUser().getContractingOrganization()
						.getCurrentSchoolYear();
				if ("UNENRL_XML_RECORD_TYPE".equals(uploadTypeCode)) {
            		contractingOrgSchoolYear = Long.valueOf(enrollmentDao.getContractingOrgSchoolYear(attendanceSchool.getId()));
            	}
				enrollmentRecord.setAttendanceSchoolId(attendanceSchool.getId());
				int enrollmentCurrentSchoolYear = enrollmentRecord.getCurrentSchoolYear();
				if (contractingOrgSchoolYear != enrollmentCurrentSchoolYear) {
					enrollmentRecord.addInvalidField("currentSchoolYear",
							enrollmentRecord.getCurrentSchoolYear() + ParsingConstants.BLANK, true, " is invalid.");
					validationPassed = false;
				}

				OrganizationTreeDetail orgDetail = organizationService
						.getOrganizationDetailBySchoolId(attendanceSchool.getId());
				if ((orgDetail != null) && (!StringUtils.equals(orgDetail.getDistrictDisplayIdentifier(),
						enrollmentRecord.getResidenceDistrictIdentifier()))) {
					enrollmentRecord.addInvalidField("residenceDistrictIdentifier",
							enrollmentRecord.getResidenceDistrictIdentifier(), true,
							" does not match the district of the attendance school "
									+ enrollmentRecord.getAttendanceSchoolProgramIdentifier() + ".");
					validationPassed = false;
				}

			}

			OrganizationTreeDetail aypSchool = null;
			if (!enrollmentRecord.getAypSchoolIdentifier().trim().isEmpty()) {
				aypSchool = organizationService.getSchoolInState(enrollmentRecord.getAypSchoolIdentifier(),
						userDetails.getUser().getContractingOrgId());

				if (aypSchool == null) {
					enrollmentRecord.addInvalidField("aypSchoolIdentifier", enrollmentRecord.getAypSchoolIdentifier(),
							true, " is not a school.");
					validationPassed = false;
				} else if (aypSchool != null) {
					if (validationPassed) {
						enrollmentRecord.setAypSchoolId(aypSchool.getSchoolId());
					}
				}
			}

		}
		

		if (!validationPassed) {
			for (InValidDetail inValidDetail : enrollmentRecord.getInValidDetails()) {
				String errMsg = new StringBuilder(stateStudentIdent).append("###")
						.append(inValidDetail.getInValidMessage()).toString();

				String fieldName = inValidDetail.getActualFieldName();
				if ("residenceDistrictIdentifier".equalsIgnoreCase(fieldName))
					errMsg = errMsg.replace("Residence", "Attendance");

				validationErrors.rejectValue(fieldName, "",
						new String[] { lineNumber, mappedFieldNames.get(fieldName) }, errMsg);
			}
		} else {
			enrollmentRecord.setCreatedUser(userDetails.getUserId());
			enrollmentRecord.setModifiedUser(userDetails.getUserId());
			enrollmentService.processExit(enrollmentRecord.getAttendanceSchoolProgramIdentifier(),
					contractingOrganizationTree, stateStudentIdent, enrollmentRecord.getExitWithdrawalType(),
					enrollmentRecord.getExitWithdrawalDate(), enrollmentRecord.getCurrentSchoolYear(),
					userDetails.getUser(), enrollmentRecord.getAttendanceSchoolId());
		}

		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", enrollmentRecord);
		logger.debug("Complete validation completed.");
		return customValidationResults;
	}

	@Override
	public Map<String, Object> customValidationForAlert(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {

		logger.debug(" unenrollment upload custom validation For Alert Message");
		EnrollmentRecord enrollmentRecord = (EnrollmentRecord) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();

		String lineNumber = enrollmentRecord.getLineNumber();

		UserDetailImpl userDetails = (UserDetailImpl) params.get("currentUser");

		boolean validationPassed = true;

		Long stateId = userDetails.getUser().getOrganization().getId();

		String stateStudentIdent = enrollmentRecord.getStudent().getStateStudentIdentifier();

		List<EnrollmentsOrganizationInfo> enrollmentsOrganizations = studentService.getOrganizationsByStateStudentId(
				enrollmentRecord.getStateStudentIdentifier(), stateId, enrollmentRecord.getCurrentSchoolYear());

		ContractingOrganizationTree contractingOrganizationTree = (ContractingOrganizationTree) params
				.get("contractingOrganizationTree");

		Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
				.getOrganization(enrollmentRecord.getAttendanceSchoolProgramIdentifier());

		List<Organization> parents = organizationService.getImmediateParents(attendanceSchool.getId());

		enrollmentRecord.setAttendanceSchoolId(attendanceSchool.getId());
		String enrollMessage = "";
		if (enrollmentsOrganizations.size() > 0) {
			for (EnrollmentsOrganizationInfo enrollmentsOrganizationInfo : enrollmentsOrganizations) {
				if (enrollmentRecord.getAttendanceSchoolId() != enrollmentsOrganizationInfo.getAttendanceSchoolId()) {
					validationPassed = false;
					enrollMessage = enrollMessage + enrollmentsOrganizationInfo.getSchoolName() + " in "
							+ enrollmentsOrganizationInfo.getDistrictName() + ", ";
				}

			}
			enrollMessage = enrollMessage + attendanceSchool.getOrganizationName() + " in "
					+ parents.get(0).getOrganizationName();
			enrollmentRecord.addInvalidField("stateStudentIdentifier", enrollmentRecord.getStateStudentIdentifier(),
					true, "Student " + enrollmentRecord.getLegalFirstName() + ", " + enrollmentRecord.getLegalLastName()
							+ " is now enrolled in multiple schools for the current year : " + enrollMessage);
		}
		if (!validationPassed) {
			for (InValidDetail inValidDetail : enrollmentRecord.getInValidDetails()) {
				String errMsg = new StringBuilder(stateStudentIdent).append("###")
						.append(inValidDetail.getAlertMessage()).toString();
				String fieldName = inValidDetail.getActualFieldName();
				if ("residenceDistrictIdentifier".equalsIgnoreCase(fieldName))
					errMsg = errMsg.replace("Residence", "Attendance");

				validationErrors.rejectValue(fieldName, "",
						new String[] { lineNumber, mappedFieldNames.get(fieldName) }, errMsg);
			}
		}

		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", enrollmentRecord);
		return customValidationResults;
	}
}
