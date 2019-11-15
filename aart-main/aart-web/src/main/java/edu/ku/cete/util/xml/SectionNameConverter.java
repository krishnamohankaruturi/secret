package edu.ku.cete.util.xml;

import com.thoughtworks.xstream.converters.SingleValueConverter;
import edu.ku.cete.report.SectionName;

public class SectionNameConverter implements SingleValueConverter {

    public String toString(Object obj) {
            return ((SectionName) obj).getName();
    }

    public Object fromString(String name) {
            return new SectionName(name);
    }

    public boolean canConvert(Class type) {
            return type.equals(SectionName.class);
    }

}
