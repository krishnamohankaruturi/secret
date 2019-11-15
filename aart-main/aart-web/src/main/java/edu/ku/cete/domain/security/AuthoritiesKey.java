package edu.ku.cete.domain.security;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * Generated class.
 */
public class AuthoritiesKey extends AuditableDomain {

    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column public.authorities.id
     *
     * @mbggenerated Thu Aug 18 17:31:16 MDT 2011
     */
    private Long id;
    /**
     * This field was generated by MyBatis Generator. This field corresponds to
     * the database column public.authorities.authority
     *
     * @mbggenerated Thu Aug 18 17:31:16 MDT 2011
     */
    private String authority;

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
     * This method was generated by MyBatis Generator. This method returns the
     * value of the database column public.authorities.authority
     *
     * @return the value of public.authorities.authority
     * @mbggenerated Thu Aug 18 17:31:16 MDT 2011
     */
    public final String getAuthority() {
        return authority;
    }

    /**
     * This method was generated by MyBatis Generator. This method sets the
     * value of the database column public.authorities.authority
     *
     * @param newAuthority
     *            the value for public.authorities.authority
     * @mbggenerated Thu Aug 18 17:31:16 MDT 2011
     */
    public final void setAuthority(final String newAuthority) {
        this.authority = newAuthority;
    }
}