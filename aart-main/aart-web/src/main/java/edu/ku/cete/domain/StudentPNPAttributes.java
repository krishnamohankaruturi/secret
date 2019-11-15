package edu.ku.cete.domain;

import edu.ku.cete.domain.audit.AuditableDomain;

public class StudentPNPAttributes extends AuditableDomain {

	private static final long serialVersionUID = 5841347182399998082L;
	private Long studentId;
	private String jsonText;

	public StudentPNPAttributes(Long studentId) {
		this.studentId = studentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getJsonText() {
		return jsonText;
	}

	public void setJsonText(String jsonText) {
		this.jsonText = jsonText;
	}

}
