package edu.ku.cete.service.api;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.api.APIDashboardError;
import edu.ku.cete.domain.api.UserAPIObject;
import edu.ku.cete.domain.api.UserRolesAPIObject;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.service.api.exception.APIRuntimeException;

public interface UserAPIService {
	public Map<String, Object> postUser(UserAPIObject uao, Long assessmentProgramId, Long createdUserId) throws APIRuntimeException;
	public Map<String, Object> putUser(UserAPIObject uao, Long assessmentProgramId, Long modifiedUserId) throws APIRuntimeException;
	public Map<String, Object> deleteUsers(List<UserAPIObject> userAPIObjs, Long assessmentProgramId, Long userId);
	Map<String, Object> validateUserAPIObject(UserAPIObject userAPIObject, Long assessmentProgramId);
	List<APIDashboardError> createUserErrorRecords(ApiRequestTypeEnum requestType, ApiRecordTypeEnum recordType,
			String externalId, String name, List<Organization> orgs, Long createModifyId, List<String> messages);
	List<Organization> rolesToOrgsList(List<UserRolesAPIObject> roles);
	List<String> getUserErrorMessages(Map<String, Object> map);
	
}
