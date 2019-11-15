package edu.ku.cete.service.api;

import edu.ku.cete.domain.api.OrganizationAddress;


public interface OrganizationAddressService {
	public OrganizationAddress saveOrganizationAddress(OrganizationAddress organizationAddressObject, Long userID);
	public boolean updateOrganizationAddress(OrganizationAddress organizationAddressObject, Long userID);
	public OrganizationAddress getOrganizationAddress(Long orgId);
}
