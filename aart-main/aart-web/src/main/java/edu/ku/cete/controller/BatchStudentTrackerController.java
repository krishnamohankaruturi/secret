/**
 * @author bmohanty_sta
 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15536 : Student Tracker - Simple Version 1 (preliminary)
 * Controller to execute student tracker batch process.
 */
package edu.ku.cete.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ku.cete.batch.BatchRegistrationProcessStarter;


@Controller
public class BatchStudentTrackerController {
	
    private Logger logger = LoggerFactory.getLogger(BatchStudentTrackerController.class);

    @Autowired
   	private BatchRegistrationProcessStarter starter;
    
	@RequestMapping(value = "processBatchStudentTracker.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> processBatchStudentTracker(HttpServletRequest request) throws Exception{
		logger.debug("--> startBatchStudentTrackerProcess");
    	Long jobId=starter.startStudentTracker();

        Map<String, String> result = new HashMap<String, String>();
		result.put("status", "INPROCESS");
		result.put("jobId", String.valueOf(jobId));
		logger.debug("<-- startBatchStudentTrackerProcess");
		return result;
	}
	
	@RequestMapping(value = "monitorBatchStudentTracker.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> monitorBatchStudentTracker(@RequestParam("jobId") Long jobId){
		logger.trace("Entering monitorProgress");
		Map<String, String> responseResult = new HashMap<String, String>();
		responseResult.put("status", starter.getJobStatus(jobId));
    	logger.trace("Leaving monitorProgress");
		return responseResult;
	}
	
	@RequestMapping(value = "processBatchSTOnlyUntracked.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> processBatchSTOnlyUntracked(HttpServletRequest request) throws Exception{
		logger.debug("--> startBatchSTProcessOnlyUntracked");
    	Long jobId=starter.startSTOnlyUntracked();

        Map<String, String> result = new HashMap<String, String>();
		result.put("status", "INPROCESS");
		result.put("jobId", String.valueOf(jobId));
		logger.debug("<-- startBatchSTProcessOnlyUntracked");
		return result;
	}
	
}
