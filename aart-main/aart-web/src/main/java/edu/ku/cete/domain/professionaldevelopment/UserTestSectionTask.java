package edu.ku.cete.domain.professionaldevelopment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserTestSectionTask extends UserTestSectionTaskKey {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column usertestsectiontask.sortorder
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	private Integer sortOrder;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column usertestsectiontask.createddate
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	private Date createdDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column usertestsectiontask.modifieddate
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	private Date modifiedDate;
	
	private List<UserTestSectionTaskFoil> foils = new ArrayList<UserTestSectionTaskFoil>();

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column usertestsectiontask.sortorder
	 * @return  the value of usertestsectiontask.sortorder
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	public Integer getSortOrder() {
		return sortOrder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column usertestsectiontask.sortorder
	 * @param sortOrder  the value for usertestsectiontask.sortorder
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column usertestsectiontask.createddate
	 * @return  the value of usertestsectiontask.createddate
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column usertestsectiontask.createddate
	 * @param createdDate  the value for usertestsectiontask.createddate
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column usertestsectiontask.modifieddate
	 * @return  the value of usertestsectiontask.modifieddate
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	public Date getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column usertestsectiontask.modifieddate
	 * @param modifiedDate  the value for usertestsectiontask.modifieddate
	 * @mbggenerated  Wed May 28 18:21:07 CDT 2014
	 */
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@JsonProperty("studentsTestSectionsTasksFoils")
	public List<UserTestSectionTaskFoil> getFoils() {
		return foils;
	}

	@JsonProperty("studentsTestSectionsTasksFoils")
	public void setFoils(List<UserTestSectionTaskFoil> foils) {
		this.foils = foils;
	}
}