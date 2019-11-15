package edu.ku.cete.service.impl.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.service.api.RosterAPIService;
import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.api.RosterAPIObject;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.service.api.exception.APIRuntimeException;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.SourceTypeEnum;


@Service
public class RosterAPIServiceImpl implements RosterAPIService{

	private Logger logger = LoggerFactory.getLogger(RosterAPIServiceImpl.class);

	@Autowired
	private RosterDao rosterDao;

	@Autowired
	private RosterService rosterService;

	@Autowired
	private OrganizationDao orgDao;

	@Autowired
	private UserService userService;

	@Autowired
	private APIDashboardErrorService apiErrorService;

	@Autowired
	private ContentAreaService contentAreaService;

	protected final ApiRecordTypeEnum RECORD_TYPE = ApiRecordTypeEnum.ROSTER;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> postRoster(Map<String, Object> response, RosterAPIObject rosterAPIObject, Long assessmentProgramId, Long userId) throws RuntimeException
	{
		boolean errorEncountered = false;
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.POST;
		Organization org = orgDao.getOrgByExternalID(rosterAPIObject.getSchoolIdentifier());
		if (org!=null && Boolean.TRUE.equals(org.getActiveFlag())) {

			Roster apiRoster = convertAPIObjectToRosterObject(rosterAPIObject,org.getId());
			List<Roster> rosters = rosterService.getRosterByClassroomId(apiRoster.getClassroomId());
			Roster roster = CollectionUtils.isEmpty(rosters) ? null : rosters.get(0);

			Organization contractingOrganization = orgDao.getContractingOrg(org.getId());
			if(contractingOrganization!=null) {

				int schoolYear = Math.toIntExact(contractingOrganization.getCurrentSchoolYear());

				if (roster == null) {
					// create new roster
					User teacher = userService.getUserByExternalIddAndOrgId(rosterAPIObject.getEducatorUniqueId(), org.getId());
					
						if (teacher != null) {
							ContentArea contentArea = contentAreaService.findByAbbreviatedName(rosterAPIObject.getCourseCode());
							if (contentArea != null) {
					
								roster = saveRoster(roster, rosterAPIObject, contentArea, teacher, org.getId(), userId, schoolYear); 
								logger.debug("new roster id = " + roster.getId());
								
							} else {
								errorEncountered = true;
								errors.add(apiErrorService.buildDashboardError(
										REQUEST_TYPE,
										RECORD_TYPE, 
										rosterAPIObject.getClassroomId().toString(), 
										null, 
										org, 
										rosterAPIObject.getClassroomId(), 
										userId, 
										new StringBuilder()
										.append("Did not find the course with code ")
										.append(rosterAPIObject.getCourseCode())
										.toString()
										)
										);
							}
						} else {
							errorEncountered = true;
							errors.add(apiErrorService.buildDashboardError(
									REQUEST_TYPE,
									RECORD_TYPE,
									rosterAPIObject.getClassroomId().toString(),
									null,
									org,
									rosterAPIObject.getClassroomId(),
									userId,
									new StringBuilder()
									.append("Did not find educator with unique ID ")
									.append(" in Organization ")
									.toString()
									)
									);

						}
					
				} else {		//roster with classroom id exists, cannot perform Post
						errorEncountered = true;
						errors.add(apiErrorService.buildDashboardError(
								REQUEST_TYPE,
								RECORD_TYPE,
								rosterAPIObject.getClassroomId().toString(),
								null,
								org,
								rosterAPIObject.getClassroomId(),
								userId,
								new StringBuilder()
								.append("A roster with classroom ID ")
								.append(roster.getClassroomId())
								.append(" already exists, cannot create a roster, ")
								.append("use PUT to modify it ")
								.toString()
								)
								);			
				}
			} else {        //contracting org is unavailable, No teacher exists in that organization
				errorEncountered = true;
				errors.add(apiErrorService.buildDashboardError(
						REQUEST_TYPE,
						RECORD_TYPE,
						rosterAPIObject.getClassroomId().toString(),
						null,
						org,
						rosterAPIObject.getClassroomId(),
						userId,
						new StringBuilder()
						.append("Did not find Contracting Organizaton with Educator unique ID ")
						.append(rosterAPIObject.getEducatorUniqueId())
						.toString()
						)
						);		

			}
		}
		else {//error handling for org unavailable
			errorEncountered = true;
			errors.add(apiErrorService.buildDashboardError(
					REQUEST_TYPE,
					RECORD_TYPE,
					rosterAPIObject.getClassroomId().toString(),
					null,
					org,
					rosterAPIObject.getClassroomId(),
					userId,
					new StringBuilder()
					.append("Did not find ")
					.append((org!=null) ? "active ":"")
					.append("Organizaton with Organization unique ID ")
					.append(rosterAPIObject.getSchoolIdentifier())
					.toString()
					)
					);	

		}
		response.put("success", !errorEncountered);
		response.put("errors", errors);
		if (errorEncountered) {
			// this is mainly for a roll-back, so that we don't have incomplete data
			throw new APIRuntimeException("Placeholder exception!");
		}

		return response;	
	}


