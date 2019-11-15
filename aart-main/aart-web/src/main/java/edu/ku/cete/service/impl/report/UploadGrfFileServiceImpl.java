package edu.ku.cete.service.impl.report;


import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.model.UploadResultFileMapper;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.report.UploadGrfFileService;
import edu.ku.cete.util.CommonConstants;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UploadGrfFileServiceImpl implements UploadGrfFileService {
	
	@Autowired
	private UploadResultFileMapper uploadResultFileMapper;
	
	@Autowired
	private OrganizationService organizationService;
		
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistinctSubjects(Long stateId,Long reportYear,Long assessmentProgramId) {
		return uploadResultFileMapper.getDistinctSubjects(stateId,reportYear,assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistinctGradesBySubjectId(Long stateId,Long subjectId,Long reportYear,Long assessmentProgramId) {
		return uploadResultFileMapper.getDistinctGradesBySubjectId(stateId,subjectId,reportYear,assessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistinctOrgIdsFromGeneralReasearch(Long subjectId,Long gradeId,Long stateId,Long reportYear,Long assessmentProgramId,Integer offset,Integer pageSize) {
		return uploadResultFileMapper.getDistinctOrgIdsFromGeneralReasearch(subjectId,gradeId,stateId,reportYear,assessmentProgramId,offset,pageSize);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<UploadGrfFile> getGeneralResearchDataBYOrgIdandSubjectidandgradeId(Long organizationId, Long reportYear,Long assessmentProgramId, Long subjectId,Long gradeId,Long stateId) {
		return uploadResultFileMapper.getGeneralResearchDataBYOrgIdandSubjectidandgradeId(organizationId, reportYear,assessmentProgramId,subjectId, gradeId, stateId);
	}

	@Override
	public List<UploadGrfFile> getGrfStudentRecord(Long studentId,
			Long reportYear, Long id, String stateStudentIdentifier,
			Long subjectId, Long versionId, Long batchUploadId) {
		return uploadResultFileMapper.getGrfStudentRecord(studentId,
				reportYear, id, stateStudentIdentifier,
				subjectId, versionId, batchUploadId);
	}

	@Override
	public List<Long> getStudentIdsFromGRFBySSID(String stateStudentIdentifier, Long reportYear, Long stateId, Long versionId) {
		return uploadResultFileMapper.getStudentIdsFromGRFBySSID(stateStudentIdentifier, reportYear, stateId, versionId);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AlternateAggregateReport> getDistinctTeacherIdsFromGeneralReasearch(Long stateId,Long reportYear,Long assessmentProgramId,Integer offset,Integer pageSize) {
		return uploadResultFileMapper.getDistinctTeacherIdsFromGeneralReasearch(stateId,reportYear,assessmentProgramId,offset,pageSize);
	}
		
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AlternateAggregateReport> getDistinctOrganizationIdFromGeneralReasearch(Long stateId,Long reportYear,Long assessmentProgramId,Integer offset,Integer pageSize) {
		return uploadResultFileMapper.getDistinctOrganizationIdFromGeneralReasearch(stateId,reportYear,assessmentProgramId,offset,pageSize);
	}
		
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public AlternateAggregateReport getGeneralResearchDataBYTeacherIdSchoolId(String kiteEducatorIdentifier, Long schoolId, Long reportYear,Long assessmentProgramId, Long stateId) {
		return uploadResultFileMapper.getGeneralResearchDataBYTeacherIdSchoolId(kiteEducatorIdentifier, schoolId, reportYear,assessmentProgramId, stateId);
	}		
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public AlternateAggregateReport getGeneralResearchDataByOrganizationId(Long organizationId, Long reportYear,Long assessmentProgramId, Long stateId) {
		return uploadResultFileMapper.getGeneralResearchDataByOrganizationId(organizationId, reportYear,assessmentProgramId, stateId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getAvailableSSIDByStudentId(Long studentId, Long batchUploadId, Long schoolyear){
		return uploadResultFileMapper.getAvailableSSIDByStudentId(studentId, batchUploadId, schoolyear);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String updateOnEdit(UploadGrfFile uploadGrfFile){
		
		List<UploadGrfFile> originalRecords = getGrfStudentRecord(uploadGrfFile.getStudentId(), uploadGrfFile.getReportYear(), null, 
				null, uploadGrfFile.getSubjectId(), 0l, null);
		
		
		UploadGrfFile recordToInsert = originalRecords.get(0);//Always only one Original GRF record will be there for the student and subject
		recordToInsert.setId(null);
		recordToInsert.setBatchUploadId(new Long(-1));
				
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		
		recordToInsert.setUploadedUserId(user.getId());
		
		//Validate state student identifier
		List<Long> studentIds = getStudentIdsFromGRFBySSID(uploadGrfFile.getStateStudentIdentifier(), uploadGrfFile.getReportYear(), recordToInsert.getStateId(), null);		
		if(studentIds.size() >= 1 && !studentIds.contains(uploadGrfFile.getStudentId())){
			return  "Given Student ID has already mapped to different student.";			
		}
		
		Organization districtDetails=organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getResidenceDistrictIdentifier(),recordToInsert.getStateId(), CommonConstants.ORGANIZATION_TYPE_ID_5);
		if(districtDetails==null) {
			return "District Code is not correct.";
		}
		
		recordToInsert.setDistrictId(districtDetails.getId());
		recordToInsert.setResidenceDistrictIdentifier(uploadGrfFile.getResidenceDistrictIdentifier());
		recordToInsert.setDistrictName(districtDetails.getOrganizationName());

		Organization schoolDetails=organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getSchoolIdentifier(),districtDetails.getId(), CommonConstants.ORGANIZATION_TYPE_ID_7);
		if(schoolDetails==null){
			return "School Code is not correct.";
		}
		
		recordToInsert.setSchoolIdentifier(schoolDetails.getDisplayIdentifier());
		recordToInsert.setSchoolId(schoolDetails.getId());
		recordToInsert.setSchoolName(schoolDetails.getOrganizationName());
		
		//Validate Educator Identifier
		List<User> educators = userService.getByEducatorIdentifierForGRF(uploadGrfFile.getEducatorIdentifier(), recordToInsert.getStateId());	
		if(educators.size() == 0){
			return "Educator Identifier is not valid.";
		}
		
		if(StringUtils.isNotBlank(uploadGrfFile.getAttendanceSchoolProgramIdentifier())){
			Organization attendanceSchool = organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getAttendanceSchoolProgramIdentifier(), recordToInsert.getStateId(), CommonConstants.ORGANIZATION_TYPE_ID_7);
			if(attendanceSchool == null)
				return "Attendance School Identifier is not valid.";
		}
		
		if(StringUtils.isNotBlank(uploadGrfFile.getAypSchoolIdentifier())){
			Organization aypSchool = organizationService.getOrgByDisplayIdAndParentId(uploadGrfFile.getAypSchoolIdentifier(), recordToInsert.getStateId(), CommonConstants.ORGANIZATION_TYPE_ID_7);	
			if(aypSchool == null)
				return "AYP School Identifier is not valid.";
		}
		
		recordToInsert.setKiteEducatorIdentifier(educators.get(0).getId().toString());
		recordToInsert.setStrKiteEducatorIdentifier(educators.get(0).getId().toString());
		recordToInsert.setEducatorFirstName(educators.get(0).getFirstName());
		recordToInsert.setEducatorLastName(educators.get(0).getSurName());
		recordToInsert.setEducatorIdentifier(uploadGrfFile.getEducatorIdentifier());
		
		//get last version grade and check with current grade, so every time mail will not be triggered
		String gradeId = getCurrentGradeFromGRF(uploadGrfFile.getStudentId(), uploadGrfFile.getReportYear(), uploadGrfFile.getSubjectId());
		
		//Check Grade change scenario
		//if(originalRecords.get(0).getGradeId().longValue() != uploadGrfFile.getGradeId().longValue()){//Grade change happened
		if(!StringUtils.equals(originalRecords.get(0).getCurrentGradelevel(), uploadGrfFile.getCurrentGradelevel())){//Grade change happened
			//Set EE 1-26 and performance value to 9 
			recordToInsert.setPerformanceLevel(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe1(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe2(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe3(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe4(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe5(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe6(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe7(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe8(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe9(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe10(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe11(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe12(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe13(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe14(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe15(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe16(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe17(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe18(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe19(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe20(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe21(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe22(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe23(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe24(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe25(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setEe26(CommonConstants.GRF_LEVEL_9);
			recordToInsert.setGradeId(uploadGrfFile.getGradeId());
			recordToInsert.setCurrentGradelevel(uploadGrfFile.getCurrentGradelevel());
			
			//if(gradeId != null && gradeId.longValue() != uploadGrfFile.getGradeId().longValue())
			if(ObjectUtils.notEqual(gradeId, uploadGrfFile.getCurrentGradelevel()))
				recordToInsert.setGradeChange(true);
			else
				recordToInsert.setGradeChange(false);			
		}else{
			recordToInsert.setGradeChange(false);
		}
		
		//Set editable value to recordToInsert object
		
		//Student
		recordToInsert.setLegalFirstName(uploadGrfFile.getLegalFirstName());
		recordToInsert.setLegalLastName(uploadGrfFile.getLegalLastName());
		recordToInsert.setLegalMiddleName(uploadGrfFile.getLegalMiddleName());		
		recordToInsert.setGenerationCode(uploadGrfFile.getGenerationCode());
		recordToInsert.setDateOfBirth(uploadGrfFile.getDateOfBirth());
		recordToInsert.setStateStudentIdentifier(uploadGrfFile.getStateStudentIdentifier());
		
		//DemoGraphic
		recordToInsert.setGender(uploadGrfFile.getGender());
		recordToInsert.setHispanicEthnicity(uploadGrfFile.getHispanicEthnicity());
		recordToInsert.setComprehensiveRace(uploadGrfFile.getComprehensiveRace());
		recordToInsert.setFirstLanguage(uploadGrfFile.getFirstLanguage());
		
		//Profile
		recordToInsert.setPrimaryDisabilityCode(uploadGrfFile.getPrimaryDisabilityCode());
		recordToInsert.setEsolParticipationCode(uploadGrfFile.getEsolParticipationCode());
		
		//school and roster
		recordToInsert.setAypSchoolIdentifier(uploadGrfFile.getAypSchoolIdentifier());
		recordToInsert.setAttendanceSchoolProgramIdentifier(uploadGrfFile.getAttendanceSchoolProgramIdentifier());
		recordToInsert.setSchoolEntryDate(uploadGrfFile.getSchoolEntryDate());
		recordToInsert.setDistrictEntryDate(uploadGrfFile.getDistrictEntryDate());
		recordToInsert.setStateEntryDate(uploadGrfFile.getStateEntryDate());
		
		recordToInsert.setExitWithdrawalDate(uploadGrfFile.getExitWithdrawalDate());
		recordToInsert.setExitWithdrawalType(uploadGrfFile.getExitWithdrawalType());
		recordToInsert.setActiveFlag(true);
		recordToInsert.setInvalidationCode(uploadGrfFile.getInvalidationCode());
		recordToInsert.setOriginal(false);
		recordToInsert.setLocalStudentIdentifier(uploadGrfFile.getLocalStudentIdentifier());
		recordToInsert.setRecentVersion(true);
		
		uploadResultFileMapper.insertGrfFileRecord(recordToInsert);
		if(recordToInsert.getGradeChange()) emailService.sendEmailForGRFGradeChange(recordToInsert.getState());
		
		return "success";
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getDistinctSubjectNamesFromGRF(Long stateId,Long reportYear,Long assessmentProgramId) {
		return uploadResultFileMapper.getDistinctSubjectNamesFromGRF(stateId,reportYear,assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public UploadGrfFile getStudentGrfDataByStudentandSubjectId(Long stateId,Long reportYear,Long assessmentProgramId,Long subjectId,Long studentId,String stateStudentIdentifier) {
		return uploadResultFileMapper.getStudentGrfDataByStudentandSubjectId(stateId,reportYear,assessmentProgramId,subjectId,studentId,stateStudentIdentifier);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String getCurrentGradeFromGRF(Long studentId, Long reportYear, Long subjectId){
		return uploadResultFileMapper.getCurrentGradeFromGRF(studentId, reportYear, subjectId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer countByUniqueRowIdentifier(Long externalUniqueRowIdentifier,
			Long batchUploadId) {
		return uploadResultFileMapper.countByUniqueRowIdentifier(externalUniqueRowIdentifier, batchUploadId);
	}

	@Override
	public List<UploadGrfFile> getValidGRFRecords(String districtCode, Long stateId,
			Long reportYear, Long batchUploadId, boolean grfUploadFlag, Integer offset,
			Integer pageSize) {
		return uploadResultFileMapper.getValidGRFRecordsForProcess(districtCode, stateId,
				reportYear, batchUploadId, grfUploadFlag, offset,
				pageSize);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<BatchUploadReason> validateUploadFile(Long stateId, Long uploadBatchId,
			String uploadType, Long assessmentProgramId, Long reportYear,
			Long createdUser, boolean isCommon) {
		return uploadResultFileMapper.validateUploadFile(stateId, uploadBatchId, uploadType, assessmentProgramId, reportYear, createdUser, isCommon);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertGrfList(String filePath, String uploadGrfFileType, List<String> columnNames) {
		return uploadResultFileMapper.insertGrfList(filePath, uploadGrfFileType, columnNames);
	}
	

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteTempGrfFileByBatchUploadId(Long batchUploadId) {
		uploadResultFileMapper.deleteTempGrfFileByBatchUploadId(batchUploadId); 
	}

	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer resetEEValuesOnGradeCahange(Long stateId, Long batchUploadId, Long reportYear){
		return uploadResultFileMapper.resetEEValuesOnGradeCahange(stateId, batchUploadId, reportYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToTempTable(List<String> columnsValues,  List<String> columnName) {
		uploadResultFileMapper.insertToTempTable(columnsValues, columnName);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getDistinctDistrictsByStateId(Long batchUploadId) {
		return uploadResultFileMapper.getDistinctDistrictsByStateId(batchUploadId);
	}

}
