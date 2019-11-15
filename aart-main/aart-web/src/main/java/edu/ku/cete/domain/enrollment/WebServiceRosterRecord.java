package edu.ku.cete.domain.enrollment;

import java.util.Date;

/**
 * @author vittaly
 *
 */
public class WebServiceRosterRecord extends RosterRecord {

	 /**
	 * 
	 */
	private static final long serialVersionUID = -7242590407243535498L;

	/**
	 * createDate
	 */
	private Date createDate;
	 
	/**
	 * recordCommonId
	 */
	private String recordCommonId;	 				 
	 			 
	/**
	 * attendanceSchoolProgramIdentifier
	 */
	private String attendanceSchoolProgramIdentifier;
			
	/**
	 * rosterAYPSchoolIdentifier
	 */
	private String aypSchoolIdentifier;	
	
	
	private String accountabilityschoolidentifier;
	
	/**
	 * legalMiddleName
	 */
	private String legalMiddleName;
	
	/**
	 * studentGenerationCode
	 */
	private String studentGenerationCode;	
	
	/**
	 * studentGender
	 */
	private Integer studentGender;	
	
	/**
	 * studentBirthDate
	 */
	private Date studentBirthDate; 
	
	/**
	 * comprehensiveRace
	 */
	private String comprehensiveRace; 
	
	/**
	 * rosterCurrentGradeLevel
	 */
	private Long rosterCurrentGradeLevel; 
	
	/**
	 * rosterLocalStudentIdentifier
	 */
	private String rosterLocalStudentIdentifier;
	
	/**
	 * educatorSchoolIdentifier
	 */
	private String educatorSchoolIdentifier;
	
	/**
	 * KCCId
	 */
	private String stateSubjectCourseIdentifier;
	
	/**
	 * localCourseId
	 */
	private String localCourseId;
	
	/**
	 * educatorMiddleName
	 */
	private String educatorMiddleName;

	/**
	 * dateFormat
	 */
	private static final String dateFormat = "MM/dd/yyyy hh:mm:ss a";
	
	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {		
		return dateFormat;
	}
	
	/**
	 * @return
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return
	 */
	public String getRecordCommonId() {
		return recordCommonId;
	}

	/**
	 * @param recordCommonId
	 */
	public void setRecordCommonId(String recordCommonId) {
		this.recordCommonId = recordCommonId;
	}	

	/**
	 * @return
	 */
	public String getAttendanceSchoolProgramIdentifier() {
		return getSchoolIdentifier();
	}

	/**
	 * @param attendanceSchoolProgramIdentifier
	 */
	public void setAttendanceSchoolProgramIdentifier(
			String attendanceSchoolProgramIdentifier) {
		this.setSchoolIdentifier(attendanceSchoolProgramIdentifier);
	}

	/**
	 * @return
	 */
	public String getAypSchoolIdentifier() {
		return getEnrollment().getAypSchoolIdentifier();
	}

