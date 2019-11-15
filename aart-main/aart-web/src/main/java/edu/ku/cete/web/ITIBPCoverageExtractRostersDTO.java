package edu.ku.cete.web;

import java.util.ArrayList;
import java.util.List;

public class ITIBPCoverageExtractRostersDTO {
	
	private String subjectName;
	private Long subjectId;
	private String gradeName;
	private String gradeAbbrName;
	private Long teacherId;
	private String teacherFirstName;
	private String teacherLastName;
	
	private List<ITIBPCoverageRosteredStudentsDTO> rosteredStudentsDetailsList = new ArrayList<ITIBPCoverageRosteredStudentsDTO>();

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeAbbrName() {
		return gradeAbbrName;
	}

	public void setGradeAbbrName(String gradeAbbrName) {
		this.gradeAbbrName = gradeAbbrName;
	}

	public Long getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
	}
	
	public String getTeacherFirstName() {
		return teacherFirstName;
	}

	public void setTeacherFirstName(String teacherFirstName) {
		this.teacherFirstName = teacherFirstName;
	}

	public String getTeacherLastName() {
		return teacherLastName;
	}

	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}

	public List<ITIBPCoverageRosteredStudentsDTO> getRosteredStudentsDetailsList() {
		return rosteredStudentsDetailsList;
	}

	public void setRosteredStudentsDetailsList(
			List<ITIBPCoverageRosteredStudentsDTO> rosteredStudentsDetailsList) {
		this.rosteredStudentsDetailsList = rosteredStudentsDetailsList;
	}	
}
