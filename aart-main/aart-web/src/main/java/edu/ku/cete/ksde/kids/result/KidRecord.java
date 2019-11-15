/**
 * 
 */
package edu.ku.cete.ksde.kids.result;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.StudentsAssessments;
import edu.ku.cete.domain.enrollment.TestRecord;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

/**
 * @author m802r921
 *
 */
public class KidRecord extends TestRecord {

	/**
	 *
	 */
	private static final long serialVersionUID = 421135292304676261L;

	private String recordType;

	private Long seqNo;

	/**
	 * dateFormat of kid record.
	 */
	private final String dateFormat = "MM/dd/yyyy hh:mm:ss a";

	private Map<String, StudentsAssessments> assessmentInputNames = new HashMap<String, StudentsAssessments>();

	private String exitWithdrawalType;

	private Date exitWithdrawalDate;

	private String primaryExceptionalityCode;

	private String userField1;

	private String userField2;

	private String userField3;

	private Date spedProgramEndDate;

	private String kelpa;

	private String stateMathAssess;

	private String stateWritingAssess;

	private String stateELAAssessment;

	@Deprecated
	private String endOfPathwaysAssessment; // unsed 2015

	private String dlmMathProctorId;
	private String dlmMathProctorName;
	private String dlmELAProctorId;
	private String dlmELAProctorName;
	private String dlmSciProctorId;
	private String dlmSciProctorName;
	@Deprecated
	private String cpassProctorId; // unused 2015
	@Deprecated
	private String cpassProctorName; // unused 2015
	private String stateSciAssessment;
	private String stateHistGovAssessment;

	private String generalCTEAssessment;
	private String comprehensiveAgAssessment;
	private String animalSystemsAssessment;
	private String plantSystemsAssessment;
	private String manufacturingProdAssessment;
	private String designPreConstructionAssessment;
	private String financeAssessment;
	private String comprehensiveBusinessAssessment;
	private String elpa21Assessment;

	private String groupingInd1Math;
	private String groupingInd2Math;
	private String groupingInd1ELA;
	private String groupingInd2ELA;
	private String groupingInd1Sci;
	private String groupingInd2Sci;
	private String groupingInd1HistGov;
	private String groupingInd2HistGov;
	private String groupingInd1CTE;
	private String groupingInd2CTE;
	@Deprecated
	private String groupingInd1Pathways; // unused 2015
	@Deprecated
	private String groupingInd2Pathways; // unused 2015

	private String groupingComprehensiveAg;
	private String groupingAnimalSystems;
	private String groupingPlantSystems;
	private String groupingManufacturingProd;
	private String groupingDesignPreConstruction;
	private String groupingFinance;
	private String groupingComprehensiveBusiness;
	private String groupingInd1Elpa21;
	private String groupingInd2Elpa21;

	private String elpaProctorId;
	private String elpaProctorFirstName;
	private String elpaProctorLastName;
	private String avCommunicationsAssessment;
	private String groupingAvCommunications;
	
	private String recordStatus;
	private ContentArea stateSubjectArea = new ContentArea();
	private Category enrollmentStatus = new Category();
	private ContentArea stateCourse = new ContentArea();
	private User educator = new User();
	private Roster roster = new Roster();
	private Date createDate;
	private String recordCommonId;
	private String lineNumber;
	
	private String reasons;
	private String status;
	private Boolean triggerEmail;
	private Boolean emailSent;
	private String emailSentTo;
	private String emailTemplateIds;
	private String historyGovProctorId;
	private String historyGovProctorFirstName;
	private String historyGovProctorLastName;
	private User historyProctor = new User();
	private Roster historyRoster = new Roster();
	private List<KidsDashboardRecord> kidsDashboardRecords = new ArrayList<KidsDashboardRecord>();
	private String aypSchoolName;
	private String attendanceSchoolName;
	private List<String> receivedSubjectCodes = new ArrayList<String>();
	private Set<String> errorSubjectCodes = new HashSet<String>();
	private String gradeCourseAbbreviatedName;
	private String allowableSubjectCodes;
	private Boolean invalidSubjectAreaCode;
	
	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	public final String getDateFormat() {
		return dateFormat;
	}

	public Map<String, StudentsAssessments> getAssessmentInputNames() {
		return assessmentInputNames;
	}

	public void setAssessmentInputNames(Map<String, StudentsAssessments> asstInputNames) {
		this.assessmentInputNames = asstInputNames;
	}

