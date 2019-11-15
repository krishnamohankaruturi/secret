package edu.ku.cete.service.impl.report;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.model.ExcludedItemsMapper;
import edu.ku.cete.service.report.ExcludedItemsService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ExcludedItemsServiceImpl implements ExcludedItemsService {

	@Autowired
	private ExcludedItemsMapper excludedItemsMapper;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllExcludedItems(Long schoolYear) {
		return excludedItemsMapper.deleteAll(schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteExcludedItems(Long assessmentProgramId, Long subjectId, Long schoolYear){
		return excludedItemsMapper.deleteExcludedItems(assessmentProgramId, subjectId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveExcludedItems(ExcludedItems excludedItemRecord) {
		return excludedItemsMapper.insertSelective(excludedItemRecord);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ExcludedItems> getByTaskVariantIds(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, Set<Long> taskVariantList, Long testingProgramId, String reportCycle) {
		return excludedItemsMapper.selectByTaskVariantIds(assessmentProgramId, subjectId, gradeId, schoolYear, taskVariantList, testingProgramId, reportCycle);
	}

	@Override
	public List<ExcludedItems> getByAssessmentProgramContentAreaGrade(
			Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Long currentSchoolYear) {
		return excludedItemsMapper.selectByAssessmentProgramContentAreaGrade(assessmentProgramId, contentAreaId, gradeId, currentSchoolYear);
	}
}
