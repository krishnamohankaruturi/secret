package edu.ku.cete.util.json;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.Roster;

public class EnrlAndRosterEventTrackerConverter {
	public static String getNewEnrollmentEvent(Enrollment newEnrollment) {		
		SimpleDateFormat formmatter = new SimpleDateFormat("yyyy-MM-dd");
		String schoolEntranceDate = StringUtils.EMPTY;
		if(newEnrollment.getSchoolEntryDate() != null) {
			schoolEntranceDate = formmatter.format(newEnrollment.getSchoolEntryDate());
		}
		String newValues = "{\"studentId\":" + newEnrollment.getStudentId() + ",\"stateId\":" + newEnrollment.getStudent().getStateId() 
									+ ",\"stateStudentIdentifier\":\"" + newEnrollment.getStudent().getStateStudentIdentifier() 
									+ "\",\"aypSchool\":" + newEnrollment.getAypSchoolId() + ",\"attendanceSchoolId\":"+ newEnrollment.getAttendanceSchoolId()
									+ ",\"grade\":" + newEnrollment.getCurrentGradeLevel() + ",\"schoolEntryDate\":\"" + schoolEntranceDate +"\"}"; 
		return newValues;
		
	}

	public static String getGradeLevelChangeEvent(Enrollment newEnrollment, Long oldGradeId) {			
		String newValues = "{\"studentId\":" + newEnrollment.getStudentId() + ",\"stateId\":" + newEnrollment.getStudent().getStateId() 
				+ ",\"stateStudentIdentifier\":\"" + newEnrollment.getStudent().getStateStudentIdentifier() 
				+ "\",\"aypSchool\":" + newEnrollment.getAypSchoolId() + ",\"attendanceSchoolId\":"+ newEnrollment.getAttendanceSchoolId()
				+ ",\"newGrade\":" + newEnrollment.getCurrentGradeLevel() + ",\"oldGrade\":" + oldGradeId + "}";
		return newValues;
	}

	public static String getExitStudentEvent(Enrollment enrollment) {
		SimpleDateFormat formmatter = new SimpleDateFormat("yyyy-MM-dd");
		String schoolExitWithdrawalDate = StringUtils.EMPTY;
		if(enrollment.getExitWithdrawalDate() != null) {
			schoolExitWithdrawalDate = formmatter.format(enrollment.getExitWithdrawalDate());
		}
		String newValues = "{\"studentId\":" + enrollment.getStudentId() + ",\"stateId\":" + enrollment.getStudent().getStateId() 
				+ ",\"stateStudentIdentifier\":\"" + enrollment.getStudent().getStateStudentIdentifier() 
				+ "\",\"aypSchool\":" + enrollment.getAypSchoolId() + ",\"attendanceSchoolId\":"+ enrollment.getAttendanceSchoolId()
				+ ",\"exitWithdrawalDate\":\"" + schoolExitWithdrawalDate + "\",\"exitReason\":\""+ enrollment.getExitWithdrawalType() + "\"}";
		return newValues;
	}

	public static String getTeacherChangeEvent(Roster upRoster, Long oldEducatorId) {
		String newValues = "{\"rosterId\":" + upRoster.getId()
				+ ",\"oldEducatorId\":" + oldEducatorId + ",\"newEducatorId\":" + upRoster.getTeacherId() + "}";
		return newValues;
	}

	public static String getSubjectOrCourseChangeEvent(Roster upRoster, Roster existingRoster) {
		String oldCourseCode = StringUtils.EMPTY;
		String newCourseCode = StringUtils.EMPTY;
		if(StringUtils.isNotEmpty(existingRoster.getStateCourseCode())) {
			oldCourseCode = existingRoster.getStateCourseCode();
		}
		if(StringUtils.isNotEmpty(upRoster.getStateCourseCode())) {
			newCourseCode = upRoster.getStateCourseCode();
		}
		String newValues = "{\"rosterId\":" + upRoster.getId() + ",\"oldSubjectId\":" + existingRoster.getStateSubjectAreaId() + ",\"newSubjectAreaId\":" + upRoster.getStateSubjectAreaId() 
				+ ",\"oldCourseId\":" + existingRoster.getStateCoursesId() + ",\"newCourseId\":" + upRoster.getStateCoursesId() 
				+",\"oldCourseCode:\":\"" + oldCourseCode + "\",\"newCourseCode\":\"" + newCourseCode +"\"}";
		return newValues;
	}

	public static String getAddOrRemoveStudentToRosterEvent(EnrollmentsRosters er) {
		String newValues = "{\"rosterId\":" + er.getRosterId() + ", \"enrollmentId\":" 
				+ er.getEnrollmentId() + ",\"enrollmentRosterId\":" + er.getId() + "}";
		return newValues;
	}
}
