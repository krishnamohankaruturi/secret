/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.security.ResourceRestriction;
import edu.ku.cete.domain.security.ResourceRestrictionExample;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.security.ResourceRestrictionDao;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;

/**
 * @author m802r921
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ResourceRestrictionServiceImpl implements ResourceRestrictionService {

	/**
	 * resourceRestrictionDao.
	 */
	@Autowired
	private ResourceRestrictionDao resourceRestrictionDao;
	/**
	 * authoritiesDao.
	 */
	@Autowired
	private AuthoritiesDao authoritiesDao;
    /**
     * permissionUtil.
     */
    @Autowired
    private PermissionUtil permissionUtil;
	/**
	 * restrictedResourceConfiguration.
	 */
	@Autowired
	RestrictedResourceConfiguration	restrictedResourceConfiguration;
	/**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(ResourceRestrictionServiceImpl.class);
	/**
	 * @param organizationId {@link Long}
	 * @param permissionIds {@link List}
	 * @param isParent {@link Boolean}
	 * @param isChild {@link Boolean}
	 * @param restrictedResourceTypeId {@link Long}
	 * @param differentialPermissionId {@link Long}
	 * @return {@link ResourceRestrictionExample}
	 */
	private ResourceRestrictionExample getResourceRestrictionExample(long organizationId,
			List<Long> permissionIds,
			boolean isParent,
			boolean isChild,
			Long differentialPermissionId,
			long restrictedResourceTypeId) {
		ResourceRestrictionExample resourceRestrictionExample = new ResourceRestrictionExample();
		ResourceRestrictionExample.Criteria resourceRestrictionCriteria
		= resourceRestrictionExample.createCriteria();
		resourceRestrictionCriteria.andIsParentEqualTo(isParent);
		resourceRestrictionCriteria.andIsChildEqualTo(isChild);
		if (isParent) {
			resourceRestrictionCriteria
					.andParentOrganizationIdIn(organizationId);
		} else if (isChild) {
			resourceRestrictionCriteria
			.andChildOrganizationIdIn(organizationId);
		} else {
			resourceRestrictionCriteria
			.andOrganizationIdEqualTo(organizationId);
		}
		resourceRestrictionCriteria.andAuthorityIdIn(permissionIds);
		resourceRestrictionCriteria.andDifferentialAuthorityIdEqualTo(differentialPermissionId);
		resourceRestrictionCriteria.andRestrictedResourceTypeIdEqualTo(restrictedResourceTypeId);
		return resourceRestrictionExample;
	}
	
	/**
	 * @param resourceRestrictions {@link List}
	 * @return {@link List}
	 */
	private List<Restriction> convertResourceRestrictions(List<ResourceRestriction> resourceRestrictions) {
		Map<Long, Restriction> restrictionMap = new HashMap<Long, Restriction>();
		//resource restrictions is not null so null check is not necessary.
		for (ResourceRestriction resourceRestriction : resourceRestrictions) {
			if (restrictionMap.containsKey(resourceRestriction.getRestrictionId())) {
				Restriction restriction = restrictionMap.get(resourceRestriction.getRestrictionId());
				restriction.mergeRestriction(resourceRestriction.getRestriction());
			} else {
				restrictionMap.put(resourceRestriction.getRestrictionId(),
						resourceRestriction.getRestriction());
			}
		}
		Collection<Restriction> restrictions = restrictionMap.values();
		List<Restriction> restrictionList = new ArrayList<Restriction>();
		//TODO is there not a better way to convert in to a list ?
		for (Restriction restriction:restrictions) {
			restrictionList.add(restriction);
		}
		return restrictionList;
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Restriction> getResourceRestrictions(
			long organizationId, List<Long> permissionIds,
			Long differentialPermissionId,
			long restrictedResourceTypeId
			) {
		List<Restriction> restrictions = new ArrayList<Restriction>();

		try {
			List<ResourceRestriction> resourceRestrictions = new ArrayList<ResourceRestriction>();
			List<ResourceRestriction> orgResourceRestrictions = resourceRestrictionDao
					.selectByExample(getResourceRestrictionExample(
							organizationId, permissionIds, false, false,
							differentialPermissionId, restrictedResourceTypeId));
			if (orgResourceRestrictions != null
					&& CollectionUtils.isNotEmpty(orgResourceRestrictions)) {
				resourceRestrictions.addAll(orgResourceRestrictions);
			}
			//Restrictions a.k.a permissions i inherit as a child of the restricted organization  i.e.
			//I am(my organization) the child of one of the restricted organization.
			//They are given by my parents (parent organization) hence called parent resource restrictions.
			List<ResourceRestriction> parentResourceRestrictions = resourceRestrictionDao
					.selectByExample(getResourceRestrictionExample(
							organizationId, permissionIds, false, true,
							differentialPermissionId, restrictedResourceTypeId));
			if (parentResourceRestrictions != null
					&& CollectionUtils.isNotEmpty(parentResourceRestrictions)) {
				resourceRestrictions.addAll(parentResourceRestrictions);
			}
			//Restrictions i.e. permissions i am bestowed as a parent of the restricted organization  i.e. I am (my organization)
			// is one of the parent of the restricted organization.
			// They are resources that belong to my children so they are called child resource restrictions.
			List<ResourceRestriction> childResourceRestrictions = resourceRestrictionDao
					.selectByExample(getResourceRestrictionExample(
							organizationId, permissionIds, true, false,
							differentialPermissionId, restrictedResourceTypeId));
			if (childResourceRestrictions != null
					&& CollectionUtils.isNotEmpty(childResourceRestrictions)) {
				resourceRestrictions.addAll(childResourceRestrictions);
			}
			//Doing it this way eliminates the null checks.
			restrictions = this.convertResourceRestrictions(resourceRestrictions);
		} catch (Exception e) {
			LOGGER.error(" Error in getting resource restrictions for organizationId " + organizationId
					+ " permissionIds " + permissionIds + " differentialPermissionId "
					+ differentialPermissionId
					+ "restrictedResourceTypeId " + restrictedResourceTypeId, e);
		}
		return restrictions;
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Restriction getResourceRestriction(long userOrganizationId,
			Long permissionId, Long differentialPermissionId,
			long restrictedResourceTypeId) {
		List<Long> permissionIds = new ArrayList<Long>();
		Restriction restriction = null;
		try {
			List<Restriction> restrictions = null;
			//if this permission is empty then the search will be empty, so why search ?
			if (permissionId != null && permissionId.longValue() > 0) {
				permissionIds.add(permissionId);
				restrictions = getResourceRestrictions(userOrganizationId,
						permissionIds, differentialPermissionId,
						restrictedResourceTypeId);
				//no need to do null check as the method returns empty list if nothing is found.
				if (restrictions.size() != 1) {
					//more than on restriction is found and this feature is not available yet.
					LOGGER.debug(" More than one resource restrictions for userOrganizationId "
					+ userOrganizationId
							+ " permissionIds " + permissionIds + " differentialPermissionId "
							+ differentialPermissionId
							+ "restrictedResourceTypeId " + restrictedResourceTypeId);
					} else {
						restriction = restrictions.get(0);
					}
				}
		} catch (Exception e) {
			// This way not a lot of if else checks.
			LOGGER.error(" Error in getting resource restrictions for userOrganizationId " + userOrganizationId
					+ " permissionIds " + permissionIds + " differentialPermissionId "
					+ differentialPermissionId
					+ "restrictedResourceTypeId " + restrictedResourceTypeId, e);
		}
		return restriction;
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Restriction getResourceRestriction(long userOrganizationId,
			long permissionId, long restrictedResourceTypeId) {
		return getResourceRestriction(
				userOrganizationId, permissionId, null, restrictedResourceTypeId);
	}
}
