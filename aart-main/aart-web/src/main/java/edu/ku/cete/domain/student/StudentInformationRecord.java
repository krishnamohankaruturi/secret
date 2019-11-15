package edu.ku.cete.domain.student;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.Reportable;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.util.ParsingConstants;

/**
 * Class that contains student information along with enrollment and roster data if any.
 * @author vittaly
 *
 */
public class StudentInformationRecord extends TraceableEntity implements Serializable,Reportable {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 2351270568810842648L;
	/**
	 * primary key.
	 */
	private Long id;
	
	/**
	 * enrollmentId.
	 */
	private Long enrollmentId;
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
	 * stateCourse.
	 */
	private ContentArea stateCourse = new ContentArea();
	
	/**
	 * stateSubjectArea.
	 */
	private ContentArea stateSubjectArea = new ContentArea();
	
	/**
	 * courseenrollment
	 */
	private Category courseEnrollment = new Category();	
	
	/**
	 * rosterId
	 */
	private Long rosterId;
	/**
	 * roster.
	 */
	private Roster roster = new Roster();
	/**
	 * educator.
	 */
	private User educator = new User();
	/**
	 * gradeCourse.
	 */
	private GradeCourse gradeCourse = new GradeCourse();
	/**
	 * attendance school id.
	 */
	private Long attendanceSchoolId;
	/**
	 * surveyStatus.
	 */
	private String surveyStatus;
	/**
	 * accessProfileStatus
	 */
	private String accessProfileStatus;
	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id2 the id to set
	 */
	public final void setId(Long id2) {
		this.id = id2;
	}
	/**
	 * @return the enrollmentId
	 */
	public Long getEnrollmentId() {
		return enrollmentId;
	}

	/**
	 * @param enrollmentId the enrollmentId to set
	 */
	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
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
	 * @return the stateCourse
	 */
	public ContentArea getStateCourse() {
		return stateCourse;
	}

	/**
	 * @param stateCourse the stateCourse to set
	 */
	public void setStateCourse(ContentArea stateCourse) {
		this.stateCourse = stateCourse;
	}

	/**
	 * @return
	 */
	public Category getCourseEnrollment() {
		
		return courseEnrollment;
	}

	/**
	 * @param courseenrollment
	 */
	public void setCourseEnrollment(Category courseEnrollment) {
		this.courseEnrollment = courseEnrollment;
	}
	
	/**
	 * @return the stateSubjectArea
	 */
	public ContentArea getStateSubjectArea() {
		return stateSubjectArea;
	}

	/**
	 * @param stateSubjectArea the stateSubjectArea to set
	 */
	public void setStateSubjectArea(ContentArea stateSubjectArea) {
		this.stateSubjectArea = stateSubjectArea;
	}

	/**
	 * @return the rosterId
	 */
	public Long getRosterId() {
		return rosterId;
	}

	/**
	 * @param rosterId the rosterId to set
	 */
	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	/**
	 * @return the roster
	 */
	public Roster getRoster() {
		return roster;
	}

	/**
	 * @param roster the roster to set
	 */
	public void setRoster(Roster roster) {
		this.roster = roster;
	}

	/**
	 * @return the educator
	 */
	public User getEducator() {
		return educator;
	}

	/**
	 * @param educator the educator to set
	 */
	public void setEducator(User educator) {
		this.educator = educator;
	}

	/**
	 * @return the gradeCourse
	 */
	public GradeCourse getGradeCourse() {
		return gradeCourse;
	}

	/**
	 * @param gradeCourse the gradeCourse to set
	 */
	public void setGradeCourse(GradeCourse gradeCourse) {
		this.gradeCourse = gradeCourse;
	}

