/**
 * 
 */
package edu.ku.cete.domain.property;

import java.util.List;

/**
 * @author mahesh
 * Implementing this interface means the object has one property which is of type Map<String,String>
 */
public interface ContainsKeyValuePairs {
	
	/**
	 * Returns just a blank list to "imitate" property.
	 * @return
	 */
	List<String> getKeyValuePairs();
	/**
	 * The first one in the list would be the key and the second one would be the value.
	 * @param keyValuePair
	 */
	void setKeyValuePairs(List<String> keyValuePair);

}
