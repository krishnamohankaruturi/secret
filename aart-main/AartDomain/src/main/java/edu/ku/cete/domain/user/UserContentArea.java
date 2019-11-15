package edu.ku.cete.domain.user;

import java.util.Date;

public class UserContentArea{
	
	private Long id;
	
	private Long userId;
	
	private Long contentAreaId;
	
	private String contentAreaAbbreviation;
	
	private Boolean active;
	
	private Long createdUser;
	
	private Date createdDate;
	
	private Long modifiedUser;
	
	private Date modifiedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public String getContentAreaAbbreviation() {
		return contentAreaAbbreviation;
	}

	public void setContentAreaAbbreviation(String contentAreaAbbreviation) {
		this.contentAreaAbbreviation = contentAreaAbbreviation;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getCreatedUser() {
		return createdUser;
	}

	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
}