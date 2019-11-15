package edu.ku.cete.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.api.EnrollmentAPIObject;
import edu.ku.cete.domain.api.OrganizationAPIObject;
import edu.ku.cete.domain.api.RosterAPIObject;
import edu.ku.cete.domain.api.StudentAPIObject;
import edu.ku.cete.domain.api.UserAPIObject;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.report.domain.APIRequest;
import edu.ku.cete.service.api.StudentAPIService;
import edu.ku.cete.service.api.UserAPIService;
import edu.ku.cete.service.api.RosterAPIService;
import edu.ku.cete.service.api.exception.APIRuntimeException;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.GetApiService;
import edu.ku.cete.service.api.APIDashboardErrorService;
import edu.ku.cete.service.api.ApiRecordTypeEnum;
import edu.ku.cete.service.api.ApiRequestTypeEnum;
import edu.ku.cete.service.api.EnrollmentAPIService;
import edu.ku.cete.service.api.OrganizationAPIService;
import edu.ku.cete.service.impl.api.APIHash;
import edu.ku.cete.service.report.APIRequestService;
import edu.ku.cete.util.CommonConstants;

@RestController
@RequestMapping("/api")
public class APIController {
	private Logger logger = LoggerFactory.getLogger(APIController.class);
	
	@Autowired
	protected StudentAPIService studentAPIService;
	
	@Autowired
	protected UserAPIService userAPIService;

	@Autowired
	protected RosterAPIService rosterAPIService; 
	
	@Autowired
	protected EnrollmentAPIService enrollmentAPIService; 
	
	@Autowired
	protected OrganizationAPIService organizationAPIService;
	
	@Autowired
	protected AppConfigurationService appConfigService;
	
	@Autowired
	protected APIDashboardErrorService apiErrorService;
	
	@Autowired
	protected GetApiService getApiService;
	
	@Autowired APIRequestService apiRequestService;
	
