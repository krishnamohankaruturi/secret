package edu.ku.cete.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.api.TimezoneZipCodes;
import edu.ku.cete.model.TimezoneZipCodesMapper;
import edu.ku.cete.service.api.TimezoneZipCodeService;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TimezoneZipCodeServiceImpl implements TimezoneZipCodeService{

	@Autowired
	private TimezoneZipCodesMapper timezoneZipMapper;
	
	@Override
	public Long getOrganizationAddress(String zipcode) {
		TimezoneZipCodes curTimezoneZipCode=timezoneZipMapper.getTimezoneCodeByZIP(zipcode);
		if(curTimezoneZipCode!=null)
			return curTimezoneZipCode.getTimezoneID();
		return null;
	}

}
