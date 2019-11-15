package edu.ku.cete.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.DashboardMessage;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecord;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecordDTO;

public interface DashboardMessageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dashboardmessage
     *
     * @mbg.generated Wed Aug 29 12:02:58 CDT 2018
     */
    int insert(DashboardMessage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table dashboardmessage
     *
     * @mbg.generated Wed Aug 29 12:02:58 CDT 2018
     */
    int insertSelective(DashboardMessage record);
    
    List<DashboardMessage> getDashboardMessages(DashboardMessage record);
    
    List<DashboardMessage> getMessagesByStudentIdAssmntProgramIdSchYr(@Param("studentId") Long studentId,
    		@Param("assessmentProgramId") Long assessmentProgramId,
    		@Param("schoolYear") Long schoolYear,
    		@Param("contentAreaId") Long contentAreaId,
    		@Param("gradeBandId") Long gradeBandId);
    
    int updateMessages(DashboardMessage record);
    
    int inactivateMessages(DashboardMessage record);
    
    /**
	 * 
	 * Changes for F845 Test Assignment Errors Dashboard
	 */
    
    List<TestAssignmentErrorRecord> getTestAssignmentErrors(Map<String, Object> criteria);
	
	Integer getCountOfTestAssignmentErrors(Map<String, Object> criteria);

	List<TestAssignmentErrorRecordDTO> getAllTestAssignmentErrorMessages(Map<String, Object> criteria);
    
}