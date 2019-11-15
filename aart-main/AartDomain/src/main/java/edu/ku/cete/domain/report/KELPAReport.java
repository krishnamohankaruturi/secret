package edu.ku.cete.domain.report;

import java.io.Serializable;

public class KELPAReport implements Serializable{

	
	private static final long serialVersionUID = 1L;

	
	private String  administration;
	private String  districtNumber;
	private String	districtName;
	private String	schoolNumber;
	private String	schoolName;
	private String	studentLastName;
	private String	studentFirstName ;
	private String	studentMiddleInitial;
	private String	studentDateOfBirth;
	private String	stateStudentID;
	private String	grade;
	private String	gender;
	private String	ethnicity;
	private String	homeLanguage;
	private String	disability;
	private String	IEP;
	private String	section504;
	private String	ELLStatus;
	private String	economicallyDisadvantaged;
	private String	studentSTN;
	private String	studentResponseFormListening;
	private String	studentResponseFormReading;
	private String	studentResponseFormSpeaking;
	private String	studentResponseFormWriting;
	private String	testedOrAttempted;
	private String	teacherID;
	private String	teacherLastName;
	private String	teacherFirstName;
	private String	teacherMiddleInitial;
	private String	universalFeatureZoom;
	private String	designatedFeatureZoomSize;
	private String	designatedFeatureAnswerMasking;
	private String	designatedFeatureScreenReader;
	private String	designatedFeatureMagnifyingGlass;
	private String	designatedFeatureBackgroundColor;
	private String	designatedFeatureBackgroundColorOptions;
	private String	designatedFeatureReverseContrast;
	private String	designatedFeatureBraille;
	private String	designatedFeatureLargePrint;
	private String	testMode;
	private String	studentNotTestedCodeListening;
	private String	studentNotTestedCodeReading;
	private String	studentNotTestedCodeSpeaking;
	private String	studentNotTestedCodeWriting;
	private String	testInvalidationForListening;
	private String	testInvalidationForReading;
	private String	testInvalidationForSpeaking;
	private String	testInvalidationForWriting;
	private String	domainStatusListening;
	private String	domainStatusReading;
	private String	domainStatusSpeaking;
	private String	domainStatusWriting;
	private String	overallRawScore;
	private String	overallScaleScore;
	private String	proficiencyDetermination;
	private String	overallMeasurementError;
	private String	listeningRawScore;
	private String	listeningScaleScore;
	private String	listeningMeasurementError;
	private String	listeningPerformanceLevel;
	private String	readingRawScore;
	private String	readingScaleScore;
	private String	readingMeasurementError;
	private String	readingPerformanceLevel;
	private String	speakingRawScore;
	private String	speakingScaleScore;
	private String	speakingMeasurementError;
	private String	speakingPerformanceLevel;
	private String	writingRawScore;
	private String	writingScaleScore;
	private String	writingMeasurementError;
	private String	writingPerformanceLevel;
	private String	comprehensionScaleScore;
	private String	comprehensionMeasurementError;
	private String epStateStudentId;
	private String ksdeGradeCode;
	private String status;	
	private String reason;
	private boolean notFoundTested;
	private boolean commonValidationPassed;
	private Long stateId;
	
	// previous fields are carried over on the POJO from 2016, some could still be used or repurposed for 2017
	// fields below were added for 2017
	
