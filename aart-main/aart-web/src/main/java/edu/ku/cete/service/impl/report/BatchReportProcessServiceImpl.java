package edu.ku.cete.service.impl.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.ReportProcess;
import edu.ku.cete.domain.ReportTestLevelSubscores;
import edu.ku.cete.domain.StudentReportTestScores;
import edu.ku.cete.domain.SuppressedLevel;

import edu.ku.cete.domain.report.ActScoringDescription;
import edu.ku.cete.domain.report.ActScoringLevel;

import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.PredictiveReportCreditPercent;
import edu.ku.cete.domain.report.PredictiveReportOrganization;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.ReportProcessRecordCounts;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.report.ReportsMedianScore;
import edu.ku.cete.domain.report.ReportsPercentByLevel;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.model.ExternalstudentreportsMapper;
import edu.ku.cete.model.GrfStateApproveAuditMapper;
import edu.ku.cete.model.InterimStudentReportMapper;
import edu.ku.cete.model.OrganizationReportDetailsMapper;
import edu.ku.cete.model.PredictiveReportCreditPercentMapper;
import edu.ku.cete.model.ReportProcessMapper;
import edu.ku.cete.model.ReportProcessReasonMapper;
import edu.ku.cete.model.ReportProcessRecordCountsMapper;
import edu.ku.cete.model.ReportSubscoresMapper;
import edu.ku.cete.model.ReportTestLevelSubscoresMapper;
import edu.ku.cete.model.ReportsMedianScoreMapper;
import edu.ku.cete.model.ReportsPercentByLevelMapper;
import edu.ku.cete.model.StudentReportMapper;
import edu.ku.cete.model.StudentReportQuestionInfoMapper;
import edu.ku.cete.model.StudentReportTestScoresMapper;
import edu.ku.cete.model.SuppressedLevelMapper;
import edu.ku.cete.model.TestingCycleMapper;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.report.SubScoreMedianScore;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.web.ExternalStudentReportDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class BatchReportProcessServiceImpl implements BatchReportProcessService {
	
    private Logger logger = LoggerFactory.getLogger(BatchReportProcessServiceImpl.class);
    
    @Autowired
    private StudentDao studentDao;
	
    @Autowired
	private ReportProcessMapper reportProcessDao;
    
    @Autowired
  	private StudentReportMapper studentReportDao;
    
    @Autowired
    private ReportsMedianScoreMapper reportsMedianScoreMapper;
	
	@Autowired
	private ReportProcessReasonMapper reportProcessReasonMapper;
	
	@Autowired
	ReportsPercentByLevelMapper reportsPercentByLevelMapper;
	
	@Autowired
	private ReportSubscoresMapper reportSubscoresMapper;
	
	@Autowired
	private OrganizationReportDetailsMapper organizationReportDetailsMapper;
	
	@Autowired
	private ReportProcessRecordCountsMapper reportProcessRecordCountsMapper;
	
	@Autowired
	private SuppressedLevelMapper suppressedLevelMapper;
	
	@Autowired
	private StudentReportTestScoresMapper studentReportTestScoresMapper;
	
	@Autowired
	private ReportTestLevelSubscoresMapper reportTestLevelSubscoresMapper;
	
	private SqlSession sqlSession;
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
    @Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;

    @Value("${cpass.student.individual.report.type.code}")
	private String dbCPASSStudentReportsImportReportType;
	
    @Autowired
    private InterimStudentReportMapper interimStudentReportMapper;
    
    @Autowired
    private StudentReportQuestionInfoMapper studentReportQuestionInfoMapper;
    
    @Autowired
    private TestingCycleMapper testingCycleMapper;
    
    @Autowired
    private PredictiveReportCreditPercentMapper predictiveReportCreditPercentMapper;
        
    @Autowired
    private ExternalstudentreportsMapper externalstudentreportsMapper;
    
    @Autowired
    private GrfStateApproveAuditMapper grfStateApproveAuditMapper;
    
    @Autowired
    private AwsS3Service s3;
    
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		sqlSession = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
	}
	
	@Override
	public List<ReportProcess> getBatchReportingHistory(Date fromDate, Date toDate, String orderByColumn, String order, Integer limit, Integer offset, List<Long> apIds){
		return reportProcessDao.getBatchReportingHistory(fromDate, toDate, orderByColumn, order, limit, offset, apIds);
	}
	
	@Override
	public int countBatchReportingHistory(Date fromDate, Date toDate, List<Long> apIds){
		return reportProcessDao.countBatchReportingHistory(fromDate, toDate, apIds);
	}
	
	@Override
	public int selectDuplicateCountBatchReportProcess(Long assessmentProgramId, Long subjectId, Long gradeId, String process) {
		return reportProcessDao.selectDuplicateCount(assessmentProgramId, subjectId, gradeId, process);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updatePreviousToInactiveBatchReport(Long assessmentProgramId, Long subjectId, Long gradeId, String process) {
		return reportProcessDao.updatePreviousToInactive(assessmentProgramId, subjectId, gradeId, process);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertBatchReport(ReportProcess record) {
		return reportProcessDao.insert(record);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsForReportProcess(Long studentId, Long contractOrgId, Long assessmentId, Long assessmentProgramId, String assessmentProgramCode, Long gradeId, Long contentAreaId, Long currentSchoolYear, List<Long> rawScaleExternalTestIds, List<Long> testsStatusIds, List<Long> studentIdList, Integer pageSize, Integer offset) {
		return studentDao.getStudentsForReportProcess(studentId, contractOrgId, assessmentId, assessmentProgramId,assessmentProgramCode, gradeId, contentAreaId, currentSchoolYear, rawScaleExternalTestIds, testsStatusIds, studentIdList, pageSize, offset);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsForReportGeneration(Long studentId, Long contractOrgId, Long assessmentProgramId, Long gradeId, Long contentAreaId, Long schoolYear, Integer pageSize, Integer offset,String processByStudentId,String reprocessEntireDistrict) {
		return studentDao.getStudentsForReportGeneration(studentId, contractOrgId, assessmentProgramId, gradeId, contentAreaId, schoolYear, pageSize, offset,processByStudentId,reprocessEntireDistrict);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelectiveBatchReportProcess(ReportProcess record) {
		return reportProcessDao.updateByPrimaryKeySelective(record);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertSelectiveReportProcessReasons(List<ReportProcessReason> reportProcessReasonList) {
		logger.debug("Writing batch report reasons to reason table");
		for (ReportProcessReason reportProcessReason : reportProcessReasonList) {
			sqlSession.insert("edu.ku.cete.model.ReportProcessReasonMapper.insertSelective", reportProcessReason);
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSelectiveStudentReport(StudentReport studentReport) {
		return studentReportDao.insertSelective(studentReport) ;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSelectiveReportSubscores(ReportSubscores subscore) {
		return reportSubscoresMapper.insertSelective(subscore) ;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertReportSubscoresList(List<ReportSubscores> subscores) {
		for(ReportSubscores subscore : subscores){
			logger.debug("studentreportid: "+subscore.getStudentReportId()+" "+subscore.getSubscoreDefinitionName());
			reportSubscoresMapper.insertSelective(subscore);
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateByPrimaryKeySelectiveStudentReport(StudentReport studentReport) {
		return studentReportDao.updateByPrimaryKeySelective(studentReport) ;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteSpecificStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long studentId, Long schoolYear) {
		return studentReportDao.deleteSpecificStudents(assessmentProgramId, contentAreaId, gradeId, studentId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteReportSubscores(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear) {
		return reportSubscoresMapper.deleteReportSubscores(assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteReportSubscoresByStudentId(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long studentId, Long schoolYear) {
		return reportSubscoresMapper.deleteReportSubscoresByStudentId(assessmentProgramId, contentAreaId, gradeId, studentId, schoolYear);
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteAllOrgsInOrganizationReportDetails(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, List<String> reportTypeSummary) {
		return organizationReportDetailsMapper.deleteAllOrgsInOrganizationReportDetails(assessmentProgramId, contentAreaId, gradeId, schoolYear, reportTypeSummary);
	}
	
	@Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT)
	public List<ReportProcessReason> findReportProcessReasonsForId(Long reportProcessId) {
		return reportProcessReasonMapper.selectByReportProcessId(reportProcessId);
	}
	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportsMedianScore> getAllOrgsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
													Integer offset, Integer pageSize) {
		return studentReportDao.selectAllOrgsFromStudentReport(assessmentProgramId, contentAreaId, gradeId, offset, pageSize);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportsMedianScore> getDistinctSchoolIdsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
													Integer offset, Integer pageSize) {
		return studentReportDao.selectDistinctSchoolIdsFromStudentReport(assessmentProgramId, contentAreaId, gradeId, offset, pageSize);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportsMedianScore> getDistinctStateIdsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
													Integer offset, Integer pageSize) {
		return studentReportDao.selectDistinctStateIdsFromStudentReport(assessmentProgramId, contentAreaId, gradeId, offset, pageSize);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportsMedianScore> getDistinctDistrictIdsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeId,
													Integer offset, Integer pageSize) {
		return studentReportDao.selectDistinctDistrictIdsFromStudentReport(assessmentProgramId, contentAreaId, gradeId, offset, pageSize);
	}


	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsBySchoolAssessmentGradeSubject(
			Long schoolId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsBySchoolAssessmentSubjectGrade(schoolId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsByDistrictAssessmentGradeSubject(
			Long districtId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsByDistrictAssessmentSubjectGrade(districtId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsByStateAssessmentGradeSubject(
			Long stateId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsByStateAssessmentSubjectGrade(stateId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertReportMedianScore(ReportsMedianScore record) {
		return reportsMedianScoreMapper.insertSelective(record);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertReportsPercentByLevel(List<ReportsPercentByLevel> records) {
		for(ReportsPercentByLevel record : records){
			reportsPercentByLevelMapper.insertSelective(record);
		}

	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteReportsPercentByLevel(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear) {
		return reportsPercentByLevelMapper.deleteReportsPercentByLevel(assessmentProgramId, subjectId, gradeId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteReportMedianScore(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear) {
		return reportsMedianScoreMapper.deleteReportMedianScore(assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	public List<ReportsMedianScore> getMedainScoreByOrgIdsAssessmentContentGradeSchoolYear(
			List<Long> organizationIds, Long assessmentProgramId,
			Long contentAreaId, List<Long> gradeIds, Long schoolYear) {
		return reportsMedianScoreMapper.getMedainScoreByOrgIdsAssessmentContentGradeSchoolYear(organizationIds, assessmentProgramId, contentAreaId, gradeIds, schoolYear);
	}
	
	@Override


	public List<ActScoringDescription> getActScoreByOrgIdsAssessmentContentGradeSchoolYear(Long assessmentProgramId,  Long gradeId, Long contentAreaId, Long currentSchoolYear, Long levelId) {
	return studentDao.getActScoreByOrgIdsAssessmentContentGradeSchoolYear(assessmentProgramId, gradeId, contentAreaId, currentSchoolYear, levelId);
	}
	
	@Override
	public List<ActScoringDescription> getActScoreDescriptionByOrgIdsAssessmentContentGradeSchoolYear(Long assessmentProgramId,  Long gradeId, Long contentAreaId, Long currentSchoolYear) {
		return studentDao.getActScoreDescriptionByOrgIdsAssessmentContentGradeSchoolYear(assessmentProgramId, gradeId, contentAreaId, currentSchoolYear);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateStudentReportFilePath(String filePath, Boolean generated, Long id, Long batchReportProcessId, String attendanceSchoolName, String districtName, String progressionText) {
		return studentReportDao.updateStudentReportFilePath(filePath,generated, id, batchReportProcessId,attendanceSchoolName,districtName, progressionText);
	}
		
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public SubScoreMedianScore getStudentsBySchoolAssmntGradeSubjectSubScoreDef(
			Long schoolId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {		
		return reportSubscoresMapper.selectAllStudentsBySchoolAssmntSubjectGradeSubScoreDef(schoolId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public SubScoreMedianScore getStudentsByDistrictAssmntGradeSubjectSubScoreDef(
			Long districtId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {		
		return reportSubscoresMapper.selectAllStudentsByDistrictAssmntSubjectGradeSubScoreDef(districtId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public SubScoreMedianScore getStudentsByStateAssmntGradeSubjectSubScoreDef(
			Long stateId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {		
		return reportSubscoresMapper.selectAllStudentsByStateAssmntSubjectGradeSubScoreDef(stateId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	public Long calcMedianScore(List<Long> studentScaleScores,	int studentCount) {
		Collections.sort(studentScaleScores);
		Long medianscore = null;
		if(studentCount >= 1){			
			if((studentCount % 2)==0){
				   // even   ( (N/2)th + ((N/2)+1)th)  )/ 2
				 BigDecimal fn = new BigDecimal(studentScaleScores.get((studentCount/2) - 1) + studentScaleScores.get(((studentCount/2) + 1) - 1));
				 medianscore = fn.divide(BigDecimal.valueOf(2), RoundingMode.HALF_UP).longValue();
			}else{
				 // odd  ((N+1)/2))
				double nonRoundOffMedainScore = (double) studentScaleScores.get(((studentCount + 1) / 2) - 1);
				medianscore = Math.round(nonRoundOffMedainScore);
			}			
		}
		return medianscore;
	}	

	@Override
	public double calcStandardDeviation(List<Long> studentScaleScores) {
		if(studentScaleScores.size() <= 1)
 			return 0;
		double sumOfScaleScores=0;
		double mean=0;  
	    for(Long studentScaleScore : studentScaleScores){
	    	  sumOfScaleScores+=studentScaleScore; //sum of all elements
	    }
	    mean=sumOfScaleScores/studentScaleScores.size();  
	    sumOfScaleScores=0;  
	    for(Long studentScaleScore : studentScaleScores){
	    	sumOfScaleScores += Math.pow((studentScaleScore - mean),2);
	    }
	    mean=sumOfScaleScores/(studentScaleScores.size() - 1);  
	    double deviation=Math.sqrt(mean);
 		return deviation;
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistinctOrganizationIdFromMedianScore(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, Long contractOrgId, Integer pageSize, Integer offset) {		
		return reportsMedianScoreMapper.getDistinctOrganizationReportMedian(assessmentProgramId, contentAreaId, gradeId, schoolYear, contractOrgId,  pageSize, offset);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportSubscores>  selectSubscoresDetailsByStudentReportIdAndReportType(Long studentId, String reportType,Long contentareaId,Long attendanceSchoolid) {		
		return reportSubscoresMapper.selectSubscoresDetailsByStudentReportIdAndReportType(studentId, reportType,contentareaId,attendanceSchoolid);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportSubscores>  selectSubscoresMediansByOrganizationIdAndReportType(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, Long orgId, String reportType) {		
		return reportSubscoresMapper.selectSubscoresMediansByOrganizationIdAndReportType(assessmentProgramId, contentAreaId, gradeId, schoolYear, orgId, reportType);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertOrganizationReportDetails(OrganizationReportDetails record) {
		organizationReportDetailsMapper.deleteOrganizationReportDetails(record);
		return organizationReportDetailsMapper.insert(record);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportsPercentByLevel> selectReportPercentByOrganization(Long assessmentProgramId, Long contentAreaId, List<Long> gradeIds, Long schoolYear, List<Long> organizationIds) {
		return reportsPercentByLevelMapper.selectReportPercentByOrganization(assessmentProgramId, contentAreaId, gradeIds, schoolYear, organizationIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentReportsForSchoolReportPdf(Long assessmentProgramId, String gradeCourseAbbrName, Long schoolYear, Long attendanceSchoolId) {
		return studentReportDao.getStudentReportsForSchoolReportPdf(assessmentProgramId, gradeCourseAbbrName, schoolYear, attendanceSchoolId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getExternalStudentReportsForSchoolReportPdf(Long assessmentProgramId, String gradeCourseAbbrName, Long schoolYear, Long attendanceSchoolId, String reportType) {
		return studentReportDao.getExternalStudentReportsForSchoolReportPdf(assessmentProgramId, gradeCourseAbbrName, schoolYear, attendanceSchoolId, reportType);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getExternalStudentReportsForDistrictReportPdf(Long assessmentProgramId, Long schoolYear, String assessmentProgramCode, Long attendanceSchoolId, String reportType) {
		return studentReportDao.getExternalStudentReportsForDistrictReportPdf(assessmentProgramId, schoolYear, assessmentProgramCode, attendanceSchoolId, reportType);
	}
    
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentReportsForDistrictReportPdf(Long assessmentProgramId, Long schoolYear, Long attendanceSchoolId) {
		return studentReportDao.getStudentReportsForDistrictReportPdf(assessmentProgramId, schoolYear, attendanceSchoolId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getSchoolIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(Long stateId, Long assessmentProgramId, Long currentSchoolYear, String gradeCourseAbbrName, Integer pageSize, Integer offset) {		
		return studentReportDao.getSchoolIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(stateId, assessmentProgramId, currentSchoolYear, gradeCourseAbbrName, pageSize, offset);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getSchoolIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(Long stateId, Long assessmentProgramId, Long currentSchoolYear, String gradeCourseAbbrName, Integer pageSize, Integer offset, String reportType) {		
		return studentReportDao.getSchoolIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(stateId, assessmentProgramId, currentSchoolYear, gradeCourseAbbrName, pageSize, offset, reportType);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistrictIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(Long stateId, Long assessmentProgramId, Long currentSchoolYear, Integer pageSize, Integer offset) {		
		return studentReportDao.getDistrictIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(stateId, assessmentProgramId, currentSchoolYear, pageSize, offset);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistrictIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(Long stateId, Long assessmentProgramId, Long currentSchoolYear, Integer pageSize, Integer offset, String reportType) {		
		return studentReportDao.getDistrictIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(stateId, assessmentProgramId, currentSchoolYear, pageSize, offset, reportType);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYear(Long stateId, Long assessmentProgramId, int currentSchoolYear) {
		return studentReportDao.geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYear(stateId, assessmentProgramId, currentSchoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYearForExternalReport(Long stateId, Long assessmentProgramId, int currentSchoolYear, String reportType) {
		return studentReportDao.geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYearForExternalReport(stateId, assessmentProgramId, currentSchoolYear,reportType);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSchoolReportOfStudentFilesPdf(
			OrganizationReportDetails schoolReportPdfOfStudentReoports) {
		return organizationReportDetailsMapper.insertSelective(schoolReportPdfOfStudentReoports);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getSchoolIdsFromOrgDetailReportByStateIdAssmntProgIdAndSchoolYear(Long contractingOrganizationId, Long assessmentProgramId,Long schoolYear, Integer pageSize, Integer offset, String reportType) {
		return organizationReportDetailsMapper.getSchoolIdsFromOrgDetailReportByStateIdAssmntProgIdAndSchoolYear(contractingOrganizationId, assessmentProgramId, schoolYear, pageSize, offset, reportType);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationReportDetails> getOrganizationReportDetailsForZip(Long assessmentProgramId, Long organizationId, Long schoolYear, String reportType) {		
		return organizationReportDetailsMapper.getOrganizationReportDetailsForZip(assessmentProgramId, organizationId, schoolYear, reportType);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSchoolReportZip(OrganizationReportDetails schoolZipReportPeoports) {
		return organizationReportDetailsMapper.insertSelective(schoolZipReportPeoports);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportProcessRecordCounts> getRecordCountForBatchReportScoreCalcJob(Long batchReportProcessId) {
		return reportProcessRecordCountsMapper.selectCountForScoreCalcJob(batchReportProcessId);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportProcessRecordCounts> getRecordCountForBatchStudentReportJob(Long batchReportProcessId) {
		return reportProcessRecordCountsMapper.selectCountForStudentReportJob(batchReportProcessId);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportProcessRecordCounts> getRecordCountForOrganizationReportJob(Long batchReportProcessId, String organizationTypeCode) {
		return reportProcessRecordCountsMapper.selectCountForOrganizationReportJob(batchReportProcessId, organizationTypeCode);
	}	
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void insertBatchReportCounts(List<ReportProcessRecordCounts> reportProcessRecordCounts, String process, Long batchReportProcessId) {
		for(ReportProcessRecordCounts reportProcessRecordCount : reportProcessRecordCounts) {
			reportProcessRecordCount.setBatchReportProcessId(batchReportProcessId);
			reportProcessRecordCount.setProcess(process);
			reportProcessRecordCountsMapper.insertSelective(reportProcessRecordCount);
		}
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getAllStudentReportByAssessmentProgramIdContentAreaIdGradeIdStudentId(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long studentId, Long schoolYear) {
		return studentReportDao.selectAllByAssessmentProgramIdContentAreaIdGradeIdStudentId(assessmentProgramId, gradeId, contentAreaId, studentId, schoolYear);
	}
	
	@Override
	public void deleteAllStudentReportFiles(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long studentId, Long schoolYear) {
		List<StudentReport> studentReportDetails = getAllStudentReportByAssessmentProgramIdContentAreaIdGradeIdStudentId(assessmentProgramId, gradeId, contentAreaId, studentId, schoolYear);
		if(CollectionUtils.isNotEmpty(studentReportDetails)){
			for(StudentReport studentReportDetail : studentReportDetails){
				if(studentReportDetail.getFilePath() != null && studentReportDetail.getFilePath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + studentReportDetail.getFilePath()))
						s3.deleteObject(REPORT_PATH + studentReportDetail.getFilePath());
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationReportDetails> getAllOrgReportDetailsByAssessmentProgramIdContentAreaIdGradeId(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long schoolYear, List<String> reportTypeSummary) {
		return organizationReportDetailsMapper.selectAllOrgReportDetailsByAssessmentProgramIdContentAreaIdGradeId(assessmentProgramId, gradeId, contentAreaId, schoolYear, reportTypeSummary);
	}
	
	@Override
	public void deleteAllOrganizationReportFiles(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long schoolYear, List<String> reportTypeSummary) {
		List<OrganizationReportDetails> organizationReportDetails = getAllOrgReportDetailsByAssessmentProgramIdContentAreaIdGradeId(assessmentProgramId, null, null, schoolYear, reportTypeSummary);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getContentAreaId() == null ||
				   organizationReportDetail.getContentAreaId().equals(contentAreaId) || 
				   organizationReportDetail.getSchoolReportPdfSize() != null || 
				   organizationReportDetail.getSchoolReportZipSize() != null){
					if(organizationReportDetail.getGradeId() == null ||
						organizationReportDetail.getGradeId().equals(gradeId) || 
						gradeId == null){
							if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
								if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
									s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
							}
					}
				}
				
			}
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllSchoolBundleReports(Long assessmentProgramId, Long schoolYear, String reportType) {		
		organizationReportDetailsMapper.deleteAllSchoolBundleReports(assessmentProgramId, schoolYear, reportType);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllSchoolBundleReportsForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId ,String reportType) {		
		organizationReportDetailsMapper.deleteAllSchoolBundleReportsForUserState(assessmentProgramId, schoolYear, organizationId , reportType);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllDistrictBundleReportsForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId ,String reportType) {		
		organizationReportDetailsMapper.deleteAllDistrictBundleReportsForUserState(assessmentProgramId, schoolYear, organizationId , reportType);
	}
	
	@Override
	public void deleteAllSchoolBundleReportFiles(Long assessmentProgramId, Long schoolYear, String reportType) {
		List<OrganizationReportDetails> organizationReportDetails = getAllSchoolReportBundleReportsByAssessmentProgramId(assessmentProgramId, schoolYear, reportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
		}
	}
	
	@Override
	public void deleteAllSchoolBundleReportFilesForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId ,String reportType) {
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.getAllSchoolReportBundleReportsByAssessmentProgramIdForUserState(assessmentProgramId, schoolYear, organizationId , reportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
		}
	}
	
	
	
	
	@Override
	public void deleteAllDistrictBundleReportFilesForUserState(Long assessmentProgramId, Long schoolYear, Long organizationId ,String reportType) {
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.getAllDistrictReportBundleReportsByAssessmentProgramIdForUserState(assessmentProgramId, schoolYear, organizationId , reportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private List<OrganizationReportDetails> getAllSchoolReportBundleReportsByAssessmentProgramId(Long assessmentProgramId, Long schoolYear, String reportType) {		
		return organizationReportDetailsMapper.getAllSchoolReportBundleReportsByAssessmentProgramId(assessmentProgramId, schoolYear, reportType);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistinctGradeIdsInOrgFromMedains(Long assessmentProgramId, Long contentAreaId, Long schoolYear, Long organizationId) {
		return reportsMedianScoreMapper.getDistinctGradeIdsInOrgFromMedains(assessmentProgramId, contentAreaId, schoolYear, organizationId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public SuppressedLevel getSuppressedLevel(Long contentAreaId, Long gradeCourseId) {
		return suppressedLevelMapper.selectByContentAreaIdAndGradeCourseId(contentAreaId, gradeCourseId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StudentReport getPreviousYearReport(StudentReport studentReport) {
		return studentReportDao.getPreviousYearReport(studentReport);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSelectiveStudentReportTestScores(StudentReportTestScores studentReportTestScores) {
		return studentReportTestScoresMapper.insertSelective(studentReportTestScores) ;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteSpecificStudentReportTestScores(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long studentId, Long schoolYear) {
		return studentReportTestScoresMapper.deleteSpecificStudentRecords(assessmentProgramId, contentAreaId, gradeId, studentId, schoolYear);
	}

	@Override
	public List<ReportSubscores> getItemCountBySubscoreDefinitionNameByTestId(Long testId, Long schoolYear,
			Long assessmentProgramId, Long subjectId, Long gradeId) {
		return reportSubscoresMapper.getItemCountBySubscoreDefinitionNameByTestId(testId, schoolYear, assessmentProgramId, subjectId, gradeId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertSelectiveTestLevelSubscore(ReportTestLevelSubscores record) {
		return reportTestLevelSubscoresMapper.insertSelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteTestLevelStudentSubscores(Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Long studentId, Long schoolYear) {
		return reportTestLevelSubscoresMapper.deleteTestLevelStudentSubscores(assessmentProgramId, contentAreaId, gradeId, studentId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsBySchoolAssessmentGradeSubjectForMdptLevel(
			Long schoolId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsBySchoolAssmntSubjectGradeForMdptLevel(schoolId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsByDistrictAssessmentGradeSubjectForMdptLevel(
			Long districtId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsByDistrictAssmntSubjectGradeForMdptLevel(districtId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsByStateAssessmentGradeSubjectForMdptLevel(
			Long stateId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsByStateAssmntSubjectGradeForMdptLevel(stateId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsBySchoolAssessmentGradeSubjectForCombinedLevel(
			Long schoolId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsBySchoolAssmntSubjectGradeForCombinedLevel(schoolId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsByDistrictAssessmentGradeSubjectForCombinedLevel(
			Long districtId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsByDistrictAssmntSubjectGradeForCombinedLevel(districtId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentsByStateAssessmentGradeSubjectForCombinedLevel(
			Long stateId, Long assessmentProgramId, Long contentAreaId,
			Long gradeId, Long schoolYear) {
		return studentReportDao.selectAllStudentsByStateAssmntSubjectGradeForCombinedLevel(stateId, assessmentProgramId, contentAreaId, gradeId, schoolYear);
	}

	@Override
	public List<ReportSubscores> selectSubscoresDetailsForOrganization(
			Long orgId, Long assessmentProgramId, Long contentAreaId,
			List<Long> orgGradeIds, Long schoolYear,String reportType) {
		// TODO Auto-generated method stub
		//return null;
		return reportSubscoresMapper.selectSubscoresDetailsForOrganization(orgId, assessmentProgramId,contentAreaId,
				orgGradeIds,schoolYear,reportType);
	}

	@Override
	public Boolean checkNonScorableMDPT(Long studentId,
			Long studentPerformanceTestId) {
		if(studentReportDao.getNonScorableMDPTForSCCodes(studentId,
				studentPerformanceTestId)>0){
			return true;
		}
		return false;
	}


	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentReportsForDynamicBundleReport(
			List<Long> subjectIds, List<Long> schoolIds, List<Long> gradeIds,Long assessmentprogramId,
			String assessmentProgramCode, Long schoolYear, String sort1,
			String sort2, String sort3) {
		
		String reportType="";
		if(assessmentProgramCode!=null && "CPASS".equals(assessmentProgramCode))
			reportType = dbCPASSStudentReportsImportReportType;
		else if(assessmentProgramCode!=null && "DLM".equals(assessmentProgramCode))
			reportType = dbDLMStudentReportsImportReportType;
		
		if("KAP".equalsIgnoreCase(assessmentProgramCode) || "KELPA2".equalsIgnoreCase(assessmentProgramCode)){		   
			return studentReportDao.getStudentReportsForDynamicBundleReport(subjectIds, schoolIds, gradeIds, assessmentprogramId, assessmentProgramCode
				                                , schoolYear, sort1, sort2, sort3);
		}else{
			return studentReportDao.getExternalStudentReportsForDynamicBundleReport(subjectIds, schoolIds, gradeIds, assessmentprogramId, assessmentProgramCode
                    , schoolYear, sort1, sort2, sort3, reportType);
		}
	}


	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getStudentsForReportProcessByStudentId(Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Long schoolYear, Long testingProgramId) {
		return studentReportDao.getStudentsForReportProcessByStudentId(assessmentProgramId, contentAreaId, gradeId, schoolYear, testingProgramId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteOrganizationBundleReportsByOrganization(
			Long attendanceSchoolId, Long assessmentProgramId, Long schoolYear, String reportType){
		organizationReportDetailsMapper.deleteOrganizationBundleReportsByOrganization(assessmentProgramId, schoolYear, attendanceSchoolId, reportType);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateStudentReportReprocessByStudentId(StudentReport record){
		return studentReportDao.updateStudentReportReprocessByStudentId(record);
	}



	@Override
	public void deleteOrganizationBundleReportFilesByOrganization(Long attendanceSchoolId, Long assessmentProgramId, Long schoolYear, String reportType) {
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.OrganizationBundleReportFilesByOrganization(assessmentProgramId, schoolYear, attendanceSchoolId, reportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
		}
		
	}


	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateStudentReportReprocessByStudentIdByIsrOption(Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, String isrByStudentIdOption, Long testingProgramId){
		return studentReportDao.updateStudentReportReprocessByStudentIdByIsrOption(assessmentProgramId, contentAreaId, gradeId, schoolYear, "2".equals(isrByStudentIdOption) ? "ALL" : "ISR", testingProgramId);
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int UpdateStudentReportReprocessStatusById(Long studentReportReprocessId,Boolean studentReportReprocessStatus){
		return studentReportDao.UpdateStudentReportReprocessStatus(studentReportReprocessId,studentReportReprocessStatus);
	}

	@Override
	public Integer getCountOfStudentReports(List<Long> subjectIds, List<Long> schoolIds, List<Long> gradeIds,Long assessmentprogramId,
			String assessmentProgramCode, Long schoolYear, String sort1,
			String sort2, String sort3) {
		
		String reportType="";
		if(assessmentProgramCode!=null && "CPASS".equals(assessmentProgramCode))
			reportType = dbCPASSStudentReportsImportReportType;
		else if(assessmentProgramCode!=null && "DLM".equals(assessmentProgramCode))
			reportType = dbDLMStudentReportsImportReportType;
		
		if("KAP".equalsIgnoreCase(assessmentProgramCode) || "KELPA2".equalsIgnoreCase(assessmentProgramCode)){		   
			return studentReportDao.getCountOfStudentReports(subjectIds, schoolIds, gradeIds, assessmentprogramId, assessmentProgramCode
				                                , schoolYear, sort1, sort2, sort3);
		}else{
			return studentReportDao.getCountOfExternalStudentReports(subjectIds, schoolIds, gradeIds, assessmentprogramId, assessmentProgramCode
                    , schoolYear, sort1, sort2, sort3, reportType);
		}
	}

	@Override
	public void deleteAllStudentSummaryBundleReportFiles(Long assessmentProgramId, Long schoolYear, Long organizationId,
			String reportType) {		
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.getAllStudentSummaryBundledReportsByAssessmentProgramIdForUserState(assessmentProgramId, schoolYear, organizationId, reportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
		}
		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllStudentSummaryBundleReports(Long assessmentProgramId, Long schoolYear, Long organizationId,
			String reportType) {		
		organizationReportDetailsMapper.deleteAllStudentSummaryBundleReportsForUserState(assessmentProgramId, schoolYear, organizationId, reportType);		
	}
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteOrganizationReportsByOrganizationIdReportType(Long assessmentProgramId, Long schoolYear, Long stateId,List<String> reportTypes){
		organizationReportDetailsMapper.deleteOrganizationReportsByOrganizationIdReportType(assessmentProgramId, schoolYear, stateId,reportTypes);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getSchoolIdsForStudentSummaryBundledReports(Long stateId, Long assessmentProgramId,
			Long currentSchoolYear, String gradeCourseAbbrName, String reportType, Integer offset, Integer pageSize) {
		return studentReportDao.getSchoolIdsForStudentSummaryBundledReports(stateId, assessmentProgramId, currentSchoolYear, gradeCourseAbbrName, reportType, offset, pageSize);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<String> geGradeCoursesAbbrNamesForStudentSummaryBundledReports(Long stateId, Long assessmentProgramId, int reportYear, String reportType) {
			return studentReportDao.geGradeCoursesAbbrNamesForStudentSummaryBundledReports(stateId, assessmentProgramId, reportYear, reportType);		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistrictIdsForStudentSummaryBundledReports(Long stateId, Long assessmentProgramId,
			Long currentSchoolYear, String reportType, Integer offset, Integer pageSize) {
		return studentReportDao.getDistrictIdsForStudentSummaryBundledReports(stateId, assessmentProgramId, currentSchoolYear, reportType, offset, pageSize);
	}

	@Override
	public List<StudentReport> getExternalStudentReportsForStudentSummaryBundledReport(Long assessmentProgramId,
			String gradeCourseAbbrName, Long schoolYear, Long attendanceSchoolId, String reportType) {
		return studentReportDao.getExternalStudentReportsForStudentSummaryBundledReport(assessmentProgramId, gradeCourseAbbrName, schoolYear, attendanceSchoolId, reportType);
	}

	@Override
	public List<StudentReport> getExternalStudentReportsForStudentSummaryDistrictBundledReport(Long assessmentProgramId,
			Long schoolYear, String assessmentProgramCode, Long districtId, String reportType) {
		return studentReportDao.getExternalStudentReportsForStudentSummaryDistrictBundledReport(assessmentProgramId, schoolYear, assessmentProgramCode, districtId, reportType);
	}
	
	@Override
	public void deleteAllDistrictBundleReportFilesForStudentSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType) {
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.getAllDistrictLevelStudentSummaryBundleReports(assessmentProgramId, schoolYear, organizationId, reportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllDistrictBundleReportsForStudentSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType) {		
		organizationReportDetailsMapper.deleteAllDistrictBundleStudentSummaryReports(assessmentProgramId, schoolYear, organizationId, reportType);
	}

	@Override
	public void deleteAllDistrictBundleReportFilesForSchoolSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType) {
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.getAllDistrictLevelSchoolSummaryBundleReports(assessmentProgramId, schoolYear, organizationId, reportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllDistrictBundleReportsForSchoolSummary(Long assessmentProgramId, Long schoolYear, Long organizationId, String reportType) {		
		organizationReportDetailsMapper.deleteAllDistrictBundleSchoolSummaryReports(assessmentProgramId, schoolYear, organizationId, reportType);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getDistrictIdsForSchoolSummaryBundledReports(Long stateId, Long assessmentProgramId,
			Long currentSchoolYear, String reportType, Integer offset, Integer pageSize) {
		return organizationReportDetailsMapper.getDistrictIdsForSchoolSummaryBundledReports(stateId, assessmentProgramId, currentSchoolYear, reportType, offset, pageSize);
	}
	
	@Override
	public List<OrganizationReportDetails> getExternalSchoolReportsForDistrictBundledReport(Long assessmentProgramId,
			Long schoolYear, String assessmentProgramCode, Long districtId, String reportType) {
		return organizationReportDetailsMapper.getSchoolReportsForSchoolSummaryDistrictBundledReport(assessmentProgramId, schoolYear, assessmentProgramCode, districtId, reportType);
	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertOrganizationSummaryReportFiles(OrganizationReportDetails orgReportDetails) {
		return organizationReportDetailsMapper.insertSelective(orgReportDetails);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationReportDetails> getAllDistrictReportsPdfFileByStateId(@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolYear") Long schoolYear, @Param("stateId") Long stateId,@Param("reportTypes") List<String> reportTypes){		
		return organizationReportDetailsMapper.getAllDistrictReportsPdfFileByStateId(assessmentProgramId, schoolYear, stateId, reportTypes);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> getStudentSummaryReportsForDynamicBundleReport(List<Long> schoolIds, List<Long> gradeIds,Long assessmentprogramId,
			String assessmentProgramCode, Long schoolYear, String sort1, String sort2, String sort3, String reportType) {
		
		return studentReportDao.getExternalStudentSummaryReportsForDynamicBundleReport(schoolIds, gradeIds, assessmentprogramId, assessmentProgramCode
                    , schoolYear, sort1, sort2, sort3, reportType);
		
	}
	
	@Override
	public Integer getCountOfStudentSummaryReports(List<Long> schoolIds, List<Long> gradeIds,Long assessmentprogramId,
			String assessmentProgramCode, Long schoolYear, String sort1, String sort2, String sort3, String reportType) {
		
		return studentReportDao.getCountOfExternalStudentSummaryReports(schoolIds, gradeIds, assessmentprogramId, assessmentProgramCode
                    , schoolYear, sort1, sort2, sort3, reportType);		
	}

	@Override
	public void deleteAllPredictiveReportFiles(Long assessmentProgramId, String reportCycle, Long gradeId, Long contentAreaId, Long studentId, Long schoolYear) {
		
		List<InterimStudentReport> studentReportDetails = getAllInterimStudentReports(assessmentProgramId, gradeId, contentAreaId, schoolYear, reportCycle, studentId);
		
		if(CollectionUtils.isNotEmpty(studentReportDetails)){
			for(InterimStudentReport studentReportDetail : studentReportDetails){
				if(studentReportDetail.getFilePath() != null && studentReportDetail.getFilePath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + studentReportDetail.getFilePath()))
						s3.deleteObject(REPORT_PATH + studentReportDetail.getFilePath());
				}
			}
		}
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteInterimStudentReports(Long assessmentProgramId, String reportCycle, Long contentAreaId,
			Long gradeId, Long schoolYear, Long studentId) {		
		return interimStudentReportMapper.deleteInterimStudentReports(assessmentProgramId, reportCycle, contentAreaId, gradeId, schoolYear, studentId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deleteStudentReportQuestionInfo(Long assessmentProgramId, String reportCycle, Long contentAreaId,
			Long gradeId, Long schoolYear, Long studentId) {
		return studentReportQuestionInfoMapper.deleteStudentReportQuestionInfo(assessmentProgramId, reportCycle, contentAreaId, gradeId, schoolYear, studentId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<InterimStudentReport> getAllInterimStudentReports(Long assessmentProgramId, Long gradeId, Long contentAreaId,
			Long schoolYear, String reportCycle, Long studentId) {
		return interimStudentReportMapper.selectAllInterimStudentReports(assessmentProgramId, gradeId, contentAreaId, schoolYear, reportCycle, studentId);
	}

	@Override
	public List<TestingCycle> getCurrentTestCycleDetails(Long assessmentProgramId, Long schoolYear, Long testingProgramId,
			String testingCycle) {
		return testingCycleMapper.getCurrentTestCycleDetails(assessmentProgramId, schoolYear, testingProgramId, testingCycle);
	}

	@Override
	public List<Long> getContentAreaIdsFromInterimStudentReport(
			Long assessmentProgramId, Long schoolYear, String reportCycle,
			Long testingProgramId) {		
		return interimStudentReportMapper.getContentAreaIdsFromInterimStudentReport(assessmentProgramId,schoolYear, reportCycle, testingProgramId);
	}
	
	@Override
	public List<Long> getGradeIdsFromInterimStudentReport(
			Long assessmentProgramId, Long schoolYear, Long contentAreaId, String reportCycle, Long testingProgramId) {
		return interimStudentReportMapper.getGradeIdsFromInterimStudentReport(assessmentProgramId, schoolYear, contentAreaId, reportCycle, testingProgramId);
	}
	
	@Override
	public List<Long> getInterimStudentIdsForReportGeneration(Long gradeId,
			Long contentAreaId, Long assessmentProgramId, Long schoolYear, String reportCycle, Long testingProgramId, 
			String processByStudentId, String reprocessEntireDistrict, int offset,	int pageSize) {
		return interimStudentReportMapper.getInterimStudentIdsForReportGeneration(gradeId, contentAreaId, assessmentProgramId, schoolYear, reportCycle, testingProgramId, processByStudentId, reprocessEntireDistrict, offset, pageSize);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<InterimStudentReport> getStudentsForPredictiveReportProcess(Long studentId, Long contractOrgId, Long assessmentId, Long assessmentProgramId, String assessmentProgramCode, 
			Long gradeId, Long contentAreaId, Long currentSchoolYear, List<Long> rawScaleExternalTestIds, List<Long> testsStatusIds, List<Long> studentIdList, 
			Long testingProgramId, String reportCycle, Integer pageSize, Integer offset, Date jobStartTime) {
		return studentDao.getStudentsForPredictiveReportProcess(studentId, contractOrgId, assessmentId, assessmentProgramId,assessmentProgramCode, gradeId, contentAreaId, 
				currentSchoolYear, rawScaleExternalTestIds, testsStatusIds, studentIdList, testingProgramId, reportCycle, pageSize, offset, jobStartTime);
	}
	
	@Override
	public List<InterimStudentReport> getInterimStudentsForReportGeneration(
			Long assessmentProgramId, Long gradeId, Long contentAreaId,
			Long studentId, Long schoolYear, Long testingProgramId, String processByStudentId) {
		return interimStudentReportMapper.getInterimStudentsForReportGeneration(assessmentProgramId, gradeId, contentAreaId, studentId, schoolYear, testingProgramId, processByStudentId);
	}

	@Override
	public List<TestingCycle> getTestingCyclesBySchoolYear(
			Long assessmentProgramId, Long schoolYear, Long testingProgramId) {
		return testingCycleMapper.getTestingCyclesByProgramIdSchoolYear(assessmentProgramId, schoolYear, testingProgramId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateInterimStudentReportFilePath(
			InterimStudentReport interimStudentReport) {
		interimStudentReportMapper.updateInterimStudentReportFilePath(interimStudentReport);		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertInterimPreditiveStudentReport(InterimStudentReport record) {
		return interimStudentReportMapper.insertSelective(record);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertPredictiveStudentReportQuestionInfo(StudentReportQuestionInfo record) {
		return studentReportQuestionInfoMapper.insertSelective(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updatePreditviceStudentReportReprocessByStudentId(InterimStudentReport record) {
		return studentReportDao.updatePredictiveStudentReportByStudentId(record);
	}
	
	@Override
	public List<TestingCycle> getTestingCyclesByProgramIdSchoolYear(Long assessmentProgramId, Long schoolYear, Long testingProgramId) {
		return testingCycleMapper.getTestingCyclesByProgramIdSchoolYear(assessmentProgramId, schoolYear, testingProgramId);
	}

	@Override
	public TestingCycle getTestingCycleByTestingProgramId(Long testingProgramId) {
		return testingCycleMapper.selectByPrimaryKey(testingProgramId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentReportReprocessByStudentIdByIsrOption(Long studentId, Long assessmentProgramId, Long contentAreaId, Long gradeId, Long schoolYear, String isrByStudentIdOption, Long testingProgramId){
		 studentReportDao.updateStudentReportReprocessByStudentByIsrOption(studentId, assessmentProgramId, contentAreaId, gradeId, schoolYear, "2".equals(isrByStudentIdOption) ? "ALL" : "ISR", testingProgramId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<PredictiveReportOrganization> getAllOrgsFromInterimStudentReport(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long schoolYear, Long contentAreaId, Long gradeId,
													Integer offset, Integer pageSize) { 
		return interimStudentReportMapper.selectAllOrgsFromInterimStudentReport(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeId, offset, pageSize);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<PredictiveReportCreditPercent> getQuestionCreditPercentCountByOrganizatonId(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long schoolYear, Long contentAreaId, 
			Long gradeId, Long testId, Long organizationId, String orgTypeCode, Long creditTypeId, List<Long> testsStatusIds){
		
		return predictiveReportCreditPercentMapper.getQuestionCreditPercentCountByOrganizatonId(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeId, testId, organizationId, orgTypeCode, creditTypeId, testsStatusIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getTestAttemptedStudentCount(Long assessmentProgramId, Long testingProgramId, String reportCycle,
			Long schoolYear, Long contentAreaId, Long gradeId, Long organizationId, String orgTypeCode,
			Long externalTestId, List<Long> testsStatusIds) {
		
		return interimStudentReportMapper.getTestAttemptedStudentCount(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeId, organizationId, orgTypeCode, externalTestId, testsStatusIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getUnAnsweredStudentCount(Long assessmentProgramId, Long testingProgramId, String reportCycle,
			Long schoolYear, Long contentAreaId, Long gradeId, Long organizationId, String orgTypeCode,
			Long externalTestId) {
		
		return interimStudentReportMapper.getUnAnsweredStudentCount(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeId, organizationId, orgTypeCode, externalTestId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertPredictiveReportCreditPercent(PredictiveReportCreditPercent predictiveReportCreditPercent) {
		
		return predictiveReportCreditPercentMapper.insertSelective(predictiveReportCreditPercent);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deletePredictiveSchoolDistrictSummaryCalculations(Long assessmentProgramId, Long testingProgramId,
			String reportCycle, Long schoolYear, Long contentAreaId, Long gradeId) {
		
		return predictiveReportCreditPercentMapper.deleteAllSchoolDistrictSummaryCalculations(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeId);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public PredictiveReportCreditPercent selectByTestIdandOrganizationId(String orgTypeCode,Long currentSchoolYear,Long assessmentProgramId,Long testSessionId){		
		return predictiveReportCreditPercentMapper.selectByTestIdandOrganizationId(orgTypeCode, currentSchoolYear, assessmentProgramId, testSessionId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateAllExternalStudentReportFilePath(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, String reportCycle, Long testingProgramId,Long organizationId, String reportType, Long userId) {
		externalstudentreportsMapper.updateAllExternalStudentReportFilePath(assessmentProgramId, subjectId, gradeId, schoolYear, reportCycle, testingProgramId, organizationId,reportType, userId);		
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllExternalStudentReportFiles(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long organizationId, Long schoolYear, String reportCycle,Long testingProgramId, String reportType) {		
		List<ExternalStudentReportDTO> studentReportDetails = getAllExternalStudentReports(assessmentProgramId, gradeId, contentAreaId, organizationId, schoolYear, reportCycle, testingProgramId, reportType);		
		if(CollectionUtils.isNotEmpty(studentReportDetails)){
			for(ExternalStudentReportDTO studentReportDetail : studentReportDetails){
				if(studentReportDetail.getFilePath() != null && studentReportDetail.getFilePath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + studentReportDetail.getFilePath()))
						s3.deleteObject(REPORT_PATH + studentReportDetail.getFilePath());
				}
			}
		}
		
	}
	

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ExternalStudentReportDTO> getAllExternalStudentReports(Long assessmentProgramId, Long gradeId, Long contentAreaId, Long organizationId, Long schoolYear, String reportCycle, Long testingProgramId, String reportType) {
		return externalstudentreportsMapper.selectAllExternalStudentReports(assessmentProgramId, gradeId, contentAreaId, organizationId, schoolYear, reportCycle, testingProgramId, reportType);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteExternalSchoolDetailReports(Long assessmentProgramId,
			Long stateId, Long subjectId, Long gradeId, Long schoolYear,
			String reportCycle, Long testingProgramId,
			String cpassSchoolDetailsReportType, Long userId) {
		
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.getOrganizationReportDetailByReportCycle(stateId, assessmentProgramId, subjectId, gradeId, schoolYear, testingProgramId, reportCycle, cpassSchoolDetailsReportType);
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
			}
			organizationReportDetailsMapper.deleteOrganizationReportDetailForCpassReport(stateId, assessmentProgramId, subjectId, schoolYear, gradeId, testingProgramId, reportCycle, cpassSchoolDetailsReportType);
		}		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllOrganizationReportsOnGRFUpload(Long stateId, Long reportYear,
			Long assessmentProgramId) {
		List<OrganizationReportDetails> organizationReportDetails = organizationReportDetailsMapper.getOrganizationReportDetails(assessmentProgramId, reportYear, stateId);
		
		if(CollectionUtils.isNotEmpty(organizationReportDetails)){
			for(OrganizationReportDetails organizationReportDetail : organizationReportDetails){
				if(organizationReportDetail.getDetailedReportPath() != null && organizationReportDetail.getDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getDetailedReportPath());
				}
				
				if(organizationReportDetail.getCsvDetailedReportPath() != null && organizationReportDetail.getCsvDetailedReportPath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + organizationReportDetail.getCsvDetailedReportPath()))
						s3.deleteObject(REPORT_PATH + organizationReportDetail.getCsvDetailedReportPath());
				}
			}
			organizationReportDetailsMapper.deleteAllOrganizationReportDetails(assessmentProgramId, reportYear, stateId);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void deleteAllStudentReportsOnGRFUpload(Long stateId,
			Long reportYear, Long assessmentProgramId) {		
		List<ExternalStudentReportDTO> studentReportDetails = externalstudentreportsMapper.selectAllExternalStudentReports(assessmentProgramId, null, null, stateId, reportYear, null, null, null);
		if(CollectionUtils.isNotEmpty(studentReportDetails)){
			for(ExternalStudentReportDTO studentReportDetail : studentReportDetails){
				if(studentReportDetail.getFilePath() != null && studentReportDetail.getFilePath().length() > 0){
					if (s3.doesObjectExist(REPORT_PATH + studentReportDetail.getFilePath()))
						s3.deleteObject(REPORT_PATH + studentReportDetail.getFilePath());
				}
			}			
			externalstudentreportsMapper.deleteByStateSchoolYearAssessmentProgramReportType(assessmentProgramId, reportYear, stateId, null);
		}
	}

	@Override
	public GrfStateApproveAudit getGRFRecentStatus(Long stateId,
			Long assessmentProgramId, Long reportYear) {
		return grfStateApproveAuditMapper.getByStateAndReportYear(stateId, assessmentProgramId, reportYear);
	}


	// test
	@Override
	public Long getStudentsCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType) {
		return organizationReportDetailsMapper.getStudentsCountOfDistrictlLevel(stateId, assessmentProgramId, reportYear,reportType);
	}
	
	@Override
	public Long getexternelStudentsCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType) {
		return organizationReportDetailsMapper.getexternelStudentsCountOfDistrictlLevel(stateId, assessmentProgramId, reportYear,reportType);
	}

	@Override
	public String getStateName(Long stateId) {
		return organizationReportDetailsMapper.getStateName(stateId);
	}

	@Override
	public Long getStudentsCountOfSchoolLevel(Long stateId, Long assessmentProgramId, Long reportYear,
			String reportType) {
		return organizationReportDetailsMapper.getStudentsCountOfSchoolLevel(stateId, assessmentProgramId, reportYear,reportType);
	}

	@Override
	public Long getexternelStudentsCountOfSchoolLevel(Long stateId, Long assessmentProgramId, Long reportYear,
			String reportType) {
		return organizationReportDetailsMapper.getexternelStudentsCountOfSchoolLevel(stateId, assessmentProgramId, reportYear,reportType);
	}

	@Override
	public List<Long> getActLevelsForActScoring() {
		return studentDao.getActLevelsForActScoring();
	}
	
	@Override
	public Long getSchoolSummaryBundleCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType) {
		return organizationReportDetailsMapper.getSchoolSummaryBundleCountOfDistrictlLevel(stateId, assessmentProgramId, reportYear,reportType);
	}
	
	@Override
	public Long getSchoolSummaryCountOfDistrictlLevel(Long stateId, Long assessmentProgramId, Long reportYear, String reportType) {
		return organizationReportDetailsMapper.getSchoolSummaryCountOfDistrictlLevel(stateId, assessmentProgramId, reportYear,reportType);
	}

}