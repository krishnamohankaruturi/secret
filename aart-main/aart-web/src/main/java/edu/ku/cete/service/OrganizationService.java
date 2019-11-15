package edu.ku.cete.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.security.access.prepost.PreAuthorize;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.OrganizationHierarchy;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.report.domain.DomainAuditHistory;

/**
 * @author nicholas.studt
 */
public interface OrganizationService extends Serializable {

    /**
     * @param toAdd object to add.
     * @return {@link Organization}
     * @throws ServiceException {@link ServiceException}
     */
    @PreAuthorize(value = "hasRole('PERM_ORG_CREATE')")
    Organization add(Organization toAdd) throws ServiceException;

    /**
     * @param id Id of the object to delete.
     * @return boolean was the object deleted.
     */
    @PreAuthorize(value = "hasRole('PERM_ORG_DELETE')")
    boolean delete(Long id);

    /**
     * Get tool by id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
   // @PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    Organization get(Long organizationId);
    
    Organization getOrganizationDetailsByOrgId(Long organizationId);
    
    Organization getParentOrgDetailsById(Long organizationId);
    
    /**
     * Get immediate parent organizations (at any level) for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
   // @PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getImmediateParents(long organizationId);

    /**
     * Get immediate child organizations (at any level) for given organization's id.
     * @param organizationId
     * @return
     */
   // @PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getImmediateChildren(Long organizationId);
    
    /**
     * Get immediate child organizations (at any level) for given organization's id.
     * @param organizations {@link Collection}.
     * @return {@link Organization}
     */
   // @PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getImmediateChildren(Collection<Organization> organizations);

    /**
     * Get all parent organizations at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    //@PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getAllParents(long organizationId);

    /**
     * Get all child organizations at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
   // @PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getAllChildren(long organizationId);


    List<OrganizationDetail> getOrganizationDetails(Long id);
    
    List<OrganizationDetail> getOrganizationDetailByOrgId(Long orgId, Long testingCycleId);
    
    int addOrganizationDetails(OrganizationDetail orgDetail);
    
    int updateOrganizationDetails(OrganizationDetail orgDetail);
    
    /**
     * Get tool by id.
     * @param displayIdentifier Id of the organization.
     * @return {@link Organization}
     */
   // @PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getByDisplayIdentifier(String displayIdentifier);
    
    /**
     * @param displayIdentifier {@link String}
     * @param user {@link User}
     * @return {@link List}
     */
    List<Organization> getByDisplayIdentifier(String displayIdentifier, User user);
    /**
     *
     * @return List of {@link Organization}
     */
  //  @PreAuthorize(value = "hasRole('PERM_ORG_VIEW')")
    List<Organization> getAll();

    /**
     * @param saveOrUpdate the object to be saved or updated.
     * @return {@link Organization}
     * @throws ServiceException {@link ServiceException}
     */
    Organization saveOrUpdate(Organization saveOrUpdate) throws ServiceException;

    /**
     * @param toUpdate the object to update.
     * @return {@link Organization}
     * @throws ServiceException {@link ServiceException}
     */
    @PreAuthorize(value = "hasRole('PERM_ORG_MODFIY')")
    Organization update(Organization toUpdate) throws ServiceException;

    /**
     *
     *@param displayIdentifier {@link String}
     *@param organizationId long
     *@return {@link Organization}
     */
    Organization getByDisplayIdWithContext(String displayIdentifier, long organizationId);

    /**
     *
     *@param displayIdentifier {@link String}
     *@param organizationId long
     *@return {@link Organization}
     */
    Organization getByDisplayIdWithFullContext(String displayIdentifier, long organizationId);

    /**
     *
     *@param organizationId long
     *@param parentOrganizationId long
     */
    void addParentOrganization(long organizationId, long parentOrganizationId);
    
    /**
     * @param organization {@link Organization}
     * @param oldParentOrganizationId {@link Long} 
     * @return {@link Organization} 
     */
    @PreAuthorize(value = "hasRole('PERM_ORG_CREATE')")
    Organization addorUpdateOrganization(Organization organization, Long oldParentOrganizationId);

	/**
	 * @param organization
	 * @return
	 */
	ContractingOrganizationTree getTree(Organization organization);

	
	/**
	 * Get all the child organizations of the given organizationids
	 * @param userOrganizationIds
	 * @param includeGivenOrgIds indicates if the passed organizations also need to be included.
	 * @return
	 */
	List<Organization> getAllChildren(List<Long> userOrganizationIds, boolean includeGivenOrgIds);

	/**
	/**
	 * Get all the child organizations of the given organization ids
	 * @param userOrganizationId
	 * @param includeGivenOrgId indicates if the passed organizations also need to be included.
	 * @return
	 */
	List<Organization> getAllChildren(Long userOrganizationId, boolean includeGivenOrgId);
    
	/**
	 * @param organizationTypeCode
	 * @return
	 */
	List<Organization> getByTypeId(String organizationTypeCode);
	
	List<Organization> getByTypeId(String organizationTypeCode, String sortByCol);
	
	boolean checkForDLM(Long organizationId, String userOrgDLM);

