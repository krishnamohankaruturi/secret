package edu.ku.cete.batch.reportprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.service.report.UploadGrfFileService;

public class BatchDLMSummaryGrfCalculationProcessPartitioner implements Partitioner{

	private final static Log logger = LogFactory .getLog(BatchDLMSummaryGrfCalculationProcessPartitioner.class);
		
	@Autowired
	UploadGrfFileService uploadGrfFileService;
			
	private StepExecution stepExecution;
	private Long stateId;
	private Long reportYear;
	private Long assessmentProgramId;
	
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchDLMSummaryGrfCalculationProcessPartitioner partition size : "+gridSize);
		
		stepExecution.getJobExecution();		
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		
		List<Long> subjects = uploadGrfFileService.getDistinctSubjects(stateId,reportYear,assessmentProgramId);
		
		if(subjects.size()>0){
			for(Long subjectId : subjects){
				List<Long> grades = uploadGrfFileService.getDistinctGradesBySubjectId(stateId,subjectId,reportYear,assessmentProgramId);
				if(grades.size()>0){
					for(Long gradeId : grades){
						ExecutionContext context = new ExecutionContext();
						context.put("subjectId", subjectId);						
						context.put("gradeId", gradeId);
						partitionMap.put(subjectId+ "_" + gradeId, context );						
					}
				}
			}
		}	
				
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
