package edu.ku.cete.service.report;

import java.util.List;

import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.report.AlternateAggregateReport;
import edu.ku.cete.report.domain.BatchUploadReason;

public interface UploadGrfFileService {

	List<Long> getDistinctSubjects(Long stateId,Long reportyear,Long assessmentProgramId);

	List<Long> getDistinctGradesBySubjectId(Long stateId,Long subjectId,Long reportYear,Long assessmentProgramId);
	
	List<Long> getDistinctOrgIdsFromGeneralReasearch(Long subjectId,Long gradeId,Long stateId,Long reportYear,Long assessmentProgramId,Integer offset,Integer pageSize);

	List<UploadGrfFile> getGeneralResearchDataBYOrgIdandSubjectidandgradeId(Long organizationId,Long reportyear,Long assessmentProgramId, Long subjectId, Long gradeId, Long stateId);

	List<UploadGrfFile> getGrfStudentRecord(Long studentId, Long reportYear,
			Long id, String stateStudentIdentifier, Long subjectId,
			Long versionId, Long batchUploadId);

	List<Long> getStudentIdsFromGRFBySSID(String stateStudentIdentifier, Long reportYear, Long stateId, Long versionId);
	
	List<AlternateAggregateReport> getDistinctTeacherIdsFromGeneralReasearch(Long stateId, Long reportYear,Long assessmentProgramId, Integer offset, Integer pageSize);
	
	List<AlternateAggregateReport> getDistinctOrganizationIdFromGeneralReasearch(Long stateId, Long reportYear,Long assessmentProgramId, Integer offset, Integer pageSize);

	AlternateAggregateReport getGeneralResearchDataBYTeacherIdSchoolId(String kiteEducatorIdentifier, Long schoolId, Long reportYear,Long assessmentProgramId, Long stateId);
	
	AlternateAggregateReport getGeneralResearchDataByOrganizationId(Long organizationId, Long reportYear,Long assessmentProgramId, Long stateId);

	List<String> getAvailableSSIDByStudentId(Long studentId, Long batchUploadId, Long schoolyear);
	
	List<ContentArea> getDistinctSubjectNamesFromGRF(Long stateId, Long reportYear,Long assessmentProgramId);

	UploadGrfFile getStudentGrfDataByStudentandSubjectId(Long stateId, Long reportYear, Long assessmentProgramId, Long subjectId, Long uniqueRowIdentifier, String stateStudentIdentifier);

	String updateOnEdit(UploadGrfFile uploadGrfFile);

	String getCurrentGradeFromGRF(Long studentId, Long long1, Long subjectId);

	Integer countByUniqueRowIdentifier(Long externalUniqueRowIdentifier,
			Long batchUploadId);

	List<UploadGrfFile> getValidGRFRecords(String districtCode, Long stateId, Long reportYear,
			Long batchUploadId, boolean grfUploadFlag, Integer offset, Integer pageSize);
	
	List<BatchUploadReason> validateUploadFile(Long stateId, Long uploadBatchId, String uploadType,
			Long assessmentProgramId, Long reportYear, Long createdUser, boolean isCommon);
	
	int insertGrfList(String filePath, String uploadGrfFileType, List<String> columnNames);


	void deleteTempGrfFileByBatchUploadId(Long batchUploadId);
	
	Integer resetEEValuesOnGradeCahange(Long stateId, Long batchUploadId, Long reportYear);

	void insertToTempTable(List<String> columns, List<String> columnName);
	
	List<String> getDistinctDistrictsByStateId(Long batchUploadId);
	
}
