package edu.ku.cete.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.ku.cete.domain.ScoringAssignment;
import edu.ku.cete.domain.ScoringAssignmentStudent;
import edu.ku.cete.domain.ScoringUploadFile;
import edu.ku.cete.domain.ScoringUploadDto;
import edu.ku.cete.domain.StudentsTestScore;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.TestSession;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.web.AssignScorerStudentScorerCriteriaDTO;
import edu.ku.cete.web.ScoreTestScoringCriteriaDTO;
import edu.ku.cete.web.ScoreTestSelfToHarmDTO;
import edu.ku.cete.web.ScoreTestTestSessionDTO;
import edu.ku.cete.web.ScorerStudentResourcesDTO;
import edu.ku.cete.web.ScorerTestStudentsSessionDTO;

public interface ScoringAssignmentServices {

	ScoringAssignment addOrUpdate(ScoringAssignment scoringAssignment);

	Integer getCountTestSessionAndStudentCountForScorer(Long userId, Long stateId, Long assessmentPrgId, Long subjectId,
			Long gradeId, String sortByColumn, String sortType, Map<String, String> scorerTestSessionRecordCriteriaMap,
			boolean isScoreAllTest);

	List<ScoreTestTestSessionDTO> getTestSessionAndStudentCountForScorer(Long userId, Long stateId,
			Long assessmentPrgId, Long subjectId, Long gradeId, String sortByColumn, String sortType, int i,
			int limitCount, Map<String, String> scorerTestSessionRecordCriteriaMap, boolean isScoreAllTest,
			String assessmentProgramCode, Long schoolYear);

	List<ContentArea> findByContentArea(Long assessmentProgramId, Long schoolId, int currentSchoolYear);

	List<GradeCourse> getScoreTestGradeCourseByContentAreaId(Long contentAreaId, int currentSchoolYear,
			Long assessmentProgramId, Long schoolId);

	List<ScorerTestStudentsSessionDTO> getScorerStudentsAppearsForScoreTests(Long scoringAssignmentId,
			Long assessmentProgramId, Long scorerId, int currentSchoolYear, boolean isScoreAllTest);

	Integer getScorerStudentsAppearsForScoreTestsCount(Long scoringAssignmentId, Long assessmentProgramId,
			Long scorerId, Map<String, String> scorerTestStudentRecordCriteriaMap, int currentSchoolYear,
			boolean isScoreAllTest);

	List<ScoreTestScoringCriteriaDTO> getStudentTestScoringCriteria(Long testSessionId, Long studentId, Long testsId,
			Long variantValue);

	List<AssignScorerStudentScorerCriteriaDTO> checkAssignScoringStudentScorer(Long testSessionId, Long[] studentIds,
			Long[] scorerIds);

	Integer checkUniqueCcqTestName(String testName);

	Integer getCountTestSessionAndStudentCountForScorer(Long districtId, Long schoolId, Long stateId,
			Long assessmentPrgId, Long subjectId, Long gradeId, String sortByColumn, String sortType,
			Map<String, String> scorerTestSessionRecordCriteriaMap, Long distictId, int currentSchoolYear,
			String assessmentProgramCode);

	List<ScoreTestTestSessionDTO> getTestSessionAndStudentCountForScorer(Long districtId, Long schoolId, Long stateId,
			Long assessmentPrgId, Long subjectId, Long gradeId, String sortByColumn, String sortType, int i,
			int limitCount, Map<String, String> scorerTestSessionRecordCriteriaMap, Long distictId,
			int currentSchoolYear, String assessmentProgramCode);

	List<ScoreTestSelfToHarmDTO> getScoresHarmToSelfDetails(Long nonscorerid, Long orgId, String sortByColumn,
			String sortType, Map<String, String> monitorSelfToHarmFilters, int i, int limitCount, int currentSchoolYear,
			Long assessmentPrgId);

	List<ScorerTestStudentsSessionDTO> getMonitorCCQStudentDetails(List<Long> studentId);

