package edu.ku.cete.batch.scoringassignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.reportprocess.processor.BatchSchoolPdfReportsProcessor;
import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.ScoringAssignment;
import edu.ku.cete.domain.ScoringAssignmentScorer;
import edu.ku.cete.domain.ScoringAssignmentStudent;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.model.AutoScoringStudentstest;
import edu.ku.cete.service.ScoringAssignmentServices;
import edu.ku.cete.util.AARTCollectionUtil;
import edu.ku.cete.util.SourceTypeEnum;

/* sudhansu.b
 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
 */	
public class BatchAutoScoringAssignmentProcessor implements
		ItemProcessor<List<AutoScoringStudentstest>, Object> {

	private StepExecution stepExecution;
	private Long assessmentProgramId;
	private User user;
	
	private JobExecution jobExecution;
	
	@Autowired
	private ScoringAssignmentServices scoringAssignmentServices;
	
	final static Log logger = LogFactory
			.getLog(BatchSchoolPdfReportsProcessor.class);
    
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
	   jobExecution = stepExecution.getJobExecution();        
	}	
	
	@Override
	public ScoringAssignment process(
			List<AutoScoringStudentstest> autoScoringStudentstests)
			throws Exception {
		Date now = new Date();
		ScoringAssignment scoringAssignment = null;
		if (autoScoringStudentstests.size() > 0) {
			scoringAssignment = scoringAssignmentServices
					.getByTestSessionAndRoster(autoScoringStudentstests.get(0).getTestSessionId(),
							autoScoringStudentstests.get(0).getRosterId());

			Integer userId = user.getId().intValue();

			List<ScoringAssignmentStudent> scoringAssignmentStudentList = new ArrayList<ScoringAssignmentStudent>();
			List<ScoringAssignmentScorer> scoringAssignmentScorerList = new ArrayList<ScoringAssignmentScorer>();
			
			List<Long> stuentsTestIds = new ArrayList<Long>();
			List<Long> scorerIds = new ArrayList<Long>();
			List<Long> mappedStudentIds = new ArrayList<Long>();
			
			for (AutoScoringStudentstest autoScoringStudentstest : autoScoringStudentstests) {
				stuentsTestIds.add(autoScoringStudentstest.getStudentstestId());
			}
			
			scorerIds.add(autoScoringStudentstests
					.get(0).getScorerId());
			List<Student> alreadyMappedStudentsList = scoringAssignmentServices.getMappedstudentForScorers(stuentsTestIds, scorerIds);
			
			if(alreadyMappedStudentsList.size() >0){
				mappedStudentIds = AARTCollectionUtil.getIds(alreadyMappedStudentsList);
			}
			if (scoringAssignment == null) {
				scoringAssignment = new ScoringAssignment();
				scoringAssignment.setCreatedUser(userId);
				scoringAssignment.setTestSessionId(autoScoringStudentstests.get(0).getTestSessionId());
				scoringAssignment.setCreatedDate(now);
				scoringAssignment.setRosterId(autoScoringStudentstests.get(0).getRosterId());
				scoringAssignment.setActive(true);
				scoringAssignment.setSource(SourceTypeEnum.AUTOSCORING.getCode());
				scoringAssignment.setCcqTestName(autoScoringStudentstests.get(0).getTestSessionName()
						+ "_"
						+ autoScoringStudentstests.get(0).getRosterName().trim()
						+ "_"
						+ autoScoringStudentstests.get(0).getEducatorIdentifier().trim()
						+ "_"
						+ (autoScoringStudentstests.get(0).getAypSchoolIdentifier() != null ? autoScoringStudentstests.get(0).getAypSchoolIdentifier().trim():"")
								);
				ScoringAssignmentScorer scoringAssignmentScorer = new ScoringAssignmentScorer();
				scoringAssignmentScorer.setScorerid(autoScoringStudentstests
						.get(0).getScorerId());
				scoringAssignmentScorer.setCreatedUser(userId);
				scoringAssignmentScorer.setCreatedDate(now);
				scoringAssignmentScorer.setActive(true);
				scoringAssignmentScorerList.add(scoringAssignmentScorer);

				scoringAssignment
						.setScoringAssignmentScorer(scoringAssignmentScorerList);
			}
			
			List<Long> studentIds = scoringAssignmentServices.getStudentsListByScoringAssignmentId(scoringAssignment.getId());
			
			for (AutoScoringStudentstest autoScoringStudentstest : autoScoringStudentstests) {
				
				if(!studentIds.contains(autoScoringStudentstest.getStudentId())){					
					/*if(!mappedStudentIds.contains(autoScoringStudentstest.getStudentId())){		*/			
						ScoringAssignmentStudent scoringAssignmentStudent = new ScoringAssignmentStudent();		
						scoringAssignmentStudent
								.setStudentsTestsId(autoScoringStudentstest
										.getStudentstestId());
						scoringAssignmentStudent.setStudentId(autoScoringStudentstest
								.getStudentId());
						scoringAssignmentStudent.setTestId(autoScoringStudentstest
								.getTestId());
						scoringAssignmentStudent.setEnrollmentId(autoScoringStudentstest
								.getEnrollmentId());
						scoringAssignmentStudent.setCreatedUser(userId);
						scoringAssignmentStudent.setCreatedDate(now);
						scoringAssignmentStudent.setActive(true);
						
						scoringAssignmentStudentList.add(scoringAssignmentStudent);
					/*}else{*/
						//((CopyOnWriteArraySet<Long>) jobExecution.getExecutionContext().get("mappedStudents")).add(autoScoringStudentstest.getStudentId());
					//}
				}
			}

			scoringAssignment
					.setScoringAssignmentStudent(scoringAssignmentStudentList);
		}else{
			throw new SkipBatchException("Skipping Scoring Batch Process");
		}
		logger.debug("BatchAutoScoringAssignmentProcessor Ended");
		return scoringAssignment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
