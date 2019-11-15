package edu.ku.cete.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.model.security.TestingProgramDao;
import edu.ku.cete.service.TestingProgramService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestingProgramServiceImpl implements TestingProgramService {

    private static final Logger logger = LoggerFactory.getLogger(TestingProgramServiceImpl.class);
    
    @Autowired
    private TestingProgramDao testingProgramDao;
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<TestingProgram> get(Long testCollectionId) {
        logger.trace("Entering the get method");
        logger.debug("Parameter testCollectionId: {}", testCollectionId);
        
        List<TestingProgram> testingPrograms = testingProgramDao.selectByTestCollection(testCollectionId);
        
        logger.debug("Returning testingPrograms: {}", testingPrograms);
        logger.trace("Leaving the get method");
        return testingPrograms;
    }
    

    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestingProgramService#getByAssessmentProgramId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<TestingProgram> getByAssessmentProgramId(Long assessmentProgramId) { 
        logger.debug("Finding testingPrograms for assessment program id " + assessmentProgramId);

        List<TestingProgram> testingPrograms = testingProgramDao.getByAssessmentProgramId(assessmentProgramId);

		logger.debug("Returning testingPrograms for assessment program id " + assessmentProgramId);
        return testingPrograms;
    }
   
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<TestingProgram> getDynamicTestingProgramByAssessmentProgramId(Long assessmentProgramId) { 
        logger.debug("Finding testingPrograms for assessment program id " + assessmentProgramId);

        List<TestingProgram> testingPrograms = testingProgramDao.getDynamicTestingProgramByAssessmentProgramId(assessmentProgramId);

		logger.debug("Returning testingPrograms for assessment program id " + assessmentProgramId);
        return testingPrograms;
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestingProgramService#getByTestingProgramId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public TestingProgram getByTestingProgramId(Long testingProgramId){
		return testingProgramDao.selectByPrimaryKey(testingProgramId);
	}
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.TestingProgramService#selectAllTestingPrograms()
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public  List<TestingProgram> selectAllTestingPrograms(long organizationId) {
		return testingProgramDao.selectAllTestingPrograms(organizationId);
	}


	@Override
	public List<TestingProgram> selectTestingtProgramsForAutoRegistration(Long assessmentProgramId) {
		return testingProgramDao.selectTestingProgramsForAutoRegistration(assessmentProgramId);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<TestingProgram> getByAssessmentProgIdAndTestingProgAbbr(Long assessmentProgramId, String testingProgramAbbr) { 
        logger.debug("Finding testingPrograms for assessment program id :  " + assessmentProgramId + " and testing program abbreviation :" + testingProgramAbbr);
        List<TestingProgram> testingPrograms = testingProgramDao.selectByAssessmentProgIdAndTestingProgAbbr(assessmentProgramId, testingProgramAbbr);
		logger.debug("Returning testingPrograms for assessment program id :  " + assessmentProgramId + " and testing program abbreviation :" + testingProgramAbbr);
        return testingPrograms;
    }


	@Override
	public List<TestingProgram> selectAllTestingProgramsDropdown(Long currentAssessmentProgramId) {
		return testingProgramDao.selectAllTestingProgramsDropdown(currentAssessmentProgramId);

	}

	@Override
	public List<TestingProgram> getTestingProgramsForReporting(Long assessmentProgramId) {
		return testingProgramDao.getTestingProgramsForReporting(assessmentProgramId);
	}
}