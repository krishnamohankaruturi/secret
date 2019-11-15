package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.user.User;

public interface AsynchronousProcessService {
	void manageOrganization(Organization organization, User user);

	void resetStudentPasswordOnAnnualReset(Long orgId, int passwordLength,
			String[] qcStates);

	void createMultipleTestSessions(List<Long> enrollmentRosterIds,String arraySelectedTestId, Long[] testIds, String sessionName,String[] students);
}
