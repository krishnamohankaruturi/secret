package edu.ku.cete.batch.dac;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.ku.cete.domain.DailyAccessCode;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.util.StudentUtil;

public class DailyAccessCodeProcessor implements ItemProcessor<DailyAccessCode, List<DailyAccessCode>> {

	private final static Log logger = LogFactory.getLog(DailyAccessCodeProcessor.class);
	
    @Value("${ticketnumber.length}")
    private int ticketNumberLength;
    
    @Autowired
    private StudentUtil studentUtil;
	private Long batchRegistrationId;
    
    @Autowired
    private OperationalTestWindowService otwService;
    
	@Override
	public List<DailyAccessCode> process(DailyAccessCode dacInput) throws Exception {

		logger.debug("in process batchRegistrationId:"+batchRegistrationId);
		
		Calendar otwBeginDate = Calendar.getInstance(TimeZone.getTimeZone("US/Central"));
		otwBeginDate.setTime(dacInput.getBeginDate());
		otwBeginDate.add(Calendar.DATE, -1); //as we need to include begin date in generation push one day back
		
		Calendar otwEndDate = Calendar.getInstance(TimeZone.getTimeZone("US/Central"));
		otwEndDate.setTime(dacInput.getEndDate());
		otwEndDate.add(Calendar.DATE, -1); //actual date included in while logic
		
		List<DailyAccessCode> accessCodes = new ArrayList<DailyAccessCode>();
		List<DailyAccessCode> existingCodes = otwService.getExistingAccessCodes(dacInput);
        while (!otwBeginDate.after(otwEndDate)) {
        	otwBeginDate.add(Calendar.DATE, 1); //increment a day           
        	otwBeginDate.set(Calendar.HOUR_OF_DAY, 0);
    		otwBeginDate.set(Calendar.MINUTE, 0);
    		otwBeginDate.set(Calendar.SECOND, 0);
    		otwBeginDate.set(Calendar.MILLISECOND, 0);
    		if(!exists(existingCodes, otwBeginDate.getTime())) {
        		DailyAccessCode code = (DailyAccessCode) BeanUtils.cloneBean(dacInput);
            	code.setEffectiveDate(otwBeginDate.getTime());
            	code.setAccessCode(studentUtil.generateRandomWord(ticketNumberLength));
            	code.setCreatedDate(new Date());
            	code.setModifiedDate(code.getCreatedDate());
            	code.setCreatedUser(((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
            	code.setModifiedUser(code.getCreatedUser());
            	accessCodes.add(code);
    		}
        	
        }
        
        if(accessCodes.isEmpty()) {
        	return null;
        }
        
		logger.debug("done process batchRegistrationId:"+batchRegistrationId);
		
		return accessCodes;
	}
	
	private boolean exists(List<DailyAccessCode> existingCodes, Date date) {
		//compareTo should be used between 2 dates in same timezone
		Calendar existingEffectiveDate = Calendar.getInstance(TimeZone.getTimeZone("US/Central"));
		for(DailyAccessCode accessCode: existingCodes) {
			existingEffectiveDate.setTime(accessCode.getEffectiveDate());
			existingEffectiveDate.add(Calendar.DATE, 1);
			existingEffectiveDate.set(Calendar.HOUR_OF_DAY, 0);
			existingEffectiveDate.set(Calendar.MINUTE, 0);
			existingEffectiveDate.set(Calendar.SECOND, 0);
			existingEffectiveDate.set(Calendar.MILLISECOND, 0);			
			if(existingEffectiveDate.getTime().compareTo(date) == 0) {
				return true;
			}
		}
		return false;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}
}
