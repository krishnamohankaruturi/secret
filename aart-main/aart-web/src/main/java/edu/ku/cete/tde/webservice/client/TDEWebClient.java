/**
 *
 */
package edu.ku.cete.tde.webservice.client;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.configuration.StudentsTestSectionsStatusConfiguration;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.tde.webservice.domain.ServiceResponseEnvelope;
import edu.ku.cete.tde.webservice.domain.StudentsResponsesDto;
import edu.ku.cete.tde.webservice.domain.StudentsTestSectionsStatusRequest;
import edu.ku.cete.tde.webservice.domain.TestStatusDto;

/**
 * 
 * @author neil.howerton
 * 
 */
@Service
public class TDEWebClient {

    /**
     * Logger for the class.
     */
    private static Logger logger = LoggerFactory.getLogger(TDEWebClient.class);

    /**
     * Object used to assist with the mapping to and from json.
     */
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Location of the student webservice.
     */
    private static final String STUDENTURI = "student";

    /**
     * Location of the student responses webservice.
     */
    private static final String STUDENTSRESPONSESURI = "studentsresponses";

    /**
     * Location of the students tests webservice.
     */
    private static final String STUDENTSTESTSURI = "studentstests";

    /**
     * Location of the students tests webservice.
     */
    private static final String SECTIONURI = "section";
    
    /**
     * Used to send REST requests and received JSON responses.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Holds the location of the TDEServices.
     */
    @Value("${webservice.tde.baseURI}")
    private String tdeServiceURL;
    
    /**
     * 
     */
    @Autowired
    private StudentsTestSectionsStatusConfiguration studentsTestSectionsStatusConfiguration;

     
    

    /**
     * Updates the students tests records in TDE by their testSessionId, sets
     * the status.
     * 
     * @param testSession
     *            {@link TestSession}
     * @return boolean - whether the call was successful or not.
     */
    public final boolean updateStudentsTestsStatus(TestSession testSession) {
        logger.trace("Entering the updateStudentsTestsStatus method");
        logger.debug("Parameters testSession: {}", testSession);

        String[] parameters = new String[] {STUDENTSTESTSURI, "updatestatus"};

        String serviceUrl = servicePath(tdeServiceURL, parameters);

        HttpHeaders headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);

        TestStatusDto testStatusDto = new TestStatusDto();
        testStatusDto.setTestSessionId(testSession.getId());
        testStatusDto.setTestStatusCode(testSession.getStatusCategory().getCategoryCode());

        HttpEntity<TestStatusDto> httpEntity = new HttpEntity<TestStatusDto>(testStatusDto, headers);
        boolean successful = false;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(serviceUrl, HttpMethod.PUT, httpEntity, String.class);

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            	String jsonResponse = responseEntity.getBody();

                ServiceResponseEnvelope<Object> envelope = mapper.readValue(jsonResponse,
                        new TypeReference<ServiceResponseEnvelope<Object>>() {
                        });

