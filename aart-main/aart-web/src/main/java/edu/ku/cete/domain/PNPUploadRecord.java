package edu.ku.cete.domain;

import java.util.Map;

import edu.ku.cete.domain.property.ValidateableRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.validation.FieldSpecification;

public class PNPUploadRecord extends ValidateableRecord {
	private static final long serialVersionUID = -7120044784303178086L;
	
	private Map<String, FieldSpecification> fieldSpecs;
	private Student student;
	private long assessmentProgramId;
	
	private String state;
	private String districtName;
	private String districtIdentifier;
	private String schoolName;
	private String schoolIdentifier;
	private String studentLastName;
	private String studentFirstName;
	private String stateStudentIdentifier;
	private String lastModifiedTime;
	private String lastModifiedBy;
	private String comprehensiveRace;
	private String hispanicEthnicity;
	private String magnification;
	private String magnificationActivateByDefault;
	private String overlayColor;
	private String overlayColorActivateByDefault;
	private String invertColorChoice;
	private String invertColorChoiceActivateByDefault;
	private String masking;
	private String maskingActivateByDefault;
	private String contrastColor;
	private String contrastColorActivateByDefault;
	private String signingType;
	private String signingTypeActivateByDefault;
	private String braille;
	private String brailleUsage;
	private String brailleActivateByDefault;
	private String keywordTranslation;
	private String keywordTranslationActivateByDefault;
	private String tactile;
	private String tactileActivateByDefault;
	private String auditoryBackground;
	private String auditoryBackgroundActivateByDefault;
	private String breaks;
	private String additionalTestingTime;
	private String additionalTestingTimeActivateByDefault;
	private String spokenAudio;
	private String spokenAudioActivateByDefault;
	private String spokenAudioReadAtStart;
	private String spokenAudioSpokenPreferences;
	private String spokenAudioDirectionsOnly;
	private String switchesScanSpeed;
	private String switchesAutomaticScanInitialDelay;
	private String switchesAutomaticScanFrequency;
	private String separateQuietOrIndividual;
	private String twoSwitchSystem;
	private String signInterpretation;
	
	private String lineNumber;

	public Map<String, FieldSpecification> getFieldSpecs() {
		return fieldSpecs;
	}

