/**
 * 
 */
package edu.ku.cete.score.util;

import java.util.List;

import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.student.Student;

/**
 * @author neil.howerton
 * TODO rename to StudentTestResponsesDto
 */
public class StudentTestResponses {

    private Long id;

    /**
     * The student who took the test.
     */
    private Student student;

    /**
     * The test the student took.
     */
    private Test test;

    /**
     * The student's responses to the test questions.
     */
    private List<StudentsResponses> responses;

    /**
     * The students local enrollment identifier.
     */
    private String localStudentIdentifier;

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
     * @return the test
     */
    public final Test getTest() {
        return test;
    }

    /**
     * @param test the test to set
     */
    public final void setTest(Test test) {
        this.test = test;
    }

    /**
     * @return the responses
     */
    public final List<StudentsResponses> getResponses() {
        return responses;
    }

    /**
     * @param responses the responses to set
     */
    public final void setResponses(List<StudentsResponses> responses) {
        this.responses = responses;
    }

    /**
     * @return the localStudentIdentifier
     */
    public String getLocalStudentIdentifier() {
        return localStudentIdentifier;
    }

    /**
     * @param localStudentIdentifier the localStudentIdentifier to set
     */
    public void setLocalStudentIdentifier(String localStudentIdentifier) {
        this.localStudentIdentifier = localStudentIdentifier;
    }
}
