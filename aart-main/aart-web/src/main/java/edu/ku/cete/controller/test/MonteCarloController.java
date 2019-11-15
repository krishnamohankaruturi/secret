package edu.ku.cete.controller.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.ItiMCLog;
import edu.ku.cete.domain.StudentItemInfoForMC;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.util.DateUtil;


@Controller
@RequestMapping("montecarlo")
public class MonteCarloController {
	
	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(SetupTestSessionController.class);
    
    /**
     * iti TestSession HistoryService
     */
    @Autowired
	private ItiTestSessionService itiTestSessionService;
    
    @Autowired
	private ItiMCResponseProcessor itiMCResponseProcessor;
    /**	
    *
	* @return {@link ModelAndView}
	*
	*/
    @RequestMapping(value = "getstudentsresponsesinfo.htm", method = RequestMethod.POST)
    @ResponseBody
	public final Map<String, Object>  getStudentItemResponsesForMC(
				@RequestParam("fromdate") String fromDate,
				@RequestParam("todate") String toDate,
	    		@RequestParam("requestid") long requestId
			) {
		LOGGER.trace("Entering the getstudentsresponsesinfo in MonteCarloController for getting student responses");
    	Map<String, Object> studentInfo = new HashMap<String, Object>();
    	List<StudentItemInfoForMC>  studentInfoMC = itiTestSessionService.getStudentItemInfoForMC(DateUtil.parse(fromDate, "MMddyyyy", null), DateUtil.parse(toDate, "MMddyyyy", null));
    	studentInfo.put("data", studentInfoMC);
    	studentInfo.put("requestid", requestId);
    	studentInfo.put("fromdate", fromDate);
    	studentInfo.put("todate",toDate);
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
    	ItiMCLog itiMCLog = new ItiMCLog();
    	itiMCLog.setToDate(DateUtil.parse(toDate, "MMddyyyy", null));
    	itiMCLog.setFromDate(DateUtil.parse(fromDate, "MMddyyyy", null));
    	itiMCLog.setActionType("request");
    	itiMCLog.setRequestId(requestId);
        ObjectMapper mapper = new ObjectMapper();
        try {
            itiMCLog.setResponse(mapper.writeValueAsString(studentInfoMC));
		} catch (JsonGenerationException e) {
			LOGGER.error("Unable to convert studentInfoMC to Json object", e);
			e.printStackTrace();
		} catch (JsonMappingException e) {
			LOGGER.error("Unable to convert studentInfoMC to Json object", e);
			e.printStackTrace();
		} catch (IOException e) {
			LOGGER.error("Unable to convert studentInfoMC to Json object", e);
			e.printStackTrace();
		}  
    	itiMCLog.setCreatedUser(user.getId());
    	itiTestSessionService.addItiMClog(itiMCLog);
    	return studentInfo;
	}
    
    /**	
    *
	* @return {@link ModelAndView}
	*
	*/
    @RequestMapping(value = "studentnodeprobability.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, String> studentNodeProbabilityFromMC(
				@RequestParam("data") String data,
	    		@RequestParam("requestid") long requestId
			) {
    	Map<String, String> result = new HashMap<String, String>();
		LOGGER.debug("Entering the studentNodeProbabilitiesFromMC in MonteCarloController saving response from MC");
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userDetails.getUser();
    	ItiMCLog itiMCLog = new ItiMCLog();
    	itiMCLog.setActionType("response");
    	itiMCLog.setRequestId(requestId);
    	itiMCLog.setCreatedUser(user.getId());
    	itiMCLog.setResponse(data);
    	itiTestSessionService.addItiMClog(itiMCLog);
    	result.put("status", "Response Received");
    	result.put("requestid", Long.toString(requestId));
    	itiMCResponseProcessor.processMCResponse(data, requestId, user.getId());
    	LOGGER.debug("Completed the studentNodeProbabilitiesFromMC in MonteCarloController saving response from MC");
    	return result;
	}
     
    
}