	public void setFieldSpecs(Map<String, FieldSpecification> fieldSpecs) {
		this.fieldSpecs = fieldSpecs;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

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

	public String getDistrictIdentifier() {
		return districtIdentifier;
	}

	public void setDistrictIdentifier(String districtIdentifier) {
		this.districtIdentifier = districtIdentifier;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolIdentifier() {
		return schoolIdentifier;
	}

	public void setSchoolIdentifier(String schoolIdentifier) {
		this.schoolIdentifier = schoolIdentifier;
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

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getComprehensiveRace() {
		return comprehensiveRace;
	}

	public void setComprehensiveRace(String comprehensiveRace) {
		this.comprehensiveRace = comprehensiveRace;
	}

	public String getHispanicEthnicity() {
		return hispanicEthnicity;
	}

	public void setHispanicEthnicity(String hispanicEthnicity) {
		if(hispanicEthnicity.equalsIgnoreCase("Yes")||hispanicEthnicity.equalsIgnoreCase("0")||hispanicEthnicity.equalsIgnoreCase("true")) {	
			this.hispanicEthnicity = "true";
		}
		else if(hispanicEthnicity.equalsIgnoreCase("No")||hispanicEthnicity.equalsIgnoreCase("1")||hispanicEthnicity.equalsIgnoreCase("false")) {
			this.hispanicEthnicity = "false";
		}
		else {
			this.hispanicEthnicity = hispanicEthnicity;
		}
	}

	public String getMagnification() {
		return magnification;
	}

	public void setMagnification(String magnification) {
		this.magnification = magnification;
	}

	public String getMagnificationActivateByDefault() {
		return magnificationActivateByDefault;
	}

	public void setMagnificationActivateByDefault(String magnificationActivateByDefault) {
		this.magnificationActivateByDefault = magnificationActivateByDefault;
	}

	public String getOverlayColor() {
		return overlayColor;
	}

	public void setOverlayColor(String overlayColor) {
		this.overlayColor = overlayColor;
	}

	public String getOverlayColorActivateByDefault() {
		return overlayColorActivateByDefault;
	}

	public void setOverlayColorActivateByDefault(String overlayColorActivateByDefault) {
		this.overlayColorActivateByDefault = overlayColorActivateByDefault;
	}

	public String getInvertColorChoice() {
		return invertColorChoice;
	}

	public void setInvertColorChoice(String invertColorChoice) {
		this.invertColorChoice = invertColorChoice;
	}

	public String getInvertColorChoiceActivateByDefault() {
		return invertColorChoiceActivateByDefault;
	}

	public void setInvertColorChoiceActivateByDefault(String invertColorChoiceActivateByDefault) {
		this.invertColorChoiceActivateByDefault = invertColorChoiceActivateByDefault;
	}

	public String getMasking() {
		return masking;
	}

	public void setMasking(String masking) {
		this.masking = masking;
	}

	public String getMaskingActivateByDefault() {
		return maskingActivateByDefault;
	}

	public void setMaskingActivateByDefault(String maskingActivateByDefault) {
		this.maskingActivateByDefault = maskingActivateByDefault;
	}

	public String getContrastColor() {
		return contrastColor;
	}

	public void setContrastColor(String contrastColor) {
		this.contrastColor = contrastColor;
	}

	public String getContrastColorActivateByDefault() {
		return contrastColorActivateByDefault;
	}

	public void setContrastColorActivateByDefault(String contrastColorActivateByDefault) {
		this.contrastColorActivateByDefault = contrastColorActivateByDefault;
	}

	public String getSigningType() {
		return signingType;
	}

	public void setSigningType(String signingType) {
		this.signingType = signingType;
	}

	public String getSigningTypeActivateByDefault() {
		return signingTypeActivateByDefault;
	}

	public void setSigningTypeActivateByDefault(String signingTypeActivateByDefault) {
		this.signingTypeActivateByDefault = signingTypeActivateByDefault;
	}

	public String getBraille() {
		return braille;
	}

	public void setBraille(String braille) {
		this.braille = braille;
	}

	public String getBrailleUsage() {
		return brailleUsage;
	}

	public void setBrailleUsage(String brailleUsage) {
		this.brailleUsage = brailleUsage;
	}

	public String getBrailleActivateByDefault() {
		return brailleActivateByDefault;
	}

	public void setBrailleActivateByDefault(String brailleActivateByDefault) {
		this.brailleActivateByDefault = brailleActivateByDefault;
	}

	public String getKeywordTranslation() {
		return keywordTranslation;
	}

	public void setKeywordTranslation(String keywordTranslation) {
		this.keywordTranslation = keywordTranslation;
	}

	public String getKeywordTranslationActivateByDefault() {
		return keywordTranslationActivateByDefault;
	}

	public void setKeywordTranslationActivateByDefault(String keywordTranslationActivateByDefault) {
		this.keywordTranslationActivateByDefault = keywordTranslationActivateByDefault;
	}

	public String getTactile() {
		return tactile;
	}

	public void setTactile(String tactile) {
		this.tactile = tactile;
	}

	public String getTactileActivateByDefault() {
		return tactileActivateByDefault;
	}

	public void setTactileActivateByDefault(String tactileActivateByDefault) {
		this.tactileActivateByDefault = tactileActivateByDefault;
	}

	public String getAuditoryBackground() {
		return auditoryBackground;
	}

	public void setAuditoryBackground(String auditoryBackground) {
		this.auditoryBackground = auditoryBackground;
	}

	public String getAuditoryBackgroundActivateByDefault() {
		return auditoryBackgroundActivateByDefault;
	}

	public void setAuditoryBackgroundActivateByDefault(String auditoryBackgroundActivateByDefault) {
		this.auditoryBackgroundActivateByDefault = auditoryBackgroundActivateByDefault;
	}

	public String getBreaks() {
		return breaks;
	}

	public void setBreaks(String breaks) {
		this.breaks = breaks;
	}

	public String getAdditionalTestingTime() {
		return additionalTestingTime;
	}

	public void setAdditionalTestingTime(String additionalTestingTime) {
		this.additionalTestingTime = additionalTestingTime;
	}

	public String getAdditionalTestingTimeActivateByDefault() {
		return additionalTestingTimeActivateByDefault;
	}

	public void setAdditionalTestingTimeActivateByDefault(String additionalTestingTimeActivateByDefault) {
		this.additionalTestingTimeActivateByDefault = additionalTestingTimeActivateByDefault;
	}

	public String getSpokenAudio() {
		return spokenAudio;
	}

	public void setSpokenAudio(String spokenAudio) {
		this.spokenAudio = spokenAudio;
	}

	public String getSpokenAudioActivateByDefault() {
		return spokenAudioActivateByDefault;
	}

	public void setSpokenAudioActivateByDefault(String spokenAudioActivateByDefault) {
		this.spokenAudioActivateByDefault = spokenAudioActivateByDefault;
	}

	public String getSpokenAudioReadAtStart() {
		return spokenAudioReadAtStart;
	}

	public void setSpokenAudioReadAtStart(String spokenAudioReadAtStart) {
		this.spokenAudioReadAtStart = spokenAudioReadAtStart;
	}

	public String getSpokenAudioSpokenPreferences() {
		return spokenAudioSpokenPreferences;
	}

	public void setSpokenAudioSpokenPreferences(String spokenAudioSpokenPreferences) {
		this.spokenAudioSpokenPreferences = spokenAudioSpokenPreferences;
	}

	public String getSpokenAudioDirectionsOnly() {
		return spokenAudioDirectionsOnly;
	}

	public void setSpokenAudioDirectionsOnly(String spokenAudioDirectionsOnly) {
		this.spokenAudioDirectionsOnly = spokenAudioDirectionsOnly;
	}

	public String getSwitchesScanSpeed() {
		return switchesScanSpeed;
	}

	public void setSwitchesScanSpeed(String switchesScanSpeed) {
		this.switchesScanSpeed = switchesScanSpeed;
	}

	public String getSwitchesAutomaticScanInitialDelay() {
		return switchesAutomaticScanInitialDelay;
	}

	public void setSwitchesAutomaticScanInitialDelay(String switchesAutomaticScanInitialDelay) {
		this.switchesAutomaticScanInitialDelay = switchesAutomaticScanInitialDelay;
	}

	public String getSwitchesAutomaticScanFrequency() {
		return switchesAutomaticScanFrequency;
	}

	public void setSwitchesAutomaticScanFrequency(String switchesAutomaticScanFrequency) {
		this.switchesAutomaticScanFrequency = switchesAutomaticScanFrequency;
	}

	public String getSeparateQuietOrIndividual() {
		return separateQuietOrIndividual;
	}

	public void setSeparateQuietOrIndividual(String separateQuietOrIndividual) {
		this.separateQuietOrIndividual = separateQuietOrIndividual;
	}

	public String getTwoSwitchSystem() {
		return twoSwitchSystem;
	}

	public void setTwoSwitchSystem(String twoSwitchSystem) {
		this.twoSwitchSystem = twoSwitchSystem;
	}

	public String getSignInterpretation() {
		return signInterpretation;
	}

	public void setSignInterpretation(String signInterpretation) {
		this.signInterpretation = signInterpretation;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	@Override
	public String getIdentifier() {
		return null;
	}
}
