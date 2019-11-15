package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.report.domain.OrganizationPrctByAssessmentTopic;
import edu.ku.cete.report.domain.OrganizationPrctTopicReportsDTO;
/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public interface OrganizationPrctByAssessmentTopicMapper {
	
    Integer insert(OrganizationPrctByAssessmentTopic organizationPrctByAssessmentTopic);
	
	Integer update(OrganizationPrctByAssessmentTopic organizationPrctByAssessmentTopic);
	
	void delete(@Param("contentAreaId")Long contentAreaId, @Param("schoolYear") Long schoolYear, 
			@Param("stateId") Long stateId, @Param("testingProgramId") Long testingProgramId, @Param("reportCycle") String reportCycle);

	List<Long> getSchoolIdsForReportGeneration(@Param("schoolYear") Long schoolYear,@Param("stateId") Long stateId,
			 @Param("assessmentCode") String assessmentCode, @Param("reportCycle") String reportCycle, @Param("testingProgramId") Long testingProgramId,
			 @Param("gradeCourseId") Long gradeCourseId,@Param("contentAreaId") Long contentAreaId, @Param("offset") int offset, @Param("pageSize") int pageSize);
	
	 
    List<OrganizationPrctTopicReportsDTO> getOrganizationDetails(@Param("schoolId") Long schoolId,
			@Param("reportCycle") String reportCycle, @Param("testingProgramId") Long testingProgramId, @Param("assessmentCode") String assessmentCode,
    @Param("schoolYear") Long schoolYear, @Param("assessmentProgramId") Long assessmentProgramId, @Param("topicsId") List<Long> topicsId);
     
}
