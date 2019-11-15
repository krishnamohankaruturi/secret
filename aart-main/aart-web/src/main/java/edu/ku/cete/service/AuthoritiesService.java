/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.Authorities;

/**
 * @author neil.howerton
 *
 */
public interface AuthoritiesService {

    public Authorities addAuthorities(Authorities authorities);
    
    public void updateAuthorities(Authorities authorities);
    
    public void deleteAuthorities(Authorities authorities);
    
    public void deleteAuthorities(long authoritiesId);
    
    public Authorities getAuthorities(long authoritiesId);
    
	public List<Authorities> getAll();

	public List<Authorities> getByCombinedStateAssessmentProgram(Long groupId, Long organizationId,
			Long assessmentProgramId);
	
	public List<Long> getPermissionsToExclude(Long groupId, Long organizationId,
			Long assessmentProgramId);

	public Boolean checkPermissionsConflict(List<Long> organizationIdList, List<Long> assesmentProgramIdList, Long groupId);

	public List<Authorities> getByCombinedStatesAssesmentPrograms(Long groupId, List<Long> organizationIdList,
			List<Long> assessmentProgramIdList);

	public List<Long> getPermissionsToExcludeMultiple(Long groupId, List<Long> organizationIdList,
			List<Long> assessmentProgramIdList);

	public List<String> getTabNames();
	
	public Authorities getByDisplayName(String displayName);

	public boolean checkTabIsAvailable(String tabName);

	public boolean checkGroupingIsAvailable(String groupingName);

	public boolean checkLabelIsAvailable(String labelName);
	
}
