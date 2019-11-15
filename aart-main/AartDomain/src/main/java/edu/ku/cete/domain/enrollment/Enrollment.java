/**
 * 
 */
package edu.ku.cete.domain.enrollment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.util.ParsingConstants;


/**
 * @author m802r921
 *
 */
public class Enrollment extends TraceableEntity implements Serializable{

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 2351270568810842648L;
	/**
	 * primary key.
	 */
	private long id;
	/**
	 *Allowable values are: ENRL Funding and Enrollment.
	 *TEST Assessment information; EOYA End-of-year reporting; EXIT for Exit records.
	 * Limit 4.
	 */
	private String recordType;
	/**
	 * The unique number that has been assigned to the aypSchool building by the state.
	 * Limit 10.
	 */
	private String aypSchoolIdentifier;
	
	private String accountabilityschoolidentifier;
	/**
	 * Limit 5.
	 */
	private String residenceDistrictIdentifier;
	/**
	 * Limit 10.
	 */
	private String generationCode;
	/**
	 * Allowable values are: 00 = Birth &ndash; 2 years old, includes Special
	 * Education Infant/Toddler (optional group); 01 = 3-Yr-Old Preschooler; 02 = 4-Yr-
	 * Old Preschooler; 03 = 5-Yr-Old Preschooler; 04 = Four-Year-Old At-Risk; 05 =
	 * Kindergarten; 06 = First Grade; 07 = Second Grade; 08 = Third Grade; 09 =
	 * Fourth Grade; 10 = Fifth Grade; 11 = Sixth Grade; 12 = Seventh Grade; 13 =
	 * Eighth Grade; 14 = Ninth Grade; 15 = Tenth Grade; 16 = Eleventh Grade; 17 =
	 * Twelfth Grade; 18 = Not Graded.
	 */
	private Long currentGradeLevel;
	
	
	private String currentGradeLevelCode;
	/**
	 * <= 9999.
	 */
	private int currentSchoolYear;
	/**
	 * Limit 4.
	 */
	//private String attendanceSchoolProgramIdentifier;
	/**
	 * mm/dd/yyyy.
	 */
	private Date schoolEntryDate;
	/**
	 * mm/dd/yyyy.
	 */
	private Date districtEntryDate;
	/**
	 * mm/dd/yyyy.
	 */
	private Date stateEntryDate;
	/**
	 * mm/dd/yyyy.
	 */
	private Date exitWithdrawalDate;
	/**
	 * <= 99.
	 */
	private int exitWithdrawalType;
	/**
	 * Limit 1.
	 */
	private String specialCircumstancesTransferChoice;
	/**
	 * Limit 5.
	 */
	private Boolean giftedStudent;
	/**
	 *
	 */
	private Date specialEdProgramEndingDate;
	/**
	 * Limit 1.
	 */
	private String qualifiedFor504;
	/**
	 * Limit 2.
	 */
	private String firstLanguage;
	/**
	 * Limit 10.
	 */
	private String testSubject;
	/**
	 * student id.
	 */
	private long studentId;
	
	/**
	 * restriction id.
	 */
	private long restrictionId;
	/**
	 * student.
	 */
	private Student student = new Student();
	/**
	 * id of a student with in the attendance school.
	 */
	private String localStudentIdentifier;
	/**
	 * attendance the student is attending.
	 */
	private Organization attendanceSchool = new Organization();
	/**
	 * attendance school id.
	 */
	private long attendanceSchoolId;
	
	/**
	 * Reasons why this is invalid.
	 */
	private String inValidFields ="";
	/**
	 * invalid.
	 */
	private boolean inValid;
	
	private String rosterAssigned;
	
	//scriptbees
	
	private String currentGrade;

	private String sourceType;
	
	
	
	private long aypSchoolId;
	
	private GradeCourse gradeCourse = new GradeCourse();
	
	private Roster roster = new Roster();

	private Long subjectAreaId;
	
	private boolean previousEnrollmentExists;
	
	/**
	 * invalid details.
	 */
	private List<InValidDetail> inValidDetails = new ArrayList<InValidDetail>();
	
