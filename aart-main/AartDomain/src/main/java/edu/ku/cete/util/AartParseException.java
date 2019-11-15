/**
 * 
 */
package edu.ku.cete.util;

import java.lang.reflect.Type;

/**
 * @author m802r921
 *
 */
public class AartParseException extends Exception {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 2853335766825399171L;
	/**
	 * Record Name.
	 */
	private String recordName;
	/**
	 * limit.
	 */
	private int limit;
	/**
	 * record identifier.
	 */
	private String identifier;
	/**
	 * type that failed conversion.
	 */
	private Type type;
	/**
	 * The value that it is being attempted to set.
	 */
	private String attemptedValue;

	/**
	 *
	 * @param message {@link String}
	 */
	public AartParseException(String message) {
	    super(message);
	}

	/**
	 * @param str {@link String}
	 * @param value {@link String}
	 * @param e {@link Exception}
	 */
	public AartParseException(String str, String value, Exception e) {
		super(e);
		this.recordName = str;
		this.setAttemptedValue(value);
	}
	/**
	 * @param str {@link String}
	 * @param typ {@link Type}
	 * @param limit2 {@link Integer}
	 */
	public AartParseException(String str, Type typ, int limit2) {
		this.recordName = str;
		this.limit = limit2;
		this.setType(typ);
	}
	/**
	 * @param str {@link String}
	 * @param limit2 {@link Integer}
	 */
	public AartParseException(String str, int limit2) {
		this.recordName = str;
		this.limit = limit2;
	}
	/**
	 * @param str {@link String}
	 * @param message {@link String}
	 * @param limit2 {@link Integer}
	 */
	public AartParseException(String str, String message, int limit2) {
		super(message);
		this.recordName = str;
		this.limit = limit2;
	}
	/**
	 * @param e {@link Exception}
	 */
	public AartParseException(Exception e) {
		super(e);
	}
	/**
	 * @param record the recordName to set
	 */
	public final void setRecordName(String record) {
		this.recordName = record;
	}
	/**
	 * @return the recordName
	 */
	public final String getRecordName() {
		return recordName;
	}
	/**
	 * @param lt the limit to set
	 */
	public final void setLimit(int lt) {
		this.limit = lt;
	}
	/**
	 * @return the limit
	 */
	public final int getLimit() {
		return limit;
	}
	/**
	 * @return the type
	 */
	public final Type getType() {
		return type;
	}

	/**
	 * @param typ the type to set
	 */
	public final void setType(Type typ) {
		this.type = typ;
	}

	/**
	 * @param id the identifier to set.
	 */
	public final void setIdentifier(String id) {
		this.identifier = id;
	}
	/**
	 * @return the identifier.
	 */
	public final String getIdentifier() {
		return identifier;
	}
	/**
	 * @return the attemptedValue
	 */
	public final String getAttemptedValue() {
		return attemptedValue;
	}
	/**
	 * @param attemptValue the attemptedValue to set
	 */
	public final void setAttemptedValue(String attemptValue) {
		this.attemptedValue = attemptValue;
	}
}
