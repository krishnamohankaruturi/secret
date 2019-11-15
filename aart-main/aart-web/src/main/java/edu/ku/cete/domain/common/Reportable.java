/**
 * 
 */
package edu.ku.cete.domain.common;

/**
 * @author mahesh
 * This is to indicate the data can be grouped and reported on.
 * This is a generic structure to flatten the object, for display in jqgrid.
 * This avoids the creation of flat objects
 */
public interface Reportable {
	
	int getNumberOfAttributes();
	/**
	 * return the parameter in the ith position.
	 * @param i
	 * @return
	 */
	String getAttribute(int i);
	
	/**
	 * return the parameters in the right order for display in the jqgrid.
	 * @return
	 */
	String[] getAttributes();
//	String getAttribute(int i,StudentsTestsStatusConfiguration studentsTestsStatusConfiguration);
//	String[] getAttributes(StudentsTestsStatusConfiguration studentsTestsStatusConfiguration);

}
