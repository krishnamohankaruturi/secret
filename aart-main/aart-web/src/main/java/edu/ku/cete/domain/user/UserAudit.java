package edu.ku.cete.domain.user;

import java.io.Serializable;
import java.util.Date;

import org.springframework.core.style.ToStringCreator;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 *
 * @author patrick.O'Malley
 *
 */
public class UserAudit extends AuditableDomain implements Serializable {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 5359461602908586257L;

    /** The id. */
    private Long id;

    /** The userid. */
    private long userId;

    /** The userid. */
    private String userOS;

    /** The userid. */
    private Date loginTime;

    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }

    /**
     * @param newId the id to set
     */
    public final void setId(final Long newId) {
        this.id = newId;
    }

    /**
     * @return the userId
     */
    public final long getUserId() {
        return userId;
    }

    /**
     * @param newUserId the userId to set
     */
    public final void setUserId(final long newUserId) {
        this.userId = newUserId;
    }

    /**
     * @return the userOS
     */
    public final String getUserOS() {
        return userOS;
    }

    /**
     * @param newUserOS the userOS to set
     */
    public final void setUserOS(final String newUserOS) {
        this.userOS = newUserOS;
    }

    /**
     * @return the loginTime
     */
    public final Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param newLoginTime the loginTime to set
     */
    public final void setLoginTime(final Date newLoginTime) {
        this.loginTime = newLoginTime;
    }

    /**
     * Convert object to a String.
     * @return {@link String} representation of a userAudit rec.
     */
    public final String toString() {
        return new ToStringCreator(this).append("id", id).append("userId", userId).append("userOS", userOS)
                .append("loginTime", loginTime).toString();
    }
}
