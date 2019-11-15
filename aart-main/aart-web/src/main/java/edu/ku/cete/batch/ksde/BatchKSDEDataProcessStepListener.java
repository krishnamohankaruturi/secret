package edu.ku.cete.batch.ksde;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.listener.StepListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.service.EmailService;

public class BatchKSDEDataProcessStepListener<T, S> extends StepListenerSupport<KidRecord, Object> {
	final static Log logger = LogFactory.getLog(BatchKSDEDataProcessStepListener.class);
	
	@Autowired
	private EmailService emailService;
	
	@Override
    public void onSkipInRead(Throwable t) {
    	logger.debug("--> onSkipInRead");
    	logger.debug(t.getMessage());
    	logger.debug("<-- onSkipInRead");
    }
	
	@Override
    public void onSkipInProcess(KidRecord item, Throwable t) {
    	logger.debug("--> onSkipInProcess");
    	logger.debug(t.getMessage());
    	logger.debug("<-- onSkipInProcess");
    }
	
	@Override
	public void onProcessError(KidRecord item, Exception e) {
		if (!(e instanceof SkipBatchException)) {
			sendErrorEmail(item, "Process", e);
		}
	}
	
	private void sendErrorEmail(KidRecord kidRecord, String step, Throwable throwable) {
		try {
			emailService.sendKIDSErrorEmail(kidRecord, step, throwable);
		} catch (Exception e) {
			logger.error("Error occurred while sending KIDS error email for \"" + step + "\" step:", e);
		}
	}
}