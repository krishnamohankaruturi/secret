package edu.ku.cete.util;

import java.beans.IntrospectionException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.model.InValidDetail;

/**
 * @author m802r921
 * The class containing COMMON (only) methods for all uploads.
 * @deprecated seems to be dead code
 */
public class UploadHelper {
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(UploadHelper.class);
	/**
	 * write the rejected records. The file object is used to avoid searching files to delete it.
	 * @param rejectedRecords {@link List}
	 * @param filePath {@link String}
	 * @param fileName {@link String}
	 * @param messageSource {@link MessageSource}
	 * @return {@link File}
	 */
	public static final File writeRejectedRecords(List<ValidateableRecord> rejectedRecords,
			String[] fileHeaders,
			String filePath,
			String fileName, MessageSource messageSource) {
		File file = null;
		File dir = null;
		if (rejectedRecords != null && CollectionUtils.isNotEmpty(rejectedRecords)) {
			try {
				file = new File(
						filePath + java.io.File.separator + fileName);
				dir = new File(
						filePath);
				dir.mkdir();
				file.createNewFile();
				LOGGER.debug("File Created Successfully" + filePath + java.io.File.separator + fileName);
				CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
				//TODO get the headers from upload.properties
				csvWriter
						.writeNext(fileHeaders);
				for (ValidateableRecord rejectedRecord : rejectedRecords) {
					csvWriter.writeNext(rejectedRecord.getRejectedReason(messageSource));
				}
				csvWriter.close();
				//to safely remove it from memory.
			} catch (IOException e) {
				LOGGER.error("Error in writing to file", e);
			} catch (Exception e) {
				//if file path is null or filename is null it will come here.
				LOGGER.error("Unknown Error in writing to file", e);
			}
		}
		return file;
	}
	
	public static final String writeRejectedRecords(List<ValidateableRecord> rejectedRecords,
			String[] fileHeaders, MessageSource messageSource) {
		StringWriter stringWriter = new StringWriter();
		if (rejectedRecords != null && CollectionUtils.isNotEmpty(rejectedRecords)) {
			try {
				
				for (String header : fileHeaders){
					stringWriter.append(header);
					stringWriter.append("\t");
				}
				stringWriter.append(System.getProperty("line.separator"));
				for (ValidateableRecord rejectedRecord : rejectedRecords) {
					for (String reasonColumn : rejectedRecord.getRejectedReason(messageSource)){
						stringWriter.append(reasonColumn);
						stringWriter.append("\t");
					}
					stringWriter.append(System.getProperty("line.separator"));
				}
				stringWriter.close();
			} catch (IOException e) {
				LOGGER.error("Error in writing to string", e);
			} catch (Exception e) {
				//if file path is null or filename is null it will come here.
				LOGGER.error("Unknown Error in writing to string", e);
			}
		}
		return stringWriter.toString();
	}
	
	/**
	 * write the rejected records.
	 * @param file {@link File}
	 */
	public static final void removeFile(File file) {
		try {
			if (file != null) {
				file.delete();
			}
			file = null;
		} catch (Exception e) {
			//if file path is null or filename is null it will come here.
			LOGGER.error("Unknown Error in deleting file", e);
		}
	}
	/**
	 * @param fullPath {@link String}
	 */
	public static void removeDir(String fullPath) {
		try {
			File file = new File(fullPath);
			if (file != null) {
				file.delete();
			}
			file = null;
		} catch (Exception e) {
			//if file path is null or filename is null it will come here.
			LOGGER.error("Unknown Error in deleting file", e);
		}
	}
	
	/**
	 * @param invalidOrgs
	 * @param orgColMap
	 */
	public static void convertActualFieldNameToCSVFieldName(List<? extends ValidateableRecord> invalidOrgs, Map<String,String> orgColMap) {
        for (ValidateableRecord upldOrg : invalidOrgs) {
            for (InValidDetail inValidDtl : upldOrg.getInValidDetails()) {
                for (String orgMapKey : orgColMap.keySet()){
                    //LOGGER.debug("orgMapKey, orgColMap.get(orgMapKey), inValidDtl.getActualFieldName() - " + orgMapKey + "," + orgColMap.get(orgMapKey) + "," + inValidDtl.getActualFieldName());
                    if (orgColMap.get(orgMapKey).equals(inValidDtl.getActualFieldName())){
                        inValidDtl.setFieldNameWithoutConversion(orgMapKey);
                        break;
                    }
                }
            }
        }
    }
	
	/**
	 * This method uses the AART parser to parse the contents of the uploaded file in to objects of the 
	 * specified class.
	 * 
	 * @param reader
	 * @param orgColMap
	 * @param fieldSpecificationMap
	 * @param typeParameterClass
	 * @return
	 * @throws IOException
	 * @throws AartParseException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	public static <T> List<T> aartParseUploadedContents(CSVReader reader, Map<String, String> orgColMap,
			Map<String, FieldSpecification> fieldSpecificationMap, Class<T> typeParameterClass) 
			throws IOException, AartParseException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException{
		 
		List<T> upLoadedObjs = new ArrayList<T>();
		 AartColumnMappingStrategy<T> columnReadingStrategy =
                 new AartColumnMappingStrategy<T>();
         columnReadingStrategy.setType(typeParameterClass);
         columnReadingStrategy.setColumnMapping(orgColMap);

         AartCsvToBean<T> aartCsvToBeanParser = new AartCsvToBean<T>(); 
         aartCsvToBeanParser.setFieldSpecificationMap(fieldSpecificationMap);

         upLoadedObjs = aartCsvToBeanParser.aartParse(columnReadingStrategy, reader);
         return upLoadedObjs;
	}
}
