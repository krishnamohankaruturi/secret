/**
 * 
 */
package edu.ku.cete.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.service.CategoryService;

/**
 * @author mahesh Instead of injecting at all places, inject once and wire it
 *         many times. Also the meta data will get loaded once and not joined
 *         repeatedly.
 * 
 *         #testing session rules sessionrules.enrollment.manual =
 *         MANUAL_DEFINED_ENROLLMENT_TO_TEST sessionrules.enrollment.system =
 *         SYSTEM_DEFINED_ENROLLMENT_TO_TEST sessionrules.ticketed.test =
 *         TICKETED_AT_TEST sessionrules.ticketed.section = TICKETED_AT_SECTION
 *         sessionrules.testcollection.graceperiod = GRACE_PERIOD
 */
@Component
public class SessionRulesConfiguration {

	/**
	 * manualEnrollmentCode
	 */
	@Value("${sessionrules.enrollment.manual}")
	private String manualEnrollmentCode;
	/**
	 * systemEnrollmentCode
	 */
	@Value("${sessionrules.enrollment.system}")
	private String systemEnrollmentCode;
	/**
	 * testTicketedCode
	 */
	@Value("${sessionrules.ticketed.test}")
	private String testTicketedCode;
	/**
	 * sectionTicketedCode.
	 */
	@Value("${sessionrules.ticketed.section}")
	private String sectionTicketedCode;
	/**
	 * TicketOfTheDayCode. 
	 */
	@Value("${sessionrules.ticketofday.section}")
	private String sectionTicketOfTheDayCode;
	
	
	@Value("${sessionrules.authenticationmethod.dac}")
	private String dailyAccessCode;
	/**
	 * notRequiredToCompleteTest.
	 */
	@Value("${sessionrules.exit.notRequiredToCompleteTest}")
	private String notRequiredToCompleteTestCode;
	/**
	 * gracePeriodCode.
	 */
	@Value("${sessionrules.testcollection.graceperiod}")
	private String gracePeriodCode;
	/**
	* gracePeriodCode.
	*/
	@Value("${sessionrules.type}")
	private String sessionRulesTypeCode;
	/**
	 * manualEnrollmentCategory
	 */
	private Category manualEnrollmentCategory;
	/**
	 * systemEnrollmentCategory
	 */
	private Category systemEnrollmentCategory;
	/**
	 * testTicketedCategory
	 */
	private Category testTicketedCategory;
	/**
	 * sectionTicketOfTheDayCategory.
	 */
	private Category sectionTicketOfTheDayCategory;
	/**
	 * sectionTicketedCategory. 
	 */
	private Category sectionTicketedCategory;
	/**
	 * notRequiredToCompleteTestCategory.
	 */
	private Category notRequiredToCompleteTestCategory;
	/**
	 * gracePeriodCategory.
	 */
	private Category gracePeriodCategory;
	
	private Category dailyAccessCodeCategory;
	
    /**
     * studentsTestsStatuses.
     */
	private Map<Long,Category> sessionRulesMap
    = new HashMap<Long, Category>();
    /**
     * categoryService.
     */
    @Autowired
    private CategoryService categoryService;
	/**
	 * @param statusId
	 * @return
	 */
	public Category getStatus(Long statusId) {
		Category result = null;
		if(statusId != null) {
			result = sessionRulesMap.get(statusId);
		}
		return result;
	}
	
