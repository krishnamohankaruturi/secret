package edu.ku.cete.service.impl.report;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.SFTPUtil;
import edu.ku.cete.web.OrganizationReportsImportDTO;
import edu.ku.cete.web.ReportsImportDTO;
import edu.ku.cete.web.StudentReportsImportDTO;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.sftp.SFTPException;

@Service
public class ExternalReportImportCustomValidationServiceImpl implements BatchUploadCustomValidationService {
	final static Log logger = LogFactory.getLog(ExternalReportImportCustomValidationServiceImpl.class);
	
	@Autowired
	private OrganizationService orgService;
	@Autowired
	private UserService userService;
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	@Autowired
	private GradeCourseService gradeCourseService;
	@Autowired
	private ContentAreaService contentAreaService;
	@Autowired
	private StudentService studentService;
	
	@Value("${report.import.ftp.directory}")
	private String reportImportFtpDirectory;
	
	@Value("${report.filestore.directory}")
	private String reportFileStoreDirectory;
	
	@Value("${print.test.file.path}")
	private String REPORT_TOP_PATH;
	
	@Autowired
	private SFTPUtil sftpUtil;	
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Started custom validation for External Report Import Batch Upload");
		
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long uploadedUserId = (Long) params.get("uploadedUserId");
		String uploadTypeCode = (String) params.get("uploadTypeCode");
		Long selectedAssessmentProgramId = (Long) params.get("assessmentProgramIdOnUI");
		if (rowData instanceof OrganizationReportsImportDTO){
			OrganizationReportsImportDTO organizationReportsDTO = (OrganizationReportsImportDTO) rowData;
			String lineNumber = organizationReportsDTO.getLineNumber();
			String reportType = organizationReportsDTO.getReportType();
			String filename = organizationReportsDTO.getFilename();
			String reportTypeErrMsg ="";

			AssessmentProgram selectedAP = assessmentProgramService.findByAssessmentProgramId(selectedAssessmentProgramId);
			if(!organizationReportsDTO.getAssessmentProgram().equalsIgnoreCase(selectedAP.getAbbreviatedname())){
				String errMsg = "Must be assessment program specified on upload page. "+selectedAP.getAbbreviatedname()+" was selected and this line has "+organizationReportsDTO.getAssessmentProgram()+".";
				logger.debug(errMsg);
				validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
				customValidationResults.put("rowDataObject", organizationReportsDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());
				return customValidationResults;
			}
			boolean invalidReportType = false;
			if (uploadTypeCode.equals("CLASSROOM_REPORTS_IMPORT") && !reportType.toLowerCase().equals("classroom")){
				reportTypeErrMsg = "Selected file type 'Classroom Reports Import' does not match the report type column '"+reportType+"'.";
				invalidReportType = true;
			} else if (uploadTypeCode.equals("CLASSROOM_CSV_REPORTS_IMPORT") && !reportType.toLowerCase().equals("classroom_csv")){
				reportTypeErrMsg = "Selected file type 'Classroom CSV Reports Import' does not match the report type column '"+reportType+"'.";
				invalidReportType = true;
			} else if (uploadTypeCode.equals("SCHOOL_REPORTS_IMPORT") && !reportType.toLowerCase().equals("school")){
				reportTypeErrMsg = "Selected file type 'School Reports Import' does not match the report type column '"+reportType+"'.";
				invalidReportType = true;
			} else if (uploadTypeCode.equals("SCHOOL_CSV_REPORTS_IMPORT") && !reportType.toLowerCase().equals("school_csv")){
				reportTypeErrMsg = "Selected file type 'School CSV Reports Import' does not match the report type column '"+reportType+"'.";
				invalidReportType = true;
			}
			if(invalidReportType){
				logger.debug(reportTypeErrMsg);
				validationErrors.rejectValue("reportType", "", new String[]{lineNumber, mappedFieldNames.get("reportType")}, reportTypeErrMsg);
				customValidationResults.put("rowDataObject", organizationReportsDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());
				return customValidationResults;
			}
			if (reportType.toLowerCase().equals("classroom")
					|| reportType.toLowerCase().equals("school")){
				if (!filename.toLowerCase().contains(".pdf")){
					String errMsg = "Filename is invalid - only pdf is valid for this report type. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("filename", "", new String[]{lineNumber, mappedFieldNames.get("filename")}, errMsg);
				}
				
			}
			// validate filename ends in pdf or csv 
			if (reportType.toLowerCase().equals("classroom_csv")
					|| reportType.toLowerCase().equals("school_csv")){
				if (!filename.toLowerCase().contains(".csv")){
					String errMsg = "Filename is invalid - only csv is valid for this report type. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("filename", "", new String[]{lineNumber, mappedFieldNames.get("filename")}, errMsg);
				}
			}
			// validate org info
			// validate state exists by the identifier
			String state = organizationReportsDTO.getState();
			Organization stateOrg = orgService.getByDisplayIdentifierAndType(state, "ST");
			if (stateOrg == null){
				String errMsg = "State is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("state", "", new String[]{lineNumber, mappedFieldNames.get("state")}, errMsg);
				customValidationResults.put("rowDataObject", organizationReportsDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());

				logger.debug("State validation failed.");
				return customValidationResults;
			}else{
				organizationReportsDTO.setStateId(stateOrg.getId());
			}
			// validate district exists by the identifier
			String district = organizationReportsDTO.getDistrict();
			List<Organization> districtOrgs = orgService.getByDisplayIdentifierAndParent_ActiveOrInactive(district, stateOrg.getId());
			Organization districtOrg = null;
			if (districtOrgs == null || districtOrgs.size() == 0){
				String errMsg = "District is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("district", "", new String[]{lineNumber, mappedFieldNames.get("district")}, errMsg);
				customValidationResults.put("rowDataObject", organizationReportsDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());

				logger.debug("District validation failed.");
				return customValidationResults;
			}else{
				districtOrg = getOrg(districtOrgs);
				organizationReportsDTO.setCurrentDistrictName(districtOrg.getOrganizationName());
			}
			// validate school exists by the identifier
			String school = organizationReportsDTO.getSchool();
			List<Organization> schoolOrgs= orgService.getByDisplayIdentifierAndParent_ActiveOrInactive(school, districtOrg.getId());
			Organization schoolOrg = null;
			if (schoolOrgs == null || schoolOrgs.size() == 0){
				String errMsg = "School is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("school", "", new String[]{lineNumber, mappedFieldNames.get("school")}, errMsg);
			}else{
				schoolOrg = getOrg(schoolOrgs);
				organizationReportsDTO.setOrganizationId(schoolOrg.getId());
				organizationReportsDTO.setCurrentSchoolName(schoolOrg.getOrganizationName());
			}
			// validate assessmentprogram exists by the abbreviated name
			String assessmentProgram = organizationReportsDTO.getAssessmentProgram();
			AssessmentProgram ap = assessmentProgramService.findByAbbreviatedName(assessmentProgram);
			if (ap == null){
				String errMsg = "Assessment program is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			}else{
				organizationReportsDTO.setAssessmentProgramId(ap.getId());
			}
			// if CPASS validate the subject and grade exist by code for classroom and school reports
			String subject = organizationReportsDTO.getSubject();
			String grade = organizationReportsDTO.getGrade();
			ContentArea ca = null;
			if (assessmentProgram.toUpperCase().equals("CPASS")){
				if (!StringUtils.isBlank(subject)){
					ca = contentAreaService.findByAbbreviatedName(subject);
					if (ca == null){
						String errMsg = "Subject is invalid. ";
						logger.debug(errMsg);
						validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
						customValidationResults.put("rowDataObject", organizationReportsDTO);
						customValidationResults.put("errors", validationErrors.getAllErrors());

						logger.debug("Content area validation failed.");
						return customValidationResults;
					}else{
						organizationReportsDTO.setContentAreaId(ca.getId());
					}
				}else{
					String errMsg = "Subject is empty. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
				}
				if (!StringUtils.isBlank(grade)){
					GradeCourse gc = gradeCourseService.selectGradeByAbbreviatedNameAndContentAreaId(grade, ca.getId());
					if (gc == null){
						String errMsg = "Grade is invalid. ";
						logger.debug(errMsg);
						validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
					}else{
						organizationReportsDTO.setGradeId(gc.getId());
					}
				}else{
					String errMsg = "Grade is empty. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
				}
			}else{
				organizationReportsDTO.setGrade(null);
				organizationReportsDTO.setSubject(null);
			}
			// validate school year
			Long schoolYear = organizationReportsDTO.getSchoolYear();
			//     validate the org report year is populated
			if (stateOrg.getReportYear() == 0){
				String errMsg = "Organization report year is not set. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("schoolYear", "", new String[]{lineNumber, mappedFieldNames.get("schoolYear")}, errMsg);
				customValidationResults.put("rowDataObject", organizationReportsDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());

				logger.debug("State report year is not set.");
				return customValidationResults;
			}
			// validate this year matches the state report year
			Integer reportYear = stateOrg.getReportYear();
			if (!schoolYear.equals(reportYear.longValue())){
				String errMsg = "School_Year does not match organization report year. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("schoolYear", "", new String[]{lineNumber, mappedFieldNames.get("schoolYear")}, errMsg);
			}
			if (reportType.toLowerCase().startsWith("classroom")){
				// validate the teacher id exists
				Long teacherID = organizationReportsDTO.getTeacherID();
				User teacher = userService.getActiveOrInactiveUser(teacherID);
				if (teacher == null){
					String errMsg = "Teacher_ID does not exist in Educator Portal. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("teacherID", "", new String[]{lineNumber, mappedFieldNames.get("teacherID")}, errMsg);
				}
			}

			String filePath = generateFilePath(organizationReportsDTO);
			String importPath = reportImportFtpDirectory + filePath;
			boolean fileIsValid = false;

			SFTPClient sftpClient = sftpUtil.getSftpClient();
			try {				
				sftpClient.lstat(importPath);
				fileIsValid = true;
			} catch (SFTPException sftpe){
				//validation error because file does not exist
				String errMsg = "Generated file path does not exist on sftp server: "+importPath;
				logger.debug(errMsg);
				validationErrors.rejectValue("filename", "", new String[]{lineNumber, mappedFieldNames.get("filename")}, errMsg);
			} catch (IOException e1) {
				logger.debug("Exception with sftpClient: ", e1);
			}
			
			if (fileIsValid){
				//set file locations for move
				organizationReportsDTO.setSourceFilePath(importPath);
				organizationReportsDTO.setTargetFilePath(REPORT_TOP_PATH + reportFileStoreDirectory + filePath);
				//set file location for storage in db
				if (reportType.toLowerCase().endsWith("_csv")){
					organizationReportsDTO.setCsvFilePath(reportFileStoreDirectory + filePath);
				}else{
					organizationReportsDTO.setPdfFilePath(reportFileStoreDirectory + filePath);
				}
			}
			
			organizationReportsDTO.setBatchReportProcessId(batchUploadId);
			organizationReportsDTO.setCreatedUser(uploadedUserId);
			organizationReportsDTO.setModifiedUser(uploadedUserId);
			customValidationResults.put("rowDataObject", organizationReportsDTO);
			
		} else if (rowData instanceof StudentReportsImportDTO){
			StudentReportsImportDTO studentReportsImportDTO = (StudentReportsImportDTO) rowData;
			String lineNumber = studentReportsImportDTO.getLineNumber();
			String reportType = studentReportsImportDTO.getReportType();
			String filename = studentReportsImportDTO.getFilename();
			String reportTypeErrMsg ="";
			
			AssessmentProgram selectedAP = assessmentProgramService.findByAssessmentProgramId(selectedAssessmentProgramId);
			if(!studentReportsImportDTO.getAssessmentProgram().equalsIgnoreCase(selectedAP.getAbbreviatedname())){
				String errMsg = "Must be assessment program specified on upload page. "+selectedAP.getAbbreviatedname()+" was selected and this line has "+studentReportsImportDTO.getAssessmentProgram()+".";
				logger.debug(errMsg);
				validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
				customValidationResults.put("rowDataObject", studentReportsImportDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());
				return customValidationResults;
			}
			boolean invalidReportType = false;
			if (uploadTypeCode.equals("STUDENT_REPORTS_IMPORT") && !reportType.toLowerCase().equals("student")){
				reportTypeErrMsg = "Selected file type 'Student Reports Import' does not match the report type column '"+reportType+"'.";
				invalidReportType = true;
			} else if (uploadTypeCode.equals("STUDENT_SUMMARY_REPORTS_IMPORT") && !reportType.toLowerCase().equals("studentsummary")){
				reportTypeErrMsg = "Selected file type 'Student Summary Reports Import' does not match the report type column '"+reportType+"'.";
				invalidReportType = true;
			}
			else if (uploadTypeCode.equals("STUDENT_DCPS_REPORTS_IMPORT") && !StringUtils.equalsIgnoreCase(reportType,"StudentDCPS")){
				reportTypeErrMsg = "Selected file type 'Student DCPS Reports Import' does not match the report type column '"+reportType+"'.";
				invalidReportType = true;
			}
			if(invalidReportType){
				logger.debug(reportTypeErrMsg);
				validationErrors.rejectValue("reportType", "", new String[]{lineNumber, mappedFieldNames.get("reportType")}, reportTypeErrMsg);
				customValidationResults.put("rowDataObject", studentReportsImportDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());
				return customValidationResults;
			}
			if (!filename.toLowerCase().contains(".pdf")){
				String errMsg = "Filename is invalid - only pdf is valid for this report type. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("filename", "", new String[]{lineNumber, mappedFieldNames.get("filename")}, errMsg);
			}
			
			// validate org info
			// validate state exists by the identifier
			String state = studentReportsImportDTO.getState();
			Organization stateOrg = orgService.getByDisplayIdentifierAndType(state, "ST");
			if (stateOrg == null){
				String errMsg = "State is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("state", "", new String[]{lineNumber, mappedFieldNames.get("state")}, errMsg);
				customValidationResults.put("rowDataObject", studentReportsImportDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());

				logger.debug("State validation failed.");
				return customValidationResults;
			}else{
				studentReportsImportDTO.setStateId(stateOrg.getId());
			}
			// validate district exists by the identifier
			String district = studentReportsImportDTO.getDistrict();
			List<Organization> districtOrgs = orgService.getByDisplayIdentifierAndParent_ActiveOrInactive(district, stateOrg.getId());
			Organization districtOrg = null;
			if (districtOrgs == null || districtOrgs.size() == 0){
				String errMsg = "District is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("district", "", new String[]{lineNumber, mappedFieldNames.get("district")}, errMsg);
				customValidationResults.put("rowDataObject", studentReportsImportDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());

				logger.debug("District validation failed.");
				return customValidationResults;
			}else{
				districtOrg = getOrg(districtOrgs);
				studentReportsImportDTO.setDistrictId(districtOrg.getId());
				studentReportsImportDTO.setCurrentDistrictName(districtOrg.getOrganizationName());
			}
			// validate school exists by the identifier
			String school = studentReportsImportDTO.getSchool();
			List<Organization> schoolOrgs = orgService.getByDisplayIdentifierAndParent_ActiveOrInactive(school, districtOrg.getId());
			Organization schoolOrg = null;
			if (schoolOrgs == null || schoolOrgs.size() == 0){
				String errMsg = "School is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("school", "", new String[]{lineNumber, mappedFieldNames.get("school")}, errMsg);
			}else{
				schoolOrg = getOrg(schoolOrgs);
				studentReportsImportDTO.setSchoolId(schoolOrg.getId());
				studentReportsImportDTO.setCurrentSchoolName(schoolOrg.getOrganizationName());
			}
			// validate assessmentprogram exists by the abbreviated name
			String assessmentProgram = studentReportsImportDTO.getAssessmentProgram();
			AssessmentProgram ap = assessmentProgramService.findByAbbreviatedName(assessmentProgram);
			if (ap == null){
				String errMsg = "Assessment program is invalid. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")}, errMsg);
			}else{
				studentReportsImportDTO.setAssessmentProgramId(ap.getId());
			}
			String subject = studentReportsImportDTO.getSubject();
			String grade = studentReportsImportDTO.getGrade();
			ContentArea ca = null;
			if (reportType.equalsIgnoreCase("student")){
				if (!StringUtils.isBlank(subject)){
					ca = contentAreaService.findByAbbreviatedName(subject);
					if (ca == null){
						String errMsg = "Subject is invalid. ";
						logger.debug(errMsg);
						validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
						customValidationResults.put("rowDataObject", studentReportsImportDTO);
						customValidationResults.put("errors", validationErrors.getAllErrors());

						logger.debug("Content area validation failed.");
						return customValidationResults;
					}else{
						studentReportsImportDTO.setContentAreaId(ca.getId());
					}
				}else{
					String errMsg = "Subject is empty. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, errMsg);
				}
			}
			if (!StringUtils.isBlank(grade)){
				Long contentAreaId = null;
				if (ca != null){
					contentAreaId = ca.getId();
				}
				GradeCourse gc = gradeCourseService.selectGradeByAbbreviatedNameAndContentAreaId(grade, contentAreaId);
				if (gc == null){
					String errMsg = "Grade is invalid. ";
					logger.debug(errMsg);
					validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
				}else{
					studentReportsImportDTO.setGradeId(gc.getId());
				}
			}else{
				String errMsg = "Grade is empty. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("grade", "", new String[]{lineNumber, mappedFieldNames.get("grade")}, errMsg);
			}
			// validate school year
			Long schoolYear = studentReportsImportDTO.getSchoolYear();
			//     validate the org report year is populated
			if (stateOrg.getReportYear() == 0){
				String errMsg = "Organization report year is not set. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("schoolYear", "", new String[]{lineNumber, mappedFieldNames.get("schoolYear")}, errMsg);
				customValidationResults.put("rowDataObject", studentReportsImportDTO);
				customValidationResults.put("errors", validationErrors.getAllErrors());

				logger.debug("State report year is not set.");
				return customValidationResults;
			}
			// validate this year matches the state report year
			Integer reportYear = stateOrg.getReportYear();
			if (!schoolYear.equals(reportYear.longValue())){
				String errMsg = "School_Year does not match organization report year. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("schoolYear", "", new String[]{lineNumber, mappedFieldNames.get("schoolYear")}, errMsg);
			}

			// validate the student id exists
			Long studentID = studentReportsImportDTO.getStudentId();
			Student student = studentService.getByStudentID(studentID);
			if (student == null){
				String errMsg = "EP_Student_ID does not exist in Educator Portal. ";
				logger.debug(errMsg);
				validationErrors.rejectValue("studentId", "", new String[]{lineNumber, mappedFieldNames.get("studentId")}, errMsg);
			}

			String filePath = generateFilePath(studentReportsImportDTO);
			String importPath = reportImportFtpDirectory + filePath;
			boolean fileIsValid = false;

			SFTPClient sftpClient = sftpUtil.getSftpClient();
			try {
									
				sftpClient.lstat(importPath);
				fileIsValid = true;
			} catch (SFTPException sftpe){
				//validation error because file does not exist
				String errMsg = "Generated file path does not exist on sftp server: "+importPath;
				logger.debug(errMsg);
				validationErrors.rejectValue("filename", "", new String[]{lineNumber, mappedFieldNames.get("filename")}, errMsg);
			} catch (IOException e1) {
				logger.debug("Exception with sftpClient: ", e1);

			}
			
			if (fileIsValid){
				//set file locations for move
				studentReportsImportDTO.setSourceFilePath(importPath);
				studentReportsImportDTO.setTargetFilePath(REPORT_TOP_PATH + reportFileStoreDirectory + filePath);
				studentReportsImportDTO.setPdfFilePath(reportFileStoreDirectory + filePath);
			}
			studentReportsImportDTO.setBatchReportProcessId(batchUploadId);
			studentReportsImportDTO.setCreatedUser(uploadedUserId);
			studentReportsImportDTO.setModifiedUser(uploadedUserId);
			customValidationResults.put("rowDataObject", studentReportsImportDTO);
		}
		customValidationResults.put("errors", validationErrors.getAllErrors());

		logger.debug("Completed validation.");
		
		return customValidationResults;
	}

	private String generateFilePath(ReportsImportDTO dto) {
		StringBuilder filePath = new StringBuilder();
		String reportType = dto.getReportType();
		String assessmentProgram = dto.getAssessmentProgram();
		filePath.append(assessmentProgram);
		filePath.append("/");
		filePath.append(dto.getSchoolYear());
		filePath.append("/");
		if(reportType.toLowerCase().startsWith("classroom")){
			filePath.append("Classroom");
		} else if (reportType.toLowerCase().startsWith("school")){
			filePath.append("School");
		} else if (reportType.equalsIgnoreCase("student")){
			filePath.append("Student");
		} else if (reportType.equalsIgnoreCase("studentsummary")){
			filePath.append("StudentSummary");
		}else if (reportType.equalsIgnoreCase("StudentDCPS")){
			filePath.append("StudentDCPS");
		}
		if (reportType.toLowerCase().endsWith("_csv")){
			filePath.append("_csv");
		}
		filePath.append("/");
		filePath.append(dto.getState());
		filePath.append("/");
		filePath.append(dto.getDistrict());
		if (reportType.equalsIgnoreCase("student") ||
			(reportType.toLowerCase().startsWith("school") && assessmentProgram.equalsIgnoreCase("CPASS"))
			){
			filePath.append("/");
			filePath.append(dto.getSchool());
			filePath.append("/");
			filePath.append(dto.getSubject());
			filePath.append("/");
			filePath.append(dto.getGrade());
		} else if (reportType.toLowerCase().startsWith("classroom")){
			filePath.append("/");
			filePath.append(dto.getSchool());
		} else if (reportType.equalsIgnoreCase("studentsummary")||reportType.equalsIgnoreCase("StudentDCPS")){
			filePath.append("/");
			filePath.append(dto.getSchool());
			filePath.append("/");
			filePath.append(dto.getGrade());
		}
		filePath.append("/");
		filePath.append(dto.getFilename());
		return filePath.toString();
	}	
	
	private Organization getOrg(List<Organization> orgs){
		Organization org = null;
		if (orgs.size() > 1){
			for (Organization d : orgs){
				if (d.getActiveFlag()){
					org = d;
					break;
				}
			}
			if (org == null){
				org = orgs.get(0);
			}
		} else {
			org = orgs.get(0);
		}
		return org;
	}
}
