/**
 * 
 */
package edu.ku.cete.domain.user;

import edu.ku.cete.domain.property.AARTValidateable;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.domain.user.User;

/**
 * @author mahesh
 * Implement this interface to indicate that the uploaded record has user in it.
 * 
 * All user records must be validateable i.e to store invalid messages and
 * identifiable say having a logical identifier.
 */
public interface UserRecord extends AARTValidateable, Identifiable{
	
	/**
	 * override this method in the implementing POJO to indicate that it has the student record.
	 * @return {@link Student}
	 */
	User getUser();

}
