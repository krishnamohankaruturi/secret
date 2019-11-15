package edu.ku.cete.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.OrganizationSnapshot;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationContractRelation;
import edu.ku.cete.domain.common.OrganizationDetail;
import edu.ku.cete.domain.common.OrganizationTreeDetail;

/**
 * @author mrajannan
 */
public interface OrganizationDao {

    /**
     * Create a new organization.
     * @param toAdd object to add.
     * @return number of rows affected.
     */
	//@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")

    int add(Organization toAdd);

    /**
     * @param id Id of the organization.
     * @return number of rows affected.
     */
    int delete(@Param("id") long id);

    /**
     * Get organization by id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    Organization get(long organizationId);
    
    /**
     * Get organization by ExternalID.
     * @param ExternalID of the organization.
     * @return {@link Organization}
     */
    Organization getOrgByExternalID(String organizationExternalId);
    
    /**
     * @param organizationExternalId {@link String}
     * @param organizationTypeCode {@link String}
     * @return {@link Organization}
     */
    Organization getOrgByExternalIDAndTypeCode(String organizationExternalId,String organizationTypeCode);
    
    Organization getOrgDetailsForSummmaryReport(long organizationId);
    
    Organization getOrganizationDetailsByOrgId(long organizationId);
    
    Organization getViewOrganizationDetailsByOrgId(long organizationId);
    
    Organization getParentOrgDetailsById(long organizationId);
    
    /**
     * Get immediate parent organizations (at any level) for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    List<Organization> getImmediateParents(long organizationId);
    
    /**
     * Get immediate child organizations (at any level) for given organization's id.
     * @param organizationId
     * @return
     */
    List<Organization> getImmediateChildrenByParentId(Long organizationId);
    /**
     * Get immediate child organizations (at any level) for given organization's id.
     * @param organizations {@link Collection}
     * @return {@link Organization}
     */
    List<Organization> getImmediateChildren(@Param("organizations")Collection<Organization> organizations);
    /**
     * Get all parent organizations at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    List<Organization> getAllParents(long organizationId);
    /**
     * Get all child organizations at any level for given organization's id.
     * @param organizationId Id of the organization.
     * @return {@link Organization}
     */
    List<Organization> getAllChildren(long organizationId);

    /**
     * Get organization by display identifier.
     * @param displayIdentifier Id of the organization.
     * @return {@link Organization}
     */
    List<Organization> getByDisplayIdentifier(@Param("displayIdentifier") String displayIdentifier);
    
    /**
     * @param organizationName {@link String}
     * @param displayIdentifier {@link String}
     * @return {@link Organization}
     */
    Organization getByDisplayIdentifierAndOrganizationName( @Param("organizationName") String organizationName, @Param("displayIdentifier") String displayIdentifier);

    /**
     *
     * @return List of {@link Organization}
     */
    List<Organization> getAll();

    /**
     * @return the id of the sequence.
     */
    long lastid();

    /**
     * @param toUpdate the object to update.
     * @return number of rows affected.
     */
    int update(Organization toUpdate);
      
    int updateOrgnameInOrgTreeDetail(Organization org);
        
    /**
     * @param organization {@link Organization} 
     * @param oldParentOrganizationId {@link long}
     * @return number of rows affected.
     */
    int updateParentOrganization(@Param("org") Organization organization, 
    		@Param("oldParentOrganizationId") long oldParentOrganizationId);

	/**
	 * @param displayIdentifier {@link String}
	 * @param userOrganizationId {@link Long}
	 * @return {@link List}
	 */
	List<Organization> getByDisplayIdentifierAndParent(
			@Param("displayIdentifier") String displayIdentifier,
			@Param("userOrganizationId")Long userOrganizationId);
	/**
	 * @param displayIdentifier {@link String}
	 * @param userOrganizationId {@link Long}
	 * @return {@link List}
	 */
	List<Organization> getByDisplayIdentifierAndChild(
			@Param("displayIdentifier") String displayIdentifier,
			@Param("userOrganizationId")Long userOrganizationId);	
	
	 /**
	 * @param organization {@link Organization}.
     * @return int.
     */
    int addParentOrganizationByOrg(@Param("org") Organization organization);

	List<OrganizationContractRelation> getTree(@Param("organizationId")Long organizationId);
	
	List<Long> getContractingOrganizationIds();
	
	/**
	 * @param organizationTypeCode
	 * @return
	 */
	List<Organization> getByTypeId(@Param("organizationTypeCode") String organizationTypeCode, @Param("sortByCol") String sortByCol);
	    
	List<Organization> getAllChildrenByType(@Param("organizationId") Long organizationId, @Param("organizationTypeCode") String organizationTypeCode);
	
	List<Organization> getAllChildrenByTypeForEditSSA(@Param("organizationTypeCode") String organizationTypeCode);
	
