/**
 * 
 */
package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.Authorities;

/**
 * @author neil.howerton
 *
 */
@Component
public class PermissionUtil {

    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(PermissionUtil.class);
    /**
     * Checks a permissions against a user's granted permissions. Returns true if the user's granted permissions
     * contains the permission.
     *@param authorities List<GrantedAuthority>
     *@param permissionToCheck {@link String}
     *@return boolean - whether the list of authorities contains the specified permission.
     */
    public final boolean hasPermission(List<GrantedAuthority> authorities, String permissionToCheck) {
        boolean hasPermission = false;

        for (int i = 0, length = authorities.size(); i < length && !hasPermission; i++) {
            if (authorities.get(i).getAuthority().equals(permissionToCheck)) {
                hasPermission = true;
            }
        }

        return hasPermission;
    }

    /**
     * Checks a list of permissions against a user's granted permissions. Returns true if the user's granted permissions
     * contains at least one of the permissions listed in the permissionList.
     *@param authorities List<GrantedAuthority> - List of permissions a user has.
     *@param permissionList List<String> - List of permissions to be checked against the user's permissions
     *@return boolean - whether the user has at least one permission from the permissions list.
     */
    public final boolean hasAPermissionFromList(List<GrantedAuthority> authorities, List<String> permissionList) {
        boolean hasPermission = false;

        for (int j = 0, jLen = permissionList.size(); j < jLen && !hasPermission; j++) {
            hasPermission = hasPermission(authorities, permissionList.get(j));
        }

        return hasPermission;
    }

    /**
     * Checks a list of permissions against a user's granted permissions. Returns true only if the user's granted permissions
     * contains all of the permissions listed in the permissionList.
     *@param authorities List<GrantedAuthority> - List of permissions a user has.
     *@param permissionList List<String> - List of permissions to be checked against the user's permissions
     *@return boolean - whether the user has all the permission provided as parameters.
     */
    public final boolean hasAllPermissionsFromList(List<GrantedAuthority> authorities, List<String> permissionList) {
        boolean hasPermission = true;

        for (int i = 0, length = authorities.size(); i < length && hasPermission; i++) {
            boolean found = false;
            for (int j = 0, jLen = permissionList.size(); j < jLen && !found; j++) {
                if (authorities.get(i).getAuthority().equals(permissionList.get(j))) {
                    found = true;
                }
            }

            if (!found) {
                hasPermission = false;
            }
        }

        return hasPermission;
    }

	/**
	 * @param authoritiesList {@link List}
	 * @param permissionToCheck {@link String}
	 * @return {@link Authorities}
	 */
	public final Authorities getAuthority(List<Authorities> authoritiesList, String permissionToCheck) {
		LOGGER.debug("Checking " + permissionToCheck + " in " + authoritiesList);
		try {
			for (Authorities authority:authoritiesList) {
			    if (authority.getAuthority().equals(permissionToCheck)) {
					LOGGER.debug("Found " + permissionToCheck + " in the list ");
			        return authority;
			    }
			}
		} catch (Exception e) {
			// This way null check is not necessary
			LOGGER.error("Error in getting authority", e);
		}
		LOGGER.debug("NOT Found " + permissionToCheck + " in " + authoritiesList);
		return null;
	}
	/**
	 * @param authoritiesList {@link List}
	 * @param permissionToCheck {@link String}
	 * @return {@link Authorities}
	 */
	public final Long getAuthorityId(List<Authorities> authoritiesList, String permissionToCheck) {
		LOGGER.debug("Checking " + permissionToCheck + " in the list ");
		return getAuthorityId(getAuthority(authoritiesList, permissionToCheck));
	}

	/**
	 * @param authoritiesList {@link List}
	 * @return {@link List}
	 */
	public final List<Long> getAuthorityIds(List<Authorities> authoritiesList) {
		List<Long> authorityIds = new ArrayList<Long>();
		try {
			if (authoritiesList != null && CollectionUtils.isNotEmpty(authoritiesList))  {
				for (Authorities authority:authoritiesList) {
					authorityIds.add(authority.getAuthoritiesId());
				}
			}
		} catch (Exception e) {
			//this way null check is not necessary.
			LOGGER.error("Error in getting authority id from authority object", e);
		}
		return authorityIds;
	}

	public final Long getAuthorityId(Authorities differentialAuthority) {
		LOGGER.debug("get id for " + differentialAuthority);
		return (differentialAuthority != null
				? differentialAuthority.getAuthoritiesId() : null);
	}

	public boolean hasPermission(Collection<GrantedAuthority> authorities,
			String permissionToCheck) {
        List<GrantedAuthority> grantedAuthoritiesList = new ArrayList<GrantedAuthority>();
        if(authorities != null) {
            grantedAuthoritiesList.addAll(authorities);
        }
        return hasPermission(grantedAuthoritiesList, permissionToCheck);
    }
}
