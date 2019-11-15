/**
 * 
 */
package edu.ku.cete.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import edu.ku.cete.configuration.StudentsTestSectionsStatusConfiguration;
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.dataextracts.model.YearOverYearReportMapper;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.Externalstudentreports;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.RubricScore;
import edu.ku.cete.domain.StudentsResponsesReportDto;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.content.TaskVariantsFoils;
import edu.ku.cete.domain.content.TaskVariantsFoilsExample;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.enrollment.StudentTestSessionDto;
import edu.ku.cete.domain.lm.LmNode;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.NodeReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.RawScoreSectionWeights;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.ExternalstudentreportsMapper;
import edu.ku.cete.model.OrganizationReportDetailsMapper;
import edu.ku.cete.model.RawScoreSectionWeightsMapper;
import edu.ku.cete.model.RubricScoreMapper;
import edu.ku.cete.model.StudentReportMapper;
import edu.ku.cete.model.StudentsResponsesDao;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TaskVariantDao;
import edu.ku.cete.model.TaskVariantsFoilsDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.report.NodeReportDao;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.StringUtil;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;
import edu.ku.cete.web.ExternalStudentReportDTO;
import edu.ku.cete.web.KAPStudentScoreDTO;
import edu.ku.cete.web.SchoolAndDistrictReportDTO;
import edu.ku.cete.web.StudentReportDTO;

