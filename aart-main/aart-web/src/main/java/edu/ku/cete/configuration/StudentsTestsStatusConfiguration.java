/**
 * 
 */
package edu.ku.cete.configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.service.CategoryService;

/**
 * studentstests.status.closed = complete
 * studentstests.status.type = STUDENT_TEST_STATUS
 * studentstests.status.unused = unused
 * studentstests.status.inProgress = inprogress
 * studentstests.status.notEnrolled = not enrolled.
 * studentstests.status.inProgressTimedout = inprogresstimedout
 * @author m802r921
 * This is the class where the studentstests status code are present.
 */
@Component
public class StudentsTestsStatusConfiguration {
    /**
     * closedStudentsTestsCode.
     */
    @Value("${studentstests.status.closed}")
    private String closedStudentsTestsCode;
    /**
     * unUsedStudentsTestsCode.
     */
    @Value("${studentstests.status.unused}")
    private String unUsedStudentsTestsCode;
    /**
     * inProgressStudentsTestsCode.
     */
    @Value("${studentstests.status.inProgress}")
    private String inProgressStudentsTestsCode;
    
    /**
     * inProgressStudentsTestsCode.
     */
    @Value("${studentstests.status.inProgressTimedout}")
    private String inProgressTimedoutStudentsTestsCode;
    
    /**
     * inProcessingLCSResponsesCode.
     */
    @Value("${studentstests.status.processingLCSResponses}")
    private String inProcessingLCSResponsesCode;
    /**
     * notEnrolledStudentsTestsCode.
     */
    @Value("${studentstests.status.notEnrolled}")
    private String notEnrolledStudentsTestsCode;
    /**
     * studentsTestsStatusTypeCode.
     */
    @Value("${studentstests.status.type}")
    private String studentsTestsStatusTypeCode;
    
    /**
     * pending StudentsTestsCode.
     */
    @Value("${studentstests.status.pending}")
    private String pendingStudentsTestsCode;
    
    /**
     * studentsTestsStatuses.
     */
    private Map<Long,Category> studentsTestsStatusMap
    = new HashMap<Long, Category>();
    /**
     * closedStudentsTestsStatusCategory.
     */
    private Category closedStudentsTestsStatusCategory;

	/**
     * unUsedStudentsTestsStatusCategory.
     */
    private Category unUsedStudentsTestsStatusCategory;
    /**
     * inProgressTimedoutStudentsTestsStatusCategory.
     */
    private Category inProgressTimedoutStudentsTestsStatusCategory;
    /**
     * inProgressStudentsTestsStatusCategory.
     */
    private Category inProgressStudentsTestsStatusCategory;
    /**
     * inProcessingLCSResponsesCategory.
     */
    private Category inProcessingLCSResponsesCategory;
    
    /**
     * pendingStudentsTestsStatusCategory.
     */
    private Category pendingStudentsTestsStatusCategory;
    
    /**
     * categoryService.
     */
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StudentsTestSectionsStatusConfiguration
    studentsTestSectionsStatusConfiguration;
    /**
	 * @return the closedStudentsTestsCode
	 */
	public String getClosedStudentsTestsCode() {
		return closedStudentsTestsCode;
	}

	/**
	 * @return the unUsedStudentsTestsCode
	 */
	public String getUnUsedStudentsTestsCode() {
		return unUsedStudentsTestsCode;
	}
	/**
	 * @return the inProgressStudentsTestsCode
	 */
	public String getInProgressStudentsTestsCode() {
		return inProgressStudentsTestsCode;
	}
	/**
	 * @return the notEnrolledStudentsTestsCode
	 */
	public String getNotEnrolledStudentsTestsCode() {
		return notEnrolledStudentsTestsCode;
	}

	/**
	 * @return the studentsTestsStatusTypeCode
	 */
	public String getStudentsTestsStatusTypeCode() {
		return studentsTestsStatusTypeCode;
	}

	/**
	 * @return the closedStudentsTestsStatusCategory
	 */
	public Category getClosedStudentsTestsStatusCategory() {
		return closedStudentsTestsStatusCategory;
	}

	/**
	 * @return the unUsedStudentsTestsStatusCategory
	 */
	public Category getUnUsedStudentsTestsStatusCategory() {
		return unUsedStudentsTestsStatusCategory;
	}

	/**
	 * @return the inProgressStudentsTestsStatusCategory
	 */
	public Category getInProgressStudentsTestsStatusCategory() {
		return inProgressStudentsTestsStatusCategory;
	}
	
