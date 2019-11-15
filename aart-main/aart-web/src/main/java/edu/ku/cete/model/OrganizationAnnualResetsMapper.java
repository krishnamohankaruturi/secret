package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.OrganizationAnnualResets;
import edu.ku.cete.domain.common.Organization;

public interface OrganizationAnnualResetsMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table organizationannualresets
	 * @mbggenerated  Wed Jul 13 15:05:44 CDT 2016
	 */
	int insert(OrganizationAnnualResets record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table organizationannualresets
	 * @mbggenerated  Wed Jul 13 15:05:44 CDT 2016
	 */
	int insertSelective(OrganizationAnnualResets record);

	int updateOrganizationAnnualResets(OrganizationAnnualResets record);
	
	void inactivateOldRecords();
    int resetAnnualDates(OrganizationAnnualResets record);
    int resetAnnualDatesStatus(OrganizationAnnualResets record);
    OrganizationAnnualResets checkIfOrgUpdateRecordExists(@Param("organizationId") Long orgId);
    List<OrganizationAnnualResets> getAnnualResetStates();
    
	OrganizationAnnualResets selectSchoolYearByOrgId(Long orgId);
	int updateFCSResetCompleteFlagByOrgId(@Param("organizationId") Long organizationId, @Param("fcsResetComplete") Boolean fcsResetComplete);

	List<Organization> getStatesBasedOnAssmntProgramIdFCSResetFlag(@Param("assessmentProgramId") Long assessmentProgramId);
	
}