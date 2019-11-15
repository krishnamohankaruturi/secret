package edu.ku.cete.domain.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import edu.ku.cete.domain.report.DashboardShortDurationTesting;
import edu.ku.cete.service.OrganizationService;

public class ShortDurationTesting {
	
	private String assessmentProgram;
	private String district;
	private String school;
	private String teacher;
	private String subject;
	private String grade;
	private String stateStudentIdentifier;
	private String student;	
	private String testName;
	private Long itemCount;
	private String allCorrectIndicator;
	private String testTimeSpan;
	private String startedDate;
	private String endedDate;
	private TimeZone timezone;
	private String rosterName;
	private String studentLastName;
	private String studentFirstName;
	
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
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Long getItemCount() {
		return itemCount;
	}
	public void setItemCount(Long itemCount) {
		this.itemCount = itemCount;
	}
	public String getAllCorrectIndicator() {
		return allCorrectIndicator;
	}
	public void setAllCorrectIndicator(String allCorrectIndicator) {
		this.allCorrectIndicator = allCorrectIndicator;
	}
	public String getTestTimeSpan() {
		return testTimeSpan;
	}
	public void setTestTimeSpan(String testTimeSpan) {
		this.testTimeSpan = testTimeSpan;
	}
	public String getStartedDate() {
		return startedDate;
	}
	public void setStartedDate(String startedDate) {
		this.startedDate = startedDate;
	}
	public String getEndedDate() {
		return endedDate;
	}
	public void setEndedDate(String endedDate) {
		this.endedDate = endedDate;
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
	public String getRosterName() {
		return rosterName;
	}
	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	
	public static final ShortDurationTesting convert(DashboardShortDurationTesting tsd, OrganizationService orgService){
		ShortDurationTesting testSDs = new ShortDurationTesting();
		testSDs.setAssessmentProgram(tsd.getAssessmentProgram());
		testSDs.setId("TSD_"+tsd.getId());
		testSDs.setDistrict(tsd.getDistrictName());
		testSDs.setSchool(tsd.getSchoolName());
		testSDs.setTeacher(tsd.getTeacherName());
		testSDs.setSubject(tsd.getContentArea());
		testSDs.setGrade(tsd.getGrade());
		testSDs.setStateStudentIdentifier(tsd.getStateStudentIdentifier());
		testSDs.setStudent(tsd.getStudentName());
		testSDs.setTestName(tsd.getTestName());
		testSDs.setItemCount(tsd.getTestItemCount());
		testSDs.setAllCorrectIndicator(tsd.getAllCorrectIndicator());
		testSDs.setTestTimeSpan(tsd.getTestTimeSpan());
		testSDs.setStartedDate(tsd.getStarted());
		testSDs.setEndedDate(tsd.getEnded());
		testSDs.setRosterName(tsd.getRosterName());
		testSDs.setStudentFirstName(tsd.getStudentFirstName());
		testSDs.setStudentLastName(tsd.getStudentLastName());
		TimeZone tz = orgService.getTimeZoneForOrganization(tsd.getSchoolId());
		if (tz == null) {
			// default to central
			tz = TimeZone.getTimeZone("US/Central");
		}
		testSDs.setTimezone(tz);
		return testSDs;
	}
	
	public static final List<ShortDurationTesting> convertList(List<DashboardShortDurationTesting> tsds, OrganizationService orgService){
		List<ShortDurationTesting> testSDs = new ArrayList<>(); 
		 Long rowId=1l;
		for (DashboardShortDurationTesting tsd : tsds){
			tsd.setId(rowId);
			testSDs.add(convert(tsd, orgService));
			rowId++;
		}
		return testSDs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}

