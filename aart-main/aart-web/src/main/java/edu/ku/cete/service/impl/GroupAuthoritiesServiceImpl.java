/**
 * 
 */
package edu.ku.cete.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import au.com.bytecode.opencsv.CSVWriter;
import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.security.GroupAuthorities;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupAuthoritiesDao;
import edu.ku.cete.model.GroupAuthoritiesExclusionMapper;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.service.GroupAuthoritiesService;
import edu.ku.cete.service.GroupsService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.web.PermissionsAndRolesDTO;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class GroupAuthoritiesServiceImpl implements GroupAuthoritiesService {

	private final Logger logger = LoggerFactory.getLogger(GroupAuthoritiesServiceImpl.class);	
	 
    @Autowired
    private GroupAuthoritiesDao groupAuthoritiesDao;
    
    @Autowired
    DomainAuditHistoryMapper domainAuditHistoryDao;
    
    @Autowired
    GroupsService groupService;
    
    @Autowired
    GroupAuthoritiesExclusionMapper groupAuthoritiesExclusionMapper;
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupAuthoritiesService#addGroupAuthorities(edu.ku.cete.domain.GroupAuthorities)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public GroupAuthorities addGroupAuthorities(GroupAuthorities groupAuthorities) {
        groupAuthoritiesDao.addGroupAuthorities(groupAuthorities);
        return groupAuthorities;
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupAuthoritiesService#updateGroupAuthorities(edu.ku.cete.domain.GroupAuthorities)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void updateGroupAuthorities(GroupAuthorities groupAuthorities) {
        groupAuthoritiesDao.updateGroupAuthorities(groupAuthorities);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupAuthoritiesService#deleteGroupAuthorities(edu.ku.cete.domain.GroupAuthorities)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void deleteGroupAuthorities(GroupAuthorities groupAuthorities) {
        deleteGroupAuthorities(groupAuthorities.getId());
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupAuthoritiesService#deleteGroupAuthorities(long)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void deleteGroupAuthorities(long groupAuthoritiesId) {
        groupAuthoritiesDao.deleteGroupAuthorities(groupAuthoritiesId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupAuthoritiesService#getGroupAuthoritiesById(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public GroupAuthorities getGroupAuthoritiesById(long groupAuthoritiesId) {
        return groupAuthoritiesDao.getGroupAuthorities(groupAuthoritiesId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupAuthoritiesService#getByAuthorityId(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<GroupAuthorities> getByAuthorityId(long authorityId) {
        return groupAuthoritiesDao.getByAuthorityId(authorityId);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.GroupAuthoritiesService#getByGroupId(long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<GroupAuthorities> getByGroups(Collection<Groups> groupsList) {
        return groupAuthoritiesDao.getByGroups(groupsList);
    }

    /**
     *@param groupId long
     *@return List<GroupAuthorities>
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<GroupAuthorities> getByGroupIdAndNotDefaultRole(long groupId) {
        return groupAuthoritiesDao.getByGroupIdAndDefaultRole(groupId, false);
    }

    /**
     *
     *@param groupId long
     *@param authorityIds List<Long>
     */
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void saveGroupAuthoritiesList(long groupId, Long organizationId, Long assessmentProgramId, List<Long> authorityIds) {
        
        List<GroupAuthorities> inactiveGroupAuthorities = groupAuthoritiesDao.getInactiveByCombinedStateAssessmentProgram(groupId, organizationId, assessmentProgramId);
        List<GroupAuthorities> deletable = new ArrayList<GroupAuthorities>();
        
        //create json object
        List<GroupAuthorities> beforeGroupAuths = groupAuthoritiesDao.getJsonFormatData(groupId, organizationId, assessmentProgramId);        
        
        List<GroupAuthorities> persistedEntries = groupAuthoritiesDao.getByCombinedStateAssessmentProgram(groupId, organizationId, assessmentProgramId);
        // Updating the current authority id by changing activeFlag to true from false
        for (GroupAuthorities currentAuthorityActiveFalse : inactiveGroupAuthorities) {
            for (Long id : authorityIds) {
                if (currentAuthorityActiveFalse.getAuthorityId() == id) {
                    authorityIds.remove(id);
                    currentAuthorityActiveFalse.setActiveFlag(true);
                    currentAuthorityActiveFalse.setAuditColumnPropertiesForUpdate();
                    groupAuthoritiesDao.updateDeletedGroupAuthorities(currentAuthorityActiveFalse);
                    break;
                }
            }
        }
        
        for (GroupAuthorities groupAuthority : persistedEntries) {
            boolean found = false;
            
            for (Long id : authorityIds) {
                if (groupAuthority.getAuthorityId() == id) {
                    found = true;
                    authorityIds.remove(id);
                    break;
                }
            }

            if (!found) {
                deletable.add(groupAuthority);
            }
        }

        for (GroupAuthorities groupAuthority : deletable) {
            groupAuthoritiesDao.deleteGroupAuthorities(groupAuthority.getId());
        }

    	for (Long id : authorityIds) {
            GroupAuthorities groupAuthority = new GroupAuthorities();
            groupAuthority.setAuthorityId(id);
            groupAuthority.setGroupId(groupId);
            groupAuthority.setOrganizationId(organizationId);
            groupAuthority.setAssessmentProgramId(assessmentProgramId);
            groupAuthority.setAuditColumnProperties();
            groupAuthoritiesDao.addByCombinedStateAssessmentProgram(groupAuthority);
        }
    	
		auditGroupAuthorities(groupId, organizationId, assessmentProgramId, beforeGroupAuths);
    }

    @Async
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void auditGroupAuthorities(long groupId, Long organizationId, Long assessmentProgramId,
			List<GroupAuthorities> beforeGroupAuths) {
		try {
			List<GroupAuthorities> afterGroupAuths = groupAuthoritiesDao.getJsonFormatData(groupId, organizationId,
					assessmentProgramId);

			DomainAuditHistory domainAuditHistory = new DomainAuditHistory();

			domainAuditHistory.setSource(SourceTypeEnum.MANUAL.getCode());
			domainAuditHistory.setObjectType("GROUPS");
			domainAuditHistory.setObjectId(groupId);
			domainAuditHistory.setCreatedUserId(
					((int) ((UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
							.getUserId()));
			domainAuditHistory.setCreatedDate(new Date());
			domainAuditHistory.setAction("EDIT ROLE");
			ObjectMapper objectMapper = new ObjectMapper();

			domainAuditHistory.setObjectBeforeValues(objectMapper.writeValueAsString(beforeGroupAuths));
			domainAuditHistory.setObjectAfterValues(objectMapper.writeValueAsString(afterGroupAuths));

			domainAuditHistoryDao.insert(domainAuditHistory);
		} catch (JsonProcessingException e) {
			// not a candidate for exception handling, just log it.
			logger.error("Error while writing group authorities audit log : ", e);
		}
	}

    @Override
	public Authorities getAuthorityForCsap(String authority, Long groupId, Long stateId,
			Long assessmentProgramId) {
		return groupAuthoritiesDao.getAuthorityForCsap(authority, groupId, stateId,
				assessmentProgramId);
	}
    
    @Override
	public List<String> getAuthorityForAlternateAggregateReport(List<String> authorities, Long stateId, Long assessmentProgramId) {
		return groupAuthoritiesDao.getAuthorityForAlternateAggregateReport(authorities, stateId, assessmentProgramId);
	}
    

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void generatePermissionsAndRolesExtract(HttpServletResponse response, HttpServletRequest request,List<String> stateIds,List<String> assessmentprogramIds) throws IOException {
		
		String[] extractColumnHeaders = { "Assessment Program", "State",
				"Tab", "Grouping", "Label","Permission" };
		List<Long> stateIdsList =new ArrayList<>();
		List<Long> assessmentprogramIdsList =new ArrayList<>();
		
		for(String stateId:stateIds){
			stateIdsList.add(Long.parseLong(stateId));
		}
		for(String assessmentprogramId:assessmentprogramIds){
			assessmentprogramIdsList.add(Long.parseLong(assessmentprogramId));
		}
		List<String> roles = groupService.getPermissionExtractGroupNames();
		String[] rowHeader = (String[]) ArrayUtils.addAll(extractColumnHeaders,
				roles.toArray(new String[roles.size()]));
		
		List<String[]> excelRows = new ArrayList<String[]>();
		excelRows.add(rowHeader);
		List<PermissionsAndRolesDTO> permissionDetailsAndRoles = groupAuthoritiesDao.getPermissionsAndRolesExtractData(stateIdsList,assessmentprogramIdsList);
		
		fillPermissionsInExcelRow(permissionDetailsAndRoles, roles, excelRows);
		
		response.setContentType("application/force-download");
		String fileName = new StringBuilder().append("PermissionsAndRolesExtract")
				.append(".csv").toString();
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		CSVWriter csvWriter = null;
		try {
			csvWriter = new CSVWriter(response.getWriter(), ',');
			
			csvWriter.writeAll(excelRows);
			excelRows.clear();
			response.flushBuffer();

		} catch (IOException ex) {
			logger.error("IOException Occured:", ex);
			throw ex;

		} finally {
			if (csvWriter != null) {
				csvWriter.close();
			}
		}
		
	}
	
	public void fillPermissionsInExcelRow(List<PermissionsAndRolesDTO> permissionDetailsAndRoles,List<String> roles, List<String[]> excelRows){
		if (permissionDetailsAndRoles != null && !permissionDetailsAndRoles.isEmpty()) {
			for (PermissionsAndRolesDTO permissionsAndRolesDTO : permissionDetailsAndRoles) {
				List<String> permissionDetails = new ArrayList<>();
				permissionDetails.add(permissionsAndRolesDTO.getAssessmentProgram());
				permissionDetails.add(permissionsAndRolesDTO.getState());
				if(permissionsAndRolesDTO.getTab() != null && ! StringUtils.isEmpty(permissionsAndRolesDTO.getTab())){
				permissionDetails.add(permissionsAndRolesDTO.getTab());}
				else{
					
					permissionDetails.add(CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING);
				}
				if(permissionsAndRolesDTO.getGrouping() != null && ! StringUtils.isEmpty(permissionsAndRolesDTO.getGrouping())){
				permissionDetails.add(permissionsAndRolesDTO.getGrouping());
				}else{
					
					permissionDetails.add(CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING);
				}
				if(permissionsAndRolesDTO.getLabel() != null && ! StringUtils.isEmpty(permissionsAndRolesDTO.getLabel())){
					permissionDetails.add(permissionsAndRolesDTO.getLabel());
				}else{
					permissionsAndRolesDTO.setLabel(CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING);
					permissionDetails.add(permissionsAndRolesDTO.getLabel());
				}
				if(permissionsAndRolesDTO.getPermission() != null && ! StringUtils.isEmpty(permissionsAndRolesDTO.getPermission())){
				permissionDetails.add(permissionsAndRolesDTO.getPermission());
				}else{
					
					permissionDetails.add(CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING);
				}
				String[] permissionRoles = permissionsAndRolesDTO.getRoles().split(",");
				List<String> userPermissionRoles = Arrays.asList(permissionRoles);
				String[] rolesData = new String[roles.size()];
				List<String> restrictedRoles = groupAuthoritiesExclusionMapper
						.checkRowExistsInGroupAuthoritiesExclusionTable(permissionsAndRolesDTO.getAssessmentProgramId(), permissionsAndRolesDTO.getStateId(),
								permissionsAndRolesDTO.getPermissionId());
				for (String role : roles) {
					if(restrictedRoles.contains(role)){
						rolesData[roles.indexOf(role)] =CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING;
					}
					else if (userPermissionRoles.contains(role)) {
						rolesData[roles.indexOf(role)] = "X";
					} else {
						rolesData[roles.indexOf(role)] = "";
					}					
				}
				String[] permissionDetailsExcelRow = (String[]) ArrayUtils.addAll(
						permissionDetails.toArray(new String[permissionDetails.size()]), rolesData);
				excelRows.add(permissionDetailsExcelRow);
			}
		}
	}
}