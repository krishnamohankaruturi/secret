package edu.ku.cete.domain.content;

import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.util.ParsingConstants;

public class TestCollection extends AuditableDomain implements Identifiable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5367680392400753743L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.id
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private Long id;
    
    private Long testCollectionId;
    
    private Long operationalTestWindowId;
    
    private Long testId;
    
    private Long panelId;
    
    private String panelName;

    private Boolean isIncludedInMultiplePanels = Boolean.FALSE;
    
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.name
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.randomizationtype
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private String randomizationType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.gradecourseid
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private Long gradeCourseId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.contentareaid
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private Long contentAreaId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.createdate
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.modifieddate
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private Date modifiedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testcollection.originationcode
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    private String originationCode;

    private GradeCourse gradeCourse;

    private ContentArea contentArea;
    
    /**
     * Minimum number of tests is not stored in
     * test collection, although in most cases, it is 
     * required to be displayed.
     * TODO If that is not the case then create TestCollectionDto
     * extends TestCollection and add these 2 fields there.
     */
    private Integer minimumNumberOfItems;
    
    /**
     * Maximum number of tests is not stored in
     * test collection, although in most cases, it is 
     * required to be displayed.
     * TODO If that is not the case then create TestCollectionDto
     * extends TestCollection and add these 2 fields there.
     */
    private Integer maximumNumberOfItems;    
    /**
	 * assessment
	 */
	private Assessment assessment = new Assessment();
	/**
	 * testingProgram
	 */
	private TestingProgram testingProgram = new TestingProgram();
	/**
	 * assessmentProgram
	 */
	private AssessmentProgram assessmentProgram = new AssessmentProgram();
	
	private Stage stage = new Stage();

    private List<Test> tests;
    /**
     * INFO:
     * canPreview.
     * By default all tests can be previewed.
     */
    private boolean canPreview = true;
    
    /**
     * systemFlag.
     * If the value is set to true then its system, if
     * false then its manual
     */
    private boolean systemFlag;
    
    /**
     * manualScoring
     */
    private Boolean manualScoring = false;
    
    private Long systemSelectoptionId;
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.id
     *
     * @return the value of public.testcollection.id
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.id
     *
     * @param id the value for public.testcollection.id
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setId(Long id) {
        this.id = id;
    }
 
    /**
     * @return the testCollectionId
     */    
    public Long getTestCollectionId() {
        return testCollectionId;
    }
    
    /**
     * @param testCollectionId the testCollectionId to set
     */
    public void setTestCollectionId(Long testCollectionId) {
        this.testCollectionId = testCollectionId;
    }

    /**
     * @return the testId
     */    
    public Long getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */    
    public void setTestId(Long testId) {
        this.testId = testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.name
     *
     * @return the value of public.testcollection.name
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.name
     *
     * @param name the value for public.testcollection.name
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.randomizationtype
     *
     * @return the value of public.testcollection.randomizationtype
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public String getRandomizationType() {
   
        return randomizationType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.randomizationtype
     *
     * @param randomizationType the value for public.testcollection.randomizationtype
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setRandomizationType(String randomizationType) {
        this.randomizationType = randomizationType == null ? null : randomizationType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.gradecourseid
     *
     * @return the value of public.testcollection.gradecourseid
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public Long getGradeCourseId() {
        return gradeCourseId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.gradecourseid
     *
     * @param gradecourseid the value for public.testcollection.gradecourseid
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setGradeCourseId(Long gradecourseid) {
        this.gradeCourseId = gradecourseid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.contentareaid
     *
     * @return the value of public.testcollection.contentareaid
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public Long getContentAreaId() {
        return contentAreaId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.contentareaid
     *
     * @param contentareaid the value for public.testcollection.contentareaid
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setContentAreaId(Long contentareaid) {
        this.contentAreaId = contentareaid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.createdate
     *
     * @return the value of public.testcollection.createdate
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.createdate
     *
     * @param createdate the value for public.testcollection.createdate
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setCreateDate(Date createdate) {
        this.createDate = createdate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.modifieddate
     *
     * @return the value of public.testcollection.modifieddate
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.modifieddate
     *
     * @param modifieddate the value for public.testcollection.modifieddate
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setModifiedDate(Date modifieddate) {
        this.modifiedDate = modifieddate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testcollection.originationcode
     *
     * @return the value of public.testcollection.originationcode
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public String getOriginationCode() {
        return originationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testcollection.originationcode
     *
     * @param originationCode the value for public.testcollection.originationcode
     *
     * @mbggenerated Wed Sep 12 16:14:17 CDT 2012
     */
    public void setOriginationCode(String originationCode) {
        this.originationCode = originationCode == null ? null : originationCode.trim();
    }

    /**
     * @return the gradeCourse
     */
    public GradeCourse getGradeCourse() {
        return gradeCourse;
    }

    /**
     * @param gradeCourse the gradeCourse to set
     */
    public void setGradeCourse(GradeCourse gradeCourse) {
        this.gradeCourse = gradeCourse;
    }

    /**
     * @return the contentArea
     */
    public ContentArea getContentArea() {
        return contentArea;
    }

    /**
     * @param contentArea the contentArea to set
     */
    public void setContentArea(ContentArea contentArea) {
        this.contentArea = contentArea;
    }

	/**
	 * @return the minimumNumberOfItems
	 */
	public Integer getMinimumNumberOfItems() {
		return minimumNumberOfItems;
	}

	/**
	 * @param minimumNumberOfItems the minimumNumberOfItems to set
	 */
	public void setMinimumNumberOfItems(Integer minimumNumberOfItems) {
		this.minimumNumberOfItems = minimumNumberOfItems;
	}

	/**
	 * @return the maximumNumberOfItems
	 */
	public Integer getMaximumNumberOfItems() {
		return maximumNumberOfItems;
	}

	/**
	 * @param maximumNumberOfItems the maximumNumberOfItems to set
	 */
	public void setMaximumNumberOfItems(Integer maximumNumberOfItems) {
		this.maximumNumberOfItems = maximumNumberOfItems;
	}
	
	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public TestingProgram getTestingProgram() {
		return testingProgram;
	}

	public void setTestingProgram(TestingProgram testingProgram) {
		this.testingProgram = testingProgram;
	}

	public AssessmentProgram getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(AssessmentProgram assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	/**
	 * @return {@link String}
	 */
	public String getNumberOfItems() {
		String result = ParsingConstants.BLANK;
		if ((minimumNumberOfItems == null && maximumNumberOfItems == null) || minimumNumberOfItems == -1) {
			result = ParsingConstants.NOT_APPLICABLE;
		} else if (minimumNumberOfItems == null && maximumNumberOfItems != null ) {
			result = ParsingConstants.NOT_APPLICABLE
					+ ParsingConstants.RANGE_SEPERATOR + maximumNumberOfItems;
		} else if (minimumNumberOfItems != null && maximumNumberOfItems == null ) {
			result = minimumNumberOfItems
					+ ParsingConstants.RANGE_SEPERATOR + ParsingConstants.NOT_APPLICABLE;
		} else if (minimumNumberOfItems == maximumNumberOfItems) {
			return minimumNumberOfItems + ParsingConstants.BLANK;
		} else {
			return minimumNumberOfItems + ParsingConstants.RANGE_SEPERATOR + maximumNumberOfItems;
		}
		return result;
	}
	/**
	 * @return the canPreview
	 */
	public boolean getCanPreview() {
		return canPreview;
	}
	/**
	 * @param canPreview the canPreview to set
	 */
	public void setCanPreview(boolean canPreview) {
		this.canPreview = canPreview;
	}

	/**
	 * @return the systemFlag
	 */
	public boolean isSystemFlag() {
		return systemFlag;
	}

	/**
	 * @param systemFlag the systemFlag to set
	 */
	public void setSystemFlag(boolean systemFlag) {
		this.systemFlag = systemFlag;
	}

	/**
	 * @return
	 */
	public Boolean getManualScoring() {
		return manualScoring;
	}

	/**
	 * @param manualScoring
	 */
	public void setManualScoring(Boolean manualScoring) {
		this.manualScoring = manualScoring;
	}

	/**
	 * @return the tests
	 */
	public List<Test> getTests() {
		return tests;
	}

	/**
	 * @param tests the tests to set
	 */
	public void setTests(List<Test> tests) {
		this.tests = tests;
	}

	@Override
	public Long getId(int order) {
	    if(order == 0) {
	        return getId();
	    } else if(order == 1) {
	        return getTestCollectionId();
	    } else {
	        return getTestId();
	    }
	}
	
	@Override
	public String getStringIdentifier(int order) {		
		return null;
	}
	
	/* 
	 * This is for collection acess to be faster and to use
	 * collection methods like contains.
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj != null
				&& obj instanceof TestCollection
				&& getId() != null 
				&& ((TestCollection) (obj)).getId() != null) {
			result = ((TestCollection) (obj)).getId().longValue() == this.getId().longValue();
		}
		return result;
		
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public Long getSystemSelectoptionId() {
		return systemSelectoptionId;
	}

	public void setSystemSelectoptionId(Long systemSelectoptionId) {
		this.systemSelectoptionId = systemSelectoptionId;
	}	
	
	public Long getPanelId() {
		return panelId;
	}

	public void setPanelId(Long panelId) {
		this.panelId = panelId;
	}

	public String getPanelName() {
		return panelName;
	}

	public void setPanelName(String panelName) {
		this.panelName = panelName;
	}

	public Boolean getIsIncludedInMultiplePanels() {
		return isIncludedInMultiplePanels;
	}

	public void setIsIncludedInMultiplePanels(Boolean isIncludedInMultiplePanels) {
		this.isIncludedInMultiplePanels = isIncludedInMultiplePanels;
	}
}