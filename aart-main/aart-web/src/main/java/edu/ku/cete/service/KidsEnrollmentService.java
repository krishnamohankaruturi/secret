/**
 * 
 */
package edu.ku.cete.service;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.ksde.kids.result.KidRecord;

public interface KidsEnrollmentService {
	/**
	 * @param kid {@link KidRecord}
	 * @param user {@link User}
	 * @param isTecRecord {@link boolean}
	 * @return {@link KidRecord}
	 */
	KidRecord cascadeAddOrUpdateForKids(KidRecord kid, ContractingOrganizationTree contractingOrganizationTree, User user);
	//Enrollment addOrUpdate(Enrollment newEnrollment);
 }