	/**
	 * @param assessmentCategory
	 *            the assessmentInputNames to set
	 */
	public void addAssessmentInputName(Category assessmentCategory) {
		if (assessmentCategory != null && !this.assessmentInputNames.containsKey(assessmentCategory)) {
			StudentsAssessments studentsAssessments = new StudentsAssessments();
			// TODO in this case category code has the brief name of the subject
			// and the assessment code.Not an ideal choice ?
			List<String> assessmentAndContentArea = StringUtil.split(assessmentCategory.getCategoryCode());
			// no safety on array list, since this is retrieved from DB. other wise,
			// this will result in insertion of junk assessments.
			studentsAssessments.setAssessmentCode(assessmentAndContentArea.get(0));
			studentsAssessments.setContentAreaAbbreviatedName(assessmentAndContentArea.get(1));
			assessmentInputNames.put(assessmentCategory.getCategoryName(), studentsAssessments);
		}
	}

	public String getStateMathAssess() {
		return stateMathAssess;
	}

	public void setStateMathAssess(String stateMathAssess) {
		this.stateMathAssess = stateMathAssess;
	}

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getExitWithdrawalType() {
		return exitWithdrawalType;
	}

	public void setExitWithdrawalType(String exitWithdrawalType) {
		this.exitWithdrawalType = exitWithdrawalType;
	}

	public Date getExitWithdrawalDate() {
		return exitWithdrawalDate;
	}

	public void setExitWithdrawalDate(Date exitWithdrawalDate) {
		this.exitWithdrawalDate = exitWithdrawalDate;
	}

	public final String toString() {
		return getIdentifier();
		// return ToStringBuilder.reflectionToString(this);
	}

	public String getPrimaryExceptionalityCode() {
		return primaryExceptionalityCode;
	}

	public void setPrimaryExceptionalityCode(String primaryExceptionalityCode) {
		this.primaryExceptionalityCode = primaryExceptionalityCode;
	}

	public String getUserField1() {
		return userField1;
	}

	public void setUserField1(String userField1) {
		this.userField1 = userField1;
	}

	public String getUserField2() {
		return userField2;
	}

	public void setUserField2(String userField2) {
		this.userField2 = userField2;
	}

	public String getUserField3() {
		return userField3;
	}

	public void setUserField3(String userField3) {
		this.userField3 = userField3;
	}

	public String getKelpa() {
		return kelpa;
	}

	public void setKelpa(String kelpa) {
		this.kelpa = kelpa;
	}

	public String getStateWritingAssess() {
		return stateWritingAssess;
	}

	public void setStateWritingAssess(String stateWritingAssess) {
		this.stateWritingAssess = stateWritingAssess;
	}

	public Date getSpedProgramEndDate() {
		return spedProgramEndDate;
	}

	public void setSpedProgramEndDate(Date spedProgramEndDate) {
		this.spedProgramEndDate = spedProgramEndDate;
	}

	public String getGiftedCode() {
		if (this.getEnrollment().getGiftedStudent()) {
			return "GI";
		} else {
			return "";
		}
	}

	public void setGiftedCode(String code) {
		if ("GI".equalsIgnoreCase(code)) {
			this.getEnrollment().setGiftedStudent(true);
		} else {
			this.getEnrollment().setGiftedStudent(false);
		}
	}

	public String getStateELAAssessment() {
		return stateELAAssessment;
	}

	public void setStateELAAssessment(String stateELAAssessment) {
		this.stateELAAssessment = stateELAAssessment;
	}

	public String getGeneralCTEAssessment() {
		return generalCTEAssessment;
	}

	public void setGeneralCTEAssessment(String generalCTEAssessment) {
		this.generalCTEAssessment = generalCTEAssessment;
	}

	@Deprecated
	public String getEndOfPathwaysAssessment() {
		return endOfPathwaysAssessment;
	}

	@Deprecated
	public void setEndOfPathwaysAssessment(String endOfPathwaysAssessment) {
		this.endOfPathwaysAssessment = endOfPathwaysAssessment;
	}

	public String getDlmMathProctorId() {
		return dlmMathProctorId;
	}

	public void setDlmMathProctorId(String dlmMathProctorId) {
		this.dlmMathProctorId = dlmMathProctorId;
	}

	public String getDlmMathProctorName() {
		return dlmMathProctorName;
	}

	public void setDlmMathProctorName(String dlmMathProctorName) {
		this.dlmMathProctorName = dlmMathProctorName;
	}

	public String getDlmELAProctorId() {
		return dlmELAProctorId;
	}

	public void setDlmELAProctorId(String dlmELAProctorId) {
		this.dlmELAProctorId = dlmELAProctorId;
	}

