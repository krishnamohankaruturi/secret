package edu.ku.cete.tde.webservice.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.tde.webservice.domain.MicroMapRequest;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;


/**
 * @author vittaly
 *
 */
@Service
public class LMWebClient {

	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LMWebClient.class);
	
	/**
	 * RestTemplate
	 */
	@Autowired
    private RestTemplate restTemplate;
	
    /**
     * Location of the student responses webservice.
     */
    private static final String CONTENTCODE = "contentCode";
	
	/**
     * lmServiceURL
     */
    @Value("${webservice.lm.baseURI}")
    private String lmServiceURL;	
    
    /**
     * Object used to assist with the mapping to and from json.
     */
    private ObjectMapper mapper = new ObjectMapper();

	/**
	 * @param contentCode
	 * @return
	 */
    public final List<MicroMapResponseDTO> getLinkageLevelInfo(String contentCode) {
		LOGGER.trace("Entering the getLinkageLevelInfo method");
        
		List<MicroMapResponseDTO> microMapResponseDtos = null;
		String serviceUrl = lmServiceURL + "/itimicromapsbyee";
	    HttpHeaders headers = createHttpHeaders(MediaType.APPLICATION_JSON);
	    MicroMapRequest microMapRequest = new MicroMapRequest();
	    microMapRequest.addContentCode(contentCode);
	    HttpEntity<MicroMapRequest> httpEntity = new HttpEntity<MicroMapRequest>(microMapRequest, headers);
	    try {
	    	ResponseEntity<String> response = restTemplate.exchange(serviceUrl, HttpMethod.POST, httpEntity, String.class);
	    	 if (response.getStatusCode().equals(HttpStatus.OK)) {
	    		 String jsonResponse = response.getBody();
	    		 LOGGER.debug("Invoked LM services with " + serviceUrl + " and received json response " + jsonResponse);
	    		 microMapResponseDtos = mapper.readValue(jsonResponse, new TypeReference<List<MicroMapResponseDTO>>() {});
	    	 }
	    } catch(Exception e) {	    	
	    	LOGGER.error("Error in getting the Micro Map info with Content Code : " + contentCode);
	    	handleException(e);
	    }
     
		return microMapResponseDtos;
	}
    
    public final List<MicroMapResponseDTO> getLinkageLevelInfo(List<String> contentCodes) {
		LOGGER.trace("Entering the getLinkageLevelInfo method");
        
		List<MicroMapResponseDTO> microMapResponseDtos = null;
		String serviceUrl = lmServiceURL + "/itimicromapsbyee";
	    HttpHeaders headers = createHttpHeaders(MediaType.APPLICATION_JSON);
	    MicroMapRequest microMapRequest = new MicroMapRequest();
	    for (String contentCode : contentCodes) {
	    	microMapRequest.addContentCode(contentCode);
	    }
	    HttpEntity<MicroMapRequest> httpEntity = new HttpEntity<MicroMapRequest>(microMapRequest, headers);
	    try {
	    	ResponseEntity<String> response = restTemplate.exchange(serviceUrl, HttpMethod.POST, httpEntity, String.class);
	    	 if (response.getStatusCode().equals(HttpStatus.OK)) {
	    		 String jsonResponse = response.getBody();
	    		 LOGGER.debug("Invoked LM services with " + serviceUrl + " and received json response " + jsonResponse);
	    		 microMapResponseDtos = mapper.readValue(jsonResponse, new TypeReference<List<MicroMapResponseDTO>>() {});
	    	 }
	    } catch(Exception e) {	    	
	    	LOGGER.error("Error in getting the Micro Map info with Content Code : " + contentCodes);
	    	handleException(e);
	    }
     
		return microMapResponseDtos;
	}
	
	
	public final List<String> getLinkageLevelsInfo(List<String> contentCodes) {
		LOGGER.trace("Entering the getLinkageLevelInfo method");
        
		List<String> microMapResponseDtos = null;
	    String lmserviceUrl = lmServiceURL + "/micromapbycontentcodesstatus";	 
	    String ids = "";
	    HttpHeaders headers = createHttpHeaders(MediaType.APPLICATION_JSON);
	    List<MicroMapRequest> mplist = new ArrayList<MicroMapRequest>();
	    if(contentCodes != null && contentCodes.size() > 0){
	    	for(int i=0;i<contentCodes.size();i++){
	    		MicroMapRequest microMapRequest = new MicroMapRequest();
	    		microMapRequest.clear();
	    		microMapRequest.addContentCode(contentCodes.get(i));
	    		mplist.add(microMapRequest);
	    		ids += contentCodes.get(i) + "\\";
	    	}
	    }
	    
	    //JSONArray jObject = new JSONArray();
	    String[] parameters = new String[] {String.valueOf(ids)};
	    //String data = contentCode'\\' + StringUtils.join(contentCodes, '\\') + '\\';
	    String serviceUrl = servicePath(lmserviceUrl, parameters);
	    try {
	    	ResponseEntity<String> response = restTemplate.exchange(serviceUrl, HttpMethod.POST, null, String.class);
	    	 if (response.getStatusCode().equals(HttpStatus.OK)) {
	    		 String jsonResponse = response.getBody();
	    		 LOGGER.debug("Invoked LM services with " + serviceUrl + " and received json response " + jsonResponse);
	    		 microMapResponseDtos = mapper.readValue(jsonResponse, new TypeReference<List<String>>() {});
	    	 }
	    } catch(Exception e) {	    	
	    	LOGGER.error("Error in getting the Micro Map info with Content Code : " + contentCodes);
	    	handleException(e);
	    }
     
		return microMapResponseDtos;
	}
	
	/**
	 * @param contentType
	 * @param acceptableMediaTypes
	 * @return
	 */
	private HttpHeaders createHttpHeaders(MediaType contentType, MediaType... acceptableMediaTypes) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(contentType);
		
		if (acceptableMediaTypes != null) {
		    List<MediaType> acceptableMediaTypesList = new ArrayList<MediaType>();
		
		    for (MediaType mediaType : acceptableMediaTypes) {
		        acceptableMediaTypesList.add(mediaType);
		    }
		
		    if (!acceptableMediaTypesList.isEmpty()) {
		        headers.setAccept(acceptableMediaTypesList);
		    }
		}
		
		return headers;
	}
	
	  private String servicePath(String uri, String[] parameters) {
		  LOGGER.trace("Entering the servicePath method");
	        LOGGER.debug("Parameters: uri: {}, parameters: {}", new Object[] {uri, parameters});

	        StringBuilder sb = new StringBuilder(uri);

	        for (String parameter : parameters) {
	            sb.append("/");
	            sb.append(parameter);
	        }

	        LOGGER.debug("Returning string: {}", sb.toString());
	        LOGGER.trace("Leaving the servicePath method");
	        return sb.toString();
	    }
	/**
	 * @param e
	 */
	private void handleException(Exception e) {
	    if (e instanceof JsonParseException) {
	    	LOGGER.error("Couldn't parse json object.", e);
		} else if (e instanceof IOException) {
			LOGGER.error("Couldn't access object.", e);
		} else if (e instanceof JsonMappingException) {
			LOGGER.error("Unexpected json mapping", e);
		} else if (e instanceof SecurityException) {
			LOGGER.error("Unexpected exception with reflection", e);
		} else if (e instanceof NoSuchMethodException) {
			LOGGER.error("Unexpected exception with reflection", e);
		} else if (e instanceof IllegalArgumentException) {
			LOGGER.error("Unexpected exception with reflection", e);
		} else if (e instanceof IllegalAccessException) {
			LOGGER.error("Unexpected exception with reflection", e);
		} else if (e instanceof InvocationTargetException) {
			LOGGER.error("Unexpected exception with reflection", e);
		} else {
			LOGGER.error("Unexpected error", e);
	    }
	}

}
