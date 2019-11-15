package edu.ku.cete.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;

import edu.ku.cete.batch.upload.validator.BatchUploadValidator;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadInfo;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.report.domain.ExternalUploadResult;
import edu.ku.cete.report.domain.StateSpecificFile;

public interface BatchUploadService {
	
	@SuppressWarnings("rawtypes")
	public Map<String, Object> validateProcess(BeanWrapperFieldSetMapper beanFieldSetMapper, BatchUploadValidator uploadFileValidator, 
			  FieldSet fieldSet, Map<String, Object> params, Map<String, String> mappedFieldNames) throws BindException;
	
	public void writeProcess(List<? extends Object> objects, String uploadType);
	
	public int insertBatchUpload(BatchUpload record);
	
	public int insertSelectiveBatchUpload(BatchUpload record);
	
	public int updateByPrimaryKeyBatchUpload(BatchUpload record);
	
	public int updateByPrimaryKeySelectiveBatchUpload(BatchUpload record);
	
	public BatchUpload selectByPrimaryKeyBatchUpload(long id);
	
	public List<BatchUpload> selectByAssessmentProgramIdsAndFiltersBatchUpload(List<Long> assessmentProgramIds,
			String orderByColumn, String order, Integer limit, Integer offset, List<Long> fileTypeIds);
	
	public int getUploadCountByAssessmentProgramIdsAndFilters(List<Long> assessmentProgramIds, List<Long> fileTypeIds);
	
	public int selectDuplicateCountBatchUpload(Long assessmentProgramId, Long contentAreaId, Long uploadTypeId, Integer schoolYear, Long testingProgramId, String reportCycle);
	
	public int updatePreviousToInactiveBatchUpload(Long assessmentProgramId, Long contentAreaId, Long uploadTypeId, Integer schoolYear, Long reportYear, Long stateId, Long testingProgramId, String reportCycle);
	
	void insertBatchUploadReasons(List<BatchUploadReason> uploadBatchReasons);

	BatchUpload selectOnePending(String status, String recordType);

	List<BatchUploadReason> findBatchUploadReasonsForId(Long batchUploadId);

	List<Long> findFrameWorkTypeForAssessmentProgram(Long asessmentProgramId, String frameworkCode);

	Long findContentFrameworkCodeWithLevelAssessmentGradeContentarea(Long assessmentProgramId, Long gradeId, 
																	Long contentAreaId, List<Long> frameworkTypeId, String levelTitle, String contentCode);
	
	/*US16252: to fetch maximum 100 records */
	List<BatchUploadReason> find100BatchUploadReasonsForId(Long batchUploadId);
	/* Added for US16548*/
	public List<BatchUploadInfo> selectByCategoryCodeBatchUpload(Long id, String categoryCode,Long userGroupId,Long userOrgId);
	//added during US16966 - To add alert message to upload
	@SuppressWarnings("rawtypes")
	public Map<String, Object> validateProcessForAlertMessage(
			BeanWrapperFieldSetMapper beanFieldSetMapper,
			BatchUploadValidator uploadFileValidator, FieldSet fieldSet,
			Map<String, Object> params, Map<String, String> mappedFieldNames)throws BindException;

	public List<BatchUpload> selectuploadResultsByAssessmentProgramIdsAndFiltersBatchUpload(
			Long assessmentProgramId,List<Long> userStates, String orderByColumn, String order,
			int limitCount, int offset);

	public int checkForInProgressUpload(Long assessmentProgramId, Long stateId,
			Long fileTypeId, Integer reportYear, Long contentAreaId, Long testingProgramId, String reportCycle);

	public BeanPropertyBindingResult externalResultsUploadCommonValidation(
			ExternalUploadResult ExternalUploadResult,
			BeanPropertyBindingResult validationErrors,
			Map<String, Object> params, Map<String, String> mappedFieldNames);

	public List<BatchUpload> selectGRFProcessStatusBYStateId(
			Long assessmentProgramId, long contractingOrgId,
			Integer reportYear, List<String> grfProcessTypes, String orderByColumn, String order, int limitCount, int offset);
	
	public int saveCustomFileData(StateSpecificFile stateSpecificFiles);

	public List<StateSpecificFile> getStateSpecificFileData(Long assessmentProgramId, Long stateId, String sortByColumn,
			String sortType, Integer offset, Integer limitCount, Map<String, String> recordCriteriaMap);

	public int removeStateSpecificFile(Long stateSpecificFileId, Long userId);
	
	public int checkForInProgressGrfUploadOrReport(Long assessmentProgramId, Long stateId, Integer reportYear);

	public void updateGrfBatchUpload(Long batchuploadid);

	public BatchUpload latestGrfBatchUpload(Long stateId,String grfProcessType);
	
	public int checkForInProgressInSpecialCircumstanceAndExitedStudents(Long assessmentProgramId, Long stateId, Integer reportYear, String grfProcessType);
}

