package edu.ku.cete.domain.common;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author 
 * TODO what is the meaning of operational test window.
 * Operational test window is applied for a test collection to prevent or allow the test collection usage in
 * the field i.e for the students in TDE.
 */
public class OperationalTestWindow extends TraceableEntity implements Serializable {
 
 /**
  * 
  */
 private static final long serialVersionUID = 2758126925468505345L; 
 /**
  * 
  */
 private Long testCollectionId;
 
 
 
 private Long gracePeriodInMin;
 
 private boolean ticketingOfTheDayFlag;
/**
  * 
  */
 private boolean ticketAtSectionFlag;
 
 private boolean testEnrollmentFlag;
 
 private Long[] testCollectionIds;
 /**
  * 
  */
 private String windowName;
 /**
  * 
  */
 private boolean ticketingFlag;
 /**
  * 
  */
 private boolean scoringWindowFlag;
 private String scoringWindowMethodName;
 private Long scoringWindowMethodId;
 private Date scoringWindowStartDate;
 private Date scoringWindowEndDate;
 /**
  * 
  */
 private boolean requiredToCompleteTest;
 /**
  * 
  */
 private Date effectiveDate;
 
 /**
  * 
  */
 private Date expiryDate;
 
 /**
  * 
  */
 private boolean suspendWindow;
 /**
  * 
  */
 private Date modifiedDate;
 
/**
  * 
  */
 private Long id;
    /**
     * 
     */ 
 private boolean managedBySystemFlag;
 /**
  * 
  */
private boolean gracePeriodFlag;
 /**
     * 
     */ 
    private boolean radomizationAtLoginFlag; 
 /**
  * @return
  */
 private Long  multipleStateId;
 
 private Long[] multipleStateIds;
 
 private String stateName;
 
 private Long testEnrollmentMethodId;
 
 private String testEnrollmentMethodName;
    
 private Long assessmentProgramId;
 
 private boolean dacFlag;
 private Time dacStartTime;
 private Time dacEndTime;
 
private Date dacStartDateTime;
 
private Date dacEndDateTime;

private Boolean instructionPlannerWindow;
private String instructionPlannerDisplayName;
private String instructionPlannerDirections;
 
 
 public Date getDacStartDateTime() {
	return dacStartDateTime;
}

public void setDacStartDateTime(Date dacStartDateTime) {
	this.dacStartDateTime = dacStartDateTime;
}

public Date getDacEndDateTime() {
	return dacEndDateTime;
}

public void setDacEndDateTime(Date dacEndDateTime) {
	this.dacEndDateTime = dacEndDateTime;
}

public Long getTestCollectionId() {
  return testCollectionId;
 }

