/**
 * 
 */
package edu.ku.cete.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.service.CategoryService;

/**
 * @author m802r921
 * This is the class where the test status of deployed or un deployed.
 */
@Component
public class TestStatusConfiguration {
    /**
     * publishedTestStatusCode.
     */
    @Value("${publishedTestStatusCode}")
    private String publishedTestStatusCode;
    /**
     * publishedTestStatusTypeCode.
     */
    @Value("${testStatusTypeCode}")
    private String testStatusTypeCode;
    /**
     * publishedTestStatusCategory.
     */
    private Category publishedTestStatusCategory;
    /**
     * categoryService.
     */
    @Autowired
    private CategoryService categoryService;

	/**
	 * initialization.
	 */
	@PostConstruct
	public final void initialize() {
		publishedTestStatusCategory
		= categoryService.selectByCategoryCodeAndType(publishedTestStatusCode,
				testStatusTypeCode);
	}
	/**
	 * @return the publishedTestStatusCategory {@link Category}
	 */
	public final Category getPublishedTestStatusCategory() {
		return publishedTestStatusCategory;
	}
}
