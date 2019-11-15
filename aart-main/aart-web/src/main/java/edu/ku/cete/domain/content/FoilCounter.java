/**
 * 
 */
package edu.ku.cete.domain.content;

/**
 * @author m802r921
 * For various displays of foil count.
 */
public class FoilCounter {
	private static final String[] foilCounter = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","Y","Z"};
	/**
	 * @param index {@link Integer}
	 * @return {@link String}
	 */
	public static final String getFoilCount(Integer index, String format){
		if (index == null) {
			index = 1;
		}
		return getFoilCount(index.intValue(), format);
	}
	/**
	 * @param index {@link Integer}
	 * @return {@link String}
	 */
	public static final String getFoilCount(int index, String format){
		if (index > 26) {
			index = index % 26;
		}
		String returnValue = "";
		if (format == null || format.equals("letters")){
			returnValue = foilCounter[index];
		} else if (format.equals("numbers")){
			returnValue = Integer.toString(index + 1);
		}
		return returnValue;
	}
}
