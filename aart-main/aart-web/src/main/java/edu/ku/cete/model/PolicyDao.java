package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Policy;

/**
 *
 * @author mrajannan
 *
 */
public interface PolicyDao {

    /**
     * @param toAdd object to add.
     * @return number of rows affected.
     */
    int add(Policy toAdd);

    /**
     * @param id Id of the policy.
     * @return number of rows affected.
     */
    int delete(@Param("id") long id);

    /**
     * @param organziationId Id of the policy.
     * @return List of {@link Policy}
     */
    List<Policy> findByOrganization(@Param("organizationId") long organizationId);

    /**
     * @param id Id of the policy.
     * @return {@link Policy}
     */
    Policy get(@Param("id") long id);

    /**
     * @return List of {@link Policy}
     */
    List<Policy> getAll();

    /**
     * @return the id of the sequence.
     */
    long lastid();

    /**
     * @param toUpdate the object to update.
     * @return number of rows affected.
     */
    int update(Policy toUpdate);
}
