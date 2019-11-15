package edu.ku.cete.controller;

import java.io.IOException;
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

@Component(value = "ksdeReturnFileProcesor")
public class KSDEReturnFileProcessor {

	private Logger LOGGER = LoggerFactory
			.getLogger(KSDEReturnFileProcessor.class);
	
	private final String STATUS ="STATUS";
	
	@Autowired
	private DataReportService dataReportService;

	@Async
	public Future<Map<String, Object>> createKSDEReturnFile(
			UserDetailImpl userDetails, Long moduleReportId, List<String> subjects, Map<String, Object> params) {
		
		Map<String, Object> result = new HashMap<String,Object>();
		result.put(STATUS, "INPROGRESS");
		try {
			dataReportService.generateKSDEReturnFile(userDetails, moduleReportId, subjects, params);			
			dataReportService.updateKSDEExtractStatusToComplete(moduleReportId);
			
			result.put(STATUS, "COMPLETED");
		} catch (IOException ioe) {
			result.put("STATUS", "ERROR");
            result.put("ERRORS", "IOException occured.");
            dataReportService.updateModuleReportStatusToFailed(moduleReportId);
            LOGGER.error("KSDE Return file IOException:", ioe);
		} catch(Exception e) {
			result.put("STATUS", "ERROR");
            result.put("ERRORS", "Unknown error occured.");
            dataReportService.updateModuleReportStatusToFailed(moduleReportId);
            LOGGER.error("KSDE Return file Exception:", e);
		}
		return new AsyncResult<Map<String,Object>>(result);
	}
}
