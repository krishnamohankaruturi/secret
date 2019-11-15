package edu.ku.cete.model;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.report.domain.BatchUploadReason;

public interface UploadResultFileMapper {
	int insertIncidentFileRecord(UploadIncidentFile record);
	int insertScCodeFileRecord(UploadScCodeFile record);
	int insertGrfFileRecord(UploadGrfFile record);
	
	void updateScCodeFileRecordsByStateAndReportYear(@Param("state") Long state,
			                                         @Param("reportYear") Long reportYear,
			                                         @Param("uploadedUserId") Long uploadedUserId,			                                         
			                                         @Param("batchuploadprocessid") Long batchuploadprocessid, 
			                                         @Param("status") boolean status);
	void updateIncidentFileFileRecordsByStateAndReportYear(@Param("state")Long state,@Param("reportYear") Long reportYear,@Param("uploadedUserId") Long uploadedUserId,@Param("batchuploadprocessid") Long batchuploadprocessid,@Param("status") boolean status);
	void updateGrfFileRecordsByStateAndReportYear(@Param("state")Long state,
			                                      @Param("reportYear") Long reportYear,
			                                      @Param("uploadedUserId") Long uploadedUserId,
			                                      @Param("assessmentProgramId") Long assessmentProgramId,
			                                      @Param("batchuploadprocessid") Long batchuploadprocessid,
			                                      @Param("status") boolean status);

	List<Long> getDistinctSubjects(@Param("stateId") Long stateId,@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId);

	List<Long> getDistinctGradesBySubjectId(@Param("stateId") Long stateId,@Param("subjectId") Long subjectId,@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId);