	List<Organization> getAllChildrenDLM(Long organizationId,
			String uSER_ORG_DLM);
	
	/**
	 * 
	 * @param organizationTypeCode
	 * @param userId
	 * @return
	 */
	List<Organization> getByTypeAndUserId(String organizationTypeCode, Long userId);
	
	List<Organization> getByTypeAndUserIdInParent(String organizationTypeCode,
			Long userId, Long parentId);

	List<Organization> getAllChildrenByOrgTypeCode(long organizationId,
			 String orgTypeCode);
	
	
	
	

	List<Organization> getAllChildrenByTypeForEditSSA(String orgTypeCode);
	
		
	List<Organization> getAllChildrenWithParentByOrgTypeCode(long organizationId,
			String orgTypeCode);	
	
	List<Organization> getLoggedInUserOrganizationHierarchy(long organizationId,
			String orgTypeCode);
	
	  /**
     * @param organization {@link Organization}
     * @param oldParentOrganizationId {@link Long} 
     * @return {@link Organization} 
     */
    @PreAuthorize(value = "hasRole('PERM_ORG_CREATE')")
    OrganizationHierarchy addOrganizationStructure(Long organizationId, Long orgtypeId);
    
    
    /**
     * @param displayIdentifier
     * @param orgTypeId
     * @return
     */
    Organization getByDisplayIdAndType(String displayIdentifier, Long orgTypeId, Long contractingOrgId);
    
    /**
     * @param displayIdentifier
     * @param orgTypeCode
     * @return
     */
    Organization getByDisplayIdRelationAndType(String displayIdentifier, Long orgTypeId);
    
    
    /**
     * @return
     */
    List<OrganizationHierarchy> getAllOrganizationHierarchies();
    
    /**
     * @param organizationId
     * @param orgTypeCode
     * @return
     */
    List<Organization> getAllParentsByOrgTypeCode(long organizationId,
			String orgTypeCode);
    
    /**
     * @param organizationId
     * @return
     */
    List<OrganizationHierarchy> getOrgHierarchiesById(Long organizationId);

    
    /**
     * 
     * @param filters
     * @param orderByClause
     * @param offset
     * @param limitCount
     * @return
     */
    List<Organization> getAllChildrenToView(Map<String, Object> filters, String sortByColumn, String sortType, 
    		Integer offset, Integer limitCount);
	
	/**
	 * 
	 * @param filters
	 * @return
	 */
    Integer countAllChildrenToView(Map<String, Object> filters);

    /**
     * 
     * @param orgId
     * @return
     */
    Organization getContractingOrganization(Long orgId);
    /**
     * 
     * @param districtDisplayIdentifier
     * @param orgId
     * @return
     */
	List<Organization> getDistrictInState(String districtDisplayIdentifier,
			Long orgId);
	OrganizationTreeDetail getSchoolInState(String schoolDisplayIdentifier, Long stateId);

	String createOrganization(Organization organization, String orgDisplayId,
			String orgName, String buldingUniqueness, String startDate,
			String endDate, String parentConsortiaOrg, Long parentOrgId,
			Map<String, Long> orgTypeCodeMap, String orgType,
			boolean contractingOrgFlag, String expirePasswords,
			String expirationDateType, String[] organizationStructure,
			Long[] assessmentProgramIds,Long testingModel, Integer reportYear, 
			String testBeginTime,String testEndTime,String testDays);
	
	Organization createOrganizationFromAPI(Organization organization, String orgType,Map<String, Long> orgTypeCodeMap,
			String buldingUniqueness, String[] organizationStructure, ArrayList<Long> assessmentProgramIds);
	
	List<Long> getAllChildrenOrgIds(Long organizationId);
	
	List<Organization> getContractingOrgsByAssessmentProgramId(Long assessmentProgramId);

	List<Organization> getDLMStatesWithMultiEEs();

	List<Organization> getDLMStatesWithPooltype(String assessmentProgram, Boolean multiAssignment);
	
	List<Organization> getDLMStatesWithPooltypeAndOperationalWindow(String assessmentProgram, Boolean multiAssignment, Long operationalWindowId);

	String clearTreeCache(Organization organization);
	
	void updateOrganizationMergeRelation(Long stateId, String orgType, String orgDisplayId);

	List<Organization> getOrgHierarchyByUserId(Long userId);
	
	Organization addorUpdateOrganizationUpload(Organization organization, Long oldParentOrganizationId);
	
	List<Organization> getStatesByOperationalTestWindowId(Long operationalTestWindowId);

	void updateCachedOrganizationDetails();

	void resetEhCacheEntries();
    
	//created for US18004 : For organization json object
	boolean addToOrganizationAuditTrailHistory(DomainAuditHistory domainAuditHistory);
	
	Map<Long, String> getStatesBasedOnassessmentProgramId(Long assessmentProgramId);
	
	List<Organization> getOrganizationByassessmentProgramId(long assessmentId);

	OrganizationTreeDetail getOrganizationDetailBySchoolId(long schoolId);
	
	OrganizationTreeDetail getOrganizationDetailById(long orgId);
	
