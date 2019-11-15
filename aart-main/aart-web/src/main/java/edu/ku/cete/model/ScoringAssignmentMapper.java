package edu.ku.cete.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.ScoringAssignment;
import edu.ku.cete.domain.ScoringUploadDto;
import edu.ku.cete.web.ScoreTestScoringCriteriaDTO;
import edu.ku.cete.web.ScoreTestSelfToHarmDTO;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.web.AssignScorerStudentScorerCriteriaDTO;
import edu.ku.cete.web.ScoreTestTestSessionDTO;
import edu.ku.cete.web.ScorerStudentResourcesDTO;
import edu.ku.cete.web.ScorerTestStudentsSessionDTO;

public interface ScoringAssignmentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scoringassignment
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    int insert(ScoringAssignment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scoringassignment
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    int insertSelective(ScoringAssignment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scoringassignment
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    ScoringAssignment selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scoringassignment
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    int updateByPrimaryKeySelective(ScoringAssignment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table scoringassignment
     *
     * @mbggenerated Mon Sep 21 17:07:36 IST 2015
     */
    int updateByPrimaryKey(ScoringAssignment record);

   	List<ScoreTestScoringCriteriaDTO> getStudentTestScoringCriteria(@Param("testSessionId") Long testSessionId,
			@Param("studentId") Long studentId, @Param("testsId") Long studentsTestsId,@Param("variantValue") Long variantValue);

    Integer getCountTestSessionAndStudentCountForScorer(@Param("userId")Long userId,@Param("stateId") Long stateId,
    		   @Param("assessmentPrgId") Long assessmentPrgId, @Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId,
    		   @Param("sidx") String sortByColumn, @Param("sord") String sortType,
    		   @Param("scorerTestSessionRecordCriteriaMap") Map<String, String> scorerTestSessionRecordCriteriaMap,
               @Param("isScoreAllTest") boolean isScoreAllTest               
               );

    List<ScoreTestTestSessionDTO> getTestSessionAndStudentCountForScorer(@Param("userId")Long userId,
    		   @Param("stateId") Long stateId, @Param("assessmentPrgId") Long assessmentPrgId, @Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId,
    		   @Param("sidx") String sortByColumn, @Param("sord") String sortType,
    		   @Param("offset") Integer i, 
   			   @Param("limit") Integer limitCount,
    		   @Param("scorerTestSessionRecordCriteriaMap") Map<String, String> scorerTestSessionRecordCriteriaMap,@Param("isScoreAllTest") boolean isScoreAllTest, 
    		   @Param("assessmentProgramCode") String assessmentProgramCode,
    		   @Param("schoolYear") Long schoolYear);
    
    List<ContentArea> findByContentArea(@Param("assessmentProgramId")Long assessmentProgramId, 
    		                            @Param("schoolId") Long schoolId,
    		                            @Param("currentSchoolYear") int currentSchoolYear);

    List<GradeCourse> selectGradeCourseByContentAreaId(@Param("contentAreaId") Long contentAreaId, 
    		                                           @Param("currentSchoolYear") int currentSchoolYear,
    		       		                               @Param("assessmentProgramId") Long assessmentProgramId,
    		       		                               @Param("schoolId") Long schoolId);	
    
    List<ScorerTestStudentsSessionDTO> getScorerStudentsAppearsForScoreTests(
    		@Param("scoringAssignmentId") Long scoringAssignmentId,
    		@Param("assessmentProgramId") Long assessmentProgramId,
    		@Param("scorerId") Long scorerId,	
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("isScoreAllTest") boolean isScoreAllTest
			/*String sortByColumn, String sortType,Integer i,Integer limitCount,
			Map<String,String> scorerTestStudentRecordCriteriaMap*/);

	Integer getScorerStudentsAppearsForScoreTestsCount(
			@Param("scoringAssignmentId") Long scoringAssignmentId,
			@Param("assessmentProgramId") Long assessmentProgramId,
			@Param("scorerId") Long scorerId,
			@Param("scorerTestStudentRecordCriteriaMap") Map<String,String> scorerTestStudentRecordCriteriaMap,
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("isScoreAllTest") boolean isScoreAllTest);
	
	List<AssignScorerStudentScorerCriteriaDTO> checkAssignScoringStudentScorer(
			@Param("testSessionId") Long testSessionId,
			@Param("studentIds") Long[] studentIds,
			@Param("scorerIds") Long[] scorerIds);
	
	Integer checkUniqueCcqTestName(@Param("ccqTestName") String ccqTestName);
	
	Integer getCountTestSessionAndStudentCountForMonitorScorer(@Param("districtId")Long districtId,@Param("schoolId")Long schoolId,@Param("stateId")Long stateId,
 		   @Param("assessmentPrgId") Long assessmentPrgId, @Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId,
 		   @Param("sidx") String sortByColumn, @Param("sord") String sortType,
 		   @Param("scorerTestSessionRecordCriteriaMap") Map<String, String> scorerTestSessionRecordCriteriaMap,@Param("distictId") Long distictId,@Param("currentSchoolYear") int currentSchoolYear, 
 		   @Param("assessmentProgramCode") String assessmentProgramCode);
	
	List<ScoreTestTestSessionDTO> getTestSessionAndStudentCountForMonitorScorer(@Param("districtId")Long districtId,@Param("schoolId")Long schoolId,
		   @Param("stateId")Long stateId, @Param("assessmentPrgId") Long assessmentPrgId, @Param("subjectId") Long subjectId, @Param("gradeId") Long gradeId,
 		   @Param("sidx") String sortByColumn, @Param("sord") String sortType,
 		   @Param("offset") Integer i, 
			   @Param("limit") Integer limitCount,
 		  @Param("scorerTestSessionRecordCriteriaMap") Map<String, String> scorerTestSessionRecordCriteriaMap,@Param("distictId") Long distictId,@Param("currentSchoolYear") int currentSchoolYear,
 		  @Param("assessmentProgramCode") String assessmentProgramCode);
	List<ScoreTestSelfToHarmDTO> getScoresHarmToSelfDetails(@Param("nonscorerid")Long nonscorerid,@Param("orgId") Long orgId, @Param("sortByColumn") String sortByColumn, @Param("sortType") String sortType, @Param("monitorSelfToHarmFilters") Map<String,String> monitorSelfToHarmFilters, @Param("offset") int offset,
				@Param("limit") int limitCount,  @Param("currentSchoolYear") int currentSchoolYear, @Param("assessmentPrgId") Long assessmentPrgId);
	List<ScorerTestStudentsSessionDTO> getMonitorCCQStudentDetails(@Param("studentIds")List<Long> studentIds);
	/* sudhansu.b
	 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
	 */	
	ScoringAssignment getByTestSessionAndRoster(@Param("testSessionId") Long testSessionId,
			@Param("rosterId") Long rosterId);

	List<ScoringAssignment> getAssignmentsByStudentsTestId(@Param("studentsTestId") Long studentsTestId);	
	List<ScorerTestStudentsSessionDTO> getscorerTestStudentsSessionDTOForDynamicColumn(@Param("scoringAssignmentId") Long scoringAssignmentId,
			@Param("testsessionId") Long testsessionId,
			@Param("currentSchoolYear") int currentSchoolYear,
			@Param("scorerId") Long scorerId,  @Param("isScoreAllTest") boolean isScoreAllTest);
	
	List<ScorerStudentResourcesDTO> getScorerStudentResources(Long testSessionId);

	List<ContentArea> getUploadScoreSubject(@Param("assessmentPrgId")Long assessmentProgramId, 
            @Param("scorerId") Long scorerId,
            @Param("isScoreAllTest") boolean isScoreAllTest,
            @Param("schoolIds") Long[] schoolId);

	List<GradeCourse> getUploadScoresGradeBySubjectId(@Param("contentAreaId") Long contentAreaId,
			@Param("scorerId") Long scorerId, 
			@Param("isScoreAllTest") boolean isScoreAllTest, 
			@Param("schoolIds") Long[] schoolId,
			@Param("currentSchoolYear") int currentSchoolYear);

	List<ScorerTestStudentsSessionDTO> getScoringAssignmentsMappedStudentsToScore(
			@Param("districtId") Long districtId, 
			@Param("schoolIds") Long[] schoolIds, 
			@Param("testSessionIds") Long[] testSessionIds,
			@Param("contentAreaId") Long contentAreaId, 
			@Param("gradeId") Long gradeId, 
			@Param("stageId") Long stageId, 
			@Param("currentSchoolYear") Long currentSchoolYear,
			@Param("includeItem") boolean includeItem,
			@Param("isScoreAllTest") boolean isScoreAllTest,
			@Param("scorerId") Long scorerId);
			
		List<ScoringUploadDto> getAssignmentsForUpload(@Param("assignmentName") String assignmentName,
			@Param("scoreAllpermission") boolean scoreAllpermission,
			@Param("scorerId") Long scorerId);

	ScoringAssignment getByAssessmentNameAndStudentAndEducator(
			@Param("assignmentName") String assignmentName, 
			@Param("stateStudentIdentifier") String stateStudentIdentifier,
			@Param("educatorIdentifier")  String educatorIdentifier);		

	Long getCountTestSessionIds(@Param("testSessionIds") Long[] testSessionIds);

	Long getCountStageIds(@Param("testSessionIds") Long[] testSessionIds);

	

	List<Student> getMappedstudentForScorers(@Param("studentIdList") List<Long> studentIdList,
			                              @Param("scorerIds") List<Long> scorerIds);

	Integer getNoOfUnScoredItemsByStudentTest(@Param("studentsTestId") Long studentsTestId);

	List<GradeCourse> getGradeCourseByContentAreaIdForAssignScorers(
			@Param("assessmentProgramId") Long assessmentProgramId,@Param("schoolAreaId") Long schoolAreaId, @Param("contentAreaId") Long contentAreaId,
			@Param("currentSchoolYear") int currentSchoolYear);
	
	List<ScorerTestStudentsSessionDTO> getStudentTestMonitorScore (
			@Param("assessmentProgramId")Long assessmentProgram,
			@Param("schoolId") Long school,
			@Param("contentAreaId") Long contentArea,
			@Param("gradeId") Long grade,
			@Param("stageId") Long stage,
			@Param("currentSchoolYear") int currentSchoolYear);
}