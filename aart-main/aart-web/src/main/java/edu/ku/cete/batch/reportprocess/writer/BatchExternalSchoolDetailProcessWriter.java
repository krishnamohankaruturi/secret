package edu.ku.cete.batch.reportprocess.writer;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.service.report.BatchReportProcessService;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class BatchExternalSchoolDetailProcessWriter implements ItemWriter<OrganizationReportDetails>{

	private final static Log logger = LogFactory .getLog(BatchExternalSchoolDetailProcessWriter.class);
		
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	@Override
	public void write(List<? extends OrganizationReportDetails> reports)
			throws Exception {		
		for (OrganizationReportDetails report : reports) {
			writeReason("CpassExternalSchooldetailReport started with organization id - "+report.getOrganizationId());
			if(report.getDetailedReportPath() != null){
				batchReportProcessService.insertOrganizationReportDetails(report);								
		      }else{
		    	  writeReason("No report created for studentid - "+report.getOrganizationId());
		      }
		}		
	}
	
	private void writeReason(String msg) {
		logger.debug(msg);
	}

}
