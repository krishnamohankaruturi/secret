/**
 * 
 */
package edu.ku.cete.service.api;

import java.util.Map;

import edu.ku.cete.domain.api.EnrollmentAPIObject;

/**
 * @author v090n216
 *
 */
public interface EnrollmentAPIService {

	Map<String, Object> postEnrollment(Map<String, Object> serviceResponse, EnrollmentAPIObject enrollmentAPIObject, Long userId);

	Map<String, Object> putEnrollment(Map<String, Object> serviceResponse, EnrollmentAPIObject enrollmentAPIObject, Long userId);

	Map<String, Object> deleteEnrollment(Map<String, Object> serviceResponse, EnrollmentAPIObject enrollmentAPIObject, Long userId);
	
}
