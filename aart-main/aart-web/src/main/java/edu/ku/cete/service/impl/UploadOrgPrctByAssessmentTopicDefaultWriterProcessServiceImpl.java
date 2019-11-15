package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.OrganizationPrctByAssessmentTopic;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.OrganizationPrctByAssessmentTopicService;

@Service
public class UploadOrgPrctByAssessmentTopicDefaultWriterProcessServiceImpl
		implements BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(UploadOrgPrctByAssessmentTopicDefaultWriterProcessServiceImpl.class);
	@Autowired
	 private OrganizationPrctByAssessmentTopicService uploadOrgPrctByAssessmentTopicProcessService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		// TODO Auto-generated method stub
		logger.debug("Started writing for batchupload - UploadOrgPrctByAssessmentTopic");
		for( Object object : objects){
			OrganizationPrctByAssessmentTopic OrgPrctByAssessmentTopic = (OrganizationPrctByAssessmentTopic) object;
			uploadOrgPrctByAssessmentTopicProcessService.insertOrUpdate(OrgPrctByAssessmentTopic);
		}
	}

}
