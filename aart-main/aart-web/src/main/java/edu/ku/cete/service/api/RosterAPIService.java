package edu.ku.cete.service.api;

import java.util.Map;

import edu.ku.cete.domain.api.RosterAPIObject;
import edu.ku.cete.service.api.exception.APIRuntimeException;

public interface RosterAPIService {
	public Map<String, Object> validateRosterAPIObject(ApiRequestTypeEnum methodEnum, RosterAPIObject rosterAPIObject);
	public Map<String, Object> postRoster(Map<String, Object> response, RosterAPIObject rosterAPIObject, Long assessmentProgramId, Long userId) throws RuntimeException;
	public Map<String, Object> putRoster(Map<String, Object> response, RosterAPIObject rosterAPIObject, Long assessmentProgramId, Long userId) throws APIRuntimeException;
	public Map<String, Object> deleteRoster(Map<String, Object> response, RosterAPIObject rosterAPIObject, Long assessmentProgramId, Long userId) throws APIRuntimeException;
}