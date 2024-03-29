package edu.ku.cete.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.CcqScore;
import edu.ku.cete.web.MonitorCcqScorersDetailsDTO;
import edu.ku.cete.web.MonitorScoringExtractDTO;
import edu.ku.cete.web.ScorerTestStudentsSessionDTO;

public interface CcqScoreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ccqscoreteststudent
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    int insert(CcqScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ccqscoreteststudent
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    int insertSelective(CcqScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ccqscoreteststudent
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    CcqScore selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ccqscoreteststudent
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    int updateByPrimaryKeySelective(CcqScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ccqscoreteststudent
     *
     * @mbggenerated Thu Oct 01 12:38:09 IST 2015
     */
    int updateByPrimaryKey(CcqScore record);
    
    int getCountOfAllTestSessionStudentsToScoreForScorer(@Param("scorerId") Long scorerId);
    int getCountOfTestSessionStudentsToScoreForScorer(@Param("scoringAssignmentScorerId") Long scoringAssignmentScorerId, @Param("scoringAssignmentid") Long scoringAssignmentid);
    
    List<MonitorCcqScorersDetailsDTO> getMonitorCcqScoresDetails(
    		@Param("scoringAssignmentId") Long scoringAssignmentId,
    		@Param("currentSchoolYear") int currentSchoolYear,
			@Param("sidx") String sortByColumn,
			@Param("sord") String sortType,
			@Param("offset") Integer i, 
			@Param("limit") Integer limitCount,
			@Param("scorerTestStudentRecordCriteriaMap") Map<String,String> scorerTestStudentRecordCriteriaMap
			);

	Long isCheckScorerAndStudent(@Param("scoringAssignmentScorerId") Long scoringAsssignmentScorerId,
			@Param("scoringAssignmentStudentId") Long scoringAssignmentStudentId);

	void updateScorerAndStudent(CcqScore ccqScore);
	
	int getCountOfInprogressDomainsForStudent(@Param("scoringDomains") List<String>scoringDomains, @Param("studentId") Long studentid);

	/* sudhansu.b
	 * Added for US19233 - KELPA2 Auto Assign Teachers Scoring Assignment 
	 */	
	List<CcqScore> getKELPAScoresByAssignmentIdAndStudentTestId(
			@Param("scoringAssignmentId") Long scoringAssignmentId,
			@Param("studentsTestId") Long studentsTestId);

	List<CcqScore> updateScoreAndStatus(CcqScore ccqScore);

	List<MonitorScoringExtractDTO> getMonitorScoringExtractByOrg(@Param("orgId")Long orgId,
		 @Param("assessmentProgIds")List<Long> assessmentPrograms, @Param("currentSchoolYear") Long currentSchoolYear);

	CcqScore getByScorerAndStudent(@Param("scoringAssignmentScorerId") Long scoringAssignmentScorerId,
			                       @Param("scoringAssignmentStudentId") Long scoringAssignmentStudentId);

	CcqScore getByEducatorIdAndStateStudentId(@Param("assignmentName") String assignmentName,
			                                  @Param("educatorId") String educatorId,
			                                  @Param("stateStudentId") String stateStudentId);

	int updateCCQScoreStatus(CcqScore ccqScore);

	void updateCCQscoreStatusAfterCountingItems(@Param("ccqScoreId") Long ccqScoreId,
			                              @Param("testId") Long completedCategoryCode); 
}