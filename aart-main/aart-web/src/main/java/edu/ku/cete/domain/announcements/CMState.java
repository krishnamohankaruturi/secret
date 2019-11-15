package edu.ku.cete.domain.announcements;

import java.math.BigInteger;

public class CMState {
	private BigInteger stateId;
	private BigInteger comminicationMessageId;
	private BigInteger roleId;
	

	public BigInteger getComminicationMessageId() {
		return comminicationMessageId;
	}

	public void setComminicationMessageId(BigInteger comminicationMessageId) {
		this.comminicationMessageId = comminicationMessageId;
	}

	public BigInteger getStateId() {
		return stateId;
	}

	public void setStateId(BigInteger stateId) {
		this.stateId = stateId;
	}

	public BigInteger getRoleId() {
		return roleId;
	}

	public void setRoleId(BigInteger roleId) {
		this.roleId = roleId;
	}

}
