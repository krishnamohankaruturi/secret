package edu.ku.cete.batch.support;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.service.BatchRegistrationService;

public class XmlBatchRegistrationStepListener <T, S> extends StepListenerSupport<DefaultFieldSet, Object> {
	private final Log LOG = LogFactory.getLog(this.getClass());
    private StepExecution       stepExecution;

    private Integer             commitInterval;
	
	@Autowired
	protected BatchRegistrationService batchRegistrationService;
	protected Long batchRegistrationId;
	
	@Override
    public void beforeStep(StepExecution pStepExecution) {
        this.stepExecution = pStepExecution;
    }

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        if (commitInterval != null) {
            LOG.debug(String.format("Step %s - Reading next %d items", stepExecution.getStepName(), commitInterval));
        }
    }

    @Override
    public void beforeWrite(List<? extends Object> items) {
        LOG.debug(String.format("Step %s - Writing %d items", stepExecution.getStepName(), items.size()));
    }

    @Override
    public void afterWrite(List<? extends Object> items) {
        if ((items != null) && !items.isEmpty()) {
            LOG.debug(String.format("Step %s - %d items writed", stepExecution.getStepName(), items.size()));
            if(items.get(0) instanceof Enrollment) {
            	// TODO fix me - Seems this process can be handled in writer service implementation.
            	/*Enrollment enrollment = (Enrollment)items.get(0);
        		for(TestSession testSession:  items.get(0)) {
            		LOG.debug(String.format("Assigned Student Id: %d to Test Session Id: %d", enrollment.getStudentId(), testSession.getId()));
        			//writeReason(String.format("Assigned Student Id: %d to Test Session Id: %d", enrollment.getStudentId(), testSession.getId()), enrollment, testSession);
        		}*/
            }
            
        }
    }
    
    @Override
    public void onReadError(Exception exception) {
    	LOG.debug("--> onReadError  - batchRegId : " + batchRegistrationId);
		LOG.error(exception);
		writeReason(exception.getMessage(), null, null);
		LOG.debug("<-- onReadError  - batchRegId : " + batchRegistrationId);
    }
    
    @Override
    public void onWriteError(Exception exception, List<? extends Object> records) {
    	LOG.debug("--> onWriteError  - batchRegistrationId : " + batchRegistrationId );
    	LOG.error(exception);
		if(!records.isEmpty()) {
			writeReason(exception.getMessage(), null, null);
		}
		LOG.debug("<-- onWriteError  - batchRegistrationId : " + batchRegistrationId );
	}
    
	@Override
    public void onProcessError(DefaultFieldSet fieldSet, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("Skipped : "+fieldSet+": "+exception.getMessage());
		writeReason(exception.getMessage(), fieldSet, null);
		LOG.debug("<-- onProcessError" );
	}
	
    @Override
    public void afterProcess(DefaultFieldSet fieldSet, Object result) {
    	LOG.debug("<--> afterProcess" );
    }

	@SuppressWarnings("unchecked")
	private void writeReason(String msg, DefaultFieldSet fieldSet, TestSession testSession) {
		BatchUploadReason brReason = new BatchUploadReason();
		brReason.setBatchUploadId(batchRegistrationId);
		if(null != fieldSet) {
			if(testSession != null) {
				brReason.setLine(msg);
			}
		}
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchUploadReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
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
        return super.afterStep(pStepExecution);
    }

    /**
     * Sets the chunk commit interval
     * 
     * @param commitInterval
     *            chunk commit interval (may be <code>null</code>)
     */
    public void setCommitInterval(Integer commitInterval) {
        this.commitInterval = commitInterval;
    }

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}
}