	List<Organization> getAllChildrenWithParentByType(@Param("organizationId") Long organizationId, @Param("organizationTypeCode") String organizationTypeCode);
	
	List<Long> checkForDLM(@Param("userOrganizationId") Long userOrganizationId, @Param("userOrgDLM") String userOrgDLM);

	List<Organization> getAllChildrenDLM(@Param("userOrganizationId") Long userOrganizationId, @Param("userOrgDLM") String userOrgDLM);
	
	/**
	 * 
	 * @param organizationTypeCode
	 * @param userId
	 * @return
	 */
	List<Organization> getByTypeAndUserId(@Param("organizationTypeCode") String organizationTypeCode, @Param("userId") Long userId);
	
	List<Organization> getByTypeAndUserIdInParent(@Param("organizationTypeCode") String organizationTypeCode, @Param("userId") Long userId,
			@Param("parentId") Long parentId);
	
	/**
	 * 
	 * @param organizationTypeCode
	 * @param userId
	 * @return
	 */
	List<Organization> getLoggedInUserOrganizationHierarchy(@Param("organizationId") Long organizationId, @Param("organizationTypeCode") String organizationTypeCode);
	
	List<Organization> getContractingOrganization(@Param("organizationId") Long organizationId);
	
	/**
	 * @param displayIdentifier
	 * @param orgTypeCode
	 * @return
	 */
	Organization getByDisplayIdAndType(@Param("displayIdentifier")String displayIdentifier, @Param("typeId")Long typeId, @Param("contractingOrgId") Long contractingOrgId);
	
	/**
	 * @param displayIdentifier
	 * @param typeId
	 * @return
	 */
	Organization getByDisplayIdRelationAndType(@Param("displayIdentifier")String displayIdentifier, @Param("typeId")Long typeId);

	 /**
 	 * @param organizationId
 	 * @return
 	 */
 	List<Organization> findSelfAndChildren(Map<String, Object> filters);
    
 	/**
 	 * @param organizationId
 	 * @return
 	 */
 	Integer countFindSelfAndChildren(Map<String, Object> filters);
 	
 	/**
 	 * 
 	 * @param orgId
 	 * @return
 	 */
 	Organization getContractingOrg(@Param("orgId") Long orgId);
 	
 	/**
 	 * 
 	 * @param districtDisplayIdentifier
 	 * @param orgId
 	 * @return
 	 */
	List<Organization> getDistrictInState(@Param("districtDisplayIdentifier") String districtDisplayIdentifier, @Param("orgId") Long orgId);
	OrganizationTreeDetail getSchoolInState(@Param("schoolDisplayIdentifier") String schoolDisplayIdentifier, @Param("stateId") Long stateId);
	
	String getPoolType(@Param("attendenceSchoolId") Long attendenceSchoolId);
	List<String> checkPoolTypes();
	
	List<Organization> getOrgParentsForStudentEnrollmentRecord(@Param("studentId") Long studentId);
	
	List<Organization> getContractingOrgsByAssessmentProgramId(@Param("assessmentProgramId") Long assessmentProgramId);
	
	List<Long> getAllChildrenOrgIds(@Param("organizationId") Long organizationId);
	
	/**
	 * @author bmohanty_sta
     * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15536 : Student Tracker - Simple Version 1 (preliminary)
     * Get organizations that are contracting organizations, assessment as DLM and have pooltype as multi ees
	 * @return
	 */
	List<Organization> getDLMStatesWithMultiEEs();
	
	List<Organization> getDLMStatesWithPooltype(@Param("assessmentProgram") String assessmentProgram, @Param("multiAssignment") Boolean multiAssignment);
	
	List<Organization> getDLMStatesWithPooltypeAndOperationalWindow(@Param("assessmentProgram") String assessmentProgram, @Param("multiAssignment") Boolean multiAssignment, @Param("operationalWindowId") Long operationalWindowId);
	
	List<Long> getAllChildrenIdsByType(@Param("organizationId") Long orgId, @Param("typeCode") String typeCode);
	
	List<Organization> getOrgHierarchyByUserId(@Param("userId") long userId);
	
	Organization getDistrictBySchoolOrgId(@Param("organizationId") Long organizationId);
	
	Organization getDistrictBySchoolOrgIdForSummaryReport(@Param("organizationId") Long organizationId);
	
	List<Organization> getStatesByOperationalTestWindowId(@Param("operationalTestWindowId") Long operationalTestWindowId);	
	int refreshOrgDetails();
	List<Organization> getStatesBasedOnassessmentProgramId(@Param("assessmentProgramId") Long assessmentProgramId);
	
	List<Long> getAllSchoolsByOrganization(@Param("organizationId") Long organizationId);
	Long getSchoolYearByOrganization(@Param("organizationId") Long orgId);
	List<Long> getAllStatesByUserId(@Param("userId") Long userId);

