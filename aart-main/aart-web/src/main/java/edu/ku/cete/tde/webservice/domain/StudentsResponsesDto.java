package edu.ku.cete.tde.webservice.domain;

public class StudentsResponsesDto {

    private Long studentsTestsId;

    private Long studentId;

    private Long testId;

    private Long taskId;

    private Long foilId;

    private String response;
    
    private String studentsTestSectionsId;

    /**
     * @return the studentsTestsId
     */
    public final Long getStudentsTestsId() {
        return studentsTestsId;
    }

    /**
     * @param studentsTestsId the id to set
     */
    public final void setStudentsTestsId(Long studentsTestsId) {
        this.studentsTestsId = studentsTestsId;
    }

    /**
     * @return the studentId
     */
    public final Long getStudentId() {
        return studentId;
    }

    /**
     * @param studentId the studentId to set
     */
    public final void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    /**
     * @return the testId
     */
    public final Long getTestId() {
        return testId;
    }

    /**
     * @param testId the testId to set
     */
    public final void setTestId(Long testId) {
        this.testId = testId;
    }

    /**
     * @return the taskId
     */
    public final Long getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public final void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the foil
     */
    public final Long getFoilId() {
        return foilId;
    }

    /**
     * @param foil the foil to set
     */
    public final void setFoilId(Long foilId) {
        this.foilId = foilId;
    }

    /**
     * @return the response
     */
    public final String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public final void setResponse(String response) {
        this.response = response;
    }

	/**
	 * @return the studentsTestSectionsId
	 */
	public String getStudentsTestSectionsId() {
		return studentsTestSectionsId;
	}

	/**
	 * @param studentsTestSectionsId the studentsTestSectionsId to set
	 */
	public void setStudentsTestSectionsId(String studentsTestSectionsId) {
		this.studentsTestSectionsId = studentsTestSectionsId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentsResponsesDto [studentsTestsId=" + studentsTestsId + ", studentId=" + studentId
				+ ", testId=" + testId + ", taskId=" + taskId + ", foilId="
				+ foilId + ", response=" + response + "]";
	}
}
