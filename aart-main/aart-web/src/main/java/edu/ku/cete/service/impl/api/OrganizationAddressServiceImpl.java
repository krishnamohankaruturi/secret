package edu.ku.cete.service.impl.api;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.api.OrganizationAddress;
import edu.ku.cete.model.OrganizationAddressMapper;
import edu.ku.cete.service.api.OrganizationAddressService;


@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrganizationAddressServiceImpl implements OrganizationAddressService{

	private Logger logger = LoggerFactory.getLogger(APIDashboardErrorServiceImpl.class);

	@Autowired
	private OrganizationAddressMapper orgAddressMapper;
	
	@Override
	public OrganizationAddress saveOrganizationAddress(OrganizationAddress organizationAddressObject, Long userID) {
		Date now = new Date();
		organizationAddressObject.setCreatedUser(userID);
		organizationAddressObject.setModifiedUser(userID);
		organizationAddressObject.setCreatedDate(now);
		organizationAddressObject.setModifiedDate(now);
		orgAddressMapper.insert(organizationAddressObject);
		return getOrganizationAddress(organizationAddressObject.getOrgID());
	}

	@Override
	public boolean updateOrganizationAddress(OrganizationAddress organizationAddressObject, Long userID) {
		if(organizationAddressObject.getId()!=null) {
			organizationAddressObject.setModifiedUser(userID);
			organizationAddressObject.setModifiedDate(new Date());
			if(orgAddressMapper.updateByPrimaryKey(organizationAddressObject)>0)
				return true;
		}

		return false;
	}

	@Override
	public OrganizationAddress getOrganizationAddress(Long orgId) {
		if(orgId!=null)
			return orgAddressMapper.getAddressForOrganization(orgId);
		return null;
	}

}
