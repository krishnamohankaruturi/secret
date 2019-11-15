package edu.ku.cete.batch.reportprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.report.BatchReportProcessService;



public class BatchDynamicBundleofStudentReportsPartitioner implements Partitioner{

	private final static Log logger = LogFactory .getLog(BatchDynamicBundleofStudentReportsPartitioner.class);
		
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
    private OrganizationService orgService;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;
	
	 private JobExecution jobExecution;
	 private Long requestProcessId;
	 private String assessmentProgramCode;
	 private OrganizationBundleReport bundleRequest;
	 private List<Long> schoolIds;
	 private List<Long> subjectIds;
	 private List<Long> gradeIds;
		
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchDynamicBundleofStudentReportsPartitioner partition size : "+gridSize);
		
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
				
		if(bundleRequest.getBySchool()){	
			for (Long schoolId : schoolIds) {
				ExecutionContext context = new ExecutionContext();
				context.put("organizationType", "SCH");
				context.put("organizationId", schoolId);
				partitionMap.put(schoolId.toString(), context);
			}			
		}
		
		Organization organization = orgService.get(bundleRequest.getOrganizationId());
		ExecutionContext context = new ExecutionContext();
		context.put("organizationType", organization.getTypeCode());
		context.put("organizationId", bundleRequest.getOrganizationId());
		partitionMap.put(bundleRequest.getOrganizationId().toString(), context);
		
		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
	}

	public JobExecution getJobExecution() {
		return jobExecution;
	}

	public void setJobExecution(JobExecution jobExecution) {
		this.jobExecution = jobExecution;
	}

	public Long getRequestProcessId() {
		return requestProcessId;
	}

	public void setRequestProcessId(Long requestProcessId) {
		this.requestProcessId = requestProcessId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}

	public OrganizationBundleReport getBundleRequest() {
		return bundleRequest;
	}

	public void setBundleRequest(OrganizationBundleReport bundleRequest) {
		this.bundleRequest = bundleRequest;
	}

	public List<Long> getSchoolIds() {
		return schoolIds;
	}

	public void setSchoolIds(List<Long> schoolIds) {
		this.schoolIds = schoolIds;
	}

	public List<Long> getSubjectIds() {
		return subjectIds;
	}

	public void setSubjectIds(List<Long> subjectIds) {
		this.subjectIds = subjectIds;
	}

	public List<Long> getGradeIds() {
		return gradeIds;
	}

	public void setGradeIds(List<Long> gradeIds) {
		this.gradeIds = gradeIds;
	}

}
