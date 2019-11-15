/**
 * 
 */
package edu.ku.cete.domain.property;

import java.util.Date;

/**
 * @author mahesh
 * Implementing this interface indicates that the underlying object is auditable.
 */
public interface Auditable {
    
    /**
     * @return {@link Date}
     */
    Date getCreatedDate();

    /**
     * @param createdDate {@link Date}
     */
    void setCreatedDate(Date createdDate);

    /**
     * @return {@link Date}
     */
    Date getModifiedDate();

    /**
     * @param modifiedDate {@link Date}
     */
    void setModifiedDate(Date modifiedDate);
    
    /**
     * @return {@link Long}
     */
    Long getCreatedUser();

    /**
     * @param createdUser {@link Long}
     */
    void setCreatedUser(Long createdUser);
   
    /**
     * @return {@link Long}
     */
    Long getModifiedUser();

    /**
     * @param modifiedUser {@link Long}
     */
    void setModifiedUser(Long modifiedUser);

    /**
     * @return {@link Boolean}
     */
    Boolean getActiveFlag();

    /**
     * @param activeFlag {@link Boolean}
     */
    void setActiveFlag(Boolean activeFlag);
    /**
     * @param currentContextUserId {@link Long}
     */
    void setCurrentContextUserId(Long currentContextUserId);
	/**
     * @return {@link Long}
     */
    Long getCurrentContextUserId();
    /**
     * set the audit column properties for Insert
     */
    public void setAuditColumnProperties();
    
    /**
     * set the audit column properties for Update
     */
    public void setAuditColumnPropertiesForUpdate();
    
    /**
     * set the audit column properties for Delete
     */
    public void setAuditColumnPropertiesForDelete();
}
