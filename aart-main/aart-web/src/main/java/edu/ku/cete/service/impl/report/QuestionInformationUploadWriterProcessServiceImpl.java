/**
 * 
 */
package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.QuestionInformationService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 7, 2017 11:30:02 AM
 */
@Service
public class QuestionInformationUploadWriterProcessServiceImpl implements BatchUploadWriterService {

	final static Log logger = LogFactory.getLog(QuestionInformationUploadWriterProcessServiceImpl.class);

	@Autowired
	private QuestionInformationService questionInformationService;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug("Started writing for batchupload - QuestionInformation");
		for( Object object : objects){
			QuestionInformation questionInformation= (QuestionInformation) object;
			questionInformationService.insertSelective(questionInformation);
		}
		logger.debug("Completed writing for batchupload - QuestionInformation");
	}

}
