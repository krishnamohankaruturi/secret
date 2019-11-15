package edu.ku.cete.domain.api;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class StudentAPIObject {
    private String recordType;
    
    private String uniqueStudentId;
    
    private String stateStudentId;
    
    private String lastName;
    
    private String firstName;
    
    private String gender;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;
    
    private String userName;
    
    private String currentGradeLevel;
    
    private String schoolIdentifier;
        
    @JsonDeserialize(using = APIBooleanDeserializer.class)
    private Boolean active;
    
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getUniqueStudentId() {
		return uniqueStudentId;
	}
	public void setUniqueStudentId(String uniqueStudentId) {
		this.uniqueStudentId = uniqueStudentId;
	}
	public String getStateStudentId() {
		return stateStudentId;
	}
	public void setStateStudentId(String stateStudentId) {
		this.stateStudentId = stateStudentId;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCurrentGradeLevel() {
		return currentGradeLevel;
	}
	public void setCurrentGradeLevel(String currentGradeLevel) {
		this.currentGradeLevel = currentGradeLevel;
	}
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
}
