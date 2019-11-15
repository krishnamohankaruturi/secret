package edu.ku.cete.domain.dashboard;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import edu.ku.cete.domain.report.DashboardTestingOutsideHours;
import edu.ku.cete.service.OrganizationService;

public class TestingOutsideHours {
	
	private static String DATE_FORMAT = "MM/dd/yyyy - hh:mm:ss a z";
	
	private String assessmentProgram;
	private String district;
	private String school;
	private String testName;
	private String student;
	private Date startedDate;
	private Date endedDate;
	private TimeZone timezone;
	private String legalLastName;
	private String legalFirstName;
	
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
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public Date getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(Date startedDate) {
		this.startedDate = startedDate;
	}
	public Date getEndedDate() {
		return endedDate;
	}
	public void setEndedDate(Date endedDate) {
		this.endedDate = endedDate;
	}
	public String getStarted() {
		if(startedDate != null){
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			format.setTimeZone(getTimezone());
			return format.format(startedDate);
		}else{
			return "";
		}
	}
	public String getEnded() {
		if(endedDate != null){
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			format.setTimeZone(getTimezone());
			return format.format(endedDate);
		}else{
			return "";
		}
		
	}
	public TimeZone getTimezone() {
		if(timezone != null){
			return timezone;
		}else{
			return TimeZone.getTimeZone("US/Central");
		}
	}
	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the legalLastName
	 */
	public String getLegalLastName() {
		return legalLastName;
	}
	/**
	 * @param legalLastName the legalLastName to set
	 */
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	/**
	 * @return the legalFirstName
	 */
	public String getLegalFirstName() {
		return legalFirstName;
	}
	/**
	 * @param legalFirstName the legalFirstName to set
	 */
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	
	public static final TestingOutsideHours convert(DashboardTestingOutsideHours toh, OrganizationService orgService){
		TestingOutsideHours testOHs = new TestingOutsideHours();
		testOHs.setAssessmentProgram(toh.getAssessmentProgram());
		testOHs.setId("TOH_"+toh.getId());
		testOHs.setDistrict(toh.getDistrictName());
		testOHs.setSchool(toh.getSchoolName());
		testOHs.setTestName(toh.getTestName());
		testOHs.setStudent(toh.getStudentName());
		testOHs.setStartedDate(toh.getStartDateTime());
		testOHs.setEndedDate(toh.getEndDateTime());
		testOHs.setLegalFirstName(toh.getLegalFirstName());
		testOHs.setLegalLastName(toh.getLegalLastName());
		TimeZone tz = orgService.getTimeZoneForOrganization(toh.getSchoolId());
		if (tz == null) {
			// default to central
			tz = TimeZone.getTimeZone("US/Central");
		}
		testOHs.setTimezone(tz);
		return testOHs;
	}
	
	public static final List<TestingOutsideHours> convertList(List<DashboardTestingOutsideHours> tohs, OrganizationService orgService){
		List<TestingOutsideHours> testOHs = new ArrayList<>(); 
		 Long rowId=1l;
		for (DashboardTestingOutsideHours toh : tohs){
			toh.setId(rowId);
			testOHs.add(convert(toh, orgService));
			rowId++;
		}
		return testOHs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}

