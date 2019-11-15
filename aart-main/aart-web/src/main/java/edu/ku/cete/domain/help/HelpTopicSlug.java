package edu.ku.cete.domain.help;

import edu.ku.cete.domain.audit.AuditableDomain;

public class HelpTopicSlug extends AuditableDomain {

	private static final long serialVersionUID = -3214183164863552314L;

	private long id;
	private Long helpTopicId;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getHelpTopicId() {
		return helpTopicId;
	}

	public void setHelpTopicId(Long helpTopicId) {
		this.helpTopicId = helpTopicId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