	public Category getPendingStudentsTestsStatusCategory() {
		return pendingStudentsTestsStatusCategory;
	}

	public Category getInProgressTimedoutStudentsTestsStatusCategory() {
		return inProgressTimedoutStudentsTestsStatusCategory;
	}

	/**
	 * @param statusId
	 * @return
	 */
	public Category getStatus(Long statusId) {
		Category result = null;
		if(statusId != null) {
			result = studentsTestsStatusMap.get(statusId);
		}
		return result;
	}

	/**
	 * overrides the status set at the students tests level based off the 
	 * status at the students test sections level.
	 * 
	 * @param studentsTestSectionsList with status id set properly.
	 */
	public void setStudentsTestsCategory(
			List<StudentsTestSections> studentsTestSectionsList) {
		Category result = unUsedStudentsTestsStatusCategory;
		int sectionsCompleted = 0;
		if(studentsTestSectionsList != null
				&& CollectionUtils.isNotEmpty(studentsTestSectionsList)) {
			for(StudentsTestSections studentsTestSections:studentsTestSectionsList) {
				if(studentsTestSections != null &&
						studentsTestSections.getStatusId() != null &&
								! studentsTestSections.getStatusId().equals(
										studentsTestSectionsStatusConfiguration.getUnUsedStudentsTestSectionsStatusCategory(
												).getId())
						) {
					//if any of the sections are in status besides unused, then the test status is in progress.
					result = inProgressStudentsTestsStatusCategory;
					if(studentsTestSections.getStatusId().equals(
							studentsTestSectionsStatusConfiguration.getClosedStudentsTestSectionsStatusCategory(
									).getId())) {						
						sectionsCompleted++;
					}
				}
			}
			if(sectionsCompleted == studentsTestSectionsList.size()) {
				//if all sections are completed then the test is completed.
				result = closedStudentsTestsStatusCategory;
			}
			for(StudentsTestSections studentsTestSections:studentsTestSectionsList) {
				//set the status of the test on each of the test wise status.
				if(!studentsTestSections.getStudentsTests().getStudentTestStatus().getId().equals(this.inProcessingLCSResponsesCategory.getId()))
				{
				studentsTestSections.getStudentsTests().setStudentTestStatus(result);
				studentsTestSections.getStudentsTests().setStatus(result.getId());
				}
			}

		}
	}
	/**
	 * initialization.
	 */
	@PostConstruct
	public final void initialize() {
		closedStudentsTestsStatusCategory
		= categoryService.selectByCategoryCodeAndType(closedStudentsTestsCode,
				studentsTestsStatusTypeCode);
		studentsTestsStatusMap.put(closedStudentsTestsStatusCategory.getId(),
				closedStudentsTestsStatusCategory);
		unUsedStudentsTestsStatusCategory
		= categoryService.selectByCategoryCodeAndType(unUsedStudentsTestsCode,
				studentsTestsStatusTypeCode);
		studentsTestsStatusMap.put(unUsedStudentsTestsStatusCategory.getId(),
				unUsedStudentsTestsStatusCategory);
		inProgressStudentsTestsStatusCategory
		= categoryService.selectByCategoryCodeAndType(inProgressStudentsTestsCode,
				studentsTestsStatusTypeCode);
		studentsTestsStatusMap.put(inProgressStudentsTestsStatusCategory.getId(),
				inProgressStudentsTestsStatusCategory);
		
		pendingStudentsTestsStatusCategory = categoryService.selectByCategoryCodeAndType(pendingStudentsTestsCode,
				studentsTestsStatusTypeCode);
		studentsTestsStatusMap.put(pendingStudentsTestsStatusCategory.getId(),
				pendingStudentsTestsStatusCategory);
		//inProcessingLCSResponsesCode
		inProcessingLCSResponsesCategory
		= categoryService.selectByCategoryCodeAndType(inProcessingLCSResponsesCode,
				studentsTestsStatusTypeCode);
		studentsTestsStatusMap.put(inProcessingLCSResponsesCategory.getId(),
				inProcessingLCSResponsesCategory);
		
		inProgressTimedoutStudentsTestsStatusCategory = categoryService.selectByCategoryCodeAndType(inProgressTimedoutStudentsTestsCode,
				studentsTestsStatusTypeCode);
		studentsTestsStatusMap.put(inProgressTimedoutStudentsTestsStatusCategory.getId(),
				inProgressTimedoutStudentsTestsStatusCategory);
	}

}