 public Long[] getMultipleStateIds() {
	return multipleStateIds;
}

public void setMultipleStateIds(Long[] multipleStateIds) {
	this.multipleStateIds = multipleStateIds;
}

public Long getMultipleStateId() {
	return multipleStateId;
}

public void setMultipleStateId(Long multipleStateId) {
	this.multipleStateId = multipleStateId;
}


public String getStateName() {
	return stateName;
}

public void setStateName(String stateName) {
	this.stateName = stateName;
}

public Long getAssessmentProgramId() {
	return assessmentProgramId;
}

public void setAssessmentProgramId(Long assessmentProgramId) {
	this.assessmentProgramId = assessmentProgramId;
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
 public String getWindowName() {
  return windowName;
 }

 /**
  * @param windowName
  */
 public void setWindowName(String windowName) {
  this.windowName = windowName;
 }

 /**
  * @return
  */
 public boolean getTicketingFlag() {
  return ticketingFlag;
 }

 /**
  * @param ticketingFlag
  */
 public void setTicketingFlag(boolean ticketingFlag) {
  this.ticketingFlag = ticketingFlag;
 }

 public boolean isTicketingOfTheDayFlag() {
	return ticketingOfTheDayFlag;
}

public void setTicketingOfTheDayFlag(boolean ticketingOfTheDayFlag) {
	this.ticketingOfTheDayFlag = ticketingOfTheDayFlag;
}

public boolean isRequiredToCompleteTest() {
  return requiredToCompleteTest;
 }

 public void setRequiredToCompleteTest(boolean testExitFlag) {
  this.requiredToCompleteTest = testExitFlag;
 }

 /**
  * @return
  */
 public Date getEffectiveDate() {
  return effectiveDate;
 }

 /**
  * @param effectiveDate
  */
 public void setEffectiveDate(Date effectiveDate) {
  this.effectiveDate = effectiveDate;
 }

 /**
  * @return
  */
 public Date getExpiryDate() {
  return expiryDate;
 }

 /**
  * @param expiryDate
  */
 public void setExpiryDate(Date expiryDate) {
  this.expiryDate = expiryDate;
 }

 /**
  * @return
  */
 public boolean isSuspendWindow() {
  return suspendWindow;
 }

 /**
  * @param suspendWindow
  */
 public void setSuspendWindow(boolean suspendWindow) {
  this.suspendWindow = suspendWindow;
 }

 /**
  * @return
  */
 public Date getModifiedDate() {
  return modifiedDate;
 }

 /**
  * @param modifiedDate
  */
 public void setModifiedDate(Date modifiedDate) {
  this.modifiedDate = modifiedDate;
 }

 /**
  * @return
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
 
 @Override
 public String getIdentifier() {
  return getId() + ParsingConstants.BLANK;
 }

 /**
  * @return {@link String}
  */
 public final String toString() {
  return ToStringBuilder.reflectionToString(this);
 }
 
 
    public Long getGracePeriodInMin() {
		return gracePeriodInMin;
	}

	public void setGracePeriodInMin(Long gracePeriodInMin) {
		this.gracePeriodInMin = gracePeriodInMin;
	}

    /**
     * @return
     */ 
    public boolean isManagedBySystemFlag() {
        return managedBySystemFlag;
    }

    /**
     * @param managedBySystemFlag
     */    
    public void setManagedBySystemFlag(boolean managedBySystemFlag) {
        this.managedBySystemFlag = managedBySystemFlag;
    }

    /**
     * @return
     */
    public boolean isRadomizationAtLoginFlag() {
        return radomizationAtLoginFlag;
    }

    public boolean isGracePeriodFlag() {
    	return gracePeriodFlag;
    }

    public void setGracePeriodFlag(boolean gracePeriodFlag) {
    	this.gracePeriodFlag = gracePeriodFlag;
    }

    /**
     * @param radomizationAtLoginFlag
     */    
    public void setRadomizationAtLoginFlag(boolean radomizationAtLoginFlag) {
        this.radomizationAtLoginFlag = radomizationAtLoginFlag;
    }
 
 public Long[] getTestCollectionIds() {
  return testCollectionIds;
 }

 public void setTestCollectionIds(Long[] testCollectionIds) {
  this.testCollectionIds = testCollectionIds;
 }

 public Long getTestEnrollmentMethodId() {
	return testEnrollmentMethodId;
}

public void setTestEnrollmentMethodId(Long testEnrollmentMethodId) {
	this.testEnrollmentMethodId = testEnrollmentMethodId;
}
public String getTestEnrollmentMethodName() {
	return testEnrollmentMethodName;
}

public void setTestEnrollmentMethodName(String testEnrollmentMethodName) {
	this.testEnrollmentMethodName = testEnrollmentMethodName;
}

public boolean isTestEnrollmentFlag() {
	return testEnrollmentFlag;
}

public void setTestEnrollmentFlag(boolean testEnrollmentFlag) {
	this.testEnrollmentFlag = testEnrollmentFlag;
}

public boolean isTicketAtSectionFlag() {
	return ticketAtSectionFlag;
}

public void setTicketAtSectionFlag(boolean ticketAtSectionFlag) {
	this.ticketAtSectionFlag = ticketAtSectionFlag;
}

public Time getDacStartTime() {
	return dacStartTime;
}

public void setDacStartTime(Time dacStartTime) {
	this.dacStartTime = dacStartTime;
}

public Time getDacEndTime() {
	return dacEndTime;
}

public void setDacEndTime(Time dacEndTime) {
	this.dacEndTime = dacEndTime;
}

public boolean isDacFlag() {
	return dacFlag;
}

public void setDacFlag(boolean dacFlag) {
	this.dacFlag = dacFlag;
}


public Long getScoringWindowMethodId() {
	return scoringWindowMethodId;
}

public void setScoringWindowMethodId(Long scoringWindowMethodId) {
	this.scoringWindowMethodId = scoringWindowMethodId;
}

public boolean isScoringWindowFlag() {
	return scoringWindowFlag;
}

public void setScoringWindowFlag(boolean scoringWindowFlag) {
	this.scoringWindowFlag = scoringWindowFlag;
}

public String getScoringWindowMethodName() {
	return scoringWindowMethodName;
}

public void setScoringWindowMethodName(String scoringWindowMethodName) {
	this.scoringWindowMethodName = scoringWindowMethodName;
}

public Date getScoringWindowStartDate() {
	return scoringWindowStartDate;
}

public void setScoringWindowStartDate(Date scoringWindowStartDate) {
	this.scoringWindowStartDate = scoringWindowStartDate;
}

public Date getScoringWindowEndDate() {
	return scoringWindowEndDate;
}

public void setScoringWindowEndDate(Date scoringWindowEndDate) {
	this.scoringWindowEndDate = scoringWindowEndDate;
}

public Boolean getInstructionPlannerWindow() {
	return instructionPlannerWindow;
}

public void setInstructionPlannerWindow(Boolean instructionPlannerWindow) {
	this.instructionPlannerWindow = instructionPlannerWindow;
}

public String getInstructionPlannerDisplayName() {
	return instructionPlannerDisplayName;
}

public void setInstructionPlannerDisplayName(String instructionPlannerDisplayName) {
	this.instructionPlannerDisplayName = instructionPlannerDisplayName;
}

public String getInstructionPlannerDirections() {
	return instructionPlannerDirections;
}

public void setInstructionPlannerDirections(String instructionPlannerDirections) {
	this.instructionPlannerDirections = instructionPlannerDirections;
}



}