package edu.ku.cete.domain.user;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * This class is used by spring security to represent the logged in user, and it contains the user's permissions and details
 * about the user.
 * @author neil.howerton
 *
 */
public class UserDetailImpl implements UserDetails {
	/**
	 * Logger for class.
	 */
	public static final Logger LOGGER = LoggerFactory.getLogger(UserDetailImpl.class);
    private static final long serialVersionUID = 5129167533026906483L;
    private final User user;
    private boolean isSSO;

    /**
     * Constructor.
     * @param user {@link User}
     */
    public UserDetailImpl(User user) {
        this.user = user;
        //set by default and override in the authentication provider for SSO.
        this.isSSO = false;
        LOGGER.debug("UserDetailImpl: "+user.getCurrentOrganizationId());
        LOGGER.debug("UserDetailImpl: "+user.getCurrentGroupsId());
        LOGGER.debug("UserDetailImpl: "+user.getDefaultOrganizationId());
        LOGGER.debug("UserDetailImpl: "+user.getDefaultUserGroupsId());
		//user.resetAuthorities();
    }

    /**
     * The GrantedAuthorities are a user's granted permssions within the system.
     */
    public Collection<GrantedAuthority> getAuthorities() {
    	Collection<GrantedAuthority> authorities = user.getAuthorities();
    	LOGGER.debug("authorities: "+authorities);
        return authorities;
    }

    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getUserName();
    }

    public boolean isAccountNonExpired() {
        return user.isAccountNonExpired();
    }

    public boolean isAccountNonLocked() {
        return user.isAccountNonLocked();
    }

    public boolean isCredentialsNonExpired() {
        return user.isCredentialsNonExpired();
    }

    public long getDefaultUserGroupsId() {
        return user.getDefaultUserGroupsId();
    }

    /**
     *
     *@return
     */
    public final long getUserId() {
        return user.getId();
    }

    /**
     *
     *@return {@link String}
     */
    public final String getEmail() {
        return user.getEmail();
    }

    public final String getSalt() {
        return user.getSalt();
    }

    /**
     * This object cannot be null.
     * If it is null means login has failed.
     *@return
     */
    public final User getUser() {
        return user;
    }

    //if statusCode is Inactive or Pending then user should unable to login 
    public boolean isEnabled() {
    	return user.isEnabled();        
    }

    @Override
	public int hashCode() {
		int hash = 0;
		hash += (user.getId()!= null ? user.getId().hashCode() : 0);
		return hash;
	}
	
	@Override
	public boolean equals(Object object) {
		
		if (!(object instanceof UserDetailImpl)) {
			return false;
		}

		UserDetailImpl other = (UserDetailImpl) object;

		if ((getUser().getId() == null && other.getUser().getId() != null)
				|| (getUser().getId() != null && !getUser().getId().equals(other.getUser().getId()))) {
			return false;
		}
		return true;
	}

	public boolean isSSO() {
		return isSSO;
	}
	
	public void setIsSSO(boolean isSSO) {
		this.isSSO = isSSO;
	}
}
