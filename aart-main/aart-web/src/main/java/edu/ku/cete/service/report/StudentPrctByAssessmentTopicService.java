package edu.ku.cete.service.report;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.ku.cete.report.domain.StudentPrctByAssessmentTopic;
import edu.ku.cete.report.domain.StudentPrctTopicReportsDTO;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
@Service
public interface StudentPrctByAssessmentTopicService {

	 Integer insertOrUpdate(
			StudentPrctByAssessmentTopic studentPrctByAssessmentTopic);

	void deleteStudentPrct(Long stateId, Long contentAreaId, Long reportYear,
			Long testingProgramId, String reportCycle);

	List<StudentPrctTopicReportsDTO> getStudentDetails(Long schoolId,
			String reportCycle, Long testingProgramId, String assessmentCode,
			Long schoolYear, Long assessmentProgramId, List<Long> topicsId);

}
