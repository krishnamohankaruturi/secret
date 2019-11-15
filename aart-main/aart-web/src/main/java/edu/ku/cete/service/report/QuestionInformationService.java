/**
 * 
 */
package edu.ku.cete.service.report;

import java.util.List;

import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 7, 2017 10:48:50 AM
 */
public interface QuestionInformationService {
	Integer deleteQuestionInformation(Long assessmentProgramId, Long subjectId, Long schoolYear, Long testingProgramId, String reportCycle);
	Integer insertSelective(QuestionInformation questionInformation);
	List<QuestionInformation> getAllQuestionsInfoByTestId(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long contentAreaId,
			Long gradeId, Long schoolYear, Long testId);
	List<StudentReportQuestionInfo> getStudentReportQuestionInfo(Long interimStudentReportId);
}
