package edu.ku.cete.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.util.CommonConstants;
@Service
public class UploadGrfFileDefaultCustomValidatonServiceImpl implements
		BatchUploadCustomValidationService {
	final static Log logger = LogFactory
			.getLog(UploadGrfFileDefaultCustomValidatonServiceImpl.class);
	@Autowired
	private OrganizationService organizationService; 
	
	@Autowired
    private GradeCourseService gradeCourseService;
	
	@Autowired
	private StudentService studentService; 
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
    /** StudentDao holder. */
    @Autowired
    private StudentDao studentDao;
    
    @Autowired
    private UploadGrfFileService uploadGrfFileService;
    
    @Autowired
    private EmailService emailService;
	
	@Value("${GRF.original.upload}")
	private String OriginalGrfUpload;
	
	@Value("${GRF.minimum.EntryDate}")
	private String minEntryDate;
	
	@Value("${GRF.upload.subject.error.message}")
	private String subjectErrorMsg;
	
	@Value("${GRF.upload.state.error.message}")
	private String stateErrorMsg;
	
	@Value("${GRF.upload.common.error.message}")
	private String commonUplodErrorMsg;
	
	@Value("${GRF.upload.entrydate.error.message}")
	private String entryDateErrorMsg;
	
	@Value("${GRF.upload.uniqueRowIdentifier.duplicate.error.message}")
	private String duplicateUniqueRowIdentifierErrorMsg;
	
	@Value("${GRF.upload.subject.Student.error.message}")
	private String subjectStudentErrorMsg;
	
	@Value("${GRF.upload.studentid.StateStudentId.error.message}")
	private String studentIdSSIDErrorMsg;
	
	@Value("${GRF.upload.StateStudentId.studentid.error.message}")
	private String SSIDstudentIdErrorMsg;
	      
	@Value("${GRF.upload.uniqueRowIdentifier.update.error.message}")
	private String updateUniqueRowIdentifierErrorMsg;	
	
	@Value("${GRF.upload.updated.common.error.message}")
	private String commonUpdateErrorMsg;	
			
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.BatchUploadCustomValidationService#customValidation(org.springframework.validation.BeanPropertyBindingResult, java.lang.Object, java.util.Map, java.util.Map)
	 */
	@Override
	public Map<String, Object> customValidation(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		// TODO Auto-generated method stub
		UploadGrfFile uploadGrfFile = (UploadGrfFile) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long uploadedUserId=(Long) params.get("uploadedUserId");
		Long stateId=(Long) params.get("stateId");
		String grfUploadType = (String)params.get("grfUploadType");
		String lineNumber = uploadGrfFile.getLineNumber();
		
		boolean validationPassed = true;	
		String subject=new String();
		
		if(uploadGrfFile.getSubject()!=null && StringUtils.equalsIgnoreCase(uploadGrfFile.getSubject(), "Math")){
			subject="M";
		}else if(uploadGrfFile.getSubject()!=null){
			subject=uploadGrfFile.getSubject();
		}
		ContentArea subjectAvailable= contentAreaService.findByAbbreviatedName(subject);
		//check the subject in content are with out case sensitive		
		if(!StringUtils.isEmpty(uploadGrfFile.getSubject()) && subjectAvailable==null){
			validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, subjectErrorMsg);
			validationPassed = false;
		}else{
			uploadGrfFile.setSubjectId(subjectAvailable.getId());
			Organization stateDetails=organizationService.getStateByNameAndType(uploadGrfFile.getState(),CommonConstants.ORGANIZATION_STATE_CODE);
			if(stateDetails==null || stateDetails.getId().longValue() != stateId.longValue()) {
				validationErrors.rejectValue("state", "", new String[]{lineNumber, mappedFieldNames.get("state")}, stateErrorMsg);
				validationPassed = false;
			}else{
				uploadGrfFile.setStateCode(stateDetails.getDisplayIdentifier());
				uploadGrfFile.setStateId(stateDetails.getId());
				Organization districtDetails=organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getResidenceDistrictIdentifier(),stateDetails.getId(), CommonConstants.ORGANIZATION_TYPE_ID_5);
				if(districtDetails==null) {
					validationErrors.rejectValue("residenceDistrictIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("residenceDistrictIdentifier")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("residenceDistrictIdentifier")));
					validationPassed = false;
				}else{
					uploadGrfFile.setDistrictId(districtDetails.getId());
					Organization schoolDetails=organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getSchoolIdentifier(),districtDetails.getId(), CommonConstants.ORGANIZATION_TYPE_ID_7);
					if(schoolDetails==null){
						validationErrors.rejectValue("schoolIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("schoolIdentifier")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("schoolIdentifier")));
						validationPassed = false;
					}else{
						uploadGrfFile.setSchoolId(schoolDetails.getId());
						if(StringUtils.isNotBlank(uploadGrfFile.getAttendanceSchoolProgramIdentifier())){
							Organization attendanceSchool = organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getAttendanceSchoolProgramIdentifier(), stateDetails.getId(), CommonConstants.ORGANIZATION_TYPE_ID_7);						
							if(attendanceSchool == null){
								validationErrors.rejectValue("attendanceSchoolProgramIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("attendanceSchoolProgramIdentifier")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("attendanceSchoolProgramIdentifier")));
								validationPassed = false;
							}
						}
						if(StringUtils.isNotBlank(uploadGrfFile.getAypSchoolIdentifier())){
							Organization aypSchool = organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getAypSchoolIdentifier(), stateDetails.getId(), CommonConstants.ORGANIZATION_TYPE_ID_7);							
							if(aypSchool == null){
								validationErrors.rejectValue("aypSchoolIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("aypSchoolIdentifier")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("aypSchoolIdentifier")));
								validationPassed = false;
							}
						}						
					}
				}
			}
		}
		if(validationPassed){
			GradeCourse gradeCourse=new GradeCourse();			
			if(!StringUtils.isEmpty(uploadGrfFile.getSubject())){
				 gradeCourse= gradeCourseService.findByContentAreaAbbreviatedNameAndGradeCourseAbbreviatedName(subject,uploadGrfFile.getCurrentGradelevel());
				 if(gradeCourse == null){
					validationErrors.rejectValue("currentGradelevel", "", new String[]{lineNumber, mappedFieldNames.get("currentGradelevel")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("currentGradelevel")));
					validationPassed = false;
				 }else{
				   uploadGrfFile.setGradeId(gradeCourse.getId());
				 }
			}
			
			//Validate Educator Identifier
			List<User> educators = userService.getByEducatorIdentifierForGRF(uploadGrfFile.getEducatorIdentifier(), uploadGrfFile.getStateId());	
			if(educators.size() == 0){
				validationErrors.rejectValue("educatorIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("educatorIdentifier")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("educatorIdentifier")+" "+uploadGrfFile.getEducatorIdentifier()));
				validationPassed = false;
			}else{
				//this code will be changed later....just setting the given file educator identifier to the new column
				uploadGrfFile.setStrKiteEducatorIdentifier(uploadGrfFile.getKiteEducatorIdentifier());
				uploadGrfFile.setKiteEducatorIdentifier(educators.get(0).getId().toString());
			}
			
			//Validate Gender
			if(!"male".equalsIgnoreCase(uploadGrfFile.getGender()) && !"female".equalsIgnoreCase(uploadGrfFile.getGender())){
				validationErrors.rejectValue("gender", "", new String[]{lineNumber, mappedFieldNames.get("gender")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("gender")));
				validationPassed = false;
			}
			
			//validate school entry date, district entry date & state entry date.		  
			try {
				Date date = new SimpleDateFormat("MM/dd/yyyy").parse(minEntryDate);
				if(date.after(uploadGrfFile.getSchoolEntryDate())){
					validationErrors.rejectValue("schoolEntryDate", "", new String[]{lineNumber, mappedFieldNames.get("schoolEntryDate")}, StringUtils.replace(entryDateErrorMsg, "$", mappedFieldNames.get("schoolEntryDate")));
					validationPassed = false;
				 }else if (uploadGrfFile.getDistrictEntryDate() != null && date.after(uploadGrfFile.getDistrictEntryDate())){					
					validationErrors.rejectValue("districtEntryDate", "", new String[]{lineNumber, mappedFieldNames.get("districtEntryDate")}, StringUtils.replace(entryDateErrorMsg, "$", mappedFieldNames.get("districtEntryDate")));
					validationPassed = false;
				 }else if (uploadGrfFile.getStateEntryDate() != null && date.after(uploadGrfFile.getStateEntryDate())){					
					validationErrors.rejectValue("stateEntryDate", "", new String[]{lineNumber, mappedFieldNames.get("stateEntryDate")}, StringUtils.replace(entryDateErrorMsg, "$", mappedFieldNames.get("stateEntryDate")));
					validationPassed = false;
				 }
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		
			//validate student ID
			Student uploadedStudent = studentService.getByStudentID(uploadGrfFile.getStudentId());
			if(uploadedStudent != null){		
				//Validate combination of student and subject
				List<UploadGrfFile> records = uploadGrfFileService.getGrfStudentRecord(uploadGrfFile.getStudentId(), (Long) params.get("reportYear"), null, 
						null, uploadGrfFile.getSubjectId(), null, batchUploadId);			
				if(records.size() > 0){
					validationErrors.rejectValue("subject", "", new String[]{lineNumber, mappedFieldNames.get("subject")}, subjectStudentErrorMsg);
					validationPassed = false;
				}else{
					//Upload file must contains unique SSID for studentId across all the subject
					List<String> availableSSID = uploadGrfFileService.getAvailableSSIDByStudentId(uploadGrfFile.getStudentId(), batchUploadId, (Long) params.get("reportYear"));
					if(availableSSID.size() > 0 && !StringUtils.equals(availableSSID.get(0), uploadGrfFile.getStateStudentIdentifier())){
						validationErrors.rejectValue("studentId", "", new String[]{lineNumber, mappedFieldNames.get("studentId")}, studentIdSSIDErrorMsg);
						validationPassed = false;
					}					
					
					//Validate studentID and state student identifier
					List<Long> studentIds = uploadGrfFileService.getStudentIdsFromGRFBySSID(uploadGrfFile.getStateStudentIdentifier(), (Long) params.get("reportYear"), stateId, null);		
					if(studentIds.size() >= 1 && !studentIds.contains(uploadGrfFile.getStudentId())){
						validationErrors.rejectValue("stateStudentIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("stateStudentIdentifier")}, SSIDstudentIdErrorMsg);
						validationPassed = false;
					}
					
					//Unique_row_identifier can not be duplicated
					Integer count = uploadGrfFileService.countByUniqueRowIdentifier(uploadGrfFile.getExternalUniqueRowIdentifier(), batchUploadId);
					if(count > 0){
						validationErrors.rejectValue("externalUniqueRowIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("externalUniqueRowIdentifier")}, duplicateUniqueRowIdentifierErrorMsg);
						validationPassed = false;
					}
					
				}
			}else{
				validationErrors.rejectValue("studentId", "", new String[]{lineNumber, mappedFieldNames.get("studentId")}, StringUtils.replace(commonUplodErrorMsg, "$", mappedFieldNames.get("studentId")));
				validationPassed = false;
			}
			
	/*		//Validate special character for student first name and last name		
			Pattern regexPattern = Pattern.compile("^[A-z0-9-_.]++$");
			Matcher regexMatcher = regexPattern.matcher(uploadGrfFile.getLegalFirstName());
			if (!regexMatcher.matches()) {
				String errMsg = "Student_Legal_First_Name contains special characters";
				logger.debug(errMsg);
				validationErrors.rejectValue("legalFirstName", "", new String[]{lineNumber, mappedFieldNames.get("legalFirstName")}, errMsg);
				validationPassed = false;
			}
			
			regexMatcher = regexPattern.matcher(uploadGrfFile.getLegalLastName());
			if (!regexMatcher.matches()) {
				String errMsg = "Student_Legal_Last_Name contains special characters";
				logger.debug(errMsg);
				validationErrors.rejectValue("legalLastName", "", new String[]{lineNumber, mappedFieldNames.get("legalLastName")}, errMsg);
				validationPassed = false;
			}*/
			uploadGrfFile.setOriginal(true);
		    if(validationPassed && !OriginalGrfUpload.equalsIgnoreCase(grfUploadType)){//For updated GRF upload
		    	uploadGrfFile.setOriginal(false);
				List<UploadGrfFile> originalRecords = uploadGrfFileService.getGrfStudentRecord(uploadGrfFile.getStudentId(), (Long) params.get("reportYear"), null, 
						null, uploadGrfFile.getSubjectId(), 0l, null); //0 = original GRF
				if(originalRecords.size() > 0){
					//validate externalUniqueRowIdentifier
					if(originalRecords.get(0).getExternalUniqueRowIdentifier().longValue() != uploadGrfFile.getExternalUniqueRowIdentifier()){
						validationErrors.rejectValue("externalUniqueRowIdentifier", "", new String[]{lineNumber, mappedFieldNames.get("externalUniqueRowIdentifier")}, updateUniqueRowIdentifierErrorMsg);
						validationPassed = false;
					}else{
						//validate Final_Band	
						if(!ObjectUtils.equals(originalRecords.get(0).getFinalBand(), uploadGrfFile.getFinalBand())){
							validationErrors.rejectValue("finalBand", "", new String[]{lineNumber, mappedFieldNames.get("finalBand")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("finalBand")));
							validationPassed = false;
						}
						
						//validate SGP
						if(!ObjectUtils.equals(originalRecords.get(0).getSgp(), uploadGrfFile.getSgp())){
							validationErrors.rejectValue("sgp", "", new String[]{lineNumber, mappedFieldNames.get("sgp")},  StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("sgp")));
							validationPassed = false;
						}
						
						//validate NY_performance level
						if(!ObjectUtils.equals(originalRecords.get(0).getNyPerformanceLevel(), uploadGrfFile.getNyPerformanceLevel())){
							validationErrors.rejectValue("nyPerformanceLevel", "", new String[]{lineNumber, mappedFieldNames.get("nyPerformanceLevel")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("nyPerformanceLevel")));
							validationPassed = false;
						}
						
						//validate Total_Linkage_Levels_Mastered
						if(!ObjectUtils.equals(originalRecords.get(0).getTotalLinkageLevelsMastered(), uploadGrfFile.getTotalLinkageLevelsMastered())){
							validationErrors.rejectValue("totalLinkageLevelsMastered", "", new String[]{lineNumber, mappedFieldNames.get("totalLinkageLevelsMastered")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("totalLinkageLevelsMastered")));
							validationPassed = false;
						}
						
						//validate IOWA_Total_Linkage_Levels_Mastered
						if(!ObjectUtils.equals(originalRecords.get(0).getIowaLinkageLevelsMastered(), uploadGrfFile.getIowaLinkageLevelsMastered())){
							validationErrors.rejectValue("iowaLinkageLevelsMastered", "", new String[]{lineNumber, mappedFieldNames.get("iowaLinkageLevelsMastered")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("iowaLinkageLevelsMastered")));
							validationPassed = false;
						}
						
						//get last version grade and check with current grade, so every time mail will not be triggered
						//Long gradeId = uploadGrfFileService.getCurrentGradeFromGRF(uploadGrfFile.getStudentId(), (Long) params.get("reportYear"), uploadGrfFile.getSubjectId());
						
						//Grade change validation
						if(originalRecords.get(0).getGradeId().longValue() != uploadGrfFile.getGradeId().longValue()){//Grade change happened
							//Set EE 1-26 and performance value to 9 
							uploadGrfFile.setPerformanceLevel(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe1(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe2(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe3(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe4(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe5(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe6(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe7(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe8(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe9(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe10(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe11(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe12(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe13(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe14(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe15(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe16(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe17(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe18(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe19(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe20(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe21(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe22(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe23(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe24(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe25(CommonConstants.GRF_LEVEL_9);
							uploadGrfFile.setEe26(CommonConstants.GRF_LEVEL_9);
							
							/*if(gradeId != null && gradeId.longValue() != uploadGrfFile.getGradeId().longValue())
							   uploadGrfFile.setGradeChange(true);
							else
							   uploadGrfFile.setGradeChange(false);*/
							
						}else{//Grade not changed, so need to validate EE 1-26 value
							uploadGrfFile.setGradeChange(false);
							
							//validate performance level
							if(originalRecords.get(0).getPerformanceLevel().longValue() != uploadGrfFile.getPerformanceLevel().longValue()){
								validationErrors.rejectValue("performanceLevel", "", new String[]{lineNumber, mappedFieldNames.get("performanceLevel")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("performanceLevel")));
								validationPassed = false;
							}
							
							//validate EE_1
							if(!ObjectUtils.equals(originalRecords.get(0).getEe1(), uploadGrfFile.getEe1())){
								validationErrors.rejectValue("ee1", "", new String[]{lineNumber, mappedFieldNames.get("ee1")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee1")));
								validationPassed = false;
							}
							
							//validate EE_2
							if(!ObjectUtils.equals(originalRecords.get(0).getEe2(), uploadGrfFile.getEe2())){
								validationErrors.rejectValue("ee2", "", new String[]{lineNumber, mappedFieldNames.get("ee2")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee2")));
								validationPassed = false;
							}
							
							//validate EE_3
							if(!ObjectUtils.equals(originalRecords.get(0).getEe3(), uploadGrfFile.getEe3())){
								validationErrors.rejectValue("ee3", "", new String[]{lineNumber, mappedFieldNames.get("ee3")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee3")));
								validationPassed = false;
							}
							
							//validate EE_4
							if(!ObjectUtils.equals(originalRecords.get(0).getEe4(), uploadGrfFile.getEe4())){
								validationErrors.rejectValue("ee4", "", new String[]{lineNumber, mappedFieldNames.get("ee4")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee4")));
								validationPassed = false;
							}
							
							//validate EE_5
							if(!ObjectUtils.equals(originalRecords.get(0).getEe5(), uploadGrfFile.getEe5())){
								validationErrors.rejectValue("ee5", "", new String[]{lineNumber, mappedFieldNames.get("ee5")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee5")));
								validationPassed = false;
							}
							
							//validate EE_6
							if(!ObjectUtils.equals(originalRecords.get(0).getEe6(), uploadGrfFile.getEe6())){
								validationErrors.rejectValue("ee6", "", new String[]{lineNumber, mappedFieldNames.get("ee6")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee6")));
								validationPassed = false;
							}
							
							
							//validate EE_7
							if(!ObjectUtils.equals(originalRecords.get(0).getEe7(), uploadGrfFile.getEe7())){
								validationErrors.rejectValue("ee7", "", new String[]{lineNumber, mappedFieldNames.get("ee7")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee7")));
								validationPassed = false;
							}
							
							//validate EE_8
							if(!ObjectUtils.equals(originalRecords.get(0).getEe8(), uploadGrfFile.getEe8())){
								validationErrors.rejectValue("ee8", "", new String[]{lineNumber, mappedFieldNames.get("ee8")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee8")));
								validationPassed = false;
							}
							
							//validate EE_9
							if(!ObjectUtils.equals(originalRecords.get(0).getEe9(), uploadGrfFile.getEe9())){
								validationErrors.rejectValue("ee9", "", new String[]{lineNumber, mappedFieldNames.get("ee9")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee9")));
								validationPassed = false;
							}
							
							//validate EE_10
							if(!ObjectUtils.equals(originalRecords.get(0).getEe10(), uploadGrfFile.getEe10())){
								validationErrors.rejectValue("ee10", "", new String[]{lineNumber, mappedFieldNames.get("ee10")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee10")));
								validationPassed = false;
							}
							
							//validate EE_11
							if(!ObjectUtils.equals(originalRecords.get(0).getEe11(), uploadGrfFile.getEe11())){
								validationErrors.rejectValue("ee11", "", new String[]{lineNumber, mappedFieldNames.get("ee11")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee11")));
								validationPassed = false;
							}
							
							//validate EE_12
							if(!ObjectUtils.equals(originalRecords.get(0).getEe12(), uploadGrfFile.getEe12())){
								validationErrors.rejectValue("ee12", "", new String[]{lineNumber, mappedFieldNames.get("ee12")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee12")));
								validationPassed = false;
							}
							
							//validate EE_13
							if(!ObjectUtils.equals(originalRecords.get(0).getEe13(), uploadGrfFile.getEe13())){
								validationErrors.rejectValue("ee13", "", new String[]{lineNumber, mappedFieldNames.get("ee13")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee13")));
								validationPassed = false;
							}
							
							//validate EE_14
							if(!ObjectUtils.equals(originalRecords.get(0).getEe14(), uploadGrfFile.getEe14())){
								validationErrors.rejectValue("ee14", "", new String[]{lineNumber, mappedFieldNames.get("ee14")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee14")));
								validationPassed = false;
							}
							
							//validate EE_15
							if(!ObjectUtils.equals(originalRecords.get(0).getEe15(), uploadGrfFile.getEe15())){
								validationErrors.rejectValue("ee15", "", new String[]{lineNumber, mappedFieldNames.get("ee15")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee15")));
								validationPassed = false;
							}
							
							//validate EE_16
							if(!ObjectUtils.equals(originalRecords.get(0).getEe16(), uploadGrfFile.getEe16())){
								validationErrors.rejectValue("ee16", "", new String[]{lineNumber, mappedFieldNames.get("ee16")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee16")));
								validationPassed = false;
							}
							
							//validate EE_17
							if(!ObjectUtils.equals(originalRecords.get(0).getEe17(), uploadGrfFile.getEe17())){
								validationErrors.rejectValue("ee17", "", new String[]{lineNumber, mappedFieldNames.get("ee17")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee17")));
								validationPassed = false;
							}
							
							//validate EE_18
							if(!ObjectUtils.equals(originalRecords.get(0).getEe18(), uploadGrfFile.getEe18())){
								validationErrors.rejectValue("ee18", "", new String[]{lineNumber, mappedFieldNames.get("ee18")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee18")));
								validationPassed = false;
							}
							
							//validate EE_19
							if(!ObjectUtils.equals(originalRecords.get(0).getEe19(), uploadGrfFile.getEe19())){
								validationErrors.rejectValue("ee19", "", new String[]{lineNumber, mappedFieldNames.get("ee19")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee19")));
								validationPassed = false;
							}
							
							//validate EE_20
							if(!ObjectUtils.equals(originalRecords.get(0).getEe20(), uploadGrfFile.getEe20())){
								validationErrors.rejectValue("ee20", "", new String[]{lineNumber, mappedFieldNames.get("ee20")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee20")));
								validationPassed = false;
							}
							
							//validate EE_21
							if(!ObjectUtils.equals(originalRecords.get(0).getEe21(), uploadGrfFile.getEe21())){
								validationErrors.rejectValue("ee21", "", new String[]{lineNumber, mappedFieldNames.get("ee21")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee21")));
								validationPassed = false;
							}
							
							//validate EE_22
							if(!ObjectUtils.equals(originalRecords.get(0).getEe22(), uploadGrfFile.getEe22())){
								validationErrors.rejectValue("ee22", "", new String[]{lineNumber, mappedFieldNames.get("ee22")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee22")));
								validationPassed = false;
							}
							
							//validate EE_23
							if(!ObjectUtils.equals(originalRecords.get(0).getEe23(), uploadGrfFile.getEe23())){
								validationErrors.rejectValue("ee23", "", new String[]{lineNumber, mappedFieldNames.get("ee23")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee23")));
								validationPassed = false;
							}
							
							//validate EE_24
							if(!ObjectUtils.equals(originalRecords.get(0).getEe24(), uploadGrfFile.getEe24())){
								validationErrors.rejectValue("ee23", "", new String[]{lineNumber, mappedFieldNames.get("ee23")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee24")));
								validationPassed = false;
							}
							
							//validate EE_25
							if(!ObjectUtils.equals(originalRecords.get(0).getEe25(), uploadGrfFile.getEe25())){
								validationErrors.rejectValue("ee25", "", new String[]{lineNumber, mappedFieldNames.get("ee25")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee25")));
								validationPassed = false;
							}
							
							//validate EE_26
							if(!ObjectUtils.equals(originalRecords.get(0).getEe26(), uploadGrfFile.getEe26())){
								validationErrors.rejectValue("ee26", "", new String[]{lineNumber, mappedFieldNames.get("ee26")}, StringUtils.replace(commonUpdateErrorMsg, "$", mappedFieldNames.get("ee26")));
								validationPassed = false;
							}
						}
						
					}
					
				}else{
					String errMsg = "studentId does not available for "+uploadGrfFile.getStudentId()+" in Original GRF record";
					logger.debug(errMsg);
					validationErrors.rejectValue("studentId", "", new String[]{lineNumber, mappedFieldNames.get("studentId")}, errMsg);
					validationPassed = false;
				}
				
			}
		}
		uploadGrfFile.setBatchUploadId(batchUploadId);
		uploadGrfFile.setCurrentGradelevel(uploadGrfFile.getCurrentGradelevel().toUpperCase());
		uploadGrfFile.setUploadedUserId(uploadedUserId);
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("gradeChanged",uploadGrfFile.getGradeChange());
		if(validationPassed){
			logger.debug("Custom Validation passed. Setting Params to domain object.");
			uploadGrfFile.setReportYear((Long) params.get("reportYear"));
			uploadGrfFile.setAssessmentProgramId((Long)params.get("assessmentProgramIdOnUI"));
			uploadGrfFile.setActiveFlag(false);//If all the row pass custom validation then we make this to true
			
		}
		customValidationResults.put("rowDataObject", uploadGrfFile);
		logger.debug("Completed validation completed.");		
		return customValidationResults;
	}	
}