	/**
	 * @return the attendanceSchoolId
	 */
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	/**
	 * @param attendanceSchoolId the attendanceSchoolId to set
	 */
	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}
	/**
	 * @return the surveyStatus
	 */
	public String getSurveyStatus() {
		return surveyStatus;
	}

	/**
	 * @param surveyStatus the surveyStatus to set
	 */
	public void setSurveyStatus(String surveyStatus) {
		this.surveyStatus = surveyStatus;
	}
	
	/**
	 * 
	 * @return accessProfileStatus
	 */
	public String getAccessProfileStatus() {
		return accessProfileStatus;
	}
	
	/**
	 * 
	 * @param accessProfileStatus
	 */
	public void setAccessProfileStatus(String accessProfileStatus) {
		this.accessProfileStatus = accessProfileStatus;
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

	@Override
	public int getNumberOfAttributes() {
		return 29;
	}

	@Override
	public String getAttribute(int i) {
		String result = null;
		if(i==0) {
			if(getId() != null) {
				result = ParsingConstants.BLANK + getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 1) {
			if(getStudent().getStateStudentIdentifier() != null &&  
					StringUtils.isNotEmpty(getStudent().getStateStudentIdentifier())) {
				result = ParsingConstants.BLANK + getStudent().getStateStudentIdentifier();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 2) {
			if(getLocalStudentIdentifier() != null &&  
					StringUtils.isNotEmpty(getLocalStudentIdentifier())) {
				result = ParsingConstants.BLANK + getLocalStudentIdentifier();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 3) {
			if(getStudent().getLegalFirstName() != null &&  
					StringUtils.isNotEmpty(getStudent().getLegalFirstName())) {
				result = ParsingConstants.BLANK + getStudent().getLegalFirstName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 4) {
			if(getStudent().getLegalMiddleName() != null &&  
					StringUtils.isNotEmpty(getStudent().getLegalMiddleName())) {
				result = ParsingConstants.BLANK + getStudent().getLegalMiddleName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 5) {
			if(getStudent().getLegalLastName() != null &&  
				StringUtils.isNotEmpty(getStudent().getLegalLastName())) {
				result = ParsingConstants.BLANK + getStudent().getLegalLastName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 6) {
			if(getStudent().getGenerationCode() != null &&  
					StringUtils.isNotEmpty(getStudent().getGenerationCode())) {
				result = ParsingConstants.BLANK + getStudent().getGenerationCode();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 7) {
			if(getStudent().getGender() != null && 
					(getStudent().getGender() == 0 || getStudent().getGender() == 1)) {
				result = ParsingConstants.BLANK + getStudent().getGender();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;	
			}
		} else if(i == 8) {
			if(getStudent().getDateOfBirth() != null) {
				result = ParsingConstants.BLANK + getStudent().getDateOfBirthStr();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;	
			}
		} else if(i == 9) {
			if(getGradeCourse().getName() != null &&  
					StringUtils.isNotEmpty(getGradeCourse().getName())) {
				result = ParsingConstants.BLANK +  getGradeCourse().getName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} /*else if(i == 10) {
			if(getStateSubjectArea().getName() != null &&  
					StringUtils.isNotEmpty(getStateSubjectArea().getName())) {
				result = ParsingConstants.BLANK + getStateSubjectArea().getName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 11) {
			if(getStateCourse().getId() != null) {
				result = ParsingConstants.BLANK +  getStateCourse().getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} */else if(i == 10) {
			if(getRoster().getCourseSectionName() != null &&  
					StringUtils.isNotEmpty(getRoster().getCourseSectionName())) {
				result = ParsingConstants.BLANK + getRoster().getCourseSectionName(); 
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 11) {
			if(getAttendanceSchool().getDisplayIdentifier() != null &&  
					StringUtils.isNotEmpty(getAttendanceSchool().getDisplayIdentifier())) {
				result = ParsingConstants.BLANK + getAttendanceSchool().getDisplayIdentifier();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 12) {
			if(getAttendanceSchool().getOrganizationName() != null &&  
					StringUtils.isNotEmpty(getAttendanceSchool().getOrganizationName())) {
				result = ParsingConstants.BLANK + getAttendanceSchool().getOrganizationName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 13) {
			result = ParsingConstants.BLANK + getCurrentSchoolYear();
		} else if(i == 14) {
			if(getStudent().getFirstLanguage() != null &&  
					StringUtils.isNotEmpty(getStudent().getFirstLanguage())) {
				result = ParsingConstants.BLANK + getStudent().getFirstLanguage();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 15) {
			if(getStudent().getComprehensiveRace() != null &&  
					StringUtils.isNotEmpty(getStudent().getComprehensiveRace())) {
				result = ParsingConstants.BLANK + getStudent().getComprehensiveRace();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 16) {
			if(getResidenceDistrictIdentifier() != null &&  
					StringUtils.isNotEmpty(getResidenceDistrictIdentifier())) {
				result = ParsingConstants.BLANK + getResidenceDistrictIdentifier();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 17) {
			if(getStudent().getPrimaryDisabilityCode() != null &&  
					StringUtils.isNotEmpty(getStudent().getPrimaryDisabilityCode())) {
				result = ParsingConstants.BLANK + getStudent().getPrimaryDisabilityCode();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 18) {
			if(getEducator().getUniqueCommonIdentifier() != null &&  
					StringUtils.isNotEmpty(getEducator().getUniqueCommonIdentifier())) {
				result = ParsingConstants.BLANK + getEducator().getUniqueCommonIdentifier();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 19) {
			if(getEducator().getFirstName() != null &&  
					StringUtils.isNotEmpty(getEducator().getFirstName())) {
				result = ParsingConstants.BLANK + getEducator().getFirstName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 20) {
			if(getEducator().getSurName() != null &&  
					StringUtils.isNotEmpty(getEducator().getSurName())) {
				result = ParsingConstants.BLANK + getEducator().getSurName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 21) {
			if(getCourseEnrollment().getCategoryName() != null &&  
					StringUtils.isNotEmpty(getCourseEnrollment().getCategoryName())) {
				result = ParsingConstants.BLANK + getCourseEnrollment().getCategoryName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 22) {
			if(getStudent().getId() != null) {
				result = ParsingConstants.BLANK + getStudent().getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 23) {
			if(getEnrollmentId() != null) {
				result = ParsingConstants.BLANK + getEnrollmentId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 24) {
			if(getRoster().getId() != null) {
				result = ParsingConstants.BLANK + getRoster().getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 25) {
			if(getEducator().getId() != null) {
				result = ParsingConstants.BLANK + getEducator().getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 26) {
			if(getAttendanceSchoolId() != null) {
				result = ParsingConstants.BLANK + getAttendanceSchoolId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} /*else if(i == 29) {
			if(getStateCourse().getAbbreviatedName() != null &&  
					StringUtils.isNotEmpty(getStateCourse().getAbbreviatedName())) {
				result = ParsingConstants.BLANK + getStateCourse().getAbbreviatedName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 30) {
			if(getStateCourse().getName() != null &&  
					StringUtils.isNotEmpty(getStateCourse().getName())) {
				result = ParsingConstants.BLANK + getStateCourse().getName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 31) {
			if(getStateSubjectArea().getId() != null) {
				result = ParsingConstants.BLANK + getStateSubjectArea().getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		}  else if(i == 32) {
			if(getStateSubjectArea().getAbbreviatedName() != null &&  
					StringUtils.isNotEmpty(getStateSubjectArea().getAbbreviatedName())) {
				result = ParsingConstants.BLANK + getStateSubjectArea().getAbbreviatedName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 33) {
			if(getGradeCourse().getId() != null) {
				result = ParsingConstants.BLANK + getGradeCourse().getId();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 34) {
			if(getCurrentGradeLevel() != null) {
				result = ParsingConstants.BLANK + getCurrentGradeLevel();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 35) {
			if(getGradeCourse().getAbbreviatedName() != null &&  
					StringUtils.isNotEmpty(getGradeCourse().getAbbreviatedName())) {
				result = ParsingConstants.BLANK + getGradeCourse().getAbbreviatedName();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 36) {
			if(getGradeCourse().getGradeLevel() != null) {
				result = ParsingConstants.BLANK +  getGradeCourse().getGradeLevel();
			} else {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} */else if(i == 27) {			
			result = getAccessProfileStatus();
		} else if(i == 28) {			
			result = getSurveyStatus();
		}
		return result;
	}

	@Override
	public String[] getAttributes() {
		String[] result = new String[getNumberOfAttributes()];
		for(int i=0;i<result.length;i++) {
			result[i] = getAttribute(i);
		}
		return result;
	}

}
