package edu.ku.cete.domain.common;

import java.io.Serializable;

import edu.ku.cete.util.AartParseException;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author m802r921
 * 
 */
public class HexBinary implements Serializable {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 1174040501809813062L;
    /**
     * logger.
     */
    private static final Log LOGGER = LogFactory.getLog(HexBinary.class);
	/**
	 * hexadecimal string.
	 */
	private String hexString="";

	/**
	 * constructor Not to be used.
	 */
	private HexBinary() {
	}
	/**
	 * constructor Not to be used.
	 */
	public HexBinary(String hex) {
		setHexString(hex);
	}
	/**
	 * @param hexStr
	 *            the hexString to set
	 * @throws AartParseException 
	 */
	private final void setHexStringAndFail(String hexStr) throws AartParseException {
		//To ensure it is a hexadecimal
		try {
			hexStr = hexStr.replace("#", "");
			Integer.parseInt(hexStr, 16);
			this.hexString = hexStr;
		} catch (NumberFormatException e) {
			throw new AartParseException(e);
		}
	}
	/**
	 * @param hexStr
	 *            the hexString to set
	 */
	private final void setHexString(String hexStr) {
		//To ensure it is a hexadecimal
		try {
			hexStr = hexStr.replace("#", "");
			Integer.parseInt(hexStr, 16);
			this.hexString = hexStr;
		} catch (NumberFormatException e) {
			LOGGER.warn("Exception setting hex string", e);
		}
	}
	/**
	 * @return the hexString
	 */
	public final String getHexString() {
		return hexString;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}

}
