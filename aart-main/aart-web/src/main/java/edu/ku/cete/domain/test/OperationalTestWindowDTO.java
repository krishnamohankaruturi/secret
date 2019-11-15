package edu.ku.cete.domain.test;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;
/**
 * Added for User test window Grid Displaying
 * By Manoj Kumar O
 */
public class OperationalTestWindowDTO {

	
	private Long otwId;
	private String windowName;
	private String managedBy;
	private String randomization;
	private Date beginDate ;
	private Date endDate;   						
	private Date createdDate;
	private Date  modifiedDate;
	private String createdBy;
	private String modifiedBy;
	private Long testOperationalWindowId;
	private Date beginTestTime ;
	private Date endTestTime;   						
	private String testCategoryCode;
	private Boolean suspendwindow;	
	private String stateNames;
	private String testEnrollmentMethodName;
	private Boolean testEnrollmentFlag;
	private String ticketing;
	private String ticketingOfTheDay;
	private String dailyAccessCode;
	private Boolean scoringWindowFlag;
	private String scoringWindowMethodName;
	private Date scoringWindowStartDate;
	private Date scoringWindowEndDate; 	
	private Date scoringWindowStartTime;
	private Date scoringWindowEndTime;
	private String status;
	
	private Boolean instructionPlannerWindow;
	private String instructionPlannerDisplayName;
	private String instructionPlannerDirections;
	
