package edu.ku.cete.domain.property;


/**
 * @author m802r921
 * All AART database objects can optionally implement this.
 * implementing this interface indicates that the implementing object has first order, 2nd order and
 * 3rd order identifiers. This is in turn is leveraged in AART utility classes.
 * 
 * CAUTION:- This overloads the getter of "id" and bean resolvers won't be 
 * able to find the getId() method.
 * The same happens for restful calls also.
 */
public interface Identifiable {
	/**
	 * @return get primary identifier.
	 */
	Long getId();
	/**
	 * TODO rename this method so that it does not overload getId()
	 * @param order
	 * @return nth order identifier.
	 */
	Long getId(int order);
	
	/**
	 * @param order
	 * @return
	 */
	String getStringIdentifier(int order);
	
}