	public String getDlmELAProctorName() {
		return dlmELAProctorName;
	}

	public void setDlmELAProctorName(String dlmELAProctorName) {
		this.dlmELAProctorName = dlmELAProctorName;
	}

	public String getDlmSciProctorId() {
		return dlmSciProctorId;
	}

	public void setDlmSciProctorId(String dlmSciProctorId) {
		this.dlmSciProctorId = dlmSciProctorId;
	}

	public String getDlmSciProctorName() {
		return dlmSciProctorName;
	}

	public void setDlmSciProctorName(String dlmSciProctorName) {
		this.dlmSciProctorName = dlmSciProctorName;
	}

	@Deprecated
	public String getCpassProctorId() {
		return cpassProctorId;
	}

	@Deprecated
	public void setCpassProctorId(String cpassProctorId) {
		this.cpassProctorId = cpassProctorId;
	}

	@Deprecated
	public String getCpassProctorName() {
		return cpassProctorName;
	}

	@Deprecated
	public void setCpassProctorName(String cpassProctorName) {
		this.cpassProctorName = cpassProctorName;
	}

	public String getStateSciAssessment() {
		return stateSciAssessment;
	}

	public void setStateSciAssessment(String stateSciAssessment) {
		this.stateSciAssessment = stateSciAssessment;
	}

	public String getStateHistGovAssessment() {
		return stateHistGovAssessment;
	}

	public void setStateHistGovAssessment(String stateHistGovAssessment) {
		this.stateHistGovAssessment = stateHistGovAssessment;
	}

	public String getGroupingInd1Math() {
		return groupingInd1Math;
	}

	public void setGroupingInd1Math(String groupingInd1Math) {
		this.groupingInd1Math = groupingInd1Math;
	}

	public String getGroupingInd2Math() {
		return groupingInd2Math;
	}

	public void setGroupingInd2Math(String groupingInd2Math) {
		this.groupingInd2Math = groupingInd2Math;
	}

	public String getGroupingInd1ELA() {
		return groupingInd1ELA;
	}

	public void setGroupingInd1ELA(String groupingInd1ELA) {
		this.groupingInd1ELA = groupingInd1ELA;
	}

	public String getGroupingInd2ELA() {
		return groupingInd2ELA;
	}

	public void setGroupingInd2ELA(String groupingInd2ELA) {
		this.groupingInd2ELA = groupingInd2ELA;
	}

	public String getGroupingInd1Sci() {
		return groupingInd1Sci;
	}

	public void setGroupingInd1Sci(String groupingInd1Sci) {
		this.groupingInd1Sci = groupingInd1Sci;
	}

	public String getGroupingInd2Sci() {
		return groupingInd2Sci;
	}

	public void setGroupingInd2Sci(String groupingInd2Sci) {
		this.groupingInd2Sci = groupingInd2Sci;
	}

	public String getGroupingInd1HistGov() {
		return groupingInd1HistGov;
	}

	public void setGroupingInd1HistGov(String groupingInd1HistGov) {
		this.groupingInd1HistGov = groupingInd1HistGov;
	}

	public String getGroupingInd2HistGov() {
		return groupingInd2HistGov;
	}

	public void setGroupingInd2HistGov(String groupingInd2HistGov) {
		this.groupingInd2HistGov = groupingInd2HistGov;
	}

	public String getGroupingInd1CTE() {
		return groupingInd1CTE;
	}

	public void setGroupingInd1CTE(String groupingInd1CTE) {
		this.groupingInd1CTE = groupingInd1CTE;
	}

	public String getGroupingInd2CTE() {
		return groupingInd2CTE;
	}

	public void setGroupingInd2CTE(String groupingInd2CTE) {
		this.groupingInd2CTE = groupingInd2CTE;
	}

	@Deprecated
	public String getGroupingInd1Pathways() {
		return groupingInd1Pathways;
	}

	@Deprecated
	public void setGroupingInd1Pathways(String groupingInd1Pathways) {
		this.groupingInd1Pathways = groupingInd1Pathways;
	}

	@Deprecated
	public String getGroupingInd2Pathways() {
		return groupingInd2Pathways;
	}

	@Deprecated
	public void setGroupingInd2Pathways(String groupingInd2Pathways) {
		this.groupingInd2Pathways = groupingInd2Pathways;
	}

	public String getComprehensiveAgAssessment() {
		return comprehensiveAgAssessment;
	}

	public void setComprehensiveAgAssessment(String comprehensiveAgAssessment) {
		this.comprehensiveAgAssessment = comprehensiveAgAssessment;
	}

