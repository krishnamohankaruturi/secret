/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.SpecialCircumstance;
import edu.ku.cete.domain.StudentSpecialCircumstance;
import edu.ku.cete.domain.StudentSpecialCircumstanceExample;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.SpecialCircumstanceMapper;
import edu.ku.cete.model.StudentSpecialCircumstanceDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentSpecialCircumstanceService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.web.StudentSpecialCircumstanceDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class StudentSpecialCircumstanceServiceImpl implements StudentSpecialCircumstanceService {
    
	@Autowired
    private SpecialCircumstanceMapper specialCircumstanceMapper;
	
	@Autowired
    private StudentSpecialCircumstanceDao studentSpecialCircumstanceDao;    
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private CategoryService categoryService;

    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int countByExample(StudentSpecialCircumstanceExample example){
    	return studentSpecialCircumstanceDao.countByExample(example);
    }

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int deleteByExample(StudentSpecialCircumstanceExample example) {
		Long currentContextUserId = null;
		if (currentContextUserId == null
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailImpl) {
			currentContextUserId = ((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal()).getUserId();
		}
		Date now = new Date();
		return studentSpecialCircumstanceDao.deleteByExample(example, now, currentContextUserId);
	}

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int insert(StudentSpecialCircumstance record){
    	record.setAuditColumnProperties();
    	return studentSpecialCircumstanceDao.insert(record);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int insertSelective(StudentSpecialCircumstance record){
    	record.setAuditColumnProperties();
    	return studentSpecialCircumstanceDao.insertSelective(record);
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<StudentSpecialCircumstance> selectByExample(StudentSpecialCircumstanceExample example){
    	return studentSpecialCircumstanceDao.selectByExample(example);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExampleSelective(@Param("record") StudentSpecialCircumstance record){
    	record.setAuditColumnPropertiesForUpdate();
    	return studentSpecialCircumstanceDao.updateByExampleSelective(record);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final int updateByExample(@Param("record") StudentSpecialCircumstance record, 
    		@Param("example") StudentSpecialCircumstanceExample example){
    	record.setAuditColumnPropertiesForUpdate();
    	return studentSpecialCircumstanceDao.updateByExampleSelective(record);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<SpecialCircumstance> getCEDSBasedOnAssessmentProgram(String assessmentProgramName){
    	return specialCircumstanceMapper.getCEDSList(assessmentProgramName);
    }   
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<SpecialCircumstance> getAllSpecialCircumstances(){
    	return specialCircumstanceMapper.selectAll();
    }

	@Override
	public List<SpecialCircumstance> getCEDSByUserState(long contractingOrgId) {
		return specialCircumstanceMapper.getCEDSByUserState(contractingOrgId);
	}
	
	@Override
	public Long getCountOfRestrictedCodesByState(long contractingOrgId) {
		return specialCircumstanceMapper.getCountOfRestrictedCodesByState(contractingOrgId);
	}

	@Override
	public List<StudentSpecialCircumstanceDTO> getStudentSpecialCircumstanceInfo(Long orgChildrenById, Integer currentSchoolYear, List<Long> assessmentPrograms) {
		return studentSpecialCircumstanceDao.getStudentSpecialCircumstanceInfo(orgChildrenById, currentSchoolYear, assessmentPrograms);
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void saveSpecialCircumstance(Long testSessionId, List<Map<String, Object>> specialCircumstances,
			Boolean highStakesTest, Long userId) {
		if(specialCircumstances != null && highStakesTest){
			Category sscPendingStatus = categoryService.selectByCategoryCodeAndType("PENDING", "SPECIAL CIRCUMSTANCE STATUS");
			Category sscSavedStatus = categoryService.selectByCategoryCodeAndType("SAVED", "SPECIAL CIRCUMSTANCE STATUS");
			
			for (Map<String, Object> specialCircumstance : specialCircumstances) {
				StudentSpecialCircumstance record = new StudentSpecialCircumstance();
				record.setActiveFlag(true);
				Long studentId = Long.valueOf(specialCircumstance.get("studentId").toString());
				String studentSpecialCircumstanceValue = "";
				if(specialCircumstance.get("specialCircumstanceValue") != null){
					studentSpecialCircumstanceValue = specialCircumstance.get("specialCircumstanceValue").toString();
					if(StringUtils.isNotBlank(studentSpecialCircumstanceValue)){
						record.setSpecialCircumstanceId(Long.valueOf(studentSpecialCircumstanceValue));
					}
					else{
						record.setActiveFlag(false);
					}
				}
				
				
				String requireConfirmValue = specialCircumstance.get("requireConfirmation").toString();
				List<StudentsTests> studentTests = studentsTestsService.findByTestSessionAndStudent(testSessionId, studentId);
				Long studentTestId = null;
				List<StudentSpecialCircumstance> existingrecord = null;
				StudentSpecialCircumstanceExample stu = new StudentSpecialCircumstanceExample();
				if(studentTests.size() > 0){
					studentTestId = studentTests.get(0).getId();
					stu.createCriteria().andStudentTestidEqualTo(studentTestId);
					stu.createCriteria().andSpecialCircumstanceActiveFlagEqualTo(true);
					existingrecord = selectActiveByStudentTestId(studentTestId, true);
				}
				
				record.setStudentTestid(studentTestId);
				
				String specialCircumstanceApprovalValue = "";
				if(specialCircumstance.get("specialCircumstanceApprovalValue") != null){
					specialCircumstanceApprovalValue = specialCircumstance.get("specialCircumstanceApprovalValue").toString();
				}
				String isApprovalAction = "";
				if(specialCircumstance.get("isApprovalAction") != null){
					isApprovalAction = specialCircumstance.get("isApprovalAction").toString();
				}
				
				boolean requireConfirmation = StringUtils.isNotBlank(requireConfirmValue)? Boolean.valueOf(requireConfirmValue) : false;
				if(isApprovalAction.equals("true")){
					Long value = StringUtils.isBlank(specialCircumstanceApprovalValue) ? null : Long.valueOf(specialCircumstanceApprovalValue);
					if(value == null){
						if(requireConfirmation){
							record.setStatus(sscPendingStatus.getId());
						} else {
							record.setStatus(sscSavedStatus.getId());
						}
					}else{
						record.setStatus(value);
					}
						
					record.setApprovedBy(userId);
				} else {
					if(requireConfirmation){
						record.setStatus(sscPendingStatus.getId());
					} else {
						record.setStatus(sscSavedStatus.getId());
					}
				}
				
				if(existingrecord != null && existingrecord.size() >0){
					if(record.getActiveFlag().booleanValue()){
						updateByExample(record, stu);
					} else {
						//deleteByExample(stu);
					 deleteByStudentTestId(studentTestId);
					}
					
				}else{
					// DE12792
					if(StringUtils.isNotBlank(specialCircumstanceApprovalValue)){
						insert(record);
					} else {
						if(record.getSpecialCircumstanceId() != null ||record.getSpecialCircumstanceId() == null){
							insert(record);
						} 
					}
				}
			}
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteApproval(Long studentTestId, Long status) {
		Long currentContextUserId = null;
		if (currentContextUserId == null
				&& SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailImpl) {
			currentContextUserId = ((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal()).getUserId();
		}
		Date now = new Date();
		studentSpecialCircumstanceDao.deleteApproval(studentTestId, status, now, currentContextUserId);
	}

	@Override
	public List<StudentSpecialCircumstance> selectActiveByStudentTestId(Long studentTestId, Boolean activeFlag) {
		return studentSpecialCircumstanceDao.selectActiveByStudentTestId(studentTestId, activeFlag);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteByStudentTestId(Long studentTestId) {
		StudentSpecialCircumstance record = new StudentSpecialCircumstance();
		record.setStudentTestid(studentTestId);
		record.setAuditColumnPropertiesForDelete();
		studentSpecialCircumstanceDao.deleteByStudentTestId(record);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SpecialCircumstance> getSCEntriesByStateAndAssessmentProgram(Long stateId, Long assessmentProgramId) {
		return specialCircumstanceMapper.getSCEntriesByStateAndAssessmentProgram(stateId, assessmentProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<SpecialCircumstance> getAppliedSCCodesByStudentsTestsId(Long studentsTestsId) {
		return specialCircumstanceMapper.getAppliedSCCodesByStudentsTestsId(studentsTestsId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StudentSpecialCircumstance getById(Long id) {
		return studentSpecialCircumstanceDao.selectById(id);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateStatusAndApprovedBy(StudentSpecialCircumstance record) {
		return studentSpecialCircumstanceDao.updateStatusAndApprovedBy(record);
	}
}
