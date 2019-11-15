package edu.ku.cete.domain.help;

import edu.ku.cete.domain.audit.AuditableDomain;

public class HelpContentSlug extends AuditableDomain {

	private static final long serialVersionUID = 7809878139770744183L;
	private long id;
	private Long helpContentId;
	private String url;


	public Long getHelpContentId() {
		return helpContentId;
	}

	public void setHelpContentId(Long helpContentId) {
		this.helpContentId = helpContentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


}
