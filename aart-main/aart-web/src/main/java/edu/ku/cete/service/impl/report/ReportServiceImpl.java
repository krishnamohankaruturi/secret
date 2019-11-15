package edu.ku.cete.service.impl.report;



import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.common.Category;

import edu.ku.cete.domain.professionaldevelopment.ModuleReport;

import edu.ku.cete.domain.report.ExtractAssessmentProgramGroup;

import edu.ku.cete.domain.report.ReportAssessmentProgram;
import edu.ku.cete.domain.report.ReportAssessmentProgramGroups;
import edu.ku.cete.model.ReportAssessmentProgramGroupsDao;
import edu.ku.cete.model.ReportAssessmentProgramMapper;
import edu.ku.cete.model.common.CategoryDao;

import edu.ku.cete.model.professionaldevelopment.ModuleReportMapper;
import edu.ku.cete.service.report.ReportService;
import edu.ku.cete.web.ReportAccessDTO;
//import edu.ku.cete.domain.professionaldevelopment.ModuleReport;


@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private CategoryDao reportTypeMapper;
	
	@Autowired
	private ReportAssessmentProgramGroupsDao reportAssessmentProgramGroupDao;
	
	@Autowired
	private ReportAssessmentProgramMapper reportAssessmentProgramMapper;
	

	@Autowired
	private ModuleReportMapper moduleReportDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Category> getReports() {
		return reportTypeMapper.getReportNames();
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<ReportAssessmentProgram> getReadyToViewFlagsForReports(Long userCurrentRoleId,Long orgId,String orgType){
		return reportAssessmentProgramMapper.getReadyToViewFlagsForReports(userCurrentRoleId,orgId,orgType);
	}
	
	@Override

	public List<ReportAccessDTO> getReportAccessData(Long assessmentPrgId, List<Long> stateId,
			Map<String, String> testSessionRecordCriteriaMap, String sortType, String sortByColumn,
			List<String> toRemoveStudentArchiveReport, List<String> reportCode) {
		return reportTypeMapper.getReportAccessData(assessmentPrgId,stateId,testSessionRecordCriteriaMap,sortType,sortByColumn, toRemoveStudentArchiveReport, reportCode);
	}
		
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean getEditReportAccessData(Long reportAssessmentProgramId, Long[] rolesId) {

		List<ReportAssessmentProgramGroups> reportAssessmentGroupList = reportAssessmentProgramGroupDao
				.getReportAssessmentGroupSelect(reportAssessmentProgramId);

		if (reportAssessmentGroupList.size() > 0) {
			for (ReportAssessmentProgramGroups reportAssessmentProgramGroups : reportAssessmentGroupList) {
				if (!(ArrayUtils.contains(rolesId, reportAssessmentProgramGroups.getGroupId())
						&& reportAssessmentProgramGroups.getActiveFlag())) {
					// unselect Groups set false
					reportAssessmentProgramGroups.setActiveFlag(false);
					reportAssessmentProgramGroupDao.updateActiveFlag(reportAssessmentProgramGroups);
				}
			}

			for (ReportAssessmentProgramGroups reportAssessmentProgramGroups : reportAssessmentGroupList) {
				if (rolesId != null) {
					for (Long roleId : rolesId) {
						if (reportAssessmentProgramGroups.getGroupId().longValue() == roleId) {
							if (!reportAssessmentProgramGroups.getActiveFlag()) {
								reportAssessmentProgramGroups.setActiveFlag(true);
								reportAssessmentProgramGroupDao.updateActiveFlag(reportAssessmentProgramGroups);
							}

							rolesId = (Long[]) ArrayUtils.removeElement(rolesId, roleId);

						}

					}
				}

			}

			if (rolesId != null && rolesId.length > 0) {
				reportAssessmentProgramGroupDao.insertMultipleAssessmentProgramGroup(reportAssessmentProgramId,
						rolesId);
			}
		} else {
			if (rolesId != null && rolesId.length > 0) {
				reportAssessmentProgramGroupDao.insertMultipleAssessmentProgramGroup(reportAssessmentProgramId,
						rolesId);
			}
		}
		
		
	
		return true;
	}
	

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Map<Long, String> getAllGroupsSelectedPermission(
			Long groupAuthoirityId,
			Long assessmentProgId,
			Long stateId) {

		Map<Long,String> groupListMap= new LinkedHashMap<Long,String>();
		List<Groups> groupsList = reportAssessmentProgramGroupDao.getAllGroupsSelectedPermission(groupAuthoirityId,assessmentProgId,stateId);
		for (Groups groups : groupsList) {
			groupListMap.put(groups.getGroupId(), groups.getGroupName());
		}		
		return groupListMap;
	}

	@Override
	public List<Long> getGroupNameSelectedPermission(
			Long reportassessmentgroupid) {

		List<Long> reportAssessmentProgramId = reportAssessmentProgramGroupDao.getByReportAssessmentProgId(reportassessmentgroupid);
		
		return reportAssessmentProgramId;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean editReportAccessDataForMultipleState(String[] editReportAccessIds, Long groupId, Boolean activeFlag) {
		
		for(String editReportAccessId : editReportAccessIds) {
			Long reportAssessmentPgmGroupId = reportAssessmentProgramGroupDao.getReportAssessmentForGroupCode(Long.parseLong(editReportAccessId), groupId);	
			if(reportAssessmentPgmGroupId != null ) {				
				ReportAssessmentProgramGroups reportAssessmentProgramGroups = new ReportAssessmentProgramGroups();				
				reportAssessmentProgramGroups.setActiveFlag(activeFlag);
				reportAssessmentProgramGroups.setReportAssessmentProgramId(Long.parseLong(editReportAccessId));
				reportAssessmentProgramGroups.setGroupId(groupId);
				reportAssessmentProgramGroups.setId(reportAssessmentPgmGroupId);
				reportAssessmentProgramGroupDao.updateActiveFlag(reportAssessmentProgramGroups);
			} else {
				reportAssessmentProgramGroupDao.insertAssessmentProgramGroup(Long.parseLong(editReportAccessId), groupId);				
			}
		}
		
		return true;
	}

	@Override
	public List<ReportAccessDTO> getExtractAccessData(Long assessmentPrgId, List<Long> stateId,
			Map<String, String> testSessionRecordCriteriaMap, String sortType, String sortByColumn) {
		return reportTypeMapper.getExtractAccessData(assessmentPrgId,stateId,testSessionRecordCriteriaMap,sortType,sortByColumn);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public boolean editExtractAccessDataForMultipleState(String[] editReportAccessIds, Long groupId,
			Boolean activeFlag) {
		for(String editReportAccessId : editReportAccessIds) {
			Long reportAssessmentPgmGroupId = reportAssessmentProgramGroupDao.getExtractAssessmentForGroupCode(Long.parseLong(editReportAccessId), groupId);	
			if(reportAssessmentPgmGroupId != null ) {				
				ExtractAssessmentProgramGroup extracttAssessmentProgramGroups = new ExtractAssessmentProgramGroup();				
				extracttAssessmentProgramGroups.setActiveFlag(activeFlag);
				extracttAssessmentProgramGroups.setReportAssessmentProgramId(Long.parseLong(editReportAccessId));
				extracttAssessmentProgramGroups.setGroupId(groupId);
				extracttAssessmentProgramGroups.setId(reportAssessmentPgmGroupId);
				reportAssessmentProgramGroupDao.updateActiveFlagForExtractAccess(extracttAssessmentProgramGroups);
			} else {
				reportAssessmentProgramGroupDao.insertExtractAssessmentProgramGroup(Long.parseLong(editReportAccessId), groupId);				
			}
		}
		
		return true;
	}

	@Override
	public List<Long> getDlmExtractAccessId(Long currentGroupId, Long currentStateId, Long currentAssessmentPgmId) {
		Long stateId = reportTypeMapper.getParentStateIdByChildId(currentStateId);
		if(stateId == null) {
			return reportTypeMapper.getDlmExtractAccessId(currentGroupId, currentStateId, currentAssessmentPgmId);			
		} else {
			return reportTypeMapper.getDlmExtractAccessId(currentGroupId, stateId, currentAssessmentPgmId);					
		}
	}

	@Override
	public String getReportAccessPermissionForReports(Long currentGroupId, Long currentStateId, Long currentAssessmentPgmId, String categoryCode) {
		Long stateId = reportTypeMapper.getParentStateIdByChildId(currentStateId);
		if(stateId == null) {
			return reportTypeMapper.getReportAccessPermissionForReports(currentGroupId, currentStateId, currentAssessmentPgmId, categoryCode);			
		} else {
			return reportTypeMapper.getReportAccessPermissionForReports(currentGroupId, stateId, currentAssessmentPgmId, categoryCode);					
		}
	}

	

}
