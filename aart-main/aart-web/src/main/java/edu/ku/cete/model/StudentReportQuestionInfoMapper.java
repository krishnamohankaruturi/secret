package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.report.StudentReportQuestionInfo;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 5, 2017 12:39:24 PM
 */
public interface StudentReportQuestionInfoMapper {
    
    int insert(StudentReportQuestionInfo record);
    
    int insertSelective(StudentReportQuestionInfo record);
    
    StudentReportQuestionInfo selectByPrimaryKey(Long id);
    
    int updateByPrimaryKeySelective(StudentReportQuestionInfo record);
    
    int updateByPrimaryKey(StudentReportQuestionInfo record);
    
    int deleteStudentReportQuestionInfo(@Param("assessmentProgramId")Long assessmentProgramId,
    		@Param("reportCycle")String reportCycle,
    		@Param("contentAreaId")Long contentAreaId, 
    		@Param("gradeId")Long gradeId, 
    		@Param("schoolYear")Long schoolYear,
    		@Param("studentId")Long studentId);

	List<StudentReportQuestionInfo> getStudentReportQuestionInfo(
			@Param("interimStudentReportId") Long interimStudentReportId);
	
	int deleteStudentReportQuestionInfoByInterimStudentReportId(@Param("interimStudentReportId")Long interimStudentReportId);
}