package edu.ku.cete.controller.test;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.ItiMCLog;
import edu.ku.cete.domain.ItiMCLogExample;
import edu.ku.cete.domain.StudentNodeProbability;
import edu.ku.cete.service.ItiTestSessionService;

@Component("itiMCResponseProcessor")
public class ItiMCResponseProcessor {

    /**
     * logger.
     */
    private Logger logger = LoggerFactory.getLogger(ItiMCResponseProcessor.class);

    @Autowired
  	private ItiTestSessionService itiTestSessionService;
     
 	@Async
    public void processMCResponse(String data, long requestId, long userId){
	 		logger.debug("Started Processing MC response for request id " + requestId);
	    	Map<String, List<String>> errorMap = new HashMap<String, List<String>>(); 
	    	JsonFactory jsonFactory = new JsonFactory();
	        ObjectMapper mapper = new ObjectMapper();
	        List<String> fieldErrors = new ArrayList<String>();
	    	try {
	    		JsonParser jsonParser = jsonFactory.createParser(data);
	    		jsonParser.nextToken();
	    		int item = 1;
	    		while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
	    			fieldErrors = new ArrayList<String>();
	    			StudentNodeProbability studentNodeProbability = new StudentNodeProbability() ;
	    			try{
		    			studentNodeProbability = mapper.readValue(jsonParser, StudentNodeProbability.class);
	    			} catch (JsonParseException e) {
	    				fieldErrors.add(e.getMessage());
	    			} catch (JsonMappingException e) {
	    				fieldErrors.add(e.getMessage());
	    			} catch (IOException e) {
	    				fieldErrors.add(e.getMessage());
	    			}
	    			if(fieldErrors.size() == 0){
		    			if(studentNodeProbability.getNodeId() == null){
		    				fieldErrors.add("Node Id is missing.");
		    			}
		    			if(studentNodeProbability.getProbability() == null){
		    				fieldErrors.add("Prabability value is missing.");
		    			}
		    			if(studentNodeProbability.getStudentId() == null){
		    				fieldErrors.add("Student Id is missing.");
		    			}
	    			}
	    			if(fieldErrors.size() == 0){
	    				studentNodeProbability.setCreatedUser(userId);
	    				studentNodeProbability.setRequestId(requestId);
	    				int result = itiTestSessionService.addStudentNodeProbability(studentNodeProbability);
	    				if(result == -1){
	    					itiTestSessionService.updateStudentNodeProbability(studentNodeProbability);
	    				}
	    			} else {
	    				errorMap.put("Item -" + item, fieldErrors);
	    			}
	    			item++;	
	    		}
			} catch (JsonParseException e) {
				logger.error("Unable save mc response. " + e.getMessage());
				fieldErrors.add(e.getMessage());
				errorMap.put("requestid -" + requestId, fieldErrors);
			} catch (JsonMappingException e) {
				logger.error("Unable save mc response. " + e.getMessage());
				fieldErrors.add(e.getMessage());
				errorMap.put("requestid -" + requestId, fieldErrors);
			} catch (IOException e) {
				logger.error("Unable save mc response. " + e.getMessage());
				fieldErrors.add(e.getMessage());
				errorMap.put("requestid -" + requestId, fieldErrors);
			}

	    	if(errorMap.size() > 0){
    			ItiMCLogExample itiMCLogExample = new ItiMCLogExample();
        		ItiMCLogExample.Criteria criteria = itiMCLogExample.createCriteria();
        		criteria.andRequestIdEqualTo(requestId);
        		ItiMCLog itiMCLog = new ItiMCLog();
        		mapper = new ObjectMapper();
    			String errorJson = "";
        		try {
        			errorJson = mapper.writeValueAsString(errorMap);
    			} catch (JsonGenerationException e) {
    				logger.error("Unable to convert errors to Json string.  " + e.getMessage());
    				e.printStackTrace();
    			} catch (JsonMappingException e) {
    				logger.error("Unable save mc response. " + e.getMessage());
    				e.printStackTrace();
    			} catch (IOException e) {
    				logger.error("Unable save mc response. " + e.getMessage());
    				e.printStackTrace();
    			}
        		itiMCLog.setErrors(errorJson);
        		itiTestSessionService.updateSelectiveItiMClog(itiMCLog, itiMCLogExample);
    		}
	 		logger.debug("Completed Processing MC response for request id " + requestId);
	}
	 
}
