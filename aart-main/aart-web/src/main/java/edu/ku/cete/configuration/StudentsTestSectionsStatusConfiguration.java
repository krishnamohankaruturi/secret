/**
 * 
 */
package edu.ku.cete.configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.util.DateUtil;

/**
 * studentstestsections.status.closed = complete
 * studentstestsections.status.type = STUDENT_TESTSECTION_STATUS
 * studentstestsections.status.unused = unused
 * studentstestsections.status.inProgress = inprogress
 * studentstestsections.status.notEnrolled = not enrolled.
 *  
 * @author m802r921
 * This is the class where the studentstestsections status code are present.
 */
@Component
public class StudentsTestSectionsStatusConfiguration {
    /**
     * closedStudentsTestSectionsCode.
     */
    @Value("${studentstestsections.status.closed}")
    private String closedStudentsTestSectionsCode;
    /**
     * unUsedStudentsTestSectionsCode.
     */
    @Value("${studentstestsections.status.unused}")
    private String unUsedStudentsTestSectionsCode;
    /**
     * inProgressStudentsTestSectionsCode.
     */
    @Value("${studentstestsections.status.inProgress}")
    private String inProgressStudentsTestSectionsCode;
    /**
     * notEnrolledStudentsTestSectionsCode.
     */
    @Value("${studentstestsections.status.notEnrolled}")
    private String notEnrolledStudentsTestSectionsCode;        
    /**
     * inProgressTimedOutStudentsTestSectionsCode.
     */
    @Value("${studentstestsections.status.inProgressTimedOut}")
	private String inProgressTimedOutStudentsTestSectionsCode;
    /**
     * reactivatedStudentsTestSectionsCode.
     */
    @Value("${studentstestsections.status.reactivated}")
	private String reactivatedStudentsTestSectionsCode;
    /**
     * inProgressTimedOutStudentsTestSectionsCode.
     */
    @Value("${studentstestsections.status.gracePeriodExceeded}")
	private String gracePeriodExceededStudentsTestSectionsCode;    
    /**
     * studentsTestsStatusTypeCode.
     */
    @Value("${studentstestsections.status.type}")
    private String studentsTestsStatusTypeCode;
    /**
     * studentsTestsStatuses.
     */
    private Map<Long,Category> studentsTestSectionsStatusMap
    = new HashMap<Long, Category>();
    /**
     * closedStudentsTestSectionsStatusCategory.
     */
    private Category closedStudentsTestSectionsStatusCategory;
	/**
     * unUsedStudentsTestSectionsStatusCategory.
     */
    private Category unUsedStudentsTestSectionsStatusCategory;
    /**
     * inProgressStudentsTestSectionsStatusCategory.
     */
    private Category inProgressStudentsTestSectionsStatusCategory;
	/**
	 * inProgressTimedOutStudentsTestSectionsStatusCategory.
	 */
	private Category inProgressTimedOutStudentsTestSectionsStatusCategory;
	/**
	 * reactivatedStudentsTestSectionsStatusCategory.
	 */
	private Category reactivatedStudentsTestSectionsStatusCategory;
	/**
	 * gracePeriodExceededStudentsTestSectionsStatusCategory.
	 */
	private Category gracePeriodExceededStudentsTestSectionsStatusCategory;	
    /**
     * categoryService.
     */
    @Autowired
    private CategoryService categoryService;
    
    private static final Logger logger = LoggerFactory.getLogger(StudentsTestSectionsStatusConfiguration.class);
    /**
	 * @return the closedStudentsTestSectionsCode
	 */
	public String getClosedStudentsTestSectionsCode() {
		return closedStudentsTestSectionsCode;
	}

	/**
	 * @return the unUsedStudentsTestSectionsCode
	 */
	public String getUnUsedStudentsTestSectionsCode() {
		return unUsedStudentsTestSectionsCode;
	}
	/**
	 * @return the inProgressStudentsTestSectionsCode
	 */
	public String getInProgressStudentsTestSectionsCode() {
		return inProgressStudentsTestSectionsCode;
	}
	/**
	 * @return the inProgressStudentsTestSectionsCode
	 */
	public String getInProgressTimedOutStudentsTestSectionsCode() {
		return inProgressTimedOutStudentsTestSectionsCode;
	}
	/**
	 * @return the reactivatedStudentsTestSectionsCode
	 */
	public String getReactivatedStudentsTestSectionsCode() {
		return reactivatedStudentsTestSectionsCode;
	}
	/**
	 * @return the inProgressStudentsTestSectionsCode
	 */
	public String getGracePeriodExceededStudentsTestSectionsCode() {
		return gracePeriodExceededStudentsTestSectionsCode;
	}
	/**
	 * @return the notEnrolledStudentsTestSectionsCode
	 */
	public String getNotEnrolledStudentsTestSectionsCode() {
		return notEnrolledStudentsTestSectionsCode;
	}

	/**
	 * @return the studentsTestsStatusTypeCode
	 */
	public String getStudentsTestSectionsStatusTypeCode() {
		return studentsTestsStatusTypeCode;
	}

