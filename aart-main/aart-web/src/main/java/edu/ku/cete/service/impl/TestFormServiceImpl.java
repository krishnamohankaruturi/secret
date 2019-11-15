/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.content.TestForm;
import edu.ku.cete.model.TestFormDao;
import edu.ku.cete.service.TestFormService;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestFormServiceImpl implements TestFormService {

    private Logger logger = LoggerFactory.getLogger(TestFormServiceImpl.class);
    @Autowired
    private TestFormDao testFormDao;

    /**
     * @param testId long
     * @return List<TestForm>
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestForm> findByTest(final long testId) {
        logger.trace("Entering the findByTest() method.");
        logger.trace("Leaving the findByTest() method.");

        return testFormDao.findByTest(testId);
    }

    /**
     * @return List<TestForm>
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestForm> getAll() {
        return testFormDao.getAll();
    }

}