	private Long contentAreaId;
	
	private Long assessmentProgramId;
	
	private boolean active;
	
	private List<SubjectArea> subjectAreaList;
    private Long attendanceSchoolDistrictId;
        
    private String accountabilityDistrictIdentifier;
	private Long accountabilitySchoolId;
    private Long accountabilityDistrictId;
    
	private String schoolName;
	private String districtName;
	private String accountabilitySchoolName;
	private String accountabilityDistrictName;
	
	private String externalId;
        
	/**
	 * @return the id
	 */
	public final long getId() {
		return id;
	}

	/**
	 * @param id2 the id to set
	 */
	public final void setId(long id2) {
		this.id = id2;
	}
	/**
	 * @return the recordType
	 */
	public final String getRecordType() {
		return recordType;
	}
	/**
	 * @param recordTyp the recordType to set
	 */
	public final void setRecordType(String recordTyp) {
		this.recordType = recordTyp;
	}
	/**
	 * @return the aypSchoolIdentifier
	 */
	public final String getAypSchoolIdentifier() {
		return this.aypSchoolIdentifier;
	}
	/**
	 * @param aypSchoolId the aypSchoolIdentifier to set
	 */
	public final void setAypSchoolIdentifier(String aypSchoolId) {
		this.aypSchoolIdentifier = aypSchoolId;
	}
	/**
	 * @return the residenceDistrictIdentifier
	 */
	public final String getResidenceDistrictIdentifier() {
		return residenceDistrictIdentifier;
	}
	/**
	 * @param residenceDistrictId the residenceDistrictIdentifier to set
	 */
	public final void setResidenceDistrictIdentifier(String residenceDistrictId) {
		this.residenceDistrictIdentifier = residenceDistrictId;
	}
	
	
	public String getAccountabilityschoolidentifier() {
		return accountabilityschoolidentifier;
	}

	public void setAccountabilityschoolidentifier(
			String accountabilityschoolidentifier) {
		this.accountabilityschoolidentifier = accountabilityschoolidentifier;
	}

