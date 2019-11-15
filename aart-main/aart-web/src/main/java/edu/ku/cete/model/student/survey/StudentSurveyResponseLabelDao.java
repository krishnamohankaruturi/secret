package edu.ku.cete.model.student.survey;

import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabel;
import edu.ku.cete.domain.student.survey.StudentSurveyResponseLabelInfo;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StudentSurveyResponseLabelDao {

    /**
     * Find both selected and un selected responses for the given survey
     * @param example
     * @return
     */
    List<StudentSurveyResponseLabel> selectSurveyResult(@Param("surveyId")Long surveyId);
  
    /**
     * Selects all the labels and responses for a student along with labels and responsevalues.
     * @param studentId
     * @return
     */
    List<StudentSurveyResponseLabel> selectExistingStudentSurveyResponseLabels(
    		@Param("surveyId") Long surveyId,
    		@Param("labelNumber") String labelNumber);
    
    /**
     * Selects all the labels and responses for a student along with labels and responsevalues.
     * @param studentId
     * @return
     */
    List<StudentSurveyResponseLabelInfo> selectAllowedStudentSurveyResponseLabels(
    		@Param("surveyId") Long surveyId,
    		@Param("globalPageNum") Integer globalPageNum);
    
}