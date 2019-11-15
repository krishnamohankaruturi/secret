package edu.ku.cete.batch;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ku.cete.service.ProjectedTestingService;
import edu.ku.cete.service.ServiceException;

@Component
public class ProjectedTestingDistrictSummaryReportScheduler {
	private final static Log logger = LogFactory.getLog(ProjectedTestingDistrictSummaryReportScheduler.class);

	@Autowired
	ProjectedTestingService projectedTestingService;

	String isScheduleOn;

	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	public void run() throws ServiceException, IOException {
		logger.debug("--> run - Job: Entering Projected Testing/Scoring District Summary Scheduler isSchduleOn "
				+ isScheduleOn);
		if (isScheduleOn.equalsIgnoreCase("ON")) {
			projectedTestingService.generateDistrictSummaryReportCSV();
		}
		logger.debug("<-- run - Job: Exiting Projected Testing/Scoring District Summary Scheduler");
	}
}
