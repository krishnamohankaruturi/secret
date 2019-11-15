package edu.ku.cete.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.service.OrganizationService;

public class BaseController {
	@Autowired
	public OrganizationService organizationService;

	public Long getStateId(Long organizationId) {
		List<Organization> parents = organizationService.getAllParents(organizationId);
		Long stateId = null;
		// in the case the user is a state level user add the state
		Organization defaultOrg = organizationService.get(organizationId);
		if (defaultOrg != null) {
			parents.add(defaultOrg);
		}
		for (Organization org : parents) {
			if (org.getOrganizationType() != null && org.getOrganizationType().getTypeCode() != null
					&& org.getOrganizationType().getTypeCode().equals("ST")) {
				stateId = org.getId();
				break;
			}
		}
		return stateId;
	}
}
