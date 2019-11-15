package edu.ku.cete.web;

import java.util.List;
import edu.ku.cete.domain.content.GradeCourse;
public class StudentGradesTestedDTO{
    
    private Long studentId;
    private Long enrollmentId;
    private List<GradeCourse> gradeCources;
    
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	
	public Long getEnrollmentId() {
		return enrollmentId;
	}
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}
	public List<GradeCourse> getGradeCources() {
		return gradeCources;
	}
	public void setGradeCources(List<GradeCourse> gradeCources) {
		this.gradeCources = gradeCources;
	}
	
}