	public Map<String, Object> putRoster(Map<String, Object> response, RosterAPIObject rosterAPIObject, Long assessmentProgramId, Long userId) throws APIRuntimeException
	{
		boolean errorEncountered = false;
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.PUT;
		Organization org = orgDao.getOrgByExternalID(rosterAPIObject.getSchoolIdentifier());
		
		if (org!=null && Boolean.TRUE.equals(org.getActiveFlag())) {
			Roster apiRoster = convertAPIObjectToRosterObject(rosterAPIObject,org.getId());
			List<Roster> rosters = rosterService.getRosterByClassroomId(apiRoster.getClassroomId());
			Roster roster = CollectionUtils.isEmpty(rosters) ? null : rosters.get(0);
			Organization contractingOrganization = orgDao.getContractingOrg(org.getId());
			if(contractingOrganization!=null) {

				int schoolYear = Math.toIntExact(contractingOrganization.getCurrentSchoolYear());

				if (roster != null) {
					// update existing roster
					User teacher = userService.getUserByExternalIddAndOrgId(rosterAPIObject.getEducatorUniqueId(), org.getId());
					
					
					if(org.getId() != null && roster.getAttendanceSchoolId() != null 
							&& org.getId().equals(roster.getAttendanceSchoolId())) {
						
						ContentArea newContentArea = contentAreaService.findByAbbreviatedName(rosterAPIObject.getCourseCode());
						if(newContentArea != null && newContentArea.getId() != null && roster.getStateSubjectAreaId() != null 
								&& newContentArea.getId().equals(roster.getStateSubjectAreaId())) {
	
							if (teacher != null) {
								roster = saveRoster(roster, rosterAPIObject, null, teacher, org.getId(), userId, schoolYear); 
								logger.debug("new roster id = " + roster.getId());
							} else { 
								errorEncountered = true;
								errors.add(apiErrorService.buildDashboardError(
										REQUEST_TYPE,
										RECORD_TYPE, 
										rosterAPIObject.getClassroomId().toString(), 
										null, 
										org, 
										rosterAPIObject.getClassroomId(), 
										userId, 
										new StringBuilder()
										.append("Did not find educator with unique ID ")
										.append(rosterAPIObject.getEducatorUniqueId())
										.append(" in Organization  ")
										.append(rosterAPIObject.getSchoolIdentifier())
										.toString()
										)
										);
							}
						} else {
							errorEncountered = true;
							errors.add(apiErrorService.buildDashboardError(
									REQUEST_TYPE,
									RECORD_TYPE, 
									rosterAPIObject.getClassroomId().toString(), 
									null, 
									org, 
									rosterAPIObject.getClassroomId(), 
									userId, 
									new StringBuilder()
									.append("Cannot change Course Code of existing roster with Classroom ID ")
									.append(rosterAPIObject.getClassroomId())
									.toString()
									)
									);
						}
					} else {
						errorEncountered = true;
						errors.add(apiErrorService.buildDashboardError(
								REQUEST_TYPE,
								RECORD_TYPE, 
								rosterAPIObject.getClassroomId().toString(), 
								null, 
								org, 
								rosterAPIObject.getClassroomId(), 
								userId, 
								new StringBuilder()
								.append("Cannot change School of existing roster with Classroom ID ")
								.append(rosterAPIObject.getClassroomId())
								.toString()
								)
								);
					}
				} else {
					errorEncountered = true;
					errors.add(apiErrorService.buildDashboardError(
							REQUEST_TYPE,
							RECORD_TYPE,
							rosterAPIObject.getClassroomId().toString(),
							null,
							org,
							rosterAPIObject.getClassroomId(),
							userId,
							new StringBuilder()
							.append("Did not find roster with classroom ID ")
							.append(rosterAPIObject.getClassroomId())
							.toString()
							)
							);		
				}	 
			} 		//contracting org is unavailable, No teacher exists in that organization
			else {     		   
				errorEncountered = true;
				errors.add(apiErrorService.buildDashboardError(
						REQUEST_TYPE,
						RECORD_TYPE,
						rosterAPIObject.getClassroomId().toString(),
						null,
						org,
						rosterAPIObject.getClassroomId(),
						userId,
						new StringBuilder()
						.append("Did not find Contracting Organizaton with Educator unique ID")
						.append(rosterAPIObject.getEducatorUniqueId())
						.toString()
						)
						);		

			}
		}			//error handling for org available
		else {					
			errorEncountered = true;
			errors.add(apiErrorService.buildDashboardError(
					REQUEST_TYPE,
					RECORD_TYPE,
					rosterAPIObject.getClassroomId().toString(),
					null,
					org,
					rosterAPIObject.getClassroomId(),
					userId,
					new StringBuilder()
					.append("Did not find ")
					.append((org!=null) ? "active ":"")
					.append("Organizaton with Organization unique ID ")
					.append(rosterAPIObject.getSchoolIdentifier())
					.toString()
					)
					);	

		}
		response.put("success", !errorEncountered);
		response.put("errors", errors);
		if (errorEncountered) {
			// this is mainly for a roll-back, so that we don't have incomplete data
			throw new APIRuntimeException("Placeholder exception!");
		}

		return response;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<String, Object> deleteRoster(Map<String, Object> response, RosterAPIObject rosterAPIObject, Long assessmentProgramId, Long userId) throws APIRuntimeException
	{
		boolean errorEncountered = false;
		List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
		final ApiRequestTypeEnum REQUEST_TYPE = ApiRequestTypeEnum.DELETE;


		List<Roster> rosters = rosterService.getRosterByClassroomId(rosterAPIObject.getClassroomId());
		Roster roster = CollectionUtils.isEmpty(rosters) ? null : rosters.get(0);
		if (roster == null){
			errorEncountered = true;
					errors.add(apiErrorService.buildDashboardError(
							REQUEST_TYPE,
							RECORD_TYPE, 
							rosterAPIObject.getClassroomId().toString(), 
							null, 
							null, 
							rosterAPIObject.getClassroomId(), 
							userId, 
							new StringBuilder()
							.append("attempting to delete a roster that does not exist for classroomid ")
							.append(rosterAPIObject.getClassroomId())
							.toString()
							)
							);
					logger.info(new StringBuilder()
							.append("/rosters -- Attempted to DELETE roster ")
							.append(rosterAPIObject.getEducatorUniqueId())
							.append(" but they do not exist")
							.toString()
							);
				}
				else {


		Organization org = orgDao.get(roster.getAttendanceSchoolId());
		if(org == null) { 
			errorEncountered = true;
			errors.add(apiErrorService.buildDashboardError(
					REQUEST_TYPE,
					RECORD_TYPE, 
					rosterAPIObject.getClassroomId().toString(), 
					null, 
					org, 
					rosterAPIObject.getClassroomId(), 
					userId, 
					new StringBuilder()
					.append("Did not find Organizaton with Educator unique ID ")
					.append(rosterAPIObject.getEducatorUniqueId())
					.toString()  
					)
					);
		}
		else {

			Organization contractingOrganization = orgDao.getContractingOrg(org.getId());
			if(contractingOrganization == null) { 
				errorEncountered = true;
				errors.add(apiErrorService.buildDashboardError(
						REQUEST_TYPE,
						RECORD_TYPE, 
						rosterAPIObject.getClassroomId().toString(), 
						null, 
						org, 
						rosterAPIObject.getClassroomId(), 
						userId, 
						new StringBuilder()
						.append("Did not find Contracting Organizaton with Educator unique ID ")
						.append(rosterAPIObject.getEducatorUniqueId())
						.toString()  
						)
						);
			}
			else {


				// fetching schoolyear 
				int schoolYear = Math.toIntExact(contractingOrganization.getCurrentSchoolYear());

					//roster has active flag 
					if(Boolean.TRUE.equals(roster.getActiveFlag())) {   

						// Checking if no students are active before deleting roster
						if(Boolean.FALSE.equals(rosterService.checkActiveStudentsCountOnRoster(roster.getClassroomId(), schoolYear))){ 
							deactivateEnrollmentsRosters(roster, userId); //deleting erollments rosters
							deactivateStudentsTestSections(roster,userId); //deleting students test sections
							deactivateRosterRecord(roster, userId);  // deleting rosters
						}	
						else { 
							errorEncountered = true;
							errors.add(apiErrorService.buildDashboardError(
									REQUEST_TYPE,
									RECORD_TYPE, 
									rosterAPIObject.getClassroomId().toString(), 
									null, 
									org, 
									rosterAPIObject.getClassroomId(), 
									userId, 
									new StringBuilder()
									.append("There are students with started or completed tests on the Roster, cannot delete Roster with Classroom ID ")
									.append(rosterAPIObject.getClassroomId())
									.toString()
									)
									);
						}
					}
					else {

						errorEncountered = true;
						errors.add(apiErrorService.buildDashboardError(
								REQUEST_TYPE,
								RECORD_TYPE, 
								rosterAPIObject.getClassroomId().toString(), 
								null, 
								org, 
								rosterAPIObject.getClassroomId(), 
								userId, 
								new StringBuilder()
								.append("attempting to deactivate an already deactivated roster for classroomid ")
								.append(rosterAPIObject.getClassroomId())
								.toString()
								)
								);
					}
				}

			}
		}	
		response.put("success", !errorEncountered);
		response.put("errors", errors);
		if (errorEncountered) {
			// this is mainly for a roll-back, so that we don't have incomplete data
			throw new APIRuntimeException("Placeholder exception!");
		}

		return response;
	}

	// converting API Object from API controller to Roster Object
	private Roster convertAPIObjectToRosterObject(RosterAPIObject rosterAPIObject, Long orgId) {

		Roster roster = new Roster();
		roster.setEducatorId(rosterAPIObject.getEducatorUniqueId());
		roster.setClassroomId(rosterAPIObject.getClassroomId());
		roster.setAttendanceSchoolId(orgId);
		roster.setStateCourseCode(rosterAPIObject.getCourseCode());  

		roster.setSourceType(SourceTypeEnum.API.getCode());
		return roster;
	}

	// validating the fields received in the request for type match
	public Map<String, Object> validateRosterAPIObject(ApiRequestTypeEnum method, RosterAPIObject rosterAPIObject)
	{
		Map<String, Object> ret = new HashMap<String, Object>();
		List<String> errors = new ArrayList<String>();
		// these first fields are required for every type of request
		if (!StringUtils.equalsIgnoreCase(rosterAPIObject.getRecordType(), "roster")) {
			errors.add("Record type did not match \"roster\"");
		}
		if (rosterAPIObject.getClassroomId() == null) {
			errors.add("Classroom ID cannot be omitted");
		}
		// POST and PUT need a lot more validations
		if (method == ApiRequestTypeEnum.POST || method == ApiRequestTypeEnum.PUT) {

			if (StringUtils.isEmpty(rosterAPIObject.getEducatorUniqueId()) || StringUtils.length(rosterAPIObject.getEducatorUniqueId()) > 30) {
				errors.add("Educator unique ID was empty or longer than 30 characters");
			}
			if (StringUtils.isEmpty(rosterAPIObject.getSchoolIdentifier()) || StringUtils.length(rosterAPIObject.getSchoolIdentifier()) > 30) {
				errors.add("School identifier was empty or longer than 30 characters");
			}	
			if (rosterAPIObject.getCourseCode() != null) {
				if (StringUtils.isEmpty(rosterAPIObject.getCourseCode()) || StringUtils.length(rosterAPIObject.getCourseCode()) > 30) {
					errors.add("Course Code was empty or longer than 30 characters");
				}
			} else {
				errors.add(new StringBuilder().append("Course code cannot be omitted from a ").append(method.toString()).append(" request").toString());
			}
		}
		ret.put("errors", errors);
		return ret;
	}

	// creating / updating a roster record
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private Roster saveRoster(Roster existingRoster, RosterAPIObject rosterAPIObject, ContentArea contentArea, User educator,
			Long schoolIdentifier, Long userId, int schoolYear) {
		Date now = new Date();
		String rosterName = new StringBuilder()
				.append(educator.getSurName())
				.append(" ")
				.append(educator.getFirstName().substring(0, 1))
				.append(". - ")
				.append(rosterAPIObject.getCourseCode())
				.append(" ")
				.append(String.valueOf(rosterAPIObject.getClassroomId()))
				.toString();

		if(existingRoster ==null) {
			Roster roster = new Roster();
			roster.setCourseSectionName(rosterName);
			roster.setAttendanceSchoolId(schoolIdentifier);
			roster.setTeacherId(educator.getId());
			roster.setStateSubjectAreaId(contentArea == null ? null : contentArea.getId());
			roster.setCurrentSchoolYear(schoolYear);
			roster.setCreatedDate(now);
			roster.setModifiedDate(now);
			roster.setCreatedUser(userId);
			roster.setModifiedUser(userId);
			roster.setSourceType(SourceTypeEnum.API.getCode());
			roster.setClassroomId(rosterAPIObject.getClassroomId());
			roster.setActiveFlag(Boolean.TRUE);
			rosterService.insertSelective(roster);
			Roster jsonRoster = rosterService.getJsonRosterData(roster.getId());
			rosterService.insertIntoDomainAuditHistory(roster.getId(), userId, EventTypeEnum.FCS_INSERTED.getCode(), SourceTypeEnum.API.getCode(), null, jsonRoster.buildJsonString());
			return roster;
		}	
		else {
			Roster jsonRoster = rosterService.getJsonRosterData(existingRoster.getId());
			String beforeJson = jsonRoster.buildJsonString();
			existingRoster.setCourseSectionName(rosterName);
			existingRoster.setTeacherId(educator.getId());
			existingRoster.setModifiedDate(now);
			existingRoster.setModifiedUser(userId);
			existingRoster.setActiveFlag(Boolean.TRUE);

			rosterService.updateByPrimaryKey(existingRoster);
			jsonRoster = rosterService.getJsonRosterData(existingRoster.getId());
			String afterJson = jsonRoster.buildJsonString();
			rosterService.insertIntoDomainAuditHistory(existingRoster.getId(), userId, EventTypeEnum.UPDATE.getCode(), SourceTypeEnum.API.getCode(), beforeJson, afterJson);
			return existingRoster;

		}	
	}

	// Deactivating enrollmentsrosters record by setting active flag as false
	private boolean deactivateEnrollmentsRosters(Roster roster, Long userId) {

		Long rosterId =roster.getId();
		rosterService.removeEnrollmentsRostersByRosterId(rosterId, userId);

		return true;

	}

	//Deactivating student test sections record by setting active flag as false
	private boolean deactivateStudentsTestSections(Roster roster, Long userId) {

		Long rosterId =roster.getId();
		rosterService.removeStudentsTestSectionsByRosterId(rosterId, userId);

		return true;

	}

	// Deactivating roster record by setting active flag as false
	private boolean deactivateRosterRecord(Roster roster, Long userId) {
		Roster rosterJson = rosterDao.getRosterJsonFormatData(roster.getId());
		String beforeJson = rosterJson == null ? null : rosterJson.buildJsonString();

		roster.setActiveFlag(false);
		roster.setModifiedDate(new Date());
		roster.setModifiedUser(userId);
		
		// deactivating the individual record 
		rosterService.updateByPrimaryKey(roster);

		roster = rosterDao.getRosterJsonFormatData(roster.getId());
		String afterJson = rosterJson == null ? null : rosterJson.buildJsonString();
		
		// Deactivation of individual roster records are added to Domain Audit History
		rosterService.insertIntoDomainAuditHistory(roster.getId(), userId, EventTypeEnum.UPDATE.getCode(), SourceTypeEnum.API.getCode(), beforeJson, afterJson);
		return true;


	}

}
