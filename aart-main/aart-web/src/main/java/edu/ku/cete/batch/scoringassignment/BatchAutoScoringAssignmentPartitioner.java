package edu.ku.cete.batch.scoringassignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.report.domain.BatchRegisteredScoringAssignments;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.TestSessionService;

/* sudhansu.b
 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
 */
public class BatchAutoScoringAssignmentPartitioner implements Partitioner {

	private final static Log logger = LogFactory.getLog(BatchAutoScoringAssignmentPartitioner.class);

	@Autowired
	TestSessionService testSessionService;

	@Autowired
	ContentAreaService contentAreaService;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;

	private StepExecution stepExecution;
	private Long assessmentProgramId;

	private JobExecution jobExecution;
	private final String[] ASSESSMENT_PROGRAM_CODES = { "KAP", "KELPA2" };
	private final String[] KAP_SCORING_SUBJECTS = { "SS" };

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchAutoScoringAssignmentPartitioner partition size : " + gridSize);
		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>(gridSize);

		jobExecution = stepExecution.getJobExecution();
		jobExecution.getExecutionContext().put("testSessionIds", new CopyOnWriteArrayList<Long>());
		jobExecution.getExecutionContext().put("batchScoringAssignments",
				new CopyOnWriteArrayList<BatchRegisteredScoringAssignments>());
		jobExecution.getExecutionContext().put("successStudents", new CopyOnWriteArraySet<Long>());
		jobExecution.getExecutionContext().put("failedStudents", new CopyOnWriteArraySet<Long>());
		jobExecution.getExecutionContext().put("mappedStudents", new CopyOnWriteArraySet<Long>());
		List<ContentArea> subjects = new ArrayList<>();
		for (String assessmentProgramCode : ASSESSMENT_PROGRAM_CODES) {
			AssessmentProgram ap= assessmentProgramService.findByAbbreviatedName(assessmentProgramCode);
			if ("KAP".equalsIgnoreCase(assessmentProgramCode)) {
				subjects = contentAreaService.findByAbbreviatedNamesAndAssessmentProgramCode(KAP_SCORING_SUBJECTS,
						assessmentProgramCode);
			} else {
				subjects = contentAreaService.findByAssessmentProgramCode(assessmentProgramCode);
			}

			for (ContentArea contentArea : subjects) {

				ExecutionContext context = new ExecutionContext();
				context.put("assessmentProgramId",ap.getId());
				context.put("subjectId", contentArea.getId());
				partitionMap.put(contentArea.getAbbreviatedName() + contentArea.getId(), context);
			}

		}
		logger.debug("BatchAutoScoringAssignmentPartitioner partition created with size : " + partitionMap.size());
		return partitionMap;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
}