package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface AuditHistoryViewDao {

	List<String> getViewAuditHistory(@Param("auditName") String auditName);
}
