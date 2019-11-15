/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;

/**
 * @author m802r921
 *
 */
public interface CategoryService {
	/**
	 * @param categoryName {@link String}
	 * @param categoryCode {@link String}
	 * @param categoryTypeCode {@link String}
	 * @return {@link Category}
	 */
	Category insertIfNotPresentByName(String categoryName,
			String categoryCode,
			String categoryTypeCode);
	/**
	 * @param categoryName {@link String}
	 * @param categoryTypeCode {@link String}
	 * @return {@link Category}
	 */
	Category insertIfNotPresentByName(String categoryName,
			String categoryTypeCode);
	/**
	 * @param categoryName {@link String}
	 * @param categoryCode {@link String}
	 * @param categoryTypeCode {@link String}
	 * @return {@link Category}
	 */
	Category insertIfNotPresentByCode(
			String categoryName, String categoryCode, String categoryTypeCode);
	/**
	 * @param categoryCode {@link String}
	 * @param categoryTypeCode {@link String}
	 * @return {@link List}
	 */
	Category selectByCategoryCodeAndType(String categoryCode,
			String categoryTypeCode);
	/**
	 * @param categoryName {@link String}
	 * @param categoryTypeCode {@link String}
	 * @return {@link List}
	 */
	Category selectByCategoryNameAndType(String categoryName,
			String categoryTypeCode);
	/**
	 * @param categoryTypeCode {@link String}
	 * @return {@link List}
	 */
	List<Category> selectByCategoryType(String categoryTypeCode);
	
	/**
	 * @param categoryTypeCode {@link String}
	 * @return {@link List}
	 */
	List<Category> selectByCategoryType(CategoryExample categoryExample);
	
	
	/**
	 * @param categoryName {@link String}
	 * @param categoryCode {@link String}
	 * @param categoryTypeCode {@link String}
	 * @return {@link Category}
	 */
	Category insert(String categoryName, String categoryCode,
			String categoryTypeCode);

	/**
	 *
	 *@param example {@link CategoryExample}
	 *@return {@link Category}
	 */
	List<Category> selectByExample(CategoryExample example);
	
	/**
	 * @param categoryTypeCode {@link String}
	 * @param categoryCodes {@link List}
	 * @return {@link Map}
	 */
	Map<String, Category> selectByCategoryTypeAndCategoryCodes(String categoryTypeCode,
			List<String> categoryCodes);

	/**
	 *
	 *@param categoryId long
	 *@return {@link Category}
	 */
	Category selectByPrimaryKey(long categoryId);
	
	int updateByCategoryCode(String categoryCode,
			Boolean categoryTypeCode);
	
	Boolean getStatusITI(String categoryCode);
	/**
	 * @param categoryTypeCode
	 * @return
	 */
	Map<Long, Category> selectIdMapByCategoryType(String categoryTypeCode);
	/**
	 * 
	 * @param category
	 */
	void updateByPrimaryKey(Category category);

	/**
	 * @param categoryTypeCodes {@link List}
	 * @return {@link List}
	 */
	List<Category> selectByCategoryType(List<String> categoryTypeCodes);
	
	Map<String, Category> selectCodeMapByCategoryType(String categoryTypeCode);
	Long getCategoryIdByName(String interimAllowedPurpose);
	Map<Long, String> getTestSessionStatusMap();
	Map<Long, String> getScoringStatusMap();
	List<Category> getCategoriesByCategoryTypeCodeAndCategoryCodes(String categoryTypeCode, List<String> categoryCodes);

}
