package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.security.Restriction;

/**
 * @author m802r921
 *
 */
public interface ResourceRestrictionService {

	/**
	 * get the resource restrictions with respective permissions
	 *  for the given organization,
	 * with one or all of the given permissions and the given
	 * differential permission (if one is required) for
	 *  the given record type.
	 *
	 * @param userOrganizationId {@link Long}
	 * @param permissionIds {@link List}
	 * @param differentialPermissionId {@link Long}
	 * @param restrictedResourceTypeId {@link Long}
	 * @return {@link List}
	 */
	List<Restriction> getResourceRestrictions(
			long userOrganizationId, List<Long> permissionIds,
			Long differentialPermissionId,
			long restrictedResourceTypeId);
	/**
	 *
	 * Get the resource restriction for the given
	 * organization, with the given permission, and
	 * given differential permission (if one is required)
	 * for the given record type.
	 *
	 * @param userOrganizationId {@link Long}
	 * @param permissionId {@link Long}
	 * @param restrictedResourceTypeId {@link Long}
	 * @return {@link Restriction}
	 */
	Restriction getResourceRestriction(
			long userOrganizationId, long permissionId,
			long restrictedResourceTypeId);
	
	/**
	 * Get the resource restriction for the given
	 * organization, with the given permission, and
	 * given differential permission (if one is required)
	 * for the given record type.
	 * 
	 * @param userOrganizationId {@link Long}
	 * @param permissionId {@link Long}
	 * @param differentialPermissionId {@link Long}
	 * @param restrictedResourceTypeId {@link Long}
	 * @return {@link Restriction}
	 */
	Restriction getResourceRestriction(long userOrganizationId,
			Long permissionId, Long differentialPermissionId,
			long restrictedResourceTypeId);
}
