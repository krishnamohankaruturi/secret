package edu.ku.cete.dataextract.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.dataextract.service.DataExtractService;
import edu.ku.cete.dataextracts.model.DataExtractsMapper;
import edu.ku.cete.dataextracts.model.DataExtractsSupportMapper;
import edu.ku.cete.dataextracts.model.YearOverYearReportMapper;
import edu.ku.cete.dlm.iti.BPCriteriaAndGroups;
import edu.ku.cete.dlm.iti.BPGroupsInfo;
import edu.ku.cete.domain.BluePrintCriteriaDescription;
import edu.ku.cete.domain.UploadGrfFile;
import edu.ku.cete.domain.UploadIncidentFile;
import edu.ku.cete.domain.UploadScCodeFile;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.warehouse.model.HistoricDataMapper;
import edu.ku.cete.web.DLMTestStatusExtractDTO;
import edu.ku.cete.web.FCSAnswer;
import edu.ku.cete.web.FCSDataExtractDTO;
import edu.ku.cete.web.FCSHeader;
import edu.ku.cete.web.ISMARTTestAdminExtractDTO;
import edu.ku.cete.web.ITIBPCoverageExtractRostersDTO;
import edu.ku.cete.web.ITIBPCoverageRosteredStudentsDTO;
import edu.ku.cete.web.KAPStudentScoreDTO;
import edu.ku.cete.web.KELPATestAdministrationDTO;
import edu.ku.cete.web.MonitorScoringExtractDTO;
import edu.ku.cete.web.OrganizationExtractDTO;
import edu.ku.cete.web.PNPAbridgedExtractDTO;
import edu.ku.cete.web.RosterExtractDTO;
import edu.ku.cete.web.StudentExitExtractDTO;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;
import edu.ku.cete.web.StudentReportDTO;
import edu.ku.cete.web.StudentSpecialCircumstanceDTO;
import edu.ku.cete.web.StudentTestDTO;
import edu.ku.cete.web.StudentTestSessionInfoDTO;
import edu.ku.cete.web.TECExtractDTO;
import edu.ku.cete.web.TestFormAssignmentsInfoDTO;
import edu.ku.cete.web.TestFormMediaResourceDTO;
import edu.ku.cete.web.TestingReadinessEnrollSubjects;
import edu.ku.cete.web.TestingReadinessExtractDTO;
import edu.ku.cete.web.UserDetailsAndRolesDTO;
import edu.ku.cete.web.UserSecurityAgreemntDTO;
import edu.ku.cete.web.ViewStudentDTO;
import edu.ku.cete.web.KELPA2StudentScoreDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class DataExtractServiceImpl implements DataExtractService {	
	
	@Autowired
    private OrganizationDao organizationDao;
	
	@Autowired
    private YearOverYearReportMapper yearOverYearReportMapper;
	
	@Autowired
    private DataExtractsSupportMapper dataExtractsSupportMapper;
	
	@Autowired
    private DataExtractsMapper dataExtractsMapper;
	
	@Autowired
    private HistoricDataMapper historicDataMapper;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KAPStudentScoreDTO> generateKAPStudentScoreExtract(Long orgId, String orgType, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeIds, String includeSubscores, Long currentSchoolYear, String stateStudentIdentifier){
		List<KAPStudentScoreDTO> kapStudentScores = null;
		kapStudentScores = yearOverYearReportMapper.generateKAPStudentScoreExtract(orgId, orgType, contentAreaId, schoolYears, gradeIds, includeSubscores, currentSchoolYear, stateStudentIdentifier);
		return kapStudentScores;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> selectStudentReportsByStudentIdSubjSchYrGrade(String stateStudentIdentifier, List<Long> contentAreaId, List<Long> schoolYears, List<Long> gradeIds){
		List<StudentReport> kapStudentReports = null;
		kapStudentReports = yearOverYearReportMapper.selectStudentReportsByStudentIdSubjSchYrGrade(stateStudentIdentifier, contentAreaId, schoolYears, gradeIds);
		return kapStudentReports;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getStudentReportCountByStudentIdSubject(String stateStudentIdentifier, List<Long> contentAreaId){
		return yearOverYearReportMapper.getStudentReportCountByStudentIdSubject(stateStudentIdentifier, contentAreaId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getStudentReportCountByStudentIdSubjectUserOrg(String stateStudentIdentifier, List<Long> contentAreaId, Long orgId, String orgType){
		return yearOverYearReportMapper.getStudentReportCountByStudentIdSubjectUserOrg(stateStudentIdentifier, contentAreaId, orgId, orgType);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization get(final Long organizationId) {
        return organizationDao.get(organizationId);
    }
	
	
	@Override
	public int getEnrollmentCountBySsidOrgId(String stateStudentIdentifier, Long orgId, int currentSchoolYear, String orgType){
		return yearOverYearReportMapper.getBySsidAndUserOrgId(stateStudentIdentifier, orgId, currentSchoolYear, orgType);
	}

	
	//Supportive queries in the extracts
	//dataExtractService.getOrganization
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Organization getOrganization(final Long organizationId) {
        return dataExtractsSupportMapper.get(organizationId);
    }
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getAllChildrenByType(Long requestedOrgId, String type){
		return dataExtractsSupportMapper.getAllChildrenByType(requestedOrgId, type);
	}
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getAllChildrenIdsByType(Long organizationId, String organizationTypeCode){
		return dataExtractsSupportMapper.getAllChildrenIdsByType(organizationId, organizationTypeCode);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getAllChildrenWithParentByType(Long organizationId, String organizationTypeCode){
		return dataExtractsSupportMapper.getAllChildrenWithParentByType(organizationId, organizationTypeCode);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getAllParents(long orgId){
		return dataExtractsSupportMapper.getAllParents(orgId);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getAllChildrenOrgIds(Long organizationId) {
		return dataExtractsSupportMapper.getAllChildrenOrgIds(organizationId);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Student findByStudentId(long studentId) {
        return dataExtractsSupportMapper.findById(studentId);
    }
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public AssessmentProgram findByAssessmentProgramId(long assessmentProgramId){
		return dataExtractsSupportMapper.findByAssessmentProgramId(assessmentProgramId);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public AssessmentProgram findAssessmentProgramByStudentId(Long id, User user) {
		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		return dataExtractsSupportMapper.findByStudentId(id, currentSchoolYear);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public AssessmentProgram findByAbbreviatedName(String abbreviatedName) {
		return dataExtractsSupportMapper.findByAbbreviatedName(abbreviatedName.trim());
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AssessmentProgram> getAllAssessmentProgramByUserId(Long userId){
		return dataExtractsSupportMapper.getAllAssessmentProgramByUserId(userId);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public User getActiveOrInactiveUser(long id) {
		return dataExtractsSupportMapper.getActiveOrInactiveUser(id);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long getCategoryId(String categoryCode, String categoryTypeCode){
		return dataExtractsSupportMapper.getCategoryId(categoryCode, categoryTypeCode);
	}
	
	//Case 2: Enrollment Extract
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getViewStudentInformationRecordsExtract(Long orgId, boolean shouldOnlySeeRosteredStudents, Long userId, int currentSchoolYear, List<Long> assessmentPrograms){
		return dataExtractsMapper.getViewStudentInformationRecordsExtract(
				orgId,
				shouldOnlySeeRosteredStudents,
				userId,currentSchoolYear,assessmentPrograms);
	}	

	//case 3: PNP Settings
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getActiveStudentIdsWithPNPEnrolledInOrg(
			Long orgId, boolean onlyRostered,
			Long teacherId, int limit, int offset, int currentSchoolYear, List<Long> assessmentProgramIds) {
		return dataExtractsMapper.getActiveStudentIdsWithPNPInOrg(orgId, onlyRostered, teacherId, currentSchoolYear, limit, offset, assessmentProgramIds);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getCurrentEnrollmentsByStudentId(Long studentId, Long orgId, int currentSchoolYear){
		return dataExtractsMapper.getCurrentEnrollmentsByStudentId(studentId, orgId,currentSchoolYear);
	}
	
	//case 4: Roster Extract
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<RosterExtractDTO> getRosterDataExtractForOrg(Long orgId, boolean shouldOnlySeeRosteredStudents, Long userId, int currentSchoolYear, List<Long> assessmentPrograms){
		return dataExtractsMapper.getRosterDataExtractForOrg(orgId, shouldOnlySeeRosteredStudents, userId, currentSchoolYear,assessmentPrograms);
	}
	
	
	//Case 5: User Extract 
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<UserDetailsAndRolesDTO> getUserDetailsAndRolesByOrgId(Long orgId, List<Long> assessmentPrograms, boolean includeInternalUsers){
		return dataExtractsMapper.getUserDetailsAndRolesByOrgId(orgId,assessmentPrograms,includeInternalUsers);
	}
	
	//Case 6: Questar 
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentProfileItemAttributeDTO> selectStudentAttributesAndContainers(Long studentId, User user) {

		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		//Get all attribute names, container names for a particular student along with all attributes/containers from dictionary.
		return dataExtractsMapper.selectAllAttributesDataAndStudentSelection(studentId, currentSchoolYear,user.getCurrentAssessmentProgramId());

	}
	
	//case 7: TEC	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TECExtractDTO> getTECExtractByOrg(Long orgId, boolean shouldOnlySeeRosteredStudents, Long userId, int currentSchoolYear, List<Long> assessmentPrograms){
		return dataExtractsMapper.getTECExtractByOrg(orgId, shouldOnlySeeRosteredStudents, userId, currentSchoolYear,assessmentPrograms);
	}
	
	//case 8: DLM_TEST_STATUS
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DLMTestStatusExtractDTO> dlmTestStatusReport(Long orgId, boolean shouldOnlySeeRosteredStudents, 
			Long userId, int currentSchoolYear, Date iniToDate, Date iniFromDate, Date eoyToDate, Date eoyFromDate, Long userOrgId){
		return dataExtractsMapper.dlmTestStatusReport(orgId, shouldOnlySeeRosteredStudents, 
				userId, currentSchoolYear, iniToDate, iniFromDate, eoyToDate, eoyFromDate, userOrgId);
	}
	
	//case 10: PNP_SUMMARY
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getAllStudentIdsByOrgIdAssessmentProgram(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId, int currentSchoolYear, String assessmentProgramCode, int offset, int limit){
		return dataExtractsMapper.getAllStudentIdsByOrgIdAssessmentProgram(orgId, onlyRostered, teacherId, currentSchoolYear, assessmentProgramCode, offset,limit);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getNonDLMStudentIdsEnrolledInOrg(UserDetailImpl userDetails, Long orgId, boolean onlyRostered, Long teacherId,int currentSchoolYear, int offset, int limit, List<Integer> assessmentPrograms, List<Long> alternateAssessmentProgramIds) {
		return dataExtractsMapper.getNonDLMStudentIdsInOrg(orgId, onlyRostered, teacherId, currentSchoolYear,offset,limit, assessmentPrograms, alternateAssessmentProgramIds);
	}

	//10. case 13: TEST_ADMIN_KAP_AMP (KAP Test Administration Monitoring)
	//11. case 14: Test Tickets for KAP
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentTestSessionInfoDTO> getStudentsTestsInfo(Long organionId, Long currentSchoolYear, Boolean isTeacher, Long educatorId, 
    		String assessmentProgramCode, int offset, int limit, List<Long> assessmentPrograms,boolean isPltw){
		return dataExtractsMapper.getStudentsTestsInfo(organionId, currentSchoolYear, 
				isTeacher, educatorId, assessmentProgramCode, offset, limit, assessmentPrograms ,isPltw);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentTestDTO> getAllStudentsTestSectionDetails(List<Long> studentTestIds){
		return dataExtractsMapper.getAllStudentsTestSectionDetails(studentTestIds);
	}
	
	
	//12. case 19: // Test Form Assignments to Test Collections
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestFormAssignmentsInfoDTO> getTestFormAssignmentsExtracts(List<Long> assessmentPrograms, String qcCompleteStatus, String beginDateStr, String toDateStr){
		return dataExtractsMapper.getTestFormAssignmentsExtracts(assessmentPrograms, qcCompleteStatus, beginDateStr, toDateStr);
	}
	
	//13. case 20:  Test Media Resource
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestFormMediaResourceDTO> getTestFormMediaResource(String fromDate, String toDate, List<Long> assessmentPrograms, String qcStatus, String media){
		return dataExtractsMapper.getTestFormMediaResource(fromDate, toDate, assessmentPrograms, qcStatus,media);
	}
	
	//14. case 25: Student Login Usernames/Passwords
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ViewStudentDTO> getKAPStudentUserNamePasswordExtract(Long orgChildrenById, boolean isTeacher, Long educatorId, Integer currentSchoolYear, Long gradeId, Long subjectId){
		return dataExtractsMapper.getKAPStudentUserNamePasswordExtract(orgChildrenById, isTeacher, educatorId, currentSchoolYear, gradeId, subjectId);
	}
	
	//15. case 26: //Restricted Special Circumstance Code
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentSpecialCircumstanceDTO> getStudentSpecialCircumstanceInfo(Long orgChildrenById, Integer currentSchoolYear, List<Long> assessmentPrograms) {
		return dataExtractsMapper.getStudentSpecialCircumstanceInfo(orgChildrenById, currentSchoolYear, assessmentPrograms);
	}
	
	//17. case 35: //Security Agreement Completion
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<UserSecurityAgreemntDTO> getUserSecurityAgreementDetails(List<Long> organizationIds, List<Long> assessmentPrograms, Long currentSchoolYear, boolean includeInternalUsers){
		return dataExtractsMapper.getUserSecurityAgreementDetails(organizationIds,assessmentPrograms, currentSchoolYear,includeInternalUsers);
	}
	
	//18. case 36: //KELPA2 Test Administration Monitoring
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KELPATestAdministrationDTO> getKELPATestAdministrationExtract(Long orgId, Long currentSchoolYear, Long assessmentProgramId, int offset, int limit) {
		return dataExtractsMapper.getKELPATestAdministrationExtract(orgId, currentSchoolYear, assessmentProgramId, offset, limit);
	}
	
	//19. case 37: //Monitor Scoring
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<MonitorScoringExtractDTO> getMonitorScoringExtractByOrg(Long orgId, List<Long> assessmentPrograms, Long currentSchoolYear, int offset, int limit){
		return dataExtractsMapper.getMonitorScoringExtractByOrg(orgId, assessmentPrograms, currentSchoolYear, offset, limit);
	}
	
	//21. case 39: //DLM Blueprint Coverage Summary
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ITIBPCoverageExtractRostersDTO> getrosterDetalsGroupByTeacherForITIBP(Long orgId, Long gradeId, 
			Long subjectId, int currentSchoolYear, boolean isTeacher, Long teacherId){
		return dataExtractsMapper.getrosterDetalsGroupByTeacherForITIBP(orgId, gradeId, subjectId, currentSchoolYear, isTeacher, teacherId);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ITIBPCoverageExtractRostersDTO> getRosterDetailsForITIBluePrintExtract(Long orgId, Long gradeId, Long subjectId, 
			int currentSchoolYear, List<Long> teacherIds){
		return dataExtractsMapper.getRosterDetailsForITIBluePrintExtract(orgId, gradeId, subjectId, currentSchoolYear, teacherIds);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<BluePrintCriteriaDescription> getBluePrintCriteriaDescByGradeAndSub(Long contentAreaId, String gradeCourseAbbrName){
		return dataExtractsMapper.getBluePrintCriteriaDescByGradeAndSub(contentAreaId, gradeCourseAbbrName);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationTreeDetail> getOrganizationDetailByStudentId(Long studentId, Long schoolYear, String subjectCode, Boolean transferred){
		return yearOverYearReportMapper.getOrganizationDetailByStudentId(studentId, schoolYear, subjectCode, transferred);
	}
	
	//dataExtractService.getOrganization
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getNumberOfStudentsMetITIBPCrietria(Long criteria, Long contentAreaId, String gradeCourseAbbrName,
			List<ITIBPCoverageRosteredStudentsDTO> rosteredStudentsDetailsList, int schoolYear, Long operationalTestWindowID) {
		int numberOfStudentsMetCriteria = 0;		
		for(ITIBPCoverageRosteredStudentsDTO itiBPRosteredStudentDTO : rosteredStudentsDetailsList) {
			if(isStudentMetCriteria(criteria, contentAreaId, gradeCourseAbbrName, itiBPRosteredStudentDTO.getStudentId(), 
					schoolYear, operationalTestWindowID)) {
				numberOfStudentsMetCriteria++;
			}
		}
		return numberOfStudentsMetCriteria;
	}
	
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private boolean isStudentMetCriteria(Long criteria, Long contentAreaId, String gradeCourseAbbrName, 
			Long studentId, int schoolYear, Long operationalTestWindowID) {		
		Map<Long, BPCriteriaAndGroups> bpCriteriasMap = new HashMap<Long, BPCriteriaAndGroups>();
		List<BPCriteriaAndGroups> bpCriterias = getCriteriaAndGroupsBySubGradeAndCriteria(criteria, contentAreaId, gradeCourseAbbrName);
		for(BPCriteriaAndGroups bpcriteria: bpCriterias) {
			for(BPGroupsInfo bpGroupInfo : bpcriteria.getGrouspInfos()) {
				bpGroupInfo.resetNumberOfITIEEsCompleted();
			}
			if(!bpCriteriasMap.containsKey(bpcriteria.getCriteriaNum())) {
				bpCriteriasMap.put(bpcriteria.getCriteriaNum(), bpcriteria);
			}
		}
		List<ContentFrameworkDetail> listOfEEsStudentCompleted = dataExtractsMapper.getStudentITITestsForSubGradeAndCriteria(criteria, gradeCourseAbbrName, contentAreaId, studentId, schoolYear, operationalTestWindowID);
		for(ContentFrameworkDetail ee:listOfEEsStudentCompleted) {
			List<BPGroupsInfo> bpGroups = bpCriteriasMap.get(ee.getCriteriaNumber()).getGrouspInfos();
			for(Long groupNum : ee.getGroupsNumbers()) {
				for(BPGroupsInfo bpg :bpGroups) {
					if(bpg.getGroupNumber().equals(groupNum)) {
						bpg.incrementNumberOfITIEEsCompleted();
					}
				}
			}
		}				
		
		if(bpCriteriasMap.get(criteria).isCriteriaRequirmentMetForITI()) {
			return true;
		}
		
		return false;
	}
	
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private List<BPCriteriaAndGroups> getCriteriaAndGroupsBySubGradeAndCriteria(
			Long criteria, Long contentAreaId, String gradeCourseAbbrName) {
		return dataExtractsMapper.getBluePrintCriteriasByGradeAndSubAndCriteria(contentAreaId, gradeCourseAbbrName, criteria);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportDTO> getWritingStudents(Map<String, Object> params) {
		return dataExtractsMapper.selectWritingStudentsByCriteria(params);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportDTO> getWritingResponseForReports(
			Map<String, Object> params) {
		return dataExtractsMapper.selectWritingResponses(params);
	}
	
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<FindEnrollments> findStudentEnrollment(String studentStateId,
			Long stateId,
			Integer currentSchoolYear,
			Long educatorId,
			Boolean isTeacher) {
		return historicDataMapper.findStudentEnrollment(studentStateId,stateId,currentSchoolYear,educatorId,isTeacher);
	}	
	
	//44. Case 44: Organization Extract
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<OrganizationExtractDTO> getOrganizationsExtractByOrg(Long orgId,boolean includeInternalUsers,String typeLevelCode){
		return dataExtractsMapper.getOrganizationsExtractByOrg(orgId,includeInternalUsers,typeLevelCode);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<FCSDataExtractDTO> getFirstContactSurveyDetails(Long orgId, List<Long> assessmentPrograms,
			Long currentSchoolYear,boolean isTeacher,Long userId) {
		return dataExtractsMapper.getFirstContactSurveyDetails(orgId, assessmentPrograms, currentSchoolYear,isTeacher,userId);
	}

	@Override
	public List<FCSHeader> getDynamicFCSHeaders() {
		return dataExtractsMapper.getDynamicFCSHeaders();
	}

	@Override
	public List<FCSAnswer> getFCSAnswers(Long studentId) {
		return dataExtractsMapper.getFCSAnswers(studentId);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestingReadinessExtractDTO> getStudentReadinessExtractDetails(List<Long> allOrgIds,
			List<Integer> assessmentPrograms, Long currentSchoolYear, boolean onlyRostered, Long teacherId) {
		return dataExtractsMapper.getStudentReadinessExtractDetails(allOrgIds, assessmentPrograms, currentSchoolYear, onlyRostered, teacherId);
	}

	@Override
	public List<TestingReadinessEnrollSubjects> getTestRecordsForExtract(Set<Long> studentIds, Long currentSchoolYear) {
		return dataExtractsMapper.getTestRecordsForExtract(studentIds, currentSchoolYear);
	}

	@Override
	public List<StudentExitExtractDTO> getDLMStudentExitExtractForGrf(Long orgId, int year, String assesmentProgram, Boolean isStateHaveSpecificExitCode) {
		return dataExtractsMapper.getDLMStudentExitExtractForGrf(orgId,year, assesmentProgram, isStateHaveSpecificExitCode);
	}

	@Override
	public List<UploadScCodeFile> getDLMSpecialCircumstanceExtract(Long orgId, int year,String stateCode) {
		return dataExtractsMapper.getDLMSpecialCircumstanceExtract(orgId,year,stateCode);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<UploadGrfFile> getDLMGeneralResearchExtract(Long stateId,
			Long districtId, Long schoolId, Long subject, Long grade, int year,int offset, int limit) {
		return dataExtractsMapper.getDLMGeneralResearchExtract(stateId,districtId,schoolId,subject,grade,year, offset, limit);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Organization> getOrganizationByTypeForGRF(Long stateId, Long districtId, 
			String orgTypeCode, int reportYear) {
		return dataExtractsMapper.getOrganizationByTypeForGRF(stateId, districtId, orgTypeCode, reportYear);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getContentAreasforGRF(Long stateId,
			Long districtId, Long schoolId, int reportYear) {
		return dataExtractsMapper.getContentAreasforGRF(stateId, districtId, schoolId, reportYear);
	}

	@Override
	public List<GradeCourse> getGradeCourseByGRF(Long stateId,
			Long districtId, Long schoolId, Long contentAreaId, int reportYear) {
		return dataExtractsMapper.getGradeCourseByGRF(stateId, districtId, schoolId, contentAreaId, reportYear);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<UploadIncidentFile> getDLMIncidentExtract(Long orgId, int year,
			int offset, int limit) {
		return dataExtractsMapper.getDLMIncidentExtract(orgId, year, offset, limit);
	}
		
	@Override
	@Transactional(value = "dataWarehouseTxMgr", readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Enrollment findStudentBasedOnStateStudentIdentifier(String stateStudentIdentifier, Long organizationId) {
		return historicDataMapper.findStudentBasedOnStateStudentIdentifier(stateStudentIdentifier, organizationId);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ISMARTTestAdminExtractDTO> getISmartTestStatusRecords(Long orgId, boolean shouldOnlySeeRosteredStudents,
			Long userId, int currentSchoolYear, Long userOrgId, List<Long> assessmentProgramIds, List<String> ismartContentAreas) {
		return dataExtractsMapper.getISmartTestStatusRecords(orgId, shouldOnlySeeRosteredStudents, userId, currentSchoolYear, userOrgId, assessmentProgramIds, ismartContentAreas);
	}
  
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<PNPAbridgedExtractDTO> getActiveStudentsWithPNPEnrolledInOrg(
			Long orgId,
			boolean onlyRostered,
			Long teacherId,
			int limit,
			int offset,
			int currentSchoolYear,
			List<Long> assessmentPrograms,
			boolean includeAllStudents) {
		return dataExtractsMapper.getActiveStudentsWithPNPInOrg(orgId, onlyRostered, teacherId, currentSchoolYear, limit, offset, assessmentPrograms, includeAllStudents);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KELPA2StudentScoreDTO> generateKELPA2StudentScoreCurrentExtractForDistrictUser(List<Long> orgIds,
			Boolean isDistict, Boolean isSchool, List<Long> contentAreaId, List<Long> schoolYears,
			List<String> gradeAbbreviatedNames, Long currentSchoolYear, Long currentReportYear, int offset, int limit,
			Long stateId, String dataExtractReportStudent, Long assessmentProgramId) {
		List<KELPA2StudentScoreDTO> kelpa2StudentScores = null;
		kelpa2StudentScores = dataExtractsMapper.generateKELPA2StudentScoreCurrentExtractForDistrictUser(orgIds,
				isDistict, isSchool, contentAreaId, schoolYears, gradeAbbreviatedNames, currentSchoolYear,
				currentReportYear, offset, limit, stateId, dataExtractReportStudent, assessmentProgramId);
		return kelpa2StudentScores;
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KAPStudentScoreDTO> generateKAPStudentScoreCurrentExtractForDistrictUser(List<Long> orgIds,
			Boolean isDistict,Boolean isSchool, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeAbbreviatedNames,
			Long currentSchoolYear, Long currentReportYear,int offset, int limit,Long stateId,String dataExtractReportStudent,Long assessmentProgramId) {
		List<KAPStudentScoreDTO> kapStudentScores = null;
		kapStudentScores = dataExtractsMapper.generateKAPStudentScoreCurrentExtractForDistrictUser(
				orgIds, isDistict,isSchool, contentAreaId, schoolYears, gradeAbbreviatedNames, currentSchoolYear,currentReportYear,offset,limit,stateId,dataExtractReportStudent,assessmentProgramId);
		return kapStudentScores;
	}

	@Override
	@Transactional(value = "readreplicaTxMgr",readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KAPStudentScoreDTO> generateKAPStudentScoreSpecifiedExtractForDistrictUser(List<Long> orgIds,Boolean isState, Boolean isDistict,Boolean isSchool,
			Long currentSchoolYear, String stateIdStudentIdentifier, Long currentReportYear,boolean isCurrentEnrolled,Long assessmentProgramId) {	
	List<KAPStudentScoreDTO> kapStudentScores = null;
		kapStudentScores = dataExtractsMapper.generateKAPStudentScoreSpecifiedExtractForDistrictUser(
				orgIds,isState,isDistict,isSchool,currentSchoolYear,stateIdStudentIdentifier, currentReportYear,isCurrentEnrolled,assessmentProgramId);
		return kapStudentScores;
	}

	@Override
	@Transactional(value = "readreplicaTxMgr",readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KAPStudentScoreDTO> generateKAPStudentScoreTestedExtractForDistrictUser(List<Long> orgIds,Long currentOrganizationId,Long districtId,boolean isState,
			boolean isDistict,boolean isSchool, List<Long> contentAreaIds, List<Long> schoolYears, List<String> gradeAbbreviatedNames,
			Long currentSchoolYear, Long currentReportYear,int offset, int limit,String extractStudent,Long stateId,Long assessmentProgramId) {
		List<KAPStudentScoreDTO> kapStudentScores = null;
		kapStudentScores = dataExtractsMapper.generateKAPStudentScoreTestedExtractForDistrictUser(
				orgIds,currentOrganizationId,districtId,isState, isDistict,isSchool, contentAreaIds, schoolYears, gradeAbbreviatedNames, currentSchoolYear,currentReportYear,offset,limit,extractStudent,stateId,assessmentProgramId);
		return kapStudentScores;
	}
//Subject
	@Override
	@Transactional(value = "readreplicaTxMgr",readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getSubjectsForStudentScoreExtract(Long schoolYear, Long assessmentProgramId,boolean isDistict,boolean isSchool,List<Long> orgList) {
		return dataExtractsSupportMapper.getSubjectsForStudentScoreExtract(schoolYear,assessmentProgramId,isDistict,isSchool,orgList);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr",readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ContentArea> getSubjectsForTestedStudentScoreExtract(Long reportYear, Long assessmentProgramId,boolean isDistict,boolean isSchool,List<Long> orgList) {
		return dataExtractsSupportMapper.getSubjectsForTestedStudentScoreExtract(reportYear,assessmentProgramId,isDistict,isSchool,orgList);
	}

	@Override
	@Transactional(value = "readreplicaTxMgr",readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getStudentReportSchoolYearsBySubject(List<Long> contentAreaId,String assessmentCode) {
		return dataExtractsSupportMapper.getStudentReportSchoolYearsBySubject(contentAreaId,assessmentCode);
	}	

	@Override
	@Transactional(value = "readreplicaTxMgr",readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> getGradesByOrgIdForStudentScoreExtract(boolean isDistict,boolean isSchool, List<Long> orgId, int schoolYear,String displayIdentifier,Long assessmentProgramId) {
		return dataExtractsSupportMapper.findGradesByOrgIdForStudentScoreExtract(isDistict,isSchool, orgId, schoolYear,displayIdentifier,assessmentProgramId);
	}
	
	@Override
	@Transactional(value = "readreplicaTxMgr",readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final List<GradeCourse> getGradesByOrgIdForStudentScoreTestedExtract(boolean isDistict,boolean isSchool, List<Long> orgId,Long assessmentProgramId, Long organizationId,String displayIdentifier,Long reportYear) {
		return dataExtractsSupportMapper.findGradesByOrgIdForStudentScoreTestedExtract(isDistict,isSchool, orgId, assessmentProgramId,organizationId,displayIdentifier,reportYear);
	}
		
	@Override
	@Transactional(value = "readreplicaTxMgr", readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<PNPAbridgedExtractDTO> getActiveStudentsWithPNPEnrolledInOrgPLTW(
			Long orgId,
			boolean onlyRostered,
			Long teacherId,
			int currentSchoolYear,
			List<Long> assessmentPrograms,
			boolean includeAllStudents){
		return dataExtractsMapper.getActiveStudentsWithPNPInOrgPLTW(orgId, onlyRostered, teacherId, currentSchoolYear, assessmentPrograms, includeAllStudents);
	}

	@Override
	public List<Long> getStudentReportSchoolYearsBySubjectForKelpa(String kelpa2StudentReportAssessmentcode) {
		return dataExtractsSupportMapper.getStudentReportSchoolYearsBySubjectForKelpa(kelpa2StudentReportAssessmentcode);
	}
	
	@Override
	public Long getKAPCurrentStudentCount(List<Long> orgIds, boolean isDistict, boolean isSchool,			
			Long currentSchoolYear, Long stateId, Long assessmentProgramId) {
		return dataExtractsMapper.getKAPCurrentStudentCount(
				orgIds, isDistict,isSchool, currentSchoolYear, stateId, assessmentProgramId);
		 
	}
	
	@Override
	public List<GradeCourse> getGradesByOrgIdForKelpaStudentScoreExtract(boolean isDistict, boolean isSchool,
			List<Long> orgId, int schoolYear, String displayIdentifier, Long assessmentProgramId) {
		return dataExtractsSupportMapper.findGradesByOrgIdForKelpaStudentScoreExtract(isDistict,isSchool, orgId, schoolYear,displayIdentifier,assessmentProgramId);
	}
}