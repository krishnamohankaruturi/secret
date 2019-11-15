package edu.ku.cete.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

/**
 * @author m802r921
 * For many meta data tables.
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(CategoryServiceImpl.class);

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#insert(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Category insert(String categoryName, String categoryCode,
            String categoryTypeCode) {
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setCategoryCode(categoryCode);
        category.setCategoryDescription("Created by Application");
        categoryDao.insertSelectiveByType(category, categoryTypeCode);
        return category;
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#insertIfNotPresentByName(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Category insertIfNotPresentByName(String categoryName,
            String categoryCode, String categoryTypeCode) {
        Category category = selectByCategoryNameAndType(categoryName, categoryTypeCode);
        if (category == null) {
            category = insert(categoryName, categoryCode, categoryTypeCode);
            category.setCreatedStatus();
        }
        return category;
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#insertIfNotPresentByName(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Category insertIfNotPresentByName(String categoryName,
            String categoryTypeCode) {
        Category category = null;
        if (StringUtils.hasText(categoryName)) {
            category = selectByCategoryNameAndType(categoryName, categoryTypeCode);
            if (category == null) {
                category = insert(categoryName,
                        StringUtil.replace(categoryTypeCode,
                                ParsingConstants.CODE, categoryName),
                        categoryTypeCode);
                category.setCreatedStatus();
            }
        }
        return category;
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#insertIfNotPresentByCode(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Category insertIfNotPresentByCode(String categoryName,
            String categoryCode, String categoryTypeCode) {
        if (StringUtils.hasText(categoryCode)) {
            Category category = selectByCategoryCodeAndType(categoryCode,
                    categoryTypeCode);
            if (category == null) {
                category = insert(categoryName, categoryCode, categoryTypeCode);
                category.setCreatedStatus();
            }
            return category;
        } else {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#selectByCategoryCodeAndType(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Category selectByCategoryCodeAndType(String categoryCode, String categoryTypeCode) {
        Category category = null;
        //get category type.
        CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        CategoryExample categoryExample = new CategoryExample();
        //there is only one category type for record type.
        CategoryExample.Criteria categoryCriteria = categoryExample.createCriteria();
        CategoryTypeExample.Criteria categoryTypeCriteria = categoryTypeExample.createCriteria();
        categoryTypeCriteria.andTypeCodeEqualTo(categoryTypeCode);
        categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
        categoryCriteria.andCategoryCodeEqualTo(categoryCode);
        List<Category> categories = categoryDao.selectByCategoryAndType(categoryExample);
        //unique code for a given type.
        if (CollectionUtils.isNotEmpty(categories)) {
            category = categories.get(0);
        } else {
            category = null;
        }
        return category;
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#selectByCategoryNameAndType(java.lang.String, java.lang.String)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Category selectByCategoryNameAndType(String categoryName, String categoryTypeCode) {
        Category category = null;
        //get category type.
        CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        CategoryExample categoryExample = new CategoryExample();
        //there is only one category type for record type.
        CategoryExample.Criteria categoryCriteria = categoryExample.createCriteria();
        CategoryTypeExample.Criteria categoryTypeCriteria = categoryTypeExample.createCriteria();
        categoryTypeCriteria.andTypeCodeEqualTo(categoryTypeCode);
        categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
        categoryCriteria.andCategoryNameEqualTo(categoryName);
        List<Category> categories = categoryDao.selectByCategoryAndType(categoryExample);
        //unique code for a given type.
        if (CollectionUtils.isNotEmpty(categories)) {
            category = categories.get(0);
        } else {
            category = null;
        }
        return category;
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#selectByCategoryType(java.lang.String)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Category> selectByCategoryType(String categoryTypeCode) {
        //get category type.
        CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        CategoryExample categoryExample = new CategoryExample();
        CategoryTypeExample.Criteria categoryTypeCriteria = categoryTypeExample.createCriteria();
        categoryTypeCriteria.andTypeCodeEqualTo(categoryTypeCode);
        categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
        categoryExample.setOrderByClause("id");
        return categoryDao.selectByCategoryAndType(categoryExample);
    }
    
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Category> selectByCategoryType(CategoryExample categoryExample) {
        //get category type.
        return categoryDao.selectByCategoryAndType(categoryExample);
    }
    
    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#selectByCategoryType(java.lang.String)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Map<Long,Category> selectIdMapByCategoryType(String categoryTypeCode) {
    	List<Category> categories = selectByCategoryType(categoryTypeCode);
    	Map<Long,Category> idCategoryMap = new HashMap<Long, Category>();
    	if(categories != null && CollectionUtils.isNotEmpty(categories)) {
    		for(Category category: categories) {
    			//no null check because they are not null columns.
    			idCategoryMap.put(category.getId(), category);
    		}
    	}
        return idCategoryMap;
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Map<String,Category> selectCodeMapByCategoryType(String categoryTypeCode) {
    	List<Category> categories = selectByCategoryType(categoryTypeCode);
    	Map<String,Category> codeCategoryMap = new HashMap<String, Category>();
    	if(categories != null && CollectionUtils.isNotEmpty(categories)) {
    		for(Category category: categories) {
    			//no null check because they are not null columns.
    			codeCategoryMap.put(category.getCategoryCode(), category);
    		}
    	}
        return codeCategoryMap;
    }
    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.CategoryService#selectByCategoryTypeAndCategoryCodes(java.lang.String, java.util.List)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Map<String, Category> selectByCategoryTypeAndCategoryCodes(String categoryTypeCode,
            List<String> categoryCodes) {

        Map<String, Category> parameterMap
        = new HashMap<String, Category>();
        CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        CategoryExample categoryExample = new CategoryExample();
        CategoryTypeExample.Criteria categoryTypeCriteria = categoryTypeExample.createCriteria();
        categoryTypeCriteria.andTypeCodeEqualTo(categoryTypeCode);
        categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
        categoryExample.createCriteria().andCategoryCodeIn(categoryCodes);

        List<Category> parameterCategories
        = categoryDao.selectByCategoryAndType(categoryExample);

        for (Category parameterCategory: parameterCategories) {
            parameterMap.put(parameterCategory.getCategoryCode(),
                    parameterCategory);
        }

        return parameterMap;
    }

    /**
     * @return List<Category>
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Category> selectByExample(CategoryExample example) {
        return categoryDao.selectByExample(example);
    }

    /**
     * @param categoryId long
     * @return {@link Category}
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Category selectByPrimaryKey(long categoryId) {
        return categoryDao.selectByPrimaryKey(categoryId);
    }
    
    /**
     * @param categoryCode String
     * @param status Boolean 
     * @return {@link Category}
     */
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByCategoryCode(String categoryCode, Boolean status){
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    	return categoryDao.updateByCategoryCode(categoryCode, status,new Date(),userDetails.getUserId());
    }
    
    /**
     * @param categoryCode String
     * @return {@link Category}
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Boolean getStatusITI(String categoryCode){
    	return categoryDao.getStatusITI(categoryCode);
    }

	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateByPrimaryKey(Category category) {
		categoryDao.updateByPrimaryKey(category);	
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Category> selectByCategoryType(List<String> categoryTypeCodes) {
		CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        CategoryExample categoryExample = new CategoryExample();
        CategoryTypeExample.Criteria categoryTypeCriteria = categoryTypeExample.createCriteria();
        categoryTypeCriteria.andTypeCodeIn(categoryTypeCodes);
        categoryExample.setCategoryTypeCriteria(categoryTypeExample.getOredCriteria());
        categoryExample.setOrderByClause("id");
        return categoryDao.selectByCategoryAndType(categoryExample);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Category> getCategoriesByCategoryTypeCodeAndCategoryCodes(String categoryTypeCode, List<String> categoryCodes) {
		return categoryDao.getCategoriesByCategoryTypeCodeAndCategoryCodes(categoryTypeCode, categoryCodes);
	}

	@Override
	public Long getCategoryIdByName(String interimAllowedPurpose) {
		return categoryDao.getCategoryIdByName(interimAllowedPurpose);
	}

	@Override
	public Map<Long, String> getTestSessionStatusMap() {
		
		return categoryDao.getTestSessionStatusMap();
	}
	
	@Override
	public Map<Long, String> getScoringStatusMap() {
		return categoryDao.getScoringStatusMap();
	}
}
