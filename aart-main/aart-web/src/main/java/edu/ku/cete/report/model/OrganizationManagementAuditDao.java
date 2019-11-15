package edu.ku.cete.report.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.report.domain.OrganizationManagementAudit;

public interface OrganizationManagementAuditDao {
	void insertManagementAudit(OrganizationManagementAudit organizationManagementAudit);

	List<OrganizationManagementAudit> getOrganizationManagementAuditHistory(
			@Param("startDateTimes")Date startDateTimes,@Param("endDateTimes")Date endDateTimes, @Param("filterValues") Map<String,String> filterValues,@Param("offset") int offset,@Param("limit") int limit);

}
