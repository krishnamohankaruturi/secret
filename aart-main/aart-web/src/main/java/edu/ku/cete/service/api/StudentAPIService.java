package edu.ku.cete.service.api;

import java.util.Map;

import edu.ku.cete.domain.api.StudentAPIObject;
import edu.ku.cete.service.api.exception.APIRuntimeException;

public interface StudentAPIService {
	public Map<String, Object> validateStudentAPIObject(ApiRequestTypeEnum methodEnum, StudentAPIObject studentAPIObject);
	public Map<String, Object> postStudent(Map<String, Object> response, StudentAPIObject studentAPIObject, Long assessmentProgramId, Long userId) throws RuntimeException;
	public Map<String, Object> putStudent(Map<String, Object> response, StudentAPIObject studentAPIObject, Long assessmentProgramId, Long userId) throws APIRuntimeException;
	public Map<String, Object> deleteStudent(Map<String, Object> response, StudentAPIObject studentAPIObject, Long assessmentProgramId, Long userId) throws APIRuntimeException;
}
