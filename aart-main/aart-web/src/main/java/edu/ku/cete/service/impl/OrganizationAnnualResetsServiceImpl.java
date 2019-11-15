package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.OrganizationAnnualResets;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.model.OrganizationAnnualResetsMapper;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.OrganizationAnnualResetsService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrganizationAnnualResetsServiceImpl implements OrganizationAnnualResetsService {


    /** Generated Serial. */
    private static final long serialVersionUID = 2735963534122808411L;
    
    /** OrganizationDao holder. */
    @Autowired
    private OrganizationDao organizationDao;
    
    @Autowired
    OrganizationAnnualResetsMapper organizationAnnualResetsMapper;


    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void inactivateOldRecords(){
		organizationAnnualResetsMapper.inactivateOldRecords();
		return;
	}
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int addOrganizationAnnualResets(OrganizationAnnualResets orgDatesObject){
		return organizationAnnualResetsMapper.insert(orgDatesObject);
    }
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateOrganizationAnnualResets(OrganizationAnnualResets orgDatesObject){
		return organizationAnnualResetsMapper.updateOrganizationAnnualResets(orgDatesObject);
		
    }
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int resetAnnualDatesForOrganization(OrganizationAnnualResets organizationAnnualResets){
		return organizationAnnualResetsMapper.resetAnnualDates(organizationAnnualResets);
    }
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int resetAnnualDatesStatusForOrganization(OrganizationAnnualResets organizationAnnualResets){
		return organizationAnnualResetsMapper.resetAnnualDatesStatus(organizationAnnualResets);
    }
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrganizationAnnualResets checkIfOrgUpdateRecordExists(Long organizationId){
		OrganizationAnnualResets organizationAnnualResetsObject = organizationAnnualResetsMapper.checkIfOrgUpdateRecordExists(organizationId);
		return organizationAnnualResetsObject;
    }
	
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OrganizationAnnualResets selectAcademicSchoolYearByOrgId(Long organizationId){
		return organizationAnnualResetsMapper.selectSchoolYearByOrgId(organizationId);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long getSchoolYearByOrganization(Long organizationId){
		return organizationDao.getSchoolYearByOrganization(organizationId);
	}
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateFCSResetCompleteFlagByOrgId(Long organizationId, Boolean fcsResetComplete){
		return organizationAnnualResetsMapper.updateFCSResetCompleteFlagByOrgId(organizationId, fcsResetComplete);		
    }
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationAnnualResets> getAnnualResetStates() {
		return organizationAnnualResetsMapper.getAnnualResetStates();
	}	
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getStatesBasedOnAssmntProgramIdFCSResetFlag(Long assessmentProgramId) {	
		List<Organization> organizations= organizationAnnualResetsMapper.getStatesBasedOnAssmntProgramIdFCSResetFlag(assessmentProgramId);
		
		return organizations;
	}
}
