/**
 * 
 */
package edu.ku.cete.domain.enrollment;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.common.Assessment;

/**
 * @author m802r921
 *
 */
public class TestRecord extends EnrollmentRecord{

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Assessment.
	 */
	private Assessment assessment = new Assessment();
	/**
	 * assessment id.
	 */
	private long assessmentId;
	/**
	 * 
	 */
	private String testSubject;
	/**
	 * 
	 */
	private String testType;
	/**
	 * @return the assessment
	 */
	public Assessment getAssessment() {
		return assessment;
	}
	/**
	 * @param assessment the assessment to set
	 */
	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}
	/**
	 * @param assessId {@link Long}
	 */
	public final void setAssessmentId(long assessId) {
		this.assessmentId = assessId;
	}

	/**
	 * @return assessmentId
	 */
	public final long getAssessmentId() {
		return assessmentId;
	}
	/**
	 * @return the testSubject
	 */
	public final String getTestSubject() {
		return testSubject;
	}
	/**
	 * @param testSubj the testSubject to set
	 */
	public final void setTestSubject(String testSubj) {
		this.testSubject = testSubj;
	}
	/**
	 * @return the testType
	 */
	public final String getTestType() {
		return testType;
	}
	/**
	 * @param testTyp the testType to set
	 */
	public final void setTestType(String testTyp) {
		this.testType = testTyp;
	}
	/**
	 * @return the specialEdProgramEndingDate
	 */
	public Date getSpecialEdProgramEndingDate() {
		return super.getEnrollment().getSpecialEdProgramEndingDate();
	}
	/**
	 * @param specialEdProgramEndingDate the specialEdProgramEndingDate to set
	 */
	public void setSpecialEdProgramEndingDate(Date specialEdProgramEndingDate) {
		super.getEnrollment().setSpecialEdProgramEndingDate(specialEdProgramEndingDate);
	}
	/**
	 * @return the string
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}	
}
