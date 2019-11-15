
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
 *         &lt;element name="get_SCRS_by_SSIDResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getSCRSBySSIDResult"
})
@XmlRootElement(name = "get_SCRS_by_SSIDResponse")
public class GetSCRSBySSIDResponse {

    @XmlElement(name = "get_SCRS_by_SSIDResult")
    protected String getSCRSBySSIDResult;

    /**
     * Gets the value of the getSCRSBySSIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetSCRSBySSIDResult() {
        return getSCRSBySSIDResult;
    }

    /**
     * Sets the value of the getSCRSBySSIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetSCRSBySSIDResult(String value) {
        this.getSCRSBySSIDResult = value;
    }

}
