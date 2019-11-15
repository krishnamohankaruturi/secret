package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 5, 2017 12:40:45 PM
 */
public interface QuestionInformationMapper {
    
    int insert(QuestionInformation record);
    
    int insertSelective(QuestionInformation record);
    
    QuestionInformation selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(QuestionInformation record);
    
    int updateByPrimaryKey(QuestionInformation record);
    
    Integer deleteQuestionInformation(@Param("assessmentProgramId") Long assessmentProgramId, 
			@Param("contentAreaId") Long contentAreaId, 
			@Param("schoolYear") Long schoolYear, 
			@Param("testingProgramId") Long testingProgramId, 
			@Param("reportCycle") String reportCycle);
    
    List<QuestionInformation> getAllQuestionsInfoByTestId(@Param("assessmentProgramId")Long assessmentProgramId,
    		@Param("testingProgramId") Long testingProgramId,
    		@Param("reportCycle")String reportCycle,
    		@Param("contentAreaId")Long contentAreaId, 
    		@Param("gradeId")Long gradeId, 
    		@Param("schoolYear")Long schoolYear,
    		@Param("testId")Long testId);
    
}