package edu.ku.cete.service.report;

import java.util.List;

import edu.ku.cete.domain.report.SubscoreFramework;;

public interface SubscoreFrameworkService {
	Integer deleteAllSubscoreFramework(Long schoolYear);
	Integer deleteSubscoreFrameworks(Long assessmentProgramId, Long contentAreaId, Long schoolYear);
	Integer insertSelectiveSubscoreFramework(SubscoreFramework subscoreFramework);
	List<String> mapSubscoreDefinitionFromTaskVariant(Long schoolYear, Long taskVariantId, Long assessmentProgramId, Long subjectId, Long gradeId);
	SubscoreFramework selectSubscoreframeworkByAssessmentGradeSubjectLevel1Level2(Long schoolYear, Long assessmentProgramId, Long subjectId, 
			Long gradeId, String subScoreDefinitionName, String frameworkCode, String frameworkCodeLevel1, String frameworkCodeLevel2, String frameworkCodeLevel3);
	
	List<String> getMissingSubscoreDefinitions(Long schoolYear, Long assessmentProgramId, Long subjectId, Long gradeId);
}
