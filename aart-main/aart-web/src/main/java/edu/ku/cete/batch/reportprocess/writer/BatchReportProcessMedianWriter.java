package edu.ku.cete.batch.reportprocess.writer;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.service.report.BatchReportProcessService;
 
/**
 * Writes products to a database
 * @param <T>
 */
public class BatchReportProcessMedianWriter implements ItemWriter<Map<String, Object>>
{
	final static Log logger = LogFactory.getLog(BatchUploadJobListener.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
     private Long batchReportProcessId;
 
	
	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends Map<String, Object>> reportMap) throws Exception {
		if(!reportMap.isEmpty()){
			for(Map<String, Object> object : reportMap ){
				//median write
				ReportsMedianScore reportsMedianScore = (ReportsMedianScore) object.get("median");
				if(reportsMedianScore != null) {
					logger.debug("Inside BatchReportProcessWriter ....writing for Organization Id - " + reportsMedianScore.getOrganizationId());
					reportsMedianScore.setBatchReportProcessId(batchReportProcessId);
					batchReportProcessService.insertReportMedianScore(reportsMedianScore);
					
					//Main level percentages 
					List<ReportsPercentByLevel> reportsPercentByLevel = (List<ReportsPercentByLevel>) object.get("mainlevelpercentages");
					batchReportProcessService.insertReportsPercentByLevel(reportsPercentByLevel);
					
					//MDPT level percentages
					/*reportsPercentByLevel = (List<ReportsPercentByLevel>) object.get("mdptlevelpercentages");
					batchReportProcessService.insertReportsPercentByLevel(reportsPercentByLevel);
					
					
					//Combined level percentages
					reportsPercentByLevel = (List<ReportsPercentByLevel>) object.get("combinedlevelpercentages");
					batchReportProcessService.insertReportsPercentByLevel(reportsPercentByLevel);*/
					
					logger.debug("Completed BatchReportProcessWriter for Student Id - " + reportsMedianScore.getOrganizationId());
				}
				
				List<ReportsMedianScore> reportMedianScoresBySubScoreDef = (List<ReportsMedianScore>) object.get("subScoremedian");
				if(CollectionUtils.isNotEmpty(reportMedianScoresBySubScoreDef) && reportMedianScoresBySubScoreDef.size() > 0) {					
					for(ReportsMedianScore reportMedianScoreBySubScoreDef : reportMedianScoresBySubScoreDef) {
						logger.debug("Inside BatchReportProcessWriter for SubScore Definition ....writing for Organization Id - " + reportMedianScoreBySubScoreDef.getOrganizationId());
						reportMedianScoreBySubScoreDef.setBatchReportProcessId(batchReportProcessId);
						batchReportProcessService.insertReportMedianScore(reportMedianScoreBySubScoreDef);
					}
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
	
	 
	

}
