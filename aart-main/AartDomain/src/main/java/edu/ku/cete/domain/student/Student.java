package edu.ku.cete.domain.student;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import edu.ku.cete.domain.property.Identifiable;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.NumericUtil;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author m802r921
 *
 */
public class Student extends TraceableEntity implements Serializable, Identifiable {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -8657718030184878065L;
	/**
	 * id.
	 */
	private long id;
	/**
	 * state id.
	 */
	private String stateStudentIdentifier;
	/**
	 * first name.
	 */
	private String legalFirstName;
	/**
	 * middle name.
	 */
	private String legalMiddleName;
	/**
	 * last name.
	 */
	private String legalLastName;
	/**
	 * generation code.
	 */
	private String generationCode;
	/**
	 * comprehensiveRace.To accomodate hexadecimal binary for kansas and text for other states.
	 */
	private String comprehensiveRace;
	/**
	 * primaryDisabilityCode
	 */
	private String primaryDisabilityCode;
	/**
	 * date of birth.
	 */
	private Date dateOfBirth;
	/**
	 * gender.
	 */
	private Integer gender;
	/**
	 * created.
	 */
	private Date createdDate = new Date();
	/**
	 * modified.
	 */
	private Date modifiedDate = new Date();
	/**
	 * first language.
	 */
	private String firstLanguage;

	private String username;

	private String password;

	private boolean synced;

	private String hispanicEthnicity;
	
	private Long assessmentProgramId;

	private Long commBandId;

	private Long elaBandId;
	
	private Long finalElaBandId;
	
	private Long mathBandId;
	
	private Long finalMathBandId;
	
	private Long studentTrackerBandId;
	
	private Boolean dlmStudent;
	
    private String esolParticipationCode;

    private Date usaEntryDate;

    private Date esolProgramEntryDate;
    
    private Date esolProgramEndingDate;
    
    private String firstLanguageStr;
    private String comprehensiveRaceStr;
    private String primaryDisabilityCodeStr;
    private String esolParticipationCodeStr;
    
    private String profileStatus;
    
    private Long stateId;
    private String studentAssessmentProgram;
    
	private Boolean giftedStudentEnrollment;
	private Integer currentSchoolYearEnrollment;	
	private Long currentGradeLevelEnrollment;
	private Date stateEntryDateEnrollment;
	private Date districtEntryDateEnrollment;
	private Date schoolEntryDateEnrollment;
	private String localStudentIdentifier;
	
	private String aypSchoolIdentifier;	
	private String schoolName;
	private String residenceDistrictIdentifier;
	private String districtName;
	private Long accountabilitySchoolId;
	private String accountabilitySchoolIdentifier;
	private String accountabilitySchoolName;
	private Long accountabilityDistrictId;
	private String accountabilityDistrictIdentifier;
	private String accountabilityDistrictName;
		
	//US18184 -- for student audit user story.
	private String beforeJsonString;
	private String afterJsonString;
    
	/**
     * US16289 - Add a variable
     */
    private String abbreviatedName;
  
    private Long[] studentAssessmentPrograms;

    private Long sciBandId;
    
    private Long finalSciBandId;
    
    private Long writingBandId;
    
    private String statePoolType;
    
    private Long studentTrackerId;
    
    private String externalId;
    
    private String firstContact;
	
	public String getStatePoolType() {
		return statePoolType;
	}

	public void setStatePoolType(String statePoolType) {
		this.statePoolType = statePoolType;
	}
	
