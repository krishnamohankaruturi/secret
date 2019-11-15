/**
 * 
 */
package edu.ku.cete.service.impl.enrollment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.EnrollmentsRostersExample;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.SourceTypeEnum;


/**
 * @author m802r921
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class EnrollmentsRostersServiceImpl implements EnrollmentsRostersService {

	/**
	 * enrollmentsRostersDao.
	 */
	@Autowired
	private EnrollmentsRostersDao enrollmentsRostersDao;
	
	@Autowired
	private RosterService rosterService;

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final EnrollmentsRosters addEnrollmentToRoster(
			EnrollmentsRosters enrollmentsRosters) {
		enrollmentsRosters.setAuditColumnProperties();

			EnrollmentsRostersExample enrollmentsRostersExample = new EnrollmentsRostersExample();
			EnrollmentsRostersExample.Criteria enrollmentsRostersCriteria = enrollmentsRostersExample
					.createCriteria();
			enrollmentsRostersCriteria.andEnrollmentIdEqualTo(enrollmentsRosters
					.getEnrollmentId());
			enrollmentsRostersCriteria.andRosterIdEqualTo(enrollmentsRosters
					.getRosterId());
			int updates = enrollmentsRostersDao.updateByExample(
					enrollmentsRosters, enrollmentsRostersExample);
		if(updates<1) {
			enrollmentsRostersDao.insert(enrollmentsRosters);
		}
		return enrollmentsRosters;
	}
	
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void removeEnrollmentFromRoster(
			EnrollmentsRosters enrollmentsRosters) {
		//TODO
	}

	@Override
	public int updateEnrollementRosterToInActive(
			EnrollmentsRosters enrollmentRosters) {
		enrollmentRosters.setAuditColumnProperties();
		EnrollmentsRostersExample enrollmentsRostersExample = new EnrollmentsRostersExample();
		EnrollmentsRostersExample.Criteria enrollmentsRostersCriteria = enrollmentsRostersExample
				.createCriteria();
		enrollmentsRostersCriteria.andEnrollmentIdEqualTo(enrollmentRosters
				.getEnrollmentId());
		enrollmentsRostersCriteria.andRosterIdEqualTo(enrollmentRosters
				.getRosterId());
		return enrollmentsRostersDao.updateByExample(enrollmentRosters, enrollmentsRostersExample);
	}

	@Override
	 @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Long getMaxRosterIdByEnrollmentId(Long enrollmentId) {		
		return enrollmentsRostersDao.getMaxRosterIdByEnrollmentId(enrollmentId);
	}
	
	@Override
	 @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Long> getRosterIdsByEnrollmentIdAndContent(Long enrollmentId, List<String> completedBands) {		
		return enrollmentsRostersDao.getRosterIdsByEnrollmentIdAndContent(enrollmentId, completedBands);
	}

	@Override
	public List<EnrollmentsRosters> getByExternalId(Long externalId) {
		return enrollmentsRostersDao.getByExternalId(externalId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int deactivateByStudentIdExternalIdAndRosterStateSubjectAreaCode(
			Long studentId, List<String> contentAreaNames,
			Long enrollmentsRostersExternalId,
			Long userId,
			SourceTypeEnum source) {
		List<EnrollmentsRosters> ers = enrollmentsRostersDao.selectEnrollmentsRostersByAPIInformation(
			studentId,
			contentAreaNames,
			enrollmentsRostersExternalId,
			userId
		);
		for (EnrollmentsRosters er : ers) {
			Roster roster = rosterService.selectByPrimaryKey(er.getRosterId());
			er.setModifiedUser(userId); // for audit purposes
			roster.setSourceType(source.getCode()); // also purely for audit purposes, this should have no persistent effect on the actual roster
			rosterService.addRmStuFromRostEventToDomainAduidtHistory(er, roster);
		}
		return enrollmentsRostersDao.updateByStudentIdAndRosterStateSubjectAreaCode(studentId, contentAreaNames, enrollmentsRostersExternalId, userId);
	}

}
