package edu.ku.cete.batch.kap.predictive;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.WriterContext;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;

public class KAPPredictiveStepListener<T, S> extends StepListenerSupport<Enrollment, WriterContext> {

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
    public void beforeWrite(List<? extends WriterContext> items) {
        LOG.debug(String.format("Step %s - Writing %d items", stepExecution.getStepName(), items.size()));
    }

    @Override
    public void afterWrite(List<? extends WriterContext> items) {
        if ((items != null) && !items.isEmpty()) {
            LOG.debug(String.format("Step %s - %d items writed", stepExecution.getStepName(), items.size()));
    		Enrollment enrollment = items.get(0).getEnrollment();
    		for(TestSession testSession:  items.get(0).getTestSessions()) {
        		LOG.debug(String.format("Assigned Student Id: %d to Test Session Id: %d", enrollment.getStudentId(), testSession.getId()));
    			writeReason(String.format("Assigned Student Id: %d to Test Session Id: %d", enrollment.getStudentId(), testSession.getId()), enrollment, testSession);
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
    public void onWriteError(Exception exception, List<? extends WriterContext> enrollments) {
    	LOG.debug("--> onWriteError  - batchRegistrationId : " + batchRegistrationId );
    	LOG.error(exception);
		if(!enrollments.isEmpty()) {
			Enrollment enrollment = enrollments.get(0).getEnrollment();
			writeReason(exception.getMessage(), enrollment, null);
		}
		LOG.debug("<-- onWriteError  - batchRegistrationId : " + batchRegistrationId );
	}
    
    @Override
    public void onProcessError(Enrollment enrollment, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("Skipped StudentId: "+enrollment.getStudentId()+": "+exception.getMessage());
		writeReason(exception.getMessage(), enrollment, null);
		LOG.debug("<-- onProcessError" );
	}
	
    @Override
    public void afterProcess(Enrollment enrollment, WriterContext result) {
    	LOG.debug("<--> afterProcess" );
    }

	@SuppressWarnings("unchecked")
	private void writeReason(String msg, Enrollment enrollment, TestSession testSession) {
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		if(null != enrollment) {
			brReason.setStudentId(enrollment.getStudentId());
			if(testSession != null) {
				brReason.setTestSessionId(testSession.getId());
			}
		}
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
