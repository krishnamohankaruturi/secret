package edu.ku.cete.web;

import java.util.List;

import edu.ku.cete.domain.student.Student;

public class AccessibilityExtractDTO {
	private Long studentId;
	private List<StudentProfileItemAttributeDTO> attributes;
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public List<StudentProfileItemAttributeDTO> getAttributes() {
		return attributes;
	}
	
	public void setAttributes(List<StudentProfileItemAttributeDTO> attributes) {
		this.attributes = attributes;
	}
}