/**
 * 
 */
package edu.ku.cete.domain.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.property.Identifiable;

/**
 * @author neil.howerton
 *
 */
public class Test extends AuditableDomain implements Identifiable{

    private long id;
    private long assessmentId;
    private long subjectId;
    private long gradeId;
    private String testName;
    private int numItems;
    private int maxScore;
    private long externalId;
    private String accessibilityFlagCode;
    private String testInternalName;
    private TestCollection testCollection;
    private Boolean isInterimTest;
    private Long testspecificationid;
    private long testCollectionId;
    private String testDescription;
    private String createdBy;
    
	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.createdate
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.modifieddate
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private Date modifiedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.originationcode
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private String originationCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.directions
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private String directions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.uitypecode
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private String uiTypeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.reviewtext
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private String reviewText;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.begininstructions
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private String beginInstructions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.endinstructions
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private String endInstructions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.gradecourseid
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private Long gradeCourseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.contentareaid
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private Long contentAreaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.test.status
     *
     * @mbggenerated Wed Oct 24 13:42:08 CDT 2012
     */
    private Long status;


	private Assessment assessment;
    private Category grade;
    private Category subject;
    private List<TestSection> testSections;
    private Boolean qcComplete;
    private boolean tutorialFlag;
    
    /**
     * testformatcode
     */
    private String testformatcode;
    
    private Boolean feedbackAllowed;
    
    public String getAccessibilityFlagCode() {
		return accessibilityFlagCode;
	}
	public void setAccessibilityFlagCode(String accessibilityFlagCode) {
		this.accessibilityFlagCode = accessibilityFlagCode;
	}
	
	

	public long getExternalId() {
		return externalId;
	}
	public void setExternalId(long externalid) {
		this.externalId = externalid;
	}


    
    
