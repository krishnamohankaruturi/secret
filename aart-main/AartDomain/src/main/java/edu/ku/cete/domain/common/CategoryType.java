package edu.ku.cete.domain.common;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author m802r921
 * Category type type of metadata saying enrollment status or subject etc..
 */
public class CategoryType extends AuditableDomain{
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public final.category_type.id
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public final.category_type.type_name
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    private String typeName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public final.category_type.type_code
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    private String typeCode;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public final.category_type.type_description
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    private String typeDescription;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public final.category_type.id
     *
     * @return the value of public final.category_type.id
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public final.category_type.id
     *
     * @param id the value for public final.category_type.id
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public final.category_type.type_name
     *
     * @return the value of public final.category_type.type_name
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final String getTypeName() {
        return typeName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public final.category_type.type_name
     *
     * @param typeName the value for public final.category_type.type_name
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public final.category_type.type_code
     *
     * @return the value of public final.category_type.type_code
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final String getTypeCode() {
        return typeCode;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public final.category_type.type_code
     *
     * @param typeCode the value for public final.category_type.type_code
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public final.category_type.type_description
     *
     * @return the value of public final.category_type.type_description
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final String getTypeDescription() {
        return typeDescription;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public final.category_type.type_description
     *
     * @param typeDescription the value for public final.category_type.type_description
     *
     * @mbggenerated Fri May 04 12:06:31 CDT 2012
     */
    public final void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription == null ? null : typeDescription.trim();
    }
}