package edu.ku.cete.service.api;

import java.util.Map;

import edu.ku.cete.domain.api.OrganizationAPIObject;

public interface OrganizationAPIService {
	public Map<String, Object> postOrganization(Map<String, Object> response,
			OrganizationAPIObject organizationAPIObject);

	public Map<String, Object> putOrganization(Map<String, Object> response,
			OrganizationAPIObject organizationAPIObject);

	public Map<String, Object> deleteOrganization(Map<String, Object> response,
			OrganizationAPIObject organizationAPIObject);

}