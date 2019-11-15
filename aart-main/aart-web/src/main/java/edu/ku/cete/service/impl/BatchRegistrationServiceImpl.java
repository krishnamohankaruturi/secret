package edu.ku.cete.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.BatchJobSchedule;
import edu.ku.cete.domain.BatchRegistrationDTO;
import edu.ku.cete.domain.TestEnrollmentMethod;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.model.BatchJobScheduleMapper;
import edu.ku.cete.model.OperationalTestWindowDao;
import edu.ku.cete.model.TestEnrollmentMethodMapper;
import edu.ku.cete.report.domain.BatchRegisteredScoringAssignments;
import edu.ku.cete.report.domain.BatchRegisteredTestSessions;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.report.domain.QuestarRegistrationReason;
import edu.ku.cete.report.model.BatchRegisteredScoringAssignmentsMapper;
import edu.ku.cete.report.model.BatchRegisteredTestSessionsMapper;
import edu.ku.cete.report.model.BatchRegistrationMapper;
import edu.ku.cete.report.model.BatchRegistrationReasonMapper;
import edu.ku.cete.report.model.QuestarRegistrationReasonMapper;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.SubjectAreaService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.util.SourceTypeEnum;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class BatchRegistrationServiceImpl implements BatchRegistrationService {
	
    /**
     * logger.
     */
    private Logger logger = LoggerFactory.getLogger(BatchRegistrationServiceImpl.class);
    
	 @Autowired
	 private BatchRegistrationMapper batchRegistrationMapper;
	 
	 @Autowired
	 private BatchRegisteredTestSessionsMapper batchRegisteredTestSessionsMapper;
	 
	 @Autowired
	 private BatchRegistrationReasonMapper batchRegistrationReasonMapper;
	 
	 @Autowired
	 private QuestarRegistrationReasonMapper questarBatchRegistrationReasonMapper;
	 
	 @Autowired
	 private BatchJobScheduleMapper batchJobScheduleMapper;
	 
	 @Autowired 
	 private OperationalTestWindowDao operationalTestWindowDao;
	 
	 @Autowired 
	 private TestEnrollmentMethodMapper enrollmentMethodDao;
	 
	 @Autowired
	 private ContentAreaService contentAreaService;
    
	 @Autowired
	 private GradeCourseService gradeCourseService;
    
	 @Autowired
	 private SubjectAreaService subjectAreaService;
    
	 @Autowired
	 private AssessmentProgramService assessmentProgramService;
    
	 @Autowired
	 private TestingProgramService testingProgramService;
	 @Autowired
	 private TestTypeService testTypeService;
    
	 @Autowired
	 private AssessmentService assessmentService;
	 
	 private SqlSession sqlStageSession;
	 
	 @Autowired	 
	 private BatchRegisteredScoringAssignmentsMapper batchRegisteredScoringAssignmentsMapper;
	 
	 @Autowired
	 @Qualifier("reportSqlSessionFactory")
	 public void setSqlStageSessionFactory(SqlSessionFactory sqlStageSessionFactory) {
		 sqlStageSession = new SqlSessionTemplate(sqlStageSessionFactory, ExecutorType.BATCH);
	 }	 
	 @Override
	 @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	 public final long insertBatchRegistration(BatchRegistration batchRegistrationRecord) {
		 return batchRegistrationMapper.insert(batchRegistrationRecord);
	 } 
    
    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public final long insertSelectiveBatchRegistration(BatchRegistration batchRegistrationRecord) {
        return batchRegistrationMapper.insertSelective(batchRegistrationRecord);
    } 

    @Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public final long updateBatchRegistrationSelective(BatchRegistration batchRegistrationRecord) {
        return batchRegistrationMapper.updateByPrimaryKeySelective(batchRegistrationRecord);
    } 

	@Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public long insertBatchRegisteredTestSessions(BatchRegisteredTestSessions batchRegisteredTestSessions) {
		return batchRegisteredTestSessionsMapper.insert(batchRegisteredTestSessions);
	}

	@Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public long insertBatchRegistrationReason(BatchRegistrationReason batchRegistrationReasonRecord) {
		return batchRegistrationReasonMapper.insert(batchRegistrationReasonRecord);
	}
	
	@Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public void insertReasons(List<BatchRegistrationReason> reasons) {
		logger.debug("--> insertReasons");
		for (BatchRegistrationReason reason : reasons) {
			sqlStageSession.insert("edu.ku.cete.report.model.BatchRegistrationReasonMapper.insertSelective",
					reason);
		}
		logger.debug("<-- insertReasons");
	}
	
	@Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public void insertQuestarReasons(List<QuestarRegistrationReason> reasons) {
		logger.debug("--> insertReasons");
		for (QuestarRegistrationReason reason : reasons) {
			sqlStageSession.insert("edu.ku.cete.report.model.QuestarRegistrationReasonMapper.insertSelective",
					reason);
		}
		logger.debug("<-- insertQuestarReasons");
	}	
	
	@Override
	public List<BatchRegistrationReason> getByBatchRegistrationId(Long batchId) {
		BatchRegistration brRecord = batchRegistrationMapper.selectByPrimaryKey(batchId);
		List<BatchRegistrationReason> reasons = null;
		if(brRecord.getBatchTypeCode().equalsIgnoreCase(SourceTypeEnum.QUESTARPROCESS.getCode())) {
			reasons = (List<BatchRegistrationReason>) questarBatchRegistrationReasonMapper.selectByBatchRegistrationId(batchId);
		} else {
			reasons = batchRegistrationReasonMapper.selectByBatchRegistrationId(batchId);
		}
		
		return reasons;
	}

	@Override
	public List<BatchRegistrationDTO> getBatchRegisteredHistory(java.util.Date fromDate, java.util.Date toDate) {
		List<BatchRegistrationDTO> batchHistory =  batchRegistrationMapper.getHistory(fromDate, toDate);
		if(batchHistory != null && batchHistory.size() > 0){
			for(BatchRegistrationDTO batchDTO : batchHistory){
				if(batchDTO != null){
					if(batchDTO.getAssessment() != null){
						batchDTO.setAssessmentName(assessmentService.getById(batchDTO.getAssessment()).getAssessmentName());
					}
					
					if(batchDTO.getAssessmentProgram() != null){
						batchDTO.setAssessmentProgramName(assessmentProgramService.findByAssessmentProgramId(batchDTO.getAssessmentProgram()).getProgramName());
					}
					
					if(batchDTO.getGrade() != null){
						batchDTO.setGradeName(gradeCourseService.selectByPrimaryKey(batchDTO.getGrade()).getName());
					}
					
					if(batchDTO.getSubject() != null){
						batchDTO.setSubjectName(subjectAreaService.getById(batchDTO.getSubject()).getSubjectAreaName());
					}
					
					if(batchDTO.getTestingProgram() != null){
						batchDTO.setTestingProgramName(testingProgramService.getByTestingProgramId(batchDTO.getTestingProgram()).getProgramName());
					}
					
					if(batchDTO.getTestType() != null){
						batchDTO.setTestTypeName(testTypeService.getById(batchDTO.getTestType()).getTestTypeName());
					}
					
					if(batchDTO.getContentAreaId() != null){
						batchDTO.setContentAreaName(contentAreaService.selectByPrimaryKey(batchDTO.getContentAreaId()).getName());
					}
					
					if(batchDTO.getOperationalTestWindowId() != null){
						List<OperationalTestWindow> windows = operationalTestWindowDao.selectTestWindowById(batchDTO.getOperationalTestWindowId());
						if(!windows.isEmpty()) {
							batchDTO.setTestWindowName(windows.get(0).getWindowName());
						}
					}
					
					if(batchDTO.getAutoEnrollmentMethodId() != null){
						batchDTO.setEnrollmentMethodName(enrollmentMethodDao.selectByPrimaryKey(batchDTO.getAutoEnrollmentMethodId()).getMethodName());
					}
				}
			}
		}
		return batchHistory;
	}
	
	@Override
	public List<BatchJobSchedule> getBatchJobSchedules(String server) {
		return batchJobScheduleMapper.selectByServer(server);
	}
	
	@Override
	public List<TestEnrollmentMethod> getTestEnrollmentMethods(Long assessmentProgramId) {
		return enrollmentMethodDao.selectByAssessmentProgramId(assessmentProgramId);
	}
	
	
	@Override
	public TestEnrollmentMethod getTestEnrollmentMethod(Long id) {
		return enrollmentMethodDao.selectByPrimaryKey(id);
	}
	
	@Override
	public TestEnrollmentMethod getTestEnrollmentMethodByCode(Long assessmentProgramId, String enrollmentMethod) {
		return enrollmentMethodDao.selectTestEnrollmentMethodByCode(assessmentProgramId, enrollmentMethod);
	}
	
	@Override
	public List<OperationalTestWindow> getTestWindowsForBatchRegistration(Long assessmentProgramId,Long autoEnrollmentMethodId) {
		return operationalTestWindowDao.selectWindowsForBatchRegistration(assessmentProgramId, autoEnrollmentMethodId);
	}
	@Override
	public List<OperationalTestWindow> getEffectiveTestWindowsForBatchRegistration(
			Long assessmentProgramId, Long autoEnrollmentMethodId) {
		return operationalTestWindowDao.selectEffectiveWindowsForBatchRegistration(assessmentProgramId, autoEnrollmentMethodId);
	}
	
	@Override
	public List<OperationalTestWindow> getEffectiveStateWindows(Long assessmentProgramId, Long autoEnrollmentMethodId) {
		return operationalTestWindowDao.selectEffectiveStateWindows(assessmentProgramId, autoEnrollmentMethodId);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
    public BatchRegistration getBatchRegistrationById(Long id) {
        return batchRegistrationMapper.selectByPrimaryKey(id);
    } 
	
	@Override
	public Date getLatestSubmissionDateWithEnrollmentMethod(Long testEnrollmentMethodId){
		return batchRegistrationMapper.selectLatestEnrollmentJobDate(testEnrollmentMethodId);
	}
	
	@Override
	public Date getLatestSubmissionDateWithBatchTypeCode(String batchTypeCode) {
		return batchRegistrationMapper.selectLatestJobDate(batchTypeCode);
	}
	
	@Override
    @Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public long insertBatchRegisteredScoringAssignments(List<BatchRegisteredScoringAssignments> batchRegisteredScoringAssignments) {
		return batchRegisteredScoringAssignmentsMapper.insert(batchRegisteredScoringAssignments);
	}
 }
