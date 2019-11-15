/**
 * 
 */
package edu.ku.cete.domain.property;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.audit.AuditableDomain;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author m802r921
 *
 * 1) Indicates if a record is invalid and/or it needs to be rejected.
 * 2) Indicates the details on why and what fields are invalid.
 * 3) Indicates if the record is a new insert or an update.
 * 4) This is one default implementation of AARTValidateable
 */
@Component
public abstract class ValidateableRecord  extends AuditableDomain implements AARTValidateable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7257191632440826319L;
	/**
	 * is the record valid or invalid.
	 */
	private boolean inValid;
	/**
	 * reject the record if true.
	 */
	private boolean doReject = false;
	/**
	 * Reasons why this is invalid.
	 */
	private String inValidFields ="";
	/**
	 * if the record is newly created.
	 */
	private boolean created = false;
	
	/**
	 * If record is removed from roster
	 */
	private boolean removed = false;
	/**
	 * @return the removed
	 */
	public boolean isRemoved() {
		return removed;
	}
	/**
	 * @param removed the removed to set
	 */
	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	/**
	 * date format.
	 * the child classes can over ride if it wants to use a different date format.
	 */
	private String dateFormat = "MM/dd/yyyy";
	/**
	 * list.
	 */
	private List<InValidDetail> inValidDetails = new ArrayList<InValidDetail>();

	/**
	 * Identifier to be displayed when displaying invalid details.
	 * This is not the primary key.
	 * @return the identifier
	 */
	public abstract String getIdentifier();
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}
	/**
	 * @return the inValid
	 */
	public final boolean isInValid() {
		return inValid;
	}

	/**
	 * @param inValid the inValid to set
	 */
	public final void setInValid(boolean inValid) {
		this.inValid = inValid;
	}

	/**
	 * @return the doReject
	 */
	public final boolean isDoReject() {
		return doReject;
	}

	/**
	 * @param doRej the doReject to set.
	 * once set should not be unset.
	 */
	public final void setDoReject(boolean doRej) {
		if (doRej) {
			this.doReject = doRej;
			this.inValid = true;
		}
	}

	public final void setDoRejectOverride(boolean doRej) {
			this.doReject = doRej;
			this.inValid = doRej;
	}
	
	/**
	 * @return the created
	 */
	public final boolean isCreated() {
		return created;
	}
	/**
	 * @param creat the created to set
	 */
	public final void setCreated(boolean creat) {
		this.created = creat;
	}
	/**
	 * @return the inValidFields
	 */
	public final String getInValidFields() {
		return inValidFields;
	}

	/**
	 * @param inValidRs the inValidFields to set
	 */
	public final void setInValidFields(String inValidRs) {
		this.inValidFields = inValidRs;
	}
	/**
	 * @param invalidField {@link String}
	 * @param invalidValue {@link String}
	 */
	public final void addInvalidField(String invalidField, String invalidValue) {
		this.setInValid(true);
		if (StringUtils.hasText(inValidFields)) {
			if (StringUtils.hasText(invalidField)) {
				inValidFields = inValidFields + ParsingConstants.OUTER_DELIM + invalidField;
				inValidDetails.add(InValidDetail.getInstance(invalidField, invalidValue));
			}
		} else {
			if (StringUtils.hasText(invalidField)) {
				inValidFields = invalidField;
				inValidDetails.add(InValidDetail.getInstance(invalidField, invalidValue));
			}
		}
	}
	/**
	 * @param invalidField {@link String}
	 * @param invalidValue {@link String}
	 * @param reject {@link Boolean}
	 */
	public final void addInvalidField(String invalidField, String invalidValue, boolean reject) {
		this.setInValid(true);
		this.setDoReject(reject);
		if (StringUtils.hasText(inValidFields)) {
			if (StringUtils.hasText(invalidField)) {
				inValidFields = inValidFields + ParsingConstants.OUTER_DELIM + invalidField;
				inValidDetails.add(InValidDetail.getInstance(invalidField, invalidValue, reject));
			}
		} else {
			if (StringUtils.hasText(invalidField)) {
				inValidFields = invalidField;
				inValidDetails.add(InValidDetail.getInstance(invalidField, invalidValue, reject));
			}
		}
	}
	
	/**
	 * 
	 * @param invalidField
	 * @param invalidValue
	 * @param reject
	 * @param reason
	 */
	public final void addInvalidField(String invalidField, String invalidValue, boolean reject, String reason) {
		this.setInValid(true);
		this.setDoReject(reject);
		if (StringUtils.hasText(inValidFields)) {
			if (StringUtils.hasText(invalidField)) {
				inValidFields = inValidFields + ParsingConstants.OUTER_DELIM + invalidField;
                InValidDetail detail = InValidDetail.getInstance(invalidField, invalidValue, reject);
                detail.setReason(reason);
                inValidDetails.add(detail);
			}
		} else {
			if (StringUtils.hasText(invalidField)) {
				inValidFields = invalidField;
                InValidDetail detail = InValidDetail.getInstance(invalidField, invalidValue, reject);
                detail.setReason(reason);
                inValidDetails.add(detail);
			}
		}
	}
	
	/**
     * @param invalidField {@link String}
     * @param invalidValue {@link String}
     * @param reject {@link Boolean}
     */
    public final void addInvalidField(String invalidField, String invalidValue, boolean reject, InvalidTypes invalidType) {
        this.setInValid(true);
        this.setDoReject(reject);
        if (StringUtils.hasText(inValidFields)) {
            if (StringUtils.hasText(invalidField)) {
                inValidFields = inValidFields + ParsingConstants.OUTER_DELIM + invalidField;
                inValidDetails.add(InValidDetail.getInstance(invalidField, invalidValue, reject, invalidType));
            }
        } else {
            if (StringUtils.hasText(invalidField)) {
                inValidFields = invalidField;
                inValidDetails.add(InValidDetail.getInstance(invalidField, invalidValue, reject, invalidType));
            }
        }
    }
    
    /**
     * 
     * @param invalidField
     * @param invalidValue
     * @param reject
     * @param invalidType
     * @param reason
     */
    public final void addInvalidField(String invalidField, String invalidValue, boolean reject, InvalidTypes invalidType, String reason) {
        this.setInValid(true);
        this.setDoReject(reject);
        if (StringUtils.hasText(inValidFields)) {
            if (StringUtils.hasText(invalidField)) {
                inValidFields = inValidFields + ParsingConstants.OUTER_DELIM + invalidField;
                InValidDetail detail = InValidDetail.getInstance(invalidField, invalidValue, reject, invalidType);
                detail.setReason(reason);
                inValidDetails.add(detail);
            }
        } else {
            if (StringUtils.hasText(invalidField)) {
                inValidFields = invalidField;
                InValidDetail detail = InValidDetail.getInstance(invalidField, invalidValue, reject, invalidType);
                detail.setReason(reason);
                inValidDetails.add(detail);
            }
        }
    }
    
    /**
     * 
     * @param invalidDetail
     */
    public final void addInvalidDetail(InValidDetail invalidDetail) {
         inValidDetails.add(invalidDetail);
    }
	/**
	 * @return the inValidDetails
	 */
	public final List<InValidDetail> getInValidDetails() {
		return inValidDetails;
	}
	
	/**
	 * TODO 
	 * @return {@link Array}
	 */
	public final String[] getRejectedReason(MessageSource msgSource) {
		StringBuffer invalidDetailsStr = new StringBuffer();
		if (inValidDetails != null && CollectionUtils.isNotEmpty(inValidDetails)) {
			for (InValidDetail invalidDetail : inValidDetails) {
				if (invalidDetail.getRejectRecord()) {
					invalidDetailsStr.append(
							ParsingConstants.OUTER_DELIM + 
							msgSource.getMessage("property.invalid",
							new String[] {invalidDetail.getFieldName(),
							invalidDetail.getFormattedFieldValue(),
							invalidDetail.getInvalidType() + ParsingConstants.BLANK}, null)
							);
				}
			}
			invalidDetailsStr.trimToSize();
			if(invalidDetailsStr.toString().startsWith(ParsingConstants.OUTER_DELIM)) {
				invalidDetailsStr.deleteCharAt(0);
			}
		}
		return new String[] {getIdentifier(), ParsingConstants.BLANK
				+ invalidDetailsStr};
	}
	
	@Override
	public String toString() {
		return "ValidateableRecord [inValid=" + inValid + ", doReject="
				+ doReject + ", inValidFields=" + inValidFields + ", created="
				+ created + ", inValidDetails=" + inValidDetails + "]";
	}

	
	
}
