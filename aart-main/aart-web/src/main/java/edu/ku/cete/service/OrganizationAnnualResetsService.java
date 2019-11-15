package edu.ku.cete.service;

import java.io.Serializable;
import java.util.List;

import edu.ku.cete.domain.OrganizationAnnualResets;
import edu.ku.cete.domain.common.Organization;

public interface OrganizationAnnualResetsService extends Serializable {

	void inactivateOldRecords();
	OrganizationAnnualResets checkIfOrgUpdateRecordExists(Long organizationId);
	int addOrganizationAnnualResets(OrganizationAnnualResets organizationAnnualResetsObject);
	int updateOrganizationAnnualResets(OrganizationAnnualResets organizationAnnualResetsObject);
	List<OrganizationAnnualResets> getAnnualResetStates();
	int resetAnnualDatesForOrganization(OrganizationAnnualResets organizationAnnualResets);
	int resetAnnualDatesStatusForOrganization(OrganizationAnnualResets organizationAnnualResets);
	
	OrganizationAnnualResets selectAcademicSchoolYearByOrgId(Long organizationId);
	Long getSchoolYearByOrganization(Long organizationId);
	int updateFCSResetCompleteFlagByOrgId(Long organizationId, Boolean fcsResetComplete);
	List<Organization> getStatesBasedOnAssmntProgramIdFCSResetFlag(Long assessmentProgramId);
}
