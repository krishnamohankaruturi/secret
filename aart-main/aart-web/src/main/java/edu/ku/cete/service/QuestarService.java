package edu.ku.cete.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import edu.ku.cete.domain.StudentResponseAudit;
import edu.ku.cete.domain.StudentResponseTaskVariantGroup;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.report.domain.QuestarStagingFile;
import edu.ku.cete.report.domain.QuestarStagingRecord;
import edu.ku.cete.report.domain.QuestarStagingResponse;
import edu.ku.cete.web.QuestarStagingDTO;

@Service
public interface QuestarService {
	public int insertAuditSelective(StudentResponseAudit qr);

	public int updateAuditSelective(StudentResponseAudit qr);
	
	public StudentResponseAudit selectAuditByPimaryKey(Long id);
	
	public int getFirstVariantGroupWithCriteria(Map<String, Object> criteria);

	List<TaskVariant> getTaskVariantsInGroup(long groupNumber, List<String> taskTypeCodes);
	
	public int updateGroupToProcessed(long groupNumber);
	
	
	public int insertStagingFileRecord(QuestarStagingFile record);
	
	public int updateStagingFileRecord(QuestarStagingFile record);
	
	public boolean insertStagingInfo(QuestarStagingRecord record, List<QuestarStagingResponse> responses);
	
	public List<QuestarStagingDTO> getNonProcessedStagingRecords(Integer offset, Integer pageSize);
	
	List<QuestarStagingResponse> getResponsesForStagingRecord(Long questarStagingId);
	
	public int updateStagingRecord(QuestarStagingRecord qsr);

	public Long mapTestExternalId(Long formNumber);

	public int findDuplicateRecords(QuestarStagingRecord record);
	
	public HashMap<Long, List<Long>> getQuestarTaskvariantMapByExternalId();
	
	public int updateStagingRecordsToBeProcessed();
	
	public int updateStagingRecordsToProcessed();

	public List<StudentResponseTaskVariantGroup> getTaskVariantsInGroups(
			List<String> questarTaskTypesList);
}