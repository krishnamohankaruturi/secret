package edu.ku.cete.web;

import java.io.Serializable;

import edu.ku.cete.domain.student.Student;

/**
 *
 * @author neil.howerton
 * TODO move it to Enrollment domain object
 */
public class StudentDto implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Student student;
    private String studentLocalId;
    private String enrolledGrade;

    /**
     *
     *@return {@link Student}
     */
    public final Student getStudent() {
        return student;
    }

    /**
     *
     *@param student {@link Student}
     */
    public final void setStudent(Student student) {
        this.student = student;
    }

    /**
     *
     *@return long
     */
    public final String getStudentLocalId() {
        return studentLocalId;
    }

    /**
     *
     *@param studentLocalId long
     */
    public final void setStudentLocalId(String studentLocalId) {
        this.studentLocalId = studentLocalId;
    }

    /**
     *
     *@return int
     */
    public final String getEnrolledGrade() {
        return enrolledGrade;
    }

    /**
     *
     *@param enrolledGrade int
     */
    public final void setEnrolledGrade(String enrolledGrade) {
        this.enrolledGrade = enrolledGrade;
    }

}
