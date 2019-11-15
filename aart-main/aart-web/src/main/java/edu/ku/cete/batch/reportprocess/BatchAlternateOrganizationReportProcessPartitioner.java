package edu.ku.cete.batch.reportprocess;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.service.report.UploadGrfFileService;

public class BatchAlternateOrganizationReportProcessPartitioner implements Partitioner{

	private final static Log logger = LogFactory .getLog(BatchAlternateOrganizationReportProcessPartitioner.class);
		
	@Autowired
	UploadGrfFileService uploadGrfFileService;
			
	private StepExecution stepExecution;
	private Long stateId;
	private Long reportYear;
	private Long assessmentProgramId;
	
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchAlternateOrganizationReportProcessPartitioner partition size : "+gridSize);
		
		stepExecution.getJobExecution();		
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);		
		ExecutionContext context = new ExecutionContext();
		context.put("stateId", stateId);						
		partitionMap.put(stateId.toString(), context );							
		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getReportYear() {
		return reportYear;
	}

	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	
}
