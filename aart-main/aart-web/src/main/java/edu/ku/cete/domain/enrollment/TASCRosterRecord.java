/**
 * 
 */
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
 * @author ktaduru_sta
 *
 */
public class TASCRosterRecord extends TraceableEntity implements StudentRecord, UserRecord {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2149288168344729735L;
	private String tascRecordType;
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
	private String removefromroster;
	
	private String lineNumber;
	
	
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

	
	@Override
	public User getUser() {
		return getEducator();
	}

	@Override
	public Student getStudent() {
		return student;
	}

	
	@Override
	public String getIdentifier() {
		return getTascStateStudentIdentifier() + ParsingConstants.BLANK;
	}

	public final String getTascStateStudentIdentifier() {
		return student.getStateStudentIdentifier();
	}
	
	public final void setTascStateStudentIdentifier(String stateStudentIdentifier) {
		student.setStateStudentIdentifier(stateStudentIdentifier);
	}
	
	public final String getStateSubjectAreaCode() {
		return (stateSubjectArea == null ? null : stateSubjectArea.getName());
	}
	
	public final void setStateSubjectAreaCode(String stateSubjectAreaCode) {
		if (stateSubjectArea == null) {
			stateSubjectArea = new ContentArea();
		}
		stateSubjectArea.setName(stateSubjectAreaCode);
	}
	
	public Long getStateSubjectAreaId() {
		return (stateSubjectArea == null ? null : stateSubjectArea.getId());
	}
	
	public final String getCourseSection() {
		return roster.getCourseSectionName();
	}
	
	public final void setCourseSection(String courseSection) {
		roster.setCourseSectionName(courseSection);
	}
	
	public String getRosterName() {		
		return roster.getCourseSectionName();
	}

	public void setRosterName(String rosterName) {
		roster.setCourseSectionName(rosterName);
	}
	
	
	public final String getResidenceDistrictIdentifier() {
		return parentToSchool.getDisplayIdentifier();
	}
	
	public final void setResidenceDistrictIdentifier(
			String residenceDistrictIdentifier) {
		parentToSchool.setDisplayIdentifier(residenceDistrictIdentifier);
	}
	
	public final String getTascEducatorIdentifier() {
		return educator.getUniqueCommonIdentifier();
	}
	
