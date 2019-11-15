package edu.ku.cete.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import java.util.Date;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import edu.ku.cete.util.DateUtil;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.help.HelpContent;
import edu.ku.cete.domain.help.HelpTag;
import edu.ku.cete.domain.help.HelpTopic;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.HelpContentService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;

@Controller
public class HelpContentController extends BaseController {

	private Logger LOGGER = LoggerFactory.getLogger(HelpContentController.class);

	private static final String VIEW_HELP_TAB_JSP = "helptab";

	@Value("${help.content.file.path}")
	private String HELP_CONTENT_FILE_PATH;

	@Value("${help.topic.host.url}")
	private String HELP_TOPIC_HOST_URL;

	@Autowired
	private HelpContentService helpContentService;

	@Autowired
	private GroupsDao groupsDao;

	@Autowired
	private AssessmentProgramService assessmentProgramService;

	@Autowired
	private GroupsService groupsService;
	
	@Autowired
	private AwsS3Service s3;

	@RequestMapping(value = "helpTab.htm")
	public final ModelAndView helpTab() {
		LOGGER.trace("--> helpTab");
		User user = getContextUser();
		List<HelpTopic> helpTopics = getHelpTopicsByUserContex(user);
		ModelAndView mv = new ModelAndView(VIEW_HELP_TAB_JSP);
		mv.addObject("helpTopics", helpTopics);
		LOGGER.trace("<-- helpTab");
		return mv;

	}
	
	/*@RequestMapping(value = "viewHelp.htm")
	public final ModelAndView getviewHelp() {
		return new ModelAndView("helpTab");
	}*/

