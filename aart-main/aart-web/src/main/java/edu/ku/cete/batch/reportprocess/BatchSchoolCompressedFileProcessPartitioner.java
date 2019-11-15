package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
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

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchSchoolCompressedFileProcessPartitioner implements Partitioner{

	private final static Log logger = LogFactory .getLog(BatchSchoolCompressedFileProcessPartitioner.class);
	
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
    private OrganizationService orgService;
	
	private Long assessmentProgramId;
	private Long batchReportProcessId;
	private Long organizationId;
	private StepExecution stepExecution;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchSchoolCompressedFileProcessPartitioner partition size : "+gridSize);
		
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		List<Organization> states = new ArrayList<Organization>();
		states.add(orgService.get(organizationId));
		if(CollectionUtils.isEmpty(states)) {
			writeReason("No contracting organizations found for assessmentprogramid - " + assessmentProgramId);
		} else {
			for(Organization state: states) {
				ExecutionContext context = new ExecutionContext();
				context.put("contractingOrganizationId", state.getId());
				context.put("schoolYear", state.getReportYear());
				partitionMap.put(state.getId()+ "_" + state.getDisplayIdentifier(), context );
			}
		}
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
