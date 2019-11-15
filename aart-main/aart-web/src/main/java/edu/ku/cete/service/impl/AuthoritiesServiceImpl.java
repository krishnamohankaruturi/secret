/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.model.AuthoritiesDao;
import edu.ku.cete.model.GroupAuthoritiesExclusionMapper;
import edu.ku.cete.service.AuthoritiesService;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class AuthoritiesServiceImpl implements AuthoritiesService {

	@Autowired
	private AuthoritiesDao authoritiesDao;

	@Autowired
	private GroupAuthoritiesExclusionMapper groupAuthoritiesExclusionMapper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.AuthoritiesService#addAuthorities(edu.ku.cete.domain.
	 * Authorities)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Authorities addAuthorities(Authorities authorities) {
		authoritiesDao.addAuthorities(authorities);
		return authorities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.AuthoritiesService#updateAuthorities(edu.ku.cete.
	 * domain.Authorities)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void updateAuthorities(Authorities authorities) {
		authoritiesDao.updateAuthorities(authorities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.ku.cete.service.AuthoritiesService#deleteAuthorities(edu.ku.cete.
	 * domain.Authorities)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void deleteAuthorities(Authorities authorities) {
		deleteAuthorities(authorities.getAuthoritiesId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.AuthoritiesService#deleteAuthorities(long)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void deleteAuthorities(long authoritiesId) {
		authoritiesDao.deleteAuthorities(authoritiesId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.ku.cete.service.AuthoritiesService#getAuthorities(long)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Authorities getAuthorities(long authoritiesId) {
		return authoritiesDao.getAuthorities(authoritiesId);
	}

	@Override
	public List<Authorities> getAll() {
		return authoritiesDao.getAll();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Authorities> getByCombinedStateAssessmentProgram(Long groupId, Long organizationId,
			Long assessmentProgramId) {
		return authoritiesDao.getByCombinedStateAssessmentProgram(groupId, organizationId, assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getPermissionsToExclude(Long groupId, Long organizationId, Long assessmentProgramId) {
		return groupAuthoritiesExclusionMapper.getPermissionsToExclude(groupId, organizationId, assessmentProgramId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Boolean checkPermissionsConflict(List<Long> organizationIdList, List<Long> assesmentProgramIdList,
			Long groupId) {

		List<Authorities> a= new ArrayList< Authorities>(); 
		for (Long organizationIdA : organizationIdList) {
			for (Long assesmentProgramIdA : assesmentProgramIdList) {
				for (Long organizationIdB : organizationIdList) {
					for (Long assesmentProgramIdB : assesmentProgramIdList) {
						if (organizationIdA != organizationIdB || assesmentProgramIdA != assesmentProgramIdB) {
							a = authoritiesDao.getConflictingCount(organizationIdA, assesmentProgramIdA,
									organizationIdB, assesmentProgramIdB, groupId);
							if (a.size() > 0)
								return true;
						}

					}
				}

			}
		}

		return false;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Authorities> getByCombinedStatesAssesmentPrograms(Long groupId, List<Long> organizationIdList,
			List<Long> assessmentProgramIdList) {
		Long[]orgIdArray= new Long[organizationIdList.size()];
		Long[]assessmentProgramIdArray= new Long[assessmentProgramIdList.size()];
		organizationIdList.toArray(orgIdArray);
		assessmentProgramIdList.toArray(assessmentProgramIdArray);
		return authoritiesDao.getByCombinedStatesAssesmentPrograms(groupId,orgIdArray,assessmentProgramIdArray);
		
	}

	@Override
	public List<Long> getPermissionsToExcludeMultiple(Long groupId, List<Long> organizationIdList,
			List<Long> assessmentProgramIdList) {
		Long[]orgIdArray= new Long[organizationIdList.size()];
		Long[]assessmentProgramIdArray= new Long[assessmentProgramIdList.size()];
		organizationIdList.toArray(orgIdArray);
		assessmentProgramIdList.toArray(assessmentProgramIdArray);
		
		return groupAuthoritiesExclusionMapper.getPermissionsToExcludeMultiple(groupId, orgIdArray, assessmentProgramIdArray);
		
	}

	@Override
	public List<String> getTabNames() {
		return authoritiesDao.getTabNames();
	}

	@Override
	public Authorities getByDisplayName(String displayName) {
	
		return authoritiesDao.getByDisplayName(displayName);
	}

	@Override
	public boolean checkTabIsAvailable(String tabName) {
		return authoritiesDao.checkTabIsAvailable( tabName);
	}
	
	@Override
	public boolean checkGroupingIsAvailable( String groupingName) {
		return 	authoritiesDao.checkGroupingIsAvailable(groupingName);
	};
	
	@Override
	public boolean checkLabelIsAvailable(String labelName) {
		return 	authoritiesDao.checkLabelIsAvailable( labelName);
	};
}
