package edu.ku.cete.domain.enrollment;

import org.apache.commons.lang3.StringUtils;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserRecord;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author mahesh
 * roster record has both student and user records.
 *
 */
public class RosterRecord extends TraceableEntity implements StudentRecord,UserRecord{
	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 176630371219935153L;
	private String rosterRecordType;
	private Organization school = new Organization();
	private Organization parentToSchool = new Organization();
	private Student student = new Student();
	//set these to null only if not present.

	private ContentArea stateSubjectArea = new ContentArea();
	private Category enrollmentStatus = new Category();
	private ContentArea stateCourse = new ContentArea();
	private User educator = new User();
	private Roster roster = new Roster();
	private Enrollment enrollment = new Enrollment();
	private boolean dlmStatus;
	/*
	* added for US-18883
	*/
	private String removefromroster;
	private String assessmentProgram1;
	private String assessmentProgram2;
	private String assessmentProgram3;
	private String assessmentProgram4;
	
	private User currentUser = new User();
	
	private String lineNumber;
	/**
	 * @return the removefromroster
	 */
	public String getRemovefromroster() {
		return removefromroster;
	}
	/**
	 * @param removefromroster the removefromroster to set
	 */
	public void setRemovefromroster(String removefromroster) {
		this.removefromroster = removefromroster;
	}
	@Override
	public String getIdentifier() {
		return getStateStudentIdentifier() + ParsingConstants.BLANK;
	}
	/**
	 * @return the rosterRecordType
	 */
	public final String getRosterRecordType() {
		return rosterRecordType;
	}
	/**
	 * @param rosterRecordType the rosterRecordType to set
	 */
	public final void setRosterRecordType(String rosterRecordType) {
		this.rosterRecordType = rosterRecordType;
	}
	/**
	 * @return the schoolIdentifier
	 */
	public final String getSchoolIdentifier() {
		return school.getDisplayIdentifier();
	}
	/**
	 * @param schoolIdentifier the schoolIdentifier to set
	 */
	public final void setSchoolIdentifier(String schoolIdentifier) {
		school.setDisplayIdentifier(schoolIdentifier);
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
	 * @return the stateSubjectAreaCode
	 */
	public final String getStateSubjectAreaCode() {
		return (stateSubjectArea == null ? null : stateSubjectArea.getName());
	}
	/**
	 * @param stateSubjectAreaCode the stateSubjectAreaCode to set
	 */
	public final void setStateSubjectAreaCode(String stateSubjectAreaCode) {
		if (stateSubjectArea == null) {
			stateSubjectArea = new ContentArea();
		}
		stateSubjectArea.setName(stateSubjectAreaCode);
	}
	/**
	 * @return the stateSubjectAreaId
	 */
	public Long getStateSubjectAreaId() {
		return (stateSubjectArea == null ? null : stateSubjectArea.getId());
	}
	/**
	 * @return the courseSection
	 */
	public final String getCourseSection() {
		return roster.getCourseSectionName();
	}
	/**
	 * @param courseSection the courseSection to set
	 */
	public final void setCourseSection(String courseSection) {
		roster.setCourseSectionName(courseSection);
	}
	
	public String getRosterName() {		
		return roster.getCourseSectionName();
	}

	public void setRosterName(String rosterName) {
		roster.setCourseSectionName(rosterName);
	}
	
	/**
	 * @return the residenceDistrictIdentifier
	 */
	public final String getResidenceDistrictIdentifier() {
		return parentToSchool.getDisplayIdentifier();
	}
	/**
	 * @param residenceDistrictIdentifier the residenceDistrictIdentifier to set
	 */
	public final void setResidenceDistrictIdentifier(
			String residenceDistrictIdentifier) {
		parentToSchool.setDisplayIdentifier(residenceDistrictIdentifier);
	}
	/**
	 * @return the educatorIdentifier
	 */
	public final String getEducatorIdentifier() {
		return educator.getUniqueCommonIdentifier();
	}
	/**
	 * @param educatorIdentifier the educatorIdentifier to set
	 */
	public final void setEducatorIdentifier(String educatorIdentifier) {
		educator.setUniqueCommonIdentifier(educatorIdentifier);
		//this is incase an unapproved educator need to be created.
		//if present these fields won't be updated.
		educator.setUserName(educatorIdentifier + ParsingConstants.BLANK);
		educator.setPassword(educatorIdentifier + ParsingConstants.BLANK);
		educator.setEmail(educatorIdentifier + ParsingConstants.BLANK);
		educator.setAccountNonLocked(false);
		educator.setAccountNonExpired(false);
		educator.setCredentialsNonExpired(false);
	}
	/**
	 * @param educatorIdentifier the educatorIdentifier to set
	 * This method adds the attendance school display identifier to
	 * the user name in order to make it unique. To address the condition
	 * of multiple schools with the same display identifiers,
	 * also appending attendance school primary key.
	 * So unique common identifier is unique with in the organization.
	 * adding the organization id makes it globally unique.
	 */
	public final void appendSchoolIdentifier(Long attendanceSchoolId) {
		if (attendanceSchoolId != null) {
			//if educator identifier or attendance school is null then
			// the validation framework will reject the record.
			String authInfo = educator.getUniqueCommonIdentifier()
			+ ParsingConstants.INNER_DELIM + getSchoolIdentifier()
			+ ParsingConstants.INNER_DELIM + attendanceSchoolId;
			if (StringUtils.isNotEmpty(authInfo)) {
				educator.setUserName(authInfo.substring(0, Math.min(45, authInfo.length())));
				educator.setPassword(authInfo.substring(0, Math.min(100, authInfo.length())));
				educator.setEmail(authInfo.substring(0,  Math.min(45, authInfo.length())));
			}
		}
	}		
	/**
	 * @return the currentSchoolYear
	 */
	public final int getCurrentSchoolYear() {
		return enrollment.getCurrentSchoolYear();
	}
	/**
	 * @param currentSchoolYear the currentSchoolYear to set
	 */
	public final void setCurrentSchoolYear(int currentSchoolYear) {
		enrollment.setCurrentSchoolYear(currentSchoolYear);
	}
	/**
	 * @return the enrollmentStatus
	 */
	public final Integer getEnrollmentStatusCode() {
		if (enrollmentStatus == null) {
			return null;
		} else {
			try {
				return Integer.parseInt(enrollmentStatus.getCategoryCode());
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}
	/**
	 * @return the enrollmentStatus
	 */
	public final String getEnrollmentStatusCodeStr() {
		if (enrollmentStatus == null) {
			return null;
		} else {
			return enrollmentStatus.getCategoryCode();
		}
	}	
	/**
	 * @param enrollmentStat the enrollmentStatus to set
	 */
	public final void setEnrollmentStatusCode(Integer enrollmentStat) {
		if (enrollmentStatus == null) {
			enrollmentStatus = new Category();
		}
		if (enrollmentStat == null) {
			enrollmentStatus.setCategoryCode(null);
		} else {
			enrollmentStatus.setCategoryCode(enrollmentStat + ParsingConstants.BLANK);
		}
	}
	/**
	 * @return the stateSubjectArea
	 */
	public final ContentArea getStateSubjectArea() {
		return stateSubjectArea;
	}
	/**
	 * @param stateSubjectArea the stateSubjectArea to set
	 */
	public final void setStateSubjectArea(ContentArea stateSubjectArea) {
		this.stateSubjectArea = stateSubjectArea;
	}
	/**
	 * @return the enrollmentStatus
	 */
	public final Category getEnrollmentStatus() {
		return enrollmentStatus;
	}
	/**
	 * @param enrollmentStatus the enrollmentStatus to set
	 */
	public final void setEnrollmentStatus(Category enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}
	/**
	 * @return the enrollmentStatusId
	 */
	public Long getEnrollmentStatusId() {
		return (enrollmentStatus == null ? null : enrollmentStatus.getId());
	}
	/**
	 * @return the stateCourse
	 */
	public final ContentArea getStateCourse() {
		return stateCourse;
	}
	/**
	 * @param stateCourse the stateCourse to set
	 */
	public final void setStateCourse(ContentArea stateCourse) {
		this.stateCourse = stateCourse;
	}
	/**
	 * @return the stateCourseCode
	 */
	public final String getStateCourseCode() {
		return (stateCourse == null ? null : stateCourse.getName());
	}
	/**
	 * @param stateCourseCode the stateCourseCode to set
	 */
	public final void setStateCourseCode(String stateCourseCode) {
		if (stateCourse == null) {
			stateCourse = new ContentArea();
		}
		stateCourse.setName(stateCourseCode);
	}
	/**
	 * @return the stateCourseId
	 */
	public Long getStateCourseId() {
		return (stateCourse == null ? null : stateCourse.getId());
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
		//leave it as null and an existing name won't be updated.
		if (StringUtils.isNotEmpty(legalLastName)) {
			this.student.setLegalLastName(legalLastName);
		}
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
		//leave it as null and an existing name won't be updated.
		if (StringUtils.isNotEmpty(legalFirstName)) {
			this.student.setLegalFirstName(legalFirstName);
		}
	}
	/**
	 * @return the localStudentIdentifier
	 */
	public final String getLocalStudentIdentifier() {
		return enrollment.getLocalStudentIdentifier();
	}
	/**
	 * @param localStudentIdentifier the localStudentIdentifier to set
	 */
	public final void setLocalStudentIdentifier(String localStudentIdentifier) {
		enrollment.setLocalStudentIdentifier(localStudentIdentifier);
	}
	/**
	 * @return the educatorLastName
	 */
	public final String getEducatorLastName() {
		return educator.getSurName();
	}
	/**
	 * @param educatorLastName the educatorLastName to set
	 */
	public final void setEducatorLastName(String educatorLastName) {
		educator.setSurName(educatorLastName);
	}
	/**
	 * @return the educatorFirstName
	 */
	public final String getEducatorFirstName() {
		return educator.getFirstName();
	}
	/**
	 * @param educatorFirstName the educatorFirstName to set
	 */
	public final void setEducatorFirstName(String educatorFirstName) {
		educator.setFirstName(educatorFirstName);
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
	 * @return the school
	 */
	public Organization getSchool() {
		return school;
	}
	/**
	 * @param school the school to set
	 */
	public void setSchool(Organization school) {
		this.school = school;
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
	 * @return the parentToSchool
	 */
	public Organization getParentToSchool() {
		return parentToSchool;
	}

	/**
	 * @param parentToSchool the parentToSchool to set
	 */
	public void setParentToSchool(Organization parentToSchool) {
		this.parentToSchool = parentToSchool;
	}
	@Override
	public User getUser() {
		return getEducator();
	}
	/**
	 * @return the enrollment
	 */
	public Enrollment getEnrollment() {
		return enrollment;
	}
	/**
	 * @param enrollment the enrollment to set
	 */
	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}
	@Override
	public Long getId() {
		return roster.getId();
	}
	@Override
	public Long getId(int order) {
		Long id = null;
		if(order == 0) {
			id = getId();
		} else if (order == 1) {
			//enrollment id is the second order identifier.
			id = enrollment.getId();
		/* } else if(order == 2) {
			//student id is the third order identifier.
			//It maintains 2 to comply with the definition of student record.
			id = student.getStateStudentIdentifier(); */ 
		} else if(order == 3) {
			id = student.getId();
		} else if(order == 4) {
			id = getEducator().getId();
		}
		return id;
	}
	
	@Override
	public String getStringIdentifier(int order) {		
		String id = null;
		 if(order == 2) {
			//student id is the third order identifier.
			//It maintains 2 to comply with the definition of student record.
			id = student.getStateStudentIdentifier(); 
		} 
		return id;
	}
	
	public boolean getDlmStatus(){
		return dlmStatus;
	}
	
	public void setDlmStatus(boolean status){
		dlmStatus = status;
	}
	
	public Long getAssessmentProgramId(){
		return student.getAssessmentProgramId();
	}
	
	public void setAssessmentProgramId(Long assessmentProgramId) {
		student.setAssessmentProgramId(assessmentProgramId);
		
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getAypSchoolIdentifier() {
		return this.getEnrollment().getAypSchoolIdentifier();
	}
	/**
	 * @param aypSchoolId the aypSchoolIdentifier to set
	 */
	public void setAypSchoolIdentifier(String aypSchoolId) {
		this.getEnrollment().setAypSchoolIdentifier(aypSchoolId) ;
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
	public String getAssessmentProgram4() {
		return assessmentProgram4;
	}
	public void setAssessmentProgram4(String assessmentProgram4) {
		this.assessmentProgram4 = assessmentProgram4;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
}
