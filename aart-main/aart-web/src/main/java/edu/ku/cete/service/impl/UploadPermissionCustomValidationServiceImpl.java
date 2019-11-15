package edu.ku.cete.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.domain.Authorities;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.Groups;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.UploadedPermissionRecord;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.GroupsDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AuthoritiesService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.OrgAssessmentProgramService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.util.CommonConstants;

@Service
public class UploadPermissionCustomValidationServiceImpl implements BatchUploadCustomValidationService {
	final static Log logger = LogFactory
			.getLog(UploadPermissionCustomValidationServiceImpl.class);
	
	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private OrgAssessmentProgramService orgAssessProgService;

	@Autowired
	private AssessmentProgramService assessmentProgramService;

	@Autowired
	private AuthoritiesService authoritiesService;

	@Autowired
	private GroupsDao groupsDao;
	
	@Value("${permissions.upload.state.error.message}")
	private String stateErrorMsg;
	
	@Value("${permissions.upload.stateNotMappedToUserErrorMsg.error.message}")
	private String stateNotMappedToUserErrorMsg;
	
	@Value("${permissions.upload.apNotAvilble.error.message}")
	private String apErrorMsg;
	
	@Value("${permissions.upload.apNotAvilbleForState.error.message}")
	private String apNotAvilbleForStateErrorMsg;
	
	@Value("${permissions.upload.apNotMappedForState.error.message}")
	private String apNotMappedForStateErrorMsg;
	
	@Value("${permissions.upload.tabNotAvilble.error.message}")
	private String tabErrorMsg;
	
	@Value("${permissions.upload.groupNotAvilble.error.message}")
	private String groupErrorMsg;
	
	@Value("${permissions.upload.labelNotAvilble.error.message}")
	private String labelErrorMsg;
	
	@Value("${permissions.upload.permissionNotAvilble.error.message}")
	private String permissionErrorMsg;

	@Value("${permissions.upload.hierarchyTab.error.message}")
	private String hierarchyTab;
	
	@Value("${permissions.upload.hierarchygrouping.error.message}")
	private String hierarchygrouping;
	
	@Value("${permissions.upload.hierarchylabel.error.message}")
	private String hierarchylabel;
	
	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug("Permissions Upload Custom Validation.");
		Properties prop =(Properties) params.get("Properties");
		UserDetailImpl userDetatil=(UserDetailImpl)params.get("currentUser");
		User user=userDetatil.getUser();
		Long userId=(Long) params.get("createdUser");	
		List<String> dynamicRoleNameList=(List) params.get("dynamicRoleNameList");
		Set<String> keys = new HashSet(prop.keySet());				
		UploadedPermissionRecord record= (UploadedPermissionRecord) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		boolean validationPassed=true;
		boolean isAllStates=false;
		boolean isPermissionhierarchyAvailble=true;
		String lineNumber = record.getLineNumber();
		Organization state=null;
		for (String key : keys) {		

			if(dynamicRoleNameList.contains(key.toLowerCase())) {
				record.getRoles().put(key, prop.getProperty(key));
			}
		}
		ContractingOrganizationTree contractingOrganizationTree  =null;
		contractingOrganizationTree = (ContractingOrganizationTree)params.get("contractingOrganizationTree");
		if(record.getState()!=null && StringUtils.equalsIgnoreCase(record.getState(),CommonConstants.UPLOAD_ALL_STRING)) {
			isAllStates=true;
		}else {
			Groups groups = groupsDao.getGroup(user.getCurrentGroupsId());
			state=organizationService.getStateByNameAndType(record.getState(),CommonConstants.ORGANIZATION_STATE_TYPE_ID);
			if(state==null) {
				validationErrors.rejectValue("state", "", new String[]{lineNumber, mappedFieldNames.get("state")},StringUtils.replace(stateErrorMsg, "$", record.getState()) );
				validationPassed = false;
			}else {


				if ( groups.getGroupCode().equalsIgnoreCase(CommonConstants.GROUP_GLOBALSYSTEMADMIN)) {
					record.getStateIds().add(state.getId());
				}else {
					if(organizationService.isUserMappedToGivenState(state.getId(),userId)) {
						validationErrors.rejectValue("state", "", new String[]{lineNumber, mappedFieldNames.get("state")},StringUtils.replace(stateNotMappedToUserErrorMsg, "$", record.getState()) );
						validationPassed = false;
					}else
					{
						record.getStateIds().add(state.getId());
					}
				}
			}
		}

