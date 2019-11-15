package edu.ku.cete.service.impl.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.PNPUploadRecord;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;

@Service
public class PNPUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService {
	final static Log logger = LogFactory.getLog(PNPUploadCustomValidationServiceImpl.class);
	
	@Autowired
	private OrganizationService orgService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private RosterService rosterService;

	@Autowired
	private AppConfigurationService appConfigurationService;
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		PNPUploadRecord pnpRecord = (PNPUploadRecord) rowData;
		
		BatchUpload batchUpload = (BatchUpload) params.get("batchUploadRecord");
		Long stateId = (Long) params.get("stateId");
		Long districtId = (Long) params.get("districtId");
		Long schoolId = (Long) params.get("schoolId");
		
		Long lowestOrgId = ObjectUtils.firstNonNull(schoolId, districtId, stateId);
		Organization org = (Organization) params.get("currentContext");
		Boolean orgIsValidChild = orgService.isChildOf(org.getId(), lowestOrgId);
		if (lowestOrgId.longValue() != org.getId().longValue() && !Boolean.TRUE.equals(orgIsValidChild)) {
			// error, org isn't valid...shouldn't ever happen, but hey, who knows
			logger.error("batch upload " + batchUpload.getId() + ": Uploaded organization (" + lowestOrgId +
					") was not a child of the user's organization (" + org.getId() + ").");
		}
		
		
    	
    	if(!pnpRecord.getStateStudentIdentifier().trim().isEmpty() ){
    		String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
    		int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
    		if(pnpRecord.getStateStudentIdentifier().trim().length()>allowedLength){
    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
    			String errorMessage = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
    			pnpRecord.addInvalidField("stateStudentIdentifier", pnpRecord.getStateStudentIdentifier(), true, 
    					errorMessage);
    		}
		}
		
		
		String ssid = pnpRecord.getStateStudentIdentifier();
		List<Student> students = studentService.getBySsidAndState(ssid, stateId);
		if (CollectionUtils.isEmpty(students)) {
			pnpRecord.addInvalidField("stateStudentIdentifier", pnpRecord.getStateStudentIdentifier(), true, InvalidTypes.STUDENT_NOT_FOUND);
		} else {
			Student student = students.get(0);
			if (!studentService.isStudentInAssessmentProgram(student.getId(), batchUpload.getAssessmentProgramId())) {
				pnpRecord.addInvalidField("stateStudentIdentifier", pnpRecord.getStateStudentIdentifier(), true, InvalidTypes.STUDENT_NOT_IN_PROGRAM);
			} else {
				Organization contractingOrg = orgService.getContractingOrganization(lowestOrgId);
				Long schoolYear = contractingOrg.getCurrentSchoolYear();
				pnpRecord.setStudent(student);
				pnpRecord.setAssessmentProgramId(batchUpload.getAssessmentProgramId());
				// make sure the student is enrolled
				List<Enrollment> enrollments = enrollmentService.getCurrentEnrollmentsByStudentId(student.getId(), lowestOrgId, schoolYear.intValue(),true);
				if (CollectionUtils.isEmpty(enrollments)) {
					pnpRecord.addInvalidField("stateStudentIdentifier", pnpRecord.getStateStudentIdentifier(), true, InvalidTypes.STUDENT_NOT_ENROLLED);
				} else {
					Groups group = groupsService.getGroup(batchUpload.getUploadedUserGroupId());
					boolean canOnlyEditRosteredStudents = Pattern.matches("(?i:teacher|proctor)", group.getGroupName());
					
					if (canOnlyEditRosteredStudents) {
						Long userId = batchUpload.getCreatedUser();
						boolean studentIsRosteredToUser = false;
						for (int e = 0; e < enrollments.size() && !studentIsRosteredToUser; e++) {
							List<Roster> rostersForEnrollment = rosterService.getRostersByEnrollmentId(enrollments.get(e).getId());
							if (CollectionUtils.isNotEmpty(rostersForEnrollment)) {
								for (int r = 0; r < rostersForEnrollment.size() && !studentIsRosteredToUser; r++) {
									studentIsRosteredToUser =
											rostersForEnrollment.get(r).getTeacherId().longValue() == userId.longValue();
								}
							}
						}
						
						if (!studentIsRosteredToUser) {
							pnpRecord.addInvalidField("stateStudentIdentifier", pnpRecord.getStateStudentIdentifier(), true, InvalidTypes.STUDENT_NOT_ROSTERED_TO_USER);
						}
					}
				}
			}
		}
		
		if (CollectionUtils.isNotEmpty(pnpRecord.getInValidDetails())) {
			for (InValidDetail invalidDetail : pnpRecord.getInValidDetails()) {
				String fieldName = invalidDetail.getActualFieldName();
				String errorMessage = new StringBuilder(pnpRecord.getStateStudentIdentifier())
		    			.append("###")
		    			.append(invalidDetail.getInValidMessage())
		    			.toString();
				
				validationErrors.rejectValue(fieldName, "", new String[]{pnpRecord.getLineNumber(), mappedFieldNames.get(fieldName)}, errorMessage);
			}
		}
		
		// having the field specs as a variable in the POJO is kind of hacky and a little less efficient than I'd like...but it works.
		pnpRecord.setFieldSpecs((Map<String, FieldSpecification>) params.get("fieldSpecRecords"));
		pnpRecord.setModifiedUser(batchUpload.getCreatedUser());
		
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", pnpRecord);
		return customValidationResults;
	}

}