	public final void setTascEducatorIdentifier(String educatorIdentifier) {
		educator.setUniqueCommonIdentifier(educatorIdentifier);
		educator.setUserName(educatorIdentifier + ParsingConstants.BLANK);
		educator.setPassword(educatorIdentifier + ParsingConstants.BLANK);
		//educator.setEmail(educatorIdentifier + ParsingConstants.BLANK);
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
				//educator.setEmail(authInfo.substring(0,  Math.min(45, authInfo.length())));
			}
		}
	}		
	
	public final String getSchoolIdentifier() {
		return school.getDisplayIdentifier();
	}
	
	public final void setSchoolIdentifier(String schoolIdentifier) {
		school.setDisplayIdentifier(schoolIdentifier);
	}
	public final int getTascCurrentSchoolYear() {
		return enrollment.getCurrentSchoolYear();
	}
	
	public final void setTascCurrentSchoolYear(int currentSchoolYear) {
		enrollment.setCurrentSchoolYear(currentSchoolYear);
	}
	
	public final Integer getCourseStatus() {
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
	
	public final String getCourseStatusStr() {
		if (enrollmentStatus == null) {
			return null;
		} else {
			return enrollmentStatus.getCategoryCode();
		}
	}	
	
	public final void setCourseStatus(Integer enrollmentStat) {
		if (enrollmentStatus == null) {
			enrollmentStatus = new Category();
		}
		if (enrollmentStat == null) {
			enrollmentStatus.setCategoryCode(null);
		} else {
			enrollmentStatus.setCategoryCode(enrollmentStat + ParsingConstants.BLANK);
		}
	}

	public String getTascRecordType() {
		return tascRecordType;
	}


	public void setTascRecordType(String tascRecordType) {
		this.tascRecordType = tascRecordType;
	}


	public Organization getSchool() {
		return school;
	}


	public void setSchool(Organization school) {
		this.school = school;
	}


	public Organization getParentToSchool() {
		return parentToSchool;
	}


	public void setParentToSchool(Organization parentToSchool) {
		this.parentToSchool = parentToSchool;
	}


	public ContentArea getStateSubjectArea() {
		return stateSubjectArea;
	}


	public void setStateSubjectArea(ContentArea stateSubjectArea) {
		this.stateSubjectArea = stateSubjectArea;
	}


	public Category getEnrollmentStatus() {
		return enrollmentStatus;
	}


	public void setEnrollmentStatus(Category enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	public Long getEnrollmentStatusId() {
		return (enrollmentStatus == null ? null : enrollmentStatus.getId());
	}
	
	public ContentArea getStateCourse() {
		return stateCourse;
	}


	public void setStateCourse(ContentArea stateCourse) {
		this.stateCourse = stateCourse;
	}


	public User getEducator() {
		return educator;
	}


	public void setEducator(User educator) {
		this.educator = educator;
	}


	public Roster getRoster() {
		return roster;
	}


	public void setRoster(Roster roster) {
		this.roster = roster;
	}


	public Enrollment getEnrollment() {
		return enrollment;
	}


	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}


	public String getRemovefromroster() {
		return removefromroster;
	}


	public void setRemovefromroster(String removefromroster) {
		this.removefromroster = removefromroster;
	}


	public String getLineNumber() {
		return lineNumber;
	}


	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}


	public void setStudent(Student student) {
		this.student = student;
	}

	public String getTascAypSchoolIdentifier() {
		return this.getEnrollment().getAypSchoolIdentifier();
	}
	
	public void setTascAypSchoolIdentifier(String aypSchoolId) {
		this.getEnrollment().setAypSchoolIdentifier(aypSchoolId) ;
	} 
	
	
	public final String getTeacherLastName() {
		return educator.getSurName();
	}
	
	public final void setTeacherLastName(String educatorLastName) {
		educator.setSurName(educatorLastName);
	}
	
	public final String getTeacherFirstName() {
		return educator.getFirstName();
	}
	
	public final void setTeacherFirstName(String educatorFirstName) {
		educator.setFirstName(educatorFirstName);
	}
	
	public final String getTeacherMiddleName() {
		return educator.getMiddleName();
	}
	
	public final void setTeacherMiddleName(String educatorMiddleName){
		educator.setMiddleName(educatorMiddleName);
	}
	
	public final String getStudentLegalLastName() {
		return student.getLegalLastName();
	}
	
	public final void setStudentLegalLastName(String legalLastName) {
		if (StringUtils.isNotEmpty(legalLastName)) {
			this.student.setLegalLastName(legalLastName);
		}
	}
	
	public final String getStudentLegalMiddleName(){
		return student.getLegalMiddleName();
	}
	
	public final void setStudentLegalMiddleName(String legalMiddleName){
		if (StringUtils.isNotEmpty(legalMiddleName)) {
			this.student.setLegalMiddleName(legalMiddleName);
		}
	}
	
	public final String getStudentLegalFirstName(){
		return student.getLegalFirstName();
	}
	
	public final void setStudentLegalFirstName(String legalFirstName){
		if (StringUtils.isNotEmpty(legalFirstName)) {
			this.student.setLegalFirstName(legalFirstName);
		}
	}
	
	public Long getLocalCourseId() {
		return (stateCourse == null ? null : stateCourse.getId());
	}
	
	public void setLocalCourseId(Long id){
		stateCourse.setId(id);
	}
	
	public final void setEducatorEmailId(String emailId){
		this.educator.setEmail(emailId);
	}
	
	public final String getEducatorEmailId(){
		return this.educator.getEmail();
	}
	
	public final String getStateCourseCode() {
		return (stateCourse == null ? null : stateCourse.getName());
	}
	
	public final void setStateCourseCode(String stateCourseCode) {
		if (stateCourse == null) {
			stateCourse = new ContentArea();
		}
		stateCourse.setName(stateCourseCode);
	}
	
	public Long getStateCourseId() {
		return (stateCourse == null ? null : stateCourse.getId());
	}
	
	public Long getStudentCurrentGradeLevel() {
		return getEnrollment().getCurrentGradeLevel();
	}
	public void setStudentCurrentGradeLevel(String studentCurrentGradeLevel) {
		getEnrollment().setCurrentGradeLevel(Long.valueOf(studentCurrentGradeLevel));
	}
	
	
}
