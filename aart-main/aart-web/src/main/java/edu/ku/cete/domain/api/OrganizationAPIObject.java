package edu.ku.cete.domain.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class OrganizationAPIObject implements Cloneable{
	private String recordType;
	private String parentId;
	private String level;
	private String uniqueId;
	private String name;
	private String address1;
	private String address2;
	private String city;
	private String stateId;
	private String zip;
	@JsonDeserialize(using = APIBooleanDeserializer.class)
	private Boolean active;
	private long userID;
	private long assessmentProgramId;
	private long timeZoneID;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	
	public String getRecordType() {
		return this.recordType!= null ?this.recordType.toUpperCase():"";
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getParentId() {
		return this.parentId!= null ?this.parentId:"";
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getLevel() {
		return this.level!= null ?this.level.toUpperCase():"";
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getUniqueId() {
		return this.uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getName() {
		return this.name!= null ?this.name:"";
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress1() {
		return this.address1!= null ?this.address1:"";
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2!= null ?this.address2:"";
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return this.city!= null ?this.city:"";
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateId() {
		return this.stateId!= null ?this.stateId:"";
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getZip() {
		return this.zip!= null ?this.zip:"";
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Boolean getActive() {
		if(this.active==null) {
			this.active=true;
		}
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@JsonIgnore
	public long getUserID() {
		return this.userID;
	}
	
	@JsonProperty
	public void setUserID(long userID) {
		this.userID = userID;
	}

	@JsonIgnore
	public long getAssessmentProgramId() {
		return this.assessmentProgramId;
	}

	@JsonProperty
	public void setAssessmentProgramId(long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public boolean isContractingOrganization() {
		if(this.level.equalsIgnoreCase("st")) {
			return true;
		}
		return false;
	}
	
	@JsonIgnore
	public long getTimezoneID() {
		return this.timeZoneID;
	}

	@JsonProperty
	public void setTimezoneID(long timeZoneID) {
		this.timeZoneID = timeZoneID;
	}

	public String[] getOrganizationStructure(){
		
		if(this.level.equalsIgnoreCase("st")) {
			String[] organizationStructure= {"CONS", "ST", "DT", "SCH"};
			return organizationStructure;
		}
		return null;
	}

}
