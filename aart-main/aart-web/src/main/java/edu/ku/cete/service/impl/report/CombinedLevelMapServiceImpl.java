/**
 * 
 */
package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.model.CombinedLevelMapMapper;
import edu.ku.cete.service.report.CombinedLevelMapService;

/**
 * @author ktaduru_sta
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class CombinedLevelMapServiceImpl implements CombinedLevelMapService {

	@Autowired
	private CombinedLevelMapMapper combinedLevelMapMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertCombinedLevelMap(CombinedLevelMap combinedLevelMap) {
		return combinedLevelMapMapper.insertSelective(combinedLevelMap);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteCombinedLevelMap(Long assessmentProgramId, Long contentAreaId, Long schoolYear) {
		return combinedLevelMapMapper.deleteCombinedLevelMap(assessmentProgramId, contentAreaId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllCombinedLevelMap(Long schoolYear) {
		return combinedLevelMapMapper.deleteAllCombinedLevelMap(schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<CombinedLevelMap> getCombinedLevelBasedOnAssessmentProgramSubjectGradeYear(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear) {
		return combinedLevelMapMapper.getCombinedLevelBasedOnAssessmentProgramSubjectGradeYear(assessmentProgramId, subjectId, gradeId, schoolYear);
	}
	
}
