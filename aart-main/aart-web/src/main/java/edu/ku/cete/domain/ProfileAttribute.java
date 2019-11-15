package edu.ku.cete.domain;

public class ProfileAttribute {
	private String attrName;
	private String attrContainer;
	private Long attrContainerId;
	private String attrValue;

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}

	public String getAttrContainer() {
		return attrContainer;
	}

	public void setAttrContainer(String attrContainer) {
		this.attrContainer = attrContainer;
	}

	public Long getAttrContainerId() {
		return attrContainerId;
	}

	public void setAttrContainerId(Long attrContainerId) {
		this.attrContainerId = attrContainerId;
	}

	public String getAttrValue() {
		return attrValue;
	}

	public void setAttrValue(String attrValue) {
		this.attrValue = attrValue;
	}
}
