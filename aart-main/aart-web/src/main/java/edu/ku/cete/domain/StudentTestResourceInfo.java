package edu.ku.cete.domain;

public class StudentTestResourceInfo {
	private Long studentsTestsId;
	private Long studentId;
	private Long testSessionId;
	private Long testId;
	private Long testSectionId;
	private String fileLocation;
	private String fileType;
	private String fileName;
	private Long stimulusVariantId;
	
	/**
	 * @return the studentsTestsId
	 */
	public Long getStudentsTestsId() {
		return studentsTestsId;
	}
	/**
	 * @param studentsTestsId the studentsTestsId to set
	 */
	public void setStudentsTestsId(Long studentsTestsId) {
		this.studentsTestsId = studentsTestsId;
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
	 * @return the testSectionId
	 */
	public Long getTestSectionId() {
		return testSectionId;
	}
	/**
	 * @param testSectionId the testSectionId to set
	 */
	public void setTestSectionId(Long testSectionId) {
		this.testSectionId = testSectionId;
	}
	/**
	 * @return the fileLocation
	 */
	public String getFileLocation() {
		return fileLocation;
	}
	/**
	 * @param fileLocation the fileLocation to set
	 */
	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the stimulusVariantId
	 */
	public Long getStimulusVariantId() {
		return stimulusVariantId;
	}
	/**
	 * @param stimulusVariantId the stimulusVariantId to set
	 */
	public void setStimulusVariantId(Long stimulusVariantId) {
		this.stimulusVariantId = stimulusVariantId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileLocation == null) ? 0 : fileLocation.hashCode());
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((fileType == null) ? 0 : fileType.hashCode());
		result = prime * result + ((testSessionId == null) ? 0 : testSessionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentTestResourceInfo other = (StudentTestResourceInfo) obj;
		if (fileLocation == null) {
			if (other.fileLocation != null)
				return false;
		} else if (!fileLocation.equals(other.fileLocation))
			return false;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (fileType == null) {
			if (other.fileType != null)
				return false;
		} else if (!fileType.equals(other.fileType))
			return false;
		if (testSessionId == null) {
			if (other.testSessionId != null)
				return false;
		} else if (!testSessionId.equals(other.testSessionId))
			return false;
		return true;
	}
}
