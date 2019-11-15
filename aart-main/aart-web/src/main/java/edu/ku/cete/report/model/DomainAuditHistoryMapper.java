package edu.ku.cete.report.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.customextract.KAPCustomExtract;
import edu.ku.cete.report.domain.AuditType;
/**
 * US16533
 * To track Domain Object Audit history
 */
import edu.ku.cete.report.domain.DomainAuditHistory;

public interface DomainAuditHistoryMapper {

	int insert(DomainAuditHistory record);

	int insertSelective(DomainAuditHistory record);

	/* Added for US-17687 */
	ArrayList<DomainAuditHistory> getUserForAuditLog(@Param("lastJobRunDateTime") String getLastJobRunDatetime);

	ArrayList<Long> getUnProcessedAuditLoggedUser();

	void changeStatusToCompletedProcessedAuditLoggedUser(@Param("successId") Long successIds,
			@Param("status") String status);

	List<DomainAuditHistory> getDomainAuditHistory(@Param("startDateTimes") Date startDateTimes,
			@Param("endDateTimes") Date endDateTimes);

	List<KAPCustomExtract> getKAPCustomExtractData(@Param("studentIds") Long[] studentIdListLong);
	
	DomainAuditHistory getById(@Param("id") Long id);
	
	List<AuditType> getAuditTypeList(); 
}