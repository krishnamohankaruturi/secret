package edu.ku.cete.service.report;

import java.util.List;
import edu.ku.cete.domain.report.OrganizationGrfCalculation;

public interface OrganizationGrfCalculationService {
	
	int insert(OrganizationGrfCalculation record);

	int insertSelective(OrganizationGrfCalculation record);

	List<Long> getDistinctOrgIdsFromOrgGrfCalculation(Long stateId,Long reportYear,Long assessmentProgramId,Integer offset,Integer pageSize);

	int deleteOrganizationGrfCalculation(Long stateId,Long reportYear,Long assessmentProgramId);

	OrganizationGrfCalculation getOrganizationGrfCalculationByOrgId(Long organizationId, Long stateId, String orgTypeCode,Long reportYear,Long assessmentProgramId);

}
