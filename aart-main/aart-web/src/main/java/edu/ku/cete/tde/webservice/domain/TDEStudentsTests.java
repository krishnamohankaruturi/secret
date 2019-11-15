/**
 * 
 */
package edu.ku.cete.tde.webservice.domain;

import java.util.Date;
import java.util.List;

/**
 * @author m802r921
 *
 */
public class TDEStudentsTests {

    /**
     * id.
     */
    private Long id;

    /**
     * studentId.
     */
    private Long studentId;

    /**
     * testId.
     */
    private Long testId;

    /**
     * testCollectionId.
     */
    private Long testCollectionId;

    /**
     * status.
     */
    private Long status;

    /**
     * testSessionId.
     */
    private Long testSessionId;
    
    private List<Long> testTypeIds;
    /**
     * testingStatusId.
     */
    private Long testingStatusId;
    /**
     * testName.
     */
    private String testName;
    /**
     * testTypeId.
     */
    private Long testTypeId;
    /**
     * active.
     */
    private boolean active;
    
    /**
     * availableStart.
     */
    private Date availableStart;
    
    private Date availableStop;
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
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the testId
	 */
	public Long getTestId() {
		return testId;
	}

	/**
	 * @param testId the testId to set
	 */
	public void setTestId(Long testId) {
		this.testId = testId;
	}

	/**
	 * @return the testCollectionId
	 */
	public Long getTestCollectionId() {
		return testCollectionId;
	}

	/**
	 * @param testCollectionId the testCollectionId to set
	 */
	public void setTestCollectionId(Long testCollectionId) {
		this.testCollectionId = testCollectionId;
	}

	/**
	 * @return the status
	 */
	public Long getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Long status) {
		this.status = status;
	}

	/**
	 * @return the testSessionId
	 */
	public Long getTestSessionId() {
		return testSessionId;
	}

	/**
	 * @param testSessionId the testSessionId to set
	 */
	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}

	/**
	 * @return the testTypeIds
	 */
	public List<Long> getTestTypeIds() {
		return testTypeIds;
	}

	/**
	 * @param testTypeIds the testTypeIds to set
	 */
	public void setTestTypeIds(List<Long> testTypeIds) {
		this.testTypeIds = testTypeIds;
	}

	/**
	 * @return the testingStatusId
	 */
	public Long getTestingStatusId() {
		return testingStatusId;
	}

	/**
	 * @param testingStatusId the testingStatusId to set
	 */
	public void setTestingStatusId(Long testingStatusId) {
		this.testingStatusId = testingStatusId;
	}

	/**
	 * @return the testName
	 */
	public String getTestName() {
		return testName;
	}

	/**
	 * @param testName the testName to set
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}

	/**
	 * @return the testTypeId
	 */
	public Long getTestTypeId() {
		return testTypeId;
	}

	/**
	 * @param testTypeId the testTypeId to set
	 */
	public void setTestTypeId(Long testTypeId) {
		this.testTypeId = testTypeId;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the availableStart
	 */
	public Date getAvailableStart() {
		return availableStart;
	}

	/**
	 * @param availableStart the availableStart to set
	 */
	public void setAvailableStart(Date availableStart) {
		this.availableStart = availableStart;
	}

	/**
	 * @return the availableStop
	 */
	public Date getAvailableStop() {
		return availableStop;
	}

	/**
	 * @param availableStop the availableStop to set
	 */
	public void setAvailableStop(Date availableStop) {
		this.availableStop = availableStop;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * This is added to trouble shoot communications between AART and TDE.
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TDEStudentsTests [");
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (studentId != null) {
			builder.append("studentId=");
			builder.append(studentId);
			builder.append(", ");
		}
		if (testId != null) {
			builder.append("testId=");
			builder.append(testId);
			builder.append(", ");
		}
		if (testCollectionId != null) {
			builder.append("testCollectionId=");
			builder.append(testCollectionId);
			builder.append(", ");
		}
		if (status != null) {
			builder.append("status=");
			builder.append(status);
			builder.append(", ");
		}
		if (testSessionId != null) {
			builder.append("testSessionId=");
			builder.append(testSessionId);
			builder.append(", ");
		}
		if (testTypeIds != null) {
			builder.append("testTypeIds=");
			builder.append(testTypeIds);
			builder.append(", ");
		}
		if (testingStatusId != null) {
			builder.append("testingStatusId=");
			builder.append(testingStatusId);
			builder.append(", ");
		}
		if (testName != null) {
			builder.append("testName=");
			builder.append(testName);
			builder.append(", ");
		}
		if (testTypeId != null) {
			builder.append("testTypeId=");
			builder.append(testTypeId);
			builder.append(", ");
		}
		builder.append("active=");
		builder.append(active);
		builder.append(", ");
		if (availableStart != null) {
			builder.append("availableStart=");
			builder.append(availableStart);
			builder.append(", ");
		}
		if (availableStop != null) {
			builder.append("availableStop=");
			builder.append(availableStop);
		}
		builder.append("]");
		return builder.toString();
	}

}
