package edu.ku.cete.batch.dlm.st;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.service.StudentTrackerService;

public class StudentTrackerWriter implements ItemWriter<StudentTracker> {
	
	private final static Log logger = LogFactory.getLog(StudentTrackerWriter.class);

	private ContentArea contentArea;
	private Organization contractingOrganization;
	
	@Autowired
	private StudentTrackerService trackerService;
	
	@Override
	public void write(List<? extends StudentTracker> recommendations) throws Exception {
		logger.debug("--> write : "+recommendations);
		if(!recommendations.isEmpty()) {
			//add recommendation
			trackerService.addBandRecommendation(recommendations.get(0));
		}
		logger.debug("<-- write : "+recommendations);
	}

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}
}
