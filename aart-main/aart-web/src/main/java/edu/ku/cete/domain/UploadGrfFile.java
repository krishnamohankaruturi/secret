package edu.ku.cete.domain;
import java.io.Serializable;
import java.util.Date;

public class UploadGrfFile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4309299256101254691L;
	private Long id;
	private Long externalUniqueRowIdentifier;
	private Long batchUploadId;
	private Long assessmentProgramId;
	private Long reportYear;
	private String localStudentIdentifier;
	private Long  studentId;
	private String stateStudentIdentifier;
	private String aypSchoolIdentifier;
	private String currentGradelevel;
	private Long gradeId;
	private String legalFirstName;
	private String legalMiddleName;
	private String legalLastName;
	private String generationCode;
	private String userName;
	private String firstLanguage;
	private Date dateOfBirth;
	private String gender;
	private String comprehensiveRace;
	private String hispanicEthnicity;
	private String primaryDisabilityCode;
	private String esolParticipationCode;
	private Date schoolEntryDate;
	private Date districtEntryDate;
	private Date stateEntryDate;
	private String attendanceSchoolProgramIdentifier;
	private String state;
	private String stateCode;
	private Long stateId;
	private String residenceDistrictIdentifier;
	private String districtName;
	private Long districtId;
	private String schoolIdentifier;
	private String schoolName;
	private Long schoolId;
	private String educatorFirstName;
	private String educatorLastName;
	private String educatorUserName;
	private String educatorIdentifier;
	private String kiteEducatorIdentifier;
	private String strKiteEducatorIdentifier;
	private Date exitWithdrawalDate;
	private String exitWithdrawalType;
	private String subject;
	private Long subjectId;
	private String finalBand;
	private String sgp;
	private Long  performanceLevel;
	private Long  nyPerformanceLevel;
	private Long invalidationCode;
	private String totalLinkageLevelsMastered;
	private String iowaLinkageLevelsMastered;
	private Long uploadedUserId;
	private Long ee1;
	private Long ee2;
	private Long ee3;
	private Long ee4;
	private Long ee5;
	private Long ee6;
	private Long ee7;
	private Long ee8;
	private Long ee9;
	private Long ee10;
	private Long ee11;
	private Long ee12;	
	private Long ee13;
	private Long ee14;
	private Long ee15;
	private Long ee16;
	private Long ee17;
	private Long ee18;
	private Long ee19;
	private Long ee20;
	private Long ee21;
	private Long ee22;
	private Long ee23;
	private Long ee24;
	private Long ee25;
	private Long ee26;
	private String lineNumber;
	private boolean activeFlag;
	private Date createdDate;
	private Long versionId;
	private boolean gradeChange;
	private String attendanceSchoolProgramName;
	private String aypSchoolName;
	
	private boolean original;
	private boolean recentVersion;
	
	private String accountabilityDistrictIdentifier;
	private String stateUse;
	private String course;
	
	
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the externalUniqueRowIdentifier
	 */
	public Long getExternalUniqueRowIdentifier() {
		return externalUniqueRowIdentifier;
	}
	/**
	 * @param externalUniqueRowIdentifier the externalUniqueRowIdentifier to set
	 */
	public void setExternalUniqueRowIdentifier(Long externalUniqueRowIdentifier) {
		this.externalUniqueRowIdentifier = externalUniqueRowIdentifier;
	}
	/**
	 * @return the batchUploadId
	 */
	public Long getBatchUploadId() {
		return batchUploadId;
	}
	/**
	 * @param batchUploadId the batchUploadId to set
	 */
	public void setBatchUploadId(Long batchUploadId) {
		this.batchUploadId = batchUploadId;
	}
	/**
	 * @return the assessmentProgramId
	 */
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	/**
	 * @param assessmentProgramId the assessmentProgramId to set
	 */
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	/**
	 * @return the reportYear
	 */
	public Long getReportYear() {
		return reportYear;
	}
	/**
	 * @param reportYear the reportYear to set
	 */
	public void setReportYear(Long reportYear) {
		this.reportYear = reportYear;
	}
	/**
	 * @return the localStudentIdentifier
	 */
	public String getLocalStudentIdentifier() {
		return localStudentIdentifier;
	}
	/**
	 * @param localStudentIdentifier the localStudentIdentifier to set
	 */
	public void setLocalStudentIdentifier(String localStudentIdentifier) {
		this.localStudentIdentifier = localStudentIdentifier;
	}
	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * @return the stateStudentIdentifier
	 */
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	/**
	 * @param stateStudentIdentifier the stateStudentIdentifier to set
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	/**
	 * @return the aypSchoolIdentifier
	 */
	public String getAypSchoolIdentifier() {
		return aypSchoolIdentifier;
	}
	/**
	 * @param aypSchoolIdentifier the aypSchoolIdentifier to set
	 */
	public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
		this.aypSchoolIdentifier = aypSchoolIdentifier;
	}
	/**
	 * @return the currentGradelevel
	 */
	public String getCurrentGradelevel() {
		return currentGradelevel;
	}
	/**
	 * @param currentGradelevel the currentGradelevel to set
	 */
	public void setCurrentGradelevel(String currentGradelevel) {
		this.currentGradelevel = currentGradelevel;
	}
	/**
	 * @return the gradeId
	 */
	public Long getGradeId() {
		return gradeId;
	}
	/**
	 * @param gradeId the gradeId to set
	 */
	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}
	/**
	 * @return the legalFirstName
	 */
	public String getLegalFirstName() {
		return legalFirstName;
	}
	/**
	 * @param legalFirstName the legalFirstName to set
	 */
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	/**
	 * @return the legalMiddleName
	 */
	public String getLegalMiddleName() {
		return legalMiddleName;
	}
	/**
	 * @param legalMiddleName the legalMiddleName to set
	 */
	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}
	/**
	 * @return the legalLastName
	 */
	public String getLegalLastName() {
		return legalLastName;
	}
	/**
	 * @param legalLastName the legalLastName to set
	 */
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	/**
	 * @return the generationCode
	 */
	public String getGenerationCode() {
		return generationCode;
	}
	/**
	 * @param generationCode the generationCode to set
	 */
	public void setGenerationCode(String generationCode) {
		this.generationCode = generationCode;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the firstLanguage
	 */
	public String getFirstLanguage() {
		return firstLanguage;
	}
	/**
	 * @param firstLanguage the firstLanguage to set
	 */
	public void setFirstLanguage(String firstLanguage) {
		this.firstLanguage = firstLanguage;
	}
	/**
	 * @return the dateOfBirth
	 */
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}
	/**
	 * @return the comprehensiveRace
	 */
	public String getComprehensiveRace() {
		return comprehensiveRace;
	}
	/**
	 * @param comprehensiveRace the comprehensiveRace to set
	 */
	public void setComprehensiveRace(String comprehensiveRace) {
		this.comprehensiveRace = comprehensiveRace;
	}
	/**
	 * @return the hispanicEthnicity
	 */
	public String getHispanicEthnicity() {
		return hispanicEthnicity;
	}
	/**
	 * @param hispanicEthnicity the hispanicEthnicity to set
	 */
	public void setHispanicEthnicity(String hispanicEthnicity) {
		this.hispanicEthnicity = hispanicEthnicity;
	}
	/**
	 * @return the primaryDisabilityCode
	 */
	public String getPrimaryDisabilityCode() {
		return primaryDisabilityCode;
	}
	/**
	 * @param primaryDisabilityCode the primaryDisabilityCode to set
	 */
	public void setPrimaryDisabilityCode(String primaryDisabilityCode) {
		this.primaryDisabilityCode = primaryDisabilityCode;
	}
	/**
	 * @return the esolParticipationCode
	 */
	public String getEsolParticipationCode() {
		return esolParticipationCode;
	}
	/**
	 * @param esolParticipationCode the esolParticipationCode to set
	 */
	public void setEsolParticipationCode(String esolParticipationCode) {
		this.esolParticipationCode = esolParticipationCode;
	}
	/**
	 * @return the schoolEntryDate
	 */
	public Date getSchoolEntryDate() {
		return schoolEntryDate;
	}
	/**
	 * @param schoolEntryDate the schoolEntryDate to set
	 */
	public void setSchoolEntryDate(Date schoolEntryDate) {
		this.schoolEntryDate = schoolEntryDate;
	}
	/**
	 * @return the districtEntryDate
	 */
	public Date getDistrictEntryDate() {
		return districtEntryDate;
	}
	/**
	 * @param districtEntryDate the districtEntryDate to set
	 */
	public void setDistrictEntryDate(Date districtEntryDate) {
		this.districtEntryDate = districtEntryDate;
	}
	/**
	 * @return the stateEntryDate
	 */
	public Date getStateEntryDate() {
		return stateEntryDate;
	}
	/**
	 * @param stateEntryDate the stateEntryDate to set
	 */
	public void setStateEntryDate(Date stateEntryDate) {
		this.stateEntryDate = stateEntryDate;
	}
	/**
	 * @return the attendanceSchoolProgramIdentifier
	 */
	public String getAttendanceSchoolProgramIdentifier() {
		return attendanceSchoolProgramIdentifier;
	}
	/**
	 * @param attendanceSchoolProgramIdentifier the attendanceSchoolProgramIdentifier to set
	 */
	public void setAttendanceSchoolProgramIdentifier(
			String attendanceSchoolProgramIdentifier) {
		this.attendanceSchoolProgramIdentifier = attendanceSchoolProgramIdentifier;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}
	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	/**
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	/**
	 * @return the residenceDistrictIdentifier
	 */
	public String getResidenceDistrictIdentifier() {
		return residenceDistrictIdentifier;
	}
	/**
	 * @param residenceDistrictIdentifier the residenceDistrictIdentifier to set
	 */
	public void setResidenceDistrictIdentifier(String residenceDistrictIdentifier) {
		this.residenceDistrictIdentifier = residenceDistrictIdentifier;
	}
	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}
	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	/**
	 * @return the districtId
	 */
	public Long getDistrictId() {
		return districtId;
	}
	/**
	 * @param districtId the districtId to set
	 */
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	/**
	 * @return the schoolIdentifier
	 */
	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}
	/**
	 * @param schoolIdentifier the schoolIdentifier to set
	 */
	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
	}
	/**
	 * @return the schoolName
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * @param schoolName the schoolName to set
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * @return the schoolId
	 */
	public Long getSchoolId() {
		return schoolId;
	}
	/**
	 * @param schoolId the schoolId to set
	 */
	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * @return the educatorFirstName
	 */
	public String getEducatorFirstName() {
		return educatorFirstName;
	}
	/**
	 * @param educatorFirstName the educatorFirstName to set
	 */
	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}
	/**
	 * @return the educatorLastName
	 */
	public String getEducatorLastName() {
		return educatorLastName;
	}
	/**
	 * @param educatorLastName the educatorLastName to set
	 */
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}
	/**
	 * @return the educatorUserName
	 */
	public String getEducatorUserName() {
		return educatorUserName;
	}
	/**
	 * @param educatorUserName the educatorUserName to set
	 */
	public void setEducatorUserName(String educatorUserName) {
		this.educatorUserName = educatorUserName;
	}
	/**
	 * @return the educatorIdentifier
	 */
	public String getEducatorIdentifier() {
		return educatorIdentifier;
	}
	/**
	 * @param educatorIdentifier the educatorIdentifier to set
	 */
	public void setEducatorIdentifier(String educatorIdentifier) {
		this.educatorIdentifier = educatorIdentifier;
	}
	/**
	 * @return the kiteEducatorIdentifier
	 */
	public String getKiteEducatorIdentifier() {
		return kiteEducatorIdentifier;
	}
	/**
	 * @param kiteEducatorIdentifier the kiteEducatorIdentifier to set
	 */
	public void setKiteEducatorIdentifier(String kiteEducatorIdentifier) {
		this.kiteEducatorIdentifier = kiteEducatorIdentifier;
	}
	/**
	 * @return the exitWithdrawalDate
	 */
	public Date getExitWithdrawalDate() {
		return exitWithdrawalDate;
	}
	/**
	 * @param exitWithdrawalDate the exitWithdrawalDate to set
	 */
	public void setExitWithdrawalDate(Date exitWithdrawalDate) {
		this.exitWithdrawalDate = exitWithdrawalDate;
	}
	/**
	 * @return the exitWithdrawalType
	 */
	public String getExitWithdrawalType() {
		return exitWithdrawalType;
	}
	/**
	 * @param exitWithdrawalType the exitWithdrawalType to set
	 */
	public void setExitWithdrawalType(String exitWithdrawalType) {
		this.exitWithdrawalType = exitWithdrawalType;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the subjectId
	 */
	public Long getSubjectId() {
		return subjectId;
	}
	/**
	 * @param subjectId the subjectId to set
	 */
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * @return the finalBand
	 */
	public String getFinalBand() {
		return finalBand;
	}
	/**
	 * @param finalBand the finalBand to set
	 */
	public void setFinalBand(String finalBand) {
		this.finalBand = finalBand;
	}
	/**
	 * @return the sgp
	 */
	public String getSgp() {
		return sgp;
	}
	/**
	 * @param sgp the sgp to set
	 */
	public void setSgp(String sgp) {
		this.sgp = sgp;
	}
	/**
	 * @return the performanceLevel
	 */
	public Long getPerformanceLevel() {
		return performanceLevel;
	}
	/**
	 * @param performanceLevel the performanceLevel to set
	 */
	public void setPerformanceLevel(Long performanceLevel) {
		this.performanceLevel = performanceLevel;
	}
	/**
	 * @return the nyPerformanceLevel
	 */
	public Long getNyPerformanceLevel() {
		return nyPerformanceLevel;
	}
	/**
	 * @param nyPerformanceLevel the nyPerformanceLevel to set
	 */
	public void setNyPerformanceLevel(Long nyPerformanceLevel) {
		this.nyPerformanceLevel = nyPerformanceLevel;
	}
	/**
	 * @return the invalidationCode
	 */
	public Long getInvalidationCode() {
		return invalidationCode;
	}
	/**
	 * @param invalidationCode the invalidationCode to set
	 */
	public void setInvalidationCode(Long invalidationCode) {
		this.invalidationCode = invalidationCode;
	}
	/**
	 * @return the totalLinkageLevelSmastered
	 */
	public String getTotalLinkageLevelsMastered() {
		return totalLinkageLevelsMastered;
	}
	/**
	 * @param totalLinkageLevelSmastered the totalLinkageLevelSmastered to set
	 */
	public void setTotalLinkageLevelsMastered(String totalLinkageLevelsMastered) {
		this.totalLinkageLevelsMastered = totalLinkageLevelsMastered;
	}
	/**
	 * @return the iowaLinkageLevelSmastered
	 */
	public String getIowaLinkageLevelsMastered() {
		return iowaLinkageLevelsMastered;
	}
	/**
	 * @param iowaLinkageLevelSmastered the iowaLinkageLevelSmastered to set
	 */
	public void setIowaLinkageLevelsMastered(String iowaLinkageLevelsMastered) {
		this.iowaLinkageLevelsMastered = iowaLinkageLevelsMastered;
	}
	/**
	 * @return the uploadedUserId
	 */
	public Long getUploadedUserId() {
		return uploadedUserId;
	}
	/**
	 * @param uploadedUserId the uploadedUserId to set
	 */
	public void setUploadedUserId(Long uploadedUserId) {
		this.uploadedUserId = uploadedUserId;
	}
	/**
	 * @return the ee1
	 */
	public Long getEe1() {
		return ee1;
	}
	/**
	 * @param ee1 the ee1 to set
	 */
	public void setEe1(Long ee1) {
		this.ee1 = ee1;
	}
	/**
	 * @return the ee2
	 */
	public Long getEe2() {
		return ee2;
	}
	/**
	 * @param ee2 the ee2 to set
	 */
	public void setEe2(Long ee2) {
		this.ee2 = ee2;
	}
	/**
	 * @return the ee3
	 */
	public Long getEe3() {
		return ee3;
	}
	/**
	 * @param ee3 the ee3 to set
	 */
	public void setEe3(Long ee3) {
		this.ee3 = ee3;
	}
	/**
	 * @return the ee4
	 */
	public Long getEe4() {
		return ee4;
	}
	/**
	 * @param ee4 the ee4 to set
	 */
	public void setEe4(Long ee4) {
		this.ee4 = ee4;
	}
	/**
	 * @return the ee5
	 */
	public Long getEe5() {
		return ee5;
	}
	/**
	 * @param ee5 the ee5 to set
	 */
	public void setEe5(Long ee5) {
		this.ee5 = ee5;
	}
	/**
	 * @return the ee6
	 */
	public Long getEe6() {
		return ee6;
	}
	/**
	 * @param ee6 the ee6 to set
	 */
	public void setEe6(Long ee6) {
		this.ee6 = ee6;
	}
	/**
	 * @return the ee7
	 */
	public Long getEe7() {
		return ee7;
	}
	/**
	 * @param ee7 the ee7 to set
	 */
	public void setEe7(Long ee7) {
		this.ee7 = ee7;
	}
	/**
	 * @return the ee8
	 */
	public Long getEe8() {
		return ee8;
	}
	/**
	 * @param ee8 the ee8 to set
	 */
	public void setEe8(Long ee8) {
		this.ee8 = ee8;
	}
	/**
	 * @return the ee9
	 */
	public Long getEe9() {
		return ee9;
	}
	/**
	 * @param ee9 the ee9 to set
	 */
	public void setEe9(Long ee9) {
		this.ee9 = ee9;
	}
	/**
	 * @return the ee10
	 */
	public Long getEe10() {
		return ee10;
	}
	/**
	 * @param ee10 the ee10 to set
	 */
	public void setEe10(Long ee10) {
		this.ee10 = ee10;
	}
	/**
	 * @return the ee11
	 */
	public Long getEe11() {
		return ee11;
	}
	/**
	 * @param ee11 the ee11 to set
	 */
	public void setEe11(Long ee11) {
		this.ee11 = ee11;
	}
	/**
	 * @return the ee12
	 */
	public Long getEe12() {
		return ee12;
	}
	/**
	 * @param ee12 the ee12 to set
	 */
	public void setEe12(Long ee12) {
		this.ee12 = ee12;
	}
	/**
	 * @return the ee13
	 */
	public Long getEe13() {
		return ee13;
	}
	/**
	 * @param ee13 the ee13 to set
	 */
	public void setEe13(Long ee13) {
		this.ee13 = ee13;
	}
	/**
	 * @return the ee14
	 */
	public Long getEe14() {
		return ee14;
	}
	/**
	 * @param ee14 the ee14 to set
	 */
	public void setEe14(Long ee14) {
		this.ee14 = ee14;
	}
	/**
	 * @return the ee15
	 */
	public Long getEe15() {
		return ee15;
	}
	/**
	 * @param ee15 the ee15 to set
	 */
	public void setEe15(Long ee15) {
		this.ee15 = ee15;
	}
	/**
	 * @return the ee16
	 */
	public Long getEe16() {
		return ee16;
	}
	/**
	 * @param ee16 the ee16 to set
	 */
	public void setEe16(Long ee16) {
		this.ee16 = ee16;
	}
	/**
	 * @return the ee17
	 */
	public Long getEe17() {
		return ee17;
	}
	/**
	 * @param ee17 the ee17 to set
	 */
	public void setEe17(Long ee17) {
		this.ee17 = ee17;
	}
	/**
	 * @return the ee18
	 */
	public Long getEe18() {
		return ee18;
	}
	/**
	 * @param ee18 the ee18 to set
	 */
	public void setEe18(Long ee18) {
		this.ee18 = ee18;
	}
	/**
	 * @return the ee19
	 */
	public Long getEe19() {
		return ee19;
	}
	/**
	 * @param ee19 the ee19 to set
	 */
	public void setEe19(Long ee19) {
		this.ee19 = ee19;
	}
	/**
	 * @return the ee20
	 */
	public Long getEe20() {
		return ee20;
	}
	/**
	 * @param ee20 the ee20 to set
	 */
	public void setEe20(Long ee20) {
		this.ee20 = ee20;
	}
	/**
	 * @return the ee21
	 */
	public Long getEe21() {
		return ee21;
	}
	/**
	 * @param ee21 the ee21 to set
	 */
	public void setEe21(Long ee21) {
		this.ee21 = ee21;
	}
	/**
	 * @return the ee22
	 */
	public Long getEe22() {
		return ee22;
	}
	/**
	 * @param ee22 the ee22 to set
	 */
	public void setEe22(Long ee22) {
		this.ee22 = ee22;
	}
	/**
	 * @return the ee23
	 */
	public Long getEe23() {
		return ee23;
	}
	/**
	 * @param ee23 the ee23 to set
	 */
	public void setEe23(Long ee23) {
		this.ee23 = ee23;
	}
	/**
	 * @return the ee24
	 */
	public Long getEe24() {
		return ee24;
	}
	/**
	 * @param ee24 the ee24 to set
	 */
	public void setEe24(Long ee24) {
		this.ee24 = ee24;
	}
	/**
	 * @return the ee25
	 */
	public Long getEe25() {
		return ee25;
	}
	/**
	 * @param ee25 the ee25 to set
	 */
	public void setEe25(Long ee25) {
		this.ee25 = ee25;
	}
	/**
	 * @return the ee26
	 */
	public Long getEe26() {
		return ee26;
	}
	/**
	 * @param ee26 the ee26 to set
	 */
	public void setEe26(Long ee26) {
		this.ee26 = ee26;
	}
	/**
	 * @return the lineNumber
	 */
	public String getLineNumber() {
		return lineNumber;
	}
	/**
	 * @param lineNumber the lineNumber to set
	 */
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	/**
	 * @return the activeFlag
	 */
	public boolean isActiveFlag() {
		return activeFlag;
	}
	/**
	 * @param activeFlag the activeFlag to set
	 */
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Long getVersionId() {
		return versionId;
	}
	public void setVersionId(Long versionId) {
		this.versionId = versionId;
	}
	public boolean getGradeChange() {
		return gradeChange;
	}
	public void setGradeChange(boolean gradechange) {
		this.gradeChange = gradechange;
	}
	public String getAttendanceSchoolProgramName() {
		return attendanceSchoolProgramName;
	}
	public void setAttendanceSchoolProgramName(String attendanceSchoolProgramName) {
		this.attendanceSchoolProgramName = attendanceSchoolProgramName;
	}
	public String getAypSchoolName() {
		return aypSchoolName;
	}
	public void setAypSchoolName(String aypSchoolName) {
		this.aypSchoolName = aypSchoolName;
	}	
	public boolean isOriginal() {
		return original;
	}
	public void setOriginal(boolean original) {
		this.original = original;
	}
	public String getStrKiteEducatorIdentifier() {
		return strKiteEducatorIdentifier;
	}
	public void setStrKiteEducatorIdentifier(String strKiteEducatorIdentifier) {
		this.strKiteEducatorIdentifier = strKiteEducatorIdentifier;
	}
	public boolean isRecentVersion() {
		return recentVersion;
	}
	public void setRecentVersion(boolean recentVersion) {
		this.recentVersion = recentVersion;
	}
	public String getAccountabilityDistrictIdentifier() {
		return accountabilityDistrictIdentifier;
	}
	public void setAccountabilityDistrictIdentifier(String accountabilityDistrictIdentifier) {
		this.accountabilityDistrictIdentifier = accountabilityDistrictIdentifier;
	}
	public String getStateUse() {
		return stateUse;
	}
	public void setStateUse(String stateUse) {
		this.stateUse = stateUse;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}

}
