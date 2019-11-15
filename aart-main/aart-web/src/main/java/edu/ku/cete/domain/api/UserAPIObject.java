package edu.ku.cete.domain.api;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class UserAPIObject {
	private String recordType;
	
	private String uniqueId;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private List<UserRolesAPIObject> roles;  
	
	private List<String> courseAccreditation; 
	
	@JsonDeserialize(using = APIBooleanDeserializer.class)
	private Boolean active;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<UserRolesAPIObject> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRolesAPIObject> roles) {
		this.roles = roles;
	}

	public List<String> getCourseAccreditation() {
		return courseAccreditation;
	}

	public void setCourseAccreditation(List<String> courseAccreditation) {
		this.courseAccreditation = courseAccreditation;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
}
