package edu.ku.cete.web;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Amani
 *
 */
public class TestingReadinessExtractDTO implements Serializable {

	private static final long serialVersionUID = -1662605340382623404L;

	private String state;
	private String districtName;
	private String attendanceDistrictIdentifier;
	private String school;
	private String aypSchoolIdentifier;
	private String studentLastName;
	private String studentFirstName;
	private String studentMiddleInitial;
	private String generationCode;
	private String stateStudentIdentifier;
	private Date dateOfBirth;
	private String gender;
	private String grade;
	private String studentLoginUsername;
	private String studentLoginPassword;
	private String studentAssessmentProgram;
	private String grouping1;
	private String grouping2;
	private String enrollmentLastModified;
	private String enrollmentLastModifiedBy;
	private String pnpLastModified;
	private String pnpLastModifiedBy;
	private boolean ela;
	private boolean math;
	private boolean science;
	private boolean hgss;
	private boolean kelpa2;
	private boolean cpassGeneral;
	private boolean comprehensiveAg;
	private boolean animalSystems;
	private boolean plantSystems;
	private boolean manufacturing;
	private boolean designPreconstruction;
	private boolean finance;
	private boolean compBusiness;
	private Long schoolId;
	private Long studentId;
	private String accountabilityDistrictIdentifier;
	private String comprehensiveRace;
	private String hispanicEthnicity;
	private String schoolDisplayIdentifier;
	private HashSet<String> pltwCourseEnrolled;
	private HashMap<String, String> pltwPNPSetting;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getAttendanceDistrictIdentifier() {
		return attendanceDistrictIdentifier;
	}

