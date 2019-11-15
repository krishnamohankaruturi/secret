package edu.ku.cete.domain.feedback;

import edu.ku.cete.domain.audit.AuditableDomain;

public class FeedBack extends AuditableDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 396469021335614284L;

	private Long id;

	private Long aartUserId;

	private String userName;

	private String email;

	private String webPage;

	private String feedBack;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAartUserId() {
		return aartUserId;
	}

	public void setAartUserId(Long aartUserId) {
		this.aartUserId = aartUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

}
