package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.security.TestingProgram;

public interface TestingProgramService {
    
    List<TestingProgram> get(Long testCollectionId);

    /**
     * Return the testing programs for the given assessment program.
     * If monitoring flag is set then return only the testing programs that can be monitored.
     * 
     * @param assessmentProgramId {@link Long}
     * @param monitoringOnly {@link Boolean}
     * @return List<{@link TestingProgram}>
     */
	List<TestingProgram> getByAssessmentProgramId(Long assessmentProgramId);
	
	/**
	 * @param testingProgramId
	 * @return {@link TestingProgram}
	 */
	TestingProgram getByTestingProgramId(Long testingProgramId);
	
	/**
	 * @return
	 */
	List<TestingProgram> selectAllTestingPrograms(long organizationId);

	List<TestingProgram> selectTestingtProgramsForAutoRegistration(Long assessmentProgramId);

	List<TestingProgram> getByAssessmentProgIdAndTestingProgAbbr(Long assessmentProgramId, String testingProgramAbbr);

	List<TestingProgram> getDynamicTestingProgramByAssessmentProgramId(Long assessmentProgramId);


	List<TestingProgram> selectAllTestingProgramsDropdown(Long currentAssessmentProgramId);
	
	List<TestingProgram> getTestingProgramsForReporting(Long assessmentProgramId);
	
}
