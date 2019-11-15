package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.ExternalStudentReportResults;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.ExternalStudentReportResultsProcessService;

@Service
public class UploadExternalStudentReportResultsDefaultWriterProcessServiceImpl
		implements BatchUploadWriterService {
	final static Log logger = LogFactory.getLog(UploadExternalStudentReportResultsDefaultWriterProcessServiceImpl.class);
	@Autowired
	ExternalStudentReportResultsProcessService externalStudentReportResultsProcessService;
	@Override
	public void writerProcess(List<? extends Object> objects) {
		// TODO Auto-generated method stub
		logger.debug("Started writing for batchupload - UploadExternalStudentReportResults");
		for( Object object : objects){
			ExternalStudentReportResults externalStudentReportResults = (ExternalStudentReportResults) object;
			externalStudentReportResultsProcessService.insertOrUpdateExternalStudentReportResults(externalStudentReportResults);
		}
	}

}
