
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
 *         &lt;element name="get_EOYA_by_SSIDResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getEOYABySSIDResult"
})
@XmlRootElement(name = "get_EOYA_by_SSIDResponse")
public class GetEOYABySSIDResponse {

    @XmlElement(name = "get_EOYA_by_SSIDResult")
    protected String getEOYABySSIDResult;

    /**
     * Gets the value of the getEOYABySSIDResult property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGetEOYABySSIDResult() {
        return getEOYABySSIDResult;
    }

    /**
     * Sets the value of the getEOYABySSIDResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGetEOYABySSIDResult(String value) {
        this.getEOYABySSIDResult = value;
    }

}
