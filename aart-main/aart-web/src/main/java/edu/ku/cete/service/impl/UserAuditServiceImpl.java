package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.user.UserAudit;
import edu.ku.cete.model.UserAuditDao;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserAuditService;

/**
 * @author patrick.O'Malley
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class UserAuditServiceImpl implements UserAuditService {

//    /** Logger. */
//    private final Log logger = LogFactory.getLog(getClass());

    /** Generated Serial. */
    private static final long serialVersionUID = -6028994662708167802L;

    /** AccommodationDao holder. */
    @Autowired
    private UserAuditDao userAuditDao;

    /**
     * @return the userAuditDao
     */
    public final UserAuditDao getUserAuditDao() {
        return userAuditDao;
    }

    /**
     * @param newUserAuditDao the userAuditDao to set
     */
    public final void setUserAuditDao(final UserAuditDao newUserAuditDao) {
        this.userAuditDao = newUserAuditDao;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserAudit add(UserAudit toAdd) throws ServiceException {
        if (userAuditDao.add(toAdd) != 1) {
            throw new ServiceException("add() returned more than one row");
        }
        toAdd.setId(userAuditDao.lastid());
        return toAdd;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean delete(final Long id) {
        if (userAuditDao.delete(id) == 1) {
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserAudit get(final Long id) {
        return userAuditDao.get(id);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<UserAudit> getAll() {
        return userAuditDao.getAll();
    }

    @Override
    public final void logout(final String username) throws ServiceException {
        if (userAuditDao.logout(username) != 1) {
            throw new ServiceException("logout() returned more than one row");
        }
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void logout(final Long userId) {
       userAuditDao.logoutById(userId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserAudit saveOrUpdate(UserAudit toSaveorUpdate) throws ServiceException {
        if (toSaveorUpdate.getId() == null) {
            return add(toSaveorUpdate);
        } else {
            return update(toSaveorUpdate);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final UserAudit update(UserAudit toUpdate) throws ServiceException {
        if (userAuditDao.update(toUpdate) != 1) {
            throw new ServiceException("update() returned more than one row");
        }
        return toUpdate;
    }
}
