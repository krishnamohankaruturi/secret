package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.Policy;
import edu.ku.cete.model.PolicyDao;
import edu.ku.cete.service.PolicyService;
import edu.ku.cete.service.ServiceException;

/**
 * @author nicholas.studt
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class PolicyServiceImpl implements PolicyService {

    /** Generated Serial. */
    private static final long serialVersionUID = -2770979986524872094L;

    /** PolicyDao holder. */
    @Autowired
    private PolicyDao policyDao;

    /**
     * @return the toolDao
     */
    public final PolicyDao getPolicyDao() {
        return policyDao;
    }

    /**
     * @param newPolicyDao the toolDao to set
     */
    public final void setPolicyDao(final PolicyDao newPolicyDao) {
        this.policyDao = newPolicyDao;
    }

    @Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Policy add(Policy toAdd) throws ServiceException {
        if (policyDao.add(toAdd) != 1) {
            throw new ServiceException("add() returned more than one row");
        }

        toAdd.setId(policyDao.lastid());
        return toAdd;
    }

    @Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean delete(final Long id) {
        if (policyDao.delete(id) == 1) {
            return true;
        }
        return false;
    }

    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Policy> findByOrganization(final Long organizationId) {
        return policyDao.findByOrganization(organizationId);
    }

    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Policy get(final Long toolId) {
        return policyDao.get(toolId);
    }

    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Policy> getAll() {
        return policyDao.getAll();
    }

    @Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Policy saveOrUpdate(Policy toSaveorUpdate) throws ServiceException {
        if (toSaveorUpdate.getId() == null) {
            return add(toSaveorUpdate);
        } else {
            return update(toSaveorUpdate);
        }
    }

    @Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Policy update(Policy toUpdate) throws ServiceException {
        if (policyDao.update(toUpdate) != 1) {
            throw new ServiceException("update() returned more than one row");
        }
        return toUpdate;
    }
}
