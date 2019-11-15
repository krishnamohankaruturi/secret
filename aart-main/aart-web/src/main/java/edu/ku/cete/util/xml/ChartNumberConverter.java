package edu.ku.cete.util.xml;

import com.thoughtworks.xstream.converters.SingleValueConverter;

import edu.ku.cete.report.ChartNumber;

public class ChartNumberConverter implements SingleValueConverter {

    public String toString(Object obj) {
            return ((ChartNumber) obj).getName();
    }

    public Object fromString(String name) {
            return new ChartNumber(name);
    }

    public boolean canConvert(Class type) {
            return type.equals(ChartNumber.class);
    }

}
