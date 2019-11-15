package edu.ku.cete.model;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.report.ExtractAssessmentProgramGroup;
import edu.ku.cete.domain.report.ReportAssessmentProgramGroups;


public interface ReportAssessmentProgramGroupsDao {
		
	int add(ReportAssessmentProgramGroups Add);
		
	int delete(@Param("id") long id);
	
	ReportAssessmentProgramGroups get(@Param("id") long id);
	
	List<ReportAssessmentProgramGroups> getAll();
	
	int update(ReportAssessmentProgramGroups Update);
	
	int updateActiveFlag(ReportAssessmentProgramGroups Update);
	
	void insertMultipleAssessmentProgramGroup(@Param("reportassessmentprogramid")Long reportAssessmentProgramId,@Param("groupids") Long[] rolesId);

	List<ReportAssessmentProgramGroups> getReportAssessmentGroupSelect(
			@Param("reportassessmentprogramid") Long reportAssessmentProgramId);

	List<Groups> getAllGroupsSelectedPermission(@Param("groupAuthoirityId") Long groupAuthoirityId,
			@Param("assessmentProgId") Long assessmentProgId,
			@Param("stateId") Long stateId);

	
	List<Long> getByReportAssessmentProgId(@Param("reportassessmentprogramid") Long reportassessmentgroupid);

	Long getReportAssessmentForGroupCode(@Param("editReportAccessId") Long editReportAccessId, @Param("groupId") Long groupId);

	void insertAssessmentProgramGroup(@Param("reportAssessmentPgmId") Long reportAssessmentPgmId, @Param("groupId") Long groupId);

	Long getExtractAssessmentForGroupCode(@Param("editReportAccessId") Long editReportAccessId, @Param("groupId") Long groupId);

	void insertExtractAssessmentProgramGroup(@Param("reportAssessmentPgmId") Long reportAssessmentPgmId, @Param("groupId") Long groupId);

	void updateActiveFlagForExtractAccess(ExtractAssessmentProgramGroup update);

	
}