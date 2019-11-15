package edu.ku.cete.domain;

import java.io.Serializable;

import org.springframework.core.style.ToStringCreator;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author mrajannan
 */
public class Policy extends AuditableDomain implements Serializable {

    /** Generated serialVersionUID. */
    private static final long serialVersionUID = 8084473849970627221L;

    /** The id. */
    private Long id;

    /** The name. */
    private String name;

    /** The organizationId. */
    private long organizationId;

    /** The data. */
    private String data;

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
     * @return the firstName
     */
    public final String getName() {
        return name;
    }

    /**
     * @param newName
     *            the name of the tool to set
     */
    public final void setName(final String newName) {
        this.name = newName;
    }

    /**
     * @return the organizationId
     */
    public final long getOrganizationId() {
        return organizationId;
    }

    /**
     * @param newOrganizationId the organizationId to set
     */
    public final void setOrganizationId(final long newOrganizationId) {
        this.organizationId = newOrganizationId;
    }

    /**
     * @return the value
     */
    public final String getData() {
        return data;
    }

    /**
     * @param newValue the value to set
     */
    public final void setData(final String newData) {
        this.data = newData;
    }

    /**
     * Convert object to a String.
     * @return {@link String} representation of object.
     */
    public final String toString() {
        return new ToStringCreator(this).append("id", id).append("organizationId", organizationId).append("name", name)
                .append("data", data).toString();
    }
}
