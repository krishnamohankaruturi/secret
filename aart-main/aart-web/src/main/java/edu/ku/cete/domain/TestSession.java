package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.enrollment.Roster;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class TestSession extends AuditableDomain{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.id
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Long id;

	/**
	 * This field was generated by MyBatis Generator. This field correspondsexpi to the database column testsession.rosterid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Long rosterId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.name
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private String name;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.status
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Long status;


	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.activeflag
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Boolean activeFlag;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.testid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Long testId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.testcollectionid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Long testCollectionId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.source
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private String source;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.attendanceschoolid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Long attendanceSchoolId;

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column testsession.operationaltestwindowid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	private Long operationalTestWindowId;
	
	private Long testTypeId;

	private Long gradeCourseId;	
	private Long stageId;	
	
	private Long schoolYear;
	
	private Long testPanelId;
	
	private Long subjectAreaId;
	
	private Timestamp windowExpiryDate;
	
	private Timestamp windowEffectiveDate;
	
	private Time windowStartTime;
	 
	private Time  windowEndTime;
	
	private String stageCode;	
	private String windowExpiryDateString;
	private String windowEffectiveDateString;
	private String windowStartTimeString;
	private String windowEndTimeString;
	
	private Integer currentTestNumber;
    
    private Integer numberOfTestsRequired;

    private Long gradeBandId;
    
	public Timestamp getWindowEffectiveDate() {
		return windowEffectiveDate;
	}

	public void setWindowEffectiveDate(Timestamp windowEffectiveDate) {
		this.windowEffectiveDate = windowEffectiveDate;
	}

	public Time getWindowStartTime() {
		return windowStartTime;
	}

	public void setWindowStartTime(Time windowStartTime) {
		this.windowStartTime = windowStartTime;
	}

	public Time getWindowEndTime() {
		return windowEndTime;
	}

	public void setWindowEndTime(Time windowEndTime) {
		this.windowEndTime = windowEndTime;
	}

	
	
	public Timestamp getWindowExpiryDate() {
		return windowExpiryDate;
	}

	public void setWindowExpiryDate(Timestamp windowExpiryDate) {
		this.windowExpiryDate = windowExpiryDate;
	}

	
	
	
	
	

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.id
	 * @return  the value of testsession.id
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.id
	 * @param id  the value for testsession.id
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.rosterid
	 * @return  the value of testsession.rosterid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Long getRosterId() {
		return rosterId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.rosterid
	 * @param rosterId  the value for testsession.rosterid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.name
	 * @return  the value of testsession.name
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.name
	 * @param name  the value for testsession.name
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.status
	 * @return  the value of testsession.status
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.status
	 * @param status  the value for testsession.status
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setStatus(Long status) {
		this.status = status;
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.activeflag
	 * @return  the value of testsession.activeflag
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Boolean getActiveFlag() {
		return activeFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.activeflag
	 * @param activeFlag  the value for testsession.activeflag
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setActiveFlag(Boolean activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.testid
	 * @return  the value of testsession.testid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Long getTestId() {
		return testId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.testid
	 * @param testId  the value for testsession.testid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setTestId(Long testId) {
		this.testId = testId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.testcollectionid
	 * @return  the value of testsession.testcollectionid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Long getTestCollectionId() {
		return testCollectionId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.testcollectionid
	 * @param testCollectionId  the value for testsession.testcollectionid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.source
	 * @return  the value of testsession.source
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public String getSource() {
		return source;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.source
	 * @param source  the value for testsession.source
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setSource(String source) {
		this.source = source == null ? null : source.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.attendanceschoolid
	 * @return  the value of testsession.attendanceschoolid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.attendanceschoolid
	 * @param attendanceSchoolId  the value for testsession.attendanceschoolid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column testsession.operationaltestwindowid
	 * @return  the value of testsession.operationaltestwindowid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column testsession.operationaltestwindowid
	 * @param operationalTestWindowId  the value for testsession.operationaltestwindowid
	 * @mbggenerated  Wed Dec 10 14:58:33 CST 2014
	 */
	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

    private Category statusCategory;

    private Roster roster;
    
    private Long finalBandId;

    /**
     * @return the statusCategory
     */
    public Category getStatusCategory() {
        return statusCategory;
    }

    /**
     * @param statusCategory the statusCategory to set
     */
    public void setStatusCategory(Category statusCategory) {
        this.statusCategory = statusCategory;
    }

    /**
     * @return the roster
     */
    public Roster getRoster() {
        return roster;
    }

    /**
     * @param roster the roster to set
     */
    public void setRoster(Roster roster) {
        this.roster = roster;
    }

	public Long getFinalBandId() {
		return finalBandId;
	}

	public void setFinalBandId(Long finalBandId) {
		this.finalBandId = finalBandId;
	}

	public Long getTestTypeId() {
		return testTypeId;
	}

	public void setTestTypeId(Long testTypeId) {
		this.testTypeId = testTypeId;
	}

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	public Long getStageId() {
		return stageId;
	}

	public void setStageId(Long stageId) {
		this.stageId = stageId;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getTestPanelId() {
		return testPanelId;
	}

	public void setTestPanelId(Long testPanelId) {
		this.testPanelId = testPanelId;
	}

	public Long getSubjectAreaId() {
		return subjectAreaId;
	}

	public void setSubjectAreaId(Long subjectAreaId) {
		this.subjectAreaId = subjectAreaId;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}  
	public String getWindowExpiryDateString() {
		if (this.windowEffectiveDate != null) {
			SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
			return df2.format(this.windowExpiryDate);
		} else {
			return null;
		}
	}

	public void setWindowExpiryDateString(String windowExpiryDateString) {
		this.windowExpiryDateString = windowExpiryDateString;
	}

	public String getWindowEffectiveDateString() {
		if (this.windowEffectiveDate != null) {
			SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yyyy");
			return df2.format(this.windowEffectiveDate);
		} else {
			return null;
		}
	}

	public void setWindowEffectiveDateString(String windowEffectiveDateString) {
		this.windowEffectiveDateString = windowEffectiveDateString;
	}

	public String getWindowStartTimeString() {
		if (this.windowStartTime != null) {
			DateFormat formatter = new SimpleDateFormat("hh:mm aa");
			return formatter.format(this.windowStartTime);
		} else {
			return null;
		}
	}

	public void setWindowStartTimeString(String windowStartTimeString) {
		this.windowStartTimeString = windowStartTimeString;
	}

	public String getWindowEndTimeString() {
		if (this.windowEndTime != null) {
			DateFormat formatter = new SimpleDateFormat("hh:mm aa");
			return formatter.format(this.windowEndTime);
		} else {
			return null;
		}
	}

	public void setWindowEndTimeString(String windowEndTimeString) {
		this.windowEndTimeString = windowEndTimeString;
	}
	

	public Integer getCurrentTestNumber() {
		return currentTestNumber;
	}

	public void setCurrentTestNumber(Integer currentTestNumber) {
		this.currentTestNumber = currentTestNumber;
	}

	public Integer getNumberOfTestsRequired() {
		return numberOfTestsRequired;
	}

	public void setNumberOfTestsRequired(Integer numberOfTestsRequired) {
		this.numberOfTestsRequired = numberOfTestsRequired;
	}

	public Long getGradeBandId() {
		return gradeBandId;
	}

	public void setGradeBandId(Long gradeBandId) {
		this.gradeBandId = gradeBandId;
	}	
}