
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
 *         &lt;element name="count_kids_by_dateResult" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "countKidsByDateResult"
})
@XmlRootElement(name = "count_kids_by_dateResponse")
public class CountKidsByDateResponse {

    @XmlElement(name = "count_kids_by_dateResult")
    protected int countKidsByDateResult;

    /**
     * Gets the value of the countKidsByDateResult property.
     * 
     */
    public int getCountKidsByDateResult() {
        return countKidsByDateResult;
    }

    /**
     * Sets the value of the countKidsByDateResult property.
     * 
     */
    public void setCountKidsByDateResult(int value) {
        this.countKidsByDateResult = value;
    }

}
