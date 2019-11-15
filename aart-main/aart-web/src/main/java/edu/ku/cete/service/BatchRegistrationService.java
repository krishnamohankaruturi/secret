package edu.ku.cete.service;

import java.util.Date;
import java.util.List;

import edu.ku.cete.domain.BatchJobSchedule;
import edu.ku.cete.domain.BatchRegistrationDTO;
import edu.ku.cete.domain.TestEnrollmentMethod;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.report.domain.BatchRegisteredScoringAssignments;
import edu.ku.cete.report.domain.BatchRegisteredTestSessions;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.report.domain.QuestarRegistrationReason;

public interface BatchRegistrationService {

	long insertBatchRegistration(BatchRegistration batchRegistrationRecord);
	
	long insertSelectiveBatchRegistration(BatchRegistration batchRegistrationRecord);

	long updateBatchRegistrationSelective(BatchRegistration batchRegistrationRecord);

	long insertBatchRegistrationReason(BatchRegistrationReason batchRegistrationReasonRecord);
	
	long insertBatchRegisteredTestSessions(BatchRegisteredTestSessions batchRegisteredTestSessions);
	
	List<BatchRegistrationDTO> getBatchRegisteredHistory(Date fromDate, Date toDate);

	void insertReasons(List<BatchRegistrationReason> reasons);

	void insertQuestarReasons(List<QuestarRegistrationReason> reasons);
	
	List<BatchRegistrationReason> getByBatchRegistrationId(Long batchId);
	
	List<BatchJobSchedule> getBatchJobSchedules(String server);
	
	List<OperationalTestWindow> getTestWindowsForBatchRegistration(Long assessmentProgramId, Long autoEnrollmentMethodId);
	
	List<OperationalTestWindow> getEffectiveTestWindowsForBatchRegistration(Long assessmentProgramId, Long autoEnrollmentMethodId);
	
	List<TestEnrollmentMethod> getTestEnrollmentMethods(Long assessmentProgramId);

	TestEnrollmentMethod getTestEnrollmentMethod(Long id);

	List<OperationalTestWindow> getEffectiveStateWindows(
			Long assessmentProgramId, Long autoEnrollmentMethodId);

	TestEnrollmentMethod getTestEnrollmentMethodByCode(Long assessmentProgramId, String enrollmentMethod);

	BatchRegistration getBatchRegistrationById(Long id);
	
	Date getLatestSubmissionDateWithEnrollmentMethod(Long testEnrollmentMethodId);
	
	Date getLatestSubmissionDateWithBatchTypeCode(String batchTypeCode);
	
	long insertBatchRegisteredScoringAssignments(List<BatchRegisteredScoringAssignments> batchRegisteredTestSessions);
}
