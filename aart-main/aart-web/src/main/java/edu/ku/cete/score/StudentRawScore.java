/**
 * 
 */
package edu.ku.cete.score;

import edu.ku.cete.domain.student.Student;

/**
 * @author neil.howerton
 *
 */
public class StudentRawScore {

    /**
     * The number of questions contained in the test.
     */
    private int numQuestions;

    /**
     * The number of questions the student answered correctly.
     */
    private int numCorrect;

    /**
     * The Student object representing the student who took the test.
     */
    private Student student;

    /**
     * The local identifier for the student.
     */
    private String localStudentIdentifier;
    
    /**
     * List of Correct Responses
     */
    private String correctresponses;

    /**
     * @return the numQuestions
     */
    public final int getNumQuestions() {
        return numQuestions;
    }

    /**
     * @param numQuestions the numQuestions to set
     */
    public final void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    /**
     * @return the numCorrect
     */
    public final int getNumCorrect() {
        return numCorrect;
    }

    /**
     * @param numCorrect the numCorrect to set
     */
    public final void setNumCorrect(int numCorrect) {
        this.numCorrect = numCorrect;
    }

    /**
     * @return the student
     */
    public final Student getStudent() {
        return student;
    }

    /**
     * @param student the student to set
     */
    public final void setStudent(Student student) {
        this.student = student;
    }

    /**
     * @return the local student identifier
     */
    public final String getLocalStudentIdentifier() {
        return localStudentIdentifier;
    }

    /**
     * @param localStudentIdentifier {@link String}
     */
    public void setLocalStudentIdentifier(String localStudentIdentifier) {
        this.localStudentIdentifier = localStudentIdentifier;
    }

	public String getCorrectresponses() {
		return correctresponses;
	}

	public void setCorrectresponses(String correctresponses) {
		this.correctresponses = correctresponses;
	}
}
