/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.api.EnrollmentAPIObject;
import edu.ku.cete.domain.api.OrganizationAPIObject;
import edu.ku.cete.domain.api.RosterAPIObject;
import edu.ku.cete.domain.api.StudentAPIObject;
import edu.ku.cete.domain.api.UserAPIObject;

/**
 * @author Nathan Tipton
 * 
 */
public interface GetApiService {
	
	public List<StudentAPIObject> getStudents(Map<String, Object> searchParams);
	
	public List<OrganizationAPIObject> getOrgs(Map<String, Object> searchParams);
	
	public List<UserAPIObject> getUsers(Map<String, Object> searchParams);
	
	public List<RosterAPIObject> getApiRosters(Map<String, Object> searchParams);
	
	public List<EnrollmentAPIObject> getApiEnrollments(Map<String, Object> searchParams);
	
}