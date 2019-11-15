package edu.ku.cete.service;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Policy;

/**
 * @author nicholas.studt
 */
public interface PolicyService extends Serializable {

    /**
     * @param toAdd object to add.
     * @return {@link Policy}
     * @throws ServiceException {@link ServiceException}
     */
    Policy add(Policy toAdd) throws ServiceException;

    /**
     * @param id Id of the object to delete.
     * @return boolean was the object deleted.
     */
    boolean delete(Long id);

    /**
     * @param organziationId Id of the policy.
     * @return List of {@link Policy}
     */
    List<Policy> findByOrganization(@Param("organziationId") Long organziationId);

    /**
     * @param id Id of the policy.
     * @return {@link Policy}
     */
    Policy get(Long id);

    /**
     * @return List of {@link Policy}
     */
    List<Policy> getAll();

    /**
     * @param saveOrUpdate the object to be saved or updated.
     * @return {@link Policy}
     * @throws ServiceException {@link ServiceException}
     */
    Policy saveOrUpdate(Policy saveOrUpdate) throws ServiceException;

    /**
     * @param toUpdate the object to update.
     * @return {@link Policy}
     * @throws ServiceException {@link ServiceException}
     */
    Policy update(Policy toUpdate) throws ServiceException;
}