	protected Map<String, Object> getAPIConfigData(String appId, String apiKey) {
		Map<String, Object> config = new HashMap<String, Object>();
		
		// if the API key or App ID are blank, we can't really do much
		if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(apiKey)) {
			List<AppConfiguration> appIds = appConfigService.selectByAttributeTypeAndAttributeValue(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID, appId);
			List<AppConfiguration> apiKeys = appConfigService.selectByAttributeTypeAndAttributeValue(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY, apiKey);
			if (CollectionUtils.isNotEmpty(appIds) && CollectionUtils.isNotEmpty(apiKeys)) {
				AppConfiguration appIdConfig = appIds.get(0);
				AppConfiguration apiKeyConfig = apiKeys.get(0);
				config.put(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID, appIdConfig);
				config.put(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY, apiKeyConfig);
			if (appIdConfig.getAssessmentProgramId() == null) {
					logger.error("APP ID's assessmentprogramid is null from the database, this signifies an error in configuration");
				}
				if (apiKeyConfig.getAssessmentProgramId() == null) {
					logger.error("API KEY's assessmentprogramid is null from the database, this signifies an error in configuration");
				}
				
				Long assessmentProgramId = appIdConfig.getAssessmentProgramId() != null ?
					appIdConfig.getAssessmentProgramId() : apiKeyConfig.getAssessmentProgramId();
				config.put("assessmentProgramId", assessmentProgramId);
				
				List<AppConfiguration> apiUsers = appConfigService.selectByAttributeTypeAndAssessmentProgramId(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER, assessmentProgramId);
				if (CollectionUtils.isNotEmpty(apiUsers)) {
					config.put(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER, apiUsers.get(0));
				} else {
					logger.error("API User ID not specified, this signifies an error in configuration");
				}
				
				List<AppConfiguration> checkHashes = appConfigService.selectByAttributeTypeAndAssessmentProgramId(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES, assessmentProgramId);
				if (CollectionUtils.isNotEmpty(checkHashes)) {
					config.put(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES, StringUtils.equalsIgnoreCase("true", checkHashes.get(0).getAttributeValue()));
				} else {
					logger.error("Hash check property for program's API not specified, this signifies an error in configuration");
				}
			}
		}
		return config;
	}      
	
	protected boolean isValidAPIAppConfig(Map<String, Object> config) {
		boolean valid = true;
		String[] necessaryKeys = {
			CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID,
			CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY,
			CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER,
			CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES,
			"assessmentProgramId"
		};
		for (int x = 0; x < necessaryKeys.length && valid; x++) {
			valid = valid && config.containsKey(necessaryKeys[x]);
			if(!valid) {
				break;
			}
		}
		return valid;
	}
	
	protected boolean isValidRequestHash(HttpServletRequest request, String body) {
		String headerHash = request.getHeader(CommonConstants.API_HEADER_CHECKSUM);
		String bodyHash = body == null ? null : APIHash.SHA256.checksumToHex(body, "UTF-8");
		logger.debug("hash from header = " + headerHash + " -- hash of body = " + bodyHash);
		return headerHash != null && bodyHash != null && headerHash.equalsIgnoreCase(bodyHash);
	}
	
	protected void populateResponse(Map<String, Object> responseMap, HttpStatus status) {
		populateResponse(responseMap, status, "");
	}

	protected void populateResponse(Map<String, Object> responseMap, HttpStatus status, String msg) {
		responseMap.put("message", msg);
		responseMap.put("code", status.value());
		responseMap.put("type", status.name());
	}
	
	protected <T> void populateGetResponse(Map<String, Object> responseMap, List<T> data, String msg, HttpStatus status) {
		responseMap.put("count", data.size());
		responseMap.put("data", data);
		responseMap.put("message", msg);
		responseMap.put("status", status.value());
		
	}
	
	/**
	 * Note, this method should only be called once as it does not consider errors already added. Do this at the end of a request.
	 * @param responseMap
	 * @param errors
	 */
	protected void populateResponseErrors(Map<String, Object> responseMap, List<APIDashboardError> errors) {
		ObjectMapper objectMapper = new ObjectMapper();
		ArrayNode errorsJsonArray = objectMapper.createArrayNode();
		if (CollectionUtils.isNotEmpty(errors)) {
			for (APIDashboardError error : errors) {
				errorsJsonArray.add(objectMapper.createObjectNode()
					.put("uniqueId", error.getExternalID())
					.put("message", error.getMessage()));
			}
		}
		responseMap.put("errors", errorsJsonArray);
	}
	
	/**
	 * Attempt to convert a JSON request body into a list of POJOs.
	 * @author Brad Koelper
	 * @param body The body to convert
	 * @param type The class type that the result should be
	 * @return List of the specified type, parsed from the JSON.
	 * <br/>
	 * Note: No exceptions will be thrown from this method, but an exception can occur, it will just be logged and swallowed.
	 * The returned list will be <code>null</code> in the case of an exception.
	 */
	protected <T> List<T> convertBodyToObjects(String body, Class<T> type) {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
		List<T> objects = null;
		try {
			objects = objectMapper.readValue(body, objectMapper.getTypeFactory().constructCollectionType(List.class, type));
		} catch (Exception e) {
			logger.error("Error reading body JSON into list of POJOs:", e);
		}
		return objects;
	}
	
	/**
	 * @author Brad Koelper
	 * @return 200 if the request has a valid configuration. 401 if the API key or App ID is wrong. 500 if some other configuration error is encountered.
	 */
	@RequestMapping(value = "/ping")
	public Map<String, Object> ping(HttpServletRequest request, HttpServletResponse response,
			@RequestHeader HttpHeaders headers,
			@RequestBody String body) {
		APIRequest apiRequest = generateAPIRequestRecord("PING", request, headers, body);
		apiRequestService.insert(apiRequest);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		HttpStatus responseStatus = HttpStatus.OK;
		String responseMessage = "";
		
		Map<String, Object> config = getAPIConfigData(request.getHeader(CommonConstants.API_HEADER_APPID), request.getHeader(CommonConstants.API_HEADER_APIKEY));
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				responseStatus = HttpStatus.UNAUTHORIZED;
			} else {
				responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				responseMessage = "Invalid configuration.";
			} 
		}
		
		response.setStatus(responseStatus.value());
		populateResponse(responseMap, responseStatus, responseMessage);
		completeAPIRequestRecord(apiRequest, response, responseMap);  
		return responseMap;
	}  
	
	/**
	 * Perform crazy things that PLTW couldn't be bothered to incorporate, such as CS -> SEC course code mapping
	 * Not needed after roster and enrollment APIs added, but leaving for now because PLTW is fickle.
	 * Consider removing in a year....
	 * @author Brad Koelper
	 * @param obj The object to update
	 */
	@SuppressWarnings("unused")
	private void performCrazyNecessaryUpdates(StudentAPIObject obj) {
		/*
		if (obj != null && obj.getCourseCode() != null && obj.getCourseCode().length > 0) {
			for (int x = 0; x < obj.getCourseCode().length; x++) {
				if (StringUtils.equals("CS", obj.getCourseCode()[x])) {
					obj.getCourseCode()[x] = "SEC";
				}
			}
		}
		*/
	}
	
	/**
	 * @author Brad Koelper
	 * @param requestType the request type (student, user, org, something similar)
	 * @param request the request
	 * @param headers the headers
	 * @param body the request body
	 */
	private APIRequest generateAPIRequestRecord(String requestType, HttpServletRequest request, HttpHeaders headers, String body) {
		APIRequest apiRequest = new APIRequest();
		apiRequest.setReceivedTime(new Date());
		apiRequest.setRequestType(requestType);
		apiRequest.setMethod(request.getMethod().toUpperCase());
		apiRequest.setEndpoint(request.getServletPath());
		apiRequest.setHeaders(headers.toString());
		apiRequest.setBody(body);
		
		return apiRequest;
	}
	
	/**
	 * Converts the object to a JSON representation
	 * @param obj the object to convert
	 * @return
	 */
	private String objToJSONString(Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (JsonProcessingException jpe) {
			logger.error("JSON processing exception:", jpe);
		}
		return null;
	}
	
	private void completeAPIRequestRecord(APIRequest apiRequest, HttpServletResponse response, Map<String, Object> responseContentObject) {
		apiRequest.setResponse(objToJSONString(responseContentObject));
		apiRequest.setResponseCode(response.getStatus());
		apiRequest.setResponseTime(new Date());
		apiRequestService.updateByPrimaryKey(apiRequest);
	}
	
	/**
	 * @author Brad Koelper
	 * @param request
	 * @param response
	 * @param body
	 * @return A JSON body consisting of three properties: "type", "code", and "message"
	 */
	@RequestMapping(
		value = "/students",
		method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
		consumes = {MediaType.APPLICATION_JSON_VALUE},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@SuppressWarnings("unchecked")
	public Map<String, Object> students(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers, @RequestBody String body) {
		APIRequest apiRequest = generateAPIRequestRecord("STUDENT", request, headers, body);
		apiRequestService.insert(apiRequest);
		
		Map<String, Object> requestResponseMap = new HashMap<String, Object>();
		
		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(requestResponseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(requestResponseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, requestResponseMap);
			return requestResponseMap;
		}  
		
		//AppConfiguration appIdConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_APP_ID);
		//AppConfiguration apiKeyConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_API_KEY);
		AppConfiguration apiUser = (AppConfiguration) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER);
		Long assessmentProgramId = (Long) config.get("assessmentProgramId");
		Long userId = Long.parseLong(apiUser.getAttributeValue());
		Boolean checkHashes = Boolean.TRUE.equals((Boolean) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES));
		
		// compare hashes and error if necessary
		if (checkHashes && !isValidRequestHash(request, body)) {
			logger.error("/students -- Hashes did not match");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			populateResponse(requestResponseMap, HttpStatus.BAD_REQUEST, "Hashes did not match");
			return requestResponseMap;
		}
		
		List<StudentAPIObject> studentAPIObjects = convertBodyToObjects(body, StudentAPIObject.class);
		if (studentAPIObjects == null) {
			// an error happened
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			populateResponse(requestResponseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (CollectionUtils.isEmpty(studentAPIObjects)) {
			// there were just no objects
			response.setStatus(HttpStatus.NO_CONTENT.value());
			populateResponse(requestResponseMap, HttpStatus.NO_CONTENT);
		} else {
			List<APIDashboardError> allErrors = new ArrayList<APIDashboardError>();
			boolean exceptionOccurred = false;
			boolean errorEncountered = false;
			final String requestMethod = request.getMethod().toUpperCase();
			ApiRequestTypeEnum methodEnum = null;
			switch (requestMethod) {
				case "POST": methodEnum = ApiRequestTypeEnum.POST; break;
				case "PUT": methodEnum = ApiRequestTypeEnum.PUT; break;
				case "DELETE": methodEnum = ApiRequestTypeEnum.DELETE; break;
				default: break;
			}
			try {
				for (StudentAPIObject studentAPIObject : studentAPIObjects) {
					Map<String, Object> validationResult = studentAPIService.validateStudentAPIObject(methodEnum, studentAPIObject);
					List<String> validationErrors = (List<String>) validationResult.get("errors");
					if (CollectionUtils.isNotEmpty(validationErrors)) {
						errorEncountered = true;
						logger.info(new StringBuilder()
							.append("/students -- Student API Object with uniqueStudentId \"")
							.append(studentAPIObject.getUniqueStudentId())
							.append("\" did not pass validation, skipping...")
							.toString()
						);
						List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
						for (String validationError : validationErrors) {
							errors.add(apiErrorService.buildDashboardError(
								methodEnum,
								ApiRecordTypeEnum.STUDENT,
								studentAPIObject.getUniqueStudentId(),
								new StringBuilder().append(studentAPIObject.getFirstName()).append(" ").append(studentAPIObject.getLastName()).toString(),
								null,
								null,
								userId,
								validationError
							));
						}
						apiErrorService.insertErrors(errors);
						allErrors.addAll(errors);
					} else {
						//performCrazyNecessaryUpdates(studentAPIObject);
						Map<String, Object> serviceResponse = new HashMap<String, Object>();
						try {
							switch (requestMethod) {
								case "POST":
									serviceResponse = studentAPIService.postStudent(serviceResponse, studentAPIObject, assessmentProgramId, userId);
									break;
								case "PUT":
									serviceResponse = studentAPIService.putStudent(serviceResponse, studentAPIObject, assessmentProgramId, userId);
									break;
								case "DELETE":
									serviceResponse = studentAPIService.deleteStudent(serviceResponse, studentAPIObject, assessmentProgramId, userId);
									break;
								default: break;
							}
						} catch (APIRuntimeException apire) {
							// this isn't the most elegant exception handling, but it's mostly for the rollback
							logger.debug("APIRuntimeException:", apire);
						}
						List<APIDashboardError> errors = (List<APIDashboardError>) serviceResponse.get("errors");
						if (CollectionUtils.isNotEmpty(errors)) {
							allErrors.addAll(errors);
						}
						if (serviceResponse != null && Boolean.FALSE.equals((Boolean) serviceResponse.get("success"))) {
							errorEncountered = true;
						}
					}
				}
			} catch (Exception e) {
				exceptionOccurred = true;
				logger.error("Error in /students (" + requestMethod + "):", e);
			}
			
			apiErrorService.insertErrors(allErrors);
			populateResponseErrors(requestResponseMap, allErrors);
			
			HttpStatus responseStatus = requestMethod.equals("POST") ? HttpStatus.CREATED : HttpStatus.OK;
			// if we had an exception, the response should be internal server error
			if (exceptionOccurred) {
				responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			} else if (errorEncountered) {
				responseStatus = HttpStatus.BAD_REQUEST;
			}
			response.setStatus(responseStatus.value());
			populateResponse(requestResponseMap, responseStatus);
		}
		
		completeAPIRequestRecord(apiRequest, response, requestResponseMap);
		return requestResponseMap;
	}
	
	/**
	 * @author Osman Mohammed
	 * @param request
	 * @param response
	 * @param body
	 * @return A JSON body consisting of three properties: "type", "code", and "message"
	 */
	
	@RequestMapping(
			value = "/rosters",
			method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
			consumes = {MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_JSON_VALUE}
			)
	@SuppressWarnings("unchecked")
	public Map<String, Object> rosters(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers, @RequestBody String body) {
		APIRequest apiRequest = generateAPIRequestRecord("ROSTER", request, headers, body);
		apiRequestService.insert(apiRequest);

		Map<String, Object> requestResponseMap = new HashMap<String, Object>();

		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);

		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(requestResponseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(requestResponseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, requestResponseMap);
			return requestResponseMap;
		}  

		AppConfiguration apiUser = (AppConfiguration) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER);
		Long assessmentProgramId = (Long) config.get("assessmentProgramId");
		Long userId = Long.parseLong(apiUser.getAttributeValue());
		Boolean checkHashes = Boolean.TRUE.equals((Boolean) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES));

		// compare hashes and error if necessary
		if (checkHashes && !isValidRequestHash(request, body)) {
			logger.error("/rosters -- Hashes did not match");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			populateResponse(requestResponseMap, HttpStatus.BAD_REQUEST, "Hashes did not match");
			return requestResponseMap;
		} 

		List<RosterAPIObject> rosterAPIObjects = convertBodyToObjects(body, RosterAPIObject.class);
		if (rosterAPIObjects == null) {
			// an error happened
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			populateResponse(requestResponseMap, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (CollectionUtils.isEmpty(rosterAPIObjects)) {
			// there were just no objects
			response.setStatus(HttpStatus.NO_CONTENT.value());
			populateResponse(requestResponseMap, HttpStatus.NO_CONTENT);
		} else {
			List<APIDashboardError> allErrors = new ArrayList<APIDashboardError>();
			boolean exceptionOccurred = false;
			boolean errorEncountered = false;
			final String requestMethod = request.getMethod().toUpperCase();
			ApiRequestTypeEnum methodEnum = null;
			switch (requestMethod) {
			case "POST": methodEnum = ApiRequestTypeEnum.POST; break;
			case "PUT": methodEnum = ApiRequestTypeEnum.PUT; break;
			case "DELETE": methodEnum = ApiRequestTypeEnum.DELETE; break;
			default: break;
			}
			try {
				for(RosterAPIObject rosterAPIObject : rosterAPIObjects) {
					Map<String, Object> validationResult = rosterAPIService.validateRosterAPIObject(methodEnum, rosterAPIObject);
					List<String> validationErrors = (List<String>) validationResult.get("errors");
					if (CollectionUtils.isNotEmpty(validationErrors)) {
						errorEncountered = true;
						logger.info(new StringBuilder()
								.append("/rosters -- Roster API Object with classroomId \"")
								.append(rosterAPIObject.getClassroomId())
								.append("\" did not pass validation, skipping...")
								.toString()
								);
						List<APIDashboardError> errors = new ArrayList<APIDashboardError>();
						for (String validationError : validationErrors) {
							errors.add(apiErrorService.buildDashboardError(
									methodEnum,
									ApiRecordTypeEnum.ROSTER,
									rosterAPIObject.getEducatorUniqueId(),
									null,
									null,
									rosterAPIObject.getClassroomId(),
									userId,
									validationError
									));
						}
						allErrors.addAll(errors);
					}
					else {
						Map<String, Object> serviceResponse = new HashMap<String, Object>();
						try {
							switch (requestMethod) {
							case "POST":
								serviceResponse = rosterAPIService.postRoster(serviceResponse, rosterAPIObject, assessmentProgramId, userId);
								break;
							case "PUT":
								serviceResponse = rosterAPIService.putRoster(serviceResponse, rosterAPIObject, assessmentProgramId, userId);
								break;
							case "DELETE":
								serviceResponse = rosterAPIService.deleteRoster(serviceResponse, rosterAPIObject, assessmentProgramId, userId);
								break;
							default: break;
							}
						} catch (APIRuntimeException apire) {
							// exception handling for the roll-back
							logger.debug("APIRuntimeException:", apire);
						}
						List<APIDashboardError> errors = (List<APIDashboardError>) serviceResponse.get("errors");
						if (CollectionUtils.isNotEmpty(errors)) {
							allErrors.addAll(errors);
						}
						if (serviceResponse != null && Boolean.FALSE.equals((Boolean) serviceResponse.get("success"))) {
							errorEncountered = true;
						}
					}
				}
			} catch (Exception e) {
				exceptionOccurred = true;
				logger.error("Error in /rosters (" + requestMethod + "):", e);
			}
			apiErrorService.insertErrors(allErrors);
			populateResponseErrors(requestResponseMap, allErrors);

			HttpStatus responseStatus = requestMethod.equals("POST") ? HttpStatus.CREATED : HttpStatus.OK;
			// if we had an exception, the response should be internal server error
			if (exceptionOccurred) {
				responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
			} else if (errorEncountered) {
				responseStatus = HttpStatus.BAD_REQUEST;
			}
			response.setStatus(responseStatus.value());
			populateResponse(requestResponseMap, responseStatus);
		}

		completeAPIRequestRecord(apiRequest, response, requestResponseMap);
		return requestResponseMap;
	}
	
	
	@RequestMapping(
			value = "/users",
				method = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
				consumes = {MediaType.APPLICATION_JSON_VALUE},
				produces = {MediaType.APPLICATION_JSON_VALUE}
		)
		public Map<String, Object> users(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers, @RequestBody String body) {
			APIRequest apiRequest = generateAPIRequestRecord("USER", request, headers, body);
			apiRequestService.insert(apiRequest);
			
			Map<String, Object> responseMap = new HashMap<String, Object>();
			
			String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
			String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
			Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
			
			if (!isValidAPIAppConfig(config)) {
				if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
					response.setStatus(HttpStatus.UNAUTHORIZED.value());
					populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
				} else {
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
					populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				completeAPIRequestRecord(apiRequest, response, responseMap);
				return responseMap;
			}
			
			//AppConfiguration appIdConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_APP_ID);
			//AppConfiguration apiKeyConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_API_KEY);
			AppConfiguration apiUser = (AppConfiguration) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER);
			Long assessmentProgramId = (Long) config.get("assessmentProgramId");
			Long userId = Long.parseLong(apiUser.getAttributeValue());
			Boolean checkHashes = Boolean.TRUE.equals((Boolean) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES));
			
			if (!checkHashes || isValidRequestHash(request, body)) {
				List<UserAPIObject> userAPIObj = convertBodyToObjects(body, UserAPIObject.class);
				if(userAPIObj == null) {
					response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
					populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
				}else if (CollectionUtils.isEmpty(userAPIObj)) {
					// there were just no objects
					response.setStatus(HttpStatus.NO_CONTENT.value());
					populateResponse(responseMap, HttpStatus.NO_CONTENT);
				} else {
					for(UserAPIObject uao : userAPIObj) {
						List<String> cleanedAccreditations = new ArrayList<String>();
						if(CollectionUtils.isNotEmpty(uao.getCourseAccreditation())) {
							for(String ca : uao.getCourseAccreditation()) {
								if(StringUtils.isNotEmpty(ca)) {
									// woo CS mapping!
									if ("CS".equals(ca)) {
										cleanedAccreditations.add("SEC");
									} else {
										cleanedAccreditations.add(ca);
									}
								}
							}
							uao.setCourseAccreditation(cleanedAccreditations);
						}
					}
					Map<String, Object> returnValues = new HashMap<String, Object>();
					List<APIDashboardError> apes = new ArrayList<APIDashboardError>();
					Boolean success = true;
					switch (request.getMethod()) {
					case "POST":
						for(UserAPIObject uao : userAPIObj) {
							try{
								returnValues = userAPIService.postUser(uao, assessmentProgramId, userId);
							}catch(APIRuntimeException e) {
								String name = uao.getFirstName() + " " + uao.getLastName();
								List<Organization> orgs = userAPIService.rolesToOrgsList(uao.getRoles());
								List<String> messages = new ArrayList<String>();
								messages.add(e.getMessage());
								apes.addAll(userAPIService.createUserErrorRecords(ApiRequestTypeEnum.POST, ApiRecordTypeEnum.USER, 
										uao.getUniqueId(), name, orgs, userId, messages));
								success = false;
								returnValues.put("success", false);
								returnValues.put("message", e.getMessage());
								returnValues.put("errors", apes);
							}
							if(!returnValues.containsKey("success") || (Boolean)returnValues.get("success") == false) {
								List<String> messages = new ArrayList<String>();
								messages.addAll(userAPIService.getUserErrorMessages(returnValues));
								String name = uao.getFirstName() + " " + uao.getLastName();
								List<Organization> orgs = userAPIService.rolesToOrgsList(uao.getRoles());
								apes.addAll(userAPIService.createUserErrorRecords(ApiRequestTypeEnum.POST, ApiRecordTypeEnum.USER, 
										uao.getUniqueId(), name, orgs, userId, messages));
								success = false;
								returnValues.put("success", false);
								returnValues.put("messages", messages);
								returnValues.put("errors", apes);
							}
						}
						if(success) {
							response.setStatus(HttpStatus.CREATED.value());
							populateResponse(responseMap, HttpStatus.CREATED);
						}else {
							response.setStatus(HttpStatus.BAD_REQUEST.value());
							populateResponse(responseMap, HttpStatus.BAD_REQUEST);
						}
						break;
					case "PUT":
						for(UserAPIObject uao : userAPIObj) {
							try{
								returnValues = userAPIService.putUser(uao, assessmentProgramId, userId);
							}catch(APIRuntimeException e) {
								String name = uao.getFirstName() + " " + uao.getLastName();
								List<Organization> orgs = userAPIService.rolesToOrgsList(uao.getRoles());
								List<String> messages = new ArrayList<String>();
								messages.add(e.getMessage());
								apes.addAll(userAPIService.createUserErrorRecords(ApiRequestTypeEnum.PUT, ApiRecordTypeEnum.USER, 
										uao.getUniqueId(), name, orgs, userId, messages));
								success = false;
								returnValues.put("success", false);
								returnValues.put("message", e.getMessage());
								returnValues.put("errors", apes);
							}
							if(!returnValues.containsKey("success") || (Boolean)returnValues.get("success") == false) {
								List<String> messages = new ArrayList<String>();
								messages.addAll(userAPIService.getUserErrorMessages(returnValues));
								String name = uao.getFirstName() + " " + uao.getLastName();
								success = false;
								List<Organization> orgs = userAPIService.rolesToOrgsList(uao.getRoles());
								apes = userAPIService.createUserErrorRecords(ApiRequestTypeEnum.PUT, ApiRecordTypeEnum.USER, 
											uao.getUniqueId(), name, orgs, userId, messages);
							}
						}
						if(success) {
							response.setStatus(HttpStatus.OK.value());
							populateResponse(responseMap, HttpStatus.OK);
						}else {
							response.setStatus(HttpStatus.BAD_REQUEST.value());
							populateResponse(responseMap, HttpStatus.BAD_REQUEST);
						}
						break;
					case "DELETE":
						returnValues = userAPIService.deleteUsers(userAPIObj, assessmentProgramId, userId);
						response.setStatus(HttpStatus.OK.value());
						populateResponse(responseMap, HttpStatus.OK);
						break;
					default: // shouldn't ever happen, as long as we keep the accepted methods and cases
								// consistent
						response.setStatus(HttpStatus.OK.value());
						populateResponse(responseMap, HttpStatus.OK);
						break;
					}
					
					if(CollectionUtils.isNotEmpty(apes)) {
						apiErrorService.insertErrors(apes);
						populateResponseErrors(responseMap, apes);
					}
					
				}
			}else {
				logger.error("/users -- Hashes did not match");
				response.setStatus(HttpStatus.BAD_REQUEST.value());
				populateResponse(responseMap, HttpStatus.BAD_REQUEST, "Hashes did not match");
			}
			
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}

	@RequestMapping(value = "/organizations", 
			method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }, 
			consumes = {MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> organizations(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers,
			@RequestBody String body) {
		APIRequest apiRequest = generateAPIRequestRecord("ORGANIZATION", request, headers, body);
		apiRequestService.insert(apiRequest);
		
		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
		int totalRecords=0;
		int errorCount=0;
		String msg="";
		HttpStatus responseStatus=null;
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}
		
		//AppConfiguration appIdConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_APP_ID);
		//AppConfiguration apiKeyConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_API_KEY);
		AppConfiguration apiUser = (AppConfiguration) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER);
		Long assessmentProgramId = (Long) config.get("assessmentProgramId");
		Long userId = Long.parseLong(apiUser.getAttributeValue());
		Boolean checkHashes = Boolean.TRUE.equals((Boolean) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES));
		
		if (!checkHashes || isValidRequestHash(request, body)) {
			List<OrganizationAPIObject> organizationAPIObjects = convertBodyToObjects(body, OrganizationAPIObject.class);
			
			if (organizationAPIObjects == null) {
				// an error happened
				msg="Please verify the request body, not able to get the org object.";
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR,msg);
			} else if (CollectionUtils.isEmpty(organizationAPIObjects)) {
				// there were just no objects
				msg="No organization json objects found in the request body.";
				response.setStatus(HttpStatus.NO_CONTENT.value());
				populateResponse(responseMap, HttpStatus.NO_CONTENT,msg);
			} else {
				List<APIDashboardError> allErrors = new ArrayList<APIDashboardError>();
				boolean exceptionOccurred = false;
				boolean errorEncountered = false;
				final String requestMethod = request.getMethod().toUpperCase();
				totalRecords=organizationAPIObjects.size();
				try {
					for (OrganizationAPIObject organizationAPIObj : organizationAPIObjects) {
						Map<String, Object> serviceResponse = new HashMap<String, Object>();
						organizationAPIObj.setUserID(userId);
						organizationAPIObj.setAssessmentProgramId(assessmentProgramId);
						try {
							switch (request.getMethod()) {
								case "POST":
									serviceResponse = organizationAPIService.postOrganization(serviceResponse, organizationAPIObj);
									responseStatus = HttpStatus.CREATED;
									break;
								case "PUT":
									serviceResponse = organizationAPIService.putOrganization(serviceResponse, organizationAPIObj);
									responseStatus=HttpStatus.OK;
									break;
								case "DELETE":
									serviceResponse = organizationAPIService.deleteOrganization(serviceResponse, organizationAPIObj);
									responseStatus=HttpStatus.OK;
									break;
								default: // shouldn't ever happen, as long as we keep the accepted methods and cases
											// consistent
									errorEncountered=true;
									responseStatus = HttpStatus.OK;
									break;
							}
						} catch (APIRuntimeException apire) {
							// this isn't the most elegant exception handling, but it's mostly for the rollback
							logger.debug("APIRuntimeException:", apire);
							errorEncountered = true;
						}
						List<APIDashboardError> errors =  new ArrayList<APIDashboardError> ();
						APIDashboardError error = (APIDashboardError)serviceResponse.get("error");
						if(error!=null) {
							errors.add(error);
						}
						if (CollectionUtils.isNotEmpty(errors)) {
							allErrors.addAll(errors);
						}
						if (serviceResponse != null && Boolean.FALSE.equals((Boolean) serviceResponse.get("success"))) {
							errorEncountered = true;
							errorCount++;
						}
					}
				}catch (Exception e) {
					exceptionOccurred = true;
					logger.error("Error in /organizations (" + requestMethod + "):", e);
				}
				
				apiErrorService.insertErrors(allErrors);
				populateResponseErrors(responseMap, allErrors);
				
				if(responseStatus==null)
					responseStatus = HttpStatus.OK;
				// if we had an exception, the response should be internal server error
				if (exceptionOccurred) {
					responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				} else if (errorEncountered) {
					responseStatus = HttpStatus.BAD_REQUEST;
					msg="Number of record Successfully Processed: "+(totalRecords-errorCount)+". Number of errored record: "+errorCount;
				}else {
					msg="Number of record Successfully Processed: "+(totalRecords-errorCount);
				}
				response.setStatus(responseStatus.value());
				populateResponse(responseMap, responseStatus,msg);
			}
		} else {
			logger.error("/organizations -- Hashes did not match");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			populateResponse(responseMap, HttpStatus.BAD_REQUEST, "Hashes did not match");
		}
		
		completeAPIRequestRecord(apiRequest, response, responseMap);
		return responseMap;
	}
		

	/**
	 * @author Vidya Nakade
	 * @param EnrollmentAPIObject 
	 * @return A JSON body consisting of response status and response messages
	 */
	@RequestMapping(value = "/enrollments", 
			method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE }, 
			consumes = {MediaType.APPLICATION_JSON_VALUE }, 
			produces = { MediaType.APPLICATION_JSON_VALUE })
	public Map<String, Object> enrollments(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers,
			@RequestBody String body) {
		APIRequest apiRequest = generateAPIRequestRecord("ENROLLMENT", request, headers, body);
		apiRequestService.insert(apiRequest);
		
		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
		int totalRecords=0;
		int errorCount=0;
		String msg="";
		HttpStatus responseStatus=null;
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || 
					!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}
		
		//AppConfiguration appIdConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_APP_ID);
		//AppConfiguration apiKeyConfig = (AppConfiguration) config.get(CONFIG_ATTRIBUTE_TYPE_API_KEY);
		AppConfiguration apiUser = (AppConfiguration) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_USER);
		Long userId = Long.parseLong(apiUser.getAttributeValue());
		Boolean checkHashes = Boolean.TRUE.equals((Boolean) config.get(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES));
		
		if (!checkHashes || isValidRequestHash(request, body)) {
			List<EnrollmentAPIObject> enrollmentAPIObjects = convertBodyToObjects(body, EnrollmentAPIObject.class);
			
			if (enrollmentAPIObjects == null) {
				// an error happened
				msg="Please verify the request body, not able to get the enrollment object.";
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR,msg);
			} else if (CollectionUtils.isEmpty(enrollmentAPIObjects)) {
				// there were just no objects
				msg="No enrollment json objects found in the request body.";
				response.setStatus(HttpStatus.NO_CONTENT.value());
				populateResponse(responseMap, HttpStatus.NO_CONTENT,msg);
			} else {
				List<APIDashboardError> allErrors = new ArrayList<APIDashboardError>();
				boolean exceptionOccurred = false;
				boolean errorEncountered = false;
				final String requestMethod = request.getMethod().toUpperCase();
				totalRecords=enrollmentAPIObjects.size();
				try {
					for (EnrollmentAPIObject enrollmentAPIObject : enrollmentAPIObjects) {
						Map<String, Object> serviceResponse = new HashMap<String, Object>();
						try {
							switch (request.getMethod()) {
								case "POST":
									serviceResponse = enrollmentAPIService.postEnrollment(serviceResponse, enrollmentAPIObject,userId);
									responseStatus = HttpStatus.CREATED;
									break;
								case "PUT":
									serviceResponse = enrollmentAPIService.putEnrollment(serviceResponse, enrollmentAPIObject,userId);
									responseStatus=HttpStatus.OK;
									break;
								case "DELETE":
									serviceResponse = enrollmentAPIService.deleteEnrollment(serviceResponse, enrollmentAPIObject,userId);
									responseStatus=HttpStatus.OK;
									break;
								default: 
									errorEncountered=true;
									responseStatus = HttpStatus.BAD_REQUEST;
									msg="Method type not supported. Supported method types are: POST Or PUT Or DELETE";
									break;
							}
						} catch (APIRuntimeException apire) {
							// this isn't the most elegant exception handling, but it's mostly for the rollback
							logger.debug("APIRuntimeException:", apire);
							errorEncountered = true;
						}
						APIDashboardError error = (APIDashboardError)serviceResponse.get("error");
						
						if (error!=null) {
							allErrors.add(error);
						}
						
						if (serviceResponse != null && Boolean.FALSE.equals((Boolean) serviceResponse.get("success"))) {
							errorEncountered = true;
							errorCount++;
						}
					}
				}catch (Exception e) {
					exceptionOccurred = true;
					logger.error("Error in /enrollments (" + requestMethod + "):", e);
				}
				
				apiErrorService.insertErrors(allErrors);
				populateResponseErrors(responseMap, allErrors);
				
				if(responseStatus==null)
					responseStatus = HttpStatus.OK;
				// if we had an exception, the response should be internal server error
				if (exceptionOccurred) {
					responseStatus = HttpStatus.INTERNAL_SERVER_ERROR;
				} else if (errorEncountered) {
					responseStatus = HttpStatus.BAD_REQUEST;
					msg="Number of record Successfully Processed: "+(totalRecords-errorCount)+". Number of errored record: "+errorCount;
				}else {
					msg="Number of record Successfully Processed: "+(totalRecords-errorCount);
				}
				response.setStatus(responseStatus.value());
				populateResponse(responseMap, responseStatus,msg);
			}
		} else {
			logger.error("/enrollments -- Hashes did not match");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			populateResponse(responseMap, HttpStatus.BAD_REQUEST, "Hashes did not match");
		}
		
		completeAPIRequestRecord(apiRequest, response, responseMap);
		return responseMap;
	}	
	
	/**
	 * @author Nathan Tipton
	 * @param request
	 * @param response
	 * @return A JSON body consisting of three properties: "type", "code", and "message"
	 */
	@RequestMapping(
		value = "/students",
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@SuppressWarnings("unchecked")
	public Map<String, Object> getStudents(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers, @RequestParam Map<String,String> allRequestParams) {
		APIRequest apiRequest = generateAPIRequestRecord("STUDENT", request, headers, StringUtils.join(allRequestParams));
		apiRequestService.insert(apiRequest);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		if(allRequestParams.containsKey("uniqueStudentId")) {
			searchParams.put("uniqueStudentId", allRequestParams.get("uniqueStudentId"));
		}
		if(allRequestParams.containsKey("enrollmentId")) {
			if(StringUtils.isNumeric(allRequestParams.get("enrollmentId"))){
				searchParams.put("enrollmentId", Long.parseLong(allRequestParams.get("enrollmentId")));
			}else {
				responseMap = badParameterApiResponse(responseMap, 
						"enrollmentId Id is a number. Please provide a numeric value if you want to filter based on it.", 
						apiRequest, response);
				return responseMap;
			}
		}
		if(allRequestParams.containsKey("username")) {
			searchParams.put("username", allRequestParams.get("username"));
		}
		if(allRequestParams.containsKey("classroomId")) {
			if(StringUtils.isNumeric(allRequestParams.get("classroomId"))){
				searchParams.put("classroomId", Long.parseLong(allRequestParams.get("classroomId")));
			}else {
				responseMap = badParameterApiResponse(responseMap, 
						"classroomId Id is a number. Please provide a numeric value if you want to filter based on it.", 
						apiRequest, response);
				return responseMap;
			}
		}
		
		if(searchParams.isEmpty()) {
			responseMap = badParameterApiResponse(responseMap, 
					"No valid parameters. Please provide at least one parameter.", 
					apiRequest, response);
			return responseMap;
		}
		
		logger.debug("externalId: " + searchParams.get("externalId"));

		//Adding current year parameter to query
		searchParams = addCurrentYrToParams(searchParams);
		List<StudentAPIObject> students = getApiService.getStudents(searchParams);
		
		response.setStatus(HttpStatus.OK.value());
		populateGetResponse(responseMap, students, HttpStatus.OK.toString(), HttpStatus.OK);
		completeAPIRequestRecord(apiRequest, response, responseMap);
		return responseMap;
	}
	
	/**
	 * @author Vidya Nakade
	 * @param uniqueId
	 * @param type
	 * @param level
	 * @param parentId
	 * @return A JSON body consisting of OrganizationAPIObject
	 */
	@RequestMapping(
		value = "/organizations",
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@SuppressWarnings("unchecked")
	public Map<String, Object> getOrganizations(HttpServletRequest request, HttpServletResponse response,
			@RequestHeader HttpHeaders headers, @RequestParam Map<String, String> allRequestParams) {
		APIRequest apiRequest = generateAPIRequestRecord("ORGANIZATION", request, headers,
				StringUtils.join(allRequestParams));
		apiRequestService.insert(apiRequest);

		Map<String, Object> responseMap = new HashMap<String, Object>();

		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);

		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID)
					|| !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}

		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (allRequestParams.containsKey("uniqueId")) {
			searchParams.put("externalId", allRequestParams.get("uniqueId"));
		}
		if (allRequestParams.containsKey("parentId")) {
			searchParams.put("parentOrgExtId", allRequestParams.get("parentId"));
		} /*
			 * commented type as needs answer if(allRequestParams.containsKey("type")) {
			 * searchParams.put("type", allRequestParams.get("type")); }
			 */
		if (allRequestParams.containsKey("level")) {
			searchParams.put("level", allRequestParams.get("level"));
		}
		if (searchParams.isEmpty()) {
			responseMap = badParameterApiResponse(responseMap, "No valid parameters. Please provide at least one parameter.",
					apiRequest, response);
		} else {

			logger.debug("externalId: " + searchParams.get("externalId"));

			List<OrganizationAPIObject> orgs = getApiService.getOrgs(searchParams);
			response.setStatus(HttpStatus.OK.value());
			populateGetResponse(responseMap, orgs, HttpStatus.OK.toString(), HttpStatus.OK);
			completeAPIRequestRecord(apiRequest, response, responseMap);
		}

		return responseMap;
	}
	
	/**
	 * @author Vidya Nakade
	 * @param uniqueId
	 * @param email
	 * @param organizationId
	 * @param role
	 * @param credit
	 * @return A JSON body consisting of UserAPIObject
	 */
	@RequestMapping(
		value = "/users",
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@SuppressWarnings("unchecked")
	public Map<String, Object> getUsers(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers, @RequestParam Map<String,String> allRequestParams) {
		APIRequest apiRequest = generateAPIRequestRecord("USERS", request, headers, StringUtils.join(allRequestParams));
		apiRequestService.insert(apiRequest);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		boolean reqParamsPresent = false;
		if(allRequestParams.containsKey("uniqueId")) {
			reqParamsPresent = true;
			searchParams.put("externalId", allRequestParams.get("uniqueId"));
		}
		if(allRequestParams.containsKey("email")) {
			reqParamsPresent = true;
			searchParams.put("email", allRequestParams.get("email"));
		}
		if(allRequestParams.containsKey("organizationId")) {
			reqParamsPresent = true;
			searchParams.put("organizationId", allRequestParams.get("organizationId"));
		}
		if(allRequestParams.containsKey("role")) {
			searchParams.put("role", allRequestParams.get("role"));
		}
		if(allRequestParams.containsKey("credit")) {
			searchParams.put("credit", "SEC".equalsIgnoreCase(allRequestParams.get("credit")) ? "CS" : allRequestParams.get("credit") );
		}

		if (searchParams.isEmpty() || !reqParamsPresent) {
			responseMap = badParameterApiResponse(responseMap, "No valid parameters. You must provide either uniqueId or email or organizationId.",
					apiRequest, response);
		} else {
			logger.debug("externalId: " + searchParams.get("externalId"));
			List<UserAPIObject> users = getApiService.getUsers(searchParams);
			response.setStatus(HttpStatus.OK.value());
			populateGetResponse(responseMap, users, HttpStatus.OK.toString(), HttpStatus.OK);
			completeAPIRequestRecord(apiRequest, response, responseMap);
		}
		return responseMap;
	}
	
	/**
	 * @author Vidya Nakade
	 * @param uniqueId
	 * @param email
	 * @param organizationId
	 * @param role
	 * @param credit
	 * @return A JSON body consisting of RosterAPIObject
	 */
	@RequestMapping(
		value = "/rosters",
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@SuppressWarnings("unchecked")
	public Map<String, Object> getRosters(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers, @RequestParam Map<String,String> allRequestParams) {
		APIRequest apiRequest = generateAPIRequestRecord("ROSTERS", request, headers, StringUtils.join(allRequestParams));
		apiRequestService.insert(apiRequest);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		if(allRequestParams.containsKey("schoolIdentifier")) {
			searchParams.put("schoolIdentifier", allRequestParams.get("schoolIdentifier"));
		}
		if(allRequestParams.containsKey("educatorUniqueId")) {
			searchParams.put("educatorExtId", allRequestParams.get("educatorUniqueId"));
		}
		if(allRequestParams.containsKey("classroomId")) {
			if(StringUtils.isNumeric(allRequestParams.get("classroomId")))
				searchParams.put("classroomId", Long.parseLong(allRequestParams.get("classroomId")));
			else {
				responseMap = badParameterApiResponse(responseMap, 
						"classroomId Id is a number. Please provide a numeric value if you want to filter based on it.", 
						apiRequest, response);
				return responseMap;
			}	
				
		}
		if(allRequestParams.containsKey("courseCode")) {
			searchParams.put("courseCode", "SEC".equalsIgnoreCase(allRequestParams.get("courseCode")) ? "CS" : allRequestParams.get("courseCode") );
		}

		if (searchParams.isEmpty()) {
			responseMap = badParameterApiResponse(responseMap, "No valid parameters. Please provide at least one parameter.",
					apiRequest, response);
		} else {
			//Adding current year parameter to query
			searchParams = addCurrentYrToParams(searchParams);
			
			List<RosterAPIObject> rosters = getApiService.getApiRosters(searchParams);

			response.setStatus(HttpStatus.OK.value());
			populateGetResponse(responseMap, rosters, HttpStatus.OK.toString(), HttpStatus.OK);
			completeAPIRequestRecord(apiRequest, response, responseMap);
		}
		return responseMap;
	}
	
	/**
	 * @author Vidya Nakade
	 * @param uniqueId
	 * @param email
	 * @param organizationId
	 * @param role
	 * @param credit
	 * @return A JSON body consisting of EnrollmentAPIObject
	 */
	@RequestMapping(
		value = "/enrollments",
		method = {RequestMethod.GET},
		produces = {MediaType.APPLICATION_JSON_VALUE}
	)
	@SuppressWarnings("unchecked")
	public Map<String, Object> getEnrollments(HttpServletRequest request, HttpServletResponse response, @RequestHeader HttpHeaders headers, @RequestParam Map<String,String> allRequestParams) {
		APIRequest apiRequest = generateAPIRequestRecord("ENROLLMENT", request, headers, StringUtils.join(allRequestParams));
		apiRequestService.insert(apiRequest);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		
		String headerAppId = request.getHeader(CommonConstants.API_HEADER_APPID);
		String headerApiKey = request.getHeader(CommonConstants.API_HEADER_APIKEY);
		Map<String, Object> config = getAPIConfigData(headerAppId, headerApiKey);
		
		if (!isValidAPIAppConfig(config)) {
			if (!config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_APP_ID) || !config.containsKey(CommonConstants.APPCONFIG_ATTRIBUTE_TYPE_API_KEY)) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
				populateResponse(responseMap, HttpStatus.UNAUTHORIZED);
			} else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
				populateResponse(responseMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			completeAPIRequestRecord(apiRequest, response, responseMap);
			return responseMap;
		}
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		
		if(allRequestParams.containsKey("studentId")) {
			searchParams.put("studentIdExt", allRequestParams.get("studentId"));
		}
		if(allRequestParams.containsKey("enrollmentId")) {
			if(StringUtils.isNumeric(allRequestParams.get("enrollmentId")))
				searchParams.put("enrollmentIdExt", Long.parseLong(allRequestParams.get("enrollmentId")));
			else {
				responseMap = badParameterApiResponse(responseMap, 
						"Enrollment Id is a number. Please provide a numeric value if you want to filter based on it.", 
						apiRequest, response);
				return responseMap;
			}
		}
		if(allRequestParams.containsKey("classroomId")) {
			if(StringUtils.isNumeric(allRequestParams.get("classroomId")))
				searchParams.put("classroomId", Long.parseLong(allRequestParams.get("classroomId")));
			else {
				responseMap = badParameterApiResponse(responseMap, 
						"classroomId Id is a number. Please provide a numeric value if you want to filter based on it.", 
						apiRequest, response);
				return responseMap;
			}		
		}

		if (searchParams.isEmpty()) {
			responseMap = badParameterApiResponse(responseMap, "No valid parameters. Please provide at least one parameter.",
					apiRequest, response);
		} else {
			//Adding current year parameter to query
			searchParams = addCurrentYrToParams(searchParams);
			
			List<EnrollmentAPIObject> rosters = getApiService.getApiEnrollments(searchParams);

			response.setStatus(HttpStatus.OK.value());
			populateGetResponse(responseMap, rosters, HttpStatus.OK.toString(), HttpStatus.OK);
			completeAPIRequestRecord(apiRequest, response, responseMap);
		}
		return responseMap;
	}
	
	private Map<String, Object>  badParameterApiResponse(Map<String, Object>  responseMap, String responseMessage, APIRequest req,
			HttpServletResponse response){
			List<Object> emptyData = new ArrayList<Object>();
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			populateGetResponse(responseMap, emptyData, responseMessage, HttpStatus.BAD_REQUEST);
			completeAPIRequestRecord(req, response, responseMap);
					
			return responseMap;
	}
	
	//Method to add current year parameter to queries. Required for students, enrollments and rosters get methods.
	private Map<String, Object> addCurrentYrToParams(Map<String, Object> searchParams ) {

		List<AppConfiguration> appConfig = appConfigService.selectByAttributeTypeAndAssessmentProgramId(CommonConstants.CURRENT_SCHOOL_YEAR , new Long(2));
		Integer currYear = CollectionUtils.isNotEmpty(appConfig) ? 
					Integer.parseInt(appConfig.get(0).getAttributeValue()) : 0;
		searchParams.put("currentYear", currYear);
		
		return searchParams;
	}
	
}
