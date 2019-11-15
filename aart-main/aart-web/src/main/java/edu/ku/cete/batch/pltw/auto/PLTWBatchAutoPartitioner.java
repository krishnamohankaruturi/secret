/**
 * 
 */
package edu.ku.cete.batch.pltw.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.configuration.TestStatusConfiguration;
import edu.ku.cete.domain.common.AppConfiguration;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

/**
 * @author Kiran Reddy Taduru
 *
 * Aug 23, 2018 11:14:33 AM
 */
public class PLTWBatchAutoPartitioner implements Partitioner {
	private final static Log logger = LogFactory.getLog(PLTWBatchAutoPartitioner.class);	
		
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	private Long testingProgramId;	
	private Long operationalTestWindowId;
	private String enrollmentMethod;
	private Long batchRegistrationId;
	private Long contentAreaId;
	private StepExecution stepExecution;
	private String stageCode;
	
	@Autowired
    private GradeCourseService gradeCourseService;
	
	@Autowired
	private ContentAreaService caService;
    
    @Autowired
    private OrganizationService orgService;
	
	@Autowired
	private TestCollectionService tcService;	
	
	@Autowired
	protected TestStatusConfiguration testStatusConfiguration;
	
	@Autowired
	protected TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
	
	@Autowired
	protected StudentSessionRuleConverter studentSessionRuleConverter;
	
	@Autowired
	protected CategoryService categoryService;
	
	@Autowired
	private AppConfigurationService appConfigurationService;
	
	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;	
	
	@Value("${testsession.status.type}")
	private String TEST_SESSION_STATUS_TYPE;	
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Entering PLTWBatchAutoPartitioner - batchRegistrationId: " + batchRegistrationId +  " - partition size: " + gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		
		//Get all the contracting organizations from active operational test window for specified assessment program and test enrollment method
		List<Organization> contractingOrgs = orgService.getContractingOrgsByAssessmentProgramIdOTWId(assessmentProgramId, operationalTestWindowId, enrollmentMethod);
		
		Category unusedSession = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED,	TEST_SESSION_STATUS_TYPE);		
			
		//Get error messages related to student ethnicity and comprehensive race
		List<AppConfiguration> pltwBatchErrorMessages = appConfigurationService.selectByAttributeType(CommonConstants.PLTW_BATCH_AUTO_MESSAGE_TYPE);
		Map<String, AppConfiguration> pltwBatchErrorMessageMap = new HashMap<String, AppConfiguration>();
		if(CollectionUtils.isNotEmpty(pltwBatchErrorMessages)) {
			for(AppConfiguration apConfig: pltwBatchErrorMessages) {
				pltwBatchErrorMessageMap.put(apConfig.getAttributeCode(), apConfig);
			}
		}
		
		//Loop thru contracting organizations
		for(Organization contractingOrg: contractingOrgs) {
			logger.debug("batchRegistrationId - " + batchRegistrationId + " - Processing contracting organization: " + contractingOrg.getId() + " (" + contractingOrg.getDisplayIdentifier() + ")");
			//Get courses for each organization by operationaltestwindowid
			List<ContentArea> contentAreas = caService.getContententAreasByOtwId(contractingOrg.getOperationalWindowId());
			if(CollectionUtils.isEmpty(contentAreas)){
				writeReason(String.format("No Content Areas found for Organization: %d (%s), OperationalTestWindowId: %d, BatchRegistrationId: %d",
						contractingOrg.getId(), contractingOrg.getDisplayIdentifier(), contractingOrg.getOperationalWindowId(), batchRegistrationId));
			}else {
				logger.debug("batchRegistrationId - " + batchRegistrationId + " , contentAreas found - " + contentAreas.size());
				//Get gradebands associated with each contentarea
				for (ContentArea contentArea : contentAreas) {
					List<GradeCourse> gradeBands = gradeCourseService.getGradeBandsByContentAreaIdAndOTWId(contractingOrg.getOperationalWindowId(), contentArea.getId());
					if(CollectionUtils.isEmpty(gradeBands)) {
						writeReason(String.format("No Gradebands found for Organization: %d (%s), OperationalTestWindowId: %d, ContentAreaId: %d (%s), BatchRegistrationId: %d",
								contractingOrg.getId(), contractingOrg.getDisplayIdentifier(), contractingOrg.getOperationalWindowId(), contentArea.getId(), contentArea.getAbbreviatedName(), batchRegistrationId));
					}else {
						logger.debug("BatchRegistrationId - " + batchRegistrationId + " , contentAreaId - " + contentArea.getId() + ", GradeBands found - " + gradeBands.size());
						
						List<TestCollection> testCollections = null;
						//Construct a partitioned context for each organization, contentarea and gradeband
						for(GradeCourse gradeBand : gradeBands) {
							ExecutionContext context = new ExecutionContext();
							
							//TestCollections
							testCollections = new ArrayList<TestCollection>();
							
							testCollections  = tcService.getTestCollectionsForBatchAutoByGradeBandOTWId(assessmentProgramId, contentArea.getId(), gradeBand.getGradeBandId(), 
									contractingOrg.getOperationalWindowId(), CommonConstants.ENROLLMENT_RANDOMIZATION_TYPE, testStatusConfiguration.getPublishedTestStatusCategory().getId(), stageCode);
							
							if(CollectionUtils.isEmpty(testCollections)) {
								String reason = String.format("No test collections found for Organization: %d (%s), OperationalTestWindowId: %d, ContentAreaId: %d (%s), GradeBand: %d (%s)", 
										contractingOrg.getId(), contractingOrg.getDisplayIdentifier(), contractingOrg.getOperationalWindowId(), contentArea.getId(), contentArea.getAbbreviatedName(),
										gradeBand.getGradeBandId(), gradeBand.getGradeBandAbbrName());
								logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
								writeReason(reason);
							} else if(CollectionUtils.isNotEmpty(testCollections)) {								
								
								List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(contractingOrg.getOperationalWindowId());
								StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
								
								Stage stage = tcService.getStageByCode(stageCode);
								
								context.put("contractingOrganization", contractingOrg);
								context.put("contentArea", contentArea);
								context.put("gradeBand", gradeBand);
								context.put("gradeBandAbbrName", gradeBand.getGradeBandAbbrName());
								context.put("operationalTestWindowId", contractingOrg.getOperationalWindowId());
								context.put("testCollections", testCollections);
								context.put("studentSessionRule", studentSessionRule);
								context.put("unusedTestSessionId", unusedSession.getId());
								context.put("pltwBatchErrorMessageMap", pltwBatchErrorMessageMap);
								context.put("stage", stage);
								partitionMap.put(contractingOrg.getId() +"_"+contentArea.getAbbreviatedName()+"_"+gradeBand.getGradeBandAbbrName(), context);
							}							
							
						}
					}					
					
				}
			}
			
		}
		logger.debug("Exiting PLTWBatchAutoPartitioner- BatchregistrationId: "+ batchRegistrationId + " - partitionMap size: " + partitionMap.size());
		return partitionMap;
	}

	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		logger.debug(msg);
		
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
	}
	
	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public String getStageCode() {
		return stageCode;
	}

	public void setStageCode(String stageCode) {
		this.stageCode = stageCode;
	}

}
