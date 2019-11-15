package edu.ku.cete.batch.dlm.st;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.StudentTracker;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;

public class StudentTrackerStepListener <T, S> extends StepListenerSupport<StudentTracker, StudentTracker> {
	private final Log LOG = LogFactory.getLog(this.getClass());
	
    private StepExecution       stepExecution;
    private Integer             commitInterval;
	
	@Autowired
	protected BatchRegistrationService brService;
	protected Long batchRegistrationId;
	
	@Override
    public void beforeStep(StepExecution pStepExecution) {
        this.stepExecution = pStepExecution;

        stepExecution.getExecutionContext().put("stepReasons",new CopyOnWriteArrayList<BatchRegistrationReason>());
    }

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        if (commitInterval != null) {
            LOG.debug(String.format("Step %s - Reading next %d items", stepExecution.getStepName(), commitInterval));
        }
    }

    @Override
    public void beforeWrite(List<? extends StudentTracker> items) {
        LOG.debug(String.format("Step %s - Writing %d items", stepExecution.getStepName(), items.size()));
    }

    @Override
    public void afterWrite(List<? extends StudentTracker> items) {
        if ((items != null) && !items.isEmpty() && items.get(0).getRecommendedBand() !=null) {
            LOG.debug(String.format("Step %s - %d items writed", stepExecution.getStepName(), items.size()));
			writeReason(String.format("Recommending band: studentId: %d,  bandId: %d, contentAreaId: %d, gradeId: %d, operationalWindowId:%d, source: %s", items.get(0).getStudentId(),
					items.get(0).getRecommendedBand().getComplexityBandId(), items.get(0).getContentAreaId(), 
					items.get(0).getGradeCourseId(), items.get(0).getOperationalWindowId(), items.get(0).getRecommendedBand().getSource()),items.get(0),null);
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
    public void onWriteError(Exception exception, List<? extends StudentTracker> items) {
    	LOG.debug("--> onWriteError  - batchRegistrationId : " + batchRegistrationId );
    	LOG.error(exception);
		if(!items.isEmpty()) {
			writeReason(exception.getMessage(), items.get(0), null);
		}
		LOG.debug("<-- onWriteError  - batchRegistrationId : " + batchRegistrationId );
	}
    
    @Override
    public void onProcessError(StudentTracker stStudent, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("Skipped StudentId: "+stStudent.getId()+": "+exception.getMessage());
		writeReason(exception.getMessage(), stStudent, null);
		LOG.debug("<-- onProcessError" );
	}
	
    @Override
    public void afterProcess(StudentTracker stStudent, StudentTracker result) {
    	LOG.debug("<--> afterProcess" );
    }

	@SuppressWarnings("unchecked")
	private void writeReason(String msg, StudentTracker stStudent, TestSession testSession) {
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		if(null != stStudent) {
			brReason.setStudentId(stStudent.getStudentId());
		}
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons")).add(brReason);
	}

    @SuppressWarnings("unchecked")
	@Override
    public ExitStatus afterStep(StepExecution pStepExecution) {
        if (LOG.isDebugEnabled()) {
            StringBuilder msg = new StringBuilder();
            msg.append("Step ").append(pStepExecution.getStepName());
            msg.append(" - Read count: ").append(pStepExecution.getReadCount());
            msg.append(" - Write count: ").append(pStepExecution.getWriteCount());
            msg.append(" - Commit count: ").append(pStepExecution.getCommitCount());
            LOG.debug(msg.toString());
        }
        List<BatchRegistrationReason> stepMessages = (List<BatchRegistrationReason>) stepExecution.getExecutionContext().get("stepReasons");
        try {
	        for(BatchRegistrationReason reason: stepMessages){
				reason.setBatchRegistrationId(batchRegistrationId);
			}
			brService.insertReasons(stepMessages);
			stepExecution.getExecutionContext().remove("stepReasons");
        } catch(Exception e) {
        	LOG.error("Exceptio while writring reasons to DB: ", e);
        	for(BatchRegistrationReason reason: stepMessages){
        		LOG.debug(reason.getStudentId()+" : "+reason.getReason());
        	}
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
