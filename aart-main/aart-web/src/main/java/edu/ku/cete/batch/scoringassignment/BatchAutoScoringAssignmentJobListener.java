package edu.ku.cete.batch.scoringassignment;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;

/* sudhansu.b
 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
 */	
public class BatchAutoScoringAssignmentJobListener<T, S> extends StepListenerSupport<Object, Object> {
	private final Log LOG = LogFactory.getLog(this.getClass());
    private StepExecution       stepExecution;
    
	@Autowired
	private BatchRegistrationService batchReportProcessService;
	
	private Long batchRegistrationId;
	
	@Override
    public void beforeStep(StepExecution pStepExecution) {
        this.stepExecution = pStepExecution;
        stepExecution.getExecutionContext().put("stepReasons",new CopyOnWriteArrayList<BatchRegistrationReason>());
    }

    @Override
    public void onSkipInRead(Throwable t) {
    	LOG.debug("d = " + t.getMessage());
    }
    
    

    @Override
    public void beforeWrite(List<? extends Object> objects) {
        LOG.debug(String.format("Step %s - Writing %d items", stepExecution.getStepName(), objects.size()));
    }

    @Override
    public void afterWrite(List<? extends Object> objects) {
    	
    }
    
    @Override
    public void onReadError(Exception exception) {
    	LOG.debug("--> onReadError  - batchRegistrationId : " + batchRegistrationId);
		LOG.error(exception);
		writeReason(exception.getMessage());
		LOG.debug("<-- onReadError  - batchRegistrationId : " + batchRegistrationId);
    }
    
    @Override
    public void onWriteError(Exception exception, List<? extends Object> objects) {
    	LOG.debug("--> onWriteError  - batchRegistrationId : " + batchRegistrationId );
    	LOG.error(exception);
		//writeReason(exception.getMessage());
		LOG.debug("<-- onWriteError  - batchRegistrationId : " + batchRegistrationId );
	}
    
    @Override
    public void onProcessError(Object object, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("<-- onProcessError" );
	}
	
	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		BatchRegistrationReason reportProcessReason = new BatchRegistrationReason();
		reportProcessReason.setBatchRegistrationId(batchRegistrationId);
		reportProcessReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
	}

    @Override
    public ExitStatus afterStep(StepExecution pStepExecution) {
        if (LOG.isInfoEnabled()) {
            StringBuilder msg = new StringBuilder();
            msg.append("Step ").append(pStepExecution.getStepName());
            msg.append(" - Read count: ").append(pStepExecution.getReadCount());
            msg.append(" - Write count: ").append(pStepExecution.getWriteCount());
            msg.append(" - Commit count: ").append(pStepExecution.getCommitCount());
            LOG.debug(msg.toString());
        }
		Long reportProcessId = (Long) stepExecution.getJobExecution().getExecutionContext().get("batchRegistrationId");
        List<BatchRegistrationReason> stepMessages = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
        if(stepMessages != null){
			for(BatchRegistrationReason reason: stepMessages) {
				reason.setBatchRegistrationId(reportProcessId);
			}
			if(CollectionUtils.isNotEmpty(stepMessages)){
				batchReportProcessService.insertReasons(stepMessages);
			}
        }
		stepExecution.getExecutionContext().remove("stepReasons");
        return super.afterStep(pStepExecution);
    }
   
    /**
     * Sets the chunk commit interval
     * 
     * @param commitInterval
     *            chunk commit interval (may be <code>null</code>)
     */
    public void setCommitInterval(Integer commitInterval) {
    }



	/**
	 * @return the batchUploadId
	 */
	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}



	/**
	 * @param batchUploadId the batchUploadId to set
	 */
	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	 
}
