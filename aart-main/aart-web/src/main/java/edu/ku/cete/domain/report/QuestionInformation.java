package edu.ku.cete.domain.report;

import java.math.BigDecimal;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 5, 2017 12:32:57 PM
 */
public class QuestionInformation extends AuditableDomain{
	
	private static final long serialVersionUID = 8475335080968432609L;
	
	private Long id;
	
    private Long schoolYear;  
    
    private Long assessmentProgramId;  
    
    private Long testingProgramId;  
    
    private String reportCycle;
    
    private Long gradeId;
    
    private Long contentAreaId;
    
    private Long externalTestId;
    
    private Long taskVariantExternalId;
    
    private String questionDescription;
    
    private Integer creditPercent;
    
    private String comment;
    
    private Long batchUploadId;
    
    private String lineNumber;
    
	private String assessmentProgram;
	
	private String subject;
	
	private String grade;
	
	private String testingProgramName;
	
	private Long testSectionId;
	
	private Long testId;
	
	private Integer testSectionOrder;
	
	private BigDecimal maxScore;
	
	private Integer testSectionTaskVariantPosition;
	
	private Integer taskVariantPosition;
	
	private Long taskVariantId;
	
	private Long questionInformationId;
	
    public Long getId() {
        return id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }

    
    public Long getSchoolYear() {
        return schoolYear;
    }

    
    public void setSchoolYear(Long schoolYear) {
        this.schoolYear = schoolYear;
    }

    
    public Long getAssessmentProgramId() {
        return assessmentProgramId;
    }

    
    public void setAssessmentProgramId(Long assessmentProgramId) {
        this.assessmentProgramId = assessmentProgramId;
    }

    
    public Long getTestingProgramId() {
        return testingProgramId;
    }

    
    public void setTestingProgramId(Long testingProgramId) {
        this.testingProgramId = testingProgramId;
    }

    
    public String getReportCycle() {
        return reportCycle;
    }

    
    public void setReportCycle(String reportCycle) {
        this.reportCycle = reportCycle == null ? null : reportCycle.trim();
    }

    
    public Long getGradeId() {
        return gradeId;
    }

    
    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    
    public Long getContentAreaId() {
        return contentAreaId;
    }

    
    public void setContentAreaId(Long contentAreaId) {
        this.contentAreaId = contentAreaId;
    }

    
    public Long getExternalTestId() {
        return externalTestId;
    }

    
    public void setExternalTestId(Long externalTestId) {
        this.externalTestId = externalTestId;
    }   

    
    public Long getTaskVariantExternalId() {
		return taskVariantExternalId;
	}


	public void setTaskVariantExternalId(Long taskVariantExternalId) {
		this.taskVariantExternalId = taskVariantExternalId;
	}


	public String getQuestionDescription() {
        return questionDescription;
    }

    
    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription == null ? null : questionDescription.trim();
    }

    
    public Integer getCreditPercent() {
        return creditPercent;
    }

    
    public void setCreditPercent(Integer creditPercent) {
        this.creditPercent = creditPercent;
    }

    
    public String getComment() {
        return comment;
    }

    
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
    
    public Long getBatchUploadId() {
        return batchUploadId;
    }

    
    public void setBatchUploadId(Long batchUploadId) {
        this.batchUploadId = batchUploadId;
    }


	public String getLineNumber() {
		return lineNumber;
	}


	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}


	public String getAssessmentProgram() {
		return assessmentProgram;
	}


	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getGrade() {
		return grade;
	}


	public void setGrade(String grade) {
		this.grade = grade;
	}


	public String getTestingProgramName() {
		return testingProgramName;
	}


	public void setTestingProgramName(String testingProgramName) {
		this.testingProgramName = testingProgramName;
	}


	public Long getTestSectionId() {
		return testSectionId;
	}


	public void setTestSectionId(Long testSectionId) {
		this.testSectionId = testSectionId;
	}


	public Integer getTestSectionOrder() {
		return testSectionOrder;
	}


	public void setTestSectionOrder(Integer testSectionOrder) {
		this.testSectionOrder = testSectionOrder;
	}

	public BigDecimal getMaxScore() {
		return maxScore;
	}


	public void setMaxScore(BigDecimal maxScore) {
		this.maxScore = maxScore;
	}


	public Integer getTestSectionTaskVariantPosition() {
		return testSectionTaskVariantPosition;
	}


	public void setTestSectionTaskVariantPosition(Integer testSectionTaskVariantPosition) {
		this.testSectionTaskVariantPosition = testSectionTaskVariantPosition;
	}


	public Integer getTaskVariantPosition() {
		return taskVariantPosition;
	}


	public void setTaskVariantPosition(Integer taskVariantPosition) {
		this.taskVariantPosition = taskVariantPosition;
	}


	public Long getTaskVariantId() {
		return taskVariantId;
	}


	public void setTaskVariantId(Long taskVariantId) {
		this.taskVariantId = taskVariantId;
	}


	public Long getQuestionInformationId() {
		return questionInformationId;
	}


	public void setQuestionInformationId(Long questionInformationId) {
		this.questionInformationId = questionInformationId;
	}


	public Long getTestId() {
		return testId;
	}


	public void setTestId(Long testId) {
		this.testId = testId;
	}
    
   
}