	/**
	 * @param rosterAYPSchoolIdentifier
	 */
	public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
		this.aypSchoolIdentifier= aypSchoolIdentifier;
		if(accountabilityschoolidentifier==null || accountabilityschoolidentifier.trim().isEmpty()){
			getEnrollment().setAypSchoolIdentifier(aypSchoolIdentifier);
		}
		
	}
	
	
	public String getRosterAYPSchoolIdentifier() {
		return getAypSchoolIdentifier();
	}

	public void setRosterAYPSchoolIdentifier(String rosterAYPSchoolIdentifier) {
		setAypSchoolIdentifier(rosterAYPSchoolIdentifier);
	}

	public String getAccountabilityschoolidentifier() {
		return getEnrollment().getAypSchoolIdentifier();
	}

	public void setAccountabilityschoolidentifier(String accountabilityschoolidentifier) {
		this.accountabilityschoolidentifier = accountabilityschoolidentifier	;
		 getEnrollment().setAccountabilityschoolidentifier(accountabilityschoolidentifier);
		if(accountabilityschoolidentifier!=null && !accountabilityschoolidentifier.trim().isEmpty()){
		  getEnrollment().setAypSchoolIdentifier(accountabilityschoolidentifier);
		}
		
	}

	/**
	 * @return
	 */
	public String getLegalMiddleName() {
		return getStudent().getLegalMiddleName();
	}

	/**
	 * @param legalMiddleName
	 */
	public void setLegalMiddleName(String legalMiddleName) {
		getStudent().setLegalMiddleName(legalMiddleName);
	}

	/**
	 * @return
	 */
	public String getStudentGenerationCode() {
		return getStudent().getGenerationCode();
	}

	/**
	 * @param studentGenerationCode
	 */
	public void setStudentGenerationCode(String studentGenerationCode) {		
		getStudent().setGenerationCode(studentGenerationCode);
	}

	/**
	 * @return
	 */
	public Integer getStudentGender() {
		return getStudent().getGender();
	}

	/**
	 * @param studentGender
	 */
	public void setStudentGender(Integer studentGender) {
		getStudent().setGender(studentGender);
	}

	/**
	 * @return
	 */
	public Date getStudentBirthDate() {
		return getStudent().getDateOfBirth();
	}

	/**
	 * @param studentBirthDate
	 */
	public void setStudentBirthDate(Date studentBirthDate) {
		getStudent().setDateOfBirth(studentBirthDate);
	}

	/**
	 * @return
	 */
	public String getComprehensiveRace() {
		return getStudent().getComprehensiveRace();
	}

	/**
	 * @param comprehensiveRace
	 */
	public void setComprehensiveRace(String comprehensiveRace) {
		getStudent().setComprehensiveRace(comprehensiveRace);
	}

	/**
	 * @return
	 */
	public Long getRosterCurrentGradeLevel() {
		return getEnrollment().getCurrentGradeLevel();
	}

	/**
	 * @param rosterCurrentGradeLevel
	 */
	public void setRosterCurrentGradeLevel(Long rosterCurrentGradeLevel) {
		getEnrollment().setCurrentGradeLevel(rosterCurrentGradeLevel);
	}

	/**
	 * @return
	 */
	public String getRosterLocalStudentIdentifier() {
		return getEnrollment().getLocalStudentIdentifier();
	}

	/**
	 * @param rosterLocalStudentIdentifier
	 */
	public void setRosterLocalStudentIdentifier(String rosterLocalStudentIdentifier) {
		getEnrollment().setLocalStudentIdentifier(rosterLocalStudentIdentifier);
	}


	/**
	 * @return
	 */
	public String getEducatorSchoolIdentifier() {
		return educatorSchoolIdentifier;
	}

	/**
	 * @param educatorSchoolIdentifier
	 */
	public void setEducatorSchoolIdentifier(String educatorSchoolIdentifier) {
		this.educatorSchoolIdentifier = educatorSchoolIdentifier;
	}

	/**
	 * @return
	 */
	public String getStateSubjectCourseIdentifier() {		
		return stateSubjectCourseIdentifier;
	}

	/**
	 * @param kCCId
	 */
	public void setStateSubjectCourseIdentifier(String stateSubjectCourseIdentifier) {
		this.stateSubjectCourseIdentifier = stateSubjectCourseIdentifier;
	}

	/**
	 * @return
	 */
	public String getLocalCourseId() {
		return localCourseId;
	}

	/**
	 * @param localCourseId
	 */
	public void setLocalCourseId(String localCourseId) {
		this.localCourseId = localCourseId;
	}

	/**
	 * @return
	 */
	public String getEducatorMiddleName() {
		return getEducator().getMiddleName();
	}

	/**
	 * @param educatorMiddleName
	 */
	public void setEducatorMiddleName(String educatorMiddleName) {		
		getEducator().setMiddleName(educatorMiddleName);
	}
	 
	
	public String getHispanicEthnicity(){
		String value = "0";
		if (getStudent().getHispanicEthnicity() != null && getStudent().getHispanicEthnicity().equals(true)){
			value="1";
		}
		return value;
	}
	public void setHispanicEthnicity(String flag){
        if (flag != null && flag.equalsIgnoreCase("1")){
        	getStudent().setHispanicEthnicity("true");
        }else if (flag != null && flag.equalsIgnoreCase("0")){
        	getStudent().setHispanicEthnicity("false");
        }
        else if (flag != null && flag.equalsIgnoreCase("2")){
        	getStudent().setHispanicEthnicity("Not Selected");
        }
        
	}
}
