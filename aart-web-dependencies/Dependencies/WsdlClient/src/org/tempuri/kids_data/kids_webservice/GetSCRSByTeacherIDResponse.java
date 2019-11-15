
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
 *         &lt;element name="get_SCRS_by_TeacherIDResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getSCRSByTeacherIDResult"
})
@XmlRootElement(name = "get_SCRS_by_TeacherIDResponse")
public class GetSCRSByTeacherIDResponse {

    @XmlElement(name = "get_SCRS_by_TeacherIDResult")
    protected String getSCRSByTeacherIDResult;

    /**
     * Gets the value of the getSCRSByTeacherIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetSCRSByTeacherIDResult() {
        return getSCRSByTeacherIDResult;
    }

    /**
     * Sets the value of the getSCRSByTeacherIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetSCRSByTeacherIDResult(String value) {
        this.getSCRSByTeacherIDResult = value;
    }

}
