package edu.ku.cete.domain.report;

import java.util.Date;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 5, 2017 12:27:58 PM
 */
public class StudentReportQuestionInfo {
    
    private Long id;
    
    private Long interimStudentReportId;
    
    private Integer taskVariantPosition;
    
    private Long questionInformationId;
    
    private Long creditEarned;
    
    private String questionDescription;
    
    private Integer creditPercent;
    
    private Date createdDate;
    
    private Date modifiedDate;
    
    private Long createdUser;
    
    private Long modifiedUser;

    private Long testId;
    
    private Long externalTestId;
    
    private Long taskVariantId;
    
    private Long taskVariantExternalId;
    
    private String creditEarnedCode;
    
    private Integer fullCreditPercent;
    
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getInterimStudentReportId() {
        return interimStudentReportId;
    }

    
    public void setInterimStudentReportId(Long interimStudentReportId) {
        this.interimStudentReportId = interimStudentReportId;
    }

    
    public Integer getTaskVariantPosition() {
        return taskVariantPosition;
    }

    
    public void setTaskVariantPosition(Integer taskVariantPosition) {
        this.taskVariantPosition = taskVariantPosition;
    }

    
    public Long getQuestionInformationId() {
        return questionInformationId;
    }

    
    public void setQuestionInformationId(Long questionInformationId) {
        this.questionInformationId = questionInformationId;
    }

    
    public Long getCreditEarned() {
        return creditEarned;
    }

    
    public void setCreditEarned(Long creditEarned) {
        this.creditEarned = creditEarned;
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

    
    public Long getCreatedUser() {
        return createdUser;
    }

    
    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    
    public Long getModifiedUser() {
        return modifiedUser;
    }

    
    public void setModifiedUser(Long modifiedUser) {
        this.modifiedUser = modifiedUser;
    }


	public String getQuestionDescription() {
		return questionDescription;
	}


	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}


	public Integer getCreditPercent() {
		return creditPercent;
	}


	public void setCreditPercent(Integer creditPercent) {
		this.creditPercent = creditPercent;
	}


	public Long getTestId() {
		return testId;
	}


	public void setTestId(Long testId) {
		this.testId = testId;
	}


	public Long getExternalTestId() {
		return externalTestId;
	}


	public void setExternalTestId(Long externalTestId) {
		this.externalTestId = externalTestId;
	}


	public Long getTaskVariantId() {
		return taskVariantId;
	}


	public void setTaskVariantId(Long taskVariantId) {
		this.taskVariantId = taskVariantId;
	}


	public Long getTaskVariantExternalId() {
		return taskVariantExternalId;
	}


	public void setTaskVariantExternalId(Long taskVariantExternalId) {
		this.taskVariantExternalId = taskVariantExternalId;
	}


	public String getCreditEarnedCode() {
		return creditEarnedCode;
	}


	public void setCreditEarnedCode(String creditEarnedCode) {
		this.creditEarnedCode = creditEarnedCode;
	}


	public Integer getFullCreditPercent() {
		return fullCreditPercent;
	}


	public void setFullCreditPercent(Integer fullCreditPercent) {
		this.fullCreditPercent = fullCreditPercent;
	}

	
}