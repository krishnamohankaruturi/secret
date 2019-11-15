package edu.ku.cete.service.impl.report;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.report.SubscoreFramework;
import edu.ku.cete.model.SubscoreFrameworkMapper;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.report.SubscoreFrameworkService;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class SubscoreFrameworkServiceImpl implements SubscoreFrameworkService {

	
	@Autowired
	private SubscoreFrameworkMapper subscoreFrameworkMapper;
	
	@Autowired
	private TestService testService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteAllSubscoreFramework(Long schoolYear) {
		return subscoreFrameworkMapper.deleteAll(schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer deleteSubscoreFrameworks(Long assessmentProgramId, Long subjectId, Long schoolYear){
		return subscoreFrameworkMapper.deleteSubscoreFrameworks(assessmentProgramId, subjectId, schoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insertSelectiveSubscoreFramework(SubscoreFramework subscoreFramework) {
		return subscoreFrameworkMapper.insertSelective(subscoreFramework);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public SubscoreFramework selectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2(Long schoolYear, Long assessmentProgramId, Long subjectId, 
			Long gradeId, String subScoreDefinitionName, String frameworkCode, String frameworkCodeLevel1, String frameworkCodeLevel2, String frameworkCodeLevel3 ) {
		return subscoreFrameworkMapper.selectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2(schoolYear, assessmentProgramId, 
				subjectId, gradeId, subScoreDefinitionName, frameworkCode, frameworkCodeLevel1, frameworkCodeLevel2, frameworkCodeLevel3);
	}
	

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> mapSubscoreDefinitionFromTaskVariant(Long schoolYear, Long taskVariantId, Long assessmentProgramId, Long subjectId, Long gradeId) {
		return subscoreFrameworkMapper.mapSubscoreDefinitionFromTaskVariantIdUsingContentCode(schoolYear, taskVariantId, assessmentProgramId, subjectId, gradeId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> getMissingSubscoreDefinitions(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeId) {
		return subscoreFrameworkMapper.getMissingSubscoreDefinitions(schoolYear, assessmentProgramId, subjectId, gradeId);
	}
	
	
	
}
