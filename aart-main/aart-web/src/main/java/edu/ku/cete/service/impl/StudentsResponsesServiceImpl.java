/**
 * 
 */
package edu.ku.cete.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.StudentResponseScore;
import edu.ku.cete.domain.StudentResponseScoreKey;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.StudentsResponsesExample;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.model.StudentResponseScoreMapper;
import edu.ku.cete.model.StudentsResponsesDao;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.tde.webservice.client.TDEWebClient;
import edu.ku.cete.web.QuestarDTO;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class StudentsResponsesServiceImpl implements StudentsResponsesService {
    /**
     * Data Access Object for StudentsResponses table.
     */
    @Autowired
    private StudentsResponsesDao studentsResponsesDao;

    @Autowired
    private StudentsTestsDao studentsTestsDao;
    
    @Autowired
    private StudentResponseScoreMapper studentResponseScoreDao;
    
    /**
     * Web client to connect to TDE web services.
     */
    @Autowired
    private TDEWebClient webClient;

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#countByExample(edu.ku.cete.domain.StudentsResponsesExample)
     */
    @Override
    public final int countByExample(StudentsResponsesExample example) {
        return studentsResponsesDao.countByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#insert(edu.ku.cete.domain.StudentsResponses)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentsResponses insert(StudentsResponses record) {
        studentsResponsesDao.insert(record);
        return record;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#insertSelective(edu.ku.cete.domain.StudentsResponses)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentsResponses insertSelective(StudentsResponses record) {
        studentsResponsesDao.insertSelective(record);
        return record;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#selectByExample(edu.ku.cete.domain.StudentsResponsesExample)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<StudentsResponses> selectByExample(StudentsResponsesExample example) {
        return studentsResponsesDao.selectByExample(example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#selectByPrimaryKey(java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentsResponses selectByPrimaryKey(Long id) {
        return studentsResponsesDao.selectByPrimaryKey(id);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#updateByExampleSelective(edu.ku.cete.domain.StudentsResponses,
     *  edu.ku.cete.domain.StudentsResponsesExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExampleSelective(@Param("record") StudentsResponses record,
            @Param("example") StudentsResponsesExample example) {
        return studentsResponsesDao.updateByExampleSelective(record, example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#updateByExample(edu.ku.cete.domain.StudentsResponses,
     *  edu.ku.cete.domain.StudentsResponsesExample)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExample(@Param("record") StudentsResponses record, @Param("example") StudentsResponsesExample example) {
        return studentsResponsesDao.updateByExample(record, example);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#updateByPrimaryKeySelective(edu.ku.cete.domain.StudentsResponses)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByPrimaryKeySelective(StudentsResponses record) {
        return studentsResponsesDao.updateByPrimaryKeySelective(record);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#updateByPrimaryKey(edu.ku.cete.domain.StudentsResponses)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByPrimaryKey(StudentsResponses record) {
        return studentsResponsesDao.updateByPrimaryKey(record);
    }

    /*
     * (non-Javadoc)
     * @see edu.ku.cete.service.StudentsResponsesService#retrieveStudentResponses(long)
     */
    @Override
    public final boolean retrieveStudentsResponses(long testSessionId) {
        
    	boolean successful = true;
        int updated = 0;       

    	List<StudentsTests> studentsTests = studentsTestsDao.findByTestSession(testSessionId);
    	
    	if(studentsTests != null &&
    			CollectionUtils.isNotEmpty(studentsTests)) {
	    	List<StudentsResponses> studentsResponses = webClient.retrieveResponsesByStudentsTestsIds(studentsTests);
		        
	        for (StudentsResponses studentResponse : studentsResponses) {
	        	
	            studentResponse.setAuditColumnPropertiesForUpdate();
	            updated = studentsResponsesDao.updateByFoilIdAndStudentsTestsId(studentResponse);
	                
	            if (updated < 1) {
	            	// if the record doesn't exist already, insert it.
	                studentResponse.setAuditColumnProperties();
	                studentsResponsesDao.insert(studentResponse);
	            }
	        }
    	}
        
        return successful;
    }

    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final StudentsResponses getStudentResponse(@Param("studentId") Long studentId, 
    		@Param("taskVariantId") Long taskVariantId, @Param("testId") Long testId) {
    	return studentsResponsesDao.getStudentResponse(studentId, taskVariantId, testId);
    }
    
    
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<QuestarDTO> getResponsesForQuestar(Map<String, Object> criteria) {
    	return studentsResponsesDao.getQuestarInfo(criteria);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateQuestarRequestId(Long questarRequestId, List<StudentsResponses> responses) {
		int count = 0;
		for (int x = 0; x < responses.size(); x++) {
			Long studentsTestSectionsId = responses.get(x).getStudentsTestSectionsId();
			Long taskVariantId = responses.get(x).getTaskVariantId();
			count += studentsResponsesDao.updateQuestarRequestId(questarRequestId, studentsTestSectionsId, taskVariantId);
		}
		return count;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StudentsResponses selectQuestarResponse(Long studentId, Long studentsTestsId,
			Long studentsTestSectionsId, Long taskVariantId) {
		return studentsResponsesDao.selectQuestarResponse(studentId, studentsTestsId, studentsTestSectionsId, taskVariantId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentsResponses> findQuestarResponseByStudentTestSectionId(@Param("studentsTestSectionsId") Long studentsTestSectionsId) {
		return studentsResponsesDao.findQuestarResponseByStudentTestSectionId(studentsTestSectionsId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public HashMap<Long, List<StudentsResponses>> findQuestarResponseMapByStudentTestId(@Param("studentsTestsId") Long studentsTestsId) {
		return studentsResponsesDao.findQuestarResponseMapByStudentTestId(studentsTestsId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateScoreForQuestar(Long userId, Long studentsTestSectionsId,
			Long taskVariantId, BigDecimal score) {
		return studentsResponsesDao.updateScoreForQuestar(userId, studentsTestSectionsId, taskVariantId, score);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StudentResponseScore addOrUpdateStudentResponseScore(StudentResponseScore record) {
		StudentResponseScore srs = studentResponseScoreDao.selectByPrimaryKeyDimensionCaseInsensitive(record);
		if (srs != null) {
			record.setCreateDate(srs.getCreateDate());
			studentResponseScoreDao.updateByPrimaryKeyDimensionCaseInsensitive(record);
		} else {
			studentResponseScoreDao.insert(record);
		}
		
		return record;
	}

	@Override
	public StudentsResponses insertStudentResponse(StudentsResponses sr) {
		studentsResponsesDao.insertStudentResponse(sr);
		return sr;
	}

	@Override
	public List<StudentResponseScore> findStudentResponseScores(
			Long studentsTestSectionsId, Long taskVariantExternalId, List<Integer> raterOrders) {
		return studentResponseScoreDao.selectByStudentsTestSectionsIdTaskVariantExternalIdRaterOrders(
				studentsTestSectionsId, taskVariantExternalId, raterOrders);
	}
	@Override
	public int getNoOfNotResponsesMachineScoreItems(Long studentsTestId){
		return studentsResponsesDao.getNoOfNotResponsesMachineScoreItems(studentsTestId);
	}
}