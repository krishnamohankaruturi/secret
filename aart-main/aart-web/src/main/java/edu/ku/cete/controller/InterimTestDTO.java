package edu.ku.cete.controller;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author vkrishna_sta
 *
 */
public class InterimTestDTO implements Serializable {

	private static final long serialVersionUID = -2434458458247805869L;
	private Long serial;
	private long id;
	private long interimTestId;
	private long assessmentId;
	private long subjectId;
	private long gradeId;
	private long externalId;

	private String testDescription;
	private String testName;
	private String assembledBy;
	@JsonInclude(Include.ALWAYS)
	private Timestamp expiryDate;
	@JsonInclude(Include.ALWAYS)
	private Timestamp effectiveDate;
	private boolean windowComplete;
	
	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Timestamp getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Timestamp effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	private String windowExpiryDateString;
	private String windowEffectiveDateString;
	private String windowStartTimeString;
	private String windowEndTimeString;
	private String scheduled;
	private String startTimeCT;
	private String schoolName;
	private int numItems;
	private String accessibilityFlagCode;
	private String testInternalName;
	private Boolean isInterimTest;
	private Boolean isTestAssigned;
	private String testStatus;
	@JsonInclude(Include.ALWAYS)
	private Long studentsAssigned;
	@JsonInclude(Include.ALWAYS)
	private Long studentsAttempted;
	private Long testSessionId;
	private Date createDate;
	private Date modifiedDate;
	private String createdDateString;

	private String originationCode;
	private String directions;
	private String uiTypeCode;
	private String reviewText;
	private Long gradeCourseId;
	private Long contentAreaId;
	private Long status;
	private Boolean qcComplete;
	private Boolean access;
	private Long createdUser;

	private Long testCollectionId;
	@JsonInclude(Include.ALWAYS)
	private String assignmentType;

	private Long autoAssignId;

	private Boolean suspend;
	
	@JsonInclude(Include.ALWAYS)
	private Boolean predictiveAutoAssigned;
	
	private Long generatedReportCount;
	
	private Long schoolReportCount;
	
	private Long districtReportCount;
	
	private Boolean feedbackAllowed;
	
	public Boolean isPredictiveAutoAssigned() {
		return this.predictiveAutoAssigned;
	}
	
	public void setPredictiveAutoAssigned(Boolean b) {
		this.predictiveAutoAssigned = b;
	}

	public Boolean getSuspend() {
		return suspend;
	}

	public void setSuspend(Boolean suspend) {
		this.suspend = suspend;
	}

	public String getCreatedDateString() {
		SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
		return df2.format(this.createDate);
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

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

	public final Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
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
	 * @param assessmentId
	 *            the assessmentId to set
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
	 * @param subjectId
	 *            the subjectId to set
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
	 * @param gradeId
	 *            the gradeId to set
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
	 * @param testName
	 *            the testName to set
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
	 * @param numItems
	 *            the numItems to set
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
	 * @param createDate
	 *            the createDate to set
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
	 * @param modifiedDate
	 *            the modifiedDate to set
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
	 * @param originationCode
	 *            the originationCode to set
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
	 * @param directions
	 *            the directions to set
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
	 * @param uiTypeCode
	 *            the uiTypeCode to set
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
	 * @param reviewText
	 *            the reviewText to set
	 */
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	/**
	 * @return the gradeCourseId
	 */
	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	/**
	 * @param gradeCourseId
	 *            the gradeCourseId to set
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
	 * @param contentAreaId
	 *            the contentAreaId to set
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
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the assessment
	 */

	/**
	 * @param assessment
	 *            the assessment to set
	 */

	/**
	 * @return the testSections
	 */

	/**
	 * This is different from the set test sections. It checks if the test
	 * section belongs to the current test , then sets it.
	 * 
	 * @param testSections
	 */

	public Boolean getQcComplete() {
		return qcComplete;
	}

	public void setQcComplete(boolean qcComplete) {
		this.qcComplete = qcComplete;
	}

	public Boolean getIsInterimTest() {
		return isInterimTest;
	}

	public void setIsInterimTest(Boolean isInterimTest) {
		this.isInterimTest = isInterimTest;
	}

	public long getInterimTestId() {
		return interimTestId;
	}

	public void setInterimTestId(long interimTestId) {
		this.interimTestId = interimTestId;
	}

	public String getTestDescription() {
		return testDescription;
	}

	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}

	public String getAssembledBy() {
		return assembledBy;
	}

	public void setAssembledBy(String assembledBy) {
		this.assembledBy = assembledBy;
	}

	public String getTestInternalName() {
		return testInternalName;
	}

	public void setTestInternalName(String testInternalName) {
		this.testInternalName = testInternalName;
	}

