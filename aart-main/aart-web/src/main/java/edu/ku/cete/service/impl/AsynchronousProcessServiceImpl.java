package edu.ku.cete.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.OrganizationSnapshotMapper;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EmailService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.AsynchronousProcessService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.exception.DuplicateTestSessionNameException;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.SourceTypeEnum;

@Service
public class AsynchronousProcessServiceImpl implements AsynchronousProcessService {

	private final Logger logger = LoggerFactory
			.getLogger(OrganizationServiceImpl.class);

	@Autowired
	private OrganizationDao organizationDao;

	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private UserService userService;

	@Autowired
	private RosterService rosterService;

	@Autowired
	private OrganizationSnapshotMapper organziaSnapshotMapper;

	@Autowired
	private StudentService studentService;

	@Autowired
	DomainAuditHistoryMapper auditHistoryMapper;
	
	@Autowired
	private StudentsTestsService studentsTestsService;
	
	@Autowired
	private EmailService emailService;

    @Autowired
    private CategoryService categoryService;
    
    /**
     * TEST_SESSION_STATUS_UNUSED
     */
    @Value("${testsession.status.unused}")
    private String TEST_SESSION_STATUS_UNUSED;
    
    /**
     * TEST_SESSION_STATUS_TYPE
     */
    @Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;
    
	@Async
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void manageOrganization(Organization organization, User user) {
		logger.info("Enters into manageOrganization for : "	+ organization.getOrganizationName());
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		try {
			Organization before = organizationDao.getJsonOrganization(organization.getId());
			// Update organization table
			organizationDao.updateOrganization(organization);

			// update organizationtreedetail table
			organizationDao.updateOrgnameInOrgTreeDetail(organization);

			// change assessment program
			List<Long> existingAssessmentPrograms = organizationDao
					.getOrgAssessmentProgramIds(organization.getId());

			for (Long assessmentId : existingAssessmentPrograms) {
				if (!organization.getAssessmentProgramIdList().contains(assessmentId)) {
					// Remove user roles and de-link user from assessmentProgram
					updateUserOnOrgAssessmentRemove(organization.getId(), assessmentId, user);

					// Remove student OR de-link student from assessmentProgram
					updateStudentOnOrgAssessmentRemove(organization.getId(),user, assessmentId, organization.getCurrentSchoolYear());

					// remove assessment program from organization
					organizationDao.disableAssessmentProgramByOrgId(organization.getId(), null, assessmentId,userDetails.getUserId(),new Date());
				}
			}

			organization.getAssessmentProgramIdList().removeAll(existingAssessmentPrograms);

			for (Long assessmentProgramId : organization.getAssessmentProgramIdList()) {
				OrgAssessmentProgram orgAssess = new OrgAssessmentProgram();
				orgAssess.setAssessmentProgramId(assessmentProgramId);
				orgAssess.setOrganizationId(organization.getId());
				orgAssess.setAuditColumnProperties();
				orgAssess.setActiveFlag(true);
				
				int count = orgAssessProgService.updateIfExist(orgAssess);
				if(count == 0)
				 orgAssessProgService.insert(orgAssess);
			}
			
			 Organization after = organizationDao.getJsonOrganization(organization.getId());
			//Put entry in domain audit history table
			 DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
			
			 domainAuditHistory.setSource(SourceTypeEnum.MANUAL.getCode());
			 domainAuditHistory.setObjectType("ORGANIZATION");
			 domainAuditHistory.setObjectId(organization.getId());
			 domainAuditHistory.setCreatedUserId(user.getId().intValue());
			 domainAuditHistory.setCreatedDate(new Date());
			 domainAuditHistory.setAction("ORGANIZATION_MANAGE");
			 domainAuditHistory.setObjectBeforeValues(before.buildJsonString());
			 domainAuditHistory.setObjectAfterValues(after.buildJsonString());
				
			 auditHistoryMapper.insert(domainAuditHistory);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("manageOrganization service failed " + e.getMessage());
		}
		logger.info("Leaving From manageOrganization for : "
				+ organization.getOrganizationName());
	}
	
