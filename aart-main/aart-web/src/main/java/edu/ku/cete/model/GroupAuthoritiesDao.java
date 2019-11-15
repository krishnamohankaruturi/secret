/**
 * 
 */
package edu.ku.cete.model;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.security.GroupAuthorities;
import edu.ku.cete.web.PermissionsAndRolesDTO;

/**
 * @author neil.howerton
 *
 */
public interface GroupAuthoritiesDao {
	public void addGroupAuthorities(GroupAuthorities groupAuthorities);

	public void updateGroupAuthorities(GroupAuthorities groupAuthorities);

	public void deleteGroupAuthorities(long groupAuthoritiesId);

	public GroupAuthorities getGroupAuthorities(long groupAuthoritiesId);

	public List<GroupAuthorities> getByAuthorityId(long authorityId);

	public List<GroupAuthorities> getByGroups(@Param("groupsList") Collection<Groups> groupsList);

	public List<GroupAuthorities> getByGroupIdAndDefaultRole(@Param("groupId") long groupId,
			@Param("defaultRole") boolean defaultRole);

	public void updateDeletedGroupAuthorities(@Param("groupAuthorities") GroupAuthorities groupAuthorities);

	public List<GroupAuthorities> getByCombinedStateAssessmentProgram(@Param("groupId") long groupId,
			@Param("organizationId") Long organizationId, @Param("assessmentProgramId") Long assessmentProgramId);

	public List<GroupAuthorities> getInactiveByCombinedStateAssessmentProgram(@Param("groupId") long groupId,
			@Param("organizationId") Long organizationId, @Param("assessmentProgramId") Long assessmentProgramId);

	public void addByCombinedStateAssessmentProgram(GroupAuthorities groupAuthority);

	public Authorities getAuthorityForCsap(@Param("authority") String authority, @Param("groupId") Long groupId,
			@Param("stateId") Long stateId, @Param("assessmentProgramId") Long assessmentProgramId);

	List<GroupAuthorities> getJsonFormatData(@Param("groupId") long groupId, @Param("organizationId") Long organizationId,
			@Param("assessmentProgramId") Long assessmentProgramId);

	public List<PermissionsAndRolesDTO> getPermissionsAndRolesExtractData(@Param("stateIds") List<Long> stateIds,
			@Param("assessmentProgramIds") List<Long> assessmentProgramIds);
	
	public List<String> getAuthorityForAlternateAggregateReport(@Param("authorities") List<String> authorities, @Param("stateId") Long stateId, @Param("assessmentProgramId") Long assessmentProgramId);
	public int updatePermission ( @Param("groupId")long groupId, @Param("authorityId") long authorityId,
			@Param("activeFlag") boolean activeFlag,@Param("stateId") Long stateId, @Param("assessmentProgramId") Long assessmentProgramId,
			@Param("userId")Long userId);
	public void updateExcludedGroupAuthorities(GroupAuthorities groupAuthorities);

}
