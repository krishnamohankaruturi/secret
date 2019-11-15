package edu.ku.cete.service;

import java.util.List;


import edu.ku.cete.domain.report.TestingCycle;

/**
 * Service wrapper for Testing Cycle.
 * 
 * @author Deb
 *
 */
public interface TestingCycleService {

	List<TestingCycle> getCurrentTestCycleDetails(Long assessmentProgramId, Long schoolYear, Long testingProgramId, String testingCycle);
    
    List<TestingCycle> getTestingCyclesByProgramIdSchoolYear(
			Long assessmentProgramId,
			Long schoolYear, 
			Long testingProgramId);
    
    List<TestingCycle> getTestingCyclesByProgramCodeSchoolYear(
			String assessmentProgramCode,
			Long schoolYear, 
			String testingProgramCode);
    
    List<TestingCycle> getTestingCyclesByStateIdSchoolYearAssessmentProgram(
			Long assessmentprogramID,
			Long schoolYear, 
			Long stateID);
    

	TestingCycle getTestingCycleByAssessmentProgramIDSchoolYearOTWID(Long assessmentprogramID, Long schoolYear,
			Long otwID);
    
    
}
