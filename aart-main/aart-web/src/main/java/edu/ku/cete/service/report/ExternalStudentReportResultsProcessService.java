package edu.ku.cete.service.report;

import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.ExternalStudentReportResults;

@Service
public interface ExternalStudentReportResultsProcessService {

	public	Integer insertOrUpdateExternalStudentReportResults(
			ExternalStudentReportResults externalStudentReportResults);
    public Integer deleteExternalStudentReportResults(Long stateId, Long assessmentProgramId, Long subjectId, Long schoolYear, Long testingProgramId, String reportCycle);

}