	void deleteOrganization(Organization organization);

	void moveSchool(@Param("sourceSchool") Long sourceSchool, @Param("destinationDistrict") Long destinationDistrict,
		@Param("modifiedDate") Date modifiedDate, @Param("modifiedUser") Long modifiedUser,  @Param("currentSchoolYear") Long currentSchoolYear);
	OrganizationTreeDetail getOrganizationDetailBySchoolId(@Param("schoolId")Long schoolId);
	
	OrganizationTreeDetail getOrganizationDetailById(@Param("orgId") Long orgId);

	List<Organization> getDeactivateChildrenByOrgTypeCode(@Param("organizationId")Long organizationId, @Param("orgTypeCode")String orgTypeCode);

	List<Organization> getLoggedInUserDeactivateOrganizationHierarchy(@Param("organizationId")Long organizationId, @Param("orgTypeCode")String orgTypeCode);
	
	void enableOrganization(Organization organization);
	void deactiveMergedOperation(Organization organization);

	List<Organization> getAllDeactiveChildrenByOrgTypeCode(@Param("organizationId")Long organizationId, @Param("orgTypeCode")String orgTypeCode);
	
	Integer getOrganizationByDisplayIDAndTypeCode(@Param("displayIdentifier") String districtNumber,
			@Param("typeCode") String typeCode,@Param("stateId") Long stateId);

	List<Organization> getChildrenWithDLMStudentsEnrolled(@Param("parentId") Long id, @Param("schoolYear") Long schoolYear,
			@Param("childType") String childType);

	List<Organization> getDLMStatesForResearchSurvey(@Param("assessmentProgram")String assessmentProgram, @Param("operationalWindowId")Long operationalWindowId);
	
	List<Organization> getContractingOrgsByAssessmentProgramIdOTWId(@Param("assessmentProgramId") Long assessmentProgramId, @Param("operationalTestWindowId") Long operationalTestWindowId, @Param("enrollmentMethod")String enrollmentMethod);

	void moveOrganization(@Param("sourceSchool")  Long sourceSchool, @Param("destinationSchool")  Long destinationSchool,
			@Param("modifiedUser") Long modifiedUser, @Param("modifiedDate") Date modifiedDate, @Param("currentSchoolYear") Long currentSchoolYear);

	List<Long> getOrgAssessmentProgramIds( @Param("schoolId")  Long schoolId);

	void disableAssessmentProgramByOrgId(@Param("sourceSchool")  Long sourceSchool, @Param("destinationSchool")  Long destinationSchool,
			@Param("sourceAssessmentprogramId") Long sourceAssessmentprogramId,
			@Param("modifiedUser") Long modifiedUser, @Param("modifiedDate") Date modifiedDate);
	
	List<OrganizationDetail> getOrganizationDetails(@Param("id")  Long orgId);
	
	List<OrganizationDetail> getOrganizationDetailByOrgId(@Param("orgId") Long orgId, @Param("testingCycleId") Long testingCycleId);
	
	int addOrganizationDetails(OrganizationDetail orgDetails);
	
	int updateOrganizationDetails(@Param("orgDetails") OrganizationDetail orgDetails);
	
	List<Organization> getBundledReportOrg(Map<String, Object> params);

	Organization getByDisplayIdentifierAndType(@Param("displayIdentifier")String displayIdentifier, @Param("typeCode")String typeCode);
	
	List<Organization> getByDisplayIdentifierAndParent_ActiveOrInactive(@Param("displayIdentifier") String displayIdentifier,	@Param("parentOrgId")Long parentOrgId);
	
	String getStateCodeByStateId(@Param("stateId")Long stateId);
	
	String getStateNameByStateId(@Param("stateId")Long stateId);

	List<Organization> getStateByAssessmentProgramIdForUploadResults(
		@Param("assessmentProgramId")long assessmentProgramId, @Param("userId")Long userId);

	Organization getStateByNameAndType(@Param("state")String state, @Param("typeCode") String typeCode);

	Organization getOrgByDisplayIdAndParentId(
			@Param("residenceDistrictIdentifier") String residenceDistrictIdentifier,			
			@Param("orgId")Long id,
			@Param("orgTypeID") Integer orgTypeId
			);
	
	List<Organization> getStateByUserIdForAuditHistoryLog(@Param("userId") Long userId);

	List<Long> getChildOrganizations(@Param("orgId") Long districtId,@Param("orgTypeId") Integer orgTypeId);

	List<Organization> getInactiveActiveParentOrgDetailsById(@Param("orgId") Long organizationId);
	
	List<OrganizationSnapshot> getSnapshotDetails(@Param("orgId") Long orgId, 
			                                      @Param("userId") Long userId);

