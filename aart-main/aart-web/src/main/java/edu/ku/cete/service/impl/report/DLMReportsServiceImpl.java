package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.BluePrintEssentialElementsMapper;
import edu.ku.cete.model.StudentsResponsesDao;
import edu.ku.cete.model.TestSpecificationMapper;
import edu.ku.cete.service.report.DLMReportsService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class DLMReportsServiceImpl implements DLMReportsService {
	
	@Autowired
    private StudentsResponsesDao studentsResponsesDao;
	
	@Autowired
	private BluePrintEssentialElementsMapper bluePrintEssentialElementsMapper;
	
	@Autowired
	private TestSpecificationMapper testSpecificationMapper;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean checkIfIntegratedEEisWritingType(Long essentialElementId){
		return (bluePrintEssentialElementsMapper.checkIfIntegratedEEisWritingType(essentialElementId) > 0);
	}
	
//	@Override
//	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
//	public boolean checkIfYearEndEEisWritingType(Long essentialElementId, Long testId){
//		return (.checkIfYearEndEEisWritingType(essentialElementId, testId) > 0);
//	}
	
	public boolean checkIfYearEndEEisWritingType(String eeCode, Long testId){
		int ranking = testSpecificationMapper.checkIfYearEndEEisWritingType(eeCode, testId);
		return (ranking>0);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int countTotalNumberOfScoreableItems(Long studentId, Long testId){
		return studentsResponsesDao.countTotalNumberOfScoreableItems(studentId, testId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int countNumberOfScoreableItemsWithCorrectResponses(Long studentId, Long testId){
		return studentsResponsesDao.countNumberOfScoreableItemsWithCorrectResponses(studentId, testId);
	}

}
