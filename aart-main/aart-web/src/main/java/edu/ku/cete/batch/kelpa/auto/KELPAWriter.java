/**
 * 
 */
package edu.ku.cete.batch.kelpa.auto;

import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import edu.ku.cete.batch.support.WriterContext;

/**
 * @author ktaduru_sta
 *
 */
public class KELPAWriter implements ItemWriter<WriterContext> {
	
	private Long batchRegistrationId;
	private StepExecution stepExecution;
		
	@BeforeStep
	void initializeValues(StepExecution stepExecution) {
	}
	
	@Override
	public void write(List<? extends WriterContext> enrollments) throws Exception {
		
	}
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

}
