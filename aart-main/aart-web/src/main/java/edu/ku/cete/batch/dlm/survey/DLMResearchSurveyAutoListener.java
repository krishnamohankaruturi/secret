package edu.ku.cete.batch.dlm.survey;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.enrollment.DLMStudentSurveyRosterDetailsDto;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;




public class DLMResearchSurveyAutoListener<T, S> extends StepListenerSupport<DLMStudentSurveyRosterDetailsDto, DLMResearchSurveyWriterContext>{
	
	private final Log LOG = LogFactory.getLog(this.getClass());
	
	private StepExecution       stepExecution;

    private Integer             commitInterval;
    
    @Autowired
	protected BatchRegistrationService batchRegistrationService;
	protected Long batchRegistrationId;
	
	@Override
    public void beforeStep(StepExecution pStepExecution) {
        this.stepExecution = pStepExecution;
        stepExecution.getExecutionContext().put("stepReasons",new CopyOnWriteArrayList<BatchRegistrationReason>());
    }
	
	@Override
	public void beforeChunk(ChunkContext chunkContext) {
		if (commitInterval != null) {
			LOG.debug(String.format("Step %s - Reading next %d items",
					stepExecution.getStepName(), commitInterval));
		}
	}
	
	@Override
	public void beforeWrite(
			List<? extends DLMResearchSurveyWriterContext> items) {
		LOG.debug(String.format("Step %s - Writing %d items",
				stepExecution.getStepName(), items.size()));
	}
	
	@Override
    public void afterWrite(List<? extends DLMResearchSurveyWriterContext> items) {
        if ((items != null) && !items.isEmpty()) {
            LOG.debug(String.format("Step %s - %d items writed", stepExecution.getStepName(), items.size()));    		
    		for(TestSession testSession:  items.get(0).getTestSessions()) {
    			DLMResearchSurveyWriterContext dlmResearchSurveyWriterContext = items.get(0);
        		LOG.debug(String.format("Assigned Student Id: %d to Test Session Id: %d", dlmResearchSurveyWriterContext.getStudentId(), testSession.getId()));
    			writeReason(String.format("Assigned Student Id: %d to Test Session Id: %d", dlmResearchSurveyWriterContext.getStudentId(), testSession.getId()), dlmResearchSurveyWriterContext, testSession);
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
    public void onWriteError(Exception exception, List<? extends DLMResearchSurveyWriterContext> items) {
    	LOG.debug("--> onWriteError  - batchRegistrationId : " + batchRegistrationId );
    	LOG.error(exception);
		if(!items.isEmpty()) {
			DLMResearchSurveyWriterContext dlmResearchSurveyWriterContext = items.get(0);
			writeReason(exception.getMessage(), dlmResearchSurveyWriterContext, null);
		}
		LOG.debug("<-- onWriteError  - batchRegistrationId : " + batchRegistrationId );
	}
	
	@Override
    public void onProcessError(DLMStudentSurveyRosterDetailsDto studentRoster, Exception exception) {
		LOG.debug("--> onProcessError" );
		LOG.debug("Skipped StudentId: "+studentRoster.getStudentId()+": "+exception.getMessage());
		DLMResearchSurveyWriterContext dlmResearchSurveyWriterContext  = new DLMResearchSurveyWriterContext();
		dlmResearchSurveyWriterContext.setStudentId(studentRoster.getStudentId());
		writeReason(exception.getMessage(), dlmResearchSurveyWriterContext, null);
		LOG.debug("<-- onProcessError" );
	}
	
    @Override
    public void afterProcess(DLMStudentSurveyRosterDetailsDto studentRoster, DLMResearchSurveyWriterContext result) {
    	LOG.debug("<--> afterProcess" );
    }
	
	@SuppressWarnings("unchecked")
	private void writeReason(String msg, DLMResearchSurveyWriterContext dlmResearchSurveyWriterContext, TestSession testSession) {
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		if(null != dlmResearchSurveyWriterContext) {
			brReason.setStudentId(dlmResearchSurveyWriterContext.getStudentId());
			if(testSession != null) {
				brReason.setTestSessionId(testSession.getId());
			}
		}
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
	}
	
	@Override
    @SuppressWarnings("unchecked")
    public ExitStatus afterStep(StepExecution pStepExecution) {
        if (LOG.isInfoEnabled()) {
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
	        batchRegistrationService.insertReasons(stepMessages);
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
