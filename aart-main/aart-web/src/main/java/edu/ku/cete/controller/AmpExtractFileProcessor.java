package edu.ku.cete.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.DataReportService;

@Component(value = "ampExtractFileProcesor")
public class AmpExtractFileProcessor {

	private Logger LOGGER = LoggerFactory
			.getLogger(AmpExtractFileProcessor.class);
	
	private final String STATUS ="STATUS";
	
	@Autowired
	private DataReportService dataReportService;

	@Async
	public Future<Map<String, Object>> createAmpExtractFile(
			UserDetailImpl userDetails, Long moduleReportId, List<String> operationalTestWindowIds, Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<String,Object>();
		result.put(STATUS, "INPROGRESS");
		try {
			List<Long> otwIds = new ArrayList<Long>();
			for(String otwId: operationalTestWindowIds){
				otwIds.add(new Long(Long.parseLong(otwId)));
			}
			dataReportService.generateAmpDataExtract(userDetails, moduleReportId, otwIds);			
			dataReportService.updateReportStatusToComplete(moduleReportId);
			result.put(STATUS, "COMPLETED");
		} catch (IOException ioe) {
			result.put("STATUS", "ERROR");
            result.put("ERRORS", "IOException occured.");
            dataReportService.updateModuleReportStatusToFailed(moduleReportId);
            LOGGER.error("AMP Return file IOException:", ioe);
		}catch(NumberFormatException e){
			result.put("STATUS", "ERROR");
            result.put("ERRORS", "Invalid Operational TestWindow Ids");
            dataReportService.updateModuleReportStatusToFailed(moduleReportId);
            LOGGER.error("AMP Return file NumberFormatException:", e);
		} catch(Exception e) {
			result.put("STATUS", "ERROR");
            result.put("ERRORS", "Unknown error occured.");
            dataReportService.updateModuleReportStatusToFailed(moduleReportId);
            LOGGER.error("AMP Return file Exception:", e);
		}
		return new AsyncResult<Map<String,Object>>(result);
	}
}
