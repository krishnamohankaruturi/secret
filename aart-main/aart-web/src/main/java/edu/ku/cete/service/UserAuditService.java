package edu.ku.cete.service;

import java.io.Serializable;
import java.util.List;

import edu.ku.cete.domain.user.UserAudit;

/**
 * @author patrick.O'Malley
 */
public interface UserAuditService extends Serializable {

    /**
     * @param toAdd object to add.
     * @return {@link UserAudit}
     * @throws ServiceException {@link ServiceException}
     */
    UserAudit add(UserAudit toAdd) throws ServiceException;

    /**
     * @param id Id of the object to delete.
     * @return boolean was the object deleted.
     */
    boolean delete(Long id);

    /**
     * Get the UserAudit rec by id.
     * @param id Id of the UserAudit rec.
     * @return {@link UserAudit}
     */
    UserAudit get(Long id);

    /**
     *
     * @return List of {@link UserAudit}
     */
    List<UserAudit> getAll();

    /**
     * @param username User name of the user to log out.
     * @throws ServiceException {@link ServiceException}
     */
    void logout(String username) throws ServiceException;

    /**
     * @param saveOrUpdate the object to be saved or updated.
     * @return {@link UserAudit}
     * @throws ServiceException {@link ServiceException}
     */
    UserAudit saveOrUpdate(UserAudit saveOrUpdate) throws ServiceException;

    /**
     * @param toUpdate the object to update.
     * @return {@link UserAudit}
     * @throws ServiceException {@link ServiceException}
     */
    UserAudit update(UserAudit toUpdate) throws ServiceException;

	void logout(Long userId);
}