	public Boolean getIsTestAssigned() {
		if (this.testSessionId != null) {
			return true;
		}
		return false;
	}

	public void setIsTestAssigned(Boolean isTestAssigned) {
		this.isTestAssigned = isTestAssigned;
	}

	public Long getStudentsAssigned() {
		return studentsAssigned;
	}

	public void setStudentsassigned(Long studentsassigned) {
		this.studentsAssigned = studentsassigned;
	}

	public Long getStudentsAttempted() {
		return studentsAttempted;
	}

	public void setStudentsAttempted(Long studentsAttempted) {
		this.studentsAttempted = studentsAttempted;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setQcComplete(Boolean qcComplete) {
		this.qcComplete = qcComplete;
	}

	public Long getTestSessionId() {
		return testSessionId;
	}

	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}

	public void setStudentsAssigned(Long studentsAssigned) {
		this.studentsAssigned = studentsAssigned;
	}



	public Boolean getAccess() {
		if (this.access == null) {
			this.access = true;
		}
		return access;
	}

	public void setAccess(Boolean access) {
		this.access = access;
	}

	public String getWindowExpiryDateString() {
		if (this.expiryDate != null) {
			SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
			return df2.format(this.expiryDate);
		} else {
			return null;
		}
	}

	public void setWindowExpiryDateString(String windowExpiryDateString) {
		this.windowExpiryDateString = windowExpiryDateString;
	}

	public String getWindowEffectiveDateString() {
		if (this.effectiveDate != null) {
			SimpleDateFormat df2 = new SimpleDateFormat("MMM dd, yyyy");
			Date date = new Date(effectiveDate.getTime());
			return df2.format(date);
		} else {
			return null;
		}
	}

	public void setWindowEffectiveDateString(String windowEffectiveDateString) {
		this.windowEffectiveDateString = windowEffectiveDateString;
	}

	

	public void setWindowStartTimeString(String windowStartTimeString) {
		this.windowStartTimeString = windowStartTimeString;
	}

	
	public void setWindowEndTimeString(String windowEndTimeString) {
		this.windowEndTimeString = windowEndTimeString;
	}

	public Long getSerial() {
		return serial;
	}

	public void setSerial(Long serial) {
		this.serial = serial;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Long getTestCollectionId() {
		return testCollectionId;
	}

	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public String getScheduled() {
		if (this.effectiveDate != null && this.expiryDate != null) {
			SimpleDateFormat df2 = new SimpleDateFormat("MMM dd");
			Date date1 = new Date(effectiveDate.getTime());
			Date date2 = new Date(expiryDate.getTime());
			return df2.format(date1) + " - " + df2.format(date2);
		} else {
			return null;
		}
	}

	public void setScheduled(String scheduled) {
		this.scheduled = scheduled;
	}



	public void setStartTimeCT(String startTimeCT) {
		this.startTimeCT = startTimeCT;
	}

	public String getTestStatus() {
		if (this.getIsTestAssigned() != null && this.getIsTestAssigned().equals(Boolean.TRUE)) {
			return "Assigned";
		}
		return "Not Assigned";
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public Long getAutoAssignId() {
		return autoAssignId;
	}

	public void setAutoAssignId(Long autoAssignId) {
		this.autoAssignId = autoAssignId;
	}

	public String getAssignmentType() {
		if (getAutoAssignId() != null || Boolean.TRUE.equals(isPredictiveAutoAssigned())) {
			return "Auto";
		} else if ("Not Assigned".equals(getTestStatus())){
			return "NA";
		}
		else{
			return "Manual";
		}
	}

	public void setAssignmentType(String assignmentType) {
		this.assignmentType = assignmentType;
	}

	public Long getGeneratedReportCount() {
		return generatedReportCount;
	}

	public void setGeneratedReportCount(Long generatedReportCount) {
		this.generatedReportCount = generatedReportCount;
	}

	public Long getSchoolReportCount() {
		return schoolReportCount;
	}

	public void setSchoolReportCount(Long schoolReportCount) {
		this.schoolReportCount = schoolReportCount;
	}

	public Long getDistrictReportCount() {
		return districtReportCount;
	}

	public void setDistrictReportCount(Long districtReportCount) {
		this.districtReportCount = districtReportCount;
	}

	public boolean isWindowComplete() {
		return windowComplete;
	}

	public void setWindowComplete(boolean windowComplete) {
		this.windowComplete = windowComplete;
	}

	public Boolean getFeedbackAllowed() {
		return feedbackAllowed;
	}

	public void setFeedbackAllowed(Boolean feedbackAllowed) {
		this.feedbackAllowed = feedbackAllowed;
	}


}
