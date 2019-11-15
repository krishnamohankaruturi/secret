
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
 *         &lt;element name="get_DistrictReclassification_by_dateResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strFromDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getDistrictReclassificationByDateResult",
    "strFromDate"
})
@XmlRootElement(name = "get_DistrictReclassification_by_dateResponse")
public class GetDistrictReclassificationByDateResponse {

    @XmlElement(name = "get_DistrictReclassification_by_dateResult")
    protected String getDistrictReclassificationByDateResult;
    protected String strFromDate;

    /**
     * Gets the value of the getDistrictReclassificationByDateResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetDistrictReclassificationByDateResult() {
        return getDistrictReclassificationByDateResult;
    }

    /**
     * Sets the value of the getDistrictReclassificationByDateResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetDistrictReclassificationByDateResult(String value) {
        this.getDistrictReclassificationByDateResult = value;
    }

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

}
