/**
 * 
 */
package edu.ku.cete.model;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Authorities;

/**
 * @author neil.howerton
 *
 */
public interface AuthoritiesDao {
	public void addAuthorities(Authorities authorities);

	public void updateAuthorities(Authorities authorities);

	public void deleteAuthorities(long authoritiesId);

	public Authorities getAuthorities(long authoritiesId);

	public List<Authorities> getByUserAndGroupExcludeLockdown(@Param("userId") Long userId,
			@Param("groupId") Long groupId, @Param("organizationId") Long organizationId,
			@Param("stateId") Long stateId, @Param("assessmentProgramId") Long AssessmentProgramId);

	public List<Authorities> getByGroupId(@Param("groupId") Long groupId);

	public List<Authorities> getAll();

	public List<Authorities> getByCombinedStateAssessmentProgram(@Param("groupId") Long groupId,
			@Param("organizationId") Long organizationId, @Param("assessmentProgramId") Long assessmentProgramId);

	public List<Authorities> getGroupExcludeLockdownForGlobalAdmin(@Param("userId") Long userId,
			@Param("groupId") Long groupId, @Param("organizationId") Long organizationId);

	public Authorities getByAuthority(@Param("authority") String authority);

	public List<Authorities> getConflictingCount(@Param("organizationIdA") Long organizationIdA,
			@Param("assesmentProgramIdA") Long assesmentProgramId, @Param("organizationIdB") Long organizationIdB,
			@Param("assesmentProgramIdB") Long assesmentProgramIdB, @Param("groupId") Long groupId);

	public List<Authorities> getByCombinedStatesAssesmentPrograms(@Param("groupId")Long groupId,@Param("orgIdArray") Long[] orgIdArray,
			@Param("assessmentProgramIdArray")Long[] assessmentProgramIdArray);

	public List<String> getTabNames();

	public Authorities getByDisplayName(@Param("displayName") String displayName);

	public Boolean checkTabIsAvailable(@Param("tabName") String tabName);
	
	public Boolean checkGroupingIsAvailable(@Param("groupingName") String groupingName);
	
	public Boolean checkLabelIsAvailable(@Param("labelName") String labelName);
}