	public String getAnimalSystemsAssessment() {
		return animalSystemsAssessment;
	}

	public void setAnimalSystemsAssessment(String animalSystemsAssessment) {
		this.animalSystemsAssessment = animalSystemsAssessment;
	}

	public String getPlantSystemsAssessment() {
		return plantSystemsAssessment;
	}

	public void setPlantSystemsAssessment(String plantSystemsAssessment) {
		this.plantSystemsAssessment = plantSystemsAssessment;
	}

	public String getManufacturingProdAssessment() {
		return manufacturingProdAssessment;
	}

	public void setManufacturingProdAssessment(String manufacturingProdAssessment) {
		this.manufacturingProdAssessment = manufacturingProdAssessment;
	}

	public String getDesignPreConstructionAssessment() {
		return designPreConstructionAssessment;
	}

	public void setDesignPreConstructionAssessment(String designPreConstructionAssessment) {
		this.designPreConstructionAssessment = designPreConstructionAssessment;
	}

	public String getFinanceAssessment() {
		return financeAssessment;
	}

	public void setFinanceAssessment(String financeAssessment) {
		this.financeAssessment = financeAssessment;
	}

	public String getComprehensiveBusinessAssessment() {
		return comprehensiveBusinessAssessment;
	}

	public void setComprehensiveBusinessAssessment(String comprehensiveBusinessAssessment) {
		this.comprehensiveBusinessAssessment = comprehensiveBusinessAssessment;
	}

	public String getElpa21Assessment() {
		return elpa21Assessment;
	}

	public void setElpa21Assessment(String elpa21Assessment) {
		this.elpa21Assessment = elpa21Assessment;
	}

	public String getGroupingComprehensiveAg() {
		return groupingComprehensiveAg;
	}

	public void setGroupingComprehensiveAg(String groupingComprehensiveAg) {
		this.groupingComprehensiveAg = groupingComprehensiveAg;
	}

	public String getGroupingAnimalSystems() {
		return groupingAnimalSystems;
	}

	public void setGroupingAnimalSystems(String groupingAnimalSystems) {
		this.groupingAnimalSystems = groupingAnimalSystems;
	}

	public String getGroupingPlantSystems() {
		return groupingPlantSystems;
	}

	public void setGroupingPlantSystems(String groupingPlantSystems) {
		this.groupingPlantSystems = groupingPlantSystems;
	}

	public String getGroupingManufacturingProd() {
		return groupingManufacturingProd;
	}