/**
 * @author m802r921
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class StudentReportServiceImpl implements StudentReportService {
	
	/**
	 * studentDao.
	 */
	@Autowired
	private StudentDao studentDao;
	/**
	 * nodeReportDao.
	 */
	@Autowired
	private NodeReportDao nodeReportDao;
	/**
	 * studentsResponsesDao.
	 */
	@Autowired
	private StudentsResponsesDao studentsResponsesDao;
	
	@Autowired
	private RubricScoreMapper rubricScoreMapper;	
	
	@Autowired
	private StudentsTestsDao studentsTestsDao;
	/**
	 * taskVariantDao.
	 */
	@Autowired
	private TaskVariantDao taskVariantDao;
	/**
	 * taskVariantsFoilsDao.
	 */
	@Autowired
	private TaskVariantsFoilsDao taskVariantsFoilsDao;
	/**
	 * taskVariantsFoilsDao.
	 */
	@Autowired
	private CategoryService categoryService;
	/**
	 * studentSessionRuleConverter.
	 */
	@Autowired
	private StudentSessionRuleConverter studentSessionRuleConverter;
    /**
     * student test status type 
     */
	@Value("${studentstests.status.type}")
    private String STUDENT_TEST_STATUS_TYPE;
	/**
	 * 
	 */
    @Value("${testsession.status.notEnrolled}")
	private String NOT_ENROLLED_TEST_STATUS;
    
    /**
     * studentsTestsStatusConfiguration.
     */
    @Autowired
    private StudentsTestsStatusConfiguration
    studentsTestsStatusConfiguration;
    
    /**
     * studentsTestSectionsStatusConfiguration.
     */
    @Autowired
    private StudentsTestSectionsStatusConfiguration
    studentsTestSectionsStatusConfiguration;    
    /**
     *  student test status map.
     */
    private Map<Long, Category> studentTestStatusMap;
	/**
	 * testService.
	 */
    @Autowired
	private TestService testService;
    @Autowired
	private TestCollectionsSessionRulesDao
	testCollectionsSessionRulesDao;
    
    @Autowired
    private StudentReportMapper studentReportMapper;
    
    @Autowired
    ExternalstudentreportsMapper externalstudentreportsMapper;
    
	@Autowired
    private YearOverYearReportMapper yearOverYearReportMapper;
    
    @Autowired
    private OrganizationReportDetailsMapper organizationReportDetailsMapper;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private RawScoreSectionWeightsMapper rawScoreSectionWeightsMapper;
    
    @Autowired
    private AuthoritiesDao authoritiesDao;
    
    private static final Logger logger = LoggerFactory.getLogger(StudentReportServiceImpl.class);
    
    /**
     * load the student test status from category.
     */
    @PostConstruct
    public void loadStudentTestStatuses() {
		//set the status ids.This is to avoid left join.
    	studentTestStatusMap = categoryService.selectIdMapByCategoryType(STUDENT_TEST_STATUS_TYPE);
    }
    
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<StudentTestSessionDto> getTestSessionReport(List<Long> rosterIds,Long testSessionId, boolean includeFoilLessItems, Long currentSchoolYear, Long organizationId) {
		boolean validInput = true;
		List<StudentTestSessionDto> studentTestSessionDtos = new ArrayList<StudentTestSessionDto>();
		List<StudentsResponsesReportDto> studentsResponsesReportDtos = new ArrayList<StudentsResponsesReportDto>();
		if(testSessionId == null || rosterIds == null || rosterIds.isEmpty()) {
			validInput = false;
		}
		if(validInput) {
			//1. Get students by roster and test session.
			//2. Get the students even if they are not enrolled in to the test session.
			//3. Indicate their status as not enrolled.
			AssessmentProgram ap = assessmentProgramService.findByTestSessionId(testSessionId);
			studentTestSessionDtos = studentDao.findByRosterAndTestSession(rosterIds, testSessionId, null, ap == null ? null : ap.getAbbreviatedname(), currentSchoolYear, organizationId);
			List<Long> studentsTestsIdList = AARTCollectionUtil.getIds(studentTestSessionDtos);
			if (studentsTestsIdList != null && CollectionUtils.isNotEmpty(studentsTestsIdList)) {
				//This is done in 2 steps to avoid left join with 
				// most students who are in the roster are not enrolled to a test or have not 
				// taken the test.
				studentsResponsesReportDtos = studentsResponsesDao
						.selectStudentTestResponsesByStudentTest(studentsTestsIdList, includeFoilLessItems);
				for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtos) {
					//set the status ids.This is to avoid left join.
					studentTestSessionDto.setStudentTestStatus(studentTestStatusMap, NOT_ENROLLED_TEST_STATUS);
					//set the responses.					
					studentTestSessionDto
							.setStudentsResponsesDtos(studentsResponsesReportDtos);
				}
			}
		}
		return studentTestSessionDtos;
	}
		    
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<StudentTestSessionDto> getAutoTestSessionReport(Long testSessionId, boolean includeFoilLessItems,
			Long currentSchoolYear, Long organizationId) {
		boolean validInput = true;
		List<StudentTestSessionDto> studentTestSessionDtos = new ArrayList<StudentTestSessionDto>();
		List<StudentsResponsesReportDto> studentsResponsesReportDtos = new ArrayList<StudentsResponsesReportDto>();
		if(testSessionId == null) {
			validInput = false;
		}
		if(validInput) {
			//1. Get students by roster and test session.
			//2. Get the students even if they are not enrolled in to the test session.
			//3. Indicate their status as not enrolled.
			AssessmentProgram ap = assessmentProgramService.findByTestSessionId(testSessionId);
			studentTestSessionDtos = studentDao.findByRosterAndTestSession(null, testSessionId, null, ap == null ? null : ap.getAbbreviatedname(), currentSchoolYear, organizationId);
			List<Long> studentsTestsIdList = AARTCollectionUtil.getIds(studentTestSessionDtos);
			if (studentsTestsIdList != null && CollectionUtils.isNotEmpty(studentsTestsIdList)) {
				//This is done in 2 steps to avoid left join with 
				// most students who are in the roster are not enrolled to a test or have not 
				// taken the test.
				studentsResponsesReportDtos = studentsResponsesDao
						.selectStudentTestResponsesByStudentTest(studentsTestsIdList, includeFoilLessItems);
				for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtos) {
					//set the status ids.This is to avoid left join.
					studentTestSessionDto.setStudentTestStatus(studentTestStatusMap, NOT_ENROLLED_TEST_STATUS);
					//set the responses.					
					studentTestSessionDto
							.setStudentsResponsesDtos(studentsResponsesReportDtos);
				}
			}
		}
		return studentTestSessionDtos;
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<StudentsTestSections> getPerformanceStatus(Long testSessionId) {
		boolean validInput = true;
		Map<Long, List<StudentsTestSections>> studentsTestsMap
		= new HashMap<Long, List<StudentsTestSections>>();
		List<StudentsTestSections> studentsTestSectionsList = new ArrayList<StudentsTestSections>();
		StudentSessionRule studentSessionRule = null;
		if(testSessionId == null) {
			validInput = false;
		}
		if(validInput) {
			//1. Get students by test session.
			studentsTestSectionsList = studentDao.findByTestSession(testSessionId);
			List<TestCollectionsSessionRules>
			testCollectionsSessionRulesList = testCollectionsSessionRulesDao.selectByTestSession(
					testSessionId);
			studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(
					testCollectionsSessionRulesList);
		}
		if(studentsTestSectionsList != null
				&& CollectionUtils.isNotEmpty(studentsTestSectionsList)) {
			for(StudentsTestSections studentsTestSections : studentsTestSectionsList) {
				if(!studentsTestsMap.containsKey(studentsTestSections.getStudentsTestId())) {
					studentsTestsMap.put(studentsTestSections.getStudentsTestId(),
							(new ArrayList<StudentsTestSections>()));
				}
				studentsTestsMap.get(
						studentsTestSections.getStudentsTestId(
								)).add(studentsTestSections);

				if(studentsTestSections != null && studentsTestSections.getStatusId() != null) {
					//TODO remove hard coded grace period after implementing 
					studentsTestSections.setStatus(
							studentsTestSectionsStatusConfiguration.getStatus(
							studentsTestSections.getStatusId(),
							studentsTestSections.getModifiedDate(),
							(studentSessionRule == null ? null : studentSessionRule.getGracePeriod())
							)
							);
					//INFO it should not be null as not null is added and it also should be valid for application to work.
					//updating test wise statuses to section wise status and vice versa will not work.
					studentsTestSections.getStatus().getId();
				}
				if(studentsTestSections != null
						&& studentsTestSections.getStudentsTests() != null
						&& studentsTestSections.getStudentsTests().getStatus() != null) {
					//TODO ignore the status id and set based off test section status.
					studentsTestSections.getStudentsTests().setStudentTestStatus(
							studentsTestsStatusConfiguration.getStatus(
							studentsTestSections.getStudentsTests().getStatus()
							));
				}
			}
			for(Long studentsTestsId:studentsTestsMap.keySet()) {
				studentsTestsStatusConfiguration.setStudentsTestsCategory(studentsTestsMap.get(studentsTestsId));
			}
		}
		return studentsTestSectionsList;
	}	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<StudentTestSessionDto> getTestSessionReportForPdf(Long testSessionId, Long completedStatusId, Long organizationId) {
		boolean validInput = true;
		List<StudentTestSessionDto> studentTestSessionDtos = new ArrayList<StudentTestSessionDto>();
		List<StudentsResponsesReportDto> studentsResponsesReportDtos = new ArrayList<StudentsResponsesReportDto>();
		//TODO add the rosterId Condition.
		if(testSessionId == null || testSessionId == null) {
			validInput = false;
		}
		if(validInput) {
			//1. Get students by roster and test session.
			//2. Get the students even if they are not enrolled in to the test session.
			//3. Indicate their status as not enrolled.
			studentTestSessionDtos = studentDao.findByRosterAndTestSession(null, testSessionId, completedStatusId, null, null, organizationId);
			List<Long> studentsTestsIdList = AARTCollectionUtil.getIds(studentTestSessionDtos);
			if (studentsTestsIdList != null && CollectionUtils.isNotEmpty(studentsTestsIdList)) {
				//This is done in 2 steps to avoid left join with 
				// most students who are in the roster are not enrolled to a test or have not 
				// taken the test.
				studentsResponsesReportDtos = studentsResponsesDao
						.selectStudentTestResponsesByStudentTest(studentsTestsIdList, false);
				Map<Long, Test> testMap = new HashMap<Long, Test>();
				Test test = null;
				for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtos) {
					//set the test for the report.
					if(testMap.containsKey(studentTestSessionDto.getStudentsTests().getTestId())){
						studentTestSessionDto.getStudentsTests().setTest(testMap.get(studentTestSessionDto.getStudentsTests().getTestId()));
					}
					else{ 	
						test = testService.findTestAndSectionById(studentTestSessionDto.getStudentsTests().getTestId());
						testMap.put(studentTestSessionDto.getStudentsTests().getTestId(), test);
						studentTestSessionDto.getStudentsTests().setTest(test);
					}
					//set the status ids.This is to avoid left join.
					studentTestSessionDto.setStudentTestStatus(studentTestStatusMap, NOT_ENROLLED_TEST_STATUS);
					//set the responses.					
					studentTestSessionDto
							.setStudentsResponsesDtos(studentsResponsesReportDtos);
				}
			}
		}
		return studentTestSessionDtos;
	}
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public RubricScore saveRubricScore(Long rubricScoreId,Long taskVariantId,String rubricInfoIds,Long studentId,Long testId,Float score){
		RubricScore rubricscore=new RubricScore();
		rubricscore.setRubricScore(new BigDecimal(score));
		rubricscore.setTaskVariantId(taskVariantId);
		Date today = new Date();
		rubricscore.setDate(today);
		rubricscore.setRubricInfoIds(rubricInfoIds);
		List<StudentsTests> studentsTests=studentsTestsDao.findByTestAndStudentId(testId,studentId);
		rubricscore.setStudentTestId(studentsTests.get(0).getId());
		List<RubricScore> scores=rubricScoreMapper.selectByStudentTestId(studentsTests.get(0).getId(),taskVariantId);
		if(scores!=null && scores.size()>0){
			rubricscore.setId(rubricScoreId);
			rubricScoreMapper.updateByPrimaryKey(rubricscore);
			}else{
				rubricscore.setStudentTestId(studentsTests.get(0).getId());
				if(rubricScoreId==null){
					rubricScoreMapper.insert(rubricscore);
			}
		}		
		return rubricscore;
	}
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public	RubricScore getRubricScore(Long studentstestId,Long taskVariantId){
		RubricScore score= null;
		List<RubricScore> scores=rubricScoreMapper.selectByStudentTestId(studentstestId,taskVariantId);
		if(scores!=null && !scores.isEmpty()){
			score =  scores.get(0);
		}
		return score;
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public	RubricScore getRubricScoreByStudent(Long studentId,Long testId,Long taskVariantId){
		RubricScore score= null;
		List<StudentsTests> studentsTests=studentsTestsDao.findByTestAndStudentId(testId,studentId);
		List<RubricScore> scores=rubricScoreMapper.selectByStudentTestId(studentsTests.get(0).getId(),taskVariantId);
		if(scores!=null && !scores.isEmpty()){
			score =  scores.get(0);
		}
		return score;
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public List<TaskVariantsFoils> getTaskVariant(Long taskVariantId) {
		TaskVariant taskVariant = null;
		List<TaskVariantsFoils> taskVariantsFoilsList = null;
		if(taskVariantId != null && taskVariantId > 1) {
			taskVariant = taskVariantDao.selectByPrimaryKey(taskVariantId);
	        if (taskVariant != null) {
				TaskVariantsFoilsExample taskVariantsFoilsExample = new TaskVariantsFoilsExample();
				TaskVariantsFoilsExample.Criteria taskVariantsFoilsCriteria = taskVariantsFoilsExample
						.createCriteria();
				taskVariantsFoilsCriteria
						.andTaskVariantIdEqualTo(taskVariantId);
				taskVariantsFoilsExample
						.setOrderByClause("tvf.taskvariantid, tvf.responseorder");
				taskVariantsFoilsList = taskVariantsFoilsDao
						.selectExtendedByExample(taskVariantsFoilsExample);
			}
			if (taskVariantsFoilsList != null && CollectionUtils.isNotEmpty(taskVariantsFoilsList)) {
				for (TaskVariantsFoils taskVariantsFoils : taskVariantsFoilsList) {
					taskVariantsFoils.setTaskVariant(taskVariant);
				}
			}
		}
		return taskVariantsFoilsList;
	}
	/**
	 * @param studentKeyword
	 * @param testKeyword
	 * @param recordCount
	 * @return
	 */
	@Override
    @Transactional(readOnly = true,
    propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public int countByStudentAndTest(
			String studentKeyword,String testKeyword,
			Long organizationId) {
		int count = 0 ;
        if (StringUtils.hasText(testKeyword)
        		&& StringUtils.hasText(studentKeyword)
        		&& organizationId != null) {
        	testKeyword ='%'+testKeyword.trim()+'%';
        	studentKeyword = '%'+studentKeyword.trim()+'%';
			count = nodeReportDao.countByStudentAndTest(
					studentKeyword, testKeyword, organizationId);
		}		
        return count;
	}
	
	/**
	 * TODO remove the distinct and add 2 seperate calls,
	 *  that will make the search faster.
	 * 
	 * @param studentKeyword
	 * @param testKeyword
	 * @param limitCount
	 * @return
	 */
	@Override
    @Transactional(readOnly = true,
    propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public Collection<NodeReport> selectByStudentAndTest(
			String studentKeyword,String testKeyword,
			Long organizationId,
			int limitCount, int pageCount,
			String sortColumn, String sortType) {
        List<NodeReport> nodeReportItems = null;
        TimerUtil timerUtil = TimerUtil.getInstance();
        if (StringUtils.hasText(testKeyword)
        		&& StringUtils.hasText(studentKeyword)
        		&& organizationId != null) {
        	testKeyword ='%'+testKeyword.trim()+'%';
        	studentKeyword = '%'+studentKeyword.trim()+'%';
        	//INFO multiplying by 2 because of the combining in java layer.
			nodeReportItems = nodeReportDao.selectByStudentAndTest(
					studentKeyword, testKeyword,
					organizationId,
					limitCount,(pageCount-1)*limitCount,
					StringUtil.convertCamelCaseToUnderScore(
							sortColumn) + " "+ sortType);
        	timerUtil.resetAndLog(logger, "Pulling node report in AART Took ");
		}
        //combine the correct and incorrect responses to one object.
        Set<String> nodeKeys = new HashSet<String>();
        if (nodeReportItems != null &&
        		CollectionUtils.isNotEmpty(nodeReportItems)) {
        	
			for (NodeReport nodeReportItem : nodeReportItems) {
				if (nodeReportItem != null
						&& nodeReportItem.getNodeKey() != null) {
					nodeKeys.add(nodeReportItem.getNodeKey());
				}
			}        	
        	
			logger.debug("No of node Report Items" + nodeReportItems.size());
	        Set<LmNode> lmNodes = testService.findNodeInformationForNodeResponse(nodeKeys);
			for (NodeReport nodeReportItem : nodeReportItems) {
				nodeReportItem.setLmNode(lmNodes);
			}
			logger.debug("No of node Report Items" + nodeReportItems.size());
		}
		return nodeReportItems;
	}
	
	/**
	 * Count the number of responses per question
	 */
	@Override
    @Transactional(readOnly = true,
    propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public int countByTaskVariantId(Long taskVariantId) {
		int responsesPerQuestion = taskVariantDao.countByTaskVariantId(taskVariantId);
		return responsesPerQuestion;
	}
	
	/**
	 * @param testSessionId
	 * @param completedStatusId
	 * @return
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public Integer getCompletedStudentsCount(Long testSessionId, Long completedStatusId) { 
		return studentDao.getCompletedStudentsCount(testSessionId, completedStatusId);
	}
	
	
	/**
	 * @param testSessionId
	 * @param completedStatusId
	 * @return
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public Integer getCompletedAndEnrolledStudentsCount(Long testSessionId, Long completedStatusId) { 
		return studentDao.getCompletedAndEnrolledStudentsCount(testSessionId, completedStatusId);
	}

	@Override
	public StudentReport getScaleScoreAndPerformanceLevel(Long studentid,
			Long enrollmentId, Long stage1TestId, Long stage2TestId, Long grade, Long contentArea) {		
		return studentReportMapper.getScaleScoreAndPerformanceLevel(studentid, enrollmentId, stage1TestId, stage2TestId, grade, contentArea);
	}
	
	@Override
	public Long countStudentReports(Map<String, Object> parameters) {
		return studentReportMapper.countByCriteria(parameters);
	}
	
	@Override
	public List<StudentReportDTO> getStudentReports(Map<String, Object> parameters) {
		return studentReportMapper.selectByCriteria(parameters);
	}
	
	@Override
	public List<ExternalStudentReportDTO> getExternalStudentReports(Map<String, Object> parameters) {
		return externalstudentreportsMapper.selectByCriteria(parameters);
	}
	
	@Override
	public List<ExternalStudentReportDTO> getExternalStudentReportsForTeacherRoster(Map<String, Object> parameters) {
		return externalstudentreportsMapper.selectByCriteriaForTeacherRoster(parameters);
	}
	
	@Override
	public Externalstudentreports getExternalReportFileDetailsByPrimaryKey(Long id, Long userOrgId) {
		return externalstudentreportsMapper.selectByPrimaryKeyAndUserOrg(id, userOrgId);
	}
	
	@Override
	public Long countExternalStudentReports(Map<String, Object> parameters) {
		return externalstudentreportsMapper.countByCriteria(parameters);
	}
	
	@Override
	public Long countExternalStudentReportsForTeacherRoster(Map<String, Object> parameters) {
		return externalstudentreportsMapper.countByCriteriaForTeacherRoster(parameters);
	}
	
	@Override
	public Long countSchoolDetailReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.countByCriteria(parameters);
	}
	
	@Override
	public Long countDistrictDetailReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.countByCriteria(parameters);
	}
	
	@Override
	public List<SchoolAndDistrictReportDTO> getSchoolDetailReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.selectByCriteria(parameters);
	}
	
	@Override
	public List<SchoolAndDistrictReportDTO> getSchoolSummaryReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getSchoolSummaryReports(parameters);
	}
	
	@Override
	public List<SchoolAndDistrictReportDTO> getDistrictDetailReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.selectByCriteria(parameters);
	}

	@Override
	public List<OrganizationReportDetails> getAllStudentsReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getAllStudentsReports(parameters);
	}
	
	@Override
	public StudentReport getByPrimaryKey(Long id) {
		return studentReportMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public StudentReport getByPrimaryKeyAndUserOrg(Long id, Long userOrgId) {
		return studentReportMapper.selectByPrimaryKeyAndUserOrg(id, userOrgId);
	}
	
	@Override
	public SchoolAndDistrictReportDTO getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId) {
		return organizationReportDetailsMapper.getSchoolAndDistrictReportByPrimaryKeyAndUserOrgId(id, userOrgId);
	}
	
	@Override
	public SchoolAndDistrictReportDTO getSchoolSummaryReportFile(Long id, Long userOrgId) {
		return organizationReportDetailsMapper.getSchoolSummaryReportFile(id, userOrgId);
	}
	
	@Override
	public OrganizationReportDetails getSchoolAndDistrictReport(Long id){
		return organizationReportDetailsMapper.getSchoolAndDistrictReport(id);
	}
	
	@Override
	public List<Long> getSchoolIdsForDistrict(Long districtId,Long reportYear){
		return organizationReportDetailsMapper.getSchoolIdsForDistrict(districtId,reportYear);
	}
	
	@Override
	public List<Long> getSchoolIdsForDistrictDLMandCpass(Long districtId, String reportTypeCode,Long reportYear){
		return organizationReportDetailsMapper.getSchoolIdsForDistrictDLMandCpass(districtId, reportTypeCode,reportYear);
	}
	
	@Override
	public List<SchoolAndDistrictReportDTO> getDistrictSummaryReport(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getDistrictSummaryReport(parameters);
	}
	
	@Override
	public SchoolAndDistrictReportDTO getDlmDistrictSummaryReport(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getDlmDistrictSummaryReport(parameters);
	}	
	
	@Override
	public SchoolAndDistrictReportDTO getDlmStateSummaryReport(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getDlmStateSummaryReport(parameters);
	}
	
	@Override
	public SchoolAndDistrictReportDTO getDlmSummaryReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId,String reportType) {
		return organizationReportDetailsMapper.getDlmSummaryReportByPrimaryKeyAndUserOrgId(id, userOrgId,reportType);
	}
	
	@Override
	public SchoolAndDistrictReportDTO getDistrictSummaryReportByPrimaryKeyAndUserOrgId(Long id, Long userOrgId) {
		return organizationReportDetailsMapper.getDistrictSummaryReportByPrimaryKeyAndUserOrgId(id, userOrgId);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)	
	public int taskvariantCountByTestId(Long testId) {
		return taskVariantDao.taskvariantCountByTestId(testId);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public	List<RawScoreSectionWeights> getRawScoreSectionWeights(Long assessmentProgramId, Long subjectId, Long testId, Long schoolYear) {
		List<RawScoreSectionWeights> temp = new ArrayList<RawScoreSectionWeights>();
		temp = rawScoreSectionWeightsMapper.getRawScoreSectionWeights(assessmentProgramId, subjectId, testId, schoolYear);
		
		return temp;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getStudentReportSchoolYearsBySubject(List<Long> contentAreaId) {
		return studentReportMapper.getStudentReportSchoolYearsBySubject(contentAreaId);
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KAPStudentScoreDTO> generateKAPStudentScoreExtract(Long orgId, String orgType, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeIds, String includeSubscores, Long currentSchoolYear, String stateStudentIdentifier){
		List<KAPStudentScoreDTO> kapStudentScores = null;
		kapStudentScores = studentReportMapper.generateKAPStudentScoreExtract(orgId, orgType, contentAreaId, schoolYears, gradeIds, includeSubscores, currentSchoolYear, stateStudentIdentifier);
		return kapStudentScores;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReport> selectStudentReportsByStudentIdSubjSchYrGrade(String stateStudentIdentifier, List<Long> contentAreaId, List<Long> schoolYears, List<Long> gradeIds){
		List<StudentReport> kapStudentReports = null;
		kapStudentReports = studentReportMapper.selectStudentReportsByStudentIdSubjSchYrGrade(stateStudentIdentifier, contentAreaId, schoolYears, gradeIds);
		return kapStudentReports;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<KAPStudentScoreDTO> generateKAPStudentScoreExtractForDistrictUser(Long orgId, String orgType, List<Long> contentAreaId, List<Long> schoolYears, List<String> gradeIds, String includeSubscores, Long currentSchoolYear, String stateStudentIdentifier){
		List<KAPStudentScoreDTO> kapStudentScores = null;
		kapStudentScores = studentReportMapper.generateKAPStudentScoreExtractForDistrictUser(orgId, orgType, contentAreaId, schoolYears, gradeIds, includeSubscores, currentSchoolYear, stateStudentIdentifier);
		return kapStudentScores;
	}	
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportDTO> searchByStudentLastName(String studentLastname, Long userId, String currentUserLevel, Long orgId, Integer limitCount, Integer offset, Long groupId, Long currentSchoolYear, Long reportYear, Groups groups) {
		Authorities auth = authoritiesDao.getByAuthority("VIEW_ALL_STUDENT_REPORTS");
		return yearOverYearReportMapper.searchByStudentLastName(studentLastname, userId, currentUserLevel, orgId, limitCount, offset, groupId, currentSchoolYear, reportYear, auth.getAuthoritiesId(), groups.getGroupCode());
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getStudentReportCountByStudentIdSubject(String stateStudentIdentifier, List<Long> contentAreaId){
		return studentReportMapper.getStudentReportCountByStudentIdSubject(stateStudentIdentifier, contentAreaId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int getStudentReportCountByStudentIdSubjectUserOrg(String stateStudentIdentifier, List<Long> contentAreaId, Long orgId, String orgType){
		return studentReportMapper.getStudentReportCountByStudentIdSubjectUserOrg(stateStudentIdentifier, contentAreaId, orgId, orgType);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportDTO> searchByStateStudentIdForKAP(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear) {
		boolean isEnrolledInUserOrg = yearOverYearReportMapper.isStudentCurrentlyEnrolledInUserOrgHierarchy(stateStudentIdentifier, currentUserLevel, orgId, currentSchoolYear);
		Authorities auth = authoritiesDao.getByAuthority("VIEW_ALL_STUDENT_REPORTS");
		return yearOverYearReportMapper.searchByStateStudentIdForKAP(stateStudentIdentifier, userId, currentUserLevel, orgId, isEnrolledInUserOrg, groupId, currentSchoolYear, reportYear, auth.getAuthoritiesId());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportDTO> searchByStateStudentIdForDLM(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear, Groups group) {
		Boolean isEnrolledInUserOrg = yearOverYearReportMapper.isStudentCurrentlyEnrolledInUserOrgHierarchy(stateStudentIdentifier, currentUserLevel, orgId, currentSchoolYear);
		Authorities auth = authoritiesDao.getByAuthority("VIEW_ALL_STUDENT_REPORTS");
		return yearOverYearReportMapper.searchByStateStudentIdForDLMOrCPASS(stateStudentIdentifier, userId, currentUserLevel, orgId, Arrays.asList("DLM"), isEnrolledInUserOrg, groupId, currentSchoolYear, reportYear, auth.getAuthoritiesId(), group.getGroupCode());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentReportDTO> searchByStateStudentIdForCPASS(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear, Groups group) {
		Boolean isEnrolledInUserOrg = yearOverYearReportMapper.isStudentCurrentlyEnrolledInUserOrgHierarchy(stateStudentIdentifier, currentUserLevel, orgId, currentSchoolYear);
		Authorities auth = authoritiesDao.getByAuthority("VIEW_ALL_STUDENT_REPORTS");
		return yearOverYearReportMapper.searchByStateStudentIdForDLMOrCPASS(stateStudentIdentifier, userId, currentUserLevel, orgId, Arrays.asList("CPASS"), isEnrolledInUserOrg, groupId, currentSchoolYear, reportYear, auth.getAuthoritiesId(), group.getGroupCode());
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StudentReportDTO getStudentInfoForAllStudentsReports(String stateStudentIdentifier, Long userId,
			String currentUserLevel, Long orgId, Long currentSchoolYear, Long groupId) {
		return yearOverYearReportMapper.getStudentInfoForAllStudentsReports(stateStudentIdentifier, userId, currentUserLevel, orgId, currentSchoolYear, groupId);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int countReportsForStudent(String stateStudentIdentifier, Long userId, String currentUserLevel, Long orgId, Long groupId, Long currentSchoolYear, Long reportYear, Groups group){
		Boolean isEnrolledInUserOrg = yearOverYearReportMapper.isStudentCurrentlyEnrolledInUserOrgHierarchy(stateStudentIdentifier, currentUserLevel, orgId, currentSchoolYear);
		Authorities auth = authoritiesDao.getByAuthority("VIEW_ALL_STUDENT_REPORTS");
		return yearOverYearReportMapper.countReportsForStudent(stateStudentIdentifier, userId, currentUserLevel, orgId, Arrays.asList("DLM","CPASS"), isEnrolledInUserOrg, groupId, currentSchoolYear, reportYear, auth.getAuthoritiesId(), group.getGroupCode());
	}	

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Boolean doesSSIDExist(String stateStudentIdentifier, Long stateId, Long currentSchoolYear){
		return yearOverYearReportMapper.doesSSIDExist(stateStudentIdentifier, stateId, currentSchoolYear);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public StudentReport getByPrimaryKeyForAllStudentReports(Long id, Long orgId, String currentUserLevel, Long currentSchoolYear, String stateStudentIdentifier) {
		Boolean isStudentInUserHierarchy = yearOverYearReportMapper.isStudentCurrentlyEnrolledInUserOrgHierarchy(stateStudentIdentifier, currentUserLevel, orgId, currentSchoolYear);
		return yearOverYearReportMapper.selectStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(id, orgId, isStudentInUserHierarchy);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Externalstudentreports getExternalReportByPrimaryKeyForAllStudentReports(Long id, Long orgId, String currentUserLevel, Long currentSchoolYear, String stateStudentIdentifier) {
		Boolean isStudentInUserHierarchy = yearOverYearReportMapper.isStudentCurrentlyEnrolledInUserOrgHierarchy(stateStudentIdentifier, currentUserLevel, orgId, currentSchoolYear);
		return yearOverYearReportMapper.selectExternalStudentReportByPrimaryKeyAndUserOrgForAllStudentReports(id, orgId, isStudentInUserHierarchy);
	}
	
	@Override
	public List<SchoolAndDistrictReportDTO> getELPAStudnetsScoreFileReport(
			Map<String, Object> params) {
		return organizationReportDetailsMapper.getELPAStudentsScoreFileReport(params);
	}
	
	@Override
	public List<OrganizationReportDetails> getAllStudentSummaryBundledReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getAllStudentSummaryBundledReports(parameters);
	}
	
	@Override
	public List<Long> getSchoolIdsInDistrictOfSummaryBundledReports(Map<String, Object> criteria){
		return organizationReportDetailsMapper.getSchoolIdsInDistrictOfSummaryBundledReports(criteria);
	}	
	
	@Override
	public List<OrganizationReportDetails> getAllSchoolSummaryBundledReportByDistrictId(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getAllSchoolSummaryBundledReportByDistrictId(parameters);
	}
	
	@Override
	public List<ExternalStudentReportDTO> getExternalStudentSummaryReports(Map<String, Object> parameters) {
		return externalstudentreportsMapper.getExternalStudentSummaryReports(parameters);
	}
	
	@Override
	public Long countOfStudentSummaryReports(Map<String, Object> parameters) {
		return externalstudentreportsMapper.countOfStudentSummaryReports(parameters);
	}
	
	@Override
	public List<ExternalStudentReportDTO> getStudentSummaryReportsByRosterId(Map<String, Object> parameters) {
		return externalstudentreportsMapper.getStudentSummaryReportsByRosterId(parameters);
	}
	
	@Override
	public Long countOfStudentSummaryReportsByRosterId(Map<String, Object> parameters) {
		return externalstudentreportsMapper.countOfStudentSummaryReportsByRosterId(parameters);
	}
	
	@Override
	public List<OrganizationReportDetails> getAllSchoolSummaryReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getAllSchoolSummaryReports(parameters);
	}
	
	@Override
	public List<OrganizationReportDetails> getAllTeacherNamesForClassroomReports(Map<String, Object> parameters) {
		return organizationReportDetailsMapper.getAllTeacherNamesForClassroomReports(parameters);
	}
		
	@Override
	public List<String> getDistinctAssessmentCode(Long assessmentProgramId, Long stateId, Long schoolYear,Long testingProgramId, String reportCycle, Long gradeCourseId, Long contentAreaId) {
		return externalstudentreportsMapper.getDistinctAssessmentCode(assessmentProgramId, stateId, schoolYear, testingProgramId, reportCycle, gradeCourseId, contentAreaId);
	}

	@Override
	public List<Long> getStudentIdsForReportGeneration(Long assessmentProgramId, Long schoolYear, Long stateId, String assessmentCode, String reportCycle, Long testingProgramId, Long gradeCourseId, Long contentAreaId, String processByStudentId, String reprocessEntireDistrict, Integer pageSize, Integer offset) {
		return externalstudentreportsMapper.getStudentIdsForReportGeneration(assessmentProgramId, schoolYear, stateId, assessmentCode, reportCycle, testingProgramId, gradeCourseId, contentAreaId, processByStudentId, reprocessEntireDistrict, pageSize, offset);
	}

	@Override
	public ExternalStudentReportDTO getStudentForReportGeneration(Long assessmentProgramId, Long schoolYear, Long stateId, String assessmentCode, String reportCycle, Long testingProgramId, String processByStudentId, String reprocessEntireDistrict, Long studentId) {
		return externalstudentreportsMapper.getStudentForReportGeneration(assessmentProgramId, schoolYear, stateId, assessmentCode, reportCycle, testingProgramId, processByStudentId, reprocessEntireDistrict, studentId);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentReportFilePath(ExternalStudentReportDTO externalstudentreport){
		externalstudentreportsMapper.updateStudentReportFilePath(externalstudentreport);		
	}

	@Override
	public List<Long> getStudentIdsFromStudentReport(Long assessmentProgramId,
			Long schoolYear, Long stateId, Long gradeCourseId,
			Long contentAreaId, String processByStudentId,
			String reprocessEntireDistrict, int offset, int pageSize) {
		
		return studentReportMapper.getStudentIdsFromStudentReport(assessmentProgramId, schoolYear, stateId, gradeCourseId, contentAreaId, processByStudentId, reprocessEntireDistrict, offset, pageSize);
	}

	@Override
	public List<StudentReport> getStudentForKELPAReport(
			Long assessmentProgramId, Long schoolYear, Long stateId,
			Long studentId, Long gradeCourseId, Long contentAreaId, String processByStudentId,
			String reprocessEntireDistrict) {
		return studentReportMapper.getStudentForKELPAReport(assessmentProgramId, schoolYear, stateId, studentId, processByStudentId, reprocessEntireDistrict, gradeCourseId, contentAreaId);
	}
}