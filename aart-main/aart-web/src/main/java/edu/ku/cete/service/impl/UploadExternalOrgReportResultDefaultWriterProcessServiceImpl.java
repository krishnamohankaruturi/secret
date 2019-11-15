package edu.ku.cete.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.OrganizationReportResults;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.report.ExternalOrganizationResultsService;

@Service
public class UploadExternalOrgReportResultDefaultWriterProcessServiceImpl
		implements BatchUploadWriterService {

	final static Log logger = LogFactory.getLog(UploadExternalOrgReportResultDefaultWriterProcessServiceImpl.class);
	@Autowired
	private ExternalOrganizationResultsService uploadOrganizationReportDetailWriterProcessService; 
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		// TODO Auto-generated method stub
		logger.debug("Started writing for batchupload - UploadExternalOrgReportResult");
					for( Object object : objects){
						OrganizationReportResults organizationReportDetail = (OrganizationReportResults) object;
						uploadOrganizationReportDetailWriterProcessService.insertOrUpdateOrganizationReportDetail(organizationReportDetail);
					}
	

	}
}
