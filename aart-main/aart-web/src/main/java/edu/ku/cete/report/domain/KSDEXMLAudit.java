package edu.ku.cete.report.domain;

import java.util.Date;

public class KSDEXMLAudit {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.id
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.type
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.xml
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private String xml;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.createdate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private Date createDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.processeddate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private Date processedDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.processedcode
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private String processedCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.fromdate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private Date fromDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.todate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private Date toDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.errors
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private String errors;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.successcount
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private Integer successCount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ksdexmlaudit.failedcount
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    private Integer failedCount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.id
     *
     * @return the value of ksdexmlaudit.id
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.id
     *
     * @param id the value for ksdexmlaudit.id
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.type
     *
     * @return the value of ksdexmlaudit.type
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.type
     *
     * @param type the value for ksdexmlaudit.type
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.xml
     *
     * @return the value of ksdexmlaudit.xml
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public String getXml() {
        return xml;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.xml
     *
     * @param xml the value for ksdexmlaudit.xml
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setXml(String xml) {
        this.xml = xml == null ? null : xml.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.createdate
     *
     * @return the value of ksdexmlaudit.createdate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.createdate
     *
     * @param createDate the value for ksdexmlaudit.createdate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.processeddate
     *
     * @return the value of ksdexmlaudit.processeddate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public Date getProcessedDate() {
        return processedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.processeddate
     *
     * @param processedDate the value for ksdexmlaudit.processeddate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setProcessedDate(Date processedDate) {
        this.processedDate = processedDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.processedcode
     *
     * @return the value of ksdexmlaudit.processedcode
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public String getProcessedCode() {
        return processedCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.processedcode
     *
     * @param processedCode the value for ksdexmlaudit.processedcode
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setProcessedCode(String processedCode) {
        this.processedCode = processedCode == null ? null : processedCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.fromdate
     *
     * @return the value of ksdexmlaudit.fromdate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.fromdate
     *
     * @param fromDate the value for ksdexmlaudit.fromdate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.todate
     *
     * @return the value of ksdexmlaudit.todate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.todate
     *
     * @param toDate the value for ksdexmlaudit.todate
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.errors
     *
     * @return the value of ksdexmlaudit.errors
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public String getErrors() {
        return errors;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.errors
     *
     * @param errors the value for ksdexmlaudit.errors
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setErrors(String errors) {
        this.errors = errors == null ? null : errors.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.successcount
     *
     * @return the value of ksdexmlaudit.successcount
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public Integer getSuccessCount() {
        return successCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.successcount
     *
     * @param successCount the value for ksdexmlaudit.successcount
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ksdexmlaudit.failedcount
     *
     * @return the value of ksdexmlaudit.failedcount
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public Integer getFailedCount() {
        return failedCount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ksdexmlaudit.failedcount
     *
     * @param failedCount the value for ksdexmlaudit.failedcount
     *
     * @mbggenerated Mon Dec 07 11:30:30 CST 2015
     */
    public void setFailedCount(Integer failedCount) {
        this.failedCount = failedCount;
    }
    public void validate() {
		if (this.id == null) {
			this.id = 0L;
		}
		if (this.type == null) {
			this.type = " ";
		}
		if (this.xml == null) {
			this.xml = " ";
		}
		if (this.processedDate == null) {
			this.processedDate = new Date();
		}
		if (this.processedCode == null) {
			this.processedCode = " ";
		}
		if (this.fromDate == null) {
			this.fromDate = new Date();
		}
		if (this.toDate == null) {
			this.toDate = new Date();
		}
		if (this.errors == null) {
			this.errors = " ";
		}

		if (this.successCount == null) {
			this.successCount = 0;
		}
		if (this.failedCount == null) {
			this.failedCount = 0;
		}

	}
}