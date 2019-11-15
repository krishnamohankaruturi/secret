package edu.ku.cete.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.JsonResultSet;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.FirstContactSettings;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentExample;
import edu.ku.cete.domain.enrollment.ViewMergeStudentDetailsDTO;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentInformationRecord;
import edu.ku.cete.domain.student.survey.Survey;
import edu.ku.cete.domain.student.SurveyJson;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabel;
import edu.ku.cete.domain.student.survey.SurveySection;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.student.survey.SurveyMapper;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.ServiceException;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.exception.NoAccessToResourceException;
import edu.ku.cete.service.student.FirstContactService;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.TimerUtil;
import edu.ku.cete.util.json.StudentRecordJsonConverter;
import edu.ku.cete.web.AssessmentProgramDto;
import edu.ku.cete.web.Metadata;
import edu.ku.cete.web.OrganizationDto;
import edu.ku.cete.web.RecordBrowserFilter;
import edu.ku.cete.web.RecordBrowserFilterRules;
import edu.ku.cete.web.RosterDTO;
import edu.ku.cete.web.StudentDto;
import edu.ku.cete.web.ViewStudentDTO;
import edu.ku.cete.domain.student.StudentExitCodesDTO;


/**
 *
 * @author neil.howerton
 *
 */
@Controller
public class StudentSearchController {
    private Logger logger = LoggerFactory.getLogger(StudentSearchController.class);

    /**
     * STUDENT_RECORDS_JSP
     */
    
    private static final String STUDENT_RECORDS_JSP = "/student/studentRecords";
    
    private final String viewName = "studentSearch";

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService orgService;

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * gradeCourseService.
     */
    @Autowired
    private GradeCourseService gradeCourseService;

    @Autowired
    private RosterService rosterService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PermissionUtil permissionUtil;

    @Autowired
    private ResourceRestrictionService resourceRestrictionService;
    /**
     * This is to tell the back end services for what reason you are trying to
     * access certain records.
     */
    @Autowired
    private RestrictedResourceConfiguration restrictedResourceConfiguration;

    @Autowired
	private OrganizationService organizationService;

    @Autowired
    private GroupsService groupService;
    
    @Autowired
    private OrgAssessmentProgramService orgAssessProgService;
    
    
    @Autowired
    private SurveyMapper surveyMapper;
    
    /**
     * mapper
     */
    private ObjectMapper mapper = new ObjectMapper();
    
    /**
     * studentService
     */
    @Autowired
    private StudentService studentService;
    
    /**
	 * firstContactService.
	 */
	@Autowired
	private FirstContactService firstContactService;
	
    /**
     * surveySectionTypeCode
     */
    
    @Value("${user.organization.DLM}")
    private String dlmOrgName;
    
    @Value("${report.assessmentprograms}")
	private String reportingAssessmentPrograms;
    
    @Autowired
    private TestSessionService testSessionSevice;
    
    @Autowired
    private AppConfigurationService appConfigurationService;

    @Autowired
    private StudentsTestsService studentsTestsService;
    
