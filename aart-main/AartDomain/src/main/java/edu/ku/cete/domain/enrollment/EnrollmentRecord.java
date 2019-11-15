package edu.ku.cete.domain.enrollment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

/**
 * This class has student record therefore implements student record
 * @author m802r921
 * @version 1.0
 * @created 27-Mar-2012 2:30:01 PM
 */
public class EnrollmentRecord extends ValidateableRecord implements Serializable,StudentRecord {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -4498714009744986571L;
	/**
	 * student.
	 */
	private Student student = new Student();
	/**
	 * The school where the student is physically attending.
	 */
	private Organization attendanceSchool = new Organization();

	private Organization aypSchool = new Organization();
	/**
	 * enrollment cannot be null for enrollment record.
	 */
	private Enrollment enrollment = new Enrollment();
	/**
	 * current grade level the student is enrolled to this school for the given school year. 
	 */
	private String currentGradeLevel;
	
	private boolean dlmStatus;
	/**
	 * for batch upload
	 */
	private String lineNumber;
	private String assessmentProgram1;
	private String assessmentProgram2;
	private String assessmentProgram3;
	private List<Long> assessmentProgsIds;

	
	/**
		 * mm/dd/yyyy.
		 */
		private Date exitWithdrawalDate;
		/**
		 * <= 99.
		 */
		private String exitWithdrawalType;
		private Date schoolExitDate;
		private String exitReason;

	
	public List<Long> getAssessmentProgsIds() {
		return assessmentProgsIds;
	}
	public void setAssessmentProgsIds(List<Long> assessmentProgsIds) {
		this.assessmentProgsIds = assessmentProgsIds;
	}
	/**
	 *
	 */
	public EnrollmentRecord() {
	}
	@Override
	public String getIdentifier() {
		return ParsingConstants.BLANK + this.getStateStudentIdentifier();
	}
	
	public final void setAccountabilityschoolidentifier(String accountabilityschoolidentifierChar) throws AartParseException{
		enrollment.setAypSchoolIdentifier(accountabilityschoolidentifierChar);
		enrollment.setAccountabilityschoolidentifier(accountabilityschoolidentifierChar);		
	}
	
	public final String getAccountabilityschoolidentifier() throws AartParseException{
		return enrollment.getAccountabilityschoolidentifier();
		
	}

	/**
	 * @return the residenceDistrictIdentifier
	 */
	public final String getResidenceDistrictIdentifier() {
		return enrollment.getResidenceDistrictIdentifier();
	}

	/**
	 * @param residenceDistrictIdentifierChar the residenceDistrictIdentifier to set
	 */
	public final void setResidenceDistrictIdentifier(String residenceDistrictIdentifierChar)
	{
		enrollment.setResidenceDistrictIdentifier(residenceDistrictIdentifierChar);
	}

	/**
	 * @return the legalLastName
	 */
	public final String getLegalLastName() {
		return student.getLegalLastName();
	}

	/**
	 * @param legalLastNameChar the legalLastName to set
	 */
	public final void setLegalLastName(String legalLastNameChar) {
		student.setLegalLastName(legalLastNameChar);
	}

	/**
	 * @return the legalMiddleName
	 */
	public final String getLegalMiddleName() {
		return student.getLegalMiddleName();
	}

	/**
	 * @param legalMiddleNameChar the legalMiddleName to set
	 */
	public final void setLegalMiddleName(String legalMiddleNameChar) {
		student.setLegalMiddleName(legalMiddleNameChar);
	}

	/**
	 * @return the generationCode
	 */
	public final String getGenerationCode() {
		return student.getGenerationCode();
	}

	/**
	 * @param generationCodeChar the generationCode to set
	 */
	public final void setGenerationCode(String generationCodeChar) {
		student.setGenerationCode(generationCodeChar);
	}