	public Date getScoringWindowStartTime() {
		return scoringWindowStartTime;
	}
	public void setScoringWindowStartTime(Date scoringWindowStartTime) {
		this.scoringWindowStartTime = scoringWindowStartTime;
	}
	public Date getScoringWindowEndTime() {
		return scoringWindowEndTime;
	}
	public void setScoringWindowEndTime(Date scoringWindowEndTime) {
		this.scoringWindowEndTime = scoringWindowEndTime;
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
	public Boolean getScoringWindowFlag() {
		return scoringWindowFlag;
	}
	public void setScoringWindowFlag(Boolean scoringWindowFlag) {
		this.scoringWindowFlag = scoringWindowFlag;
	}
	public String getScoringWindowMethodName() {
		return scoringWindowMethodName;
	}
	public void setScoringWindowMethodName(String scoringWindowMethodName) {
		this.scoringWindowMethodName = scoringWindowMethodName;
	}
	public Boolean getSuspendwindow() {
		return suspendwindow;
	}
	public void setSuspendwindow(Boolean suspendwindow) {
		this.suspendwindow = suspendwindow;
	}
	public String getStateNames() {
		return stateNames;
	}
	public void setStateNames(String stateNames) {
		this.stateNames = stateNames;
	}
	public String getTestEnrollmentMethodName() {
		return testEnrollmentMethodName;
	}
	public void setTestEnrollmentMethodName(String testEnrollmentMethodName) {
		this.testEnrollmentMethodName = testEnrollmentMethodName;
	}
	public Boolean getTestEnrollmentFlag() {
		return testEnrollmentFlag;
	}
	public void setTestEnrollmentFlag(Boolean testEnrollmentFlag) {
		this.testEnrollmentFlag = testEnrollmentFlag;
	}
	public String getTestCategoryCode() {
		return testCategoryCode;
	}
	public void setTestCategorycode(String testCategoryCode) {
		this.testCategoryCode = testCategoryCode;
	}
	public Long getTestOperationalWindowId() {
		return testOperationalWindowId;
	}
	public void setTestOperationalWindowId(Long testOperationalWindowId) {
		this.testOperationalWindowId = testOperationalWindowId;
	}
	
	public String getWindowName() {
		return windowName;
	}
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	
	public String getManagedBy() {
		return managedBy;
	}
	public void setManagedBy(String managedBy) {
		this.managedBy = managedBy;
	}
	
	
	public String getRandomization() {
		return randomization;
	}
	public void setRandomization(String randomization) {
		this.randomization = randomization;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	
	
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Long getOtwId() {
		return otwId;
	}
	public void setOtwId(Long otwId) {
		this.otwId = otwId;
	}
	
	public Date getBeginTestTime() {
		return beginTestTime;
	}
	public void setBeginTestTime(Date beginTestTime) {
		this.beginTestTime = beginTestTime;
	}
	public Date getEndTestTime() {
		return endTestTime;
	}
	public void setEndTestTime(Date endTestTime) {
		this.endTestTime = endTestTime;
	}

	public String getTicketing() {
		return ticketing;
	}
	public void setTicketing(String ticketing) {
		this.ticketing = ticketing;
	}
	public String getTicketingOfTheDay() {
		return ticketingOfTheDay;
	}
	public void setTicketingOfTheDay(String ticketingOfTheDay) {
		this.ticketingOfTheDay = ticketingOfTheDay;
	}
	public String getDailyAccessCode() {
		return dailyAccessCode;
	}
	public void setDailyAccessCode(String dailyAccessCode) {
		this.dailyAccessCode = dailyAccessCode;
	}
	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();
		
		//Set OtwId
		if(getOtwId() != null) {
			cells.add(ParsingConstants.BLANK + getOtwId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set suspendWindow
		if(getSuspendwindow() != null) {
			cells.add(ParsingConstants.BLANK + getSuspendwindow());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set Window Name
		if(getWindowName() != null) {
			cells.add(ParsingConstants.BLANK + getWindowName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
				
		//Set Managed By
		if(getManagedBy() != null) {
			cells.add(ParsingConstants.BLANK + getManagedBy());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
				
		//Set Randomization
		if(getRandomization() != null) {
			cells.add(ParsingConstants.BLANK + getRandomization());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set begin Date
		if(getBeginDate() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.convertDatetoSpecificTimeZoneStringFormat(getBeginDate(),"US/Central", "MM/dd/yyyy"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set EndDate
		if(getEndDate() != null) {
			cells.add(ParsingConstants.BLANK +DateUtil.convertDatetoSpecificTimeZoneStringFormat(getEndDate(),"US/Central", "MM/dd/yyyy"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set Created Date
		if(getCreatedDate() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.format(getCreatedDate(), "MM/dd/yyyy"));
		}else{
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		//Set Modified Date
		if(getModifiedDate() != null) {
			cells.add(ParsingConstants.BLANK +DateUtil.format(getModifiedDate(), "MM/dd/yyyy"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set Created by
		if(getCreatedBy() != null) {
			cells.add(ParsingConstants.BLANK + getCreatedBy());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set Modified by
		if(getModifiedBy() != null) {
			cells.add(ParsingConstants.BLANK + getModifiedBy());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set Opertional Test window Id
		if(getTestOperationalWindowId() != null) {
			cells.add(ParsingConstants.BLANK + getTestOperationalWindowId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		//Set StartTime
		if(getBeginTestTime() != null) {		
				cells.add(ParsingConstants.BLANK + DateUtil.convertDatetoSpecificTimeZoneStringFormat(getBeginTestTime(),"US/Central", "hh:mm:ss a"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set EndTime
		if(getEndTestTime() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.convertDatetoSpecificTimeZoneStringFormat(getEndTestTime(),"US/Central", "hh:mm:ss a"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getTestCategoryCode() != null) {
			cells.add(ParsingConstants.BLANK + getTestCategoryCode());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getStateNames() != null) {
			cells.add(ParsingConstants.BLANK + getStateNames());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getTestEnrollmentMethodName() != null && !getTestEnrollmentMethodName().isEmpty()) {
			cells.add(ParsingConstants.BLANK + getTestEnrollmentMethodName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getTestEnrollmentFlag() != null) {
			if(getTestEnrollmentFlag() == true){
				cells.add(ParsingConstants.BLANK + "On");	
			}else{
				cells.add(ParsingConstants.BLANK + "Off");	
			}
		} else {
			cells.add(ParsingConstants.BLANK + "Off");	
		}
		
		/*if(getTicketing() != null) {
			cells.add(ParsingConstants.BLANK + getTicketing());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getTicketingOfTheDay() != null) {
			cells.add(ParsingConstants.BLANK + getTicketingOfTheDay());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}*/
		if(getDailyAccessCode() != null) {
			cells.add(ParsingConstants.BLANK + getDailyAccessCode());
		} else {
			cells.add(ParsingConstants.NONE);
		}
		
		if(getScoringWindowFlag() != null) {
			if(getScoringWindowFlag() == true){
				cells.add(ParsingConstants.BLANK + "On");	
			}else{
				cells.add(ParsingConstants.BLANK + "Off");	
			}
		} else {
			cells.add(ParsingConstants.BLANK + "Off");	
		}
		
		if(getScoringWindowMethodName() != null && !getScoringWindowMethodName().isEmpty()) {
			cells.add(ParsingConstants.BLANK + getScoringWindowMethodName());
		} else {
			cells.add(ParsingConstants.NONE);
		}
		
		if(getScoringWindowStartDate() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.convertDatetoSpecificTimeZoneStringFormat(getScoringWindowStartDate(),"US/Central", "MM/dd/yyyy"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getScoringWindowEndDate() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.convertDatetoSpecificTimeZoneStringFormat(getScoringWindowEndDate(),"US/Central", "MM/dd/yyyy"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getScoringWindowStartTime() != null) {
			cells.add(ParsingConstants.BLANK +DateUtil.convertDatetoSpecificTimeZoneStringFormat(getScoringWindowStartTime(),"US/Central", "hh:mm:ss a"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getScoringWindowEndTime() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.convertDatetoSpecificTimeZoneStringFormat(getScoringWindowEndTime(),"US/Central", "hh:mm:ss a"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getStatus() != null) {
			cells.add(ParsingConstants.BLANK + getStatus());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if (Boolean.TRUE.equals(getInstructionPlannerWindow())) {
			cells.add("On"); // window
			cells.add(getInstructionPlannerDisplayName() == null ? ParsingConstants.BLANK : getInstructionPlannerDisplayName()); // display name
		} else {
			cells.add("Off"); // window
			cells.add(ParsingConstants.BLANK); // display name
		}
		
		return cells;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
