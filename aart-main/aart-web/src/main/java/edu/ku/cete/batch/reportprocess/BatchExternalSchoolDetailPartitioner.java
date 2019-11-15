package edu.ku.cete.batch.reportprocess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.report.domain.AssessmentTopic;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.report.AssessmentTopicService;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class BatchExternalSchoolDetailPartitioner implements Partitioner {

	private final static Log logger = LogFactory
			.getLog(BatchExternalSchoolDetailPartitioner.class);

	private Long assessmentProgramId;
	private Long schoolYear;
	private Long stateId;
	private String reportCycle;
	private Long testingProgramId;
	private Long gradeCourseId;
	private Long contentAreaId;
		
	@Autowired
	private AssessmentTopicService assessmentTopicService;
	
	@Autowired
	private TestTypeService testTypeService;

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {

		Map<String, ExecutionContext> partitionMap = new HashMap<String, ExecutionContext>(gridSize);
		List<String> assessmentCodes = assessmentTopicService.getAvailableAssessmentCodes(schoolYear, gradeCourseId, contentAreaId);
		if(assessmentCodes.size()>0){			
			for (String assessmentCode : assessmentCodes) {
				    List<AssessmentTopic> topics = assessmentTopicService.getTopicsByAssessmentCodes(assessmentCode);
					List<TestType> assessment = testTypeService.getCPASSTestTypesForReportsByTestTypeCode(testingProgramId, assessmentCode);
					ExecutionContext context = new ExecutionContext();
					context.put("assessmentCode", assessmentCode);
					context.put("assessmentName", assessment.get(0).getTestTypeName());
					context.put("topics", topics);
					
					partitionMap.put(assessmentCode + "_" + stateId, context);
				}
		}else{
			logger.info("Data not available for Assessment Topic");
		}		
		logger.debug("Created "+partitionMap.size()+" partitions.");
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
