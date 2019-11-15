/**
 * 
 */
package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.OrgAssessmentProgram;

/**
 * @author neil.howerton
 *
 */
public interface OrgAssessmentProgramDao {

    List<OrgAssessmentProgram> getAll();
    
    OrgAssessmentProgram findById(@Param("orgAssessmentProgramId")long orgAssessmentProgramId);
    
    List<OrgAssessmentProgram> findByOrganizationId(long organizationId);
    List<OrgAssessmentProgram> findByContractingOrganizationId(long userOrganizationId);
    
    List<OrgAssessmentProgram> findByAssessmentProgramId(long assessmentProgramId);
    
    void deleteByPrimaryKey(@Param("id")long id);
    
    void insert(OrgAssessmentProgram orgAssessmentProgram);
    
    long lastid();
    
    List<OrgAssessmentProgram> getAllWithAssociations();
    
    List<OrgAssessmentProgram> findStatesByAssessmentProgramId(@Param("assessmentProgramId")long assessmentProgramId);    
    
    OrgAssessmentProgram findByOrganizationAndAssessmentProgram(@Param("organizationId")long organizationId, @Param("assessmentProgramId")long assessmentProgramId);
    
    List<OrgAssessmentProgram> selectAllAssessmentPrograms(@Param("aartUserId") long userId);
    
    
	List<OrgAssessmentProgram> findByUserIdAndOrganizationId(@Param("aartUserId") long userId,
			@Param("organizationId") long organizationId, @Param("currentOrganizationId") long currentOrganizationId,
			@Param("currentGroupId") Long currentGroupId);
    
    List<OrgAssessmentProgram> getByUserId(@Param("aartUserId") long userId);

	List<OrgAssessmentProgram> findByOrganizationIds(@Param("organizationIds")List<Long> organizationIds);

	List<OrgAssessmentProgram> getExtractReportAssessmentPrograms(@Param("userId") Long userId,
			@Param("contractOrgId") Long contractOrgId, @Param("currentOrganizationId") long currentOrganizationId,
			@Param("currentGroupsId") long currentGroupsId,
			@Param("currentAssessmentProgramId") Long currentAssessmentProgramId, @Param("stateId") Long stateId,
			@Param("permissionCode") String permissionCode);

	Integer updateIfExist(OrgAssessmentProgram orgAssess);
}
