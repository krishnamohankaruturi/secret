/**
 * 
 */
package edu.ku.cete.report.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.DataReportService;
import edu.ku.cete.service.ServiceException;

/**
 * @author ktaduru_sta
 * US16739 : 09/04/2015
 */
public class ModuleReportScheduler {
	private final static Log logger = LogFactory.getLog(ModuleReportScheduler.class);
	
	@Autowired
	DataReportService dataReportService;
	
	String isScheduleOn;
	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	} 
	
	public void run() throws ServiceException {
		logger.debug("--> run - Job: Entring ModuleReportScheduler isSchduleOn "+isScheduleOn);
		
		if(isScheduleOn.equalsIgnoreCase("ON")) {
			logger.debug("****************** Inactive extract file deletion process started*****************");
			dataReportService.deleteModuleReports();
			logger.debug("****************** Inactive extract file deletion process completed successfully*****************");
		}
		
		logger.debug("<-- run - Job: Exiting ModuleReportScheduler scheduler" );
	}
}