	@RequestMapping(value = "saveHelpTopic.htm", method = RequestMethod.POST)
	public final @ResponseBody String saveHelpTopic(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "slug", required = true) String slug) throws Exception {
		try {
			LOGGER.info("--> saveHelpTopic.htm ");

			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = getObjectMapper();

			if(helpContentService.existsTopicByNames(name).booleanValue()){
				throw new Exception("Topic Name already exists");
			}
			 if(helpContentService.existsTopicBySlug(slug).booleanValue()){
				throw new Exception("Slug URL already exists");
			}
			 
			HelpTopic helpTopic = new HelpTopic();
			helpTopic.setName(name);
			helpTopic.setDescription(description);

			if (StringUtils.isNotBlank(slug)) {
				slug = slug.replace(" ", "-");
			} else {
				slug = name.replace(" ", "-");
			}

			helpContentService.saveHelpTopic(helpTopic, slug);

			model.put("status", "success");
			model.put("successMessage", "Topic " + name + " has Successfully created");

			
			String modelJson = mapper.writeValueAsString(model);

			LOGGER.debug("<-- saveHelpTopic");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred in saving Help Topic: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "updateHelpTopic.htm", method = RequestMethod.POST)
	public final @ResponseBody String updateHelpTopic(
			@RequestParam(value = "helpTopicId", required = true) Long helpTopicId,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "slug", required = true) String slug) throws Exception {
		try {
			LOGGER.info("--> updateHelpTopic.htm ");

			Map<String, Object> model = new HashMap<String, Object>();

			ObjectMapper mapper = getObjectMapper();

			if(helpContentService.existsEditTopicByNames(helpTopicId,name).booleanValue()){
				throw new Exception("Topic Name already exists");
			}
			 if(helpContentService.existsEditTopicBySlug(helpTopicId,slug).booleanValue()){
				throw new Exception("Slug URL already exists");
			}
			HelpTopic helpTopic = new HelpTopic();
			helpTopic.setId(helpTopicId);
			helpTopic.setName(name);
			helpTopic.setDescription(description);

			if (StringUtils.isNotBlank(slug)) {
				slug = slug.replace(" ", "-");
			} else {
				slug = name.replace(" ", "-");
			}

			helpContentService.updateHelpTopic(helpTopic, slug);

			model.put("status", "success");
			model.put("successMessage", "Topic " + name + " has Successfully updated");
			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- updateHelpTopic");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred in updating Help Topic: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getHelpTopic.htm", method = RequestMethod.POST)
	public final @ResponseBody String getHelpTopic(
			@RequestParam(value = "helpTopicId", required = true) Long helpTopicId) {
		try {
			LOGGER.info("->> getHelpTopic.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			HelpTopic helpTopic = helpContentService.getHelpTopic(helpTopicId);

			model.put("status", "success");
			model.put("helpTopic", helpTopic);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpTopic.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting Help Topic: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getHelpTopics.htm", method = RequestMethod.GET)
	public final @ResponseBody String getHelpTopics() {
		try {
			LOGGER.info("->> getHelpTopics.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			List<HelpTopic> helpTopics = helpContentService.getHelpTopics();

			model.put("status", "success");
			model.put("helpTopics", helpTopics);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpTopics.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting Help Topics: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getTopicPageHostUrl.htm", method = RequestMethod.GET)
	public final @ResponseBody String getTopicPageHostUrl() {
		try {
			LOGGER.info("->> getTopicPageHostUrl.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			String topicPageHostUrl = HELP_TOPIC_HOST_URL;

			model.put("status", "success");
			model.put("topicPageHostUrl", topicPageHostUrl);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getTopicPageHostUrl.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting Help Topics: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getHelpTopicCount.htm", method = RequestMethod.GET)
	public final @ResponseBody String getHelpTopicCount() {

		try {
			LOGGER.info("--> getHelpTopicCount.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			Long helpTopicsCount = helpContentService.getHelpTopicCount();

			LOGGER.debug("getting help topic count" + helpTopicsCount);

			model.put("status", "success");
			model.put("count", helpTopicsCount.longValue());
			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpTopicCount");
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while getting Help Topic Count: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getHelpContentAssessments.htm", method = RequestMethod.GET)
	private @ResponseBody String getHelpContentAssessments() {
		try {
			LOGGER.info("->> getHelpContentAssessments.htm ");
			User user = getContextUser();
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());
			List<AssessmentProgram> aps = null;

			if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {

				aps = assessmentProgramService.getAllActive();
			} else {
				aps = assessmentProgramService.getAllAssessmentProgramByUserId(user.getId());
			}
			model.put("status", "success");
			model.put("aps", aps);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpContentAssessments.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting Assessment Programs: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getHelpContentStates.htm", method = RequestMethod.GET)
	private @ResponseBody String getHelpContentStates() {

		try {
			LOGGER.info("->> getHelpContentStates.htm ");
			User user = getContextUser();
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());
			List<Organization> states = null;
			if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
				states = organizationService.getByTypeId(CommonConstants.ORGANIZATION_STATE_CODE);
			} else {
				states = organizationService.getByTypeAndUserId(CommonConstants.ORGANIZATION_STATE_CODE, user.getId());
			}

			model.put("status", "success");
			model.put("states", states);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpContentStates.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getHelpContentRoles.htm", method = RequestMethod.GET)
	private @ResponseBody String getHelpContentRoles() {

		try {
			LOGGER.info("->> getHelpContentRoles.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			List<Groups> allRoles = groupsService.getRolesForNotifications();

			model.put("status", "success");
			model.put("allRoles", allRoles);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpContentRoles.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getHelpTags.htm", method = RequestMethod.GET)
	private @ResponseBody String getHelpTags(@RequestParam(value = "term", required = true) String term) {

		try {
			LOGGER.info("--> getHelpTags.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			List<String> terms = Arrays.asList(term.split(","));

			String requestTerm = terms.get(terms.size() - 1).trim();
			List<HelpTag> helpTags = new ArrayList<HelpTag>();
			if (StringUtils.isNotBlank(requestTerm)) {
				helpTags = helpContentService
						.getHelpTags(CommonConstants.PERCENTILE_DELIM + requestTerm + CommonConstants.PERCENTILE_DELIM);
			}

			model.put("status", "success");
			model.put("helpTags", helpTags);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpTags.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "saveHelpContent.htm", method = RequestMethod.POST)
	private @ResponseBody String saveHelpContent(MultipartHttpServletRequest request, HttpServletResponse response) {
		try {
			LOGGER.info("--> saveHelpContent.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			String uploadFileName = StringUtils.EMPTY;
			String filePath = StringUtils.EMPTY;
			Iterator<String> itr = request.getFileNames();

			String assessmentProgramIds = request.getParameter("assessmentProgramIds");
			List<Long> apIds = new ArrayList<Long>();
			if (assessmentProgramIds != null && assessmentProgramIds.split(",").length > 0) {
				for (String apId : assessmentProgramIds.split(",")) {
					if (StringUtils.isNotBlank(apId)) {
						apIds.add(Long.valueOf(apId));
					}
				}
			}

			String stIds = request.getParameter("stateIds");
			List<Long> states = new ArrayList<Long>();
			if (stIds != null && stIds.split(",").length > 0) {
				for (String stId : stIds.split(",")) {
					if (StringUtils.isNotBlank(stId)) {
						states.add(Long.valueOf(stId));
					}
				}
			}

			String roleIds = request.getParameter("rolesIds");
			List<Long> roles = new ArrayList<Long>();
			if (roleIds != null && roleIds.split(",").length > 0) {
				for (String roleId : roleIds.split(",")) {
					if (StringUtils.isNotBlank(roleId)) {
						roles.add(Long.valueOf(roleId));
					}
				}
			}

			Long helpTopicId = StringUtils.isBlank(request.getParameter("helpTopicId")) ? null
					: Long.valueOf(request.getParameter("helpTopicId"));

			String helpContentTitle = request.getParameter("helpContentTitle");
			String helpContentText = request.getParameter("helpContentText");
			String helpContentTags = request.getParameter("helpContentTags");
			String expireHelpContentDate = request.getParameter("expireHelpContentDate");
			String operation = request.getParameter("operation");

			String slug = request.getParameter("slug");
			if (StringUtils.isNotBlank(slug)) {
				slug = slug.replace(" ", "-");
			} else {
				slug = helpContentTitle.replace(" ", "-");
			}

			
			Long helpContentId = StringUtils.isBlank(request.getParameter("helpContentId")) ? null
					: Long.valueOf(request.getParameter("helpContentId"));
			
			if(helpContentId == null){
				if(helpContentService.existsContentByHelpTitle(helpContentTitle).booleanValue()){
					throw new Exception("Help Content Title already exists");
				}
				 if(helpContentService.existsContentBySlug(slug).booleanValue()){
					throw new Exception("Help Content Slug URL already exists");
				}
			} else {
				if(helpContentService.existsEditContentByHelpTitle(helpContentId, helpContentTitle).booleanValue()){
					throw new Exception("Help Content Title already exists");
				}
				 if(helpContentService.existsEditContentBySlug(helpContentId, slug).booleanValue()){
					throw new Exception("Help Content Slug URL already exists");
				}
			}

			long hcId = helpContentService.saveHelpContent(apIds, states, roles, helpTopicId, helpContentTitle,
					helpContentText, helpContentTags, expireHelpContentDate, operation, helpContentId, null, slug);

			if (itr.hasNext()) {
				MultipartFile mpf = request.getFile(itr.next());
				CommonsMultipartFile cmpf = (CommonsMultipartFile) mpf;
				uploadFileName = cmpf.getOriginalFilename().replace(" ", "_");
				String folderPath = HELP_CONTENT_FILE_PATH;
				if (!folderPath.endsWith(CommonConstants.S3_KEY_SEPARATOR)) {
					folderPath += CommonConstants.S3_KEY_SEPARATOR;
				}

				folderPath = folderPath + "HC"+ hcId;

				filePath = folderPath + CommonConstants.S3_KEY_SEPARATOR + uploadFileName;

				s3.synchMultipartUpload(filePath, cmpf.getInputStream(), cmpf.getContentType());

				HelpContent helpContent = new HelpContent();
				helpContent.setId(hcId);
				helpContent.setFileName("HC"+ hcId + CommonConstants.S3_KEY_SEPARATOR + uploadFileName);
				
				if (StringUtils.isNotBlank(expireHelpContentDate)) {
					// check and convert expire date if exists
					Date expireDate = DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(expireHelpContentDate + " 11:59 PM", "US/Central",  "MM/dd/yyyy hh:mm a");
					helpContent.setExpireDate(expireDate);
				}
				else {
					helpContent.setExpireDate(null);
				}

				helpContentService.updateHelpContent(helpContent);
			}

			model.put("status", "success");
			model.put("successMessage", "Help Content " + operation + " successful.");
			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- saveHelpContent.htm");
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while saving help content: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getUserFaqs.htm", method = RequestMethod.POST)
	private @ResponseBody String getUserFaqs(@RequestParam(value = "faqType", required = true) String faqType) {
		try {
			LOGGER.info("--> getUserFaqs.htm ");
			User user = getContextUser();
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			List<HelpTopic> helpTopics = getHelpTopicsByUserContex(user);
			
			
			model.put("status", "success");
			model.put("helpTopics", helpTopics);
			String modelJson = mapper.writeValueAsString(model);

			LOGGER.debug("<-- getUserFaqs.htm");
			return modelJson;
		} catch (Exception e) {
			LOGGER.error("Exception occurred while saving help content: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	public List<HelpTopic> getHelpTopicsByUserContex(User user) {
		List<HelpTopic> helpTopics = new ArrayList<HelpTopic>();
		Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());

		LOGGER.info("current user id " +user.getId());
		LOGGER.info("current user groupId " +user.getCurrentGroupsId());
		LOGGER.info("current user groupCode " +groups.getGroupCode());
		boolean includeContent = false;
		if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
			helpTopics = helpContentService.getHelpTopics(includeContent);
			for (HelpTopic helpTopic : helpTopics) {
				List<HelpContent> helpContents = helpContentService.getHelpContentByUserContext(0L, 0L, 0L,
						includeContent, helpTopic.getId());
				helpTopic.setHelpContent(helpContents);
			}
		} else {
			Long stateId = getUserStateId();
			helpTopics = helpContentService.getHelpTopicsByUserContext(user.getCurrentAssessmentProgramId(),
					user.getCurrentGroupsId(), stateId, includeContent);
			for (HelpTopic helpTopic : helpTopics) {
				List<HelpContent> helpContents = helpContentService.getHelpContentByUserContext(
						user.getCurrentAssessmentProgramId(), user.getCurrentGroupsId(),
						stateId, includeContent, helpTopic.getId());
				helpTopic.setHelpContent(helpContents);
			}
		}
		return helpTopics;
	}
	public Long getUserStateId() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long organizationId = userDetails.getUser().getCurrentOrganizationId();
        List<Organization> userOrgHierarchy = new ArrayList<Organization>();
        userOrgHierarchy = organizationService.getAllParents(organizationId);
        userOrgHierarchy.add(organizationService.get(organizationId));
        Long stateId = null;
        for(Organization org : userOrgHierarchy){
        	if(org.getOrganizationType().getTypeCode().equals("ST")){
        		stateId = org.getId();
        	}
        }
		return stateId;
	}
	
	@RequestMapping(value = "helpTopic.htm", method = RequestMethod.GET)
	private @ResponseBody String helpTopic(@RequestParam(value = "slugURL", required = true) String slugURL) {

		try {
			LOGGER.info("--> helpTopic.htm ");
			User user = getContextUser();
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			HelpContent helpContent ;
			Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());

			if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
				helpContent = helpContentService.gethelpTopicBySlugUrl(slugURL, 0L, 0L,
						0L);
			} else {
				helpContent = helpContentService.gethelpTopicBySlugUrl(slugURL, user.getCurrentAssessmentProgramId(), user.getCurrentGroupsId(),
						getUserStateId());
			}

			model.put("status", "success");
			model.put("helpContent", helpContent);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- helpTopic.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getAllHelpTopics.htm", method = RequestMethod.POST)
	private final @ResponseBody Map<String, Object> getAllHelpTopics(
			@RequestParam("rows") String limitCountStr, @RequestParam("page") String page,
			@RequestParam("sidx") String sortByColumn, @RequestParam("sord") String sortType,
			@RequestParam(value = "filters", required = false) String filters)
			throws JsonProcessingException, IOException {

		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);

		List<HelpContent> helpContentList = new ArrayList<HelpContent>();
		Map<String, Object> responseMap = new HashMap<String, Object>();

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Organization organization = user.getOrganization();

		Map<String, Object> criteria = constructHelpContentCriteria(filters);

		Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());

		if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
			helpContentList = helpContentService.getAllHelpContents(sortByColumn, sortType,
					(currentPage - 1) * limitCount, limitCount, criteria);

			totalCount = helpContentService.getAllHelpContentsCount(criteria);
		} else {
			helpContentList = helpContentService.getHelpContentByContext(organization.getId(), sortByColumn, sortType,
					(currentPage - 1) * limitCount, limitCount, criteria, user.getCurrentAssessmentProgramId(),
					user.getCurrentGroupsId(), user.getCurrentOrganizationId());

			totalCount = helpContentService.getHelpContentCountByContext(criteria, user.getCurrentAssessmentProgramId(),
					user.getCurrentGroupsId(), user.getCurrentOrganizationId());
		}

		responseMap.put("rows", helpContentList);
		responseMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		responseMap.put("page", currentPage);
		responseMap.put("records", totalCount);
		LOGGER.trace("Leaving the getAllHelpTopics() method.");
		return responseMap;

	}

	private Map<String, Object> constructHelpContentCriteria(String filters) {
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

	@RequestMapping(value = "getHelpContentById.htm", method = RequestMethod.GET)
	private @ResponseBody String getHelpContentById(@RequestParam(value = "id", required = true) Long id) {

		try {
			LOGGER.info("--> getHelpContentById.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			HelpContent helpContent = helpContentService.getHelpContentById(id);

			model.put("status", "success");
			model.put("helpContent", helpContent);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpContentById.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "getViewHelpContentById.htm", method = RequestMethod.GET)
	private @ResponseBody String getViewHelpContentById(@RequestParam(value = "id", required = true) Long id) {

		try {
			LOGGER.info("--> getViewHelpContentById.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			HelpContent helpContent = helpContentService.getViewHelpContentById(id);

			model.put("status", "success");
			model.put("helpContent", helpContent);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getViewHelpContentById.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	private ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
		return mapper;
	}

	private User getContextUser() {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userDetails.getUser();
	}
	
	
	@RequestMapping(value = "getHelpContentFile.htm", method = RequestMethod.GET)
	public @ResponseBody void getHelpContentFile(@RequestParam("fileName") String fileName, final HttpServletRequest request,
			final HttpServletResponse response) {

		String path = HELP_CONTENT_FILE_PATH.endsWith(CommonConstants.S3_KEY_SEPARATOR) ? HELP_CONTENT_FILE_PATH : (HELP_CONTENT_FILE_PATH + CommonConstants.S3_KEY_SEPARATOR );

		InputStream inputStream = null;
		try {
			
			if(fileName.toLowerCase().contains(".png")){
				response.setContentType("image/png");
				response.addHeader("Content-Disposition", "inline");
			} else{
				response.setContentType("application/force-download");
				response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
			}
			String fullPath = path + fileName;
			if (s3.doesObjectExist(fullPath)){
				inputStream = s3.getObject(fullPath).getObjectContent();
				IOUtils.copy(inputStream, response.getOutputStream());
				response.flushBuffer();
			}
		} catch (FileNotFoundException e) {
			LOGGER.error("Help content file not Found. File - " + fileName);
			LOGGER.error("FileNotFoundException : ", e);
		} catch (IOException e) {
			LOGGER.error("Error occurred while fetching File - ." + fileName);
			LOGGER.error("IOException : ", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					LOGGER.error("ignore IOException closing: ", e);
				}
			}
		}
	}
	
	@RequestMapping(value = "getHelpContentPDFFile.htm", method = RequestMethod.GET)
	public final @ResponseBody String getHelpContentPDFFile(@RequestParam("fileName") String fileName, final HttpServletRequest request,
			final HttpServletResponse response) {

		String path = HELP_CONTENT_FILE_PATH.endsWith(CommonConstants.S3_KEY_SEPARATOR) ? HELP_CONTENT_FILE_PATH : (HELP_CONTENT_FILE_PATH + CommonConstants.S3_KEY_SEPARATOR );

		Map<String, Object> model = new HashMap<String, Object>();
		ObjectMapper mapper = getObjectMapper();
		try {
			
			if(fileName.toLowerCase().contains(".pdf")){
				String fullPath = path + fileName;
				if (s3.doesObjectExist(fullPath)){
					InputStream inputStream = s3.getObject(fullPath).getObjectContent();
					String[] splitFileName = fullPath.split("\\.");
					File tempFile = File.createTempFile(splitFileName[0], "."+splitFileName[1]);
					FileUtils.copyInputStreamToFile(inputStream, tempFile);
					PDDocument document = PDDocument.load(tempFile);
					PDFRenderer pdfRenderer = new PDFRenderer(document);
					List<String> hcPdfImages = new ArrayList<String>();
					for (int page = 0; page < document.getNumberOfPages(); ++page)
					{ 
					    BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 300, ImageType.RGB);
					    // suffix in filename will be used as the file format
					    String imgFileName = fileName.replace(".pdf", "") + "-" + (page+1) + ".png";
					    ImageIOUtil.writeImage(bim, path + imgFileName, 300);
					    hcPdfImages.add(imgFileName);
					}
					document.close();
					model.put("hcPdfImages", hcPdfImages);
					FileUtils.deleteQuietly(tempFile);
				}
			}
			model.put("status", "success");

			String modelJson = mapper.writeValueAsString(model);
			return modelJson;
			
		} catch (FileNotFoundException e) {
			LOGGER.error("Help content file not Found. File - " + fileName);
			LOGGER.error("FileNotFoundException : ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		} catch (IOException e) {
			LOGGER.error("Error occurred while fetching File - ." + fileName);
			LOGGER.error("IOException : ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}
	
	@RequestMapping(value = "helpTopicEligibleForDelete.htm", method = RequestMethod.GET)
	private @ResponseBody String helpTopicEligibleForDelete(
			@RequestParam(value = "helpTopicId", required = true) Long helpTopicId) {

		try {
			LOGGER.info("--> helpTopicEligibleForDelete.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();
			boolean eligible = false;

			eligible = helpContentService.helpTopicEligibleForDelete(helpTopicId);

			model.put("status", "success");
			model.put("eligible", eligible);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- helpTopicEligibleForDelete.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "deleteHelpTopic.htm", method = RequestMethod.POST)
	private @ResponseBody String deleteHelpTopic(
			@RequestParam(value = "helpTopicId", required = true) Long helpTopicId) {

		try {
			LOGGER.info("--> deleteHelpTopic.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			helpContentService.deleteHelpTopic(helpTopicId);

			model.put("status", "success");
			model.put("successMessage", " Selected topic has successfully deleted");

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- deleteHelpTopic.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	@RequestMapping(value = "deleteHelpContent.htm", method = RequestMethod.POST)
	private @ResponseBody String deleteHelpContent(
			@RequestParam(value = "helpContentId", required = true) Long helpContentId) {

		try {
			LOGGER.info("--> deleteHelpTopic.htm ");
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			helpContentService.deleteHelpContent(helpContentId);

			model.put("status", "success");
			model.put("successMessage", " Selected content has successfully deleted");

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- deleteHelpContent.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}

	public List<HelpContent> getHelpContentsByHelpTopicId(User user, Long helpTopicId) {
		boolean includeContent = false;
		List<HelpContent> helpContents = new ArrayList<HelpContent>();
		Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());

		if (groups.getGroupCode().equalsIgnoreCase("GSAD")) {
			helpContents = helpContentService.getHelpContentByUserContext(0L, 0L, 0L, includeContent, helpTopicId);
		} else {
			Long stateId = getUserStateId();
			helpContents = helpContentService.getHelpContentByUserContext(user.getCurrentAssessmentProgramId(),
					user.getCurrentGroupsId(), stateId, includeContent, helpTopicId);
		}

		return helpContents;
	}

	@RequestMapping(value = "getHelpContentsByHelpTopicId.htm", method = RequestMethod.GET)
	private @ResponseBody String getHelpContentByHTid(@RequestParam(value = "id", required = true) Long id) {

		try {
			LOGGER.info("--> getHelpContentsByHelpTopicId.htm ");
			User user = getContextUser();
			Map<String, Object> model = new HashMap<String, Object>();
			ObjectMapper mapper = getObjectMapper();

			List<HelpContent> helpContents = getHelpContentsByHelpTopicId(user, id);

			model.put("status", "success");
			model.put("helpContents", helpContents);

			String modelJson = mapper.writeValueAsString(model);
			LOGGER.debug("<-- getHelpContentsByHelpTopicId.htm");
			return modelJson;
		} catch (Exception e) {

			LOGGER.error("Exception occurred while getting States: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}
}
