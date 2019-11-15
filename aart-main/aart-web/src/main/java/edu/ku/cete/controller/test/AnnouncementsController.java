package edu.ku.cete.controller.test;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.controller.BaseController;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.announcements.CommunicationMessage;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.announcements.AnnouncementsService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;

/**
 * Announcements Controller for DLM.
 * 
 * @author scriptbees
 *
 */

@Controller
public class AnnouncementsController extends BaseController{

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory
			.getLogger(AnnouncementsController.class);

	@Autowired
	private AnnouncementsService announcementsService;
	
	@Autowired
    private GroupsService groupsService;
	
	@Autowired
	private GroupsDao groupsDao;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;

	/**
	 * User organization for DLM
	 */
	@Value("${user.organization.DLM}")
	private String USER_ORG_DLM;

	// CHANGE
	@RequestMapping(value = "getMessagesByAssessmentProgram.htm", method = RequestMethod.POST)
	private final @ResponseBody Map<String, Object> getMessagesByAssessmentProgram(@RequestParam("rows") String limitCountStr,
			@RequestParam("page") String page, @RequestParam("sidx") String sortByColumn,
			@RequestParam("sord") String sortType, @RequestParam(value = "filters", required = false) String filters)
			throws JsonProcessingException, IOException {

		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);

		List<CommunicationMessage> messageList = new ArrayList<CommunicationMessage>();
		Map<String, Object> responseMap = new HashMap<String, Object>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Organization organization = user.getOrganization();

