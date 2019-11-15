package edu.ku.cete.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author vittaly
 *
 */
public class TestCollectionDTO extends AuditableDomain implements Identifiable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 6117974675830411188L;

	/**
     * id
     */
    private Long id;
    
    /**
     * testCollectionId
     */
    private Long testCollectionId;
    
    /**
     * testId
     */
    private Long testId;
   
    private Long externalId;
    /**
     * name
     */
    private String name;

    /**
     * assessmentProgramId
     */
    private Long assessmentProgramId;
    
    /**
     * assessmentProgramName
     */
    private String assessmentProgramName;
    
    /**
     * testingProgramId
     */
    private Long testingProgramId;
    
    /**
     * testingProgramName
     */
    private String testingProgramName;
    
    /**
     * assessmentId
     */
    private Long assessmentId;
    
    /**
     * assessmentName
     */
    private String assessmentName;
    
    /**
     * gradeCourseId
     */
    private Long gradeCourseId;
    
    /**
     * gradeCourseName 
     */
    private String gradeCourseName;
    
    /**
     * contentAreaId
     */
    private Long contentAreaId;
    
    /**
     * contentAreaName
     */
    private String contentAreaName;

    /**
     * randomizationType
     */
    private String randomizationType;
    
    /**
     * minimumNumberOfItems
     */
    private Integer minimumNumberOfItems;
    
    /**
     * maximumNumberOfItems
     */
    private Integer maximumNumberOfItems;
        
    /**
     * qcComplete
     */
    private Boolean qcComplete;
    
    /**
     * highStake
     */
    private Boolean highStake;
    
    /**
     * canPreview
     */
    private Boolean canPreview = true;
        
    /**
     * systemFlag
     */
    private Boolean systemFlag;
       
    /**
     * manualScoring
     */
    private Boolean manualScoring = false;
        
	/**
	 * collectionName
	 */
	private String collectionName;
	
	 /**
     * gradeCourseId
     */
    private Long gradeBandId;
    
    /**
     * gradeCourseName 
     */
    private String gradeBandName;
    
    private String testInternalName;
    
    private Boolean expiredFlag;
    
    private Boolean dlmAssociation;
    
	/**
	 * @return the dlmAssociation
	 */
	public Boolean getDlmAssociation() {
		return dlmAssociation;
	}

	/**
	 * @param dlmAssociation the dlmAssociation to set
	 */
	public void setDlmAssociation(Boolean dlmAssociation) {
		this.dlmAssociation = dlmAssociation;
	}

	public Long getExternalId() {
		return externalId;
	}

	public void setExternalId(Long externalId) {
		this.externalId = externalId;
	}

	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.property.Identifiable#getId()
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @return
	 */
	public Long getTestCollectionId() {
		return testCollectionId;
	}

	/**
	 * @param testCollectionId
	 */
	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}

	/**
	 * @return
	 */
	public Long getTestId() {
		return testId;
	}

	/**
	 * @param testId
	 */
	public void setTestId(Long testId) {
		this.testId = testId;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	/**
	 * @param assessmentProgramId
	 */
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	/**
	 * @return
	 */
	public String getAssessmentProgramName() {
		return assessmentProgramName;
	}

	/**
	 * @param assessmentProgramName
	 */
	public void setAssessmentProgramName(String assessmentProgramName) {
		this.assessmentProgramName = assessmentProgramName;
	}

	/**
	 * @return
	 */
	public Long getTestingProgramId() {
		return testingProgramId;
	}

	/**
	 * @param testingProgramId
	 */
	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	/**
	 * @return
	 */
	public String getTestingProgramName() {
		return testingProgramName;
	}

	/**
	 * @param testingProgramName
	 */
	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}

	/**
	 * @return
	 */
	public Long getAssessmentId() {
		return assessmentId;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    	}

	/**
	 * @param assessmentId
	 */
	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	/**
	 * @return
	 */
	public String getAssessmentName() {
		return assessmentName;
	}

	/**
	 * @param assessmentName
	 */
	public void setAssessmentName(String assessmentName) {
		this.assessmentName = assessmentName;
	}

	/**
	 * @return
	 */
	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	/**
	 * @param gradeCourseId
	 */
	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	/**
	 * @return
	 */
	public String getGradeCourseName() {
		return gradeCourseName;
	}

	/**
	 * @param gradeCourseName
	 */
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}

	/**
	 * @return
	 */
	public Long getContentAreaId() {
		return contentAreaId;
	}

	/**
	 * @param contentAreaId
	 */
	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	/**
	 * @return
	 */
	public String getContentAreaName() {
		return contentAreaName;
	}

	/**
	 * @param contentAreaName
	 */
	public void setContentAreaName(String contentAreaName) {
		this.contentAreaName = contentAreaName;
	}

	/**
	 * @return
	 */
	public String getRandomizationType() {
		return randomizationType;
	}

	/**
	 * @param randomizationType
	 */
	public void setRandomizationType(String randomizationType) {
		this.randomizationType = randomizationType;
	}

	/**
	 * @return
	 */
	public Integer getMinimumNumberOfItems() {
		return minimumNumberOfItems;
	}

	/**
	 * @param minimumNumberOfItems
	 */
	public void setMinimumNumberOfItems(Integer minimumNumberOfItems) {
		this.minimumNumberOfItems = minimumNumberOfItems;
	}

	/**
	 * @return
	 */
	public Integer getMaximumNumberOfItems() {
		return maximumNumberOfItems;
	}

	/**
	 * @param maximumNumberOfItems
	 */
	public void setMaximumNumberOfItems(Integer maximumNumberOfItems) {
		this.maximumNumberOfItems = maximumNumberOfItems;
	}

	/**
	 * @return
	 */
	public Boolean getQcComplete() {
		return qcComplete;
	}

	/**
	 * @param qcComplete
	 */
	public void setQcComplete(Boolean qcComplete) {
		this.qcComplete = qcComplete;
	}

	/**
	 * @return
	 */
	public Boolean getHighStake() {
		return highStake;
	}

	/**
	 * @param highStake
	 */
	public void setHighStake(Boolean highStake) {
		this.highStake = highStake;
	}

	/**
	 * @return
	 */
	public Boolean getCanPreview() {
		return canPreview;
	}

	/**
	 * @param canPreview
	 */
	public void setCanPreview(Boolean canPreview) {
		this.canPreview = canPreview;
	}

	/**
	 * @return
	 */
	public Boolean isSystemFlag() {
		return systemFlag;
	}

	/**
	 * @param systemFlag
	 */
	public void setSystemFlag(Boolean systemFlag) {
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
	 * @return the collectionName
	 */
	public String getCollectionName() {
		return collectionName;
	}

	/**
	 * @param collectionName the collectionName to set
	 */
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public Long getGradeBandId() {
		return gradeBandId;
	}

	public void setGradeBandId(Long gradeBandId) {
		this.gradeBandId = gradeBandId;
	}

	public String getGradeBandName() {
		return gradeBandName;
	}

	public void setGradeBandName(String gradeBandName) {
		this.gradeBandName = gradeBandName;
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
	
	/**
	 * Method to construct the json list for select assessments record browser.
	 * 
	 * @return
	 */
	public List<String> buildJSONRow(boolean hasHighStakesPermission, boolean hasQCCompletePermission) {
		List<String> cells = new ArrayList<String>();

		if(getId() != null) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getTestCollectionId() != null) {
			cells.add(ParsingConstants.BLANK + getTestCollectionId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getExternalId() != null){
			cells.add(ParsingConstants.BLANK + getExternalId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		
		if(getTestId() != null) {
			cells.add(ParsingConstants.BLANK + getTestId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}			
		
		if(getName() != null &&  
				StringUtils.isNotEmpty(getName())) {
			cells.add(ParsingConstants.BLANK + getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if (hasQCCompletePermission){
			if(getTestInternalName() != null) {
				cells.add(ParsingConstants.BLANK + getTestInternalName());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
		}
		
		if(getCollectionName() != null &&  
				StringUtils.isNotEmpty(getCollectionName())) {
			cells.add(ParsingConstants.BLANK + getCollectionName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getAssessmentProgramName() != null &&  
				StringUtils.isNotEmpty(getAssessmentProgramName())) {
			cells.add(ParsingConstants.BLANK + getAssessmentProgramName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getTestingProgramName() != null &&  
				StringUtils.isNotEmpty(getTestingProgramName())) {
			cells.add(ParsingConstants.BLANK + getTestingProgramName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getAssessmentName() != null &&  
				StringUtils.isNotEmpty(getAssessmentName())) {
			cells.add(ParsingConstants.BLANK + getAssessmentName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getContentAreaName() != null &&  
				StringUtils.isNotEmpty(getContentAreaName())) {
			cells.add(ParsingConstants.BLANK + getContentAreaName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getGradeCourseName() != null &&  
				StringUtils.isNotEmpty(getGradeCourseName())) {
			cells.add(ParsingConstants.BLANK + getGradeCourseName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getGradeBandName() != null &&  
				StringUtils.isNotEmpty(getGradeBandName())) {
			cells.add(ParsingConstants.BLANK + getGradeBandName());
		} else {
			cells.add(ParsingConstants.NOT_APPLICABLE);
		}			
		
		if (hasQCCompletePermission){
			if(getQcComplete() != null) {
				cells.add(ParsingConstants.BLANK + getQcComplete());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
		}
		
		if (hasHighStakesPermission){
			if(getHighStake() != null) {
				cells.add(ParsingConstants.BLANK + getHighStake());
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
		}
		
		if(getManualScoring() != null) {
			cells.add(ParsingConstants.BLANK + getManualScoring());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		cells.add(ParsingConstants.BLANK + getCanPreview());
		//cells.add(ParsingConstants.BLANK + isSystemFlag());
		
		cells.add(ParsingConstants.BLANK + isExpiredFlag());
		
		cells.add(ParsingConstants.BLANK + getDlmAssociation());
		
		return cells;
	}

	public String getTestInternalName() {
		return testInternalName;
	}

	public void setTestInternalName(String testInternalName) {
		this.testInternalName = testInternalName;
	}

	public Boolean isExpiredFlag() {
		return expiredFlag;
	}

	public void setExpiredFlag(Boolean expiredFlag) {
		this.expiredFlag = expiredFlag;
	}
	
}