	/**
	 * @return the gender
	 */
	public final Integer getGender() {
		return student.getGender();
	}
	/**
	 * @return the gender
	 */
	public final String getGenderStr() {
		return student.getGender() + ParsingConstants.BLANK;
	}
	/**
	 * @param genderChar the gender to set
	 */
	public final void setGenderStr(String genderChar) {
		student.setGenderStr(genderChar);
	}
	/**
	 * @param genderChar the gender to set
	 */
	public final void setGender(Integer genderChar) {
		student.setGender(genderChar);
	}
	/**
	 * @return the dateOfBirth
	 */
	public final Date getDateOfBirth() {
		return student.getDateOfBirth();
	}

	/**
	 * @param dob the dateOfBirth to set
	 */
	public final void setDateOfBirth(Date dob) {
		student.setDateOfBirth(dob);
	}
	/**
	 * @return the dateOfBirth
	 */
	public final String getDateOfBirthStr() {
		return student.getDateOfBirthStr();
	}

	/**
	 * @param dob the dateOfBirth to set
	 * @throws AartParseException AartParseException
	 */
	public final void setDateOfBirthStr(String dob)
			throws AartParseException {
		student.setDateOfBirthStr(dob);
	}
	/**
	 * @return the currentGradeLevel
	 */
	public final String getCurrentGradeLevel() {
		return this.currentGradeLevel;
	}

	/**
	 * @param currGradeLevel the currentGradeLevel to set
	 */
	public final void setCurrentGradeLevel(String currGradeLevel) {
		this.currentGradeLevel = currGradeLevel;
	}
	/**
	 * @return the localStudentIdentifier
	 */
	public final String getLocalStudentIdentifier() {
		return enrollment.getLocalStudentIdentifier();
	}

	/**
	 * @param localStudentId the localStudentIdentifier to set
	 */
	public final void setLocalStudentIdentifier(String localStudentId) {
		enrollment.setLocalStudentIdentifier(localStudentId);
	}

	/**
	 * @return the stateStudentIdentifier
	 */
	public final String getStateStudentIdentifier() {
		return student.getStateStudentIdentifier();
	}

	/**
	 * @param stateStudentId the stateStudentIdentifier to set
	 */
	public final void setStateStudentIdentifier(String stateStudentId)  {
		student.setStateStudentIdentifier(stateStudentId);
	}

	/**
	 * @return the currentSchoolYear
	 */
	public final int getCurrentSchoolYear() {
		return enrollment.getCurrentSchoolYear();
	}

	/**
	 * @param currentSchoolYr the currentSchoolYear to set
	 */
	public final void setCurrentSchoolYear(int currentSchoolYr) {
		enrollment.setCurrentSchoolYear(currentSchoolYr);
	}
	/**
	 * @return the currentSchoolYear
	 */
	public final String getCurrentSchoolYearStr() {
		return enrollment.getCurrentSchoolYear() + ParsingConstants.BLANK;
	}

	/**
	 * @param currentSchoolYr the currentSchoolYear to set
	 */
	public final void setCurrentSchoolYearStr(String currentSchoolYr) {
		enrollment.setCurrentSchoolYear(NumericUtil.parse(currentSchoolYr));
	}
	/**
	 * @return the attendanceSchoolProgramIdentifier
	 */
	public final String getAttendanceSchoolProgramIdentifier() {
		String identifier = "";
		if (attendanceSchool != null && attendanceSchool.getDisplayIdentifier() != null)
			identifier = attendanceSchool.getDisplayIdentifier();
		return identifier;
	}

	/**
	 * @param attendanceSchoolProgramId the attendanceSchoolProgramIdentifier to set
	 */
	public final void setAttendanceSchoolProgramIdentifier(
			String attendanceSchoolProgramId) {
		attendanceSchool.setDisplayIdentifier(attendanceSchoolProgramId);
	}
	
	/**
	 * @return the attendanceSchool
	 */
	public Organization getAttendanceSchool() {
		return attendanceSchool;
	}

	/**
	 * @param attendSchool the attendanceSchool to set
	 */
	public void setAttendanceSchool(Organization attendSchool) {
		if (attendSchool != null) {
			this.attendanceSchool = attendSchool;
		}
	}
	
