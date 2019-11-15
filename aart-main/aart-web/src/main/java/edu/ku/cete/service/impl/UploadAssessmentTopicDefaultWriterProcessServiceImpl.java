package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.AssessmentTopicService;

@Service
public class UploadAssessmentTopicDefaultWriterProcessServiceImpl implements
		BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(UploadAssessmentTopicDefaultWriterProcessServiceImpl.class);
	@Autowired
	private AssessmentTopicService assessmentTopicService; 
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		// TODO Auto-generated method stub
		logger.debug("Started writing for batchupload - UploadAssessmentTopic");
		for( Object object : objects){
			AssessmentTopic assessmentTopic = (AssessmentTopic) object;
			assessmentTopicService.insertOrUpdateAssessmentTopic(assessmentTopic);
		}
	}
}