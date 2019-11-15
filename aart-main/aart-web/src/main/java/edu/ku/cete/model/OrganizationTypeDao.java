package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.common.OrganizationType;

public interface OrganizationTypeDao {

    int add(OrganizationType organizationType);
    
    void update(OrganizationType organizationType);
    
    void delete(long organizationTypeId);
    
    OrganizationType get(@Param("organizationTypeId")long organizationTypeId);
    
    OrganizationType getByTypeCode(@Param("typeCode")String typeCode);
    
    OrganizationType getByTypeCodeCaseInsensitive(@Param("typeCode")String typeCode);
    
    List<OrganizationType> getAll();

	List<OrganizationType> getOrgHierarchyByOrg(@Param("organizationId") Long organizationId);

	OrganizationType getByLevel(@Param("level") int level);
}
