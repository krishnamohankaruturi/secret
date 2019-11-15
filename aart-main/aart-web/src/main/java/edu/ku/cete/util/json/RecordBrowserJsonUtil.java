package edu.ku.cete.util.json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;

/**
 * @author mahesh
 * annotating as component so that multiple instance of object mappers will not be created.
 */
@Component
public class RecordBrowserJsonUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(RecordBrowserJsonUtil.class);
	 /**
     * mapper. Documentation indicate that this is multi threaded
     */
    private ObjectMapper mapper = new ObjectMapper();
    /**
     * Method to build the map for StudentRosterFilter Criteria.
     * @param recordBrowserFilter
     * @return
     */
        
    /**
     * specialChars
     */
    public final List<String> specialChars = Arrays.asList(new String[] { "%", "_" });
    
    /**
     * @param filters
     * @return
     */
    public Map<String,String> constructRecordBrowserFilterCriteria(String filters) {
    	LOGGER.trace("Entering the constructStudentRosterFilterCriteria method");
    	
    	RecordBrowserFilter recordBrowserFilter = null;
    	String dataValue = null;
    	//Parse the column filter values which the user enters on the UI record browser grid.
    	if(null != filters && !"".equals(filters.trim())) {
	        try {
	        	recordBrowserFilter = mapper.readValue(filters, 
	        			new TypeReference<RecordBrowserFilter>() {});
	        } catch(JsonParseException e) {
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
    	}
    	Map<String,String> studentRosterCriteriaMap = new HashMap<String, String>();
    	if(recordBrowserFilter != null) {
	    	for(RecordBrowserFilterRules recordBrowserFilterRules : recordBrowserFilter.getRules()) {
	    		dataValue = recordBrowserFilterRules.getData().trim();
	        	//Check for special chars
	    		for(String specialChar : specialChars) {
	        		if (dataValue.contains(specialChar)) {
	        			dataValue = dataValue.replace(specialChar, ("\\" + specialChar));    		
	                }
	        	}
	    		
	    		studentRosterCriteriaMap.put(recordBrowserFilterRules.getField(), 
	    				CommonConstants.PERCENTILE_DELIM + dataValue + CommonConstants.PERCENTILE_DELIM);	    		
	    	}
    	}
    	
    	LOGGER.trace("Leaving the constructStudentRosterFilterCriteria method");
    	return studentRosterCriteriaMap;
    }

    
    /**
     * Method to build the map for auto complete criteria.
     * 
     * @param fileterAttribute
     * @param termValue
     * @return
     */
    public Map<String,String> constructAutoCompleteFilterCriteria(String fileterAttribute, String termValue) {
    	LOGGER.trace("Entering the constructAutoCompleteFilterCriteria method");
    	
    	//Check for special chars
    	for(String specialChar : specialChars) {
    		if (termValue.contains(specialChar)) {
    			termValue = termValue.replace(specialChar, ("\\" + specialChar));    		
            }
    	}
    	
    	Map<String,String> autoCompleteCriteriaMap = new HashMap<String, String>();
    	if(fileterAttribute != null && termValue != null) {
    		autoCompleteCriteriaMap.put(fileterAttribute, 
    				CommonConstants.PERCENTILE_DELIM + termValue + CommonConstants.PERCENTILE_DELIM);
    	}
    	
    	LOGGER.trace("Leaving the constructAutoCompleteFilterCriteria method");
    	return autoCompleteCriteriaMap;
    }
}
