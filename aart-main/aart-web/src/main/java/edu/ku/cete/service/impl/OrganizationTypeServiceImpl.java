/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.model.OrganizationTypeDao;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrganizationTypeServiceImpl implements edu.ku.cete.service.OrganizationTypeService {

    @Autowired
    private OrganizationTypeDao orgTypeDao;

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrganizationTypeService#add(edu.ku.cete.domain.common.OrganizationType)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrganizationType add(OrganizationType organizationType) {
        orgTypeDao.add(organizationType);
        return organizationType;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrganizationTypeService#update(edu.ku.cete.domain.common.OrganizationType)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void update(OrganizationType organizationType) {
        orgTypeDao.update(organizationType);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrganizationTypeService#delete(long)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void delete(long organizationTypeId) {
        orgTypeDao.delete(organizationTypeId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrganizationTypeService#get(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrganizationType get(long organizationTypeId) {
        return orgTypeDao.get(organizationTypeId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrganizationTypeService#getByTypeCode(java.lang.String)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrganizationType getByTypeCode(String typeCode) {
        return orgTypeDao.getByTypeCode(typeCode);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrganizationTypeService#getAll()
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrganizationType> getAll() {
        return orgTypeDao.getAll();
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrganizationType getByTypeCodeCaseInsensitive(String typeCode) {
        return orgTypeDao.getByTypeCodeCaseInsensitive(typeCode);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<OrganizationType> getOrgHierarchyByOrg(Long organizationId) {
        return orgTypeDao.getOrgHierarchyByOrg(organizationId);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final OrganizationType getByTypeLevel(int level) {
    	return orgTypeDao.getByLevel(level);
    }
}
