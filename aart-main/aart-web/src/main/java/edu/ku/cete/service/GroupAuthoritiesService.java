/**
 * 
 */
package edu.ku.cete.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.security.GroupAuthorities;

/**
 * @author neil.howerton
 *
 */
public interface GroupAuthoritiesService {
    public GroupAuthorities addGroupAuthorities(GroupAuthorities groupAuthorities);
    
    public void updateGroupAuthorities(GroupAuthorities groupAuthorities);
    
    public void deleteGroupAuthorities(GroupAuthorities groupAuthorities);
    
    public void deleteGroupAuthorities(long groupAuthoritiesId);
    
    public GroupAuthorities getGroupAuthoritiesById(long groupAuthoritiesId);
    
    public List<GroupAuthorities> getByAuthorityId(long authorityId);
    
    public List<GroupAuthorities> getByGroups(Collection<Groups> groupsList);
    
    public List<GroupAuthorities> getByGroupIdAndNotDefaultRole(long groupId);
    
    public void saveGroupAuthoritiesList(long groupId, Long organizationId, Long assessmentProgramId, List<Long> authorityIds);
    
    public Authorities getAuthorityForCsap(String authority, Long groupId, Long stateId,
			Long assessmentProgramId);

	public void generatePermissionsAndRolesExtract(HttpServletResponse response, HttpServletRequest request, List<String> stateIds, List<String> assessmentprogramIds) throws IOException;

	public List<String> getAuthorityForAlternateAggregateReport(List<String> authorities,Long stateId, Long assessmentProgramId);
}