		Map<String, Object> criteria = constructMessageCriteria(filters);

		Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());

		if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
			messageList = announcementsService.getAllActiveMessages(sortByColumn, sortType,
					(currentPage - 1) * limitCount, limitCount, criteria);

			totalCount = announcementsService.getAllActiveMessagesCount(criteria);
		} else {
			messageList = announcementsService.getMessagesByAssessmentProgram(organization.getId(), sortByColumn, sortType,
					(currentPage - 1) * limitCount, limitCount, criteria, user.getCurrentAssessmentProgramId());

			totalCount = announcementsService.messageCountDetails(criteria, user.getCurrentAssessmentProgramId());
		}
		

		responseMap.put("rows", messageList);
		responseMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		responseMap.put("page", currentPage);
		responseMap.put("records", totalCount);
		LOGGER.trace("Leaving the getMessagesByAssessmentProgram() method.");
		return responseMap;

	}

	private Map<String, Object> constructMessageCriteria(String filters) {
		LOGGER.debug("Entering the constructMessageCriteria method");
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> messageCriteriaMap = new HashMap<String, Object>();
		if (null != filters && !filters.equals("")) {
			RecordBrowserFilter recordBrowserFilter = null;
			// Parse the column filter values which the user enters on the UI
			// record browser grid.
			try {
				recordBrowserFilter = mapper.readValue(filters, new TypeReference<RecordBrowserFilter>() {
				});
			} catch (JsonParseException e) {
				LOGGER.error("Couldn't parse json object.", e);
			} catch (JsonMappingException e) {
				LOGGER.error("Unexpected json mapping", e);
			} catch (SecurityException e) {
				LOGGER.error("Unexpected exception with reflection", e);
			} catch (IllegalArgumentException e) {
				LOGGER.error("Unexpected exception with reflection", e);
			} catch (Exception e) {
				LOGGER.error("Unexpected error", e);
			}
			if (recordBrowserFilter != null) {
				for (RecordBrowserFilterRules recordBrowserFilterRules : recordBrowserFilter.getRules()) {
					messageCriteriaMap.put(recordBrowserFilterRules.getField(), CommonConstants.PERCENTILE_DELIM
							+ recordBrowserFilterRules.getData().trim() + CommonConstants.PERCENTILE_DELIM);
				}
			}
		}
		LOGGER.debug("" + messageCriteriaMap);
		LOGGER.debug("Leaving the constructMessageCriteria method");
		return messageCriteriaMap;
	}

	// COMMENT
	@RequestMapping(value = "getNotificationAssessments.htm", method = RequestMethod.GET)
	private @ResponseBody List<CommunicationMessage> getAssessmentDetails() {

		CommunicationMessage createMessage = new CommunicationMessage();
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		List<CommunicationMessage> messageList = new ArrayList<CommunicationMessage>();

		Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());

		if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {

			List<AssessmentProgram> aps = assessmentProgramService.getAllActive();
			for (AssessmentProgram ap : aps) {

				createMessage = new CommunicationMessage();
				createMessage.setAssessmentId(ap.getId());
				createMessage.setAssessmentName(ap.getProgramName());

				messageList.add(createMessage);
			}
		} else {
			createMessage.setAssessmentId(user.getCurrentAssessmentProgramId());
			messageList = announcementsService.getAssessmentPrograms(createMessage);
		}
		return messageList;
	}

	// COMMENT
	@RequestMapping(value = "getStatesList.htm", method = RequestMethod.GET)
	private @ResponseBody List<CommunicationMessage> getStatesList(
			@RequestParam(value = "assessmentProgramId", required = false) String assessmentProgramId) {
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());
		List<CommunicationMessage> messageList = new ArrayList<CommunicationMessage>();
		if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
			List<Organization> states = organizationService.getByTypeId(CommonConstants.ORGANIZATION_STATE_CODE);
			for (Organization state : states) {
				CommunicationMessage createMessage = new CommunicationMessage();
				createMessage.setStateId(state.getId());
				createMessage.setStateName(state.getOrganizationName());
				messageList.add(createMessage);
			}
		} else {
			CommunicationMessage createMessage = new CommunicationMessage();
			createMessage.setAssessmentProgramId(Long.parseLong(assessmentProgramId));
			messageList = announcementsService.getStatesList(createMessage);
		}

		return messageList;
	}

	// COMMENT
	@RequestMapping(value = "saveMessage.htm", method = RequestMethod.POST)
	private @ResponseBody List<CommunicationMessage> saveMessage(
			@RequestParam(value = "messageTitle", required = true) String messageTitle,
			@RequestParam(value = "messageContent", required = true) String messageContent,
			@RequestParam(value = "displayMessageDate", required = true) String displayMessageDate,
			@RequestParam(value = "expireMessageDate", required = true) String expireMessageDate,
			@RequestParam(value = "assessmentProgramId", required = true) String assessmentProgramId,
			@RequestParam(value = "stateProgramId[]", required = false) String[] stateProgramString,
			@RequestParam(value = "rolesId[]", required = false) String[] rolesString,
			@RequestParam(value = "displayMessagetime", required = true) String displayMessagetime,
			@RequestParam(value = "expireMessagetime", required = true) String expireMessagetime,
			@RequestParam(value = "messageId", required = false) String messageId)
			throws ParseException {

		List<Long> stateProgramIds = new ArrayList<Long>();
		if (stateProgramString != null && stateProgramString.length > 0) {
			for (String stateProgramId : stateProgramString) {
				stateProgramIds.add(Long.parseLong(stateProgramId));
			}
		}
		
		
		List<Long> roleIds = new ArrayList<Long>();
		if (rolesString != null && rolesString.length > 0) {
			for (String roleId : rolesString) {
				roleIds.add(Long.parseLong(roleId));
			}
		}
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
		Organization organization = user.getOrganization();

		CommunicationMessage createMessage = new CommunicationMessage();
		createMessage.setMessageId(Integer.parseInt(messageId));
		createMessage.setOrganizationId(Integer.parseInt(organization.getId()
				+ ""));
		createMessage.setMessageStatus("Active");
		createMessage.setMessageTitle(messageTitle);
		createMessage.setMessageContent(messageContent);
		createMessage.setAssessmentProgramId(Long.parseLong(assessmentProgramId));
		
		createMessage.setStateProgramIdList(stateProgramIds);
		createMessage.setRolesIdList(roleIds);
		
		String expireDate = expireMessageDate + " " + expireMessagetime;
		String displayDate = displayMessageDate + " " + displayMessagetime;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		dateFormat.setTimeZone(TimeZone.getTimeZone("CST"));
		
		
		
		createMessage.setDisplayMessageDate(displayMessageDate);
		createMessage.setDisplayMessagetime(displayMessagetime);
		createMessage.setExpireMessageDate(expireMessageDate);
		createMessage.setExpireMessagetime(expireMessagetime);
		createMessage.setDisplayDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(displayDate, "US/Central",  "MM/dd/yyyy hh:mm a")
				.getTime()));
		createMessage.setExpiryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(expireDate, "US/Central",  "MM/dd/yyyy hh:mm a")
				.getTime()));
		List<CommunicationMessage> messageList = new ArrayList<CommunicationMessage>();

		if (createMessage.getMessageId() != 0) {
			announcementsService.updateMessage(createMessage);
		} else {
			announcementsService.saveMessage(createMessage);
		}

		return messageList;
	}

	@RequestMapping(value = "editMessage.htm", method = RequestMethod.GET)
	private @ResponseBody CommunicationMessage editMessage(
			@RequestParam(value = "messageId", required = false) String messageId,
			@RequestParam(value = "assessmentProgramId", required = false) String assessmentProgramId) {

		CommunicationMessage createMessage = announcementsService.editMessageData(Integer.parseInt(messageId));

		return createMessage;
	}

	@RequestMapping(value = "cancelMessage.htm", method = RequestMethod.GET)
	private @ResponseBody CommunicationMessage cancelMessage(
			@RequestParam(value = "messageId", required = true) String messageId) {

		CommunicationMessage createMessage = new CommunicationMessage();
		createMessage.setMessageId(Integer.parseInt(messageId));
		createMessage.setMessageStatus("Cancelled");
		createMessage.setExpiryDate(new Timestamp(new Date().getTime()));
		createMessage.setAuditColumnPropertiesForDelete();
		announcementsService.cancelMessage(createMessage);
		return createMessage;
	}

	/**
	 * Gets Current user Messages Based their associated Organization.
	 * 
	 * @return Returns the Communication Messages which are for the user and
	 *         these will be filtered again by the Permission the User has.
	 */
	@RequestMapping(value = "getUserMessageList.htm", method = RequestMethod.POST)
	private @ResponseBody List<CommunicationMessage> getUserMessageList() {

		List<CommunicationMessage> messageList = new ArrayList<CommunicationMessage>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Long stateId = getStateId(user.getCurrentOrganizationId());
		messageList = announcementsService.getUserMessageList(stateId, user.getCurrentGroupsId(), user.getCurrentAssessmentProgramId());

		return messageList;
	}

	@RequestMapping(value = "getRolesForNotifications.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getRolesForNotifications(final HttpServletRequest request,
			final HttpServletResponse response) throws ServiceException {
		LOGGER.trace("Entering the view method.");
		Map<String, Object> hashMap = new HashMap<String, Object>();
		UserDetailImpl loggedinUser = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		LOGGER.debug("orgType : " + loggedinUser.getUser().getOrganization().getOrganizationTypeId());
		List<Groups> allGroups = groupsService.getRolesForNotifications();
		hashMap.put("allGroups", allGroups);
		return hashMap;
	}
}
