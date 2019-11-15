package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.SubscoresDescription;
import edu.ku.cete.model.SubscoresDescriptionMapper;
import edu.ku.cete.service.report.SubscoresDescriptionService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SubscoresDescriptionServiceImpl implements SubscoresDescriptionService {

	@Autowired
	private SubscoresDescriptionMapper subscoresDescriptionMapper;

		@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllSubscoresDescription(Long schoolYear) {
		return subscoresDescriptionMapper.deleteAll(schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteSubscoresDescriptions(Long assessmentProgramId, Long subjectId, Long schoolYear){
		return subscoresDescriptionMapper.deleteSubscoresDescriptions(assessmentProgramId, subjectId, schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveSubscoresDescription(
			SubscoresDescription subscoresDescription) {
		return subscoresDescriptionMapper.insertSelective(subscoresDescription);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean checkForDuplicateSequenceForReport(Long schoolYear, Long assessmentProgramId, Long subject, String report, String subScoreDefinitionName, Integer subScoreDisplaySequence)
	{
		return (subscoresDescriptionMapper.checkForDuplicateSequenceForReport(schoolYear, assessmentProgramId, subject, subScoreDefinitionName, report, subScoreDisplaySequence) > 0);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean checkForDuplicateDefinitionForReport(Long schoolYear, Long assessmentProgramId, Long subject, String report, String subScoreDefinitionNamee)
	{
		return (subscoresDescriptionMapper.checkForDuplicateDefinitionForReport(schoolYear, assessmentProgramId, subject, subScoreDefinitionNamee, report) > 0);
	}
	
	@Override
	public boolean checkIfSubscoreDefinitionNameExists(Long schoolYear, Long assessmentProgramId, Long subject, String subScoreDefinitionName){
		return (subscoresDescriptionMapper.checkIfSubscoreDefinitionNameExists(schoolYear, assessmentProgramId, subject, subScoreDefinitionName) > 0);
	}
	
}
