package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.StudentPrctByAssessmentTopic;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.StudentPrctByAssessmentTopicService;

@Service
public class UploadStudentPrctByAssessmentTopicDefaultWriterProcessServiceImpl
		implements BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(UploadStudentPrctByAssessmentTopicDefaultWriterProcessServiceImpl.class);
	
	@Autowired
	private StudentPrctByAssessmentTopicService studentPrctByAssessmentTopicService;
	@Override
	public void writerProcess(List<? extends Object> objects) {
		// TODO Auto-generated method stub
		logger.debug("Started writing for batchupload - UploadStudentPrctByAssessmentTopic");
		for( Object object : objects){
			StudentPrctByAssessmentTopic studentPrctByAssessmentTopic = (StudentPrctByAssessmentTopic) object;
			studentPrctByAssessmentTopicService.insertOrUpdate(studentPrctByAssessmentTopic);
		}
	}

}
