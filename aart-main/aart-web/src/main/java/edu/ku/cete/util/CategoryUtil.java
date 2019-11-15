package edu.ku.cete.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.domain.validation.FieldSpecificationExample;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.model.validation.FieldSpecificationDao;

@Component
public class CategoryUtil {

	/**
	 * 
	 */
	private static final Log LOGGER = LogFactory.getLog(PermissionUtil.class);	

	/**
     * for getting the code for record type.
     */
    @Autowired
	private CategoryTypeDao categoryTypeDao;
    
    /**
     * for getting the categories.
     */
    @Autowired
    private CategoryDao categoryDao;
    
    /**
     * meta data for record types.
     */
    private Map<Long, Category> recordTypeIdMap = new HashMap<Long, Category>();
    
    /**
     * Field specification dao.
     */
    @Autowired
    private FieldSpecificationDao fieldSpecificationDao;
    
	/**
	 * @param uploadSpecification
	 * @param recordType
	 * @return
	 */
	public Category getCSVUploadRecordTypes(UploadSpecification uploadSpecification, String recordType) {
		//get category type.
        CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        categoryTypeExample.createCriteria().andTypeCodeEqualTo(uploadSpecification.getCsvRecordTypeCode());
        //This should not be empty.Failure to deploy is the expected behavior.
        List<CategoryType> categoryTypes = categoryTypeDao.selectByExample(categoryTypeExample);
        if (CollectionUtils.isEmpty(categoryTypes)) {
            LOGGER.debug("No category Types");
            return null;
        }
        CategoryExample categoryExample = new CategoryExample();
        //there is only one category type for record type.
        categoryExample.createCriteria().andCategoryTypeIdEqualTo(categoryTypes.get(0).getId());
        recordTypeIdMap = new HashMap<Long, Category>();
        List<Category> recordTypes = categoryDao.selectByExample(categoryExample);
        if (CollectionUtils.isEmpty(recordTypes)) {
            LOGGER.debug("No categories of record type");
            return null;
        }
        for (Category category:recordTypes) {
            recordTypeIdMap.put(category.getId(), category);
        }    
        
        for (Category category:recordTypeIdMap.values()) {
            if (category.getCategoryCode().equalsIgnoreCase(recordType)) {
                return category;
            }
        }
        
        return null;
	}
	
	
	/**
	 * set fields specification.
	 */
	public Map<String, FieldSpecification> getFieldSpecificationMap(
			Category recordType, Category personalNeedsProfileRecordRecordType) {
	    Map<String, FieldSpecification> fieldSpecificationMap = new HashMap<String, FieldSpecification>();
	    FieldSpecificationExample fieldSpecificationExample = new FieldSpecificationExample();
	    if (recordType == null) {
	        return fieldSpecificationMap;
	    }
	    fieldSpecificationExample.createCriteria().andRecordTypeIdEqualTo(recordType.getId());
	    List<FieldSpecification> fieldSpecifications = fieldSpecificationDao.selectByExample(fieldSpecificationExample);
	    if (CollectionUtils.isNotEmpty(fieldSpecifications)) {
	        for (FieldSpecification fieldSpecification : fieldSpecifications) {
	            //LOGGER.debug("FieldSpecification.toString() :" + fieldSpecification.toString());
	        	//For PNP, need to support all cases, so convert fieldnames to lowercase.
	        	if(personalNeedsProfileRecordRecordType != null && 
	        			recordType.getCategoryCode().equalsIgnoreCase(personalNeedsProfileRecordRecordType.getCategoryCode())) {
	        		fieldSpecificationMap.put(fieldSpecification.getFieldName().toLowerCase(), fieldSpecification);
	        	} else {
	        		fieldSpecificationMap.put(fieldSpecification.getFieldName(), fieldSpecification);
	        	}
	        }
	    }
	    LOGGER.debug(fieldSpecificationMap.toString());
	    return fieldSpecificationMap;
	}
	
    
}
