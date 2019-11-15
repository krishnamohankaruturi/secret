package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import edu.ku.cete.service.report.UploadGrfFileService;

public class BatchDlmSummaryGrfCalculationProcessPageReader<T> extends AbstractPagingItemReader<T> {
	
	
	@Autowired 
	UploadGrfFileService uploadGrfFileService;

	private Long subjectId;
	private Long gradeId;
	private Long stateId;
	private Long reportYear;
	private Long assessmentProgramId;
	 
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		
		logger.debug("Inside BatchDlmSummaryGrfCalculationProcessPageReader" );
		results.addAll(getDistinctOrgIdsFromGeneralReasearch(subjectId,gradeId,stateId,reportYear,assessmentProgramId, getPage() * getPageSize(), getPageSize()));
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		// nothing
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getDistinctOrgIdsFromGeneralReasearch(Long subjectId,Long gradeId,Long stateId,Long reportYear,Long assessmentProgramId, Integer offset, Integer pageSize) {		
		List<T> results = (List<T>) uploadGrfFileService.getDistinctOrgIdsFromGeneralReasearch(subjectId,gradeId,stateId,reportYear,assessmentProgramId,offset,pageSize);
		return results;
	}
	

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
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
