package edu.ku.cete.dataextracts.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;

public interface DataExtractsSupportMapper {

	Organization get(long organizationId);
	List<Organization> getAllChildrenByType(@Param("organizationId") Long organizationId, @Param("organizationTypeCode") String organizationTypeCode);
	List<Long> getAllChildrenIdsByType(@Param("organizationId") Long orgId, @Param("typeCode") String typeCode);
	List<Organization> getAllChildrenWithParentByType(@Param("organizationId") Long organizationId, @Param("organizationTypeCode") String organizationTypeCode);
	List<Organization> getAllParents(long organizationId);
	List<Long> getAllChildrenOrgIds(@Param("organizationId") Long organizationId);
	
	AssessmentProgram findByAssessmentProgramId(@Param("assessmentProgramId")long assessmentProgramId);
	AssessmentProgram findByAbbreviatedName(@Param("abbreviatedName") String abbreviatedName);
	AssessmentProgram findByStudentId(@Param("studentId") Long id, @Param("currentSchoolYear") int currentSchoolYear);
	List<AssessmentProgram> getAllAssessmentProgramByUserId(@Param("userId") Long userId);
	
	Student findById(@Param("studentId") long studentId);
	
	User getActiveOrInactiveUser(@Param("id") long id);
	
	Long getCategoryId(@Param("categoryCode") String categoryCode, @Param("categoryTypeCode") String categoryTypeCode);
	//Grade
	List<GradeCourse> findGradesByOrgIdForStudentScoreExtract(@Param("isDistict") boolean isDistict,@Param("isSchool") boolean isSchool,
			@Param("organizationId") List<Long> orgId, @Param("schoolYear") int schoolYear, @Param("displayIdentifier") String displayIdentifier,@Param("assessmentProgramId") Long assessmentProgramId);

	
	List<GradeCourse> findGradesByOrgIdForStudentScoreTestedExtract(@Param("isDistict") boolean isDistict,@Param("isSchool") boolean isSchool,
			@Param("organizationId") List<Long> orgId, @Param("assessmentProgramId") Long assessmentProgramId, @Param("stateId") Long organizationId, @Param("displayIdentifier") String displayIdentifier,@Param("reportYear") Long reportYear);
	
	//Subject
	List<ContentArea> getSubjectsForStudentScoreExtract(@Param("schoolYear") Long schoolYear, @Param("assessmentProgramId") Long assessmentProgramId,@Param("isDistict")boolean isDistict,@Param("isSchool")boolean isSchool,@Param("orgList") List<Long> orgList);
	List<ContentArea> getSubjectsForTestedStudentScoreExtract(@Param("reportYear") Long reportYear, @Param("assessmentProgramId") Long assessmentProgramId,@Param("isDistict")boolean isDistict, @Param("isSchool")boolean isSchool,@Param("orgList") List<Long> orgList);
	//SchoolYear
	List<Long> getStudentReportSchoolYearsBySubject(@Param("contentAreaId") List<Long> contentAreaId,@Param("assessmentCode") String assessmentCode);
	List<Long> getStudentReportSchoolYearsBySubjectForKelpa(@Param("assessmentCode") String kelpa2StudentReportAssessmentcode);
	
	List<GradeCourse> findGradesByOrgIdForKelpaStudentScoreExtract(@Param("isDistict") boolean isDistict,@Param("isSchool") boolean isSchool,
			@Param("organizationId") List<Long> orgId, @Param("schoolYear") int schoolYear, @Param("displayIdentifier") String displayIdentifier,@Param("assessmentProgramId") Long assessmentProgramId);
	
	
}
