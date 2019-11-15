package edu.ku.cete.domain.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.audit.AuditableDomain;

public class PnpStateSettings extends AuditableDomain {

	private static final long serialVersionUID = 2244877250983307163L;

	// Persistent fields.
	private Long pianacId;

	private Long stateId;

	private Long assessmentProgramId;

	private String viewOption;

	// Non-Persistent fields.
	private String assessmentProgram;

	private String stateName;

	private Long categoryId;
	
	private List<Long> childSettingIds;
	
	private Map<Long, PnpStateSettings> childSettings = new HashMap<Long, PnpStateSettings>();

	public Long getPianacId() {
		return pianacId;
	}

	public void setPianacId(Long pianacId) {
		this.pianacId = pianacId;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getViewOption() {
		return viewOption;
	}

	public void setViewOption(String viewOption) {
		this.viewOption = viewOption;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public List<Long> getChildSettingIds() {
		return childSettingIds;
	}

	public void setChildSettingIds(List<Long> childSettingIds) {
		this.childSettingIds = childSettingIds;
	}

	public Map<Long, PnpStateSettings> getChildSettings() {
		return childSettings;
	}

	public void setChildSettings(Map<Long, PnpStateSettings> childSettings) {
		this.childSettings = childSettings;
	}


}