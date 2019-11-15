package edu.ku.cete.domain.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DashboardShortDurationTesting {
	
	private static String DATE_FORMAT = "MM/dd/yyyy - hh:mm:ss a z";

    private Long id;
    private String assessmentProgram;
    private Long assessmentProgramId;
    private String contentArea;
    private Long contentAreaId;
    private String grade;
    private Long gradeCourseId;
    private String gradeBand;
    private Long gradeBandId;
    private Long studentId;
    private String studentName;
    private String teacherName;
    private String stateStudentIdentifier;
    private String schoolName;
    private Long schoolId;
    private String districtName;
    private Long districtId;
    private String stateName;
    private Long stateId;
    private Date startDateTime;
    private Date endDateTime;
    private Long studentsTestsId;
    private String testName;
    private Long testSessionId;
    private String stageName;
    private Date createdDate;
    private Date modifiedDate;
    private Long testItemCount;
    private String allCorrectIndicator;
    private String testTimeSpan;
    private String rosterName;
    private TimeZone timezone;
    private String studentLastName;
	private String studentFirstName;
	
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAssessmentProgram() {
        return assessmentProgram;
    }

    public void setAssessmentProgram(String assessmentProgram) {
        this.assessmentProgram = assessmentProgram == null ? null : assessmentProgram.trim();
    }
    public Long getAssessmentProgramId() {
        return assessmentProgramId;
    }


    public void setAssessmentProgramId(Long assessmentProgramId) {
        this.assessmentProgramId = assessmentProgramId;
    }
    public String getContentArea() {
        return contentArea;
    }

    public void setContentArea(String contentArea) {
        this.contentArea = contentArea == null ? null : contentArea.trim();
    }
    public Long getContentAreaId() {
        return contentAreaId;
    }

    public void setContentAreaId(Long contentAreaId) {
        this.contentAreaId = contentAreaId;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade == null ? null : grade.trim();
    }
    public Long getGradeCourseId() {
        return gradeCourseId;
    }

    public void setGradeCourseId(Long gradeCourseId) {
        this.gradeCourseId = gradeCourseId;
    }
    public String getGradeBand() {
        return gradeBand;
    }

    public void setGradeBand(String gradeBand) {
        this.gradeBand = gradeBand == null ? null : gradeBand.trim();
    }
    public Long getGradeBandId() {
        return gradeBandId;
    }

    public void setGradeBandId(Long gradeBandId) {
        this.gradeBandId = gradeBandId;
    }
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName == null ? null : studentName.trim();
    }
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getStateStudentIdentifier() {
        return stateStudentIdentifier;
    }

    public void setStateStudentIdentifier(String stateStudentIdentifier) {
        this.stateStudentIdentifier = stateStudentIdentifier == null ? null : stateStudentIdentifier.trim();
    }
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName == null ? null : schoolName.trim();
    }
    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }
    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName == null ? null : districtName.trim();
    }

      public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }
    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName == null ? null : stateName.trim();
    }
    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }


    public Date getStartDateTime() {
        return startDateTime;
    }
    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    public Long getStudentsTestsId() {
        return studentsTestsId;
    }
    public void setStudentsTestsId(Long studentsTestsId) {
        this.studentsTestsId = studentsTestsId;
    }

    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName == null ? null : testName.trim();
    }

    public Long getTestSessionId() {
        return testSessionId;
    }
    public void setTestSessionId(Long testSessionId) {
        this.testSessionId = testSessionId;
    }
    public String getStageName() {
        return stageName;
    }
    public void setStageName(String stageName) {
        this.stageName = stageName == null ? null : stageName.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	public Long getTestItemCount() {
		return testItemCount;
	}
	public void setTestItemCount(Long testItemCount) {
		this.testItemCount = testItemCount;
	}
	public String getAllCorrectIndicator() {
		return allCorrectIndicator;
	}
	public void setAllCorrectIndicator(String allCorrectIndicator) {
		this.allCorrectIndicator = allCorrectIndicator;
	}
	public String getTestTimeSpan() {
		return testTimeSpan;
	}
	public void setTestTimeSpan(String testTimeSpan) {
		this.testTimeSpan = testTimeSpan;
	}
	public String getRosterName() {
		return rosterName;
	}
	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}

	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public TimeZone getTimezone() {
		if(timezone != null){
			return timezone;
		}else{
			return TimeZone.getTimeZone("US/Central");
		}
	}
	public void setTimezone(TimeZone timezone) {
		this.timezone = timezone;
	}
	public String getStarted() {
		if(startDateTime != null){
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			format.setTimeZone(getTimezone());
			return format.format(startDateTime);
		}else{
			return "";
		}
	}
	public String getEnded() {
		if(endDateTime != null){
			SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
			format.setTimeZone(getTimezone());
			return format.format(endDateTime);
		}else{
			return "";
		}
		
	}	
}