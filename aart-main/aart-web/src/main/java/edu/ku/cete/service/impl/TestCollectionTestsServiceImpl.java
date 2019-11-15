/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.content.TestCollectionTests;
import edu.ku.cete.domain.content.TestCollectionTestsExample;
import edu.ku.cete.model.TestCollectionTestsDao;
import edu.ku.cete.service.TestCollectionTestsService;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestCollectionTestsServiceImpl implements TestCollectionTestsService {

    @Autowired
    private TestCollectionTestsDao testCollectionTestsDao;

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#countByExample(edu.ku.cete.domain.TestCollectionTestsExample)
     */
    @Override
    public final int countByExample(TestCollectionTestsExample example) {
        return testCollectionTestsDao.countByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#deleteByExample(edu.ku.cete.domain.TestCollectionTestsExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int deleteByExample(TestCollectionTestsExample example) {
        return testCollectionTestsDao.deleteByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#deleteByPrimaryKey(edu.ku.cete.domain.TestCollectionTests)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int deleteByPrimaryKey(TestCollectionTests key) {
        return testCollectionTestsDao.deleteByPrimaryKey(key);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#insert(edu.ku.cete.domain.TestCollectionTests)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestCollectionTests insert(TestCollectionTests record) {
        testCollectionTestsDao.insert(record);
        return record;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#insertSelective(edu.ku.cete.domain.TestCollectionTests)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestCollectionTests insertSelective(TestCollectionTests record) {
        testCollectionTestsDao.insertSelective(record);
        return record;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#selectByExample(edu.ku.cete.domain.TestCollectionTestsExample)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<TestCollectionTests> selectByExample(TestCollectionTestsExample example) {
        return testCollectionTestsDao.selectByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#updateByExampleSelective(edu.ku.cete.domain.TestCollectionTests, edu.ku.cete.domain.TestCollectionTestsExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExampleSelective(TestCollectionTests record, TestCollectionTestsExample example) {
        return testCollectionTestsDao.updateByExampleSelective(record, example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestCollectionTestsService#updateByExample(edu.ku.cete.domain.TestCollectionTests, edu.ku.cete.domain.TestCollectionTestsExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExample(TestCollectionTests record, TestCollectionTestsExample example) {
        return testCollectionTestsDao.updateByExample(record, example);
    }

}
