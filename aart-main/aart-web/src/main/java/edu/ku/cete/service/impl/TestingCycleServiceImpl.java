package edu.ku.cete.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.model.TestingCycleMapper;
import edu.ku.cete.service.TestingCycleService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestingCycleServiceImpl implements TestingCycleService {


    @Autowired
    private TestingCycleMapper testingCycleMapper;
	
	@Override
	public List<TestingCycle> getCurrentTestCycleDetails(Long assessmentProgramId, Long schoolYear,
			Long testingProgramId, String testingCycle) {
		
		return testingCycleMapper.getCurrentTestCycleDetails(assessmentProgramId, schoolYear, testingProgramId, testingCycle);
	}


	@Override
	public List<TestingCycle> getTestingCyclesByProgramIdSchoolYear(Long assessmentProgramId, Long schoolYear,
			Long testingProgramId) {
		
		return testingCycleMapper.getTestingCyclesByProgramIdSchoolYear(assessmentProgramId, schoolYear, schoolYear);
	}

	@Override
	public List<TestingCycle> getTestingCyclesByProgramCodeSchoolYear(String assessmentProgramCode, Long schoolYear,
			String testingProgramCode) {
		
		return testingCycleMapper.getTestingCyclesByProgramCodeSchoolYear(assessmentProgramCode, schoolYear, testingProgramCode);
	}

	@Override
	public List<TestingCycle> getTestingCyclesByStateIdSchoolYearAssessmentProgram(Long assessmentprogramID,
			Long schoolYear, Long stateID) {
		
		return testingCycleMapper.getTestingCyclesByStateIdSchoolYearAssessmentProgram(assessmentprogramID, schoolYear, stateID);
	}
	
	@Override
	public TestingCycle getTestingCycleByAssessmentProgramIDSchoolYearOTWID(Long assessmentprogramID,
			Long schoolYear, Long otwID) {
		
		return testingCycleMapper.getTestingCycleByAssessmentProgramIDSchoolYearOTWID(assessmentprogramID, schoolYear, otwID);
	}

}
