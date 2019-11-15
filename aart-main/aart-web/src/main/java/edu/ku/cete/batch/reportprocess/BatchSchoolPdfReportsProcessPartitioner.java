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
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchSchoolPdfReportsProcessPartitioner implements Partitioner{

	private final static Log logger = LogFactory .getLog(BatchSchoolPdfReportsProcessPartitioner.class);
		
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
	private Long batchReportProcessId;
	private Long organizationId;
	private StepExecution stepExecution;
	private JobExecution jobExecution;		
	
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;

	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
	
	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
	
	@Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;

	@Value("${cpass.student.individual.report.type.code}")
	private String dbCPASSStudentReportsImportReportType;
	
	@Value("${kelpa.student.bundled.report.type.code}")
	private String kelpaStudentBundledReportTypeCode;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchSchoolPdfReportsProcessPartitioner partition size : "+gridSize);
		
		jobExecution = stepExecution.getJobExecution();
		jobExecution.getExecutionContext().put("bundleReports",new CopyOnWriteArrayList<OrganizationBundleReport>());
		jobExecution.getExecutionContext().put("successSchoolIds", new CopyOnWriteArraySet<Long>());
		Long inProgressStatusId = categoryDao.getCategoryId("IN_PROGRESS", "PD_REPORT_STATUS");
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		List<Organization> states = new ArrayList<Organization>();
		List<Long> schoolIds = null;
		states.add(orgService.get(organizationId));
		if(CollectionUtils.isEmpty(states)) {
			writeReason("No contracting organizations found for assessmentprogramid - " + assessmentProgramId);
		} else {
			AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
			List<String> gradeCoursesAbbrNames = null;
			
			String reportType="";
			if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname()))
				reportType = cpassStudentBundledReportTypeCode;
			else if(assessmentProgram!=null && "DLM".equals(assessmentProgram.getAbbreviatedname()))
				reportType = alternateStudentBundledReportTypeCode;
			else if(assessmentProgram!=null && "KAP".equals(assessmentProgram.getAbbreviatedname()))
				reportType = generalStudentBundledReportTypeCode;
			else if(assessmentProgram!=null && "KELPA2".equals(assessmentProgram.getAbbreviatedname()))
				reportType = kelpaStudentBundledReportTypeCode;
			
			for(Organization state: states) {
				//Changed for F97 - report year changes
				Long newReportYear = orgService.getReportYear(state.getId(), assessmentProgramId);
				if(newReportYear != null)
					state.setReportYear(newReportYear.intValue());
				
				if("CPASS".equalsIgnoreCase(assessmentProgram.getAbbreviatedname()) || "DLM".equalsIgnoreCase(assessmentProgram.getAbbreviatedname())){
					String individualReportType="";
					if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname()))
						individualReportType = dbCPASSStudentReportsImportReportType;
					else if(assessmentProgram!=null && "DLM".equals(assessmentProgram.getAbbreviatedname()))
						individualReportType = dbDLMStudentReportsImportReportType;
					
					schoolIds = batchReportProcessService.getSchoolIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(state.getId(), assessmentProgramId, new Long(state.getReportYear()), null, null, null, individualReportType);
				    gradeCoursesAbbrNames = batchReportProcessService.geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYearForExternalReport(state.getId(), assessmentProgramId, state.getReportYear(), individualReportType);
				}else{
					schoolIds = batchReportProcessService.getSchoolIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(state.getId(), assessmentProgramId, new Long(state.getReportYear()), null, null, null);
					gradeCoursesAbbrNames = batchReportProcessService.geGradeCoursesAbbrNamesByAssmntPrgmAndSchoolYear(state.getId(), assessmentProgramId, state.getReportYear());
				}
				
				//Putting entry in organizationbundlereportprocess table for all the school that are going to process					
					for (Long schoolId : schoolIds) {
						OrganizationBundleReport bundleReport = new OrganizationBundleReport();
						bundleReport.setOrganizationId(schoolId);
						bundleReport.setActiveFlag(true);
						bundleReport.setStatus(inProgressStatusId);
						bundleReport.setAssessmentProgramId(assessmentProgram.getId());
						bundleReport.setAuditColumnProperties();
						bundleReport.setCreatedUser(-1l);
						bundleReport.setModifiedUser(-1l);
						bundleReport.setSchoolYear(new Long(state.getReportYear()));
						bundleReport.setSort1("grade");
						bundleReport.setSort2("legallastname");
						bundleReport.setSort3("subject");						
						bundleReport.setReportType(reportType);
						bundleReportService.insert(bundleReport);
						((List<OrganizationBundleReport>)jobExecution.getExecutionContext().get("bundleReports")).add(bundleReport);
					}
					
				for(String gradeCourseAbbrName : gradeCoursesAbbrNames) {					
					ExecutionContext context = new ExecutionContext();
					context.put("gradeCourseAbbrName", gradeCourseAbbrName);						
					context.put("schoolYear", state.getReportYear());
					context.put("stateId", state.getId());
					partitionMap.put(gradeCourseAbbrName+ "_" + state.getId(), context );					
				}				
			}
		}
		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
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

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}	
	
	private void writeReason(String msg) {
		logger.debug(msg);
		
	}
}
