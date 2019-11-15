package edu.ku.cete.domain.enrollment;

public class EnrollmentsOrganizationInfo {

	Long enrollmentId;
	String schoolName;
	String districtName;
	Long attendanceSchoolId;	
	
	public Long getAttendanceSchoolId() {
		return attendanceSchoolId;
	}

	public void setAttendanceSchoolId(Long attendanceSchoolId) {
		this.attendanceSchoolId = attendanceSchoolId;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
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

}
