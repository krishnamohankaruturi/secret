package edu.ku.cete.batch.auto.ismart;

import java.util.ArrayList;
import java.util.Arrays;
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

import edu.ku.cete.domain.ComplexityBand;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.studentsession.StudentSessionRule;
import edu.ku.cete.domain.studentsession.TestCollectionsSessionRules;
import edu.ku.cete.model.studentsession.TestCollectionsSessionRulesDao;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.util.studentsession.StudentSessionRuleConverter;

/**
 * @author Kiran Reddy Taduru
 * Jun 1, 2018 2:38:28 PM
 */
public class ISmartAutoEnrollmentPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(ISmartAutoEnrollmentPartitioner.class);
	
	private Long operationalTestWindowId;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	private String enrollmentMethod;
	private Long enrollmentMethodId;
	private String gradeAbbrName;
	private Long batchRegistrationId;

	@Value("${testsession.status.unused}")
	private String TEST_SESSION_STATUS_UNUSED;
	
	@Value("${testsession.status.type}")
    private String TEST_SESSION_STATUS_TYPE;
	
	@Autowired
	private OperationalTestWindowService otwService;
	
    @Autowired
    private OrganizationService orgService;
    
    @Autowired
    private ContentAreaService contentAreaService;
    
	@Autowired
	private ComplexityBandService cbService;
	
	@Autowired
	private BatchRegistrationService brService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
	protected TestCollectionsSessionRulesDao testCollectionsSessionRulesDao;
	
	@Autowired
	protected StudentSessionRuleConverter studentSessionRuleConverter;
	
	private StepExecution stepExecution;
	
	@Value("${ismart.contentArea.abbreviatedName}")
    private String ISMART_CONTENTAREA_ABBRNAME;		
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchRegistrationPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);		
		
		Map<Organization, Long> orgWindowIds = new HashMap<Organization, Long>();
		
		if (enrollmentMethod.equalsIgnoreCase("FXD")) {
			if( operationalTestWindowId != null) {
				Map<Long, String> states = otwService.getOperationalTestWindowSelectedState(operationalTestWindowId);
				for(Long contractingOrgId: states.keySet()) {
					orgWindowIds.put(orgService.get(contractingOrgId), operationalTestWindowId);
				}
			} else {
				List<OperationalTestWindow> windows= brService.getEffectiveTestWindowsForBatchRegistration(assessmentProgramId, enrollmentMethodId);
				for(OperationalTestWindow otw: windows) {
					Map<Long, String> states = otwService.getOperationalTestWindowSelectedState(otw.getId());
					for(Long contractingOrgId: states.keySet()) {
						orgWindowIds.put(orgService.get(contractingOrgId), otw.getId());
					}
				}
			}
		}		
				
		Category unusedStatusCategory = categoryService.selectByCategoryCodeAndType(TEST_SESSION_STATUS_UNUSED, TEST_SESSION_STATUS_TYPE);
		
		if(!orgWindowIds.isEmpty()) {
			for(Organization contractingOrg: orgWindowIds.keySet()) {
				List<ContentArea> contentAreas = contentAreaService.selectContentAreasForISmartAutoEnrollment(contractingOrg.getId(), ISMART_CONTENTAREA_ABBRNAME);
				//contains the grades of the test collections associated with the operational test window
				List<GradeCourse> courses = gradeCourseService.getCoursesListByOtwId(orgWindowIds.get(contractingOrg));		
				
				List<TestCollectionsSessionRules> sessionsRulesList = testCollectionsSessionRulesDao.selectByOperationalTestWindowId(orgWindowIds.get(contractingOrg));
				StudentSessionRule studentSessionRule = studentSessionRuleConverter.convertToStudentSessionRule(sessionsRulesList);
				
				if(CollectionUtils.isNotEmpty(contentAreas)) {
					for(ContentArea contentArea: contentAreas) {
						//contains the grade course selected on the UI or grade courses associated with the content area
						List<GradeCourse> gradeCourses = null;
						if(gradeAbbrName != null) {
							GradeCourse selectedGradeCourse = gradeCourseService.getIndependentGradeByAbbreviatedName(gradeAbbrName);
							gradeCourses = Arrays.asList(selectedGradeCourse);
						} else {
							gradeCourses = gradeCourseService.selectGradeCourseByContentAreaId(contentArea.getId());
						}

						Map<Long, Long> gradeCoursesToGradeBand = gradeCourseService.getGradeCourseToGradeBandMap(orgWindowIds.get(contractingOrg), contentArea.getId());
						List<ComplexityBand> allBands = cbService.getAll();

						//limit the partition of the grades to those on the OTW whether selected in UI or all associated with the content area
						List<GradeCourse> gradesToProcess = new ArrayList<GradeCourse>();
						for (GradeCourse contentAreaGradeFromOTW : courses) {
							for (GradeCourse selectedGrade : gradeCourses) {
								if (contentAreaGradeFromOTW.getAbbreviatedName() != null && 
									selectedGrade.getAbbreviatedName() != null &&
									contentAreaGradeFromOTW.getAbbreviatedName().equals(selectedGrade.getAbbreviatedName())) {
									gradesToProcess.add(selectedGrade);
								}
							}
						}
						
						if(CollectionUtils.isNotEmpty(gradesToProcess)) {
							for(GradeCourse gradeCourse : gradesToProcess) {
								ExecutionContext context = new ExecutionContext();
								context.put("contractingOrganization", contractingOrg);
								context.put("contentArea", contentArea);
								context.put("gradeCoursesToGradeBand", gradeCoursesToGradeBand);
								context.put("gradeAbbrName", gradeCourse.getAbbreviatedName());
								context.put("operationalTestWindowId", orgWindowIds.get(contractingOrg));
								context.put("allBands", allBands);
								context.put("testSessionUnusedStatusId", unusedStatusCategory.getId());
								context.put("studentSessionRule", studentSessionRule);
								partitionMap.put(contractingOrg.getId() +"_"+contentArea.getAbbreviatedName()+"_"+gradeCourse.getAbbreviatedName(), context);
							}
						} else {
							StringBuffer msg = new StringBuffer();
							msg.append("No gradeCourses found. BatchRegistrationId: ");
							msg.append(batchRegistrationId);
							msg.append(" - OTW ID: ");
							msg.append(orgWindowIds.get(contractingOrg));
							msg.append(" - Organization: ");
							msg.append(contractingOrg.getDisplayIdentifier());
							msg.append("(");
							msg.append(contractingOrg.getId());
							msg.append(")");
							msg.append(" - AbbreviatedName: ");
							msg.append(contentArea.getAbbreviatedName());							
							
							logger.debug(msg.toString());
							writeReason(msg.toString());
						}
					}
				} else {
					StringBuffer msg = new StringBuffer();
					msg.append("No partitions created due to no contentareas found. Check OTW details. BatchRegistrationId: ");
					msg.append(batchRegistrationId);
					msg.append(" - OTW ID: ");
					msg.append(orgWindowIds.get(contractingOrg));
					msg.append(" - Organization: ");
					msg.append(contractingOrg.getDisplayIdentifier());
					msg.append("(");
					msg.append(contractingOrg.getId());
					msg.append(")");
					
					logger.debug(msg.toString());
					writeReason(msg.toString());
				}
			}
		}else {
			StringBuffer msg = new StringBuffer();
			msg.append("No partitions created due to Operational test windows not found. Check OTW details. BatchRegistrationId: ");
			msg.append(batchRegistrationId);
			
			logger.debug(msg.toString());
			writeReason(msg.toString());
		}

		
		logger.debug("Created "+partitionMap.size()+" partitions.");
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

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
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

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public Long getEnrollmentMethodId() {
		return enrollmentMethodId;
	}

	public void setEnrollmentMethodId(Long enrollmentMethodId) {
		this.enrollmentMethodId = enrollmentMethodId;
	}

	public String getGradeAbbrName() {
		return gradeAbbrName;
	}

	public void setGradeAbbrName(String gradeAbbrName) {
		this.gradeAbbrName = gradeAbbrName;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

}
