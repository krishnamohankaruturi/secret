package edu.ku.cete.domain.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import edu.ku.cete.domain.report.DashboardTestingSummary;

public class TestingSummary {

	private String assessmentProgram;
	private String subject;
	private String classroomId;
	private String todayCompleted;
	private String priorDayCompleted;
	private String yearCompleted;
	private String yearStudentsAssigned;
	private String yearStudentsCompleted;
	private String studentsPercentCompleted;
	private String priorDayReactivations;
	private String yearReactivations;
	private Attr attr;
	private String rowId;
	private CourseAttr courseAttr;
	
	/**
	 * @return the classroomId
	 */
	public String getClassroomId() {
		return classroomId;
	}
	/**
	 * @param classroomId the classroomId to set
	 */
	public void setClassroomId(String classroomId) {
		this.classroomId = classroomId;
	}
	/**
	 * @return the courseAttr
	 */
	public CourseAttr getcourseAttr() {
		return courseAttr;
	}
	/**
	 * @param courseAttr the courseAttr to set
	 */
	public void setcourseAttr(CourseAttr courseAttr) {
		this.courseAttr = courseAttr;
	}
	
	public String getAssessmentProgram() {
		return assessmentProgram;
	}
	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTodayCompleted() {
		return todayCompleted;
	}
	public void setTodayCompleted(String todayCompleted) {
		this.todayCompleted = todayCompleted;
	}
	public String getPriorDayCompleted() {
		return priorDayCompleted;
	}
	public void setPriorDayCompleted(String priorDayCompleted) {
		this.priorDayCompleted = priorDayCompleted;
	}
	public String getYearCompleted() {
		return yearCompleted;
	}
	public void setYearCompleted(String yearCompleted) {
		this.yearCompleted = yearCompleted;
	}
	public String getYearStudentsCompleted() {
		return yearStudentsCompleted;
	}
	public void setYearStudentsCompleted(String yearStudentsCompleted) {
		this.yearStudentsCompleted = yearStudentsCompleted;
	}
	//added Saikat
	public String getStudentsPercentCompleted() {
		return studentsPercentCompleted;
	}
	public void setStudentsPercentCompleted(String studentsPercentCompleted) {
		this.studentsPercentCompleted = studentsPercentCompleted;
	}
	//
	public String getPriorDayReactivations() {
		return priorDayReactivations;
	}
	public void setPriorDayReactivations(String priorDayReactivations) {
		this.priorDayReactivations = priorDayReactivations;
	}
	public String getYearReactivations() {
		return yearReactivations;
	}
	public void setYearReactivations(String yearReactivations) {
		this.yearReactivations = yearReactivations;
	}
	public Attr getAttr() {
		return attr;
	}
	public void setAttr(Attr attr) {
		this.attr = attr;
	}
	public String getYearStudentsAssigned() {
		return yearStudentsAssigned;
	}
	public void setYearStudentsAssigned(String yearStudentsAssigned) {
		this.yearStudentsAssigned = yearStudentsAssigned;
	}
	
	
	
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public static final TestingSummary convert(DashboardTestingSummary summary, boolean hasReactivationPermission, boolean hasMiddayRunCompleted, boolean showClassroomId){
		TestingSummary ts = new TestingSummary();
		ts.setAssessmentProgram(summary.getAssessmentProgram());
		ts.setSubject(summary.getContentArea());
		
		if(showClassroomId && summary.getClassroomId() != null) {
			ts.setClassroomId(summary.getClassroomId().toString());
		}
		ts.setPriorDayCompleted(summary.getCountSessionsCompletedLastSchoolDay().toString());
		if (hasMiddayRunCompleted){
			ts.setTodayCompleted(summary.getCountSessionsCompletedToday().toString());
		}else{
			ts.setTodayCompleted("n/a");
		}
		ts.setYearCompleted(summary.getCountSessionsCompletedThisYear().toString());
		ts.setYearStudentsAssigned(summary.getCountStudentsAssignedThisYear().toString());
		if (summary.getAssessmentProgramId().equals(11L) 
				|| summary.getAssessmentProgram().contains("ITI")
				|| summary.getAssessmentProgram().contains("Instructional")){
			ts.setYearStudentsCompleted("n/a");
		}else{
			ts.setYearStudentsCompleted(summary.getCountStudentsCompletedThisYear().toString());
		}
		if (!hasReactivationPermission || summary.getAssessmentProgramId().equals(3L)
				|| summary.getAssessmentProgram().contains("Instructional")
				|| summary.getAssessmentProgramId().equals(11L)){
			ts.setPriorDayReactivations("n/a");
			ts.setYearReactivations("n/a");
			ts.setStudentsPercentCompleted("n/a");
		}else{
			ts.setPriorDayReactivations(summary.getCountReactivatedLastSchoolDay().toString());
			ts.setYearReactivations(summary.getCountReactivatedThisYear().toString());
			DecimalFormat decimalFormatter = new DecimalFormat("##.#");
			String studentsPercentCompleted = String.valueOf(Double.valueOf(decimalFormatter.format(summary.getStudentsPercentCompletedThisYear()))) + "%";
			ts.setStudentsPercentCompleted(studentsPercentCompleted);
		}
		return ts;
	}
	
	public static final List<TestingSummary> convertList(List<DashboardTestingSummary> summaries, boolean hasReactivationPermission, boolean hasMiddayRunCompleted, boolean showClassroomId){
		List<TestingSummary> testingSummaries = new ArrayList<>(); 
		for (DashboardTestingSummary summary : summaries){
			testingSummaries.add(convert(summary, hasReactivationPermission, hasMiddayRunCompleted, showClassroomId));
		}
		return testingSummaries;
	}
}
