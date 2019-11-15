package edu.ku.cete.report.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.report.domain.StudentPNPAuditHistory;

public interface StudentPNPAuditHistoryDao {
	
	void insertStudentPNPAttributesHistory(StudentPNPAuditHistory studentPNPAuditHistory);

	List<StudentPNPAuditHistory> getAuditHistory(@Param("startDateTimes")Date startDateTimes,
			@Param("endDateTimes")Date endDateTimes,@Param("stateStudentId") String stateStudentId,@Param("offset") int offset,@Param("limit") int limit);

}
