package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.report.domain.StudentPrctByAssessmentTopic;
import edu.ku.cete.report.domain.StudentPrctTopicReportsDTO;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public interface StudentPrctByAssessmentTopicMapper {
	
    Integer insert(StudentPrctByAssessmentTopic studentPrctByAssessmentTopic);
	
	Integer update(StudentPrctByAssessmentTopic studentPrctByAssessmentTopic);
	
	void delete(@Param("contentAreaId")Long contentAreaId, @Param("schoolYear") Long schoolYear, 
			@Param("stateId") Long stateId, @Param("testingProgramId") Long testingProgramId, @Param("reportCycle") String reportCycle);

	List<StudentPrctTopicReportsDTO> getStudentDetails(@Param("schoolId") Long schoolId,
			@Param("reportCycle") String reportCycle, @Param("testingProgramId") Long testingProgramId, @Param("assessmentCode") String assessmentCode,
			@Param("schoolYear") Long schoolYear, @Param("assessmentProgramId") Long assessmentProgramId, @Param("topicsId") List<Long> topicsId);
}