                if (envelope.getStatus().getCode() == HttpStatus.OK.value()) {
                    successful = true;
                }
            }
        } catch (Exception e) {
        	logger.error("Error in update students tests status. " + testStatusDto,e);
            handleException(e);
        }

        return successful;
    }

    

    /**
     * 
     * @param studentsTests
     *            long
     * @return List<StudentsResponses>
     */
    public final List<StudentsResponses> retrieveResponsesByStudentsTestsIds(List<StudentsTests> studentsTests) {
        logger.trace("Entering the retrieveResponsesByTestSession method");
        logger.debug("Parameters testSessionId: {}", studentsTests);
        String studentsTestsIds = "";
        
        for(StudentsTests studentsTest : studentsTests ) {
        	studentsTestsIds += studentsTest.getId() + ",";
        }
        studentsTestsIds = studentsTestsIds.substring(0, studentsTestsIds.length()-1);
        
        String[] parameters = new String[] {STUDENTSRESPONSESURI, "byStudentTestsIds", String.valueOf(studentsTestsIds)};

        String serviceUrl = servicePath(tdeServiceURL, parameters);
        List<StudentsResponsesDto> studentsResponsesDtos = null;

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(serviceUrl, HttpMethod.GET, null, String.class);
            logger.debug("Invoking TDE services with " + serviceUrl + " and received " + responseEntity);

            if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            	String jsonResponse = responseEntity.getBody();
                logger.debug("Invoking TDE services with " + serviceUrl + " and received json response " + jsonResponse);

                ServiceResponseEnvelope<List<StudentsResponsesDto>> envelope = mapper.readValue(jsonResponse,
                        new TypeReference<ServiceResponseEnvelope<List<StudentsResponsesDto>>>() {
                        });
                logger.debug("Invoking TDE services with " + serviceUrl + " and received envelope " + envelope);

                if (envelope.getStatus().getCode() == HttpStatus.OK.value()) {
                    studentsResponsesDtos = (List<StudentsResponsesDto>) envelope.getResponse();
                    logger.debug("Invoking TDE services with " + serviceUrl + " and received envelope " + envelope);
                }
            }
        } catch (Exception e) {
            logger.error("Encountered an issue retrieveResponsesByTestSession parsing the json response"
        	+ studentsResponsesDtos, e);
        }

        List<StudentsResponses> studentsResponses = convertFromStudentsResponsesDto(studentsResponsesDtos);

        return studentsResponses;
    }
    
    /**
     * @param studentsTestSectionsList
     * @param testSessionId
     * @param tdeStatusName
     */
    public final List<StudentsTestSectionsStatusRequest> pushStatusByStudentsTestsIdsToTDE(
    		List<StudentsTestSections> studentsTestSectionsList,
    		Long testSessionId, String tdeStatusName) {
        logger.debug("Entering the pushStatusByStudentsTestsIds method");        
                     
        String[] parameters = new String[] {STUDENTSTESTSURI,SECTIONURI};
        String serviceUrl = servicePath(tdeServiceURL, parameters);
        logger.debug("tdeServiceURL " + tdeServiceURL);        
        logger.debug("SECTIONURI " + SECTIONURI);        
        logger.debug("STUDENTSTESTSURI " + STUDENTSTESTSURI);        
        HttpHeaders headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
        StudentsTestSectionsStatusRequest response = null;
        List<StudentsTestSectionsStatusRequest>
        modifiedStudentsTestSectionsStatusRequestList = new ArrayList<StudentsTestSectionsStatusRequest>();        
        try {
        	
	        HttpEntity<StudentsTestSectionsStatusRequest> httpEntity = null;
	        ResponseEntity<String> responseEntity = null;
	        String jsonResponse = null;
	        	        
	        for(StudentsTestSections studentsTestSections: studentsTestSectionsList ) {

    			StudentsTestSectionsStatusRequest
    			studentsTestSectionsStatusRequest = new StudentsTestSectionsStatusRequest(
 				studentsTestSections.getStudentsTestId(), testSessionId,
 				(new Date()), studentsTestSections.getTestSectionId(),
 				getTDEStatusId(tdeStatusName));
    			
    			logger.debug("Sending to TDE services " + studentsTestSectionsStatusRequest);
    			
	        	httpEntity = new HttpEntity<StudentsTestSectionsStatusRequest>(
	        			studentsTestSectionsStatusRequest,
	            		headers);
	        	responseEntity = restTemplate.exchange(serviceUrl,
	        			HttpMethod.PUT, httpEntity, String.class);
	        	if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
	        		jsonResponse = responseEntity.getBody();
	        	}	                                     

	        	response = mapper.readValue(jsonResponse,
                        new TypeReference<StudentsTestSectionsStatusRequest>() {
                        });                 
    			logger.debug("Received from TDE services " + response);
	        	if(response.getStudentsTestId() != null) {
                	modifiedStudentsTestSectionsStatusRequestList.add(response);
	        	}               
            }

        } catch (Exception e) {
            logger.error("pushStatusByStudentsTestsIdsToTDE:"
        + modifiedStudentsTestSectionsStatusRequestList, e);
            handleException(e);
        }
                
        logger.debug("Leaving the pushStatusByStudentsTestsIds method");
        return modifiedStudentsTestSectionsStatusRequestList;
    }
     
    
    //TODO change this logic to a more generalized way.
    /**
     * @param tdeStatusName
     * @return
     */
    private Long getTDEStatusId(String tdeStatusName) {
    	Long tdeStatusId = (long)0;
    	if(studentsTestSectionsStatusConfiguration.getUnUsedStudentsTestSectionsCode() .equalsIgnoreCase(tdeStatusName))
    		tdeStatusId = (long)1;
    	else if(studentsTestSectionsStatusConfiguration.getInProgressStudentsTestSectionsCode().equalsIgnoreCase(tdeStatusName))
    		tdeStatusId = (long)2;
    	else if(studentsTestSectionsStatusConfiguration.getReactivatedStudentsTestSectionsCode().equalsIgnoreCase(tdeStatusName))
    		tdeStatusId = (long)4;
    	else if(studentsTestSectionsStatusConfiguration.getInProgressTimedOutStudentsTestSectionsCode().equalsIgnoreCase(tdeStatusName))
    		tdeStatusId = (long)5;    	
    	return tdeStatusId;
    }
    
    
    /**
     * 
     * @param studentsResponsesDtos
     *            List<StudentsResponsesDto>
     * @return List<StudentsResponses>
     */
    private List<StudentsResponses> convertFromStudentsResponsesDto(final List<StudentsResponsesDto> studentsResponsesDtos) {
        List<StudentsResponses> studentsResponses = null;

        if (studentsResponsesDtos != null) {

            studentsResponses = new ArrayList<StudentsResponses>();
            for (StudentsResponsesDto dto : studentsResponsesDtos) {
                StudentsResponses sr = new StudentsResponses();
                sr.setStudentsTestsId(dto.getStudentsTestsId());                
                sr.setStudentId(dto.getStudentId());
                sr.setFoilId(dto.getFoilId());
                sr.setResponse(dto.getResponse());
                sr.setTaskVariantId(dto.getTaskId());
                sr.setTestId(dto.getTestId());
                studentsResponses.add(sr);
            }
        }

        return studentsResponses;
    }

    
     

    private String servicePath(String uri, String[] parameters) {
        logger.trace("Entering the servicePath method");
        logger.debug("Parameters: uri: {}, parameters: {}", new Object[] {uri, parameters});

        StringBuilder sb = new StringBuilder(uri);

        for (String parameter : parameters) {
            sb.append("/");
            sb.append(parameter);
        }

        logger.debug("Returning string: {}", sb.toString());
        logger.trace("Leaving the servicePath method");
        return sb.toString();
    }

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

    private void handleException(Exception e) {
        if (e instanceof JsonParseException) {
            logger.error("Couldn't parse json object.", e);
        } else if (e instanceof IOException) {
            logger.error("Couldn't access object.", e);
        } else if (e instanceof JsonMappingException) {
            logger.error("Unexpected json mapping", e);
        } else if (e instanceof SecurityException) {
            logger.error("Unexpected exception with reflection", e);
        } else if (e instanceof NoSuchMethodException) {
            logger.error("Unexpected exception with reflection", e);
        } else if (e instanceof IllegalArgumentException) {
            logger.error("Unexpected exception with reflection", e);
        } else if (e instanceof IllegalAccessException) {
            logger.error("Unexpected exception with reflection", e);
        } else if (e instanceof InvocationTargetException) {
            logger.error("Unexpected exception with reflection", e);
        } else {
            logger.error("Unexpected error", e);
        }

    }
}
