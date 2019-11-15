package edu.ku.cete.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.domain.validation.FieldSpecification;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * @author vittaly
 * Overrides CsvToBean for parsing csv file one vertical record  
 * at a time.
 * @param <T>
 */
public class AartCsvVerticalParser<T> extends CsvToBean<T> {

	private Logger logger = LoggerFactory.getLogger(AartCsvVerticalParser.class);

	/**
	 * field specifications.
	 */
	private Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();

	/**
	 * @return the fieldSpecificationMap
	 */
	public Map<String, FieldSpecification> getFieldSpecificationMap() {
		return fieldSpecificationMap;
	}

	/**
	 * @param fieldSpecificationMp
	 *            the fieldSpecificationMap to set
	 */
	public void setFieldSpecificationMap(
			Map<String, FieldSpecification> fieldSpecificationMp) {
		// Do not set empty ones.
		if (MapUtils.isNotEmpty(fieldSpecificationMp)) {
			fieldSpecificationMap = fieldSpecificationMp;
		}
	}

	/**
	 * @param mapper
	 * @param csv
	 * @return
	 * @throws IOException
	 * @throws AartParseException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	public final List<T> aartParse(MappingStrategy<T> mapper, CSVReader csv)
			throws IOException, AartParseException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			IntrospectionException {
		// mapper.captureHeader(csv);
		((AartColumnMappingStrategy<T>) mapper).validateHeaders(csv);
		String[] line = csv.readNext();
		List<T> list = new ArrayList<T>();
		while (line != null) {
			T obj = null;
			if (StringUtil.isValidCsvLine(line)) {
				obj = processAartLine(mapper, line);
				logger.debug("processAartLine() returned object: {}", obj);
				// TODO: (Kyle) null check object
			} else {
				obj = mapper.createBean();
				((ValidateableRecord) obj).addInvalidField(
						ParsingConstants.BLANK + FieldName.RECORD,
						ParsingConstants.BLANK, true);
			}
			list.add(obj);
			line = csv.readNext();
		}
		return list;
	}

	/**
	 * This is for validating all csv columns other than attribute, value pairs .
	 * @param bean
	 * @param propertyDesc
	 * @param value
	 */
	private void validate(T bean, PropertyDescriptor propertyDesc, String value) {
		FieldSpecification fieldSpec = fieldSpecificationMap.get(propertyDesc
				.getName().toLowerCase());		
		fieldSpec.validate(bean, value);		
	}

	/**
	 * This is for validating attribute, value pairs only .
	 * @param bean
	 * @param inAttribute
	 * @param inValue
	 */
	public void validate(Object bean, String inAttribute, String inValue) {
		FieldSpecification fieldSpec = fieldSpecificationMap.get(inAttribute.toLowerCase());
		if (fieldSpec !=  null) {
			//Attribute_Value(inValue) cannot be null, if so report an error.
			if(inValue != null &&
					StringUtils.isNotEmpty(inValue)) {
				fieldSpec.validateAttribute(bean, inAttribute,inValue);
			} else {
				((ValidateableRecord) bean).addInvalidField(
						FieldName.ATTRIBUTE_VALUE + ParsingConstants.BLANK,
						inValue,					
						true, InvalidTypes.NOT_ALLOWED);
			}
		} else {
			((ValidateableRecord) bean).addInvalidField(
					FieldName.ATTRIBUTE_NAME + ParsingConstants.BLANK,
					inAttribute,					
					true, InvalidTypes.NOT_ALLOWED);
		}
	}
	
	/**
	 * @param mapper
	 * @param line
	 * @return
	 * @throws AartParseException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	public final T processAartLine(MappingStrategy<T> mapper, String[] line)
			throws AartParseException, IllegalAccessException,
			InvocationTargetException, InstantiationException,
			IntrospectionException {
		logger.debug("Line to convert: {}", line);
		T bean = mapper.createBean();
		for (int col = 0; col < line.length; col++) {
			PropertyDescriptor prop = mapper.findDescriptor(col);
			if (null != prop) {
				if(prop.getName().equalsIgnoreCase(
						CommonConstants.CSV_VERTICLE_RECORDS_COLUMNS_HEADER_ATTRIBUTE_NAME)) {
					String name = checkForTrim(line[col], prop);
					String value = checkForTrim(line[col+1], mapper.findDescriptor(col+1));
					validate(bean, name, value);
				} else if(!prop.getName().equalsIgnoreCase(
						CommonConstants.CSV_VERTICLE_RECORDS_COLUMNS_HEADER_ATTRIBUTE_VALUE)) {
					String value = checkForTrim(line[col], prop);
					validate(bean, prop, value);
				}
			}
		}
		logger.debug("Returning record from cvs: {}", bean);
		return bean;
	}

	/**
	 * @param s
	 *            {@link String}
	 * @param prop
	 *            {@link PropertyDescriptor}
	 * @return {@link String}
	 */
	private String checkForTrim(String s, PropertyDescriptor prop) {
		return trimmableProperty(prop) ? s.trim() : s;
	}

	/**
	 * @param prop
	 *            {@link PropertyDescriptor}
	 * @return {@link Boolean}
	 */
	private boolean trimmableProperty(PropertyDescriptor prop) {
		return prop.getPropertyType().getName().contains("String");
	}
}
