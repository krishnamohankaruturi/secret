package edu.ku.cete.domain.dashboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import edu.ku.cete.domain.report.DashboardReactivations;
import edu.ku.cete.service.OrganizationService;

public class ReactivationsDetail {
	
	private static String DATE_FORMAT = "MM/dd/yyyy - hh:mm:ss a z";
	
	private String id;
	private String assessmentProgram;
	private String district;
	private String school;
	private String testName;
	private String student;
	private String reactivatedBy;
	private Date date;
	private TimeZone timezone;
	

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public String getReactivatedBy() {
		return reactivatedBy;
	}
	public void setReactivatedBy(String reactivatedBy) {
		this.reactivatedBy = reactivatedBy;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReactivatedDate() {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		format.setTimeZone(getTimezone());
		return format.format(date);
	}
	public TimeZone getTimezone() {
		return timezone;
	}
	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}
	
	public static final ReactivationsDetail convert(DashboardReactivations activation, OrganizationService orgService){
		ReactivationsDetail react = new ReactivationsDetail();
		react.setId("RA_"+activation.getId());
		react.setAssessmentProgram(activation.getAssessmentProgram());
		react.setDistrict(activation.getDistrictName());
		react.setSchool(activation.getSchoolName());
		react.setTestName(activation.getTestName());
		react.setStudent(activation.getStudentName());
		react.setReactivatedBy(activation.getReactivatedByName());
		react.setDate(activation.getReactivatedDate());
		TimeZone tz = orgService.getTimeZoneForOrganization(activation.getSchoolId());
		if (tz == null) {
			// default to central
			tz = TimeZone.getTimeZone("US/Central");
		}
		react.setTimezone(tz);
		return react;
	}
	
	public static final List<ReactivationsDetail> convertList(List<DashboardReactivations> activations, OrganizationService orgService){
		List<ReactivationsDetail> reactivations = new ArrayList<>(); 
		 Long rowId=1l;
		for (DashboardReactivations activation : activations){
			activation.setId(rowId);
			reactivations.add(convert(activation, orgService));
			rowId++;
		}
		return reactivations;
	}
	
}

