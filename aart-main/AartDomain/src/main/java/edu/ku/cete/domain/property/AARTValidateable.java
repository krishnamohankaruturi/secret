/**
 * 
 */
package edu.ku.cete.domain.property;

import java.lang.reflect.Array;
import java.util.List;

import org.springframework.context.MessageSource;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.model.InValidDetail;

/**
 * @author m802r921
 *
 * 1) Indicates if a record is invalid and/or it needs to be rejected.
 * 2) Indicates the details on why and what fields are invalid.
 * 3) Indicates if the record is a new insert or an update.
 */
public interface AARTValidateable {
	/**
	 * Identifier to be displayed when displaying invalid details.
	 * This is not the primary key.
	 * @return the identifier
	 */
	public String getIdentifier();
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat();
	/**
	 * @return the inValid
	 */
	public boolean isInValid();
	/**
	 * @param inValid the inValid to set
	 */
	public void setInValid(boolean inValid);
	/**
	 * @return the doReject
	 */
	public boolean isDoReject();
	/**
	 * @param doRej the doReject to set.
	 * once set should not be unset.
	 */
	public void setDoReject(boolean doRej);
	/**
	 * @return the created
	 */
	public boolean isCreated();
	/**
	 * @param creat the created to set
	 */
	public void setCreated(boolean creat);
	/**
	 * @return the inValidFields
	 */
	public String getInValidFields() ;

	/**
	 * @param inValidRs the inValidFields to set
	 */
	public void setInValidFields(String inValidRs);
	/**
	 * @param invalidField {@link String}
	 * @param invalidValue {@link String}
	 */
	public void addInvalidField(String invalidField, String invalidValue);
	/**
	 * @param invalidField {@link String}
	 * @param invalidValue {@link String}
	 * @param reject {@link Boolean}
	 */
	public void addInvalidField(String invalidField, String invalidValue, boolean reject);
	
	/**
     * @param invalidField {@link String}
     * @param invalidValue {@link String}
     * @param reject {@link Boolean}
     */
    public void addInvalidField(String invalidField,
    		String invalidValue, boolean reject, InvalidTypes invalidType);
	/**
	 * @return the inValidDetails
	 */
	public List<InValidDetail> getInValidDetails();
	/**
	 * TODO 
	 * @return {@link Array}
	 */
	public String[] getRejectedReason(MessageSource msgSource) ;

}
