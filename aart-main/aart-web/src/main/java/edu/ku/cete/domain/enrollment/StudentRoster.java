/**
 * 
 */
package edu.ku.cete.domain.enrollment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.configuration.StudentsTestsStatusConfiguration;
import edu.ku.cete.domain.StudentSpecialCircumstance;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.Reportable;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author m802r921
 *
 */
public class StudentRoster extends TraceableEntity implements Serializable,Reportable{

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
	private Long studentId;
	/**
	 * Test Session ID
	 */
	private long testSessionId;
	
	/**
	 * restriction id.
	 */
	private long restrictionId;
	
	private String accessProfileStatus;
	
	private String firstContact;
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
	
	private StudentsTests studentsTests;
	
	private String enrollmentStatus;
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
	 * statusId.
	 */
	private Long status;
	
	private String history;
	
	private StudentSpecialCircumstance studentSpecialCircumstance;

	private Long studentEnrlId;
	
	private Long scienceBandId;
	
	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

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
	public final Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studId the studentId to set
	 */
	public final void setStudentId(Long studId) {
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
	
	public StudentsTests getStudentsTests() {
		return studentsTests;
	}

	public void setStudentsTests(StudentsTests studentsTests) {
		this.studentsTests = studentsTests;
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
		return 40;
	}
	
	public long getTestSessionId() {
		return testSessionId;
	}

	public void setTestSessionId(long testSessionId) {
		this.testSessionId = testSessionId;
	}

	public String getEnrollmentStatus() {
		return enrollmentStatus;
	}

	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	@Override
	public String getAttribute(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	public String getAccessprofilestatus() {
		return accessProfileStatus;
	}

	public void setAccessprofilestatus(String accessProfileStatus) {
		this.accessProfileStatus = accessProfileStatus;
	}

	
	public String getFirstContact() {
		return firstContact;
	}

	public void setFirstContact(String firstContact) {
		this.firstContact = firstContact;
	}

	public StudentSpecialCircumstance getStudentSpecialCircumstance() {
		return studentSpecialCircumstance;
	}

	public void setStudentSpecialCircumstance(StudentSpecialCircumstance studentSpecialCircumstance) {
		this.studentSpecialCircumstance = studentSpecialCircumstance;
	}

	
	public Long getStudentEnrlId() {
		return studentEnrlId;
	}

	public void setStudentEnrlId(Long studentEnrlId) {
		this.studentEnrlId = studentEnrlId;
	}
	
	/**
	 * @param studentsTestsStatusConfiguration
	 * @return
	 */
	public List<String> buildJSONRow(StudentsTestsStatusConfiguration studentsTestsStatusConfiguration) {
		List<String> cells = new ArrayList<String>();
		
		// 0
		if(getId() != null) { 
			cells.add(ParsingConstants.BLANK + getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// Follow the index and update if there are any new columns are added.
		// Need to update the values for special circumstance column formatter
		// 1
		if(getStudent().getStateStudentIdentifier() != null &&  
				StringUtils.isNotEmpty(getStudent().getStateStudentIdentifier())) {
			cells.add(ParsingConstants.BLANK + getStudent().getStateStudentIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 2
		if(getLocalStudentIdentifier() != null &&  
				StringUtils.isNotEmpty(getLocalStudentIdentifier())) {
			cells.add(ParsingConstants.BLANK + getLocalStudentIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}			
		
		// 3
		if(getStudent().getLegalFirstName() != null &&  
				StringUtils.isNotEmpty(getStudent().getLegalFirstName())) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 4
		if(getStudent().getLegalMiddleName() != null &&  
				StringUtils.isNotEmpty(getStudent().getLegalMiddleName())) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalMiddleName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 5
		if(getStudent().getLegalLastName() != null &&  
				StringUtils.isNotEmpty(getStudent().getLegalLastName())) {
			cells.add(ParsingConstants.BLANK + getStudent().getLegalLastName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 6
		if(getStudent().getGenerationCode() != null &&  
				StringUtils.isNotEmpty(getStudent().getGenerationCode())) {
			cells.add(ParsingConstants.BLANK + getStudent().getGenerationCode());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 7
		if(getStudent().getGender() != null) {
			String gender = StringUtils.EMPTY;
			if(getStudent().getGender() == 0) {
				gender = "Female";
			}
			if(getStudent().getGender() == 1) {
				gender = "Male";
			}
			cells.add(ParsingConstants.BLANK + gender);
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);	
		}
		
		// 8
		if(getStudent().getDateOfBirth() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getDateOfBirthStr());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);	
		}
		
		// 9
		if(getGradeCourse().getName() != null &&  
				StringUtils.isNotEmpty(getGradeCourse().getName())) {
			cells.add(ParsingConstants.BLANK +  getGradeCourse().getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
	
		// 10
		if(getRoster().getCourseSectionName() != null &&  
				StringUtils.isNotEmpty(getRoster().getCourseSectionName())) {
			cells.add(ParsingConstants.BLANK + getRoster().getCourseSectionName()); 
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 11
		if(getStateSubjectArea().getName() != null &&  
				StringUtils.isNotEmpty(getStateSubjectArea().getName())) {
			cells.add(ParsingConstants.BLANK + getStateSubjectArea().getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 12
		if(getStateCourse().getName() != null &&  
				StringUtils.isNotEmpty(getStateCourse().getName())) {
			cells.add(ParsingConstants.BLANK + getStateCourse().getName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 13
		if(getAttendanceSchool().getDisplayIdentifier() != null &&  
				StringUtils.isNotEmpty(getAttendanceSchool().getDisplayIdentifier())) {
			cells.add(ParsingConstants.BLANK + getAttendanceSchool().getDisplayIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 14
		if(getAttendanceSchool().getOrganizationName() != null &&  
				StringUtils.isNotEmpty(getAttendanceSchool().getOrganizationName())) {
			cells.add(ParsingConstants.BLANK + getAttendanceSchool().getOrganizationName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 15
		cells.add(ParsingConstants.BLANK + getCurrentSchoolYear());
		
		// 16
		if(getStudent().getFirstLanguage() != null &&  
				StringUtils.isNotEmpty(getStudent().getFirstLanguage())) {
			cells.add(ParsingConstants.BLANK + getStudent().getFirstLanguage());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 17
		if(getStudent().getComprehensiveRace() != null &&  
				StringUtils.isNotEmpty(getStudent().getComprehensiveRace())) {
			cells.add(ParsingConstants.BLANK + getStudent().getComprehensiveRace());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 18
		if(getResidenceDistrictIdentifier() != null &&  
				StringUtils.isNotEmpty(getResidenceDistrictIdentifier())) {
			cells.add(ParsingConstants.BLANK + getResidenceDistrictIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 19
		if(getStudent().getPrimaryDisabilityCode() != null &&  
				StringUtils.isNotEmpty(getStudent().getPrimaryDisabilityCode())) {
			cells.add(ParsingConstants.BLANK + getStudent().getPrimaryDisabilityCode());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 20
		if(getEducator().getUniqueCommonIdentifier() != null &&  
				StringUtils.isNotEmpty(getEducator().getUniqueCommonIdentifier())) {
			cells.add(ParsingConstants.BLANK + getEducator().getUniqueCommonIdentifier());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 21
		if(getEducator().getFirstName() != null &&  
				StringUtils.isNotEmpty(getEducator().getFirstName())) {
			cells.add(ParsingConstants.BLANK + getEducator().getFirstName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);	
		}
		
		// 22
		if(getEducator().getSurName() != null &&  
				StringUtils.isNotEmpty(getEducator().getSurName())) {
			cells.add(ParsingConstants.BLANK + getEducator().getSurName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 23
		if(getCourseEnrollment().getCategoryName() != null &&  
				StringUtils.isNotEmpty(getCourseEnrollment().getCategoryName())) {
			cells.add(ParsingConstants.BLANK + getCourseEnrollment().getCategoryName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}

		// 24
		if(getRoster().getId() != null) {
			cells.add(ParsingConstants.BLANK + getRoster().getId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 25
		if(getStudentsTests() != null) {
			cells.add(ParsingConstants.BLANK + getStudentsTests().getTestSessionId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 26
		if(getEnrollmentStatus() != null) {
			cells.add(ParsingConstants.BLANK + getEnrollmentStatus());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 27
		if(getStudentsTests() != null) {
			Long statusId = getStudentsTests().getStatus();
			Category category = studentsTestsStatusConfiguration.getStatus(statusId);
			if(category != null){
				cells.add(ParsingConstants.BLANK + category.getCategoryName());				
			} else {
				cells.add(ParsingConstants.NOT_AVAILABLE);
			}
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		/*if(getStudent().isDlmStudent() != null && getStudent().isDlmStudent()) {
			cells.add(ParsingConstants.BLANK + "Yes");
		} else {
			cells.add(ParsingConstants.BLANK + "No");
		}*/
		
		//Added during US16421 :To bring assessment program associated with student
		// 28
		if(getStudent().getStudentAssessmentProgram() != null) {
			cells.add(ParsingConstants.BLANK + getStudent().getStudentAssessmentProgram());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 29
		if(getStudentSpecialCircumstance() != null && getStudentSpecialCircumstance().getSpecialCircumstanceId() != null) {
			cells.add(ParsingConstants.BLANK + getStudentSpecialCircumstance().getSpecialCircumstanceId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 30
		if(getStudentSpecialCircumstance() != null && getStudentSpecialCircumstance().getStatusName() != null) {
			cells.add(ParsingConstants.BLANK + getStudentSpecialCircumstance().getStatusName());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 31
		if(getStudentId() != null) {
			cells.add(ParsingConstants.BLANK + getStudentId());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		
		// 32
		if(getStudentSpecialCircumstance() != null && getStudentSpecialCircumstance().getStatus() != null) {
			cells.add(ParsingConstants.BLANK + getStudentSpecialCircumstance().getStatus());
		} else {
			cells.add(ParsingConstants.NOT_AVAILABLE);
		}
		return cells;
	}

	public Long getScienceBandId() {
		return scienceBandId;
	}

	public void setScienceBandId(Long scienceBandId) {
		this.scienceBandId = scienceBandId;
	}

}
