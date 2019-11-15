package edu.ku.cete.model.validation;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import edu.ku.cete.domain.common.OperationalTestWindow;

@Component
public class OperationalTestWindowValidator implements Validator {
    
	/**
	 * logger
	 */
	private Logger logger = LoggerFactory.getLogger(OperationalTestWindowValidator.class);
	
	 /**
     * invalidWindowName.
     */
    @Value("${error.operTestWindow.invalidwindowName}")
    private String invalidWindowName;
    
    /**
     * invalidEffectiveDate.
     */
    @Value("${error.operTestWindow.effectiveDatetimeGreaterThanCurrent}")
    private String invalidEffectiveDate;
    
    @Value("${error.operTestWindow.dacStartDatetimeGreaterThanCurrent}")
    private String invalidDACStartDate;
    
    /**
     * invalidExpiryDate.
     */
    @Value("${error.operTestWindow.expirationDatetimeGreaterThanEffective}")
    private String invalidExpiryDate;
    
    @Value("${error.operTestWindow.dacEndDatetimeGreaterThanEffective}")
    private String invalidDACEndDate;
    
    /**
     * expirationDatetimeGreaterThanCurrent.
     */
    @Value("${error.operTestWindow.expirationDatetimeGreaterThanCurrent}")
    private String expirationDatetimeGreaterThanCurrent;
    
    @Value("${error.operTestWindow.dacDatetimeGreaterThanCurrent}")
    private String expirationDACDatetimeGreaterThanCurrent;
    
    @Value("${error.operTestWindow.scoringDatetimeGreaterThanEffectiveDate}")
    private String scoringDateGreaterThanEffectiveDate;
    
    @Value("${error.operTestWindow.scoringDatetimeGreaterThanCurrentDate}")
    private String scoringDateGreaterThanCurrentDate;
	
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> operationalTestWindow) {
		return OperationalTestWindow.class == operationalTestWindow;
	}
 
	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object object, Errors errors) {
			
		logger.debug("Entering the validate() method");
		
		OperationalTestWindow operationalTestWindow = (OperationalTestWindow) object;
		if (operationalTestWindow == null){
			errors.reject("operationalTestWindow.null", "OperationalTestWindow is null");
		}
 
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "windowName", "name.required", "Name is required");
		
		if(StringUtils.isEmpty(operationalTestWindow.getWindowName()) || StringUtils.isBlank(operationalTestWindow.getWindowName())) {
			errors.reject("windowName.null", invalidWindowName);
		}
		
		if(operationalTestWindow.getEffectiveDate().compareTo(new Date()) < 0 && operationalTestWindow.getId() == null) {
			errors.reject("effectiveDate.invalid", invalidEffectiveDate);
		}
		
		if(operationalTestWindow.getExpiryDate() != null && operationalTestWindow.getEffectiveDate().compareTo(operationalTestWindow.getExpiryDate()) >= 0) {
			errors.reject("expiryDate.invalid", invalidExpiryDate);
		}
		
		if(operationalTestWindow.getExpiryDate() != null && operationalTestWindow.getExpiryDate().compareTo(new Date()) < 0) {
			errors.reject("expiryDate.invalid", expirationDatetimeGreaterThanCurrent);
		}
		
		
		if(operationalTestWindow.getScoringWindowStartDate() != null && operationalTestWindow.getScoringWindowEndDate() != null){
		if(operationalTestWindow.getScoringWindowStartDate().getTime() < operationalTestWindow.getEffectiveDate().getTime()) {
				errors.reject("scoringWindowStartDate.invalid", scoringDateGreaterThanEffectiveDate);			
		}
			
		if(operationalTestWindow.getScoringWindowStartDate().getTime() <= (new Date().getTime())  && operationalTestWindow.getId() == null ) {
			errors.reject("scoringWindowStartDate.invalid", scoringDateGreaterThanCurrentDate);			
		}
		if(operationalTestWindow.getScoringWindowEndDate() != null && operationalTestWindow.getScoringWindowStartDate().compareTo(operationalTestWindow.getScoringWindowEndDate()) >= 0) {
			errors.reject("scoringWindowEndDate.invalid", invalidExpiryDate);
		}
		if(operationalTestWindow.getScoringWindowEndDate() != null && operationalTestWindow.getScoringWindowEndDate().compareTo(new Date()) < 0) {
			errors.reject("scoringWindowEndDate.invalid", expirationDatetimeGreaterThanCurrent);
		}
			
		}	
		
		
		// Dac start and end validated
		
		//Sudhansu:11/08/2017 - Commented because DAC time is applicable for everyday from window start date to window date so below validation is not required
		
		/*if(operationalTestWindow.getDacStartDateTime() != null && operationalTestWindow.getDacEndDateTime() != null){
		if(operationalTestWindow.getDacStartDateTime().compareTo(new Date()) < 0 && operationalTestWindow.getId() == null) {
			errors.reject("effectiveDate.invalid", invalidDACStartDate);
		}
		
		if(operationalTestWindow.getDacEndDateTime() != null && operationalTestWindow.getDacStartDateTime().compareTo(operationalTestWindow.getDacEndDateTime()) >= 0) {
			errors.reject("expiryDate.invalid", invalidDACEndDate);
		}
		
		if(operationalTestWindow.getDacEndDateTime() != null && operationalTestWindow.getDacEndDateTime().compareTo(new Date()) < 0) {
			errors.reject("expiryDate.invalid", expirationDACDatetimeGreaterThanCurrent);
		}
		}*/
		logger.debug("Leaving the validate() method");
	}
	
}