    /**
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "studentSearch.htm", method = RequestMethod.GET)
    public final ModelAndView view(final HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(viewName);
        mav.addObject("current", "search");

        return mav;
    }

    
    /**
     *
     *@param selectedOrganizationId Long
     *@return List<RosterDTO>
     */
    @RequestMapping(value = "getRosters.htm", method = RequestMethod.POST)
    public final @ResponseBody List<RosterDTO> getRostersByOrganizationAndGrade(
            final @RequestParam Long selectedOrganizationId) {
        List<RosterDTO> rosters = new ArrayList<RosterDTO>();
        if (selectedOrganizationId != null && selectedOrganizationId > 0) {
            UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //Find the restriction for what the user is trying to do on this page.
            Restriction restriction = resourceRestrictionService.getResourceRestriction(
                    userDetails.getUser().getOrganization().getId(),
                    permissionUtil.getAuthorityId(
                            userDetails.getUser().getAuthoritiesList(),
                            RestrictedResourceConfiguration.getSearchRosterPermissionCode()),
                    permissionUtil.getAuthorityId(
                            userDetails.getUser().getAuthoritiesList(),
                            RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
                    restrictedResourceConfiguration.getRosterResourceCategory().getId());
            //restriction is null for teachers
            rosters = rosterService.getRosterDtoByUserAndOrg(
                    userDetails.getUserId(),
                    selectedOrganizationId, restriction);
            if (rosters.size() > 0) {
                for (RosterDTO roster : rosters) {
                    //find the number of students in the roster.
                    //TODO change it to group by and in clause sql.
                    roster.setNumStudents(rosterService.getNumberOfStudents(roster.getRoster().getId()));
                }
            }
        }

        return rosters;
    }

    /**
     *
     * @param rosterId long
     * @return List<StudentDto>
     */
    @RequestMapping(value = "getStudents.htm", method = RequestMethod.POST)
    public final @ResponseBody List<StudentDto> searchStudents(
    		@RequestParam final long rosterId) {
        logger.debug("Entering the searchStudents method.");
        List<StudentDto> students = getStudentData(rosterId);

        logger.debug("Leaving the searchStudents method.");
        return students;
    }

    /**
     *
     * @param request
     *            {@link HttpServletRequest}
     * @param response
     *            {@link HttpServletResponse}
     * @return List<Organization>
     */
    @RequestMapping(value = "getUsersOrgs.htm", method = RequestMethod.POST)
    public final @ResponseBody List<OrganizationDto> getUsersOrgs(final HttpServletRequest request,
            final HttpServletResponse response) {
        logger.debug("Entering the getUsersOrgs method.");
        SecurityContext context = (SecurityContext) request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        String name = context.getAuthentication().getName();
        User user = userService.getByUserName(name);
        List<OrganizationDto> organizations = null;
        
        try {
        	organizations = getUsersOrganizations(user.getId());
        	logger.debug("Returning organizations {} for user {}", organizations, user);
        } catch(Exception e) {
        	logger.debug("Exception while getting user organizations", e.getStackTrace());
        }
        
        logger.debug("Leaving the getUsersOrgs method.");
        return organizations;
    }

    /**
     *
     *@param request {@link HttpServletRequest}
     *@param response {@link HttpServletResponse}
     *@return List<Integer>
     */
    @RequestMapping(value = "getGradesForOrg.htm", method = RequestMethod.POST)
    public final @ResponseBody List<String> getGradesForOrganization(final HttpServletRequest request,
            final HttpServletResponse response) {
        logger.debug("Entering the getGradesForOrganization method.");
        String strId = request.getParameter("orgId");
        List<String> grades = new ArrayList<String>();
        if (StringUtils.isNumeric(strId)) {
            long orgId = Long.parseLong(strId);
            grades = getGradeLevelsForBulding(orgId);
        }

        logger.debug("Returning grades {} for organization {}", grades, strId);
        logger.debug("Leaving the getGradesForOrganization method.");
        return grades;
    }
    
    /**
     * @param rosterId long
     * @return List<Enrollment>
     */
    private List<StudentDto> getStudentData(long rosterId) {
        logger.debug("Entering the getStudentData method");
        // TODO fix this method to actually get the student data. Get the student from the enrollment.
        List<StudentDto> students = new ArrayList<StudentDto>();
        EnrollmentExample newEnrollEx = new EnrollmentExample();
        EnrollmentExample.Criteria enrollmentCriteria = newEnrollEx.createCriteria();
        enrollmentCriteria.andRosterIdEqualTo(rosterId);

        List<Enrollment> enrollments = enrollmentService.getByCriteria(newEnrollEx);
        for (Enrollment enrollment : enrollments) {
            StudentDto studentDto = new StudentDto();
            studentDto.setStudent(enrollment.getStudent());
            studentDto.setStudentLocalId(enrollment.getLocalStudentIdentifier());
            //TODO This will have to change when the grade is pulled out into its own table!
            if (enrollment.getCurrentGradeLevel() != null) {
                //TODO This will run this sql for every enrollment found. Take it out of the for loop and do an in statement.
                GradeCourse currentGradeLevel = gradeCourseService.selectByPrimaryKey(enrollment.getCurrentGradeLevel());
                studentDto.setEnrolledGrade(currentGradeLevel.getName());
            }
            students.add(studentDto);
        }

        logger.debug("Leaving the getStudentData method.");
        return students;
    }

    /**
     * This method is used to generate the tree structure on the student search page.
     * @param userId long
     * @return List<Organization>
     */
    private List<OrganizationDto> getUsersOrganizations(long userId) {
        logger.debug("Entering the getUserOrganizations method.");
        List<Organization> userOrgs = userService.getOrganizations(userId);
        List<OrganizationDto> organizationDtos = new ArrayList<OrganizationDto>();
        if (userOrgs != null && userOrgs.size() > 0) {
        	organizationDtos = getUsersOrganizationHierarchy(userId, userOrgs);
        }
        logger.debug("Leaving the getUserOrganizations method.");
        return organizationDtos;
    }
    
    /**
     * This method is used to generate the hierarchy structure on the student search page.
     * @param userId long
     * @return List<Organization>
     */
    private List<OrganizationDto> getUsersOrganizationHierarchy(long userId, List<Organization> userOrgs) {
        logger.debug("Entering the getUserOrganizations method.");
        List<Organization> userOrgsList = orgService.getOrgHierarchyByUserId(userId);
        Map<Long,List<OrganizationDto>> childOrgsMap = new HashMap<Long,List<OrganizationDto>>();
        List<OrganizationDto> organizationDtos = new ArrayList<OrganizationDto>();
        List<OrganizationDto> userOrgsChildList = new ArrayList<OrganizationDto>();
        
        for(Organization userOrg:userOrgsList) {
        	OrganizationDto immediateChildOrgDto = new OrganizationDto();
            immediateChildOrgDto.setData(userOrg
                    .getOrganizationName() + " - " + userOrg.getDisplayIdentifier());
            Metadata metadata = new Metadata();
            metadata.setId(userOrg.getId());
            immediateChildOrgDto.setMetadata(metadata);
            immediateChildOrgDto.setRelatedOrganizationId(userOrg);

        	if(childOrgsMap.containsKey(userOrg.getRelatedOrganizationId())) {
        		List<OrganizationDto> childOrgsList = childOrgsMap.get(userOrg.getRelatedOrganizationId());
        		childOrgsList.add(immediateChildOrgDto);
        	} else {
        		List<OrganizationDto> childOrgsList = new ArrayList<OrganizationDto>();
        		childOrgsList.add(immediateChildOrgDto);
        		childOrgsMap.put(userOrg.getRelatedOrganizationId(), childOrgsList);
        		userOrgsChildList.add(immediateChildOrgDto);
        	}
        }
        
        for(OrganizationDto userChildOrg:userOrgsChildList) {
        	userChildOrg.setChildren(childOrgsMap.get(userChildOrg.getMetadata().getId()));
        }
        
        for(Organization userOrg:userOrgs) {
        	OrganizationDto immediateChildOrgDto = new OrganizationDto();
            immediateChildOrgDto.setData(userOrg
                    .getOrganizationName() + " - " + userOrg.getDisplayIdentifier());
            Metadata metadata = new Metadata();
            metadata.setId(userOrg.getId());
            immediateChildOrgDto.setMetadata(metadata);
            immediateChildOrgDto.setRelatedOrganizationId(userOrg);
            immediateChildOrgDto.setChildren(childOrgsMap.get(userOrg.getId()));
            organizationDtos.add(immediateChildOrgDto);
        }

        logger.debug("Leaving the getUserOrganizations method.");
        return organizationDtos;
    }

    /**
     *
     *@param organizationId {@link Long}
     *@return List<Integer>
     */
    private List<String> getGradeLevelsForBulding(long organizationId) {
        logger.debug("Entering the getGradeLevelsForBuildling method.");
        List<String> grades = new ArrayList<String>();
        List<GradeCourse> gradeCourses
        = gradeCourseService.selectByExample(null);

        if (gradeCourses != null && CollectionUtils.isNotEmpty(gradeCourses)) {
            for (GradeCourse grade : gradeCourses) {
                grades.add(grade.getId()
                        + ParsingConstants.OUTER_DELIM +
                        grade.getName());
            }
        }

        logger.debug("Returning grades {} for organization with id {}", grades, organizationId);
        logger.debug("Leaving the getGradeLevelsForBuilding method.");
        return grades;
    }

    /**
     *
     * @param inOrganizations {@link List}
     * @param count int
     * @return List<OrganizationDto>
     */
    private List<OrganizationDto> getImmediateChildOrgDtos(List<Organization> inOrganizations, int count) {
        //find all the immediate children of the given organization
        List<Organization> immediateChildOrgs = orgService.getImmediateChildren(inOrganizations);
        List<OrganizationDto> immediateChildOrgDtos = new ArrayList<OrganizationDto>();
        if (immediateChildOrgs != null && CollectionUtils.isNotEmpty(immediateChildOrgs) && count < 6) {
            //TODO Performance Improvement
            //form the dto for the immediate children
            for (Organization immediateChildOrg : immediateChildOrgs) {
                OrganizationDto immediateChildOrgDto = new OrganizationDto();
                immediateChildOrgDto.setData(immediateChildOrg
                        .getOrganizationName() + " - " + immediateChildOrg.getDisplayIdentifier());
                Metadata metadata = new Metadata();
                metadata.setId(immediateChildOrg.getId());
                immediateChildOrgDto.setMetadata(metadata);
                immediateChildOrgDto.setRelatedOrganizationId(immediateChildOrg);
                immediateChildOrgDtos.add(immediateChildOrgDto);
            }
            List<OrganizationDto> immediateGrandChildOrgs = getImmediateChildOrgDtos(immediateChildOrgs, ++count);
            if (immediateGrandChildOrgs != null && CollectionUtils.isNotEmpty(immediateGrandChildOrgs)) {
                //set the grand children to each of the immediate child.
                for (OrganizationDto immediateChildOrgDto : immediateChildOrgDtos) {
                    immediateChildOrgDto.setChildren(immediateGrandChildOrgs);
                }
            }
        }
        return immediateChildOrgDtos;
    }

    /**
     * @param request
     *            {@link HttpServletRequest}
     * @return {@link ModelAndView}
     */
    @RequestMapping(value = "studentRecords.htm", method = RequestMethod.GET)
    public final ModelAndView browseStudents() {
        ModelAndView mav = new ModelAndView(STUDENT_RECORDS_JSP);
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        
        List<Organization> orgChildren=organizationService.getAllChildren(user.getOrganizationId(), true);
        List<Long> orgChildrenIds = AARTCollectionUtil.getIds(orgChildren);
        
        logger.debug("Getting AssessmentPrograms for user's current organization");
        mav.addObject("orgChildrenIds", orgChildrenIds);
        mav.addObject("user", user);
        return mav;
    }
   
    @RequestMapping(value = "getStudentInformationRecordsForCategoryCodeName.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getStudentInformationRecordsForCategoryCodeName(){
    	Map<String, Object> categoryNameAndCode = studentService.getViewStudentInformationRecordsForFirstLanguageCategoryCodeName();
    	return categoryNameAndCode;    	
    }
    		
    /**
     * This method finds students that the currently logged in user is authorized to view.
     * @param request
     * @return List<Roster>
     */
    @RequestMapping(value = "getStudentInformationRecords.htm", method = RequestMethod.POST)
    public final @ResponseBody JsonResultSet getStudentInformationRecords(
    		@RequestParam("orgChildrenIds") String[] orgChildrenIds,    		
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters) {
    	
        logger.debug("Entering the getStudentInformationRecords page for getting results");
                
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        JsonResultSet jsonResultSet = null;
        List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        
        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
        Long educatorId = null;

        if(userDetails.getUser().isTeacher()){
        	educatorId = userDetails.getUserId();
        }
        TimerUtil timerUtil = TimerUtil.getInstance();
        List<StudentInformationRecord> studentInformationRecords = studentService.getStudentInformationRecords(
				null, null, inputOrgIds.get(0),
				userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
				RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				sortByColumn + ParsingConstants.BLANK_SPACE + sortType, (currentPage-1)*limitCount,
				limitCount, studentInformationRecordCriteriaMap,educatorId);
        timerUtil.resetAndLog(logger, "Getting student records took ");
        
        totalCount = studentService.getStudentInformationRecordsCount(
				null, null, inputOrgIds.get(0), userDetails,
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
						studentInformationRecordCriteriaMap,educatorId);
        timerUtil.resetAndLog(logger, "Counting student records took ");
        
        jsonResultSet = StudentRecordJsonConverter.convertToStudentRecordJson(
        		studentInformationRecords,totalCount,currentPage, limitCount);
        
        logger.debug("Leaving the getStudentInformationRecords page.");
        return jsonResultSet;
    }   
    
    /**
     * This method finds students that the currently logged in user is authorized to view.
     * @param request
     * @return List<Roster>
     * @throws NoAccessToResourceException 
     */
    @RequestMapping(value = "getViewStudentInformationRecords.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getViewStudentInformationRecords(
    		@RequestParam("orgChildrenIds") String[] orgChildrenIds,    		
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
        logger.debug("Entering the getStudentInformationRecords page for getting results");
        Map<String, Object> studentInfo = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
           
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
           
        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
        Long educatorId = null;

        if(userDetails.getUser().isTeacher()){
        	educatorId = userDetails.getUserId();
        }
        Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		if(childrenOrgIds.contains(inputOrgIds.get(0))){
			TimerUtil timerUtil = TimerUtil.getInstance();
	        List<ViewStudentDTO> studentInformationRecords = studentService.getViewStudentInformationRecords(
					null, null, inputOrgIds.get(0),
					userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
					RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
					sortByColumn, sortType, (currentPage-1)*limitCount,
					limitCount, studentInformationRecordCriteriaMap, userDetails.getUser().isTeacher(), educatorId);
	        
	        studentInfo.put("rows", studentInformationRecords);
	        timerUtil.resetAndLog(logger, "Getting student records took");
	        totalCount = 0;

	        if(!studentInformationRecords.isEmpty()) {
	        	totalCount = studentInformationRecords.get(0).getTotalRecords();
	        }
	        timerUtil.resetAndLog(logger, "Counting student records took ");
	        studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
	        studentInfo.put("page", currentPage);
	        studentInfo.put("records", totalCount);
		}else{
			throw new NoAccessToResourceException("Access to this information is restricted.");
		}
        logger.debug("Leaving the getStudentInformationRecords page.");
        return studentInfo;
    }
    
    @RequestMapping(value = "getViewStudentInformationRecordsForTestLets.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getViewStudentInformationRecordsForDLMTestlets(
    		@RequestParam("orgChildrenIds") String[] orgChildrenIds,    		
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
        logger.debug("Entering the getStudentInformationRecords page for getting results");
        Map<String, Object> studentInfo = new HashMap<String, Object>();
                int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
           
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        Long assessmentProgramId =userDetails.getUser().getCurrentAssessmentProgramId();
             
           
        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
        Long educatorId = null;

        if(userDetails.getUser().isTeacher()){
        	educatorId = userDetails.getUserId();
        }
        Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		if(childrenOrgIds.contains(inputOrgIds.get(0))){
			TimerUtil timerUtil = TimerUtil.getInstance();
	        List<ViewStudentDTO> studentInformationRecords = studentService.getViewStudentInformationRecordsForDLMTestlets(
					null, null, inputOrgIds.get(0),
					userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
					RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
					sortByColumn, sortType, (currentPage-1)*limitCount,
					limitCount, studentInformationRecordCriteriaMap, userDetails.getUser().isTeacher(), educatorId);
	        
	        studentInfo.put("rows", studentInformationRecords);
	        timerUtil.resetAndLog(logger, "Getting student records took");
	        totalCount = 0;

	        if(!studentInformationRecords.isEmpty()) {
	        	totalCount = studentInformationRecords.get(0).getTotalRecords();
	        }
	        timerUtil.resetAndLog(logger, "Counting student records took ");
	        studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
	        studentInfo.put("page", currentPage);
	        studentInfo.put("records", totalCount);
		}else{
			throw new NoAccessToResourceException("Access to this information is restricted.");
		}
        logger.debug("Leaving the getStudentInformationRecords page.");
        return studentInfo;
    }
    
    
    @RequestMapping(value = "getViewStudentInformationRecordsInLcs.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getViewStudentInformationRecordsInLcs(
    		@RequestParam("orgChildrenIds") String[] orgChildrenIds,    		
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
        logger.debug("Entering the getStudentInformationRecords page for getting results");
        Map<String, Object> studentInfo = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
           
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
           
        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
        Long educatorId = null;

        if(userDetails.getUser().isTeacher()){
        	educatorId = userDetails.getUserId();
        }
        Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		if(childrenOrgIds.contains(inputOrgIds.get(0))){
			TimerUtil timerUtil = TimerUtil.getInstance();
	        List<ViewStudentDTO> studentInformationRecords = studentService.getViewStudentInformationRecordsInLcs(
					null, null, inputOrgIds.get(0),
					userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
					RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
					sortByColumn, sortType, (currentPage-1)*limitCount,
					limitCount, studentInformationRecordCriteriaMap, userDetails.getUser().isTeacher(), educatorId);
	        
	        studentInfo.put("rows", studentInformationRecords);
	        timerUtil.resetAndLog(logger, "Getting student records took");
	        totalCount = 0;

	        if(!studentInformationRecords.isEmpty()) {
	        	totalCount = studentInformationRecords.get(0).getTotalRecords();
	        }
	        timerUtil.resetAndLog(logger, "Counting student records took ");
	        studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
	        studentInfo.put("page", currentPage);
	        studentInfo.put("records", totalCount);
		}else{
			throw new NoAccessToResourceException("Access to this information is restricted.");
		}
        logger.debug("Leaving the getStudentInformationRecords page.");
        return studentInfo;
    }
    
    @RequestMapping(value = "getViewDuplicateStudentInformationRecords.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getViewDuplicateStudentInformationRecords(
    		@RequestParam("orgChildrenIds") String[] orgChildrenIds,    		
    		@RequestParam("rows") String limitCountStr,
    		@RequestParam("page") String page,
    		@RequestParam("sidx") String sortByColumn,
    		@RequestParam("sord") String sortType,    		
    		@RequestParam("filters") String filters) throws NoAccessToResourceException {
        logger.debug("Entering the getStudentInformationRecords page for getting results");
        Map<String, Object> studentInfo = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
           
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();                                          		
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
           
        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
        Long educatorId = null;

        if(userDetails.getUser().isTeacher()){
        	educatorId = userDetails.getUserId();
        }
        Long userOrgId = userDetails.getUser().getOrganization().getId();
		List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		childrenOrgIds.add(userOrgId);
		if(childrenOrgIds.contains(inputOrgIds.get(0))){
			TimerUtil timerUtil = TimerUtil.getInstance();
	        List<ViewStudentDTO> studentInformationRecords = studentService.getViewDuplicateStudentInformationRecords(
					null, null, inputOrgIds.get(0),
					userDetails, permissionUtil.hasPermission(userDetails.getAuthorities(),
					RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
					sortByColumn, sortType, (currentPage-1)*limitCount,
					limitCount, studentInformationRecordCriteriaMap, userDetails.getUser().isTeacher(), educatorId);
	        
	        studentInfo.put("rows", studentInformationRecords);
	        timerUtil.resetAndLog(logger, "Getting student records took");
	        totalCount = 0;

	        if(!studentInformationRecords.isEmpty()) {
	        	totalCount = studentInformationRecords.get(0).getTotalRecords();
	        }
	        timerUtil.resetAndLog(logger, "Counting student records took ");
	        studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
	        studentInfo.put("page", currentPage);
	        studentInfo.put("records", totalCount);
		}else{
			throw new NoAccessToResourceException("Access to this information is restricted.");
		}
        logger.debug("Leaving the getStudentInformationRecords page.");
        return studentInfo;
    }
    /**
     * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15460 : View Student - new detail overlay
     * This method is called when user clicks on the student state identifier link provided in
     * view students grid. It gets the student information including the enrollment and roster
     * details.
     * @param studentId
     * @return
     * @throws NoAccessToResourceException
     */
    @RequestMapping(value = "viewStudentDetails.htm", method = RequestMethod.POST)
    public final ModelAndView viewStudentDetails(
    		final @RequestParam("studentId") Long studentId,final @RequestParam("action") String action,
    		final @RequestParam("selectedOrg[]") String[] selectedOrg) throws NoAccessToResourceException, JsonProcessingException {
        logger.debug("Entering the viewStudentDetails page for getting results");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();
        
        List<Long> inputOrgIds = NumericUtil.convert(selectedOrg);
        boolean isDLMAuthenticated = false;
        boolean isTeacherSelected = false;
        /**
         * Populate required criteria that will be used to filter data.
         */
        Map<String, Long> studentInformationRecordCriteriaMap = new HashMap<String, Long>();
        List<StudentExitCodesDTO> exitCodes = new ArrayList<StudentExitCodesDTO>();
        studentInformationRecordCriteriaMap.put("studentId", studentId);
        if(!"exit".equalsIgnoreCase(action)){
        	studentInformationRecordCriteriaMap.put("orgChildrenById", userDetails.getUser().getOrganizationId());
        }else{
        	studentInformationRecordCriteriaMap.put("orgChildrenById", inputOrgIds.get(0));
        	
        	exitCodes = studentService.getExitCodesByState(userDetails.getUser().getCurrentAssessmentProgramId(), 
        			userDetails.getUser().getCurrentOrganizationId(), userDetails.getUser().getContractingOrganization().getCurrentSchoolYear());        	
        }
        studentInformationRecordCriteriaMap.put("userId", userDetails.getUser().getId());
        
        /**
         * Get student details by student id received from grid.
         */
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        List<ViewStudentDTO> studentInformationRecords = studentService.getStudentDetailsByStudentId(studentInformationRecordCriteriaMap, userDetails.getUser().isTeacher(), currentSchoolYear);
        
        //Added to fix DE10148
        Groups group=groupService.getGroup(userDetails.getUser().getCurrentGroupsId());
        if ("Teacher".equals(group.getGroupName())) {
        	isTeacherSelected = true;
		}
        
        /**
         * Added during US16243
         * Check whether User is DLM Authenticated or not 
        **/
         isDLMAuthenticated = userService.checkDLMAuthentication(userDetails.getUser(), currentSchoolYear);
        
        /**
         * Populate the school enrollment details for each subject.
         */
        Map<String,ArrayList<String>> studentSubjectMap = new HashMap<String, ArrayList<String>>();
        Map<String,ArrayList<String>> studentCourseMap = new HashMap<String, ArrayList<String>>();
        Map<String,ArrayList<String>> studentEducatorMap = new HashMap<String, ArrayList<String>>();
        Map<String,ArrayList<String>> studentRosterMap = new HashMap<String, ArrayList<String>>();
        
        if(studentInformationRecords != null && studentInformationRecords.size()>0){
        	for(ViewStudentDTO dto : studentInformationRecords){
        		if(studentSubjectMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> subjectL = (ArrayList<String>) studentSubjectMap.get(dto.getSchoolName());
        			subjectL.add(dto.getSubjectName());
        		} else {
        			ArrayList<String> subjectL = new ArrayList<String>();
        			subjectL.add(dto.getSubjectName());
        			studentSubjectMap.put(dto.getSchoolName(), subjectL);
        		}
        		
        		if(studentCourseMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> courseL = (ArrayList<String>) studentCourseMap.get(dto.getSchoolName());
        			courseL.add(dto.getCourseName());
        		} else {
        			ArrayList<String> courseL = new ArrayList<String>();
        			courseL.add(dto.getCourseName());
        			studentCourseMap.put(dto.getSchoolName(), courseL);
        		}
        		
        		if(studentEducatorMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> educatorL = (ArrayList<String>) studentEducatorMap.get(dto.getSchoolName());
        			educatorL.add(dto.getEducatorName());
        		} else {
        			ArrayList<String> educatorL = new ArrayList<String>();
        			educatorL.add(dto.getEducatorName());
        			studentEducatorMap.put(dto.getSchoolName(), educatorL);
        		}
        		
        		if(studentRosterMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> rosterL = (ArrayList<String>) studentRosterMap.get(dto.getSchoolName());
        			rosterL.add(dto.getRosterName());
        		} else {
        			ArrayList<String> rosterL = new ArrayList<String>();
        			rosterL.add(dto.getRosterName());
        			studentRosterMap.put(dto.getSchoolName(), rosterL);
        		}
        		
        	}
    		
        }
        
        /**
         * Add data to scope and render view.
         */
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        ModelAndView mav = new ModelAndView("viewStudentDetails");
     	mav.addObject("students",studentInformationRecords);
     	mav.addObject("studentSubjects",studentSubjectMap);
     	mav.addObject("studentCourses",studentCourseMap);
     	mav.addObject("studentEducators",studentEducatorMap);
     	mav.addObject("studentRosters",studentRosterMap);
     	mav.addObject("isDLMAuthenticated", isDLMAuthenticated);
     	mav.addObject("isTeacherSelected", isTeacherSelected);
     	mav.addObject("exitCodes",objectMapper.writeValueAsString(exitCodes));
     	mav.addObject("action", action);
     	boolean hasFcsReadOnlyPermission = firstContactService.hasFcsReadOnlyPermission();
		mav.addObject("hasFcsReadOnlyPermission", hasFcsReadOnlyPermission);
     	if("exit".equalsIgnoreCase(action)){
     	    mav.addObject("selectedOrg", inputOrgIds.get(0));
     	}
     	logger.debug("Leaving viewStudentDetails method");
         return mav;
    }

    
    @RequestMapping(value = "viewInactiveStudentDetails.htm", method = RequestMethod.POST)
    public final ModelAndView viewInactiveStudentDetails(
    		final @RequestParam("studentId") Long studentId,final @RequestParam("action") String action,
    		final @RequestParam("selectedOrg[]") String[] selectedOrg) throws NoAccessToResourceException {
        logger.debug("Entering the viewStudentDetails page for getting results");
        
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
        		).getAuthentication().getPrincipal();
        
        List<Long> inputOrgIds = NumericUtil.convert(selectedOrg);
        boolean isDLMAuthenticated = false;
        boolean isTeacherSelected = false;
        /**
         * Populate required criteria that will be used to filter data.
         */
        Map<String, Long> studentInformationRecordCriteriaMap = new HashMap<String, Long>();
        studentInformationRecordCriteriaMap.put("studentId", studentId);
        if(!"exit".equalsIgnoreCase(action)){
        	studentInformationRecordCriteriaMap.put("orgChildrenById", userDetails.getUser().getOrganizationId());
        }else{
        	studentInformationRecordCriteriaMap.put("orgChildrenById", inputOrgIds.get(0));
        }
        studentInformationRecordCriteriaMap.put("userId", userDetails.getUser().getId());
        
        /**
         * Get student details by student id received from grid.
         */
        int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
        List<ViewStudentDTO> studentInformationRecords = studentService.getInactiveStudentDetailsByStudentId(studentInformationRecordCriteriaMap, userDetails.getUser().isTeacher(), currentSchoolYear);
        
        //Added to fix DE10148
        Groups group=groupService.getGroup(userDetails.getUser().getCurrentGroupsId());
        if ("Teacher".equals(group.getGroupName())) {
        	isTeacherSelected = true;
		}
        
        /**
         * Added during US16243
         * Check whether User is DLM Authenticated or not 
        **/
         isDLMAuthenticated = userService.checkDLMAuthentication(userDetails.getUser(), currentSchoolYear);
        
        /**
         * Populate the school enrollment details for each subject.
         */
        Map<String,ArrayList<String>> studentSubjectMap = new HashMap<String, ArrayList<String>>();
        Map<String,ArrayList<String>> studentCourseMap = new HashMap<String, ArrayList<String>>();
        Map<String,ArrayList<String>> studentEducatorMap = new HashMap<String, ArrayList<String>>();
        Map<String,ArrayList<String>> studentRosterMap = new HashMap<String, ArrayList<String>>();
        
        if(studentInformationRecords != null && studentInformationRecords.size()>0){
        	for(ViewStudentDTO dto : studentInformationRecords){
        		if(studentSubjectMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> subjectL = (ArrayList<String>) studentSubjectMap.get(dto.getSchoolName());
        			subjectL.add(dto.getSubjectName());
        		} else {
        			ArrayList<String> subjectL = new ArrayList<String>();
        			subjectL.add(dto.getSubjectName());
        			studentSubjectMap.put(dto.getSchoolName(), subjectL);
        		}
        		
        		if(studentCourseMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> courseL = (ArrayList<String>) studentCourseMap.get(dto.getSchoolName());
        			courseL.add(dto.getCourseName());
        		} else {
        			ArrayList<String> courseL = new ArrayList<String>();
        			courseL.add(dto.getCourseName());
        			studentCourseMap.put(dto.getSchoolName(), courseL);
        		}
        		
        		if(studentEducatorMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> educatorL = (ArrayList<String>) studentEducatorMap.get(dto.getSchoolName());
        			educatorL.add(dto.getEducatorName());
        		} else {
        			ArrayList<String> educatorL = new ArrayList<String>();
        			educatorL.add(dto.getEducatorName());
        			studentEducatorMap.put(dto.getSchoolName(), educatorL);
        		}
        		
        		if(studentRosterMap.get(dto.getSchoolName()) != null){
        			ArrayList<String> rosterL = (ArrayList<String>) studentRosterMap.get(dto.getSchoolName());
        			rosterL.add(dto.getRosterName());
        		} else {
        			ArrayList<String> rosterL = new ArrayList<String>();
        			rosterL.add(dto.getRosterName());
        			studentRosterMap.put(dto.getSchoolName(), rosterL);
        		}
        		
        	}
    		
        }
        
        /**
         * Add data to scope and render view.
         */
        ModelAndView mav = new ModelAndView("viewStudentDetails");
     	mav.addObject("students",studentInformationRecords);
     	mav.addObject("studentSubjects",studentSubjectMap);
     	mav.addObject("studentCourses",studentCourseMap);
     	mav.addObject("studentEducators",studentEducatorMap);
     	mav.addObject("studentRosters",studentRosterMap);
     	mav.addObject("isDLMAuthenticated", isDLMAuthenticated);
     	mav.addObject("isTeacherSelected", isTeacherSelected);
     	mav.addObject("action", action);
     	boolean hasFcsReadOnlyPermission = firstContactService.hasFcsReadOnlyPermission();
		mav.addObject("hasFcsReadOnlyPermission", hasFcsReadOnlyPermission);
     	if("exit".equalsIgnoreCase(action)){
     	    mav.addObject("selectedOrg", inputOrgIds.get(0));
     	}
     	logger.debug("Leaving viewStudentDetails method");
         return mav;
    }
    
    /**
     * Method to build the map for StudentInformationRecordFilter Criteria.
     * @param recordBrowserFilter
     * @return
     */
    private Map<String,String> constructStudentInformationRecordFilterCriteria(String filters) {
    	logger.debug("Entering the constructStudentRosterFilterCriteria method");
    	Map<String,String> studentRosterCriteriaMap = new HashMap<String, String>();
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
		    		studentRosterCriteriaMap.put(recordBrowserFilterRules.getField(), 
		    				CommonConstants.PERCENTILE_DELIM + recordBrowserFilterRules.getData().trim() + CommonConstants.PERCENTILE_DELIM);	    		
		    	}
	    	}
    	}
    	logger.debug(""+studentRosterCriteriaMap);
    	logger.debug("Leaving the constructStudentRosterFilterCriteria method");
    	return studentRosterCriteriaMap;
    }
    
    /**
     * This method pulls all the labels and responses along with student marked responses. 
     * @param studentId
     * @return
     */
    @RequestMapping(value = "firstContactResponseView.htm", method = RequestMethod.GET)
    public final ModelAndView firstContactResponseView(final @RequestParam Long studentId) {
    	logger.debug("Entering firstContactResponseView method");
    	
    	
   	    List<SurveySection> surveyRootSections=firstContactService.getSurveyRootSections();
   	    List <SurveySection> surveySubSections=firstContactService.getSurveySubSections();
   	    List <SurveySection> noOfSubSections=firstContactService.getNoOfSubSections();
   	    List<Integer> globalPageNumList = new ArrayList<>();
    	ModelAndView mav = new ModelAndView("firstContactReponseView");
    	Boolean allQuestionsRequired = false;
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long organizationId = userDetails.getUser().getOrganizationId();
        
        FirstContactSettings firstContactSettings = firstContactService.getFirstContactSurveySetting(organizationId);
        
        Category allQuestionsRequiredOption = categoryService.selectByCategoryCodeAndType("ALL_QUESTIONS", "FIRST CONTACT SETTINGS");
        
        if(allQuestionsRequiredOption != null && firstContactSettings != null){
        	if(allQuestionsRequiredOption.getId().equals(firstContactSettings.getCategoryId())){
        		allQuestionsRequired = true;
        	}
        }
        for(SurveySection surveySubSection : surveySubSections){
        	globalPageNumList.add(surveySubSection.getGlobalPageNumber());
		}
    	mav.addObject("studentId", studentId);
    	Survey survey = firstContactService.findByStudentId(studentId);
    	mav.addObject("check", survey != null);
    	mav.addObject("surveyRootSections",surveyRootSections);
    	mav.addObject("noOfRootSections", surveyRootSections.size());
    	mav.addObject("surveySubSections",surveySubSections);
    	mav.addObject("noOfSubSections", surveySubSections.size());
    	mav.addObject("globalPageNumList",globalPageNumList);
    	mav.addObject("fcsLastPageId", noOfSubSections.size() + 1);
    	mav.addObject("allQuestionsRequired", allQuestionsRequired);
    	
    	if (survey != null) {
    		mav.addObject("surveyId", survey.getId());
    	}

    	logger.debug("Leaving firstContactResponseView method");
        return mav;
    }

    /**
     * This method checks for already existing student surveys. 
     * @param studentId
     * @return {@link Boolean}
     */
    @RequestMapping(value = "checkForExistingSurvey.htm", method = RequestMethod.GET)
    public boolean checkForExistingSurveyBool(Long studentId){
    	boolean check=firstContactService.checkForExistingSurvey(studentId);
    	return check;
    }
    
    /**
     * This method pulls all the labels and responses along with student marked responses. 
     * @param studentId
     * @return
     */
    @RequestMapping(value = "getFirstContactResponses.htm", method = RequestMethod.GET)
    public final @ResponseBody Map<String, Object> getFirstContactResponses(
    		final @RequestParam Long studentId,
    		@RequestParam Long surveyId,
    		@RequestParam Integer lastPageToBeSaved,
    		@RequestParam Integer pageNumber,
    		@RequestParam String status) {
    	logger.debug("Entering firstContactResponseView method");
    	Map<String, Object> result = firstContactService.getFirstContactResponses(studentId, surveyId, lastPageToBeSaved, pageNumber, status, false, null,false);
    	logger.debug("Leaving firstContactResponseView method");
        return result;
    }
    
    /**
     * Method to save the student response for labels when the user makes the selection on UI.
     * @param surveyId
     * @param surveyResponseId
     * @return
     */
    @RequestMapping(value = "saveFirstContactResponses.htm", method = RequestMethod.GET)
    public final @ResponseBody Long saveFirstContactResponses(
    		@RequestParam(required = false) Long surveyId, // will be populated except for the first time a save is called
    		@RequestParam Long studentId,
    		@RequestParam int currentPageNumber,
    		@RequestParam(value="labelNumbers[]") String[] labelNumbers,
    		@RequestParam(value="surveyResponseIds[]") Long[] surveyResponseIds,
    		@RequestParam(value="surveyResponseTexts[]") String[] surveyResponseTexts) {
    	logger.debug("Entering saveFirstContactResponses method");
    	String action = StringUtils.EMPTY;
		String jsonBeforeUpdate = null;
		String jsonAfterUpdate = null;
		SurveyJson surveyJson = null;
    	Survey survey = null;
    	if (surveyId == null) {
    		survey = firstContactService.addSurveyIfNotPresent(studentId);
    		surveyId = survey.getId();
    		action = "FCS_INSERTED";
    	} else {
    		survey = firstContactService.findBySurveyId(surveyId);
    		action = "FCS_SECTION_EDITED";
    	}
    	surveyJson = getSurveyJsonobj(surveyId, currentPageNumber, action);
    	if(surveyJson != null) {
    		jsonBeforeUpdate = surveyJson.buildjsonString();
    		if(StringUtils.isBlank(jsonBeforeUpdate)) {
    			surveyJson = surveyMapper.getSurveyStatusJson(surveyId);
    			jsonBeforeUpdate = surveyJson.buildjsonString();
    		}
    	}
    	
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User user = userDetails.getUser();
    	
    	if(labelNumbers!=null && labelNumbers.length>0) {
    		List<StudentSurveyResponseLabel> studentSurveyResponseLabelList = new ArrayList<StudentSurveyResponseLabel>();
    		for(int i=0;i<labelNumbers.length;i++) {
    			StudentSurveyResponseLabel studentSurveyResponseLabel = new StudentSurveyResponseLabel();
    			studentSurveyResponseLabel.setLabelNumber(labelNumbers[i]);
    			studentSurveyResponseLabel.setStudentSurveyResponseId(surveyResponseIds[i]);
    			if(surveyResponseIds[i] <= 0) {
    				studentSurveyResponseLabel.setStudentSurveyResponseId(null);
    	    	}
    			if(surveyResponseTexts[i]!=null && !surveyResponseTexts[i].equals("null")) {
    				studentSurveyResponseLabel.setStudentSurveyResponseText(surveyResponseTexts[i]);	
    			}
    			studentSurveyResponseLabelList.add(studentSurveyResponseLabel);
    		}
    		if(studentSurveyResponseLabelList.size()>0) {
    			Category category = categoryService.selectByCategoryCodeAndType("COMPLETE", "SURVEY_STATUS");
    			if(survey != null && survey.getStatus().equals(category.getId())){
    				List<Integer> surveyPageStatusList = firstContactService.getSurveyPageStatusList(surveyId);
    				int activePageCount = firstContactService.getFirstContactActivePageCount();
    				if(surveyPageStatusList.size() == activePageCount && category.getId().equals(survey.getStatus())){
    					category = categoryService.selectByCategoryCodeAndType("READY_TO_SUBMIT", "SURVEY_STATUS");
    					survey.setStatus(category.getId());
    					firstContactService.updateSurveyStatus(survey);
    				}
    			}
    			firstContactService.addOrUpdateStudentSurvey(surveyId, studentSurveyResponseLabelList, user,currentPageNumber);
    		}
    	}
    	surveyJson = getSurveyJsonobj(surveyId, currentPageNumber, action);
    	if(surveyJson != null) {
    		jsonAfterUpdate = surveyJson.buildjsonString();
    	}
    	if((StringUtils.equalsIgnoreCase(action, "FCS_SECTION_EDITED") && (jsonBeforeUpdate != null || jsonAfterUpdate != null))
				|| StringUtils.equalsIgnoreCase(action, "FCS_INSERTED")) {
    		firstContactService.insertIntoDomainAuditHistory(surveyId, action, SourceTypeEnum.MANUAL.getCode(), jsonBeforeUpdate, jsonAfterUpdate);
    	}
    	logger.debug("Leaving saveFirstContactResponses method");
        return surveyId;
    }
    
    private SurveyJson getSurveyJsonobj(Long surveyId, int currentPageNumber, String action) {		
		if(StringUtils.equals(action, "FCS_INSERTED")) {
    		return  surveyMapper.getStudentAndSurveyValuesForJson(surveyId, null);
    	} else {
    		return surveyMapper.getStudentAndSurveyValuesForJson(surveyId, currentPageNumber);
    	}
	}
    
    /**
     * Method to update the student response for labels with skip logic.
     * @param surveyId
     * @param surveyResponseId
     * @return
     */
    @RequestMapping(value = "updateFirstContactSkippedResponses.htm", method = RequestMethod.GET)
    public final @ResponseBody Boolean updateFirstContactSkippedResponses(
    		@RequestParam Long surveyId,    		
    		@RequestParam(value="labelNumbers[]") String[] labelNumbers) {
    	firstContactService.updateFirstContactSkippedResponses(surveyId, labelNumbers);
        return true;
    }
    
    
    /**
     * Method to update SurveyPageStatus.
     * @param surveyId 
     * @return
     */
    @RequestMapping(value = "updateSurveyPageStatus.htm", method = RequestMethod.GET)
    public final @ResponseBody String updateSurveyPageStatus(
    		@RequestParam Long surveyId,
    		@RequestParam Integer pageNumber) {
    	logger.debug("Entering updateSurveyPageStatus method");
    	
    	//Update pagestatus for previous page to complete.
    	Long userStateId = firstContactService.getUserStateId() ;
    	firstContactService.updateSurveyPageStatus(pageNumber, surveyId, false, userStateId);
    	List<Integer> surveyPageStatusList = firstContactService.getSurveyPageStatusList(surveyId);
    	
    	ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
		Survey survey = firstContactService.findBySurveyId(surveyId);
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("surveyPageStatusList", surveyPageStatusList);
		Category category = categoryService.selectByPrimaryKey(survey.getStatus());
		values.put("surveyStatus", category.getCategoryCode());
		try {
			jsonData = objectMapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			logger.error("Error while creating JSON data", e);
		}
		
    	logger.debug("Leaving updateSurveyPageStatus method");
        return jsonData;
    }
    
    @RequestMapping(value = "updateCurrentSurveyPageStatus.htm", method = RequestMethod.GET)
    public final @ResponseBody String updateCurrentSurveyPageStatus(
    		@RequestParam Long surveyId,
    		@RequestParam Integer pageNumber,
    		@RequestParam String status) {
    	logger.debug("Entering updateSurveyPageStatus method");
    	
    	//Update pagestatus for  page to in complete.
    	Long userStateId = firstContactService.getUserStateId();
    	firstContactService.updateSurveyPageStatusToNotComplete(pageNumber, surveyId, status, userStateId);
    	List<Integer> surveyPageStatusList = firstContactService.getSurveyPageStatusList(surveyId);
    	ObjectMapper objectMapper = new ObjectMapper();
		String jsonData = null;
    	Survey survey = firstContactService.findBySurveyId(surveyId);
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("surveyPageStatusList", surveyPageStatusList);
		Category category = categoryService.selectByPrimaryKey(survey.getStatus());
		values.put("surveyStatus", category.getCategoryCode());
		try {
			jsonData = objectMapper.writeValueAsString(values);
		} catch (JsonProcessingException e) {
			logger.error("Error while creating JSON data", e);
		}
    	logger.debug("Leaving updateSurveyPageStatus method");
        return jsonData;
    }

	
    /**
     * Method to check if the survey is complete or not.
     * @param surveyId 
     * @return
     */
    @RequestMapping(value = "checkSurveyCompletion.htm", method = RequestMethod.GET)
    public final @ResponseBody List<Integer> checkSurveyCompletion(
    		@RequestParam Long surveyId) {
    	logger.debug("Entering checkSurveyCompletion method");
    	
    	List<Integer> activeRootSections = firstContactService.getActiveRootSections(surveyId);
    	
    	logger.debug("Leaving checkSurveyCompletion method");
        return activeRootSections;
    }
    
    
    @RequestMapping(value = "getStudentAssessmentPrograms.htm", method = RequestMethod.GET)
    public final @ResponseBody List<AssessmentProgram> getAssessmentPrograms(@RequestParam("studentId") String studentId) {
		
    	
    	
    	
		//TODO check contract organization tree to get APs
    	List<AssessmentProgram> assessmentPrograms = new ArrayList<AssessmentProgram>();
        
    	
        
        assessmentPrograms=studentService.getStudentAssessmentProgram(Long.parseLong(studentId));
        
        return assessmentPrograms;        
    }
    
    /**
     * This method should only be called when a survey has been submitted as complete.
     * This will mark the survey as complete and then run calculateComplexityBands
     * to create the test session(s).
     * @param surveyId
     */
    @RequestMapping(value = "createTestSessionForFirstContact.htm", method = RequestMethod.POST)
    public final @ResponseBody boolean createTestSessionForFirstContact(
    		@RequestParam Long surveyId) {
    	
    	logger.debug("Entering createTestSessionForFirstContact method");
    	List<Integer> surveyPageStatusList = firstContactService.getSurveyPageStatusList(surveyId);
    	logger.debug("Size of surveyPageStatusList = " + surveyPageStatusList.size() + " for Survery ID = " + surveyId);
    	if(surveyPageStatusList.size() >= firstContactService.getFirstContactActivePageCount()){
    		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        	User user = userDetails.getUser();
        	logger.debug("Calling updateSurveyStatusToComplete to update for Survey Id = " + surveyId);
        	firstContactService.updateSurveyStatusToComplete(surveyId, user);
        	logger.debug("return from updateSurveyStatusToComplete to update for Survey Id = " + surveyId);
        	logger.debug("completed complexity band cal");

    	}
    	logger.debug("Leaving createTestSessionForFirstContact method");
    	return true;
    }
    
    @RequestMapping(value = "activeStudent.htm", method=RequestMethod.POST)
    public final ModelAndView viewActivationPage( @RequestParam(value="stateStudentId",required=true) String stateStudentId,
   		 @RequestParam(value="orgaName",required=true) String orgaName,
			 @RequestParam(value="enrollmentId",required=true) String enrollmentId,
			 @RequestParam(value="studentId",required=true)Long studentId,
   		 @RequestParam(value="districtId",required=false)Long districtId,
   		 @RequestParam(value="schoolId",required=false)Long schoolId,
   		 @RequestParam(value="gradeId",required=false)Long gradeId){
    	boolean haveEnrollment=true;
    	ModelAndView mv = new ModelAndView("activeStudentDetails");
    	Student student = studentService.findById(studentId);
    	if(StringUtils.isEmpty(enrollmentId)||enrollmentId.equals(" ")){
    		haveEnrollment=false;
    		enrollmentId=new String();
    		districtId=-1l;
    		schoolId=-1l;
    		gradeId=-1l;
    	}
    	
    	Map<String, String> languageMap = new LinkedHashMap<String, String>();
    	Map<String, String> comprehensiveRaceMap = new LinkedHashMap<String, String>();
    	Map<String, String> primaryDisabilitiesMap = new LinkedHashMap<String, String>();
    	Map<Long, String> currenrGradeMap = new LinkedHashMap<Long, String>();    	
    	Map<String, String> esolParticipationMap = new LinkedHashMap<String, String>();
    	Map<String, String> generationMap = new LinkedHashMap<String, String>();
    	Map<String, String> genderMap = new LinkedHashMap<String, String>();
    	Map<String, String> hispanicEthinicityMap = new LinkedHashMap<String, String>();
    	Map<String, String> giftedStudentMap = new LinkedHashMap<String, String>();

    	List<Category> languages = categoryService.selectByCategoryType("FIRST_LANGUAGE");
    	List<Category> comprehensiveRaces = categoryService.selectByCategoryType("COMPREHENSIVE_RACE");
    	List<Category> primaryDisabilities = categoryService.selectByCategoryType("PRIMARY_DISABILITY_CODES");

    	//Esol participationcode
    	List<AppConfiguration> esolParticipationcodes = appConfigurationService.selectByAttributeType(CommonConstants.ESOL_PARTICIPATION_CODE_ATTRIBUTE_TYPE);
    	
    	//Generation
    	List<AppConfiguration> generations = appConfigurationService.selectByAttributeType(CommonConstants.GENERATION_ATTRIBUTE_TYPE);
    	  
   	  	//Gender
    	List<AppConfiguration> genders = appConfigurationService.selectByAttributeType(CommonConstants.GENDER_ATTRIBUTE_TYPE);
   	  
   	  	//Hispanic Ethinicity
    	UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		
    	List<AppConfiguration> hispanicEthinicitiesNullCheck = appConfigurationService.selectByAttributeTypeAndAssessmentProgramIdForHispanic(CommonConstants.HISPANIC_CODE_ASSESSMENT_PROGRAM,user.getCurrentAssessmentProgramId());
   	 	List<AppConfiguration> hispanicEthinicities;
   	 	if(hispanicEthinicitiesNullCheck.size()>0){
   		   hispanicEthinicities = appConfigurationService.selectByAttributeTypeAndAssessmentProgramId(CommonConstants.HISPANIC_CODE_ASSESSMENT_PROGRAM,user.getCurrentAssessmentProgramId());
   		}else {
   		  hispanicEthinicities = appConfigurationService.selectByAttributeTypeAndDefaultAssessment(CommonConstants.HISPANIC_ETHINICITY_ATTRIBUTE_TYPE);
   		}
   	  
   	  	//Gifted Student
    	List<AppConfiguration> giftedStudents = appConfigurationService.selectByAttributeType(CommonConstants.GIFTED_STUDENT_ATTRIBUTE_TYPE);
    	
    	List<GradeCourse> currentGrade = gradeCourseService.getAllIndependentGrades();
    	
    	Comparator<Category> alphabetical = new Comparator<Category>(){
			public int compare(Category o1, Category o2) {
				return o1.getCategoryName().compareTo(o2.getCategoryName());
			}
    	};
    	
 		Collections.sort(languages, alphabetical);
    	Collections.sort(comprehensiveRaces, alphabetical);
    	Collections.sort(primaryDisabilities, alphabetical);
    	
    	for (AppConfiguration config: esolParticipationcodes) {
    		esolParticipationMap.put(config.getAttributeValue(), config.getAttributeValue()+" - "+config.getAttributeName());
    	}
    	for (AppConfiguration config: generations) {
    		generationMap.put(config.getAttributeValue(), config.getAttributeValue());
    	}
    	for (AppConfiguration config: genders) {
    		genderMap.put(config.getAttributeValue(), config.getAttributeName());
    	}
    	for (AppConfiguration config: hispanicEthinicities) {
    		hispanicEthinicityMap.put(config.getAttributeValue(), config.getAttributeName());
    	}
    	for (AppConfiguration config: giftedStudents) {
    		giftedStudentMap.put(config.getAttributeValue(), config.getAttributeName());
    	}    	
    		
    	for (Category cat: languages) {
    		languageMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (Category cat : comprehensiveRaces) {
    		comprehensiveRaceMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (Category cat : primaryDisabilities) {
    		primaryDisabilitiesMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (GradeCourse grade: currentGrade) {
    		currenrGradeMap.put(grade.getId(),grade.getName());
    	}
        	
 		student.setCurrentSchoolYearEnrollment(user.getContractingOrganization().getCurrentSchoolYear().intValue());    	
    	
    	mv.addObject("haveEnrollment", haveEnrollment);
    	mv.addObject("studentId", student.getId());
    	mv.addObject("student", student);
    	mv.addObject("orgaName", orgaName);
    	mv.addObject("enrollmentId", enrollmentId);
    	mv.addObject("stateStudentId", stateStudentId);
    	mv.addObject("districtId", -1l);
    	mv.addObject("schoolId", -1l);
    	mv.addObject("gradeId", -1l);
    	
    	mv.addObject("languageOptions", languageMap);
    	mv.addObject("comprehensiveRaceOptions", comprehensiveRaceMap);
    	mv.addObject("primaryDisabilityOptions", primaryDisabilitiesMap);
      	mv.addObject("currentGradeOptions",currenrGradeMap);	
      	mv.addObject("esolParticipationOptions", esolParticipationMap);
     	mv.addObject("generationOptions", generationMap);
     	mv.addObject("genderOptions", genderMap);
     	mv.addObject("hispanicEthinicityOptions", hispanicEthinicityMap);
     	mv.addObject("giftedStudentOptions", giftedStudentMap);
     	
    	return mv;
    }
    
    @RequestMapping(value = "editStudent.htm")
    public final ModelAndView editStudent(@RequestParam Long studentId) {
    	ModelAndView mav = new ModelAndView("editStudent");
    	Student student = studentService.findById(studentId);
    	
   		student.setDlmStudent(student.getAssessmentProgramId() != null);
    	List<Category> languages = categoryService.selectByCategoryType("FIRST_LANGUAGE");
    	List<Category> comprehensiveRaces = categoryService.selectByCategoryType("COMPREHENSIVE_RACE");
    	List<Category> primaryDisabilities = categoryService.selectByCategoryType("PRIMARY_DISABILITY_CODES");

    	//Esol participationcode
		List<AppConfiguration> esolParticipationcodes = appConfigurationService
				.selectByAttributeType(CommonConstants.ESOL_PARTICIPATION_CODE_ATTRIBUTE_TYPE);
		String assessmentProgramESOLParticipationCode = appConfigurationService
				.getByAttributeCode(CommonConstants.ESOL_PARTICIPATION_CODE_ASSESSMENT_PROGRAM);
		List<String> assessmentProgramList = new ArrayList<String>();
		if (StringUtils.isNotBlank(assessmentProgramESOLParticipationCode)
				&& assessmentProgramESOLParticipationCode.contains(",")) {
			String aPrgm[] = assessmentProgramESOLParticipationCode.split("\\,");
			if (aPrgm.length > 0) {
				for (int j = 0; j < aPrgm.length; j++) {
					assessmentProgramList.add(aPrgm[j]);
				}
			}
		} else {
			assessmentProgramList.add(assessmentProgramESOLParticipationCode);
		}	
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userDetails.getUser();
		Organization current = user.getOrganization();

		Iterator<AppConfiguration> iterator = esolParticipationcodes.iterator();
		while (iterator.hasNext()) {
			AppConfiguration next = iterator.next();
			if (assessmentProgramList.contains(user.getCurrentAssessmentProgramName())) {
				if (next.getAttributeValue().equals("4")) {
					iterator.remove();
				}
			} else {
				if (next.getAttributeValue().equals("7") || next.getAttributeValue().equals("8")) {
					iterator.remove();
				}
			}
		}
		Collections.sort(esolParticipationcodes, new Comparator<AppConfiguration>() {
			public int compare(AppConfiguration ap1, AppConfiguration ap2) {
				return ap1.getAttributeName().compareTo(ap2.getAttributeName());
			}
		});
   	 
    	//Generation
    	List<AppConfiguration> generations = appConfigurationService.selectByAttributeType(CommonConstants.GENERATION_ATTRIBUTE_TYPE);
    	  
   	  	//Gender
    	List<AppConfiguration> genders = appConfigurationService.selectByAttributeType(CommonConstants.GENDER_ATTRIBUTE_TYPE);
   	  
   	  	//Hispanic Ethinicity
    	// F933-Code is added based on assessment program id drop down value needs to be displayed.
    	 List<AppConfiguration> hispanicEthinicitiesNullCheck = appConfigurationService.selectByAttributeTypeAndAssessmentProgramIdForHispanic(CommonConstants.HISPANIC_CODE_ASSESSMENT_PROGRAM,userDetails.getUser().getCurrentAssessmentProgramId());
    	 List<AppConfiguration> hispanicEthinicities;
    	 if(hispanicEthinicitiesNullCheck.size()>0){
    		   hispanicEthinicities = appConfigurationService.selectByAttributeTypeAndAssessmentProgramId(CommonConstants.HISPANIC_CODE_ASSESSMENT_PROGRAM,userDetails.getUser().getCurrentAssessmentProgramId());
    		}
    	 else {
    		  hispanicEthinicities = appConfigurationService.selectByAttributeTypeAndDefaultAssessment(CommonConstants.HISPANIC_ETHINICITY_ATTRIBUTE_TYPE);
    
    	 }
   	  
   	  	//Gifted Student
    	List<AppConfiguration> giftedStudents = appConfigurationService.selectByAttributeType(CommonConstants.GIFTED_STUDENT_ATTRIBUTE_TYPE);
    	
    	
    	List<AssessmentProgram> studentAssessment= studentService.getStudentAssessmentProgram(studentId);
    	List<GradeCourse> currentGrade = gradeCourseService.getAllIndependentGrades();
//      	
    	
    	
        //DE14558 - changed the query that gets the enrollments for the student to also include the school year to limit the enrollments
    	List<Enrollment> studentEnrollment = enrollmentService.findEnrollmentForStudentEdit(studentId, user.getContractingOrganization().getCurrentSchoolYear());
        //TODO check contract organization tree to get APs
        List<AssessmentProgramDto> assessmentPrograms = new ArrayList<AssessmentProgramDto>();
        List<String> validAssessmentPrograms = Arrays.asList(reportingAssessmentPrograms.split("\\s*,\\s*"));

  //      if (current != null) {
            List<OrgAssessmentProgram> orgAssessProgs = orgAssessProgService.findByOrganizationId(current.getId());
            
  //      }

    	
    		
    	
    	Enrollment enrollment=null;
    	if(!(studentEnrollment == null) && studentEnrollment.size() != 0)
    	{    	
    		enrollment = studentEnrollment.get(0);
    		student.setCurrentSchoolYearEnrollment(enrollment.getCurrentSchoolYear());
    		student.setCurrentGradeLevelEnrollment(enrollment.getCurrentGradeLevel());
    		student.setGiftedStudentEnrollment(enrollment.getGiftedStudent());    	
    		student.setStateEntryDateEnrollment(enrollment.getStateEntryDate());
    		student.setDistrictEntryDateEnrollment(enrollment.getDistrictEntryDate());
    		student.setSchoolEntryDateEnrollment(enrollment.getSchoolEntryDate());
    		student.setLocalStudentIdentifier(enrollment.getLocalStudentIdentifier());
    		student.setAypSchoolIdentifier(enrollment.getAypSchoolIdentifier());
    		student.setSchoolName(enrollment.getSchoolName());
    		student.setResidenceDistrictIdentifier(enrollment.getResidenceDistrictIdentifier());
    		student.setDistrictName(enrollment.getDistrictName());
    		student.setAccountabilityDistrictIdentifier(enrollment.getAccountabilityDistrictIdentifier());
    		student.setAccountabilityDistrictName(enrollment.getAccountabilityDistrictName());
    		student.setAccountabilitySchoolIdentifier(enrollment.getAccountabilityschoolidentifier());
    		student.setAccountabilitySchoolName(enrollment.getAccountabilitySchoolName());
    	}
    		
    	Comparator<Category> alphabetical = new Comparator<Category>(){
			public int compare(Category o1, Category o2) {
				return o1.getCategoryName().compareTo(o2.getCategoryName());
			}
    	};
    	Collections.sort(languages, alphabetical);
    	Collections.sort(comprehensiveRaces, alphabetical);
    	Collections.sort(primaryDisabilities, alphabetical);
    	
    	Map<String, String> languageMap = new LinkedHashMap<String, String>();
    	Map<String, String> comprehensiveRaceMap = new LinkedHashMap<String, String>();
    	Map<String, String> primaryDisabilitiesMap = new LinkedHashMap<String, String>();
    	Map<Long, String> currenrGradeMap = new LinkedHashMap<Long, String>();
    	Map<Long, String> studentAssessmentMap = new LinkedHashMap<Long, String>();
    	Map<Long, String> fillAssessmentProgramComboMap =new LinkedHashMap<Long,String>();
    	
    	Map<String, String> esolParticipationMap = new LinkedHashMap<String, String>();
    	Map<String, String> generationMap = new LinkedHashMap<String, String>();
    	Map<String, String> genderMap = new LinkedHashMap<String, String>();
    	Map<String, String> hispanicEthinicityMap = new LinkedHashMap<String, String>();
    	Map<String, String> giftedStudentMap = new LinkedHashMap<String, String>();
    	
    	for (OrgAssessmentProgram oap : orgAssessProgs) {
        	if (validAssessmentPrograms.contains(oap.getAssessmentProgram().getProgramName())){
        		fillAssessmentProgramComboMap.put(oap.getAssessmentProgram().getId(), oap.getAssessmentProgram().getProgramName());
			
        	}
        }
    	
    	for (AppConfiguration config: esolParticipationcodes) {
    		esolParticipationMap.put(config.getAttributeValue(), config.getAttributeValue()+" - "+config.getAttributeName());
    	}
    	for (AppConfiguration config: generations) {
    		generationMap.put(config.getAttributeValue(), config.getAttributeValue());
    	}
    	for (AppConfiguration config: genders) {
    		genderMap.put(config.getAttributeValue(), config.getAttributeName());
    	}
    	for (AppConfiguration config: hispanicEthinicities) {
    		hispanicEthinicityMap.put(config.getAttributeValue(), config.getAttributeName());
    	}
    	for (AppConfiguration config: giftedStudents) {
    		giftedStudentMap.put(config.getAttributeValue(), config.getAttributeName());
    	}
    	
    	
    	for (Category cat: languages) {
    		languageMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (Category cat : comprehensiveRaces) {
    		comprehensiveRaceMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (Category cat : primaryDisabilities) {
    		primaryDisabilitiesMap.put(cat.getCategoryCode(), cat.getCategoryCode()+" - "+cat.getCategoryName());
    	}
    	for (GradeCourse grade: currentGrade) {
    		currenrGradeMap.put(grade.getId(),grade.getName());
    	}
    
    	for (AssessmentProgram asmtProgram: studentAssessment) {
    		studentAssessmentMap.put(asmtProgram.getId(),asmtProgram.getAbbreviatedname());
		}
    	//DE13887 -- Disability code is not visible in grid
    	if(!StringUtils.isEmpty(student.getPrimaryDisabilityCode())){
    		student.setPrimaryDisabilityCode(student.getPrimaryDisabilityCode().toUpperCase());
    	}
    	
        boolean isDLMOnly = isStudentOnlyInDLM(studentId);
        boolean doesStudentHaveTestSessions = false;
        for (Enrollment e : studentEnrollment) {
        	doesStudentHaveTestSessions |= testSessionSevice.doesStudentHaveTestSessionsForCurrentGrade(studentId, e.getCurrentSchoolYear(), e.getAttendanceSchoolId(), e.getCurrentGradeLevel());
        }
    	
    	// have to add ID manually because of the Identifiable interface on Student
    	mav.addObject("studentId", student.getId());
    	mav.addObject("student", student);
    	mav.addObject("languageOptions", languageMap);
    	mav.addObject("comprehensiveRaceOptions", comprehensiveRaceMap);
    	mav.addObject("primaryDisabilityOptions", primaryDisabilitiesMap);
    	mav.addObject("studentEnrollmentOptions",enrollment);
     	mav.addObject("currentGradeOptions",currenrGradeMap);
     	mav.addObject("assessmentProgram",studentAssessmentMap);
     	mav.addObject("fillAssessmentProgramComboMapOptions",fillAssessmentProgramComboMap);
     	mav.addObject("isDLMOnly", isDLMOnly);
     	mav.addObject("doesStudentHaveTestSessions", doesStudentHaveTestSessions);
     	
     	mav.addObject("esolParticipationOptions", esolParticipationMap);
     	mav.addObject("generationOptions", generationMap);
     	mav.addObject("genderOptions", genderMap);
     	mav.addObject("hispanicEthinicityOptions", hispanicEthinicityMap);
     	mav.addObject("giftedStudentOptions", giftedStudentMap);
    	  
    	return mav;
    }
    
	@RequestMapping(value = "isStudentDemographicValueExists.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> isStudentDemographicValueExists(
			@RequestParam("addStudentSchool") Long schoolId,
			@RequestParam("legalFirstName") String legalFirstName,
			@RequestParam("legalLastName") String legalLastName,
			@RequestParam("gender") Long gender,
			@RequestParam("dobDate") String dobDate){
			Date dobDates = DateUtil.parse(dobDate);
			Long stateId = studentService.getStudentStateIdBySchoolId(schoolId);
		boolean demographic = studentService.isAddStudentDemographicValueExists(legalFirstName, legalLastName, gender, dobDates, stateId);
		return isDemographicResult(demographic);
	}
    
    @RequestMapping(value = "isEditStudentDemographicValueExists.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> isEditStudentDemographicValueExists(
			@RequestParam("legalFirstName") String legalFirstName,
			@RequestParam("legalLastName") String legalLastName,
			@RequestParam("gender") Long gender,
			@RequestParam("dateOfBirth") String dobDate,
			@RequestParam("stateStudentIdentifier") String stateStudentId,
			@RequestParam("stateId") Long stateId){
			Date dobDates = DateUtil.parse(dobDate);
		boolean demographic = studentService.isEditStudentDemographicValueExists(stateStudentId, legalFirstName, legalLastName, gender, dobDates, stateId);
		return isDemographicResult(demographic);
	}

    private Map<String, Object> isDemographicResult(boolean demographic) {
		Map<String, Object> result = new HashMap<String, Object>();
		if(demographic) {
			result.put("demographicMatches", true);
		}
		return result;
	}
    
    @RequestMapping(value = "saveEditedStudent.htm", method = RequestMethod.POST)
    public final @ResponseBody String saveEditedStudent(final HttpServletRequest request,
    		@ModelAttribute("student") Student newStudent) {
    	String ret = "success";
    	
    	String[] assessments=request.getParameterValues("assessmentProgramStudentEdit");
    	Long[] assessmentPrograms= new Long[assessments.length];
    	for (int i=0;i<assessments.length;i++) {
			assessmentPrograms[i]=Long.parseLong(assessments[i]);
		}
    	
    	try {
    		// had to kind of hack the ID in, it wasn't auto-populating in the @ModelAttribute
    		String strId = request.getParameter("id");
    		if (strId != null && !strId.isEmpty()) {
    			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            	User user = userDetails.getUser();
            	Long currentSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
    			Long id = Long.parseLong(strId);
    			newStudent.setModifiedUser(user.getId());
    			/*if (newStudent.isDlmStudent()) {
    				AssessmentProgram ap = assessmentProgramDao.findByProgramName(dlmOrgName);
    				newStudent.setAssessmentProgramId(ap.getId());
    			} else {
    				newStudent.setAssessmentProgramId(null);
    			}*/
    			
    			newStudent.setStudentAssessmentPrograms(assessmentPrograms);
    			
    			if(StringUtils.isNotEmpty(newStudent.getStateStudentIdentifier())) {    				
    				newStudent.setStateStudentIdentifier(newStudent.getStateStudentIdentifier().trim());
    			}
    			ret = studentService.editStudent(id, newStudent, currentSchoolYear);
    		} else {
    			return  "{\"result\": \"invalid\"}";
    		}
    	} catch (Exception e) {
    		logger.error("exception while updating student: ", e);
    		return  "{\"result\": \"fail\"}";
    	}    	
    	return "{\"result\": \""+ret+"\"}";
    }
    
    @RequestMapping(value = "getCreateTestRecordStudentsGridData.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getCreateTestRecordStudentsGridData(
      @RequestParam("orgChildrenIds") String[] orgChildrenIds,
      @RequestParam("assessmentProgramId") Long assessmentProgramId,
      @RequestParam("rows") String limitCountStr,
      @RequestParam("page") String page,
      @RequestParam("sidx") String sortByColumn,
      @RequestParam("sord") String sortType,      
      @RequestParam("filters") String filters) throws NoAccessToResourceException {
        logger.debug("Entering the getCreateTestRecordStudentsGridData page for getting results");
        Map<String, Object> studentInfo = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
        if(assessmentProgramId == 0)
        {
        	assessmentProgramId=null;  
        }
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
          ).getAuthentication().getPrincipal();                                            
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
        assessmentProgramId =userDetails.getUser().getCurrentAssessmentProgramId();
        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
        Long userOrgId = userDetails.getUser().getOrganization().getId();
        List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
        childrenOrgIds.add(userOrgId);
        if(childrenOrgIds.contains(inputOrgIds.get(0))){
         TimerUtil timerUtil = TimerUtil.getInstance();
         List<ViewStudentDTO> studentInformationRecords = studentService.getCreateTestRecordStudentsGridData(inputOrgIds.get(0),assessmentProgramId,userDetails,
        		 sortByColumn, sortType, (currentPage-1)*limitCount,limitCount, studentInformationRecordCriteriaMap);
         
         studentInfo.put("rows", studentInformationRecords);
         timerUtil.resetAndLog(logger, "Getting student records took for getCreateTestRecordStudentsGridData");
         totalCount = studentService.getCreateTestRecordStudentsGridDataCount(inputOrgIds.get(0),assessmentProgramId,userDetails,studentInformationRecordCriteriaMap);
         timerUtil.resetAndLog(logger, "Counting student records took for getCreateTestRecordStudentsGridData");
         studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
         studentInfo.put("page", currentPage);
         studentInfo.put("records", totalCount);
        
	  }else{
	   throw new NoAccessToResourceException("Access to this information is restricted.");
	  }
        logger.debug("Leaving the for getCreateTestRecordStudentsGridData page.");
        return studentInfo;
    }
    
    
    
    @RequestMapping(value = "getClearTestRecordStudentsGridData.htm", method = RequestMethod.POST)
    public final @ResponseBody Map<String, Object> getCreateTestRecordStudentsGridData(
      @RequestParam("orgChildrenIds") String[] orgChildrenIds,
      @RequestParam("assessmentProgramId") Long assessmentProgramId,
      @RequestParam("testTypeCode") String testTypeCode,
      @RequestParam("subjectCode") String subjectCode,
      
      @RequestParam("rows") String limitCountStr,
      @RequestParam("page") String page,
      @RequestParam("sidx") String sortByColumn,
      @RequestParam("sord") String sortType,      
      @RequestParam("filters") String filters) throws NoAccessToResourceException {
        logger.debug("Entering the getCreateTestRecordStudentsGridData page for getting results");
       
        Map<String, Object> studentInfo = new HashMap<String, Object>();
        
        int currentPage = -1;
        int limitCount = -1;
        int totalCount = -1;
        List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
           
        UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext(
          ).getAuthentication().getPrincipal();                                            
        currentPage = NumericUtil.parse(page, 1);
        limitCount = NumericUtil.parse(limitCountStr,5);
           
        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
        Long userOrgId = userDetails.getUser().getOrganization().getId();
        List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
        childrenOrgIds.add(userOrgId);
        if(childrenOrgIds.contains(inputOrgIds.get(0))){
         TimerUtil timerUtil = TimerUtil.getInstance();
         List<ViewStudentDTO> studentInformationRecords = studentService.getClearTestRecordStudentsGridData(inputOrgIds.get(0),testTypeCode,subjectCode,assessmentProgramId,userDetails,
        		 sortByColumn, sortType, (currentPage-1)*limitCount,limitCount, studentInformationRecordCriteriaMap);
         
         studentInfo.put("rows", studentInformationRecords);
         timerUtil.resetAndLog(logger, "Getting student records took for getCreateTestRecordStudentsGridData");
         totalCount = studentService.getClearTestRecordStudentsGridDataCount(inputOrgIds.get(0),testTypeCode,subjectCode,assessmentProgramId,userDetails,studentInformationRecordCriteriaMap);
         timerUtil.resetAndLog(logger, "Counting student records took for getCreateTestRecordStudentsGridData");
         studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
         studentInfo.put("page", currentPage);
         studentInfo.put("records", totalCount);
        
  }else{
   throw new NoAccessToResourceException("Access to this information is restricted.");
  }
        logger.debug("Leaving the for getCreateTestRecordStudentsGridData page.");
        return studentInfo;
    }
    
		    
		    @RequestMapping(value = "getViewTestRecordsByStudentsGridData.htm", method = RequestMethod.POST)
		    public final @ResponseBody Map<String, Object> getViewTestRecordByStudentsGridData(
		      @RequestParam("orgChildrenIds") String[] orgChildrenIds,
		      @RequestParam("studentId") Long studentId,
		      @RequestParam("assessmentProgramId") Long assessmentProgramId,
		      @RequestParam("rows") String limitCountStr,
		      @RequestParam("page") String page,
		      @RequestParam("sidx") String sortByColumn,
		      @RequestParam("sord") String sortType,      
		      @RequestParam("filters") String filters) throws NoAccessToResourceException {
		       logger.debug("Entering the getViewTestRecordsByStudentsGridData page for getting results");
		       Map<String, Object> studentInfo = new HashMap<String, Object>();
		       int currentPage = -1;
		       int limitCount = -1;
		       int totalCount = -1;
		       List<Long> inputOrgIds = NumericUtil.convert(orgChildrenIds);
		       UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		       int currentSchoolYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
		        currentPage = NumericUtil.parse(page, 1);
		        limitCount = NumericUtil.parse(limitCountStr,5);
		           
		        Map<String,String> studentInformationRecordCriteriaMap = constructStudentInformationRecordFilterCriteria(filters);
		        Long userOrgId = userDetails.getUser().getOrganization().getId();
		        List<Long> childrenOrgIds = organizationService.getAllChildrenOrgIds(userOrgId);
		        childrenOrgIds.add(userOrgId);
		       if(childrenOrgIds.contains(inputOrgIds.get(0))){
		         TimerUtil timerUtil = TimerUtil.getInstance();
		         List<ViewStudentDTO> studentInformationRecords = studentService.getTestRecordByStudentsGridData(studentId,inputOrgIds.get(0),
		        		 sortByColumn, sortType, (currentPage-1)*limitCount,limitCount, studentInformationRecordCriteriaMap,assessmentProgramId,currentSchoolYear);
		         
		         studentInfo.put("rows", studentInformationRecords);
		         timerUtil.resetAndLog(logger, "Getting student records took for getCreateTestRecordStudentsGridData");
		         totalCount = studentService.getTestRecordByStudentsGridDataCount(studentId,inputOrgIds.get(0),studentInformationRecordCriteriaMap,assessmentProgramId,currentSchoolYear);
		         timerUtil.resetAndLog(logger, "Counting student records took for getCreateTestRecordStudentsGridData");
		         studentInfo.put("total", NumericUtil.getPageCount(totalCount, limitCount));
		         studentInfo.put("page", currentPage);
		         studentInfo.put("records", totalCount);
		        
		  }else{
		  throw new NoAccessToResourceException("Access to this information is restricted.");
		 }
		        logger.debug("Leaving the for getCreateTestRecordStudentsGridData page.");
		        return studentInfo;
		 }
		    	   
	    @RequestMapping(value = "isStudentInDLMOnly.htm", method = RequestMethod.GET)
	    public @ResponseBody boolean isStudentOnlyInDLM(Long studentId){
	    	return studentService.isStudentOnlyInDLM(studentId);
	    }
	    
     /**
	 *  for US-19355
	 */
	@RequestMapping(value = "changeStudentUsernamePassword.htm", method = RequestMethod.POST)
	public final @ResponseBody String changeStudentUsernamePassword(final HttpServletRequest request,
			final HttpServletResponse response) throws JSONException {
		logger.trace("Entering the changePassword method.");
		Boolean usernameExists=false;
		Boolean passwordExists=false;
		Long studentId = Long.parseLong(request.getParameter("studentId"));
		
		String newStudUsername = request.getParameter("newUsername");
		String dbStudUsername = studentService.checkStudentUsername(newStudUsername,studentId);
		String newStudPassword = request.getParameter("newPassword");
	//	String dbStudPassword = studentService.checkStudentPassword(studentId);
		if((!newStudUsername.equals(dbStudUsername)) && (newStudUsername!=null && !newStudUsername.isEmpty())){
			 studentService.updateStudentUsername(studentId,newStudUsername);
		}
		else{
			usernameExists=true;
		}
		 studentService.updateStudentPassword(studentId,newStudPassword);
		/*if((!newStudPassword.equals(dbStudPassword)) && (newStudPassword!=null && !newStudPassword.isEmpty())){
		}
		else{
			passwordExists=true;
		}*/
		if(usernameExists){
			return "{\"error\":\"  Username is already being used by another student in the system. Please Re Enter Details.\"}";
		}
			return "{\"success\":\"Username and Password updated successfully\"}";
	}
	
	@RequestMapping(value = "getStudentMergeDetails.htm", method = RequestMethod.POST)
    public final @ResponseBody String getStudentMergeDetails(
    		final HttpServletRequest request,
    				final HttpServletResponse response) throws JSONException {
		try{
			Long studentToRetain = Long.parseLong((request.getParameter("studentToRetain")));
			Long studentToRemove = Long.parseLong((request.getParameter("studentToRemove")));
			Long studentIdForPnp = Long.parseLong((request.getParameter("studentPnpToRetain")));
			Long studentIdForFcs = Long.parseLong((request.getParameter("studentFcsToRetain")));
			Long studentIdForRoster = Long.parseLong((request.getParameter("studentRosterToRetain")));
			String[] stringStudentTestIds = request.getParameterValues("selectedStudentTestIdList[studentTestId][]");
			String[] stringUnSelectedStudentTestIds = request.getParameterValues("unSelectedStudentTestIds[]");
			List<Long> selectedStudentTestIds = new ArrayList<Long>();
			List<Long> unSelectedStudentTestIds = new ArrayList<Long>();
			if(stringStudentTestIds != null){
				for(String sst:stringStudentTestIds){
					if(sst!=null){
						selectedStudentTestIds.add(Long.valueOf(sst));
					}
				}
			}
			if(stringUnSelectedStudentTestIds != null){
				for(String usst:stringUnSelectedStudentTestIds){
					if(usst!=null){
						unSelectedStudentTestIds.add(Long.valueOf(usst));
					}
				}
			}
			studentService.mergeStudents(studentToRetain, studentToRemove, studentIdForPnp, studentIdForFcs, studentIdForRoster, selectedStudentTestIds, unSelectedStudentTestIds);
			
			return "{\"success\":\"Merge Student Details Successfully\"}";
		}catch (Exception e) {
			logger.error("Exception occurred while student merge: ", e);
			return "{\"errorFound\":\"true\", \"errorMessage\":\"" + StringEscapeUtils.escapeEcmaScript(e.getMessage()) + "\"}";
		}
	}
	  @SuppressWarnings("unused")
	private void populateCriteria(Map<String, Object> criteria, String filters)
			throws JsonProcessingException, IOException {

		if (null != filters && !filters.equals("")) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(filters);

			final JsonNode results = rootNode.get("rules");
			
			for (final JsonNode element : results) {
				criteria.put(element.get("field").asText(), element
						.get("data").asText());
			}
		}
	}   
    @RequestMapping(value = "/getStudentTestsBasedOnStudentIds.htm", method = RequestMethod.POST)
   	public final @ResponseBody Map<String, Object> getStudentTestsBasedOnStudentIds(final HttpServletRequest request, 
   			final HttpServletResponse response) throws ServiceException {
   		logger.trace("Entering the view method.");
   		Map<String, Object> hashMap = new HashMap<String, Object>();

   		try {	   			
   			String[] stringStudentIds = request.getParameterValues("studentsList[id][]");   			
   			Long[] longStudentIds = new Long[stringStudentIds.length];   
   			for (int i = 0; i < stringStudentIds.length; i++) {   
   				longStudentIds[i] = Long.parseLong(stringStudentIds[i]);   
   			}   			
   			
   			List<ViewMergeStudentDetailsDTO> studentsTests = studentsTestsService.getStudentTestsBasedOnStudentIds(longStudentIds);   	
   			
   			hashMap.put("studentsTest", studentsTests);   		
   		} catch (NumberFormatException e) {
   			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
   			hashMap.put("invalidParams", true);
   		}

   		return hashMap;
   	}
    
    @RequestMapping(value = "getstatestudentidentifierlength.htm", method = RequestMethod.POST)
   	public final @ResponseBody String getStateStudentIdentifierLength(final HttpServletRequest request, 
   			final HttpServletResponse response) throws ServiceException {
    	String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
    	return stateStudentIdentifierLength;
   	}
}
