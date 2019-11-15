package edu.ku.cete.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.CcqScore;
import edu.ku.cete.domain.ScoringAssignment;
import edu.ku.cete.domain.ScoringAssignmentScorer;
import edu.ku.cete.domain.ScoringAssignmentStudent;
import edu.ku.cete.domain.ScoringUploadFile;
import edu.ku.cete.domain.StudentsTestScore;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.TaskVariant;
import edu.ku.cete.domain.test.StimulusVariant;
import edu.ku.cete.domain.test.TestSectionTaskVariantDetails;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.CcqScoreService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ScoringAssignmentServices;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.exception.EpServiceException;
import edu.ku.cete.service.exception.NoAccessToResourceException;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.FileUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.web.AssignScorerStudentScorerCriteriaDTO;
import edu.ku.cete.web.MonitorCcqScorersDetailsDTO;
import edu.ku.cete.web.MonitorScorerStudentRemainderDTO;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;
import edu.ku.cete.web.ScoreTestScoringCriteriaDTO;
import edu.ku.cete.web.ScoreTestSelfToHarmDTO;
import edu.ku.cete.web.ScoreTestTestSessionDTO;
import edu.ku.cete.web.ScorerStudentResourcesDTO;
import edu.ku.cete.web.ScorerTestSessionStudentDTO;
import edu.ku.cete.web.ScorerTestStudentsSessionDTO;
import edu.ku.cete.web.ScorersAssignScorerDTO;
import edu.ku.cete.web.UserDTO;

@Controller
public class ScoringController {
	
	private static final Logger logger = LoggerFactory
			.getLogger(ScoringController.class);

	private ObjectMapper mapper = new ObjectMapper();
	 
	private static final String VIEW_SCORING_TAB_JSP = "/scoring/scoringtab";
	
	@Autowired
	private AwsS3Service s3;
	
	@Autowired
	private DownloadController downloadController;	
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ScoringAssignmentServices scoringAssignmentServices;
	
	@Autowired
	private CcqScoreService ccqScoreTestService;
	
	@Autowired
	private ContentAreaService contentAreaService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	PermissionUtil permissionUtil;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Value("${epservice.url}")
	private String epServiceURL;
	
