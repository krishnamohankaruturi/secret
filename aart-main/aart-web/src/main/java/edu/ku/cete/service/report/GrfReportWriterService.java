package edu.ku.cete.service.report;

public interface GrfReportWriterService {

	void generateScCodeExtract(Long assessmentProgramId, long contractingOrgId, int reportYear, Long userId,
			String stateCode, Long batchUploadId);

	void generateStudentExitDetailsExtract(Long assessmentProgramId, long contractingOrgId, int reportYear, Long userId,
			Long batchuploadid);
}
