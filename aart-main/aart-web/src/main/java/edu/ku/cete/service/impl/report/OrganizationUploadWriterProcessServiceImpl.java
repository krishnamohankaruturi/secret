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
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.util.SourceTypeEnum;

/**
 *  Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(Organization)
 */
@Service
public class OrganizationUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(OrganizationUploadWriterProcessServiceImpl.class);

	//@Autowired
	//private OrganizationService orgService;
	
	@Autowired
	private OrganizationDao organizationDao;
	
	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug(" Org writter ");
		for( Object object : objects){
			UploadedOrganization uploadedOrg = (UploadedOrganization) object;
			addorUpdateOrganizationUpload(uploadedOrg,null);
		} 
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private final Organization addorUpdateOrganizationUpload(UploadedOrganization uploadedOrg, Long oldParentOrganizationId) {
		Organization organization = uploadedOrg.getOrganization();
		Long contractingOrgId = uploadedOrg.getStateId();
    	logger.debug("Entering the addorUpdateOrganization with - " + organization.getId() + ","
    			+ organization.getOrganizationTypeId() + "," + organization.getRelatedOrganizationId() + "," + oldParentOrganizationId);
    	Date now = new Date();
    	boolean isInsert = true;
    	String beforeUpdate = null;
    	String afterUpdate = null;
    	
    	// FIX for organization id is not populating if it already exists.
    	Organization org = organizationService.getByDisplayIdAndType(organization.getDisplayIdentifier(), 
				organization.getOrganizationType().getOrganizationTypeId(), contractingOrgId);
		if(org != null){
			organization.setId(org.getId());
		}
    	
    	if( organization.getActiveFlag() == null)
    		organization.setActiveFlag(true);
    	if( organization.getCreatedDate() == null)
    		organization.setCreatedDate(now);
    	if( organization.getModifiedDate() == null)
    		organization.setModifiedDate(now);
    	String orgTypeCode = organization.getOrganizationType().getTypeCode();
    	if (organization.getId() != null) {
    		
    		isInsert = false;
    		org = organizationDao.get(organization.getId());
    		org.setAuditColumnPropertiesForUpdate();
    		org.setOrganizationName(organization.getOrganizationName());
    		beforeUpdate = org.buildJsonString();
    		organizationDao.update(org);
			if (oldParentOrganizationId != null && oldParentOrganizationId > 0) {
				organizationDao.updateParentOrganization(org, oldParentOrganizationId);
			}
			try {
				organizationService.updateOrgnameInOrgTreeDetail(org);
			} catch (ServiceException e) {
				logger.error("Exception while updating org tree details ", e);
			}
    	} else {
    		isInsert = true;
    		organizationDao.add(organization);
			// If the parent organization is set create the organization relationship.
			if (organization.getRelatedOrganizationId() != null && 
					organization.getRelatedOrganizationId() > 0) {
				organizationDao.addParentOrganizationByOrg(organization);
			}
			Organization orgCreated = organizationDao.get(organization.getId());
			organizationService.addOrganizationtreeDetail(orgTypeCode,orgCreated);
    	}
    	//}
    	
    	organization = organizationDao.get(organization.getId());
		afterUpdate = organization.buildJsonString();
		
        DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(SourceTypeEnum.UPLOAD.getCode());
		domainAuditHistory.setObjectType("ORGANIZATION");
		domainAuditHistory.setObjectId(organization.getId());
		domainAuditHistory.setCreatedUserId(organization.getModifiedUser().intValue());
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(isInsert ? "INSERT":"UPDATE");
		domainAuditHistory.setObjectBeforeValues(beforeUpdate);
		domainAuditHistory.setObjectAfterValues(afterUpdate);
		
		domainAuditHistoryDao.insert(domainAuditHistory);
    	logger.debug("Exit addorUpdateOrganization with - " + organization.getId() + "," + organization.getTypeCode() + ","
    			+ organization.getRelatedOrganizationId());
    	return organization;
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addtoDomainAuditHistory(Long objectId, Long createdUserId,boolean isInsert){
		DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(SourceTypeEnum.UPLOAD.getCode());
		domainAuditHistory.setObjectType("ORGANIZATION");
		domainAuditHistory.setObjectId(objectId);
		domainAuditHistory.setCreatedUserId( createdUserId.intValue() );
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(isInsert ? "INSERT":"UPDATE");
		
		domainAuditHistoryDao.insert(domainAuditHistory);
	}
	
	
}
