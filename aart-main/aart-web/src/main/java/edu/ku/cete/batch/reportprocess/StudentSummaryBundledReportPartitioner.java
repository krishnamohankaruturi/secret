/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 1:59:50 PM
 */
public class StudentSummaryBundledReportPartitioner implements Partitioner {
	private final static Log logger = LogFactory .getLog(StudentSummaryBundledReportPartitioner.class);
	
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
    private OrganizationService orgService;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;
	
	@Autowired
	OrganizationBundleReportService bundleReportService;
	
	@Autowired
	CategoryDao categoryDao;
	
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Long batchReportProcessId;
	private Long organizationId;
	private StepExecution stepExecution;
	private JobExecution jobExecution;
	private String bundledReportType;
	
	@Value("${external.import.reportType.studentSummary}")
	private String REPORT_TYPE_STUDENT_SUMMARY; 
	
	@Value("${external.schoolLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL;
    
    @Value("${external.districtLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL;
    
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		
		logger.debug("Entering StudentSummaryBundledReport partition size : "+gridSize);
		
		jobExecution = stepExecution.getJobExecution();
		jobExecution.getExecutionContext().put("bundleReports",new CopyOnWriteArrayList<OrganizationBundleReport>());		
		Long inProgressStatusId = categoryDao.getCategoryId("IN_PROGRESS", "PD_REPORT_STATUS");
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		List<Organization> states = new ArrayList<Organization>();
		List<Long> orgIds = null;
		states.add(orgService.get(organizationId));
		if(CollectionUtils.isEmpty(states)) {
			writeReason("No contracting organizations found for assessmentprogram - " + assessmentProgramCode);
		} else {
			
			List<String> gradeCoursesAbbrNames = null;
			for(Organization state: states) {
				if("CPASS".equalsIgnoreCase(assessmentProgramCode) || "DLM".equalsIgnoreCase(assessmentProgramCode)){
					if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL.equalsIgnoreCase(bundledReportType)){
						
						orgIds = batchReportProcessService.getSchoolIdsForStudentSummaryBundledReports(state.getId(), assessmentProgramId, new Long(state.getReportYear()), null, REPORT_TYPE_STUDENT_SUMMARY, null, null);
						gradeCoursesAbbrNames = batchReportProcessService.geGradeCoursesAbbrNamesForStudentSummaryBundledReports(state.getId(), assessmentProgramId, state.getReportYear(), REPORT_TYPE_STUDENT_SUMMARY);
						
						jobExecution.getExecutionContext().put("successSchoolIds", new CopyOnWriteArraySet<Long>());
						
						for(String gradeCourseAbbrName : gradeCoursesAbbrNames) {				
							ExecutionContext context = new ExecutionContext();
							context.put("schoolYear", state.getReportYear());
							context.put("gradeCourseAbbrName", gradeCourseAbbrName);
							context.put("organizationId", state.getId());
							context.put("bundledReportType", bundledReportType);
							partitionMap.put(gradeCourseAbbrName + "_" + state.getId(), context );					
						}
						
					}else if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL.equalsIgnoreCase(bundledReportType)){
						
						orgIds = batchReportProcessService.getDistrictIdsForStudentSummaryBundledReports(state.getId(), assessmentProgramId, new Long(state.getReportYear()), REPORT_TYPE_STUDENT_SUMMARY, null, null);
						
						jobExecution.getExecutionContext().put("successDistrictIds", new CopyOnWriteArraySet<Long>());
						
						ExecutionContext context = new ExecutionContext();
						context.put("schoolYear", state.getReportYear());
						context.put("gradeCourseAbbrName", null);
						context.put("organizationId", state.getId());
						context.put("bundledReportType", bundledReportType);
						partitionMap.put(state.getId()+ "_" + state.getDisplayIdentifier(), context);
						
					}					
				   
				}
				
				//Putting entry in organizationbundlereportprocess table for all the school that are going to process
				if(CollectionUtils.isNotEmpty(orgIds)){
					for (Long orgId : orgIds) {
						OrganizationBundleReport bundleReport = new OrganizationBundleReport();
						bundleReport.setOrganizationId(orgId);
						bundleReport.setActiveFlag(true);
						bundleReport.setStatus(inProgressStatusId);
						bundleReport.setAssessmentProgramId(assessmentProgramId);
						bundleReport.setAuditColumnProperties();
						bundleReport.setCreatedUser(-1l);
						bundleReport.setModifiedUser(-1l);
						bundleReport.setSchoolYear(new Long(state.getReportYear()));
						bundleReport.setSort1("legallastname");
						bundleReport.setSort2("student first name");
						bundleReport.setSort3("student id");						
						bundleReport.setReportType(bundledReportType);
						bundleReportService.insert(bundleReport);
						
						((List<OrganizationBundleReport>)jobExecution.getExecutionContext().get("bundleReports")).add(bundleReport);
					}	
				}
							
					
			}
		}
		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
	}

	
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}


	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}


	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}


	public Long getOrganizationId() {
		return organizationId;
	}


	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}


	public StepExecution getStepExecution() {
		return stepExecution;
	}


	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}


	private void writeReason(String msg) {
		logger.debug(msg);
		
	}


	public String getBundledReportType() {
		return bundledReportType;
	}


	public void setBundledReportType(String bundledReportType) {
		this.bundledReportType = bundledReportType;
	}


	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}


	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
}
