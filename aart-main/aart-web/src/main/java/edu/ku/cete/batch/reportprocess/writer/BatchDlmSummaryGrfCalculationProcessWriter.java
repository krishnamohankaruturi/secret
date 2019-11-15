package edu.ku.cete.batch.reportprocess.writer;

 
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.domain.report.OrganizationGrfCalculation;
import edu.ku.cete.service.report.OrganizationGrfCalculationService;


/**
 * Writes products to a database
 * @param <T>
 */
public class BatchDlmSummaryGrfCalculationProcessWriter implements ItemWriter<OrganizationGrfCalculation>
{
	final static Log logger = LogFactory.getLog(BatchDlmSummaryGrfCalculationProcessWriter.class);

	 @Autowired 
	 OrganizationGrfCalculationService orgGrfCalculationService;
 
	
	@Override
	public void write(List<? extends OrganizationGrfCalculation> orgGrfCalculationList) throws Exception {
		
		logger.debug("Inside BatchDlmSummaryGrfCalculationProcessWriter for organization report Generation...."+orgGrfCalculationList.size() );
		for(OrganizationGrfCalculation orgGrfCalculation : orgGrfCalculationList){
			orgGrfCalculationService.insertSelective(orgGrfCalculation);
		}
		logger.debug("Completed BatchDlmSummaryGrfCalculationProcessWriter" );
	}

	
	 
	

}
