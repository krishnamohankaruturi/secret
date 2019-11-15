/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.DashboardMessage;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecordDTO;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecord;

/**
 * @author Kiran Reddy Taduru
 *
 * Aug 29, 2018 12:12:55 PM
 */
public interface DashboardMessageService {

	int insertDashboardMessage(DashboardMessage record);
	
	List<DashboardMessage> getDashboardMessages(DashboardMessage record);
	
	List<DashboardMessage> getMessagesByStudentIdAssmntProgramIdSchYr(Long studentId, Long assessmentProgramId, Long schoolYear, Long contentAreaId, Long gradeBandId);
	
	int updateDashboardMessages(DashboardMessage record);
	
	int inactivateDashboardMessages(DashboardMessage record);
	
	/**
	 * 
	 * Changes for F845 Test Assignment Errors Dashboard
	 * 
	 */
	
	List<TestAssignmentErrorRecord> getTestAssignmentErrors(Map<String, Object> criteria, String sortByColumn, String sortType,	int offset, Integer limitCount);
	
	Integer getCountOfTestAssignmentErrors(Map<String, Object> criteria);
	
	List<TestAssignmentErrorRecordDTO> getAllTestAssignmentErrorMessages(Map<String, Object> criteria);
	
}
