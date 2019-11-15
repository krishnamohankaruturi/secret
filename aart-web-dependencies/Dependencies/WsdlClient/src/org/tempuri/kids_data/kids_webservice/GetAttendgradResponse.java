
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
 *         &lt;element name="get_attendgradResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bolRequest_Complete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "getAttendgradResult",
    "bolRequestComplete"
})
@XmlRootElement(name = "get_attendgradResponse")
public class GetAttendgradResponse {

    @XmlElement(name = "get_attendgradResult")
    protected String getAttendgradResult;
    @XmlElement(name = "bolRequest_Complete")
    protected boolean bolRequestComplete;

    /**
     * Gets the value of the getAttendgradResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetAttendgradResult() {
        return getAttendgradResult;
    }

    /**
     * Sets the value of the getAttendgradResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetAttendgradResult(String value) {
        this.getAttendgradResult = value;
    }

    /**
     * Gets the value of the bolRequestComplete property.
     * 
     */
    public boolean isBolRequestComplete() {
        return bolRequestComplete;
    }

    /**
     * Sets the value of the bolRequestComplete property.
     * 
     */
    public void setBolRequestComplete(boolean value) {
        this.bolRequestComplete = value;
    }

}
