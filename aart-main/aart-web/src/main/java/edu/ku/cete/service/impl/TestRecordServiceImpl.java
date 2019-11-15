package edu.ku.cete.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.controller.TestController;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectArea;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectAreaExample;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.service.TestRecordService;
import edu.ku.cete.web.ViewStudentDTO;

/**
 * @author nicholas.studt
 */
@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TestRecordServiceImpl implements TestRecordService{
	
	 private Logger logger = LoggerFactory.getLogger(TestRecordService.class);
	
	@Autowired
    private EnrollmentDao enrollmentDao;
	
	@Autowired
    private EnrollmentTestTypeSubjectAreaDao enrollmentTestTypeSubjectAreaDao;
	
	@Override
	public void createTestRecord(ViewStudentDTO student, Long testTypeId,
			Long testSubjectId) {
		logger.trace("Entering into createTestRecord method");
		
		
		EnrollmentTestTypeSubjectArea enrollmentTestTypeSubjectArea =new EnrollmentTestTypeSubjectArea();
		//String attendanceSchoolDisplayIdentifer=student.getAttendanceSchoolDisplayIdentifiers();
		
		
		
		Enrollment enrollment=enrollmentDao.getBySSIDYearAndSchool(student.getStateStudentIdentifier(), student.getCurrentSchoolYearEnrollment(), student.getAttendanceSchoolDisplayIdentifiers(), true);
		
		enrollmentTestTypeSubjectArea.setAuditColumnPropertiesForUpdate();
		enrollmentTestTypeSubjectArea.setEnrollmentId(enrollment.getId());
		enrollmentTestTypeSubjectArea.setActiveFlag(true);
		enrollmentTestTypeSubjectArea.setTestTypeId(testTypeId);
		enrollmentTestTypeSubjectArea.setSubjectareaId(testSubjectId);		
		
		EnrollmentTestTypeSubjectAreaExample enrollmentTestTypeSubjectAreaExample = new EnrollmentTestTypeSubjectAreaExample();
		EnrollmentTestTypeSubjectAreaExample.Criteria enrollmentTestTypeSubjectAreaCriteria
		    = enrollmentTestTypeSubjectAreaExample.createCriteria();
		enrollmentTestTypeSubjectAreaCriteria.andEnrollmentIdEqualTo(enrollment.getId());
		enrollmentTestTypeSubjectAreaCriteria.andSubjectareaIdEqualTo(testSubjectId);
		Integer updatedValue = enrollmentTestTypeSubjectAreaDao.updateByExampleSelective(
				enrollmentTestTypeSubjectArea, enrollmentTestTypeSubjectAreaExample);
		
		if(updatedValue == null || updatedValue < 1) {	        
			enrollmentTestTypeSubjectArea.setAuditColumnProperties();
			enrollmentTestTypeSubjectAreaDao.insert(enrollmentTestTypeSubjectArea);
		}
		logger.trace("leaving from createTestRecord method");
	}

}
