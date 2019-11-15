package edu.ku.cete.service.impl.api;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.api.OrganizationAPIObject;
import edu.ku.cete.domain.api.OrganizationAddress;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.service.api.OrganizationAPIService;
import edu.ku.cete.service.api.OrganizationAddressService;
import edu.ku.cete.service.api.TimezoneZipCodeService;
import edu.ku.cete.service.api.exception.APIRuntimeException;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class OrganizationAPIServiceImpl implements OrganizationAPIService {
	private Logger logger = LoggerFactory.getLogger(OrganizationAPIServiceImpl.class);

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private OrganizationService organizationService;

	/**
	 * OrganizationTypeService
	 */
	@Autowired
	private OrganizationTypeService organizationTypeService;

	/**
	 * OrganizationAddressService
	 */
	@Autowired
	private OrganizationAddressService organizationAddressService;

	/**
	 * API Dashboard error
	 */
	@Autowired
	private APIDashboardErrorService apiErrorService;
	
	/**
	 * Timezone vs ZIPcode service
	 */
	@Autowired
	private TimezoneZipCodeService timezoneZipCode;
	

	protected final ApiRecordTypeEnum RECORD_TYPE = ApiRecordTypeEnum.ORGANIZATION;
	
	private APIDashboardError gererateAPIDashboardError(OrganizationAPIObject organizationAPIObject,Organization org,String msg, ApiRequestTypeEnum REQUEST_TYPE) {
		APIDashboardError errorReturned = apiErrorService.buildDashboardError(
				REQUEST_TYPE,
				RECORD_TYPE,
				organizationAPIObject.getUniqueId(),
				organizationAPIObject.getName(),
				org,
				null,
				organizationAPIObject.getUserID(),
				msg
				);
		return errorReturned;
		
	}
	
	public Map<String, Object> validateOrganization(Map<String, Object> response,OrganizationAPIObject organizationAPIObject,ApiRequestTypeEnum REQUEST_TYPE) {
		boolean validObject = true;
		APIDashboardError error=null;
		String errorMsg = "";
		if (organizationAPIObject.getRecordType().isEmpty()) {
			validObject = false;
			errorMsg = "RecordType is mandatory field for the Organization request";
		}else if (!organizationAPIObject.getRecordType().equalsIgnoreCase("org")) {
			validObject = false;
			errorMsg = "Incorrect RecordType for the Organization request. Record type found in the request "+organizationAPIObject.getRecordType();
		}else if (organizationAPIObject.getUniqueId()!=null && organizationAPIObject.getUniqueId().isEmpty()) {
			validObject = false;
			errorMsg = "UniqueId is mandatory field for the Organization request";
		} else if (organizationAPIObject.getLevel().isEmpty()) {
			validObject = false;
			errorMsg =new StringBuilder()
			.append("Did not find the org level with unique ID ")
			.append(organizationAPIObject.getUniqueId())
			.toString();
		} else if (organizationAPIObject.getName().isEmpty()) {
			validObject = false;
			errorMsg =new StringBuilder()
					.append("Did not find the org name with unique ID ")
					.append(organizationAPIObject.getUniqueId())
					.toString();
		} else if (CommonConstants.ORGANIZATION_SCHOOL_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())
				&& organizationAPIObject.getParentId().isEmpty() && organizationAPIObject.getStateId().isEmpty()) {
			validObject = false;
			errorMsg =new StringBuilder()
					.append("Did not find the State ID or the Parent ID for the school with unique ID")
					.append(organizationAPIObject.getUniqueId())
					.toString();
		} else if (CommonConstants.ORGANIZATION_DISTRICT_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())
				&& organizationAPIObject.getParentId().isEmpty()) {
			validObject = false;
			errorMsg = "Parent ID is mandatory field for the District Organization post request";
			errorMsg =new StringBuilder()
					.append("Did not find the Parent ID for the district with unique ID ")
					.append(organizationAPIObject.getUniqueId())
					.toString();
		}else if (CommonConstants.ORGANIZATION_STATE_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())
				&& organizationAPIObject.getActive()==false) {
			validObject = false;
			errorMsg =new StringBuilder()
					.append("State org with unique ID ")
					.append(organizationAPIObject.getUniqueId())
					.append(" can't be inactive.")
					.toString();
		}
		if(validObject==false) {
			error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
			logger.error(errorMsg);
			response.put("error", error);
		}
		response.put("success", validObject);
		return response;
	}

	public Map<String, Object> validateOrganizationAddressIfNeeded(Map<String, Object> response, OrganizationAPIObject organizationAPIObject, ApiRequestTypeEnum REQUEST_TYPE) {
		boolean isValid = true;
		APIDashboardError error=null;
		String errorMsg = "";
		
		if (!CommonConstants.ORGANIZATION_STATE_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())) {
			if (organizationAPIObject.getAddress1().isEmpty()) {
				// Address 1 is empty
				isValid = false;
				errorMsg =new StringBuilder()
						.append("Did not find the Address 1 for the org with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.toString();
			} else if (organizationAPIObject.getCity().isEmpty()) {
				// city is empty
				isValid = false;
				errorMsg =new StringBuilder()
						.append("Did not find the City for the org with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.toString();
			} else if (organizationAPIObject.getStateId().isEmpty()) {
				// state is is empty
				isValid = false;
				errorMsg =new StringBuilder()
						.append("Did not find the State ID for the org with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.toString();
			} else if (organizationAPIObject.getZip().isEmpty()) {
				// zip code is empty
				isValid = false;
				errorMsg =new StringBuilder()
						.append("Did not find the ZIP for the org with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.toString();
			} else if (organizationAPIObject.getZip().length() != 5) {
				// Validate the zip code
				isValid = false;
				errorMsg =new StringBuilder()
						.append("Please verify the Zip code length for the org with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.toString();
			}
		}
		if(isValid==false) {
			error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
			logger.error(errorMsg);
			response.put("error", error);
		}
		response.put("success", isValid);
		return response;
	}

	/*
	 * Get parent organization from ORG API object
	 */
	public Organization getParentOrganizationFromAPIObject(OrganizationAPIObject organizationAPIObject) {
		Organization parentOrg = null;
		if (CommonConstants.ORGANIZATION_STATE_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())) {
			parentOrg = organizationDao.get(organizationDao.getCeteOrganization().getId());
		} else if (organizationAPIObject.getParentId() != null && !organizationAPIObject.getParentId().isEmpty()) {
			parentOrg = organizationDao.getOrgByExternalID(organizationAPIObject.getParentId());
		} else if (CommonConstants.ORGANIZATION_DISTRICT_CODE.equalsIgnoreCase(organizationAPIObject.getLevel()) && organizationAPIObject.getStateId() != null && !organizationAPIObject.getStateId().isEmpty()) {
			parentOrg = organizationDao.getOrgByExternalID(organizationAPIObject.getStateId());
		}
		return parentOrg;
	}

	/*
	 * Map the values from the OrgAPI Object to the actual organization
	 */
	public Organization mapOrganizationFromAPIObject(OrganizationAPIObject organizationAPIObject,
			Organization parentOrg) {
		Organization organization = new Organization();
		OrganizationType organizationType = organizationTypeService.getByTypeCode(organizationAPIObject.getLevel());

		organization.setActiveFlag(organizationAPIObject.getActive());
		organization.setOrganizationName(organizationAPIObject.getName());
		// Generation of Display Identifier
		organization.setDisplayIdentifier(organizationAPIObject.getLevel() + organizationAPIObject.getUniqueId());
		organization.setExternalid(organizationAPIObject.getUniqueId());
		organization.setContractingOrganization(organizationAPIObject.isContractingOrganization());
		organization.setRelatedOrganizationId(parentOrg.getId());
		organization.setExpirePasswords(true);
		organization.setOrganizationTypeId(organizationType.getOrganizationTypeId());
		organization.setCreatedUser(organizationAPIObject.getUserID());
		organization.setModifiedUser(organizationAPIObject.getUserID());
		if(organizationAPIObject.getTimezoneID()>0) {
			organization.setTimeZone(organizationAPIObject.getTimezoneID());
		}
		if (CommonConstants.ORGANIZATION_STATE_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())) {
			int year = Calendar.getInstance().get(Calendar.YEAR);
			int month = Calendar.getInstance().get(Calendar.MONTH);
			organization.setReportYear(year);
			String startDate = "08/01/" + Integer.toString(year);
			String endDate = "07/31/" + Integer.toString(year + 1);
			try {
				if (startDate != null && !StringUtils.isEmpty(startDate)) {
					organization.setSchoolStartDate(DateUtil.parseAndFail(startDate, "MM/dd/yyyy"));
				}
				if (endDate != null && !StringUtils.isEmpty(endDate)) {
					organization.setSchoolEndDate(DateUtil.parseAndFail(endDate, "MM/dd/yyyy"));
				}
			} catch (Exception ex) {
				logger.error("Caught in createOrganization. Stacktrace: {}", ex);
				return null;
			}
		}

		return organization;
	}

	public Map<String, Long> getOrgTypeCodeMap(OrganizationType newOrganizationType) {
		Map<String, Long> orgTypeCodeMap = new HashMap<String, Long>();
		List<OrganizationType> organizationTypes = new ArrayList<OrganizationType>();
		boolean validOrgTypeForState = false;
		organizationTypes = organizationTypeService.getAll();
		for (OrganizationType organizationType : organizationTypes) {
			orgTypeCodeMap.put(organizationType.getTypeCode(), organizationType.getOrganizationTypeId());
			if (newOrganizationType.getTypeLevel() >= organizationType.getTypeLevel()) {
				validOrgTypeForState = true;
			}
		}
		if (!validOrgTypeForState) {
			return null;
		}
		return orgTypeCodeMap;
	}

	/*
	 * Verify if the Organization is of school type and doesn't have parent ID
	 */
	public Map<String, Object> generateDistrictIfNecessary(Map<String, Object> response, OrganizationAPIObject organizationAPIObject, ApiRequestTypeEnum REQUEST_TYPE) {
		APIDashboardError error=null;
		String errorMsg = "";
		
		if (CommonConstants.ORGANIZATION_SCHOOL_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())) {
			if (organizationAPIObject.getParentId() == null || organizationAPIObject.getParentId().isEmpty()) {
				if (organizationAPIObject.getStateId() != null && !organizationAPIObject.getStateId().isEmpty()) {
					// Need to create the district first with same
					try {
						OrganizationAPIObject newDTorganizationAPIObject = (OrganizationAPIObject) organizationAPIObject
								.clone();
						newDTorganizationAPIObject.setLevel("DT");
						newDTorganizationAPIObject.setUniqueId(null);
						response = createOrganizationFromOrgObject(response,newDTorganizationAPIObject,REQUEST_TYPE);
						if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true
								&& response.containsKey("createdOrg")) {
							response.put("parentOrg", response.get("createdOrg"));
							response.remove("createdOrg");
						}else {
							StringBuilder sbMessage = new StringBuilder()
									.append("Unable to create the District org for the Orphaned School with unique ID ");
							if(organizationAPIObject.getUniqueId()!=null)
								sbMessage.append(organizationAPIObject.getUniqueId());
							errorMsg = sbMessage.toString();
							response.put("success", false);
							error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
							response.put("error", error);
							return response;
						}
					} catch (CloneNotSupportedException e) {
						// Write message internal error. Ideally the code should allow to clone the
						// object
						
						organizationAPIObject = null;
						throw new APIRuntimeException("Placeholder exception!");
					}
				}
			}
		}
		response.put("success", true);
		response.put("updatedOrgAPI", organizationAPIObject);
		return response;
	}

	/*
	 * Creating organization record from the ORG_API object
	 */
	public Map<String, Object> createOrganizationFromOrgObject(Map<String, Object> response, OrganizationAPIObject organizationAPIObject, ApiRequestTypeEnum REQUEST_TYPE) {
		Organization organization;
		Organization createdOrg = null;
		Map<String, Long> orgTypeCodeMap = new HashMap<String, Long>();
		APIDashboardError error=null;
		String errorMsg = "";
		
		response = generateDistrictIfNecessary(response,organizationAPIObject, REQUEST_TYPE);
		if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true) {
			if(response.containsKey("updatedOrgAPI")){
				organizationAPIObject= (OrganizationAPIObject)response.get("updatedOrgAPI");
				response.remove("updatedOrgAPI");
			}
		}else if(response != null && response.containsKey("success") && (Boolean)response.get("success") == false){
			return response;
		}
		Organization parentOrg = getParentOrganizationFromAPIObject(organizationAPIObject);
		if(parentOrg==null && response.containsKey("parentOrg")) {
			parentOrg= (Organization) response.get("parentOrg");
			response.remove("parentOrg");
		}
		if (parentOrg == null) {
			// Unable to find existing parent organization
			StringBuilder sbMessage = new StringBuilder()
					.append("Didn't find the parent for the org with unique ID ");
			if(organizationAPIObject.getUniqueId()!=null) {
				sbMessage.append(organizationAPIObject.getUniqueId());
			}
			errorMsg = sbMessage.toString();
			response.put("success", false);
			error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
			response.put("error", error);
			return response;
		} else if (parentOrg.getActiveFlag() == false) {
			// Unable to create an organization under a inactive organization
			errorMsg =new StringBuilder()
					.append("The parent Org is inactive. Please activate the parent with unique ID ")
					.append(organizationAPIObject.getUniqueId())
					.append(" before creating the organization")
					.toString();
			response.put("success", false);
			error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
			response.put("error", error);
			return response;
		} else {
			organization = mapOrganizationFromAPIObject(organizationAPIObject, parentOrg);
			if (organization != null) {
				OrganizationType organizationType = organizationTypeService
						.getByTypeCode(organizationAPIObject.getLevel());
				orgTypeCodeMap = getOrgTypeCodeMap(organizationType);
				if (orgTypeCodeMap != null) {
					ArrayList<Long> assessmentProgramIds = new ArrayList<Long>();
					// Set Assessment program only for State level Org or ContractingOrganization
					if (organizationAPIObject.isContractingOrganization()) {
						assessmentProgramIds.add(organizationAPIObject.getAssessmentProgramId());						
					} 
					createdOrg = organizationService.createOrganizationFromAPI(organization,
							organizationAPIObject.getLevel(), orgTypeCodeMap, null, organizationAPIObject.getOrganizationStructure(), assessmentProgramIds);
					if (createdOrg != null) {
						if (CommonConstants.ORGANIZATION_SCHOOL_CODE
								.equalsIgnoreCase(organizationAPIObject.getLevel())) {
							if (null != createdOrg.getRelatedOrganizationId()){
								Organization parentOrganization= organizationDao.get(createdOrg.getRelatedOrganizationId());
								if (null != parentOrganization) {
									// Getting the state ID for the school
									Long stateId = parentOrganization.getRelatedOrganizationId();
									organizationService.updateOrganizationMergeRelation(stateId,
											organizationAPIObject.getLevel(), createdOrg.getDisplayIdentifier());
								}
							}
						}
						// success
						// Create Org Address Record
						response = createUpdateOrgAddress(response, organizationAPIObject, createdOrg, null, REQUEST_TYPE);
					}
				}
			}

		}
		response.put("success", true);
		response.put("createdOrg", createdOrg);
		return response;
	}

	/*
	 * Create or Update the Organization Address
	 */
	public Map<String, Object> createUpdateOrgAddress(Map<String, Object> response, OrganizationAPIObject orgAPIObject, Organization org,
			OrganizationAddress orgAddress, ApiRequestTypeEnum REQUEST_TYPE) {
		boolean isSuccessful = true;
		APIDashboardError error=null;
		String errorMsg = "";
		
		if (orgAddress == null) {
			// create new org address
			orgAddress = new OrganizationAddress(org.getId(), orgAPIObject.getAddress1(), orgAPIObject.getAddress2(),
					orgAPIObject.getCity(), orgAPIObject.getStateId(), orgAPIObject.getZip());
			if (organizationAddressService.saveOrganizationAddress(orgAddress, orgAPIObject.getUserID()) != null) {
				isSuccessful = true;
			}else {
				isSuccessful = false;
				errorMsg =new StringBuilder()
						.append("Failed to create the organization address for the org with unique ID ")
						.append(orgAPIObject.getUniqueId())
						.toString();
				response.put("success", false);
				error=gererateAPIDashboardError(orgAPIObject,org,errorMsg,REQUEST_TYPE);
				response.put("error", error);
				return response;
			}
		} else {
			// update the org address
			orgAddress.setDataForOrgAddress(org.getId(), orgAPIObject.getAddress1(), orgAPIObject.getAddress2(),
					orgAPIObject.getCity(), orgAPIObject.getStateId(), orgAPIObject.getZip());
			if (organizationAddressService.updateOrganizationAddress(orgAddress, orgAPIObject.getUserID())) {
				isSuccessful = true;
			}else {
				isSuccessful = false;
				errorMsg =new StringBuilder()
						.append("Failed to update the organization address for the org with unique ID ")
						.append(orgAPIObject.getUniqueId())
						.toString();
				response.put("success", false);
				error=gererateAPIDashboardError(orgAPIObject,org,errorMsg,REQUEST_TYPE);
				response.put("error", error);
				return response;
			}
		}
		response.put("success", isSuccessful);
		return response;

	}

	/*
	 * 
	 * @see
	 * edu.ku.cete.service.api.OrganizationAPIService#postOrganization(edu.ku.cete.
	 * domain.api.OrganizationAPIObject)
	 */
	@Override
	public Map<String, Object> postOrganization(Map<String, Object> response, OrganizationAPIObject organizationAPIObject) {
		boolean okTOProceed = true;
		APIDashboardError error=null;
		String errorMsg = "";

		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.POST;
		
		// Validate the organizationAPIObject before processing
		response=validateOrganization(response,organizationAPIObject,REQUEST_TYPE);
		if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true) {
			// Validate Address if necessary. for state no need to validate
			response=validateOrganizationAddressIfNeeded(response,organizationAPIObject,REQUEST_TYPE);
			if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true) {
				// verify if the organization already exist
				Organization org = organizationDao.getOrgByExternalID(organizationAPIObject.getUniqueId());
				if (org != null) {
					errorMsg =new StringBuilder()
							.append("The Requested Organization with the unique ID ")
							.append(organizationAPIObject.getUniqueId())
							.append(" already exist. Please use put request to update it if required")
							.toString();
					okTOProceed = false;
					response.put("success", okTOProceed);
					error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
					response.put("error", error);
				}
				if (okTOProceed) {
					if(organizationAPIObject.getZip()!=null && !organizationAPIObject.getZip().isEmpty()) {
						Long timezoneID= timezoneZipCode.getOrganizationAddress(organizationAPIObject.getZip());
						if(timezoneID!=null) {
							organizationAPIObject.setTimezoneID(timezoneID);
						}
					}
					//if time zone is not set create a time zone error to the api dashboard
					if(organizationAPIObject.getTimezoneID()==0) {
						String msg=new StringBuilder()
								.append("The timezone for the org with external unique id ")
								.append(organizationAPIObject.getUniqueId())
								.append(" is not found from the zipcode table. Please update the timezone id manually")
								.toString();
						error=gererateAPIDashboardError(organizationAPIObject,org,msg,REQUEST_TYPE);
						response.put("timeZoneError", error);
					}
					
					// Ok to create the organization only when the response from above is Success
					if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true) {
						response=createOrganizationFromOrgObject(response,organizationAPIObject,REQUEST_TYPE);
					}
				}
			} else {
				// Error Already handled in the respective method
			}

		}
		return response;
	}

	/*
	 * Validate if Address is changed for the organization
	 */
	public boolean validateIfAddressChanged(OrganizationAPIObject organizationAPIObject,
			OrganizationAddress orgAddress) {
		boolean isAdddressChanged = false;
		if (orgAddress == null) {
			isAdddressChanged = true;
		} else if (!organizationAPIObject.getAddress1().equals(orgAddress.getOrgAddress1())
				|| !organizationAPIObject.getAddress2().equals(orgAddress.getOrgAddress2())
				|| !organizationAPIObject.getCity().equals(orgAddress.getCity())
				|| !organizationAPIObject.getZip().equals(orgAddress.getZip())
				|| !organizationAPIObject.getStateId().equals(orgAddress.getState())) {
			isAdddressChanged = true;
		}

		return isAdddressChanged;
	}

	/*
	 * Update the Organization Name
	 */
	public Organization updateOrganizationName(Organization org, String orgName, String orgTypeCode, Long userId) {

		org.setOrganizationName(orgName);
		Organization updatedOrg = organizationService.addorUpdateOrganizationFromAPI(org, userId);
		if (CommonConstants.ORGANIZATION_STATE_CODE.equalsIgnoreCase(orgTypeCode)
				|| CommonConstants.ORGANIZATION_DISTRICT_CODE.equalsIgnoreCase(orgTypeCode)
				|| CommonConstants.ORGANIZATION_SCHOOL_CODE.equalsIgnoreCase(orgTypeCode)) {
			try {
				organizationService.updateOrgnameInOrgTreeDetail(updatedOrg);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
		return updatedOrg;
	}

	/*
	 * Activate or Deactivate the organization depending on the "shouldEnable" flag
	 */
	public Map<String, Object> enableOrDisableOrganization(Map<String, Object> response, Organization org,
			OrganizationAPIObject organizationAPIObject, boolean shouldEnable,Organization parentOrg, ApiRequestTypeEnum REQUEST_TYPE) {
		boolean isSuccessful = true;
		String errorMsg = "";
		APIDashboardError error=null;
		
		if (!CommonConstants.ORGANIZATION_STATE_CODE.equalsIgnoreCase(organizationAPIObject.getLevel())) {
			if (shouldEnable) {
				// Verify the Parent organization is active
				if (parentOrg.getActiveFlag() == true) {
					try {
						if (org.getActiveFlag() == false)
							isSuccessful = organizationService.enableOrganizationFromAPI(org, organizationAPIObject.getUserID());
						else
							isSuccessful=true;
					} catch (Exception e) {
						e.printStackTrace();
						isSuccessful=false;
						errorMsg="Exception occurred while enabling the organization "+organizationAPIObject.getUniqueId();
						response.put("success", isSuccessful);
						error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
						response.put("error", error);
						throw new APIRuntimeException("Placeholder exception!");
					}
				} else {
					// parent is not active
					errorMsg =new StringBuilder()
							.append("Failed to enable the organization with unique ID ")
							.append(organizationAPIObject.getUniqueId())
							.append(" as the parent organization is inactive.")
							.toString();
				}
			} else {
				// deactivate the organization
				try {
					if (org.getActiveFlag() == true)
						isSuccessful = organizationService.disableOrganizationFromAPI(org, organizationAPIObject.getUserID());
					else
						isSuccessful = true;
				} catch (Exception e) {
					e.printStackTrace();
					isSuccessful=false;
					errorMsg="Exception occurred while disabling the organization "+organizationAPIObject.getUniqueId();
					response.put("success", isSuccessful);
					error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
					response.put("error", error);
					throw new APIRuntimeException("Placeholder exception!");
				}
			}
		}else if(organizationAPIObject.getActive()!=org.getActiveFlag()) {
			isSuccessful=false;
			errorMsg =new StringBuilder()
					.append("API can't set the active flag for State level organization with unique id ")
					.append(organizationAPIObject.getUniqueId())
					.toString();
		}else {
			isSuccessful=true;
		}
		if(isSuccessful==false) {
			if(errorMsg.isEmpty()) {
				errorMsg="Failed to Activate or deactivate the org with unique ID "+organizationAPIObject.getUniqueId();
			}
			error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
			logger.error(errorMsg);
			response.put("error", error);
		}
		response.put("success", isSuccessful);
		return response;
	}

	/*
	 * Move a School to a different district
	 */
	public Map<String, Object> moveASchool(Map<String, Object> response,Organization org,OrganizationAPIObject organizationAPIObject,
			Organization parentOrg, Organization newParentOrg, ApiRequestTypeEnum REQUEST_TYPE) {
		boolean isSuccessful = true;
		String errorMsg = "";
		APIDashboardError error=null;
		
		if (newParentOrg != null && parentOrg != null) {
			//Verify both the parents organization has same Parent organization
			Organization oldContractingOrganization = organizationDao.getParentOrgDetailsById(parentOrg.getId());
			Organization newContractingOrganization = organizationDao.getParentOrgDetailsById(newParentOrg.getId());
			
			if (oldContractingOrganization != null && newContractingOrganization != null && 
					oldContractingOrganization.getId().equals(newContractingOrganization.getId())) {
				Long oldParentID = parentOrg.getId();
				Long newParentID = newParentOrg.getId();
				logger.debug(newParentID.toString());
				if (isSuccessful && !oldParentID.equals(newParentID)) {
					if (CommonConstants.ORGANIZATION_SCHOOL_CODE
							.equalsIgnoreCase(organizationAPIObject.getLevel())) {
						// call the move school method
						try {
							//Verify if both parents are on the same level
							if(parentOrg.getOrganizationType().getTypeCode().equals(newParentOrg.getOrganizationType().getTypeCode())) {
								isSuccessful = organizationService.moveSchoolFromAPI(org, newParentOrg.getId(), organizationAPIObject.getUserID());
							}else {
								isSuccessful=false;
								errorMsg =new StringBuilder()
										.append("Failed to move org with unique ID ")
										.append(organizationAPIObject.getUniqueId())
										.append(". Parent organization should be on the same level.")
										.toString();
							}
						} catch (Exception e) {
							e.printStackTrace();
							isSuccessful=false;
							errorMsg="Exception occurred while moving the organization "+organizationAPIObject.getUniqueId();
							response.put("success", isSuccessful);
							error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
							response.put("error", error);
							throw new APIRuntimeException("Placeholder exception!");
						}
					} else {
						// Only School level org can be moved
						isSuccessful=false;
						errorMsg =new StringBuilder()
								.append("API can only move School level org. Failed to move org with unique ID ")
								.append(organizationAPIObject.getUniqueId())
								.toString();
					}
				}
			}else {
				//Parent of parent level org is different
				isSuccessful=false;
				errorMsg =new StringBuilder()
						.append("Failed to move org with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.append(". The school can be moved within a state only.")
						.toString();
			}
		}else {
			// not able to find the new parent organization or parent organization
			isSuccessful=false;
			errorMsg="Failed to find the new parent or the actual parent org for the org with unique ID "+organizationAPIObject.getUniqueId();
		}
		if(isSuccessful==false) {
			if(errorMsg.isEmpty()) {
				errorMsg="Failed to move the org with unique ID "+organizationAPIObject.getUniqueId();
			}
			error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
			logger.error(errorMsg);
			response.put("error", error);
		}
		response.put("success", isSuccessful);
		return response;
	}

	/*
	 * 
	 * @see
	 * edu.ku.cete.service.api.OrganizationAPIService#putOrganization(edu.ku.cete.
	 * domain.api.OrganizationAPIObject)
	 */
	@Override
	public Map<String, Object> putOrganization(Map<String, Object> response,OrganizationAPIObject organizationAPIObject) {
		boolean isSuccessful = true;
		Organization org = null;
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.PUT;
		String errorMsg = "";
		APIDashboardError error=null;
		
		response=validateOrganization(response,organizationAPIObject,REQUEST_TYPE);
		if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true) {
			// verify if the organization exist for the given level
			String externalORGID = organizationAPIObject.getUniqueId();
			String typeCode = organizationAPIObject.getLevel();
			org = organizationDao.getOrgByExternalID(externalORGID);
			if (org != null) {
				//verify the level of the organization to that of the org API object
				if(typeCode.equalsIgnoreCase(org.getTypeCode())) {
					// fetch the address
					OrganizationAddress orgAddress = organizationAddressService.getOrganizationAddress(org.getId());
					try {
						// Name Updated
						if (!org.getOrganizationName().equals(organizationAPIObject.getName())) {
							org = updateOrganizationName(org, organizationAPIObject.getName(),
									organizationAPIObject.getLevel(), organizationAPIObject.getUserID());
							if(org==null) {
								isSuccessful=false;
								errorMsg =new StringBuilder()
										.append("Failed to change the name of the org with unique ID ")
										.append(organizationAPIObject.getUniqueId())
										.toString();
								response.put("success", isSuccessful);
								error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
								response.put("error", error);
							}
						}
						
						// Enable Disable organization
						Organization parentOrg = organizationDao.getParentOrgDetailsById(org.getId());
						parentOrg = organizationDao.get(parentOrg.getId());
						if (isSuccessful) {
							response = enableOrDisableOrganization(response, org, organizationAPIObject, organizationAPIObject.getActive(),
									parentOrg,REQUEST_TYPE);
							if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true) {
								org = organizationDao.get(org.getId());
								isSuccessful=true;
							}
							else {
								//We already have error response from the above method.
								isSuccessful=false;
							}
						}
						
						// move a school
						// verify if parent id is changed
						Organization newParentOrg = null;
						if(organizationAPIObject.getParentId() != null && !organizationAPIObject.getParentId().isEmpty())
						{
							newParentOrg= organizationDao.getOrgByExternalID(organizationAPIObject.getParentId());
						}
						if(isSuccessful && newParentOrg!=null) {
							response = moveASchool(response, org, organizationAPIObject, parentOrg, newParentOrg,
									REQUEST_TYPE);
							if (response != null && response.containsKey("success") && (Boolean)response.get("success") == true) {
								org = organizationDao.get(org.getId());
								isSuccessful=true;
							}
							else {
								//We already have error response from the above method.
								isSuccessful=false;
							}
						}
						
						//Verify and change the address
						if (isSuccessful && validateIfAddressChanged(organizationAPIObject, orgAddress)) {
							org = organizationDao.get(org.getId());
							response = createUpdateOrgAddress(response,organizationAPIObject, org, orgAddress,REQUEST_TYPE);
							return response;
						}
					} catch (Exception e) {
						isSuccessful=false;
						errorMsg="Exception occurred while updating the organization";
						response.put("success", isSuccessful);
						error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
						response.put("error", error);
						throw new APIRuntimeException("Placeholder exception!");
					}
				}else {
					isSuccessful=false;
					errorMsg =new StringBuilder()
							.append("The level of the org with unique ID ")
							.append(organizationAPIObject.getUniqueId())
							.append(" doesn't match with the org found in the database.")
							.toString();
					response.put("success", isSuccessful);
					error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
					response.put("error", error);
				}
			} else {
				// org not found
				isSuccessful=false;
				errorMsg =new StringBuilder()
						.append("Organization with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.append(" not found in the database.")
						.toString();
				response.put("success", isSuccessful);
				error=gererateAPIDashboardError(organizationAPIObject,org,errorMsg,REQUEST_TYPE);
				response.put("error", error);
			}
		}
		response.put("success", isSuccessful);
		return response;
	}


	/*
	 * 
	 * @see
	 * edu.ku.cete.service.api.OrganizationAPIService#deleteOrganization(edu.ku.cete
	 * .domain.api.OrganizationAPIObject)
	 */
	@Override
	public Map<String, Object> deleteOrganization(Map<String, Object> response,OrganizationAPIObject organizationAPIObject) {
		boolean isSuccessful = true;
		String errorMsg="";
		APIDashboardError error=null;
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.DELETE;
		
		if (organizationAPIObject.getRecordType().isEmpty()) {
			isSuccessful = false;
			errorMsg = "RecordType is mandatory field for the Organization request";
		}else if (!organizationAPIObject.getRecordType().equalsIgnoreCase("org")) {
			isSuccessful = false;
			errorMsg = "Incorrect RecordType for the Organization request. Record type found in the request "+organizationAPIObject.getRecordType();
		}else if (!organizationAPIObject.getUniqueId().isEmpty()) {
			Organization org = organizationDao.getOrgByExternalID(organizationAPIObject.getUniqueId());
			if (org != null) {
				if (!CommonConstants.ORGANIZATION_STATE_CODE.equalsIgnoreCase(org.getOrganizationType().getTypeCode())){
					try {
						if (org.getActiveFlag() == true)
							isSuccessful = organizationService.disableOrganizationFromAPI(org,
								organizationAPIObject.getUserID());
						else
							isSuccessful=true;
					} catch (Exception e) {
						isSuccessful=false;
						errorMsg =new StringBuilder()
								.append("Exception occoured while deactivating the org with Unique ID ")
								.append(organizationAPIObject.getUniqueId())
								.toString();
						e.printStackTrace();
					}
				}else {
					isSuccessful=false;
					errorMsg = "State level organization can't be deactivated";
				}
			}else {
				isSuccessful=false;
				errorMsg =new StringBuilder()
						.append("Organization with unique ID ")
						.append(organizationAPIObject.getUniqueId())
						.append(" not found in the database.")
						.toString();
			}	
		}else {
			isSuccessful=false;
			errorMsg = "UniqueId is mandatory field for the Organization Delete request";
		}
		if(isSuccessful==false) {
			if(errorMsg.isEmpty()) {
				errorMsg="Failed to deactivate the org with unique ID "+organizationAPIObject.getUniqueId();
			}
			error=gererateAPIDashboardError(organizationAPIObject,null,errorMsg,REQUEST_TYPE);
			logger.error(errorMsg);
			response.put("error", error);
		}
		response.put("success", isSuccessful);
		response.put("message", errorMsg);
		return response;
	}

}
