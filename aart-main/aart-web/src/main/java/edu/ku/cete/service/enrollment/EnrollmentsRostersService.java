package edu.ku.cete.service.enrollment;

import java.util.List;

import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * @author m802r921
 *
 */
public interface EnrollmentsRostersService {
	/**
	 * @param enrollmentsRosters {@link EnrollmentsRosters}
	 * @return {@link EnrollmentsRosters}
	 */
	EnrollmentsRosters addEnrollmentToRoster(EnrollmentsRosters enrollmentsRosters);
	
	/**
	 * @param enrollmentsRosters {@link EnrollmentsRosters}
	 * @return {@link void}
	 */
	void removeEnrollmentFromRoster(EnrollmentsRosters enrollmentsRosters);
	
	int updateEnrollementRosterToInActive(EnrollmentsRosters enrollmentRosters);
	
	Long getMaxRosterIdByEnrollmentId(Long enrollmentId);

	List<Long> getRosterIdsByEnrollmentIdAndContent(Long enrollmentId, List<String> completedBands);

	List<EnrollmentsRosters> getByExternalId(Long externalId);
	
	int deactivateByStudentIdExternalIdAndRosterStateSubjectAreaCode(
		Long studentId,
		List<String> contentAreaNames,
		Long enrollmentsRostersExternalId,
		Long userId,
		SourceTypeEnum source);

}
