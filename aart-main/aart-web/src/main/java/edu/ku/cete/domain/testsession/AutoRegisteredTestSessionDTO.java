package edu.ku.cete.domain.testsession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

/**
 * @author vittaly
 *
 */
public class AutoRegisteredTestSessionDTO {

	/**
	 * testSessionId
	 */
	private Long testSessionId;
	
	/**
	 * testSessionName
	 */
	private String testSessionName;
		
	/**
	 * assessmentProgramName
	 */
	private String assessmentProgramName;
	
	/**
	 * organizationDisplayIdentifier
	 */
	private String organizationDisplayIdentifier;
	
	/**
	 * contentAreaName
	 */
	private String contentAreaName;
	
	/**
	 * gradeCourseName
	 */
	private String gradeCourseName;
	
	/**
	 * gradeBandName
	 */
	private String gradeBandName;
	
	private String printTestFiles;

	private Boolean expiredFlag;
	
	private Boolean includeCompletedTestSession;
	
	private Boolean includeInProgressTestSession;
	
	private Date effectiveDate;
	private Date expiryDate;
	private Long otwId;
	private Long testPanelId;
	
	private int totalRecords;
	
	public Boolean getIncludeCompletedTestSession() {
		return includeCompletedTestSession;
	}

	public void setIncludeCompletedTestSession(Boolean includeCompletedTestSession) {
		this.includeCompletedTestSession = includeCompletedTestSession;
	}

	public Boolean getIncludeInProgressTestSession() {
		return includeInProgressTestSession;
	}

	public void setIncludeInProgressTestSession(Boolean includeInProgressTestSession) {
		this.includeInProgressTestSession = includeInProgressTestSession;
	}
	public Long getTestPanelId() {
		return testPanelId;
	}

	public void setTestPanelId(Long testPanelId) {
		this.testPanelId = testPanelId;
	}

	/**
	 * @return
	 */
	public Long getTestSessionId() {
		return testSessionId;
	}

	/**
	 * @param testSessionId
	 */
	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}

	/**
	 * @return
	 */
	public String getTestSessionName() {
		return testSessionName;
	}

	/**
	 * @param testSessionName
	 */
	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
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
	public String getOrganizationDisplayIdentifier() {
		return organizationDisplayIdentifier;
	}

	/**
	 * @param organizationDisplayIdentifier
	 */
	public void setOrganizationDisplayIdentifier(
			String organizationDisplayIdentifier) {
		this.organizationDisplayIdentifier = organizationDisplayIdentifier;
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
	public String getGradeCourseName() {
		return gradeCourseName;
	}

	/**
	 * @param gradeCourseName
	 */
	public void setGradeCourseName(String gradeCourseName) {
		this.gradeCourseName = gradeCourseName;
	}
	
	public String getGradeBandName() {
		return gradeBandName;
	}

	public void setGradeBandName(String gradeBandName) {
		this.gradeBandName = gradeBandName;
	}	
	
	/**
	 * 
	 * @return
	 */
	public String getPrintTestFiles() {
		return printTestFiles;
	}
	
	public Boolean getExpiredFlag() {
		return expiredFlag;
	}

	public void setExpiredFlag(Boolean expiredFlag) {
		this.expiredFlag = expiredFlag;
	}

	/**
	 * 
	 * @param printTestFiles
	 */
	public void setPrintTestFiles(String printTestFiles) {
		this.printTestFiles = printTestFiles;
	}
	
	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	/**
	 * @return the otwId
	 */
	public Long getOtwId() {
		return otwId;
	}

	/**
	 * @param otwId the otwId to set
	 */
	public void setOtwId(Long otwId) {
		this.otwId = otwId;
	}

	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();
		
		//Set testSessionId
		if(getTestSessionId() != null) {
			cells.add(ParsingConstants.BLANK + getTestSessionId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set assessmentProgramName
		if(getAssessmentProgramName() != null) {
			cells.add(ParsingConstants.BLANK + getAssessmentProgramName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
				
		//Set organizationDisplayIdentifier
		if(getOrganizationDisplayIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getOrganizationDisplayIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
				
		//Set testSessionName
		if(getTestSessionName() != null) {
			cells.add(ParsingConstants.BLANK + getTestSessionName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set tickets		
		cells.add("Print Ticket");
		
		//Set Print test files
		if(getPrintTestFiles() != null) {
			cells.add(ParsingConstants.BLANK + getPrintTestFiles());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set contentAreaName
		if(getContentAreaName() != null) {
			cells.add(ParsingConstants.BLANK + getContentAreaName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		//Set getGrade
		if(getGradeCourseName() != null) {
			cells.add(ParsingConstants.BLANK + getGradeCourseName());
		} else if (getGradeBandName() != null) {
			cells.add(ParsingConstants.BLANK + getGradeBandName());
		}else{
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		if(getExpiredFlag() != null) {
			cells.add(StringUtil.convert(getExpiredFlag() ? "Y": "N", ParsingConstants.NOT_AVAILABLE));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getEffectiveDate() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.format(getEffectiveDate(), "MM/dd/yyyy"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getExpiryDate() != null) {
			cells.add(ParsingConstants.BLANK + DateUtil.format(getExpiryDate(), "MM/dd/yyyy"));
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getOtwId() != null) {
			cells.add(ParsingConstants.BLANK + getOtwId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getTestPanelId() != null) {
			cells.add(ParsingConstants.BLANK + getTestPanelId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		return cells;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

}
