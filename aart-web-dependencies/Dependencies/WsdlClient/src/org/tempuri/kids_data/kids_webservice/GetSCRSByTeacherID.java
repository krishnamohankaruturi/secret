
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
 *         &lt;element name="teacherID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strSchool_Year" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "teacherID",
    "strSchoolYear"
})
@XmlRootElement(name = "get_SCRS_by_TeacherID")
public class GetSCRSByTeacherID {

    protected String teacherID;
    @XmlElement(name = "strSchool_Year")
    protected String strSchoolYear;

    /**
     * Gets the value of the teacherID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTeacherID() {
        return teacherID;
    }

    /**
     * Sets the value of the teacherID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTeacherID(String value) {
        this.teacherID = value;
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

}
