package edu.ku.cete.model;

import edu.ku.cete.domain.api.OrganizationAddress;

public interface OrganizationAddressMapper {
    
 	int insert(OrganizationAddress record);

    int insertSelective(OrganizationAddress record);


    OrganizationAddress selectByPrimaryKey(Long id);

    OrganizationAddress getAddressForOrganization(Long orgid);
    
    int update(OrganizationAddress record);

    int updateByPrimaryKey(OrganizationAddress record);
}