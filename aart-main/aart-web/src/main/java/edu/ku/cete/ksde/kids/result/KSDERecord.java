package edu.ku.cete.ksde.kids.result;

import java.io.Serializable;
import java.util.Date;

public class KSDERecord implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
 * kids.
 */
	private Long id;
	private Long xmlAuditId;
	private Long seqNo;
	private String reason;
	private String animalSystemsAssessment;
	private String attendanceSchoolProgramIdentifier;
	private String aypSchoolIdentifier;
	private String comprehensiveAgAssessment;
	private String comprehensiveBusinessAssessment;
	private String comprehensiveRace;
	private String courseStatus;
	private String createDate;
	private String currentGradeLevel;
	private String currentSchoolYear;
	private String dateOfBirth;
	private String designPreConstructionAssessment;
	private String districtEntryDate;
	private String educatorEmailId;
	private String educatorSchoolId;
	private String elpa21Assessment;
	private String endOfPathwaysAssessment;
	private String esolParticipationCode;
	private String esolProgramEndingDate;
	private String esolProgramEntryDate;
	private String exitWithdrawalDate;
	private String exitWithdrawalType;
	private String financeAssessment;
	private String gender;
	private String generalCTEAssessment;
	private String generationCode;
	private String giftedCode;
	private String groupingAnimalSystems;
	private String groupingComprehensiveAg;
	private String groupingComprehensiveBusiness;
	private String groupingDesignPreConstruction;
	private String groupingFinance;
	private String groupingInd1CTE;
	private String groupingInd1ELA;
	private String groupingInd1Elpa21;
	private String groupingInd1HistGov;
	private String groupingInd1Math;
	private String groupingInd1Pathways;
	private String groupingInd1Sci;
	private String groupingInd2CTE;
	private String groupingInd2ELA;
	private String groupingInd2Elpa21;
	private String groupingInd2HistGov;
	private String groupingInd2Math;
	private String groupingInd2Pathways;
	private String groupingInd2Sci;
	private String groupingManufacturingProd;
	private String groupingPlantSystems;
	private String hispanicEthnicity;
	private String legalFirstName;
	private String legalLastName;
	private String legalMiddleName;
	private String manufacturingProdAssessment;
	private String plantSystemsAssessment;
	private String primaryDisabilityCode;
	private String recordCommonId;
	private String recordType;
	private String schoolEntryDate;
	private String spedProgramEndDate;
	private String stateELAAssessment;
	private String stateEntryDate;
	private String stateHistGovAssessment;
	private String stateMathAssess;
	private String stateSciAssessment;
	private String stateStudentIdentifier;
	private String tascEducatorIdentifier;
	private String tascLocalCourseId;
	private String tascStateSubjectAreaCode;
	private String teacherFirstName;
	private String teacherLastName;
	private String teacherMiddleName;
	
	private String avCommunicationsAssessment;
	private String groupingAvCommunications;
	private String elpaProctorId;
	private String elpaProctorFirstName;
	private String elpaProctorLastName;
	private String status;
	private Boolean triggerEmail;
	private Boolean emailSent;
	private String emailSentTo;
	private String emailTemplateIds;
	private Date emailSentDate;
	private Date processedDate;	
	private String historyGovProctorId;
	private String historyGovProctorFirstName;
	private String historyGovProctorLastName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getXmlAuditId() {
		return xmlAuditId;
	}
	public void setXmlAuditId(Long xmlAuditId) {
		this.xmlAuditId = xmlAuditId;
	}
	public Long getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}
	public String getAnimalSystemsAssessment() {
		return animalSystemsAssessment;
	}
	public void setAnimalSystemsAssessment(String animalSystemsAssessment) {
		this.animalSystemsAssessment = animalSystemsAssessment;
	}
	public String getAttendanceSchoolProgramIdentifier() {
		return attendanceSchoolProgramIdentifier;
	}
	public void setAttendanceSchoolProgramIdentifier(String attendanceSchoolProgramIdentifier) {
		this.attendanceSchoolProgramIdentifier = attendanceSchoolProgramIdentifier;
	}
	public String getAypSchoolIdentifier() {
		return aypSchoolIdentifier;
	}
	public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
		this.aypSchoolIdentifier = aypSchoolIdentifier;
	}
	public String getComprehensiveAgAssessment() {
		return comprehensiveAgAssessment;
	}
	public void setComprehensiveAgAssessment(String comprehensiveAgAssessment) {
		this.comprehensiveAgAssessment = comprehensiveAgAssessment;
	}
	public String getComprehensiveBusinessAssessment() {
		return comprehensiveBusinessAssessment;
	}
	public void setComprehensiveBusinessAssessment(String comprehensiveBusinessAssessment) {
		this.comprehensiveBusinessAssessment = comprehensiveBusinessAssessment;
	}
	public String getComprehensiveRace() {
		return comprehensiveRace;
	}
	public void setComprehensiveRace(String comprehensiveRace) {
		this.comprehensiveRace = comprehensiveRace;
	}
	public String getCourseStatus() {
		return courseStatus;
	}
	public void setCourseStatus(String courseStatus) {
		this.courseStatus = courseStatus;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getCurrentGradeLevel() {
		return currentGradeLevel;
	}
	public void setCurrentGradeLevel(String currentGradeLevel) {
		this.currentGradeLevel = currentGradeLevel;
	}
	public String getCurrentSchoolYear() {
		return currentSchoolYear;
	}
	public void setCurrentSchoolYear(String currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDesignPreConstructionAssessment() {
		return designPreConstructionAssessment;
	}
	public void setDesignPreConstructionAssessment(String designPreConstructionAssessment) {
		this.designPreConstructionAssessment = designPreConstructionAssessment;
	}
	public String getDistrictEntryDate() {
		return districtEntryDate;
	}
	public void setDistrictEntryDate(String districtEntryDate) {
		this.districtEntryDate = districtEntryDate;
	}
	public String getEducatorEmailId() {
		return educatorEmailId;
	}
	public void setEducatorEmailId(String educatorEmailId) {
		this.educatorEmailId = educatorEmailId;
	}
	public String getEducatorSchoolId() {
		return educatorSchoolId;
	}
	public void setEducatorSchoolId(String educatorSchoolId) {
		this.educatorSchoolId = educatorSchoolId;
	}
	public String getElpa21Assessment() {
		return elpa21Assessment;
	}
	public void setElpa21Assessment(String elpa21Assessment) {
		this.elpa21Assessment = elpa21Assessment;
	}
	public String getEndOfPathwaysAssessment() {
		return endOfPathwaysAssessment;
	}
	public void setEndOfPathwaysAssessment(String endOfPathwaysAssessment) {
		this.endOfPathwaysAssessment = endOfPathwaysAssessment;
	}
	public String getEsolParticipationCode() {
		return esolParticipationCode;
	}
	public void setEsolParticipationCode(String esolParticipationCode) {
		this.esolParticipationCode = esolParticipationCode;
	}
	public String getEsolProgramEndingDate() {
		return esolProgramEndingDate;
	}
	public void setEsolProgramEndingDate(String esolProgramEndingDate) {
		this.esolProgramEndingDate = esolProgramEndingDate;
	}
	public String getEsolProgramEntryDate() {
		return esolProgramEntryDate;
	}
	public void setEsolProgramEntryDate(String esolProgramEntryDate) {
		this.esolProgramEntryDate = esolProgramEntryDate;
	}
	public String getExitWithdrawalDate() {
		return exitWithdrawalDate;
	}
	public void setExitWithdrawalDate(String exitWithdrawalDate) {
		this.exitWithdrawalDate = exitWithdrawalDate;
	}
	public String getExitWithdrawalType() {
		return exitWithdrawalType;
	}
	public void setExitWithdrawalType(String exitWithdrawalType) {
		this.exitWithdrawalType = exitWithdrawalType;
	}
	public String getFinanceAssessment() {
		return financeAssessment;
	}
	public void setFinanceAssessment(String financeAssessment) {
		this.financeAssessment = financeAssessment;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getGeneralCTEAssessment() {
		return generalCTEAssessment;
	}
	public void setGeneralCTEAssessment(String generalCTEAssessment) {
		this.generalCTEAssessment = generalCTEAssessment;
	}
	public String getGenerationCode() {
		return generationCode;
	}
	public void setGenerationCode(String generationCode) {
		this.generationCode = generationCode;
	}
	public String getGiftedCode() {
		return giftedCode;
	}
	public void setGiftedCode(String giftedCode) {
		this.giftedCode = giftedCode;
	}
	public String getGroupingAnimalSystems() {
		return groupingAnimalSystems;
	}
	public void setGroupingAnimalSystems(String groupingAnimalSystems) {
		this.groupingAnimalSystems = groupingAnimalSystems;
	}
	public String getGroupingComprehensiveAg() {
		return groupingComprehensiveAg;
	}
	public void setGroupingComprehensiveAg(String groupingComprehensiveAg) {
		this.groupingComprehensiveAg = groupingComprehensiveAg;
	}
	public String getGroupingComprehensiveBusiness() {
		return groupingComprehensiveBusiness;
	}
	public void setGroupingComprehensiveBusiness(String groupingComprehensiveBusiness) {
		this.groupingComprehensiveBusiness = groupingComprehensiveBusiness;
	}
	public String getGroupingDesignPreConstruction() {
		return groupingDesignPreConstruction;
	}
	public void setGroupingDesignPreConstruction(String groupingDesignPreConstruction) {
		this.groupingDesignPreConstruction = groupingDesignPreConstruction;
	}
	public String getGroupingFinance() {
		return groupingFinance;
	}
	public void setGroupingFinance(String groupingFinance) {
		this.groupingFinance = groupingFinance;
	}
	public String getGroupingInd1CTE() {
		return groupingInd1CTE;
	}
	public void setGroupingInd1CTE(String groupingInd1CTE) {
		this.groupingInd1CTE = groupingInd1CTE;
	}
	public String getGroupingInd1ELA() {
		return groupingInd1ELA;
	}
	public void setGroupingInd1ELA(String groupingInd1ELA) {
		this.groupingInd1ELA = groupingInd1ELA;
	}
	public String getGroupingInd1Elpa21() {
		return groupingInd1Elpa21;
	}
	public void setGroupingInd1Elpa21(String groupingInd1Elpa21) {
		this.groupingInd1Elpa21 = groupingInd1Elpa21;
	}
	public String getGroupingInd1HistGov() {
		return groupingInd1HistGov;
	}
	public void setGroupingInd1HistGov(String groupingInd1HistGov) {
		this.groupingInd1HistGov = groupingInd1HistGov;
	}
	public String getGroupingInd1Math() {
		return groupingInd1Math;
	}
	public void setGroupingInd1Math(String groupingInd1Math) {
		this.groupingInd1Math = groupingInd1Math;
	}
	public String getGroupingInd1Pathways() {
		return groupingInd1Pathways;
	}
	public void setGroupingInd1Pathways(String groupingInd1Pathways) {
		this.groupingInd1Pathways = groupingInd1Pathways;
	}
	public String getGroupingInd1Sci() {
		return groupingInd1Sci;
	}
	public void setGroupingInd1Sci(String groupingInd1Sci) {
		this.groupingInd1Sci = groupingInd1Sci;
	}
	public String getGroupingInd2CTE() {
		return groupingInd2CTE;
	}
	public void setGroupingInd2CTE(String groupingInd2CTE) {
		this.groupingInd2CTE = groupingInd2CTE;
	}
	public String getGroupingInd2ELA() {
		return groupingInd2ELA;
	}
	public void setGroupingInd2ELA(String groupingInd2ELA) {
		this.groupingInd2ELA = groupingInd2ELA;
	}
	public String getGroupingInd2Elpa21() {
		return groupingInd2Elpa21;
	}
	public void setGroupingInd2Elpa21(String groupingInd2Elpa21) {
		this.groupingInd2Elpa21 = groupingInd2Elpa21;
	}
	public String getGroupingInd2HistGov() {
		return groupingInd2HistGov;
	}
	public void setGroupingInd2HistGov(String groupingInd2HistGov) {
		this.groupingInd2HistGov = groupingInd2HistGov;
	}
	public String getGroupingInd2Math() {
		return groupingInd2Math;
	}
	public void setGroupingInd2Math(String groupingInd2Math) {
		this.groupingInd2Math = groupingInd2Math;
	}
	public String getGroupingInd2Pathways() {
		return groupingInd2Pathways;
	}
	public void setGroupingInd2Pathways(String groupingInd2Pathways) {
		this.groupingInd2Pathways = groupingInd2Pathways;
	}
	public String getGroupingInd2Sci() {
		return groupingInd2Sci;
	}
	public void setGroupingInd2Sci(String groupingInd2Sci) {
		this.groupingInd2Sci = groupingInd2Sci;
	}
	public String getGroupingManufacturingProd() {
		return groupingManufacturingProd;
	}
	public void setGroupingManufacturingProd(String groupingManufacturingProd) {
		this.groupingManufacturingProd = groupingManufacturingProd;
	}
	public String getGroupingPlantSystems() {
		return groupingPlantSystems;
	}
	public void setGroupingPlantSystems(String groupingPlantSystems) {
		this.groupingPlantSystems = groupingPlantSystems;
	}
	public String getHispanicEthnicity() {
		return hispanicEthnicity;
	}
	public void setHispanicEthnicity(String hispanicEthnicity) {
		this.hispanicEthnicity = hispanicEthnicity;
	}
	public String getLegalFirstName() {
		return legalFirstName;
	}
	public void setLegalFirstName(String legalFirstName) {
		this.legalFirstName = legalFirstName;
	}
	public String getLegalLastName() {
		return legalLastName;
	}
	public void setLegalLastName(String legalLastName) {
		this.legalLastName = legalLastName;
	}
	public String getLegalMiddleName() {
		return legalMiddleName;
	}
	public void setLegalMiddleName(String legalMiddleName) {
		this.legalMiddleName = legalMiddleName;
	}
	public String getManufacturingProdAssessment() {
		return manufacturingProdAssessment;
	}
	public void setManufacturingProdAssessment(String manufacturingProdAssessment) {
		this.manufacturingProdAssessment = manufacturingProdAssessment;
	}
	public String getPlantSystemsAssessment() {
		return plantSystemsAssessment;
	}
	public void setPlantSystemsAssessment(String plantSystemsAssessment) {
		this.plantSystemsAssessment = plantSystemsAssessment;
	}
	public String getPrimaryDisabilityCode() {
		return primaryDisabilityCode;
	}
	public void setPrimaryDisabilityCode(String primaryDisabilityCode) {
		this.primaryDisabilityCode = primaryDisabilityCode;
	}
	public String getRecordCommonId() {
		return recordCommonId;
	}
	public void setRecordCommonId(String recordCommonId) {
		this.recordCommonId = recordCommonId;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getSchoolEntryDate() {
		return schoolEntryDate;
	}
	public void setSchoolEntryDate(String schoolEntryDate) {
		this.schoolEntryDate = schoolEntryDate;
	}
	public String getSpedProgramEndDate() {
		return spedProgramEndDate;
	}
	public void setSpedProgramEndDate(String spedProgramEndDate) {
		this.spedProgramEndDate = spedProgramEndDate;
	}
	public String getStateELAAssessment() {
		return stateELAAssessment;
	}
	public void setStateELAAssessment(String stateELAAssessment) {
		this.stateELAAssessment = stateELAAssessment;
	}
	public String getStateEntryDate() {
		return stateEntryDate;
	}
	public void setStateEntryDate(String stateEntryDate) {
		this.stateEntryDate = stateEntryDate;
	}
	public String getStateHistGovAssessment() {
		return stateHistGovAssessment;
	}
	public void setStateHistGovAssessment(String stateHistGovAssessment) {
		this.stateHistGovAssessment = stateHistGovAssessment;
	}
	public String getStateMathAssess() {
		return stateMathAssess;
	}
	public void setStateMathAssess(String stateMathAssess) {
		this.stateMathAssess = stateMathAssess;
	}
	public String getStateSciAssessment() {
		return stateSciAssessment;
	}
	public void setStateSciAssessment(String stateSciAssessment) {
		this.stateSciAssessment = stateSciAssessment;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getTascEducatorIdentifier() {
		return tascEducatorIdentifier;
	}
	public void setTascEducatorIdentifier(String tascEducatorIdentifier) {
		this.tascEducatorIdentifier = tascEducatorIdentifier;
	}
	public String getTascLocalCourseId() {
		return tascLocalCourseId;
	}
	public void setTascLocalCourseId(String tascLocalCourseId) {
		this.tascLocalCourseId = tascLocalCourseId;
	}
	public String getTascStateSubjectAreaCode() {
		return tascStateSubjectAreaCode;
	}
	public void setTascStateSubjectAreaCode(String tascStateSubjectAreaCode) {
		this.tascStateSubjectAreaCode = tascStateSubjectAreaCode;
	}
	public String getTeacherFirstName() {
		return teacherFirstName;
	}
	public void setTeacherFirstName(String teacherFirstName) {
		this.teacherFirstName = teacherFirstName;
	}
	public String getTeacherLastName() {
		return teacherLastName;
	}
	public void setTeacherLastName(String teacherLastName) {
		this.teacherLastName = teacherLastName;
	}
	public String getTeacherMiddleName() {
		return teacherMiddleName;
	}
	public void setTeacherMiddleName(String teacherMiddleName) {
		this.teacherMiddleName = teacherMiddleName;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAvCommunicationsAssessment() {
		return avCommunicationsAssessment;
	}
	public void setAvCommunicationsAssessment(String avCommunicationsAssessment) {
		this.avCommunicationsAssessment = avCommunicationsAssessment;
	}
	public String getGroupingAvCommunications() {
		return groupingAvCommunications;
	}
	public void setGroupingAvCommunications(String groupingAvCommunications) {
		this.groupingAvCommunications = groupingAvCommunications;
	}	
	public String getElpaProctorId() {
		return elpaProctorId;
	}
	public void setElpaProctorId(String elpaProctorId) {
		this.elpaProctorId = elpaProctorId;
	}
	public String getElpaProctorFirstName() {
		return elpaProctorFirstName;
	}
	public void setElpaProctorFirstName(String elpaProctorFirstName) {
		this.elpaProctorFirstName = elpaProctorFirstName;
	}
	public String getElpaProctorLastName() {
		return elpaProctorLastName;
	}
	public void setElpaProctorLastName(String elpaProctorLastName) {
		this.elpaProctorLastName = elpaProctorLastName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getTriggerEmail() {
		return triggerEmail;
	}
	public void setTriggerEmail(Boolean triggerEmail) {
		this.triggerEmail = triggerEmail;
	}
	public Boolean getEmailSent() {
		return emailSent;
	}
	public void setEmailSent(Boolean emailSent) {
		this.emailSent = emailSent;
	}
	public String getEmailSentTo() {
		return emailSentTo;
	}
	public void setEmailSentTo(String emailSentTo) {
		this.emailSentTo = emailSentTo;
	}
	public Date getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	public String getEmailTemplateIds() {
		return emailTemplateIds;
	}
	public void setEmailTemplateIds(String emailTemplateIds) {
		this.emailTemplateIds = emailTemplateIds;
	}
	public Date getEmailSentDate() {
		return emailSentDate;
	}
	public void setEmailSentDate(Date emailSentDate) {
		this.emailSentDate = emailSentDate;
	}
	public String getHistoryGovProctorId() {
		return historyGovProctorId;
	}
	public void setHistoryGovProctorId(String historyGovProctorId) {
		this.historyGovProctorId = historyGovProctorId;
	}
	public String getHistoryGovProctorFirstName() {
		return historyGovProctorFirstName;
	}
	public void setHistoryGovProctorFirstName(String historyGovProctorFirstName) {
		this.historyGovProctorFirstName = historyGovProctorFirstName;
	}
	public String getHistoryGovProctorLastName() {
		return historyGovProctorLastName;
	}
	public void setHistoryGovProctorLastName(String historyGovProctorLastName) {
		this.historyGovProctorLastName = historyGovProctorLastName;
	}
	
	
	
}