	public String getAbbreviatedName() {
		return abbreviatedName;
	}
	public void setAbbreviatedName(String abbreviatedName) {
		this.abbreviatedName = abbreviatedName;
	}	
	public Long[] getStudentAssessmentPrograms() {
		return studentAssessmentPrograms;
	}
	public void setStudentAssessmentPrograms(Long[] studentAssessmentPrograms) {
		this.studentAssessmentPrograms = studentAssessmentPrograms;
	}
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long statId) {
		this.stateId = statId;
	}

	/**
	 * @return the profileStatus
	 */
	public String getProfileStatus() {
		return profileStatus;
	}
	/**
	 * @param profileStatus the profileStatus to set
	 */
	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}
	
	public Boolean getGiftedStudentEnrollment() {
		return giftedStudentEnrollment;
	}
	
	public void setGiftedStudentEnrollment(Boolean giftedStudentEnrollment) {
		this.giftedStudentEnrollment = giftedStudentEnrollment;
	}
	
	public Integer getCurrentSchoolYearEnrollment() {
		return currentSchoolYearEnrollment;
	}
	
	public void setCurrentSchoolYearEnrollment(Integer currentSchoolYearEnrollment) {
		this.currentSchoolYearEnrollment = currentSchoolYearEnrollment;
	}
	
	public Long getCurrentGradeLevelEnrollment() {
		return currentGradeLevelEnrollment;
	}
	
	public void setCurrentGradeLevelEnrollment(Long currentGradeLevelEnrollment) {
		this.currentGradeLevelEnrollment = currentGradeLevelEnrollment;
	}
	
	public Date getSchoolEntryDateEnrollment() {
		return schoolEntryDateEnrollment;
	}
	
	public void setSchoolEntryDateEnrollment(Date schoolEntryDateEnrollment) {
		this.schoolEntryDateEnrollment = schoolEntryDateEnrollment;
	}
	
	public Date getStateEntryDateEnrollment() {
		return stateEntryDateEnrollment;
	}

	public void setStateEntryDateEnrollment(Date stateEntryDateEnrollment) {
		this.stateEntryDateEnrollment = stateEntryDateEnrollment;
	}

	public Date getDistrictEntryDateEnrollment() {
		return districtEntryDateEnrollment;
	}

	public void setDistrictEntryDateEnrollment(Date districtEntryDateEnrollment) {
		this.districtEntryDateEnrollment = districtEntryDateEnrollment;
	}

	public String getLocalStudentIdentifier() {
		return localStudentIdentifier;
	}

	public void setLocalStudentIdentifier(String localStudentIdentifier) {
		this.localStudentIdentifier = localStudentIdentifier;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id2 the id to set
	 */
	public void setId(long id2) {
		this.id = id2;
	}
	/**
	 * @return the stateStudentIdentifier
	 */
	public final String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	/**
	 * @param stateStudentId the stateStudentIdentifier to set
	 */
	public final void setStateStudentIdentifier(String stateStudentId) {
		if(StringUtils.isNotBlank(stateStudentId)) {
			stateStudentId = StringUtils.trim(stateStudentId);
		}
		this.stateStudentIdentifier = stateStudentId;
	}
	/**
	 * @return the legalFirstName
	 */
	public final String getLegalFirstName() {
		return legalFirstName;
	}
	/**
	 * @param firstName the legalFirstName to set
	 */
	public final void setLegalFirstName(String firstName) {
		this.legalFirstName = firstName;
	}
	/**
	 * @return the legalMiddleName
	 */
	public final String getLegalMiddleName() {
		return legalMiddleName;
	}
	/**
	 * @param middleName the legalMiddleName to set
	 */
	public final void setLegalMiddleName(String middleName) {
		this.legalMiddleName = middleName;
	}
	/**
	 * @return the legalLastName
	 */
	public final String getLegalLastName() {
		return legalLastName;
	}
	/**
	 * @param lastName the legalLastName to set
	 */
	public final void setLegalLastName(String lastName) {
		this.legalLastName = lastName;
	}
	/**
	 * @return the generationCode
	 */
	public final String getGenerationCode() {
		return generationCode;
	}
	/**
	 * @param genCode the generationCode to set
	 */
	public final void setGenerationCode(String genCode) {
		this.generationCode = genCode;
	}
	/**
	 * @return the comprehensiveRace
	 */
	public String getComprehensiveRace() {
		if(this.comprehensiveRace != null) {
			return this.comprehensiveRace.trim();
		}
		return this.comprehensiveRace;
	}
	/**
	 * @param comprehensiveRace the comprehensiveRace to set
	 */
	public void setComprehensiveRace(String comprehensiveRace) {
		this.comprehensiveRace = comprehensiveRace;
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
	 * @return the dateOfBirth
	 */
	public final Date getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dob the dateOfBirth to set
	 */
	public final void setDateOfBirth(Date dob) {
		this.dateOfBirth = dob;
	}
	/**
	 * @return the dateOfBirth
	 */
	public final String getDateOfBirthStr() {
		if(dateOfBirth!=null)
			return ParsingConstants.BLANK + DateUtil.format(dateOfBirth, "MM/dd/yyyy");
		else 
			return "";
	}
	/**
	 * @param dob {@link Date}
	 */
	public final void setDateOfBirthStr(String dob) {
		this.dateOfBirth = DateUtil.parse(dob);
	}
	/**
	 * @return the gender
	 */
	public final Integer getGender() {
		return gender;
	}
	/**
	 * @param gen the gender to set
	 */
	public final void setGender(Integer gen) {
		this.gender = gen;
	}
	/**
	 * @return the gender
	 */
	public final String getGenderStr() {
		return gender + ParsingConstants.BLANK;
	}
	/**
	 * @param gen the gender to set
	 */
	public final void setGenderStr(String gen) {
		this.gender = NumericUtil.parse(gen);
	}
	/**
	 * @return the created
	 */
	public final Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the created to set
	 */
	public final void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	/**
	 * @return the modified
	 */
	public final Date getModifiedDate() {
		return modifiedDate;
	}
	/**
	 * @param modifiedDate the modified to set
	 */
	public final void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	/**
	 * @return {@link String}
	 */
	public final String getFirstLanguage() {
		return firstLanguage;
	}
	/**
	 * @param firstLang {@link String}
	 */
	public final void setFirstLanguage(String firstLang) {
		this.firstLanguage = firstLang;
	}
	/**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *@return synced
     */
    public boolean getSynced() {
        return synced;
    }

    /**
     * @param synced the synced to set
     */
    public void setSynced(boolean synced) {
        this.synced = synced;
    }

    /**
	 * @return
	 */
	public String getHispanicEthnicity() {
		return hispanicEthnicity;
	}

	/**
	 * @param hispanicEthnicity
	 */
	public void setHispanicEthnicity(String hispanicEthnicity) {
		if(hispanicEthnicity.equalsIgnoreCase("Yes")||hispanicEthnicity.equalsIgnoreCase("1")||hispanicEthnicity.equalsIgnoreCase("true")) {	
			this.hispanicEthnicity = "true";
		}else if(hispanicEthnicity.equalsIgnoreCase("No")||hispanicEthnicity.equalsIgnoreCase("0")||hispanicEthnicity.equalsIgnoreCase("false")) {
			this.hispanicEthnicity = "false";
		}else{
			this.hispanicEthnicity = hispanicEthnicity;
		}
	}
	
	public String getHispanicEthnicityStr() {
		if (hispanicEthnicity!=null && !hispanicEthnicity.isEmpty())
			return "Yes";
		else
			return "No";

	}
	
    public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
	@Override
	public String getIdentifier() {
		return getId() + ParsingConstants.BLANK;
	}
    
	public Long getId(int order) {
		Long id  = null;
		if(order == 0) {
			id = getId();		
		}
		return id;
	}
	
	public String getStringIdentifier(int order) {		
		String id  = null;
		 if(order == 1) {
			//state student identifier is the second order
			// identifier for student . It is only unique with in the contracting
			// organization.
			id = getStateStudentIdentifier();
		}
		return id;
	}
	
    /**
	 * This method should not be called even in debug mode.Only for development.
	 * @return the string
	 */
	public final String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public Long getCommBandId() {
		return commBandId;
	}
	public void setCommBandId(Long commBandId) {
		this.commBandId = commBandId;
	}
	public Long getElaBandId() {
		return elaBandId;
	}
	public void setElaBandId(Long elaBandId) {
		this.elaBandId = elaBandId;
	}
	public Long getFinalElaBandId() {
		return finalElaBandId;
	}
	public void setFinalElaBandId(Long finalElaBandId) {
		this.finalElaBandId = finalElaBandId;
	}
	public Long getMathBandId() {
		return mathBandId;
	}
	public String getStudentAssessmentProgram() {
		return studentAssessmentProgram;
	}
	public void setStudentAssessmentProgram(String studentAssessmentProgram) {
		this.studentAssessmentProgram = studentAssessmentProgram;
	}
	public void setMathBandId(Long mathBandId) {
		this.mathBandId = mathBandId;
	}
	public Long getFinalMathBandId() {
		return finalMathBandId;
	}
	public void setFinalMathBandId(Long finalMathBandId) {
		this.finalMathBandId = finalMathBandId;
	}
	
	public Boolean isDlmStudent() {
		return dlmStudent;
	}

	public void setDlmStudent(Boolean dlmStudent) {
		this.dlmStudent = dlmStudent;
	}	
	
	public String getDlmStudentStr() {
		return (dlmStudent!=null && dlmStudent) ? "Yes" : "No";
	}
	
	public String getEsolParticipationCode() {
		return esolParticipationCode;
	}
	public void setEsolParticipationCode(String esolParticipationCode) {
		this.esolParticipationCode = esolParticipationCode;
	}
	
	public Date getEsolProgramEntryDate() {
		return esolProgramEntryDate;
	}
	public void setEsolProgramEntryDate(Date esolProgramEntryDate) {
		this.esolProgramEntryDate = esolProgramEntryDate;
	}
	
	public final String getEsolProgramEntryDateStr() {
		if(esolProgramEntryDate!=null)
			return ParsingConstants.BLANK + DateUtil.format(esolProgramEntryDate, "MM/dd/yyyy");
		else 
			return "";
	}
	
	public Date getEsolProgramEndingDate() {
		return esolProgramEndingDate;
	}
	public void setEsolProgramEndingDate(Date esolProgramEndingDate) {
		this.esolProgramEndingDate = esolProgramEndingDate;
	}
	public Date getUsaEntryDate() {
		return usaEntryDate;
	}
	public void setUsaEntryDate(Date usaEntryDate) {
		this.usaEntryDate = usaEntryDate;
	}
	
	public final String getUsaEntryDateStr() {
		if(usaEntryDate!=null)
			return ParsingConstants.BLANK + DateUtil.format(usaEntryDate, "MM/dd/yyyy");
		else 
			return "";
	}
	/**
	 * @return the firstLanguageStr
	 */
	public String getFirstLanguageStr() {
		return firstLanguageStr;
	}
	/**
	 * @param firstLanguageStr the firstLanguageStr to set
	 */
	public void setFirstLanguageStr(String firstLanguageStr) {
		this.firstLanguageStr = firstLanguageStr;
	}
	/**
	 * @return the comprehensiveRaceStr
	 */
	public String getComprehensiveRaceStr() {
		return comprehensiveRaceStr;
	}
	/**
	 * @param comprehensiveRaceStr the comprehensiveRaceStr to set
	 */
	public void setComprehensiveRaceStr(String comprehensiveRaceStr) {
		this.comprehensiveRaceStr = comprehensiveRaceStr;
	}
	/**
	 * @return the primaryDisabilityCodeStr
	 */
	public String getPrimaryDisabilityCodeStr() {
		return primaryDisabilityCodeStr;
	}
	/**
	 * @param primaryDisabilityCodeStr the primaryDisabilityCodeStr to set
	 */
	public void setPrimaryDisabilityCodeStr(String primaryDisabilityCodeStr) {
		this.primaryDisabilityCodeStr = primaryDisabilityCodeStr;
	}
	/**
	 * @return the esolParticipationCodeStr
	 */
	public String getEsolParticipationCodeStr() {
		return esolParticipationCodeStr;
	}
	/**
	 * @param esolParticipationCodeStr the esolParticipationCodeStr to set
	 */
	public void setEsolParticipationCodeStr(String esolParticipationCodeStr) {
		this.esolParticipationCodeStr = esolParticipationCodeStr;
	}
	public Long getStudentTrackerBandId() {
		return studentTrackerBandId;
	}
	public void setStudentTrackerBandId(Long studentTrackerBandId) {
		this.studentTrackerBandId = studentTrackerBandId;
	}
	public String getBeforeJsonString() {
		return beforeJsonString;
	}
	public void setBeforeJsonString(String beforeJsonString) {
		this.beforeJsonString = beforeJsonString;
	}
	public String getAfterJsonString() {
		return afterJsonString;
	}
	public void setAfterJsonString(String afterJsonString) {
		this.afterJsonString = afterJsonString;
	}
	/**
	 * @return the sciBandId
	 */
	public Long getSciBandId() {
		return sciBandId;
	}
	/**
	 * @param sciBandId the sciBandId to set
	 */
	public void setSciBandId(Long sciBandId) {
		this.sciBandId = sciBandId;
	}
	/**
	 * @return the finalSciBandId
	 */
	public Long getFinalSciBandId() {
		return finalSciBandId;
	}
	/**
	 * @param finalSciBandId the finalSciBandId to set
	 */
	public void setFinalSciBandId(Long finalSciBandId) {
		this.finalSciBandId = finalSciBandId;
	}

	public Long getWritingBandId() {
		return writingBandId;
	}

	public void setWritingBandId(Long writingBandId) {
		this.writingBandId = writingBandId;
	}

	public Long getStudentTrackerId() {
		return studentTrackerId;
	}

	public void setStudentTrackerId(Long studentTrackerId) {
		this.studentTrackerId = studentTrackerId;
	}

	public String getAypSchoolIdentifier() {
		return aypSchoolIdentifier;
	}

	public void setAypSchoolIdentifier(String aypSchoolIdentifier) {
		this.aypSchoolIdentifier = aypSchoolIdentifier;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getResidenceDistrictIdentifier() {
		return residenceDistrictIdentifier;
	}

	public void setResidenceDistrictIdentifier(String residenceDistrictIdentifier) {
		this.residenceDistrictIdentifier = residenceDistrictIdentifier;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getAccountabilitySchoolIdentifier() {
		return accountabilitySchoolIdentifier;
	}

	public void setAccountabilitySchoolIdentifier(
			String accountabilitySchoolIdentifier) {
		this.accountabilitySchoolIdentifier = accountabilitySchoolIdentifier;
	}

	public String getAccountabilitySchoolName() {
		return accountabilitySchoolName;
	}

	public void setAccountabilitySchoolName(String accountabilitySchoolName) {
		this.accountabilitySchoolName = accountabilitySchoolName;
	}

	public String getAccountabilityDistrictIdentifier() {
		return accountabilityDistrictIdentifier;
	}

	public void setAccountabilityDistrictIdentifier(
			String accountabilityDistrictIdentifier) {
		this.accountabilityDistrictIdentifier = accountabilityDistrictIdentifier;
	}

	public String getAccountabilityDistrictName() {
		return accountabilityDistrictName;
	}

	public void setAccountabilityDistrictName(String accountabilityDistrictName) {
		this.accountabilityDistrictName = accountabilityDistrictName;
	}

	public Long getAccountabilitySchoolId() {
		return accountabilitySchoolId;
	}

	public void setAccountabilitySchoolId(Long accountabilitySchoolId) {
		this.accountabilitySchoolId = accountabilitySchoolId;
	}

	public Long getAccountabilityDistrictId() {
		return accountabilityDistrictId;
	}

	public void setAccountabilityDistrictId(Long accountabilityDistrictId) {
		this.accountabilityDistrictId = accountabilityDistrictId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getFirstContact() {
		return firstContact;
	}

	public void setFirstContact(String firstContact) {
		this.firstContact = firstContact;
	}
	
	public String getMiddleNameInitial() {
		return StringUtils.isEmpty(this.legalMiddleName) ? "" : StringUtils.trim(this.legalMiddleName).substring(0, 1);
	}
	
}