    public String getTestInternalName() {
		return testInternalName;
	}
	public void setTestInternalName(String testInternalName) {
		this.testInternalName = testInternalName;
	}


    
    /**
     * @return the grade
     */
    public final Category getGrade() {
        return grade;
    }
    /**
     * @param grade the grade to set
     */
    public final void setGrade(Category grade) {
        this.grade = grade;
    }
    /**
     * @return the subject
     */
    public final Category getSubject() {
        return subject;
    }
    /**
     * @param subject the subject to set
     */
    public final void setSubject(Category subject) {
        this.subject = subject;
    }
    /**
     * @return the id
     */
    public final Long getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public final void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the assessmentId
     */
    public final long getAssessmentId() {
        return assessmentId;
    }
    /**
     * @param assessmentId the assessmentId to set
     */
    public final void setAssessmentId(long assessmentId) {
        this.assessmentId = assessmentId;
    }
    
    
    /**
     * @return the subjectId
     */
    public final long getSubjectId() {
        return subjectId;
    }
    /**
     * @param subjectId the subjectId to set
     */
    public final void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }
    /**
     * @return the gradeId
     */
    public final long getGradeId() {
        return gradeId;
    }
    /**
     * @param gradeId the gradeId to set
     */
    public final void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }
    /**
     * @return the testName
     */
    public final String getTestName() {
        return testName;
    }
    /**
     * @param testName the testName to set
     */
    public final void setTestName(String testName) {
        this.testName = testName;
    }
    /**
     * @return the numItems
     */
    public final int getNumItems() {
        return numItems;
    }
    /**
     * @param numItems the numItems to set
     */
    public final void setNumItems(int numItems) {
        this.numItems = numItems;
    }
    /**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the modifiedDate
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return the originationCode
	 */
	public String getOriginationCode() {
		return originationCode;
	}
	/**
	 * @param originationCode the originationCode to set
	 */
	public void setOriginationCode(String originationCode) {
		this.originationCode = originationCode;
	}
	/**
	 * @return the directions
	 */
	public String getDirections() {
		return directions;
	}
	/**
	 * @param directions the directions to set
	 */
	public void setDirections(String directions) {
		this.directions = directions;
	}
	/**
	 * @return the uiTypeCode
	 */
	public String getUiTypeCode() {
		return uiTypeCode;
	}
	/**
	 * @param uiTypeCode the uiTypeCode to set
	 */
	public void setUiTypeCode(String uiTypeCode) {
		this.uiTypeCode = uiTypeCode;
	}
	/**
	 * @return the reviewText
	 */
	public String getReviewText() {
		return reviewText;
	}
	/**
	 * @param reviewText the reviewText to set
	 */
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	/**
	 * @return the beginInstructions
	 */
	public String getBeginInstructions() {
		return beginInstructions;
	}
	/**
	 * @param beginInstructions the beginInstructions to set
	 */
	public void setBeginInstructions(String beginInstructions) {
		this.beginInstructions = beginInstructions;
	}
	/**
	 * @return the endInstructions
	 */
	public String getEndInstructions() {
		return endInstructions;
	}
	/**
	 * @param endInstructions the endInstructions to set
	 */
	public void setEndInstructions(String endInstructions) {
		this.endInstructions = endInstructions;
	}
	/**
	 * @return the gradeCourseId
	 */
	public Long getGradeCourseId() {
		return gradeCourseId;
	}
	/**
	 * @param gradeCourseId the gradeCourseId to set
	 */
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}
	/**
	 * @return the contentAreaId
	 */
	public Long getContentAreaId() {
		return contentAreaId;
	}
	/**
	 * @param contentAreaId the contentAreaId to set
	 */
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}
	/**
     * @return the assessment
     */
    public Assessment getAssessment() {
        return assessment;
    }
    /**
     * @param assessment the assessment to set
     */
    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }
    /**
     * @return the testSections
     */
    public List<TestSection> getTestSections() {
        return testSections;
    }
    /**
     * @param testSections the testSections to set
     */
    public void setTestSections(List<TestSection> testSections) {
        this.testSections = testSections;
    }
	@Override
	public Long getId(int order) {
		return getId();
	}
	
	@Override
	public String getStringIdentifier(int order) {		
		return null;
	}
	
	/**
	 * This is different from the set test sections. It checks if the test section belongs to the current
	 * test , then sets it.
	 * @param testSections
	 */
	public void setApplicableTestSections(List<TestSection> testSections) {
		if(testSections != null && CollectionUtils.isNotEmpty(testSections)) {
			if(this.testSections == null) {
				this.testSections = new ArrayList<TestSection>();
			}
			for(TestSection testSection:testSections) {
				if(testSection != null && testSection.getTestId() != null
						&& testSection.getTestId().equals(getId())) {
					getTestSections().add(testSection);
				}
			}
		}
	}
	
	public Boolean getQcComplete(){
		return qcComplete;
	}
	
	public void setQcComplete(boolean qcComplete) {
		this.qcComplete = qcComplete;		
	}
	public boolean isTutorialFlag() {
		return tutorialFlag;
	}
	public void setTutorialFlag(boolean tutorialFlag) {
		this.tutorialFlag = tutorialFlag;
	}
	/**
	 * @return
	 */
	public String getTestformatcode() {
		return testformatcode;
	}
	/**
	 * @param testformatcode
	 */
	public void setTestformatcode(String testformatcode) {
		this.testformatcode = testformatcode;
	}
	
	public int getMaxScore() {
		return maxScore;
	}
	
	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}
	
	public TestCollection getTestCollection() {
		return testCollection;
	}
	public void setTestCollection(TestCollection testCollection) {
		this.testCollection = testCollection;
	}
	public Boolean getIsInterimTest() {
		return isInterimTest;
	}
	public void setIsInterimTest(Boolean isInterimTest) {
		this.isInterimTest = isInterimTest;
	}
	public long getTestCollectionId() {
		return testCollectionId;
	}
	public void setTestCollectionId(long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	public Long getTestspecificationid() {
		return testspecificationid;
	}
	public void setTestspecificationid(Long testspecificationid) {
		this.testspecificationid = testspecificationid;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Boolean getFeedbackAllowed() {
		return feedbackAllowed;
	}
	public void setFeedbackAllowed(Boolean feedbackAllowed) {
		this.feedbackAllowed = feedbackAllowed;
	}	
}