/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.DashboardMessage;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecordDTO;
import edu.ku.cete.domain.testerror.TestAssignmentErrorRecord;
import edu.ku.cete.model.DashboardMessageMapper;
import edu.ku.cete.service.DashboardMessageService;

/**
 * @author Kiran Reddy Taduru
 *
 * Aug 29, 2018 12:16:24 PM
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class DashboardMessageServiceImpl implements DashboardMessageService {

	@Autowired
	private DashboardMessageMapper dashboardMessageMapper;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int insertDashboardMessage(DashboardMessage dashboardMessage) {
		return dashboardMessageMapper.insertSelective(dashboardMessage);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardMessage> getMessagesByStudentIdAssmntProgramIdSchYr(Long studentId, Long assessmentProgramId,
			Long schoolYear, Long contentAreaId, Long gradeBandId) {
		return dashboardMessageMapper.getMessagesByStudentIdAssmntProgramIdSchYr(studentId, assessmentProgramId, schoolYear, contentAreaId, gradeBandId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int updateDashboardMessages(DashboardMessage record) {
		return dashboardMessageMapper.updateMessages(record);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int inactivateDashboardMessages(DashboardMessage record) {
		return dashboardMessageMapper.inactivateMessages(record);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DashboardMessage> getDashboardMessages(DashboardMessage record) {
		return dashboardMessageMapper.getDashboardMessages(record);
	}
	
	/**
	 * 
	 * Changes for F845 Test Assignment Errors Dashboard
	 */
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestAssignmentErrorRecord> getTestAssignmentErrors(Map<String, Object> criteria, String sortByColumn, String sortType,
			int offset, Integer limitCount) {
		criteria.put("limit", limitCount);
		criteria.put("offset", (offset < 0) ? 0 : offset);
		criteria.put("sortByColumn", sortByColumn);
		criteria.put("sortType", sortType);
		return dashboardMessageMapper.getTestAssignmentErrors(criteria);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer getCountOfTestAssignmentErrors(Map<String, Object> criteria) {
		return dashboardMessageMapper.getCountOfTestAssignmentErrors(criteria);
	}

	/**
	 * To get Download Messages
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<TestAssignmentErrorRecordDTO> getAllTestAssignmentErrorMessages(Map<String, Object> criteria) {
		return dashboardMessageMapper.getAllTestAssignmentErrorMessages(criteria);
	}
	
	
	
}