	List<Long> getDistinctOrgIdsFromGeneralReasearch(
			@Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId,@Param("stateId") Long stateId,
			@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

	List<UploadGrfFile> getGeneralResearchDataBYOrgIdandSubjectidandgradeId(
			@Param("organizationId") Long organizationId,
			@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId,@Param("stateId") Long stateId);

	List<UploadIncidentFile> getDLMIncidentExtract(@Param("stateId")Long orgId, @Param("year") int year);

	List<UploadScCodeFile> getDLMSpecialCircumstanceExtract(@Param("stateId")Long orgId, @Param("year") int year);

	List<UploadGrfFile> getDLMGeneralResearchExtract(@Param("stateId") Long stateId, @Param("districtId")Long districtId, @Param("schoolId")Long schoolId, 
			@Param("subjectId")Long subjectId, @Param("gradeId") Long gradeId, @Param("year") int year);

	List<UploadGrfFile> getDistrictForGRF(@Param("stateId")Long stateId, @Param("reportYear") int reportYear);

	List<UploadGrfFile> getSchoolsInDistrictForGRF(@Param("districtId")Long districtId,
			@Param("stateId") Long stateId);

	List<UploadGrfFile> getContentAreasforGRF(@Param("stateId")Long stateId,
			@Param("districtId")Long districtId, @Param("schoolId")Long schoolId);

	List<UploadGrfFile> getGradeCourseByGRF(@Param("stateId")Long stateId,
			@Param("districtId")Long districtId, @Param("schoolId")Long schoolId, @Param("subjectId")Long contentAreaId);
	void deleteFailedBatchGrfFileRecords( @Param("batchUploadId") Long batchUploadId);
	void deleteBatchFailedScCodeFileRecords( @Param("batchUploadId") Long batchUploadId);
	void deleteFailedBatchIncidentFileRecords( @Param("batchUploadId") Long batchUploadId);
	
	List<UploadGrfFile> getGrfStudentRecord(@Param("studentId") Long studentId, @Param("reportYear") Long reportYear,
			@Param("id") Long id, @Param("stateStudentIdentifier") String stateStudentIdentifier, @Param("subjectId") Long subjectId,
			@Param("versionId") Long versionId,@Param("batchUploadId") Long batchUploadId);
	
	List<Long> getStudentIdsFromGRFBySSID(@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("reportYear") Long reportYear, @Param("stateId") Long stateId, @Param("versionId") Long versionId);
	
	void clearRecordsOnOriginalGRFUpload(@Param("stateId") Long stateId,@Param("reportYear") Long reportYear,
			@Param("assessmentProgramId") Long assessmentProgramId);
	
	List<AlternateAggregateReport> getDistinctTeacherIdsFromGeneralReasearch(
			@Param("stateId") Long stateId,
			@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

	List<AlternateAggregateReport> getDistinctOrganizationIdFromGeneralReasearch(
			@Param("stateId") Long stateId,
			@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);
	
	AlternateAggregateReport getGeneralResearchDataBYTeacherIdSchoolId(
			@Param("kiteEducatorIdentifier") String kiteEducatorIdentifier, @Param("schoolId") Long schoolId,
			@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("stateId") Long stateId);
	
	AlternateAggregateReport getGeneralResearchDataByOrganizationId(
			@Param("organizationId") Long organizationId,
			@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("stateId") Long stateId);

	void generateScCodeExtract(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("organizationId") long contractingOrgId, @Param("reportYear") int reportYear,
			@Param("userId") Long userId,@Param("stateCode") String stateCode,@Param("batchUploadId") Long batchUploadId);

	void generateStudentExitDetailsExtract(@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("organizationId") long contractingOrgId, @Param("reportYear") int reportYear,
			@Param("userId") Long userId, @Param("isStateHaveSpecificExitCode") Boolean isStateHaveSpecificExitCode);
	
	List<String> getAvailableSSIDByStudentId(@Param("studentId")  Long studentId,
			@Param("batchUploadId") Long batchUploadId, @Param("reportYear") Long schoolyear);
		
	List<ContentArea> getDistinctSubjectNamesFromGRF(@Param("stateId") Long stateId,@Param("reportYear") Long reportYear,
			@Param("assessmentProgramId") Long assessmentProgramId);
	
	UploadGrfFile getStudentGrfDataByStudentandSubjectId(@Param("stateId") Long stateId,@Param("reportYear") Long reportYear,
			@Param("assessmentProgramId") Long assessmentProgramId,@Param("subjectId") Long subjectId,@Param("uniqueRowIdentifier")Long uniqueRowIdentifier,@Param("stateStudentIdentifier") String stateStudentIdentifier);
	
	String getCurrentGradeFromGRF(@Param("studentId") Long studentId,@Param("reportYear") Long reportYear,@Param("subjectId") Long subjectId);
	
	Integer countByUniqueRowIdentifier(@Param("externalUniqueRowIdentifier") Long externalUniqueRowIdentifier,
			@Param("batchUploadId") Long batchUploadId);
	
	void setRecentFlag(@Param("batchUploadId")Long batchUploadId, @Param("reportYear") Long reportYear);
	
	GrfStateApproveAudit getOriginalGRFUploadAudit(@Param("stateId") Long stateId,@Param("year") int year);
	List<UploadGrfFile> getValidGRFRecordsForProcess(@Param("districtCode") String districtCode, @Param("stateId") Long stateId,
			@Param("reportYear") Long reportYear,@Param("batchUploadId") Long batchUploadId,@Param("grfUploadFlag") boolean grfUploadFlag, @Param("offset") Integer offset	,
			@Param("pageSize") Integer pageSize);
	
	List<BatchUploadReason> validateUploadFile(@Param("stateId") Long stateId,@Param("batchUploadId") Long uploadBatchId,
			@Param("uploadType") String uploadType,@Param("assessmentProgramId") Long assessmentProgramId,@Param("reportYear") Long reportYear,
			@Param("createdUser") Long createdUser,@Param("isCommon") boolean isCommon);
	
	Integer resetEEValuesOnGradeCahange(@Param("stateId") Long stateId,@Param("batchUploadId") Long batchUploadId,@Param("reportYear")
			Long reportYear);
	
	int insertGrfList(@Param("filePath") String  filePath, @Param("uploadGrfFileType") String uploadGrfFileType,  @Param("columnNames") List<String> columnNames);
	
	void deleteTempGrfFileByBatchUploadId( @Param("batchUploadId") Long batchUploadId);
	
	void insertToTempTable(@Param("columns")  List<String> columns,@Param("columnName") List<String> columnName);
	
	List<String> getDistinctDistrictsByStateId(@Param("batchUploadId") Long batchUploadId);

}
