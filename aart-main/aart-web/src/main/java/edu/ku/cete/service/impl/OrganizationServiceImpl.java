package edu.ku.cete.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.OrganizationHierarchy;
import edu.ku.cete.domain.OrganizationHierarchyExample;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationContractRelation;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.user.UserOrganization;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.model.OrganizationAnnualResetsMapper;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.OrganizationHierarchyDao;
import edu.ku.cete.model.OrganizationSnapshotMapper;
import edu.ku.cete.model.OrganizationTypeDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.OrganizationAuditTrailHistory;
import edu.ku.cete.report.domain.OrganizationManagementAudit;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.report.model.OrganizationManagementAuditDao;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.student.FirstContactService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.PoolTypeEnum;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.TimerUtil;

/**
 * @author nicholas.studt
 */
@CacheConfig(cacheManager="epCacheManager")
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrganizationServiceImpl implements OrganizationService {

    /** Generated Serial. */
    private static final long serialVersionUID = 2735963534122808411L;
    /**
     * logger.
     */
    private final Logger
    logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    /** OrganizationDao holder. */
    @Autowired
    private OrganizationDao organizationDao;
    
    @Autowired
    private OrganizationManagementAuditDao organizationManagementAuditDao;
    
    @Autowired
    OrganizationAnnualResetsMapper organizationAnnualResetsMapper;

    /** OrganizationDao holder. */
    @Autowired
    private OrganizationHierarchyDao organizationHierarchyDao;
    
    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    private PermissionUtil permissionUtil;
    
    @Autowired
    private UserAuditTrailHistoryMapper userAuditTrailHistoryMapperDao;
    
    @Autowired
    private OrganizationTypeDao orgTypeDao;

    /**
	 * orgAssessProgService
	 */
	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;
	
	/**
	 * OrganizationTypeService
	 */
	@Autowired
	private OrganizationTypeService organizationTypeService;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;
	
    @Autowired
    @Qualifier("epCacheManager")
    private JCacheCacheManager epCacheManager;
	
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RosterService rosterService;
    
    @Autowired
    private OrganizationSnapshotMapper organziaSnapshotMapper;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private FirstContactService firstContactService;
 	
    @Autowired
    private CategoryService categoryService;
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization add(Organization toAdd) throws ServiceException {
        if (organizationDao.add(toAdd) != 1) {
            throw new ServiceException("add() returned more than one row");
        }

        // Create the default role for this new organization.
        Groups group = new Groups();
        group.setOrganizationId(toAdd.getId());
        group.setGroupName("DEFAULT");
        group.setDefaultRole(true);
        group.setAuditColumnProperties();

        groupsDao.addGroup(group);

        // If the parent organization is set create the organization relationship.

        if (toAdd.getRelatedOrganizationId() > 0) {
            this.addParentOrganization(toAdd.getId(), toAdd.getRelatedOrganizationId());
        }

        return toAdd;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean delete(final Long id) {
        if (organizationDao.delete(id) == 1) {
            return true;
        }
        return false;
    }

    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization get(final Long organizationId) {
        return organizationDao.get(organizationId);
    }
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final Organization getOrganizationDetailsByOrgId(final Long organizationId) {
           return organizationDao.getOrganizationDetailsByOrgId(organizationId);
     } 
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final Organization getViewOrganizationDetailsByOrgId(final Long organizationId) {
           return organizationDao.getViewOrganizationDetailsByOrgId(organizationId);
    }
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final Organization getParentOrgDetailsById(final Long organizationId) {
           return organizationDao.getParentOrgDetailsById(organizationId);
     }   
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getByDisplayIdentifier(String displayIdentifier) {
        return organizationDao.getByDisplayIdentifier(displayIdentifier);
    }

    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getByDisplayIdentifier(String displayIdentifier, User user) {
        List<Organization> organizations = new ArrayList<Organization>();
        Authorities authority = permissionUtil.getAuthority(user.getAuthoritiesList(),
                RestrictedResourceConfiguration.getViewOrganizationPermissionCode());
        if (authority == null) {
            logger.debug(" The user does not have the permission to see the organization");
        } else {
            organizations = organizationDao.getByDisplayIdentifierAndParent(displayIdentifier,
                    user.getOrganization().getId());
        }
        return organizations;
    }
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getAll() {
        return organizationDao.getAll();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization saveOrUpdate(Organization toSaveorUpdate) throws ServiceException {
        if (toSaveorUpdate.getId() == null) {
            return add(toSaveorUpdate);
        } else {
            return update(toSaveorUpdate);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization update(Organization toUpdate) throws ServiceException {
        if (organizationDao.update(toUpdate) != 1) {
            throw new ServiceException("update() returned more than one row");
        }
        return toUpdate;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateOrgnameInOrgTreeDetail(Organization organization) {
    	 return organizationDao.updateOrgnameInOrgTreeDetail(organization) ;
    }
    
    
    /**
     * Get immediate parent organizations (at any level) for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getImmediateParents(long organizationId) {
        return organizationDao.getImmediateParents(organizationId);
    }

    /**
     * Get immediate child organizations (at any level) for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getImmediateChildren(Long organizationId) {
        return organizationDao.getImmediateChildrenByParentId(organizationId);
    }
    
    /**
     * Get immediate child organizations (at any level) for given organization's id.
     * @param organizations {@link Collection}
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getImmediateChildren(Collection<Organization> organizations) {
        return organizationDao.getImmediateChildren(organizations);
    }

    /**
     * Get all parent organizations at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getAllParents(long organizationId) {
        return organizationDao.getAllParents(organizationId);
    }

    /**
     * Get all child organizations at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getAllChildren(long organizationId) {
        return organizationDao.getAllChildren(organizationId);
    }
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final List<OrganizationDetail> getOrganizationDetails(Long id) {
           return organizationDao.getOrganizationDetails(id);
    }
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final List<OrganizationDetail> getOrganizationDetailByOrgId(Long orgId, Long testingCycleId) {
           return organizationDao.getOrganizationDetailByOrgId(orgId, testingCycleId);
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final int addOrganizationDetails(OrganizationDetail orgDetail) {
		   return organizationDao.addOrganizationDetails(orgDetail);
       }
 
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final int updateOrganizationDetails(OrganizationDetail orgDetail) {
    	String beforeUpdate = getOrganizationDetails(orgDetail.getId()).get(0).buildJsonString();
    	int result = organizationDao.updateOrganizationDetails(orgDetail);
    	if(result > 0){
    		String afterUpdate = getOrganizationDetails(orgDetail.getId()).get(0).buildJsonString();
	    	DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
	    	domainAuditHistory.setSource(SourceTypeEnum.MANUAL.getCode());
			domainAuditHistory.setObjectType("ORGANIZATIONDETAIL");
			domainAuditHistory.setObjectId(orgDetail.getId());
			domainAuditHistory.setCreatedUserId(orgDetail.getModifiedUser().intValue());
			domainAuditHistory.setCreatedDate(new Date());
			domainAuditHistory.setAction("UPDATE");
			domainAuditHistory.setObjectBeforeValues(beforeUpdate);
			domainAuditHistory.setObjectAfterValues(afterUpdate);
			domainAuditHistory.setStatus("COMPLETE");
			result = domainAuditHistoryDao.insertSelective(domainAuditHistory);
    	}
    	return result;
       }
    
    /**
     * Get all child organizations of a certain type code at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @param orgTypeCode desired organization type
     * @return {@link Organization}
     */
  
    
    @Override
   	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
       public final List<Organization> getAllChildrenByTypeForEditSSA( String orgTypeCode) {
           //List<Organization> childrenByType = new ArrayList<Organization>();
       	List<Organization> children =  organizationDao.getAllChildrenByTypeForEditSSA( orgTypeCode);
       	return children;
    }
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getAllChildrenByOrgTypeCode(long organizationId, String orgTypeCode) {
        //List<Organization> childrenByType = new ArrayList<Organization>();
    	List<Organization> children =  organizationDao.getAllChildrenByType(organizationId, orgTypeCode);
        
    	/*OrganizationType orgType = orgTypeDao.getByTypeCodeCaseInsensitive(orgTypeCode);
    	
    	for (Organization org : children){
        	if (org.getOrganizationType().getOrganizationTypeId() == orgType.getOrganizationTypeId()){
        		org.getOrganizationType().setTypeCode(orgType.getTypeCode());
        		childrenByType.add(org);
        	}
        }*/
        
        return children;
    }
    
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getAllChildrenWithParentByOrgTypeCode(long organizationId, String orgTypeCode) {

    	List<Organization> children =  organizationDao.getAllChildrenWithParentByType(organizationId, orgTypeCode);
        
        return children;
    }    
    
    /**
     * @param displayIdentifier {@link String}
     * @param organizationId long
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization getByDisplayIdWithContext(String displayIdentifier, long organizationId) {
        Organization org = null;
        //TODO change it to select * from organization where id in (select id from organization_children(#{orgId})
        // converting to upper case..since organizations are any where converted to upper case before storing.
        List<Organization> orgs;
        if (displayIdentifier != null) {
            orgs = organizationDao.getByDisplayIdentifierAndParent(
                    displayIdentifier.toUpperCase(), organizationId);
            if (orgs != null && CollectionUtils.isNotEmpty(orgs)) {
                org = orgs.get(0);
            }
        }
        return org;
    }

    /**
     * @param displayIdentifier {@link String}
     * @param organizationId long
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization getByDisplayIdWithFullContext(String displayIdentifier, long organizationId) {
        Organization org = null;
        //TODO change it to select * from organization where id in (select id from organization_children(#{orgId})
        // converting to upper case..since organizations are any where converted to upper case before storing.
        List<Organization> orgs;
        if (displayIdentifier != null) {
            //check if the display identifier passed is one of my children
            orgs = organizationDao.getByDisplayIdentifierAndParent(
                    displayIdentifier.toUpperCase(), organizationId);
            if (orgs != null && CollectionUtils.isNotEmpty(orgs)) {
                org = orgs.get(0);
            }
            if (org == null) {
                orgs = organizationDao.getByDisplayIdentifierAndChild(
                        displayIdentifier.toUpperCase(), organizationId);
                if (orgs != null && CollectionUtils.isNotEmpty(orgs)) {
                    org = orgs.get(0);
                }
            }
        }
        return org;
    }

    /**
     * @param organizationId long
     * @param parentOrganizationId long
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void addParentOrganization(long organizationId, long parentOrganizationId) {
    	Organization organization = new Organization();
    	organization.setId(organizationId);
    	organization.setRelatedOrganizationId(parentOrganizationId);
    	organization.setAuditColumnProperties();
    	
        organizationDao.addParentOrganizationByOrg(organization);
    }

    /**
     * @param organization {@link Organization}
     * @param oldParentOrganizationId {@link Long}
     * @return {@link Organization}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization addorUpdateOrganization(Organization organization, Long oldParentOrganizationId) {
    	//TODO add this to the aspect.
    	logger.debug("Entering the addorUpdateOrganization with - " + organization.getId() + ","
    			+ organization.getOrganizationTypeId() + "," + organization.getRelatedOrganizationId() + "," + oldParentOrganizationId);
    	String beforeUpdate = null;
    	String afterUpdate = null;
    	
    	//Changed during US18004 - creating json objects for organization
    	if (organization.getId() != null) {    		
    		organization = get(organization.getId());
    		beforeUpdate = organization.buildJsonString();
    		organization.setAuditColumnPropertiesForUpdate();
    		organization.setExist(true);
    		organizationDao.update(organization);
    		if (oldParentOrganizationId != null && oldParentOrganizationId > 0) {
    			organizationDao.updateParentOrganization(organization,
    					oldParentOrganizationId);
    		} else if (organization.getRelatedOrganizationId() > 0) { 
    			organizationDao.addParentOrganizationByOrg(organization); 
    		}
    	} else {
    		organization.setAuditColumnProperties();
    		organizationDao.add(organization);
    			// If the parent organization is set create the organization relationship.
			if (organization.getRelatedOrganizationId() != null && 
    					organization.getRelatedOrganizationId() > 0) {
    				organizationDao.addParentOrganizationByOrg(organization);
    			}
    		}
    	//}
    	
    	organization = get(organization.getId());
    	afterUpdate = organization.buildJsonString();
    	DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(SourceTypeEnum.MANUAL.getCode());
		domainAuditHistory.setObjectType("ORGANIZATION");
		domainAuditHistory.setObjectId(organization.getId());
		domainAuditHistory.setCreatedUserId(organization.getModifiedUser().intValue());
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(organization.isExist() ? "UPDATE":"CREATE");
		domainAuditHistory.setObjectBeforeValues(beforeUpdate);
		domainAuditHistory.setObjectAfterValues(afterUpdate);
		
		domainAuditHistoryDao.insert(domainAuditHistory);
    	
    	
		logger.debug("Exit addorUpdateOrganization with - " + organization.getId() + "," + organization.getTypeCode() + ","
    			+ organization.getRelatedOrganizationId());
    	
    	return organization;
    }

    /**
     * @param organization {@link Organization}
     * @param userID {@link Long}
     * @return {@link Organization}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization addorUpdateOrganizationFromAPI(Organization organization, Long userID) {
    	logger.debug("Entering the addorUpdateOrganization with - " + organization.getId() + ","
    			+ organization.getOrganizationTypeId() + "," + organization.getRelatedOrganizationId() + "," + userID);
    	String beforeUpdate = null;
    	String afterUpdate = null;
    	
    	if (organization.getId() != null) {    		
    		beforeUpdate = organization.buildJsonString();
    		organization.setAuditColumnPropertiesForUpdate();
    		organization.setExist(true);
    		organizationDao.update(organization);
    	} else {
    		organization.setAuditColumnProperties();
    		organizationDao.add(organization);
    		Organization org=organizationDao.get(organization.getId());
    		String displayIdentifier= org.getTypeCode() + org.getId();
    		logger.debug("Generated Display Identifier for the organization " +displayIdentifier);
    		org.setDisplayIdentifier(displayIdentifier);
    		organizationDao.update(org);
    			// If the parent organization is set create the organization relationship.
			if (organization.getRelatedOrganizationId() != null && 
    					organization.getRelatedOrganizationId() > 0) {
    				organizationDao.addParentOrganizationByOrg(organization);
    			}
    		}
    	//}
    	
    	organization = get(organization.getId());
    	afterUpdate = organization.buildJsonString();
    	//Create Audit History
    	DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		domainAuditHistory.setSource(SourceTypeEnum.MANUAL.getCode());
		domainAuditHistory.setObjectType("ORGANIZATION");
		domainAuditHistory.setObjectId(organization.getId());
		domainAuditHistory.setCreatedUserId(organization.getModifiedUser().intValue());
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(organization.isExist() ? "UPDATE":"CREATE");
		domainAuditHistory.setObjectBeforeValues(beforeUpdate);
		domainAuditHistory.setObjectAfterValues(afterUpdate);
		domainAuditHistoryDao.insert(domainAuditHistory);
		logger.debug("Exit addorUpdateOrganization with - " + organization.getId() + "," + organization.getTypeCode() + ","
    			+ organization.getRelatedOrganizationId());
    	
    	return organization;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization updateOrganization(Organization organization) {
    	//TODO add this to the aspect.
    	logger.debug("Entering the updateOrganization with - " + organization.getId() + ","
    			+ organization.getOrganizationName());
    	String beforeUpdate = null;
    	String afterUpdate = null;
    	
    	Organization beforeOrg = get(organization.getId());
        	
    	if (organization.getId() != null) {    		
    		beforeUpdate = beforeOrg.buildJsonString();
    		organization.setAuditColumnPropertiesForUpdate();
    		organization.setExist(true);
    		organization.setOrganizationTypeId(beforeOrg.getOrganizationTypeId());
    		organizationDao.update(organization);    		
    	}   
    	
    	Organization afterOrg = get(organization.getId());
    	afterUpdate = afterOrg.buildJsonString();
    	DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		
		domainAuditHistory.setSource(SourceTypeEnum.MANUAL.getCode());
		domainAuditHistory.setObjectType("ORGANIZATION");
		domainAuditHistory.setObjectId(organization.getId());
		domainAuditHistory.setCreatedUserId(organization.getModifiedUser().intValue());
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setAction(organization.isExist() ? "UPDATE":"CREATE");
		domainAuditHistory.setObjectBeforeValues(beforeUpdate);
		domainAuditHistory.setObjectAfterValues(afterUpdate);
		
		domainAuditHistoryDao.insert(domainAuditHistory);
     	
		logger.debug("Exit updateOrganization with - " + organization.getId() + "," + organization.getOrganizationName());
    	
    	return organization;
    }
    public final Organization addorUpdateOrganizationUpload(Organization organization, Long oldParentOrganizationId) {
    	//TODO add this to the aspect.
    	logger.debug("Entering the addorUpdateOrganization with - " + organization.getId() + ","
    			+ organization.getOrganizationTypeId() + "," + organization.getRelatedOrganizationId() + "," + oldParentOrganizationId);
    	
    	Date now = new Date();
    	
    	if( organization.getActiveFlag() == null)
    		organization.setActiveFlag(true);
    	if( organization.getCreatedDate() == null)
    		organization.setCreatedDate(now);
    	if( organization.getModifiedDate() == null)
    		organization.setModifiedDate(now);
    	
    	if (organization.getId() != null) {
    		//organization.setAuditColumnPropertiesForUpdate();
    		organizationDao.update(organization);
    		if (oldParentOrganizationId != null && oldParentOrganizationId > 0) {
    			organizationDao.updateParentOrganization(organization,
    					oldParentOrganizationId);
    		} else if (organization.getRelatedOrganizationId() > 0) { 
    			organizationDao.addParentOrganizationByOrg(organization); 
    		}
    	} else {
    		organizationDao.add(organization);
    			// If the parent organization is set create the organization relationship.
    			if (organization.getRelatedOrganizationId() != null && 
    					organization.getRelatedOrganizationId() > 0) {
    				organizationDao.addParentOrganizationByOrg(organization);
    			}
    		}
    	//}
    	logger.debug("Exit addorUpdateOrganization with - " + organization.getId() + "," + organization.getTypeCode() + ","
    			+ organization.getRelatedOrganizationId());
    	return organization;
    }
    
    
    /**
     * TODO move this to AART CollectionUtil
     * Return the map for efficiency.
     * @param orgContractRelations
     * @return
     */
    private ContractingOrganizationTree getOrganizationContractRelationMap(List<OrganizationContractRelation> orgContractRelations) {
    	Map<Long,List<OrganizationContractRelation>> childOrgMap
    	= new HashMap<Long,List<OrganizationContractRelation>>();
    	Map<Long,List<OrganizationContractRelation>> parentOrgMap
    	= new HashMap<Long,List<OrganizationContractRelation>>();
    	OrganizationContractRelation userOrganizationContractRelation = null;
    	ContractingOrganizationTree contractingOrganizationTree = new ContractingOrganizationTree();
    	if(orgContractRelations != null && CollectionUtils.isNotEmpty(orgContractRelations)) {
			for (OrganizationContractRelation orgContractRelation : orgContractRelations) {
				Long relatedOrganizationId = orgContractRelation.getOrganization().getRelatedOrganizationId();
				if (relatedOrganizationId != null && orgContractRelation.getIsParent()) {
					//These are children to user's organization. i.e. the current user's organization
					// is a parent of these organizations
					List<OrganizationContractRelation>
					orgContractRelationsMapValue = childOrgMap.get(relatedOrganizationId);
					if(orgContractRelationsMapValue == null) {
						orgContractRelationsMapValue = new ArrayList<OrganizationContractRelation>();
						childOrgMap.put(relatedOrganizationId,
								orgContractRelationsMapValue);
					}
					orgContractRelationsMapValue.add(orgContractRelation);					
				} else if (relatedOrganizationId != null && !orgContractRelation.getIsParent()) {
					//These are parents to user's organization. i.e. the current user's organization
					// is the children of these organizations
					List<OrganizationContractRelation>
					orgContractRelationsMapValue = parentOrgMap.get(relatedOrganizationId);
					if(orgContractRelationsMapValue == null) {
						orgContractRelationsMapValue = new ArrayList<OrganizationContractRelation>();
						parentOrgMap.put(orgContractRelation.getOrganization().getRelatedOrganizationId(),
								orgContractRelationsMapValue);
					}
					orgContractRelationsMapValue.add(userOrganizationContractRelation);					
				} else if (relatedOrganizationId == null) {
					//This is the users organization itself
					userOrganizationContractRelation = orgContractRelation;
				}
			}
    	}
    	//This is the user's child organization tree.
    	//Case 1. highest contracting org (only 1) are at higher level than user's organization. Logged in as school admin and contracting org is KS
    	// Get all children of KS.
    	logger.debug(" child organization map " + childOrgMap.size());
    	contractingOrganizationTree.setChildOrgMap(childOrgMap);
    	//Case 2. Highest contracting orgs are at lower level than user's organization. Logged in as CETESysAdmin and contracting orgs are KS and MO.
    	// Get all children of KS and get all children of MO.
    	logger.debug(" parent organization map " + parentOrgMap.size());
    	contractingOrganizationTree.setParentOrgMap(parentOrgMap);
    	//Case 3. Highest contracting org is the user's organization.
    	// user's organization tree is the contracting organization tree. Say logged in KSSysAdmin.
    	logger.debug(" Logged in users organization. " + userOrganizationContractRelation);
    	contractingOrganizationTree.setUserOrganizationContractRelation(
    			userOrganizationContractRelation);
    	contractingOrganizationTree.setUserOrganizationTree();
    	return contractingOrganizationTree;
    }
    /**
     * This has to be set on the list to make use of the sorted order in finding 
     * the highest contracting organizations.
     * @param orgContractRelations
     * @return
     */
    private void setContractPresent(
    		List<OrganizationContractRelation> orgContractRelations,
    		List<Long> contractingOrgIds) {
		if(contractingOrgIds != null && CollectionUtils.isNotEmpty(contractingOrgIds)) {
			for (OrganizationContractRelation orgContractRelation : orgContractRelations) {
				if (contractingOrgIds.contains(orgContractRelation.getOrganization().getId())) {
					orgContractRelation.setContractPresent(true);
					logger.debug("Organization "+orgContractRelation + " is contracting org");
				} else {
					orgContractRelation.setContractPresent(false);
				}
				
			}
		}    	
    }    
    /**
     * @param orgContractRelationsMap.keySet() sorted by the level.
     * @return
     */
    private List<OrganizationContractRelation> getHigestContractingOrgs(
    		List<OrganizationContractRelation> orgContractRelations) {
		Integer highestContractingLevel = Integer.MAX_VALUE;
		List<OrganizationContractRelation> highestContractingOrgs = new ArrayList<OrganizationContractRelation>();

		for (OrganizationContractRelation foundOrgContractRelation:orgContractRelations) {
			if(foundOrgContractRelation.isContractPresent()
					&& foundOrgContractRelation.getCurrentLevel() < highestContractingLevel) {
				highestContractingLevel = foundOrgContractRelation.getCurrentLevel();
				highestContractingOrgs.clear();
				highestContractingOrgs.add(foundOrgContractRelation);
			}else if(foundOrgContractRelation.isContractPresent()
					&& foundOrgContractRelation.getCurrentLevel() == highestContractingLevel) {
				highestContractingLevel = foundOrgContractRelation.getCurrentLevel();
				highestContractingOrgs.add(foundOrgContractRelation);
			} else if(foundOrgContractRelation.getCurrentLevel() > highestContractingLevel) {
				//This is not breaking the for loop
				break;
			}				
		}

		logger.debug("The highest contracting orgs are "+ highestContractingOrgs);
		return highestContractingOrgs;
    }    

    @Cacheable(value="getTreeCache", key = "#organization.id")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public ContractingOrganizationTree getTree(Organization organization) {
		ContractingOrganizationTree contractingOrganizationTree = null;		
		if (organization != null) {
			long currentTime = System.currentTimeMillis();
			logger.warn("Starting to retrieve tree for " + organization.getId() + ":" + currentTime);
			List<OrganizationContractRelation> orgContractRelations = organizationDao.getTree(organization.getId());
			Collections.sort(orgContractRelations);
			
			//Executing as a seperate query to get consistent results
			List<Long> contractingOrgIds = organizationDao.getContractingOrganizationIds();
			setContractPresent(orgContractRelations,contractingOrgIds);
			List<OrganizationContractRelation> highestContractingOrgs = getHigestContractingOrgs(orgContractRelations);
			logger.warn("Highest Contracting Orgs "+ highestContractingOrgs);
			contractingOrganizationTree = getOrganizationContractRelationMap(orgContractRelations);	
			contractingOrganizationTree.setHighestContractingOrgs(highestContractingOrgs);
			
			// It is not always necessary to get all children of each of the contracting org.
			// For higher level users this is already available.
			for (OrganizationContractRelation highestContractingOrg:highestContractingOrgs) {
				Long highestContractingOrgId = highestContractingOrg.getId();
				List<Organization> contractingOrgChildren = null;
				if(contractingOrganizationTree.getUserOrganizationContractRelation().getOrganization().getId().equals(highestContractingOrgId)) {
					contractingOrgChildren = contractingOrganizationTree.getUserOrganizationTree().getUserOrganizationList();
					logger.warn("The children for contracting org is same as users org" + highestContractingOrg);				
				} else {
					contractingOrgChildren = organizationDao.getAllChildren(highestContractingOrg.getId());
					logger.warn("found the children for contracting org " + highestContractingOrg);				
				}
				contractingOrganizationTree.getContractingOrganizationChildren().put(highestContractingOrg.getId(),contractingOrgChildren);
			}
			
			long currentTime2 = System.currentTimeMillis();
			logger.warn("Retrieved tree for " + organization.getId() + " in (ms): " + (currentTime2 - currentTime));
		}
		return contractingOrganizationTree;
	}
    
    @CacheEvict(value = "getTreeCache", key = "#organization.id")
	@Override
	public String clearTreeCache(Organization organization) {
		return "Ok";
	}

	/* (non-Javadoc)
	 * @see edu.ku.cete.service.OrganizationService#getAllChildren(java.util.List, boolean)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getAllChildren(List<Long> userOrganizationIds,
			boolean includeGivenOrganizationIds) {
		//TODO Consider moving timer to an aspect.
		TimerUtil timerUtil = TimerUtil.getInstance();
		
		logger.debug("Getting children timer start");
		boolean validInput = false;
		List<Organization> childOrganizations = new ArrayList<Organization>();
		if(userOrganizationIds != null && CollectionUtils.isNotEmpty(userOrganizationIds)) {
			validInput = true;
		} else {
			logger.debug("Input is invalid "+userOrganizationIds);
		}
		if(validInput) {
			for(Long userOrganizationId:userOrganizationIds) {
				if(includeGivenOrganizationIds) {
					childOrganizations.add(organizationDao.get(userOrganizationId));
				}
				childOrganizations.addAll(organizationDao.getAllChildren(userOrganizationId));
			}
		}
		
		timerUtil.stop();
//		if(timerUtil.getLevel() > 3) {
//			logger.error("Org Retrieval took "+timerUtil.getInterval());
//		} else if(timerUtil.getLevel() > 2) {
//			logger.warn("Org Retrieval took "+timerUtil.getInterval());
//		} else if(timerUtil.getLevel() > 1) {
//			logger.debug("Org Retrieval took "+timerUtil.getInterval());
//		} else {
//			logger.debug("Org Retrieval took "+timerUtil.getInterval());
//		}
		logger.debug("Org Retrieval took "+timerUtil.getInterval());
		return childOrganizations;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getAllChildren(Long userOrganizationId,
			boolean includeGivenOrgId) {
		List<Long> userOrganizationIds = new ArrayList<Long>();
		List<Organization> childOrganizations = new ArrayList<Organization>();
		if(userOrganizationId != null) {
			userOrganizationIds.add(userOrganizationId);
			childOrganizations = getAllChildren(userOrganizationIds, includeGivenOrgId);
		}
		return childOrganizations;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.OrganizationService#getByTypeId(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getByTypeId(String organizationTypeCode) {
		return organizationDao.getByTypeId(organizationTypeCode, null);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getByTypeId(String organizationTypeCode, String sortByCol) {
		return organizationDao.getByTypeId(organizationTypeCode, sortByCol);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getByTypeAndUserId(String organizationTypeCode,
			Long userId) {
		return organizationDao.getByTypeAndUserId(organizationTypeCode, userId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getByTypeAndUserIdInParent(
			String organizationTypeCode, Long userId, Long parentId) {
		return organizationDao.getByTypeAndUserIdInParent(organizationTypeCode, userId, parentId);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getByTypeAndUserIdInParentByReportYear(
			String organizationTypeCode, Long userId, Long parentId,Long reportYear) {
		return organizationDao.getByTypeAndUserIdInParentByReportYear(organizationTypeCode, userId, parentId,reportYear);
	}
	
	@Override
	public boolean checkForDLM(Long organizationId, String userOrgDLM) {
		boolean check = false;
		List<Long> orgs = organizationDao.checkForDLM(organizationId, userOrgDLM);
		if(orgs != null && orgs.size() > 0){
		check = true;
		}	
		return check;
	}

	@Override
	public List<Organization> getAllChildrenDLM(Long organizationId,
			String userOrgDLM) {
		return organizationDao.getAllChildrenDLM(organizationId, userOrgDLM);
	}
	
	@Override
	public OrganizationHierarchy addOrganizationStructure(Long organizationId, Long orgtypeId){
		
		OrganizationHierarchy organizationHierarchy = new OrganizationHierarchy();
		organizationHierarchy.setOrganizationId(organizationId);
		organizationHierarchy.setOrganizationTypeId(orgtypeId);
		organizationHierarchyDao.insert(organizationHierarchy);
		return organizationHierarchy;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Organization getByDisplayIdAndType(String displayIdentifier, Long orgTypeId, Long contractingOrgId) {
	    
		return organizationDao.getByDisplayIdAndType(displayIdentifier, orgTypeId, contractingOrgId);
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.OrganizationService#getByDisplayIdRelationAndType(java.lang.String, 
	 * java.lang.Long)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Organization getByDisplayIdRelationAndType(String displayIdentifier, Long orgTypeId) {
		Organization children =  organizationDao.getByDisplayIdRelationAndType(displayIdentifier, orgTypeId);
	    return children;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<OrganizationHierarchy> getAllOrganizationHierarchies() {
		return organizationHierarchyDao.getAllOrganizationHierarchies();
	}
	
	
	/**
     * Get all child organizations of a certain type code at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @param orgTypeCode desired organization type
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getAllParentsByOrgTypeCode(long organizationId, String orgTypeCode) {
        List<Organization> childrenByType = new ArrayList<Organization>();
    	List<Organization> children =  organizationDao.getAllParents(organizationId);
        
    	OrganizationType orgType = orgTypeDao.getByTypeCodeCaseInsensitive(orgTypeCode);
    	
    	for (Organization org : children){
        	if (org.getOrganizationType().getOrganizationTypeId() == orgType.getOrganizationTypeId()){
        		org.getOrganizationType().setTypeCode(orgType.getTypeCode());
        		childrenByType.add(org);
        	}
        }
        
        return childrenByType;
    }
    
    /**
     * Get organization hierarchy for the loggedin user's account, this is primarily used for Organization filter widget
     * @param organizationId Id of the organization.
     * @param orgTypeCode desired organization type
     * @return {@link Organization}
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getLoggedInUserOrganizationHierarchy(long organizationId, String orgTypeCode){
    	List<Organization> loggedInUserOrganizations =  organizationDao.getLoggedInUserOrganizationHierarchy(organizationId, orgTypeCode);
    	return loggedInUserOrganizations;
    }
	
    /* (non-Javadoc)
     * @see edu.ku.cete.service.OrganizationService#getOrgHierarchiesById(java.lang.Long)
     */
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<OrganizationHierarchy> getOrgHierarchiesById(Long organizationId) {
    	OrganizationHierarchyExample example = new OrganizationHierarchyExample();
        example.createCriteria().andOrganizationIdEqualTo(organizationId);
		return organizationHierarchyDao.selectByExample(example);
	}
    

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getAllChildrenToView(Map<String, Object> criteria, String sortByColumn, String sortType, 
    		Integer offset, Integer limitCount) {
    	criteria.put("limit", limitCount);
		criteria.put("offset", offset);
		criteria.put("sortByColumn", sortByColumn);
		criteria.put("sortType", sortType);
        return organizationDao.findSelfAndChildren(criteria);
    }
    

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Integer countAllChildrenToView(Map<String, Object> filters) {
        return organizationDao.countFindSelfAndChildren(filters);
    }

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Organization getContractingOrganization(Long orgId) {
		return organizationDao.getContractingOrg(orgId);
	}
    
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getDistrictInState(String districtDisplayIdentifier, Long orgId) {
		return organizationDao.getDistrictInState(districtDisplayIdentifier, orgId);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public OrganizationTreeDetail getSchoolInState(String schoolDisplayIdentifier, Long stateId){
		return organizationDao.getSchoolInState(schoolDisplayIdentifier, stateId);
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String createOrganization(Organization organization, String orgDisplayId, String orgName,
			String buldingUniqueness, String startDate, String endDate, String parentConsortiaOrg, Long parentOrgId,
			Map<String, Long> orgTypeCodeMap, String orgType,boolean contractingOrgFlag, String expirePasswords,
			String expirationDateType, String[] organizationStructure, Long[] assessmentProgramIds,Long testingModel,Integer reportYear, 
			String testBeginTime,String testEndTime,String testDays){
       		
			String success = "failed";
			organization.setDisplayIdentifier(orgDisplayId);
			organization.setOrganizationName(orgName);
			if (buldingUniqueness != null && !StringUtils.isEmpty(buldingUniqueness)) {
				OrganizationType buildingUniquenessType = organizationTypeService.getByTypeCode(buldingUniqueness);
				organization.setBuildingUniqueness(buildingUniquenessType
						.getOrganizationTypeId());
			}
			try {
				if (startDate != null && !StringUtils.isEmpty(startDate)) {
					organization.setSchoolStartDate(DateUtil.parseAndFail(
							startDate, "MM/dd/yyyy"));
				}
				if (endDate != null && !StringUtils.isEmpty(endDate)) {
					organization.setSchoolEndDate(DateUtil.parseAndFail(
							endDate, "MM/dd/yyyy"));
				}
			} catch (Exception ex) {
				logger.error("Caught in createOrganization. Stacktrace: {}", ex);
				return success;
			}
			
			try {
				if (testBeginTime != null && !StringUtils.isEmpty(testBeginTime)) {
					organization.setTestBeginTime(DateUtil.parseAndFail(
							testBeginTime,"hh:mm a"));
				}
				if (testEndTime != null && !StringUtils.isEmpty(testEndTime)) {
					organization.setTestEndTime(DateUtil.parseAndFail(
							testEndTime, "hh:mm a"));
				} 
			} catch (Exception ex) {
				logger.error("Caught in createOrganization. Stacktrace: {}", ex);
				return success;
			}
			if(testDays != null) organization.setTestDays(testDays);

			if (parentConsortiaOrg != null
					&& !StringUtils.isEmpty(parentConsortiaOrg)) {
				organization.setRelatedOrganizationId(Long
						.parseLong(parentConsortiaOrg));
			} else {
				organization.setRelatedOrganizationId(parentOrgId);
			}
			organization.setOrganizationTypeId(orgTypeCodeMap.get(orgType));
			organization.setContractingOrganization(contractingOrgFlag);
			organization.setExpirePasswords(expirePasswords == null || expirePasswords.endsWith("null") ? true : (expirePasswords.equals("yes") ? true : false));
			organization.setExpirationDateType(expirationDateType == null ||expirationDateType == "" || expirationDateType.equals("null") ? null : Long.parseLong(expirationDateType));
			if(reportYear != null) organization.setReportYear(reportYear);
			if(testingModel != null) organization.setTestingModel(testingModel);
			Organization organizationcreated = addorUpdateOrganization(organization, null);
	
			if (organizationStructure != null
					&& orgType
							.equalsIgnoreCase(CommonConstants.ORGANIZATION_STATE_CODE)) {
				for (String orgStructureTypeId : organizationStructure) {
					addOrganizationStructure(organizationcreated.getId(), orgTypeCodeMap.get(orgStructureTypeId));
				}
			}
			
			addOrganizationtreeDetail(orgType, organizationcreated);
			
			AssessmentProgram DLMAssessment = assessmentProgramService.findByAbbreviatedName("DLM");
			
			if (assessmentProgramIds != null) {
				for (Long assessmentProgramId : assessmentProgramIds) {
					OrgAssessmentProgram orgAssessmentProgram = new OrgAssessmentProgram();
					orgAssessmentProgram.setAssessmentProgramId(assessmentProgramId);
					orgAssessmentProgram.setOrganizationId(organizationcreated.getId());
					orgAssessmentProgram.setAuditColumnProperties();
					orgAssessProgService.insert(orgAssessmentProgram);
					if(DLMAssessment != null && assessmentProgramId.longValue() == DLMAssessment.getId().longValue())
						setFCSSetting(organizationcreated);
					success = "success";
				}
			} else {
				success = "success";
			}
			clearTreeCache(organizationcreated);
			return success;
	}

	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Organization createOrganizationFromAPI(Organization organization,String orgType,Map<String, Long> orgTypeCodeMap,
			String buldingUniqueness, String[] organizationStructure, ArrayList<Long> assessmentProgramIds){
       		
			if (buldingUniqueness != null && !StringUtils.isEmpty(buldingUniqueness)) {
				OrganizationType buildingUniquenessType = organizationTypeService.getByTypeCode(buldingUniqueness);
				organization.setBuildingUniqueness(buildingUniquenessType
						.getOrganizationTypeId());
			}
			Organization organizationcreated = addorUpdateOrganizationFromAPI(organization, organization.getCreatedUser());
			if(organizationcreated!=null) {
				if (organizationStructure != null
						&& orgType
								.equalsIgnoreCase(CommonConstants.ORGANIZATION_STATE_CODE)) {
					for (String orgStructureTypeId : organizationStructure) {
						addOrganizationStructure(organizationcreated.getId(), orgTypeCodeMap.get(orgStructureTypeId));
					}
				}
				
				addOrganizationtreeDetail(orgType, organizationcreated);
				if (assessmentProgramIds != null) {
					for (Long assessmentProgramId : assessmentProgramIds) {
						OrgAssessmentProgram orgAssessmentProgram = new OrgAssessmentProgram();
						orgAssessmentProgram.setAssessmentProgramId(assessmentProgramId);
						orgAssessmentProgram.setOrganizationId(organizationcreated.getId());
						orgAssessmentProgram.setCreatedUser(organizationcreated.getCreatedUser());
						orgAssessmentProgram.setModifiedUser(organizationcreated.getModifiedUser());
						orgAssessmentProgram.setAuditColumnProperties();
						orgAssessProgService.insert(orgAssessmentProgram);
					}
				}
				clearTreeCache(organizationcreated);
			}
			
			return organizationcreated;
	}
	
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addOrganizationtreeDetail(String orgType, Organization organizationcreated) {
		if("SCH".equalsIgnoreCase(orgType)){
		  List<Organization> parents = getAllParents(organizationcreated.getId());
		  
		  OrganizationTreeDetail organizationTreeDetail = new OrganizationTreeDetail();
		  for (Organization org : parents) {
			if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_5){
				organizationTreeDetail.setDistrictId(org.getId());
				organizationTreeDetail.setDistrictDisplayIdentifier(org.getDisplayIdentifier());
				organizationTreeDetail.setDistrictName(org.getOrganizationName());
			}else if(org.getOrganizationTypeId() == CommonConstants.ORGANIZATION_TYPE_ID_2){
				organizationTreeDetail.setStateId(org.getId());
				organizationTreeDetail.setStateDisplayIdentifier(org.getDisplayIdentifier());
				organizationTreeDetail.setStateName(org.getOrganizationName());
			}
		  }
		  
		  organizationTreeDetail.setSchoolId(organizationcreated.getId());
		  organizationTreeDetail.setSchoolName(organizationcreated.getOrganizationName());
		  organizationTreeDetail.setSchoolDisplayIdentifier(organizationcreated.getDisplayIdentifier());
		  
		  organizationDao.addOrganizationtreeDetail(organizationTreeDetail);
		}
	}
	
	private void setFCSSetting(Organization organizationcreated) {		
		Calendar calendar = new GregorianCalendar();
	    calendar.setTime(organizationcreated.getSchoolEndDate());
	    int year = calendar.get(Calendar.YEAR);
	    
	    Category allQuestion = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
	    
		FirstContactSettings firstContactSettings = new FirstContactSettings();
		firstContactSettings.setCategoryId(allQuestion.getId());
		firstContactSettings.setOrganizationId(organizationcreated.getId());
		firstContactSettings.setScienceFlag(true);
		//Added MathFlag and ElaFlag for F607
		firstContactSettings.setMathFlag(true);
		firstContactSettings.setElaFlag(true);
		firstContactSettings.setSchoolYear(Long.valueOf(year));
		firstContactSettings.setAuditColumnProperties();
		firstContactService.insertFirstContactSettings(firstContactSettings);		
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getAllChildrenOrgIds(Long organizationId) {
		return organizationDao.getAllChildrenOrgIds(organizationId);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getContractingOrgsByAssessmentProgramId(Long assessmentProgramId) {
		return organizationDao.getContractingOrgsByAssessmentProgramId(assessmentProgramId);
	}
	
	@Override
	public List<Organization> getDLMStatesWithMultiEEs() {
		return organizationDao.getDLMStatesWithMultiEEs();
	}
	
	@Override
	public List<Organization> getDLMStatesWithPooltype(String assessmentProgram, Boolean multiAssignment) {
		return organizationDao.getDLMStatesWithPooltype(assessmentProgram, multiAssignment);
	}
	
	@Override
	public List<Organization> getDLMStatesWithPooltypeAndOperationalWindow(String assessmentProgram, Boolean multiAssignment, Long operationalWindowId) {
		return organizationDao.getDLMStatesWithPooltypeAndOperationalWindow(assessmentProgram, multiAssignment, operationalWindowId);
	}
	
	@Override
	public List<Organization> getOrgHierarchyByUserId(Long userId) {
		return organizationDao.getOrgHierarchyByUserId(userId);
	}

	@Override
	public List<Organization> getStatesByOperationalTestWindowId(
			Long operationalTestWindowId) {
		return organizationDao.getStatesByOperationalTestWindowId(operationalTestWindowId);
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateCachedOrganizationDetails() {
		organizationDao.refreshOrgDetails();
	}
	
	@Override
	public void resetEhCacheEntries() {
		if(CollectionUtils.isNotEmpty(epCacheManager.getCacheNames())){
			for (String cacheName : epCacheManager.getCacheNames()) {
				epCacheManager.getCache(cacheName).clear();
			}
		}
	}
	/*
	 * sudhansu : created for US18004 for comparing organization objects...
	 */
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean addToOrganizationAuditTrailHistory(DomainAuditHistory domainAuditHistory){
		JsonNode before = null;
		JsonNode after = null;
		boolean isProcessed= false;
		try {
		if(domainAuditHistory.getObjectBeforeValues() == null && domainAuditHistory.getObjectAfterValues() == null){
			logger.debug("In-valid entry in Domainaudithistory table"+ domainAuditHistory.getObjectId());
			
		}else if(domainAuditHistory.getObjectBeforeValues() == null){			
			insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
					                   ,SourceTypeEnum.UPLOAD.getCode().equals(domainAuditHistory.getSource())?"Organization Uploaded":"Organization Created",null,domainAuditHistory.getObjectAfterValues());
		}else{
				before = mapper.readTree(domainAuditHistory.getObjectBeforeValues());
				after = mapper.readTree(domainAuditHistory.getObjectAfterValues());			
			
			//Assuming always the json structure for before object and after object is same 			
			for(Iterator<Entry<String, JsonNode>> it = before.fields();it.hasNext();){
				
				Entry<String, JsonNode> entry = it.next();
				String key = entry.getKey();
	           if(!"ModifiedDate".equalsIgnoreCase(key) && !"ModifiedUser".equalsIgnoreCase(key)){							
				   if((before.get(key) == null && after.get(key)!=null)||(before.get(key) != null && after.get(key) == null) ||
						   before.get(key)!=null && after.get(key)!=null && !before.get(key).asText().equalsIgnoreCase(after.get(key).asText())){					 
					   
					   insertToAuditTrailHistory(domainAuditHistory.getObjectId(),domainAuditHistory.getId(),domainAuditHistory.getCreatedUserId().longValue()
			                   ,key+" Changed",before.get(key).asText(),after.get(key).asText());   					
					
				 }
				   
			 }
	           
			
		}	
			
		 }
		isProcessed= true;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			logger.error("value inserted in organizationaudittrail table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();
			isProcessed= false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("value inserted in organizationaudittrail table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();
			isProcessed= false;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("value inserted in organizationaudittrail table Failed for " + domainAuditHistory.getObjectId());
			e.printStackTrace();
			isProcessed= false;
		}
		if (isProcessed){
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"COMPLETED");
		}
		else{
			userService.changeStatusToCompletedProcessedAuditLoggedUser(domainAuditHistory.getId(),"FAILED");
		}
		return isProcessed;
		
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertToAuditTrailHistory(Long objectId,Long domainAuditHistoryId,Long modifiedUserId,String eventName,String beforeValue,String currentValue){
		Long organizationId=objectId;
		String stateName=new String();
		Long stateId=null;
		String districtName=new String();
		Long districtId=null;
		String schoolName=new String();
		Long schoolId=null;
		List<Organization> parentList=organizationDao.getInactiveActiveParentOrgDetailsById(organizationId);
		for(Organization org:parentList){
			if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_STATE_CODE)){
				stateName=org.getOrganizationName();
				stateId=org.getId();
			}
			else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_DISTRICT_CODE)){
				districtName=org.getOrganizationName();
				districtId=org.getId();
			}else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				schoolName=org.getOrganizationName();
				schoolId=org.getId();
			} 
		}
		User user=userService.get(modifiedUserId);
		if(user==null){
			user=new User();
		}
		OrganizationAuditTrailHistory organizationAuditTrailHistory = new OrganizationAuditTrailHistory();
		organizationAuditTrailHistory.setAffectedorganization(objectId);
		organizationAuditTrailHistory.setCreatedDate(new Date());
		organizationAuditTrailHistory.setModifiedUser(modifiedUserId);
		organizationAuditTrailHistory.setEventName(eventName);
		organizationAuditTrailHistory.setBeforeValue(beforeValue);
		organizationAuditTrailHistory.setCurrentValue(currentValue);
		organizationAuditTrailHistory.setDomainAuditHistoryId(domainAuditHistoryId);
		organizationAuditTrailHistory.setStateName(stateName);
		organizationAuditTrailHistory.setStateId(stateId);
		organizationAuditTrailHistory.setDistrictName(districtName);
		organizationAuditTrailHistory.setDistrictId(districtId);
		organizationAuditTrailHistory.setSchoolName(schoolName);
		organizationAuditTrailHistory.setSchoolId(schoolId);
		organizationAuditTrailHistory.setModifiedUserName(user.getUserName());
		organizationAuditTrailHistory.setModifiedUserFirstName(user.getFirstName());
		organizationAuditTrailHistory.setModifiedUserLastName(user.getSurName());
		organizationAuditTrailHistory.setModifiedUserEducatorIdentifier(user.getUniqueCommonIdentifier());
		userAuditTrailHistoryMapperDao.insertOrganizationAuditTrail(organizationAuditTrailHistory);
		logger.trace("value inserted in organizationaudittrail table ");
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<Long, String> getStatesBasedOnassessmentProgramId(Long assessmentProgramId) {		
		Map<Long,String> operationTestWindowStateIds= new HashMap<Long,String>();
		List<Organization> organizations= organizationDao.getStatesBasedOnassessmentProgramId(assessmentProgramId);
		for(Organization org:organizations){
			operationTestWindowStateIds.put(org.getId(),org.getOrganizationName());
		}
		return operationTestWindowStateIds;
	}	
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getOrganizationByassessmentProgramId(long assessmentId) {	
		
		List<Organization> orgs = organizationDao.getStatesBasedOnassessmentProgramId(assessmentId);
		List<Organization> organizations = new ArrayList<Organization>();
		//Added to exclude DLM QC state
		for(Organization org:orgs){
			if(!"DLMQCEOYST".equalsIgnoreCase(org.getDisplayIdentifier()) && !"DLMQCIMST".equalsIgnoreCase(org.getDisplayIdentifier()) &&
					!"DLMQCST".equalsIgnoreCase(org.getDisplayIdentifier()) && !"DLMQCYEST".equalsIgnoreCase(org.getDisplayIdentifier())){
				organizations.add(org);
			}
		}		
	  return organizations;
	}
	
	public OrganizationTreeDetail getOrganizationDetailBySchoolId(long schoolId){
		return organizationDao.getOrganizationDetailBySchoolId(schoolId);
	}
	
	public OrganizationTreeDetail getOrganizationDetailById(long orgId){
		return organizationDao.getOrganizationDetailById(orgId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long mergeSchool(Long sourceSchool, Long destinationSchool) throws Exception {
		// Validate parameters
		validateOrganizationType(sourceSchool, CommonConstants.ORGANIZATION_SCHOOL_CODE);
		validateOrganizationType(destinationSchool, CommonConstants.ORGANIZATION_SCHOOL_CODE);
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
	 	Long currentSchoolYear= userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
	 	Long reportYear= (long) userDetails.getUser().getContractingOrganization().getReportYear();
	 	String action = "merged";
		boolean activeflag = true;
	 	
	 	List<OrganizationManagementAudit> organizationManagementAudits = new ArrayList<OrganizationManagementAudit>();
	 	
		// move students to destination school
		List<Enrollment> enrollments = enrollmentService.getEnrollementsByOrg(sourceSchool, currentSchoolYear);
		
		
		OrganizationManagementAudit organizationManagementAudit1 = new OrganizationManagementAudit(sourceSchool, "MERGE");
		organizationManagementAudit1.setCurrentSchoolYear(currentSchoolYear);
		organizationManagementAudit1.setDestOrgId(destinationSchool);
		organizationManagementAudit1.setAuditColumnProperties();
		setValuesOrganizationManagementAudit(organizationManagementAudit1,sourceSchool,destinationSchool,null,userDetails,"MERGE");
		organizationManagementAudits.add(organizationManagementAudit1);
		
		for(Enrollment enrollment: enrollments){
			// update enrollment with destination school id
			enrollmentService.transferEnrollment(enrollment, destinationSchool);
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(sourceSchool, "MERGE");
//			organizationManagementAudit.setDestOrgId(destinationSchool);
			organizationManagementAudit.setEnrollmentId(enrollment.getId());
			organizationManagementAudit.setStudentId(enrollment.getStudentId());
			organizationManagementAudit.setStateStudentIdentifier(enrollment.getStudent().getStateStudentIdentifier());
//			organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAudit(organizationManagementAudit,sourceSchool,destinationSchool,null,userDetails,"MERGE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		
		// move users to destination school
		List<UserOrganization> userOrganizations = userService.getUserOrganizationsByOrgId(sourceSchool);
		for(UserOrganization userOrganization : userOrganizations){
			List<UserOrganization> destUserId = userService.getDestUserOrganizationsByOrgId(destinationSchool, userOrganization.getUserId());
			if(destUserId!= null && !destUserId.isEmpty()){
				//Do nothing
			}else{
				userService.transferUserOrganization(userOrganization,destinationSchool);
				
				OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(sourceSchool, "MERGE");
				organizationManagementAudit.setDestOrgId(destinationSchool);
				organizationManagementAudit.setAartUserId(userOrganization.getUserId());
				organizationManagementAudit.setAartUserName(userOrganization.getUserName());
				organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
				organizationManagementAudit.setAuditColumnProperties();
				setValuesOrganizationManagementAudit(organizationManagementAudit,sourceSchool,destinationSchool,null,userDetails,"MERGE");
				logger.info(organizationManagementAudit.toString());
				organizationManagementAudits.add(organizationManagementAudit);
			}
		}
		// move rosters to destination school
		List<Roster> rosters = rosterService.getRostersByOrgId(sourceSchool, currentSchoolYear);
		for(Roster roster : rosters) {
			rosterService.transferRoster(roster, destinationSchool);
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(sourceSchool, "MERGE");
			organizationManagementAudit.setDestOrgId(destinationSchool);
			organizationManagementAudit.setRosterId(roster.getId());
			organizationManagementAudit.setAartUserId(roster.getTeacherId());
			if(roster.getTeacher() !=null){
			organizationManagementAudit.setAartUserName(roster.getTeacher().getUserName());
			}
			organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
			organizationManagementAudit.setAuditColumnProperties();
			organizationManagementAudit.setRosterName(roster.getCourseSectionName());
			setValuesOrganizationManagementAudit(organizationManagementAudit,sourceSchool,destinationSchool,null,userDetails,"MERGE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		
		List<Long> destApIds=organizationDao.getOrgAssessmentProgramIds(destinationSchool);
		List<Long> sourceApIds=organizationDao.getOrgAssessmentProgramIds(sourceSchool);
		
		if (!destApIds.isEmpty()) {
			for (Long destAssessmentprogramId : destApIds) {
				for (Long sourceAssessmentprogramId : sourceApIds) {
					if (destAssessmentprogramId.equals(sourceAssessmentprogramId)) {
						organizationDao.disableAssessmentProgramByOrgId(sourceSchool, destinationSchool, sourceAssessmentprogramId,
								userDetails.getUserId(),new Date());
					} else {
						//do nothing
					}
				}
			}
			organizationDao.moveOrganization(sourceSchool, destinationSchool, userDetails.getUserId(), new Date(), currentSchoolYear);
		} else {
			organizationDao.moveOrganization(sourceSchool, destinationSchool, userDetails.getUserId(), new Date(), currentSchoolYear);
		}
		
		//organizationDao.moveOrganization(sourceSchool, destinationSchool, userDetails.getUserId(), new Date());
		// update in audit table
		for(OrganizationManagementAudit organizationManagementAudit : organizationManagementAudits){
			organizationManagementAuditDao.insertManagementAudit(organizationManagementAudit);
		}
		
		Organization sourceorg = get(sourceSchool);
		String sourceorgdisplayidentifier = sourceorg.getDisplayIdentifier();
		String sourceorgname = sourceorg.getOrganizationName();
		organizationDao.saveMergedOrgDetails(sourceSchool, sourceorgdisplayidentifier, sourceorgname, destinationSchool,activeflag, action,
				currentSchoolYear,reportYear,new Date(),new Date(),userDetails.getUserId(),userDetails.getUserId());
		
		// deactivate school (Do we need this?)
		deleteOrganization(sourceSchool,true);
		
		return 1L;
	}

	private void deleteOrganization(Long organizationId, Boolean isMerged) {
		Organization organization = get(organizationId);
		organization.setAuditColumnPropertiesForDelete();
		organization.setIsMerged(isMerged);
		organizationDao.deleteOrganization(organization);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long moveSchool(Long sourceSchool, Long destinationDistrict) throws Exception {
		validateOrganizationType(sourceSchool, CommonConstants.ORGANIZATION_SCHOOL_CODE);
		validateOrganizationType(destinationDistrict, CommonConstants.ORGANIZATION_DISTRICT_CODE);
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Long currentSchoolYear= userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		//Long reportYear = (long) userDetails.getUser().getContractingOrganization().getReportYear();
		OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(sourceSchool, "MOVE");
		organizationManagementAudit.setDestOrgId(destinationDistrict);
		organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
		organizationManagementAudit.setAuditColumnProperties();
		setValuesOrganizationManagementAudit(organizationManagementAudit,sourceSchool,null,destinationDistrict,userDetails,"MOVE");
		organizationDao.moveSchool( sourceSchool,  destinationDistrict, new Date(), userDetails.getUser().getId(), currentSchoolYear);
		logger.info(organizationManagementAudit.toString());
		organizationManagementAuditDao.insertManagementAudit(organizationManagementAudit);
		
		return 1L;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean moveSchoolFromAPI(Organization sourceSchool, Long destinationDistrict, Long userID) throws Exception {
		boolean isSuccessful=false;
		if(sourceSchool.getTypeCode().equalsIgnoreCase(CommonConstants.ORGANIZATION_SCHOOL_CODE)) {
			User user= userService.get(userID);
			Organization curContractingOrganization = organizationDao.getContractingOrg(sourceSchool.getId());
			Date date=curContractingOrganization.getSchoolEndDate();
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			Long currentSchoolYear=(long) calendar.get(Calendar.YEAR);
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(sourceSchool.getId(), "MOVE");
			organizationManagementAudit.setDestOrgId(destinationDistrict);
			organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
			organizationManagementAudit.setCreatedUser(userID);
			organizationManagementAudit.setModifiedUser(userID);
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAuditAPI(organizationManagementAudit,sourceSchool.getId(),null,destinationDistrict,user,"MOVE");
			organizationDao.moveSchool( sourceSchool.getId(),  destinationDistrict, new Date(), user.getId(), currentSchoolYear);
			logger.info(organizationManagementAudit.toString());
			organizationManagementAuditDao.insertManagementAudit(organizationManagementAudit);
			isSuccessful=true;
		}
		return isSuccessful;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long disableSchool(Long schoolId) throws Exception {
		validateOrganizationType(schoolId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
		disableBySchoolId(schoolId);
		return 1L;
	}
	

	private void disableBySchoolId(Long schoolId) {
		// Disable all students in school
		// Disable all users organizations for the given school
		// Disable all rosters in school
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
	 	Long currentSchoolYear= userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
	 	
	 	List<OrganizationManagementAudit> organizationManagementAudits = new ArrayList<OrganizationManagementAudit>();
	 	
		// move students to destination school
		List<Enrollment> enrollments = enrollmentService.getEnrollementsByOrg(schoolId, currentSchoolYear);
		logger.info(currentSchoolYear.toString());
		OrganizationManagementAudit organizationManagementAuditdisable = new OrganizationManagementAudit(schoolId, "DISABLE");
		organizationManagementAuditdisable.setCurrentSchoolYear(currentSchoolYear);
		organizationManagementAuditdisable.setAuditColumnProperties();
		setValuesOrganizationManagementAudit(organizationManagementAuditdisable,schoolId,null,null,userDetails,"DISABLE");
		organizationManagementAudits.add(organizationManagementAuditdisable);		
		for(Enrollment enrollment: enrollments){
			// update enrollment with destination school id
			enrollmentService.disableEnrollment(enrollment);
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(schoolId, "DISABLE");
			organizationManagementAudit.setEnrollmentId(enrollment.getId());
			organizationManagementAudit.setStudentId(enrollment.getStudentId());
			organizationManagementAudit.setStateStudentIdentifier(enrollment.getStudent().getStateStudentIdentifier());
			organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAudit(organizationManagementAudit,schoolId,null,null,userDetails,"DISABLE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		
		// move users to destination school
		List<UserOrganization> userOrganizations = userService.getUserOrganizationsByOrgId(schoolId);
		for(UserOrganization userOrganization : userOrganizations){
			Long count = userService.checkUserOrganizationCount(userOrganization.getUserId());
			if(count == 1){
				userService.deactivateUser(userOrganization.getUserId());
			}
			userService.disableUserOrganization(userOrganization);
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(schoolId, "DISABLE");
			organizationManagementAudit.setAartUserId(userOrganization.getUserId());
			organizationManagementAudit.setAartUserName(userOrganization.getUserName());
			organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAudit(organizationManagementAudit,schoolId,null,null,userDetails,"DISABLE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		// move rosters to destination school
		List<Roster> rosters = rosterService.getRostersByOrgId(schoolId, currentSchoolYear);
		for(Roster roster : rosters) {
			rosterService.disableRoster(roster);
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(schoolId, "DISABLE");
			organizationManagementAudit.setRosterId(roster.getId());
			organizationManagementAudit.setRosterName(roster.getCourseSectionName());
			organizationManagementAudit.setAartUserId(roster.getTeacherId());
			if(roster.getTeacher() !=null){
				organizationManagementAudit.setAartUserName(roster.getTeacher().getUserName());
			}
			organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAudit(organizationManagementAudit,schoolId,null,null,userDetails,"DISABLE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		// update in audit table
		for(OrganizationManagementAudit organizationManagementAudit : organizationManagementAudits){
			organizationManagementAuditDao.insertManagementAudit(organizationManagementAudit);
		}
		// deactivate school (Do we need this?)
		deleteOrganization(schoolId, false);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long disableDistrict(Long districtId) throws Exception {
		validateOrganizationType(districtId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
		List<Organization> schools = getAllChildrenByOrgTypeCode(districtId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
		for(Organization school: schools){
			disableBySchoolId(school.getId());
		}
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		List<OrganizationManagementAudit> organizationManagementAudits = new ArrayList<OrganizationManagementAudit>();
		List<UserOrganization> userOrganizations = userService.getUserOrganizationsByOrgId(districtId);
		for(UserOrganization userOrganization : userOrganizations){
			Long count = userService.checkUserOrganizationCount(userOrganization.getUserId());
			if(count == 1){
				userService.deactivateUser(userOrganization.getUserId());
			}
			userService.disableUserOrganization(userOrganization);
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(districtId, "DISABLE");
			organizationManagementAudit.setAartUserId(userOrganization.getUserId());
			organizationManagementAudit.setAartUserName(userOrganization.getUserName());
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAudit(organizationManagementAudit,null,null,null,userDetails,"DISABLE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		deleteOrganization(districtId, false);
		return 1L;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean disableOrganizationFromAPI(Organization org, Long userId) throws Exception {
		boolean isSuccessful=true;
		if(org!=null) {
			String orgType= org.getOrganizationType().getTypeCode();
			if(orgType.equalsIgnoreCase("ST")) {
				//create message not allowed to disable State level organization
				isSuccessful=false;
			}else if(orgType.equalsIgnoreCase(CommonConstants.ORGANIZATION_DISTRICT_CODE)) {
				
				logger.debug("--> disableDistrict");
				//Disable its child organization
				List<Organization> schools = getAllChildrenByOrgTypeCode(org.getId(), CommonConstants.ORGANIZATION_SCHOOL_CODE);
				for(Organization school: schools){
					if(school.getActiveFlag()!=null && school.getActiveFlag()==true ) {
						isSuccessful=false;
						break;
					}
				}
				//Disable District
				if(isSuccessful) {
					org.setModifiedDate(new Date());
					org.setModifiedUser(userId); 
			    	if(org.getActiveFlag() == null) {
			    		org.setActiveFlag(false);
			    	}
			    	org.setIsMerged(false);
					organizationDao.deleteOrganization(org);
					isSuccessful=true;
				}
				
			}else if(orgType.equalsIgnoreCase(CommonConstants.ORGANIZATION_SCHOOL_CODE)) {
				//Disable School
				logger.debug("--> disableSchool");
				//Need to see the impact of not in-activating the enrollment and rosters
				org.setModifiedDate(new Date());
				org.setModifiedUser(userId); 
		    	if(org.getActiveFlag() == null) {
		    		org.setActiveFlag(false);
		    	}
		    	org.setIsMerged(false);
				organizationDao.deleteOrganization(org);
				organizationDao.deleteSchoolFromOrgTreeDetail(org.getId());
				isSuccessful=true;
			}else {
				//The api works only if the org is of type DT or SCH
			}
		}
		return isSuccessful;
	}
	
	public boolean validateOrganizationType(Long organizationId, String orgTypeCode) throws Exception {
		Organization srcOrg = get(organizationId);
		if (!srcOrg.getOrganizationType().getTypeCode().equals(orgTypeCode)) {
			throw new Exception(organizationId + " is not a  valid organization of type " + orgTypeCode);
		}
		return true;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getDeactivateChildrenByOrgTypeCode(Long organizationId, String orgTypeCode) {
		List<Organization> children =  organizationDao.getDeactivateChildrenByOrgTypeCode(organizationId, orgTypeCode);
		return children;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getLoggedInUserDeactivateOrganizationHierarchy(Long organizationId, String orgTypeCode) {
		List<Organization> loggedInUserOrganizations =  organizationDao.getLoggedInUserDeactivateOrganizationHierarchy(organizationId, orgTypeCode);
    	return loggedInUserOrganizations;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long enableSchool(Long schoolId) throws Exception {
		validateOrganizationType(schoolId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
		enableBySchoolId(schoolId);
		return 1L;
	}

	private void enableBySchoolId(Long schoolId) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
	 	Long currentSchoolYear= userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
	 	
	 	List<OrganizationManagementAudit> organizationManagementAudits = new ArrayList<OrganizationManagementAudit>();
	 	OrganizationManagementAudit organizationManagementAuditDirectEntry = new OrganizationManagementAudit(schoolId, "ENABLE");
	 	organizationManagementAuditDirectEntry.setCurrentSchoolYear(currentSchoolYear);
	 	organizationManagementAuditDirectEntry.setAuditColumnProperties();
		setValuesOrganizationManagementAudit(organizationManagementAuditDirectEntry,schoolId,null,null,userDetails,"ENABLE");
		logger.info(organizationManagementAuditDirectEntry.toString());
		organizationManagementAudits.add(organizationManagementAuditDirectEntry);
		
	 	List<UserOrganization> userOrganizations = userService.getDeactiveUserOrganizationsByOrgId(schoolId);
		for(UserOrganization userOrganization : userOrganizations){
			Long count = userService.checkUserOrganizationCount(userOrganization.getUserId());
			if(count == 0){
				userService.activateUser(userOrganization.getUserId());
			}
			userService.enableUserOrganization(userOrganization);
			//userService.setStatusToInactive(userOrganization.getId());
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(schoolId, "ENABLE");
			organizationManagementAudit.setAartUserId(userOrganization.getUserId());
			organizationManagementAudit.setAartUserName(userOrganization.getUserName());
			organizationManagementAudit.setCurrentSchoolYear(currentSchoolYear);
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAudit(organizationManagementAudit,schoolId,null,null,userDetails,"ENABLE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		
			// update in audit table
			for(OrganizationManagementAudit organizationManagementAudit : organizationManagementAudits){
				organizationManagementAuditDao.insertManagementAudit(organizationManagementAudit);
			}
			
			enableOrganization(schoolId);
		
	}

	private void enableOrganization(Long organizationId) {
		Organization organization = get(organizationId);
		organization.setAuditColumnPropertiesForUpdate();
		organizationDao.enableOrganization(organization);
		organizationDao.deactiveMergedOperation(organization);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long enableDistrict(Long districtId) throws Exception {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		validateOrganizationType(districtId, CommonConstants.ORGANIZATION_DISTRICT_CODE);
		List<Organization> schools = getAllDeactiveChildrenByOrgTypeCode(districtId, CommonConstants.ORGANIZATION_SCHOOL_CODE);
		for(Organization school: schools){
			enableBySchoolId(school.getId());
		}
		List<OrganizationManagementAudit> organizationManagementAudits = new ArrayList<OrganizationManagementAudit>();

	 	List<UserOrganization> userOrganizations = userService.getDeactiveUserOrganizationsByOrgId(districtId);
		for(UserOrganization userOrganization : userOrganizations){
			Long count = userService.checkUserOrganizationCount(userOrganization.getUserId());
			if(count == 0){
				userService.activateUser(userOrganization.getUserId());
			}
			userService.enableUserOrganization(userOrganization);
			//userService.setStatusToInactive(userOrganization.getId());
			
			OrganizationManagementAudit organizationManagementAudit = new OrganizationManagementAudit(districtId, "ENABLE");
			organizationManagementAudit.setAartUserId(userOrganization.getUserId());
			organizationManagementAudit.setAartUserName(userOrganization.getUserName());
			organizationManagementAudit.setAuditColumnProperties();
			setValuesOrganizationManagementAudit(organizationManagementAudit,null,null,null,userDetails,"ENABLE");
			logger.info(organizationManagementAudit.toString());
			organizationManagementAudits.add(organizationManagementAudit);
		}
		enableOrganization(districtId);
		return 1L;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean enableOrganizationFromAPI(Organization org, Long userId) throws Exception {
		boolean isSuccessful=false;
		if(org!=null) {
			if(org.getOrganizationType().getTypeCode().equalsIgnoreCase(CommonConstants.ORGANIZATION_SCHOOL_CODE)) {
				User user= userService.get(userId);
				OrganizationManagementAudit organizationManagementAuditDirectEntry = new OrganizationManagementAudit(org.getId(), "ENABLE");
				//Deb check: public Long getReportYear(Long contractingOrgId, Long assessmentProgramId)
				Organization contractingOrg= organizationDao.getContractingOrg(org.getId());
				/*Date date=contractingOrg.getCurrentSchoolYear() contractingOrg.getSchoolEndDate();
				Calendar calendar = new GregorianCalendar();
				calendar.setTime(date);*/
				//Long currentSchoolYear=(long) calendar.get(Calendar.YEAR);
				Long currentSchoolYear = contractingOrg.getCurrentSchoolYear();
				organizationManagementAuditDirectEntry.setCurrentSchoolYear(currentSchoolYear);
				organizationManagementAuditDirectEntry.setCreatedUser(userId);
				organizationManagementAuditDirectEntry.setModifiedUser(userId);
			 	organizationManagementAuditDirectEntry.setAuditColumnProperties();
				setValuesOrganizationManagementAuditAPI(organizationManagementAuditDirectEntry,org.getId(),null,null,user,"ENABLE");
				logger.info(organizationManagementAuditDirectEntry.toString());
				organizationManagementAuditDao.insertManagementAudit(organizationManagementAuditDirectEntry);
			}
			org.setModifiedDate(new Date());
			org.setModifiedUser(userId); 
	    	if(org.getActiveFlag() == null) {
	    		org.setActiveFlag(true);
	    	}
			organizationDao.enableOrganization(org);
			organizationDao.deactiveMergedOperation(org);
			//updating the organizationrelation table
			org=organizationDao.get(org.getId());
			addOrganizationtreeDetail(org.getOrganizationType().getTypeCode(), org);
			isSuccessful=true;
			logger.info(org.getId()+"Enabled");
		}
		return isSuccessful;
		
	}
	
	private List<Organization> getAllDeactiveChildrenByOrgTypeCode(Long organizationId, String orgTypeCode) {
		
		List<Organization> children =  organizationDao.getAllDeactiveChildrenByOrgTypeCode(organizationId, orgTypeCode);
        
        return children;
	}

	@Override
	public boolean isOrgChild(List<Long> orgIds, Long selectedDistrictId, Long selectedSchoolId, Long userOrgType,
			Long selectedOrgType) {

		boolean checkUserOrgExists = false;
		if (userOrgType == orgTypeDao.getByTypeCode("CONS").getOrganizationTypeId()) {
			checkUserOrgExists = true;
		}else if (userOrgType == orgTypeDao.getByTypeCode("ST").getOrganizationTypeId()) {
			if (orgIds.contains(selectedDistrictId) || orgIds.contains(selectedSchoolId)) {
				checkUserOrgExists = true;
			} else {
				checkUserOrgExists = false;
			}
		} else if (userOrgType == orgTypeDao.getByTypeCode("DT").getOrganizationTypeId()) {
			if (orgIds.contains(selectedDistrictId) || orgIds.contains(selectedSchoolId)) {
				checkUserOrgExists = true;
			}
		} else if (userOrgType == orgTypeDao.getByTypeCode("SCH").getOrganizationTypeId()) {
			if (orgIds.contains(selectedSchoolId)) {
				checkUserOrgExists = true;
			}
		}

		return checkUserOrgExists;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getDLMStatesForResearchSurvey(String assessmentProgram,
			Long operationalWindowId) {		
		return organizationDao.getDLMStatesForResearchSurvey(assessmentProgram, operationalWindowId);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getContractingOrgsByAssessmentProgramIdOTWId(Long assessmentProgramId, Long operationalTestWindowId, String enrollmentMethod) {
		return organizationDao.getContractingOrgsByAssessmentProgramIdOTWId(assessmentProgramId, operationalTestWindowId, enrollmentMethod);
	}

	@Override
	public List<Organization> getBundledReportOrg(Map<String, Object> params) {
		// TODO Auto-generated method stub
		
		return organizationDao.getBundledReportOrg(params);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Organization> getStatesByAssesmentPrograms(List<Long> assesmentProgramList,
			String organizationStateCode) {
		// TODO Auto-generated method stub
		List<Organization> org= new ArrayList<Organization>();
		Set<Organization> orgSet= new HashSet<Organization>();
		for(Long assesmentProgramId:assesmentProgramList)
		{
			orgSet.addAll(organizationDao.getStatesBasedOnassessmentProgramId(assesmentProgramId));
		}
		
		org.addAll(orgSet);
		try
		{
		Collections.sort(org);
		}
		catch(Exception e)
		{
			logger.error("Error Sorting",e);
		}
		return org;
	}

	@Override
	public Long getStateIdByUserOrgId(Long userOrgId) {
		List<Organization> userOrgHierarchy = new ArrayList<Organization>();
		userOrgHierarchy = organizationDao.getAllParents(userOrgId);
		userOrgHierarchy.add(organizationDao.get(userOrgId));
		Long stateId = null;
		for (Organization org : userOrgHierarchy) {
			if (org.getOrganizationType().getTypeCode().equals("ST")) {
				stateId = org.getId();
			}
		}
		return stateId;
	}

	@Override
	public String getStateCodeByStateId(Long stateId) {
		// TODO Auto-generated method stub
		return organizationDao.getStateCodeByStateId(stateId);
	}

	@Override
	public String getStateNameByStateId(Long stateId) {
		// TODO Auto-generated method stub
		return organizationDao.getStateNameByStateId(stateId);
	}

	@Override
	public List<Organization> getStateByAssessmentProgramIdForUploadResults(
			long assessmentProgramId, Long userId) {
		// TODO Auto-generated method stub		
		
		List<Organization> uploadResultsState =	organizationDao.getStateByAssessmentProgramIdForUploadResults(assessmentProgramId,userId);
		
		return uploadResultsState;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Organization getByDisplayIdentifierAndType(String displayIdentifier, String orgTypeCode) {
	    
		Organization children =  organizationDao.getByDisplayIdentifierAndType(displayIdentifier, orgTypeCode);

	    return children;
	}
	
    @Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Organization> getByDisplayIdentifierAndParent_ActiveOrInactive(String displayIdentifier, long organizationId) {
        List<Organization> org = null;
        if (displayIdentifier != null) {
            org = organizationDao.getByDisplayIdentifierAndParent_ActiveOrInactive(
                    displayIdentifier.toUpperCase(), organizationId);
        }
        return org;
    }

	@Override
	public Organization getStateByNameAndType(String state, String typeCode) {
		// TODO Auto-generated method stub
		return organizationDao.getStateByNameAndType(state,typeCode);
	}

	@Override
	public Organization getOrgByDisplayIdAndParentId(
			String residenceDistrictIdentifier, Long id, Integer orgTypeId) {
		// TODO Auto-generated method stub
		return organizationDao.getOrgByDisplayIdAndParentId(residenceDistrictIdentifier,id,orgTypeId);
	}
	
	@Override
	public List<Organization> getStateByUserIdForAuditHistoryLog(Long userId) {
		// TODO Auto-generated method stub
		List<Organization> uploadResultsState =	organizationDao.getStateByUserIdForAuditHistoryLog(userId);
		return uploadResultsState;
	}
	/*
	 * Sudhansu : F61 - Manage organization
	 */
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void createOrganizationSnapshot(Long orgId, Long userId){		
		//insert into snapshot table
		List<Long> ids = organziaSnapshotMapper.insert(orgId,userId);//insert into snapshot table
		organziaSnapshotMapper.updateParentSnapshot(ids);//update parent snapshot id 	
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getAllActiveAndInactiveChildrenByOrgTypeCode(
			Long stateId, String organizationCode) {
		// TODO Auto-generated method stub
		return organizationDao.getAllActiveAndInactiveChildrenByType(stateId, organizationCode);
	}
	
	@Override
	public boolean checkForOrganizationAssessmentProgram(Long organizationId, String assessmentProgram) {
		boolean orgHasAP = false;
		List<Long> orgs = organizationDao.checkForOrganizationAssessmentProgram(organizationId, assessmentProgram);
		if(orgs != null && orgs.size() > 0){
			orgHasAP = true;
		}	
		return orgHasAP;
	}

	@Override
	public List<Organization> getContractingOrgsForPredictiveReports(Long assessmentProgramId, Long testingProgramId,
			String testingCycle) {
		return organizationDao.getContractingOrgsForPredictiveReports(assessmentProgramId, testingProgramId, testingCycle);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Organization getContractingOrgByAssessmentProgramIdOrgId(Long assessmentProgramId, Long organizationId) {
		return organizationDao.getContractingOrgByAssessmentProgramIdOrgId(assessmentProgramId, organizationId);
	}
	
	private void setValuesOrganizationManagementAudit(OrganizationManagementAudit organizationManagementAudit, Long sourceSchool, Long destinationSchool, Long destinationDistrict,UserDetailImpl userdetails,String operationtype){
		
		List<Organization> sourceparentList=organizationDao.getInactiveActiveParentOrgDetailsById(sourceSchool);
		
		String stateName=new String();
		Long stateId=null;
		String sourcedistrictName=new String();
		Long sourcedistrictId=null;
		String sourceschoolName=new String();
		Long sourceschoolId=null;
		
		for(Organization org:sourceparentList){
			if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_STATE_CODE)){
				stateName=org.getOrganizationName();
				stateId=org.getId();
			}
			else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_DISTRICT_CODE)){
				sourcedistrictName=org.getOrganizationName();
				sourcedistrictId=org.getId();
			}else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				sourceschoolName=org.getOrganizationName();
				sourceschoolId=org.getId();
			} 
		}
		
		if(operationtype.equals("MERGE")){
		
		List<Organization> destparentList=organizationDao.getInactiveActiveParentOrgDetailsById(destinationSchool);
		String destdistrictName=new String();
		Long destdistrictId=null;
		String destSchoolName=new String();
		Long destschoolId=null;
		
		for(Organization org:destparentList){
			
			if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_DISTRICT_CODE)){
				destdistrictName=org.getOrganizationName();
				destdistrictId=org.getId();
			}else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				destSchoolName=org.getOrganizationName();
				destschoolId=org.getId();
			} 
		}
		
		organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName);
		organizationManagementAudit.setDestOrgNameWithIdentifier(destSchoolName);
		organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);
		organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
		organizationManagementAudit.setDestOrgDistrictId(destdistrictId);
		organizationManagementAudit.setDestOrgDistrictName(destdistrictName);
		organizationManagementAudit.setSateId(stateId);
		organizationManagementAudit.setStateName(stateName);
		if(userdetails !=null){
			organizationManagementAudit.setModifiedUserName(userdetails.getUser().getUserName());
		}
		}
		else if(operationtype.equals("MOVE")){
			
			Organization districtOrg = get(destinationDistrict);
			organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName); 
			organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);  
			organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
			organizationManagementAudit.setDestOrgDistrictId(destinationDistrict);
			if(districtOrg !=null){
				organizationManagementAudit.setDestOrgDistrictName(districtOrg.getOrganizationName());
			}
			organizationManagementAudit.setSateId(stateId);
			organizationManagementAudit.setStateName(stateName);
			if(userdetails !=null){
			organizationManagementAudit.setModifiedUserName(userdetails.getUser().getUserName());
			}
		}
		else if(operationtype.equals("DISABLE")){
			
			if(sourceschoolName !=null){
				organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName);
			}
			organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);
			organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
			organizationManagementAudit.setSateId(stateId);
			organizationManagementAudit.setStateName(stateName);
			if(userdetails !=null){
				organizationManagementAudit.setModifiedUserName(userdetails.getUser().getUserName());
			}
		}
		else if(operationtype.equals("ENABLE")){
			if(sourceschoolName !=null){
				organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName);
			}
			organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);
			organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
			organizationManagementAudit.setSateId(stateId);
			organizationManagementAudit.setStateName(stateName);
			if(userdetails !=null){
			organizationManagementAudit.setModifiedUserName(userdetails.getUser().getUserName());
			}
		}
	}
	
private void setValuesOrganizationManagementAuditAPI(OrganizationManagementAudit organizationManagementAudit, Long sourceSchool, Long destinationSchool, Long destinationDistrict,User userdetails,String operationtype){
		
		List<Organization> sourceparentList=organizationDao.getInactiveActiveParentOrgDetailsById(sourceSchool);
		
		String stateName=new String();
		Long stateId=null;
		String sourcedistrictName=new String();
		Long sourcedistrictId=null;
		String sourceschoolName=new String();
		Long sourceschoolId=null;
		
		for(Organization org:sourceparentList){
			if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_STATE_CODE)){
				stateName=org.getOrganizationName();
				stateId=org.getId();
			}
			else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_DISTRICT_CODE)){
				sourcedistrictName=org.getOrganizationName();
				sourcedistrictId=org.getId();
			}else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				sourceschoolName=org.getOrganizationName();
				sourceschoolId=org.getId();
			} 
		}
		
		if(operationtype.equals("MERGE")){
		
		List<Organization> destparentList=organizationDao.getInactiveActiveParentOrgDetailsById(destinationSchool);
		String destdistrictName=new String();
		Long destdistrictId=null;
		String destSchoolName=new String();
		Long destschoolId=null;
		
		for(Organization org:destparentList){
			
			if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_DISTRICT_CODE)){
				destdistrictName=org.getOrganizationName();
				destdistrictId=org.getId();
			}else if(org.getTypeCode()!=null && StringUtils.equalsIgnoreCase(org.getTypeCode(), CommonConstants.ORGANIZATION_SCHOOL_CODE)){
				destSchoolName=org.getOrganizationName();
				destschoolId=org.getId();
			} 
		}
		
		organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName);
		organizationManagementAudit.setDestOrgNameWithIdentifier(destSchoolName);
		organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);
		organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
		organizationManagementAudit.setDestOrgDistrictId(destdistrictId);
		organizationManagementAudit.setDestOrgDistrictName(destdistrictName);
		organizationManagementAudit.setSateId(stateId);
		organizationManagementAudit.setStateName(stateName);
		if(userdetails !=null){
			organizationManagementAudit.setModifiedUserName(userdetails.getUserName());
		}
		}
		else if(operationtype.equals("MOVE")){
			
			Organization districtOrg = get(destinationDistrict);
			organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName); 
			organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);  
			organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
			organizationManagementAudit.setDestOrgDistrictId(destinationDistrict);
			if(districtOrg !=null){
				organizationManagementAudit.setDestOrgDistrictName(districtOrg.getOrganizationName());
			}
			organizationManagementAudit.setSateId(stateId);
			organizationManagementAudit.setStateName(stateName);
			if(userdetails !=null){
			organizationManagementAudit.setModifiedUserName(userdetails.getUserName());
			}
		}
		else if(operationtype.equals("DISABLE")){
			
			if(sourceschoolName !=null){
				organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName);
			}
			organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);
			organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
			organizationManagementAudit.setSateId(stateId);
			organizationManagementAudit.setStateName(stateName);
			if(userdetails !=null){
				organizationManagementAudit.setModifiedUserName(userdetails.getUserName());
			}
		}
		else if(operationtype.equals("ENABLE")){
			if(sourceschoolName !=null){
				organizationManagementAudit.setSourceOrgNameWithIdentifier(sourceschoolName);
			}
			organizationManagementAudit.setSourceOrgDistrictId(sourcedistrictId);
			organizationManagementAudit.setSourceOrgDistrictName(sourcedistrictName);
			organizationManagementAudit.setSateId(stateId);
			organizationManagementAudit.setStateName(stateName);
			if(userdetails !=null){
			organizationManagementAudit.setModifiedUserName(userdetails.getUserName());
			}
		}
	}
	
	
	@Override
	public TimeZone getTimeZoneForOrganization(Long organizationId) {
		String tzCode = organizationDao.getTimeZoneForOrg(organizationId);
		if (tzCode != null) {
			return TimeZone.getTimeZone(tzCode);
		}
		logger.info("!!!!!organization without timezone found: "+organizationId);
		return null;
	}
	@Override
	public Long getReportYear(Long contractingOrgId, Long assessmentProgramId) {
		return organizationDao.getReportYear(contractingOrgId, assessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateOrganizationMergeRelation(Long stateId, String orgType, String orgDisplayId) {
		organizationDao.updateOrganizationMergeRelation(stateId, orgType, orgDisplayId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Boolean isChildOf(Long parentOrgId, Long childOrgId) {
		return organizationDao.isChildOf(parentOrgId, childOrgId);
	}

	@Override
	public Organization getCeteOrganization() {
		return organizationDao.getCeteOrganization();
	}

	@Override
	public List<Organization> getPermittedStateIds(Long userId) {
		return organizationDao.getPermittedStateIds(userId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Organization> getInterimSchoolsInDistrict(Long districtId, Long currentSchoolYear, Boolean predictiveStudentScore, Long assessmentProgramId) {
		return organizationDao.getInterimSchoolsInDistrict(districtId,currentSchoolYear, predictiveStudentScore,assessmentProgramId);
	}

	@Override
	public List<Organization> getInterimDistrictsInState(Long stateId, Long currentSchoolYear, Boolean predictiveStudentScore, Long assessmentProgramId) {
		return organizationDao.getInterimDistrictsInState(stateId,currentSchoolYear, predictiveStudentScore, assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void setReportYearToSchoolYear(Long stateId, Long modifiedUserid) {
		organizationDao.setReportYearToSchoolYear(stateId, modifiedUserid);		
	}

	@Override
	public List<Organization> getByDisplayIdentifierAndTypeId(
			String displayIdentifier, Integer typeId) {
		return organizationDao.getByDisplayIdentifierAndTypeId(displayIdentifier, typeId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getParentOrganizationsByOrgId(Long organizationId) {
		return organizationDao.getParentOrganizationsByOrgId(organizationId);
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Organization getDistrictBySchoolOrgId(Long organizationId) {
		return organizationDao.getDistrictBySchoolOrgId(organizationId);
		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Organization getOrganizationByDisplayIdentifierAndRelationalOrgnizationId(String districtIdentifier,
			Long userOrganizationId) {
        Organization org = null;
        List<Organization> orgs;
        if (districtIdentifier != null) {
            orgs = organizationDao.getOrganizationByDisplayIdentifierAndRelationalOrgnizationId(
            		districtIdentifier.toUpperCase(), userOrganizationId);
            if (CollectionUtils.isNotEmpty(orgs)) {
                org = orgs.get(0);
            }
        }
        return org;
    }
	@Override
	public Date getSchoolStartDate(Long orgId){
		return organizationDao.getSchoolStartDate(orgId);
		
	}
	//Added Saikat
	@Override
	public Date getSchoolEndDate(Long orgId){
		return organizationDao.getSchoolEndDate(orgId);
		
	}
	@Override
	public Date getTestEndTime(Long orgId){
		return organizationDao.getTestEndTime(orgId);
		
	}
	
	@Override
	public Date getTestBeginTime(Long orgId){
		return organizationDao.getTestBeginTime(orgId);
		
	}
	
	@Override
	public String getTestDays(Long orgId){
		return organizationDao.getTestDays(orgId);
		
	}
	
	@Override
	public Boolean isUserMappedToGivenState(Long stateId, Long userId) {
		return organizationDao.isUserMappedToGivenState( stateId,  userId);
	}

	
	@Override
	public Boolean isIEModelState(Organization state) {
		//Deb: Please verify if the state is valid or not before using the method
		//In this method if the pool type is SINGLEEE we are considering the state as IE Model state else YearEnd Model State
		if(state!=null) {
			String porgPoolType = state.getPoolType();
			if(PoolTypeEnum.SINGLEEE.name().equals(porgPoolType)) {
				return true;
			}
		}
		return false;
	}
}
