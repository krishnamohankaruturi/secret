package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.model.ContentAreaDao;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.TestTypeDao;
import edu.ku.cete.service.TestTypeService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestTypeServiceImpl implements TestTypeService{

	@Autowired
	private TestTypeDao dao;
	
	@Autowired
	private EnrollmentTestTypeSubjectAreaDao enrollmentTestTypeSubjectAreaDao;
	
	@Autowired
	private ContentAreaDao contentAreaDao;

	
	@Override
	public List<TestType> getTestTypeByAssessmentId(Long assessmentId) {
//		TestTypeExample example = new TestTypeExample();
//		example.createCriteria().andAssessmentIdEqualTo(assessmentId);
		return dao.selectByAssessmentId(assessmentId);
	}

	public TestType getById(Long id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public List<TestType> getTestTypeBySubjectIdAssessmentProgram(
			String subjectCode, Long assessmentProgramId) {
		// TODO Auto-generated method stub
		return enrollmentTestTypeSubjectAreaDao.getTestTypeBySubjectAssesment(subjectCode,assessmentProgramId);
	}

	@Override
	public List<TestType> getCPASSTestTypesForReportsByTestTypeCode(
			Long testingProgramId, String testType) {
		return dao.getCPASSTestTypesForReportsByTestTypeCode(testingProgramId, testType);
	}
}
