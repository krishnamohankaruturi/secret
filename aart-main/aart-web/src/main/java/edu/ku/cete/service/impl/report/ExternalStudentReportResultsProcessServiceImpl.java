package edu.ku.cete.service.impl.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.model.ExternalstudentreportsMapper;
import edu.ku.cete.report.domain.ExternalStudentReportResults;
import edu.ku.cete.service.report.ExternalStudentReportResultsProcessService;
@Service
public class ExternalStudentReportResultsProcessServiceImpl implements
		ExternalStudentReportResultsProcessService {

	@Autowired
	ExternalstudentreportsMapper externalstudentreportsMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertOrUpdateExternalStudentReportResults(
			ExternalStudentReportResults externalStudentReportResults) {
		Integer results = null;
		  results = externalstudentreportsMapper.updateResults(externalStudentReportResults);
		if(results == 0)
          results = externalstudentreportsMapper.insertResults(externalStudentReportResults);
		return results;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteExternalStudentReportResults(Long stateId, Long assessmentProgramId,
			Long subjectId, Long schoolYear, Long testingProgramId,
			String reportCycle) {
		// TODO Auto-generated method stub
					
		return externalstudentreportsMapper.deleteExternalStudentReportResults(stateId, assessmentProgramId,subjectId,schoolYear,testingProgramId,reportCycle);
	}

}