		AssessmentProgram assessmentProgram = assessmentProgramService.findByAbbreviatedName(record.getAssessmentProgram());
		if(assessmentProgram ==null) {
			validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")},StringUtils.replace(apErrorMsg, "$", record.getAssessmentProgram()) );
			validationPassed = false;
		}else {
			record.setAssessmentProgramId(assessmentProgram.getId());
			if(state!=null) {
				List<OrgAssessmentProgram> assessmentPrograms = orgAssessProgService.findByContractingOrganizationId(state.getId());
				if(assessmentPrograms==null || assessmentPrograms.isEmpty() ){
					validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")},StringUtils.replace(apNotAvilbleForStateErrorMsg, "$", record.getState()) );
					validationPassed = false;
				}else {

					if(assessmentPrograms.stream().filter(a->a.getAssessmentProgram().getAbbreviatedname()
							.equals(assessmentProgram.getAbbreviatedname())).collect(Collectors.toList()).isEmpty()) {
						validationErrors.rejectValue("assessmentProgram", "", new String[]{lineNumber, mappedFieldNames.get("assessmentProgram")},StringUtils.replace(apNotMappedForStateErrorMsg, "$", record.getAssessmentProgram()) );
						validationPassed = false;

					}
				}
			}
		}

		if(isAllStates && assessmentProgram!=null) {
			List<Organization> organizationList= organizationService.getStateByAssessmentProgramIdForUploadResults(record.getAssessmentProgramId(),userId);
			for(Organization org:organizationList) {
				record.getStateIds().add(org.getId());
			}
		}
		;
		if(! authoritiesService.checkTabIsAvailable(record.getTab())) {
			validationErrors.rejectValue("tab", "", new String[]{lineNumber, mappedFieldNames.get("tab")},StringUtils.replace(tabErrorMsg, "$", record.getTab()) );
			validationPassed = false;
			isPermissionhierarchyAvailble=false;
		}
		if(record.getGrouping()!=null && !StringUtils.equalsIgnoreCase(CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING, record.getGrouping())) {
			if(! authoritiesService.checkGroupingIsAvailable(record.getGrouping())) {
				validationErrors.rejectValue("grouping", "", new String[]{lineNumber, mappedFieldNames.get("grouping")},StringUtils.replace(groupErrorMsg, "$", record.getGrouping()) );
				validationPassed = false;
				isPermissionhierarchyAvailble=false;
			}
		}
		if(record.getGrouping()!=null && !StringUtils.equalsIgnoreCase(CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING, record.getLabel())) {
			if(! authoritiesService.checkLabelIsAvailable(record.getLabel())) {
				validationErrors.rejectValue("label", "", new String[]{lineNumber, mappedFieldNames.get("labelName")},StringUtils.replace(labelErrorMsg, "$", record.getLabel()) );
				validationPassed = false;
				isPermissionhierarchyAvailble=false;
			}
		}
		//
		Authorities auth=authoritiesService.getByDisplayName(record.getPermission());
		if(auth ==null) {
			validationErrors.rejectValue("permission", "", new String[]{lineNumber, mappedFieldNames.get("permission")},StringUtils.replace(permissionErrorMsg, "$", record.getPermission()) );
			validationPassed = false;
		}else if(isPermissionhierarchyAvailble) {
			
			if( !StringUtils.equalsIgnoreCase(auth.getTabName().trim(), record.getTab())) {
				validationErrors.rejectValue("permission", "", new String[]{lineNumber, mappedFieldNames.get("permission")},StringUtils.replace(hierarchyTab, "$", record.getTab()) );
				validationPassed = false;
				isPermissionhierarchyAvailble=false;
			}
			if(isPermissionhierarchyAvailble && !StringUtils.equalsIgnoreCase(auth.getGroupingName()==null  ||  StringUtils.isEmpty(auth.getGroupingName())? CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING : auth.getGroupingName().trim(), record.getGrouping())) {
				validationErrors.rejectValue("permission", "", new String[]{lineNumber, mappedFieldNames.get("permission")},StringUtils.replace(hierarchygrouping, "$", record.getPermission()) );
				validationPassed = false;
				isPermissionhierarchyAvailble=false;
			}
			if(isPermissionhierarchyAvailble && !StringUtils.equalsIgnoreCase(auth.getLabelName()==null ||  StringUtils.isEmpty(auth.getLabelName())? CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING: auth.getLabelName().trim(), record.getLabel())) {
				validationErrors.rejectValue("permission", "", new String[]{lineNumber, mappedFieldNames.get("permission")},StringUtils.replace(hierarchylabel, "$", record.getPermission()) );
				validationPassed = false;
				isPermissionhierarchyAvailble=false;
			}
			if(isPermissionhierarchyAvailble) {
				record.setPermissionId(auth.getAuthoritiesId());
			}
		}
		
		for(Entry<String, String> roleEntry:record.getRoles().entrySet()) {
			if(!StringUtils.equalsIgnoreCase(roleEntry.getValue(), CommonConstants.ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING)) {
				if(!StringUtils.equalsIgnoreCase(roleEntry.getValue(), CommonConstants.ROLES_PERMISSION_UPLOAD_HAVEPERMISSION_STRING)) {
					if(!roleEntry.getValue().isEmpty()) {
						validationPassed = false;
						validationErrors.rejectValue("permission", "",new String[]{lineNumber, mappedFieldNames.get("permission")},"Permission should be either X,empty or N/A");
					}
				}
			}
		}
		
		if(validationPassed) {
			logger.debug("Custom Validation passed.");
			record.setUserId(userId);
		}
		params.put("rowDataObject", record);//Required it for alert message.. 
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", record);
		return customValidationResults;	
	}

}
