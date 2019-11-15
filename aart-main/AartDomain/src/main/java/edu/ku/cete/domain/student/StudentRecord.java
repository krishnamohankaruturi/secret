/**
 * 
 */
package edu.ku.cete.domain.student;

import edu.ku.cete.domain.property.AARTValidateable;
import edu.ku.cete.domain.property.Identifiable;

/**
 * @author mahesh
 * Implement this interface to indicate that the uploaded record has student in it.
 * 
 * All student records must be validateable i.e to store invalid messages and
 * identifiable say having a logical identifier.
 * TODO change to student information...? any better name.
 */
public interface StudentRecord extends AARTValidateable, Identifiable{
	
	/**
	 * override this method in the implementing POJO to indicate that it has the student record.
	 * @return {@link Student}
	 */
	Student getStudent();

}
