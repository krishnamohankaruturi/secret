package edu.ku.cete.domain.test;

import java.util.Date;

import edu.ku.cete.domain.audit.AuditableDomain;

public class ContentFrameworkDetailDTO extends AuditableDomain {

    private String contentCodeForLM;

    private String descriptionForLM;

    private Long frameworkLevelForLM;

    private Long contentFrameWorkIdForLM;

	public String getContentCodeForLM() {
		return contentCodeForLM;
	}

	public void setContentCodeForLM(String contentCodeForLM) {
		this.contentCodeForLM = contentCodeForLM;
	}

	public String getDescriptionForLM() {
		return descriptionForLM;
	}

	public void setDescriptionForLM(String descriptionForLM) {
		this.descriptionForLM = descriptionForLM;
	}

	public Long getFrameworkLevelForLM() {
		return frameworkLevelForLM;
	}

	public void setFrameworkLevelForLM(Long frameworkLevelForLM) {
		this.frameworkLevelForLM = frameworkLevelForLM;
	}

	public Long getContentFrameWorkIdForLM() {
		return contentFrameWorkIdForLM;
	}

	public void setContentFrameWorkIdForLM(Long contentFrameWorkIdForLM) {
		this.contentFrameWorkIdForLM = contentFrameWorkIdForLM;
	}
}