	/**
	 * @return the legalLastName
	 */
	public final String getLegalLastName() {
		return student.getLegalLastName();
	}
	/**
	 * @param legalLastName the legalLastName to set
	 */
	public final void setLegalLastName(String legalLastName) {
		student.setLegalLastName(legalLastName);
	}
	/**
	 * @return the legalMiddleName
	 */
	public final String getLegalMiddleName() {
		return student.getLegalMiddleName();
	}
	/**
	 * @param legalMiddleName the legalMiddleName to set
	 */
	public final void setLegalMiddleName(String legalMiddleName) {
		student.setLegalMiddleName(legalMiddleName);
	}
	/**
	 * @return the generationCode
	 */
	public final String getGenerationCode() {
		return generationCode;
	}
	/**
	 * @param genCode the generationCode to set
	 */
	public final void setGenerationCode(String genCode) {
		this.generationCode = genCode;
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
	 * @param gen the gender to set
	 */
	public final void setGender(Integer gen) {
		student.setGender(gen);
	}
	/**
	 * @param gen the gender to set
	 */
	public final void setGenderStr(String gen) {
		student.setGenderStr(gen);
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
	
	
	
	public String getCurrentGrade() {
		return currentGrade;
	}

	public void setCurrentGrade(String currentGrade) {
		this.currentGrade = currentGrade;
	}

	/**
	 * @return the currentGradeLevel
	 */
	
	
	public final Long getCurrentGradeLevel() {
		return currentGradeLevel;
	}
	/**
	 * @param currGradeLevel the currentGradeLevel to set
	 */
	public final void setCurrentGradeLevel(Long currGradeLevel) {
		this.currentGradeLevel = currGradeLevel;
	}
	/**
	 * @param grade the currentGradeLevel to set
	 */
//	public final void setCurrentGradeLevel(GradeCourse grade) {
//		if (grade != null) {
//			this.currentGradeLevel = grade.getId();
//		} else {
//			this.currentGradeLevel = null;
//		}
//	}

	/**
	 * @return the localStudentIdentifier
	 */
	public final String getLocalStudentIdentifier() {
		return localStudentIdentifier;
	}
	/**
	 * @param localStudentId the localStudentIdentifier to set
	 */
	public final void setLocalStudentIdentifier(String localStudentId) {
		this.localStudentIdentifier
		= localStudentId;
	}
	/**
	 * @return the stateStudentIdentifier
	 */
	public final String getStateStudentIdentifier() {
		return student.getStateStudentIdentifier();
	}
	/**
	 * @param stateStudentIdentifier the stateStudentIdentifier to set
	 */
	public final void setStateStudentIdentifier(String stateStudentIdentifier) {
		student.setStateStudentIdentifier(stateStudentIdentifier);
	}
	/**
	 * @return the currentSchoolYear
	 */
	public final int getCurrentSchoolYear() {
		return currentSchoolYear;
	}
	/**
	 * @param currentSchoolYr the currentSchoolYear to set
	 */
	public final void setCurrentSchoolYear(int currentSchoolYr) {
		this.currentSchoolYear = currentSchoolYr;
	}
	/**
	 * @return the attendanceSchoolProgramIdentifier
	 */
	public final String getAttendanceSchoolProgramIdentifier() {
		return attendanceSchool.getDisplayIdentifier();
	}
	/**
	 * @param attendanceSchoolProgIdentifier the attendanceSchoolProgramIdentifier to set
	 */
	public final void setAttendanceSchoolProgramIdentifier(
			String attendanceSchoolProgIdentifier) {
		attendanceSchool.setDisplayIdentifier(attendanceSchoolProgIdentifier);
	}
	/**
	 * @return the schoolEntryDate
	 */
	public final Date getSchoolEntryDate() {
		return schoolEntryDate;
	}
	/**
	 * @param schoolEntryDt the schoolEntryDate to set
	 */
	public final void setSchoolEntryDate(Date schoolEntryDt) {
		this.schoolEntryDate = schoolEntryDt;
	}
	/**
	 * @return the districtEntryDate
	 */
	public final Date getDistrictEntryDate() {
		return districtEntryDate;
	}
	/**
	 * @param districtEntryDt the districtEntryDate to set
	 */
	public final void setDistrictEntryDate(Date districtEntryDt) {
		this.districtEntryDate = districtEntryDt;
	}
	/**
	 * @return the stateEntryDate
	 */
	public final Date getStateEntryDate() {
		return stateEntryDate;
	}
	/**
	 * @param stateEntryDt the stateEntryDate to set
	 */
	public final void setStateEntryDate(Date stateEntryDt) {
		this.stateEntryDate = stateEntryDt;
	}
	/**
	 * @return the exitWithdrawalDate
	 */
	public final Date getExitWithdrawalDate() {
		return exitWithdrawalDate;
	}
	/**
	 * @param exitWithdrawalDt the exitWithdrawalDate to set
	 */
	public final void setExitWithdrawalDate(Date exitWithdrawalDt) {
		this.exitWithdrawalDate = exitWithdrawalDt;
	}
	/**
	 * @return the exitWithdrawalType
	 */
	public final int getExitWithdrawalType() {
		return exitWithdrawalType;
	}
	/**
	 * @param exitWithdrawalTyp the exitWithdrawalType to set
	 */
	public final void setExitWithdrawalType(int exitWithdrawalTyp) {
		this.exitWithdrawalType = exitWithdrawalTyp;
	}
	/**
	 * @return the specialCircumstancesTransferChoice
	 */
	public final String getSpecialCircumstancesTransferChoice() {
		return specialCircumstancesTransferChoice;
	}
	/**
	 * @param specialCircumTransferChoice the specialCircumstancesTransferChoice to set
	 */
	public final void setSpecialCircumstancesTransferChoice(
			String specialCircumTransferChoice) {
		this.specialCircumstancesTransferChoice = specialCircumTransferChoice;
	}
	/**
	 * @return the giftedStudent
	 */
	public final Boolean getGiftedStudent() {
		return giftedStudent;
	}
	/**
	 * @param giftedStud the giftedStudent to set
	 */
	public final void setGiftedStudent(Boolean giftedStud) {
		this.giftedStudent = giftedStud;
	}
	/**
	 * @return the specialEdProgramEndingDate
	 */
	public final Date getSpecialEdProgramEndingDate() {
		return specialEdProgramEndingDate;
	}
	/**
	 * @param specialEdProgramEndDate the specialEdProgramEndingDate to set
	 */
	public final void setSpecialEdProgramEndingDate(Date specialEdProgramEndDate) {
		this.specialEdProgramEndingDate = specialEdProgramEndDate;
	}
	/**
	 * @return the qualifiedFor504
	 */
	public final String getQualifiedFor504() {
		return qualifiedFor504;
	}
	/**
	 * @param qualified504 the qualifiedFor504 to set
	 */
	public final void setQualifiedFor504(String qualified504) {
		this.qualifiedFor504 = qualified504;
	}
	/**
	 * @return the firstLanguage
	 */
	public final String getFirstLanguage() {
		return firstLanguage;
	}
	/**
	 * @param firstLang the firstLanguage to set
	 */
	public final void setFirstLanguage(String firstLang) {
		this.firstLanguage = firstLang;
	}
	/**
	 * @return the testSubject
	 */
	public final String getTestSubject() {
		return testSubject;
	}
	/**
	 * @param testSub the testSubject to set
	 */
	public final void setTestSubject(String testSub) {
		this.testSubject = testSub;
	}
	/**
	 * @return the legalFirstName
	 */
	public final String getLegalFirstName() {
		return student.getLegalFirstName();
	}
	/**
	 * @param legalFirstName the legalFirstName to set
	 */
	public final void setLegalFirstName(String legalFirstName) {
		student.setLegalFirstName(legalFirstName);
	}
	/**
	 * @return the studentId
	 */
	public final long getStudentId() {
		return studentId;
	}

	/**
	 * @param studId the studentId to set
	 */
	public final void setStudentId(long studId) {
		this.studentId = studId;
	}

	/**
     * @return the restrictionId
     */
    public long getRestrictionId() {
        return restrictionId;
    }

    /**
     * @param restrictionId the restrictionId to set
     */
    public void setRestrictionId(long restrictionId) {
        this.restrictionId = restrictionId;
    }

    /**
	 * @return the student
	 */
	public final Student getStudent() {
		return student;
	}

	/**
	 * @param stud the student to set
	 */
	public final void setStudent(Student stud) {
		if (stud != null) {
			this.student = stud;
		}
	}
	/**
	 * @return the attendanceSchool
	 */
	public Organization getAttendanceSchool() {
		return attendanceSchool;
	}

	/**
	 * @param attendanceSchool the attendanceSchool to set
	 */
	public void setAttendanceSchool(Organization attendanceSchool) {
		if (attendanceSchool != null) {
			this.attendanceSchool = attendanceSchool;
		}
	}

	/**
	 * @return the attendanceSchoolId
	 */
	public long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	/**
	 * @param attendanceSchoolId the attendanceSchoolId to set
	 */
	public void setAttendanceSchoolId(long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}
	
	public GradeCourse getGradeCourse() {
		return gradeCourse;
	}

	public void setGradeCourse(GradeCourse gradeCourse) {
		if (gradeCourse != null) {
			this.gradeCourse = gradeCourse;
		}
	}

	public String getCurrentGradeLevelCode() {
		return currentGradeLevelCode;
	}

	public void setCurrentGradeLevelCode(String currentGradeLevelCode) {
		this.currentGradeLevelCode = currentGradeLevelCode;
	}
	
	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return {@link String}
	 */
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	
	public List<String> buildJSONRow() {
		List<String> cells = new ArrayList<String>();

		if(getId() > 0) {
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStudent().getStateStudentIdentifier() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getStateStudentIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStudent().getLegalFirstName() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStudent().getLegalMiddleName() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalMiddleName());
		} else {
			cells.add("");
		}

		if(getStudent().getLegalLastName() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalLastName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getStudent().getGenderStr() != null && !"null".equalsIgnoreCase(getStudent().getGenderStr().trim())) {
			if(getStudent().getGenderStr().equals("0")) {
				cells.add(ParsingConstants.BLANK + "FEMALE");
			} else if(getStudent().getGenderStr().equals("1")) {
				cells.add(ParsingConstants.BLANK + "MALE");
			}
		} else {
			cells.add("");
		}		
		if(getGradeCourse().getGradeLevel() != null) {			
			cells.add(ParsingConstants.BLANK + getGradeCourse().getGradeLevel());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		if(getGradeCourse().getName() != null) {
			cells.add(ParsingConstants.BLANK + getGradeCourse().getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		if(getRosterAssigned() != null) {
			cells.add(ParsingConstants.BLANK + "Enrolled Students");
		} else {
			cells.add(ParsingConstants.BLANK + "Other Students");
		}
		// Changed During US16275
		String localStudentIdentifier = getLocalStudentIdentifier();
		if(localStudentIdentifier != null){
			cells.add(ParsingConstants.BLANK + localStudentIdentifier);
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		return cells;
	}

	public String getRosterAssigned() {
		return rosterAssigned;
	}

	public void setRosterAssigned(String rosterAssigned) {
		this.rosterAssigned = rosterAssigned;
	}

	/**
	 * @return the aypSchoolId
	 */
	public long getAypSchoolId() {
		return aypSchoolId;
	}

	/**
	 * @param aypSchoolId the aypSchoolId to set
	 */
	public void setAypSchoolId(long aypSchoolId) {
		this.aypSchoolId = aypSchoolId;
	}

	public Roster getRoster() {
		return roster;
	}

	public void setRoster(Roster roster) {
		this.roster = roster;
	}

	public Long getSubjectAreaId() {
		return subjectAreaId;
	}

	public void setSubjectAreaId(Long subjectAreaId) {
		this.subjectAreaId = subjectAreaId;
	}	
	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public boolean isPreviousEnrollmentExists() {
		return previousEnrollmentExists;
	}

	public void setPreviousEnrollmentExists(boolean previousEnrollmentExists) {
		this.previousEnrollmentExists = previousEnrollmentExists;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<SubjectArea> getSubjectAreaList() {
		return subjectAreaList;
	}

	public void setSubjectAreaList(List<SubjectArea> subjectAreaList) {
		this.subjectAreaList = subjectAreaList;
	}

	public Long getAttendanceSchoolDistrictId() {
		return attendanceSchoolDistrictId;
	}

	public void setAttendanceSchoolDistrictId(Long attendanceSchoolDistrictId) {
		this.attendanceSchoolDistrictId = attendanceSchoolDistrictId;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public String getAccountabilityDistrictIdentifier() {
		return accountabilityDistrictIdentifier;
	}

	public void setAccountabilityDistrictIdentifier(
			String accountabilityDistrictIdentifier) {
		this.accountabilityDistrictIdentifier = accountabilityDistrictIdentifier;
	}

	public Long getAccountabilityDistrictId() {
		return accountabilityDistrictId;
	}

	public void setAccountabilityDistrictId(Long accountabilityDistrictId) {
		this.accountabilityDistrictId = accountabilityDistrictId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getAccountabilitySchoolName() {
		return accountabilitySchoolName;
	}

	public void setAccountabilitySchoolName(String accountabilitySchoolName) {
		this.accountabilitySchoolName = accountabilitySchoolName;
	}

	public String getAccountabilityDistrictName() {
		return accountabilityDistrictName;
	}

	public void setAccountabilityDistrictName(String accountabilityDistrictName) {
		this.accountabilityDistrictName = accountabilityDistrictName;
	}

	public Long getAccountabilitySchoolId() {
		return accountabilitySchoolId;
	}

	public void setAccountabilitySchoolId(Long accountabilitySchoolId) {
		this.accountabilitySchoolId = accountabilitySchoolId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	
}
