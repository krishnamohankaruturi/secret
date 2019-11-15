package edu.ku.cete.domain.audit;

import java.io.Serializable;
import java.util.Date;

import org.springframework.security.core.context.SecurityContextHolder;

import edu.ku.cete.domain.property.Auditable;
import edu.ku.cete.domain.user.UserDetailImpl;

/**
 * @author sureshmuthu
 * TODO declare this as an abstract class.
 * TODO Remove references to security context holder and UserDetailsImpl
 */
public class AuditableDomain implements Auditable, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 9173551892379128940L;

	/**
     * createDate
     */
    private Date createdDate;
    
    /**
     * modifiedDate
     */
    private Date modifiedDate;
    
    /**
     * createdUser
     */
    private Long createdUser;

    /**
     * modifiedUser
     */
    private Long modifiedUser;
   
    /**
     * activeFlag
     */
    private Boolean activeFlag;
    
    /**
     * @return {@link Date}
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate {@link Date}
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return {@link Date}
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * @param modifiedDate {@link Date}
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
    
    /**
     * @return {@link Long}
     */
    public Long getCreatedUser() { 
        return createdUser;
    }

    /**
     * @param createdUser {@link Long}
     */
    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }
   
    /**
     * @return {@link Long}
     */
    public Long getModifiedUser() { 
        return modifiedUser;
    }

    /**
     * @param modifiedUser {@link Long}
     */
    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    /**
     * @return {@link Boolean}
     */
    public Boolean getActiveFlag() { 
        return activeFlag;
    }

    /**
     * @param activeFlag {@link Boolean}
     */
    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }
     
    
    /**
     * currentContextUserId
     */
    private Long currentContextUserId = null;
    
    /**
     * @param currentContextUserId {@link Long}
     */
    public void setCurrentContextUserId(Long currentContextUserId) {
		this.currentContextUserId = currentContextUserId;
	}

	/**
     * @return {@link Long}
     */
    public Long getCurrentContextUserId(){
    	if(currentContextUserId == null && SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailImpl){
    		currentContextUserId
    		= ((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId(); 
    	}
    	return currentContextUserId;
    }

    /**
     * set the audit column properties for Insert
     */
    public void setAuditColumnProperties(){
    	
    	Long userId = null;
    	if( getCreatedUser() == null || getModifiedUser() == null)
    		userId = getCurrentContextUserId();
    	
    	Date now = new Date();
    	if (getCreatedDate() == null) {
    		setCreatedDate(now);
    	}
    	if (getCreatedUser() == null) {
    		setCreatedUser(userId);
    	}
    	if (getModifiedDate() == null) {
    		setModifiedDate(now);
    	}
    	if (getModifiedUser() == null) {
    		setModifiedUser(userId);
    	}
    	if(getActiveFlag() == null) {
        	setActiveFlag(true);
    	}
    }
    
    /**
     * set the audit column properties for Update
     */
    public void setAuditColumnPropertiesForUpdate(){ 
    	setModifiedDate(new Date());
    	setModifiedUser(getCurrentContextUserId()); 
    	if(getActiveFlag() == null) {
        	setActiveFlag(true);
    	}
    }
    
    /**
     * set the audit column properties for Delete
     */
    public void setAuditColumnPropertiesForDelete(){ 
    	setModifiedDate(new Date());
    	setModifiedUser(getCurrentContextUserId()); 
    	if(getActiveFlag() == null) {
        	setActiveFlag(false);
    	}
    }
}
