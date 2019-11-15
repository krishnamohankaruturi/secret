package edu.ku.cete.controller.test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ku.cete.domain.InterfaceRequestHistory;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.InterfaceRequestHistoryService;
import edu.ku.cete.util.DataReportTypeEnum;

@RestController
@RequestMapping("ksde")
public class KSDEExtractController {

	/**
	 * LOGGER
	 */
	private Logger LOGGER = LoggerFactory.getLogger(SetupTestSessionController.class);
		
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Autowired
	private AwsS3Service s3;
	
    @Autowired
	private DataReportService dataReportService;
    
    @Autowired
	private InterfaceRequestHistoryService interfaceRequestHistoryService;
    
    @RequestMapping(value = "getKSDEReturnExtract.htm", method = RequestMethod.POST, produces = "text/csv")
    public ResponseEntity<InputStreamResource> processRequestForKSDEExtractFile(
    		HttpServletRequest request,
    		@RequestParam("subjectCode") String subjectCode)
            throws IOException {
    	LOGGER.debug("Entering KSDEExtractController get  processRequestForKSDEExtractFile() method");
    	
    	DataReportTypeEnum ksdeExtractType = null;
    	if(StringUtils.isNotBlank(subjectCode)) {
    		subjectCode = subjectCode.trim();
    		if(subjectCode.equalsIgnoreCase("ELAMATH")) {
        		ksdeExtractType = DataReportTypeEnum.KSDE_ELA_AND_MATH_RETURN_FILE;
    		} else if (subjectCode.equalsIgnoreCase("SS")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_SOCIAL_STUDIES_RETURN_FILE;      		
    		} else if (subjectCode.equalsIgnoreCase("Sci")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_SCIENCE_RETURN_FILE; 
    		} else if (subjectCode.equalsIgnoreCase("DLMELAMATH")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_DLM_ELA_AND_MATH_RETURN_FILE;      		
    		} else if (subjectCode.equalsIgnoreCase("DLMSCI")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_DLM_SCIENCE_RETURN_FILE;      		
    		} else if (subjectCode.equalsIgnoreCase("DLMHISTORY")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_DLM_HISTORY_RETURN_FILE;      		
    		} else if (subjectCode.equalsIgnoreCase("DLMMATH")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_DLM_MATH_RETURN_FILE; 
    		} else if (subjectCode.equalsIgnoreCase("DLMELA")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_DLM_ELA_RETURN_FILE; 
    		} else if (subjectCode.equalsIgnoreCase("GKS")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_CPASS_GKS_RETURN_FILE;      		
    		} else if (subjectCode.equalsIgnoreCase("Mfg")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_CPASS_MFG_RETURN_FILE; 
    		} else if (subjectCode.equalsIgnoreCase("AgFNR")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_CPASS_AGFNR_RETURN_FILE; 
    		}else if (subjectCode.equalsIgnoreCase("ELP")) {
    			ksdeExtractType = DataReportTypeEnum.KSDE_KELPA_STATE_RETURN_FILE; 
    		}
        }
    	
    	// Log request details
    	InterfaceRequestHistory interfaceRequestHistory = new InterfaceRequestHistory();
    	interfaceRequestHistory.setInterfaceName("KSDEReturnExtract: subjectCode - " + subjectCode);
    	interfaceRequestHistory.setRequestedDate(new Date());
    	//::TODO get the userid
    	String reqUserName = request.getHeader("ksdeusername");
    	interfaceRequestHistory.setRequestedUserId(reqUserName);
    	// ip address
    	String ipAddress = request.getHeader("X-FORWARDED-FOR"); 
	    if (ipAddress == null) {
	    	ipAddress = request.getRemoteAddr();  
	    }    	
    	interfaceRequestHistory.setRequestedIPAddress(ipAddress);
    	
    	String message = null;
    	if (ksdeExtractType != null) {
    		
    		ModuleReport extractReport = dataReportService.getMostRecentCompletedReportByTypeId(null, ksdeExtractType.getId());
    		        	
    		if (extractReport != null
    				&& StringUtils.isNotBlank(extractReport.getFileName())
    				&& s3.doesObjectExist(extractReport.getFileName())) {
    			
    			message = "Found KSDE Return Extract with fileName:" + extractReport.getFileName();
    			interfaceRequestHistory.setModuleReportId(extractReport.getId());
    			interfaceRequestHistory.setMessage(message);

	        	InputStreamResource ksdeFile = (InputStreamResource)s3.getObjectAsInputStreamResource(extractReport.getFileName());
	        	
	        	// Insert the request to history
	        	interfaceRequestHistoryService.insert(interfaceRequestHistory);
	        	
	            return ResponseEntity
	                    .ok()
	                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
	            		.header("Pragma", "no-cache")
	            		.header("Expires", "0")
	            		.header("Content-Disposition", "attachment; filename=" + extractReport.getFileName())
	                    .contentLength(ksdeFile.contentLength())
	                    .contentType(MediaType.parseMediaType("text/csv"))                
	                    .body(ksdeFile);
    		} else {
    			// extract not available for selected subject message or file structure not found
    			message = "Not Found KSDE Return extract in Kite platform. Please contact Helpdesk.";
    			interfaceRequestHistory.setMessage(message);
    			
	        	// Insert the request to history
	        	interfaceRequestHistoryService.insert(interfaceRequestHistory);
	        	
    			// convert String message into InputStream
    			 return ResponseEntity
 	                    .ok()
 	                    .contentType(MediaType.TEXT_PLAIN)
 	                    //.body(new InputStreamResource(new InputStream(new StringReader("Not Found KSDE extract in KITE platform. Please contact Helpdesk."))));
 	                    .body(new InputStreamResource(new ByteArrayInputStream(message.getBytes())));
    		}
    	} else {    		    		
    		// return error subject is not correct
			message = "Verify SubjectCode. No matching KSDE Return extract found for SubjectCode:" + subjectCode + ", found in Kite Platform";
			interfaceRequestHistory.setMessage(message);
			
        	// Insert the request to history
        	interfaceRequestHistoryService.insert(interfaceRequestHistory);
        	
    		// convert String message into InputStream
			 return ResponseEntity
                    .ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(new InputStreamResource(new ByteArrayInputStream(message.getBytes())));
    	}
    } 
}