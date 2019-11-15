/**
 * 
 */
package edu.ku.cete.util;

/**
 * @author m802r921
 *
 */
public interface WebServiceInputConfiguration {
	/**
	 * @return the wsConnectionUsername
	 */
	public String getWsConnectionUsername();
	/**
	 * @return the wsConnectionPassword
	 */
	public String getWsConnectionPassword();
	/**
	 * @return the kansasImmediateWebServiceStartTime
	 */
	public String getKansasWebServiceStartTime();
	/**
	 * @return the kansasWebServiceEndTime
	 */
	public String getKansasWebServiceEndTime();
	/**
	 * @return the kansasWebServiceSchoolYear
	 */
	public String getKansasWebServiceSchoolYear();
	/**
	 * @return the kidsByDateInputParameterCode
	 */
	public String getKidsByDateInputParameterCode();
	/**
	 * @return the getRosterWebServiceStartTime
	 */
	public String getRosterWebServiceStartTime();
	/**
	 * @return the getRosterWebServiceEndTime
	 */
	public String getRosterWebServiceEndTime();	
	/**
	 * @return getRosterByDateInputParameterCode
	 */
	public String getRosterByDateInputParameterCode();
}
