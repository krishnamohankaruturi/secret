package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.common.OrganizationType;

public interface OrganizationTypeService {

    OrganizationType add(OrganizationType organizationType);
    
    void update(OrganizationType organizationType);
    
    void delete(long organizationTypeId);
    
    OrganizationType get(long organizationTypeId);
    
    OrganizationType getByTypeCode(String typeCode);
    
    OrganizationType getByTypeCodeCaseInsensitive(String typeCode);
    
    List<OrganizationType> getAll();

	List<OrganizationType> getOrgHierarchyByOrg(Long organizationId);

	OrganizationType getByTypeLevel(int level);
}