	@Value("${print.test.file.path}")
	private String DOWNLOAD_FILEPATH;

	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "viewScoringTab.htm")
	public final ModelAndView viewScorer(){
		logger.trace("Entering the viewScorer() method");
		ModelAndView mv = new ModelAndView(VIEW_SCORING_TAB_JSP);
		logger.trace("Leaving the viewScorer() method");
		return mv;
		
	}
	

	@RequestMapping(value = "manageScore.htm")
	public final ModelAndView manageScorer(){
		logger.trace("Entering the manageScorer() method");
		ModelAndView mv = new ModelAndView("new-settings/managescore");
		logger.trace("Leaving the manageScorer() method");
		return mv;
		
	}
	
	@RequestMapping(value = "myScore.htm")
	public final ModelAndView myScore(){
		logger.trace("Entering the myScore() method");
		ModelAndView mv = new ModelAndView("new-settings/myscore");
		logger.trace("Leaving the myScore() method");
		return mv;
		
	}
	
	
	
	@RequestMapping(value = "getAssignScorerScorersView.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getAssignScorerScorersView(@RequestParam("stateId") Long stateId,
    		@RequestParam("assessmentPrgId") Long assessmentPrgId,
    		@RequestParam("districtId") Long districtId,
    		@RequestParam("schoolId") Long schoolId,	
    		@RequestParam("filters") String filters ) throws NoAccessToResourceException {		
        Map<String, Object> assignScorersScorerMap = new HashMap<String, Object>();
		TimerUtil timerUtil = TimerUtil.getInstance();
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
		 int orgLevel = userDetails.getUser().getAccessLevel();
		if(orgLevel >= 70){
			districtId = schoolId;
			stateId = schoolId;
		}else if(orgLevel >= 50){
			stateId = districtId; 
		}
			
	        List<ScorersAssignScorerDTO> assignScorerScorersRecords = 
	        		userService.getScorersByAssessmentProgramId(stateId,
      				assessmentPrgId,districtId, schoolId);	       
	        assignScorersScorerMap.put("rows", assignScorerScorersRecords);
        timerUtil.resetAndLog(logger, "Getting student records took");
        timerUtil.resetAndLog(logger, "Counting student records took ");
        logger.debug("Leaving the getStudentInformationRecords page.");
        return assignScorersScorerMap;
    }
	@RequestMapping(value = "getAssignScorerTestsessionStudents.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getAssignScorerTestSessionStudents(
    		@RequestParam("testSessionId") Long testSessionId,
    		@RequestParam("subjectId") Long subjectId,
    		@RequestParam("gradeId") Long gradeId,
    		@RequestParam(value="userIds[]" , required=false)  Long [] userIds,    		  		
    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
        Map<String, Object> testsessionStudentsMap = new HashMap<String, Object>();     
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();          
    	List<ScorerTestSessionStudentDTO> testSessionStudentsRecords =testSessionService.getTestSectionStudents(testSessionId,subjectId,userDetails.getUser().getContractingOrganization().getCurrentSchoolYear(),gradeId);       
    	testsessionStudentsMap.put("rows", testSessionStudentsRecords);		 
        return testsessionStudentsMap;
    }
	
	private Map<String,String> constructTestSessionRecordFilterCriteria(String filters) {
    	Map<String,String> testSessionCriteriaMap = new HashMap<String, String>();
    	if(null != filters && !filters.equals("")) {
	    	RecordBrowserFilter recordBrowserFilter = null;
	    	//Parse the column filter values which the user enters on the UI record browser grid.
	        try {
	        	recordBrowserFilter = mapper.readValue(filters, 
	        			new TypeReference<RecordBrowserFilter>() {});
	        } catch(JsonParseException e) {
	            logger.error("Couldn't parse json object.", e);
	        } catch (JsonMappingException e) {
	            logger.error("Unexpected json mapping", e);
	        } catch (SecurityException e) {
	            logger.error("Unexpected exception with reflection", e);
	        } catch (IllegalArgumentException e) {
	            logger.error("Unexpected exception with reflection", e);
	        } catch (Exception e) {
	            logger.error("Unexpected error", e);
	        }
	        
	    	
	    	if(recordBrowserFilter != null) {
		    	for(RecordBrowserFilterRules recordBrowserFilterRules : recordBrowserFilter.getRules()) {
		    		testSessionCriteriaMap.put(recordBrowserFilterRules.getField(), 
		    				CommonConstants.PERCENTILE_DELIM + recordBrowserFilterRules.getData().trim() + CommonConstants.PERCENTILE_DELIM);	    		
		    	}
	    	}
    	}
    	return testSessionCriteriaMap;
    }
	@RequestMapping(value = "checkAssignScoringStudentScorer.htm", method = RequestMethod.POST)
	public final @ResponseBody List<AssignScorerStudentScorerCriteriaDTO> checkAssignScoringStudentScorer(
			@RequestParam("testSessionId") Long testSessionId,
			@RequestParam("studentIds[]") Long[] studentIds,
			@RequestParam("scorerIds[]") Long[] scorerIds){
		
		
		return scoringAssignmentServices.checkAssignScoringStudentScorer(testSessionId, studentIds, scorerIds);
	}
	
	@RequestMapping(value = "createScoringAssignments.htm", method = RequestMethod.POST)
	public final @ResponseBody String createScoringAssignments(
			@RequestParam("testSessionId") Long testSessionId,
			@RequestParam("studentIdTestIds[]") String [] studentIdTestIds,
			@RequestParam("scorerIds[]") Long [] scorerIds,
			@RequestParam("testName") String testName
			){

		
		String success = "failed";
		if( testName == null || testName.trim().length() == 0 ){
			success = "Test name can not be empty";
		}
		else if( studentIdTestIds == null || studentIdTestIds.length == 0){
			success = "No student(s) selected";
		}
		else if( scorerIds == null || scorerIds.length == 0){
			success = "No scorer(s) selected";
		}
		else{
			Integer testNameCount = scoringAssignmentServices.checkUniqueCcqTestName(testName);
			if( testNameCount > 0 ){
				success = "Duplicate";
			}
			else if( testSessionId != null ){
				 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
			        		).getAuthentication().getPrincipal();  
				
				Date now = new Date();
				
				ScoringAssignment scoringAssignment = new ScoringAssignment();
				Integer userId =userDetails.getUser().getId().intValue();
				scoringAssignment.setCreatedUser( userId);
				scoringAssignment.setTestSessionId(testSessionId);
				scoringAssignment.setCreatedDate(now);
				scoringAssignment.setActive(true);
				scoringAssignment.setSource(SourceTypeEnum.MANUAL.getCode());
				scoringAssignment.setCcqTestName(testName.trim());
				List<ScoringAssignmentStudent> scoringAssignmentStudentList = new ArrayList<ScoringAssignmentStudent>();
				String [] studentDetailsRow = null;
				for(String studentTestId : studentIdTestIds ){
					ScoringAssignmentStudent scoringAssignmentStudent = new ScoringAssignmentStudent();
					studentDetailsRow = studentTestId.split("#");
					
		/*			//Temporary logic to populate enrollment and test id begin (Plan to bring from UI student grid)
						StudentsTests stTest = studentsTestsService.selectByPrimaryKey(Long.parseLong(studentDetailsRow[3]));
					//Temporary logic end
*/					scoringAssignmentStudent.setStudentsTestsId(Long.parseLong(studentDetailsRow[3]));
					scoringAssignmentStudent.setStudentId(Long.parseLong(studentDetailsRow[0]));
					scoringAssignmentStudent.setEnrollmentId(Long.parseLong(studentDetailsRow[1]));
					scoringAssignmentStudent.setTestId(Long.parseLong(studentDetailsRow[2]));
					scoringAssignmentStudent.setCreatedUser(userId);
					scoringAssignmentStudent.setCreatedDate(now);
					scoringAssignmentStudent.setActive(true);
					scoringAssignmentStudentList.add(scoringAssignmentStudent);
				}
				scoringAssignment.setScoringAssignmentStudent(scoringAssignmentStudentList);
				
				List<ScoringAssignmentScorer> scoringAssignmentScorerList = new ArrayList<ScoringAssignmentScorer>();
				for(Long scorerId : scorerIds ){
					ScoringAssignmentScorer scoringAssignmentScorer = new ScoringAssignmentScorer();
					scoringAssignmentScorer.setScorerid(scorerId);
					scoringAssignmentScorer.setCreatedUser(userId);
					scoringAssignmentScorer.setCreatedDate(now);
					scoringAssignmentScorer.setActive(true);
					scoringAssignmentScorerList.add(scoringAssignmentScorer);
				}
				scoringAssignment.setScoringAssignmentScorer(scoringAssignmentScorerList) ;
				
				scoringAssignmentServices.addOrUpdate(scoringAssignment);
			 
				success = "success";
			}
			else{
				success = "TestSession id can not be empty";
			}
		}	
		return "{\"result\": \""+success+"\"}";	
	}

	@RequestMapping(value = "getStudentTestScoringCriteria.htm", method = RequestMethod.GET)
    public final @ResponseBody List<ScoreTestScoringCriteriaDTO> getStudentTestScoringCriteria(
    		@RequestParam("testSessionId") Long testSessionId,
    		@RequestParam("studentId") Long studentId,
    		@RequestParam("testsId") Long testsId,
    		@RequestParam("variantValue") Long variantValue){
		List<ScoreTestScoringCriteriaDTO> scoreTestScoringCriteriaDTOs=new ArrayList<ScoreTestScoringCriteriaDTO>();
		List<Float> diffRentScores=new ArrayList<Float>();
		scoreTestScoringCriteriaDTOs=scoringAssignmentServices.getStudentTestScoringCriteria(testSessionId, studentId, testsId,variantValue);
		for(ScoreTestScoringCriteriaDTO scoringCriteriaDto:scoreTestScoringCriteriaDTOs){
			scoringCriteriaDto.setLastRow(false);
			if(!diffRentScores.contains(scoringCriteriaDto.getScore()))
			diffRentScores.add(scoringCriteriaDto.getScore());
		}
		
		if(scoreTestScoringCriteriaDTOs!=null && scoreTestScoringCriteriaDTOs.size()>0){/*
			int Size=diffRentScores.size();
			for(int i=0;i<diffRentScores.size();i++){
			ScoreTestScoringCriteriaDTO newDTO=new ScoreTestScoringCriteriaDTO();
			newDTO.setRubricCatergoryId(0l);
			newDTO.setLastRow(true);
			newDTO.setRubricCatName(new String());
			if(i==Size-1){
			newDTO.setRubricInfoDesc("Total Score");
			}else{
				newDTO.setRubricInfoDesc(new String());
			}
			newDTO.setScore(diffRentScores.get(i));
			scoreTestScoringCriteriaDTOs.add(newDTO);
			}
			*/}
		return scoreTestScoringCriteriaDTOs; 
	}
    		
	@RequestMapping(value = "createScoringScoreTest.htm", method = RequestMethod.POST)
	public final @ResponseBody String createScoreTest(
			@RequestParam("scoringAssignmentId") Long scoringAssignmentId,
			@RequestParam("scoringAssignmentStudentId") Long scoringAssignmentStudentId,
			@RequestParam("scoringAssignmentScorerId") Long scoringAssignmentScorerId,
			@RequestParam("rubricCategoryIdsScores[]") String [] rubricCategoryIdsScores,
			@RequestParam("taskVariantId[]") Long [] taskVariantIds,
			@RequestParam("nonScoringReason") Long nonScoringReason,
			@RequestParam("scoringStatus") String scoringStatus,
			@RequestParam("studentsTestId") Long studentsTestId,
			@RequestParam("testId") Long testId,
			@RequestParam(value="isScored", defaultValue="false") boolean isScored
			){
		
		String success = "failed";
		StringBuilder returnStr = new StringBuilder();
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		
		String [] rubricCategoryIdScoreRow = null;
		int score = 0;
		//New code  Begins
		List<StudentsTestScore> studentsTestScoreList = new ArrayList<StudentsTestScore>();	
		if( scoringAssignmentId != null ){
			for(Long taskVariantId :taskVariantIds){
				for(String rubricCategoryIdScore : rubricCategoryIdsScores ){
					StudentsTestScore studentsTestScore = new StudentsTestScore();
					rubricCategoryIdScoreRow = rubricCategoryIdScore.split("#");
					studentsTestScore.setRubriccategoryid(Long.parseLong(rubricCategoryIdScoreRow[0]));
					studentsTestScore.setScore((rubricCategoryIdScoreRow.length > 1 && rubricCategoryIdScoreRow[1].trim().length()>0)? Integer.parseInt(rubricCategoryIdScoreRow[1]):0);
					studentsTestScore.setNonscorereason(nonScoringReason);
					studentsTestScore.setScorerid(userDetails.getUser().getId());
					studentsTestScore.setTaskvariantid(taskVariantId);
					studentsTestScore.setStudensTsestId(studentsTestId);
					studentsTestScore.setCreatedUser(userDetails.getUser().getId());
					studentsTestScore.setTestId(testId);
					studentsTestScore.setSource(SourceTypeEnum.MANUAL.getCode());
					studentsTestScore.setActiveFlag(true);
					
					studentsTestScoreList.add(studentsTestScore);
				}			
		  }
			scoringAssignmentServices.soreTest(studentsTestScoreList);
			success = "success";
			returnStr.append("\"alltestsession\": \"PENDING\"");
			returnStr.append(",\"currenttestsession\": \"PENDING\"");
	   }		
		
		//New code end	
		return "{\"result\": \""+success+"\"," + returnStr + "}";	
	}
	
	
	@RequestMapping(value = "getTestScorerAssignedTestSessions.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getTestScorerTestSessionStudents(
    		@RequestParam("assessmentProgramId") Long assessmentPrgId,   
    	 	@RequestParam("subjectId") Long subjectId,
    		@RequestParam("gradeId") Long gradeId,
    		@RequestParam(value = "schoolId", required = false) Long schoolId,
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
		return getScoringDetails(assessmentPrgId, subjectId, gradeId, schoolId,
				limitCountStr, page, sortByColumn, sortType, filters, false);
    }
	
	@RequestMapping(value = "getMonitorScoresScoringDetails.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getScoresTestScoringDetails(
    		@RequestParam("districtId") Long districtId,
    		@RequestParam("schoolId") Long schoolId,
    		@RequestParam("assessmentPrgId") Long assessmentPrgId,
    	 	@RequestParam("subjectId") Long subjectId,
    		@RequestParam("gradeId") Long gradeId,
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
     
		/*return getScoringDetails(assessmentPrgId, subjectId, gradeId,
				limitCountStr, page, sortByColumn, sortType, filters, true);*/

		Map<String, Object> testScorertestSessionMap = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
           
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        
        String assessmentProgramCode = userDetails.getUser().getCurrentAssessmentProgramName();
        
        limitCount = NumericUtil.parse(limitCountStr,5);
           
        Map<String,String> scorerTestSessionRecordCriteriaMap = constructTestSessionRecordFilterCriteria(filters);
        
       // Long stateId = userDetails.getUser().getContractingOrganization().getId();
        
        Long stateId = userDetails.getUser().getCurrentOrganizationId();
        //TimerUtil timerUtil = TimerUtil.getInstance();
        Long distictId = null;
        if(userDetails.getUser().getAccessLevel() == 50){
        	distictId = stateId;
        	stateId = null;
        } 
        if(userDetails.getUser().getAccessLevel() == 70){
        	schoolId = stateId;
        	stateId = null;
        }
		/*totalCount =  scoringAssignmentServices.getCountTestSessionAndStudentCountForScorer(districtId,schoolId,stateId, assessmentPrgId,
				subjectId, gradeId, 
        		sortByColumn, sortType, scorerTestSessionRecordCriteriaMap,distictId,currentSchoolYear,assessmentProgramCode) ;
*/
		 
		//if( totalCount > 0){
			
	        List<ScoreTestTestSessionDTO> assignScorertestsessionRecords = scoringAssignmentServices.
	        		getTestSessionAndStudentCountForScorer(districtId,schoolId,
	        		stateId, assessmentPrgId, subjectId, gradeId, 
	        		sortByColumn, sortType, (currentPage-1)*limitCount,
	        		limitCount, scorerTestSessionRecordCriteriaMap,distictId,currentSchoolYear,assessmentProgramCode); 
	        
	        if(assignScorertestsessionRecords.size() > 0)
		        totalCount = assignScorertestsessionRecords.get(0).getTotalrecords();
	        testScorertestSessionMap.put("rows", assignScorertestsessionRecords);
		//}
		      
        
        testScorertestSessionMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
        testScorertestSessionMap.put("records", totalCount);
        testScorertestSessionMap.put("page", currentPage);
      
        return testScorertestSessionMap;	
    }
	
	@RequestMapping(value = "getMonitorHarmToSelfDetails.htm", method = RequestMethod.POST)
 	private final @ResponseBody Map<String, Object> getScoresHarmToSelfDetails(@RequestParam("rows") String limitCountStr,@RequestParam("page") String page,
 			@RequestParam("sidx") String sortByColumn, @RequestParam("sord") String sortType,
 			@RequestParam(value="filters",required=false) String filters)
 			throws JsonProcessingException, IOException {
 		
 		Map<String,String> monitorSelfToHarmFilters = constructTestSessionRecordFilterCriteria(filters);
 		
 		int currentPage = -1;
		int limitCount = -1;
		int totalCount = -1;

		currentPage = NumericUtil.parse(page, 1);
		limitCount = NumericUtil.parse(limitCountStr, 5);
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        Long assessmentPrgId = userDetails.getUser().getCurrentAssessmentProgramId();
        
        Long orgId = userDetails.getUser().getCurrentOrganizationId();
        Long nonscorerid=categoryService.selectByCategoryCodeAndType("HSO", "KELPA_NON_SCORE_REASON_CODE").getId();
       	Map<String, Object> responseMap = new HashMap<String, Object>();
 		List<ScoreTestSelfToHarmDTO> studentList = scoringAssignmentServices.getScoresHarmToSelfDetails(nonscorerid,orgId,sortByColumn, sortType,monitorSelfToHarmFilters, (currentPage - 1) * limitCount, limitCount, currentSchoolYear, assessmentPrgId);
 		if(studentList.size() > 0){	
 			totalCount = studentList.get(0).getTotalRecords();
 		}
 		
 		responseMap.put("rows", studentList);
 		responseMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		responseMap.put("page", currentPage);
		responseMap.put("records", totalCount);
 		
 		return responseMap;
 	}
	
	private Map<String, Object> getScoringDetails(Long assessmentPrgId,
    	 	Long subjectId,Long gradeId,Long schoolId, String limitCountStr,
    	 	String page,String sortByColumn,String sortType, String filters,boolean monitorCcqScore){
		Map<String, Object> testScorertestSessionMap = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
           
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
           
        Map<String,String> scorerTestSessionRecordCriteriaMap = constructTestSessionRecordFilterCriteria(filters);
        
        String assessmentProgramCode = userDetails.getUser().getCurrentAssessmentProgramName();
        Long stateId = userDetails.getUser().getContractingOrganization().getId();
		//TimerUtil timerUtil = TimerUtil.getInstance();
		Long userId = userDetails.getUser().getId();
		boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
		Long schoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		if(isScoreAllTest){
			stateId = schoolId;
			userId = null;			
		}else{
			stateId = userDetails.getUser().getCurrentOrganizationId();
		}
	        List<ScoreTestTestSessionDTO> assignScorertestsessionRecords = scoringAssignmentServices.
	        		getTestSessionAndStudentCountForScorer( userId,
	        		stateId, assessmentPrgId, subjectId, gradeId, 
	        		sortByColumn, sortType, (currentPage-1)*limitCount,
	        		limitCount, scorerTestSessionRecordCriteriaMap, isScoreAllTest,assessmentProgramCode, schoolYear); 
	        
	        if(assignScorertestsessionRecords.size() > 0)
 	        totalCount = assignScorertestsessionRecords.get(0).getTotalrecords();
	        testScorertestSessionMap.put("rows", assignScorertestsessionRecords);
		//}
		      
        
        testScorertestSessionMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
        testScorertestSessionMap.put("records", totalCount);
        testScorertestSessionMap.put("page", currentPage);
      
        return testScorertestSessionMap;
	}
	static Comparator<ContentArea> contentAreaComparator = new Comparator<ContentArea>() {
		public int compare(ContentArea ca1, ContentArea ca2) {
			return ca1.getName().compareToIgnoreCase(ca2.getName());
		}
	};
	
	@RequestMapping(value = "getScoreTestContentAreasByAssessmentProgram.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ContentArea> getContentAreasByAssessmentProgramId(
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
  			@RequestParam("schoolId") Long schoolId) {
		logger.trace("Entering getContentAreasByAssessmentProgramId");
		  UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
		//Long scorerId = userDetails.getUser().getId();
		//Long stateId = userDetails.getUser().getCurrentOrganizationId();
		//boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
		//if(!isScoreAllTest){
		//	stateId =  userDetails.getUser().getContractingOrganization().getId();
		//}
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		List<ContentArea> contentAreas = scoringAssignmentServices.findByContentArea(assessmentProgramId,schoolId,currentSchoolYear);
		Collections.sort(contentAreas, contentAreaComparator);

		logger.trace("Leaving getContentAreasByAssessmentProgramId");
		return contentAreas;
	}
	
	static Comparator<GradeCourse> gradeCourseComparator = new Comparator<GradeCourse>(){
		   public int compare(GradeCourse gc1, GradeCourse gc2){
		   		return gc1.getName().compareToIgnoreCase(gc2.getName());
		   	}
	};
	
	@RequestMapping(value = "getScoreTestGradeCourseByContentAreaId.htm", method = RequestMethod.GET)
  	public final @ResponseBody List<GradeCourse> getGradeCourseByContentAreaIdForTestManagement(
  			@RequestParam("contentAreaId") Long contentAreaId,
  			@RequestParam("assessmentProgramId") Long assessmentProgramId,
  			@RequestParam("schoolId") Long schoolId) {
  		logger.trace("Entering the getGradeCourseByContentAreaIdForTestCoordination() method");
  		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
		//  Long scorerId = userDetails.getUser().getId();
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		//Long.valueOf(currentSchoolYear);
		//Long stateId = userDetails.getUser().getCurrentOrganizationId();
		//boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
		//if(!isScoreAllTest){
		//	stateId =  userDetails.getUser().getContractingOrganization().getId();
		//}
  		List<GradeCourse> gradeCourses = scoringAssignmentServices.getScoreTestGradeCourseByContentAreaId(contentAreaId,currentSchoolYear,assessmentProgramId,schoolId);     
  		Collections.sort(gradeCourses, gradeCourseComparator);		

  		logger.trace("Leaving the getGradeCourseByContentAreaIdForTestCoordination() method");
  		return gradeCourses;
  	 }
	
	@RequestMapping(value = "getscorerStudentsAppearsForScoreTest.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getscorerStudentsAppearsForScoreTest(
    		@RequestParam("scoringAssignmentId") Long scoringAssignmentId,
    		@RequestParam("assessmentProgramId") Long assessmentProgramId     	
    	) throws NoAccessToResourceException {
        Map<String, Object> testsessionStudentsMap = new HashMap<String, Object>();
        
        //Map<String,String> scorerTestStudentRecordCriteriaMap = constructTestSessionRecordFilterCriteria(filters);
		List<Object> columnModelLists = new ArrayList<Object>();
		List<Object> columnNameLists = new ArrayList<Object>();
		
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        
        boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
       	List<ScorerTestStudentsSessionDTO> testSessionStudentsRecords =
    			scoringAssignmentServices.getScorerStudentsAppearsForScoreTests(scoringAssignmentId, assessmentProgramId, userDetails.getUserId(), currentSchoolYear, isScoreAllTest);
       	
        int	totalCount = testSessionStudentsRecords.size();
       	
        if( totalCount > 0){
        	
           List<Object> totalLists = new ArrayList<Object>();
        	List<String> dynamiccolumn=new ArrayList<String>();
    		if(testSessionStudentsRecords!=null && testSessionStudentsRecords.size()>0){
    			 dynamiccolumn=getNumberOfDynamicColumn(testSessionStudentsRecords);
    		}
        	 
        	if(testSessionStudentsRecords!=null && testSessionStudentsRecords.size()>0){
        		int index=1;
        			for(ScorerTestStudentsSessionDTO studentSessionDTO : testSessionStudentsRecords){
        				 Map<Object,Object> resultMap = new HashMap<Object, Object>();
        				 resultMap.put("id", studentSessionDTO.getId());
        				 resultMap.put("studentsTestsId", studentSessionDTO.getStudentsTestsId());
        				 resultMap.put("scoringStudentId", studentSessionDTO.getScoringStudentId());
        				 resultMap.put("firstName", studentSessionDTO.getFirstName());
        				 resultMap.put("mi", studentSessionDTO.getMi());
        				 resultMap.put("lastName", studentSessionDTO.getLastName());
        				 resultMap.put("stateStudentIdentifier", studentSessionDTO.getStateStudentIdentifier());
        				 resultMap.put("districtName", studentSessionDTO.getDistrictName());
        				 resultMap.put("schoolName", studentSessionDTO.getSchoolName());
        				 resultMap.put("testingProgramName", studentSessionDTO.getTestingProgramName());
        				 resultMap.put("grade", studentSessionDTO.getGrade());
        				 resultMap.put("status", studentSessionDTO.getStatus());
        				 resultMap.put("testsId", studentSessionDTO.getTestsId());
        				 resultMap.put("testName", studentSessionDTO.getTestName());
        				 resultMap.put("studentNameIdentifier",  studentSessionDTO.getFirstName()+" "+((studentSessionDTO.getMi()==null||studentSessionDTO.getMi().equalsIgnoreCase("null")) ?"":studentSessionDTO.getMi())+" "+studentSessionDTO.getLastName()+" - "+studentSessionDTO.getStateStudentIdentifier());
        				 if(studentSessionDTO.getTaskVariantDetails()!=null && studentSessionDTO.getTaskVariantDetails().size()>0){
        					 Map<Object,Object> dynamicColumnForStudent=new HashMap<Object,Object>();
        					 dynamicColumnForStudent= getMapforeachstident(dynamiccolumn,studentSessionDTO,CommonConstants.SCORING_MONITOR_SCORE_TEST); 
        					if(dynamicColumnForStudent!=null && !dynamicColumnForStudent.isEmpty()){
               				 resultMap.putAll(dynamicColumnForStudent);
               				 }
        				 }
        				 resultMap.put("rowIndex", index);
        				 totalLists.add(resultMap);
        				 index++;
        			}
        	}        	
        	
        	testsessionStudentsMap.put("rows", totalLists);
        	
            for(String column:dynamiccolumn){
    			Map<Object, Object> colmodal = new HashMap<Object, Object>();
    			colmodal.put("label", column);
    			colmodal.put("name",  column);
    			colmodal.put("index",  column);
    			colmodal.put("width", 80);
    			colmodal.put("search", false);
    			colmodal.put("sortable", false);
    			colmodal.put("hidden", false);		
    			colmodal.put("hidedlg", true);
    			    			
    		 	if(column.contains("-")){
            		String[] columns = column.split("-");
            		columnNameLists.add(columns[0]+"-"+columns[columns.length-1]);
            	}else{
            		columnNameLists.add(column);
            	}         	
    			
    			columnModelLists.add(colmodal);
    		}
        	
        	
        }else{
        	testsessionStudentsMap.put("rows", 0);
        } 
		
        testsessionStudentsMap.put("ColNames", columnNameLists);
        testsessionStudentsMap.put("ColModel", columnModelLists);
        testsessionStudentsMap.put("numberOfDynamicColumn", columnModelLists.size()); 
    	testsessionStudentsMap.put("records", totalCount);
    
        return testsessionStudentsMap;
    }


	@RequestMapping(value = "checkAllTestSessionStudentsScoredForScorer.htm", method = RequestMethod.GET)
	public final @ResponseBody String checkAllTestSessionStudentsScoredForScorer(){
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
	    
		Long scorerId = userDetails.getUser().getId();
		
		int count = ccqScoreTestService.getCountOfAllTestSessionStudentsToScoreForScorer(scorerId);
		if(count == 0 ){
			return "{\"status\": \"COMPLETED\"}";
		}
		return "{\"status\": \"PENDING\"}";
	}
			
	@RequestMapping(value = "checkAllStudentInTestSessionScoredForScorer.htm", method = RequestMethod.GET)
	public final @ResponseBody String checkAllStudentInTestSessionScoredForScorer(
			@RequestParam("scoringAssignmentId") Long scoringAssignmentId){
		
 	    UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
 	    
		Long scorerId = userDetails.getUser().getId();
		
		int count = ccqScoreTestService.getCountOfStudentsInTestSessionToScoreForScorer(scorerId,scoringAssignmentId);
		if(count == 0 ){
			return "{\"status\": \"COMPLETED\"}";
		}
		return "{\"status\": \"PENDING\"}";
	}		

	
	@RequestMapping(value = "getMonitorCcqScoresTestDetails.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getMonitorCcqScoresTestDetails(
    		@RequestParam("scoringAssignmentId") Long scoringAssignmentId
    		) throws NoAccessToResourceException {
        Map<String, Object> ccqScoreMap = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        String sortByColumn = "id",sortType="asc",limitCountStr="10",page="1", filters = null; 
        
        Map<String,String> scorerTestStudentRecordCriteriaMap = constructTestSessionRecordFilterCriteria(filters);
        
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        
        List<MonitorCcqScorersDetailsDTO> monitorCcqScorersDetails = ccqScoreTestService.getMonitorCcqScoresDetails(scoringAssignmentId, 
				currentSchoolYear, sortByColumn, sortType, (currentPage-1)*limitCount,
				limitCount, scorerTestStudentRecordCriteriaMap);
        
        if( monitorCcqScorersDetails.size() > 0){
        	ccqScoreMap.put("rows", convertToGridFormat(monitorCcqScorersDetails));
        }else{
        	ccqScoreMap.put("rows", 0);
        }
        
        ccqScoreMap.put("total", NumericUtil.getPageCount(totalCount, limitCount));
        ccqScoreMap.put("records", totalCount);
        ccqScoreMap.put("page", currentPage);
        return ccqScoreMap;
    }
	
	private List<ArrayList<String>> convertToGridFormat(List<MonitorCcqScorersDetailsDTO> monitorCcqScorersDetails){
		List<ArrayList<String>> gridFormat = new ArrayList<ArrayList<String>>();
		
		String prevStuId = null;
		String prevScoringAssignmentStudentId = null;
		MonitorCcqScorersDetailsDTO  prevMonitorCcqScorersDTO = null;
		ArrayList<String> scorDetails = null;
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("id");
		headers.add("scorerstudentId");
		headers.add("lastName");
		headers.add("firstName");
		for(MonitorCcqScorersDetailsDTO monitorCcqScorersDTO : monitorCcqScorersDetails){
			
			// DE13407 - modified to show all the student which same test session but with different tests
			if( prevStuId == null || !prevScoringAssignmentStudentId.equals(monitorCcqScorersDTO.getScoringAssignmentStudentId().toString()) ){
				if( prevMonitorCcqScorersDTO != null){
					scorDetails.add(prevMonitorCcqScorersDTO.getDistrictName());
					scorDetails.add(prevMonitorCcqScorersDTO.getSchoolName());
					scorDetails.add(prevMonitorCcqScorersDTO.getTestingProgramName());
					scorDetails.add(prevMonitorCcqScorersDTO.getStateStudentIdentifier());
					if( gridFormat.size() < 1 ){
						headers.add("district");
						headers.add("school");
						headers.add("testingprogram");
						headers.add("stateStudentIdentifier");
						gridFormat.add(headers);	
					}	
					gridFormat.add(scorDetails);
				}
				scorDetails = new ArrayList<String>();
				scorDetails.add(monitorCcqScorersDTO.getStudentId().toString());
				scorDetails.add(monitorCcqScorersDTO.getScoringAssignmentStudentId().toString());
				scorDetails.add(monitorCcqScorersDTO.getLastName());
				scorDetails.add(monitorCcqScorersDTO.getFirstName());
				
				prevMonitorCcqScorersDTO = monitorCcqScorersDTO;
			}
			scorDetails.add(monitorCcqScorersDTO.getScored());
			
			prevStuId = monitorCcqScorersDTO.getStudentId().toString();
			prevScoringAssignmentStudentId = monitorCcqScorersDTO.getScoringAssignmentStudentId().toString();
			if( gridFormat.size() < 1 )
				headers.add(monitorCcqScorersDTO.getScorerId()+"-"+monitorCcqScorersDTO.getScoringAssignmentScorerId()+"-"+monitorCcqScorersDTO.getScorerFirstName()+"-"+monitorCcqScorersDTO.getScorerLastName());
		}
		scorDetails.add(prevMonitorCcqScorersDTO.getDistrictName());
		scorDetails.add(prevMonitorCcqScorersDTO.getSchoolName());
		scorDetails.add(prevMonitorCcqScorersDTO.getTestingProgramName());
		scorDetails.add(prevMonitorCcqScorersDTO.getStateStudentIdentifier());
		if( gridFormat.size() < 1 ){
			headers.add("district");
			headers.add("school");
			headers.add("testingprogram");
			headers.add("stateStudentIdentifier");
			gridFormat.add(headers);	
		}	
		gridFormat.add(scorDetails);
		
		return gridFormat; 
	}
	
	@RequestMapping(value = "getMonitorCCQScoresDetails.htm", method = RequestMethod.POST)
    public final @ResponseBody UserDTO getMonitorCCQScoresDetails(
    		@RequestParam("scorerId") Long scorerId
    		) throws NoAccessToResourceException {
		
		return userService.getMonitorCCQScoresDetails(scorerId);
	}
	
	@RequestMapping(value = "monitorScorerSendRemainder.htm", method = RequestMethod.POST)
    public final @ResponseBody String monitorScorerSendRemainder(
    		@RequestParam("subject") String subject,
    		@RequestParam("pathway") String pathWay,
    		@RequestParam("CCQTestName") String CCQTestName,
    		@RequestParam("monitorassessmentProgram") Long monitorassessmentProgram,
    		@RequestParam("monitorScorerStudentDetails[]") String [] monitorScorerStudentDetails,
    		@RequestParam("monitorScorerStudent") String  monitorScorerStudent) throws JsonParseException, JsonMappingException, IOException{
		
		boolean mailSend = false;
		List<MonitorScorerStudentRemainderDTO> monitorCCQScorerList = new ArrayList<MonitorScorerStudentRemainderDTO>();
		if(monitorScorerStudent != null && monitorScorerStudent.trim().length() > 0){
			monitorScorerStudentDetails = new String [ 1 ];
			monitorScorerStudentDetails [ 0 ] = monitorScorerStudent;
			
		}
		for(String details : monitorScorerStudentDetails ){
			
			MonitorScorerStudentRemainderDTO scorerReminder = mapper.readValue(details, MonitorScorerStudentRemainderDTO.class);
			monitorCCQScorerList.add(scorerReminder);
		}
		if(monitorCCQScorerList.size() > 0){
			Collections.sort(monitorCCQScorerList, monitorScorerStudentRemainderDTOComparator);
		
			Long prevScorer = null;
			Long prevScoringAssignmentScorerId = null;
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();  
			List<Long> studentIds = new ArrayList<Long>();
			for (MonitorScorerStudentRemainderDTO monitorScorerStudentRemainderDTO : monitorCCQScorerList) {
				
				if(prevScorer != null && !monitorScorerStudentRemainderDTO.getScorerId().equals(prevScorer )){
					if(studentIds.size() > 0){
						try {
							mailSend = true;
							monitorScorerMailSend(prevScorer,studentIds,userDetails,subject,pathWay,CCQTestName,monitorassessmentProgram,prevScoringAssignmentScorerId);
						} catch (ServiceException e) {
							// TODO Auto-generated catch block

						}
						studentIds.clear();
					}
				}
				if(monitorScorerStudentRemainderDTO.getType().equals("R")){
					studentIds.add(monitorScorerStudentRemainderDTO.getStudentId());					
				}
				prevScorer = monitorScorerStudentRemainderDTO.getScorerId();
				prevScoringAssignmentScorerId = monitorScorerStudentRemainderDTO.getScoringAssignmentScorerId();
				addMonitorCCQScorerTest(monitorScorerStudentRemainderDTO,userDetails);
				
			}
			if(studentIds.size() > 0){
				try {
					mailSend = true;
					monitorScorerMailSend(prevScorer,studentIds,userDetails,subject,pathWay,CCQTestName,monitorassessmentProgram,prevScoringAssignmentScorerId);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					
				}	
			}
			
		}
		return "{\"result\": \"success\",\"mailSend\": "+mailSend+"}";	
	}
	
	/**
	 * Added for F454(EP Drop-Down Menus Display Choices Relevant to User)
	 * @param assessmentProgramId
	 * @param testingProgramId
	 *            {@link long}
	 * @return List<contentAreas>
	 */
	
	
	
	private void addMonitorCCQScorerTest(MonitorScorerStudentRemainderDTO monitorScorerStudent,UserDetailImpl userDetails ){
		
		Long isCheckStudentAndScorer = ccqScoreTestService.isCheckStudentAndScorer(monitorScorerStudent.getScoringAssignmentScorerId(),monitorScorerStudent.getScoringAssignmentStudentId());
		if( isCheckStudentAndScorer != null && isCheckStudentAndScorer.intValue() > 0){
			CcqScore ccqScore = new CcqScore();
			ccqScore.setId(Long.valueOf(isCheckStudentAndScorer));
			ccqScore.setScoringAssignmentScorerId(monitorScorerStudent.getScoringAssignmentScorerId());
			ccqScore.setScoringAssignmentStudentId(monitorScorerStudent.getScoringAssignmentStudentId());
			if(monitorScorerStudent.getType().equals("R")){
				ccqScore.setExcludeFlag(false);
				ccqScore.setNotificationFlag(true);
			}else {
				ccqScore.setExcludeFlag(true);
				ccqScore.setNotificationFlag(false);
			}
			ccqScoreTestService.updateScorerAndStudent(ccqScore);
		} else {
			CcqScore ccqScore = new CcqScore();
			ccqScore.setScoringAssignmentScorerId(monitorScorerStudent.getScoringAssignmentScorerId());
			ccqScore.setScoringAssignmentStudentId(monitorScorerStudent.getScoringAssignmentStudentId());
			ccqScore.setIsScored(false);
			
			ccqScore.setTotalScore(0);
			ccqScore.setCreatedDate(new Date());
			ccqScore.setCreatedUser(userDetails.getUser().getId().intValue());
			ccqScore.setActive(true);
			if(monitorScorerStudent.getType().equals("R")){
				ccqScore.setExcludeFlag(false);
				ccqScore.setNotificationFlag(true);
			}else {
				ccqScore.setExcludeFlag(true);
				ccqScore.setNotificationFlag(false);
			}
			ccqScoreTestService.add(ccqScore);
		}		
	}
	private void monitorScorerMailSend(Long scorerId,List<Long> studentId,UserDetailImpl userDetails
			,String subject,String pathway,String CCQTestName,Long monitorassessmentProgram,Long prevScoringAssignmentScorerId) throws ServiceException{
		
		UserDTO scorerDetails = userService.getMonitorCCQScoresDetails(scorerId);
		List<ScorerTestStudentsSessionDTO> studentDetails =  scoringAssignmentServices.getMonitorCCQStudentDetails(studentId);
		String stageName = scoringAssignmentServices.getMonitorStageDetails(prevScoringAssignmentScorerId);
		emailService.sendMonitorScorerMsg(scorerDetails,studentDetails,userDetails,subject,pathway,CCQTestName,monitorassessmentProgram,stageName);
		
	}
	static Comparator<MonitorScorerStudentRemainderDTO> monitorScorerStudentRemainderDTOComparator = new Comparator<MonitorScorerStudentRemainderDTO>(){
		   public int compare(MonitorScorerStudentRemainderDTO msr1, MonitorScorerStudentRemainderDTO msr2){
		   		return msr1.getScorerId().compareTo(msr2.getScorerId());
		   	}
	};
	
	/*@RequestMapping(value = "getTheColumnStructure.htm", method = RequestMethod.POST)
	public final @ResponseBody  Map<String, Object>  getTheColumnStracture(
			@RequestParam("scoringAssignmentId") Long scoringAssignmentId,
    		@RequestParam("testsessionId") Long testsessionId) {
		Map<String, Object> columnStructure=new HashMap<String, Object>();
		//we have to call the dao to get the column structure
		
		List<Object> columnModelLists = new ArrayList<Object>();
		List<Object> columnNameLists = new ArrayList<Object>();
		
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		Integer currentSchoolYear= userDetails.getUser().getContractingOrganization().getCurrentSchoolYear().intValue();
		boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
		
		List<ScorerTestStudentsSessionDTO> scorerTestStudentsSessionDtos = scoringAssignmentServices.getscorerTestStudentsSessionDTOForDynamicColumn(scoringAssignmentId,testsessionId,currentSchoolYear,userDetails.getUserId(),isScoreAllTest);
		List<String> dynamiccolumn=new ArrayList<String>();
			
		if(scorerTestStudentsSessionDtos!=null && scorerTestStudentsSessionDtos.size()>0){
			 dynamiccolumn=getNumberOfDynamicColumn(scorerTestStudentsSessionDtos);
		}	
	    for(String column:dynamiccolumn){
				Map<Object, Object> colmodal = new HashMap<Object, Object>();
				colmodal.put("label", column);
				colmodal.put("name",  column);
				colmodal.put("index",  column);
				colmodal.put("width", 5);
				colmodal.put("search", false);
				colmodal.put("sortable", false);
				colmodal.put("hidden", false);		
				colmodal.put("hidedlg", true);
				columnNameLists.add(column);
				columnModelLists.add(colmodal);
		}
		
		columnStructure.put("ColNames", columnNameLists);
		columnStructure.put("ColModel", columnModelLists);
		columnStructure.put("numberOfDynamicColumn", columnModelLists.size());
		return columnStructure;
	}*/
	static Comparator<TestSectionTaskVariantDetails> taskvarientComparator = new Comparator<TestSectionTaskVariantDetails>() {
        public int compare(TestSectionTaskVariantDetails t1, TestSectionTaskVariantDetails t2) {
        	Integer t1position = NumericUtil.parse(t1.getTaskVariantPosition(), -1);
        	Integer t2position = NumericUtil.parse(t2.getTaskVariantPosition(), -1);
            return t1position.compareTo(t2position);
        }
    };
	private List<String> getNumberOfDynamicColumn(List<ScorerTestStudentsSessionDTO> scorerTestStudentsSessionDtos){
		List<String> distinctcolumns=new ArrayList<String>();
		
		for(ScorerTestStudentsSessionDTO scorerTestStudentsSessionDTO:scorerTestStudentsSessionDtos){
			Map<Object,String> studentTestletColumn=new HashMap<Object,String>();
			List<TestSectionTaskVariantDetails> testletcolumns=new ArrayList<TestSectionTaskVariantDetails>();
			for(TestSectionTaskVariantDetails taskVariant:scorerTestStudentsSessionDTO.getTaskVariantDetails()){
				if(!taskVariant.isClusterScoring()){
					String tempdistinctcolumn=null;
						
					if(!distinctcolumns.contains(taskVariant.getTaskVariantPosition())){
						tempdistinctcolumn=taskVariant.getTaskVariantPosition();
					}
					if(tempdistinctcolumn!=null)
						distinctcolumns.add(tempdistinctcolumn);
					}else{
						testletcolumns.add(taskVariant);
					}
			}
			 Collections.sort(testletcolumns,taskvarientComparator);
			for(TestSectionTaskVariantDetails testletcol:testletcolumns){
				if(studentTestletColumn.containsKey(testletcol.getTestletId())){
					String colname=studentTestletColumn.get(testletcol.getTestletId());
					colname=colname+"-"+testletcol.getTaskVariantPosition();
					
					studentTestletColumn.put(testletcol.getTestletId(), colname);
				
				}else{
					studentTestletColumn.put(testletcol.getTestletId(), testletcol.getTaskVariantPosition());
				}
			}
			
			List<String> distinctTestletcolumns=new ArrayList<String>();
			
			for(Entry<Object, String> colname:studentTestletColumn.entrySet()){
				
				distinctTestletcolumns.add(colname.getValue());
				
			}
			Collections.sort(distinctTestletcolumns,columnComparator);
			Collections.reverse(distinctTestletcolumns);
			for(String colName : distinctTestletcolumns){
				if(!distinctcolumns.contains(colName)){
					distinctcolumns.add(0,colName);
				
				}
				
			}
			
		}
		List<String> taskColNames = new ArrayList<String>();
		for(String column : distinctcolumns){
			if(column.contains("-")){
        		String[] columns = column.split("-");
        		taskColNames.add(columns[0]+"-"+columns[columns.length-1]);
        	}else{
        		taskColNames.add(column);
        	}    
		}
		Collections.sort(taskColNames,testletColumnComparator);
		return taskColNames;
	}
	static Comparator<String> columnComparator = new Comparator<String>(){
	   	public int compare(String gc1, String gc2){
	   		return gc1.compareToIgnoreCase(gc2);
	   	}
	};
	private Map<Object,Object> getMapforeachstident(List<String> distinctcolumns,ScorerTestStudentsSessionDTO studentSessionDTO,String screenName){
		Map<Object,Object> dynamicColumnForStudent=new HashMap<Object,Object>();
		Map<Object,String> studentTestletColumn=new HashMap<Object,String>();
		Map<Object,String> studentTestletvarints=new HashMap<Object,String>();
		List<TestSectionTaskVariantDetails> testletcolumns=new ArrayList<TestSectionTaskVariantDetails>();
		for(TestSectionTaskVariantDetails taskVariant:studentSessionDTO.getTaskVariantDetails()){
			if(!taskVariant.isClusterScoring()){
				String tempdistinctcolumn=null;
				if(!distinctcolumns.contains(taskVariant.getTaskVariantPosition())){
					tempdistinctcolumn=taskVariant.getTaskVariantPosition();
				}
				if(tempdistinctcolumn!=null)
				distinctcolumns.add(tempdistinctcolumn);
		}else{					
			testletcolumns.add(taskVariant);
		  }
		}
		Collections.sort(testletcolumns,taskvarientComparator);
		for(TestSectionTaskVariantDetails testletcol:testletcolumns){
			if(studentTestletColumn.containsKey(testletcol.getTestletId())){
				String colname=studentTestletColumn.get(testletcol.getTestletId());
				colname=colname+"-"+testletcol.getTaskVariantPosition();
				if(colname.contains("-")){
	        		String[] columns = colname.split("-");
	        		
	        		studentTestletColumn.put(testletcol.getTestletId(), columns[0]+"-"+columns[columns.length-1]);
	        	}else{
	        		studentTestletColumn.put(testletcol.getTestletId(), colname);
	        	}
				
			
			}else{
				studentTestletColumn.put(testletcol.getTestletId(), testletcol.getTaskVariantPosition());
			}
			if(studentTestletvarints.containsKey(testletcol.getTestletId())){
				String VariantIds=studentTestletvarints.get(testletcol.getTestletId());
				VariantIds=VariantIds+"-"+testletcol.getTaskVariantId();
				studentTestletvarints.put(testletcol.getTestletId(), VariantIds);
			
			}else{
				studentTestletvarints.put(testletcol.getTestletId(), testletcol.getTaskVariantId());
			}
		}
		
		for(TestSectionTaskVariantDetails taskVariant:studentSessionDTO.getTaskVariantDetails()){
			if( !taskVariant.isPositionChanged()){
			if(studentTestletColumn.containsKey(taskVariant.getTestletId()) && taskVariant.isClusterScoring()){
				taskVariant.setTaskVariantPosition(studentTestletColumn.get(taskVariant.getTestletId()));
				taskVariant.setPositionChanged(true);
			}
			if(studentTestletvarints.containsKey(taskVariant.getTestletId()) && taskVariant.isClusterScoring()){
				taskVariant.setTaskVariantId(studentTestletvarints.get(taskVariant.getTestletId()));
			}
			}
		}
		for(String id:distinctcolumns){
			if(studentSessionDTO!=null && studentSessionDTO.getTaskVariantDetails()!=null && studentSessionDTO.getTaskVariantDetails().size()>0){
				String value=null;
				for(TestSectionTaskVariantDetails taskVariant:studentSessionDTO.getTaskVariantDetails()){
					if(taskVariant.getTaskVariantPosition().equalsIgnoreCase(id)){
						StringBuffer columnvalue=new StringBuffer();
						if(screenName.equals(CommonConstants.SCORING_MONITOR_SCORES)){
						columnvalue.append(taskVariant.getVariantScore()==null ? "":taskVariant.getVariantScore());
						if(taskVariant.getVariantNonScoreReason()!=null){
						columnvalue.append("-"+taskVariant.getVariantNonScoreReason());
						}
						id=id.replace("-", "--");
						}else if(screenName.equals(CommonConstants.SCORING_MONITOR_SCORE_TEST)) 
						{
							columnvalue.append(taskVariant.getTaskVariantId());
							columnvalue.append(taskVariant.getVariantStatus());
							columnvalue.append("_");							
							columnvalue.append(taskVariant.getTaskVariantId().contains("-") ? taskVariant.getTestletId():"un");
							columnvalue.append("_");
							columnvalue.append(taskVariant.getStimulusFlag()? "True":"False");
						}
						value=columnvalue.toString();
						dynamicColumnForStudent.put(id, value);
						break;
					}else{
						if(screenName.equals(CommonConstants.SCORING_MONITOR_SCORES)){
						value="N/A";
						}else{
							value="ID_N/A";
						}
						dynamicColumnForStudent.put(id, value);
					}
				
				}
			}else{
				if(screenName.equals(CommonConstants.SCORING_MONITOR_SCORES)){
					dynamicColumnForStudent.put(id, "N/A");
					}else{
						dynamicColumnForStudent.put(id, "ID_N/A");
					}
				
			}
		}
		return dynamicColumnForStudent;
	}
	
	@RequestMapping(value="getScorerStudentResources.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ScorerStudentResourcesDTO> getScorerStudentResources(@RequestParam("testSessionId") Long testSessionId){
		
		List<ScorerStudentResourcesDTO> scorerStudentResourcesDTO = scoringAssignmentServices.getScorerStudentResources(testSessionId);
		
		return scorerStudentResourcesDTO;
	}
	
	
	
	
	
	@RequestMapping(value="getnonScoreReasons.htm", method = RequestMethod.POST)
	public final @ResponseBody List<Category> getStudnetNonScoringReason(){
		
		List<Category> nonScoringReason = categoryService.selectByCategoryType("KELPA_NON_SCORE_REASON_CODE");
		Comparator<Category> alphabetical = new Comparator<Category>(){
			public int compare(Category o1, Category o2) {
				return o1.getCategoryName().compareTo(o2.getCategoryName());
			}
    	};
    	Collections.sort(nonScoringReason, alphabetical);
		
		return nonScoringReason;
	}
	
	@RequestMapping(value="getPromptAndStudentResponse.htm", method = RequestMethod.POST)
	public final @ResponseBody List<TaskVariant> getPromptAndStudentResponse(
		@RequestParam("taskVariantId[]") Long[] taskVariantIds,
		@RequestParam("studentId") Long studentId,
		@RequestParam("studentsTestsId") Long studentsTestsId){				
		List<TaskVariant> promptAndStudentResponse = testService.getPromptAndStudentResponse(taskVariantIds,studentId,studentsTestsId);		
		return promptAndStudentResponse;
	}		
	
	@RequestMapping(value = "getUploadScoresTestSessions.htm", method = RequestMethod.POST)
	public final @ResponseBody List<TestSession> getUploadScoresTestSessions(
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("subjectId") Long subjectId,
			@RequestParam("gradeId") Long gradeId,
			@RequestParam("stageId")Long stageId,
			@RequestParam("schoolIds[]") Long[] schoolIds) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();
		long currentSchoolYear =  (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		Long scorerId = userDetails.getUser().getId();
		String assessmentCode = userDetails.getUser().getCurrentAssessmentProgramName();
		//Long stateId = userDetails.getUser().getCurrentOrganizationId();
		boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
		
		
		List<TestSession> testSessionsUploadScores = scoringAssignmentServices.getUploadScoresTestSessions(
				assessmentProgramId, assessmentCode, subjectId, gradeId, stageId, schoolIds, scorerId, isScoreAllTest,currentSchoolYear);
		//Collections.sort(contentAreas, contentAreaComparator);

		return testSessionsUploadScores;
	}
	
	@RequestMapping(value = "getUploadScoresStage.htm", method = RequestMethod.POST)
	public final @ResponseBody List<Stage> getUploadScoresStage(
			@RequestParam("schoolId[]") Long[] schoolId,
			@RequestParam("subjectId") Long subjectId,
			@RequestParam("gradeId") Long gradeId) {
		//LOGGER.trace("Entering getContentAreasByAssessmentProgramId");
		List<Stage> stageUploadScores = scoringAssignmentServices.getUploadScoresStage(schoolId,subjectId,gradeId);
		//Collections.sort(contentAreas, contentAreaComparator);	
		return stageUploadScores;
	}	
	
	@RequestMapping(value = "getUploadScoreSubject.htm", method = RequestMethod.POST)
	public final @ResponseBody List<ContentArea> getUploadScoreSubject(
			@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("schoolId[]") Long[] schoolId) {
		
		logger.trace("Entering getUploadScoreSubject");
		  UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
		Long scorerId = userDetails.getUser().getId();
		
		boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
		
		List<ContentArea> contentAreas = scoringAssignmentServices.getUploadScoreSubject(assessmentProgramId,scorerId,isScoreAllTest,schoolId);
		Collections.sort(contentAreas, contentAreaComparator);

		logger.trace("Leaving getUploadScoreSubject");
		return contentAreas;
	}
	
	@RequestMapping(value = "getUploadScoresGradeBySubjectId.htm", method = RequestMethod.GET)
  	public final @ResponseBody List<GradeCourse> getUploadScoresGradeBySubjectId(
  			@RequestParam("subjectId") Long subjectId,
  			@RequestParam("schoolId[]") Long[] schoolId) {
  		logger.trace("Entering the getGradeCourseByContentAreaIdForTestCoordination() method");
  		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
		Long scorerId = userDetails.getUser().getId();
		//Long stateId = userDetails.getUser().getCurrentOrganizationId();
		boolean isScoreAllTest = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_SCORE_ALL_TEST");
		/*if(!isScoreAllTest){
			stateId =  userDetails.getUser().getContractingOrganization().getId();
		}*/
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
  		List<GradeCourse> gradeCourses = scoringAssignmentServices.getUploadScoresGradeBySubjectId(subjectId,scorerId,isScoreAllTest,schoolId,currentSchoolYear);     
  		Collections.sort(gradeCourses, gradeCourseComparator);		

  		logger.trace("Leaving the getGradeCourseByContentAreaIdForTestCoordination() method");
  		return gradeCourses;
  	 }

	@RequestMapping(value = "s3Credentials.htm", method = RequestMethod.GET)
	@ResponseBody
	public final String gets3Credentials(@RequestParam("filename") final String filename) throws Exception{
		try{
			return studentService.gets3Credentials(filename);
		}catch(Exception e){
			logger.error("s3Credentials :", e);
			throw new EpServiceException("error.studentHome.s3Credentials");
		}		
	}
	
	@RequestMapping(value = "getStudentsToBeScored.htm", method = RequestMethod.POST)
	public final @ResponseBody String getStudentsToBeScoredData(@RequestParam(value="districtId" , required=false) Long districtId,
			@RequestParam(value="schoolIds[]" , required=false)  Long [] schoolIds,
			@RequestParam(value="testSessionIds[]" , required=false)  Long [] testSessionIds,
			@RequestParam(value="contentAreaId" , required=false)  Long contentAreaId,
			@RequestParam(value="gradeId", required=false)  Long gradeId,
			@RequestParam(value="stageId", required=false)  Long stageId,
			@RequestParam(value="includeItem", required=false) boolean includeItem) throws IOException {
			
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
	        		).getAuthentication().getPrincipal();
    		String fileName = "";	
    		logger.info("Entering the students to be scored method.");
		
    		fileName = scoringAssignmentServices.getScoringAssignmentStudentsList(districtId,schoolIds,testSessionIds,contentAreaId,gradeId,stageId,userDetails.getUser().getContractingOrganization().getCurrentSchoolYear(),includeItem);
    		
		return fileName;
	}
	
	
	@RequestMapping(value = "getScoringAssignmentScoreFile.htm")
	public final void getExternalStudentReportFile(final HttpServletRequest request, final HttpServletResponse response, String fileName)
			throws IOException {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
    			SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			ScoringUploadFile file = scoringAssignmentServices.getScoringAssignmentUploadFile(fileName);
			if (file != null) {
				String path = FileUtil.buildFilePath(DOWNLOAD_FILEPATH, file.getFilePath());
				if (s3.doesObjectExist(path)){
					downloadController.download(request, response, path);
				} else {
					response.sendError(404, "Student report not found.");
				}
			} else {
				response.sendRedirect("permissionDenied.htm"); 
			}
		}
	}
	
	static Comparator<String> testletColumnComparator = new Comparator<String>(){		
        public int compare(String o1, String o2) {  
        	if(o1.contains("-")) {
        		o1 =o1.split("-")[0];
        	}
        	if(o2.contains("-")) {
        		o2 =o2.split("-")[0];
        	}
            return Integer.valueOf(o1).compareTo(Integer.valueOf(o2));
        }
    };
    
    //Added by debasis 
    @RequestMapping(value = "getStageByGradeIdForAssignScorers.htm", method = RequestMethod.GET)
  	public final @ResponseBody List<Stage> getStageByGradeIdForAssignScorers(
  			@RequestParam("assessmentProgramId") Long assessmentProgramId,
  			@RequestParam("schoolId") Long schoolId,@RequestParam("contentAreaId") Long contentAreaId,@RequestParam("gradeId") Long gradeId) {
  		logger.trace("Entering the getGradeCourseByContentAreaIdForTestCoordination() method");
  		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
  		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
  		String assessmentCode =  userDetails.getUser().getCurrentAssessmentProgramName();
		List<Stage> stageUploadScores = scoringAssignmentServices.getStageByGradeIdForAssignScorers(assessmentProgramId,schoolId,contentAreaId,gradeId,currentSchoolYear,assessmentCode);
  		return stageUploadScores;
  	 }  
    @RequestMapping(value = "getGradeCourseByContentAreaIdForAssignScorers.htm", method = RequestMethod.GET)
  	public final @ResponseBody List<GradeCourse> getGradeCourseByContentAreaIdForAssignScorers(
  			@RequestParam("assessmentProgramId") Long assessmentProgramId,
  			@RequestParam("schoolAreaId") Long schoolAreaId,@RequestParam("contentAreaId") Long contentAreaId) {
  		logger.trace("Entering the getGradeCourseByContentAreaIdForTestCoordination() method");
  		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
				int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
  		List<GradeCourse> gradeCourses = scoringAssignmentServices.getGradeCourseByContentAreaIdForAssignScorers(assessmentProgramId,schoolAreaId,contentAreaId,currentSchoolYear);     
  		Collections.sort(gradeCourses, gradeCourseComparator);		
  		logger.trace("Leaving the getGradeCourseByContentAreaIdForTestCoordination() method");
  		return gradeCourses;
  	 }    
   
  	 
    
    
    
   @RequestMapping(value = "getStimulusContent.htm", method = RequestMethod.POST)
	public final @ResponseBody  List<StimulusVariant> getStimulusContent(@RequestParam(value="testletId" , required=false) String mediaVariantId,
			@RequestParam(value="taskVariantId" , required=false) String taskVariantId 
			) throws IOException {
    	Long testletId=-1l;
    	Long taskVariantIdForStimulus=-1l;
    	if(!StringUtils.isEmpty(mediaVariantId)){
    		testletId=Long.valueOf(mediaVariantId);
    	}
    	if(!StringUtils.isEmpty(taskVariantId)){
    		taskVariantIdForStimulus=Long.valueOf(taskVariantId);
    	}
    	List<StimulusVariant> stimulusVariant=new ArrayList<>();
    	
    	if(!testletId.equals(-1l)){
    	 stimulusVariant=ccqScoreTestService.getStimulusVariant(testletId);
    		//String stimulusContent = "{\"Content\": \""+stimulusVariant.getStimulusContent()+"\"}";	
    	}else{
    		 stimulusVariant=ccqScoreTestService.getStimulusVariantByTaskVariantId(taskVariantIdForStimulus);
    	}
    		
		return stimulusVariant;
	}
	
    @RequestMapping(value = "getStudentTestMonitorScore.htm", method = RequestMethod.POST)
  	public final @ResponseBody  Map<String, Object> getStudentTestMonitorScore(
  			@RequestParam(value="assessmentProgramId" , required=true) Long assessmentProgram,
  			@RequestParam(value="schoolId" , required=true) Long school,
  			@RequestParam(value="contentAreaId" , required=true) Long contentArea,
  			@RequestParam(value="gradeId" , required=true) Long grade,
  			@RequestParam(value="stageId" , required=true) Long stage)  {
    	logger.trace("Entering the getStudentTestMonitorScore() method");

  		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
    	
		List<ScorerTestStudentsSessionDTO> monitorScoreList = null;
		
		monitorScoreList=scoringAssignmentServices.getStudentTestMonitorScore(assessmentProgram,school,contentArea,grade,stage,currentSchoolYear);
		
		List<String> sectionTaskColumnNames = getNumberOfDynamicColumn(monitorScoreList);
		List<Object> allStudentsResponsesList = new ArrayList<Object>();
		Map<String, Object> allStudentsMonitorMap = new LinkedHashMap<String, Object>();
		int index=1;
		for(ScorerTestStudentsSessionDTO studentSessionDTO : monitorScoreList){
			 Map<Object,Object> resultMap = new HashMap<Object, Object>();
			 resultMap.put("id", studentSessionDTO.getId());
			 resultMap.put("Student_First_Name", studentSessionDTO.getFirstName().concat(" " + studentSessionDTO.getLastName()));
			 resultMap.put("Student_Last_Name", studentSessionDTO.getLastName());
			 resultMap.put("State_Student_ID", studentSessionDTO.getStateStudentIdentifier());
			 resultMap.put("Overall_Status", studentSessionDTO.getStatus());
			 resultMap.put("Stage", studentSessionDTO.getStage());
			 resultMap.put("Scorer", studentSessionDTO.getEducatorFirstName());
			 if(studentSessionDTO.getTaskVariantDetails()!=null && studentSessionDTO.getTaskVariantDetails().size()>0){
				 Map<Object,Object> dynamicColumnForStudent=new HashMap<Object,Object>();
				 dynamicColumnForStudent= getMapforeachstident(sectionTaskColumnNames,studentSessionDTO,CommonConstants.SCORING_MONITOR_SCORES); 
				if(dynamicColumnForStudent!=null && !dynamicColumnForStudent.isEmpty()){
  				 resultMap.putAll(dynamicColumnForStudent);
  				 }
			 }
			 resultMap.put("rowIndex", index);
			 allStudentsResponsesList.add(resultMap);
			 index++;
		}
		
		List<String> taskColLabels = new ArrayList<String>();
		List<String> taskColNames = new ArrayList<String>();
		for(String column : sectionTaskColumnNames){
			if(column.contains("-")){
        		String[] columns = column.split("-");
        		taskColLabels.add(columns[0]+"-"+columns[columns.length-1]);
        	}else{
        		taskColLabels.add(column);
        	}    
		}
		for(String column : sectionTaskColumnNames){
			if(column.contains("-")){
        		String[] columns = column.split("-");
        		taskColNames.add(columns[0]+"--"+columns[columns.length-1]);
        	}else{
        		taskColNames.add(column);
        	}    
		}
		allStudentsMonitorMap.put("sectionStatusColumnNames", taskColNames);
		allStudentsMonitorMap.put("sectionTaskColumnLabels", taskColLabels);
		allStudentsMonitorMap.put("students", allStudentsResponsesList);
		//allStudentsMonitorMap.put("groupingHeaders", sectionGroupingHeaders);

		logger.trace("Leaving the getStudentTestMonitorScore() method");
		return allStudentsMonitorMap;
	
      		
      		
  		
  	}
    @RequestMapping(value = "getContentAreasByScorerAssessmentProgram.htm", method = RequestMethod.POST)
   	public final @ResponseBody List<ContentArea> getContentAreasByScorerAssessmentProgram(
   			final Long assessmentProgramId, Long schoolAreaId) {		
       	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
   				.getContext().getAuthentication().getPrincipal();
   		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
   		List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgramforAssignScorer(assessmentProgramId,schoolAreaId,currentSchoolYear);
   		Collections.sort(contentAreas, contentAreaComparator);
   		return contentAreas;
   	}   
   	
       @RequestMapping(value = "getAssignScorerTest.htm", method = RequestMethod.POST)
       public final @ResponseBody List<TestSession> getAssignScorerTestSession(
       		final Long assessmentProgramId,
       		 Long schoolId,@RequestParam(value="stageId" , required=false) Long stageId,
       		 Long subjectId,Long gradeId) {		
   		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
   				.getContext().getAuthentication().getPrincipal();
   		int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
   		List<TestSession> testSessionsassignScores = scoringAssignmentServices.getAssignScoresTestSessions(
   				assessmentProgramId, schoolId, stageId, subjectId, gradeId, currentSchoolYear, userDetails.getUser().getCurrentAssessmentProgramName());
   		return testSessionsassignScores;
   	}
 
}
