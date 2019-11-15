package edu.ku.cete.service.impl.report;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import edu.ku.cete.domain.report.OrganizationGrfCalculation;
import edu.ku.cete.model.OrganizationGrfCalculationMapper;
import edu.ku.cete.service.report.OrganizationGrfCalculationService;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrganizationGrfCalculationServiceImpl implements OrganizationGrfCalculationService {
	
	@Autowired
	private OrganizationGrfCalculationMapper orgGrfCalculationMapper;

	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insert(OrganizationGrfCalculation record){
		return orgGrfCalculationMapper.insert(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSelective(OrganizationGrfCalculation record) {
		return orgGrfCalculationMapper.insertSelective(record);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistinctOrgIdsFromOrgGrfCalculation(Long stateId,Long reportYear,Long assessmentProgramId,Integer offset,Integer pageSize) {
		return orgGrfCalculationMapper.getDistinctOrgIdsFromOrgGrfCalculation(stateId,reportYear,assessmentProgramId, offset, pageSize);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OrganizationGrfCalculation getOrganizationGrfCalculationByOrgId(Long organizationId,Long stateId,String orgTypeCode,Long reportYear,Long assessmentProgramId) {
		return orgGrfCalculationMapper.getOrganizationGrfCalculationByOrgId(organizationId,stateId,orgTypeCode,reportYear,assessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteOrganizationGrfCalculation(Long stateId,Long reportYear,Long assessmentProgramId) {
		return orgGrfCalculationMapper.deleteOrganizationGrfCalculation(stateId,reportYear,assessmentProgramId);	
	}
	
	
	
}
