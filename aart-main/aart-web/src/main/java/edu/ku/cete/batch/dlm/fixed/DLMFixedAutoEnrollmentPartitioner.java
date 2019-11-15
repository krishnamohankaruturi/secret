package edu.ku.cete.batch.dlm.fixed;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OperationalTestWindowService;
import edu.ku.cete.service.OrganizationService;

public class DLMFixedAutoEnrollmentPartitioner implements Partitioner{
	
	private final static Log logger = LogFactory .getLog(DLMFixedAutoEnrollmentPartitioner.class);

	@Autowired
	private OperationalTestWindowService otwService;
	@Autowired
    private OrganizationService orgService;
	@Autowired
    private ContentAreaService contentAreaService;
	@Autowired
	private GradeCourseService gradeCourseSerice;
	@Autowired
	private BatchRegistrationService brService;
	
	private StepExecution stepExecution;
	private Long batchRegistrationId;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	private Long operationalTestWindowId;
	private Long enrollmentMethodId;
	private String enrollmentMethod;
	private String gradeAbbrName;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter DLMFixedAutoEnrollmentPartitioner partition size : "+gridSize);
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
		
		if(!orgWindowIds.isEmpty()) {
			for(Organization contractingOrg: orgWindowIds.keySet()) {
				List<ContentArea> contentAreas = contentAreaService.getContententAreasByOtwId(orgWindowIds.get(contractingOrg));
				List<GradeCourse> courses = gradeCourseSerice.getCoursesListByOtwId(orgWindowIds.get(contractingOrg));		
				
				if(CollectionUtils.isNotEmpty(contentAreas)) {
					for(ContentArea contentArea: contentAreas) {
						List<GradeCourse> gradeCourses = null;
						if(gradeAbbrName != null) {
							GradeCourse selectedGradeCourse = gradeCourseSerice.getIndependentGradeByAbbreviatedName(gradeAbbrName);
							gradeCourses = Arrays.asList(selectedGradeCourse);
						} else {
							gradeCourses = gradeCourseSerice.selectGradeCourseByContentAreaId(contentArea.getId());
						}
						if(CollectionUtils.isNotEmpty(gradeCourses)) {
							for(GradeCourse gradeCourse : gradeCourses) {
								if(CollectionUtils.isNotEmpty(courses)) {
									for(GradeCourse course : courses) {
										ExecutionContext context = new ExecutionContext();
										context.put("contractingOrganization", contractingOrg);
										context.put("contentArea", contentArea);
										context.put("course", course);
										context.put("gradeAbbrName", gradeCourse.getAbbreviatedName());
										context.put("operationalTestWindowId", orgWindowIds.get(contractingOrg));
										partitionMap.put(contractingOrg.getId() +"_"+contentArea.getAbbreviatedName()+"_"+gradeCourse.getAbbreviatedName()+"_"+course.getAbbreviatedName(), context);
									}
								} else {
									ExecutionContext context = new ExecutionContext();
									context.put("contractingOrganization", contractingOrg);
									context.put("contentArea", contentArea);
									context.put("course", new GradeCourse());
									context.put("gradeAbbrName", gradeCourse.getAbbreviatedName());
									context.put("operationalTestWindowId", orgWindowIds.get(contractingOrg));
									partitionMap.put(contractingOrg.getId() +"_"+contentArea.getAbbreviatedName()+"_"+gradeCourse.getAbbreviatedName()+"_NOCOURSE", context);
								}
							}
						} else {
							logger.warn(enrollmentMethod+" job:no gradeCourses found. BatchRegistrationId:"+batchRegistrationId+" OTW:"+orgWindowIds.get(contractingOrg)+", AbbreviatedName: "+contentArea.getAbbreviatedName());
						}
					}
				} else {
					logger.warn(enrollmentMethod+" job: no partitions created due to no contentareas found. Check OTW details. BatchRegistrationId:"+batchRegistrationId+" OTW:"+orgWindowIds.get(contractingOrg));
				}
			}
		}else {
			logger.warn(enrollmentMethod+" job: no partitions created. Check OTW details. BatchRegistrationId:"+batchRegistrationId);
		}
		logger.debug("Created "+partitionMap.size()+" partitions.BatchRegistrationId:"+batchRegistrationId);
		return partitionMap;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
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

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public Long getEnrollmentMethodId() {
		return enrollmentMethodId;
	}

	public void setEnrollmentMethodId(Long enrollmentMethodId) {
		this.enrollmentMethodId = enrollmentMethodId;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public String getGradeAbbrName() {
		return gradeAbbrName;
	}

	public void setGradeAbbrName(String gradeAbbrName) {
		this.gradeAbbrName = gradeAbbrName;
	}	
}
