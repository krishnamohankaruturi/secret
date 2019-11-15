package edu.ku.cete.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ku.cete.domain.validation.FieldName;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

/**
 *
 * @author neil.howerton
 *
 */
public class AartColumnMappingStrategy<T> extends HeaderColumnNameTranslateMappingStrategy<T> {
	private static Logger LOGGER = LoggerFactory.getLogger(AartColumnMappingStrategy.class);

    /**
     *
     *@param csvReader {@link CSVReader}
     * @throws IOException IOException
     * @throws AartParseException AartParseException
     */
    public final void validateHeaders(final CSVReader csvReader) throws IOException, AartParseException {
        this.captureHeader(csvReader);
        Map<String, String> columnMapping = this.getColumnMapping();
        if (columnMapping != null) {
            Set<String> columnKeys = columnMapping.keySet();
            Set<String> caseColumnKeys = new HashSet<String>();
            for(String columnKey: columnKeys){
            	LOGGER.debug("columnKey "+columnKey);
            	caseColumnKeys.add(columnKey.toUpperCase());
            }
            if (this.header.length == columnKeys.size()) {
                for (String headerVal : this.header) {
                	LOGGER.debug("headerVal" +headerVal);
                    if (!caseColumnKeys.contains(headerVal.toUpperCase())) {
                    	LOGGER.warn(FieldName.HEADER_NAME + ParsingConstants.BLANK +
                        		headerVal + "Header Columns does not match");
                        throw new AartParseException(FieldName.HEADER_NAME + ParsingConstants.BLANK,
                        		headerVal, new RuntimeException("Header Columns does not match"));
                    }
                }
            } else {
            	LOGGER.warn(FieldName.HEADER_COLUMN_COUNT + ParsingConstants.BLANK +
                		this.header.length + ParsingConstants.BLANK + "Header count does not match");
            	throw new AartParseException(FieldName.HEADER_COLUMN_COUNT + ParsingConstants.BLANK,
                		this.header.length + ParsingConstants.BLANK,
                		new RuntimeException("Header Column count does not match"));
            }
        } else {
        	LOGGER.warn(FieldName.HEADER_COLUMN_COUNT + ParsingConstants.BLANK +
            		ParsingConstants.BLANK + "Header count does not match");
        	throw new AartParseException(FieldName.HEADER_COLUMN_COUNT + ParsingConstants.BLANK,
            		ParsingConstants.BLANK,
            		new RuntimeException("Header Column count does not match"));
        }
    }

    /**
    *
    *@param csvReader {@link CSVReader}
    * @throws IOException IOException
    * @throws AartParseException AartParseException
    */
   public final boolean validateLineCoulumn(String[] line) {
       Map<String, String> columnMapping = this.getColumnMapping();
       if (columnMapping != null) {
           Set<String> columnKeys = columnMapping.keySet();
           if (line.length != columnKeys.size()) {
        	  return false; 
           }  
       } 
       return true;
   }

    /**
     *
     */
    @Override
    public PropertyDescriptor findDescriptor(int paramInt) throws IntrospectionException {
        String str = getColumnName(paramInt);
        return (((null != str) && (str.trim().length() > 0)) ? findDescriptor(str) : null);
    }
    
    @Override
    protected PropertyDescriptor findDescriptor(String paramString)
            throws IntrospectionException {
        if (null == this.descriptorMap)
          this.descriptorMap = loadDescriptorMap(getType());
        return ((PropertyDescriptor)this.descriptorMap.get(paramString.toUpperCase().trim()));
    }
}
