package edu.ku.cete.domain.dashboard;

import java.util.ArrayList;
import java.util.List;

import edu.ku.cete.domain.report.DashboardReactivations;

public class ReactivationsSummary {
	
	private String assessmentProgram;
	private String district;
	private String school;
	private String testName;
	private Long count;
	private String reactivatedBy;
	private String id;
	
	public String getAssessmentProgram() {
		return assessmentProgram;
	}
	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getReactivatedBy() {
		return reactivatedBy;
	}
	public void setReactivatedBy(String reactivatedBy) {
		this.reactivatedBy = reactivatedBy;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public static final ReactivationsSummary convert(DashboardReactivations activation){
		ReactivationsSummary react = new ReactivationsSummary();
		react.setAssessmentProgram(activation.getAssessmentProgram());
		react.setId("RS_"+activation.getId());
		react.setDistrict(activation.getDistrictName());
		react.setSchool(activation.getSchoolName());
		react.setTestName(activation.getTestName());
		react.setCount(activation.getCount());
		react.setReactivatedBy(activation.getReactivatedByName());
		return react;
	}
	
	public static final List<ReactivationsSummary> convertList(List<DashboardReactivations> activations){
		List<ReactivationsSummary> reactivations = new ArrayList<>(); 
		 Long rowId=1l;
		for (DashboardReactivations activation : activations){
			activation.setId(rowId);
			reactivations.add(convert(activation));
			rowId++;
		}
		return reactivations;
	}
	
}

