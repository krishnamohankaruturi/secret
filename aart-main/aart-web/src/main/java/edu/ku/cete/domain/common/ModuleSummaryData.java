package edu.ku.cete.domain.common;

import edu.ku.cete.domain.JsonSummaryData;

public class ModuleSummaryData extends JsonSummaryData {
	/**
	 * @return the numberOfModules
	 */
	public double getNumberOfModules() {
		return getFieldSummary1();
	}

	/**
	 * @param numberOfModules the numberOfModules to set
	 */
	public void setNumberOfModules(double numberOfModules) {
		this.setFieldSummary1(numberOfModules);
	}
}