	public void setGroupingManufacturingProd(String groupingManufacturingProd) {
		this.groupingManufacturingProd = groupingManufacturingProd;
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

	public String getGroupingComprehensiveBusiness() {
		return groupingComprehensiveBusiness;
	}

	public void setGroupingComprehensiveBusiness(String groupingComprehensiveBusiness) {
		this.groupingComprehensiveBusiness = groupingComprehensiveBusiness;
	}

	public String getGroupingInd1Elpa21() {
		return groupingInd1Elpa21;
	}

	public void setGroupingInd1Elpa21(String groupingInd1Elpa21) {
		this.groupingInd1Elpa21 = groupingInd1Elpa21;
	}

	public String getGroupingInd2Elpa21() {
		return groupingInd2Elpa21;
	}

	public void setGroupingInd2Elpa21(String groupingInd2Elpa21) {
		this.groupingInd2Elpa21 = groupingInd2Elpa21;
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

	public final String getTascEducatorIdentifier() {
		return educator.getUniqueCommonIdentifier();
	}

	public final void setTascEducatorIdentifier(String educatorIdentifier) {
		educator.setUniqueCommonIdentifier(educatorIdentifier);
		educator.setUserName(educatorIdentifier + ParsingConstants.BLANK);
		educator.setPassword(educatorIdentifier + ParsingConstants.BLANK);
		// educator.setEmail(educatorIdentifier + ParsingConstants.BLANK);
		educator.setAccountNonLocked(false);
		educator.setAccountNonExpired(false);
		educator.setCredentialsNonExpired(false);
	}

	/**
	 * @param educatorIdentifier
	 *            the educatorIdentifier to set This method adds the attendance
	 *            school display identifier to the user name in order to make it
	 *            unique. To address the condition of multiple schools with the
	 *            same display identifiers, also appending attendance school
	 *            primary key. So unique common identifier is unique with in the
	 *            organization. adding the organization id makes it globally
	 *            unique.
	 */
	public final void appendSchoolIdentifier(Long attendanceSchoolId) {
		if (attendanceSchoolId != null) {
			// if educator identifier or attendance school is null then
			// the validation framework will reject the record.
			String authInfo = educator.getUniqueCommonIdentifier() + ParsingConstants.INNER_DELIM
					+ getAttendanceSchoolProgramIdentifier() + ParsingConstants.INNER_DELIM + attendanceSchoolId;
			if (StringUtils.isNotEmpty(authInfo)) {
				educator.setUserName(authInfo.substring(0, Math.min(45, authInfo.length())));
				educator.setPassword(authInfo.substring(0, Math.min(100, authInfo.length())));
				// educator.setEmail(authInfo.substring(0, Math.min(45,
				// authInfo.length())));
			}
		}
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


	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
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

	public final void setTeacherMiddleName(String educatorMiddleName) {
		educator.setMiddleName(educatorMiddleName);
	}

	public Long getLocalCourseId() {
		return (stateCourse == null ? null : stateCourse.getId());
	}

	public void setLocalCourseId(Long id) {
		stateCourse.setId(id);
	}

	public final void setEducatorEmailId(String emailId) {
		this.educator.setEmail(emailId);
	}

	public final String getEducatorEmailId() {
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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRecordCommonId() {
		return recordCommonId;
	}

	public void setRecordCommonId(String recordCommonId) {
		this.recordCommonId = recordCommonId;
	}

	public String getEducatorSchoolId() {
		return getEducator().getSchoolID();
	}

	public void setEducatorSchoolId(String educatorSchoolId) {
		getEducator().setSchoolID(educatorSchoolId);
	}

	public String getTascStateSubjectAreaCode() {
		return getStateSubjectAreaCode();
	}

	public void setTascStateSubjectAreaCode(String tascStateSubjectAreaCode) {
		this.setStateSubjectAreaCode(tascStateSubjectAreaCode);
	}

	public Long getTascLocalCourseId() {
		return getLocalCourseId();
	}

	public void setTascLocalCourseId(Long tascLocalCourseId) {
		this.setLocalCourseId(tascLocalCourseId);
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
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

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
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

	public String getEmailTemplateIds() {
		return emailTemplateIds;
	}

	public void setEmailTemplateIds(String emailTemplateIds) {
		this.emailTemplateIds = emailTemplateIds;
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

	public User getHistoryProctor() {
		return historyProctor;
	}

	public void setHistoryProctor(User historyProctor) {
		this.historyProctor = historyProctor;
	}

	public Roster getHistoryRoster() {
		return historyRoster;
	}

	public void setHistoryRoster(Roster historyRoster) {
		this.historyRoster = historyRoster;
	}

	public List<KidsDashboardRecord> getKidsDashboardRecords() {
		return kidsDashboardRecords;
	}

	public void setKidsDashboardRecords(List<KidsDashboardRecord> kidsDashboardRecords) {
		this.kidsDashboardRecords = kidsDashboardRecords;
	}

	public String getAypSchoolName() {
		return aypSchoolName;
	}

	public void setAypSchoolName(String aypSchoolName) {
		this.aypSchoolName = aypSchoolName;
	}

	public String getAttendanceSchoolName() {
		return attendanceSchoolName;
	}

	public void setAttendanceSchoolName(String attendanceSchoolName) {
		this.attendanceSchoolName = attendanceSchoolName;
	}

	public List<String> getReceivedSubjectCodes() {
		return receivedSubjectCodes;
	}

	public void setReceivedSubjectCodes(List<String> receivedSubjectCodes) {
		this.receivedSubjectCodes = receivedSubjectCodes;
	}

	public Set<String> getErrorSubjectCodes() {
		return errorSubjectCodes;
	}

	public void setErrorSubjectCodes(Set<String> errorSubjectCodes) {
		this.errorSubjectCodes = errorSubjectCodes;
	}

	public String getGradeCourseAbbreviatedName() {
		return gradeCourseAbbreviatedName;
	}

	public void setGradeCourseAbbreviatedName(String gradeCourseAbbreviatedName) {
		this.gradeCourseAbbreviatedName = gradeCourseAbbreviatedName;
	}

	public String getAllowableSubjectCodes() {
		return allowableSubjectCodes;
	}

	public void setAllowableSubjectCodes(String allowableSubjectCodes) {
		this.allowableSubjectCodes = allowableSubjectCodes;
	}

	public Boolean getInvalidSubjectAreaCode() {
		return invalidSubjectAreaCode;
	}

	public void setInvalidSubjectAreaCode(Boolean invalidSubjectAreaCode) {
		this.invalidSubjectAreaCode = invalidSubjectAreaCode;
	}

}
