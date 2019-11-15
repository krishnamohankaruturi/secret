/**
 * 
 */
package edu.ku.cete.domain.enrollment;

/**
 * @author mahesh
 * This is the class for enrollmentsrosterskeys plus the student id.
 */
public class StudentRosterKeyDto {
	
	private Long id;
	private Long rosterId;
	private Long studentId;
	private Long enrollmentId;
	private Long testSessionId;
	private Long studentTestId;
	
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
	 * @return the rosterId
	 */
	public Long getRosterId() {
		return rosterId;
	}
	/**
	 * @param rosterId the rosterId to set
	 */
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
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
	 * @return the enrollmentId
	 */
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	/**
	 * @param enrollmentId the enrollmentId to set
	 */
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
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

	public Long getStudentTestId() {
		return studentTestId;
	}
	
	public void setStudentTestId(Long studentTestId) {
		this.studentTestId = studentTestId;
	}
}
