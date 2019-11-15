package edu.ku.cete.batch.reportprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.service.report.TestCutScoresService;

public class BatchExternalStudentReportPartitioner implements Partitioner {

	private final static Log logger = LogFactory
			.getLog(BatchExternalStudentReportPartitioner.class);

	private Long assessmentProgramId;
	private Long schoolYear;
	private Long stateId;
	private String reportCycle;
	private Long testingProgramId;
	private Long gradeCourseId;
	private Long contentAreaId;
		
	@Autowired
	private StudentReportService studentReportService;

	@Autowired
	private TestCutScoresService testCutScoresService;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>(gridSize);
		List<String> assessmentCodes = studentReportService.getDistinctAssessmentCode(assessmentProgramId, stateId,	schoolYear, testingProgramId, reportCycle, gradeCourseId, contentAreaId);
		
		for (String assessmentCode : assessmentCodes) {
			List<TestCutScores> testCutScores = testCutScoresService.getExternalStudentReportTestCutScores(assessmentProgramId,	gradeCourseId, contentAreaId, schoolYear, testingProgramId, reportCycle, assessmentCode);
			ExecutionContext context = new ExecutionContext();
			context.put("assessmentCode", assessmentCode);
			context.put("testCutScores", testCutScores);
			partitionMap.put(assessmentCode + "_" + stateId, context);
		}

		return partitionMap;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getReportCycle() {
		return reportCycle;
	}

	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

}
