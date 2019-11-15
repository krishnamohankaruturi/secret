/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.OrgAssessmentProgram;

/**
 * @author neil.howerton
 *
 */
public interface OrgAssessmentProgramService {

    List<OrgAssessmentProgram> getAll();

    OrgAssessmentProgram findById(@Param("orgAssessmentProgramId")long orgAssessmentProgramId);

    List<OrgAssessmentProgram> findByOrganizationId(long organizationId);
    
    List<OrgAssessmentProgram> findByContractingOrganizationId(long userOrganizationId);

    List<OrgAssessmentProgram> findByAssessmentProgramId(long assessmentProgramId);
    
    void deleteByPrimaryKey(long id);
    
    OrgAssessmentProgram insert(OrgAssessmentProgram orgAssessmentProgram);
    
    List<OrgAssessmentProgram> getAllWithAssociations();
    
    OrgAssessmentProgram findByOrganizationAndAssessmentProgram(long organizationId, long assessmentProgramId);
    
    List<OrgAssessmentProgram> selectAllAssessmentPrograms(long userId);
    
    List<OrgAssessmentProgram> findByUserIdAndOrganizationId(long userId,long organizationId, Long userCurrentOrganizaitonId, Long userCurrentGroupsId);
    
    List<OrgAssessmentProgram> getByUserId(long userId);

	List<OrgAssessmentProgram> findStatesByAssessmentProgramId(long assessmentProgramId);
	
	List<OrgAssessmentProgram> findByOrganizationIds(List<Long> organizationIds);

	List<OrgAssessmentProgram> getExtractReportAssessmentPrograms(Long userId, Long contractOrgId,
			long currentOrganizationId, long currentGroupsId, Long currentAssessmentProgramId, String permissionCode);

	Integer updateIfExist(OrgAssessmentProgram orgAssess);
}


