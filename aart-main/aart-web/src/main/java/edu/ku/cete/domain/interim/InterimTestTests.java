package edu.ku.cete.domain.interim;

import edu.ku.cete.domain.audit.AuditableDomain;

/**
 * @author venkat
 *
 */
public class InterimTestTests extends AuditableDomain {

	private static final long serialVersionUID = 4351898279312725791L;

	// Add all the Properties of InterimTestTests Table.
	private long id;
	private long interimTestId;
	private long testId;
	private String testType;
	private Boolean isActive;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getInterimTestId() {
		return interimTestId;
	}

	public void setInterimTestId(long interimTestId) {
		this.interimTestId = interimTestId;
	}

	public long getTestId() {
		return testId;
	}

	public void setTestId(long testId) {
		this.testId = testId;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
