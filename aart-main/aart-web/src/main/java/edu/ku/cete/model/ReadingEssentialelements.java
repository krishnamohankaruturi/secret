package edu.ku.cete.model;

public class ReadingEssentialelements {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column readingessentialelements.id
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column readingessentialelements.essentialelement
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    private String essentialElement;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column readingessentialelements.systemcode
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    private String systemCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column readingessentialelements.parentcode
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    private String parentCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column readingessentialelements.gradeabbrname
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    private String gradeAbbrName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column readingessentialelements.id
     *
     * @return the value of readingessentialelements.id
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column readingessentialelements.id
     *
     * @param id the value for readingessentialelements.id
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column readingessentialelements.essentialelement
     *
     * @return the value of readingessentialelements.essentialelement
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public String getEssentialElement() {
        return essentialElement;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column readingessentialelements.essentialelement
     *
     * @param essentialElement the value for readingessentialelements.essentialelement
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public void setEssentialElement(String essentialElement) {
        this.essentialElement = essentialElement == null ? null : essentialElement.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column readingessentialelements.systemcode
     *
     * @return the value of readingessentialelements.systemcode
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column readingessentialelements.systemcode
     *
     * @param systemCode the value for readingessentialelements.systemcode
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode == null ? null : systemCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column readingessentialelements.parentcode
     *
     * @return the value of readingessentialelements.parentcode
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public String getParentCode() {
        return parentCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column readingessentialelements.parentcode
     *
     * @param parentCode the value for readingessentialelements.parentcode
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column readingessentialelements.gradeabbrname
     *
     * @return the value of readingessentialelements.gradeabbrname
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public String getGradeAbbrName() {
        return gradeAbbrName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column readingessentialelements.gradeabbrname
     *
     * @param gradeAbbrName the value for readingessentialelements.gradeabbrname
     *
     * @mbggenerated Mon Jun 05 15:30:46 CDT 2017
     */
    public void setGradeAbbrName(String gradeAbbrName) {
        this.gradeAbbrName = gradeAbbrName == null ? null : gradeAbbrName.trim();
    }
}