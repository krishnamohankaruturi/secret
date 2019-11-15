package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.SubjectAreaExample;
import edu.ku.cete.model.SubjectAreaDao;
import edu.ku.cete.service.SubjectAreaService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SubjectAreaServiceImpl implements SubjectAreaService {

	@Autowired
	private SubjectAreaDao subjectAreaDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SubjectArea> getSubjectAreaByTestTypeId(Long testTypeId) {
		return subjectAreaDao.selectByTestTypeId(testTypeId);
	}

	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SubjectArea> getSubjectAreasForAutoRegistration(Long testTypeId, Long assessmentId) {
		return subjectAreaDao.selectSubjectAreasForAutoRegistration(testTypeId, assessmentId);
	}	
	
	public SubjectArea getById(Long id) {
		return subjectAreaDao.selectByPrimaryKey(id);
	}
	
	@Override
	public List<SubjectArea> getByName(String name) {
		SubjectAreaExample example = new SubjectAreaExample();
		example.or().andSubjectAreaNameEqualTo(name);
		return subjectAreaDao.selectByExample(example);
	}
}
