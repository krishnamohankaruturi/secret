/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.model.OrgAssessmentProgramDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.OrgAssessmentProgramService;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrgAssessmentProgramServiceImpl implements OrgAssessmentProgramService {

    @Autowired
    private OrgAssessmentProgramDao orgAssessProgDao;

    @Autowired
	private OrganizationDao organizationDao;
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrgAssessmentProgramService#getAll()
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> getAll() {
        return orgAssessProgDao.getAll();
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrgAssessmentProgramService#findById(long)
     */
    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrgAssessmentProgram findById(long orgAssessmentProgramId) {
        return orgAssessProgDao.findById(orgAssessmentProgramId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrgAssessmentProgramService#findByOrganizationId(long)
     */
    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> findByOrganizationId(long organizationId) {
        return orgAssessProgDao.findByOrganizationId(organizationId);
    }

    @Override
   	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final List<OrgAssessmentProgram> findByOrganizationIds(List<Long> organizationIds) {
           return orgAssessProgDao.findByOrganizationIds(organizationIds);
       }
    
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> findByUserIdAndOrganizationId(long userId,long organizationId, Long currentOrganizationId, Long currentGroupId) {
        return orgAssessProgDao.findByUserIdAndOrganizationId(userId,organizationId, currentOrganizationId, currentGroupId);
    }
    
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> findByContractingOrganizationId(long userOrganizationId) {
        return orgAssessProgDao.findByContractingOrganizationId(userOrganizationId);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrgAssessmentProgramService#findByAssessmentProgramId(long)
     */
    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> findByAssessmentProgramId(long assessmentProgramId) {
        return orgAssessProgDao.findByAssessmentProgramId(assessmentProgramId);
    }

    /**
     *@param id long
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void deleteByPrimaryKey(long id) {
        orgAssessProgDao.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrgAssessmentProgram insert(OrgAssessmentProgram orgAssessmentProgram) {
        orgAssessProgDao.insert(orgAssessmentProgram);
        orgAssessmentProgram.setId(orgAssessProgDao.lastid());

        return orgAssessmentProgram;
    }

    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> getAllWithAssociations() {
        return orgAssessProgDao.getAllWithAssociations();
    }

    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> findStatesByAssessmentProgramId(long assessmentProgramId) {
        return orgAssessProgDao.findStatesByAssessmentProgramId(assessmentProgramId);
    }
        
    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrgAssessmentProgram findByOrganizationAndAssessmentProgram(long organizationId, long assessmentProgramId) {
        return orgAssessProgDao.findByOrganizationAndAssessmentProgram(organizationId, assessmentProgramId);
    }

    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrgAssessmentProgram> selectAllAssessmentPrograms(long userId) {
        return orgAssessProgDao.selectAllAssessmentPrograms(userId);
    }

	@Override
	public List<OrgAssessmentProgram> getByUserId(long userId) {
		return orgAssessProgDao.getByUserId(userId);
	}

	@Override
	public List<OrgAssessmentProgram> getExtractReportAssessmentPrograms(Long userId, Long contractOrgId,
			long currentOrganizationId, long currentGroupsId, Long currentAssessmentProgramId, String permissionCode) {
		Long stateId = getStateIdByOrgId(currentOrganizationId);
		return orgAssessProgDao.getExtractReportAssessmentPrograms(userId, contractOrgId, currentOrganizationId,
				currentGroupsId, currentAssessmentProgramId, stateId, permissionCode);
	}

	private Long getStateIdByOrgId(Long organizationId) {
		List<Organization> userOrgHierarchy = new ArrayList<Organization>();
		userOrgHierarchy = organizationDao.getAllParents(organizationId);
		userOrgHierarchy.add(organizationDao.get(organizationId));
		Long stateId = null;
		for (Organization org : userOrgHierarchy) {
			if (org.getOrganizationType().getTypeCode().equals("ST")) {
				stateId = org.getId();
			}
		}
		return stateId;
	}

	@Override
	public Integer updateIfExist(OrgAssessmentProgram orgAssess) {
		return orgAssessProgDao.updateIfExist(orgAssess);
	}
    
}
