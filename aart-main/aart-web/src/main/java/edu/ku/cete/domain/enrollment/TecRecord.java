package edu.ku.cete.domain.enrollment;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.util.ParsingConstants;

public class TecRecord extends ValidateableRecord implements Serializable, StudentRecord {

	private static final long serialVersionUID = -748473829837576L;
	private Student student = new Student();
	private Organization attendanceSchool = new Organization();
	private Enrollment enrollment = new Enrollment();
	private String recordType;
	private String testType;
	private String subject;
	
	/*Used for batch upload*/
	private KidRecord kidRecord; 
	private WebServiceRosterRecord rosterRecord;
	private User user;
	private String exitDateStr;
	private String lineNumber;
	private String grade;
	private int currentSchoolYear;
	// For use by SIF API
	private String exitWithdrawalType;
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public TecRecord() {
	}
	@Override
	public String getIdentifier() {
		return ParsingConstants.BLANK + this.getStateStudentIdentifier();
	}
	
	public String getStringIdentifier(int order) {		
		String id = null;
		 if(order == 2) {
			//state student identifier is the 3rd order identifier.
			id = getStateStudentIdentifier(); 
		}
		return id;
	}
	
	public Long getId() {
		return getEnrollment().getId();
	}
	public Long getId(int order) {
		Long id = null;
		if(order == 0) {
			id = getId();
		} else if(order == 1) {
			id = getStudentId();
		}
		return id;
	}

	public final String getStateStudentIdentifier() {
		return student.getStateStudentIdentifier();
	}

	public final void setStateStudentIdentifier(String stateStudentId)  {
		student.setStateStudentIdentifier(stateStudentId);
	}

	public final String getAttendanceSchoolProgramIdentifier() {
		String identifier = "";
		if (attendanceSchool != null && attendanceSchool.getDisplayIdentifier() != null)
			identifier = attendanceSchool.getDisplayIdentifier();
		return identifier;
	}


	public final void setAttendanceSchoolProgramIdentifier(
			String attendanceSchoolProgramId) {
		attendanceSchool.setDisplayIdentifier(attendanceSchoolProgramId);
	}

	public final Student getStudent() {
		return student;
	}

	public final void setStudent(Student stud) {
		this.student = stud;
	}

	public final long getStudentId() {
		return student.getId();
	}

	public final void setStudentId(long studentId) {
		student.setId(studentId);
	}
	public Enrollment getEnrollment() {
		return enrollment;
	}
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public Date getExitDate(){
		return this.enrollment.getExitWithdrawalDate();
	}
	public String getExitDateStr(){
		
		return this.exitDateStr;
	}
	/**
	 * 
	 * @param exitDateStr
	 * @throws ParseException
	 * Added US16352 UploadTEC. Exitdate is not mandatory. can not convert empty string to Date in BeanWrapperFieldSetMapper
	 */
	public void setExitDateStr(String exitDateStr) throws ParseException{
		this.exitDateStr = exitDateStr;
		Date exitDate = null;
		ArrayList<SimpleDateFormat> formatters =new ArrayList<SimpleDateFormat>();
		try{
			if( exitDateStr != null && ! exitDateStr.isEmpty()){
				//SimpleDateFormat formatter = null;
				if(exitDateStr.indexOf("/") > 0  ){
					formatters.add(new SimpleDateFormat("MM/dd/yyyy"));
					formatters.add(new SimpleDateFormat("M/d/yyyy"));
				}else{
					formatters.add(new SimpleDateFormat("MM-dd-yyyy"));
				}
				
				// set date only when the format and date matches.
				for(SimpleDateFormat formatter:formatters){
					if(formatter.format(formatter.parse(exitDateStr)).equals(exitDateStr)){
						exitDate  = formatter.parse(exitDateStr);
					}
				}
			}	
			setExitDate(exitDate);
		}catch(ParseException e){
			// from csv reader try to convert to date. if it is not / or -. it may get exceptions. it is handled batch upload validate.
		}
	}
	
	public void setExitDate(Date exitDate){
		this.enrollment.setExitWithdrawalDate(exitDate);
	}
	public int getExitReason(){
		return this.enrollment.getExitWithdrawalType();
	}
	public void setExitReason(Integer exitReason){
		if( exitReason != null )
			this.enrollment.setExitWithdrawalType(exitReason);
	}
	public int getSchoolYear(){
		return this.enrollment.getCurrentSchoolYear();
	}
	public void setSchoolYear(int year){
		this.enrollment.setCurrentSchoolYear(year);
	}
	public Organization getAttendanceSchool() {
		return attendanceSchool;
	}
	public void setAttendanceSchool(Organization attendanceSchool) {
		this.attendanceSchool = attendanceSchool;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String testSubject) {
		this.subject = testSubject;
	}
	/**
	 * @return the string
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public KidRecord getKidRecord() {
		return kidRecord;
	}
	public void setKidRecord(KidRecord kidRecord) {
		this.kidRecord = kidRecord;
	}
	public WebServiceRosterRecord getRosterRecord() {
		return rosterRecord;
	}
	public void setRosterRecord(WebServiceRosterRecord rosterRecord) {
		this.rosterRecord = rosterRecord;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public String getExitWithdrawalType() {
		return exitWithdrawalType;
	}
	public void setExitWithdrawalType(String exitWithdrawalType) {
		this.exitWithdrawalType = exitWithdrawalType;
	}
	public int getCurrentSchoolYear() {
		return currentSchoolYear;
	}
	public void setCurrentSchoolYear(int currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}
}