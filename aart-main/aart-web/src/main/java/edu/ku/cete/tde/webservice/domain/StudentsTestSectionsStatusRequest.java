package edu.ku.cete.tde.webservice.domain;

import java.util.Date;

public class StudentsTestSectionsStatusRequest {

	private Long studentsTestId;
	private Long testSessionId;
	private Date modifiedDate;
	private Long testSectionId;
	private Long testingStatusId;
	
	
	public StudentsTestSectionsStatusRequest () {  }
	
	public StudentsTestSectionsStatusRequest (Long studentsTestId,Long testSessionId,Date modifiedDate,Long testSectionId,Long testingStatusId) {
		this.studentsTestId = studentsTestId;
		this.testSessionId = testSessionId;
		this.modifiedDate = modifiedDate;
		this.testSectionId = testSectionId;
		this.testingStatusId = testingStatusId;
	}
	
	public Long getStudentsTestId() {
		return studentsTestId;
	}

	public void setStudentsTestId(Long studentsTestId) {
		this.studentsTestId = studentsTestId;
	}

	public Long getTestSessionId() {
		return testSessionId;
	}

	public void setTestSessionId(Long testSessionId) {
		this.testSessionId = testSessionId;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getTestSectionId() {
		return testSectionId;
	}

	public void setTestSectionId(Long testSectionId) {
		this.testSectionId = testSectionId;
	}

	public Long getTestingStatusId() {
		return testingStatusId;
	}

	public void setTestingStatusId(Long testingStatusId) {
		this.testingStatusId = testingStatusId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("StudentsTestSectionsStatusRequest [");
		if (studentsTestId != null) {
			builder.append("studentsTestId=");
			builder.append(studentsTestId);
			builder.append(", ");
		}
		if (testSessionId != null) {
			builder.append("testSessionId=");
			builder.append(testSessionId);
			builder.append(", ");
		}
		if (modifiedDate != null) {
			builder.append("modifiedDate=");
			builder.append(modifiedDate);
			builder.append(", ");
		}
		if (testSectionId != null) {
			builder.append("testSectionId=");
			builder.append(testSectionId);
			builder.append(", ");
		}
		if (testingStatusId != null) {
			builder.append("testingStatusId=");
			builder.append(testingStatusId);
		}
		builder.append("]");
		return builder.toString();
	}

}
