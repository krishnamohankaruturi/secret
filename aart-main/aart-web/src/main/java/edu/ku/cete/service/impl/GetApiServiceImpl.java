/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.dataextracts.model.GetApiMapper;
import edu.ku.cete.domain.api.EnrollmentAPIObject;
import edu.ku.cete.domain.api.OrganizationAPIObject;
import edu.ku.cete.domain.api.RosterAPIObject;
import edu.ku.cete.domain.api.StudentAPIObject;
import edu.ku.cete.domain.api.UserAPIObject;
import edu.ku.cete.service.GetApiService;

/**
 * @author Nathan Tipton
 * 
 */
@Service
@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class GetApiServiceImpl implements GetApiService {

	@Autowired
	private GetApiMapper getApiMapper;
	
	private Logger logger = LoggerFactory.getLogger(GetApiServiceImpl.class);
	
	@Override
	public List<StudentAPIObject> getStudents(Map<String, Object> searchParams) {
		logger.debug("externalId: " + searchParams.get("externalId"));
		return getApiMapper.getApiStudents(searchParams);
	}
	
	@Override
	public List<OrganizationAPIObject> getOrgs(Map<String, Object> searchParams) {
		return getApiMapper.getApiOrgs(searchParams);
	}
	
	@Override
	public List<UserAPIObject> getUsers(Map<String, Object> searchParams) {
		return getApiMapper.getApiUsers(searchParams);
	}
	
	@Override
	public List<RosterAPIObject> getApiRosters(Map<String, Object> searchParams) {
		return getApiMapper.getApiRosters(searchParams);
	};
	
	@Override
	public List<EnrollmentAPIObject> getApiEnrollments(Map<String, Object> searchParams) {
		return getApiMapper.getApiEnrollments(searchParams);
	}
	
}