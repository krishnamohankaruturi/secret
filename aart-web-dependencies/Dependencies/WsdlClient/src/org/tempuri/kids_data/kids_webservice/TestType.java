
package org.tempuri.kids_data.kids_webservice;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TestType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TestType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ReadingMath"/>
 *     &lt;enumeration value="Writing"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TestType")
@XmlEnum
public enum TestType {

    @XmlEnumValue("ReadingMath")
    READING_MATH("ReadingMath"),
    @XmlEnumValue("Writing")
    WRITING("Writing");
    private final String value;

    TestType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TestType fromValue(String v) {
        for (TestType c: TestType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
