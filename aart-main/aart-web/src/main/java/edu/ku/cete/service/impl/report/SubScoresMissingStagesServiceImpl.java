package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.SubScoresMissingStages;
import edu.ku.cete.model.SubScoresMissingStagesMapper;
import edu.ku.cete.service.report.SubScoresMissingStagesService;

@Service
public class SubScoresMissingStagesServiceImpl implements SubScoresMissingStagesService{

	@Autowired
	private SubScoresMissingStagesMapper subScoresMissingStagesMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteSubScoreDefaultStages(Long assessmentProgramId,
			Long contentAreaId, Long schoolYear) {
		return subScoresMissingStagesMapper.deleteSubScoresMissingStages(assessmentProgramId, contentAreaId,
				schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveSubScoreMissingStages(SubScoresMissingStages subScoreMissingStages) {		
		return subScoresMissingStagesMapper.insertSelective(subScoreMissingStages);
	}

}
