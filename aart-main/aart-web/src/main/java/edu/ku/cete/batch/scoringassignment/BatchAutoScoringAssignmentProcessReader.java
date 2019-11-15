package edu.ku.cete.batch.scoringassignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.RosterAutoScoringStudentsTestsMap;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.model.AutoScoringStudentstest;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;

/* sudhansu.b
 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
 */	
public class BatchAutoScoringAssignmentProcessReader<T> extends AbstractPagingItemReader<T> {
	
	private final static Log logger = LogFactory.getLog(BatchAutoScoringAssignmentProcessReader.class);	
	
	private Integer schoolYear;
	private Long subjectAreaId;
	private Long assessmentProgramId;
	private StepExecution stepExecution;
	
	private JobExecution jobExecution;
	
	private List<TestSession> sessions = null;
	private List<List<AutoScoringStudentstest>> autoScoringStudentstests = null;
	List<List<AutoScoringStudentstest>> autoScoringStudentstestPagination = new ArrayList<List<AutoScoringStudentstest>>();
	
	@Autowired
	StudentsTestsService studentsTestsService;

	@Autowired
	TestSessionService testSessionService;
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		 jobExecution = stepExecution.getJobExecution();
		results.addAll(getStudentsTestForAutoScoring(schoolYear,subjectAreaId,getPage() * getPageSize(), getPageSize()));
	}
	
	@SuppressWarnings("unchecked")
	private List<T> getStudentsTestForAutoScoring(Integer schoolYear,Long subjectAreaId,Integer offset, Integer pageSize) {
		
	    autoScoringStudentstestPagination.clear();
		
		if(autoScoringStudentstests == null ){
			   sessions = testSessionService.getSessionsForAutoScoring(assessmentProgramId);
			   autoScoringStudentstests = new ArrayList<List<AutoScoringStudentstest>>();
			   for(TestSession testSession : sessions){
					((CopyOnWriteArrayList<Long>) jobExecution.getExecutionContext().get("testSessionIds")).add(testSession.getId());
					List<RosterAutoScoringStudentsTestsMap> AutoScoringMap = studentsTestsService.getStudentsTestForAutoScoring(testSession.getId(),schoolYear,subjectAreaId,getPage() * getPageSize(),getPageSize());
					for (RosterAutoScoringStudentsTestsMap rosterAutoScoringStudentsTestsMap : AutoScoringMap) {
						   autoScoringStudentstests.add(rosterAutoScoringStudentsTestsMap.getAutoScoringStudentstests());
					}	
				}
		}
		
		if(autoScoringStudentstests.size() > 0 && offset < autoScoringStudentstests.size()){
		 Integer offestLimit = offset+pageSize > autoScoringStudentstests.size() ? autoScoringStudentstests.size() : offset+pageSize;
	      for(int i = offset;  i < offestLimit; i++ ){
	    	  autoScoringStudentstestPagination.add(autoScoringStudentstests.get(i)); 
	      }
		
		}
		
		logger.debug("BatchAutoScoringAssignmentProcessReader found "+ autoScoringStudentstests.size()+" rosters with testsession ");
		return (List<T>) autoScoringStudentstestPagination;
	}
	
	@Override
	protected void doJumpToPage(int itemIndex) {		
		logger.debug("NO-OP");
	}
	
	public Integer getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Integer schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getSubjectAreaId() {
		return subjectAreaId;
	}

	public void setSubjectAreaId(Long subjectAreaId) {
		this.subjectAreaId = subjectAreaId;
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