	public void setAttendanceDistrictIdentifier(String attendanceDistrictIdentifier) {
		this.attendanceDistrictIdentifier = attendanceDistrictIdentifier;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getAypSchoolIdentifier() {
		return aypSchoolIdentifier;
	}

	public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
		this.aypSchoolIdentifier = aypSchoolIdentifier;
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

	public String getStudentMiddleInitial() {
		return studentMiddleInitial;
	}

	public void setStudentMiddleInitial(String studentMiddleInitial) {
		this.studentMiddleInitial = studentMiddleInitial;
	}

	public String getGenerationCode() {
		return generationCode;
	}

	public void setGenerationCode(String generationCode) {
		this.generationCode = generationCode;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStudentLoginUsername() {
		return studentLoginUsername;
	}

	public void setStudentLoginUsername(String studentLoginUsername) {
		this.studentLoginUsername = studentLoginUsername;
	}

	public String getStudentLoginPassword() {
		return studentLoginPassword;
	}

	public void setStudentLoginPassword(String studentLoginPassword) {
		this.studentLoginPassword = studentLoginPassword;
	}

	public String getStudentAssessmentProgram() {
		return studentAssessmentProgram;
	}

	public void setStudentAssessmentProgram(String studentAssessmentProgram) {
		this.studentAssessmentProgram = studentAssessmentProgram;
	}

	public String getGrouping1() {
		return grouping1;
	}

	public void setGrouping1(String grouping1) {
		this.grouping1 = grouping1;
	}

	public String getGrouping2() {
		return grouping2;
	}

	public void setGrouping2(String grouping2) {
		this.grouping2 = grouping2;
	}

	public String getEnrollmentLastModified() {
		return enrollmentLastModified;
	}

	public void setEnrollmentLastModified(String enrollmentLastModified) {
		this.enrollmentLastModified = enrollmentLastModified;
	}

	public String getEnrollmentLastModifiedBy() {
		return enrollmentLastModifiedBy;
	}

	public void setEnrollmentLastModifiedBy(String enrollmentLastModifiedBy) {
		this.enrollmentLastModifiedBy = enrollmentLastModifiedBy;
	}

	public String getPnpLastModified() {
		return pnpLastModified;
	}

	public void setPnpLastModified(String pnpLastModified) {
		this.pnpLastModified = pnpLastModified;
	}

	public String getPnpLastModifiedBy() {
		return pnpLastModifiedBy;
	}

	public void setPnpLastModifiedBy(String pnpLastModifiedBy) {
		this.pnpLastModifiedBy = pnpLastModifiedBy;
	}

	public boolean isEla() {
		return ela;
	}

	public void setEla(boolean ela) {
		this.ela = ela;
	}

	public boolean isMath() {
		return math;
	}

	public void setMath(boolean math) {
		this.math = math;
	}

	public boolean isScience() {
		return science;
	}

	public void setScience(boolean science) {
		this.science = science;
	}

	public boolean isHgss() {
		return hgss;
	}

	public void setHgss(boolean hgss) {
		this.hgss = hgss;
	}

	public boolean isKelpa2() {
		return kelpa2;
	}

	public void setKelpa2(boolean kelpa2) {
		this.kelpa2 = kelpa2;
	}

	public boolean isCpassGeneral() {
		return cpassGeneral;
	}

	public void setCpassGeneral(boolean cpassGeneral) {
		this.cpassGeneral = cpassGeneral;
	}

	public boolean isComprehensiveAg() {
		return comprehensiveAg;
	}

	public void setComprehensiveAg(boolean comprehensiveAg) {
		this.comprehensiveAg = comprehensiveAg;
	}

	public boolean isAnimalSystems() {
		return animalSystems;
	}

	public void setAnimalSystems(boolean animalSystems) {
		this.animalSystems = animalSystems;
	}

	public boolean isPlantSystems() {
		return plantSystems;
	}

	public void setPlantSystems(boolean plantSystems) {
		this.plantSystems = plantSystems;
	}

	public boolean isManufacturing() {
		return manufacturing;
	}

	public void setManufacturing(boolean manufacturing) {
		this.manufacturing = manufacturing;
	}

	public boolean isDesignPreconstruction() {
		return designPreconstruction;
	}

	public void setDesignPreconstruction(boolean designPreconstruction) {
		this.designPreconstruction = designPreconstruction;
	}

	public boolean isFinance() {
		return finance;
	}

	public void setFinance(boolean finance) {
		this.finance = finance;
	}

	public boolean isCompBusiness() {
		return compBusiness;
	}

	public void setCompBusiness(boolean compBusiness) {
		this.compBusiness = compBusiness;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return the accountabilityDistrictIdentifier
	 */
	public String getAccountabilityDistrictIdentifier() {
		return accountabilityDistrictIdentifier;
	}

	/**
	 * @param accountabilityDistrictIdentifier the accountabilityDistrictIdentifier to set
	 */
	public void setAccountabilityDistrictIdentifier(
			String accountabilityDistrictIdentifier) {
		this.accountabilityDistrictIdentifier = accountabilityDistrictIdentifier;
	}

	public String getComprehensiveRace() {
		return comprehensiveRace;
	}
	
	public void setComprehensiveRace(String comprehensiveRace) {
		this.comprehensiveRace = comprehensiveRace;
	}
	
	public String getHispanicEthnicityStr() {
		
		String hispanicEthnicity="";
		
		if (this.hispanicEthnicity != null  && this.hispanicEthnicity.equalsIgnoreCase("true")) {
			hispanicEthnicity= "Yes";
		} else if (this.hispanicEthnicity != null && this.hispanicEthnicity.equalsIgnoreCase("false")) {
			hispanicEthnicity= "No";
		}else if(this.hispanicEthnicity != null) {
			hispanicEthnicity= this.hispanicEthnicity;
		}
		
		return hispanicEthnicity;
	}
	
	public String HispanicethniCity() {
		return hispanicEthnicity;
	}
	
	public void setHispanicethniCity(String hispanicEthnicity) {
		this.hispanicEthnicity = hispanicEthnicity;
	}
	
	public HashSet<String> getPltwCourseEnrolled() {
		if(this.pltwCourseEnrolled==null) {
			return new HashSet<String>();
		}
		return this.pltwCourseEnrolled;
	}
	
	public void setPltwCourseEnrolled(HashSet<String> pltwCourseEnrolled) {
		this.pltwCourseEnrolled = pltwCourseEnrolled;
	}
	
	public HashMap<String, String> getPltwPNPSetting() {
		if(this.pltwPNPSetting==null) {
			return new HashMap<String, String>();
		}
		return this.pltwPNPSetting;
	}
	
	public void setPltwPNPSetting(HashMap<String, String> pltwPNPSetting) {
		this.pltwPNPSetting = pltwPNPSetting;
	}

	public String getSchoolDisplayIdentifier() {
		return schoolDisplayIdentifier;
	}

	public void setSchoolDisplayIdentifier(String schoolDisplayIdentifier) {
		this.schoolDisplayIdentifier = schoolDisplayIdentifier;
	}

}
