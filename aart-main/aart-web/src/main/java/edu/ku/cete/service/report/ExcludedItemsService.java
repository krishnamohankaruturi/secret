package edu.ku.cete.service.report;

import java.util.List;
import java.util.Set;

import edu.ku.cete.domain.report.ExcludedItems;



public interface ExcludedItemsService {

	Integer deleteAllExcludedItems(Long schoolYear);
	
	Integer deleteExcludedItems(Long assessmentProgramId, Long contentAreaId, Long schoolYear);
	
	Integer insertSelectiveExcludedItems(ExcludedItems excludedItem);
	
	List<ExcludedItems> getByTaskVariantIds(Long assessmentProgramId, Long subjectId, Long gradeId, Long schoolYear, Set<Long> taskVariantList, Long testingProgramId, String reportCycle);

	List<ExcludedItems> getByAssessmentProgramContentAreaGrade(
			Long assessmentProgramId, Long contentAreaId, Long gradeId,
			Long currentSchoolYear);
}