	public Organization getAypSchool() {
		return aypSchool;
	}
	public void setAypSchool(Organization aypSchool) {
		if (aypSchool != null) {
			this.aypSchool = aypSchool;
		}
	}
	
	/**
	 * @return the attendanceSchool
	 */
	public long getAypSchoolId() {
		Long school = 0l;
		if (aypSchool != null && aypSchool.getId() != null)
			school = aypSchool.getId();
		return school;
	}

	/**
	 * @param attendSchool the attendanceSchool to set.
	 */
	public void setAypSchoolId(long aypSchoolId) {
		aypSchool.setId(aypSchoolId);
	}
	
	public long getAttendanceSchoolId() {
		Long school = -1L;
		if (attendanceSchool != null && attendanceSchool.getId() != null)
			school = attendanceSchool.getId();
		return school;
	}

	/**
	 * @param attendSchool the attendanceSchool to set.
	 */
	public void setAttendanceSchoolId(long attendSchoolId) {
		attendanceSchool.setId(attendSchoolId);
	}

	
	public final String getAypSchoolIdentifier() {
		return enrollment.getAypSchoolIdentifier();
	}
	  
	public final void setAypSchoolIdentifier(String aypSchoolIdentifierChar) throws AartParseException {
		enrollment.setAypSchoolIdentifier(aypSchoolIdentifierChar);
		aypSchool.setDisplayIdentifier(aypSchoolIdentifierChar);
	}
			
	/*
	public final String getAypSchoolIdentifier() {
		//return enrollment.getAypSchoolIdentifier();
		String identifier = "";
		if (aypSchool != null && aypSchool.getDisplayIdentifier() != null)
			identifier = aypSchool.getDisplayIdentifier();
		return identifier;
	}

	
	public final void setAypSchoolIdentifier(String aypSchoolIdentifierChar) throws AartParseException {
		aypSchool.setDisplayIdentifier(aypSchoolIdentifierChar);
		enrollment.setAypSchoolIdentifier(aypSchoolIdentifierChar);
	}
*/
	  
	
	/**
	 * @return the schoolEntryDate
	 */
	public final Date getSchoolEntryDate() {
		return enrollment.getSchoolEntryDate();
	}

	/**
	 * @param schoolEntryDt the schoolEntryDate to set
	 */
	public final void setSchoolEntryDate(Date schoolEntryDt) {
		this.enrollment.setSchoolEntryDate(schoolEntryDt);
	}

	/**
	 * @return the schoolEntryDate
	 */
	public final String getSchoolEntryDateStr() {
		return ParsingConstants.BLANK + DateUtil.format(enrollment.getSchoolEntryDate(), "MM/dd/yyyy");
	}
	/**
	 * @param schoolEntryDt the schoolEntryDate to set
	 */
	public final void setSchoolEntryDateStr(String schoolEntryDt) {
		enrollment.setSchoolEntryDate(DateUtil.parse(schoolEntryDt));
	}
	/**
	 * @return the districtEntryDate
	 */
	public final Date getDistrictEntryDate() {
		return enrollment.getDistrictEntryDate();
	}

	/**
	 * @param districtEntryDt the districtEntryDate to set
	 */
	public final void setDistrictEntryDate(Date districtEntryDt) {
		enrollment.setDistrictEntryDate(districtEntryDt);
	}	
	/**
	 * @return the districtEntryDate
	 */
	public final String getDistrictEntryDateStr() {
		return ParsingConstants.BLANK +  enrollment.getDistrictEntryDate();
	}
	/**
	 * @param districtEntryDt the districtEntryDate to set
	 */
	public final void setDistrictEntryDateStr(String districtEntryDt) {
		enrollment.setDistrictEntryDate(DateUtil.parse(districtEntryDt));
	}
	/**
	 * @return the stateEntryDate
	 */
	public final Date getStateEntryDate() {
		return enrollment.getStateEntryDate();
	}

