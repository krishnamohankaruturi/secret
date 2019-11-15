package edu.ku.cete.service.impl.report;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.upload.UploadedOrganization;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.util.SourceTypeEnum;

@Service
public class OrganizationDeleteUploadWriterProcessServiceImpl implements BatchUploadWriterService {

	final static Log logger = LogFactory.getLog(OrganizationDeleteUploadWriterProcessServiceImpl.class);

	@Autowired
	OrganizationDao organizationDao;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;

	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug(" Org writter ");
		for (Object object : objects) {
			UploadedOrganization uploadedOrg = (UploadedOrganization) object;
			deleteUploadedOrganization(uploadedOrg,
					uploadedOrg.getOrganizationTypeCode().toUpperCase());
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final Organization deleteUploadedOrganization(UploadedOrganization uploadedOrg, String organizationTypeCode) {
		Organization organization = uploadedOrg.getOrganization();
		Long contractingOrgId = uploadedOrg.getStateId();
		logger.debug("Entering the deleteUploadedOrganization with - " + organization.getId() + ","
				+ organization.getOrganizationTypeId() + "," + organization.getRelatedOrganizationId() + ","
				+ organizationTypeCode);
		Date now = new Date();
		String beforeUpdate = null;
		String afterUpdate = null;
		Organization org = organizationService.getByDisplayIdAndType(organization.getDisplayIdentifier(), 
				organization.getOrganizationType().getOrganizationTypeId(), contractingOrgId);
		if(org != null){
			organization.setId(org.getId());
			if (organization.getActiveFlag() == null)
				organization.setActiveFlag(true);
			if (organization.getCreatedDate() == null)
				organization.setCreatedDate(now);
			if (organization.getModifiedDate() == null)
				organization.setModifiedDate(now);

			if (organization.getId() != null) {
				organization = organizationService.get(organization.getId());
				beforeUpdate = organization.buildJsonString();
				organization.setAuditColumnPropertiesForDelete();

				try {
					if (organizationTypeCode.equals("DT")) {
						organizationService.disableDistrict(organization.getId());
					} else if (organizationTypeCode.equals("SCH")) {
						organizationService.disableSchool(organization.getId());
					} else {
						organizationDao.deleteOrganization(organization);
					}
				} catch (Exception e) {
					logger.error("Exception while disabling organization", e);
				}
			} else {
				// Do nothing.
			}

			organization = organizationDao.get(organization.getId());
			afterUpdate = organization.buildJsonString();

			DomainAuditHistory domainAuditHistory = new DomainAuditHistory();

			domainAuditHistory.setSource(SourceTypeEnum.UPLOAD.getCode());
			domainAuditHistory.setObjectType("ORGANIZATION");
			domainAuditHistory.setObjectId(organization.getId());
			domainAuditHistory.setCreatedUserId(organization.getModifiedUser().intValue());
			domainAuditHistory.setCreatedDate(new Date());
			domainAuditHistory.setAction("DELETE");
			domainAuditHistory.setObjectBeforeValues(beforeUpdate);
			domainAuditHistory.setObjectAfterValues(afterUpdate);

			domainAuditHistoryDao.insert(domainAuditHistory);
			logger.debug("Exit Delete Organization with - " + organization.getId() + "," + organization.getTypeCode() + ","
					+ organization.getRelatedOrganizationId());
		} else {
			// Do nothing. By this time the existence of the organization in system
			// should be handled by custom validation.
		}
		return organization;
	}
}
