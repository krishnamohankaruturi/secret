package edu.ku.cete.domain.apierrors;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.ku.cete.util.DateUtil;

public class ApiAllErrorRecords {

	private static final long serialVersionUID = 3350215087863991083L;
	
	private Date requestDate;
	
    private String recordType;
	
    private String requestType;
    
    private String name;
    
    private Long classroomID;
    
    private String message;
    
    private String schoolName;
	
    private String districtName;
	
	private String stateName;
	
	public String getRequestDate() {
		
		if(this.requestDate == null) {
			return StringUtils.EMPTY;
		}		
		return DateUtil.format(this.requestDate,"MM/dd/yy hh:mm a z");
	}

	@JsonProperty("Requested")
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	
	 
	public String getRecordType() {
		return recordType;
	}
	
	@JsonProperty("Record Type")
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getRequestType() {
		return requestType;
	}
	
	@JsonProperty("Request Type")
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getName() {
		return name;
	}
	
	@JsonProperty("Name")
	public void setName(String name) {
		this.name = name;
	}

	public Long getClassroomID() {
		return classroomID;
	}

	@JsonProperty("Classroom Id")
	public void setClassroomID(Long classroomID) {
		this.classroomID = classroomID;
	}

	public String getMessage() {
		return message;
	}
	
	@JsonProperty("Message")
	public void setMessage(String message) {
		this.message = message;
	}

	public String getSchoolName() {
		return schoolName;
	}
	
	@JsonProperty("School Name")
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getDistrictName() {
		return districtName;
	}
	
	@JsonProperty("District Name")
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getStateName() {
		return stateName;
	}
	
	@JsonProperty("State Name")
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}


	
	
	
	 
	 
}
