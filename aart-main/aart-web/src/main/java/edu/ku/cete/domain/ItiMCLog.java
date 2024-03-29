package edu.ku.cete.domain;

import java.util.Date;

public class ItiMCLog {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.id
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private Long id;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.requestid
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private Long requestId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.fromdate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private Date fromDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.todate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private Date toDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.response
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private String response;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.errors
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private String errors;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.actiontype
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private String actionType;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.createddate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private Date createdDate;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column itimclog.createduser
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	private Long createdUser;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.id
	 * @return  the value of itimclog.id
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public Long getId() {
		return id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.id
	 * @param id  the value for itimclog.id
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.requestid
	 * @return  the value of itimclog.requestid
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public Long getRequestId() {
		return requestId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.requestid
	 * @param requestId  the value for itimclog.requestid
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.fromdate
	 * @return  the value of itimclog.fromdate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.fromdate
	 * @param fromDate  the value for itimclog.fromdate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.todate
	 * @return  the value of itimclog.todate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.todate
	 * @param toDate  the value for itimclog.todate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.response
	 * @return  the value of itimclog.response
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.response
	 * @param response  the value for itimclog.response
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setResponse(String response) {
		this.response = response == null ? null : response.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.errors
	 * @return  the value of itimclog.errors
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.errors
	 * @param errors  the value for itimclog.errors
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setErrors(String errors) {
		this.errors = errors == null ? null : errors.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.actiontype
	 * @return  the value of itimclog.actiontype
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public String getActionType() {
		return actionType;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.actiontype
	 * @param actionType  the value for itimclog.actiontype
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setActionType(String actionType) {
		this.actionType = actionType == null ? null : actionType.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.createddate
	 * @return  the value of itimclog.createddate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.createddate
	 * @param createdDate  the value for itimclog.createddate
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column itimclog.createduser
	 * @return  the value of itimclog.createduser
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public Long getCreatedUser() {
		return createdUser;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column itimclog.createduser
	 * @param createdUser  the value for itimclog.createduser
	 * @mbggenerated  Thu Aug 21 10:13:34 CDT 2014
	 */
	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}
}