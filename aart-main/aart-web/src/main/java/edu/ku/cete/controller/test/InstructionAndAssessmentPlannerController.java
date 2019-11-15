package edu.ku.cete.controller.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import edu.ku.cete.domain.ItiTestSessionHistory;
import edu.ku.cete.domain.ItiTestSessionResourceInfo;
import edu.ku.cete.domain.LinkageLevelSortOrder;
import edu.ku.cete.domain.SensitivityTag;
import edu.ku.cete.domain.SpecialCircumstance;
import edu.ku.cete.domain.StudentSpecialCircumstance;
import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.test.ContentFrameworkDetail;
import edu.ku.cete.domain.test.OperationalTestWindowDTO;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ComplexityBandDao;
import edu.ku.cete.report.IAPReportGenerator;
import edu.ku.cete.report.iap.ClaimContextData;
import edu.ku.cete.report.iap.ConceptualAreaContextData;
import edu.ku.cete.report.iap.CriteriaContextData;
import edu.ku.cete.report.iap.IAPReportContext;
import edu.ku.cete.domain.UserSecurityAgreement;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AwsS3Service;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.ContentFrameworkService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.ItiTestSessionService;
import edu.ku.cete.service.MicroMapService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.SensitivityTagService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentSpecialCircumstanceService;
import edu.ku.cete.service.StudentTrackerService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestingCycleService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.ComplexityBandEnum;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.web.IAPContentFramework;
import edu.ku.cete.tde.webservice.client.LMWebClient;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;
import edu.ku.cete.service.UserService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.web.IAPHomeStudentSearchDTO;
import edu.ku.cete.web.IAPStudentTestStatusDTO;

@Controller
public class InstructionAndAssessmentPlannerController {
	
	/**
	 * LOGGER
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InstructionAndAssessmentPlannerController.class);
	
	@Autowired
	private StudentService studentService;
	
	 @Autowired
	private PermissionUtil permissionUtil;
	 
	 @Autowired
	private RosterService rosterService;
	 
	 @Autowired
	private EnrollmentService enrollmentService;
	 
	 @Autowired
	private ContentAreaService contentAreaService;
	 
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private OperationalTestWindowService otwService;
	
	@Autowired
	private MicroMapService microMapService;
	
	@Autowired
	private ContentFrameworkService cfService;
	
	@Autowired
	private TestCollectionService testCollectionService;
	
	@Autowired
	private ItiTestSessionService itiService;
	
	@Autowired
	private LMWebClient lmWebClient;
	
	/**
	 * gradeCourseService
	 */
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private UserService userService;	
	
	@Autowired
	private AppConfigurationService appConfigurationService;
	
	@Autowired
	private StudentTrackerService studentTrackerService;
	
	@Autowired
	private ItiTestSessionService itiTestSessionService;
	
	@Autowired
	private SensitivityTagService sensitivityTagService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private StudentSpecialCircumstanceService studentSCService;
	
	@Autowired
	private IAPReportGenerator iapReportGenerator;
	
	@Value("${print.test.file.path}")
	private String REPORT_PATH;
	
	@Autowired
	private AwsS3Service s3;
	
	//TODO put a service here...bad practice to have the DAO in the controller
	@Autowired
	private TestingCycleService testingCycleService;
	
	@Value("${error.agreement.not.signed}")
	private String errorAgreementNotSigned;
	
	@Value("${error.pdtraining.not.completed}")
	private String errorPdtrainingNotCompleted;
	
	@Value("${error.agreement.and.pdtraining.not.completed}")
	private String errorAgreementAndPdtrainingNotCompleted;
  
	@Value("${nfs.url}")
	private String MEDIA_PATH;
	
	protected static Map<String, String> LEVEL_DESCRIPTION_REPLACEMENTS;
	
	static {
		LEVEL_DESCRIPTION_REPLACEMENTS = new LinkedHashMap<String, String>();
		LEVEL_DESCRIPTION_REPLACEMENTS.put("\u201C",          "\""); // left curly quote
		LEVEL_DESCRIPTION_REPLACEMENTS.put("\u201D",          "\""); // right curly quote
		LEVEL_DESCRIPTION_REPLACEMENTS.put("â\u0080\u009C",   "\""); // left curly quote
		LEVEL_DESCRIPTION_REPLACEMENTS.put("â\u0080\u009D",   "\""); // right curly quote
		LEVEL_DESCRIPTION_REPLACEMENTS.put("â\u0080\u0099",   "'"); // apostrophe
		LEVEL_DESCRIPTION_REPLACEMENTS.put("\u2026",          "..."); // weird ellipses character
		LEVEL_DESCRIPTION_REPLACEMENTS.put("â\u0080¦",        "..."); // weird ellipses character
		LEVEL_DESCRIPTION_REPLACEMENTS.put("\u0080\u009C",    "");
		LEVEL_DESCRIPTION_REPLACEMENTS.put("\u0080\u009D",    "");
		LEVEL_DESCRIPTION_REPLACEMENTS.put("\u0080",          "");
	}
	
	@RequestMapping(value="instruction-planner.htm")
	public final ModelAndView instructionPlanner() throws IOException {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			ModelAndView mav = new ModelAndView("instruction-and-assessment-planner/iap-home");
			
			boolean agreementSigned=true;
			boolean pdTrainingCompleted=true;
			
			User user = userDetails.getUser();
			int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
			List<UserSecurityAgreement> agreements = userService.getDLMActiveUserSecurityAgreementInfo(user.getId(), user.getCurrentAssessmentProgramId(), (long) currentSchoolYear);
			
			if(CollectionUtils.isEmpty(agreements)) {
				agreementSigned=false;
			}
			
			// verify user has completed PDTraining
			if (user.getCurrentAssessmentProgramName() != null 
					&& user.getCurrentAssessmentProgramName().equalsIgnoreCase("DLM")) {
				if(!userService.isPdTrainingCompleted(user.getId(), currentSchoolYear)) {	
					pdTrainingCompleted=false;
				}
			} 
			
			boolean viewTestpermission = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_TEST_VIEW");
			boolean viewRosterpermission = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_ROSTERRECORD_VIEW");
			boolean viewAllRosterpermission = permissionUtil.hasPermission(userDetails.getAuthorities(), "PERM_ROSTERRECORD_VIEWALL");
			if(user.isTeacher() && agreementSigned == false) {
				//Teachers must sign and agree to the security agreement (teacher role only)
				String errorMessage= errorAgreementNotSigned;
				if(pdTrainingCompleted == false) {
					errorMessage= errorAgreementAndPdtrainingNotCompleted;
				}
				mav.addObject("error", errorMessage);
				
			}else if(user.isTeacher() && pdTrainingCompleted== false) {
				//Teachers must complete DLM required training
				String errorMessage= errorPdtrainingNotCompleted;
				mav.addObject("error", errorMessage);
				
			}else if((viewTestpermission && viewRosterpermission) || viewAllRosterpermission) {
				
				List<Organization> organizations = null;
				List<GradeCourse> gradeCourses = new ArrayList<GradeCourse>();
				User Myuser= userDetails.getUser();
				String OrganizationKey ="";
				if("ST".equalsIgnoreCase(Myuser.getCurrentOrganizationType())) {
					OrganizationKey="DT";
				}else if("DT".equalsIgnoreCase(Myuser.getCurrentOrganizationType())) {
					OrganizationKey="SCH";
				}
				organizations = organizationService.getAllChildrenByOrgTypeCode(Myuser.getCurrentOrganizationId(), OrganizationKey); 
				gradeCourses = gradeCourseService.selectAllGradeCoursesDropdown(userDetails.getUser().getCurrentAssessmentProgramId());

				mav.addObject(OrganizationKey, organizations);
				Collections.sort(gradeCourses);
				mav.addObject("grades", gradeCourses);
			}else {
				//User doesn't has View Tests and View Roster or View All Rosters permissions given to their role to access the Instruction and Assessment Planner page
				String errorMessage= "You don't have access to the page.";
				mav.addObject("error", errorMessage);
				LOGGER.warn("User doesn't has View Tests and View Roster or View All Rosters permissions given to their role to access the Instruction and Assessment Planner page. User with ID "+user.getId() +" group id "+ user.getUserGroupId());
			}
			return mav;
		}
		
