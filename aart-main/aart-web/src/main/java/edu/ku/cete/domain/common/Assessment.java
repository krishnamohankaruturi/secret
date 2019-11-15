package edu.ku.cete.domain.common;

import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.util.ParsingConstants;


public class Assessment extends TraceableEntity{
	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * primary key.
	 */
	private Long id;
    /**
     * Testing Program Id
     */
    private Long testingProgramId;
    /**
     * assessmentName.
     */
    private String assessmentName;
    /**
     * assessmentCode.
     */
    private String assessmentCode;
    /**
     * assessmentDescription.
     */
    private String assessmentDescription;
	@Override
	public String getIdentifier() {
		return ParsingConstants.BLANK + getId();
	}
    /**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * @return the testingProgramId
     */
    public Long getTestingProgramId() {
        return testingProgramId;
    }

    /**
     * @param testingProgramId the testingProgramId to set
     */
    public void setTestingProgramId(Long testingProgramId) {
        this.testingProgramId = testingProgramId;
    }

    /**
     * @return the assessmentName
     */
    public String getAssessmentName() {
        return assessmentName;
    }

    /**
     * @param assessmentName the assessmentName to set
     */
    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }
	/**
	 * @return the assessmentCode
	 */
	public String getAssessmentCode() {
		return assessmentCode;
	}
	/**
	 * @param assessmentCode the assessmentCode to set
	 */
	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
	}
	/**
	 * @return the assessmentDescription
	 */
	public String getAssessmentDescription() {
		return assessmentDescription;
	}
	/**
	 * @param assessmentDescription the assessmentDescription to set
	 */
	public void setAssessmentDescription(String assessmentDescription) {
		this.assessmentDescription = assessmentDescription;
	}

}