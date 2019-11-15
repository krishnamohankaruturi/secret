/**
 * 
 */
package edu.ku.cete.batch.reportprocess.writer;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.PredictiveReportCreditPercent;
import edu.ku.cete.domain.report.PredictiveReportOrganization;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 *
 * Oct 3, 2017 9:25:35 AM
 */
public class PredictiveOrganizationSummaryCalcWriter implements ItemWriter<Map<String, Object>> {

	final static Log logger = LogFactory.getLog(PredictiveOrganizationSummaryCalcWriter.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
    private Long batchReportProcessId;
    private Long createdUserId;
    
	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends Map<String, Object>> reportMap) throws Exception {
		//if map is not empty
		if(!reportMap.isEmpty()){
			for(Map<String, Object> object : reportMap ){
				PredictiveReportOrganization reportOrganization = (PredictiveReportOrganization) object.get("reportOrganization");
				if(reportOrganization != null){
					logger.debug("Inside PredictiveOrganizationSummaryCalcWriter ....writing for Organization Id - " + reportOrganization.getOrganizationId()
																		+ " - OrganizationType - " + reportOrganization.getOrgTypeCode()
																		+ " - ContentAreaId - " + reportOrganization.getContentAreaId()
																		+ " - GradeId - " + reportOrganization.getGradeId());
					
					//Get the list of percentage records for each question on the test from the map which was added in processor and insert into database
					List<PredictiveReportCreditPercent> fullCreditPercentRecords = (List<PredictiveReportCreditPercent>) object.get("fullCreditPercentRecords");
					
					if(CollectionUtils.isNotEmpty(fullCreditPercentRecords)) {
						for(PredictiveReportCreditPercent reportCreditPercent : fullCreditPercentRecords){							
							logger.debug("Inside PredictiveOrganizationSummaryCalcWriter ....writing for Organization Id - " + reportCreditPercent.getOrganizationId()
									+ " - TestExternalId - " + reportCreditPercent.getExternalTestId()
									+ " - TaskVariantExternalId - " + reportCreditPercent.getTaskVariantExternalId());
							
							reportCreditPercent.setBatchReportProcessId(batchReportProcessId);
							reportCreditPercent.setCreatedUser(createdUserId);
							reportCreditPercent.setModifiedUser(createdUserId);
							batchReportProcessService.insertPredictiveReportCreditPercent(reportCreditPercent);
						}				
						
					}
					logger.debug("Completed PredictiveOrganizationSummaryCalcWriter for Organization Id - " + reportOrganization.getOrganizationId());
				}
				
			}
		}
	}

	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}
    
}
