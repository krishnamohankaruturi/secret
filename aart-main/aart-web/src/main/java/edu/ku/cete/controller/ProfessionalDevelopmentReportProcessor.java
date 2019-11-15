package edu.ku.cete.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.user.User;
import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.ProfessionalDevelopmentService;

@Component("pdReportProcessor")
public class ProfessionalDevelopmentReportProcessor {
	
	private Logger LOGGER = LoggerFactory.getLogger(ProfessionalDevelopmentReportProcessor.class);
	
	@Autowired
	private DataReportService dataReportService;
	
	@Autowired
	private ProfessionalDevelopmentService professionalDevelopmentService;
	
    @Async
    public Future<Map<String, Object>> startPDReportGeneration(User user, Long moduleReportId) {
    	LOGGER.debug("--> startPDReportGeneration");
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            result.put("STATUS", "INPROGRESS");
            
            professionalDevelopmentService.startPDReportGeneration(user, moduleReportId);
    		
            result.put("STATUS", "COMPLETED");
        } catch (IOException e) {
            result.put("STATUS", "ERROR");
            result.put("ERRORS", "IOException occured.");
            dataReportService.updateModuleReportStatusToFailed(moduleReportId);
            LOGGER.error("startExtract IOException:", e);            
        } catch (Exception e) {
            result.put("STATUS", "ERROR");
            result.put("ERRORS", "Unknown error occured.");
            dataReportService.updateModuleReportStatusToFailed(moduleReportId);
            LOGGER.error("startExtract Exception:", e);            
        }
        
        LOGGER.debug("<-- startPDReportGeneration");
        return new AsyncResult<Map<String, Object>>(result);
    }
}
