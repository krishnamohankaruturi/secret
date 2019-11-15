
package org.tempuri.kids_data.kids_webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strFromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strToDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strSchool_Year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="testtype" type="{http://tempuri.org/kids_data/KIDS_WebService}TestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "strFromDate",
    "strToDate",
    "strSchoolYear",
    "testtype"
})
@XmlRootElement(name = "get_EOYA_by_date")
public class GetEOYAByDate {

    protected String strFromDate;
    protected String strToDate;
    @XmlElement(name = "strSchool_Year")
    protected String strSchoolYear;
    @XmlElement(required = true)
    protected TestType testtype;

    /**
     * Gets the value of the strFromDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrFromDate() {
        return strFromDate;
    }

    /**
     * Sets the value of the strFromDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrFromDate(String value) {
        this.strFromDate = value;
    }

    /**
     * Gets the value of the strToDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrToDate() {
        return strToDate;
    }

    /**
     * Sets the value of the strToDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrToDate(String value) {
        this.strToDate = value;
    }

    /**
     * Gets the value of the strSchoolYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrSchoolYear() {
        return strSchoolYear;
    }

    /**
     * Sets the value of the strSchoolYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrSchoolYear(String value) {
        this.strSchoolYear = value;
    }

    /**
     * Gets the value of the testtype property.
     * 
     * @return
     *     possible object is
     *     {@link TestType }
     *     
     */
    public TestType getTesttype() {
        return testtype;
    }

    /**
     * Sets the value of the testtype property.
     * 
     * @param value
     *     allowed object is
     *     {@link TestType }
     *     
     */
    public void setTesttype(TestType value) {
        this.testtype = value;
    }

}
