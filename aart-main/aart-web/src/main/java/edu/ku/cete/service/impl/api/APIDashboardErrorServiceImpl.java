package edu.ku.cete.service.impl.api;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.model.APIDashboardErrorMapper;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.util.CommonConstants;


@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class APIDashboardErrorServiceImpl implements APIDashboardErrorService {
	
	private Logger logger = LoggerFactory.getLogger(APIDashboardErrorServiceImpl.class);
	
	@Autowired
	private APIDashboardErrorMapper errorMapper;
	
	@Autowired
	private OrganizationDao orgDao;
	
	
	@Override
	public APIDashboardError buildDashboardError(ApiRequestTypeEnum requestType, ApiRecordTypeEnum recordType, String externalId, String name, Organization org, Long classroomId, Long userId, String message) {
		APIDashboardError error = new APIDashboardError();
		error.setRecordType(recordType.toString());
		error.setRequestType(requestType.toString());
		error.setExternalID(externalId);
		error.setName(name);
		error.setClassroomID(classroomId);
		error.setMessage(message);
		Date now = new Date();
		error.setCreatedDate(now);
		error.setModifiedDate(now);
		error.setCreatedUser(userId);
		error.setModifiedUser(userId);
		if (org != null) {
			if (org.getOrganizationType() != null) {
				if (CommonConstants.ORGANIZATION_SCHOOL_CODE.equals(org.getOrganizationType().getTypeCode())) {
					error.setSchoolID(org.getId());
				}else if (CommonConstants.ORGANIZATION_DISTRICT_CODE.equals(org.getOrganizationType().getTypeCode())) {
					error.setDistrictID(org.getId());
				}else if (CommonConstants.ORGANIZATION_STATE_CODE.equals(org.getOrganizationType().getTypeCode())) {
					error.setStateID(org.getId());
				}
			}
			List<Organization> parents = orgDao.getAllParents(org.getId());
			if (CollectionUtils.isNotEmpty(parents)) {
				for (Organization parent : parents) {
					if (CommonConstants.ORGANIZATION_DISTRICT_CODE.equals(parent.getOrganizationType().getTypeCode())) {
						error.setDistrictID(parent.getId());
					}
					if (CommonConstants.ORGANIZATION_STATE_CODE.equals(parent.getOrganizationType().getTypeCode())) {
						error.setStateID(parent.getId());
					}
				}
			}
		}
		return error;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean insertErrors(List<APIDashboardError> errors) {
		if (CollectionUtils.isNotEmpty(errors)) {
			Date now = new Date();
			for (APIDashboardError error : errors) {
				error.setCreatedDate(now);
				error.setModifiedDate(now);
				logger.debug(error.getMessage());
				errorMapper.insert(error);
			}
		}
		return true;
	}
}