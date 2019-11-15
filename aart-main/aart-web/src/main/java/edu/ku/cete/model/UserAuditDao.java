package edu.ku.cete.model;

import edu.ku.cete.domain.user.UserAudit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/** UserAudit Dao. */
public interface UserAuditDao {

    /**
     * Create a new userAudit rec.
     * @param toAdd object to add.
     * @return number of rows affected.
     */
    int add(UserAudit toAdd);

    /**
     * @param id Id of the userAudit.
     * @return number of rows affected.
     */
    int delete(@Param("id") long id);

    /**
     * Get the userAudit rec by id.
     * @param id Id of the userAudit rec.
     * @return {@link UserAudit}
     */
    UserAudit get(@Param("id") long id);

    /**
     *
     * @return List of {@link UserAudit}
     */
    List<UserAudit> getAll();

    /**
     * @return the id of the sequence.
     */
    long lastid();

    /**
     * @param username Username to logout.
     * @return number of rows affected.
     */
    int logout(@Param("username") String username);
    
    
    int logoutById(@Param("userId") Long userId);

    /**
     * @param toUpdate the object to update.
     * @return number of rows affected.
     */
    int update(UserAudit toUpdate);

    /**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
	void deleteByUserId(@Param("id")Long id);
}
