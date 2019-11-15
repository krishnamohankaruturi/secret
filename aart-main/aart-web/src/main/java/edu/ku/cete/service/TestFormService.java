/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.content.TestForm;

/**
 * @author neil.howerton
 *
 */
public interface TestFormService {

    /**
     *
     *@param testId long
     *@return List<TestForm>
     */
    List<TestForm> findByTest(long testId);

    /**
     *
     *@return List<TestForm>
     */
    List<TestForm> getAll();
}