	private Integer overallLevel;
	private Integer listeningQuestionCount;
	private Integer listeningResponseCount;
	private Integer readingQuestionCount;
	private Integer readingResponseCount;
	private Integer speakingQuestionCount;
	private Integer speakingResponseCount;
	private Integer writingQuestionCount;
	private Integer writingResponseCount;
	private Integer speakingHumanScoredCount;
	private String speakingNonScorableCodes;
	private Integer writingHumanScoredCount;
	private String writingNonScorableCodes;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdministration() {
		return administration;
	}
	public void setAdministration(String administration) {
		this.administration = administration;
	}
	public String getDistrictNumber() {
		return districtNumber;
	}
	public void setDistrictNumber(String districtNumber) {
		this.districtNumber = districtNumber;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getSchoolNumber() {
		return schoolNumber;
	}
	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
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
	public String getStudentDateOfBirth() {
		return studentDateOfBirth;
	}
	public void setStudentDateOfBirth(String studentDateOfBirth) {
		this.studentDateOfBirth = studentDateOfBirth;
	}
	public String getStateStudentID() {
		return stateStudentID;
	}
	public void setStateStudentID(String stateStudentID) {
		this.stateStudentID = stateStudentID;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEthnicity() {
		return ethnicity;
	}
	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	public String getHomeLanguage() {
		return homeLanguage;
	}
	public void setHomeLanguage(String homeLanguage) {
		this.homeLanguage = homeLanguage;
	}
	public String getDisability() {
		return disability;
	}
	public void setDisability(String disability) {
		this.disability = disability;
	}
	public String getIEP() {
		return IEP;
	}
	public void setIEP(String iEP) {
		IEP = iEP;
	}
	public String getSection504() {
		return section504;
	}
	public void setSection504(String section504) {
		this.section504 = section504;
	}
	public String getELLStatus() {
		return ELLStatus;
	}
	public void setELLStatus(String eLLStatus) {
		ELLStatus = eLLStatus;
	}
	public String getEconomicallyDisadvantaged() {
		return economicallyDisadvantaged;
	}
	public void setEconomicallyDisadvantaged(String economicallyDisadvantaged) {
		this.economicallyDisadvantaged = economicallyDisadvantaged;
	}
	public String getStudentSTN() {
		return studentSTN;
	}
	public void setStudentSTN(String studentSTN) {
		this.studentSTN = studentSTN;
	}
	public String getStudentResponseFormListening() {
		return studentResponseFormListening;
	}
	public void setStudentResponseFormListening(String studentResponseFormListening) {
		this.studentResponseFormListening = studentResponseFormListening;
	}
	public String getStudentResponseFormReading() {
		return studentResponseFormReading;
	}
	public void setStudentResponseFormReading(String studentResponseFormReading) {
		this.studentResponseFormReading = studentResponseFormReading;
	}
	public String getStudentResponseFormSpeaking() {
		return studentResponseFormSpeaking;
	}
	public void setStudentResponseFormSpeaking(String studentResponseFormSpeaking) {
		this.studentResponseFormSpeaking = studentResponseFormSpeaking;
	}
	public String getStudentResponseFormWriting() {
		return studentResponseFormWriting;
	}
	public void setStudentResponseFormWriting(String studentResponseFormWriting) {
		this.studentResponseFormWriting = studentResponseFormWriting;
	}
	public String getTestedOrAttempted() {
		return testedOrAttempted;
	}
	public void setTestedOrAttempted(String testedOrAttempted) {
		this.testedOrAttempted = testedOrAttempted;
	}
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	public String getTeacherLastName() {
		return teacherLastName;
	}
	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}
	public String getTeacherFirstName() {
		return teacherFirstName;
	}
	public void setTeacherFirstName(String teacherFirstName) {
		this.teacherFirstName = teacherFirstName;
	}
	public String getTeacherMiddleInitial() {
		return teacherMiddleInitial;
	}
	public void setTeacherMiddleInitial(String teacherMiddleInitial) {
		this.teacherMiddleInitial = teacherMiddleInitial;
	}
	public String getUniversalFeatureZoom() {
		return universalFeatureZoom;
	}
	public void setUniversalFeatureZoom(String universalFeatureZoom) {
		this.universalFeatureZoom = universalFeatureZoom;
	}
	public String getDesignatedFeatureZoomSize() {
		return designatedFeatureZoomSize;
	}
	public void setDesignatedFeatureZoomSize(String designatedFeatureZoomSize) {
		this.designatedFeatureZoomSize = designatedFeatureZoomSize;
	}
	public String getDesignatedFeatureAnswerMasking() {
		return designatedFeatureAnswerMasking;
	}
	public void setDesignatedFeatureAnswerMasking(
			String designatedFeatureAnswerMasking) {
		this.designatedFeatureAnswerMasking = designatedFeatureAnswerMasking;
	}
	public String getDesignatedFeatureScreenReader() {
		return designatedFeatureScreenReader;
	}
	public void setDesignatedFeatureScreenReader(
			String designatedFeatureScreenReader) {
		this.designatedFeatureScreenReader = designatedFeatureScreenReader;
	}
	public String getDesignatedFeatureMagnifyingGlass() {
		return designatedFeatureMagnifyingGlass;
	}
	public void setDesignatedFeatureMagnifyingGlass(
			String designatedFeatureMagnifyingGlass) {
		this.designatedFeatureMagnifyingGlass = designatedFeatureMagnifyingGlass;
	}
	public String getDesignatedFeatureBackgroundColor() {
		return designatedFeatureBackgroundColor;
	}
	public void setDesignatedFeatureBackgroundColor(
			String designatedFeatureBackgroundColor) {
		this.designatedFeatureBackgroundColor = designatedFeatureBackgroundColor;
	}
	public String getDesignatedFeatureBackgroundColorOptions() {
		return designatedFeatureBackgroundColorOptions;
	}
	public void setDesignatedFeatureBackgroundColorOptions(
			String designatedFeatureBackgroundColorOptions) {
		this.designatedFeatureBackgroundColorOptions = designatedFeatureBackgroundColorOptions;
	}
	public String getDesignatedFeatureReverseContrast() {
		return designatedFeatureReverseContrast;
	}
	public void setDesignatedFeatureReverseContrast(
			String designatedFeatureReverseContrast) {
		this.designatedFeatureReverseContrast = designatedFeatureReverseContrast;
	}
	public String getDesignatedFeatureBraille() {
		return designatedFeatureBraille;
	}
	public void setDesignatedFeatureBraille(String designatedFeatureBraille) {
		this.designatedFeatureBraille = designatedFeatureBraille;
	}
	public String getDesignatedFeatureLargePrint() {
		return designatedFeatureLargePrint;
	}
	public void setDesignatedFeatureLargePrint(String designatedFeatureLargePrint) {
		this.designatedFeatureLargePrint = designatedFeatureLargePrint;
	}
	public String getTestMode() {
		return testMode;
	}
	public void setTestMode(String testMode) {
		this.testMode = testMode;
	}
	public String getStudentNotTestedCodeListening() {
		return studentNotTestedCodeListening;
	}
	public void setStudentNotTestedCodeListening(
			String studentNotTestedCodeListening) {
		this.studentNotTestedCodeListening = studentNotTestedCodeListening;
	}
	public String getStudentNotTestedCodeReading() {
		return studentNotTestedCodeReading;
	}
	public void setStudentNotTestedCodeReading(String studentNotTestedCodeReading) {
		this.studentNotTestedCodeReading = studentNotTestedCodeReading;
	}
	public String getStudentNotTestedCodeSpeaking() {
		return studentNotTestedCodeSpeaking;
	}
	public void setStudentNotTestedCodeSpeaking(String studentNotTestedCodeSpeaking) {
		this.studentNotTestedCodeSpeaking = studentNotTestedCodeSpeaking;
	}
	public String getStudentNotTestedCodeWriting() {
		return studentNotTestedCodeWriting;
	}
	public void setStudentNotTestedCodeWriting(String studentNotTestedCodeWriting) {
		this.studentNotTestedCodeWriting = studentNotTestedCodeWriting;
	}
	public String getTestInvalidationForListening() {
		return testInvalidationForListening;
	}
	public void setTestInvalidationForListening(String testInvalidationForListening) {
		this.testInvalidationForListening = testInvalidationForListening;
	}
	public String getTestInvalidationForReading() {
		return testInvalidationForReading;
	}
	public void setTestInvalidationForReading(String testInvalidationForReading) {
		this.testInvalidationForReading = testInvalidationForReading;
	}
	public String getTestInvalidationForSpeaking() {
		return testInvalidationForSpeaking;
	}
	public void setTestInvalidationForSpeaking(String testInvalidationForSpeaking) {
		this.testInvalidationForSpeaking = testInvalidationForSpeaking;
	}
	public String getTestInvalidationForWriting() {
		return testInvalidationForWriting;
	}
	public void setTestInvalidationForWriting(String testInvalidationForWriting) {
		this.testInvalidationForWriting = testInvalidationForWriting;
	}
	public String getDomainStatusListening() {
		return domainStatusListening;
	}
	public void setDomainStatusListening(String domainStatusListening) {
		this.domainStatusListening = domainStatusListening;
	}
	public String getDomainStatusReading() {
		return domainStatusReading;
	}
	public void setDomainStatusReading(String domainStatusReading) {
		this.domainStatusReading = domainStatusReading;
	}
	public String getDomainStatusSpeaking() {
		return domainStatusSpeaking;
	}
	public void setDomainStatusSpeaking(String domainStatusSpeaking) {
		this.domainStatusSpeaking = domainStatusSpeaking;
	}
	public String getDomainStatusWriting() {
		return domainStatusWriting;
	}
	public void setDomainStatusWriting(String domainStatusWriting) {
		this.domainStatusWriting = domainStatusWriting;
	}
	public String getOverallRawScore() {
		return overallRawScore;
	}
	public void setOverallRawScore(String overallRawScore) {
		this.overallRawScore = overallRawScore;
	}
	public String getOverallScaleScore() {
		return overallScaleScore;
	}
	public void setOverallScaleScore(String overallScaleScore) {
		this.overallScaleScore = overallScaleScore;
	}
	public String getProficiencyDetermination() {
		return proficiencyDetermination;
	}
	public void setProficiencyDetermination(String proficiencyDetermination) {
		this.proficiencyDetermination = proficiencyDetermination;
	}
	public String getOverallMeasurementError() {
		return overallMeasurementError;
	}
	public void setOverallMeasurementError(String overallMeasurementError) {
		this.overallMeasurementError = overallMeasurementError;
	}
	public String getListeningRawScore() {
		return listeningRawScore;
	}
	public void setListeningRawScore(String listeningRawScore) {
		this.listeningRawScore = listeningRawScore;
	}
	public String getListeningScaleScore() {
		return listeningScaleScore;
	}
	public void setListeningScaleScore(String listeningScaleScore) {
		this.listeningScaleScore = listeningScaleScore;
	}
	public String getListeningMeasurementError() {
		return listeningMeasurementError;
	}
	public void setListeningMeasurementError(String listeningMeasurementError) {
		this.listeningMeasurementError = listeningMeasurementError;
	}
	public String getListeningPerformanceLevel() {
		return listeningPerformanceLevel;
	}
	public void setListeningPerformanceLevel(String listeningPerformanceLevel) {
		this.listeningPerformanceLevel = listeningPerformanceLevel;
	}
	public String getReadingRawScore() {
		return readingRawScore;
	}
	public void setReadingRawScore(String readingRawScore) {
		this.readingRawScore = readingRawScore;
	}
	public String getReadingScaleScore() {
		return readingScaleScore;
	}
	public void setReadingScaleScore(String readingScaleScore) {
		this.readingScaleScore = readingScaleScore;
	}
	public String getReadingMeasurementError() {
		return readingMeasurementError;
	}
	public void setReadingMeasurementError(String readingMeasurementError) {
		this.readingMeasurementError = readingMeasurementError;
	}
	public String getReadingPerformanceLevel() {
		return readingPerformanceLevel;
	}
	public void setReadingPerformanceLevel(String readingPerformanceLevel) {
		this.readingPerformanceLevel = readingPerformanceLevel;
	}
	public String getSpeakingRawScore() {
		return speakingRawScore;
	}
	public void setSpeakingRawScore(String speakingRawScore) {
		this.speakingRawScore = speakingRawScore;
	}
	public String getSpeakingScaleScore() {
		return speakingScaleScore;
	}
	public void setSpeakingScaleScore(String speakingScaleScore) {
		this.speakingScaleScore = speakingScaleScore;
	}
	public String getSpeakingMeasurementError() {
		return speakingMeasurementError;
	}
	public void setSpeakingMeasurementError(String speakingMeasurementError) {
		this.speakingMeasurementError = speakingMeasurementError;
	}
	public String getSpeakingPerformanceLevel() {
		return speakingPerformanceLevel;
	}
	public void setSpeakingPerformanceLevel(String speakingPerformanceLevel) {
		this.speakingPerformanceLevel = speakingPerformanceLevel;
	}
	public String getWritingRawScore() {
		return writingRawScore;
	}
	public void setWritingRawScore(String writingRawScore) {
		this.writingRawScore = writingRawScore;
	}
	public String getWritingScaleScore() {
		return writingScaleScore;
	}
	public void setWritingScaleScore(String writingScaleScore) {
		this.writingScaleScore = writingScaleScore;
	}
	public String getWritingMeasurementError() {
		return writingMeasurementError;
	}
	public void setWritingMeasurementError(String writingMeasurementError) {
		this.writingMeasurementError = writingMeasurementError;
	}
	public String getWritingPerformanceLevel() {
		return writingPerformanceLevel;
	}
	public void setWritingPerformanceLevel(String writingPerformanceLevel) {
		this.writingPerformanceLevel = writingPerformanceLevel;
	}
	public String getComprehensionScaleScore() {
		return comprehensionScaleScore;
	}
	public void setComprehensionScaleScore(String comprehensionScaleScore) {
		this.comprehensionScaleScore = comprehensionScaleScore;
	}
	public String getComprehensionMeasurementError() {
		return comprehensionMeasurementError;
	}
	public void setComprehensionMeasurementError(
			String comprehensionMeasurementError) {
		this.comprehensionMeasurementError = comprehensionMeasurementError;
	}
	public String getEpStateStudentId() {
		return epStateStudentId;
	}
	public void setEpStateStudentId(String epStateStudentId) {
		this.epStateStudentId = epStateStudentId;
	}
	public String getKsdeGradeCode() {
		return ksdeGradeCode;
	}
	public void setKsdeGradeCode(String ksdeGradeCode) {
		this.ksdeGradeCode = ksdeGradeCode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public boolean isNotFoundTested() {
		return notFoundTested;
	}
	public void setNotFoundTested(boolean notFoundTested) {
		this.notFoundTested = notFoundTested;
	}
	public boolean isCommonValidationPassed() {
		return commonValidationPassed;
	}
	public void setCommonValidationPassed(boolean commonValidationPassed) {
		this.commonValidationPassed = commonValidationPassed;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public Integer getOverallLevel() {
		return overallLevel;
	}
	public void setOverallLevel(Integer overallLevel) {
		this.overallLevel = overallLevel;
	}
	public Integer getListeningQuestionCount() {
		return listeningQuestionCount;
	}
	public void setListeningQuestionCount(Integer listeningQuestionCount) {
		this.listeningQuestionCount = listeningQuestionCount;
	}
	public Integer getListeningResponseCount() {
		return listeningResponseCount;
	}
	public void setListeningResponseCount(Integer listeningResponseCount) {
		this.listeningResponseCount = listeningResponseCount;
	}
	public Integer getReadingQuestionCount() {
		return readingQuestionCount;
	}
	public void setReadingQuestionCount(Integer readingQuestionCount) {
		this.readingQuestionCount = readingQuestionCount;
	}
	public Integer getReadingResponseCount() {
		return readingResponseCount;
	}
	public void setReadingResponseCount(Integer readingResponseCount) {
		this.readingResponseCount = readingResponseCount;
	}
	public Integer getSpeakingQuestionCount() {
		return speakingQuestionCount;
	}
	public void setSpeakingQuestionCount(Integer speakingQuestionCount) {
		this.speakingQuestionCount = speakingQuestionCount;
	}
	public Integer getSpeakingResponseCount() {
		return speakingResponseCount;
	}
	public void setSpeakingResponseCount(Integer speakingResponseCount) {
		this.speakingResponseCount = speakingResponseCount;
	}
	public Integer getWritingQuestionCount() {
		return writingQuestionCount;
	}
	public void setWritingQuestionCount(Integer writingQuestionCount) {
		this.writingQuestionCount = writingQuestionCount;
	}
	public Integer getWritingResponseCount() {
		return writingResponseCount;
	}
	public void setWritingResponseCount(Integer writingResponseCount) {
		this.writingResponseCount = writingResponseCount;
	}
	public Integer getSpeakingHumanScoredCount() {
		return speakingHumanScoredCount;
	}
	public void setSpeakingHumanScoredCount(Integer speakingHumanScoredCount) {
		this.speakingHumanScoredCount = speakingHumanScoredCount;
	}
	public String getSpeakingNonScorableCodes() {
		return speakingNonScorableCodes;
	}
	public void setSpeakingNonScorableCodes(String speakingNonScorableCodes) {
		this.speakingNonScorableCodes = speakingNonScorableCodes;
	}
	public Integer getWritingHumanScoredCount() {
		return writingHumanScoredCount;
	}
	public void setWritingHumanScoredCount(Integer writingHumanScoredCount) {
		this.writingHumanScoredCount = writingHumanScoredCount;
	}
	public String getWritingNonScorableCodes() {
		return writingNonScorableCodes;
	}
	public void setWritingNonScorableCodes(String writingNonScorableCodes) {
		this.writingNonScorableCodes = writingNonScorableCodes;
	}
}
