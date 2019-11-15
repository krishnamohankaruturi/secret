package edu.ku.cete.domain.content;

import edu.ku.cete.domain.audit.AuditableDomain;

public class Stage extends AuditableDomain {

	private static final long serialVersionUID = -6918427183810503850L;
	private Long id;
	private String name;
	private String code;
	private Long predecessorId;
	private Integer sortOrder;
	private Long sessionId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getPredecessorId() {
		return predecessorId;
	}

	public void setPredecessorId(Long predecessorId) {
		this.predecessorId = predecessorId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}
	
}
