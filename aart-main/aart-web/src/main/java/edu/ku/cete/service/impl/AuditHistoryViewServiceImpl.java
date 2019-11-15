package edu.ku.cete.service.impl;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.customextract.KAPCustomExtract;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.report.domain.ActivationTemplateAuditTrailHistory;
import edu.ku.cete.report.domain.AuditType;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.domain.FirstContactSurveyAuditHistory;
import edu.ku.cete.report.domain.OrganizationAuditTrailHistory;
import edu.ku.cete.report.domain.OrganizationManagementAudit;
import edu.ku.cete.report.domain.RoleAuditTrailHistory;
import edu.ku.cete.report.domain.RosterAuditTrailHistory;
import edu.ku.cete.report.domain.StudentAuditTrailHistory;
import edu.ku.cete.report.domain.StudentPNPAuditHistory;
import edu.ku.cete.report.domain.UserAuditTrailHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.report.model.KSDEXMLAuditMapper;
import edu.ku.cete.report.model.OrganizationManagementAuditDao;
import edu.ku.cete.report.model.StudentPNPAuditHistoryDao;
import edu.ku.cete.report.model.UserAuditTrailHistoryMapper;
import edu.ku.cete.service.AuditHistoryViewService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;

@Service
public class AuditHistoryViewServiceImpl implements AuditHistoryViewService {
	private Logger LOGGER = LoggerFactory.getLogger(AuditHistoryViewServiceImpl.class);
	@Autowired
	StudentPNPAuditHistoryDao studentPNPAuditHistoryDao;

	@Autowired
	DomainAuditHistoryMapper domainAuditHistoryMapper;
	
	@Autowired
	UserAuditTrailHistoryMapper userAuditTrailHistoryMapper;
	
	@Autowired
	OrganizationManagementAuditDao organizationManagementAuditDao;
	@Autowired
	KSDEXMLAuditMapper ksdeXMLAuditMapper;
	
	 @Autowired
	    private OrganizationDao organizationDao;
	
	@Override
	public void downloadAuditHistory(HttpServletResponse response, HttpServletRequest request) {

		response.setContentType("application/force-download");
		String fileName = new StringBuilder().append(request.getParameter("auditName")).append("_")
				.append(request.getParameter("startDate")).append("-").append(request.getParameter("endDate"))
				.append(".csv").toString();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		String serverPath = request.getSession().getServletContext().getRealPath("/");
		try {
			writeAuditHistoryCSV(request, fileName, response, serverPath);
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("Exception in Generating CSV for Audit History", e);
		}
		return;
	}

	private void writeAuditHistoryCSV(HttpServletRequest request, String fileName, HttpServletResponse response,
			String serverPath) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm aa");
		String startDateTime = request.getParameter("startDate");
		String endDateTime = request.getParameter("endDate");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		/**
		  * uday
		  * F424
		  * Filter values for audit history 
		  */
		String asessmentProgrmId=request.getParameter("assessmentProgramId");
		String stateId=request.getParameter("stateId");
		String organizationId=request.getParameter("organizationId");
		String schoolId=request.getParameter("schoolId");
		String districtId=request.getParameter("districtId");
		String stateStudentId=request.getParameter("stateStudentId");
		String educatorId=request.getParameter("educatorId");
		String emailAddress=request.getParameter("emailAddress");
		String contentArea=request.getParameter("contentArea");		
		String groupId=request.getParameter("groupId");		
		Timestamp startDateTimes = null;
		Timestamp endDateTimes = null;
		Date startDateTimesToChange = null;
		Date endDateTimesToChange = null;
		String startDateAndTime=startDateTime+" "+startTime;
		String endDateAndTime=endDateTime+" "+endTime;
		try { 
			startDateTimes = new Timestamp(dateFormat.parse(startDateAndTime).getTime());
			endDateTimes = new Timestamp(dateFormat.parse(endDateAndTime).getTime());
			
			String strstartTime  = dateFormat.format(startDateTimes);
			String strEndTime = dateFormat.format(endDateTimes);
			startDateTimesToChange =DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(strstartTime, "US/Central",  "MM-dd-yyyy hh:mm aa");
			endDateTimesToChange = DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(strEndTime, "US/Central",  "MM-dd-yyyy hh:mm aa");
			
		} catch (ParseException e) {
			e.printStackTrace();
		}

