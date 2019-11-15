package edu.ku.cete.batch.upload;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;

import edu.ku.cete.report.domain.BatchUploadReason;

public class BatchUploadStepListener <T, S> extends StepListenerSupport<Object, Object> {
	private final Log LOG = LogFactory.getLog(this.getClass());
    private StepExecution       stepExecution;

	private Long batchUploadId;
	
	@Override
    public void beforeStep(StepExecution pStepExecution) {
		LOG.debug("--> beforeStep" );
        this.stepExecution = pStepExecution;
        LOG.debug("<-- beforeStep" );
    }

    @Override
    public void onSkipInRead(Throwable t) {
    	LOG.debug("--> onSkipInRead" );
    	LOG.debug("d = " + t.getMessage());
    	LOG.debug("<-- onSkipInRead" );
    }
    
    

    @Override
    public void beforeWrite(List<? extends Object> objects) {
    	LOG.debug("--> beforeWrite" );
        LOG.debug(String.format("Step %s - Writing %d items", stepExecution.getStepName(), objects.size()));
        LOG.debug("<-- beforeWrite" );
    }

    @Override
    public void afterWrite(List<? extends Object> objects) {
    	LOG.debug("--> afterWrite" );
		LOG.debug("<-- afterWrite" );
    }
    
    @Override
    public void onReadError(Exception exception) {
    	LOG.debug("--> onReadError  - batchRegId : " + batchUploadId);
		LOG.error(exception);
		writeReason(exception.getMessage());
		LOG.debug("<-- onReadError  - batchRegId : " + batchUploadId);
    }
    
    @Override
    public void onWriteError(Exception exception, List<? extends Object> objects) {
    	LOG.debug("--> onWriteError  - batchRegistrationId : " + batchUploadId );
    	LOG.error(exception);
		writeReason(exception.getMessage());
		LOG.debug("<-- onWriteError  - batchRegistrationId : " + batchUploadId );
	}
    
    @Override
    public void onProcessError(Object object, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("<-- onProcessError" );
	}
	
	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		LOG.debug("--> writeReason" );
		BatchUploadReason buReason = new BatchUploadReason();
		buReason.setBatchUploadId(batchUploadId);
		buReason.setReason(msg);
		if(msg != null && msg.startsWith("Parsing error")){
			String line = msg.substring(msg.indexOf("line:"),msg.indexOf("in resource"));
			buReason.setReason("File contains invalid characters.");
			buReason.setLine(line);
			buReason.setFieldName(line);
		}
		((CopyOnWriteArrayList<BatchUploadReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(buReason);
		LOG.debug("<-- writeReason" );
	}

    @Override
    public ExitStatus afterStep(StepExecution pStepExecution) {
    	LOG.debug("--> afterStep" );
        if (LOG.isInfoEnabled()) {
            StringBuilder msg = new StringBuilder();
            msg.append("Step ").append(pStepExecution.getStepName());
            msg.append(" - Read count: ").append(pStepExecution.getReadCount());
            msg.append(" - Write count: ").append(pStepExecution.getWriteCount());
            msg.append(" - Commit count: ").append(pStepExecution.getCommitCount());
            LOG.debug(msg.toString());
        }
        LOG.debug("<-- afterStep" );
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
	public Long getBatchUploadId() {
		return batchUploadId;
	}

	/**
	 * @param batchUploadId the batchUploadId to set
	 */
	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}
}
