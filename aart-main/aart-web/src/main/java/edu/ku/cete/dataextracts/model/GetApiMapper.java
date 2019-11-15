package edu.ku.cete.dataextracts.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.api.EnrollmentAPIObject;
import edu.ku.cete.domain.api.OrganizationAPIObject;
import edu.ku.cete.domain.api.RosterAPIObject;
import edu.ku.cete.domain.api.StudentAPIObject;
import edu.ku.cete.domain.api.UserAPIObject;

public interface GetApiMapper{

	List<StudentAPIObject> getApiStudents(@Param("searchParams") Map<String, Object> searchParams);
	
	List<OrganizationAPIObject> getApiOrgs(@Param("searchParams") Map<String, Object> searchParams);
	
	List<UserAPIObject> getApiUsers(@Param("searchParams") Map<String, Object> searchParams);
	
	List<RosterAPIObject> getApiRosters(@Param("searchParams") Map<String, Object> searchParams);
	
	List<EnrollmentAPIObject> getApiEnrollments(@Param("searchParams") Map<String, Object> searchParams);
	
}