		switch (request.getParameter("auditName")) {
		case "domainaudithistory":
			generateDomainAuditHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName);
			break;
		case "useraudittrailhistory":
			generateUserAuditTrailHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName,educatorId,emailAddress);
			break;
		case "studentaudittrailhistory":
			generateStudentAuditTrailHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName,stateStudentId);

			break;
		case "studentpnpsaudithistory":
			generatePNPAuditHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName, stateStudentId);
			break;
		case "rosteraudittrailhistory":
			generateRosterAuditTrailHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName,stateId,districtId,schoolId,contentArea,educatorId);
			break;
		case "roleaudittrailhistory":
			generateRoleAuditTrailHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName,asessmentProgrmId,stateId,groupId);
			break;
		case "organizationmanagementaudit":
			generateOrganizationManagementAuditCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName, stateId, districtId, schoolId, organizationId);
			break;
		case "organizationaudittrailhistory":
			generateOrganizationAuditTrailHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName,stateId,districtId,schoolId,organizationId);

			break;
		case "firstcontactsurveyaudithistory":
			generateFirstContactSurveyAuditHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName,stateStudentId);

			break;
		case "activationtemplateaudittrailhistory":
			generateActivationTemplateAuditTrailHistoryCSV(startDateTimesToChange, endDateTimesToChange, response, serverPath, fileName,asessmentProgrmId,stateId);

			break;
		
		}

	}

	private void generatePNPAuditHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName, String stateStudentId) throws Exception {

		CSVWriter csvWriter = null;
		try {
			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<String[]> records = new ArrayList<String[]>();
			List<StudentPNPAuditHistory> studentPNPAuditHistoryList = studentPNPAuditHistoryDao
					.getAuditHistory(startDateTimes, endDateTimes,stateStudentId,offset,limit);
			
			offset = offset + limit;
			
			recordCount=studentPNPAuditHistoryList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				if(!isrecordsEist){
				records.add(new String[] { "ID ", "Event Name ",
						"State Student ID",
						 "Student First Name","Student Last Name", "Before Value JSON ",
						"After Value JSON","Modified By"
						,"Modified User Name","Modified Date"});
			}
			for (StudentPNPAuditHistory studentPNPAuditHistory : studentPNPAuditHistoryList) {
				studentPNPAuditHistory.validate();
				records.add(new String[] {
						studentPNPAuditHistory.getId().toString(),
						studentPNPAuditHistory.getEventName(),
						studentPNPAuditHistory.getStateStudentIdentifier(),
						studentPNPAuditHistory.getStudentFirstName(), 
						studentPNPAuditHistory.getStudentLastName(),
						studentPNPAuditHistory.getBeforeValue(),
						studentPNPAuditHistory.getCurrentValue(),
						studentPNPAuditHistory.getModifiedUser() == 0? "":studentPNPAuditHistory.getModifiedUser().toString(),
//						StringUtils.isEmpty(studentPNPAuditHistory.getModifiedUser().toString())?new String():studentPNPAuditHistory.getModifiedUser().toString(),
//						StringUtils.isEmpty(studentPNPAuditHistory.getModifiedUserName())?new String():studentPNPAuditHistory.getModifiedUserName(),
						studentPNPAuditHistory.getModifiedUserName(),
						studentPNPAuditHistory.getCreateddateStr()+"."
						
						});
			}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}

	
	private void generateDomainAuditHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName) throws Exception {

		CSVWriter csvWriter = null;
		try {
			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');
			List<String[]> records = new ArrayList<String[]>();
			List<DomainAuditHistory> domainAuditHistoryList = domainAuditHistoryMapper
					.getDomainAuditHistory(startDateTimes, endDateTimes);
			if(domainAuditHistoryList== null || domainAuditHistoryList.isEmpty())
			{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				records.add(new String[] { "ID ", "Source", "Object Type",
						"Object Id", "Created User Id", "Created Date",
						"Action", "Before Value JSON", "After Value JSON" });
			}
			for (DomainAuditHistory domainAuditHistory : domainAuditHistoryList) {
				domainAuditHistory.validate();
				records.add(new String[] {domainAuditHistory.getId().toString(),
						domainAuditHistory.getSource(),domainAuditHistory.getObjectType(),
						domainAuditHistory.getObjectId().toString(),
						domainAuditHistory.getCreatedUserId().toString(),						
						domainAuditHistory.getCreateddateStr()+".",
						domainAuditHistory.getAction(),
						domainAuditHistory.getObjectBeforeValues(),
						domainAuditHistory.getObjectAfterValues()
						});
					}
			csvWriter.writeAll(records);
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}
	private void generateUserAuditTrailHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName, String educatorId, String emailAddress) throws Exception {

		CSVWriter csvWriter = null;
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<String[]> records = new ArrayList<String[]>();
			List<UserAuditTrailHistory> userAuditTrailHistoryList = userAuditTrailHistoryMapper
					.getUserAuditTrailHistory(startDateTimes, endDateTimes,educatorId,emailAddress,offset,limit);
			
			offset = offset + limit;
			
			recordCount=userAuditTrailHistoryList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				if(!isrecordsEist){
				records.add(new String[] { "ID ", "Event Name", "Affected User","Affected User Name","Affected User First Name","Affected User Last Name","Affected User Eductor IdentiFier" , "Before Value JSON",
						"Current Value JSON","Modified By", "Modified User Name","Modified Date" });
			}
			for (UserAuditTrailHistory userAuditTrailHistory : userAuditTrailHistoryList) {
				userAuditTrailHistory.validate();
				records.add(new String[] {
						userAuditTrailHistory.getId().toString(),
						userAuditTrailHistory.getEventName(),
						userAuditTrailHistory.getAffectedUser().toString(),
						userAuditTrailHistory.getUserName(),
						userAuditTrailHistory.getUserFirstName(),
						userAuditTrailHistory.getUserLastName(),
						userAuditTrailHistory.getUserEducatorIdentifier(),
						userAuditTrailHistory.getBeforeValue(),
						userAuditTrailHistory.getCurrentValue(),
						userAuditTrailHistory.getModifiedUser().toString(),
						userAuditTrailHistory.getModifiedUserName(),
						userAuditTrailHistory.getCreateddateStr()+"."
						
						});
					}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}
	private void generateStudentAuditTrailHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName,String stateStudentId) throws Exception {

		CSVWriter csvWriter = null;
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');

		
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
				List<String[]> records = new ArrayList<String[]>();
				List<StudentAuditTrailHistory> studentAuditTrailHistoryList = userAuditTrailHistoryMapper.getStudentAuditTrailHistory(startDateTimes, endDateTimes,stateStudentId,offset,limit);
				offset = offset + limit;
				
				recordCount=studentAuditTrailHistoryList.size();
				
			if( recordCount<=0 && !isrecordsEist)
			{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				
				if(!isrecordsEist){
				records.add(new String[] { "ID", "Event Name","State Student ID",
						 "Student First Name","Student Last Name", 
						"Before Value JSON", "Current Value JSON","Modified By","Modified User Name",
						"Modified Date"});
				}
				
				isrecordsEist=true;

					
					for (StudentAuditTrailHistory studentAuditTrailHistory : studentAuditTrailHistoryList) {
				studentAuditTrailHistory.validate();
				records.add(new String[] {
						studentAuditTrailHistory.getId().toString(),
						studentAuditTrailHistory.getEventName(),
						studentAuditTrailHistory.getStudentStateId(),
						studentAuditTrailHistory.getStudentFirstName(),
						studentAuditTrailHistory.getStudentLastName(),
						studentAuditTrailHistory.getBeforeValue(),
						studentAuditTrailHistory.getCurrentValue(),
						studentAuditTrailHistory.getModifiedUser().toString(),
						studentAuditTrailHistory.getModifiedUserName(),
						studentAuditTrailHistory.getCreateddateStr()+"."
						
						});
					}
				
			}
			csvWriter.writeAll(records);
			}
			
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}
	private void generateRosterAuditTrailHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName, String stateId, String districtId, String schoolId, String contentArea, String educatorId) throws Exception {

		CSVWriter csvWriter = null;
		List schoolIds=new ArrayList();
		if(!StringUtils.isEmpty(educatorId)){
			stateId=null;
			districtId=null;
			schoolId=null;
		}
		if(!StringUtils.isEmpty(schoolId)){
			schoolIds.add(Long.valueOf(schoolId));
		}else if(!StringUtils.isEmpty(districtId)){
			schoolIds=organizationDao.getChildOrganizations(Long.valueOf(districtId),CommonConstants.ORGANIZATION_TYPE_ID_7);
		}
		
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<String[]> records = new ArrayList<String[]>();
			List<RosterAuditTrailHistory> rosterAuditTrailHistoryList = userAuditTrailHistoryMapper
					.getRosterAuditTrailHistory(startDateTimes, endDateTimes,stateId,schoolIds,contentArea,educatorId,offset,limit);
			offset = offset + limit;
			
			recordCount=rosterAuditTrailHistoryList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
			
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				if(!isrecordsEist){
			
				records.add(new String[] { "ID ", "Event Name",
						 "Affected Roster","Roster Name","State","District","School","Educator Name",
						"Educator ID","Subject","Before Value JSON", "Current Value JSON",
						"Modified By","Modified User Name","Modified Date"});
			}
			for (RosterAuditTrailHistory rosterAuditTrailHistory : rosterAuditTrailHistoryList) {
				rosterAuditTrailHistory.validate();
				records.add(new String[] {
						rosterAuditTrailHistory.getId().toString(),
						rosterAuditTrailHistory.getEventName(),
						rosterAuditTrailHistory.getAffectedroster().toString(),
						rosterAuditTrailHistory.getRosterName(),
						rosterAuditTrailHistory.getState(),
						rosterAuditTrailHistory.getDistrict(),
						rosterAuditTrailHistory.getSchool(),
						rosterAuditTrailHistory.getEducatorName(),
						rosterAuditTrailHistory.getEducatorId(),
						rosterAuditTrailHistory.getSubject(),
						rosterAuditTrailHistory.getBeforeValue(),
						rosterAuditTrailHistory.getCurrentValue(),
						rosterAuditTrailHistory.getModifiedUser().toString(),
						rosterAuditTrailHistory.getModifiedUserName(),
						rosterAuditTrailHistory.getCreateddateStr()+"."
						
						});
					}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}
	
	private void generateRoleAuditTrailHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName, String asessmentProgrmId, String stateId, String groupId) throws Exception {

		CSVWriter csvWriter = null;
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');

			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<String[]> records = new ArrayList<String[]>();
			List<RoleAuditTrailHistory> roleAuditTrailHistoryList = userAuditTrailHistoryMapper
					.getRoleAuditTrailHistory(startDateTimes, endDateTimes,asessmentProgrmId,stateId,groupId,offset,limit);
			
			offset = offset + limit;
			
			recordCount=roleAuditTrailHistoryList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				if(!isrecordsEist){
			
				records.add(new String[] { "ID ", "Event Name",
						"Affected Role","Affected Role Name" ,"Assessment program","State Name","Before Value JSON",
						"Current Value JSON","Modified By","Modified User Name","Modified Date" });
				isrecordsEist=true;
				}
			for (RoleAuditTrailHistory roleAuditTrailHistory : roleAuditTrailHistoryList) {
				roleAuditTrailHistory.validate();
				records.add(new String[] {
						roleAuditTrailHistory.getId().toString(),
						roleAuditTrailHistory.getEventName(),
						roleAuditTrailHistory.getAffectedrole().toString(),
						roleAuditTrailHistory.getAffectedrolename(),
						roleAuditTrailHistory.getAssessmentProgram(),
						roleAuditTrailHistory.getStateNames(),
						roleAuditTrailHistory.getBeforeValue(),
						roleAuditTrailHistory.getCurrentValue(),
						StringUtils.isEmpty(roleAuditTrailHistory.getModifiedUserName())?new String():roleAuditTrailHistory.getModifiedUserName(),
						//roleAuditTrailHistory.getModifiedUser().toString(),
						roleAuditTrailHistory.getModifiedUserName(),
						roleAuditTrailHistory.getCreateddateStr()+"."
						
						});
					}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}
	
	private void generateOrganizationManagementAuditCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName, String stateId,String districtId,String schoolId, String organizationId) throws Exception {

		CSVWriter csvWriter = null;
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');

			List<String[]> records = new ArrayList<String[]>();
			Map<String,String> filterValues=new HashMap();
			
			if(!StringUtils.isEmpty(organizationId)){
				filterValues.put("organizationId",organizationId);
				filterValues.put("stateId",null);
				filterValues.put("sourceordestorgdistrictid",null);
				filterValues.put("schoolId",null);
				 
			}else if (!StringUtils.isEmpty(schoolId)||!StringUtils.isEmpty(districtId)||!StringUtils.isEmpty(stateId)){
			
				filterValues.put("organizationId",null);				
				filterValues.put("stateId",StringUtils.isEmpty(stateId) ? null:stateId);
				filterValues.put("sourceordestorgdistrictid",StringUtils.isEmpty(districtId) ? null:districtId);
				filterValues.put("schoolId",StringUtils.isEmpty(schoolId) ? null:schoolId);				
			}else {
				filterValues.put("organizationId",null);				
				filterValues.put("stateId",null);
				filterValues.put("sourceordestorgdistrictid",null);
				filterValues.put("schoolId",null);
			}
			
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<OrganizationManagementAudit> OrganizationManagementAuditList = organizationManagementAuditDao
					.getOrganizationManagementAuditHistory(startDateTimes, endDateTimes, filterValues,offset,limit);
			offset = offset + limit;
			
			recordCount=OrganizationManagementAuditList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				if(!isrecordsEist){
			
				records.add(new String[] { "ID ", "Operation Type", "State Name", "Source OrgId", "Source OrgName", "Source OrgDistrict Name", "Dest OrgId", 
						"Destination OrgName","Dest OrgDistrict Name",
						"State Student ID", "User Id","User Name", "Roster Id","Roster Name", "Enrollment Id",
						"Current SchoolYear","Modified By","Modified User Name", "Modified Date"
						});
				isrecordsEist=true;
			}
			for (OrganizationManagementAudit organizationManagementAudit : OrganizationManagementAuditList) {
				organizationManagementAudit.validate();
				
				String destOrgId = String.valueOf(organizationManagementAudit.getDestOrgId());
				String aartUserId = String.valueOf(organizationManagementAudit.getAartUserId());
				String rosterId = String.valueOf(organizationManagementAudit.getRosterId());
				String enrollment = String.valueOf(organizationManagementAudit.getEnrollmentId());
				
				records.add(new String[] {
						organizationManagementAudit.getId().toString(),
						organizationManagementAudit.getOperationType(),						
						StringUtils.isEmpty(organizationManagementAudit.getStateName())? new String():organizationManagementAudit.getStateName(),					
						organizationManagementAudit.getSourceOrgId().toString(),
						StringUtils.isEmpty(organizationManagementAudit.getSourceOrgNameWithIdentifier())? new String():organizationManagementAudit.getSourceOrgNameWithIdentifier(),
						StringUtils.isEmpty(organizationManagementAudit.getSourceOrgDistrictName())?new String():organizationManagementAudit.getSourceOrgDistrictName(),
						destOrgId.equals("0")?new String():destOrgId,
						StringUtils.isEmpty(organizationManagementAudit.getDestOrgNameWithIdentifier())? new String():organizationManagementAudit.getDestOrgNameWithIdentifier(),
						StringUtils.isEmpty(organizationManagementAudit.getDestOrgDistrictName())?new String():organizationManagementAudit.getDestOrgDistrictName(),		
						StringUtils.isEmpty(organizationManagementAudit.getStateStudentIdentifier())? new String():organizationManagementAudit.getStateStudentIdentifier(),
						aartUserId.equals("0")?new String():aartUserId,
						StringUtils.isEmpty(organizationManagementAudit.getAartUserName())? new String():organizationManagementAudit.getAartUserName(),
						rosterId.equals("0")?new String():rosterId,
						StringUtils.isEmpty(organizationManagementAudit.getRosterName())?new String():organizationManagementAudit.getRosterName(),
						enrollment.equals("0")?new String():enrollment,
						organizationManagementAudit.getCurrentSchoolYear().toString(),
						organizationManagementAudit.getModifiedUser().toString(),
						StringUtils.isEmpty(organizationManagementAudit.getModifiedUserName())?new String():organizationManagementAudit.getModifiedUserName(),
								organizationManagementAudit.getModifieddateStr()+"."
								
						});
					}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}
	
	private void generateOrganizationAuditTrailHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName,String stateId,String districtId,String schoolId,String organizationId) throws Exception {

		CSVWriter csvWriter = null;
		try {
			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');
			List<String[]> records = new ArrayList<String[]>();
			Map<String,String> filterValues=new HashMap();
			
			/**
			  * uday
			  * F424
			  * Filter values for audit history 
			  */
			
			if(!StringUtils.isEmpty(organizationId)){
				filterValues.put("organizationId",organizationId);
				filterValues.put("stateId",null);
				filterValues.put("districtId",null);
				filterValues.put("schoolId",null);
				 
			}else if (!StringUtils.isEmpty(schoolId)||!StringUtils.isEmpty(districtId)||!StringUtils.isEmpty(stateId)){
			
				filterValues.put("organizationId",null);				
				filterValues.put("stateId",StringUtils.isEmpty(stateId) ? null:stateId);
				filterValues.put("districtId",StringUtils.isEmpty(districtId) ? null:districtId);
				filterValues.put("schoolId",StringUtils.isEmpty(schoolId) ? null:schoolId);				
			}else {
				filterValues.put("organizationId",null);				
				filterValues.put("stateId",null);
				filterValues.put("districtId",null);
				filterValues.put("schoolId",null);
			}
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<OrganizationAuditTrailHistory> organizationAuditTrailHistoryList = userAuditTrailHistoryMapper
					.getOrganizationAuditTrailHistory(startDateTimes, endDateTimes,filterValues,offset,limit);
			
			offset = offset + limit;
			
			recordCount=organizationAuditTrailHistoryList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				
				if(!isrecordsEist){
				records.add(new String[] { "ID ", "Event Name",
						"Affected Organization","State","District","School",
						"Before Value JSON", "Current Value JSON",
						"Modified By","Modified User Name","Modified Date"});
			}
			for (OrganizationAuditTrailHistory organizationAuditTrailHistory : organizationAuditTrailHistoryList) {
				organizationAuditTrailHistory.validate();
				records.add(new String[] {
						organizationAuditTrailHistory.getId().toString(),
						organizationAuditTrailHistory.getEventName(),
						organizationAuditTrailHistory.getAffectedorganization().toString(),
						StringUtils.isEmpty(organizationAuditTrailHistory.getStateName())? new String():organizationAuditTrailHistory.getStateName(),
						StringUtils.isEmpty(organizationAuditTrailHistory.getDistrictName())? new String():organizationAuditTrailHistory.getDistrictName(),
						StringUtils.isEmpty(organizationAuditTrailHistory.getSchoolName())? new String():organizationAuditTrailHistory.getSchoolName(),
						organizationAuditTrailHistory.getBeforeValue(),
						organizationAuditTrailHistory.getCurrentValue(),
						organizationAuditTrailHistory.getModifiedUser().toString(),
						organizationAuditTrailHistory.getModifiedUserName(),
						organizationAuditTrailHistory.getCreateddateStr()+"."
						
				});
					}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}
	private void generateFirstContactSurveyAuditHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName, String stateStudentId) throws Exception {

		CSVWriter csvWriter = null;
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<String[]> records = new ArrayList<String[]>();
			List<FirstContactSurveyAuditHistory> FirstContactSurveyAuditHistoryList = userAuditTrailHistoryMapper
					.getFirstContactSurveyAuditHistory(startDateTimes, endDateTimes,stateStudentId,offset,limit);
	
			offset = offset + limit;
			
			recordCount=FirstContactSurveyAuditHistoryList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				if(!isrecordsEist){
				records.add(new String[] { "ID ", "Event Name","SurveyId","StudentId", "State Student Id","Student FirstName",
						"Student LastName","Survey Status BeforeEdit","Survey Status AfterEdit",						
						"Before Value JSON"
						,"Current Value JSON", "Modified By","modifiedUserName","Modifyed Date"
						 	 });
			}
			for (FirstContactSurveyAuditHistory firstContactSurveyAuditHistory : FirstContactSurveyAuditHistoryList) {
				
				firstContactSurveyAuditHistory.validate();
				String beforeValue = firstContactSurveyAuditHistory.getBeforeValue().replaceAll("\"", StringUtils.EMPTY);
				String afterValue = firstContactSurveyAuditHistory.getCurrentValue().replaceAll("\"", StringUtils.EMPTY);
				int excelCellLimit = 32700;
				records.add(new String[] {
					
						firstContactSurveyAuditHistory.getId().toString(),
						firstContactSurveyAuditHistory.getEventName(),
						firstContactSurveyAuditHistory.getSurveyId().toString(),
						firstContactSurveyAuditHistory.getStudentId().toString(),
						firstContactSurveyAuditHistory.getStateStudentIdentifier(),
						firstContactSurveyAuditHistory.getStudentFirstName(),
						firstContactSurveyAuditHistory.getStudentLastName(),
						firstContactSurveyAuditHistory.getSurveyStatusBeforeEdit(),
						firstContactSurveyAuditHistory.getSurveyStatusAfterEdit(),
						(beforeValue.length()> excelCellLimit-2 ? "\""+beforeValue.substring(0, excelCellLimit-2)+"\"" : "\""+beforeValue+"\""),
						(afterValue.length()> excelCellLimit-2 ? "\""+afterValue.substring(0, excelCellLimit-2)+"\"" : "\""+afterValue+"\""),
						StringUtils.isEmpty(firstContactSurveyAuditHistory.getModifiedUser().toString())?new String():firstContactSurveyAuditHistory.getModifiedUser().toString(),
						//firstContactSurveyAuditHistory.getModifiedUser().toString(),
						firstContactSurveyAuditHistory.getModifiedUserName(),
						firstContactSurveyAuditHistory.getCreateddateStr()+"."
						
											
						});
					}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}	
	private void generateActivationTemplateAuditTrailHistoryCSV(Date startDateTimes, Date endDateTimes,
			HttpServletResponse response, String serverPath, String fileName,String assessmentProgram,String stateId) throws Exception {

		CSVWriter csvWriter = null;
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');
			int offset = 0,recordCount=0, limit = 5000;
			boolean isrecordsEist=false;
			while (offset == 0 || recordCount== limit) {
			List<String[]> records = new ArrayList<String[]>();
			List<ActivationTemplateAuditTrailHistory> activationTemplateAuditTrailHistoryList = userAuditTrailHistoryMapper
					.getActivationTemplateAuditTrailHistory(startDateTimes, endDateTimes,assessmentProgram,stateId,offset,limit);
			offset = offset + limit;
			
			recordCount=activationTemplateAuditTrailHistoryList.size();
			
		if( recordCount<=0 && !isrecordsEist)
		{
				records.add(new String []{"No data found for the entered date and time combination."});
			}
			else {
				if(!isrecordsEist){
			
				records.add(new String[] { "ID ","Event Name",
						 "Affected Email Template Id","Template Name",
						 "Assessment Program","State",
						"Before Value JSON", "Current Value JSON",
						"Modified By","Modified User Name","Modified Date"
						 });
			}
			for (ActivationTemplateAuditTrailHistory activationTemplateAuditTrailHistory : activationTemplateAuditTrailHistoryList) {
				activationTemplateAuditTrailHistory.validate();
				records.add(new String[] {
						activationTemplateAuditTrailHistory.getId().toString(),						
						activationTemplateAuditTrailHistory.getEventName(),
						activationTemplateAuditTrailHistory.getAffectedemailtemplateid().toString(),
						activationTemplateAuditTrailHistory.getTemplateName(),
						activationTemplateAuditTrailHistory.getAssessmentProgram(),
						activationTemplateAuditTrailHistory.getStateNames(),
						activationTemplateAuditTrailHistory.getBeforeValue(),
						activationTemplateAuditTrailHistory.getCurrentValue(),
						activationTemplateAuditTrailHistory.getModifiedUser().toString(),
						activationTemplateAuditTrailHistory.getModifiedUserName(),
						activationTemplateAuditTrailHistory.getCreateddateStr()+"."
						
						});
					}
			}
			csvWriter.writeAll(records);
			}
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}	
	
	@Override
	public void downloadKAPCustomExtract(HttpServletResponse response, HttpServletRequest request) {
		response.setContentType("application/force-download");
		String fileName = new StringBuilder().append("KAP_Custom_Extract_").append(System.currentTimeMillis())
				.append(".csv").toString().toString();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		String serverPath = request.getSession().getServletContext().getRealPath("/");
		try {
			writeKAPCustomExtract(request, fileName, response, serverPath);
			response.flushBuffer();
		} catch (Exception e) {
			LOGGER.error("Exception in Generating CSV for KAP Custom Extract", e);
		}
		return;
	}

	private void writeKAPCustomExtract(HttpServletRequest request, String fileName, HttpServletResponse response,
			String serverPath) throws Exception {
		CSVWriter csvWriter = null;
		try {

			File csvFile = new File(fileName);
			csvWriter = new CSVWriter(response.getWriter(), ',');

			List<String[]> records = new ArrayList<String[]>();
			String studentIds = (String) request.getParameter("studentIds");
			String[] studentIdList = studentIds.split(",");
			Long[] studentIdListLong = new Long[studentIdList.length];
			for (int i = 0; i < studentIdList.length; i++) {
				studentIdListLong[i] = new Long(studentIdList[i]);
			}

			List<KAPCustomExtract> kAPCustomExtractList = domainAuditHistoryMapper
					.getKAPCustomExtractData(studentIdListLong);
			records.add(new String[] { "State", "District", "School", "School Identifier", "Grade", "Student Last Name",
					"Student First Name", "Student Middle Name", "State Student Identifier", "Local Student Identifier",
					"Enrollment ID", "Enrollment Status", "Subject", "Test Session Name", "Test Session Id", "Test Id",
					"Test Collection Id", "Test Status", "Test Active Flag", "Stage", "Total Items",
					"Total Omitted Items", "Test Start Date", "Test End Date", "Special Circumstance",
					"Special Circumstance Status", "Last Reactivated Date Time" });
			for (KAPCustomExtract kAPCustomExtract : kAPCustomExtractList) {
				kAPCustomExtract.validate();

				records.add(new String[] { kAPCustomExtract.getState(), kAPCustomExtract.getDistrict(),
						kAPCustomExtract.getSchool(), kAPCustomExtract.getSchoolIdentifer(),
						kAPCustomExtract.getStudentLastname(), kAPCustomExtract.getStudentFirstname(),
						kAPCustomExtract.getStudentMiddlename(), kAPCustomExtract.getStateStudentIdentifier(),
						kAPCustomExtract.getLocalStudentIdentifier(), kAPCustomExtract.getEnrollmentId(),
						kAPCustomExtract.getEnrollmentStatus(), kAPCustomExtract.getSubject(),
						kAPCustomExtract.getTestSessionName(), kAPCustomExtract.getTestSessionId(),
						kAPCustomExtract.getTestid(), kAPCustomExtract.getTestCollectionId(),
						kAPCustomExtract.getTestStatus(), kAPCustomExtract.getTestActiveFlag(),
						kAPCustomExtract.getStage(), kAPCustomExtract.getTotalItems(),
						kAPCustomExtract.getTotaloMittedItems(), kAPCustomExtract.getTestStartDate(),
						kAPCustomExtract.getTestEndDate(), kAPCustomExtract.getSpecialCircumstance(),
						kAPCustomExtract.getSpecialCircumstanceStatus(),
						kAPCustomExtract.getSpecialCircumstanceStatus() });
			}
			csvWriter.writeAll(records);
			response.flushBuffer();

		} catch (IOException ex) {
			throw ex;
		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}

	}

	@Override
	public List<AuditType> getAuditTypeList() {
		// TODO Auto-generated method stub		
		return domainAuditHistoryMapper.getAuditTypeList();
	}

	
}