	List<Organization> getAllActiveAndInactiveChildrenByType(@Param("organizationId") Long organizationId, @Param("organizationTypeCode") String organizationTypeCode);

	void updateOrganization(Organization organization);

	Organization getJsonOrganization(Long id);

	void addOrganizationtreeDetail(OrganizationTreeDetail organizationTreeDetail);

	Organization getOrganizationByIdExcludingGivenDispalyIdentifiers(@Param("organizationId") Long organizationId,
			@Param("stateDisplayIdentifiersList") String[] qcStates);
	
	List<Long> checkForOrganizationAssessmentProgram(@Param("userOrganizationId") Long userOrganizationId, @Param("apAbbreviatedName") String assessmentProgram);
	
	List<Organization> getContractingOrgsForPredictiveReports(@Param("assessmentProgramId") Long assessmentProgramId, @Param("testingProgramId")Long testingProgramId, @Param("testingCycle")String testingCycle);

	Organization getContractingOrgByAssessmentProgramIdOrgId(@Param("assessmentProgramId") Long assessmentProgramId, @Param("organizationId") Long organizationId);
	
	void saveMergedOrgDetails(@Param("sourceSchool")  Long sourceSchool,@Param("sourceorgdisplayidentifier") String sourceorgdisplayidentifier,@Param("sourceorgname") String sourceorgname,
			@Param("destinationSchool")  Long destinationSchool,@Param("activeflag") boolean activeflag, @Param("action") String action,
			@Param("currentSchoolYear") Long currentSchoolYear,@Param("reportYear") Long reportYear,@Param("createdDate") Date createdDate,@Param("modifiedDate") Date modifiedDate,
			@Param("createdUser") Long createdUser,@Param("modifiedUser") Long modifiedUser);

	public String getTimeZoneForOrg(@Param("id") Long organizationId);
	
	Long getReportYear(@Param("orgId") Long contractingOrgId, @Param("assessmentProgramId")  Long assessmentProgramId);
	
	void updateOrganizationMergeRelation(@Param("stateId") Long stateId, @Param("orgType") String orgType, @Param("orgDisplayId") String orgDisplayId);
	
	Boolean isChildOf(@Param("parentOrgId") Long parentOrgId, @Param("childOrgId") Long childOrgId);

	Organization getCeteOrganization();

	List<Organization> getPermittedStateIds(@Param("userId") Long userId);

	List<Organization> getInterimSchoolsInDistrict(@Param("districtId")Long districtId, @Param("currentSchoolYear") Long currentSchoolYear,
			@Param("predictiveStudentScore") Boolean predictiveStudentScore,@Param("assessmentProgramId") Long assessmentProgramId);

	List<Organization> getInterimDistrictsInState(@Param("stateId")Long stateId, @Param("currentSchoolYear")Long currentSchoolYear,@Param("predictiveStudentScore") Boolean predictiveStudentScore,@Param("assessmentProgramId") Long assessmentProgramId);

	

	List<Organization> getByTypeAndUserIdInParentByReportYear(@Param("organizationTypeCode") String organizationTypeCode, @Param("userId") Long userId,
			@Param("parentId") Long parentId, @Param("reportYear") Long reportYear);

	void setReportYearToSchoolYear(@Param("stateId") Long stateId,
			@Param("userId") Long modifiedUserid);

	List<Organization> getByDisplayIdentifierAndTypeId(@Param("displayIdentifier") String displayIdentifier,@Param("typeId") Integer typeId);
	
	List<Organization> getParentOrganizationsByOrgId(@Param("organizationId") Long organizationId);
	
	List<Organization> getISmartStatesForAutoEnrollment(@Param("assessmentProgramId") Long assessmentProgramId, @Param("operationalTestWindowId") Long operationalTestWindowId, @Param("enrollmentMethod")String enrollmentMethod);

	List<Organization> getOrganizationByDisplayIdentifierAndRelationalOrgnizationId(@Param("displayIdentifier") String displayIdentifier,
			@Param("userOrganizationId")Long userOrganizationId);
	
	/**
     * @param schoolid Id of the School.
     * @return number of rows affected.
     */
    int deleteSchoolFromOrgTreeDetail(@Param("schoolid") long schoolid);
    public Date getSchoolStartDate(@Param("orgId") Long orgId);
    //added Saikat
    public Date getSchoolEndDate(@Param("orgId") Long orgId);
    public Date getTestEndTime(@Param("orgId") Long orgId);
    public Date getTestBeginTime(@Param("orgId") Long orgId);
    public String getTestDays(@Param("orgId") Long orgId);
     Boolean isUserMappedToGivenState(@Param("stateId") Long stateId,@Param("userId") Long userId);

	Boolean isGivenOrganizationIdIsState(@Param("currentOrganizationId") Long currentOrganizationId);
}
