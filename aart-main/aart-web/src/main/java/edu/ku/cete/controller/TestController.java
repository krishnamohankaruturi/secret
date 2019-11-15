/**
 * 
 */
package edu.ku.cete.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.TraxSource;

import edu.ku.cete.configuration.SessionRulesConfiguration;
import edu.ku.cete.configuration.StudentsTestSectionsStatusConfiguration;
import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.StudentsResponsesReportDto;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestCollectionDTO;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.FoilCounter;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TaskVariantsFoils;
import edu.ku.cete.domain.content.Test;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.content.TestSection;
import edu.ku.cete.domain.content.TestSectionsTaskVariants;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentExample;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.enrollment.StudentTestSessionDto;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.TestCollectionTestsDao;
import edu.ku.cete.model.TestSectionsTaskVariantsDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.pdf.PDFGeneratorUtil;
import edu.ku.cete.score.util.ResponseLabelUtil;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsResponsesService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.service.studentsession.TestCollectionsSessionRulesService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.web.AssessmentProgramDto;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;
import edu.ku.cete.web.TestSessionPdfDTO;

/**
 * @author neil.howerton
 * TODO use the parameter object instead of request object.
 */
@Controller
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);
    
	@Value("/templates/xslt/students.xsl")
	private Resource studentsTicketsXslFile;
	
	@Value("classpath:reports.fop.conf.xml")
	private Resource reportConfigFile;
	
    //private static final String TEST_SESSION_JSP = "/test/testSession";
    private static final String MANAGE_TEST_SESSION_JSP = "/test/manageTestSession";
    private static final String MONITOR_TEST_SESSION_JSP = "/test/monitorTestSession";

    @Value("${testsession.status.unused}")
    private String TEST_SESSION_STATUS_UNUSED;

    @Value("${studentstests.status.unused}")
    private String STUDENT_TEST_STATUS_UNUSED;
    
    @Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;

    @Value("${studentstests.status.type}")
    private String STUDENT_TEST_STATUS_TYPE;
    
    @Autowired
    CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrgAssessmentProgramService orgAssessProgService;

    @Autowired
    private RosterService rosterService;

    @Autowired
    private StudentsTestsService studentTestsService;

    @Autowired
    private EnrollmentDao enrollmentDao;

    @Autowired
    private StudentsTestsService studentsTestsService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private StudentService studentService;
    
    @Autowired
    private StudentReportService studentReportService;

    @Autowired
    private GradeCourseService gradeCourseService;

    @Autowired
    private ContentAreaService contentAreaService;

    @Autowired
    private TestCollectionService testCollectionService;

    /**
     * for test status.
     */
    @Autowired
    private TestSessionService testSessionService;

    @Autowired
    private TestStatusConfiguration testStatusConfiguration;

    /**
     * resourceRestrictionService.
     */
    @Autowired
    private ResourceRestrictionService resourceRestrictionService;

    /**
     * permissionUtil.
     */
    @Autowired
    private PermissionUtil permissionUtil;

    /**
     * restrictedResourceConfiguration.
     */
    @Autowired
    private RestrictedResourceConfiguration restrictedResourceConfiguration;

    @Autowired
    private StudentsResponsesService studentsResponsesService;

    /**
     * {@link TestService}.
     */
    @Autowired
    private TestService testService;

    /**
     * testCollectionTestsDao.
     */
    @Autowired
    private TestCollectionTestsDao testCollectionTestsDao;
    
    /**
     * testingProgramService
     */
    @Autowired
    TestingProgramService testingProgramService;
    
    /**
     * assessmentService
     */
    @Autowired
    AssessmentService assessmentService;
    /**
     * studentsTestsStatusConfiguration.
     */
    @Autowired
	private StudentsTestsStatusConfiguration studentsTestsStatusConfiguration;
    /**
     * studentsTestSectionsStatusConfiguration.
     */
    @Autowired
	private StudentsTestSectionsStatusConfiguration studentsTestSectionsStatusConfiguration;
    /**
     * testCollectionsSessionRulesDao.
     */
    @Autowired
	private TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
	/**
	 * sessionRulesConfiguration.
	 */
	@Autowired
    private SessionRulesConfiguration sessionRulesConfiguration;
	 /**
     * testCollectionsSessionRulesService
     */
    @Autowired
    private TestCollectionsSessionRulesService testCollectionsSessionRulesService;
    /**
     * organizationDao
     */
    @Autowired
	private OrganizationDao organizationDao;
    /**
     * organizationService
     */
    @Autowired
	private OrganizationService organizationService;    
    /**
     * enrollmentService
     */
    @Autowired
    private EnrollmentService enrollmentService;
    
    /**
     * testSectionsTaskVariantsDao
     */
    @Autowired
    private TestSectionsTaskVariantsDao testSectionsTaskVariantsDao;
    /**
     * mapper
     */
    private ObjectMapper mapper = new ObjectMapper();
    
    @Value("${nfs.url}")
	private String MEDIA_PATH;

    /**
     * Displays the Manage Test Session page.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "manageTestSession.htm")
    public final ModelAndView viewManageTestSession(final HttpServletRequest request) {
        logger.trace("Enterind the viewManageTestSession() method.");
        ModelAndView mav = new ModelAndView(MANAGE_TEST_SESSION_JSP);

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        logger.trace("Getting TestingPrograms for user's current organization");
        mav.addObject("testingPrograms", getAssessmentProgramsForUser(user));
        mav.addObject("current", "manageTestSession");

        mav.addObject("invalidPdfParams", request.getAttribute("invalidPdfParams"));
        mav.addObject("noSessions", request.getAttribute("noSesssions"));

        logger.trace("Leaving the viewManageTestSession() method");
        return mav;
    }

    /**
     *
     * @param request
     *            {@link HttpServletRequest}
     * @param response
     *            {@link HttpServletResponse}
     * @return List<TestForm>
     */
    @RequestMapping(value = "findTests1.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> findTests(final HttpServletRequest request, final HttpServletResponse response) {
        logger.trace("Entering the findTests() method.");
        String keyword = request.getParameter("keyword");
        String id = request.getParameter("assessmentId");
        String[] subjects = request.getParameterValues("subject[]");
        String[] grades = request.getParameterValues("grade[]");

        logger.debug("Finding tests by keyword '{}', assessment Id with id {}, subjects {} and grades {}",
                new Object[] {keyword, id, subjects, grades});
 
        List<Long> subjectIds = null;
        List<Long> gradeIds = null;
        
        if (subjects != null) {
        	subjectIds = new ArrayList<Long>();
        }
        
        if (grades != null) {
        	gradeIds = new ArrayList<Long>();
        }
        
        if (keyword.length() == 0)
        	keyword = null;
       
        /*
        List<Long> subjectIds = new ArrayList<Long>();
        List<Long> gradeIds = new ArrayList<Long>(); 
         */
        List<TestCollectionDTO> testCollections = new ArrayList<TestCollectionDTO>();
        Map<String, Object> responseMap = new HashMap<String, Object>();

        if (subjects != null && subjects.length > 0) {
            for (String subject : subjects) {
                if (StringUtils.isNumeric(subject)) {
                    subjectIds.add(Long.parseLong(subject));
                } else {
                    responseMap.put("invalidParameters", true);
                }
            }
        }

        if (grades != null && grades.length > 0) {
            for (String grade : grades) {
                if (StringUtils.isNumeric(grade)) {
                    Long gradeId = Long.parseLong(grade);
                    if (gradeId > 0) {
                        gradeIds.add(gradeId);
                    }
                } else {
                    responseMap.put("invalidParameters", true);
                }
            }
        }

        if (StringUtils.isNumeric(id)) {
            testCollections = findTestsByCriteria(Long.parseLong(id), subjectIds, gradeIds, keyword);
            logger.debug("Returned test collections {} for search criteria.", testCollections);
            responseMap.put("tests", testCollections);
        } else {
            // respond with an error message.
            responseMap.put("invalidParameters", true);
        }

        logger.trace("Leaving the findTests() method.");
        return responseMap;
    }

    /**
     * Displays the Manage Test Session page.
     *
     * @param selectedTestCollectionId
     *            {@link Long}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "previewTest.htm")
    public final ModelAndView previewTestCollection(final @RequestParam Long selectedTestCollectionId, final @RequestParam Long selectedTestId) {
        logger.trace("Enterind the previewTest() method.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("previewTest");
        TestCollection testCollection = null;
        if (selectedTestCollectionId != null && selectedTestId !=null) {
        	Long statusId=testService.getTestStaus(selectedTestId);
            testCollection = testService.findContentByTestCollectionAndStatus(selectedTestCollectionId.longValue(), selectedTestId.longValue(),statusId);
        }
        mav.addObject("testCollection", testCollection);
        mav.addObject("foilCounter", new FoilCounter());
        mav.addObject("MEDIA_PATH", MEDIA_PATH);
        logger.trace("Leaving the previewTest() method");
        return mav;
    }
    /**
     * Displays the Manage Test Session page.
     *
     * @param selectedTestCollectionId
     *            {@link Long}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "previewNodes.htm")
    public final ModelAndView previewNodes(final @RequestParam Long selectedTestCollectionId) {
        logger.trace("Enterind the previewNodes() method.");
        ModelAndView mav = new ModelAndView();
        mav.setViewName("previewNodes");
        TestCollection testCollection = null;
        if (selectedTestCollectionId != null) {
            testCollection = testService.findNodeInfoByTestCollectionAndStatus(
            		selectedTestCollectionId.longValue(),
                    testStatusConfiguration.getPublishedTestStatusCategory(
                    		).getId().longValue());
        }
        mav.addObject("testCollection", testCollection);
        logger.trace("Leaving the previewNodes() method");
        return mav;
    }
    /**
     * TODO use the parameter object.
     * This method looks at all the test collections for the chosen assessment program and returns the grades, and subjects
     * for the test collections.
     * @param request
     * @return Map<String, Object>
     */
    @RequestMapping(value = "getGradesAndSubjectsForAP.htm", method = RequestMethod.GET)
    public final @ResponseBody Map<String, Object> getGradesAndSubjectsForAp(final HttpServletRequest request) {
        String apId = request.getParameter("assessmentProgramId");
        Map<String, Object> map = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(apId)) {
            Long assessmentProgramId = Long.parseLong(apId);
            List<GradeCourse> gradeCourses = gradeCourseService.findByAssessmentProgram(assessmentProgramId);

            Collections.sort(gradeCourses, new Comparator<GradeCourse>() {

                @Override
                public int compare(GradeCourse o1, GradeCourse o2) {
                    return o1.getGradeLevel() - o2.getGradeLevel();
                }
            });
            List<ContentArea> contentAreas = contentAreaService.findByAssessmentProgram(assessmentProgramId);

            map.put("grades", gradeCourses);
            map.put("subjects", contentAreas);
            map.put("valid", true);
        } else {
            // set an error message and return.
            map.put("valid", false);
        }

        return map;
    }

    /**
     * This method finds rosters that the currently logged in user is authorized to view.
     * @param request
     * @return List<Roster>
     */
    @RequestMapping(value = "getRostersByTeacher.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Roster> getRosters(final HttpServletRequest request, @RequestParam("selectedOrg") String selectedOrgId,
    		@RequestParam(value = "contentAreaId", required = false) Long contentAreaId) {
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //This will not have full impact un till more than one pool is defined.
        Restriction restriction = resourceRestrictionService.getResourceRestriction(
                userDetails.getUser().getOrganization().getId(), permissionUtil.getAuthorityId(userDetails.getUser()
                        .getAuthoritiesList(), RestrictedResourceConfiguration.getSearchRosterPermissionCode()), permissionUtil
                        .getAuthorityId(userDetails.getUser().getAuthoritiesList(),
                         RestrictedResourceConfiguration.getViewAllRostersPermissionCode()), restrictedResourceConfiguration
                        .getRosterResourceCategory().getId());
        
        List<Roster> tempRosters = null;
        List<Roster> rosters = null;
        if (selectedOrgId == null){
        	// restriction is null for teachers
        	tempRosters = rosterService.getRosterByUserRestrictionAndOrganization(userDetails.getUserId(), restriction,
        		userDetails.getUser().getOrganizationId());
        }else{
        	tempRosters = rosterService.getRosterByUserRestrictionAndOrganization(userDetails.getUserId(), restriction,
            		Long.parseLong(selectedOrgId));
        }
        if(contentAreaId != null && contentAreaId.longValue()>0 && tempRosters!=null){
        	rosters = new ArrayList<Roster>();
        	for(Roster roster : tempRosters){
        		if(roster.getStateSubjectAreaId() !=null && roster.getStateSubjectAreaId().longValue()>0
        				&& contentAreaId.longValue()==roster.getStateSubjectAreaId()){
        			rosters.add(roster);
        		}
        	}
        } else {
        	rosters = tempRosters;
        }
        
        return rosters;
    }
    /**
     * This method finds rosters that the currently logged in user is authorized to view.
     *  ------Old Create Test Session creation code - This can be deleted as we are no longer using.-------
     * @param request
     * @return List<Roster>
     */
    @Deprecated
    @RequestMapping(value = "getRosterStudentsByTeacher1.htm", method = RequestMethod.POST)
    public final @ResponseBody JsonResultSet getRosterStudents(
    		@RequestParam("orgChildrenIds") String[] orgChildrenIds,    		
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters,
    		@RequestParam("testSessionId") String testSessionId1) {
        logger.trace("Entering the getRosterStudentsByTeacher page for getting results");
                
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        JsonResultSet jsonResultSet = null;
        Long testSessionId = Long.parseLong(testSessionId1);
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        
        Map<String,String> studentRosterCriteriaMap = constructStudentRosterFilterCriteria(filters);
        
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
       
        List<StudentRoster> studentRosters = enrollmentService.getEnrollmentWithRoster(
				userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				sortByColumn, sortType, (currentPage-1)*limitCount,
				limitCount, studentRosterCriteriaMap,testSessionId, null, null, currentSchoolYear);        
        
        totalCount = enrollmentService.countEnrollmentWithRoster(userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				studentRosterCriteriaMap,testSessionId, null, null, currentSchoolYear);
        
        //jsonResultSet = StudentRosterJsonConverter.convertToStudentRosterJson1(
        //		studentRosters,totalCount,currentPage, limitCount,studentsTestsStatusConfiguration);
        
        logger.trace("Leaving the getRosterStudentsByTeacher page.");
        return jsonResultSet;
    }       
    
    /**
     * Method to build the map for StudentRosterFilter Criteria.
     * @param recordBrowserFilter
     * @return
     */
    private Map<String,String> constructStudentRosterFilterCriteria(String filters) {
    	logger.trace("Entering the constructStudentRosterFilterCriteria method");
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
        
    	Map<String,String> studentRosterCriteriaMap = new HashMap<String, String>();
    	if(recordBrowserFilter != null) {
	    	for(RecordBrowserFilterRules recordBrowserFilterRules : recordBrowserFilter.getRules()) {
	    		studentRosterCriteriaMap.put(recordBrowserFilterRules.getField(), 
	    				CommonConstants.PERCENTILE_DELIM + recordBrowserFilterRules.getData().trim() + CommonConstants.PERCENTILE_DELIM);	    		
	    	}
    	}
    	
    	logger.trace("Leaving the constructStudentRosterFilterCriteria method");
    	return studentRosterCriteriaMap;
    }
    
    /**
     * This method returns the students for a selected roster.
     * @param request {@link HttpServletRequest}
     * @return List<Enrollment>
     */
    @RequestMapping(value = "getStudentsForRoster.htm", method = RequestMethod.POST)
    public final @ResponseBody List<Enrollment> getStudentsForRoster(final HttpServletRequest request) {
        String roster = request.getParameter("roster");
        List<Enrollment> enrollments = new ArrayList<Enrollment>();

        if (StringUtils.isNumeric(roster)) {
            EnrollmentExample example = new EnrollmentExample();
            EnrollmentExample.Criteria criteria = example.createCriteria();
            criteria.andRosterIdEqualTo(Long.parseLong(roster));

            enrollments = enrollmentDao.getByCriteria(example);
        }

        return enrollments;
    }

    /**
     * TODO use the @param instead of using the request object.
     * Returns the student enrollment to test relationships for a selected roster.
     * @param request
     *            {@link HttpServletRequest}
     * @return Map<String, Object>
     */
    @RequestMapping(value = "getStudentsAndTestsForRoster.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getStudentsAndTestForRoster(final HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNumeric(request.getParameter("roster"))
                && StringUtils.isNumeric(request.getParameter("assessmentProgram"))) {
            long rosterId = Long.parseLong(request.getParameter("roster"));
            long apId = Long.parseLong(request.getParameter("assessmentProgram"));
            long tpId = Long.parseLong(request.getParameter("testingProgramId"));
            long aId = Long.parseLong(request.getParameter("assessmentId"));

            logger.debug("getStudentsAndTestForRoster enter with :"
            + rosterId + "," + apId + "," + tpId + "," + aId);
            List<Student> students = studentService.findByRoster(rosterId);

            map.put("students", students);

            List<StudentsTests> studentsTests = studentsTestsService.findByAssessmentIdAndRoster(aId, rosterId);

            map.put("studentsTests", studentsTests);

            //TODO use the test status configuration instead of this select.
            Category unused = categoryService.selectByCategoryCodeAndType(STUDENT_TEST_STATUS_UNUSED, STUDENT_TEST_STATUS_TYPE);

            map.put("notStartedStatus", unused);
        }

        return map;
    }

    /**
     * This method enrolls students to a test.
     * @param request {@link HttpServletRequest}
     * @return Map<String, Boolean>
     */
    @RequestMapping(value = "assignStudentsToTest1.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Boolean> assignStudentsToTest(final HttpServletRequest request) {
        logger.trace("Entering the assignStudentsToTest() method.");

        String[] strEnrollmentRosterIds = request.getParameterValues("students[]");
        String strTestCollectionId = request.getParameter("testCollectionId");
        String strTestId = request.getParameter("testId");
//        String strRosterId = request.getParameter("rosterId");
        String sessionName = request.getParameter("sessionName");
        Map<String, Boolean> map = new HashMap<String, Boolean>();

        // Create a test session
        boolean isValid = false;
        if (StringUtils.isNumeric(strTestCollectionId) && StringUtils.isNumeric(strTestId)) {
            isValid = true;
            long testCollectionId = Long.parseLong(strTestCollectionId);
            long testId = Long.parseLong(strTestId);
            Category unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED,
            		TEST_SESSION_STATUS_TYPE);
            TestSession testSession = new TestSession();
//            testSession.setRosterId(Long.parseLong(strRosterId));
            testSession.setName(sessionName);
            testSession.setTestCollectionId(testCollectionId);
            if (unusedSession != null) {
                testSession.setStatus(unusedSession.getId());
            }
            try {
                List<Long> enrollmentRosterIds = NumericUtil.convert(strEnrollmentRosterIds);
                isValid = CollectionUtils.isNotEmpty(enrollmentRosterIds);

                if (isValid && enrollmentRosterIds.size() > 0 && testCollectionId > 0 && testId >0) {
                    boolean successful = studentTestsService.createTestSessions(
                            enrollmentRosterIds, testCollectionId, testId, testSession, null, null);
                    if (!successful) {
                        isValid = false;
                    }
                }
            } catch (DuplicateTestSessionNameException e) {
                map.put("duplicateKey", true);
                isValid = false;
            }
        }
        map.put("valid", isValid);
        logger.trace("Leaving the assignStudentsToTest() method.");
        return map;
    }

    /**
     * This method edits the student enrollment to a test records for a given roster based on user input.
     * @param request
     *            {@link HttpServletRequest}
     * @return boolean
     */
    @RequestMapping(value = "saveStudentsTests.htm", method = RequestMethod.POST)
    public final @ResponseBody boolean editTestSessions(final HttpServletRequest request) {
        logger.trace("Entering the editTestSessions() method.");

        String length = request.getParameter("length");
        String assessmentId = request.getParameter("assessmentId");
        String rosterId = request.getParameter("rosterId");

        List<Long> studentIds = new ArrayList<Long>();
        Map<Long, List<Long>>
        studentTestSessionIdMap = new HashMap<Long, List<Long>>();
        Map<Long, List<Long>>
        studentTestCollectionIdMap = new HashMap<Long, List<Long>>();
        boolean valid = true;

        if (StringUtils.isNumeric(length)
                && StringUtils.isNumeric(assessmentId)
                && StringUtils.isNumeric(rosterId)) {
            int len = Integer.parseInt(length);
            long aId = Long.parseLong(assessmentId);
            long rId = Long.parseLong(rosterId);
            for (int i = 0; i < len; i++) {
                String sId = request.getParameter("studentsTests[" + i + "][studentId]");
                String[] testSessionIds = request.getParameterValues("studentsTests[" + i + "][testSessions][]");
                String[] testCollectionIds = request.getParameterValues("studentsTests[" + i + "][testCollections][]");

                if (StringUtils.isNumeric(sId)) {
                    long studentId = Long.parseLong(sId);
                    List<Long> testSessionIdList = new ArrayList<Long>();
                    List<Long> testCollectionIdList = new ArrayList<Long>();
                    if (testSessionIds != null && testCollectionIds != null) {
                        for (int j = 0, num = testSessionIds.length; j < num; j++) {
                            if (StringUtils.isNumeric(testSessionIds[j])) {
                                testSessionIdList.add(Long.parseLong(testSessionIds[j]));
                            }
                        }

                        for (int j = 0, num = testCollectionIds.length; j < num; j++) {
                            if (StringUtils.isNumeric(testCollectionIds[j])) {
                                testCollectionIdList.add(Long.parseLong(testCollectionIds[j]));
                            }
                        }
                    }
                    //DONE fix the transaction to..manage all in one transaction.
                    studentIds.add(studentId);
                    studentTestSessionIdMap.put(studentId, testSessionIdList);
                    studentTestCollectionIdMap.put(studentId, testCollectionIdList);
                }
            }
				valid = studentTestsService
				        .manageTestSessions(studentIds, studentTestSessionIdMap,
				        		studentTestCollectionIdMap, aId, rId);
        }

        logger.debug("Returning {}.", valid);
        logger.trace("Leaving the editTestSessions() method.");
        return valid;
    }

    /**
     * Returns the PDF file for a roster and testing session.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @param response
     *            {@link HttpServletResponse}
     * @throws IOException
     *             IOException
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "getPDFForRosterAndTest.htm")
    public final void getPDFForRosterAndTest(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        logger.trace("Entering the getPDFForRosterAndTest");
 
        OutputStream out = null;
        boolean pdfGenerated = false;
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
        try {
            long testSessionId = Long.parseLong(request.getParameter("testSessionId"));
            List<Long> testSessionIds = new ArrayList<Long>(Arrays.asList(testSessionId));
            String assessmentProgramName = request.getParameter("assessmentProgramName");
            Boolean isAutoRegistered = Boolean.parseBoolean(request.getParameter("isAutoRegistered"));
            String selectedStudents = request.getParameter("selectedStudents");
			List<Long> studentIds = new ArrayList<Long>();
			
			if(selectedStudents != null && selectedStudents.length() > 0) {
				for(int i=0;i< selectedStudents.split(",").length;i++) {
					studentIds.add(Long.parseLong(selectedStudents.split(",")[i]));
				}
			}
			
            //INFO: For a test with multiple sections at test level ticketing,
            // the ticket will be printed with ticket numbers at each section,
            // to enable the students to take the sections separately.
            List<TestSessionPdfDTO> testSessionPdfDTOList = testSessionService.findTestSessionTicketDetailsById(
            		testSessionIds, isAutoRegistered, studentIds, userDetails);
            out = response.getOutputStream();
            response.setContentType("application/pdf");
            String fileName = "tickets" + ".pdf";
            if (!CollectionUtils.isEmpty(testSessionPdfDTOList)) {
            	for(TestSessionPdfDTO testSessionPdfDTO: testSessionPdfDTOList)
            		testSessionPdfDTO.setAssessmentProgramName(assessmentProgramName);
            	
                fileName = testSessionPdfDTOList.get(0).getTestSessionName() + ".pdf";
                logger.debug("Found students for testsession {}, roster and test combination, size is {}.",
                		testSessionPdfDTOList.get(0).getTestSessionName(),
                		testSessionPdfDTOList.size());
                
            } 
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); 
     	    String serverPath = request.getSession().getServletContext().getRealPath("/");
            setupPDFGeneration(testSessionPdfDTOList, out, serverPath);
            pdfGenerated = true;
        } catch (NumberFormatException e) {
            logger.error("Caught NumberFormatException in getPDFForRosterAndTest(): {}", e); 
        } catch (FOPException e) {
            logger.error("Caught {} in generatePDFForRoster() method.", e); 
        } catch (IOException e) {
            logger.error("Caught {} in generatePDFForRoster() method.", e); 
        } catch (TransformerException e) {
            logger.error("Caught {} in generatePDFForRoster() method.", e); 
        } finally {
            if (out != null) {
                out.close();
            }
        }        
    }

    /**
     * This method ends a test session and retrieves student responses from TDE.
     * @param request {@link HttpServletRequest}
     * @return Map<String, Object>
     */
    @RequestMapping(value = "cancelTestSession.htm")
    public final @ResponseBody Map<String, Object> cancelTestSession(final HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        try {
            Long testSessionId = Long.parseLong(request.getParameter("testSessionId"));

            if (testSessionId > 0) {
                boolean params = testSessionService.cancelTestSession(testSessionId, user);
                boolean retrieval = true;
        		//US14114 Technical: Fix Test Monitor and other TDE services dependency
                /*if (params) {
                    retrieval = studentsResponsesService.retrieveStudentsResponses(testSessionId);
                }*/
                map.put("successful", params && retrieval);
                map.put("retrieval", retrieval);
            }

        } catch (NumberFormatException e) {
            logger.error("Caught NumberFormatException {}", e);
            map.put("successful", false);
        }

        return map;
    }

    /**
     * This method retrieves student responses by test session id based on a user selection.
     * @param testSessionId long
     * @return Map<String, Object>
     */
    @RequestMapping(value = "getResponses.htm")
    public final @ResponseBody Map<String, Object> getResponses(@RequestParam long testSessionId) {
        Map<String, Object> map = new HashMap<String, Object>();

        //map.put("successful", studentsResponsesService.retrieveStudentsResponses(testSessionId));

        return map;
    }

    /**
     * This method sets up the student login credentials PDF.
     * @param studentsTests
     *            List<StudentsTests>
     * @param out
     *            {@link OutputStream}
     * @throws FOPException
     *             FOPException
     * @throws IOException
     *             IOException
     * @throws TransformerException
     *             TransformerException
     */
    private void setupPDFGeneration( List<TestSessionPdfDTO> testSessionPdfDTOList, OutputStream out, String serverPath) throws Exception {

        // convert that data into xml
        XStream xstream = new XStream();
        xstream.alias("tsPdfDTO", TestSessionPdfDTO.class);  
        if(CollectionUtils.isEmpty(testSessionPdfDTOList)){
			TestSessionPdfDTO testSessionPdf = new TestSessionPdfDTO();
			testSessionPdfDTOList.add(testSessionPdf);
		}
        TraxSource source = new TraxSource(testSessionPdfDTOList, xstream);
        generatePdf(out, studentsTicketsXslFile.getFile(), source);
	}
	
	protected void generatePdf(OutputStream out, File foFile, TraxSource source) throws Exception {
		try {
			//out = new FileOutputStream(pdfFile);
			out = new BufferedOutputStream(out);
	
			// Setup JAXP using identity transformer
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(foFile.getCanonicalPath()));
	
			FopFactory fopFactory = FopFactory.newInstance(reportConfigFile.getFile());
			fopFactory.getImageManager().getCache().clearCache();
			
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,out);
	
			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());
	
			// Start XSLT transformation and FOP processing
			transformer.transform(source, res);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

    /**
     * 
     * @param aId
     * @param subjectIds
     * @param gradeIds
     * @param keyword
     * @return
     */
    private List<TestCollectionDTO> findTestsByCriteria(long aId, List<Long> subjectIds, List<Long> gradeIds, String keyword) {
        logger.trace("Entering the findTestByCriteria() method.");
        logger.debug("Finding tests with parameters, aId: {}, subjectId: {}, gradeId: {}, keyword: {}", new Object[] { aId,
                subjectIds, gradeIds, keyword });

        List<TestCollectionDTO> testCollections = new ArrayList<TestCollectionDTO>();
        
       /* 
        TestCollectionExample example = new TestCollectionExample();
        TestCollectionExample.Criteria criteria = example.createCriteria();

        if (subjectIds != null && subjectIds.size() > 0) {
            criteria.andContentareaidIn(subjectIds);
        }

        if (gradeIds != null && gradeIds.size() > 0) {
            criteria.andGradecourseidIn(gradeIds);
        }

        if (keyword != null && !keyword.equals("")) {
            // criteria.andNameLike("%" + keyword + "%");
            criteria.andNameILike("%" + keyword + "%");
        }
        */
        
        if (aId > 0) {
            logger.debug("Finding Tests by keyword: {}", keyword);
            // no null check as this is initialized.
            
            UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
    				.getContext().getAuthentication().getPrincipal();
    		User user = userDetails.getUser();
    		Organization current = user.getOrganization();
    		
            //TODO move testStatusConfiguration.
            //testCollections = testCollectionService.selectByExampleAndAssessmentId(
            //		testStatusConfiguration
            //       .getPublishedTestStatusCategory().getId(),
            //        sessionRulesConfiguration.getSystemEnrollmentCategory().getId(),
            //       sessionRulesConfiguration.getManualEnrollmentCategory().getId(),current.getId(), true, false);
            if(testCollections != null && CollectionUtils.isNotEmpty(testCollections)) {
	            //INFO these test collections cannot be previewed.
	            // Doing it as a separate query to avoid left outer join.
				List<Long> nonPreviewableTestCollectionIds
				= testCollectionsSessionRulesService.selectByTestCollectionIdsAndSessionRuleId(AARTCollectionUtil
	                    .getIds(testCollections,1),
	                    sessionRulesConfiguration.getSystemEnrollmentCategory().getId());
				if(nonPreviewableTestCollectionIds != null
						&& CollectionUtils.isNotEmpty(nonPreviewableTestCollectionIds)) {
					/*for(TestCollection testCollection:testCollections) {
						if(nonPreviewableTestCollectionIds.contains(
								testCollection.getId())) {
							testCollection.setCanPreview(false);
						}
					}*/
				}
            }
        }

        logger.debug("Returning Tests {} for keyword {}", testCollections, keyword);
        return testCollections;
    }

    /**
     * @param user
     *            {@link User}
     * @return List<AssessmentProgram>
     */
    private List<AssessmentProgramDto> getAssessmentProgramsForUser(final User user) {
        //List<Organization> orgs = userService.getOrganizations(user.getId());
        // TODO change this from the current organization to a unique list of the current organization and its parents assessment
        // programs.
        Organization current = user.getOrganization();
        // List<AssessmentProgram> assessmentPrograms = new ArrayList<AssessmentProgram>();
        List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();

        if (current != null) {
            List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(current.getId());
            for (OrgAssessmentProgram oap : orgAssessProgs) {
                if (!assessmentPrograms.contains(oap.getAssessmentProgram())) {
                    AssessmentProgramDto temp = new AssessmentProgramDto();
                    temp.setAssessmentProgram(oap.getAssessmentProgram());
                    assessmentPrograms.add(temp);
                }
            }
        }

        return assessmentPrograms;
    }
    
    /**
     * @param assessmentProgramId
     *            {@link long}
     * @return List<testingPrograms>
     */
    @RequestMapping(value = "getTestingPrograms1.htm", method = RequestMethod.POST)
    private @ResponseBody List<TestingProgram> getTestingProgramByAssessmentProgramId(
    		final long assessmentProgramId) {  

        List<TestingProgram> testingPrograms = new ArrayList<TestingProgram>();
        testingPrograms = testingProgramService.getByAssessmentProgramId(assessmentProgramId); 

        return testingPrograms;
    }
    
    /**
     * @param assessmentProgramId
     *            {@link long}
     * @return List<testingPrograms>
     */
    @RequestMapping(value = "getAssessments1.htm", method = RequestMethod.POST)
    private @ResponseBody List<Assessment> getAssessmentByTestingProgramId(final long assessmentProgramId, final long testingProgramId) {  

    	logger.debug("getAssessmentByTestingProgramId enter with : " + assessmentProgramId + "," + testingProgramId);
        List<Assessment> assessments = new ArrayList<Assessment>(); 
        assessments = assessmentService.getByAssessmentProgramIdAndTestingProgramId(assessmentProgramId, testingProgramId);

        return assessments;
    }
    
    /**
     * This method looks at all the test collections for the chosen assessment program and returns the grades, and subjects
     * for the test collections.
     * @param request
     * @return Map<String, Object>
     */
    @RequestMapping(value = "getGradesAndSubjectsForAssessmentId1.htm", method = RequestMethod.GET)
    public final @ResponseBody Map<String, Object> getGradesAndSubjectsForAssessmentId(final HttpServletRequest request) {
        String aId = request.getParameter("assessmentId");
        Map<String, Object> map = new HashMap<String, Object>();

        if (!StringUtils.isEmpty(aId)) {
            Long assessmentId = Long.parseLong(aId);
            List<GradeCourse> gradeCourses = gradeCourseService.findByAssessmentId(assessmentId);

            Collections.sort(gradeCourses, new Comparator<GradeCourse>() {

                @Override
                public int compare(GradeCourse o1, GradeCourse o2) {
                    return o1.getGradeLevel() - o2.getGradeLevel();
                }
            });
            List<ContentArea> contentAreas = contentAreaService.findByAssessmentId(assessmentId);

            map.put("grades", gradeCourses);
            map.put("subjects", contentAreas);
            map.put("valid", true);
        } else {
            // set an error message and return.
            map.put("valid", false);
        }

        return map;
    }



    /**
     * Displays the Monitor Test Session page.
     *
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "monitorTestSession1.htm")
    public final ModelAndView viewMonitorTestSession(final HttpServletRequest request) {                
        logger.trace("Entering the viewMonitorTestSession() method");
        ModelAndView mav = new ModelAndView(MONITOR_TEST_SESSION_JSP);

        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();

        logger.trace("Getting AssessmentPrograms for user's current organization");
        mav.addObject("assessmentPrograms", getAssessmentProgramsForUser(user));
        mav.addObject("current", "testSession");

        logger.trace("Leaving the viewMonitorTestSession() method");
        return mav;
    }
    
    /**
     * TODO use the @param instead of using the request object.
     * Returns the studentsTests for a selected roster.
     * @param request
     *            {@link HttpServletRequest}
     * @return Map<String, Object>
     */
    @RequestMapping(value = "getStudentsTestsForRoster.htm", method = RequestMethod.POST)
    //public final @ResponseBody Map<String, Object> getStudentsTestsForRoster(final HttpServletRequest request) {
    public final @ResponseBody Map<String, Object> getStudentsTestsForRoster(@RequestParam Long rosterId,
    		@RequestParam Long assessmentProgramId, @RequestParam Long testingProgramId, 
    		@RequestParam Long assessmentId) {
        logger.trace("Entering the getStudentsTestsForRoster() method");
        
        Map<String, Object> map = new HashMap<String, Object>();                                  
        Category unused = null;
        List<StudentsTests> studentsTests;                          

        studentsTests = studentsTestsService.findByAssessmentIdAndRoster(assessmentId, rosterId);
        map.put("studentsTests", studentsTests);                       
        unused = studentsTestsStatusConfiguration.getUnUsedStudentsTestsStatusCategory();            
        map.put("notStartedStatus", unused);

        //Check TestCollectionsSessionRules for Monitoring and populate the systemFlag based on that.
        if(CollectionUtils.isNotEmpty(studentsTests) && studentsTests.size() > 0) {            	
        		getTestCollectionsSessionRules(studentsTests,
        				sessionRulesConfiguration.getSystemEnrollmentCategory().getId());
        }
            
        
        logger.trace("Leaving the getStudentsTestsForRoster() method");
        return map;
    }
    
    private void getTestCollectionsSessionRules(List<StudentsTests> studentsTestsList, Long sessionRuleId) {
    	logger.debug("Entering getTestCollectionsSessionRules() method");
    	
    	TestCollection testCollection = null;
    	//Get the list of testcollectionId'd.
    	Set<Long> testCollectionIdsSet =  new HashSet<Long>();
    	for(StudentsTests StudentsTests : studentsTestsList) {
    		testCollectionIdsSet.add(StudentsTests.getTestCollectionId());
    	}
    	
    	//Get TestCollectionsSessionRules for testCollectionIds and sessionRuleId
    	List<Long> testCollectionIdsList = testCollectionsSessionRulesService.
    			selectByTestCollectionIdsAndSessionRuleId(new ArrayList<Long>(
    					testCollectionIdsSet), sessionRuleId);
    	
    	//Populate the systemFlag based on the above results.    	
    	for(StudentsTests studentsTests:studentsTestsList) {
    		testCollection = new TestCollection();
    		if(testCollectionIdsList != null && CollectionUtils.isNotEmpty(testCollectionIdsList) && 
    				testCollectionIdsList.contains(studentsTests.getTestCollectionId())) {	    				    			
    			testCollection.setSystemFlag(true);
    		} else {
    			testCollection.setSystemFlag(false);
    		}
    		studentsTests.setTestCollection(testCollection);
    	}
    	
    	logger.debug("Leaving getTestCollectionsSessionRules() method");
    }
    
    /**
     * Returns the test session report info for a selected roster and test session.
     * @param request
     *            {@link HttpServletRequest}
     * @return List<String>
     */
    @RequestMapping(value = "getTestSessionReport.htm", method = RequestMethod.POST)
    public final @ResponseBody List<String> getTestSessionReport(final HttpServletRequest request) {
    	logger.trace("Entering the getTestSessionReport() method");
    	
    	List<StudentTestSessionDto> studentTestSessionDtoList = null;    	    
    	List<String> testSessionResultsList = new ArrayList<String>();
    	String testSessionResults = "";
        int correctResponseCount = 0;
        int totalCount = 0;
        int[] score= new int[500];
        int totalQuestionsCount = 0;
        List<StudentsResponsesReportDto> studentsResponsesReportDtoList = new ArrayList<StudentsResponsesReportDto>();
        StudentsResponsesReportDto studentsResponsesReportDto = null;
        DecimalFormat decimalFormatter = new DecimalFormat("###");
		
		List<String> taskVariantPositionsAndSectionsList = new ArrayList<String>();
	    String taskVariantPositionAndSection = "";
	    
	    UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Long organizationId = userDetails.getUser().getCurrentOrganizationId();
		Long currentSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        
        if (StringUtils.isNumeric(request.getParameter("roster"))
                && StringUtils.isNumeric(request.getParameter("testSessionId"))) {
        	//input is valid
            long rosterId = Long.parseLong(request.getParameter("roster"));
            long testSessionId = Long.parseLong(request.getParameter("testSessionId"));

            //List<Student> students = studentService.findByRoster(rosterId);
            //map.put("students", students);

            //Retrieve student responses.
            //studentsResponsesService.retrieveStudentsResponses(testSessionId);
			List<Long> rosterIds = new ArrayList<Long>();
			rosterIds.add(rosterId);
            studentTestSessionDtoList = studentReportService.getTestSessionReport(rosterIds,testSessionId, false, currentSchoolYear, organizationId);
                                  
            if(studentTestSessionDtoList != null &&  CollectionUtils.isNotEmpty(studentTestSessionDtoList)) {
            	Long testId = studentTestSessionDtoList.get(0).getStudentsTests().getTestId();
            	Test test = null;
            	//for only formative test collection with one test question information will be gathered.
            	if(testId != null && testId > 0) {
            		test = testService.findTestAndSectionById(testId);
            		if(test != null) {
                    	for(TestSection testSection : test.getTestSections()) {
                    		totalQuestionsCount = totalQuestionsCount + testSection.getNumberOfTestItems();
                    	}            			
            		}
            	}

            	taskVariantPositionsAndSectionsList = testSectionsTaskVariantsDao.getTaskVariantPositionAndSectionByTestId(testId);
            	
	            if (totalQuestionsCount == 0) {
					// Get max questions count by iterating through all the StudentsResponsesReportDtos
					for (StudentTestSessionDto studentTestSessionDto : studentTestSessionDtoList) {
						if (studentTestSessionDto != null
								&& studentTestSessionDto.getStudent() != null
								&& studentTestSessionDto
										.getStudentsResponsesReportDtos() != null) {
							if (totalQuestionsCount < studentTestSessionDto
									.getStudentsResponsesReportDtos().size()) {
								totalQuestionsCount = studentTestSessionDto
										.getStudentsResponsesReportDtos()
										.size();
								studentsResponsesReportDtoList = studentTestSessionDto
										.getStudentsResponsesReportDtos();
							}
						}
					}
				}
				//Build the table header
            	//TODO do not rely on string parsing. Use Json objects or use standard bean resolvers.
	            testSessionResults += "Name;" + "Status;";
            	if (test != null && test.getTestSections() != null && CollectionUtils.isNotEmpty(test.getTestSections())) {
					for (TestSection testSec : test.getTestSections()) {
						for (TestSectionsTaskVariants testSectionsTaskVariants : testSec
								.getTestSectionsTaskVariants()) {
							testSessionResults = testSessionResults
									+ testSectionsTaskVariants.getTaskVariantId() + ",";
							testSessionResults = testSessionResults
									+ testSectionsTaskVariants
											.getTaskVariantPosition() + ";";
						}
					}
				} else {
					//The test collection has more than 1 test.
		            for( StudentsResponsesReportDto studentsResponsesReportDtoObj : studentsResponsesReportDtoList) {
		            	testSessionResults += studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTestSectionId() + ",";
		            	if(studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() != null) {
		            		testSessionResults += studentsResponsesReportDtoObj.getTestSectionsTaskVariants().getTaskVariantPosition() + ";";
		            	} else {
		            		testSessionResults += "-" + ";";
		            	}
		            }					
				}
				testSessionResults += "Score;" + "Score %";
	            testSessionResultsList.add(testSessionResults);
	            testSessionResults = "";
	            	            	            	           
	            //Build table rows
	            //TODO Consider how to move it to the service method itself the operations on this dto.
	            Boolean found = false;
	            for(StudentTestSessionDto studentTestSessionDto :  studentTestSessionDtoList) {
	            		            	
	            	if(studentTestSessionDto != null && studentTestSessionDto.getStudent() != null &&
	            			studentTestSessionDto.getStudentsResponsesReportDtos() != null) {
	            		testSessionResults += studentTestSessionDto.getStudent().getId() + ";";
	            		testSessionResults += studentTestSessionDto.getStudent().getLegalFirstName()
	            				+ " " + studentTestSessionDto.getStudent().getLegalLastName() + ";";
	            		testSessionResults += studentTestSessionDto.getStudentsTestStatus() + ";";
	            		//for(int i = 0; i < totalQuestionsCount; i++) {
	            		for(String taskVariantPosAndSec : taskVariantPositionsAndSectionsList) {
	            			found = false;
	            			for(int i = 0; i < studentTestSessionDto.getStudentsResponsesReportDtos().size() && !found; i++)  {
	            				studentsResponsesReportDto = studentTestSessionDto.getStudentsResponsesReportDtos().get(i);	            		
		            			
	            				if(studentsResponsesReportDto != null && studentsResponsesReportDto.getTaskVariantsFoils()!= null) {
		            				
	            					if(studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse() != null) {
	            						
	            						taskVariantPositionAndSection =  ((studentsResponsesReportDto.getTestSectionsTaskVariants().getTestSectionId()).toString() + 
	            								(studentsResponsesReportDto.getTestSectionsTaskVariants().getTaskVariantPosition()).toString());
										
	            						if(taskVariantPosAndSec.equalsIgnoreCase(taskVariantPositionAndSection)) {
	            							found = true;
		            						// if the response is not null and correct
			            					if(studentsResponsesReportDto.getTaskVariantsFoils().getCorrectResponse()) {
						            			testSessionResults += "T;";
						            			correctResponseCount++;
						            			score[totalCount] +=1;
						            		} else {
						            			// if the response is not null and incorrect
						            			String rtlf = studentsResponsesReportDto.getReportTaskLayoutFormat();
						            			if (rtlf == null)
						            				rtlf = "no label";
						            			Integer responseOrder = studentsResponsesReportDto.getTaskVariantsFoils().getResponseOrder();
						            			String responseLabel = ResponseLabelUtil.getResponseLabel(responseOrder, rtlf);
						            			if (responseLabel.equals("")){
						            				//no label
						            				testSessionResults += "-;";
						            			}else{
						            				testSessionResults += responseLabel + ";";
						            			}
						            		}
	            						}/* else {
	            							//If the student skipped a particular question.
	            							testSessionResults += "-;";
	            						}*/
		            				}/* else {            						
	            						// if the response is null
	            						testSessionResults += "-;";
		            				}*/
		            			}
		            		}
	            			if(!found){
		            			testSessionResults += "-" + ";";
		            		}
		            		totalCount++;            		
		            	//}
	            		}
	            		//TODO move this to a helper.Consider the report helper written previously.
	            		try {
			            	testSessionResults +=  String.valueOf(correctResponseCount) + "/" + String.valueOf(totalCount) + ";" ;
			            	if(totalCount != 0) {
			            		testSessionResults += String.valueOf(Integer.valueOf(decimalFormatter.format((100.0/totalCount) * correctResponseCount))) + "%";
			            	} else {
			            		testSessionResults += "0%";
			            	}
	            		} catch (ArithmeticException ex) {
	            			logger.error("Caught ArithmeticException {}", ex);
	            		}
		            	testSessionResultsList.add(testSessionResults);
		            	testSessionResults = "";
		            	correctResponseCount = 0;	            	 
		            	totalCount = 0;
	            	}
	            }
	            
	            // Build the table last row to show question-wise score
	            //TODO move it to report helper written previously.
	            testSessionResults += ";;Score;";
	            for (int scoreIndex=0; scoreIndex < totalQuestionsCount; scoreIndex++) {
	            	testSessionResults += String.valueOf(score[scoreIndex]) + " of  " + studentTestSessionDtoList.size();
	            	if(studentTestSessionDtoList.size() != 0) {
	            		testSessionResults += "<br>" + String.valueOf(Integer.valueOf(decimalFormatter.format((100.0/studentTestSessionDtoList.size()) * score[scoreIndex]))) + "%;";
	            	} else {
	            		testSessionResults += "0%;";
	            	}
	            }
	            testSessionResults += ";;";
	            testSessionResults = testSessionResults.substring(0, testSessionResults.lastIndexOf(";"));
	            testSessionResultsList.add(testSessionResults);  
            }
        }

        logger.trace("Leaving the getTestSessionReport() method");
        return testSessionResultsList;
    }
    
    /**
     * Returns the StudentsTestSessionStatus info for a selected roster and test session.
     * Here we are dynamically populating the section status's into jqgrid.
     * @param request
     *            {@link HttpServletRequest}
     * @return List<String>
     */
    @RequestMapping(value = "getStudentsTestSessionStatus.htm", method = RequestMethod.POST)
    public final @ResponseBody  Map<String, Object>  getStudentsTestSessionStatus(final @RequestParam Long testSessionId) {
    	logger.trace("Entering the getStudentsTestSessionStatus() method");
        
    	Map<String, Object> studentsTestSectionsMap = new HashMap<String, Object>();
    	//TODO do not use Generic type as object..what is the use ? Is this because of the dynamic jqgrid ?
        List<Object> studentsStatusList = new ArrayList<Object>();
        List<String> sectionStatusColumnNamesList = new ArrayList<String>();
        //TODO Do not use Object in the map. Is this because of the dynamic jqgrid.
        Map<String, Object> studentMap = new HashMap<String, Object>();
        Long studentId = 0L;
        boolean inProgressTimedOut = false;
        
        //Retrieve student responses.
        //studentsResponsesService.retrieveStudentsResponses(testSessionId);
        
        List<StudentsTestSections> studentsTestSectionsList = studentReportService.getPerformanceStatus(testSessionId);
                        
        if(CollectionUtils.isNotEmpty(studentsTestSectionsList) && studentsTestSectionsList.size() > 0) {
	        for(StudentsTestSections studentsTestSections : studentsTestSectionsList) {						
				if(!studentId.equals(studentsTestSections.getStudentsTests().getStudent().getId())) {
					if(studentMap != null && studentMap.size() > 0) {
						//Populate inProgressTimedOut value true/false based on the section status
						studentMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_INPROGRESSTIMEDOUT,
								inProgressTimedOut);
						inProgressTimedOut = false;
						studentsStatusList.add(studentMap);							
						studentMap = new HashMap<String, Object>();						
					}
					studentId = studentsTestSections.getStudentsTests().getStudent().getId();
					
					//TODO move the conversion to HElper or converter.Call it StudentTestSectionToMapHelper..or alike
					//Populate studentsTestId,studentName and overallStatus key values.						
					studentMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_STUDENTS_TEST_ID,
							studentsTestSections.getStudentsTestId());
					studentMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_STUDENT_NAME,
							studentsTestSections.getStudentsTests().getStudent().getLegalFirstName() +
							ParsingConstants.BLANK_SPACE + studentsTestSections.getStudentsTests().getStudent().getLegalLastName());
					studentMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_OVERALL_STATUS,
							studentsTestSections.getStudentsTests().getStudentTestStatus().getCategoryName());					
				}
				//Populate section-wise status key value pairs.
				studentMap.put(studentsTestSections.getTestSection().getTestSectionName(),
						studentsTestSections.getStatus().getCategoryName());
				
				if(StringUtils.isNotEmpty(studentsTestSections.getStatus().getCategoryName())
						&& studentsTestSections.getStatus().getCategoryName().replace(ParsingConstants.BLANK_SPACE,
								ParsingConstants.BLANK).equalsIgnoreCase(
								studentsTestSectionsStatusConfiguration.getInProgressTimedOutStudentsTestSectionsCode())) {
					inProgressTimedOut = true;
				}
				
				//Populate section-wise status columnNames for jqGrid dynamically. 
				if(!sectionStatusColumnNamesList.contains(studentsTestSections.getTestSection().getTestSectionName())) {
					sectionStatusColumnNamesList.add(studentsTestSections.getTestSection().getTestSectionName());												
				}
			
			}
	        studentMap.put(CommonConstants.STUDENTS_TEST_SECTION_STATUS_INPROGRESSTIMEDOUT,
					inProgressTimedOut);
	        studentsStatusList.add(studentMap);	
	        
	        studentsTestSectionsMap.put("students", studentsStatusList);
	        studentsTestSectionsMap.put("sectionStatusColumnNames", sectionStatusColumnNamesList);
        }
        
        logger.trace("Leaving the getStudentsTestSessionStatus() method");
        return studentsTestSectionsMap;
    }
    
    /**
     * Returns the test session report info for a selected roster and test session.
     * TODO why is spring not able to convert to long.
     * @param request
     *            {@link HttpServletRequest}
     * @return List<String>
     * @throws Exception 
     */
    @RequestMapping(value = "closeStudentsTests.htm", method = RequestMethod.POST)
    public final @ResponseBody boolean closeStudentsTests(@RequestParam(value="studentIds[]") List<String> studentIds,
    		@RequestParam Long testSessionId) throws Exception {
    	List<Long> convertedStudentIds = new ArrayList<Long>();
    	if(studentIds != null && CollectionUtils.isNotEmpty(studentIds) && testSessionId != null) {
    		for(String studentId : studentIds) {
    			convertedStudentIds.add(Long.parseLong(studentId));
    		}
    		studentsTestsService.closeStudentsTestsStatus(convertedStudentIds, testSessionId);
    	} else {
    		throw new Exception("Input is not valid " + studentIds + " and test session id" + testSessionId);
    	}
    	logger.debug("Closed students tests " + convertedStudentIds + " and test session id "+ testSessionId);
    	return true;
    }
    
    /**
     * Returns the test session report info for a selected roster and test session.
     * @param request
     *            {@link HttpServletRequest}
     * @return List<String>
     */
    @RequestMapping(value = "getQuestionForTest.htm", method = RequestMethod.POST)
    public final @ResponseBody String getQuestionForTest(final HttpServletRequest request) {
    	logger.trace("Entering the getQuestionForTest() method");
    	List<TaskVariantsFoils> taskVariantsFoilsList = null;
    	String taskVariant = "";
    	
        long questionid = Long.parseLong(request.getParameter("questionid"));
        
        taskVariantsFoilsList = studentReportService.getTaskVariant(questionid);
        if(taskVariantsFoilsList != null) {
        	taskVariant = taskVariantsFoilsList.get(0).getTaskVariant().getTaskStem();
        }
        
        logger.trace("Leaving the getQuestionForTest() method");
    	return taskVariant;
    }    
    
    /**
     * This method is to update the studentTestSection status to reactivate the students
     * @param request
     *            {@link HttpServletRequest}
     * @return true
     */
    @RequestMapping(value = "reactivateStudentsSection1.htm", method = RequestMethod.POST)
    public final @ResponseBody boolean reactivateStudentsSection1(
    		@RequestParam(value="studentsTestIds[]") List<String> studentsTestIds,
    		@RequestParam Long testSessionId ) {    	
    	List<Long> studentsTestIdsList;
    	boolean updateSuccessful = false;
    	studentsTestIdsList = AARTCollectionUtil.getListAsLongType(studentsTestIds);
    	
    	updateSuccessful = studentsTestsService.reactivateStudentsTestSectionsStatus(studentsTestIdsList,testSessionId);
    	
    	logger.debug("reactivate studentsTestIds " + studentsTestIds);
    	return updateSuccessful;
    }
    
    /**
  	 * @return
  	 */
  	@RequestMapping(value = "getGradeCourseByContentAreaIdForTestCoordination.htm", method = RequestMethod.GET)
  	public final @ResponseBody List<GradeCourse> getGradeCourseByContentAreaIdForTestCoordination(
  			@RequestParam("assessmentProgramId") Long assessmentProgramId,
  			@RequestParam("contentAreaId") Long contentAreaId,
  			@RequestParam("testingProgramId") Long testingProgramId) {
  		logger.trace("Entering the getGradeCourseByContentAreaIdForTestCoordination() method");
  		 
  		List<GradeCourse> gradeCourses = gradeCourseService.getGradeCourseByContentAreaIdForTestCoordination(assessmentProgramId, testingProgramId, contentAreaId);     
  		Collections.sort(gradeCourses, gradeCourseComparator);		

  		logger.trace("Leaving the getGradeCourseByContentAreaIdForTestCoordination() method");
  		return gradeCourses;
  	 }
  	
  	 /**
  	 * @return
  	 */
  	@RequestMapping(value = "getGradeCourseByContentAreaIdForTestManagement.htm", method = RequestMethod.GET)
  	public final @ResponseBody List<GradeCourse> getGradeCourseByContentAreaIdForTestManagement(
  			@RequestParam("assessmentProgramId") Long assessmentProgramId,
  			@RequestParam("testingProgramId") Long testingProgramId,
  			@RequestParam("contentAreaId") Long contentAreaId) {
  		logger.trace("Entering the getGradeCourseByContentAreaIdForTestCoordination() method");
  		 
  		List<GradeCourse> gradeCourses = gradeCourseService.getGradeCourseByContentAreaIdForTestManagement(assessmentProgramId, testingProgramId, contentAreaId);     
  		Collections.sort(gradeCourses, gradeCourseComparator);		

  		logger.trace("Leaving the getGradeCourseByContentAreaIdForTestCoordination() method");
  		return gradeCourses;
  	 }
  	
  	 static Comparator<GradeCourse> gradeCourseComparator = new Comparator<GradeCourse>(){
  		   	public int compare(GradeCourse gc1, GradeCourse gc2){
  		   		return gc1.getName().compareToIgnoreCase(gc2.getName());
  		   	}
  		};

}
