package edu.ku.cete.batch.dac;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;

public class DailyAccessCodeStepListener <T, S> extends StepListenerSupport<DailyAccessCode, List<DailyAccessCode>> {

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
    public void beforeWrite(List<? extends List<DailyAccessCode>> items) {
        LOG.debug(String.format("Step %s - Writing %d items", stepExecution.getStepName(), items.size()));
    }

    @Override
    public void afterWrite(List<? extends List<DailyAccessCode>> items) {
        if ((items != null) && !items.isEmpty() && !items.get(0).isEmpty()) {
        	stepExecution.setWriteCount(stepExecution.getWriteCount() + items.get(0).size() - items.size());
            LOG.debug(String.format("Step %s - %d items writed", stepExecution.getStepName(), items.size()));
    		LOG.debug(String.format("completed DAC write for : otw (%d), stage (%d), contentarea (%d), gradecourse (%d)", items.get(0).get(0).getOperationalTestwindowId()
    				,items.get(0).get(0).getStageId(), items.get(0).get(0).getContentAreaId(), items.get(0).get(0).getGradeCourseId()));
    		writeReason(String.format("completed DAC write for : otw (%d), stage (%d), contentarea (%d), gradecourse (%d)", items.get(0).get(0).getOperationalTestwindowId()
    				,items.get(0).get(0).getStageId(), items.get(0).get(0).getContentAreaId(), items.get(0).get(0).getGradeCourseId()));
        }
    }
    
    @Override
    public void onReadError(Exception exception) {
    	LOG.debug("--> onReadError  - batchRegId : " + batchRegistrationId);
		LOG.error(exception);
		writeReason(exception.getMessage());
		LOG.debug("<-- onReadError  - batchRegId : " + batchRegistrationId);
    }
    
    @Override
    public void onWriteError(Exception exception,List<? extends List<DailyAccessCode>> items) {
    	LOG.debug("--> onWriteError  - batchRegistrationId : " + batchRegistrationId );
    	LOG.error(exception);
		writeReason("WriteError: "+ exception.getMessage());
		LOG.debug("<-- onWriteError  - batchRegistrationId : " + batchRegistrationId );
	}
    
    @Override
    public void onProcessError(DailyAccessCode enrollment, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("Skipped getOperationalTestwindowId: "+enrollment.getOperationalTestwindowId()+": "+exception.getMessage());
		writeReason("Skipped getOperationalTestwindowId: "+enrollment.getOperationalTestwindowId() +", "+exception.getMessage());
		LOG.debug("<-- onProcessError" );
	}
	
    @Override
    public void afterProcess(DailyAccessCode enrollment, List<DailyAccessCode> enrollments) {
    	LOG.debug("<--> afterProcess" );
    }

	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
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
