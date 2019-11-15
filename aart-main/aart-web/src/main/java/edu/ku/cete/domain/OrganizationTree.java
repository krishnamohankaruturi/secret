/**
 * 
 */
package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.util.AARTCollectionUtil;

/**
 * @author m802r921
 * This object is NOT to be stored in session.
 * 1. Once the organization tree is set this class indicates if any organization is part of the tree or not.
 * This is used by uploads so that all records not part of user's organization tree will be rejected quickly.
 * 2. Also stores organizations that are not unique by display identifier.
 * 3. Does not take in to account the position of child or parent with in the tree.
 */
public class OrganizationTree implements Serializable {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -736902035467767986L;
	/**
	 * organizationMap.
	 */
	private Map<String, Organization> organizationMap = new HashMap<String, Organization>();
	/**
	 * organizationMap.
	 */
	private Map<String, List<Organization>> duplicateOrganizationMap = new HashMap<String, List<Organization>>();
	/**
	 * LOGGER.
	 */
	private static final Log LOGGER = LogFactory.getLog(OrganizationTree.class);
	/**
	 * @param organization {@link Organization}
	 */
	public final void addOrganization(Organization organization) {
		if (organization != null
				&& organization.getDisplayIdentifier() != null
				&& StringUtils.hasText(organization.getDisplayIdentifier())) {
			Organization foundOrganization = organizationMap.get(organization.getDisplayIdentifier());			
			if (
					organizationMap.containsKey(organization.getDisplayIdentifier()) &&
					foundOrganization != null &&
					foundOrganization.getId() != organization.getId()
					) {
				//upload does not know which organization to tie it to because,
				//2 organizations match the same display identifier and parent organization tree needs to be
				//indicated in the upload file in order to uniquely find the organization.
				LOGGER.debug(" Potentially duplicate identifier set on organization "
			+ organization.getDisplayIdentifier());
				addDuplicateOrganization(foundOrganization);
				addDuplicateOrganization(organization);
				//TODO this will have an impact
				organizationMap.remove(organization.getDisplayIdentifier());
			} else {
				organizationMap.put(organization.getDisplayIdentifier(), organization);
			}
		}
	}
	/**
	 * After verifying that there is an other organization with the same identifier but
	 * they have different ids then call this method.
	 * @param organization {@link Organization}
	 */
	public final void addDuplicateOrganization(Organization organization) {
		if (organization != null
				&& organization.getDisplayIdentifier() != null
				&& StringUtils.hasText(organization.getDisplayIdentifier())) {
			List<Organization> foundDuplicateOrganizations = duplicateOrganizationMap.get(organization.getDisplayIdentifier());			
			if (
					duplicateOrganizationMap.containsKey(organization.getDisplayIdentifier()) &&
					foundDuplicateOrganizations != null &&
					CollectionUtils.isNotEmpty(foundDuplicateOrganizations)
					) {
				foundDuplicateOrganizations.add(organization);
			} else {
				foundDuplicateOrganizations = new ArrayList<Organization>();
				foundDuplicateOrganizations.add(organization);
				duplicateOrganizationMap.put(organization.getDisplayIdentifier(), foundDuplicateOrganizations);
			}
		}
	}	
	/**
	 * @param organizations {@link Organization}
	 */
	public final void addOrganizations(List<Organization> organizations) {
		if (organizations != null && CollectionUtils.isNotEmpty(organizations)) {
			for (Organization organization:organizations) {
				addOrganization(organization);
			}
		}
	}
	/**
	 * return the organization if present in the context of the user.
	 * @param displayIdentifier {@link String}
	 * @return {@link Organization}
	 */
	public final Organization getOrganization(String displayIdentifier) {
		Organization organization = null;
		//check the non duplicate map.
		if (displayIdentifier != null && StringUtils.hasText(displayIdentifier)) {
			organization = organizationMap.get(displayIdentifier);
		}
		if (displayIdentifier != null
				&& StringUtils.hasText(displayIdentifier)
				&& organization == null
				&& duplicateOrganizationMap.containsKey(displayIdentifier)
				) {
			//it should never have empty lists.Minimum expected is 2.
			LOGGER.debug("This display identifier has multiple organizations " + displayIdentifier);
			organization = duplicateOrganizationMap.get(displayIdentifier).get(0);
		}
		return organization;
	}
	
	/**
	 * @param displayIdentifier
	 * @return
	 */
	public boolean isDuplicate(String displayIdentifier) {
		boolean isDuplicate = false;
		if (displayIdentifier != null && StringUtils.hasText(displayIdentifier)) {
			isDuplicate = duplicateOrganizationMap.containsKey(displayIdentifier);
		}
		return isDuplicate;
	}
	/**
	 * @return
	 */
	public final List<Organization> getUserOrganizationList() {
		List<Organization> organizations = new ArrayList<Organization>();
		organizations.addAll(getUserOrganizationTree());
		return organizations;
	}	
	/**
	 * @return
	 */
	public final Collection<Organization> getUserOrganizationTree() {
		Collection<Organization> organizations = organizationMap.values();
		if(organizations == null || CollectionUtils.isEmpty(organizations)) {
			organizations = new ArrayList<Organization>();
		}
		return organizations;
	}
	/**
	 * TODO why not store the ids once and not form it every time.
	 * @return
	 */
	public final Collection<Long> getUserOrganizationIds() {
		return AARTCollectionUtil.getIds(organizationMap.values());
	}
	/**
	 * TODO why not store the ids once and not form it every time.
	 * @return
	 */
	public final List<Long> getUserOrganizationListIds() {
		return AARTCollectionUtil.getIds(organizationMap.values());
	}	
}