	/**
	 * @param stateEntryDt the stateEntryDate to set
	 */
	public final void setStateEntryDate(Date stateEntryDt) {
		enrollment.setStateEntryDate(stateEntryDt);
	}
	/**
	 * @return the stateEntryDate
	 */
	public final String getStateEntryDateStr() {
		return ParsingConstants.BLANK + enrollment.getStateEntryDate();
	}
	/**
	 * @param stateEntryDt the stateEntryDate to set
	 */
	public final void setStateEntryDateStr(String stateEntryDt) {
		enrollment.setStateEntryDate(DateUtil.parse(stateEntryDt));
	}
	/**
	 * @return the comprehensiveRace
	 */
	public final String getComprehensiveRace() {
		return student.getComprehensiveRace();
	}

	/**
	 * @param comprehRace the comprehensiveRace to set
	 */
	public final void setComprehensiveRace(String comprehRace) {
		if (StringUtil.compare(comprehRace,
				ParsingConstants.FIVE_DIGIT_ZEROS)) {
			//TODO move this to field specification by updating the field specification row regex.
			throw new NumberFormatException(comprehRace +  "is Not Valid");
		}
		student.setComprehensiveRace(comprehRace);
	}

	/**
	 * @return the primaryDisabilityCode
	 */
	public final String getPrimaryDisabilityCode() {
		return student.getPrimaryDisabilityCode();
	}

	/**
	 * @param primaryDisCode the primaryDisabilityCode to set
	 */
	public final void setPrimaryDisabilityCode(String primaryDisCode) {
		student.setPrimaryDisabilityCode(primaryDisCode);
	}

	/**
	 * @return the giftedStudent
	 */
	public final Boolean getGiftedStudent() {
		return enrollment.getGiftedStudent();
	}

	/**
	 * @param gifStudent the giftedStudent to set
	 */
	public final void setGiftedStudent(Boolean gifStudent) {
		enrollment.setGiftedStudent(gifStudent);
	}
	/**
	 * @return the firstLanguage
	 */
	public final String getFirstLanguage() {
		return student.getFirstLanguage();
	}
	/**
	 * @param firstLang the firstLang to set
	 */
	public final void setFirstLanguage(String firstLang) {
		student.setFirstLanguage(firstLang);
	}

	/**
	 * @return the legalFirstName
	 */
	public final String getLegalFirstName() {
		return student.getLegalFirstName();
	}

	/**
	 * @param legalFirstNameChar the legalFirstName to set
	 */
	public final void setLegalFirstName(String legalFirstNameChar) {
		student.setLegalFirstName(legalFirstNameChar);
	}

	public final Student getStudent() {
		return student;
	}

	public final void setStudent(Student stud) {
		this.student = stud;
	}
	/**
	 * @return the studentId
	 */
	public final long getStudentId() {
		return student.getId();
	}

	/**
	 * @param studentId the studentId to set
	 */
	public final void setStudentId(long studentId) {
		student.setId(studentId);
	}
	/**
	 * @return {@link Enrollment}
	 */
	public final Enrollment getEnrollment() {
		return this.enrollment;
	}
	
