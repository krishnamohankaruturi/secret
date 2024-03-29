package edu.ku.cete.model.professionaldevelopment;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.professionaldevelopment.DataDetailDto;
import edu.ku.cete.domain.professionaldevelopment.ModuleReport;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.web.AccessibilityExtractDTO;
import edu.ku.cete.web.DLMPDTrainingDTO;
import edu.ku.cete.web.PDTrainingDetailsReportDTO;
import edu.ku.cete.web.PDTrainingStatusReportDTO;
import edu.ku.cete.web.QuestarExtractDTO;

public interface ModuleReportMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulereport
	 * @mbggenerated  Wed Nov 26 16:52:40 CST 2014
	 */
	int insert(ModuleReport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulereport
	 * @mbggenerated  Wed Nov 26 16:52:40 CST 2014
	 */
	int insertSelective(ModuleReport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulereport
	 * @mbggenerated  Wed Nov 26 16:52:40 CST 2014
	 */
	ModuleReport selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulereport
	 * @mbggenerated  Wed Nov 26 16:52:40 CST 2014
	 */
	int updateByPrimaryKeySelective(ModuleReport record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table modulereport
	 * @mbggenerated  Wed Nov 26 16:52:40 CST 2014
	 */
	int updateByPrimaryKey(ModuleReport record);

	List<ModuleReport> getAdminModuleReports(@Param("organizationId") Long organizationId,
			@Param("isStateAdmin") Boolean isStateAdmin, 
			@Param("isDistrictAdmin") Boolean isDistrictAdmin, 
			@Param("groupId") Long groupId,
			@Param("userId") Long userId, 
			@Param("typeIds") List<Short> typeIds);
	
	int countAdminModuleReports(@Param("organizationId") Long organizationId,
			@Param("isStateAdmin") Boolean isStateAdmin, 
			@Param("isDistrictAdmin") Boolean isDistrictAdmin,
			@Param("groupId") Long groupId,
			@Param("userId") Long userId, 
			@Param("typeIds") List<Short> typeIds);
	
	List<PDTrainingStatusReportDTO> generatePDTrainingStatusReportItems(
    		@Param("stateId") Long stateId, @Param("districtId") Long districtId);
	
	List<PDTrainingDetailsReportDTO> generatePDTrainingDetailsReportItems(
    		@Param("stateId") Long stateId, @Param("districtId") Long districtId);
	
	List<DLMPDTrainingDTO> generateDLMPDTrainingListItems(
    		@Param("currentSchoolYear") Long currentSchoolYear,@Param("organizationIds") List<Long> organizations,@Param("includeInternalUsers") boolean includeInternalUsers);
	
	List<DLMPDTrainingDTO> generateDLMPDTrainingManagementListItems(
    		@Param("currentSchoolYear") Long currentSchoolYear,@Param("organizationIds") List<Long> organizations,@Param("includeInternalUsers") boolean includeInternalUsers);

	 
    List<ModuleReport> getReportsForUserByTypes(@Param("userId") Long userId, @Param("organizationId") Long organizationId,
    		@Param("typeIds") List<Short> typeIds, @Param("isTeacher") Boolean isTeacher);
    
    List<ModuleReport> getReportsByTypeForExitOrSC(@Param("organizationId") Long organizationId,
    		@Param("typeIds") List<Short> typeIds);
    
    List<DataDetailDto> getReportsForUserByTypesWithDataDictionaryDetail( @Param("organizationId") Long organizationId,
    		@Param("typeIds") List<Short> typeIds,  @Param("assessmentprogramid") Long assessmentprogramid, @Param("organizationid") Long organizationid);

	int countReportsForUserByTypes(@Param("userId") Long userId, @Param("organizationId") Long organizationId,
			@Param("typeIds") List<Short> typeIds, @Param("isTeacher") Boolean isTeacher);
	
	List<AccessibilityExtractDTO> getAccessibilityReportByStudentIds(@Param("studentIds") List<Long> studentIds,
			@Param("currentSchoolYear") int currentSchoolYear, @Param("assessmentPrograms") List<Long> assessmentPrograms);
	
	List<QuestarExtractDTO> getQuestarDataForOrg(@Param("organizationId") Long organizationId,
			@Param("isTeacher") Boolean isTeacher, @Param("educatorId") Long educatorId, @Param("currentSchoolYear") int currentSchoolYear,@Param("assessmentPrograms") List<Long> assessmentPrograms);
	
	int checkKAPExtract(@Param("organizationId") Long organizationId);
	
	ModuleReport getMostRecentReportByTypeId(@Param("userId") Long userId, @Param("typeId") short typeId, @Param("organizationId") Long organizationId);
	
	ModuleReport getMostRecentCompletedReportByTypeId(@Param("userId") Long userId, @Param("typeId") short typeId);
	
	public ModuleReport getMostRecentGRFExtractReportByTypeId(@Param("reporttype") short reporttype, @Param("organizationId") Long organizationId);
	
	/**
	 * US16739 : Kiran Reddy Taduru : 09/04/2015
	 * Select inactive extract file names to delete 
	 */
	List<ModuleReport> selectModuleReportsForDeletion();
	
	
	/**
	 * US16739 : Kiran Reddy Taduru : 09/04/2015
	 * Update deleteflag to true after the deletion of extract from server
	 * @param moduleReportId
	 * @return
	 */
	int updateModuleReportDeleteFlag(@Param("id") Long moduleReportId);
	
	ModuleReport updateAndGetQueuedModuleReport(@Param("inqueueStatusId") Long inqueueStatusId, @Param("inprogressStatusId") Long inprogressStatusId);

	List<ModuleReport> getQueuedReports(@Param("inQueueStatusId") Long inQueueStatusId,
			@Param("inProgressStatusId") Long inProgressStatusId, @Param("organizationCode") String organizationCode);

	int moveReportToInProgress(@Param("inQueueStatusId") Long inQueueStatusId,
			@Param("inProgressStatusId") Long inProgressStatusId, @Param("reportId") Long reportId);
}