	List<ScorerTestStudentsSessionDTO> getscorerTestStudentsSessionDTOForDynamicColumn(Long scoringAssignmentId,
			Long testsessionId, Integer currentSchoolYear, Long scorerId, boolean isScoreAllTest);

	List<ScorerStudentResourcesDTO> getScorerStudentResources(Long testSessionId);

	ScoringAssignment getByTestSessionAndRoster(Long testSessionId, Long rosterId);

	void reAssignStudentsOnChangeRoster(Long studentsTestId, Long oldRosterId, Long newRosterId, User modifiedUser);

	void reAssignStudentsOnExitStudent(Long studentsTestId, Date exitDate, User modifiedUser);

	List<Long> getStudentsListByScoringAssignmentId(Long scoringassignmentId);

	String getMonitorStageDetails(Long prevScoringAssignmentScorerId);

	List<Stage> getUploadScoresStage(Long[] schoolId, Long subjectId, Long gradeId);

	List<TestSession> getUploadScoresTestSessions(Long assessmentProgramId, String assessmentCode, Long subjectId,
			Long gradeId, Long stageId, Long[] schoolIds, Long scorerId, boolean isScoreAllTest,
			Long currentSchoolYear);

	List<ContentArea> getUploadScoreSubject(Long assessmentProgramId, Long scorerId, boolean isScoreAllTest,
			Long[] schoolId);

	List<GradeCourse> getUploadScoresGradeBySubjectId(Long subjectId, Long scorerId, boolean isScoreAllTest,
			Long[] schoolId, int currentSchoolYear);

	String getScoringAssignmentStudentsList(Long districtId, Long[] schoolIds, Long[] testSessionIds,
			Long contentAreaId, Long gradeId, Long stageId, Long currentSchoolYear, boolean includeItem)
			throws IOException;

	ScoringUploadFile getScoringAssignmentUploadFile(String fileName);

	List<Stage> getStageByAssessmentProgram(Long assessmentProgramId);

	List<TestSession> getTestSessionsByScorerAssessmentProgram(Long assessmentProgramId);

	List<ScoringUploadDto> getAssignmentsForUpload(String assignmentName, boolean scoreAllpermission, Long scorerId);

	ScoringAssignment getByAssessmentNameAndStudentAndEducator(String assignmentName, String stateStudentIdentifier,
			String educatorIdentifier);

	List<Student> getMappedstudentForScorers(List<Long> studentIdList, List<Long> scorerIds);

	Integer getNoOfScoredItemsByStudentTest(Long studentsTestId);

	void setKelpaScoringStatus(Long studentsTestId, Long completedCategoryCode, Long modifiedUserId);

	void updateKelpaScoringStatus(List<Long> studentIdList, Long modifiedUser);

	void createScoringTestMetaData(Set<Long> testIds);

	void soreTest(List<StudentsTestScore> studentsTestScoreList);

	void calculateAndSetScoringStatus(Long studentsTestId, Long testId, Long modifiedUserId);

	ScoringAssignmentStudent getStudentByNameAndStudentIdentifier(String assignmentName, String stateStudentId);

	List<ScorerTestStudentsSessionDTO> getStudentTestMonitorScore(Long assessmentProgram, Long school, Long contentArea,
			Long grade, Long stage, int currentSchoolYear);

	List<Stage> getStageByGradeIdForAssignScorers(Long assessmentProgramId, Long schoolId, Long contentAreaId,
			Long gradeId, int currentSchoolYear, String assessmentCode);

	List<TestSession> getAssignScoresTestSessions(Long assessmentPrgId, Long schoolId, Long stageId, Long subjectId,
			Long gradeId, int currentSchoolYear, String assessmentProgramCode);

	List<GradeCourse> getGradeCourseByContentAreaIdForAssignScorers(Long assessmentProgramId, Long schoolAreaId,
			Long contentAreaId, int currentSchoolYear);

	void removeStudentsTestScore(List<StudentsTestScore> removedStudentsScores);

	void reassignStudentsOnRosterChangeKapSS(Long oldEnrollmentId, Long newEnrollmentId, Long newRosterId);

	void deleteScoringAssignment(StudentsTests studentsTest, User modifiedUser);

}
