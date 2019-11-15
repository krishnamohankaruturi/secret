/**
 * 
 */
package edu.ku.cete.domain;

/**
 * @author mahesh
 *   "userdata": {
    "fieldSummary1": 3370,
    "fieldSummary2": 462,
    "fieldSummary3": 3834,
    "name": "Totals:"
  }
 * TODO inheritance is not properly supported in JQGRID. So remove this
 * and add client side computation of Grand totals. Refer Node response report.
 */
public abstract class JsonSummaryData {

	private double fieldSummary1;
	private double fieldSummary2;
	private double fieldSummary3;
	private String name;	
	/**
	 * @return the fieldSummary1
	 */
	public final double getFieldSummary1() {
		return fieldSummary1;
	}
	/**
	 * @param fieldSummary1 the fieldSummary1 to set
	 */
	public final void setFieldSummary1(double fieldSummary1) {
		this.fieldSummary1 = fieldSummary1;
	}
	/**
	 * @return the fieldSummary2
	 */
	public final double getFieldSummary2() {
		return fieldSummary2;
	}
	/**
	 * @param fieldSummary2 the fieldSummary2 to set
	 */
	public final void setFieldSummary2(double fieldSummary2) {
		this.fieldSummary2 = fieldSummary2;
	}
	/**
	 * @return the fieldSummary3
	 */
	public final double getFieldSummary3() {
		return fieldSummary3;
	}
	/**
	 * @param fieldSummary3 the fieldSummary3 to set
	 */
	public final void setFieldSummary3(double fieldSummary3) {
		this.fieldSummary3 = fieldSummary3;
	}
	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JsonSummaryData [fieldSummary1=");
		builder.append(fieldSummary1);
		builder.append(", fieldSummary2=");
		builder.append(fieldSummary2);
		builder.append(", fieldSummary3=");
		builder.append(fieldSummary3);
		builder.append(", ");
		if (name != null) {
			builder.append("name=");
			builder.append(name);
		}
		builder.append("]");
		return builder.toString();
	}

}