		userDetails.getUser().getAccessLevel();
		return null;
	}
	
	@RequestMapping(value="instruction-planner-student.htm")
	public final ModelAndView instructionPlannerStudent(@RequestParam(value = "studentID", required = true) Long studentId,
			@RequestParam(value = "rosterID", required = true) Long rosterId,
			@RequestParam(value = "enrollmentRosterID", required = true) Long enrollmentRosterID,
			@RequestParam(value = "otwId", required = false) Long operationalTestWindowId) throws IOException {
		ModelAndView mav = new ModelAndView("instruction-and-assessment-planner/iap-student");
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			mav.addObject("isInstructionallyEmbeddedModel", organizationService.isIEModelState(userDetails.getUser().getContractingOrganization()));
			Long stateId = userDetails.getUser().getContractingOrganization().getId();
			Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
			if(studentId!=null) {
				Roster curRoster = rosterService.selectByPrimaryKey(rosterId);
				//try to do: Modify the get roster call to fetch school details and subject detail in a single call.
				//Enrollment curEnrollment = enrollmentService.getByEnrollmentId(enrollmentID);
				if(curRoster!= null && curRoster!=null) {
					Long schoolId = curRoster.getAttendanceSchoolId();
					Long subjectId =  curRoster.getStateSubjectAreaId();
					List<Long> allChilAndSelfOrgIds = userDetails.getUser().getAllChilAndSelfOrgIds();
					if(schoolId!=null && allChilAndSelfOrgIds.contains(schoolId)) {
						Student student = studentService.getByStudentIDWithFirstContact(studentId);
						ContentArea subject = contentAreaService.selectByPrimaryKey(subjectId);
						Organization orgDetail = organizationService.getViewOrganizationDetailsByOrgId(schoolId);
						GradeCourse grade = gradeCourseService.getGradeCourseByEnrollmentsRostersId(enrollmentRosterID);
						
						mav.addObject("student", student);
						mav.addObject("enrollmentsRostersId", enrollmentRosterID);
						mav.addObject("rosterId", rosterId);
						mav.addObject("subject", subject);
						mav.addObject("schoolName", orgDetail.getOrganizationName());
						mav.addObject("districtName", orgDetail.getParentOrganizationName());
						
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						mav.addObject("studentDateOfBirth", sdf.format(student.getDateOfBirth()));
						OperationalTestWindowDTO otw = otwService.getOpenInstructionAssessmentPlannerWindow(assessmentProgramId, stateId, subject.getId(), operationalTestWindowId);
						mav.addObject("otw", otw);
						
						// at initial time of implementation, DLM will only have 2 windows, Fall and Spring, so if there is a previous one, we use it.
						List<OperationalTestWindowDTO> previousOTWs = otwService.getPreviousInstructionPlannerWindowsByStateAssessmentProgramAndSchoolYear(
								stateId, assessmentProgramId, curRoster.getCurrentSchoolYear());
						
						mav.addObject("previousWindow", CollectionUtils.isNotEmpty(previousOTWs) ? previousOTWs.get(0) : null);
						
						mav.addObject("studentGrade", grade.getAbbreviatedName());
						
						List<IAPContentFramework> eeList = otwService.getIAPContentFrameworkForWindow(otw.getOtwId(), subject.getAbbreviatedName(), grade.getAbbreviatedName());
						List<String> eeContentCodes = new ArrayList<String>(eeList.size());
						for (IAPContentFramework ee : eeList) {
							eeContentCodes.add(ee.getEE().getContentCode());
						}
						List<String> linkageLevelNamesFromLM = new ArrayList<String>();
						List<MicroMapResponseDTO> allEELLs = lmWebClient.getLinkageLevelInfo(eeContentCodes);
						Map<String, List<MicroMapResponseDTO>> eeToLLMap = new HashMap<String, List<MicroMapResponseDTO>>();
						
						// populate the linkage levels for the whole subject,
						// and map out the LLs to their respective EEs
						for (int x = 0; x < allEELLs.size(); x++) {
							MicroMapResponseDTO mmrDto = allEELLs.get(x);
							mmrDto.setLinkagelevelshortdesc(replaceSpecialCharacters(mmrDto.getLinkagelevelshortdesc()));
							mmrDto.setLinkagelevellongdesc(replaceSpecialCharacters(mmrDto.getLinkagelevellongdesc()));
							// This condition is mainly for writing, since the LLs won't really exist, but they'll still come back from LM with basically no data.
							// Science has no short descriptions at the moment because DLM doesn't understand their own data, so this condition can probably
							// be re-added at some point once they fix it.
							if (StringUtils.isNotBlank(mmrDto.getLinkagelevellongdesc()) /*&& StringUtils.isNotBlank(mmrDto.getLinkagelevelshortdesc())*/) {
								String contentCode = mmrDto.getAssociatedee();
								String linkageLevelName = mmrDto.getLinkagelabel();
								if (!linkageLevelNamesFromLM.contains(linkageLevelName)) {
									linkageLevelNamesFromLM.add(linkageLevelName);
								}
								
								List<MicroMapResponseDTO> mapResult = eeToLLMap.get(contentCode);
								if (mapResult == null) {
									mapResult = new ArrayList<MicroMapResponseDTO>();
								}
								mapResult.add(mmrDto);
								
								eeToLLMap.put(contentCode, mapResult);
							}
						}
						
						List<LinkageLevelSortOrder> linkageLevelSortOrders = microMapService.getLinkageLevelSortOrders(subject.getId(), linkageLevelNamesFromLM);
						
						Set<Integer> criteriaNumbers = new HashSet<Integer>();
						
						// cache only for setRecommendedLevel()
						Map<Long, ComplexityBandDao> complexityBandCache = new HashMap<Long, ComplexityBandDao>();
						
						// now, sort the linkage levels according to the names from the DB, and attach them to the EE data.
						for (IAPContentFramework ee : eeList) {
							String contentCode = ee.getEE().getContentCode();
							List<MicroMapResponseDTO> lls = eeToLLMap.get(contentCode);
							List<MicroMapResponseDTO> sortedLLs = new ArrayList<MicroMapResponseDTO>(CollectionUtils.isNotEmpty(lls) ? lls.size() : 0);
							
							if (CollectionUtils.isNotEmpty(lls)) {
								for (LinkageLevelSortOrder linkageLevelSortOrder : linkageLevelSortOrders) {
									for (MicroMapResponseDTO mmrDto : lls) {
										if (linkageLevelSortOrder.getLevelName().equals(mmrDto.getLinkagelabel())) {
											sortedLLs.add(mmrDto);
										}
									}
								}
							}
							eeToLLMap.remove(contentCode); // just clean up afterwards
							ee.setLinkageLevels(sortedLLs);
							setRecommendedLevel(student, otw, subject, ee, complexityBandCache);
							
							criteriaNumbers.add(ee.getCriteria());
						}
						
						mav.addObject("EEList", eeList);
						mav.addObject("linkageLevels", linkageLevelSortOrders);
						
						List<ItiTestSessionHistory> itis = itiService.getForIAP(studentId, rosterId, enrollmentRosterID, otw.getOtwId());
						
						if (CollectionUtils.isNotEmpty(itis)) {
							for (ItiTestSessionHistory iti : itis) {
								for (IAPContentFramework ee : eeList) {
									if (ee.getEeId().equals(iti.getEssentialElementId())) {
										ee.addItiEntry(iti);
									}
								}
							}
						}
						
						Map<Integer, Boolean> blueprintCriteriaCompletionMap = new HashMap<Integer, Boolean>();
						for (Integer criteriaNumber: criteriaNumbers) {
							if (criteriaNumber != null) {
								boolean completedCriteria = studentTrackerService.didStudentMeetSpecificBlueprintCriteria(
										studentId, subject.getId(), grade.getAbbreviatedName(), curRoster.getCurrentSchoolYear(), new Long(criteriaNumber));
								blueprintCriteriaCompletionMap.put(criteriaNumber, completedCriteria);
							}
						}
						mav.addObject("blueprintCriteriaCompletionMap", blueprintCriteriaCompletionMap);
					}else {
						//log message
					}
					
				}else {
					//log info
				}
			}else {
				//log warning message
			}
		}else {
			//log warning unauthorized access
		}
		
		return mav;
	}
	
	private void setRecommendedLevel(Student student, OperationalTestWindowDTO otw, ContentArea ca, IAPContentFramework iapcf,
			Map<Long, ComplexityBandDao> complexityBandCache) {
		String contentAreaAbbreviatedName = ca.getAbbreviatedName();
		// if this is the first window, or the student hasn't been tested on the EE, use the student's FCS info.
		Long bandId = null;
		switch (ca.getAbbreviatedName()) {
			case "M":
				bandId = student.getFinalMathBandId();
				break;
			case "ELA":
				// TODO need a way to figure out if the EE is writing.
				bandId = student.getFinalElaBandId();
				break;
			case "Sci":
				bandId = student.getFinalSciBandId();
				break;
		}
		
		if(bandId != null){
			// don't be fooled, this "dao" object is not a typical DAO. It's a POJO, but I don't have time to correct it right now
			ComplexityBandDao recommendedLevel = null;				
			String recommendedLevelCode = null;
			
			// this caching is kind of dirty, since it comes from outside the method, but it works
			if (complexityBandCache != null && complexityBandCache.containsKey(bandId)) {
				recommendedLevel = complexityBandCache.get(bandId);			
			} else {
				recommendedLevel = testCollectionService.getComplexityBandById(bandId);	
				complexityBandCache.put(bandId, recommendedLevel);
			}
			
			recommendedLevelCode = recommendedLevel.getBandCode();
			if(ComplexityBandEnum.getByBandCode(recommendedLevelCode, contentAreaAbbreviatedName) != null) {
				String recommendedLevelName = ComplexityBandEnum.getByBandCode(recommendedLevelCode, contentAreaAbbreviatedName).getLinkageLevel();
				for (MicroMapResponseDTO mmrDto : iapcf.getLinkageLevels()) {
					if (StringUtils.equals(mmrDto.getLinkagelabel(), recommendedLevelName)) {
						iapcf.getEE().setRecommendedLinkageLevelName(mmrDto.getLinkagelabel());
						break;
					}
				}
			}
		}
	}
	
	private static String replaceSpecialCharacters(String str) {
		if(StringUtils.isNotEmpty(str)) {
			for (String key : LEVEL_DESCRIPTION_REPLACEMENTS.keySet()) {
				str = StringUtils.replace(str, key, LEVEL_DESCRIPTION_REPLACEMENTS.get(key));
			}
		}
		return str;
	}
	
	@RequestMapping(value="instruction-planner-popover-content.htm")
	public final ModelAndView instructionPlannerPopover(
			@RequestParam("studentId") Long studentId,
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("enrollmentsRostersId") Long enrollmentsRostersId,
			@RequestParam("otwId") Long otwId,
			@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeLevel") String gradeLevel,
			@RequestParam("contentFrameworkDetailId") Long contentFrameworkDetailId,
			@RequestParam("contentCode") String contentCode,
			@RequestParam("linkageLevelName") String linkageLevelName,
			@RequestParam("isWriting") Boolean isWriting) throws Exception {
		ModelAndView mav = new ModelAndView("instructionAndAssessmentPlannerPopover");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String poolType = userDetails.getUser().getContractingOrganization().getPoolType();
			ContentFrameworkDetail cfd = cfService.getContentFrameworkDetailById(contentFrameworkDetailId);
			if (cfd != null && StringUtils.equals(cfd.getContentCode(), contentCode)) {
				mav.addObject("ee", cfd);
				mav.addObject("linkageLevelName", linkageLevelName);
				mav.addObject("randomId", new Random().nextInt());
				mav.addObject("isWriting", isWriting);
				mav.addObject("instPDFLoc",getInstructionsPdfLink(contentCode));
				
				List<MicroMapResponseDTO> dtos = lmWebClient.getLinkageLevelInfo(cfd.getContentCode());
				String shortDesc = null;
				String longDesc = null;
				for (MicroMapResponseDTO dto : dtos) {
					if (StringUtils.equals(dto.getLinkagelabel(), linkageLevelName)) {
						shortDesc = replaceSpecialCharacters(dto.getLinkagelevelshortdesc());
						longDesc = replaceSpecialCharacters(dto.getLinkagelevellongdesc());
						break;
					}
				}
				mav.addObject("linkageLevelShortDesc", shortDesc);
				mav.addObject("linkageLevelLongDesc", longDesc);
				
				List<ItiTestSessionHistory> existingPlans = itiService.getForIAPByContentFrameworkDetailIdAndLinkageLevel(
						studentId, rosterId, enrollmentsRostersId, otwId, contentFrameworkDetailId, linkageLevelName);
				
				String popoverType = "beginInstruction";
				
				ItiTestSessionHistory latestPlan = null;
				ItiTestSessionHistory latestPlanWithTestlet = null;
				
				List<TestCollection> testCollections = itiTestSessionService.getEligibleTestCollectionsForEE(
						studentId, rosterId, contentAreaId, gradeLevel, contentFrameworkDetailId, linkageLevelName);
				
				mav.addObject("contentAvailable", CollectionUtils.isNotEmpty(testCollections));
				
				if (CollectionUtils.isNotEmpty(existingPlans)) {
					Category started = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_STARTED, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
					//Category completedNoTestlet = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_COMPLETED_WITH_NO_TESTLET, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
					Category completedWithTestlet = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_COMPLETED_WITH_TESTLET, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
					//Category canceled = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_CANCELED, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
					
					List<ItiTestSessionHistory> completedPlans = new ArrayList<ItiTestSessionHistory>(existingPlans.size());
					for (ItiTestSessionHistory existingPlan : existingPlans) {
						if (completedWithTestlet.getId().equals(existingPlan.getStatus()) && "complete".equals(existingPlan.getStudentsTestsStatus())) {
							completedPlans.add(existingPlan);
						}
					}
					mav.addObject("existingPlans", existingPlans);
					mav.addObject("completedPlans", completedPlans);
					
					latestPlan = existingPlans.get(existingPlans.size() - 1);
					
					//braille Resource for the student
					StudentTestResourceInfo brailleResourceLocation = getBrailleResourceLocation(latestPlan);
					if(brailleResourceLocation != null) {
						mav.addObject("brailleResource", brailleResourceLocation);
					}
					
					for (int x = existingPlans.size() - 1; x >= 0; x--) {
						ItiTestSessionHistory existingPlan = existingPlans.get(x);
						if (existingPlan.getTestSessionId() != null) {
							latestPlanWithTestlet = existingPlan;
							break;
						}
					}
					
					//resource pdf
					////don't display TIP resource file if the testlet is in complete status
					if(latestPlanWithTestlet!= null && latestPlanWithTestlet.getStudentsTestsId()!=null && !"complete".equals(latestPlanWithTestlet.getStudentsTestsStatus())) {
							List<ItiTestSessionResourceInfo> resourceLocs = getResourceLocations(latestPlanWithTestlet); 
							if(!resourceLocs.isEmpty()) {
								mav.addObject("resourcePDFs", resourceLocs);
							}
					}
					
					
					if (started.getId().equals(latestPlan.getStatus())) {
						popoverType = "finishInstruction";
						mav.addObject("itiId", latestPlan.getId());
					} else if (completedWithTestlet.getId().equals(latestPlan.getStatus()) && !StringUtils.equals(latestPlan.getStudentsTestsStatus(), "complete")) {
						popoverType = "cancelTestlet";
						mav.addObject("itiId", latestPlan.getId());
					}
					
					// user can apply SC codes
					if (permissionUtil.hasPermission(userDetails.getAuthorities(), "HIGH_STAKES_SPL_CIRCUM_CODE")) {
						if (latestPlanWithTestlet != null) {
							List<SpecialCircumstance> scCodes = studentSCService.getSCEntriesByStateAndAssessmentProgram(
									userDetails.getUser().getContractingOrganization().getId(), userDetails.getUser().getCurrentAssessmentProgramId());
							
							mav.addObject("scCodes", scCodes);
							
							List<SpecialCircumstance> scCodesAlreadyApplied = studentSCService.getAppliedSCCodesByStudentsTestsId(latestPlanWithTestlet.getStudentsTestsId());
							mav.addObject("lastSCCodeApplied", CollectionUtils.isNotEmpty(scCodesAlreadyApplied) ? scCodesAlreadyApplied.get(scCodesAlreadyApplied.size() - 1) : null);
							
							// user can set approval of SC codes
							if (permissionUtil.hasPermission(userDetails.getAuthorities(), "HIGH_STAKES_SPL_CIRCUM_CODE_SEL")) {
								if (CollectionUtils.isNotEmpty(scCodesAlreadyApplied)) {
									List<Category> scCodeStatuses = categoryService.selectByCategoryType("SPECIAL CIRCUMSTANCE STATUS");
									List<String> codesWanted = Arrays.asList("APPROVED", "NOT_APPROVED", "PENDING_FURTHER_REVIEW");
									
									for (int x = 0; x < scCodeStatuses.size(); x++) {
										Category scCodeStatus = scCodeStatuses.get(x);
										if (!codesWanted.contains(scCodeStatus.getCategoryCode())) {
											scCodeStatuses.remove(x);
											x--;
										}
									}
									
									mav.addObject("scCodeStatuses", scCodeStatuses);
								}
							}
						}
					}
				}
				
				mav.addObject("latestPlan", latestPlan);
				mav.addObject("latestPlanWithTestlet", latestPlanWithTestlet);
				mav.addObject("popoverType", popoverType);
			} else {
				mav.addObject("error", "Content codes did not match, was data manipulated?");
			}
		} else {
			mav.addObject("error", "unauthorized");
		}
		return mav;
	}
	
	private String getInstructionsPdfLink(String essentialElement) {
		String result = StringUtils.EMPTY;
		if(essentialElement!=null) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
			User user= userDetails.getUser();
		 	Integer currentSchoolYear= user.getContractingOrganization().getCurrentSchoolYear().intValue();
			String instructionsPdfFileName = REPORT_PATH + java.io.File.separator +  "itiinstructions"  + java.io.File.separator + currentSchoolYear 
					+ java.io.File.separator + essentialElement + "_Instructions.pdf";
			if(s3.doesObjectExist(instructionsPdfFileName)){
				result = currentSchoolYear + "/"  + essentialElement + "_Instructions";
			} else {
				LOGGER.error("File not found in s3 with essential element " + essentialElement +". Filepath: "+instructionsPdfFileName);
			}
		}else {
			LOGGER.info("contentFrameworkDetailId can't be empty");
		}
		return result;
	}
	
	private List<ItiTestSessionResourceInfo> getResourceLocations(
			ItiTestSessionHistory latestITITestSessionHistory) {
		List<ItiTestSessionResourceInfo> resourceLocations = new ArrayList<ItiTestSessionResourceInfo>();
		String resourceLoc = StringUtils.EMPTY;
		if(latestITITestSessionHistory != null && latestITITestSessionHistory.getTestSessionId()!=null) {
			List<ItiTestSessionResourceInfo> testResourceInfo = itiTestSessionService.getTestResourceByTestSessionHistoryId(latestITITestSessionHistory);
			if(testResourceInfo != null) {
				for(ItiTestSessionResourceInfo resourceInfo : testResourceInfo){
					if(resourceInfo.getFileType().equalsIgnoreCase("pdf")){
		 				resourceLoc = MEDIA_PATH + resourceInfo.getFileLocation().replaceFirst("/", "");
		 				resourceInfo.setFileLocation(resourceLoc);
		 				resourceLocations.add(resourceInfo);
		 			}
				}				
			}
		}
		return resourceLocations;
	}

	private StudentTestResourceInfo getBrailleResourceLocation(ItiTestSessionHistory latestITITestSessionHistory) {
		StudentTestResourceInfo studentTestResultInfo = null;
		String resourceLoc = StringUtils.EMPTY;
		if (latestITITestSessionHistory != null && latestITITestSessionHistory.getTestSessionId() != null) {
			List<StudentTestResourceInfo> testResourceInfo = itiTestSessionService.getBrailleResourceByTestSessionHistoryId(latestITITestSessionHistory);
			if (testResourceInfo != null) {
				for (StudentTestResourceInfo resourceInfo : testResourceInfo) {
					if (resourceInfo.getFileType().equalsIgnoreCase("ebae") || resourceInfo.getFileType().equalsIgnoreCase("ueb")) {
						resourceLoc = MEDIA_PATH + resourceInfo.getFileLocation().replaceFirst("/", "");
						resourceInfo.setFileLocation(resourceLoc);
						studentTestResultInfo = resourceInfo;
						break;
					}
				}
			}
		}
		return studentTestResultInfo;
	}
	
	@RequestMapping(value="instruction-planner-search.htm", method = RequestMethod.GET)
	public final ModelAndView instructionPlannerSearch(@RequestParam(value = "stateStudentID", required = false) String stateStudentID,
			@RequestParam(value = "grades[]", required = false) Long[] grades,
			@RequestParam(value = "studentIDs[]", required = false) Long[] studentIDs,
			@RequestParam(value = "teacherIDs[]", required = false) Long[] pEducatorIDs,
			@RequestParam(value = "schoolID", required = false) Long schoolID,
			@RequestParam(value = "offSet", required = false) Long offSet,
			@RequestParam(value = "operationalTestId", required = false) Long operationalTestId,
			@RequestParam(value = "contentAreaId", required = false) Long contentAreaId) throws IOException {
		
		Long[] educatorIDs = pEducatorIDs;
		List<Long> orgs = new ArrayList<>();
		Long stateID=null;
		String testingCycleName = "";
		
		Map<String,IAPHomeStudentSearchDTO> studentRecords = new HashMap<String,IAPHomeStudentSearchDTO>();
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//boolean studentPermission =  permissionUtil.hasPermission(userDetails.getAuthorities(),"VIEW_ALTERNATE_STUDENT_REPORT");
		
		ModelAndView mav = new ModelAndView("instructionAndAssessmentStudentSearch");
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			Long schoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
			Long assessmentProgramID= userDetails.getUser().getCurrentAssessmentProgramId();
			Boolean isTeacher= userDetails.getUser().isTeacher();

			if(stateStudentID!=null && !stateStudentID.isEmpty()) {
				//code here for state student identifier
				stateID=userDetails.getUser().getContractingOrganization().getId();				
				Map <String, Object> ssidValidationDetails=studentService.validateStateStudentId(stateStudentID);
				
				if(ssidValidationDetails.get("Action").toString().equals("Edit")){
					schoolID=null;
					educatorIDs=null;
					studentIDs=null;
					grades=null;
				}
				else{									
					mav.addObject("errorMessage",ssidValidationDetails.get("errorMessage"));
					return mav;
				}
				
			}else if(isTeacher) {
				educatorIDs = new Long [1];
				educatorIDs[0]=userDetails.getUser().getId();
				orgs.add(userDetails.getUser().getCurrentOrganizationId());
			}
			else {	
				stateStudentID=null;
				orgs.add(schoolID);
			}
			
			if(contentAreaId==null && operationalTestId == null) {
				//get current open operational test window
				OperationalTestWindowDTO otw = otwService.getOpenInstructionAssessmentPlannerWindow(assessmentProgramID, userDetails.getUser().getContractingOrganization().getId(), null, null);
				operationalTestId= otw.getOtwId();
			}else if(operationalTestId==null){
				//if content area id is passed as parameter
				OperationalTestWindowDTO otw = otwService.getOpenInstructionAssessmentPlannerWindow(assessmentProgramID, userDetails.getUser().getContractingOrganization().getId(), contentAreaId, operationalTestId);
				operationalTestId= otw.getOtwId();
			}
			
			if(operationalTestId != null) {
				//get the respective latest testing cycle
				TestingCycle testCycleWindow = testingCycleService.getTestingCycleByAssessmentProgramIDSchoolYearOTWID(assessmentProgramID,schoolYear,operationalTestId);
				testingCycleName = testCycleWindow == null ? "" : testCycleWindow.getTestingCycleName();
			}
			
			//Reading the pagination limit from the database
			Long paginationLimit = Long.parseLong(appConfigurationService.getByAttributeCode(CommonConstants.IPA_Search_PaginationLimit));
			
			List<IAPStudentTestStatusDTO> studentDLMTestResult = studentService.iapHomeStudentsTestStatusRecords( schoolYear,
					stateID, schoolID, educatorIDs, studentIDs, stateStudentID, grades, offSet, paginationLimit,contentAreaId,operationalTestId);
			
			Long totalRecordCount = -1L;
			int pageCount=0;
			
			
			for(IAPStudentTestStatusDTO curRecord : studentDLMTestResult) {
				IAPHomeStudentSearchDTO studentRecord=null;
				String recordKey= curRecord.getGradeLevel()+"_"+curRecord.getStudentId().toString()+"_"+curRecord.getTeacherID().toString();
				if(studentRecords.containsKey(recordKey)) {
					studentRecord=(IAPHomeStudentSearchDTO) studentRecords.get(recordKey);
				}else {
					Student curStudent = studentService.getByStudentIDWithFirstContact(curRecord.getStudentId());
					studentRecord = new IAPHomeStudentSearchDTO();
					studentRecord.setStudent(curStudent);
					studentRecord.setGrade(curRecord.getGrade());
					studentRecord.setGradeAbbreviatedName(curRecord.getGradeLevel());
					studentRecord.setStudentName(curStudent.getLegalLastName()+", "+curStudent.getLegalFirstName()+ " "+ curStudent.getMiddleNameInitial());
					studentRecord.setStudentUserName(curStudent.getUsername());
					studentRecord.setStudentPassword(curStudent.getPassword());
					studentRecord.setStudentStateIdentifier(curStudent.getStateStudentIdentifier());
					studentRecord.setTeacherName(curRecord.getTeacherLastName()+", "+curRecord.getTeacherFirstName());
					studentRecord.setRecordKey(recordKey);
					if(totalRecordCount<0) {
						totalRecordCount=curRecord.getRecordCount();
					}
				}
				studentRecord.addSubjectRecordFor(curRecord);
				studentRecords.put(recordKey, studentRecord);
			}
			if(studentRecords.isEmpty()){
				mav.addObject("errorMessage", "No record found");
			}else {
				
				List<IAPHomeStudentSearchDTO> listOfStudentSearchValues = studentRecords.values().stream().sorted().collect(Collectors.toList());
				Collections.sort(listOfStudentSearchValues);
				mav.addObject("resultStudent", listOfStudentSearchValues);
				//For pagination
				mav.addObject("offSet", offSet);
				if(offSet>0) {
					mav.addObject("displayBack", true);
				}
				if(paginationLimit*(offSet+1)<totalRecordCount) {
					mav.addObject("displayNext", true);
				}
				pageCount = (int) (totalRecordCount.intValue()/paginationLimit + ((totalRecordCount.intValue()%paginationLimit) == 0 ? 0:1));
				mav.addObject("pageCount", pageCount);
				//Booolean for IE states. The Organization object in User doen't have the IE state information
				mav.addObject("isIEModelState", organizationService.isIEModelState(userDetails.getUser().getContractingOrganization()));
				
				mav.addObject("testingCycleName", testingCycleName.toUpperCase());
			}
		}else {
			//log warning unauthorized access
			mav.addObject("errorMessage", "Access Denied");
		}
		
		
		return mav;
	}
	
	@RequestMapping(value = "getGradesBySchoolID.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getGradesBySchoolID(@RequestParam(value = "schoolID", required = true) Long schoolID){

		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			Long schoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
			Long assesmentProgramID = userDetails.getUser().getCurrentAssessmentProgramId();
			Long teacherID =null;
			if(userDetails.getUser().isTeacher()) {
				teacherID = userDetails.getUserId();
			}
			List<GradeCourse> result = gradeCourseService.getGradeBandsBySchoolIDAndAssesmentProgrammIDAndYear(schoolID, assesmentProgramID, schoolYear, teacherID);
			hashMap.put("grades", result);
		}else {
			hashMap.put("error", true);
		}
		return hashMap;
	}
	
	@RequestMapping(value = "getStudentBySchoolIDandGradeIDandSchoolyear.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getStudentBySchoolIDandGradeIDandSchoolyear(@RequestParam(value = "schoolID", required = true) Long schoolID, 
			@RequestParam(value = "grades[]", required = true) Long[] grades){
		
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			Long schoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
			Long assesmentProgramID = userDetails.getUser().getCurrentAssessmentProgramId();
			Long teacherID =null;
			if(userDetails.getUser().isTeacher()) {
				teacherID = userDetails.getUserId();
			}
			List<Student> result = studentService.getStudentBySchoolIDandGradeIDandSchoolyear(schoolID, schoolYear, grades, assesmentProgramID, teacherID);
			hashMap.put("studentList", result);
		}else {
			hashMap.put("error", true);
		}
		
		return hashMap;
		
	}
	
	@RequestMapping(value = "getTeacherByStudentIDandSchoolIDandGradeID.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> getTeacherByStudentIDandSchoolIDandGradeID(@RequestParam(value = "schoolID", required = true) Long schoolID, 
			@RequestParam(value = "grades[]", required = true) Long[] grades, @RequestParam(value = "studentIDs[]", required = true) Long[] studentIDs){
 
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();	
		Map<String, Object> hashMap = new HashMap<String, Object>();
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			Long schoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
			Long assesmentProgramID = userDetails.getUser().getCurrentAssessmentProgramId();
			List<User> result = studentService.getTeacherByStudentIDandSchoolIDandGradeID(schoolID, schoolYear, grades, assesmentProgramID,studentIDs);
			hashMap.put("teacherList", result);
		}else {
			hashMap.put("error", true);
		}
		
		return hashMap;
	}
	
	@RequestMapping(value = "getSensitiveThemes.htm", method = RequestMethod.GET)
	public final ModelAndView getSensitiveThemes(@RequestParam("studentId") Long studentId, @RequestParam("contentAreaId") Long contentAreaId) {
		ModelAndView mav = null;
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			mav = new ModelAndView("instructionAndAssessmentPlannerPopoverThemes");
			List<SensitivityTag> tags = sensitivityTagService.selectAllByStudentIdAndContentAreaId(studentId, contentAreaId);
			mav.addObject("sensitivityTags", tags);
		}
		return mav;
	}
	
	@RequestMapping(value = "saveSensitiveThemes.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> saveSensitiveThemes(@RequestParam("studentId") Long studentId, @RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam(value = "tagIds[]", required = false) Long[] tagIds) {
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<Long> selectedTagIds = tagIds == null ? new ArrayList<Long>() : Arrays.asList(tagIds);
			sensitivityTagService.updateStudentTags(studentId, contentAreaId, selectedTagIds, userDetails.getUser().getId());
		}
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("status", "success");
		return ret;
	}
	
	@RequestMapping(value = "createPlan.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> createPlan(
			@RequestParam("studentId") Long studentId,
			@RequestParam("enrollmentsRostersId") Long enrollmentsRostersId,
			@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeLevel") String gradeLevel,
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("essentialElementId") Long contentFrameworkDetailId,
			@RequestParam(value = "linkageLevel", required = false) String linkageLevel,
			@RequestParam(value = "levelDesc", required = false) String levelDesc) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("success", false);
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long stateId = userDetails.getUser().getContractingOrganization().getId();
			Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
			ContentArea ca = contentAreaService.selectByPrimaryKey(contentAreaId);
			OperationalTestWindowDTO otw = otwService.getOpenInstructionAssessmentPlannerWindow(assessmentProgramId, stateId, ca.getId(), null);
			String poolType = userDetails.getUser().getContractingOrganization().getPoolType();
			if (otw != null) {
				List<IAPContentFramework> eeList = otwService.getIAPContentFrameworkForWindow(otw.getOtwId(), ca.getAbbreviatedName(), gradeLevel);
				
				IAPContentFramework ee = null;
				for (IAPContentFramework iapEE : eeList) {
					if (iapEE.getEE().getId().equals(contentFrameworkDetailId)) {
						ee = iapEE;
						break;
					}
				}
				
				if (ee != null) {
					List<MicroMapResponseDTO> dtos = lmWebClient.getLinkageLevelInfo(ee.getEE().getContentCode());
					MicroMapResponseDTO dtoToUse = null;
					for (MicroMapResponseDTO dto : dtos) {
						if (StringUtils.equals(linkageLevel, dto.getLinkagelabel())) {
							dtoToUse = dto;
							break;
						}
					}
					if (dtoToUse != null) {
						itiTestSessionService.insertITISelective(studentId, enrollmentsRostersId, rosterId, ee, linkageLevel,
								dtoToUse.getLinkagelevellongdesc(), otw.getOtwId());
						ret.put("success", true);
					} else {
						String message = "Did not find linkage level \"" + linkageLevel +
								"\" for contentframeworkdetail " + contentFrameworkDetailId + " (" + ee.getEE().getContentCode() + ")";
						LOGGER.error(message);
						ret.put("message", message);
					}
				} else {
					String message = "Did not find contentframeworkdetailid " + contentFrameworkDetailId + " in the framework for the window.";
					LOGGER.error(message);
					ret.put("message", message);
				}
			}
		}
		return ret;
	}
	
	@RequestMapping(value = "finishPlan.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> finishPlan(
			@RequestParam("studentId") Long studentId,
			@RequestParam("enrollmentsRostersId") Long enrollmentsRostersId,
			@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("gradeLevel") String gradeLevel,
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("itiId") Long itiId,
			@RequestParam("assignTestlet") boolean assignTestlet) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("success", false);
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Long stateId = userDetails.getUser().getContractingOrganization().getId();
			Long assessmentProgramId = userDetails.getUser().getCurrentAssessmentProgramId();
			ContentArea ca = contentAreaService.selectByPrimaryKey(contentAreaId);
			OperationalTestWindowDTO otw = otwService.getOpenInstructionAssessmentPlannerWindow(assessmentProgramId, stateId, ca.getId(), null);
			if (otw != null) {
				itiTestSessionService.finishPlan(studentId, contentAreaId, gradeLevel, rosterId, itiId, assignTestlet);
				ret.put("success",  true);
			}
		}
		return ret;
	}
	
	@RequestMapping(value = "cancelTestlet.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> cancelTestlet(
			@RequestParam("studentId") Long studentId,
			@RequestParam("itiId") Long itiId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("success", false);
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			ItiTestSessionHistory iti = itiTestSessionService.selectByPrimaryKey(itiId);
			if (studentId != null && studentId.equals(iti.getStudentId())) {
				int updated = itiTestSessionService.endTestletForITI(iti);
				boolean success = updated > 0;
				ret.put("success", success);
				ret.put("message", success ? "" : "No records updated.");
			} else {
				ret.put("success", false);
				ret.put("message", "Student ID did not match request");
			}
		} else {
			ret.put("message", "Not authenticated.");
		}
		return ret;
	}
	
	@RequestMapping(value = "applySCCodeToPlan.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> applySCCodeToPlan(
			@RequestParam("studentId") Long studentId,
			@RequestParam("itiId") Long itiId,
			@RequestParam("studentsTestsId") Long studentsTestsId,
			@RequestParam("scId") Long scId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("success", false);
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<SpecialCircumstance> scCodes = studentSCService.getSCEntriesByStateAndAssessmentProgram(
					userDetails.getUser().getContractingOrganization().getId(), userDetails.getUser().getCurrentAssessmentProgramId());
			
			SpecialCircumstance scToApply = null;
			
			for (SpecialCircumstance sc : scCodes) {
				if (sc.getId().equals(scId)) {
					scToApply = sc;
					break;
				}
			}
			
			if (scToApply != null) {
				Category status = categoryService.selectByCategoryCodeAndType("SAVED", "SPECIAL CIRCUMSTANCE STATUS");
				if (Boolean.TRUE.equals(scToApply.getRequireConfirmation())) {
					status = categoryService.selectByCategoryCodeAndType("PENDING_FURTHER_REVIEW", "SPECIAL CIRCUMSTANCE STATUS");
				}
				
				StudentSpecialCircumstance ssc = new StudentSpecialCircumstance();
				ssc.setStudentTestid(studentsTestsId);
				ssc.setSpecialCircumstanceId(scToApply.getId());
				ssc.setStatus(status.getId());
				ret.put("success", studentSCService.insert(ssc) > 0);
			} else {
				ret.put("message", "");
			}
		}
		return ret;
	}
	
	@RequestMapping(value = "approveSCCodeForPlan.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, Object> approveSCCodeForPlan(@RequestParam("studentId") Long studentId,
			@RequestParam("sscId") Long sscId,
			@RequestParam("statusId") Long statusId) {
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("success", false);
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			//Category savedStatus = categoryService.selectByCategoryCodeAndType("SAVED", "SPECIAL CIRCUMSTANCE STATUS");
			Category approvedStatus = categoryService.selectByCategoryCodeAndType("APPROVED", "SPECIAL CIRCUMSTANCE STATUS");
			Category notApprovedStatus = categoryService.selectByCategoryCodeAndType("NOT_APPROVED", "SPECIAL CIRCUMSTANCE STATUS");
			Category pendingStatus = categoryService.selectByCategoryCodeAndType("PENDING_FURTHER_REVIEW", "SPECIAL CIRCUMSTANCE STATUS");
			
			Category status = categoryService.selectByPrimaryKey(statusId);
			StudentSpecialCircumstance ssc = studentSCService.getById(sscId);
			
			if (Arrays.asList(
					//savedStatus.getId(),
					approvedStatus.getId(),
					notApprovedStatus.getId(),
					pendingStatus.getId()).contains(status.getId())) {
				ssc.setModifiedDate(new Date());
				ssc.setModifiedUser(userDetails.getUser().getId());
				ssc.setApprovedBy(approvedStatus.getId().equals(status.getId()) ? userDetails.getUser().getId() : null);
				ssc.setStatus(status.getId());
				
				ret.put("success", studentSCService.updateStatusAndApprovedBy(ssc) > 0);
			} else {
				ret.put("message", "invalid status");
			}
		}
		return ret;
	}
	
	@RequestMapping(value = "printIAP.htm")
	public final void printIAP(
			@RequestParam("studentId") Long studentId,
			@RequestParam("rosterId") Long rosterId,
			@RequestParam("enrollmentRosterId") Long enrollmentRosterId,
			@RequestParam(value = "operationalTestWindowId", required = false) Long operationalTestWindowId,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		if (SecurityContextHolder.getContext().getAuthentication() != null &&
				SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			Long stateId = user.getContractingOrganization().getId();
			Long assessmentProgramId = user.getCurrentAssessmentProgramId();
			Long schoolYear = user.getContractingOrganization().getCurrentSchoolYear();
			if(studentId!=null) {
				Roster curRoster = rosterService.selectByPrimaryKey(rosterId);
				ModelAndView iapSearchMav=null;
				if(curRoster!= null) {
					//If operationalTestId is null it will take the operational TestWindow from the latest Testing Cyle
					//for Spring we need to pass the operationalTestId
					Long operationalTestId = null;
					Long[] studentIDs = new Long [1];
					studentIDs[0]=studentId;
					iapSearchMav = instructionPlannerSearch(null, null, studentIDs, null, curRoster.getAttendanceSchoolId(), (long) 0, operationalTestId, curRoster.getStateSubjectAreaId());
				}
				ModelAndView mav = instructionPlannerStudent(studentId, rosterId, enrollmentRosterId, operationalTestWindowId);
				Map<String, Object> mavData = new HashMap<String, Object>();
				if(iapSearchMav!=null) {
					mavData.putAll(iapSearchMav.getModel());
				}
				if(mav!=null) {
					mavData.putAll(mav.getModel());
				}
				IAPReportContext context = mapPageDataToPDFData(mavData);
				context.setSchoolYear(schoolYear);
				context.setIsInstructionallyEmbeddedModel((Boolean) mavData.get("isInstructionallyEmbeddedModel"));
				File file = null;
				try {
					file = iapReportGenerator.generateReport(context, request.getSession().getServletContext().getRealPath("/"));
					
					Student student = (Student) mavData.get("student");
					ContentArea subject = (ContentArea) mavData.get("subject");
					
					String fileName_studentName = student.getLegalLastName() + "-" + student.getLegalFirstName();
					if (StringUtils.isNotBlank(student.getMiddleNameInitial())) {
						fileName_studentName += "-" + student.getMiddleNameInitial();
					}
					
					String attachmentFileName = "IAP-PrintView";
					
					if (operationalTestWindowId == null) {
						TimeZone tz = organizationService.getTimeZoneForOrganization(stateId);
						if (tz == null) {
							// default to Central
							tz = TimeZone.getTimeZone("US/Central");
						}
						String dateStr = DateUtil.convertDatetoSpecificTimeZoneStringFormat(new Date(), tz.getDisplayName(), "MM-dd-yyyy");
						attachmentFileName = "Essential Element Status Report-" + fileName_studentName + "-" + subject.getAbbreviatedName() + "-" + dateStr;
					} else {
						List<TestingCycle> cycles = testingCycleService.getTestingCyclesByStateIdSchoolYearAssessmentProgram(assessmentProgramId, schoolYear, stateId);
						for (TestingCycle cycle : cycles) {
							if (operationalTestWindowId.equals(cycle.getOperationalTestWindowId())) {
								attachmentFileName = cycle.getTestingCycleName() + " Essential Element Status Report-" + fileName_studentName +
										"-" + subject.getAbbreviatedName();
								break;
							}
						}
					}
					
					response.setContentType("application/force-download");
					response.addHeader("Content-Disposition", "attachment; filename=\"" + attachmentFileName + ".pdf\"");
					
					IOUtils.copy(new FileInputStream(file), response.getOutputStream());
				} catch (IOException ioe) {
					// log some error and give a bad response
				}
				finally {
					if (file != null) {
						FileUtils.deleteQuietly(file);
					}
				}
			}		
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private IAPReportContext mapPageDataToPDFData(Map<String, Object> mavData) {
		IAPReportContext context = new IAPReportContext();
		List<CriteriaContextData> criteriaList = new ArrayList<CriteriaContextData>();
		
		String pattern = "MM/dd/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		
		String testingCylceName = (String) mavData.get("testingCycleName");
		Collection<IAPHomeStudentSearchDTO> iapStudentData = (Collection<IAPHomeStudentSearchDTO>) mavData.get("resultStudent");
		
		if(iapStudentData!=null) {
			for(IAPHomeStudentSearchDTO curStudentData : iapStudentData) {
				context.setStudentName(curStudentData.getStudentName());
				if(curStudentData.getSubjectRecord()!=null) {
					for (Map.Entry<String,IAPStudentTestStatusDTO> entry : curStudentData.getSubjectRecord().entrySet()) {
						IAPStudentTestStatusDTO studentTestStatus = entry.getValue();
						String subjectName = entry.getKey();
						String essentialElementComplete = StringUtils.equalsIgnoreCase("SCI", subjectName) ? "NA" : Integer.toString(studentTestStatus.getNumofEEsCompleted()) + " of " + Integer.toString(studentTestStatus.getNumofEEs());
						String planInProgress = Integer.toString(studentTestStatus.getInstTestsInProgress());
						String assignedTest = Integer.toString(studentTestStatus.getInstTestsNotStarted());
						String testletCompleted = Integer.toString(studentTestStatus.getInstTestscompleted());

						context.setEssentialElementComplete(essentialElementComplete);
						context.setSubjectAbbreviatedName(subjectName);
						context.setPlanInProgress(planInProgress);
						context.setAssignedTeslets(assignedTest);
						context.setTesletCompleted(testletCompleted);
						break;
					}
				}

				context.setStudentStateIdentifier(curStudentData.getStudentStateIdentifier());
				context.setReportDate(date);
				context.setWindowName(testingCylceName);
				context.setStudentUserName(curStudentData.getStudentUserName());
				context.setStudentPassword(curStudentData.getStudentPassword());
			}
		}
		context.setSchoolName((String) mavData.get("schoolName"));
		context.setDistrictName((String) mavData.get("districtName"));
		
		List<IAPContentFramework> eeList = (List<IAPContentFramework>) mavData.get("EEList");
		Map<Integer, Boolean> blueprintCriteriaCompletionMap = (Map<Integer, Boolean>) mavData.get("blueprintCriteriaCompletionMap");
		
		context.setAllLinkageLevels((List<LinkageLevelSortOrder>) mavData.get("linkageLevels"));
		context.setContentArea((ContentArea) mavData.get("subject")); 
		
		if (CollectionUtils.isNotEmpty(eeList)) {
			Category completedNoTestlet = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_COMPLETED_WITH_NO_TESTLET, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
			Category canceled = categoryService.selectByCategoryCodeAndType(CommonConstants.IAP_STATUS_CANCELED, CommonConstants.IAP_STATUS_CATEGORYTYPE_CODE);
			
			List<Long> statusesToRemove = Arrays.asList(completedNoTestlet.getId(), canceled.getId());
			
			CriteriaContextData currentCriteria = null;
			ClaimContextData currentClaim = null;
			ConceptualAreaContextData currentConceptualArea = null;
			
			for (IAPContentFramework iap : eeList) {
				Integer iapCriteria = iap.getCriteria();
				//if (iap.getCriteria() != null) {
					boolean higherMismatch = false;
					
					if (currentCriteria == null ||
							(iapCriteria != null && currentCriteria.getCriteria().intValue() != iapCriteria.intValue())) {
						higherMismatch = true;
						
						CriteriaContextData ctx = new CriteriaContextData();
						ctx.setCriteria(iapCriteria);
						ctx.setCriteriaText(iapCriteria == null ?
								"The below additional Essential Elements are available for instruction and assessment for your students." : iap.getCriteriaText());
						ctx.setMetCriteria(blueprintCriteriaCompletionMap.get(iapCriteria));
						currentCriteria = ctx;
						
						if (!criteriaList.contains(currentCriteria)) {
							criteriaList.add(currentCriteria);
						}
					}
					
					if (higherMismatch || currentClaim == null || currentClaim.getClaimId().longValue() != iap.getClaimId().longValue()) {
						higherMismatch = true;
						
						ClaimContextData claim = new ClaimContextData();
						claim.setClaimId(iap.getClaimId());
						claim.setClaimContentCode(iap.getClaimContentCode());
						claim.setClaimDescription(iap.getClaimDescription());
						currentClaim = claim;
						
						if (!currentCriteria.getClaims().contains(currentClaim)) {
							currentCriteria.addClaim(currentClaim);
						}
					}
					
					if (higherMismatch || currentConceptualArea == null || currentConceptualArea.getConceptualAreaId().longValue() != iap.getConceptualAreaId().longValue()) {
						higherMismatch = true;
						
						ConceptualAreaContextData conceptualArea = new ConceptualAreaContextData();
						conceptualArea.setConceptualAreaId(iap.getConceptualAreaId());
						conceptualArea.setConceptualAreaContentCode(iap.getConceptualAreaContentCode());
						conceptualArea.setConceptualAreaDescription(iap.getConceptualAreaDescription());
						currentConceptualArea = conceptualArea;
						
						if (!currentClaim.getConceptualAreas().contains(currentConceptualArea)) {
							currentClaim.addConceptualArea(currentConceptualArea);
						}
					}
					
					if (CollectionUtils.isNotEmpty(iap.getItiEntries())) {
						for (int x = 0; x < iap.getItiEntries().size(); x++) {
							ItiTestSessionHistory iti = iap.getItiEntries().get(x);
							// we don't care about these entries for the PDF,
							// so just remove them so we don't have to worry about them
							if (statusesToRemove.contains(iti.getStatus())) {
								iap.getItiEntries().remove(x--);
							}
						}
					}
					
					if (currentConceptualArea != null) {
						currentConceptualArea.addEE(iap);
					}
				//}
			}
		}
		
		context.setCriteria(criteriaList);
		
		return context;
	}
}
