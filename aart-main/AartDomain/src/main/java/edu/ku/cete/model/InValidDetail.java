/**
 * 
 */
package edu.ku.cete.model;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

/**
 * @author m802r921
 *
 */
/**
 * @author m802r921
 *
 */
public class InValidDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2752102546673012806L;

	/**
	 * field that is invalid.
	 */
	private String fieldName;
	
	private String actualFieldName;
	/**
	 * the value that is invalid.
	 */
	private String fieldValue;
	/**
	 * This means the record has to be rejected.
	 */
	private boolean rejectRecord = false;
	/**
	 * say invalid not found etc.
	 */
	private InvalidTypes invalidType = InvalidTypes.IN_VALID;
	
	private String reason;

	/**
     * @param fieldNam {@link String}
     * @param fieldVal {@link String}
     */
    private InValidDetail(String fieldNam, String fieldVal) {
    	this.actualFieldName = fieldNam;
    	setFieldName(fieldNam);
        this.fieldValue = fieldVal;
    }

	/**
	 * @param fieldNam {@link String}
	 * @param fieldVal {@link String}
	 * @param rejectRecord2 {@link Boolean}
	 * @param invalidType {@link InvalidTypes}
	 */
	public InValidDetail(String fieldNam, String fieldVal, boolean rejectRecord2, InvalidTypes invalidType) {
        this(fieldNam, fieldVal, rejectRecord2);
        this.invalidType = invalidType;
    }
	/**
	 * @param fieldNam {@link String}
	 * @param fieldVal {@link String}
	 * @param rejectRecord2 {@link Boolean}
	 */
	public InValidDetail(String fieldNam, String fieldVal, boolean rejectRecord2) {
		this(fieldNam, fieldVal);
		setRejectRecord(rejectRecord2);
	}
	/**
	 * @param fieldNam {@link String}
	 */
	private void setFieldName(String fieldNam) {
		this.fieldName = StringUtil.convertToNormalCase(fieldNam);
	}
	/**
	 * @param fieldNam {@link String}
	 */
	public void setFieldNameWithoutConversion(String fieldNam) {
		this.fieldName = fieldNam;
	}	
	/**
	 * @param fieldNam {@link String}
	 * @param fieldVal {@link String}
	 * @return {@link InValidDetail}
	 */
	public static InValidDetail getInstance(String fieldNam, String fieldVal) {
		return new InValidDetail(fieldNam, fieldVal);
	}
	/**
	 * @param fieldNam {@link String}
	 * @param fieldVal {@link String}
	 * @param rejectRecord {@link Boolean}
	 * @return {@link InValidDetail}
	 */
	public static InValidDetail getInstance(String fieldNam, String fieldVal, boolean rejectRecord) {
		return new InValidDetail(fieldNam, fieldVal, rejectRecord);
	}
	/**
	 * @param fieldNam {@link String}
	 * @param fieldVal {@link String}
	 * @param rejectRecord {@link Boolean}
	 * @param invalidType {@link InvalidTypes}
	 * @return {@link InValidDetail}
	 */
	public static InValidDetail getInstance(String fieldNam, String fieldVal,
			boolean rejectRecord, InvalidTypes invalidType) {
       return new InValidDetail(fieldNam, fieldVal, rejectRecord, invalidType);
       }
	/**
	 * @return the fieldName
	 */
	public final String getFieldName() {
		return fieldName;
	}
	/**
	 * @return the fieldValue
	 */
	public final String getFieldValue() {
		return fieldValue;
	}
	/**
	 * @return the fieldValue
	 */
	public final String getFormattedFieldValue() {
		String formattedValue = "";
		if (StringUtils.isEmpty(fieldValue == null ? fieldValue : fieldValue.trim())) {
			formattedValue = ParsingConstants.BLANK_VALUE;
		}
		if (fieldValue!=null && fieldValue.startsWith(" ")){
			formattedValue = ParsingConstants.BLANK_VALUE + ParsingConstants.BLANK_SPACE + fieldValue;
		} else{
			formattedValue = ParsingConstants.VALUE + ParsingConstants.BLANK_SPACE + fieldValue;
		}
		return formattedValue;
	}
	
	/*public final boolean isRejectRecord() {
		return rejectRecord;
	} Causes Reflection conflicting getter errors in serializer*/
	/**
	 * @return the rejectRecord
	 */
	public final boolean getRejectRecord() {
		return rejectRecord;
	}
	/**
	 * @param rejRecord the rejectRecord to set
	 */
	public final void setRejectRecord(boolean rejRecord) {
		this.rejectRecord = rejRecord;
	}
	/**
     * @return the invalidType
     */
    public final InvalidTypes getInvalidType() {
        return invalidType;
    }
    /**
     * @param invalidTyp the invalidType to set
     */
    public final void setInvalidType(InvalidTypes invalidTyp) {
        this.invalidType = invalidTyp;
    }
    /**
	 * @return {@link String}
	 */
	public final String getInValidMessage() {
		String message=new String();
		if(fieldName.equalsIgnoreCase("State Course Code")){
			if(reason.equalsIgnoreCase("BIO")){
				message= "the student's grade does not apply for the selected subject and course."+ (invalidType == null ? "" : " " + invalidType.getInvalidTypeName()); 
			}else{
				message = "Course code" + (fieldValue == null || fieldValue.isEmpty() ? ("") : (" with value(s) " +fieldValue)) + (reason == null || reason.isEmpty() ? "" : " " + reason) + (invalidType == null ? "" : " " + invalidType.getInvalidTypeName());
			}
			

		}
		else if(! fieldName.contains("non Score Reason")){
			message = "the " + fieldName + (fieldValue == null || fieldValue.isEmpty() ? ("") : (" with value(s) " +fieldValue)) + (reason == null || reason.isEmpty() ? "" : " " + reason) + (invalidType == null ? "" : " " + invalidType.getInvalidTypeName());
		}else{
			String itemName=fieldName;
			itemName=itemName.replace("non", " Non");
			 message = "item " + itemName + (fieldValue == null || fieldValue.isEmpty() ? ("") : (" with value(s) " +fieldValue)) + (reason == null || reason.isEmpty() ? "" : " " + reason) + (invalidType == null ? "" : " " + invalidType.getInvalidTypeName());
		}
		return message;
	}
	/**
	 * @return {@link String}
	 */
	public final String getAlertMessage() {
		String message = reason == null || reason.isEmpty() ? "" : " " + reason;
		return message;
	}
	
	/**
	 * @return {@link String}
	 */
	public String getActualFieldName() {
		return actualFieldName;
	}
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return {@link String}
	 */
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