	Organization updateOrganization(Organization organization);

	int updateOrgnameInOrgTreeDetail(Organization organization) throws ServiceException;

	Long mergeSchool(Long sourceSchool, Long destinationSchool)throws Exception;
	
	Long moveSchool( Long sourceSchool, Long destinationSchool)throws Exception;
	
	Long disableSchool(Long schoolId )throws Exception;
	
	Long disableDistrict(Long districtId )throws Exception;
	
	public boolean disableOrganizationFromAPI(Organization org,Long userId) throws Exception;
	
	boolean validateOrganizationType(Long organizationId, String orgTypeCode) throws Exception;

	List<Organization> getDeactivateChildrenByOrgTypeCode(Long orgId, String orgType);

	List<Organization> getLoggedInUserDeactivateOrganizationHierarchy(Long loggedInUserOrgId, String orgType);

	Long enableSchool(Long schoolId)throws Exception;

	Long enableDistrict(Long districtId)throws Exception;
	
	public boolean enableOrganizationFromAPI(Organization org,Long userId) throws Exception;
	
	boolean isOrgChild(List<Long> orgIds, Long selectedDistrictId, Long selectedSchoolId, Long userOrgType, Long selectedOrgType);
	
	List<Organization> getDLMStatesForResearchSurvey(String assessmentProgram, Long operationalWindowId);

	List<Organization> getContractingOrgsByAssessmentProgramIdOTWId(Long assessmentProgramId, Long operationalTestWindowId, String assessmentProgramCode);

	List<Organization> getBundledReportOrg(Map<String, Object> params);

	List<Organization> getStatesByAssesmentPrograms(List<Long> assesmentProgramList, String organizationStateCode);

	Long getStateIdByUserOrgId(Long userOrgId);

	Organization getByDisplayIdentifierAndType(String displayIdentifier, String orgTypeCode);

	List<Organization> getByDisplayIdentifierAndParent_ActiveOrInactive(String displayIdentifier, long organizationId);
	
	String getStateCodeByStateId(Long stateId);

	String getStateNameByStateId(Long stateId);

	List<Organization> getStateByAssessmentProgramIdForUploadResults(
			long assessmentProgramId, Long userId);

	Organization getStateByNameAndType(String state, String typeCode);

	Organization getOrgByDisplayIdAndParentId(
			String residenceDistrictIdentifier, Long id, Integer orgTypeId);
	
    Organization getViewOrganizationDetailsByOrgId(Long organizationId);
	
	List<Organization> getStateByUserIdForAuditHistoryLog(Long userId);
	
	void createOrganizationSnapshot(Long orgId, Long userId);

	List<Organization> getAllActiveAndInactiveChildrenByOrgTypeCode(
			Long stateId, String organizationCode);

	boolean checkForOrganizationAssessmentProgram(Long organizationId, String assessmentProgram);

	void addOrganizationtreeDetail(String orgType, Organization organizationcreated);
	
	List<Organization> getContractingOrgsForPredictiveReports(Long assessmentProgramId, Long testingProgramId, String testingCycle);
	
	Organization getContractingOrgByAssessmentProgramIdOrgId(Long assessmentProgramId, Long organizationId);

	Long getReportYear(Long contractingOrgId, Long assessmentProgramId);
	
	TimeZone getTimeZoneForOrganization(Long organizationId);

	Boolean isChildOf(Long parentOrgId, Long childOrgId);

	Organization getCeteOrganization();

	List<Organization> getPermittedStateIds(Long userId);

	List<Organization> getInterimSchoolsInDistrict(Long districtId, Long currentSchoolYear, Boolean predictiveStudentScore, Long assessmentProgramId);

	List<Organization> getInterimDistrictsInState(Long stateId, Long currentSchoolYear, Boolean predictiveStudentScore, Long assessmentProgramId);
	
	Collection<? extends Organization> getByTypeAndUserIdInParentByReportYear(
			String organizationSchoolCode, Long userId, Long id, Long reportYear);

	void setReportYearToSchoolYear(Long stateId, Long modifiedUserid);

	List<Organization> getByDisplayIdentifierAndTypeId(
			String displayIdentifier, Integer typeId);
	
	List<Organization> getParentOrganizationsByOrgId(Long organizationId);
	
	Organization getDistrictBySchoolOrgId(Long organizationId);

	Organization getOrganizationByDisplayIdentifierAndRelationalOrgnizationId(String districtIdentifier,Long userOrganizationId);

	boolean moveSchoolFromAPI(Organization sourceSchool, Long destinationDistrict, Long userID) throws Exception;

	Organization addorUpdateOrganizationFromAPI(Organization organization, Long userID);
	
	public Date getSchoolStartDate(Long orgId);
	
	//Saikat Added
	public Date getSchoolEndDate(Long orgId);

	public Date getTestEndTime(Long orgId);
	
	public Date getTestBeginTime(Long orgId);
	
	public String getTestDays(Long orgId);
	
	Boolean isUserMappedToGivenState(Long stateId,  Long userId );

	Boolean isIEModelState(Organization state);

}
