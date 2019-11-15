package edu.ku.cete.service.impl.report;

import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.GroupAuthoritiesExclusion;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.UploadedPermissionRecord;
import edu.ku.cete.domain.security.GroupAuthorities;
import edu.ku.cete.model.GroupAuthoritiesDao;
import edu.ku.cete.model.GroupAuthoritiesExclusionMapper;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.service.report.UploadPermissionWriteService;
import edu.ku.cete.util.CommonConstants;
@Service
public class UploadPermissionWriteServiceImpl implements UploadPermissionWriteService {
    @Autowired
    private GroupAuthoritiesDao groupAuthoritiesDao;
    @Autowired
	private GroupAuthoritiesExclusionMapper groupAuthoritiesExclusionMapper;
    @Autowired
	private   GroupsDao groupsDao;
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void updatePermission(UploadedPermissionRecord permissionRecord) {
		//iterate through the roles and 
		
		for(Long stateId:permissionRecord.getStateIds()) {
			for(Entry<String, String> roleEntry:permissionRecord.getRoles().entrySet()) {
				Groups group=groupsDao.getGroupByName(roleEntry.getKey());
				GroupAuthoritiesExclusion record=new GroupAuthoritiesExclusion();
				if(!StringUtils.equalsIgnoreCase(roleEntry.getValue(), CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING)) {
					boolean activeFlag=StringUtils.equalsIgnoreCase(roleEntry.getValue(),CommonConstants.ROLES_PERMISSION_UPLOAD_HAVEPERMISSION_STRING)? true:false;
				//int updatedRecordCount;
				int updatedRecordCount=groupAuthoritiesDao.updatePermission(group.getGroupId(), permissionRecord.getPermissionId(),
						activeFlag,	stateId,  permissionRecord.getAssessmentProgramId(),permissionRecord.getUserId());
					if(updatedRecordCount == 0) {
						GroupAuthorities groupAuthorities=new GroupAuthorities();				
						groupAuthorities.setAssessmentProgramId(permissionRecord.getAssessmentProgramId());
						groupAuthorities.setOrganizationId(stateId);
						groupAuthorities.setAuthorityId(permissionRecord.getPermissionId());
						groupAuthorities.setGroupId(group.getGroupId());
						groupAuthorities.setAuditColumnProperties();
						groupAuthorities.setCreatedUser(permissionRecord.getUserId());
						groupAuthorities.setModifiedUser(permissionRecord.getUserId());
						groupAuthorities.setActiveFlag(activeFlag);
						groupAuthoritiesDao.addByCombinedStateAssessmentProgram(groupAuthorities);
					}
					record.setAuthorityId(permissionRecord.getPermissionId());
					record.setAssessmentProgramId(permissionRecord.getAssessmentProgramId());
					record.setStateId(stateId);
					record.setGroupId(group.getGroupId());
					record.setActiveFlag(false);
					record.setId(groupAuthoritiesExclusionMapper.groupAuthoritiesExclusionExists(record));
					groupAuthoritiesExclusionMapper.updateByPrimaryKeySelective(record);
				}else {
					record.setAuthorityId(permissionRecord.getPermissionId());
					record.setAssessmentProgramId(permissionRecord.getAssessmentProgramId());
					record.setStateId(stateId);
					record.setGroupId(group.getGroupId());
					record.setActiveFlag(true);
					if(groupAuthoritiesExclusionMapper.groupAuthoritiesExclusionExists(record) == null){
						groupAuthoritiesExclusionMapper.insertSelective(record);
					}else{
						record.setId(groupAuthoritiesExclusionMapper.groupAuthoritiesExclusionExists(record));
						groupAuthoritiesExclusionMapper.updateByPrimaryKeySelective(record);						
					}
					GroupAuthorities groupAuthorities=new GroupAuthorities();				
					groupAuthorities.setAssessmentProgramId(permissionRecord.getAssessmentProgramId());
					groupAuthorities.setOrganizationId(stateId);
					groupAuthorities.setAuthorityId(permissionRecord.getPermissionId());
					groupAuthorities.setGroupId(group.getGroupId());
					groupAuthorities.setModifiedUser(permissionRecord.getUserId());
					groupAuthorities.setActiveFlag(false);
					groupAuthoritiesDao.updateExcludedGroupAuthorities(groupAuthorities);
					
				}
		 	}
		}
	}

}