	/**
	 * @return the closedStudentsTestSectionsStatusCategory
	 */
	public Category getClosedStudentsTestSectionsStatusCategory() {
		return closedStudentsTestSectionsStatusCategory;
	}

	/**
	 * @return the unUsedStudentsTestSectionsStatusCategory
	 */
	public Category getUnUsedStudentsTestSectionsStatusCategory() {
		return unUsedStudentsTestSectionsStatusCategory;
	}

	/**
	 * @return the inProgressStudentsTestSectionsStatusCategory
	 */
	public Category getInProgressStudentsTestSectionsStatusCategory() {
		return inProgressStudentsTestSectionsStatusCategory;
	}
	
	/**
	 * @return
	 */
	public Category getInProgressTimedOutStudentsTestSectionsStatusCategory() {
		return inProgressTimedOutStudentsTestSectionsStatusCategory;
	}
	/**
	 * @return
	 */
	public Category getReactivatedStudentsTestSectionsStatusCategory() {
		return reactivatedStudentsTestSectionsStatusCategory;
	}
	/**
	 * @return the gracePeriodExceededStudentsTestSectionsStatusCategory
	 */
	public final Category getGracePeriodExceededStudentsTestSectionsStatusCategory() {
		return gracePeriodExceededStudentsTestSectionsStatusCategory;
	}

	/**
	 * @param statusId
	 * @return
	 */
	public Category getStatus(Long statusId) {
		Category result = null;
		if(statusId != null) {
			result = studentsTestSectionsStatusMap.get(statusId);
		}
		return result;
	}
	public Category getStatus(Long statusId, Date modifiedDate, Long gracePeriod) {
		Category result = null;
		boolean validInput = false;
		result = getStatus(statusId);
		if(result != null
				&& modifiedDate != null
				&& gracePeriod != null) {
			validInput = true;
		} else {
			/******logger.error("Input is invalid statusId:"+
		statusId + " modified date: "+modifiedDate + " Grace Period: " + gracePeriod);*/
		}
		if(validInput
				&& result.getCategoryCode().equalsIgnoreCase(inProgressStudentsTestSectionsCode)
				&& DateUtil.isBefore(modifiedDate, gracePeriod, new Date())) {
			logger.debug("Changed to grace period state from " + result.getCategoryCode());
			result = getGracePeriodExceededStudentsTestSectionsStatusCategory();
		}
		return result;
	}

	/**
	 * initialization.
	 */
	@PostConstruct
	public final void initialize() {
		closedStudentsTestSectionsStatusCategory
		= categoryService.selectByCategoryCodeAndType(closedStudentsTestSectionsCode,
				studentsTestsStatusTypeCode);
		studentsTestSectionsStatusMap.put(closedStudentsTestSectionsStatusCategory.getId(),
				closedStudentsTestSectionsStatusCategory);
		unUsedStudentsTestSectionsStatusCategory
		= categoryService.selectByCategoryCodeAndType(unUsedStudentsTestSectionsCode,
				studentsTestsStatusTypeCode);
		studentsTestSectionsStatusMap.put(unUsedStudentsTestSectionsStatusCategory.getId(),
				unUsedStudentsTestSectionsStatusCategory);
		inProgressStudentsTestSectionsStatusCategory
		= categoryService.selectByCategoryCodeAndType(inProgressStudentsTestSectionsCode,
				studentsTestsStatusTypeCode);
		studentsTestSectionsStatusMap.put(inProgressStudentsTestSectionsStatusCategory.getId(),
				inProgressStudentsTestSectionsStatusCategory);
		inProgressTimedOutStudentsTestSectionsStatusCategory
		= categoryService.selectByCategoryCodeAndType(inProgressTimedOutStudentsTestSectionsCode,
				studentsTestsStatusTypeCode);		
		studentsTestSectionsStatusMap.put(inProgressTimedOutStudentsTestSectionsStatusCategory.getId(),
				inProgressTimedOutStudentsTestSectionsStatusCategory);
		reactivatedStudentsTestSectionsStatusCategory
		= categoryService.selectByCategoryCodeAndType(reactivatedStudentsTestSectionsCode,
				studentsTestsStatusTypeCode);
		studentsTestSectionsStatusMap.put(reactivatedStudentsTestSectionsStatusCategory.getId(),
				reactivatedStudentsTestSectionsStatusCategory);
		gracePeriodExceededStudentsTestSectionsStatusCategory
		= categoryService.selectByCategoryCodeAndType(gracePeriodExceededStudentsTestSectionsCode,
				studentsTestsStatusTypeCode);
		studentsTestSectionsStatusMap.put(gracePeriodExceededStudentsTestSectionsStatusCategory.getId(),
				gracePeriodExceededStudentsTestSectionsStatusCategory);
		}

	/**
	 * @return
	 */
	public List<Long> getAllTimedOutStatusIds() {
		List<Long> allTimedOutStatusIds = new ArrayList<Long>();
		allTimedOutStatusIds.add(getInProgressStudentsTestSectionsStatusCategory(
				).getId());
		allTimedOutStatusIds.add(getInProgressTimedOutStudentsTestSectionsStatusCategory(
				).getId());
		return allTimedOutStatusIds;
	}


}
