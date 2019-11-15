/**
 * 
 */
package edu.ku.cete.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.domain.validation.FieldSpecification;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * @author m802r921
 * Overrides CsvToBean for parsing one record at a time.
 * @param <T>
 */
public class AartCsvToBean<T> extends CsvToBean<T> {
	
	private Logger logger = LoggerFactory.getLogger(AartCsvToBean.class);
	/**
	 * field specifications for scrs.
	 */
	private Map<String, FieldSpecification> fieldSpecificationMap
	= new HashMap<String, FieldSpecification>();
	private Map<String, String> columnAttributeMap;
    /**
	 * @return the fieldSpecificationMap
	 */
	public Map<String, FieldSpecification> getFieldSpecificationMap() {
		return fieldSpecificationMap;
	}
	/**
	 * @param fieldSpecificationMp the fieldSpecificationMap to set
	 */
	public void setFieldSpecificationMap(
			Map<String, FieldSpecification> fieldSpecificationMp) {
		//Do not set empty ones.
		if (MapUtils.isNotEmpty(fieldSpecificationMp)) {
			fieldSpecificationMap = fieldSpecificationMp;
		}
	}
	/**
     * This is written because the default implementation throws only run time exception.
     * @param mapper {@link MappingStrategy}
     * @param csv {@link CSVReader}
     * @return T
     * @throws IOException {@link IOException}
     * @throws AartParseException {@link AartParseException}
     * @throws IntrospectionException {@link IntrospectionException}
     * @throws InstantiationException {@link InstantiationException}
     * @throws InvocationTargetException {@link InvocationTargetException}
     * @throws IllegalAccessException {@link IllegalAccessException}
     */
    public final List<T> aartParse(MappingStrategy<T> mapper, CSVReader csv)
    		throws IOException, AartParseException,
    		IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException {
            //mapper.captureHeader(csv);
            ((AartColumnMappingStrategy<T>) mapper).validateHeaders(csv);
            String[] line = csv.readNext();
            List<T> list = new ArrayList<T>();
            int row = 0;
            while (line != null) {
            	T obj = null;
            	row++;
            	if (StringUtil.isValidCsvLine(line) && ((AartColumnMappingStrategy<T>) mapper).validateLineCoulumn(line)) {
                    obj = processAartLine(mapper, line);
                    logger.debug("processAartLine() returned object: {}", obj);
                    // TODO: (Kyle) null check object
            	} else {
            		obj = mapper.createBean();
            		((ValidateableRecord) obj).addInvalidField("Row " + row, ParsingConstants.BLANK , true, " not match with header columns.");
            	}
                list.add(obj);
                line = csv.readNext();
                
            }
            return list;
    }

    /**
     * @param bean {@link T}
     * @param propertyDesc {@link PropertyDescriptor}
     * @param value {@link String}
     * @return
     */
    private void validate(T bean, PropertyDescriptor propertyDesc, String value) {
    	FieldSpecification fieldSpec = fieldSpecificationMap.get(propertyDesc.getName());
    	if (fieldSpec != null) {
    		fieldSpec.validate(bean, value);
    	}
    }
    /**
     * @param mapper {@link MappingStrategy}
     * @param line {@link String[]}
     * @return T {@link Object}
     * @throws IllegalAccessException {@link IllegalAccessException}
     * @throws InvocationTargetException {@link InvocationTargetException}
     * @throws InstantiationException {@link InstantiationException}
     * @throws IntrospectionException {@link IntrospectionException}
     * @throws AartParseException {@link AartParseException}
     */
    public final T processAartLine(MappingStrategy<T> mapper, String[] line)
    		throws AartParseException, IllegalAccessException, InvocationTargetException,
    		InstantiationException, IntrospectionException  {
    	logger.debug("Line to convert: {}", line);
        T bean = mapper.createBean();
       
        for (int colPosition = 0; colPosition < line.length; colPosition++) {
            PropertyDescriptor prop = mapper.findDescriptor(colPosition);
            
            if (null != prop) {
                String value = checkForTrim(line[colPosition], prop);
                validate(bean, prop, value);
            } else if(this.columnAttributeMap != null && MapUtils.isNotEmpty(columnAttributeMap)) {
            	//INFO: there is no setter in the object for the attribute name that matches the given header.
            	String attributeName = getAttributeName(colPosition);
                validate(bean, attributeName,line[colPosition] );
            	logger.debug("bean, " +bean+
            			"prop," +prop);
            }
        }
        logger.debug("Returning record from cvs: {}", bean);
        return bean;
    }
    private void validate(T bean, String attributeName, String value) {
    	FieldSpecification fieldSpec = fieldSpecificationMap.get(attributeName);
    	if (fieldSpec != null) {
    		fieldSpec.validate(bean, value);
    	}
    }
	/**
     * @param s {@link String}
     * @param prop {@link PropertyDescriptor}
     * @return {@link String}
     */
    private String checkForTrim(String s, PropertyDescriptor prop) {
        return trimmableProperty(prop) ? s.trim() : s;
    }

    /**
     * @param prop {@link PropertyDescriptor}
     * @return {@link Boolean}
     */
    private boolean trimmableProperty(PropertyDescriptor prop) {
        return !prop.getPropertyType().getName().contains("String");
    }
	/**
	 * @param firstContactColumnAttributeMap
	 */
	public void setColumnAttributeMap(
			Map<String, String> firstContactColumnAttributeMap) {
		this.columnAttributeMap = firstContactColumnAttributeMap;
	}
	
	public String getAttributeName(int position) {
		int i=0;
		String attributeName = null;
		for(Entry<String, String> columnAttributeEntry :columnAttributeMap.entrySet()) {
			if(i == position) {
				attributeName = columnAttributeEntry.getValue();
			}
			i ++;
		}
		return attributeName;
	}
}