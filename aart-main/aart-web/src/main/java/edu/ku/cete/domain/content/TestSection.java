package edu.ku.cete.domain.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.domain.test.StimulusVariant;

public class TestSection extends AuditableDomain implements Identifiable, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3979877777120141418L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.id
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.externalid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Long externalId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.testid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Long testId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.testsectionname
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private String testSectionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.numberoftestitems
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Integer numberOfTestItems;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.helpnotes
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private String helpNotes;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.toolsusageid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Long toolsUsageId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.taskdeliveryruleid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Long taskDeliveryRuleId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.createdate
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.modifieddate
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Date modifiedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.originationcode
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private String originationCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.begininstructions
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private String beginInstructions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.endinstructions
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private String endInstructions;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.contextstimulusid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Long contextStimulusId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.testsection.ticketed
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    private Boolean ticketed;
    
    private Boolean hardBreak;    

    private Integer sectionOrder;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.id
     *
     * @return the value of public.testsection.id
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     * 
     * 
     */
    public TestSection( TestSection ts)
    {
    	this.externalId= ts.getExternalId();
    	this.testSectionName= ts.getTestSectionName();
    	this.numberOfTestItems= ts.getNumberOfTestItems();
    	this.helpNotes= ts.getHelpNotes();
    	this.toolsUsageId= ts.getToolsUsageId();
    	this.taskDeliveryRuleId= ts.getTaskDeliveryRuleId();
    	this.originationCode= ts.getOriginationCode();
    	this.beginInstructions= ts.getBeginInstructions();
    	this.endInstructions= ts.getEndInstructions();
    	this.contextStimulusId= ts.getContextStimulusId();
    	this.ticketed= ts.getTicketed();
    	this.hardBreak= ts.getHardBreak();    
    	this.sectionOrder= ts.getSectionOrder();

    }
    public TestSection()
    {
    	super();
    }
    
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.id
     *
     * @param id the value for public.testsection.id
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.externalid
     *
     * @return the value of public.testsection.externalid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Long getExternalId() {
        return externalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.externalid
     *
     * @param externalId the value for public.testsection.externalid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setExternalId(Long externalId) {
        this.externalId = externalId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.testid
     *
     * @return the value of public.testsection.testid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Long getTestId() {
        return testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.testid
     *
     * @param testId the value for public.testsection.testid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setTestId(Long testId) {
        this.testId = testId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.testsectionname
     *
     * @return the value of public.testsection.testsectionname
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public String getTestSectionName() {
        return testSectionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.testsectionname
     *
     * @param testSectionName the value for public.testsection.testsectionname
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setTestSectionName(String testSectionName) {
        this.testSectionName = testSectionName == null ? null : testSectionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.numberoftestitems
     *
     * @return the value of public.testsection.numberoftestitems
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Integer getNumberOfTestItems() {
        return numberOfTestItems;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.numberoftestitems
     *
     * @param numberOfTestItems the value for public.testsection.numberoftestitems
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setNumberOfTestItems(Integer numberOfTestItems) {
        this.numberOfTestItems = numberOfTestItems;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.helpnotes
     *
     * @return the value of public.testsection.helpnotes
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public String getHelpNotes() {
        return helpNotes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.helpnotes
     *
     * @param helpNotes the value for public.testsection.helpnotes
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setHelpNotes(String helpNotes) {
        this.helpNotes = helpNotes == null ? null : helpNotes.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.toolsusageid
     *
     * @return the value of public.testsection.toolsusageid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Long getToolsUsageId() {
        return toolsUsageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.toolsusageid
     *
     * @param toolsUsageId the value for public.testsection.toolsusageid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setToolsUsageId(Long toolsUsageId) {
        this.toolsUsageId = toolsUsageId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.taskdeliveryruleid
     *
     * @return the value of public.testsection.taskdeliveryruleid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Long getTaskDeliveryRuleId() {
        return taskDeliveryRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.taskdeliveryruleid
     *
     * @param taskDeliveryRuleId the value for public.testsection.taskdeliveryruleid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setTaskDeliveryRuleId(Long taskDeliveryRuleId) {
        this.taskDeliveryRuleId = taskDeliveryRuleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.createdate
     *
     * @return the value of public.testsection.createdate
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.createdate
     *
     * @param createDate the value for public.testsection.createdate
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.modifieddate
     *
     * @return the value of public.testsection.modifieddate
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.modifieddate
     *
     * @param modifiedDate the value for public.testsection.modifieddate
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.originationcode
     *
     * @return the value of public.testsection.originationcode
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public String getOriginationCode() {
        return originationCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.originationcode
     *
     * @param originationCode the value for public.testsection.originationcode
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setOriginationCode(String originationCode) {
        this.originationCode = originationCode == null ? null : originationCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.begininstructions
     *
     * @return the value of public.testsection.begininstructions
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public String getBeginInstructions() {
        return beginInstructions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.begininstructions
     *
     * @param beginInstructions the value for public.testsection.begininstructions
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setBeginInstructions(String beginInstructions) {
        this.beginInstructions = beginInstructions == null ? null : beginInstructions.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.endinstructions
     *
     * @return the value of public.testsection.endinstructions
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public String getEndInstructions() {
        return endInstructions;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.endinstructions
     *
     * @param endInstructions the value for public.testsection.endinstructions
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setEndInstructions(String endInstructions) {
        this.endInstructions = endInstructions == null ? null : endInstructions.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.contextstimulusid
     *
     * @return the value of public.testsection.contextstimulusid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Long getContextStimulusId() {
        return contextStimulusId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.contextstimulusid
     *
     * @param contextStimulusId the value for public.testsection.contextstimulusid
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setContextStimulusId(Long contextStimulusId) {
        this.contextStimulusId = contextStimulusId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.testsection.ticketed
     *
     * @return the value of public.testsection.ticketed
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public Boolean getTicketed() {
        return ticketed;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.testsection.ticketed
     *
     * @param ticketed the value for public.testsection.ticketed
     *
     * @mbggenerated Wed Dec 12 21:51:26 CST 2012
     */
    public void setTicketed(Boolean ticketed) {
        this.ticketed = ticketed;
    }

    public Boolean getHardBreak() {
        return hardBreak;
    }

    public void setHardBreak(Boolean hardBreak) {
        this.hardBreak = hardBreak;
    }
    
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TestSection other = (TestSection) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getExternalId() == null ? other.getExternalId() == null : this.getExternalId().equals(other.getExternalId()))
            && (this.getTestId() == null ? other.getTestId() == null : this.getTestId().equals(other.getTestId()))
            && (this.getTestSectionName() == null ? other.getTestSectionName() == null : this.getTestSectionName().equals(other.getTestSectionName()))
            && (this.getNumberOfTestItems() == null ? other.getNumberOfTestItems() == null : this.getNumberOfTestItems().equals(other.getNumberOfTestItems()))
            && (this.getHelpNotes() == null ? other.getHelpNotes() == null : this.getHelpNotes().equals(other.getHelpNotes()))
            && (this.getToolsUsageId() == null ? other.getToolsUsageId() == null : this.getToolsUsageId().equals(other.getToolsUsageId()))
            && (this.getTaskDeliveryRuleId() == null ? other.getTaskDeliveryRuleId() == null : this.getTaskDeliveryRuleId().equals(other.getTaskDeliveryRuleId()))
            && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
            && (this.getModifiedDate() == null ? other.getModifiedDate() == null : this.getModifiedDate().equals(other.getModifiedDate()))
            && (this.getOriginationCode() == null ? other.getOriginationCode() == null : this.getOriginationCode().equals(other.getOriginationCode()))
            && (this.getBeginInstructions() == null ? other.getBeginInstructions() == null : this.getBeginInstructions().equals(other.getBeginInstructions()))
            && (this.getEndInstructions() == null ? other.getEndInstructions() == null : this.getEndInstructions().equals(other.getEndInstructions()))
            && (this.getContextStimulusId() == null ? other.getContextStimulusId() == null : this.getContextStimulusId().equals(other.getContextStimulusId()))
            && (this.getTicketed() == null ? other.getTicketed() == null : this.getTicketed().equals(other.getTicketed()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getExternalId() == null) ? 0 : getExternalId().hashCode());
        result = prime * result + ((getTestId() == null) ? 0 : getTestId().hashCode());
        result = prime * result + ((getTestSectionName() == null) ? 0 : getTestSectionName().hashCode());
        result = prime * result + ((getNumberOfTestItems() == null) ? 0 : getNumberOfTestItems().hashCode());
        result = prime * result + ((getHelpNotes() == null) ? 0 : getHelpNotes().hashCode());
        result = prime * result + ((getToolsUsageId() == null) ? 0 : getToolsUsageId().hashCode());
        result = prime * result + ((getTaskDeliveryRuleId() == null) ? 0 : getTaskDeliveryRuleId().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getModifiedDate() == null) ? 0 : getModifiedDate().hashCode());
        result = prime * result + ((getOriginationCode() == null) ? 0 : getOriginationCode().hashCode());
        result = prime * result + ((getBeginInstructions() == null) ? 0 : getBeginInstructions().hashCode());
        result = prime * result + ((getEndInstructions() == null) ? 0 : getEndInstructions().hashCode());
        result = prime * result + ((getContextStimulusId() == null) ? 0 : getContextStimulusId().hashCode());
        result = prime * result + ((getTicketed() == null) ? 0 : getTicketed().hashCode());
        return result;
    }
    
    private List<TestSectionsTaskVariants> testSectionsTaskVariants;

	private StimulusVariant contextStimulus;
	
	 /**
     * @return the testSectionsTaskVariants
     */
    public List<TestSectionsTaskVariants> getTestSectionsTaskVariants() {
        return testSectionsTaskVariants;
    }

    /**
     * @param testSectionsTaskVariants the testSectionsTaskVariants to set
     */
    public void setTestSectionsTaskVariants(List<TestSectionsTaskVariants> testSectionsTaskVariants) {
        this.testSectionsTaskVariants = testSectionsTaskVariants;
    }
    /**
     * @param testSectionsTaskVariant {@link TestSectionsTaskVariants}
     */
    public final void addTestSectionsTaskVariants(TestSectionsTaskVariants testSectionsTaskVariant) {
    	if (getTestSectionsTaskVariants() == null) {
    		this.testSectionsTaskVariants = new ArrayList<TestSectionsTaskVariants>();
    	}
		if (testSectionsTaskVariant != null
				&& testSectionsTaskVariant.getTestSectionId() != null
				&& getId() != null
				&& getId().longValue() == testSectionsTaskVariant.getTestSectionId().longValue()
				) {
			this.testSectionsTaskVariants.add(testSectionsTaskVariant);
		}
	}
    /**
     * @param testSectionsTaskVariantsList the testSectionsTaskVariants to set
     */
    public final void setApplicableTestSectionsTaskVariants(List<TestSectionsTaskVariants> testSectionsTaskVariantsList) {
    	if (testSectionsTaskVariantsList != null && CollectionUtils.isNotEmpty(testSectionsTaskVariantsList)) {
    		for (TestSectionsTaskVariants testSectionsTaskVariant:testSectionsTaskVariantsList) {
    			addTestSectionsTaskVariants(testSectionsTaskVariant);
    		}
    		//set the number of items in the section for sake of correctness.
    		this.setNumberOfTestItems();
    	}
    }

	private void setNumberOfTestItems() {
		if(this.testSectionsTaskVariants != null) {
			this.setNumberOfTestItems(this.testSectionsTaskVariants.size());
		}else {
			this.setNumberOfTestItems(0);
		}
	}
		

	public void setContextStimulus(List<StimulusVariant> stimulusVariants) {
		if (stimulusVariants != null && CollectionUtils.isNotEmpty(stimulusVariants)) {
			for (StimulusVariant contextStim:stimulusVariants) {
				this.setContextStimulus(contextStim);
			}
		}
	}

	public void setContextStimulus(StimulusVariant contextStimulus) {
		this.contextStimulus = contextStimulus;
	}
	
	public StimulusVariant getContextStimulus() {
		return contextStimulus;
	}

	@Override
	public Long getId(int order) {
		return getId();
	}
	
	@Override
	public String getStringIdentifier(int order) {		
		return null;
	}

	public Integer getSectionOrder() {
		return sectionOrder;
	}

	public void setSectionOrder(Integer sectionOrder) {
		this.sectionOrder = sectionOrder;
	}
	@Override
	public String toString() {
		return "TestSection [id=" + id + ", externalId=" + externalId + ", testId=" + testId + ", testSectionName="
				+ testSectionName + ", numberOfTestItems=" + numberOfTestItems + ", helpNotes=" + helpNotes
				+ ", toolsUsageId=" + toolsUsageId + ", taskDeliveryRuleId=" + taskDeliveryRuleId + ", createDate="
				+ createDate + ", modifiedDate=" + modifiedDate + ", originationCode=" + originationCode
				+ ", beginInstructions=" + beginInstructions + ", endInstructions=" + endInstructions
				+ ", contextStimulusId=" + contextStimulusId + ", ticketed=" + ticketed + ", hardBreak=" + hardBreak
				+ ", sectionOrder=" + sectionOrder + ", testSectionsTaskVariants=" + testSectionsTaskVariants
				+ ", contextStimulus=" + contextStimulus + "]";
	}
}