/**
 * 
 */
package edu.ku.cete.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.model.QuestionInformationMapper;
import edu.ku.cete.model.StudentReportQuestionInfoMapper;
import edu.ku.cete.service.report.QuestionInformationService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 7, 2017 10:53:43 AM
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class QuestionInformationServiceImpl implements QuestionInformationService {

	@Autowired
	private QuestionInformationMapper questionInformationMapper;
	
	@Autowired
	private StudentReportQuestionInfoMapper studentReportQuestionInfoMapper;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteQuestionInformation(Long assessmentProgramId, Long subjectId, Long schoolYear,
			Long testingProgramId, String reportCycle) {
		return questionInformationMapper.deleteQuestionInformation(assessmentProgramId, subjectId, schoolYear, testingProgramId, reportCycle);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelective(QuestionInformation questionInformation) {
		return questionInformationMapper.insertSelective(questionInformation);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<QuestionInformation> getAllQuestionsInfoByTestId(Long assessmentProgramId, Long testingProgramId,
			String reportCycle, Long contentAreaId, Long gradeId, Long schoolYear, Long testId) {		
		return questionInformationMapper.getAllQuestionsInfoByTestId(assessmentProgramId, testingProgramId, reportCycle, contentAreaId, gradeId, schoolYear, testId);
	}

	@Override
	public List<StudentReportQuestionInfo> getStudentReportQuestionInfo(
			Long interimStudentReportId) {
		return studentReportQuestionInfoMapper.getStudentReportQuestionInfo(interimStudentReportId);
	}

}
