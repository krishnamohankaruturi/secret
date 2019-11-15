package edu.ku.cete.model;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import edu.ku.cete.domain.report.OrganizationGrfCalculation;
import edu.ku.cete.domain.report.OrganizationReportDetails;

public interface OrganizationGrfCalculationMapper {

	int insert(OrganizationGrfCalculation record);

	int insertSelective(OrganizationGrfCalculation record);	
	
	List<Long> getDistinctOrgIdsFromOrgGrfCalculation(@Param("stateId") Long stateId,@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId,@Param("offset") Integer offset,@Param("pageSize") Integer pageSize);

	OrganizationGrfCalculation getOrganizationGrfCalculationByOrgId(@Param("organizationId") Long organizationId,@Param("stateId") Long stateId,@Param("orgTypeCode") String orgTypeCode,@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId);
	
	int deleteOrganizationGrfCalculation(@Param("stateId") Long stateId,@Param("reportYear") Long reportYear,@Param("assessmentProgramId") Long assessmentProgramId);
}