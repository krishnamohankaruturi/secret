package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.TestType;

public interface TestTypeService {

	public List<TestType> getTestTypeByAssessmentId(Long assessmentId);
	
	public TestType getById(Long id);
	

	public List<TestType> getTestTypeBySubjectIdAssessmentProgram(
			String subjectCode, Long assessmentProgramId);

	public List<TestType> getCPASSTestTypesForReportsByTestTypeCode(
			Long testingProgramIdOnUI, String testType);

	
}