	@Async
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void resetStudentPasswordOnAnnualReset(Long orgId, int passwordLength,String[] qcStates){
		studentService.resetStudentPasswordOnAnnualReset(orgId,passwordLength,qcStates);
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateUserOnOrgAssessmentRemove(Long organizationId,
			Long assessmentProgramId, User modifiedUser) {
		List<User> affectedUsers = userService
				.getAllUsersByAssessmentProgramAndOrgId(organizationId,
						assessmentProgramId);

		// remove all roles related to user organization
		userService.removeRolesfromUsersByOrgAndAssessment(organizationId,
				assessmentProgramId, modifiedUser.getId());

		// Deactivating the user if they do not have any other organization
		for (User user : affectedUsers) {
			Long count = userService.checkUserOrganizationCount(user.getId());
			if (count == 0) {
				userService.deactivateUser(user.getId());
			}else{
				userService.resetDefaultOrganization(user.getId(), modifiedUser.getId());
			}
			 
			
			// TODO If auditing required for removed user
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updateStudentOnOrgAssessmentRemove(Long organizationId,
			User user, Long assessmentProgramId, Long currentSchoolYear) {

		// De-link students and assessment program
		studentService.removeAssessmentProgram(organizationId,
				currentSchoolYear, assessmentProgramId, user.getId());

		// Find student Enrollments without assessment program
		List<Enrollment> enrollments = enrollmentService
				.getStudentEnrollmentWithoutAssessmentPrograms(organizationId,
						currentSchoolYear);

		Set<Long> uniqueRosterIds = new HashSet<Long>();
		List<Long> rosterIds = new ArrayList<Long>();

		for (Enrollment enrollment : enrollments) {
			rosterIds.clear();
			// remove from roster
			rosterIds = rosterService.removeRostersByEnrollmentId(
					enrollment.getId(), user.getId());

			// remove enrollment
			enrollment.setActiveFlag(false);
			enrollment.setModifiedUser(user.getId());
			enrollment.setModifiedDate(new Date());
			enrollmentService.update(enrollment);

			uniqueRosterIds.addAll(rosterIds);
		}

		for (Long roster : uniqueRosterIds) {
			rosterService.deleteIfNoStudentPresent(roster, user.getId());
		}
	}

	   @Async
	   @Override
	   @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	   public void createMultipleTestSessions(List<Long> enrollmentRosterIds, String arraySelectedTestId, Long[] testIds, 
	    			String sessionName, String[] students){
			boolean successful = false;
			List<String> createdTestSessionNames=new ArrayList();
			if(testIds!=null && testIds.length>0 && arraySelectedTestId!=null && !arraySelectedTestId.equals("")){
				for(Long testid : testIds){	
					
					String testName="";
					Long testCollectionId = null;
					ObjectMapper objectMapper = new ObjectMapper();
					JsonNode root;
					try {
						root = objectMapper.readTree(arraySelectedTestId);
						ArrayNode partialResponsesArray = (ArrayNode) root.get("testIds");
						if(partialResponsesArray.size()>0){
							for (int x = 0; x < partialResponsesArray.size(); x++) {
								JsonNode partialResponse = partialResponsesArray.get(x);										
								if(partialResponse!=null && partialResponse.get(testid.toString())!=null){
									JsonNode response = partialResponse.get(testid.toString());
									if(response!=null && response.get("testName")!=null && !response.get("testName").equals("")) 
										testName = response.get("testName").asText();
									if(response!=null && response.get("testCollectionId")!=null && !response.get("testCollectionId").equals("")) 
										testCollectionId = response.get("testCollectionId").asLong();
							 }
						  }
						}
											
						
					}catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}			 	
							
							
					TestSession testSession = new TestSession();
					Category unusedSession = null;

					unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED,
					TEST_SESSION_STATUS_TYPE);
					testSession.setName(sessionName+"_"+testName);
					testSession.setTestCollectionId(testCollectionId);
					testSession.setSource(SourceTypeEnum.MANUAL.getCode());
					if (unusedSession != null) {
							testSession.setStatus(unusedSession.getId());
					}
					enrollmentRosterIds = NumericUtil.convert(students);

					try {
							if (enrollmentRosterIds != null && enrollmentRosterIds.size() > 0 && testCollectionId > 0) {				
										successful = studentsTestsService.createTestSessions(enrollmentRosterIds, testCollectionId, testid,
												testSession, null, null);					
										
								if (successful) {
									createdTestSessionNames.add("\n"+testSession.getName());
									//isValid = true;								
								}
							}					
							
						
					} catch (DuplicateTestSessionNameException e) {
						SimpleDateFormat sdf = new SimpleDateFormat("MMssSS");
				        String now = sdf.format(new Date());
						testSession.setName(sessionName+"_"+testName+"_"+now);
						if (enrollmentRosterIds != null && enrollmentRosterIds.size() > 0 && testCollectionId > 0) {				
							try {
								successful = studentsTestsService.createTestSessions(enrollmentRosterIds, testCollectionId, testid,
										testSession, null, null);
								if (successful) {
									createdTestSessionNames.add("\n"+testSession.getName());
									//isValid = true;								
								}
							} catch (DuplicateTestSessionNameException e1) {
								
							}
						 }	
					   }					
					
					}
				
					UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication()
							.getPrincipal();
					try {
						createdTestSessionNames.toString();
						emailService.sendTestSessionCreationCompletionMsg(userDetails,createdTestSessionNames);
					} catch (Exception e) {							
						e.printStackTrace();
					}
				
			}
	    	 	
	    }    
}
