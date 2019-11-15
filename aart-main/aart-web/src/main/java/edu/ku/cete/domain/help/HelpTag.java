package edu.ku.cete.domain.help;

import edu.ku.cete.domain.audit.AuditableDomain;

public class HelpTag extends AuditableDomain {

	private static final long serialVersionUID = -1188292647228038176L;
	private Long id;
	private String tag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