	public boolean isSystemEnrollment(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(systemEnrollmentCode));
	}
	public boolean isManualEnrollment(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(manualEnrollmentCode));
	}
	public boolean isTicketedAtTest(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(testTicketedCode));
	}
	public boolean isTicketOfTheDaySection(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(sectionTicketOfTheDayCode));
	}
	public boolean isTicketedAtSection(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(sectionTicketedCode));
	}
	public boolean isNotRequiredToCompleteTest(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(notRequiredToCompleteTestCode));
	}	
	public boolean isGracePeriodSet(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(gracePeriodCode));
	}
	public boolean isDailyAccessCode(Category category) {
		return (category != null && category.getCategoryCode().equalsIgnoreCase(dailyAccessCode));
	}
	
	/**
	 * initialization.
	 */
	@PostConstruct
	public final void initialize() {
		manualEnrollmentCategory
		= categoryService.selectByCategoryCodeAndType(manualEnrollmentCode,
				sessionRulesTypeCode);
		sessionRulesMap.put(manualEnrollmentCategory.getId(),
				manualEnrollmentCategory);
		systemEnrollmentCategory
		= categoryService.selectByCategoryCodeAndType(systemEnrollmentCode,
				sessionRulesTypeCode);
		sessionRulesMap.put(systemEnrollmentCategory.getId(),
				systemEnrollmentCategory);
		testTicketedCategory
		= categoryService.selectByCategoryCodeAndType(testTicketedCode,
				sessionRulesTypeCode);	
		sessionRulesMap.put(testTicketedCategory.getId(),
				testTicketedCategory);
		sectionTicketedCategory
		= categoryService.selectByCategoryCodeAndType(sectionTicketedCode,
				sessionRulesTypeCode);
		sessionRulesMap.put(sectionTicketedCategory.getId(),
				sectionTicketedCategory);
		sectionTicketOfTheDayCategory
		= categoryService.selectByCategoryCodeAndType(sectionTicketOfTheDayCode,
				sessionRulesTypeCode);
		sessionRulesMap.put(sectionTicketOfTheDayCategory.getId(),
				sectionTicketOfTheDayCategory);
		notRequiredToCompleteTestCategory
		= categoryService.selectByCategoryCodeAndType(notRequiredToCompleteTestCode,
				sessionRulesTypeCode);
		sessionRulesMap.put(notRequiredToCompleteTestCategory.getId(),
				notRequiredToCompleteTestCategory);		
		
		gracePeriodCategory
		= categoryService.selectByCategoryCodeAndType(gracePeriodCode,
				sessionRulesTypeCode);
		sessionRulesMap.put(gracePeriodCategory.getId(),
				gracePeriodCategory);
		
		dailyAccessCodeCategory 
			= categoryService.selectByCategoryCodeAndType(dailyAccessCode,
				sessionRulesTypeCode);
		sessionRulesMap.put(dailyAccessCodeCategory.getId(),
				dailyAccessCodeCategory);
		
}
	/**
	 * @return the manualEnrollmentCode
	 */
	public final String getManualEnrollmentCode() {
		return manualEnrollmentCode;
	}
	/**
	 * @return the systemEnrollmentCode
	 */
	public final String getSystemEnrollmentCode() {
		return systemEnrollmentCode;
	}
	/**
	 * @return the testTicketedCode
	 */
	public final String getTestTicketedCode() {
		return testTicketedCode;
	}
	/**
	 * @return the sectionTicketeOfTheDayCode
	 */
	public final String getTestTicketOfTheDayCode() {
		return sectionTicketOfTheDayCode;
	}
	/**
	 * @return the sectionTicketedCode
	 */
	public final String getSectionTicketedCode() {
		return sectionTicketedCode;
	}
	/**
	 * @return the gracePeriodCode
	 */
	public final String getGracePeriodCode() {
		return gracePeriodCode;
	}
	/**
	 * @return the notRequiredToCompleteTestCode
	 */
	public final String getNotRequiredToCompleteTestCode() {
		return notRequiredToCompleteTestCode;
	}
	/**
	 * @return the sessionRulesTypeCode
	 */
	public final String getSessionRulesTypeCode() {
		return sessionRulesTypeCode;
	}	
	/**
	 * @return the manualEnrollmentCategory
	 */
	public final Category getManualEnrollmentCategory() {
		return manualEnrollmentCategory;
	}
	/**
	 * @return the systemEnrollmentCategory
	 */
	public final Category getSystemEnrollmentCategory() {
		return systemEnrollmentCategory;
	}
	/**
	 * @return the testTicketedCategory
	 */
	public final Category getTestTicketedCategory() {
		return testTicketedCategory;
	}
	/**
	 * @return the sectionTicketedCategory
	 */
	public final Category getSectionTicketedCategory() {
		return sectionTicketedCategory;
	}
	/**
	 * @return the notRequiredToCompleteTestCategory
	 */
	public final Category getNotRequiredToCompleteCategory() {
		return notRequiredToCompleteTestCategory;
	}	
	/**
	 * @return the gracePeriodCategory
	 */
	public final Category getGracePeriodCategory() {
		return gracePeriodCategory;
	}
	public final Category getDailyAccessCodeCategory() {
		return dailyAccessCodeCategory;
	}
	/**
	 * @return the sectionTicketOfTheDayCategory
	 */
	public final Category getTestTicketOfTheDayCategory() {
		return sectionTicketOfTheDayCategory;
	}
}
