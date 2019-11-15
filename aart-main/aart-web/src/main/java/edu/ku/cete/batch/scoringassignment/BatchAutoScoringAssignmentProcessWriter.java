package edu.ku.cete.batch.scoringassignment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.context.SecurityContextHolder;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.ScoringAssignment;
import edu.ku.cete.domain.ScoringAssignmentScorer;
import edu.ku.cete.domain.ScoringAssignmentStudent;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ScoringAssignmentMapper;
import edu.ku.cete.model.ScoringAssignmentScorerMapper;
import edu.ku.cete.model.ScoringAssignmentStudentMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.report.domain.BatchRegisteredScoringAssignments;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.service.ScoringAssignmentServices;

public class BatchAutoScoringAssignmentProcessWriter  implements ItemWriter<ScoringAssignment>{
	
	private final static Log logger = LogFactory.getLog(BatchAutoScoringAssignmentProcessWriter.class);
	
	private Long batchRegistrationId;
	
	@Autowired
	private ScoringAssignmentMapper scoringAssignmentDao;
	
	@Autowired
	private ScoringAssignmentStudentMapper scoringAssignmentStudentDao;
	
	@Autowired
	private ScoringAssignmentScorerMapper scoringAssignmentScorerDao;
	
	@Autowired
	private StudentsTestsDao studentsTestsDao;
	
	@Autowired
	private ScoringAssignmentServices scoringAssignmentService;

	private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        jobExecution = stepExecution.getJobExecution();        
    }
	
    @SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends ScoringAssignment> scoringAssignmentList) throws Exception {
		BatchRegistration brRecord = (BatchRegistration) jobExecution.getExecutionContext().get("batchRegistrationRecord");
		for (ScoringAssignment scoringAssignment : scoringAssignmentList) {
			logger.debug("BatchAutoScoringAssignmentProcessWriter started for scoringAssignment : "+ scoringAssignment.getCcqTestName());	
			
			List<ScoringAssignmentStudent> scoringAssignmentStudentList = scoringAssignment.getScoringAssignmentStudent();
			List<ScoringAssignmentScorer> ScoringAssignmentsScorerList = scoringAssignment.getScoringAssignmentScorer();
			
			List<Long> studentsTestIds = new ArrayList<Long>();
			Set<Long> testIds = new HashSet<>();
			if(scoringAssignmentStudentList.size() > 0){
				if(scoringAssignment.getId() == null){//New Entry
					try{
						scoringAssignmentDao.insertSelective(scoringAssignment);
					}catch(DuplicateKeyException Dk ){
						jobExecution.getExecutionContext().put("dupulicateTestName","Test name : "+scoringAssignment.getCcqTestName()+" is already exist");
						for (ScoringAssignmentStudent scoringAssignmentStudent : scoringAssignmentStudentList) {
							studentsTestIds.add(scoringAssignmentStudent.getStudentsTestsId());
							((CopyOnWriteArraySet<Long>) jobExecution.getExecutionContext().get("failedStudents")).add(scoringAssignmentStudent.getStudentId());
						}					
						throw new SkipBatchException("Assignment name : "+scoringAssignment.getCcqTestName()+" is already exist");
					}
				
					Long id = scoringAssignment.getId();
					for( ScoringAssignmentScorer scoringAssignmentsScore: ScoringAssignmentsScorerList){
						scoringAssignmentsScore.setScoringAssignmentId(id);
						scoringAssignmentScorerDao.insert(scoringAssignmentsScore);
					}
				}
				
				for (ScoringAssignmentStudent scoringAssignmentStudent : scoringAssignmentStudentList) {
					studentsTestIds.add(scoringAssignmentStudent.getStudentsTestsId());
					testIds.add(scoringAssignmentStudent.getTestId());
					((CopyOnWriteArraySet<Long>) jobExecution.getExecutionContext().get("successStudents")).add(scoringAssignmentStudent.getStudentId());
					scoringAssignmentStudent.setScoringAssignmentId(scoringAssignment.getId());
					scoringAssignmentStudentDao.insert(scoringAssignmentStudent);
				}
				
				UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
		                .getContext().getAuthentication().getPrincipal();
				
				//It will update the scoringassignmentid in studentstest table
				if(studentsTestIds.size() > 0){
				   studentsTestsDao.updateScoringAssignment(scoringAssignment.getId(),studentsTestIds,userDetails.getUserId());
				}
				
				//Update KELPA2 scoring status	
				for (Long studentsTestId : studentsTestIds) {
					scoringAssignmentStudentDao.updateKelpaScoringStatus(studentsTestId,Long.valueOf(scoringAssignmentStudentList.get(0).getCreatedUser()));
				}					
				
				//update scoringTestMetaData
				scoringAssignmentService.createScoringTestMetaData(testIds);
				
				BatchRegisteredScoringAssignments batchRegisteredScoringAssignments = new BatchRegisteredScoringAssignments();
				batchRegisteredScoringAssignments.setBatchRegistrationId(brRecord.getId());
				batchRegisteredScoringAssignments.setScoringAssignmentId(scoringAssignment.getId());			
				((CopyOnWriteArrayList<BatchRegisteredScoringAssignments>) jobExecution.getExecutionContext().get("batchScoringAssignments")).add(batchRegisteredScoringAssignments);
				
			}
		}	
	
	}	

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

		
}
