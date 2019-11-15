package edu.ku.cete.warehouse.model;

import java.util.List;


import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.FindEnrollments;


public interface HistoricDataMapper {

	List<FindEnrollments> findStudentEnrollment(@Param("studentStateId") String studentStateId,
			@Param("stateId") Long stateId,
			@Param("currentSchoolYear") Integer currentSchoolYear,
			@Param("educatorId") Long educatorId,
			@Param("isTeacher") boolean isTeacher );
	
	Enrollment findStudentBasedOnStateStudentIdentifier(
			@Param("stateStudentIdentifier") String stateStudentIdentifier, 
			@Param("organizationId") Long organizationId);
	
}