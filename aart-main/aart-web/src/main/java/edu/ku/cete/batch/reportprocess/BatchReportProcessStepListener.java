package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchReportProcessStepListener <T, S> extends StepListenerSupport<Object, Object> {
	private final Log LOG = LogFactory.getLog(this.getClass());
    private StepExecution       stepExecution;
    
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	private Long batchReportProcessId;
	
	@Override
    public void beforeStep(StepExecution pStepExecution) {
        this.stepExecution = pStepExecution;
        stepExecution.getExecutionContext().put("stepReasons",new CopyOnWriteArrayList<ReportProcessReason>());
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
    	LOG.debug("--> onReadError  - batchReportProcessId : " + batchReportProcessId);
		LOG.error(exception);
		writeReason(exception.getMessage());
		LOG.debug("<-- onReadError  - batchReportProcessId : " + batchReportProcessId);
    }
    
    @Override
    public void onWriteError(Exception exception, List<? extends Object> objects) {
    	LOG.debug("--> onWriteError  - batchReportProcessId : " + batchReportProcessId );
    	LOG.error(exception);
		writeReason(exception.getMessage());
		LOG.debug("<-- onWriteError  - batchReportProcessId : " + batchReportProcessId );
	}
    
    @Override
    public void onProcessError(Object object, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("<-- onProcessError" );
	}
	
	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		ReportProcessReason reportProcessReason = new ReportProcessReason();
		reportProcessReason.setReportProcessId(batchReportProcessId);
		reportProcessReason.setReason(msg);
		((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
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
		Long reportProcessId = (Long) stepExecution.getJobExecution().getExecutionContext().get("batchReportProcessId");
        List<ReportProcessReason> stepMessages = (List<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons");
		for(ReportProcessReason reason: stepMessages) {
			reason.setReportProcessId(reportProcessId);
		}
		if(CollectionUtils.isNotEmpty(stepMessages)){
			batchReportProcessService.insertSelectiveReportProcessReasons(stepMessages);
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
	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}



	/**
	 * @param batchUploadId the batchUploadId to set
	 */
	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	 
}
