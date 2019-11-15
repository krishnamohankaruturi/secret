package edu.ku.cete.customextract;

public class KAPCustomExtract {
	private String state ;
	
	private String  district ;
	
	private String school ;
	
	private String schoolIdentifer;
	
	private String grade;
	
	private String studentLastname;
	
	private String studentFirstname;
	
	private String studentMiddlename;
	
	private String stateStudentIdentifier;
	
	private String localStudentIdentifier;
	
	private String enrollmentId;
	
	private String enrollmentStatus ;
	
	private String subject ;
	
	private String testSessionName ;
	
	private String testSessionId;
	
	private String testid; 
	
	private String testCollectionId ;
	
	private String testStatus;
	
	private String testActiveFlag;
	
	private String stage;
	
	private String totalItems;
	
	private String totalOmittedItems ;
	
	private String testStartDate ; 
	
	private String testEndDate ;
	
	private String specialCircumstance ;
	
	private String specialCircumstanceStatus ;
	
	private String  lastReactivatedDateTime ;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchoolIdentifer() {
		return schoolIdentifer;
	}

	public void setSchoolIdentifer(String schoolIdentifer) {
		this.schoolIdentifer = schoolIdentifer;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStudentLastname() {
		return studentLastname;
	}

	public void setStudentLastname(String studentLastname) {
		this.studentLastname = studentLastname;
	}

	public String getStudentFirstname() {
		return studentFirstname;
	}

	public void setStudentFirstname(String studentFirstname) {
		this.studentFirstname = studentFirstname;
	}

	public String getStudentMiddlename() {
		return studentMiddlename;
	}

	public void setStudentMiddlename(String studentMiddlename) {
		this.studentMiddlename = studentMiddlename;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getLocalStudentIdentifier() {
		return localStudentIdentifier;
	}

	public void setLocalStudentIdentifier(String localStudentIdentifier) {
		this.localStudentIdentifier = localStudentIdentifier;
	}

	public String getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(String enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getEnrollmentStatus() {
		return enrollmentStatus;
	}

	public void setEnrollmentStatus(String enrollmentStatus) {
		this.enrollmentStatus = enrollmentStatus;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTestSessionName() {
		return testSessionName;
	}

	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}

	public String getTestSessionId() {
		return testSessionId;
	}

	public void setTestSessionId(String testSessionId) {
		this.testSessionId = testSessionId;
	}

	public String getTestid() {
		return testid;
	}

	public void setTestid(String testid) {
		this.testid = testid;
	}

	public String getTestCollectionId() {
		return testCollectionId;
	}

	public void setTestCollectionId(String testCollectionId) {
		this.testCollectionId = testCollectionId;
	}

	public String getTestStatus() {
		return testStatus;
	}

	public void setTestStatus(String testStatus) {
		this.testStatus = testStatus;
	}

	public String getTestActiveFlag() {
		return testActiveFlag;
	}

	public void setTestActiveFlag(String testActiveFlag) {
		this.testActiveFlag = testActiveFlag;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(String totalItems) {
		this.totalItems = totalItems;
	}

	public String getTotaloMittedItems() {
		return totalOmittedItems;
	}

	public void setTotalOmittedItems(String totalOmittedItems) {
		this.totalOmittedItems = totalOmittedItems;
	}

	public String getTestStartDate() {
		return testStartDate;
	}

	public void setTestStartDate(String testStartDate) {
		this.testStartDate = testStartDate;
	}

	public String getTestEndDate() {
		return testEndDate;
	}

	public void setTestEndDate(String testEndDate) {
		this.testEndDate = testEndDate;
	}

	public String getSpecialCircumstance() {
		return specialCircumstance;
	}

	public void setSpecialCircumstance(String specialCircumstance) {
		this.specialCircumstance = specialCircumstance;
	}

	public String getSpecialCircumstanceStatus() {
		return specialCircumstanceStatus;
	}

	public void setSpecialCircumstanceStatus(String specialCircumstanceStatus) {
		this.specialCircumstanceStatus = specialCircumstanceStatus;
	}

	public String getLastReactivatedDateTime() {
		return lastReactivatedDateTime;
	}

	public void setLastReactivatedDateTime(String lastReactivatedDateTime) {
		this.lastReactivatedDateTime = lastReactivatedDateTime;
	}

	public void validate() {
        if (this.state == null) {
            this.state = "";
        }
        if (this.district == null) {
            this.district = "";
        }
        
        if (this.school == null) {
            this.school = "";
        }
        if (this.schoolIdentifer == null) {
            this.schoolIdentifer = "";
        }
        if (this.grade == null) {
            this.grade = "";
        }
        if (this.studentLastname == null) {
            this.studentLastname = "";
        }
        if (this.studentFirstname == null) {
            this.studentFirstname = "";
        }
        if (this.studentMiddlename == "") {
            this.studentMiddlename = null;
        }
        if (this.stateStudentIdentifier == null) {
            this.stateStudentIdentifier = null;
        }
        if (this.localStudentIdentifier == null) {
            this.localStudentIdentifier = null;
        }
        if (this.enrollmentId == null) {
            this.enrollmentId = null;
        }
        if (this.enrollmentStatus == null) {
            this.enrollmentStatus = null;
        }
        if (this.subject == null) {
            this.subject = null;
        }
        if (this.testSessionName == null) {
            this.testSessionName = "";
        }
        if (this.testSessionId == null) {
            this.testSessionId = "";
        }
        if (this.testid == null) {
            this.testid = "";
        }
        if (this.testCollectionId == null) {
            this.testCollectionId = "";
        }
        if (this.testStatus == null) {
            this.testStatus = "";
        }
        if (this.testActiveFlag == null) {
            this.testActiveFlag = "";
        }
        if (this.stage == null) {
            this.stage = "";
        }
        if (this.totalItems == null) {
            this.totalItems = "";
        }
        if (this.totalOmittedItems == null) {
            this.totalOmittedItems = "";
        }
        if (this.testStartDate == null) {
            this.testStartDate = "";
        }
        if (this.testEndDate == null) {
            this.testEndDate = "";
        }
        if (this.specialCircumstance == null) {
            this.specialCircumstance = "";
        }
        if (this.specialCircumstanceStatus == null) {
            this.specialCircumstanceStatus = "";
        }
        if (this.lastReactivatedDateTime == null) {
            this.lastReactivatedDateTime = "";
        }
    }

}