	/**
	 * @param enroll {@link Enrollment}
	 */
	public final void setEnrollment(Enrollment enroll) {
		if (enroll != null) {
			this.enrollment = enroll;
		}
	}
	/**
	 * @return the string
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public Long getId() {
		return getEnrollment().getId();
	}
	public Long getId(int order) {
		Long id = null;
		if(order == 0) {
			id = getId();
		} else if(order == 1) {
			//student id is the second order identifier.
			id = getStudentId();
		/* } else if(order ==2) {
			//state student identifier is the 3rd order identifier.
			id = getStateStudentIdentifier(); */
		}
		return id;
	}

	public String getStringIdentifier(int order) {		
		String id = null;
		 if(order == 2) {
			//state student identifier is the 3rd order identifier.
			id = getStateStudentIdentifier(); 
		}
		return id;
	}
	
	public Boolean getDlmStatus(){
		return dlmStatus;
	}
	
	public void setDlmStatus(Boolean status){
		dlmStatus = status;
	}
	
	public Long[] getAssessmentProgramIds(){
		return student.getStudentAssessmentPrograms();
	}
	
	public void setAssessmentProgramIds(Long[] assessmentProgramIds) {
		student.setStudentAssessmentPrograms(assessmentProgramIds);
		
	}
	
	public Long getAssessmentProgramId(){
		return student.getAssessmentProgramId();
	}
	
	public void setAssessmentProgramId(Long assessmentProgramId) {
		student.setAssessmentProgramId(assessmentProgramId);
		
	}
	
	public String getEsolParticipationCode() {
		return student.getEsolParticipationCode();
	}
	public void setEsolParticipationCode(String esolParticipationCode) {
		student.setEsolParticipationCode(esolParticipationCode);
	}
	
	public Date getEsolProgramEntryDate() {
		return student.getEsolProgramEntryDate();
	}
	public void setEsolProgramEntryDate(Date esolProgramEntryDate) {
		student.setEsolProgramEntryDate(esolProgramEntryDate);
	}
	
	public Date getEsolProgramEndingDate() {
		return student.getEsolProgramEndingDate();
	}
	public void setEsolProgramEndingDate(Date esolProgramEndingDate) {
		student.setEsolProgramEndingDate(esolProgramEndingDate);
	}
	
	public Date getUsaEntryDate() {
		return student.getUsaEntryDate();
	}
	public void setUsaEntryDate(Date usaEntryDate) {
		student.setUsaEntryDate(usaEntryDate);
	}
	
	public String getHispanicEthnicity(){
		return student.getHispanicEthnicity();
	}
	public void setHispanicEthnicity(String flag){

		student.setHispanicEthnicity(flag);
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String getAssessmentProgram1() {
		return assessmentProgram1;
	}
	public void setAssessmentProgram1(String assessmentProgram1) {
		this.assessmentProgram1 = assessmentProgram1;
	}
	public String getAssessmentProgram2() {
		return assessmentProgram2;
	}
	public void setAssessmentProgram2(String assessmentProgram2) {
		this.assessmentProgram2 = assessmentProgram2;
	}
	public String getAssessmentProgram3() {
		return assessmentProgram3;
	}
	public void setAssessmentProgram3(String assessmentProgram3) {
		this.assessmentProgram3 = assessmentProgram3;
	}

	public Date getExitWithdrawalDate() {
		return exitWithdrawalDate;
	}
	public void setExitWithdrawalDate(Date exitWithdrawalDate) {
		this.exitWithdrawalDate = exitWithdrawalDate;
	}
	public String getExitWithdrawalType() {
		return exitWithdrawalType;
	}
	public void setExitWithdrawalType(String exitWithdrawalType) {
		this.exitWithdrawalType = exitWithdrawalType;
	}	
	
	public long getAccountabilityDistrictId() {
		Long accDistrictId = -1L;
		if (enrollment != null && enrollment.getAccountabilityDistrictId() != null)
			accDistrictId = enrollment.getAccountabilityDistrictId();
		return accDistrictId;
	}

	public void setAccountabilityDistrictId(Long accountabilityDistrictId) {
		enrollment.setAccountabilityDistrictId(accountabilityDistrictId);
	}
	
	public final void setAccountabilityDistrictIdentifier(String accountabilityDistrictIdentifierChar) throws AartParseException{
		enrollment.setAccountabilityDistrictIdentifier(accountabilityDistrictIdentifierChar);		
	}
	
	public final String getAccountabilityDistrictIdentifier(){
		return enrollment.getAccountabilityDistrictIdentifier();
		
	}

	public Date getSchoolExitDate() {
		return schoolExitDate;
	}
	public void setSchoolExitDate(Date schoolExitDate) {
		this.schoolExitDate = schoolExitDate;
	}
	public String getExitReason() {
		return exitReason;
	}
	public void setExitReason(String exitReason) {
		this.exitReason = exitReason;
	}

}