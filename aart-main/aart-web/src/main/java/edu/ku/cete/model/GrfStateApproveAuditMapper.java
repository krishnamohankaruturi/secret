package edu.ku.cete.model;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.GrfStateApproveAudit;

public interface GrfStateApproveAuditMapper {

	void insert(GrfStateApproveAudit audit);

	GrfStateApproveAudit getByStateAndReportYear(@Param("stateId") Long stateId,
			@Param("assessmentProgramId") Long assessmentProgramId, @Param("reportYear") Long reportYear);
    
}
