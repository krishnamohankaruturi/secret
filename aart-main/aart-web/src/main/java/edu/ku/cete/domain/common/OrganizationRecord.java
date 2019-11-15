/**
 * 
 */
package edu.ku.cete.domain.common;

import edu.ku.cete.domain.property.AARTValidateable;
import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.domain.student.Student;

/**
 * @author mahesh
 * Implement this interface to indicate that the uploaded record has organization in it.
 * 
 * All organization records must be validateable i.e to store invalid messages and
 * identifiable say having a logical identifier.
 */
public interface OrganizationRecord extends AARTValidateable, Identifiable{
	
	/**
	 * override this method in the implementing POJO to indicate that it has the organization record.
	 * @return {@link Student}
	 */
	Organization getOrganization();

}
