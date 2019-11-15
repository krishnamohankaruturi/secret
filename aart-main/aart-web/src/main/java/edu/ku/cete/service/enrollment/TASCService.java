/**
 * 
 */
package edu.ku.cete.service.enrollment;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.ksde.kids.result.KidRecord;

/**
 * @author ktaduru_sta
 *
 */
public interface TASCService {

	String cascadeAddOrUpdate(KidRecord kidRecord, User user, ContractingOrganizationTree contractingOrganizationTree);
	
}
