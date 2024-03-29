package edu.ku.cete.domain.api;

import java.util.Date;

public class OrganizationAddress {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.id
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.orgid
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private Long orgID;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.orgaddress1
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private String orgAddress1;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.orgaddress2
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private String orgAddress2;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.city
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private String city;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.state
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private String state;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.zip
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private String zip;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.createduser
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private Long createdUser;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.createddate
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private Date createdDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.modifieduser
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private Long modifiedUser;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column organizationaddress.modifieddate
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	private Date modifiedDate;


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.id
	 * @return  the value of organizationaddress.id
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public Long getId() {
		return id;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.id
	 * @param id  the value for organizationaddress.id
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.orgid
	 * @return  the value of organizationaddress.orgid
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public Long getOrgID() {
		return orgID;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.orgid
	 * @param orgID  the value for organizationaddress.orgid
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setOrgID(Long orgID) {
		this.orgID = orgID;
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.orgaddress1
	 * @return  the value of organizationaddress.orgaddress1
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public String getOrgAddress1() {
		return orgAddress1;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.orgaddress1
	 * @param orgAddress1  the value for organizationaddress.orgaddress1
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setOrgAddress1(String orgAddress1) {
		this.orgAddress1 = orgAddress1 == null ? "" : orgAddress1.trim();
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.orgaddress2
	 * @return  the value of organizationaddress.orgaddress2
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public String getOrgAddress2() {
		return orgAddress2;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.orgaddress2
	 * @param orgAddress2  the value for organizationaddress.orgaddress2
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setOrgAddress2(String orgAddress2) {
		this.orgAddress2 = orgAddress2 == null ? "" : orgAddress2.trim();
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.city
	 * @return  the value of organizationaddress.city
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public String getCity() {
		return city;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.city
	 * @param city  the value for organizationaddress.city
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setCity(String city) {
		this.city = city == null ? "" : city.trim();
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.state
	 * @return  the value of organizationaddress.state
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public String getState() {
		return state;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.state
	 * @param state  the value for organizationaddress.state
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setState(String state) {
		this.state = state == null ? "" : state.trim();
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.zip
	 * @return  the value of organizationaddress.zip
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public String getZip() {
		return zip;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.zip
	 * @param zip  the value for organizationaddress.zip
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setZip(String zip) {
		this.zip = zip == null ? "" : zip.trim();
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.createduser
	 * @return  the value of organizationaddress.createduser
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public Long getCreatedUser() {
		return createdUser;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.createduser
	 * @param createdUser  the value for organizationaddress.createduser
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.createddate
	 * @return  the value of organizationaddress.createddate
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public Date getCreatedDate() {
		return createdDate;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.createddate
	 * @param createdDate  the value for organizationaddress.createddate
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.modifieduser
	 * @return  the value of organizationaddress.modifieduser
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public Long getModifiedUser() {
		return modifiedUser;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.modifieduser
	 * @param modifiedUser  the value for organizationaddress.modifieduser
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}


	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column organizationaddress.modifieddate
	 * @return  the value of organizationaddress.modifieddate
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}


	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column organizationaddress.modifieddate
	 * @param modifiedDate  the value for organizationaddress.modifieddate
	 * @mbg.generated  Sun Aug 26 05:13:05 CDT 2018
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	//Default Constructor
	public OrganizationAddress() {
		super();
	}

	public OrganizationAddress(Long orgID, String orgAddress1, String orgAddress2, String city, String state,
			String zip) {
		this.orgID = orgID;
		this.orgAddress1 = orgAddress1 == null ? "" : orgAddress1.trim();
		this.orgAddress2 = orgAddress2 == null ? "" : orgAddress2.trim();
		this.city = city == null ? "" : city.trim();
		this.state = state == null ? "" : state.trim();
		this.zip = zip == null ? "" : zip.trim();
	}


	public void setDataForOrgAddress(Long orgID, String orgAddress1, String orgAddress2, String city, String state,
			String zip) {
    	this.orgID = orgID;
    	this.orgAddress1 = orgAddress1 == null ? "" : orgAddress1.trim();
		this.orgAddress2 = orgAddress2 == null ? "" : orgAddress2.trim();
		this.city = city == null ? "" : city.trim();
		this.state = state == null ? "" : state.trim();
		this.zip = zip == null ? "" : zip.trim();
    }
}